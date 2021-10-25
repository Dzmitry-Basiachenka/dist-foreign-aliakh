package com.copyright.rup.dist.foreign.service.impl.acl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.UdmValue;
import com.copyright.rup.dist.foreign.domain.UdmValueDto;
import com.copyright.rup.dist.foreign.domain.UdmValueStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmValueFilter;
import com.copyright.rup.dist.foreign.repository.api.IUdmBaselineRepository;
import com.copyright.rup.dist.foreign.repository.api.IUdmValueRepository;
import com.copyright.rup.dist.foreign.service.api.IRightsService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmValueService;

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
    private IUdmBaselineRepository baselineRepository;
    @Autowired
    private IRightsService rightsService;
    @Value("$RUP{dist.foreign.udm.record.threshold}")
    private int udmRecordsThreshold;

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Override
    public void updateValue(UdmValueDto udmValueDto) {
        String userName = RupContextUtils.getUserName();
        LOGGER.debug("Update UDM value. Started. Value={}, UserName={}", udmValueDto, userName);
        udmValueDto.setUpdateUser(userName);
        udmValueRepository.update(udmValueDto);
        LOGGER.debug("Update UDM value. Finished. Value={}, UserName={}", udmValueDto, userName);
    }

    @Override
    public Map<String, String> getCurrencyCodesToCurrencyNamesMap() {
        return currencyCodesToCurrencyNamesMap;
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
    public void assignValues(Set<String> valueIds) {
        String userName = RupContextUtils.getUserName();
        udmValueRepository.updateAssignee(valueIds, userName, userName);
    }

    @Override
    public void unassignValues(Set<String> valueIds) {
        udmValueRepository.updateAssignee(valueIds, null, RupContextUtils.getUserName());
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
        Map<Long, String> wrWrkInstToValueIdMap = allNotPopulatedValues.stream()
            .filter(value -> Objects.nonNull(value.getRhAccountNumber()))
            .peek(value -> {
                value.setId(RupPersistUtils.generateUuid());
                value.setStatus(UdmValueStatusEnum.NEW);
                value.setCreateUser(userName);
                value.setUpdateUser(userName);
                udmValueRepository.insert(value);
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
        int publishedValuesCount = udmValueRepository.publishToBaseline(period, userName);
        LOGGER.info("Publish UDM Values to baseline. Finished. Period={}, UserName={}, PublishedValuesCount={}",
            period, userName, publishedValuesCount);
        return publishedValuesCount;
    }
}
