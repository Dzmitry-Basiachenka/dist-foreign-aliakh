package com.copyright.rup.dist.foreign.ui.scenario.impl.sal;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.filter.ScenarioUsageFilter;
import com.copyright.rup.dist.foreign.service.api.IFundPoolService;
import com.copyright.rup.dist.foreign.service.api.sal.ISalScenarioService;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonScenarioController;
import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonScenarioWidget;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenarioHistoryController;
import com.copyright.rup.dist.foreign.ui.scenario.api.sal.ISalScenarioController;
import com.copyright.rup.dist.foreign.ui.scenario.api.sal.ISalScenariosController;
import com.copyright.rup.dist.foreign.ui.scenario.api.sal.ISalScenariosWidget;
import com.copyright.rup.dist.foreign.ui.scenario.impl.CommonScenariosController;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Implementation of {@link ISalScenariosController}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 09/22/2020
 *
 * @author Aliaksandr Liakh
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SalScenariosController extends CommonScenariosController implements ISalScenariosController {

    private static final String LIST_SEPARATOR = ", ";

    @Autowired
    private IScenarioHistoryController scenarioHistoryController;
    @Autowired
    private ISalScenarioController scenarioController;
    @Autowired
    private ISalScenarioService salScenarioService;
    @Autowired
    private IFundPoolService fundPoolService;

    @Override
    public void sendToLm() {
        // TODO implement sending to LM for SAL scenario
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

    @Override
    public void onDeleteButtonClicked() {
        Scenario scenario = getWidget().getSelectedScenario();
        Windows.showConfirmDialog(ForeignUi.getMessage("message.confirm.delete_action", scenario.getName(), "scenario"),
            () -> {
                salScenarioService.deleteScenario(scenario);
                getWidget().refresh();
            });
    }

    @Override
    public String getFundPoolName(String fundPoolId) {
        return fundPoolService.getFundPoolById(fundPoolId).getName();
    }

    @Override
    protected ISalScenariosWidget instantiateWidget() {
        return new SalScenariosWidget(this, scenarioHistoryController);
    }

    @Override
    protected ICommonScenarioController getScenarioController() {
        return scenarioController;
    }

    @Override
    protected ICommonScenarioWidget initScenarioWidget() {
        return scenarioController.initWidget();
    }
}
