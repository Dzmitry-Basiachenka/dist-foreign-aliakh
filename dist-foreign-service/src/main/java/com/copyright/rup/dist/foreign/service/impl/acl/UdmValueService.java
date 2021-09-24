package com.copyright.rup.dist.foreign.service.impl.acl;

import com.copyright.rup.dist.foreign.repository.api.IUdmValueRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmValueService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
}
