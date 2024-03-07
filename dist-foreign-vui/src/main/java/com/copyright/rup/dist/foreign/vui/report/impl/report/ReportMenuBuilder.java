package com.copyright.rup.dist.foreign.vui.report.impl.report;

import com.copyright.rup.dist.foreign.vui.report.api.IReportController;
import com.copyright.rup.dist.foreign.vui.report.api.IReportMenuBuilder;
import com.copyright.rup.dist.foreign.vui.report.api.IReportWidget;

import com.vaadin.flow.component.contextmenu.MenuItem;

/**
 * Class to build report menus for different product families.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/12/2019
 *
 * @author Aliaksanr Liakh
 */
public enum ReportMenuBuilder implements IReportMenuBuilder {

    /**
     * Report builder for AACL product family
     */
    AACL(false) {
        @Override
        public void addItems(IReportController controller, IReportWidget widget, MenuItem rootItem) {
            //TODO: implement on scope of corresponding story
        }
    },

    /**
     * Report builder for FAS product family
     */
    FAS(true) {
        @Override
        public void addItems(IReportController controller, IReportWidget widget, MenuItem rootItem) {
            new FasReportMenuBuilder().addItems(controller, widget, rootItem);
        }
    },

    /**
     * Report builder for FAS2 product family
     */
    FAS2(true) {
        @Override
        public void addItems(IReportController controller, IReportWidget widget, MenuItem rootItem) {
            new FasReportMenuBuilder().addItems(controller, widget, rootItem);
        }
    },

    /**
     * Report builder for NTS product family
     */
    NTS(false) {
        @Override
        public void addItems(IReportController controller, IReportWidget widget, MenuItem rootItem) {
            //TODO: implement on scope of corresponding story
        }
    };

    private final boolean visible;

    /**
     * Constructor.
     *
     * @param visible visibility of report menu tab
     */
    ReportMenuBuilder(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }
}
