package com.copyright.rup.dist.foreign.service.impl.aacl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Scenario.AaclFields;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.common.util.ForeignLogUtils;
import com.copyright.rup.dist.foreign.domain.filter.ScenarioUsageFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IScenarioRepository;
import com.copyright.rup.dist.foreign.service.api.IScenarioAuditService;
import com.copyright.rup.dist.foreign.service.api.IScenarioUsageFilterService;
import com.copyright.rup.dist.foreign.service.api.aacl.IAaclScenarioService;
import com.copyright.rup.dist.foreign.service.api.aacl.IAaclUsageService;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implements of {@link IAaclScenarioService}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 04/08/2020
 *
 * @author Anton Azarenka
 */
@Service
public class AaclScenarioService implements IAaclScenarioService {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IAaclUsageService aaclUsageService;
    @Autowired
    private IScenarioRepository scenarioRepository;
    @Autowired
    private IScenarioAuditService scenarioAuditService;
    @Autowired
    private IScenarioUsageFilterService scenarioUsageFilterService;

    @Override
    @Transactional
    public Scenario createScenario(String scenarioName, AaclFields aaclFields, String description,
                                   UsageFilter usageFilter) {
        LOGGER.info("Insert AACL scenario. Started. Name={}, AaclFields={}, Description={}, UsageFilter={}",
            scenarioName, ForeignLogUtils.scenarioAaclFields(aaclFields), description, usageFilter);
        Scenario scenario = buildAaclScenario(scenarioName, aaclFields, description, usageFilter);
        scenarioRepository.insert(scenario);
        aaclUsageService.addUsagesToScenario(scenario, usageFilter);
        scenarioUsageFilterService.insert(scenario.getId(), new ScenarioUsageFilter(usageFilter));
        scenarioAuditService.logAction(scenario.getId(), ScenarioActionTypeEnum.ADDED_USAGES, StringUtils.EMPTY);
        aaclUsageService.updateAaclUsagesUnderMinimum(scenario.getId(), aaclFields.getTitleCutoffAmount(),
            scenario.getCreateUser());
        aaclUsageService.calculateAmounts(scenario.getId(), scenario.getCreateUser());
        aaclUsageService.populatePayees(scenario.getId());
        LOGGER.info("Insert AACL scenario. Finished. Name={}, Description={}, UsageFilter={}",
            scenarioName, description, usageFilter);
        return scenario;
    }

    @Override
    @Transactional
    public void deleteScenario(Scenario scenario) {
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Delete scenario. Started. {}, User={}", ForeignLogUtils.scenario(scenario), userName);
        String scenarioId = scenario.getId();
        aaclUsageService.deleteFromScenario(scenarioId);
        scenarioAuditService.deleteActions(scenarioId);
        scenarioUsageFilterService.removeByScenarioId(scenarioId);
        scenarioRepository.remove(scenarioId);
        LOGGER.info("Delete scenario. Finished. {}, User={}", ForeignLogUtils.scenario(scenario), userName);
    }

    @Override
    public String getScenarioNameByFundPoolId(String fundPoolId) {
        return scenarioRepository.findNameByAaclFundPoolId(fundPoolId);
    }

    private Scenario buildAaclScenario(String scenarioName, AaclFields aaclFields, String description,
                                       UsageFilter usageFilter) {
        Scenario scenario = new Scenario();
        scenario.setId(RupPersistUtils.generateUuid());
        scenario.setAaclFields(aaclFields);
        scenario.setName(scenarioName);
        scenario.setProductFamily(usageFilter.getProductFamily());
        scenario.setStatus(ScenarioStatusEnum.IN_PROGRESS);
        scenario.setDescription(description);
        String userName = RupContextUtils.getUserName();
        scenario.setCreateUser(userName);
        scenario.setUpdateUser(userName);
        return scenario;
    }
}
