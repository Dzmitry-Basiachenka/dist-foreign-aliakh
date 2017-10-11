package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenarioController;
import com.copyright.rup.vaadin.ui.LongColumnGenerator;
import com.copyright.rup.vaadin.ui.MoneyColumnGenerator;
import com.copyright.rup.vaadin.ui.VaadinUtils;
import com.copyright.rup.vaadin.ui.component.lazytable.LazyTable;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.BaseTheme;

import java.math.BigDecimal;

/**
 * Represents table to view rightsholder, payee and associated amounts grouped by rightsholder.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 04/05/17
 *
 * @author Ihar Suvorau
 */
public class RightsholderTotalsHolderTable extends LazyTable<RightsholderTotalsHolderBeanQuery,
    RightsholderTotalsHolder> {

    private static final String RIGHTSHOLDER_NAME_PROPERTY = "rightsholderName";
    private static final String RIGHTSHOLDER_ACCOUNT_NUMBER_PROPERTY = "rightsholderAccountNumber";
    private static final String PAYEE_NAME_PROPERTY = "payeeName";
    private static final String PAYEE_ACCOUNT_NUMBER_PROPERTY = "payeeAccountNumber";
    private static final String GROSS_TOTAL_PROPERTY = "grossTotal";
    private static final String SERVICE_FEE_TOTAL_PROPERTY = "serviceFeeTotal";
    private static final String NET_TOTAL_PROPERTY = "netTotal";
    private static final String SERVICE_FEE_PROPERTY = "serviceFee";
    private IScenarioController controller;

    /**
     * Constructor.
     *
     * @param controller {@link IScenarioController} instance
     * @param queryClass query class
     */
    public RightsholderTotalsHolderTable(IScenarioController controller,
                                         Class<RightsholderTotalsHolderBeanQuery> queryClass) {
        super(controller, queryClass);
        this.controller = controller;
        initTable();
    }

    @Override
    protected Object getIdProperty() {
        return RIGHTSHOLDER_ACCOUNT_NUMBER_PROPERTY;
    }

    private void initTable() {
        setSizeFull();
        addProperties();
        addColumnsGenerators();
        setColumnsSizes();
        setVisibleColumns(
            RIGHTSHOLDER_ACCOUNT_NUMBER_PROPERTY,
            RIGHTSHOLDER_NAME_PROPERTY,
            PAYEE_ACCOUNT_NUMBER_PROPERTY,
            PAYEE_NAME_PROPERTY,
            GROSS_TOTAL_PROPERTY,
            SERVICE_FEE_TOTAL_PROPERTY,
            NET_TOTAL_PROPERTY,
            SERVICE_FEE_PROPERTY);
        setColumnHeaders(
            ForeignUi.getMessage("table.column.rh_account_number"),
            ForeignUi.getMessage("table.column.rh_account_name"),
            ForeignUi.getMessage("table.column.payee_account_number"),
            ForeignUi.getMessage("table.column.payee_account_name"),
            ForeignUi.getMessage("table.column.amount_in_usd"),
            ForeignUi.getMessage("table.column.service_fee_amount"),
            ForeignUi.getMessage("table.column.net_amount"),
            ForeignUi.getMessage("table.column.service_fee"));
        addStyleName("table-ext-footer");
        setColumnCollapsingAllowed(true);
        setColumnCollapsible(RIGHTSHOLDER_ACCOUNT_NUMBER_PROPERTY, false);
        setFooterVisible(true);
        setColumnFooter(RIGHTSHOLDER_ACCOUNT_NUMBER_PROPERTY, "Totals");
        VaadinUtils.addComponentStyle(this, "rightsholders-totals-table");
    }

    private void addProperties() {
        addProperty(RIGHTSHOLDER_ACCOUNT_NUMBER_PROPERTY, Long.class, true);
        addProperty(RIGHTSHOLDER_NAME_PROPERTY, String.class, true);
        addProperty(PAYEE_ACCOUNT_NUMBER_PROPERTY, Long.class, false);
        addProperty(PAYEE_NAME_PROPERTY, String.class, false);
        addProperty(GROSS_TOTAL_PROPERTY, BigDecimal.class, true);
        addProperty(SERVICE_FEE_TOTAL_PROPERTY, BigDecimal.class, true);
        addProperty(NET_TOTAL_PROPERTY, BigDecimal.class, true);
        addProperty(SERVICE_FEE_PROPERTY, BigDecimal.class, false);
    }

    private void setColumnsSizes() {
        setColumnExpandRatio(RIGHTSHOLDER_ACCOUNT_NUMBER_PROPERTY, 0.1f);
        setColumnExpandRatio(PAYEE_ACCOUNT_NUMBER_PROPERTY, 0.1f);
        setColumnExpandRatio(GROSS_TOTAL_PROPERTY, 0.1f);
        setColumnExpandRatio(SERVICE_FEE_TOTAL_PROPERTY, 0.1f);
        setColumnExpandRatio(NET_TOTAL_PROPERTY, 0.1f);
        setColumnExpandRatio(SERVICE_FEE_PROPERTY, 0.1f);
        setColumnExpandRatio(RIGHTSHOLDER_NAME_PROPERTY, 0.2f);
        setColumnExpandRatio(PAYEE_NAME_PROPERTY, 0.2f);
    }

    private void addColumnsGenerators() {
        LongColumnGenerator longColumnGenerator = new LongColumnGenerator();
        addGeneratedColumn(RIGHTSHOLDER_ACCOUNT_NUMBER_PROPERTY,
            new RightsholderAccountNumberColumnGenerator(controller));
        addGeneratedColumn(PAYEE_ACCOUNT_NUMBER_PROPERTY, longColumnGenerator);
        MoneyColumnGenerator moneyColumnGenerator = new MoneyColumnGenerator();
        addGeneratedColumn(GROSS_TOTAL_PROPERTY, moneyColumnGenerator);
        addGeneratedColumn(SERVICE_FEE_TOTAL_PROPERTY, moneyColumnGenerator);
        addGeneratedColumn(NET_TOTAL_PROPERTY, moneyColumnGenerator);
    }

    /**
     * Button column generator for rightsholder account number column.
     */
    static class RightsholderAccountNumberColumnGenerator implements Table.ColumnGenerator {

        private IScenarioController controller;

        /**
         * Constructor.
         *
         * @param controller controller instance
         */
        RightsholderAccountNumberColumnGenerator(IScenarioController controller) {
            this.controller = controller;
        }

        @Override
        public Object generateCell(Table source, Object itemId, Object columnId) {
            Long rightsholderAccountNumber =
                VaadinUtils.getContainerPropertyValue(source, itemId, columnId, Long.class);
            Button buttonLink = createButtonLink(String.valueOf(rightsholderAccountNumber));
            buttonLink.addClickListener(
                (ClickListener) event -> controller.onRightsholderAccountNumberClicked(rightsholderAccountNumber,
                    VaadinUtils.getContainerPropertyValue(source, itemId, RIGHTSHOLDER_NAME_PROPERTY, String.class)));
            VaadinUtils.setButtonsAutoDisabled(buttonLink);
            return buttonLink;
        }

        private Button createButtonLink(String caption) {
            Button button = new Button(caption);
            button.addStyleName(BaseTheme.BUTTON_LINK);
            return button;
        }
    }
}
