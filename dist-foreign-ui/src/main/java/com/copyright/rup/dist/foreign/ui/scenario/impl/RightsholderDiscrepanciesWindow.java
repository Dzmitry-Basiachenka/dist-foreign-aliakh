package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancy;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.vaadin.ui.LongColumnGenerator;
import com.copyright.rup.vaadin.ui.VaadinUtils;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import java.util.List;

/**
 * Represents component to display rightsholder discrepancies.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/25/18
 *
 * @author Ihar Suvorau
 */
public class RightsholderDiscrepanciesWindow extends VerticalLayout {

    private static final String WR_WRK_INST_PROPERTY = "wrWrkInst";
    private static final String WORK_TITLE_PROPERTY = "workTitle";
    private static final String RIGHTSHOLDER_ACCOUNT_NUMBER_PROPERTY = "oldRightsholder.accountNumber";
    private static final String RIGHTSHOLDER_NAME_PROPERTY = "oldRightsholder.name";
    private static final String NEW_RIGHTSHOLDER_ACCOUNT_NUMBER_PROPERTY = "newRightsholder.accountNumber";
    private static final String NEW_RIGHTSHOLDER_NAME_PROPERTY = "newRightsholder.name";
    private BeanItemContainer<RightsholderDiscrepancy> container;

    /**
     * Constructor.
     *
     * @param rightsholderDiscrepancies list of {@link RightsholderDiscrepancy}ies
     */
    public RightsholderDiscrepanciesWindow(List<RightsholderDiscrepancy> rightsholderDiscrepancies) {
        setWidth(900, Unit.PIXELS);
        setHeight(500, Unit.PIXELS);
        initContent();
        populateDiscrepancies(rightsholderDiscrepancies);
        VaadinUtils.addComponentStyle(this, "rightsholder-discrepancies-window");
    }

    private void populateDiscrepancies(List<RightsholderDiscrepancy> rightsholderDiscrepancies) {
        container.removeAllItems();
        container.addAll(rightsholderDiscrepancies);
        container.sort(new Object[]{RIGHTSHOLDER_ACCOUNT_NUMBER_PROPERTY, NEW_RIGHTSHOLDER_ACCOUNT_NUMBER_PROPERTY},
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
        VaadinUtils.addComponentStyle(discrepanciesTable, "rightsholder-discrepancies-table");
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
            NEW_RIGHTSHOLDER_ACCOUNT_NUMBER_PROPERTY,
            NEW_RIGHTSHOLDER_NAME_PROPERTY,
            WR_WRK_INST_PROPERTY,
            WORK_TITLE_PROPERTY);
    }

    private void addGeneratedColumns(Table discrepanciesTable) {
        LongColumnGenerator longColumnGenerator = new LongColumnGenerator();
        discrepanciesTable.addGeneratedColumn(WR_WRK_INST_PROPERTY, longColumnGenerator);
        discrepanciesTable.addGeneratedColumn(RIGHTSHOLDER_ACCOUNT_NUMBER_PROPERTY, longColumnGenerator);
        discrepanciesTable.addGeneratedColumn(NEW_RIGHTSHOLDER_ACCOUNT_NUMBER_PROPERTY, longColumnGenerator);
    }

    private void setColumnHeaders(Table discrepanciesTable) {
        discrepanciesTable.setColumnHeaders(
            ForeignUi.getMessage("table.column.rh_account_number"),
            ForeignUi.getMessage("table.column.rh_account_name"),
            ForeignUi.getMessage("table.column.new_rh_account_number"),
            ForeignUi.getMessage("table.column.new_rh_name"),
            ForeignUi.getMessage("table.column.wr_wrk_inst"),
            ForeignUi.getMessage("table.column.work_title"));
    }
}
