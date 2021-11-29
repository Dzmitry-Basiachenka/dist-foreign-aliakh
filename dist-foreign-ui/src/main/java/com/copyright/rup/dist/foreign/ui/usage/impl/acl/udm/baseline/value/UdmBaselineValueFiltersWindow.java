package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.baseline.value;

import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.filter.FilterExpression;
import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineValueFilter;
import com.copyright.rup.dist.foreign.ui.common.utils.BooleanUtils;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.Binder;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * Window to apply additional filters for {@link UdmBaselineValueFilterWidget}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/25/2021
 *
 * @author Anton Azarenka
 */
public class UdmBaselineValueFiltersWindow extends Window {

    private static final List<String> Y_N_ITEMS = Arrays.asList("Y", "N");

    private final TextField wrWrkInstField = new TextField(ForeignUi.getMessage("label.wr_wrk_inst"));
    private final TextField systemTitleField = new TextField(ForeignUi.getMessage("label.system_title"));
    private final ComboBox<FilterOperatorEnum> systemTitleOperatorComboBox = buildOperatorComboBox();
    private final ComboBox<PublicationType> pubTypeComboBox = new ComboBox<>(ForeignUi.getMessage("label.pub_type"));
    private final TextField priceFromField = new TextField(ForeignUi.getMessage("label.price_from"));
    private final TextField priceToField = new TextField(ForeignUi.getMessage("label.price_to"));
    private final ComboBox<FilterOperatorEnum> priceOperatorComboBox = buildOperatorComboBox();
    private final TextField contentFromField = new TextField(ForeignUi.getMessage("label.content_from"));
    private final TextField contentToField = new TextField(ForeignUi.getMessage("label.content_to"));
    private final ComboBox<FilterOperatorEnum> contentOperatorComboBox = buildOperatorComboBox();
    private final TextField contentUnitPriceFromField =
        new TextField(ForeignUi.getMessage("label.content_unit_price_from"));
    private final TextField contentUnitPriceToField =
        new TextField(ForeignUi.getMessage("label.content_unit_price_to"));
    private final ComboBox<FilterOperatorEnum> contentUnitPriceComboBox = buildOperatorComboBox();
    private final ComboBox<String> priceFlagComboBox =
        new ComboBox<>(ForeignUi.getMessage("label.price_flag"));
    private final ComboBox<String> contentFlagComboBox =
        new ComboBox<>(ForeignUi.getMessage("label.content_flag"));
    private final TextField commentField = new TextField(ForeignUi.getMessage("label.comment"));
    private final UdmBaselineValueFilter baselineValueFilter;
    private final Binder<UdmBaselineValueFilter> filterBinder = new Binder<>();
    private final UdmBaselineValueFilter appliedBaselineValueFilter;

    /**
     * Constructor.
     *
     * @param filter instance of {@link UdmBaselineValueFilter}
     */
    public UdmBaselineValueFiltersWindow(UdmBaselineValueFilter filter) {
        this.baselineValueFilter = new UdmBaselineValueFilter(filter);
        appliedBaselineValueFilter = baselineValueFilter;
        setContent(initRootLayout());
        setCaption(ForeignUi.getMessage("window.udm_baseline_values_additional_filters"));
        setResizable(false);
        setWidth(550, Unit.PIXELS);
        setHeight(400, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "udm-baseline-values-additional-filters-window");
    }

    /**
     * @return applied UDM value filter.
     */
    public UdmBaselineValueFilter getAppliedValueFilter() {
        return appliedBaselineValueFilter;
    }

    private ComponentContainer initRootLayout() {
        HorizontalLayout buttonsLayout = initButtonsLayout();
        VerticalLayout rootLayout = new VerticalLayout();
        rootLayout.addComponents(initWrWrkInstPubTypeLayout(), initSystemTitleLayout(), initFlagsLayout(),
            initPriceInUsdLayout(), initContentLayout(), initContentUnitLayout(), initCommentLayout(),
            buttonsLayout);
        rootLayout.setMargin(new MarginInfo(true, true, true, true));
        rootLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        return rootLayout;
    }

    private HorizontalLayout initFlagsLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout(priceFlagComboBox, contentFlagComboBox);
        setComponentsFullSize(priceFlagComboBox, contentFlagComboBox, horizontalLayout);
        priceFlagComboBox.setItems(Y_N_ITEMS);
        contentFlagComboBox.setItems(Y_N_ITEMS);
        priceFlagComboBox.setValue(BooleanUtils.toYNString(baselineValueFilter.getPriceFlag()));
        contentFlagComboBox.setValue(BooleanUtils.toYNString(baselineValueFilter.getContentFlag()));
        horizontalLayout.setSpacing(true);
        VaadinUtils.addComponentStyle(priceFlagComboBox, "udm-baseline-value-price-flag-filter");
        VaadinUtils.addComponentStyle(contentFlagComboBox, "udm-baseline-value-content-flag-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initWrWrkInstPubTypeLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout(wrWrkInstField, pubTypeComboBox);
        setComponentsFullSize(wrWrkInstField, pubTypeComboBox, horizontalLayout);
        VaadinUtils.addComponentStyle(wrWrkInstField, "udm-baseline-value-wr-wrk-inst-filter");
        VaadinUtils.addComponentStyle(pubTypeComboBox, "udm-baseline-value-pub-type-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initSystemTitleLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout(systemTitleField, systemTitleOperatorComboBox);
        systemTitleOperatorComboBox.setItems(FilterOperatorEnum.EQUALS, FilterOperatorEnum.CONTAINS);
        populateOperatorFilters(systemTitleField, null, systemTitleOperatorComboBox,
            UdmBaselineValueFilter::getSystemTitleExpression);
        setComponentsFullSize(systemTitleField, systemTitleOperatorComboBox, horizontalLayout);
        VaadinUtils.addComponentStyle(systemTitleField, "udm-baseline-value-system-title-filter");
        VaadinUtils.addComponentStyle(systemTitleOperatorComboBox, "udm-baseline-value-system-title-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initPriceInUsdLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout(priceFromField, priceToField, priceOperatorComboBox);
        populateOperatorFilters(priceFromField, priceToField, priceOperatorComboBox,
            UdmBaselineValueFilter::getPriceInUsdExpression);
        priceOperatorComboBox.addValueChangeListener(event -> updateOperatorField(priceToField, event.getValue()));
        setComponentsFullSize(priceFromField, priceToField, priceOperatorComboBox, horizontalLayout);
        VaadinUtils.addComponentStyle(priceFromField, "udm-baseline-value-price-from-filter");
        VaadinUtils.addComponentStyle(priceToField, "udm-baseline-value-price-to-filter");
        VaadinUtils.addComponentStyle(priceOperatorComboBox, "udm-baseline-value-price-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initContentLayout() {
        HorizontalLayout horizontalLayout =
            new HorizontalLayout(contentFromField, contentToField, contentOperatorComboBox);
        populateOperatorFilters(contentFromField, contentToField, contentOperatorComboBox,
            UdmBaselineValueFilter::getContentExpression);
        contentOperatorComboBox.addValueChangeListener(event -> updateOperatorField(contentToField, event.getValue()));
        contentFromField.setSizeFull();
        horizontalLayout.setSizeFull();
        VaadinUtils.addComponentStyle(contentFromField, "udm-baseline-value-content-from-filter");
        VaadinUtils.addComponentStyle(contentToField, "udm-baseline-value-content-to-filter");
        VaadinUtils.addComponentStyle(contentOperatorComboBox, "udm-baseline-value-content-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initContentUnitLayout() {
        HorizontalLayout horizontalLayout =
            new HorizontalLayout(contentUnitPriceFromField, contentUnitPriceToField, contentUnitPriceComboBox);
        populateOperatorFilters(contentUnitPriceFromField, contentUnitPriceToField, contentOperatorComboBox,
            UdmBaselineValueFilter::getContentExpression);
        contentUnitPriceComboBox.addValueChangeListener(
            event -> updateOperatorField(contentUnitPriceToField, event.getValue()));
        setComponentsFullSize(contentUnitPriceFromField, contentUnitPriceToField, contentUnitPriceComboBox,
            horizontalLayout);
        VaadinUtils.addComponentStyle(contentUnitPriceFromField, "udm-baseline-value-content-unit-price-from-filter");
        VaadinUtils.addComponentStyle(contentUnitPriceToField, "udm-baseline-value-content-unit-price-to-filter");
        VaadinUtils.addComponentStyle(contentUnitPriceComboBox,
            "udm-baseline-value-content-unit-price-operator-filter");
        return horizontalLayout;
    }

    private TextField initCommentLayout() {
        commentField.setSizeFull();
        VaadinUtils.addComponentStyle(commentField, "udm-baseline-value-comment-filter");
        return commentField;
    }

    private HorizontalLayout initButtonsLayout() {
        Button closeButton = Buttons.createCloseButton(this);
        Button saveButton = Buttons.createButton(ForeignUi.getMessage("button.save"));
        Button clearButton = Buttons.createButton(ForeignUi.getMessage("button.clear"));
        return new HorizontalLayout(saveButton, clearButton, closeButton);
    }

    private ComboBox<FilterOperatorEnum> buildOperatorComboBox() {
        ComboBox<FilterOperatorEnum> filterOperatorComboBox =
            new ComboBox<>(ForeignUi.getMessage("label.operator"));
        filterOperatorComboBox.setEmptySelectionAllowed(false);
        filterOperatorComboBox.setSizeFull();
        filterOperatorComboBox.setItems(FilterOperatorEnum.EQUALS, FilterOperatorEnum.GREATER_THAN,
            FilterOperatorEnum.LESS_THAN, FilterOperatorEnum.BETWEEN);
        filterOperatorComboBox.setSelectedItem(FilterOperatorEnum.EQUALS);
        return filterOperatorComboBox;
    }

    private void populateOperatorFilters(TextField fromField, TextField toField, ComboBox<FilterOperatorEnum> comboBox,
                                         Function<UdmBaselineValueFilter, FilterExpression<?>> expressionFunction) {
        FilterExpression<?> filterExpression = expressionFunction.apply(baselineValueFilter);
        Object firstFieldValue = filterExpression.getFieldFirstValue();
        Object secondFieldValue = filterExpression.getFieldSecondValue();
        FilterOperatorEnum filterOperator = filterExpression.getOperator();
        if (Objects.nonNull(firstFieldValue)) {
            fromField.setValue(firstFieldValue.toString());
            toField.setValue(Objects.nonNull(secondFieldValue) ? secondFieldValue.toString() : StringUtils.EMPTY);
            comboBox.setSelectedItem(filterOperator);
        }
        if (Objects.nonNull(toField)) {
            toField.setEnabled(Objects.nonNull(secondFieldValue));
        }
    }

    private void updateOperatorField(TextField fieldToUpdate, FilterOperatorEnum value) {
        if (FilterOperatorEnum.BETWEEN == value) {
            fieldToUpdate.setEnabled(true);
        } else {
            fieldToUpdate.clear();
            fieldToUpdate.setEnabled(false);
        }
        filterBinder.validate();
    }

    private void setComponentsFullSize(Component... components) {
        Arrays.stream(components).forEach(Sizeable::setSizeFull);
    }
}
