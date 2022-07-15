package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.AclScenarioDetailDto;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclDrillDownByRightsholderController;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclDrillDownByRightsholderWidget;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.widget.api.CommonController;

import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.ui.Window;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Implementation of {@link IAclDrillDownByRightsholderController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 07/14/2022
 *
 * @author Dzmitry Basiachenka
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AclDrillDownByRightsholderController extends CommonController<IAclDrillDownByRightsholderWidget>
    implements IAclDrillDownByRightsholderController {

    @Override
    public List<AclScenarioDetailDto> loadBeans(int startIndex, int count, List<QuerySortOrder> sortOrders) {
        //TODO {dbasiachenka} implement
        return Collections.emptyList();
    }

    @Override
    public int getSize() {
        //TODO {dbasiachenka} implement
        return 0;
    }

    @Override
    public void showWidget(Long accountNumber, String rhName, AclScenario scenario) {
        Window drillDownWindow = (Window) initWidget();
        drillDownWindow.setCaption(ForeignUi.getMessage("table.foreign.rightsholder.format",
            StringUtils.defaultString(rhName), accountNumber));
        Windows.showModalWindow(drillDownWindow);
    }

    @Override
    protected IAclDrillDownByRightsholderWidget instantiateWidget() {
        return new AclDrillDownByRightsholderWidget();
    }
}
