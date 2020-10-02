package com.copyright.rup.dist.foreign.service.impl.sal;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Scenario.SalFields;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.ScenarioUsageFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IScenarioRepository;
import com.copyright.rup.dist.foreign.service.api.IScenarioAuditService;
import com.copyright.rup.dist.foreign.service.api.IScenarioUsageFilterService;
import com.copyright.rup.dist.foreign.service.api.sal.ISalScenarioService;
import com.copyright.rup.dist.foreign.service.api.sal.ISalUsageService;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IScenarioRepository scenarioRepository;
    @Autowired
    private ISalUsageService salUsageService;
    @Autowired
    private IScenarioAuditService scenarioAuditService;
    @Autowired
    private IScenarioUsageFilterService scenarioUsageFilterService;

    @Override
    public String getScenarioNameByFundPoolId(String fundPoolId) {
        return scenarioRepository.findNameBySalFundPoolId(fundPoolId);
    }

    @Override
    @Transactional
    public Scenario createScenario(String scenarioName, String fundPoolId, String description,
                                   UsageFilter usageFilter) {
        LOGGER.info("Insert SAL scenario. Started. Name={}, FundPoolId={}, Description={}, UsageFilter={}",
            scenarioName, fundPoolId, description, usageFilter);
        Scenario scenario = buildScenario(scenarioName, fundPoolId, description);
        scenarioRepository.insert(scenario);
        salUsageService.addUsagesToScenario(scenario, usageFilter);
        scenarioUsageFilterService.insert(scenario.getId(), new ScenarioUsageFilter(usageFilter));
        scenarioAuditService.logAction(scenario.getId(), ScenarioActionTypeEnum.ADDED_USAGES, StringUtils.EMPTY);
        salUsageService.populatePayees(scenario.getId());
        LOGGER.info("Insert SAL scenario. Finished. Name={}, Description={}, UsageFilter={}",
            scenarioName, description, usageFilter);
        return scenario;
    }

    private Scenario buildScenario(String scenarioName, String fundPoolId, String description) {
        Scenario scenario = new Scenario();
        scenario.setId(RupPersistUtils.generateUuid());
        scenario.setName(scenarioName);
        scenario.setProductFamily(FdaConstants.SAL_PRODUCT_FAMILY);
        scenario.setStatus(ScenarioStatusEnum.IN_PROGRESS);
        scenario.setDescription(description);
        String userName = RupContextUtils.getUserName();
        scenario.setCreateUser(userName);
        scenario.setUpdateUser(userName);
        SalFields salFields = new SalFields();
        salFields.setFundPoolId(fundPoolId);
        scenario.setSalFields(salFields);
        return scenario;
    }
}

