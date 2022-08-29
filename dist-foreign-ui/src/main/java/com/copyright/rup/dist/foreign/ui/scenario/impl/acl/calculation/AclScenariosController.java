package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import com.copyright.rup.dist.foreign.domain.AclFundPool;
import com.copyright.rup.dist.foreign.domain.AclFundPoolDetailDto;
import com.copyright.rup.dist.foreign.domain.AclGrantSet;
import com.copyright.rup.dist.foreign.domain.AclPublicationType;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.AclScenarioDto;
import com.copyright.rup.dist.foreign.domain.AclUsageBatch;
import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.UsageAge;
import com.copyright.rup.dist.foreign.service.api.ILicenseeClassService;
import com.copyright.rup.dist.foreign.service.api.IPublicationTypeService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclFundPoolService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclGrantSetService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioUsageService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclUsageService;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioController;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioHistoryController;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenariosController;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenariosWidget;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Set;

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
    private IAclScenarioUsageService scenarioUsageService;
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
        return scenarioUsageService.getAclScenarioWithAmountsAndLastAction(scenarioId);
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
    public List<DetailLicenseeClass> getDetailLicenseeClasses() {
        return licenseeClassService.getDetailLicenseeClasses(FdaConstants.ACL_PRODUCT_FAMILY);
    }

    @Override
    public List<AggregateLicenseeClass> getAggregateLicenseeClasses() {
        return licenseeClassService.getAggregateLicenseeClasses(FdaConstants.ACL_PRODUCT_FAMILY);
    }

    @Override
    public void createAclScenario(AclScenario aclScenario) {
        aclScenarioService.insertScenario(aclScenario);
    }

    @Override
    public IAclScenarioController getAclScenarioController() {
        return aclScenarioController;
    }

    @Override
    public boolean isValidUsageBatch(String batchId, String grantSetId, Integer distributionPeriod,
                                     List<Integer> periodPriors) {
        return 0 == aclUsageService.getCountInvalidUsages(batchId, grantSetId, distributionPeriod, periodPriors);
    }

    @Override
    public List<PublicationType> getPublicationTypes() {
        return publicationTypeService.getPublicationTypes(FdaConstants.ACL_PRODUCT_FAMILY);
    }

    @Override
    public List<AclPublicationType> getAclHistoricalPublicationTypes() {
        return publicationTypeService.getAclHistoricalPublicationTypes();
    }

    @Override
    public List<UsageAge> getUsageAgeWeights() {
        return aclUsageService.getDefaultUsageAgesWeights();
    }

    @Override
    public void insertAclHistoricalPublicationType(AclPublicationType publicationType) {
        publicationTypeService.insertAclHistoricalPublicationType(publicationType);
    }

    @Override
    public Set<AclFundPoolDetailDto> getFundPoolDetailsNotToBeDistributed(String batchId, String fundPoolId,
                                                                          String grantSetId,
                                                                          List<DetailLicenseeClass> mapping) {
        return aclScenarioService.getFundPoolDetailsNotToBeDistributed(batchId, fundPoolId, grantSetId, mapping);
    }

    @Override
    public AclScenario getScenarioById(String scenarioId) {
        return aclScenarioService.getScenarioById(scenarioId);
    }

    @Override
    public List<UsageAge> getUsageAgeWeightsByScenarioId(String scenarioId) {
        return aclScenarioService.getUsageAgeWeightsByScenarioId(scenarioId);
    }

    @Override
    public List<AclPublicationType> getAclPublicationTypesByScenarioId(String scenarioId) {
        return aclScenarioService.getAclPublicationTypesByScenarioId(scenarioId);
    }

    @Override
    public List<DetailLicenseeClass> getDetailLicenseeClassesByScenarioId(String scenarioId) {
        return aclScenarioService.getDetailLicenseeClassesByScenarioId(scenarioId);
    }

    @Override
    public void onDeleteButtonClicked() {
        AclScenario aclScenario = getWidget().getSelectedScenario();
        Windows.showConfirmDialog(
            ForeignUi.getMessage("message.confirm.delete_action", aclScenario.getName(), "scenario"),
            () -> {
                aclScenarioService.deleteAclScenario(aclScenario);
                getWidget().refresh();
            });
    }

    @Override
    protected IAclScenariosWidget instantiateWidget() {
        return new AclScenariosWidget(this, aclScenarioHistoryController);
    }

    private void appendCriterionMessage(StringBuilder builder, String criterionName, Object values) {
        builder.append(String.format("<li><b><i>%s </i></b>(%s)</li>", ForeignUi.getMessage(criterionName), values));
    }
}
