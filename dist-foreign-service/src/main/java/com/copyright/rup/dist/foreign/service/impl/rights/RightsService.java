package com.copyright.rup.dist.foreign.service.impl.rights;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.domain.GrantPriority;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.domain.RmsGrant;
import com.copyright.rup.dist.common.domain.job.JobInfo;
import com.copyright.rup.dist.common.domain.job.JobStatusEnum;
import com.copyright.rup.dist.common.integration.rest.rms.IRmsRightsService;
import com.copyright.rup.dist.common.integration.rest.rms.RightsAssignmentResult;
import com.copyright.rup.dist.common.repository.api.IGrantPriorityRepository;
import com.copyright.rup.dist.common.service.api.discrepancy.IRmsGrantProcessorService;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.domain.UdmValue;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IRightsService;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.aacl.IAaclUsageService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageService;
import com.copyright.rup.dist.foreign.service.api.executor.IChainExecutor;
import com.copyright.rup.dist.foreign.service.api.fas.IFasUsageService;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import io.micrometer.core.annotation.Timed;

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
@Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
public class RightsService implements IRightsService {

    private static final int PRODUCT_FAMILIES_COUNT = 1;
    private static final String PRINT_TYPE_OF_USE = "PRINT";
    private static final String DIGITAL_TYPE_OF_USE = "DIGITAL";
    private static final String NTS_WITHDRAWN_AUDIT_MESSAGE =
        "Detail was made eligible for NTS because sum of gross amounts, grouped by Wr Wrk Inst, is less than $100";
    private static final String RH_FOUND_REASON_FORMAT = "Rightsholder account %s was found in RMS";
    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Value("#{$RUP{dist.foreign.rest.rms.rights.partition_size}}")
    private int rightsPartitionSize;
    @Value("#{$RUP{dist.foreign.rest.rms.rights.statuses}}")
    private Map<String, Set<String>> productFamilyToRightStatusesMap;
    @Autowired
    private IUsageBatchService usageBatchService;
    @Autowired
    private IUdmUsageService udmUsageService;
    @Autowired
    private IUsageService usageService;
    @Autowired
    private IFasUsageService fasUsageService;
    @Autowired
    private IAaclUsageService aaclUsageService;
    @Autowired
    private IUsageAuditService auditService;
    @Autowired
    private IUdmUsageAuditService udmAuditService;
    @Autowired
    @Qualifier("df.service.rmsGrantProcessorCacheService")
    private IRmsGrantProcessorService rmsGrantProcessorCacheService;
    @Autowired
    @Qualifier("df.service.rmsGrantProcessorService")
    private IRmsGrantProcessorService rmsGrantProcessorService;
    @Autowired
    private IRightsholderService rightsholderService;
    @Autowired
    @Qualifier("df.service.rmsCacheService")
    private IRmsRightsService rmsRightsService;
    @Autowired
    @Qualifier("usageChainExecutor")
    private IChainExecutor<Usage> chainExecutor;
    @Autowired
    private IGrantPriorityRepository grantPriorityRepository;

    @Override
    @Transactional
    public JobInfo sendForRightsAssignment() {
        updateNtsWithdrawnUsages();
        JobInfo jobInfo;
        List<String> availableBatches = usageBatchService.getBatchNamesForRightsAssignment();
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
                var message = String.format("ProductFamily=%s, UsagesCount=%s; ", productFamily, usagesCount);
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
                var message = "ProductFamily=" + productFamily + ", Reason=There are no usages;";
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
                rmsGrantProcessorCacheService.getAccountNumbersByWrWrkInsts(wrWrkInsts, LocalDate.now(), productFamily,
                    productFamilyToRightStatusesMap.get(productFamily), Set.of(), licenseTypes);
            usages.forEach(usage -> {
                Long wrWrkInst = usage.getWrWrkInst();
                Long rhAccountNumber = wrWrkInstToRhAccountNumberMap.get(wrWrkInst);
                if (Objects.nonNull(rhAccountNumber)) {
                    usage.setRightsholder(buildRightsholder(rhAccountNumber));
                    usage.setStatus(UsageStatusEnum.RH_FOUND);
                    usageService.updateProcessedUsage(usage);
                    logAction(Set.of(usage.getId()), UsageActionTypeEnum.RH_FOUND,
                        String.format(RH_FOUND_REASON_FORMAT, rhAccountNumber), logAction);
                } else {
                    usage.setStatus(UsageStatusEnum.RH_NOT_FOUND);
                    usageService.updateProcessedUsage(usage);
                    logAction(Set.of(usage.getId()), UsageActionTypeEnum.RH_NOT_FOUND,
                        String.format("Rightsholder account for %s was not found in RMS", wrWrkInst), logAction);
                }
            });
        }
    }

    @Override
    @Transactional
    public void updateUdmRights(List<UdmUsage> udmUsages) {
        if (CollectionUtils.isNotEmpty(udmUsages)) {
            Set<String> licenseTypes = getLicenseTypes(FdaConstants.ACL_UDM_USAGE_PRODUCT_FAMILY);
            Map<LocalDate, List<UdmUsage>> periodEndDatesToUdmUsagesMap = udmUsages
                .stream()
                .collect(Collectors.groupingBy(UdmUsage::getPeriodEndDate));
            periodEndDatesToUdmUsagesMap.forEach((periodEndDate, groupedUdmUsages) -> {
                List<Long> wrWrkInsts = groupedUdmUsages.stream()
                    .map(UdmUsage::getWrWrkInst)
                    .collect(Collectors.toList());
                Map<Long, Long> wrWrkInstToRhAccountNumberMap = rmsGrantProcessorCacheService
                    .getAccountNumbersByWrWrkInsts(wrWrkInsts, periodEndDate, FdaConstants.ACL_UDM_USAGE_PRODUCT_FAMILY,
                        productFamilyToRightStatusesMap.get(FdaConstants.ACL_UDM_USAGE_PRODUCT_FAMILY),
                        Set.of(), licenseTypes);
                groupedUdmUsages.forEach(usage -> {
                    Long wrWrkInst = usage.getWrWrkInst();
                    Long rhAccountNumber = wrWrkInstToRhAccountNumberMap.get(wrWrkInst);
                    if (Objects.nonNull(rhAccountNumber)) {
                        usage.setRightsholder(buildRightsholder(rhAccountNumber));
                        usage.setStatus(UsageStatusEnum.RH_FOUND);
                        udmAuditService.logAction(usage.getId(), UsageActionTypeEnum.RH_FOUND,
                            String.format(RH_FOUND_REASON_FORMAT, rhAccountNumber));
                    } else {
                        usage.setStatus(UsageStatusEnum.RH_NOT_FOUND);
                        udmAuditService.logAction(usage.getId(), UsageActionTypeEnum.RH_NOT_FOUND,
                            String.format("Rightsholder account for %s was not found in RMS", wrWrkInst));
                    }
                    udmUsageService.updateProcessedUsage(usage);
                });
            });
        }
    }

    @Override
    @Transactional
    public void updateUdmValuesRights(List<UdmValue> udmValues, Integer period) {
        if (CollectionUtils.isNotEmpty(udmValues)) {
            Set<Long> rhAccountNumbers = new HashSet<>();
            Map<Long, Long> wrWrkInstToRhAccountNumberMap = new HashMap<>();
            Set<String> rightsStatuses = productFamilyToRightStatusesMap.get(FdaConstants.ACL_UDM_VALUE_PRODUCT_FAMILY);
            Set<String> licenseTypes = getLicenseTypes(FdaConstants.ACL_UDM_VALUE_PRODUCT_FAMILY);
            LocalDate periodEndDate = buildPeriodEndDateFromPeriod(period);
            List<Long> wrWrkInsts = udmValues.stream()
                .map(UdmValue::getWrWrkInst)
                .collect(Collectors.toList());
            Iterables.partition(wrWrkInsts, rightsPartitionSize).forEach(wrWrkInstsPart ->
                wrWrkInstToRhAccountNumberMap.putAll(rmsGrantProcessorService.getAccountNumbersByWrWrkInsts(
                    wrWrkInstsPart, periodEndDate, FdaConstants.ACL_UDM_VALUE_PRODUCT_FAMILY, rightsStatuses,
                    Set.of(), licenseTypes)));
            udmValues.forEach(value -> {
                Long rhAccountNumber = wrWrkInstToRhAccountNumberMap.get(value.getWrWrkInst());
                if (Objects.nonNull(rhAccountNumber)) {
                    rhAccountNumbers.add(rhAccountNumber);
                    value.setRhAccountNumber(rhAccountNumber);
                }
            });
            rightsholderService.updateRightsholders(rhAccountNumbers);
        }
    }

    @Override
    @Transactional
    public void updateAaclRights(List<Usage> usages) {
        if (CollectionUtils.isNotEmpty(usages)) {
            String productFamily = validateAndGetProductFamily(usages);
            Map<LocalDate, List<Usage>> batchPeriodEndDatesToUsagesMap = usages
                .stream()
                .collect(Collectors.groupingBy(usage -> usage.getAaclUsage().getBatchPeriodEndDate()));
            batchPeriodEndDatesToUsagesMap.forEach((batchPeriodEndDate, groupedUsages) -> {
                List<Long> wrWrkInsts = groupedUsages
                    .stream()
                    .map(Usage::getWrWrkInst)
                    .collect(Collectors.toList());
                Map<Long, List<RmsGrant>> wrWrkInstToGrants = rmsRightsService.getGrants(wrWrkInsts,
                    batchPeriodEndDate, productFamilyToRightStatusesMap.get(FdaConstants.AACL_PRODUCT_FAMILY),
                    ImmutableSet.of(PRINT_TYPE_OF_USE, DIGITAL_TYPE_OF_USE), getLicenseTypes(productFamily))
                    .stream()
                    .collect(Collectors.groupingBy(RmsGrant::getWrWrkInst));
                groupedUsages.forEach(usage -> {
                    Set<RmsGrant> eligibleGrants = wrWrkInstToGrants
                        .getOrDefault(usage.getWrWrkInst(), List.of())
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
                        logAction(Set.of(usage.getId()), UsageActionTypeEnum.RH_FOUND,
                            String.format(RH_FOUND_REASON_FORMAT, rhAccountNumber), true);
                    } else {
                        usage.setStatus(UsageStatusEnum.RH_NOT_FOUND);
                    }
                });
            });
        }
    }

    @Override
    @Transactional
    public void updateSalRights(List<Usage> usages) {
        if (CollectionUtils.isNotEmpty(usages)) {
            String productFamily = validateAndGetProductFamily(usages);
            Map<LocalDate, List<Usage>> batchPeriodEndDatesToUsagesMap = usages
                .stream()
                .collect(Collectors.groupingBy(usage -> usage.getSalUsage().getBatchPeriodEndDate()));
            batchPeriodEndDatesToUsagesMap.forEach((batchPeriodEndDate, groupedUsages) -> {
                List<Long> wrWrkInsts = groupedUsages
                    .stream()
                    .map(Usage::getWrWrkInst)
                    .collect(Collectors.toList());
                Map<Long, List<RmsGrant>> wrWrkInstToGrants = rmsRightsService.getGrants(wrWrkInsts,
                    batchPeriodEndDate, productFamilyToRightStatusesMap.get(FdaConstants.SAL_PRODUCT_FAMILY),
                    ImmutableSet.of(DIGITAL_TYPE_OF_USE), getLicenseTypes(productFamily))
                    .stream()
                    .collect(Collectors.groupingBy(RmsGrant::getWrWrkInst));
                parseRmsGrantsAndUpdateUsages(groupedUsages, wrWrkInstToGrants, FdaConstants.SAL_PRODUCT_FAMILY);
            });
        }
    }

    @Override
    public void updateAclciRights(List<Usage> usages) {
        if (CollectionUtils.isNotEmpty(usages)) {
            validateAndGetProductFamily(usages);
            Map<LocalDate, List<Usage>> batchPeriodEndDatesToUsagesMap = usages
                .stream()
                .collect(Collectors.groupingBy(usage -> usage.getAclciUsage().getBatchPeriodEndDate()));
            batchPeriodEndDatesToUsagesMap.forEach((batchPeriodEndDate, groupedUsagesByPeriodAndDate) -> {
                Map<String, List<Usage>> licenseTypeToUsagesMap = groupedUsagesByPeriodAndDate
                    .stream()
                    .collect(Collectors.groupingBy(usage -> usage.getAclciUsage().getLicenseType().name()));
                licenseTypeToUsagesMap.forEach((licenseType, groupedUsagesByLicenseType) -> {
                    List<Long> wrWrkInsts = groupedUsagesByLicenseType
                        .stream()
                        .map(Usage::getWrWrkInst)
                        .distinct()
                        .collect(Collectors.toList());
                    Map<Long, List<RmsGrant>> wrWrkInstToGrants = rmsRightsService.getGrants(wrWrkInsts,
                        batchPeriodEndDate, productFamilyToRightStatusesMap.get(FdaConstants.ACLCI_PRODUCT_FAMILY),
                        ImmutableSet.of(licenseType), ImmutableSet.of(licenseType))
                        .stream()
                        .collect(Collectors.groupingBy(RmsGrant::getWrWrkInst));
                    parseRmsGrantsAndUpdateUsages(groupedUsagesByLicenseType, wrWrkInstToGrants, licenseType);
                });
            });
        }
    }

    private RmsGrant findGrantByTypeOfUse(Set<RmsGrant> grants, String typeOfUse) {
        return grants.stream()
            .filter(rmsGrant -> typeOfUse.equals(rmsGrant.getTypeOfUse()))
            .findFirst()
            .orElse(null);
    }

    private RmsGrant findGrantByStatus(Set<RmsGrant> grants, String rightStatus) {
        return grants.stream()
            .filter(rmsGrant -> rightStatus.equals(rmsGrant.getRightStatus()))
            .findFirst()
            .orElse(null);
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
        List<Rightsholder> rightsholders = rightsholderService.updateRightsholders(Set.of(rhAccountNumber));
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
        Map<Long, Long> wrWrkInstToAccountNumber = rmsGrantProcessorCacheService.getAccountNumbersByWrWrkInsts(
            new ArrayList<>(wrWrkInstToUsagesMap.keySet()), LocalDate.now(), productFamily,
            productFamilyToRightStatusesMap.get(productFamily), Set.of(), getLicenseTypes(productFamily));
        wrWrkInstToAccountNumber.forEach((wrWrkInst, rhAccountNumber) -> {
            List<Usage> usagesToUpdate = wrWrkInstToUsagesMap.get(wrWrkInst);
            Set<String> usageIds = usagesToUpdate.stream().map(Usage::getId).collect(Collectors.toSet());
            usageService.updateStatusAndRhAccountNumber(usageIds, UsageStatusEnum.RH_FOUND, rhAccountNumber);
            auditService.logAction(usageIds, UsageActionTypeEnum.RH_FOUND,
                String.format(RH_FOUND_REASON_FORMAT, rhAccountNumber));
            chainExecutor.execute(usagesToUpdate, ChainProcessorTypeEnum.ELIGIBILITY);
        });
        rightsholderService.updateRightsholders(new HashSet<>(wrWrkInstToAccountNumber.values()));
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

    private LocalDate buildPeriodEndDateFromPeriod(Integer period) {
        int year = period / 100;
        int month = period % 100;
        return 6 == month ? LocalDate.of(year, month, 30) : LocalDate.of(year, month, 31);
    }

    private void parseRmsGrantsAndUpdateUsages(List<Usage> usages, Map<Long, List<RmsGrant>> wrWrkInstToGrants,
                                               String productFamily) {
        usages.forEach(usage -> {
            Long wrWrkInst = usage.getWrWrkInst();
            Set<RmsGrant> eligibleGrants = wrWrkInstToGrants
                .getOrDefault(wrWrkInst, List.of())
                .stream()
                .filter(grant -> productFamily.equals(grant.getProductFamily()))
                .collect(Collectors.toSet());
            RmsGrant grant = ObjectUtils.defaultIfNull(
                findGrantByStatus(eligibleGrants, FdaConstants.RIGHT_STATUS_GRANT),
                findGrantByStatus(eligibleGrants, FdaConstants.RIGHT_STATUS_DENY));
            if (Objects.nonNull(grant)) {
                Long rhAccountNumber = grant.getWorkGroupOwnerOrgNumber().longValueExact();
                if (FdaConstants.RIGHT_STATUS_GRANT.equals(grant.getRightStatus())) {
                    usage.setRightsholder(buildRightsholder(rhAccountNumber));
                    usage.setStatus(UsageStatusEnum.RH_FOUND);
                    usageService.updateProcessedUsage(usage);
                    logAction(Set.of(usage.getId()), UsageActionTypeEnum.RH_FOUND,
                        String.format(RH_FOUND_REASON_FORMAT, rhAccountNumber), true);
                } else {
                    usage.setStatus(UsageStatusEnum.WORK_NOT_GRANTED);
                    usageService.updateProcessedUsage(usage);
                    logAction(Set.of(usage.getId()), UsageActionTypeEnum.WORK_NOT_GRANTED,
                        String.format("Right for %s is denied for rightsholder account %s", wrWrkInst, rhAccountNumber),
                        true);
                }
            } else {
                usage.setStatus(UsageStatusEnum.RH_NOT_FOUND);
                usageService.updateProcessedUsage(usage);
                logAction(Set.of(usage.getId()), UsageActionTypeEnum.RH_NOT_FOUND,
                    String.format("Rightsholder account for %s was not found in RMS", wrWrkInst), true);
            }
        });
    }
}
