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
import com.copyright.rup.dist.foreign.domain.UdmValueAuditFieldToValuesMap;
import com.copyright.rup.dist.foreign.domain.UdmValueDto;
import com.copyright.rup.dist.foreign.domain.UdmValueStatusEnum;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmValueFilter;
import com.copyright.rup.dist.foreign.repository.api.IUdmBaselineRepository;
import com.copyright.rup.dist.foreign.repository.api.IUdmValueRepository;
import com.copyright.rup.dist.foreign.service.api.IRightsService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmValueAuditService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmValueService;

import com.google.common.collect.ImmutableList;

import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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
public class UdmValueService implements IUdmValueService {

    @Value("#{$RUP{dist.foreign.udm.currencies}}")
    private Map<String, String> currencyCodesToCurrencyNamesMap;
    @Autowired
    private IUdmValueRepository udmValueRepository;
    @Autowired
    private IUdmValueAuditService udmValueAuditService;
    @Autowired
    private IUdmBaselineRepository baselineRepository;
    @Autowired
    private IRightsService rightsService;
    @Value("$RUP{dist.foreign.udm.record.threshold}")
    private int udmRecordsThreshold;

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Override
    @Transactional
    public void updateValue(UdmValueDto udmValueDto, UdmValueAuditFieldToValuesMap fieldToValueChangesMap) {
        String userName = RupContextUtils.getUserName();
        LOGGER.debug("Update UDM value. Started. Value={}, UserName={}", udmValueDto, userName);
        udmValueDto.setUpdateUser(userName);
        udmValueRepository.update(udmValueDto);
        fieldToValueChangesMap.getEditAuditReasons().forEach(reason ->
            udmValueAuditService.logAction(udmValueDto.getId(), UsageActionTypeEnum.USAGE_EDIT, reason));
        LOGGER.debug("Update UDM value. Finished. Value={}, UserName={}", udmValueDto, userName);
    }

    @Override
    public List<Currency> getAllCurrencies() {
        return ImmutableList.copyOf(currencyCodesToCurrencyNamesMap
            .entrySet()
            .stream()
            .map(entry -> new Currency(entry.getKey(), entry.getValue()))
            .collect(Collectors.toList()));
    }

    @Override
    public List<Integer> getPeriods() {
        return udmValueRepository.findPeriods();
    }

    @Override
    public List<UdmValueDto> getValueDtos(UdmValueFilter filter, Pageable pageable, Sort sort) {
        return !filter.isEmpty()
            ? udmValueRepository.findDtosByFilter(filter, pageable, sort)
            : Collections.emptyList();
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
                    udmValueAuditService.logAction(udmValue.getId(), UsageActionTypeEnum.ASSIGNEE_CHANGE,
                        String.format("Assignment was changed. Value was assigned to ‘%s’", userName));
                } else {
                    udmValueAuditService.logAction(udmValue.getId(), UsageActionTypeEnum.ASSIGNEE_CHANGE,
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
            .peek(udmValue -> udmValueAuditService.logAction(udmValue.getId(), UsageActionTypeEnum.ASSIGNEE_CHANGE,
                String.format("Assignment was changed. Old assignee is '%s'. Value is not assigned to anyone",
                    udmValue.getAssignee())))
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
                udmValueAuditService.logAction(value.getId(), UsageActionTypeEnum.CREATED, auditReason);
            })
            .collect(Collectors.toMap(UdmValue::getWrWrkInst, UdmValue::getId));
        int updatedUsagesCount = 0;
        if (MapUtils.isNotEmpty(wrWrkInstToValueIdMap)) {
            updatedUsagesCount = baselineRepository.populateValueId(period, wrWrkInstToValueIdMap, userName);
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
    public int publishToBaseline(Integer period) {
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Publish UDM Values to baseline. Started. Period={}, UserName={}", period, userName);
        List<String> publishedIds = udmValueRepository.publishToBaseline(period, userName);
        String actionReason = String.format("UDM Value batch for period '%s' was published to baseline", period);
        publishedIds.forEach(
            valueId -> udmValueAuditService.logAction(valueId, UsageActionTypeEnum.PUBLISH_TO_BASELINE, actionReason));
        LOGGER.info("Publish UDM Values to baseline. Finished. Period={}, UserName={}, PublishedValuesCount={}",
            period, userName, LogUtils.size(publishedIds));
        return publishedIds.size();
    }
}
