package com.copyright.rup.dist.foreign.service.impl.sal;

import com.copyright.rup.dist.foreign.repository.api.IScenarioRepository;
import com.copyright.rup.dist.foreign.service.api.sal.ISalScenarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implements of {@link ISalScenarioService}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 09/24/2020
 *
 * @author Aliaksandr Liakh
 */
@Service
public class SalScenarioService implements ISalScenarioService {

    @Autowired
    private IScenarioRepository scenarioRepository;

    @Override
    public String getScenarioNameByFundPoolId(String fundPoolId) {
        return scenarioRepository.findNameBySalFundPoolId(fundPoolId);
    }
}
