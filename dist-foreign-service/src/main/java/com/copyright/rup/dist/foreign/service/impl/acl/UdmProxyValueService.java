package com.copyright.rup.dist.foreign.service.impl.acl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.UdmProxyValueDto;
import com.copyright.rup.dist.foreign.domain.UdmValueActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmProxyValueFilter;
import com.copyright.rup.dist.foreign.repository.api.IUdmProxyValueRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmProxyValueService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmValueAuditService;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import io.micrometer.core.annotation.Timed;

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

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IUdmProxyValueRepository udmProxyValueRepository;
    @Autowired
    private IUdmValueAuditService udmValueAuditService;

    @Override
    public List<Integer> findPeriods() {
        return udmProxyValueRepository.findPeriods();
    }

    @Override
    @Transactional
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public int calculateProxyValues(Integer period) {
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Calculate UDM proxy values. Started. Period={}, UserName={}", period, userName);
        udmProxyValueRepository.deleteProxyValues(period);
        udmProxyValueRepository.clearProxyValues(period, userName);
        udmProxyValueRepository.insertProxyValues(period, userName);
        List<String> updatedValuesIds = udmProxyValueRepository.applyProxyValues(period, userName);
        String actionReason =
            String.format("Proxy value was applied based on proxy calculation for '%s' period", period);
        updatedValuesIds.forEach(
            id -> udmValueAuditService.logAction(id, UdmValueActionTypeEnum.PROXY_CALCULATION, actionReason));
        LOGGER.info("Calculate UDM proxy values. Finished. Period={}, UserName={}, UpdatedValuesCount={}",
            period, userName, LogUtils.size(updatedValuesIds));
        return updatedValuesIds.size();
    }

    @Override
    public List<UdmProxyValueDto> getDtosByFilter(UdmProxyValueFilter filter) {
        return !filter.isEmpty() ? udmProxyValueRepository.findDtosByFilter(filter) : List.of();
    }
}
