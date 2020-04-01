package com.copyright.rup.dist.foreign.ui.scenario.impl.aacl;

import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.filter.ScenarioUsageFilter;
import com.copyright.rup.dist.foreign.service.api.ILicenseeClassService;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonScenarioController;
import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonScenarioWidget;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenarioHistoryController;
import com.copyright.rup.dist.foreign.ui.scenario.api.aacl.IAaclScenariosController;
import com.copyright.rup.dist.foreign.ui.scenario.api.aacl.IAaclScenariosWidget;
import com.copyright.rup.dist.foreign.ui.scenario.impl.CommonScenariosController;
import com.copyright.rup.dist.foreign.ui.usage.api.aacl.IAaclUsageController;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Implementation of {@link IAaclScenariosController}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 03/19/20
 *
 * @author Uladzislau Shalamitski
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AaclScenariosController extends CommonScenariosController implements IAaclScenariosController {

    private static final String LIST_SEPARATOR = ", ";

    @Autowired
    private IScenarioHistoryController scenarioHistoryController;
    @Autowired
    private ILicenseeClassService licenseeClassService;

    @Autowired
    private IAaclUsageController aaclUsageController;

    @Override
    public void sendToLm() {
        //TODO: implement logic for sending to LM in scope of corresponding story
    }

    @Override
    protected IAaclScenariosWidget instantiateWidget() {
        return new AaclScenariosWidget(this, scenarioHistoryController, aaclUsageController);
    }

    @Override
    protected ICommonScenarioController getScenarioController() {
        //TODO init scenario controller in scope of B-55091 story
        return null;
    }

    @Override
    protected ICommonScenarioWidget initScenarioWidget() {
        //TODO init scenario widget in scope of B-55091 story
        return null;
    }

    @Override
    public List<DetailLicenseeClass> getDetailLicenseeClassesByScenarioId(String scenarioId) {
        return licenseeClassService.getDetailLicenseeClassesByScenarioId(scenarioId);
    }

    @Override
    public String getCriteriaHtmlRepresentation() {
        ScenarioUsageFilter filter =
            getScenarioUsageFilterService().getByScenarioId(getWidget().getSelectedScenario().getId());
        StringBuilder sb = new StringBuilder(ForeignUi.getMessage("label.criteria"));
        if (Objects.nonNull(filter)) {
            sb.append("<ul>");
            if (Objects.nonNull(filter.getProductFamily())) {
                appendCriterionMessage(sb, "label.product_family", filter.getProductFamily());
            }
            if (CollectionUtils.isNotEmpty(filter.getUsageBatches())) {
                appendCriterionMessage(sb, "label.batch_in", StringUtils.join(
                    filter.getUsageBatches().stream().map(UsageBatch::getName).collect(Collectors.toList()),
                    LIST_SEPARATOR));
            }
            if (Objects.nonNull(filter.getUsageStatus())) {
                appendCriterionMessage(sb, "label.status", filter.getUsageStatus());
            }
            sb.append("</ul>");
        }
        return sb.toString();
    }
}
