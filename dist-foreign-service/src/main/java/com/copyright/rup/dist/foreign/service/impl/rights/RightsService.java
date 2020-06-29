package com.copyright.rup.dist.foreign.service.impl.rights;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.domain.GrantPriority;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.domain.RmsGrant;
import com.copyright.rup.dist.common.domain.RmsGrant.RightStatusEnum;
import com.copyright.rup.dist.common.domain.job.JobInfo;
import com.copyright.rup.dist.common.domain.job.JobStatusEnum;
import com.copyright.rup.dist.common.integration.rest.rms.IRmsRightsService;
import com.copyright.rup.dist.common.integration.rest.rms.RightsAssignmentResult;
import com.copyright.rup.dist.common.repository.api.IGrantPriorityRepository;
import com.copyright.rup.dist.common.service.api.discrepancy.IRmsGrantProcessorService;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IRightsService;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.aacl.IAaclUsageService;
import com.copyright.rup.dist.foreign.service.api.executor.IChainExecutor;
import com.copyright.rup.dist.foreign.service.api.fas.IFasUsageService;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
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

    private static final int PRODUCT_FAMILIES_COUNT = 1;
    private static final String PRINT_TYPE_OF_USE = "PRINT";
    private static final String DIGITAL_TYPE_OF_USE = "DIGITAL";
    private static final String NTS_WITHDRAWN_AUDIT_MESSAGE =
        "Detail was made eligible for NTS because sum of gross amounts, grouped by Wr Wrk Inst, is less than $100";
    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IUsageBatchService usageBatchService;
    @Autowired
    private IUsageService usageService;
    @Autowired
    private IFasUsageService fasUsageService;
    @Autowired
    private IAaclUsageService aaclUsageService;
    @Autowired
    private IUsageAuditService auditService;
    @Autowired
    @Qualifier("df.service.rmsGrantProcessorService")
    private IRmsGrantProcessorService rmsGrantProcessorService;
    @Autowired
    private IRightsholderService rightsholderService;
    @Autowired
    @Qualifier("df.service.rmsCacheService")
    private IRmsRightsService rmsRightsService;
    @Autowired
    @Qualifier("usageChainChunkExecutor")
    private IChainExecutor<Usage> chainExecutor;
    @Autowired
    private IGrantPriorityRepository grantPriorityRepository;

    @Override
    @Transactional
    public JobInfo sendForRightsAssignment() {
        updateNtsWithdrawnUsages();
        JobInfo jobInfo;
        List<String> availableBatches = usageBatchService.getBatchNamesAvailableForRightsAssignment();
        LOGGER.info("Send for Rights Assignment. Started. BatchesCount={}", availableBatches.size());
        AtomicInteger updatedUsagesCount = new AtomicInteger(0);
        AtomicInteger failedUsagesCount = new AtomicInteger(0);
        Set<String> failedBatches = new HashSet<>();
        if (CollectionUtils.isNotEmpty(availableBatches)) {
            availableBatches.forEach(batchName -> {
                Map<Long, Set<String>> wrWrkInstToUsageIds =
                    usageService.getWrWrkInstToUsageIdsForRightsAssignment(batchName);
                Set<String> usageIds = wrWrkInstToUsageIds.values()
                    .stream()
                    .flatMap(Set::stream)
                    .collect(Collectors.toSet());
                RightsAssignmentResult result =
                    usageService.sendForRightsAssignment(batchName, wrWrkInstToUsageIds.keySet(), usageIds);
                if (result.isSuccessful()) {
                    updatedUsagesCount.addAndGet(usageIds.size());
                } else {
                    failedUsagesCount.addAndGet(usageIds.size());
                    failedBatches.add(batchName);
                }
            });
            if (0 == failedUsagesCount.get()) {
                jobInfo = new JobInfo(JobStatusEnum.FINISHED, "UsagesCount=" + updatedUsagesCount);
            } else {
                jobInfo = new JobInfo(JobStatusEnum.FAILED, "FailedBatches=" + String.join(",", failedBatches) +
                    ", UsagesCount=" + updatedUsagesCount + ", NotReportedUsage=" + failedUsagesCount);
            }
            LOGGER.info("Send for Rights Assignment. Finished. BatchesCount={}, SentUsagesCount={}",
                availableBatches.size(), updatedUsagesCount);
        } else {
            LOGGER.info("Send for Rights Assignment. Skipped. Reason=There are no usages for sending");
            jobInfo = new JobInfo(JobStatusEnum.SKIPPED, "Reason=There are no usages");
        }
        return jobInfo;
    }

    @Override
    @Transactional
    public JobInfo updateRightsSentForRaUsages() {
        JobInfo jobInfo = null;
        for (String productFamily : FdaConstants.FAS_FAS2_PRODUCT_FAMILY_SET) {
            List<Usage> usages =
                usageService.getUsagesByStatusAndProductFamily(UsageStatusEnum.SENT_FOR_RA, productFamily);
            if (CollectionUtils.isNotEmpty(usages)) {
                LogUtils.ILogWrapper usagesCount = LogUtils.size(usages);
                String message = String.format("ProductFamily=%s, UsagesCount=%s; ", productFamily, usagesCount);
                LOGGER.info("Update rights for SEND_FOR_RA usages. Started. {}", message);
                updateSentForRaUsagesRightsholders(usages, productFamily);
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
    public void updateRights(List<Usage> usages, boolean logAction) {
        if (CollectionUtils.isNotEmpty(usages)) {
            String productFamily = validateAndGetProductFamily(usages);
            List<Long> wrWrkInsts = usages.stream()
                .map(Usage::getWrWrkInst)
                .distinct()
                .collect(Collectors.toList());
            Set<String> licenseTypes = getLicenseTypes(productFamily);
            Map<Long, Long> wrWrkInstToRhAccountNumberMap =
                rmsGrantProcessorService.getAccountNumbersByWrWrkInsts(wrWrkInsts, productFamily,
                    FdaConstants.RIGHT_STATUSES_GRANT_DENY, Collections.emptySet(), licenseTypes);
            usages.forEach(usage -> {
                Long wrWrkInst = usage.getWrWrkInst();
                Long rhAccountNumber = wrWrkInstToRhAccountNumberMap.get(wrWrkInst);
                if (Objects.nonNull(rhAccountNumber)) {
                    usage.setRightsholder(buildRightsholder(rhAccountNumber));
                    usage.setStatus(UsageStatusEnum.RH_FOUND);
                    usageService.updateProcessedUsage(usage);
                    logAction(Collections.singleton(usage.getId()), UsageActionTypeEnum.RH_FOUND,
                        String.format("Rightsholder account %s was found in RMS", rhAccountNumber), logAction);
                } else {
                    usage.setStatus(UsageStatusEnum.RH_NOT_FOUND);
                    usageService.updateProcessedUsage(usage);
                    logAction(Collections.singleton(usage.getId()), UsageActionTypeEnum.RH_NOT_FOUND,
                        String.format("Rightsholder account for %s was not found in RMS", wrWrkInst), logAction);
                }
            });
        }
    }

    @Override
    @Transactional
    public void updateAaclRights(List<Usage> usages) {
        if (CollectionUtils.isNotEmpty(usages)) {
            String productFamily = validateAndGetProductFamily(usages);
            Map<LocalDate, List<Usage>> batchPeriodEndDatesToUsages = usages
                .stream()
                .collect(Collectors.groupingBy(usage -> usage.getAaclUsage().getBatchPeriodEndDate()));
            batchPeriodEndDatesToUsages.forEach((batchPeriodEndDate, groupedUsages) -> {
                List<Long> wrWrkInsts = groupedUsages
                    .stream()
                    .map(Usage::getWrWrkInst)
                    .collect(Collectors.toList());
                Map<Long, List<RmsGrant>> wrWrkInstToGrants = rmsRightsService.getGrants(wrWrkInsts,
                    batchPeriodEndDate, Collections.singleton(RightStatusEnum.GRANT.name()),
                    ImmutableSet.of(PRINT_TYPE_OF_USE, DIGITAL_TYPE_OF_USE), getLicenseTypes(productFamily))
                    .stream()
                    .collect(Collectors.groupingBy(RmsGrant::getWrWrkInst));
                groupedUsages.forEach(usage -> {
                    Set<RmsGrant> eligibleGrants = wrWrkInstToGrants
                        .getOrDefault(usage.getWrWrkInst(), Collections.emptyList())
                        .stream()
                        .filter(grant -> FdaConstants.AACL_PRODUCT_FAMILY.equals(grant.getProductFamily()))
                        .collect(Collectors.toSet());
                    RmsGrant grant = ObjectUtils.defaultIfNull(
                        findGrantByTypeOfUse(eligibleGrants, DIGITAL_TYPE_OF_USE),
                        findGrantByTypeOfUse(eligibleGrants, PRINT_TYPE_OF_USE));
                    if (Objects.nonNull(grant)) {
                        Long rhAccountNumber = grant.getWorkGroupOwnerOrgNumber().longValueExact();
                        usage.setRightsholder(buildRightsholder(rhAccountNumber));
                        usage.setStatus(UsageStatusEnum.RH_FOUND);
                        usage.getAaclUsage().setRightLimitation(
                            DIGITAL_TYPE_OF_USE.equals(grant.getTypeOfUse()) ? "ALL" : PRINT_TYPE_OF_USE);
                        aaclUsageService.updateProcessedUsage(usage);
                        logAction(Collections.singleton(usage.getId()), UsageActionTypeEnum.RH_FOUND,
                            String.format("Rightsholder account %s was found in RMS", rhAccountNumber), true);
                    } else {
                        usage.setStatus(UsageStatusEnum.RH_NOT_FOUND);
                    }
                });
            });
        }
    }

    private RmsGrant findGrantByTypeOfUse(Set<RmsGrant> grants, String typeOfUse) {
        return grants.stream().filter(rmsGrant -> typeOfUse.equals(rmsGrant.getTypeOfUse())).findFirst().orElse(null);
    }

    private void updateNtsWithdrawnUsages() {
        LOGGER.info("Update RH_NOT_FOUND usages to NTS_WITHDRAWN status. Started.");
        List<String> updatedIds = fasUsageService.updateNtsWithdrawnUsagesAndGetIds();
        if (CollectionUtils.isNotEmpty(updatedIds)) {
            updatedIds.forEach(usageId ->
                auditService.logAction(usageId, UsageActionTypeEnum.ELIGIBLE_FOR_NTS, NTS_WITHDRAWN_AUDIT_MESSAGE)
            );
            LOGGER.info("Update RH_NOT_FOUND usages to NTS_WITHDRAWN status. Finished. Updated UsagesCount={}",
                LogUtils.size(updatedIds));
        } else {
            LOGGER.info("Update RH_NOT_FOUND usages to NTS_WITHDRAWN status. Skipped. Reason=There are no usages");
        }
    }

    private void logAction(Set<String> usageId, UsageActionTypeEnum type, String message, boolean logAction) {
        if (logAction) {
            auditService.logAction(usageId, type, message);
        }
    }

    private Rightsholder buildRightsholder(Long rhAccountNumber) {
        List<Rightsholder> rightsholders =
            rightsholderService.updateRightsholders(Collections.singleton(rhAccountNumber));
        Rightsholder rightsholder = new Rightsholder();
        if (CollectionUtils.isNotEmpty(rightsholders)) {
            rightsholder = rightsholders.get(0);
        } else {
            rightsholder.setAccountNumber(rhAccountNumber);
            LOGGER.warn("Rightsholder account {} was not found in PRM", rhAccountNumber);
        }
        return rightsholder;
    }

    private void updateSentForRaUsagesRightsholders(List<Usage> usages, String productFamily) {
        Map<Long, List<Usage>> wrWrkInstToUsagesMap = usages.stream()
            .collect(Collectors.groupingBy(Usage::getWrWrkInst));
        Map<Long, Long> wrWrkInstToAccountNumber =
            rmsGrantProcessorService.getAccountNumbersByWrWrkInsts(Lists.newArrayList(wrWrkInstToUsagesMap.keySet()),
                productFamily, FdaConstants.RIGHT_STATUSES_GRANT_DENY, Collections.emptySet(),
                getLicenseTypes(productFamily));
        wrWrkInstToAccountNumber.forEach((wrWrkInst, rhAccountNumber) -> {
            List<Usage> usagesToUpdate = wrWrkInstToUsagesMap.get(wrWrkInst);
            Set<String> usageIds = usagesToUpdate.stream().map(Usage::getId).collect(Collectors.toSet());
            usageService.updateStatusAndRhAccountNumber(usageIds, UsageStatusEnum.RH_FOUND, rhAccountNumber);
            auditService.logAction(usageIds, UsageActionTypeEnum.RH_FOUND,
                String.format("Rightsholder account %s was found in RMS", rhAccountNumber));
            chainExecutor.execute(usagesToUpdate, ChainProcessorTypeEnum.ELIGIBILITY);
        });
        rightsholderService.updateRightsholders(Sets.newHashSet(wrWrkInstToAccountNumber.values()));
    }

    private Set<String> getLicenseTypes(String productFamily) {
        return grantPriorityRepository.findByProductFamily(productFamily).stream()
            .map(GrantPriority::getLicenseType)
            .collect(Collectors.toSet());
    }

    private String validateAndGetProductFamily(List<Usage> usages) {
        Set<String> productFamilies = usages.stream()
            .map(Usage::getProductFamily)
            .collect(Collectors.toSet());
        if (PRODUCT_FAMILIES_COUNT < productFamilies.size()) {
            throw new RupRuntimeException("The list of usages to update rights contains multiple product families");
        }
        return productFamilies.iterator().next();
    }
}
