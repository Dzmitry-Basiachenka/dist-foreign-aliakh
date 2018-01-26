package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancy;
import com.copyright.rup.dist.foreign.ui.common.util.PercentColumnGenerator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.vaadin.ui.LongColumnGenerator;
import com.copyright.rup.vaadin.ui.VaadinUtils;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import java.util.List;

/**
 * Represents component to display service fee discrepancies.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/26/18
 *
 * @author Ihar Suvorau
 */
public class ServiceFeeDiscrepanciesWindow extends VerticalLayout {
    private static final String RIGHTSHOLDER_ACCOUNT_NUMBER_PROPERTY = "oldRightsholder.accountNumber";
    private static final String RIGHTSHOLDER_NAME_PROPERTY = "oldRightsholder.name";
    private static final String SERVICE_FEE_PROPERTY = "oldServiceFee";
    private static final String NEW_SERVICE_FEE_PROPERTY = "newServiceFee";

    private BeanItemContainer<RightsholderDiscrepancy> container;

    /**
     * Constructor.
     *
     * @param rightsholderDiscrepancies list of {@link RightsholderDiscrepancy}ies
     */
    public ServiceFeeDiscrepanciesWindow(List<RightsholderDiscrepancy> rightsholderDiscrepancies) {
        setWidth(900, Unit.PIXELS);
        setHeight(500, Unit.PIXELS);
        initContent();
        populateDiscrepancies(rightsholderDiscrepancies);
        VaadinUtils.addComponentStyle(this, "service-fee-discrepancies-window");
    }

    private void populateDiscrepancies(List<RightsholderDiscrepancy> rightsholderDiscrepancies) {
        container.removeAllItems();
        container.addAll(rightsholderDiscrepancies);
        container.sort(new Object[]{RIGHTSHOLDER_ACCOUNT_NUMBER_PROPERTY, SERVICE_FEE_PROPERTY},
            new boolean[]{true, true});
    }

    private void initContent() {
        Table discrepanciesTable = initDiscrepanciesTable();
        this.addComponents(discrepanciesTable);
        this.setMargin(true);
        this.setSpacing(true);
        this.setSizeFull();
        this.setExpandRatio(discrepanciesTable, 1.0f);
    }

    private Table initDiscrepanciesTable() {
        Table discrepanciesTable = new Table(null, initContainer());
        setVisibleColumns(discrepanciesTable);
        setColumnHeaders(discrepanciesTable);
        addGeneratedColumns(discrepanciesTable);
        discrepanciesTable.setSizeFull();
        VaadinUtils.addComponentStyle(discrepanciesTable, "service-fee-discrepancies-table");
        return discrepanciesTable;
    }

    private BeanItemContainer<RightsholderDiscrepancy> initContainer() {
        container = new BeanItemContainer<>(RightsholderDiscrepancy.class);
        container.addNestedContainerBean("oldRightsholder");
        container.addNestedContainerBean("newRightsholder");
        return container;
    }

    private void setVisibleColumns(Table discrepanciesTable) {
        discrepanciesTable.setVisibleColumns(
            RIGHTSHOLDER_ACCOUNT_NUMBER_PROPERTY,
            RIGHTSHOLDER_NAME_PROPERTY,
            SERVICE_FEE_PROPERTY,
            NEW_SERVICE_FEE_PROPERTY);
    }

    private void addGeneratedColumns(Table discrepanciesTable) {
        LongColumnGenerator longColumnGenerator = new LongColumnGenerator();
        discrepanciesTable.addGeneratedColumn(RIGHTSHOLDER_ACCOUNT_NUMBER_PROPERTY, longColumnGenerator);
        discrepanciesTable.addGeneratedColumn(SERVICE_FEE_PROPERTY, new PercentColumnGenerator());
        discrepanciesTable.addGeneratedColumn(NEW_SERVICE_FEE_PROPERTY, new PercentColumnGenerator());
    }

    private void setColumnHeaders(Table discrepanciesTable) {
        discrepanciesTable.setColumnHeaders(
            ForeignUi.getMessage("table.column.rh_account_number"),
            ForeignUi.getMessage("table.column.rh_account_name"),
            ForeignUi.getMessage("table.column.service_fee"),
            ForeignUi.getMessage("table.column.new_service_fee"));
    }
}
