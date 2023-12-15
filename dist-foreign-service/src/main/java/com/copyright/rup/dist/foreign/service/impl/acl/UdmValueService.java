package com.copyright.rup.dist.foreign.service.impl.acl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.BaseEntity;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.Currency;
import com.copyright.rup.dist.foreign.domain.UdmValue;
import com.copyright.rup.dist.foreign.domain.UdmValueActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UdmValueDto;
import com.copyright.rup.dist.foreign.domain.UdmValueStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmValueFilter;
import com.copyright.rup.dist.foreign.repository.api.IUdmBaselineRepository;
import com.copyright.rup.dist.foreign.repository.api.IUdmValueRepository;
import com.copyright.rup.dist.foreign.service.api.IRightsService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmValueAuditService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmValueService;

import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import io.micrometer.core.annotation.Timed;

/**
 * Implementation of {@link IUdmValueService}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/15/2021
 *
 * @author Dzmitry Basiachenka
 */
@Service
@Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
public class UdmValueService implements IUdmValueService {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    private final List<Currency> allCurrencies;
    @Autowired
    private IUdmValueRepository udmValueRepository;
    @Autowired
    private IUdmValueAuditService udmValueAuditService;
    @Autowired
    private IUdmBaselineRepository baselineRepository;
    @Autowired
    private IRightsService rightsService;
    @Value("$RUP{dist.foreign.grid.multi.select.record.threshold}")
    private int udmRecordsThreshold;

    /**
     * Constructor.
     *
     * @param currencyCodesToCurrencyNamesMap map from currency codes to currency names
     */
    @Autowired
    public UdmValueService(@Value("#{$RUP{dist.foreign.udm.currencies}}")
                           Map<String, String> currencyCodesToCurrencyNamesMap) {
        this.allCurrencies = List.copyOf(currencyCodesToCurrencyNamesMap
            .entrySet()
            .stream()
            .map(entry -> new Currency(entry.getKey(), entry.getValue()))
            .collect(Collectors.toList()));
    }

    @Override
    @Transactional
    public void updateValue(UdmValueDto udmValueDto, List<String> actionReasons) {
        String userName = RupContextUtils.getUserName();
        LOGGER.debug("Update UDM value. Started. Value={}, Reasons={}, UserName={}", udmValueDto, actionReasons,
            userName);
        udmValueDto.setUpdateUser(userName);
        udmValueRepository.update(udmValueDto);
        actionReasons.forEach(actionReason ->
            udmValueAuditService.logAction(udmValueDto.getId(), UdmValueActionTypeEnum.VALUE_EDIT, actionReason));
        LOGGER.debug("Update UDM value. Finished. Value={}, Reasons={}, UserName={}", udmValueDto, actionReasons,
            userName);
    }

    @Override
    public List<Currency> getAllCurrencies() {
        return List.copyOf(allCurrencies);
    }

    @Override
    public List<Integer> getPeriods() {
        return udmValueRepository.findPeriods();
    }

    @Override
    public List<UdmValueDto> getValueDtos(UdmValueFilter filter, Pageable pageable, Sort sort) {
        return !filter.isEmpty()
            ? udmValueRepository.findDtosByFilter(filter, pageable, sort)
            : List.of();
    }

    @Override
    public int getValueCount(UdmValueFilter filter) {
        return !filter.isEmpty() ? udmValueRepository.findCountByFilter(filter) : 0;
    }

    @Override
    public void assignValues(Set<UdmValueDto> udmValues) {
        String userName = RupContextUtils.getUserName();
        Set<String> udmValueIds = udmValues
            .stream()
            .filter(udmValue -> !userName.equals(udmValue.getAssignee()))
            .peek(udmValue -> {
                if (Objects.isNull(udmValue.getAssignee())) {
                    udmValueAuditService.logAction(udmValue.getId(), UdmValueActionTypeEnum.ASSIGNEE_CHANGE,
                        String.format("Assignment was changed. Value was assigned to ‘%s’", userName));
                } else {
                    udmValueAuditService.logAction(udmValue.getId(), UdmValueActionTypeEnum.ASSIGNEE_CHANGE,
                        String.format("Assignment was changed. Old assignee is '%s'. New assignee is '%s'",
                            udmValue.getAssignee(), userName));
                }
            })
            .map(BaseEntity::getId)
            .collect(Collectors.toSet());
        if (!udmValueIds.isEmpty()) {
            udmValueRepository.updateAssignee(udmValueIds, userName, userName);
        }
    }

    @Override
    public void unassignValues(Set<UdmValueDto> udmValues) {
        String userName = RupContextUtils.getUserName();
        Set<String> udmValueIds = udmValues
            .stream()
            .peek(udmValue -> udmValueAuditService.logAction(udmValue.getId(), UdmValueActionTypeEnum.UNASSIGN,
                String.format("Value was unassigned from '%s'", udmValue.getAssignee())))
            .map(BaseEntity::getId)
            .collect(Collectors.toSet());
        udmValueRepository.updateAssignee(udmValueIds, null, userName);
    }

    @Override
    public List<String> getAssignees() {
        return udmValueRepository.findAssignees();
    }

    @Override
    public List<String> getLastValuePeriods() {
        return udmValueRepository.findLastValuePeriods();
    }

    @Override
    @Transactional
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public int populateValueBatch(Integer period) {
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Populate UDM Value batch. Started. Period={}, UserName={}", period, userName);
        List<UdmValue> allNotPopulatedValues = baselineRepository.findNotPopulatedValuesFromBaseline(period);
        rightsService.updateUdmValuesRights(allNotPopulatedValues, period);
        String auditReason = String.format("UDM Value batch for period '%s' was populated", period);
        Map<Long, String> wrWrkInstToValueIdMap = allNotPopulatedValues.stream()
            .filter(value -> Objects.nonNull(value.getRhAccountNumber()))
            .peek(value -> {
                value.setId(RupPersistUtils.generateUuid());
                value.setStatus(UdmValueStatusEnum.NEW);
                value.setCreateUser(userName);
                value.setUpdateUser(userName);
                udmValueRepository.insert(value);
                udmValueAuditService.logAction(value.getId(), UdmValueActionTypeEnum.CREATED, auditReason);
            })
            .collect(Collectors.toMap(UdmValue::getWrWrkInst, UdmValue::getId));
        int updatedUsagesCount = baselineRepository.populateValueId(period, userName);
        if (MapUtils.isNotEmpty(wrWrkInstToValueIdMap) && String.valueOf(period).endsWith("12")) {
            udmValueRepository.updateResearchedInPrevPeriod(period, userName);
        }
        LOGGER.info("Populate UDM Value batch. Finished. Period={}, UserName={}, PopulatedValuesCount={}, " +
            "UpdatedUsagesCount={}", period, userName, wrWrkInstToValueIdMap.size(), updatedUsagesCount);
        return wrWrkInstToValueIdMap.size();
    }

    @Override
    public boolean isAllowedForPublishing(Integer period) {
        return udmValueRepository.isAllowedForPublishing(period);
    }

    @Override
    public int getUdmRecordThreshold() {
        return udmRecordsThreshold;
    }

    @Override
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public int publishToBaseline(Integer period) {
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Publish UDM Values to baseline. Started. Period={}, UserName={}", period, userName);
        List<String> publishedIds = udmValueRepository.publishToBaseline(period, userName);
        String actionReason = String.format("UDM Value batch for period '%s' was published to baseline", period);
        publishedIds.forEach(
            valueId -> udmValueAuditService.logAction(valueId, UdmValueActionTypeEnum.PUBLISH_TO_BASELINE,
                actionReason));
        LOGGER.info("Publish UDM Values to baseline. Finished. Period={}, UserName={}, PublishedValuesCount={}",
            period, userName, LogUtils.size(publishedIds));
        return publishedIds.size();
    }

    @Override
    public boolean isAllowedForRecalculating(Integer period) {
        return udmValueRepository.isAllowedForRecalculating(period);
    }
}
