package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.dist.foreign.domain.RightsholderPayeePair;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenarioController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.ConfirmActionDialogWindow;
import com.copyright.rup.vaadin.ui.LongColumnGenerator;
import com.copyright.rup.vaadin.ui.VaadinUtils;
import com.copyright.rup.vaadin.ui.Windows;
import com.copyright.rup.vaadin.ui.component.SelectableTable;
import com.copyright.rup.vaadin.widget.SearchWidget;
import com.copyright.rup.vaadin.widget.SearchWidget.ISearchController;

import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.filter.Or;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Represents window with ability to exclude RH details for Source RRO.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 8/3/2017
 *
 * @author Uladzislau_Shalamitski
 */
public class ExcludeRightsholdersWindow extends Window implements ISearchController {

    private static final String PAYEE_ACCOUNT_NUMBER = "payee.accountNumber";
    private static final String PAYEE_NAME = "payee.name";
    private static final String RH_ACCOUNT_NUMBER = "rightsholder.accountNumber";
    private static final String RH_NAME = "rightsholder.name";
    private static final String SELECTED = "selected";

    private BeanContainer<Long, RightsholderPayeePair> rightsholderContainer;
    private SelectableTable rightsholdersTable;
    private Long accountNumber;
    private IScenarioController scenarioController;
    private SearchWidget searchWidget;
    private Window parentWindow;

    /**
     * Constructor.
     *
     * @param rroAccountNumber   rro account number
     * @param scenarioController instance of {@link IScenarioController}
     * @param parent             parent window
     */
    ExcludeRightsholdersWindow(Long rroAccountNumber, IScenarioController scenarioController, Window parent) {
        this.accountNumber = rroAccountNumber;
        this.scenarioController = scenarioController;
        this.parentWindow = parent;
        setCaption(ForeignUi.getMessage("window.exclude.rh", rroAccountNumber));
        initContent();
    }

    @Override
    public void performSearch() {
        rightsholderContainer.removeAllContainerFilters();
        String searchValue = searchWidget.getSearchValue();
        if (StringUtils.isNotEmpty(searchValue)) {
            rightsholderContainer.addContainerFilter(new Or(
                new SimpleStringFilter(PAYEE_ACCOUNT_NUMBER, searchValue, true, false),
                new SimpleStringFilter(PAYEE_NAME, searchValue, true, false),
                new SimpleStringFilter(RH_ACCOUNT_NUMBER, searchValue, true, false),
                new SimpleStringFilter(RH_NAME, searchValue, true, false)));
        }
    }

    private void initContent() {
        setWidth(830, Unit.PIXELS);
        setHeight(500, Unit.PIXELS);
        searchWidget = new SearchWidget(this);
        searchWidget.setPrompt(ForeignUi.getMessage("field.prompt.scenario.search_widget.rh_payee"));
        VaadinUtils.addComponentStyle(this, "exclude-rightsholders-window");
        HorizontalLayout buttonsLayout = createButtonsLayout();
        Table rightsholderTable = initTable();
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(new MarginInfo(false, true, true, true));
        layout.setSpacing(true);
        layout.setSizeFull();
        layout.addComponents(searchWidget, rightsholderTable, buttonsLayout);
        layout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        layout.setExpandRatio(rightsholderTable, 1);
        rightsholderContainer.addAll(scenarioController.getRightsholdersPayeePairs(accountNumber));
        setContent(layout);
    }

    private Table initTable() {
        rightsholderContainer = new BeanContainer<>(RightsholderPayeePair.class);
        rightsholderContainer.addNestedContainerBean("payee");
        rightsholderContainer.addNestedContainerBean("rightsholder");
        rightsholderContainer.setBeanIdProperty(RH_ACCOUNT_NUMBER);
        rightsholdersTable = new SelectableTable(rightsholderContainer);
        rightsholdersTable.setSizeFull();
        rightsholdersTable.setVisibleColumns(
            SELECTED,
            PAYEE_ACCOUNT_NUMBER,
            PAYEE_NAME,
            RH_ACCOUNT_NUMBER,
            RH_NAME
        );
        rightsholdersTable.setColumnHeaders(
            StringUtils.EMPTY,
            ForeignUi.getMessage("table.column.payee_account_number"),
            ForeignUi.getMessage("table.column.payee_account_name"),
            ForeignUi.getMessage("table.column.rh_account_number"),
            ForeignUi.getMessage("table.column.rh_account_name")
        );
        rightsholdersTable.addCheckboxColumn(SELECTED);
        rightsholdersTable.addGeneratedColumn(PAYEE_ACCOUNT_NUMBER, new LongColumnGenerator());
        rightsholdersTable.addGeneratedColumn(RH_ACCOUNT_NUMBER, new LongColumnGenerator());
        rightsholdersTable.setImmediate(true);
        return rightsholdersTable;
    }

    private HorizontalLayout createButtonsLayout() {
        Button confirmButton = Buttons.createButton(ForeignUi.getMessage("button.confirm"));
        confirmButton.addClickListener(event -> {
            List<Long> selectedIds = rightsholdersTable.getSelectedItemsIds(Long.class);
            if (CollectionUtils.isNotEmpty(selectedIds)) {
                Windows.showModalWindow(new ConfirmActionDialogWindow(reason -> {
                    scenarioController.deleteFromScenario(accountNumber, selectedIds, reason);
                    scenarioController.getWidget().refresh();
                    scenarioController.getWidget().refreshTable();
                    parentWindow.close();
                    this.close();
                }, ForeignUi.getMessage("window.confirm"),
                    ForeignUi.getMessage("message.confirm.exclude.rightsholders"),
                    ForeignUi.getMessage("button.yes"), ForeignUi.getMessage("button.cancel"),
                    new StringLengthValidator(ForeignUi.getMessage("field.error.length", 1024), 0, 1024, false)));
            } else {
                Windows.showNotificationWindow(ForeignUi.getMessage("message.exclude.empty"));
            }
        });
        Button clearButton = Buttons.createButton(ForeignUi.getMessage("button.clear"));
        clearButton.addClickListener(event -> rightsholdersTable.clearSelection());
        HorizontalLayout layout = new HorizontalLayout(confirmButton, clearButton, Buttons.createCloseButton(this));
        layout.setSpacing(true);
        return layout;
    }
}
