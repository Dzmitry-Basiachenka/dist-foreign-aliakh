package com.copyright.rup.dist.foreign.service.impl.acl;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.UdmBaselineDto;
import com.copyright.rup.dist.foreign.domain.UdmValue;
import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineFilter;
import com.copyright.rup.dist.foreign.repository.api.IUdmBaselineRepository;
import com.copyright.rup.dist.foreign.service.api.IRightsService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmBaselineService;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Implementation of {@link IUdmBaselineService}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/03/21
 *
 * @author Anton Azarenka
 */
@Service
public class UdmBaselineService implements IUdmBaselineService {

    @Autowired
    private IUdmBaselineRepository baselineRepository;
    @Autowired
    private IRightsService rightsService;

    @Override
    public List<UdmBaselineDto> getBaselineUsageDtos(UdmBaselineFilter filter, Pageable pageable, Sort sort) {
        return !filter.isEmpty()
            ? baselineRepository.findDtosByFilter(filter, pageable, sort)
            : Collections.emptyList();
    }

    @Override
    public int getBaselineUsagesCount(UdmBaselineFilter filter) {
        return !filter.isEmpty() ? baselineRepository.findCountByFilter(filter) : 0;
    }

    @Override
    public int removeFromBaseline(Integer period) {
        return baselineRepository.removeUdmUsagesFromBaseline(period, RupContextUtils.getUserName()).size();
    }

    @Override
    public List<Integer> getPeriods() {
        return baselineRepository.findPeriods();
    }

    @Override
    @Transactional
    public int populateValueBatch(Integer period) {
        List<UdmValue> allNotPopulatedValues = baselineRepository.findNotPopulatedValuesFromBaseline(period);
        rightsService.updateUdmValuesRights(allNotPopulatedValues, period);
        List<UdmValue> grantedValues = allNotPopulatedValues.stream()
            .filter(value -> Objects.nonNull(value.getRhAccountNumber()))
            .collect(Collectors.toList());
        //TODO: insert granted values into db
        return CollectionUtils.size(grantedValues);
    }
}
