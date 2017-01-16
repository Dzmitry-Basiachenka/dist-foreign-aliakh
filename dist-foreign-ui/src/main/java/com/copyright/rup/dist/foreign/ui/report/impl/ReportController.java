package com.copyright.rup.dist.foreign.ui.report.impl;

import com.copyright.rup.dist.foreign.ui.report.api.IReportController;
import com.copyright.rup.dist.foreign.ui.report.api.IReportWidget;
import com.copyright.rup.dist.foreign.ui.report.api.IReportWindowController;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link IReportController}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 01/16/17
 *
 * @author Nikita Levyankov
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReportController extends CommonController<IReportWidget> implements IReportController {

    @Override
    public IReportWindowController getReportWindowController() {
        return null;
    }

    @Override
    protected IReportWidget instantiateWidget() {
        return new ReportWidget();
    }
}
