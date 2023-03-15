package com.copyright.rup.dist.foreign.ui.audit.impl;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.ui.audit.api.ICommonAuditFilterController;
import com.copyright.rup.dist.foreign.ui.audit.api.ICommonAuditFilterWidget;
import com.copyright.rup.dist.foreign.ui.common.LazyRightsholderFilterWidget;
import com.copyright.rup.dist.foreign.ui.common.UsageBatchFilterWidget;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.IFilterSaveListener;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

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

    private ICommonAuditFilterController controller;
    private AuditFilter filter;
    private AuditFilter appliedFilter;
    private Button applyButton;
    private CommonAuditAppliedFilterWidget appliedFilterWidget;

    @SuppressWarnings("unchecked")
    @Override
    public CommonAuditFilterWidget init() {
        filter = buildAuditFilter();
        appliedFilter = buildAuditFilter();
        appliedFilterWidget = getAppliedFilterWidget();
        HorizontalLayout buttonsLayout = buildButtonsLayout();
        initFields();
        addComponents(buttonsLayout, buildAppliedFiltersHeaderLabel(), appliedFilterWidget);
        setComponentAlignment(buttonsLayout, Alignment.MIDDLE_RIGHT);
        setMargin(true);
        setSpacing(true);
        VaadinUtils.addComponentStyle(this, "audit-filter-widget");
        return this;
    }

    @Override
    public void setController(ICommonAuditFilterController controller) {
        this.controller = controller;
    }

    @Override
    public void applyFilter() {
        appliedFilter = new AuditFilter(filter);
        appliedFilterWidget.refreshFilterPanel(appliedFilter);
        filterChanged();
        fireEvent(new FilterChangedEvent(this));
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
     * Init fields.
     */
    public abstract void initFields();

    /**
     * trim values of filter.
     */
    public abstract void trimFilterValues();

    /**
     * Handles filter change event.
     */
    protected void filterChanged() {
        applyButton.setEnabled(!filter.equals(appliedFilter));
    }

    /**
     * refresh filter.
     */
    protected void refreshFilter() {
        filter = buildAuditFilter();
    }

    /**
     * @return instantiated applied filter widget.
     */
    protected abstract CommonAuditAppliedFilterWidget getAppliedFilterWidget();

    /**
     * @return rightsholder filter {@link LazyRightsholderFilterWidget}.
     */
    protected LazyRightsholderFilterWidget buildRightsholdersFilter() {
        LazyRightsholderFilterWidget rightsholderFilterWidget =
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
        UsageBatchFilterWidget usageBatchFilterWidget = new UsageBatchFilterWidget(() -> controller.getUsageBatches());
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
        TextField cccEventIdField = buildTextField(ForeignUi.getMessage("label.event_id"));
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
        TextField distributionNameField = buildTextField(ForeignUi.getMessage("label.distribution_name"));
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
        Label filterHeaderLabel = new Label(ForeignUi.getMessage("label.filters"));
        filterHeaderLabel.addStyleName(Cornerstone.LABEL_H2);
        return filterHeaderLabel;
    }

    private HorizontalLayout buildButtonsLayout() {
        applyButton = Buttons.createButton(ForeignUi.getMessage("button.apply"));
        applyButton.addClickListener(event -> trimFilterValues());
        applyButton.setEnabled(false);
        Button clearButton = Buttons.createButton(ForeignUi.getMessage("button.clear"));
        clearButton.addClickListener(event -> clearFilter());
        HorizontalLayout layout = new HorizontalLayout(applyButton, clearButton);
        layout.setSpacing(true);
        VaadinUtils.setMaxComponentsWidth(layout, applyButton, clearButton);
        VaadinUtils.addComponentStyle(layout, "filter-buttons");
        return layout;
    }

    private AuditFilter buildAuditFilter() {
        AuditFilter auditFilter = new AuditFilter();
        auditFilter.setProductFamily(controller.getProductFamily());
        return auditFilter;
    }

    private TextField buildTextField(String caption) {
        TextField textField = new TextField(caption);
        VaadinUtils.setMaxComponentsWidth(textField);
        return textField;
    }

    private Label buildAppliedFiltersHeaderLabel() {
        Label appliedFilterHeaderLabel = new Label(ForeignUi.getMessage("label.applied_filters"));
        appliedFilterHeaderLabel.addStyleNames(Cornerstone.LABEL_H2, "applied-filter-header");
        return appliedFilterHeaderLabel;
    }
}
