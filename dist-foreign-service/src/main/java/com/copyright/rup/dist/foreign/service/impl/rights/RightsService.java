package com.copyright.rup.dist.foreign.service.impl.rights;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.domain.job.JobInfo;
import com.copyright.rup.dist.common.domain.job.JobStatusEnum;
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
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
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
    private IUsageBatchService usageBatchService;
    @Autowired
    private IUsageService usageService;
    //TODO {dbaraukova} replace repository with service
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
    public JobInfo sendForRightsAssignment() {
        Set<String> usageIds = usageService.getIdsByStatus(UsageStatusEnum.RH_NOT_FOUND);
        LOGGER.info("Send for Rights Assignment. Started. UsagesCount={}", LogUtils.size(usageIds));
        JobInfo jobInfo;
        if (CollectionUtils.isNotEmpty(usageIds)) {
            Map<String, Set<Long>> batchNameToWrWrkInsts =
                usageBatchService.getBatchNameToWrWrkInstsForRightsAssignment(usageIds);
            //TODO {dbaraukova} adjust RA service and send Batch Name as job id
            Set<Long> wrWrkInsts = batchNameToWrWrkInsts.values()
                .stream()
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
            RightsAssignmentResult result = rmsIntegrationService.sendForRightsAssignment(wrWrkInsts);
            if (result.isSuccessful()) {
                usageRepository.updateStatus(usageIds, UsageStatusEnum.SENT_FOR_RA);
                auditService.logAction(usageIds, UsageActionTypeEnum.SENT_FOR_RA,
                    String.format("Sent for RA: job id '%s'", result.getJobId()));
                LOGGER.info("Send for Rights Assignment. Finished. UsagesCount={}, JobId={}", LogUtils.size(usageIds),
                    result.getJobId());
                jobInfo = new JobInfo(JobStatusEnum.FINISHED, "UsagesCount=" + LogUtils.size(usageIds));
            } else {
                LOGGER.warn("Send for Rights Assignment. Failed. Reason={}, UsagesIds={}", result.getErrorMessage(),
                    usageIds);
                jobInfo = new JobInfo(JobStatusEnum.FAILED,
                    "UsagesCount=" + LogUtils.size(usageIds) + ", Reason=" + result.getErrorMessage());
            }
        } else {
            LOGGER.info("Send for Rights Assignment. Skipped. Reason=There are no usages for sending");
            jobInfo = new JobInfo(JobStatusEnum.SKIPPED, "Reason=There are no usages");
        }
        return jobInfo;
    }

    @Override
    @Transactional
    public JobInfo updateRightsSentForRaUsages() {
        List<String> fasProductFamilies =
            Arrays.asList(FdaConstants.FAS_PRODUCT_FAMILY, FdaConstants.CLA_FAS_PRODUCT_FAMILY);
        JobInfo jobInfo = null;
        for (String productFamily : fasProductFamilies) {
            List<Usage> usages =
                usageService.getUsagesByStatusAndProductFamily(UsageStatusEnum.SENT_FOR_RA, productFamily);
            if (CollectionUtils.isNotEmpty(usages)) {
                LogUtils.ILogWrapper usagesCount = LogUtils.size(usages);
                String message = String.format("ProductFamily=%s, UsagesCount=%s; ", productFamily, usagesCount);
                LOGGER.info("Update rights for SEND_FOR_RA usages. Started. {}", message);
                updateSentForRaUsagesRightsholders(usages);
                LOGGER.info("Update rights for SEND_FOR_RA usages. Finished. {}", message);
                if (Objects.isNull(jobInfo)) {
                    jobInfo = new JobInfo(JobStatusEnum.FINISHED, message);
                } else {
                    if (JobStatusEnum.SKIPPED == jobInfo.getStatus()) {
                        jobInfo.setStatus(JobStatusEnum.FINISHED);
                    }
                    jobInfo.setResult(jobInfo.getResult() + message);
                }
            } else {
                String message = "ProductFamily=" + productFamily + ", Reason=There are no usages;";
                LOGGER.info("Update rights for SEND_FOR_RA usages. Skipped. {}", message);
                if (Objects.isNull(jobInfo)) {
                    jobInfo = new JobInfo(JobStatusEnum.SKIPPED, message);
                } else {
                    jobInfo.setResult(jobInfo.getResult() + message);
                }
            }
        }
        return jobInfo;
    }

    @Override
    @Transactional
    public void updateRight(Usage usage, boolean logAction) {
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
            logAction(usageId, UsageActionTypeEnum.RH_FOUND,
                String.format("Rightsholder account %s was found in RMS", rhAccountNumber), logAction);
        } else {
            usage.setStatus(UsageStatusEnum.RH_NOT_FOUND);
            usageService.updateProcessedUsage(usage);
            logAction(usageId, UsageActionTypeEnum.RH_NOT_FOUND,
                String.format("Rightsholder account for %s was not found in RMS", wrWrkInst), logAction);
        }
    }

    private void logAction(Set<String> usageId, UsageActionTypeEnum type, String message, boolean logAction) {
        if (logAction) {
            auditService.logAction(usageId, type, message);
        }
    }

    private void updateSentForRaUsagesRightsholders(List<Usage> usages) {
        Map<Long, List<Usage>> wrWrkInstToUsagesMap = usages.stream()
            .collect(Collectors.groupingBy(Usage::getWrWrkInst));
        String productFamily = usages.iterator().next().getProductFamily();
        Map<Long, Long> wrWrkInstToAccountNumber =
            rmsGrantProcessorService.getAccountNumbersByWrWrkInsts(Lists.newArrayList(wrWrkInstToUsagesMap.keySet()),
                productFamily);
        wrWrkInstToAccountNumber.forEach((wrWrkInst, rhAccountNumber) -> {
            List<Usage> usagesToUpdate = wrWrkInstToUsagesMap.get(wrWrkInst);
            Set<String> usageIds = usagesToUpdate.stream().map(Usage::getId).collect(Collectors.toSet());
            usageRepository.updateStatusAndRhAccountNumber(usageIds, UsageStatusEnum.RH_FOUND, rhAccountNumber);
            auditService.logAction(usageIds, UsageActionTypeEnum.RH_FOUND,
                String.format("Rightsholder account %s was found in RMS", rhAccountNumber));
            chainExecutor.execute(usagesToUpdate, ChainProcessorTypeEnum.ELIGIBILITY);
        });
        rightsholderService.updateRightsholders(Sets.newHashSet(wrWrkInstToAccountNumber.values()));
    }
}
