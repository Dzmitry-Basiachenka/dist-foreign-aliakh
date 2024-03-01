package com.copyright.rup.dist.foreign.vui.scenario.impl.nts;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.service.api.nts.INtsScenarioService;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.scenario.api.ExcludeUsagesEvent;
import com.copyright.rup.dist.foreign.vui.scenario.api.ICommonScenarioWidget;
import com.copyright.rup.dist.foreign.vui.scenario.api.IScenarioHistoryController;
import com.copyright.rup.dist.foreign.vui.scenario.api.nts.INtsScenarioController;
import com.copyright.rup.dist.foreign.vui.scenario.api.nts.INtsScenarioWidget;
import com.copyright.rup.dist.foreign.vui.scenario.api.nts.INtsScenariosController;
import com.copyright.rup.dist.foreign.vui.scenario.api.nts.INtsScenariosWidget;
import com.copyright.rup.dist.foreign.vui.scenario.impl.CommonScenariosController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;

import com.vaadin.flow.component.ComponentUtil;

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
 * Date: 12/11/2019
 *
 * @author Stanislau Rudak
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class NtsScenariosController extends CommonScenariosController implements INtsScenariosController {

    private static final long serialVersionUID = -876763418798742517L;
    private static final String LIST_SEPARATOR = ", ";

    @Autowired
    private IScenarioHistoryController scenarioHistoryController;
    @Autowired
    private INtsScenarioController scenarioController;
    @Autowired
    private INtsScenarioService ntsScenarioService;

    @Override
    public void sendToLm() {
        //TODO: {dbasiachenka} implement
    }

    @Override
    public String getCriteriaHtmlRepresentation() {
        var filter = getScenarioUsageFilterService().getByScenarioId(getWidget().getSelectedScenario().getId());
        var sb = new StringBuilder(128).append(ForeignUi.getMessage("label.criteria"));
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
        var scenario = getWidget().getSelectedScenario();
        Windows.showConfirmDialog(ForeignUi.getMessage("message.confirm.delete_action", scenario.getName(), "scenario"),
            () -> {
                ntsScenarioService.deleteScenario(scenario);
                getWidget().refresh();
            });
    }

    @Override
    protected INtsScenarioController getScenarioController() {
        return scenarioController;
    }

    @Override
    protected ICommonScenarioWidget initScenarioWidget() {
        var scenarioWidget = (INtsScenarioWidget) scenarioController.initWidget();
        ComponentUtil.addListener((NtsScenarioWidget) scenarioWidget, ExcludeUsagesEvent.class, event -> {
            scenarioWidget.refresh();
            scenarioWidget.refreshTable();
            getWidget().refreshSelectedScenario();
        });
        return scenarioWidget;
    }

    @Override
    protected INtsScenariosWidget instantiateWidget() {
        return new NtsScenariosWidget(this, scenarioHistoryController);
    }
}
