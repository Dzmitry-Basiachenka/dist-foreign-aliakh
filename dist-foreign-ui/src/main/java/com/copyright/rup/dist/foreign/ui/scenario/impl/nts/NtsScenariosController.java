package com.copyright.rup.dist.foreign.ui.scenario.impl.nts;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.filter.ScenarioUsageFilter;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonScenarioWidget;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenarioHistoryController;
import com.copyright.rup.dist.foreign.ui.scenario.api.nts.INtsScenarioController;
import com.copyright.rup.dist.foreign.ui.scenario.api.nts.INtsScenariosController;
import com.copyright.rup.dist.foreign.ui.scenario.api.nts.INtsScenariosWidget;
import com.copyright.rup.dist.foreign.ui.scenario.impl.CommonScenariosController;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.ui.Window;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Implementation of {@link INtsScenariosController}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/11/19
 *
 * @author Stanislau Rudak
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class NtsScenariosController extends CommonScenariosController implements INtsScenariosController {

    private static final String LIST_SEPARATOR = ", ";

    @Autowired
    private IScenarioHistoryController scenarioHistoryController;
    @Autowired
    private INtsScenarioController scenarioController;

    @Override
    public void onViewButtonClicked() {
        scenarioController.setScenario(getWidget().getSelectedScenario());
        Window scenarioWindow = (Window) scenarioController.initWidget();
        Windows.showModalWindow(scenarioWindow);
        scenarioWindow.setPositionY(30);
    }

    @Override
    public void sendToLm() {
        Scenario scenario = getWidget().getSelectedScenario();
        Windows.showConfirmDialog(ForeignUi.getMessage("window.send_scenario", scenario.getName()),
            () -> {
                try {
                    getScenarioService().sendNtsToLm(scenario);
                } catch (RuntimeException e) {
                    Windows.showNotificationWindow(e.getMessage());
                }
                getWidget().refresh();
            });
    }

    @Override
    protected INtsScenariosWidget instantiateWidget() {
        return new NtsScenariosWidget(scenarioHistoryController);
    }

    @Override
    protected INtsScenarioController getScenarioController() {
        return scenarioController;
    }

    @Override
    protected ICommonScenarioWidget initScenarioWidget() {
        return scenarioController.initWidget();
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
            if (CollectionUtils.isNotEmpty(filter.getRhAccountNumbers())) {
                appendCriterionMessage(sb, "label.rro_in", StringUtils.join(generateRightsholderList(
                    getRightsholderService().updateAndGetRightsholders(filter.getRhAccountNumbers())), LIST_SEPARATOR));
            }
            if (Objects.nonNull(filter.getPaymentDate())) {
                appendCriterionMessage(sb, "label.payment_date_to",
                    CommonDateUtils.format(filter.getPaymentDate(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT));
            }
            if (Objects.nonNull(filter.getUsageStatus())) {
                appendCriterionMessage(sb, "label.status", filter.getUsageStatus());
            }
            if (Objects.nonNull(filter.getFiscalYear())) {
                appendCriterionMessage(sb, "label.fiscal_year_to", filter.getFiscalYear());
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
                getScenarioService().deleteScenario(scenario);
                getWidget().refresh();
            });
    }
}
