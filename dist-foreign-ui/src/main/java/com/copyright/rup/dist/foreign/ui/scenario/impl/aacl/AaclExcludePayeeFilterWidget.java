package com.copyright.rup.dist.foreign.ui.scenario.impl.aacl;

import com.copyright.rup.dist.common.service.impl.csv.validator.AmountValidator;
import com.copyright.rup.dist.foreign.domain.filter.ExcludePayeeFilter;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.aacl.IAaclExcludePayeeFilterController;
import com.copyright.rup.dist.foreign.ui.scenario.api.aacl.IAaclExcludePayeeFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.Binder;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * Interface for exclude AACL payees filter widget.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 11/05/2020
 *
 * @author Ihar Suvorau
 */
public class AaclExcludePayeeFilterWidget extends VerticalLayout implements IAaclExcludePayeeFilterWidget {

    private final Binder<String> binder = new Binder<>();
    private ExcludePayeeFilter filter = new ExcludePayeeFilter();
    private ExcludePayeeFilter appliedFilter = new ExcludePayeeFilter();
    private TextField minimumThreshold;
    private Button applyButton;

    @Override
    public ExcludePayeeFilter getAppliedFilter() {
        return appliedFilter;
    }

    @Override
    public void applyFilter() {
        appliedFilter = new ExcludePayeeFilter(filter);
        filterChanged();
        fireEvent(new FilterChangedEvent(this));
    }

    @Override
    public void clearFilter() {
        minimumThreshold.clear();
        filter = new ExcludePayeeFilter();
        applyFilter();
    }

    @Override
    public void setController(IAaclExcludePayeeFilterController controller) {
    }

    @Override
    @SuppressWarnings("unchecked")
    public AaclExcludePayeeFilterWidget init() {
        initMinimumThresholdFilter();
        HorizontalLayout buttonsLayout = initButtonsLayout();
        addComponents(buildFiltersHeaderLabel(), minimumThreshold, buttonsLayout);
        setComponentAlignment(buttonsLayout, Alignment.MIDDLE_RIGHT);
        setMargin(true);
        setSpacing(true);
        VaadinUtils.addComponentStyle(this, "audit-filter-widget");
        return this;
    }

    private HorizontalLayout initButtonsLayout() {
        applyButton = Buttons.createButton(ForeignUi.getMessage("button.apply"));
        applyButton.addClickListener(event -> applyFilter());
        applyButton.setEnabled(false);
        Button clearButton = Buttons.createButton(ForeignUi.getMessage("button.clear"));
        clearButton.addClickListener(event -> clearFilter());
        HorizontalLayout layout = new HorizontalLayout(applyButton, clearButton);
        layout.setSpacing(true);
        VaadinUtils.setMaxComponentsWidth(layout, applyButton, clearButton);
        VaadinUtils.addComponentStyle(layout, "filter-buttons");
        return layout;
    }

    private void initMinimumThresholdFilter() {
        minimumThreshold = new TextField(ForeignUi.getMessage("label.minimum_threshold"));
        binder.forField(minimumThreshold)
            .withValidator(value -> new AmountValidator().isValid(StringUtils.trimToEmpty(value)),
                ForeignUi.getMessage("field.error.positive_number_and_length", 10))
            .bind(source -> source, (beanValue, fieldValue) -> beanValue = fieldValue);
        minimumThreshold.addValueChangeListener(event -> {
            if (!binder.validate().hasErrors()) {
                filter.setNetAmountMinThreshold(StringUtils.isNotBlank(event.getValue())
                    ? new BigDecimal(StringUtils.trimToEmpty(event.getValue())) : null);
            }
            filterChanged();
        });
        VaadinUtils.setMaxComponentsWidth(minimumThreshold);
        VaadinUtils.addComponentStyle(minimumThreshold, "minimum-threshold-filter");
    }

    private void filterChanged() {
        applyButton.setEnabled(!binder.validate().hasErrors() && !filter.equals(appliedFilter));
    }

    private Label buildFiltersHeaderLabel() {
        Label filterHeaderLabel = new Label(ForeignUi.getMessage("label.filters"));
        filterHeaderLabel.addStyleName(Cornerstone.LABEL_H2);
        return filterHeaderLabel;
    }
}