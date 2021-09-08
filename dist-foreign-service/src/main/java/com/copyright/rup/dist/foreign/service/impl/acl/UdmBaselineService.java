package com.copyright.rup.dist.foreign.service.impl.acl;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.UdmBaselineDto;
import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineFilter;
import com.copyright.rup.dist.foreign.repository.api.IUdmBaselineRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmBaselineService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

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
        return baselineRepository.removeUmdUsagesFromBaseline(period, RupContextUtils.getUserName()).size();
    }
}
