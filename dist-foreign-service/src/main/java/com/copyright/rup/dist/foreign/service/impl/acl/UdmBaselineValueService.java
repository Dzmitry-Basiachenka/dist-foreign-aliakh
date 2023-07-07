package com.copyright.rup.dist.foreign.service.impl.acl;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.UdmValueBaselineDto;
import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineValueFilter;
import com.copyright.rup.dist.foreign.repository.api.IUdmBaselineValueRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmBaselineValueService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import io.micrometer.core.annotation.Timed;

/**
 * Implementation of {@link IUdmBaselineValueService}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/29/2021
 *
 * @author Anton Azarenka
 */
@Service
@Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
public class UdmBaselineValueService implements IUdmBaselineValueService {

    @Autowired
    private IUdmBaselineValueRepository repository;

    @Override
    public List<Integer> getPeriods() {
        return repository.findPeriods();
    }

    @Override
    public List<UdmValueBaselineDto> getValueDtos(UdmBaselineValueFilter filter, Pageable pageable, Sort sort) {
        return !filter.isEmpty()
            ? repository.findDtosByFilter(filter, pageable, sort)
            : List.of();
    }

    @Override
    public int getBaselineValueCount(UdmBaselineValueFilter filter) {
        return !filter.isEmpty() ? repository.findCountByFilter(filter) : 0;
    }
}
