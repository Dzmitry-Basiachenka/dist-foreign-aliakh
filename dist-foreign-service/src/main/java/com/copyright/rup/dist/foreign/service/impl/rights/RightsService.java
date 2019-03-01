package com.copyright.rup.dist.foreign.service.impl.rights;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.domain.BaseEntity;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.integration.rest.rms.RightsAssignmentResult;
import com.copyright.rup.dist.common.service.api.discrepancy.IRmsGrantProcessorService;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.integration.rms.api.IRmsIntegrationService;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IRightsService;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.executor.IChainExecutor;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Service contains logic related to Rights Assignment.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 03/22/2018
 *
 * @author Nikita Levyankov
 */
@Service
public class RightsService implements IRightsService {
    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IUsageService usageService;
    @Autowired
    private IUsageRepository usageRepository;
    @Autowired
    private IRmsIntegrationService rmsIntegrationService;
    @Autowired
    private IUsageAuditService auditService;
    @Autowired
    @Qualifier("df.service.rmsGrantProcessorService")
    private IRmsGrantProcessorService rmsGrantProcessorService;
    @Autowired
    private IRightsholderService rightsholderService;
    @Autowired
    private IChainExecutor<Usage> chainExecutor;

    @Override
    @Transactional
    public void sendForRightsAssignment() {
        List<Usage> usages = usageRepository.findByStatuses(UsageStatusEnum.RH_NOT_FOUND);
        LOGGER.info("Send for Rights Assignment. Started. UsagesCount={}", LogUtils.size(usages));
        if (CollectionUtils.isNotEmpty(usages)) {
            RightsAssignmentResult result = rmsIntegrationService.sendForRightsAssignment(
                usages.stream().map(Usage::getWrWrkInst).collect(Collectors.toSet()));
            if (result.isSuccessful()) {
                Set<String> usageIds = usages.stream()
                    .map(BaseEntity::getId)
                    .collect(Collectors.toSet());
                usageRepository.updateStatus(usageIds, UsageStatusEnum.SENT_FOR_RA);
                auditService.logAction(usageIds, UsageActionTypeEnum.SENT_FOR_RA,
                    String.format("Sent for RA: job id '%s'", result.getJobId()));
                LOGGER.info("Send for Rights Assignment. Finished. UsagesCount={}, JobId={}", LogUtils.size(usages),
                    result.getJobId());
            } else {
                LOGGER.warn("Send for Rights Assignment. Failed. Reason={}, UsagesIds={}", result.getErrorMessage(),
                    usages.stream().map(Usage::getId).collect(Collectors.toList()));
            }
        } else {
            LOGGER.info("Send for Rights Assignment. Skipped. Reason=There are no usages for sending");
        }
    }

    @Override
    @Transactional
    public void updateRightsSentForRaUsages() {
        Arrays.asList(FdaConstants.FAS_PRODUCT_FAMILY, FdaConstants.CLA_FAS_PRODUCT_FAMILY).forEach(productFamily -> {
            List<Usage> usages =
                usageService.getUsagesByStatusAndProductFamily(UsageStatusEnum.SENT_FOR_RA, productFamily);
            if (CollectionUtils.isNotEmpty(usages)) {
                LogUtils.ILogWrapper usagesCount = LogUtils.size(usages);
                LOGGER.info("Update rights for SEND_FOR_RA usages. Started. ProductFamily={},, UsagesCount={}",
                    productFamily, usagesCount);
                updateSentForRaUsagesRightsholders(usages);
                LOGGER.info("Update rights for SEND_FOR_RA usages. Finished. ProductFamily={}, UsagesCount={}",
                    productFamily, usagesCount);
            } else {
                LOGGER.info("Update rights for SEND_FOR_RA usages. Skipped. Reason=There are no SENT_FOR_RA {} usages.",
                    productFamily);
            }
        });
    }

    @Override
    @Transactional
    public void updateRight(Usage usage) {
        Long wrWrkInst = usage.getWrWrkInst();
        Set<String> usageId = Collections.singleton(usage.getId());
        Map<Long, Long> wrWrkInstToRhAccountNumberMap =
            rmsGrantProcessorService.getAccountNumbersByWrWrkInsts(Collections.singletonList(wrWrkInst),
                usage.getProductFamily());
        Long rhAccountNumber = wrWrkInstToRhAccountNumberMap.get(wrWrkInst);
        if (Objects.nonNull(rhAccountNumber)) {
            List<Rightsholder> rightsholders =
                rightsholderService.updateRightsholders(Collections.singleton(rhAccountNumber));
            usage.setRightsholder(rightsholders.get(0));
            usage.setStatus(UsageStatusEnum.RH_FOUND);
            usageService.updateProcessedUsage(usage);
            auditService.logAction(usageId, UsageActionTypeEnum.RH_FOUND,
                String.format("Rightsholder account %s was found in RMS", rhAccountNumber));
        } else {
            usage.setStatus(UsageStatusEnum.RH_NOT_FOUND);
            usageService.updateProcessedUsage(usage);
            auditService.logAction(usageId, UsageActionTypeEnum.RH_NOT_FOUND,
                String.format("Rightsholder account for %s was not found in RMS", wrWrkInst));
        }
    }

    private long updateSentForRaUsagesRightsholders(List<Usage> usages) {
        Map<Long, List<Usage>> wrWrkInstToUsagesMap = usages.stream()
            .collect(Collectors.groupingBy(Usage::getWrWrkInst));
        String productFamily = usages.iterator().next().getProductFamily();
        Map<Long, Long> wrWrkInstToAccountNumber =
            rmsGrantProcessorService.getAccountNumbersByWrWrkInsts(Lists.newArrayList(wrWrkInstToUsagesMap.keySet()),
                productFamily);
        AtomicLong eligibleUsagesCount = new AtomicLong();
        wrWrkInstToAccountNumber.forEach((wrWrkInst, rhAccountNumber) -> {
            List<Usage> usagesToUpdate = wrWrkInstToUsagesMap.get(wrWrkInst);
            Set<String> usageIds = usagesToUpdate.stream().map(Usage::getId).collect(Collectors.toSet());
            usageRepository.updateStatusAndRhAccountNumber(usageIds, UsageStatusEnum.RH_FOUND, rhAccountNumber);
            eligibleUsagesCount.addAndGet(usageIds.size());
            auditService.logAction(usageIds, UsageActionTypeEnum.RH_FOUND,
                String.format("Rightsholder account %s was found in RMS", rhAccountNumber));
            chainExecutor.execute(usagesToUpdate, ChainProcessorTypeEnum.ELIGIBILITY);
        });
        rightsholderService.updateRightsholders(Sets.newHashSet(wrWrkInstToAccountNumber.values()));
        return eligibleUsagesCount.longValue();
    }
}
