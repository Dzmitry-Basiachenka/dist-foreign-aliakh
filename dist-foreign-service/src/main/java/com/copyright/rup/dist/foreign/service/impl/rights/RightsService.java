package com.copyright.rup.dist.foreign.service.impl.rights;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.domain.BaseEntity;
import com.copyright.rup.dist.common.integration.camel.IProducer;
import com.copyright.rup.dist.common.service.api.discrepancy.IRmsGrantsProcessorService;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.integration.rms.api.IRmsIntegrationService;
import com.copyright.rup.dist.foreign.integration.rms.api.RightsAssignmentResult;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IRightsService;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashMap;
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
    private IUsageRepository usageRepository;
    @Autowired
    private IRmsIntegrationService rmsIntegrationService;
    @Autowired
    private IUsageAuditService auditService;
    @Autowired
    @Qualifier("df.service.rmsGrantsProcessorService")
    private IRmsGrantsProcessorService rmsGrantsProcessorService;
    @Autowired
    private IRightsholderService rightsholderService;
    @Autowired
    @Qualifier("df.service.rightsProducer")
    private IProducer<Usage> rightsProducer;
    @Autowired
    @Qualifier("df.service.rhTaxProducer")
    private IProducer<Usage> rhTaxProducer;

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
    public void updateRights() {
        List<Usage> usages = usageRepository.findByStatuses(UsageStatusEnum.WORK_FOUND);
        if (CollectionUtils.isNotEmpty(usages)) {
            LOGGER.info("Update Rights. Started. UsagesStatus={}, UsagesCount={}", UsageStatusEnum.WORK_FOUND,
                LogUtils.size(usages));
            usages.forEach(rightsProducer::send);
            LOGGER.info("Update Rights. Finished. UsagesStatus={}, UsagesCount={}", UsageStatusEnum.WORK_FOUND,
                LogUtils.size(usages));
        } else {
            LOGGER.info("Update Rights. Skipped. Reason=There are no WORK_FOUND usages");
        }
        usages = usageRepository.findByStatuses(UsageStatusEnum.SENT_FOR_RA);
        if (CollectionUtils.isNotEmpty(usages)) {
            LOGGER.info("Update Rights. Started. UsagesStatus={}, UsagesCount={}", UsageStatusEnum.SENT_FOR_RA,
                LogUtils.size(usages));
            updateSentForRaUsagesRightsholders(usages);
            LOGGER.info("Update Rights. Finished. UsagesStatus={}, UsagesCount={}", UsageStatusEnum.SENT_FOR_RA,
                LogUtils.size(usages));
        } else {
            LOGGER.info("Send for getting Rights. Skipped. Reason=There are no SENT_FOR_RA usages.");
        }
    }

    @Override
    public void updateRight(Usage usage) {
        Long wrWrkInst = usage.getWrWrkInst();
        Set<String> usageId = Collections.singleton(usage.getId());
        Map<Long, Long> wrWrkInstToRhAccountNumberMap =
            rmsGrantsProcessorService.getAccountNumbersByWrWrkInsts(Collections.singletonList(wrWrkInst));
        Long rhAccountNumber = wrWrkInstToRhAccountNumberMap.get(wrWrkInst);
        boolean isNtsUsage = FdaConstants.NTS_PRODUCT_FAMILY.equals(usage.getProductFamily());
        if (Objects.nonNull(rhAccountNumber)) {
            usageRepository.updateStatusAndRhAccountNumber(usageId,
                isNtsUsage ? UsageStatusEnum.RH_FOUND : UsageStatusEnum.ELIGIBLE, rhAccountNumber);
            auditService.logAction(usageId, UsageActionTypeEnum.RH_FOUND,
                String.format("Rightsholder account %s was found in RMS", rhAccountNumber));
            if (isNtsUsage) {
                rhTaxProducer.send(usage);
            }
            rightsholderService.updateRightsholders(Collections.singleton(rhAccountNumber));
        } else if (isNtsUsage) {
            auditService.deleteActions(usage.getId());
            usageRepository.deleteById(usage.getId());
        } else {
            usageRepository.updateStatus(usageId, UsageStatusEnum.RH_NOT_FOUND);
            auditService.logAction(usageId, UsageActionTypeEnum.RH_NOT_FOUND,
                String.format("Rightsholder account for %s was not found in RMS", wrWrkInst));
        }
    }

    private long updateSentForRaUsagesRightsholders(List<Usage> usages) {
        Map<Long, Set<String>> wrWrkInstToUsageIds = usages.stream().collect(
            Collectors.groupingBy(Usage::getWrWrkInst, HashMap::new, Collectors
                .mapping(Usage::getId, Collectors.toSet())));
        Map<Long, Long> wrWrkInstToAccountNumber =
            rmsGrantsProcessorService.getAccountNumbersByWrWrkInsts(Lists.newArrayList(wrWrkInstToUsageIds.keySet()));
        AtomicLong eligibleUsagesCount = new AtomicLong();
        wrWrkInstToAccountNumber.forEach((wrWrkInst, rhAccountNumber) -> {
            Set<String> usageIds = wrWrkInstToUsageIds.get(wrWrkInst);
            usageRepository.updateStatusAndRhAccountNumber(usageIds, UsageStatusEnum.ELIGIBLE, rhAccountNumber);
            eligibleUsagesCount.addAndGet(usageIds.size());
            auditService.logAction(usageIds, UsageActionTypeEnum.RH_FOUND,
                String.format("Rightsholder account %s was found in RMS", rhAccountNumber));
        });
        rightsholderService.updateRightsholders(Sets.newHashSet(wrWrkInstToAccountNumber.values()));
        return eligibleUsagesCount.longValue();
    }
}
