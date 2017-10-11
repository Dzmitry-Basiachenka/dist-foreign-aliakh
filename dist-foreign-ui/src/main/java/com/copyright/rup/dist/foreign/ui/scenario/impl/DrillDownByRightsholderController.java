package com.copyright.rup.dist.foreign.ui.scenario.impl;

import static java.util.Objects.requireNonNull;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.IDrillDownByRightsholderController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IDrillDownByRightsholderWidget;
import com.copyright.rup.vaadin.ui.Windows;
import com.copyright.rup.vaadin.widget.api.CommonController;

import com.vaadin.ui.Window;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Controller for {@link DrillDownByRightsholderWidget}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 10/11/17
 *
 * @author Ihar Suvorau
 */
@Component()
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DrillDownByRightsholderController extends CommonController<IDrillDownByRightsholderWidget> implements
    IDrillDownByRightsholderController {

    @Override
    public int getSize() {
        //TODO {isuvorau} call service logic after implementing
        return 1;
    }

    @Override
    public List<UsageDto> loadBeans(int startIndex, int count, Object[] sortPropertyIds, boolean... sortStates) {
        //TODO {isuvorau} call service logic after implementing
        return Collections.singletonList(buildUsageDto());
    }

    @Override
    public IDrillDownByRightsholderWidget instantiateWidget() {
        return new DrillDownByRightsholderWidget();
    }

    @Override
    public void showWidget(Long accountNumber, String rhName, Scenario scenario) {
        requireNonNull(accountNumber);
        requireNonNull(scenario);
        Window drillDownWindow = (Window) initWidget();
        drillDownWindow.setCaption(ForeignUi.getMessage("table.foreign.rightsholder.format",
            StringUtils.defaultString(rhName), accountNumber));
        Windows.showModalWindow(drillDownWindow);
    }

    //TODO {isuvorau} will be removed after implementing service logic for selecting usage details
    private UsageDto buildUsageDto() {
        UsageDto usageDto = new UsageDto();
        usageDto.setDetailId(512512L);
        return usageDto;
    }
}
