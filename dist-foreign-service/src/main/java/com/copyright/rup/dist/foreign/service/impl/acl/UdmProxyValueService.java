package com.copyright.rup.dist.foreign.service.impl.acl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.repository.api.IUdmProxyValueRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmProxyValueService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of {@link IUdmProxyValueService}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/24/2021
 *
 * @author Aliaksandr Liakh
 */
@Service
public class UdmProxyValueService implements IUdmProxyValueService {

    @Autowired
    private IUdmProxyValueRepository udmProxyValueRepository;

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Override
    public List<Integer> findPeriods() {
        return udmProxyValueRepository.findPeriods();
    }

    @Override
    @Transactional
    public int calculateProxyValues(Integer period) {
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Calculate UDM proxy values. Started. Period={}, UserName={}", period, userName);
        udmProxyValueRepository.deleteProxyValues(period);
        udmProxyValueRepository.insertProxyValues(period, userName);
        int updatedValuesCount = udmProxyValueRepository.applyProxyValues(period, userName);
        LOGGER.info("Calculate UDM proxy values. Finished. Period={}, UserName={}, UpdatedValuesCount={}",
            period, userName, updatedValuesCount);
        return updatedValuesCount;
    }
}
