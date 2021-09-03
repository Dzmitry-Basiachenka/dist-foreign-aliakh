package com.copyright.rup.dist.foreign.service.impl.acl;

import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.repository.api.IUdmBaselineRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmBaselineService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public int removeFromBaseline(Integer period) {
        return baselineRepository.removeUmdUsagesFromBaseline(period, RupContextUtils.getUserName()).size();
    }
}
