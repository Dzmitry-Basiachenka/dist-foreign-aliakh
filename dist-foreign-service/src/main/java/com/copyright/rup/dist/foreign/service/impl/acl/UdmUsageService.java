package com.copyright.rup.dist.foreign.service.impl.acl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.domain.BaseEntity;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.CompanyInformation;
import com.copyright.rup.dist.foreign.domain.UdmActionReason;
import com.copyright.rup.dist.foreign.domain.UdmAuditFieldToValuesMap;
import com.copyright.rup.dist.foreign.domain.UdmBatch;
import com.copyright.rup.dist.foreign.domain.UdmIneligibleReason;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.domain.UdmUsageDto;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;
import com.copyright.rup.dist.foreign.integration.telesales.api.ITelesalesService;
import com.copyright.rup.dist.foreign.repository.api.IUdmActionReasonRepository;
import com.copyright.rup.dist.foreign.repository.api.IUdmIneligibleReasonRepository;
import com.copyright.rup.dist.foreign.repository.api.IUdmUsageRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmBaselineService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmTypeOfUseService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageService;
import com.copyright.rup.dist.foreign.service.api.executor.IChainExecutor;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;
import com.copyright.rup.dist.foreign.service.impl.InconsistentUsageStateException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of {@link IUdmUsageService}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 04/30/2021
 *
 * @author Uladzislau Shalamitski
 */
@Service
public class UdmUsageService implements IUdmUsageService {

    private static final BigDecimal DEFAULT_STATISTICAL_MULTIPLIER =
        BigDecimal.ONE.setScale(5, BigDecimal.ROUND_HALF_UP);
    private static final Logger LOGGER = RupLogUtils.getLogger();
    private static final String USAGE_EDIT_REASON = "The field '%s' was edited. Old Value is %s. New Value is %s";
    private static final String NOT_SPECIFIED = "not specified";

    @Autowired
    private IUdmUsageRepository udmUsageRepository;
    @Autowired
    private IUdmActionReasonRepository udmActionReasonRepository;
    @Autowired
    private IUdmIneligibleReasonRepository udmIneligibleReasonRepository;
    @Autowired
    private UdmAnnualMultiplierCalculator udmAnnualMultiplierCalculator;
    @Autowired
    private UdmAnnualizedCopiesCalculator udmAnnualizedCopiesCalculator;
    @Autowired
    private IUdmTypeOfUseService udmTypeOfUseService;
    @Autowired
    private IUdmBaselineService baselineService;
    @Autowired
    @Qualifier("df.integration.telesalesCacheService")
    private ITelesalesService telesalesService;
    @Autowired
    private IUdmUsageAuditService udmUsageAuditService;
    @Autowired
    @Qualifier("udmUsageChainExecutor")
    private IChainExecutor<UdmUsage> chainExecutor;
    @Value("$RUP{dist.foreign.udm.record.threshold}")
    private int udmRecordsThreshold;

    @Override
    @Transactional
    public void insertUdmUsages(UdmBatch udmBatch, List<UdmUsage> udmUsages) {
        String userName = RupContextUtils.getUserName();
        int size = udmUsages.size();
        LOGGER.info("Insert UDM usages. Started. UsageBatchName={}, UsagesCount={}, UserName={}", udmBatch.getName(),
            size, userName);
        LocalDate periodEndDate = createPeriodEndDate(udmBatch);
        Map<String, String> udmTouToRmsTou = udmTypeOfUseService.getUdmTouToRmsTouMap();
        udmUsages.forEach(usage -> {
            usage.setBatchId(udmBatch.getId());
            usage.setPeriod(udmBatch.getPeriod());
            usage.setPeriodEndDate(periodEndDate);
            usage.setTypeOfUse(udmTouToRmsTou.get(usage.getReportedTypeOfUse()));
            usage.setAnnualMultiplier(udmAnnualMultiplierCalculator.calculate(usage.getSurveyStartDate(),
                usage.getSurveyEndDate()));
            usage.setStatisticalMultiplier(DEFAULT_STATISTICAL_MULTIPLIER);
            if (Objects.nonNull(usage.getReportedTypeOfUse())) {
                usage.setAnnualizedCopies(udmAnnualizedCopiesCalculator.calculate(usage.getReportedTypeOfUse(),
                    usage.getQuantity(), usage.getAnnualMultiplier(), usage.getStatisticalMultiplier()));
            }
            CompanyInformation companyInformation = telesalesService.getCompanyInformation(usage.getCompanyId());
            usage.setCompanyName(companyInformation.getName());
            usage.setDetailLicenseeClassId(companyInformation.getDetailLicenseeClassId());
            usage.setCreateUser(userName);
            usage.setUpdateUser(userName);
            udmUsageRepository.insert(usage);
        });
        udmUsages.forEach(usage -> {
            udmUsageAuditService.logAction(usage.getId(), UsageActionTypeEnum.LOADED,
                "Uploaded in '" + udmBatch.getName() + "' Batch");
            if (UsageStatusEnum.INELIGIBLE == usage.getStatus()) {
                udmUsageAuditService.logAction(usage.getId(), UsageActionTypeEnum.INELIGIBLE,
                    "No reported use");
            }
        });
        LOGGER.info("Insert UDM usages. Finished. UsageBatchName={}, UsagesCount={}, UserName={}", udmBatch.getName(),
            size, userName);
    }

    @Override
    @Transactional
    public void updateUsage(UdmUsageDto udmUsageDto, UdmAuditFieldToValuesMap fieldToValueChangesMap,
                            boolean isResearcher) {
        String userName = RupContextUtils.getUserName();
        LOGGER.debug("Update UDM usage. Started. Usage={}, UserName={}", udmUsageDto, userName);
        if (isResearcher && udmUsageDto.getStatus() == UsageStatusEnum.NEW) {
            udmUsageDto.setAssignee(null);
        }
        udmUsageDto.setUpdateUser(userName);
        udmUsageRepository.update(udmUsageDto);
        getUdmUsageEditAuditReasons(fieldToValueChangesMap).forEach(reason ->
            udmUsageAuditService.logAction(udmUsageDto.getId(), UsageActionTypeEnum.USAGE_EDIT, reason));
        LOGGER.debug("Update UDM usage. Finished. Usage={}, UserName={}", udmUsageDto, userName);
    }

    @Override
    public boolean isOriginalDetailIdExist(String originalDetailId) {
        return udmUsageRepository.isOriginalDetailIdExist(originalDetailId);
    }

    @Override
    public List<UdmUsageDto> getUsageDtos(UdmUsageFilter filter, Pageable pageable, Sort sort) {
        return !filter.isEmpty()
            ? udmUsageRepository.findDtosByFilter(filter, pageable, sort)
            : Collections.emptyList();
    }

    @Override
    public int getUsagesCount(UdmUsageFilter filter) {
        return !filter.isEmpty() ? udmUsageRepository.findCountByFilter(filter) : 0;
    }

    @Override
    public void sendForMatching(Set<UdmUsageDto> udmUsageDtos) {
        sendForMatching(udmUsageDtos.stream().map(this::convertUdmDtoToUsage).collect(Collectors.toList()));
    }

    @Override
    public void sendForMatching(List<UdmUsage> udmUsages) {
        chainExecutor.execute(() -> {
            List<UdmUsage> usagesInNewStatus = udmUsages.stream()
                .filter(usage -> UsageStatusEnum.NEW == usage.getStatus())
                .collect(Collectors.toList());
            chainExecutor.execute(usagesInNewStatus, ChainProcessorTypeEnum.MATCHING);
        });
    }

    @Override
    public void updateProcessedUsage(UdmUsage udmUsage) {
        String usageId = udmUsageRepository.updateProcessedUsage(udmUsage);
        if (Objects.isNull(usageId)) {
            throw new InconsistentUsageStateException(udmUsage);
        }
        udmUsage.setVersion(udmUsage.getVersion() + 1);
    }

    @Override
    public List<String> getUdmUsageIdsByStatus(UsageStatusEnum status) {
        return udmUsageRepository.findIdsByStatus(status);
    }

    @Override
    public List<UdmUsage> getUdmUsagesByIds(List<String> udmUsageIds) {
        return udmUsageRepository.findByIds(udmUsageIds);
    }

    @Override
    public List<Integer> getPeriods() {
        return udmUsageRepository.findPeriods();
    }

    @Override
    public List<String> getAssignees() {
        return udmUsageRepository.findAssignees();
    }

    @Override
    public List<String> getPublicationTypes() {
        return udmUsageRepository.findPublicationTypes();
    }

    @Override
    public List<String> getPublicationFormats() {
        return udmUsageRepository.findPublicationFormats();
    }

    @Override
    public List<UdmActionReason> getAllActionReasons() {
        return udmActionReasonRepository.findAll();
    }

    @Override
    public List<UdmIneligibleReason> getAllIneligibleReasons() {
        return udmIneligibleReasonRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteUdmBatchDetails(UdmBatch udmBatch) {
        udmUsageAuditService.deleteActionsByBatchId(udmBatch.getId());
        udmUsageRepository.deleteByBatchId(udmBatch.getId());
    }

    @Override
    @Transactional
    public void assignUsages(Set<UdmUsageDto> udmUsages) {
        String userName = RupContextUtils.getUserName();
        udmUsages.forEach(usage -> {
            if (Objects.isNull(usage.getAssignee())) {
                udmUsageAuditService.logAction(usage.getId(), UsageActionTypeEnum.ASSIGNEE_CHANGE,
                    String.format("Assignment was changed. Usage was assigned to ‘%s’", userName));
            } else {
                udmUsageAuditService.logAction(usage.getId(), UsageActionTypeEnum.ASSIGNEE_CHANGE,
                    String.format("Assignment was changed. Old assignee is '%s'. " +
                        "New assignee is '%s'", usage.getAssignee(), userName));
            }
        });
        Set<String> udmUsageIds = udmUsages.stream()
            .map(BaseEntity::getId)
            .collect(Collectors.toSet());
        udmUsageRepository.updateAssignee(udmUsageIds, userName, userName);
    }

    @Override
    public void unassignUsages(Set<String> udmUsageIds) {
        udmUsageRepository.updateAssignee(udmUsageIds, null, RupContextUtils.getUserName());
    }

    @Override
    @Transactional
    public void updateUsages(Map<UdmUsageDto, UdmAuditFieldToValuesMap> udmUsageDtoToFieldValuesMap,
                             boolean isResearcher) {
        udmUsageDtoToFieldValuesMap.forEach((udmUsageDto, udmAuditFieldToValuesMap) ->
            updateUsage(udmUsageDto, udmAuditFieldToValuesMap, isResearcher));
    }

    @Override
    @Transactional
    public Pair<Integer, Integer> publishUdmUsagesToBaseline(Integer period) {
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Publish to baseline UDM usages. Started. Period={}. UserName={}", period, userName);
        Set<String> publishedUsageIds = udmUsageRepository.publishUdmUsagesToBaseline(period, userName);
        int publishedCount = publishedUsageIds.size();
        publishedUsageIds.forEach(usageId ->
            udmUsageAuditService.logAction(usageId, UsageActionTypeEnum.PUBLISH_TO_BASELINE,
                String.format("UDM usage was published to baseline by '%s'", userName)));
        int removedCount = baselineService.removeFromBaseline(period);
        LOGGER.info("Publish to baseline UDM usages. Finished. PublishedCount={}, RemovedCount={}",
            publishedCount, removedCount);
        return Pair.of(publishedCount, removedCount);
    }

    @Override
    public int getUdmRecordThreshold() {
        return udmRecordsThreshold;
    }

    private UdmUsage convertUdmDtoToUsage(UdmUsageDto usageDto) {
        UdmUsage udmUsage = new UdmUsage();
        udmUsage.setId(usageDto.getId());
        udmUsage.setWrWrkInst(usageDto.getWrWrkInst());
        udmUsage.setTypeOfUse(usageDto.getTypeOfUse());
        udmUsage.setReportedStandardNumber(usageDto.getReportedStandardNumber());
        udmUsage.setStandardNumber(usageDto.getStandardNumber());
        udmUsage.setReportedTitle(usageDto.getReportedTitle());
        udmUsage.setSystemTitle(usageDto.getSystemTitle());
        udmUsage.setPeriodEndDate(usageDto.getPeriodEndDate());
        udmUsage.setStatus(usageDto.getStatus());
        udmUsage.setVersion(usageDto.getVersion() + 1);
        return udmUsage;
    }

    private LocalDate createPeriodEndDate(UdmBatch udmBatch) {
        String stringPeriod = String.valueOf(udmBatch.getPeriod());
        int year = Integer.parseInt(stringPeriod.substring(0, 4));
        int month = Integer.parseInt(stringPeriod.substring(4, 6));
        return 6 == month ? LocalDate.of(year, month, 30) : LocalDate.of(year, month, 31);
    }

    private List<String> getUdmUsageEditAuditReasons(UdmAuditFieldToValuesMap fieldToValueChangesMap) {
        List<String> result = new ArrayList<>();
        fieldToValueChangesMap.entrySet().forEach(fieldToValueChangesMapEntry -> {
            Pair<String, String> valuePair = fieldToValueChangesMapEntry.getValue();
            if (!Objects.equals(valuePair.getLeft(), valuePair.getRight())) {
                String oldValue = StringUtils.isBlank(valuePair.getLeft())
                    ? NOT_SPECIFIED : String.format("'%s'", valuePair.getLeft());
                String newValue = StringUtils.isBlank(valuePair.getRight())
                    ? NOT_SPECIFIED : String.format("'%s'", valuePair.getRight());
                result.add(String.format(USAGE_EDIT_REASON, fieldToValueChangesMapEntry.getKey(), oldValue, newValue));
            }
        });
        return result;
    }
}
