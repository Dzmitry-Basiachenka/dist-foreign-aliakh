package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.dist.common.domain.BaseEntity;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenariosController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenariosWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.DateColumnGenerator;
import com.copyright.rup.vaadin.ui.VaadinUtils;
import com.copyright.rup.vaadin.util.CurrencyUtils;
import com.copyright.rup.vaadin.widget.api.IMediator;

import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItem;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Widget that represents 'Scenarios' tab.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/14/17
 *
 * @author Aliaksandr Radkevich
 * @author Mikalai Bezmen
 */
public class ScenariosWidget extends VerticalLayout implements IScenariosWidget {

    private IScenariosController controller;
    private Button deleteButton = Buttons.createButton(ForeignUi.getMessage("button.delete"));
    private Button viewButton = Buttons.createButton(ForeignUi.getMessage("button.view"));
    private Button submitButton = Buttons.createButton(ForeignUi.getMessage("button.submit"));
    private Button rejectButton = Buttons.createButton(ForeignUi.getMessage("button.reject"));
    private Button approveButton = Buttons.createButton(ForeignUi.getMessage("button.approve"));
    private BeanContainer<String, Scenario> container;
    private Label ownerLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private Label distributionTotalLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private Label reportedTotalLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private Label grossTotalLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private Label descriptionLabel = new Label(StringUtils.EMPTY, ContentMode.HTML);
    private Table table;
    private Panel metadataPanel;
    private VerticalLayout metadataLayout;
    private ScenariosMediator mediator;

    @Override
    public IMediator initMediator() {
        mediator = new ScenariosMediator();
        mediator.setViewButton(viewButton);
        mediator.setDeleteButton(deleteButton);
        mediator.setApproveButton(approveButton);
        mediator.setRejectButton(rejectButton);
        mediator.setSubmitButton(submitButton);
        mediator.selectedScenarioChanged(getSelectedScenario());
        return mediator;
    }

    @Override
    public void refresh() {
        container.removeAllItems();
        container.addAll(controller.getScenarios());
        selectScenario(table.firstItemId());
    }

    @Override
    public void selectScenario(Object scenarioId) {
        table.select(scenarioId);
    }

    @Override
    public Scenario getSelectedScenario() {
        String itemId = Objects.toString(table.getValue(), null);
        return null != itemId ? container.getItem(itemId).getBean() : null;
    }

    @Override
    public void refreshSelectedScenario() {
        onItemChanged(getSelectedScenario());
    }

    @Override
    @SuppressWarnings("unchecked")
    public ScenariosWidget init() {
        setSizeFull();
        initTable();
        initMetadataPanel();
        HorizontalLayout horizontalLayout = new HorizontalLayout(table, metadataPanel);
        horizontalLayout.setSizeFull();
        horizontalLayout.setExpandRatio(table, 0.7f);
        horizontalLayout.setExpandRatio(metadataPanel, 0.3f);
        horizontalLayout.setSpacing(true);
        addComponents(initButtonsLayout(), horizontalLayout);
        setExpandRatio(horizontalLayout, 1);
        return this;
    }

    @Override
    public void setController(IScenariosController controller) {
        this.controller = controller;
    }

    private HorizontalLayout initButtonsLayout() {
        HorizontalLayout layout = new HorizontalLayout();
        addButtonsListeners();
        VaadinUtils.setButtonsAutoDisabled(viewButton, deleteButton, submitButton, rejectButton, approveButton);
        layout.addComponents(viewButton, deleteButton, submitButton, rejectButton, approveButton);
        layout.setSpacing(true);
        layout.setMargin(true);
        VaadinUtils.addComponentStyle(layout, "scenarios-buttons");
        return layout;
    }

    private void addButtonsListeners() {
        deleteButton.addClickListener(event -> controller.onDeleteButtonClicked());
        viewButton.addClickListener(event -> controller.onViewButtonClicked());
        submitButton.addClickListener(event -> controller.handleAction(ScenarioActionTypeEnum.SUBMITTED));
        rejectButton.addClickListener(event -> controller.handleAction(ScenarioActionTypeEnum.REJECTED));
        approveButton.addClickListener(event -> controller.handleAction(ScenarioActionTypeEnum.APPROVED));
    }

    private void initTable() {
        container = new BeanContainer<>(Scenario.class);
        container.setBeanIdResolver(BaseEntity::getId);
        container.addAll(controller.getScenarios());
        table = new Table(null, container);
        table.setSizeFull();
        table.setVisibleColumns("name", "createDate", "status");
        table.setColumnHeaders(
            ForeignUi.getMessage("table.column.name"),
            ForeignUi.getMessage("table.column.create_date"),
            ForeignUi.getMessage("table.column.status"));
        table.setColumnWidth("createDate", 100);
        table.setColumnWidth("status", 130);
        table.setColumnExpandRatio("name", 1);
        table.addGeneratedColumn("createDate", new DateColumnGenerator());
        table.addValueChangeListener(event -> {
            BeanItem<Scenario> item = container.getItem(event.getProperty().getValue());
            onItemChanged(null != item ? item.getBean() : null);
        });
        VaadinUtils.addComponentStyle(table, "scenarios-table");
    }

    private void initMetadataPanel() {
        metadataPanel = new Panel();
        metadataPanel.setSizeFull();
        VaadinUtils.addComponentStyle(metadataPanel, "scenarios-metadata");
        metadataLayout = new VerticalLayout(ownerLabel, distributionTotalLabel, grossTotalLabel, reportedTotalLabel,
            descriptionLabel);
        metadataLayout.setSpacing(true);
        metadataLayout.setMargin(new MarginInfo(false, true, false, true));
        VaadinUtils.setMaxComponentsWidth(metadataLayout);
    }

    private void onItemChanged(Scenario scenario) {
        if (null != scenario) {
            Scenario scenarioWithAmounts = controller.getScenarioWithAmountsAndLastAction(scenario);
            ownerLabel.setValue(ForeignUi.getMessage("label.owner", scenario.getCreateUser()));
            distributionTotalLabel.setValue(ForeignUi.getMessage("label.distribution_total",
                formatAmount(scenarioWithAmounts.getNetTotal())));
            grossTotalLabel.setValue(ForeignUi.getMessage("label.gross_total",
                formatAmount(scenarioWithAmounts.getGrossTotal())));
            reportedTotalLabel.setValue(ForeignUi.getMessage("label.reported_total",
                formatAmount(scenarioWithAmounts.getReportedTotal())));
            descriptionLabel.setValue(ForeignUi.getMessage("label.description", scenario.getDescription()));
            metadataPanel.setContent(metadataLayout);
        } else {
            metadataPanel.setContent(new Label());
        }
        mediator.selectedScenarioChanged(scenario);
    }

    private String formatAmount(BigDecimal amount) {
        return CurrencyUtils.formatAsHtml(amount.setScale(2, BigDecimal.ROUND_HALF_UP));
    }
}
