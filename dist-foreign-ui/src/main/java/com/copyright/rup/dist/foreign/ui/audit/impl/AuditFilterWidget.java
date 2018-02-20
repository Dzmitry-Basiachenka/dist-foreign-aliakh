package com.copyright.rup.dist.foreign.ui.audit.impl;

import com.copyright.rup.dist.foreign.domain.AuditFilter;
import com.copyright.rup.dist.foreign.ui.audit.api.IAuditFilterController;
import com.copyright.rup.dist.foreign.ui.audit.api.IAuditFilterWidget;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.dist.foreign.ui.usage.impl.RightsholderFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.UsageBatchFilterWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.VaadinUtils;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;

/**
 * Interface for audit filter widget.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 1/12/18
 *
 * @author Aliaksandr Radkevich
 */
public class AuditFilterWidget extends VerticalLayout implements IAuditFilterWidget {

    private IAuditFilterController controller;
    private RightsholderFilterWidget rightsholderFilterWidget;
    private UsageBatchFilterWidget usageBatchFilterWidget;
    private StatusFilterWidget statusFilterWidget;
    private TextField cccEventIdField;
    private TextField distributionNameField;
    private AuditFilter filter = new AuditFilter();
    private AuditFilter appliedFilter = new AuditFilter();
    private Button applyButton;

    @SuppressWarnings("unchecked")
    @Override
    public AuditFilterWidget init() {
        HorizontalLayout buttonsLayout = initButtonsLayout();
        initRightsholdersFilter();
        initUsageBatchesFilter();
        initStatusesFilterWidget();
        initEventIdField();
        initDistributionNameField();
        addComponents(buildFiltersHeaderLabel(), rightsholderFilterWidget, usageBatchFilterWidget, statusFilterWidget,
            cccEventIdField, distributionNameField, buttonsLayout);
        setComponentAlignment(buttonsLayout, Alignment.MIDDLE_RIGHT);
        setMargin(true);
        setSpacing(true);
        VaadinUtils.addComponentStyle(this, "audit-filter-widget");
        return this;
    }

    @Override
    public void setController(IAuditFilterController controller) {
        this.controller = controller;
    }

    @Override
    public void applyFilter() {
        appliedFilter = new AuditFilter(filter);
        filterChanged();
        fireEvent(new FilterChangedEvent(this));
    }

    @Override
    public void clearFilter() {
        usageBatchFilterWidget.reset();
        rightsholderFilterWidget.reset();
        statusFilterWidget.reset();
        cccEventIdField.setValue(null);
        distributionNameField.setValue(null);
        filter = new AuditFilter();
        applyFilter();
    }

    @Override
    public AuditFilter getAppliedFilter() {
        return appliedFilter;
    }

    private void initUsageBatchesFilter() {
        usageBatchFilterWidget = new UsageBatchFilterWidget(() -> controller.getUsageBatches());
        usageBatchFilterWidget.addFilterSaveListener(event -> {
            filter.setBatchesIds(event.getSelectedItemsIds());
            filterChanged();
        });
        VaadinUtils.addComponentStyle(usageBatchFilterWidget, "audit-batches-filter");
    }

    private void initRightsholdersFilter() {
        rightsholderFilterWidget = new RightsholderFilterWidget(ForeignUi.getMessage("label.rightsholders"),
            ForeignUi.getMessage("prompt.rightsholder"), () -> controller.getRightsholders());
        rightsholderFilterWidget.addFilterSaveListener(event -> {
            filter.setRhAccountNumbers(event.getSelectedItemsIds());
            filterChanged();
        });
        VaadinUtils.addComponentStyle(rightsholderFilterWidget, "audit-rightsholders-filter");
    }

    private void initStatusesFilterWidget() {
        statusFilterWidget = new StatusFilterWidget();
        statusFilterWidget.addFilterSaveListener(event -> {
            filter.setStatuses(event.getSelectedItemsIds());
            filterChanged();
        });
        VaadinUtils.addComponentStyle(statusFilterWidget, "audit-statuses-filter");
    }

    private HorizontalLayout initButtonsLayout() {
        applyButton = Buttons.createButton(ForeignUi.getMessage("button.apply"));
        applyButton.addClickListener(event -> trimFilterValues());
        Button clearButton = Buttons.createButton(ForeignUi.getMessage("button.clear"));
        clearButton.addClickListener(event -> clearFilter());
        HorizontalLayout layout = new HorizontalLayout(applyButton, clearButton);
        layout.setSpacing(true);
        VaadinUtils.setMaxComponentsWidth(layout, applyButton, clearButton);
        VaadinUtils.addComponentStyle(layout, "filter-buttons");
        return layout;
    }

    private void filterChanged() {
        applyButton.setEnabled(!filter.equals(appliedFilter));
    }

    private Label buildFiltersHeaderLabel() {
        Label filterHeaderLabel = new Label(ForeignUi.getMessage("label.filters"));
        filterHeaderLabel.addStyleName(Cornerstone.LABEL_H2);
        return filterHeaderLabel;
    }

    private void initEventIdField() {
        cccEventIdField = createTextField(ForeignUi.getMessage("label.event_id"));
        cccEventIdField.addTextChangeListener(event -> {
            filter.setCccEventId(StringUtils.trimToNull(event.getText()));
            filterChanged();
        });
        VaadinUtils.addComponentStyle(cccEventIdField, "ccc-event-id-filter");
    }

    private void initDistributionNameField() {
        distributionNameField = createTextField(ForeignUi.getMessage("label.distribution_name"));
        distributionNameField.addTextChangeListener(event -> {
            filter.setDistributionName(StringUtils.trimToNull(event.getText()));
            filterChanged();
        });
        VaadinUtils.addComponentStyle(distributionNameField, "distribution-name-filter");
    }

    private TextField createTextField(String caption) {
        TextField textField = new TextField(caption);
        VaadinUtils.setMaxComponentsWidth(textField);
        textField.setNullRepresentation(StringUtils.EMPTY);
        return textField;
    }

    private void trimFilterValues() {
        cccEventIdField.setValue(StringUtils.trimToNull(cccEventIdField.getValue()));
        distributionNameField.setValue(StringUtils.trimToNull(distributionNameField.getValue()));
        applyFilter();
    }
}
