package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancy;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.IReconcileRightsholdersController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenariosController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.LongColumnGenerator;
import com.copyright.rup.vaadin.ui.VaadinUtils;
import com.copyright.rup.vaadin.ui.Windows;

import com.google.common.collect.Sets;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Objects;
import java.util.Set;

/**
 * Represents component to display rightsholder discrepancies.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/25/18
 *
 * @author Ihar Suvorau
 */
public class RightsholderDiscrepanciesWindow extends Window {

    private static final String WR_WRK_INST_PROPERTY = "wrWrkInst";
    private static final String WORK_TITLE_PROPERTY = "workTitle";
    private static final String RIGHTSHOLDER_ACCOUNT_NUMBER_PROPERTY = "oldRightsholder.accountNumber";
    private static final String RIGHTSHOLDER_NAME_PROPERTY = "oldRightsholder.name";
    private static final String NEW_RIGHTSHOLDER_ACCOUNT_NUMBER_PROPERTY = "newRightsholder.accountNumber";
    private static final String NEW_RIGHTSHOLDER_NAME_PROPERTY = "newRightsholder.name";
    private BeanItemContainer<RightsholderDiscrepancy> container;
    private IReconcileRightsholdersController controller;
    private IScenariosController scenariosController;

    /**
     * Constructor.
     *
     * @param reconcileRightsholdersController instance of {@link IReconcileRightsholdersController}
     * @param scenariosController              instance of {@link IScenariosController}
     */
    public RightsholderDiscrepanciesWindow(IReconcileRightsholdersController reconcileRightsholdersController,
                                           IScenariosController scenariosController) {
        setWidth(900, Unit.PIXELS);
        setHeight(530, Unit.PIXELS);
        setContent(initContent());
        setCaption(ForeignUi.getMessage("label.reconcile_rightsholders"));
        this.controller = reconcileRightsholdersController;
        this.scenariosController = scenariosController;
        populateDiscrepancies();
        VaadinUtils.addComponentStyle(this, "rightsholder-discrepancies-window");
    }

    private void populateDiscrepancies() {
        container.removeAllItems();
        container.addAll(controller.getDiscrepancies());
        container.sort(new Object[]{RIGHTSHOLDER_ACCOUNT_NUMBER_PROPERTY, NEW_RIGHTSHOLDER_ACCOUNT_NUMBER_PROPERTY},
            new boolean[]{true, true});
    }

    private Component initContent() {
        Table discrepanciesTable = initDiscrepanciesTable();
        HorizontalLayout buttons = initButtons();
        VerticalLayout content = new VerticalLayout(discrepanciesTable, buttons);
        content.setMargin(true);
        content.setSpacing(true);
        content.setSizeFull();
        content.setExpandRatio(discrepanciesTable, 1.0f);
        content.setComponentAlignment(buttons, Alignment.BOTTOM_RIGHT);
        return content;
    }

    private HorizontalLayout initButtons() {
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        Button approveButton = Buttons.createButton(ForeignUi.getMessage("button.approve"));
        approveButton.addClickListener(event -> {
            Set<Long> prohibitedAccountNumbers = Sets.newHashSet();
            controller.getDiscrepancies().stream()
                .filter(discrepancy -> Objects.isNull(discrepancy.getNewRightsholder().getAccountNumber()))
                .forEach(discrepancy ->
                    prohibitedAccountNumbers.add(discrepancy.getOldRightsholder().getAccountNumber()));
            if (CollectionUtils.isEmpty(prohibitedAccountNumbers)) {
                Windows.showConfirmDialog(
                    ForeignUi.getMessage("message.confirm.approve", controller.getScenario().getName()), () -> {
                        controller.approveReconciliation();
                        this.close();
                        scenariosController.getWidget().refreshSelectedScenario();
                    });
            } else {
                Windows.showNotificationWindow(
                    ForeignUi.getMessage("window.prohibition_approval", prohibitedAccountNumbers));
            }
        });
        buttonsLayout.addComponents(approveButton, Buttons.createCancelButton(this));
        buttonsLayout.setSpacing(true);
        VaadinUtils.addComponentStyle(buttonsLayout, "rightsholder-discrepancies-buttons-layout");
        return buttonsLayout;
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
