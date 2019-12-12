package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancyStatusEnum;
import com.copyright.rup.dist.foreign.domain.RightsholderPayeePair;
import com.copyright.rup.dist.foreign.service.api.IRightsholderDiscrepancyService;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.ExcludeUsagesEvent;
import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonDrillDownByRightsholderController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IExcludePayeeController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IExcludePayeeWidget;
import com.copyright.rup.dist.foreign.ui.scenario.api.IFasDrillDownByRightsholderController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IFasScenarioController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IFasScenarioWidget;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.ui.Window;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementation of {@link IFasScenarioController}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/10/19
 *
 * @author Stanislau Rudak
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FasScenarioController extends CommonScenarioController<IFasScenarioWidget, IFasScenarioController>
    implements IFasScenarioController {

    @Autowired
    private IRightsholderDiscrepancyService rightsholderDiscrepancyService;
    @Autowired
    private IFasDrillDownByRightsholderController drillDownByRightsholderController;
    @Autowired
    private IExcludePayeeController excludePayeesController;

    @Override
    public boolean isScenarioEmpty() {
        return getUsageService().isScenarioEmpty(getScenario());
    }

    @Override
    public void onExcludeByRroClicked() {
        if (hasApprovedDiscrepancies()) {
            Windows.showNotificationWindow(ForeignUi.getMessage("message.info.exclude_details.reconciled_scenario"));
        } else {
            Windows.showModalWindow(new FasExcludeSourceRroWindow(this));
        }
    }

    @Override
    public void onExcludeByPayeeClicked() {
        if (hasApprovedDiscrepancies()) {
            Windows.showNotificationWindow(ForeignUi.getMessage("message.info.exclude_details.reconciled_scenario"));
        } else {
            excludePayeesController.setScenario(getScenario());
            IExcludePayeeWidget widget = excludePayeesController.initWidget();
            widget.addListener(this::fireWidgetEvent);
            Windows.showModalWindow((Window) widget);
        }
    }

    @Override
    public List<Rightsholder> getSourceRros() {
        return getScenarioService().getSourceRros(getScenario().getId());
    }

    @Override
    public void deleteFromScenario(Long rroAccountNumber, List<Long> accountNumbers, String reason) {
        getUsageService().deleteFromScenario(getScenario().getId(), rroAccountNumber, accountNumbers, reason);
    }

    @Override
    public List<RightsholderPayeePair> getRightsholdersPayeePairs(Long rroAccountNumber) {
        return getScenarioService().getRightsholdersByScenarioAndSourceRro(getScenario().getId(), rroAccountNumber);
    }

    @Override
    public void fireWidgetEvent(ExcludeUsagesEvent event) {
        getWidget().fireWidgetEvent(event);
    }

    @Override
    protected IFasScenarioWidget instantiateWidget() {
        return new FasScenarioWidget();
    }

    @Override
    protected ICommonDrillDownByRightsholderController<?, ?> getDrillDownByRightsholderController() {
        return drillDownByRightsholderController;
    }

    private boolean hasApprovedDiscrepancies() {
        return 0 < rightsholderDiscrepancyService.getCountByScenarioIdAndStatus(getScenario().getId(),
            RightsholderDiscrepancyStatusEnum.APPROVED);
    }
}
