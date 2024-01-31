package com.copyright.rup.dist.foreign.ui.report.impl;

import com.copyright.rup.dist.foreign.ui.report.api.INtsPreServiceFeeFundReportController;
import com.copyright.rup.dist.foreign.ui.report.api.INtsPreServiceFeeFundReportWidget;

import com.vaadin.ui.Window;

/**
 * Widget for exporting NTS pre-service fee fund report.
 * <p>
 * Copyright (C) 2024 copyright.com
 * <p>
 * Date: 01/31/2024
 *
 * @author Dzmitry Basiachenka
 */
public class NtsPreServiceFeeFundReportWidget extends Window implements INtsPreServiceFeeFundReportWidget {

    private static final long serialVersionUID = -6503188590957261712L;

    @Override
    @SuppressWarnings("unchecked")
    public INtsPreServiceFeeFundReportWidget init() {
        return this;
    }

    @Override
    public void setController(INtsPreServiceFeeFundReportController controller) {
        //TODO: {dbasiachenka} implement
    }
}
