package com.copyright.rup.dist.foreign.service.impl.acl;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.UdmValueDto;
import com.copyright.rup.dist.foreign.domain.filter.UdmValueFilter;
import com.copyright.rup.dist.foreign.repository.api.IUdmValueRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmValueService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
}
