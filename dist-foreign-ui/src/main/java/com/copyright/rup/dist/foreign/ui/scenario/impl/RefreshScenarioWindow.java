package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.dist.common.domain.BaseEntity;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.ui.common.util.FiscalYearColumnGenerator;
import com.copyright.rup.dist.foreign.ui.common.util.IntegerColumnGenerator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenariosController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.LocalDateColumnGenerator;
import com.copyright.rup.vaadin.ui.LongColumnGenerator;
import com.copyright.rup.vaadin.ui.MoneyColumnGenerator;
import com.copyright.rup.vaadin.ui.VaadinUtils;

import com.vaadin.data.util.BeanContainer;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import java.util.List;

/**
 * Widget displays information about usages that will be added to scenario after refreshing.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 03/12/18
 *
 * @author Ihar Suvorau
 */
public class RefreshScenarioWindow extends Window {

    private final IScenariosController controller;
    private BeanContainer<String, UsageDto> container;
    private Table usagesTable;
    private HorizontalLayout buttonsLayout;

    /**
     * Constructor.
     *
     * @param usageDtos  usageDtos for adding to scenario
     * @param controller instance of {@link IScenariosController}
     */
    public RefreshScenarioWindow(List<UsageDto> usageDtos, IScenariosController controller) {
        this.controller = controller;
        setCaption(ForeignUi.getMessage("label.refresh_scenario"));
        setWidth(800, Unit.PIXELS);
        setHeight(400, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "refresh-scenario-window");
        this.setContent(initContent());
        container.addAll(usageDtos);
    }

    private VerticalLayout initContent() {
        Label label = new Label(ForeignUi.getMessage("label.content.note"), ContentMode.HTML);
        label.setStyleName("label-note");
        initButtonsLayout();
        initTable();
        VerticalLayout content = new VerticalLayout(label, usagesTable, buttonsLayout);
        content.setExpandRatio(usagesTable, 1);
        content.setSizeFull();
        content.setSpacing(true);
        content.setMargin(true);
        content.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        return content;
    }

    private void initButtonsLayout() {
        buttonsLayout = new HorizontalLayout();
        Button continueButton = Buttons.createOkButton();
        continueButton.addClickListener(e -> {
            controller.refreshScenario();
            close();
        });
        buttonsLayout.addComponents(continueButton, Buttons.createCancelButton(this));
        buttonsLayout.setSpacing(true);
    }

    private void initTable() {
        container = new BeanContainer<>(UsageDto.class);
        container.setBeanIdResolver(BaseEntity::getId);
        usagesTable = new Table(null, container);
        usagesTable.setSizeFull();
        setVisibleColumns();
        setColumnHeaders();
        setColumnGenerators();
        usagesTable.setColumnCollapsingAllowed(true);
        usagesTable.setColumnCollapsible("detailId", false);
        VaadinUtils.addComponentStyle(this, "refresh-usages-table");
    }

    private void setColumnHeaders() {
        usagesTable.setColumnHeaders(
            ForeignUi.getMessage("table.column.detail_id"),
            ForeignUi.getMessage("table.column.usage_status"),
            ForeignUi.getMessage("table.column.product_family"),
            ForeignUi.getMessage("table.column.batch_name"),
            ForeignUi.getMessage("table.column.fiscal_year"),
            ForeignUi.getMessage("table.column.rro_account_number"),
            ForeignUi.getMessage("table.column.rro_account_name"),
            ForeignUi.getMessage("table.column.payment_date"),
            ForeignUi.getMessage("table.column.work_title"),
            ForeignUi.getMessage("table.column.article"),
            ForeignUi.getMessage("table.column.standard_number"),
            ForeignUi.getMessage("table.column.wr_wrk_inst"),
            ForeignUi.getMessage("table.column.rh_account_number"),
            ForeignUi.getMessage("table.column.rh_account_name"),
            ForeignUi.getMessage("table.column.publisher"),
            ForeignUi.getMessage("table.column.publication_date"),
            ForeignUi.getMessage("table.column.number_of_copies"),
            ForeignUi.getMessage("table.column.reported_value"),
            ForeignUi.getMessage("table.column.gross_amount"),
            ForeignUi.getMessage("table.column.batch_gross_amount"),
            ForeignUi.getMessage("table.column.market"),
            ForeignUi.getMessage("table.column.market_period_from"),
            ForeignUi.getMessage("table.column.market_period_to"),
            ForeignUi.getMessage("table.column.author"));
    }

    private void setVisibleColumns() {
        usagesTable.setVisibleColumns(
            "detailId",
            "status",
            "productFamily",
            "batchName",
            "fiscalYear",
            "rroAccountNumber",
            "rroName",
            "paymentDate",
            "workTitle",
            "article",
            "standardNumber",
            "wrWrkInst",
            "rhAccountNumber",
            "rhName",
            "publisher",
            "publicationDate",
            "numberOfCopies",
            "reportedValue",
            "grossAmount",
            "batchGrossAmount",
            "market",
            "marketPeriodFrom",
            "marketPeriodTo",
            "author");
    }

    private void setColumnGenerators() {
        IntegerColumnGenerator integerColumnGenerator = new IntegerColumnGenerator();
        usagesTable.addGeneratedColumn("numberOfCopies", integerColumnGenerator);
        usagesTable.addGeneratedColumn("marketPeriodFrom", integerColumnGenerator);
        usagesTable.addGeneratedColumn("marketPeriodTo", integerColumnGenerator);
        usagesTable.addGeneratedColumn("fiscalYear", new FiscalYearColumnGenerator());
        LongColumnGenerator longColumnGenerator = new LongColumnGenerator();
        usagesTable.addGeneratedColumn("detailId", longColumnGenerator);
        usagesTable.addGeneratedColumn("wrWrkInst", longColumnGenerator);
        usagesTable.addGeneratedColumn("rhAccountNumber", longColumnGenerator);
        usagesTable.addGeneratedColumn("rroAccountNumber", longColumnGenerator);
        LocalDateColumnGenerator localDateColumnGenerator = new LocalDateColumnGenerator();
        usagesTable.addGeneratedColumn("publicationDate", localDateColumnGenerator);
        usagesTable.addGeneratedColumn("paymentDate", localDateColumnGenerator);
        MoneyColumnGenerator moneyColumnGenerator = new MoneyColumnGenerator();
        usagesTable.addGeneratedColumn("reportedValue", moneyColumnGenerator);
        usagesTable.addGeneratedColumn("grossAmount", moneyColumnGenerator);
        usagesTable.addGeneratedColumn("batchGrossAmount", moneyColumnGenerator);
    }
}
