package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import com.copyright.rup.dist.foreign.domain.AclFundPool;
import com.copyright.rup.dist.foreign.domain.AclGrantSet;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.AclScenarioDto;
import com.copyright.rup.dist.foreign.domain.AclUsageBatch;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.service.api.ILicenseeClassService;
import com.copyright.rup.dist.foreign.service.api.IPublicationTypeService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclFundPoolService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclGrantSetService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclUsageService;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioController;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioHistoryController;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenariosController;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenariosWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * Implementation of {@link IAclScenariosController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/22/2022
 *
 * @author Dzmitry Basiachenka
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AclScenariosController extends CommonController<IAclScenariosWidget> implements IAclScenariosController {

    @Autowired
    private IAclScenarioService aclScenarioService;
    @Autowired
    private IAclUsageBatchService usageBatchService;
    @Autowired
    private IAclGrantSetService grantSetService;
    @Autowired
    private IAclFundPoolService fundPoolService;
    @Autowired
    private IAclScenarioController aclScenarioController;
    @Autowired
    private IAclScenarioHistoryController aclScenarioHistoryController;
    @Autowired
    private IPublicationTypeService publicationTypeService;
    @Autowired
    private ILicenseeClassService licenseeClassService;
    @Autowired
    private IAclUsageService aclUsageService;

    @Override
    public List<AclScenario> getScenarios() {
        return aclScenarioService.getScenarios();
    }

    @Override
    public AclScenarioDto getAclScenarioWithAmountsAndLastAction(String scenarioId) {
        return aclScenarioService.getAclScenarioWithAmountsAndLastAction(scenarioId);
    }

    @Override
    public String getCriteriaHtmlRepresentation() {
        AclScenario scenario = getWidget().getSelectedScenario();
        StringBuilder sb = new StringBuilder(ForeignUi.getMessage("label.criteria"));
        if (Objects.nonNull(scenario)) {
            sb.append("<ul>");
            appendCriterionMessage(sb, "label.usage_batch",
                usageBatchService.getById(scenario.getUsageBatchId()).getName());
            appendCriterionMessage(sb, "label.grant_set",
                grantSetService.getById(scenario.getGrantSetId()).getName());
            appendCriterionMessage(sb, "label.fund_pool",
                fundPoolService.getById(scenario.getFundPoolId()).getName());
            sb.append("</ul>");
        }
        return sb.toString();
    }

    @Override
    public boolean aclScenarioExists(String scenarioName) {
        return aclScenarioService.aclScenarioExists(scenarioName);
    }

    @Override
    public List<AclUsageBatch> getUsageBatchesByPeriod(Integer period, boolean editableFlag) {
        return usageBatchService.getUsageBatchesByPeriod(period, editableFlag);
    }

    @Override
    public List<AclFundPool> getFundPoolsByLicenseTypeAndPeriod(String licenseType, Integer period) {
        return fundPoolService.getFundPoolsByLicenseTypeAndPeriod(licenseType, period);
    }

    @Override
    public List<AclGrantSet> getGrantSetsByLicenseTypeAndPeriod(String licenseType, Integer period,
                                                                boolean editableFlag) {
        return grantSetService.getGrantSetsByLicenseTypeAndPeriod(licenseType, period, editableFlag);
    }

    @Override
    public List<Integer> getAllPeriods() {
        return usageBatchService.getPeriods();
    }

    @Override
    public void createAclScenario(AclScenario aclScenario) {
        //TODO sets default values. this logic will be changed after implementing additional windows for creating
        // scenario window.
        aclScenario.setDetailLicenseeClasses(
            licenseeClassService.getDetailLicenseeClasses(FdaConstants.ACL_PRODUCT_FAMILY));
        aclScenario.setPublicationTypes(
            publicationTypeService.getPublicationTypes(FdaConstants.ACL_PRODUCT_FAMILY));
        aclScenario.setUsageAges(aclUsageService.getDefaultUsageAgesWeights());
        aclScenarioService.insertScenario(aclScenario);
    }

    @Override
    public IAclScenarioController getAclScenarioController() {
        return aclScenarioController;
    }

    @Override
    public boolean isValidUsageBatch(String batchId) {
        return 0 == aclUsageService.getCountWithNullPubTypeOrContentUnitPriceByBatchId(batchId);
    }

    @Override
    protected IAclScenariosWidget instantiateWidget() {
        return new AclScenariosWidget(this, aclScenarioHistoryController);
    }

    private void appendCriterionMessage(StringBuilder builder, String criterionName, Object values) {
        builder.append(String.format("<li><b><i>%s </i></b>(%s)</li>", ForeignUi.getMessage(criterionName), values));
    }
}
