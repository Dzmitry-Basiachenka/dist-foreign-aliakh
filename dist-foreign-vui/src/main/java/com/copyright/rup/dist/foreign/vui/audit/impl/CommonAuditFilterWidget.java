package com.copyright.rup.dist.foreign.vui.audit.impl;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.vui.audit.api.ICommonAuditFilterController;
import com.copyright.rup.dist.foreign.vui.audit.api.ICommonAuditFilterWidget;
import com.copyright.rup.dist.foreign.vui.common.LazyRightsholderFilterWidget;
import com.copyright.rup.dist.foreign.vui.common.UsageBatchFilterWidget;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.filter.CommonFilterWindow;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.filter.CommonFilterWindow.IFilterSaveListener;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.filter.IFilterSaveAction;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

import org.apache.commons.lang3.StringUtils;

import java.util.stream.Collectors;

/**
 * Interface for audit filter widget.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 1/12/18
 *
 * @author Aliaksandr Radkevich
 */
public abstract class CommonAuditFilterWidget extends VerticalLayout implements ICommonAuditFilterWidget {

    private static final long serialVersionUID = 4076078713027073911L;

    private ICommonAuditFilterController controller;
    private AuditFilter filter;
    private AuditFilter appliedFilter;
    private Button applyButton;
    private IFilterSaveAction filterSaveAction;
    private CommonAuditAppliedFilterWidget appliedFilterWidget;

    @SuppressWarnings("unchecked")
    @Override
    public CommonAuditFilterWidget init() {
        setMinWidth("150px");
        setSpacing(false);
        setWidthFull();
        initContent();
        VaadinUtils.setPadding(this, 0, 10, 0, 10);
        VaadinUtils.addComponentStyle(this, "audit-filter-widget");
        return this;
    }

    @Override
    public void setFilterSaveAction(IFilterSaveAction action) {
        this.filterSaveAction = action;
    }

    @Override
    public void setController(ICommonAuditFilterController controller) {
        this.controller = controller;
    }

    @Override
    public void applyFilter() {
        appliedFilter = new AuditFilter(filter);
        appliedFilterWidget.refreshFilterPanel(appliedFilter);
        filterSaveAction.onFilterSaveAction();
        filterChanged();
    }

    @Override
    public AuditFilter getAppliedFilter() {
        return appliedFilter;
    }

    @Override
    public AuditFilter getFilter() {
        return filter;
    }

    /**
     * Initializes fields.
     */
    public abstract void initFields();

    /**
     * Trims values of filter.
     */
    public abstract void trimFilterValues();

    /**
     * Handles filter change event.
     */
    protected void filterChanged() {
        applyButton.setEnabled(!filter.equals(appliedFilter));
    }

    /**
     * Refreshes filter.
     */
    protected void refreshFilter() {
        filter = buildAuditFilter();
    }

    /**
     * Initializes applied filter widget.
     *
     * @return instantiated applied filter widget
     */
    protected abstract CommonAuditAppliedFilterWidget initAppliedFilterWidget();

    /**
     * @return rightsholder filter {@link LazyRightsholderFilterWidget}.
     */
    protected LazyRightsholderFilterWidget buildRightsholdersFilter() {
        var rightsholderFilterWidget =
            new LazyRightsholderFilterWidget(ForeignUi.getMessage("label.rightsholders"), controller);
        rightsholderFilterWidget.addFilterSaveListener((CommonFilterWindow.IFilterSaveListener<Rightsholder>) event -> {
            getFilter().setRhAccountNumbers(event.getSelectedItemsIds()
                .stream()
                .map(Rightsholder::getAccountNumber)
                .collect(Collectors.toSet()));
            filterChanged();
        });
        VaadinUtils.addComponentStyle(rightsholderFilterWidget, "audit-rightsholders-filter");
        return rightsholderFilterWidget;
    }

    /**
     * @return usage butches filter {@link UsageBatchFilterWidget}.
     */
    protected UsageBatchFilterWidget buildUsageBatchesFilter() {
        var usageBatchFilterWidget = new UsageBatchFilterWidget(() -> controller.getUsageBatches());
        usageBatchFilterWidget.addFilterSaveListener((IFilterSaveListener<UsageBatch>) event -> {
            getFilter().setBatchesIds(
                event.getSelectedItemsIds().stream().map(UsageBatch::getId).collect(Collectors.toSet()));
            filterChanged();
        });
        VaadinUtils.addComponentStyle(usageBatchFilterWidget, "audit-batches-filter");
        return usageBatchFilterWidget;
    }

    /**
     * @return event id field.
     */
    protected TextField buildEventIdField() {
        var cccEventIdField = buildTextField(ForeignUi.getMessage("label.event_id"));
        cccEventIdField.addValueChangeListener(event -> {
            getFilter().setCccEventId(StringUtils.trimToNull(event.getValue()));
            filterChanged();
        });
        VaadinUtils.addComponentStyle(cccEventIdField, "ccc-event-id-filter");
        return cccEventIdField;
    }

    /**
     * @return distribution name field.
     */
    protected TextField buildDistributionNameField() {
        var distributionNameField = buildTextField(ForeignUi.getMessage("label.distribution_name"));
        distributionNameField.addValueChangeListener(event -> {
            getFilter().setDistributionName(StringUtils.trimToNull(event.getValue()));
            filterChanged();
        });
        VaadinUtils.addComponentStyle(distributionNameField, "distribution-name-filter");
        return distributionNameField;
    }

    /**
     * @return filter header label.
     */
    protected Label buildFiltersHeaderLabel() {
        var label = new Label(ForeignUi.getMessage("label.filters"));
        label.addClassName("filter-label");
        return label;
    }

    private void initContent() {
        filter = buildAuditFilter();
        appliedFilter = buildAuditFilter();
        appliedFilterWidget = initAppliedFilterWidget();
        initFields();
        add(buildButtonsLayout(), buildAppliedFiltersHeaderLabel(), appliedFilterWidget);
    }

    private AuditFilter buildAuditFilter() {
        var auditFilter = new AuditFilter();
        auditFilter.setProductFamily(controller.getProductFamily());
        return auditFilter;
    }

    private HorizontalLayout buildButtonsLayout() {
        applyButton = Buttons.createButton(ForeignUi.getMessage("button.apply"));
        applyButton.addClickListener(event -> trimFilterValues());
        applyButton.setEnabled(false);
        applyButton.setWidthFull();
        var clearButton = Buttons.createButton(ForeignUi.getMessage("button.clear"));
        clearButton.addClickListener(event -> clearFilter());
        clearButton.setWidthFull();
        var layout = new HorizontalLayout(applyButton, clearButton);
        layout.setWidthFull();
        VaadinUtils.addComponentStyle(layout, "filter-buttons");
        return layout;
    }

    private Label buildAppliedFiltersHeaderLabel() {
        var label = new Label(ForeignUi.getMessage("label.applied_filters"));
        label.addClassNames("filter-label");
        return label;
    }

    private TextField buildTextField(String caption) {
        var textField = new TextField(caption);
        textField.setWidthFull();
        textField.setValueChangeMode(ValueChangeMode.LAZY);
        return textField;
    }
}
