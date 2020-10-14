package com.copyright.rup.dist.foreign.ui.report.impl.report;

import com.copyright.rup.dist.foreign.ui.report.api.IReportController;
import com.copyright.rup.dist.foreign.ui.report.api.IReportMenuBuilder;
import com.copyright.rup.dist.foreign.ui.report.api.IReportWidget;

import com.vaadin.ui.MenuBar.MenuItem;

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
    NTS(true) {
        @Override
        public void addItems(IReportController controller, IReportWidget widget, MenuItem rootItem) {
            new NtsReportMenuBuilder().addItems(controller, widget, rootItem);
        }
    },

    /**
     * Report builder for AACL product family
     */
    AACL(true) {
        @Override
        public void addItems(IReportController controller, IReportWidget widget, MenuItem rootItem) {
            new AaclReportMenuBuilder().addItems(controller, widget, rootItem);
        }
    },

    /**
     * Report builder for SAL product family
     */
    SAL(true) {
        @Override
        public void addItems(IReportController controller, IReportWidget widget, MenuItem rootItem) {
            new SalReportMenuBuilder().addItems(controller, widget, rootItem);
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
