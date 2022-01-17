package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.baseline.value;

import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineValueFilter;
import com.copyright.rup.dist.foreign.ui.common.utils.BooleanUtils;
import com.copyright.rup.dist.foreign.ui.common.validator.AmountValidator;
import com.copyright.rup.dist.foreign.ui.common.validator.AmountZeroValidator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.SerializablePredicate;
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

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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

    private static final int ONE = 1;
    private static final List<String> Y_N_ITEMS = Arrays.asList("Y", "N");
    private static final String NUMBER_VALIDATION_MESSAGE = "field.error.not_numeric";
    private static final String LENGTH_VALIDATION_MESSAGE = "field.error.length";
    private static final String BETWEEN_OPERATOR_VALIDATION_MESSAGE =
        ForeignUi.getMessage("field.error.populated_for_between_operator");
    private static final String GRATER_OR_EQUAL_VALIDATION_MESSAGE = "field.error.greater_or_equal_to";
    private static final String EQUALS = "EQUALS";

    private final StringLengthValidator numberStringLengthValidator =
        new StringLengthValidator(ForeignUi.getMessage("field.error.number_length", 9), 0, 9);
    private final TextField wrWrkInstFromField = new TextField(ForeignUi.getMessage("label.wr_wrk_inst_from"));
    private final TextField wrWrkInstToField = new TextField(ForeignUi.getMessage("label.wr_wrk_inst_to"));
    private final ComboBox<FilterOperatorEnum> wrWrkInstOperatorComboBox = buildNumericOperatorComboBox();
    private final TextField systemTitleField = new TextField(ForeignUi.getMessage("label.system_title"));
    private final ComboBox<FilterOperatorEnum> systemTitleOperatorComboBox = buildTextOperatorComboBox();
    private final TextField priceFromField = new TextField(ForeignUi.getMessage("label.price_from"));
    private final TextField priceToField = new TextField(ForeignUi.getMessage("label.price_to"));
    private final ComboBox<FilterOperatorEnum> priceOperatorComboBox = buildNumericOperatorComboBox();
    private final TextField contentFromField = new TextField(ForeignUi.getMessage("label.content_from"));
    private final TextField contentToField = new TextField(ForeignUi.getMessage("label.content_to"));
    private final ComboBox<FilterOperatorEnum> contentOperatorComboBox = buildNumericOperatorComboBox();
    private final TextField contentUnitPriceFromField =
        new TextField(ForeignUi.getMessage("label.content_unit_price_from"));
    private final TextField contentUnitPriceToField =
        new TextField(ForeignUi.getMessage("label.content_unit_price_to"));
    private final ComboBox<FilterOperatorEnum> contentUnitPriceComboBox = buildNumericOperatorComboBox();
    private final ComboBox<String> priceFlagComboBox =
        new ComboBox<>(ForeignUi.getMessage("label.price_flag"));
    private final ComboBox<String> contentFlagComboBox =
        new ComboBox<>(ForeignUi.getMessage("label.content_flag"));
    private final TextField commentField = new TextField(ForeignUi.getMessage("label.comment"));
    private final ComboBox<FilterOperatorEnum> commentOperatorComboBox = buildTextOperatorComboBox();
    private final Binder<UdmBaselineValueFilter> filterBinder = new Binder<>();
    private final UdmBaselineValueFilter baselineValueFilter;

    /**
     * Constructor.
     *
     * @param filter instance of {@link UdmBaselineValueFilter}
     */
    public UdmBaselineValueFiltersWindow(UdmBaselineValueFilter filter) {
        this.baselineValueFilter = filter;
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
    public UdmBaselineValueFilter getBaselineValueFilter() {
        return baselineValueFilter;
    }

    private ComponentContainer initRootLayout() {
        HorizontalLayout buttonsLayout = initButtonsLayout();
        VerticalLayout rootLayout = new VerticalLayout();
        rootLayout.addComponents(initWrWrkInstLayout(), initSystemTitleLayout(), initFlagsLayout(), initPriceLayout(),
            initContentLayout(), initContentUnitPriceLayout(), initCommentLayout(), buttonsLayout);
        rootLayout.setMargin(new MarginInfo(true, true, true, true));
        rootLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        filterBinder.readBean(baselineValueFilter);
        filterBinder.validate();
        return rootLayout;
    }

    private HorizontalLayout initFlagsLayout() {
        filterBinder.forField(priceFlagComboBox)
            .bind(filter -> BooleanUtils.toYNString(filter.getPriceFlag()),
                (filter, value) ->
                    filter.setPriceFlag(StringUtils.isNotEmpty(value) ? convertStringToBoolean(value) : null));
        filterBinder.forField(contentFlagComboBox)
            .bind(filter -> BooleanUtils.toYNString(filter.getContentFlag()),
                (filter, value) ->
                    filter.setContentFlag(StringUtils.isNotEmpty(value) ? convertStringToBoolean(value) : null));
        priceFlagComboBox.setItems(Y_N_ITEMS);
        contentFlagComboBox.setItems(Y_N_ITEMS);
        HorizontalLayout horizontalLayout = new HorizontalLayout(priceFlagComboBox, contentFlagComboBox);
        setComponentsFullSize(priceFlagComboBox, contentFlagComboBox, horizontalLayout);
        horizontalLayout.setSpacing(true);
        VaadinUtils.addComponentStyle(priceFlagComboBox, "udm-baseline-value-price-flag-filter");
        VaadinUtils.addComponentStyle(contentFlagComboBox, "udm-baseline-value-content-flag-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initWrWrkInstLayout() {
        wrWrkInstToField.setEnabled(false);
        filterBinder.forField(wrWrkInstFromField)
            .withValidator(numberStringLengthValidator)
            .withValidator(getNumberValidator(), ForeignUi.getMessage(NUMBER_VALIDATION_MESSAGE))
            .withValidator(getBetweenOperatorValidator(wrWrkInstFromField, wrWrkInstOperatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .bind(filter -> Objects.toString(filter.getWrWrkInstExpression().getFieldFirstValue(), StringUtils.EMPTY),
                (filter, value) -> filter.getWrWrkInstExpression()
                    .setFieldFirstValue(NumberUtils.createLong(StringUtils.trimToNull(value))));
        filterBinder.forField(wrWrkInstToField)
            .withValidator(numberStringLengthValidator)
            .withValidator(getNumberValidator(), ForeignUi.getMessage(NUMBER_VALIDATION_MESSAGE))
            .withValidator(getBetweenOperatorValidator(wrWrkInstToField, wrWrkInstOperatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .withValidator(value -> validateIntegerFromToValues(wrWrkInstFromField, wrWrkInstToField),
                ForeignUi.getMessage(GRATER_OR_EQUAL_VALIDATION_MESSAGE,
                    ForeignUi.getMessage("label.wr_wrk_inst_from")))
            .bind(filter -> Objects.toString(filter.getWrWrkInstExpression().getFieldSecondValue(), StringUtils.EMPTY),
                (filter, value) -> filter.getWrWrkInstExpression()
                    .setFieldSecondValue(NumberUtils.createLong(StringUtils.trimToNull(value))));
        filterBinder.forField(wrWrkInstOperatorComboBox)
            .bind(filter -> ObjectUtils.defaultIfNull(
                filter.getWrWrkInstExpression().getOperator(), FilterOperatorEnum.valueOf(EQUALS)),
                (filter, value) -> filter.getWrWrkInstExpression().setOperator(value));
        wrWrkInstOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(wrWrkInstFromField, wrWrkInstToField, event.getValue()));
        HorizontalLayout wrWrkInstLayout =
            new HorizontalLayout(wrWrkInstFromField, wrWrkInstToField, wrWrkInstOperatorComboBox);
        setComponentsFullSize(wrWrkInstFromField, wrWrkInstToField, wrWrkInstOperatorComboBox, wrWrkInstLayout);
        VaadinUtils.addComponentStyle(wrWrkInstFromField, "udm-baseline-value-wr-wrk-inst-from-filter");
        VaadinUtils.addComponentStyle(wrWrkInstToField, "udm-baseline-value-wr-wrk-inst-to-filter");
        VaadinUtils.addComponentStyle(wrWrkInstOperatorComboBox, "udm-baseline-value-wr-wrk-inst-operator-filter");
        return wrWrkInstLayout;
    }

    private HorizontalLayout initSystemTitleLayout() {
        filterBinder.forField(systemTitleField)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage(LENGTH_VALIDATION_MESSAGE, 2000), 0, 2000))
            .bind(filter -> Objects.toString(filter.getSystemTitleExpression().getFieldFirstValue(), null),
                (filter, value) -> filter.getSystemTitleExpression().setFieldFirstValue(StringUtils.trimToNull(value)));
        filterBinder.forField(systemTitleOperatorComboBox)
            .bind(filter -> ObjectUtils.defaultIfNull(
                    filter.getSystemTitleExpression().getOperator(), FilterOperatorEnum.valueOf(EQUALS)),
                (filter, value) -> filter.getSystemTitleExpression().setOperator(value));
        HorizontalLayout horizontalLayout = new HorizontalLayout(systemTitleField, systemTitleOperatorComboBox);
        systemTitleOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(systemTitleField, event.getValue()));
        setComponentsFullSize(systemTitleField, systemTitleOperatorComboBox, horizontalLayout);
        VaadinUtils.addComponentStyle(systemTitleField, "udm-baseline-value-system-title-filter");
        VaadinUtils.addComponentStyle(systemTitleOperatorComboBox, "udm-baseline-value-system-title-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initPriceLayout() {
        priceToField.setEnabled(false);
        filterBinder.forField(priceFromField)
            .withValidator(new AmountValidator())
            .withValidator(getBetweenOperatorValidator(priceFromField, priceOperatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .bind(filter -> Objects.toString(filter.getPriceExpression().getFieldFirstValue(), StringUtils.EMPTY),
                (filter, value) -> filter.getPriceExpression()
                    .setFieldFirstValue(NumberUtils.createBigDecimal(StringUtils.trimToNull(value))));
        filterBinder.forField(priceToField)
            .withValidator(new AmountValidator())
            .withValidator(getBetweenOperatorValidator(priceToField, priceOperatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .withValidator(value -> validateBigDecimalFromToValues(priceFromField, priceToField),
                ForeignUi.getMessage(GRATER_OR_EQUAL_VALIDATION_MESSAGE,
                    ForeignUi.getMessage("label.price_from")))
            .bind(filter -> Objects.toString(filter.getPriceExpression().getFieldSecondValue(), StringUtils.EMPTY),
                (filter, value) -> filter.getPriceExpression()
                    .setFieldSecondValue(NumberUtils.createBigDecimal(StringUtils.trimToNull(value))));
        filterBinder.forField(priceOperatorComboBox)
            .bind(filter -> ObjectUtils.defaultIfNull(
                    filter.getPriceExpression().getOperator(), FilterOperatorEnum.valueOf(EQUALS)),
                (filter, value) -> filter.getPriceExpression().setOperator(value));
        HorizontalLayout horizontalLayout = new HorizontalLayout(priceFromField, priceToField, priceOperatorComboBox);
        priceOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(priceFromField, priceToField, event.getValue()));
        setComponentsFullSize(priceFromField, priceToField, priceOperatorComboBox, horizontalLayout);
        VaadinUtils.addComponentStyle(priceFromField, "udm-baseline-value-price-from-filter");
        VaadinUtils.addComponentStyle(priceToField, "udm-baseline-value-price-to-filter");
        VaadinUtils.addComponentStyle(priceOperatorComboBox, "udm-baseline-value-price-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initContentLayout() {
        contentToField.setEnabled(false);
        filterBinder.forField(contentFromField)
            .withValidator(new AmountValidator())
            .withValidator(getBetweenOperatorValidator(contentFromField, contentOperatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .bind(filter -> Objects.toString(filter.getContentExpression().getFieldFirstValue(), StringUtils.EMPTY),
                (filter, value) -> filter.getContentExpression()
                    .setFieldFirstValue(NumberUtils.createBigDecimal(StringUtils.trimToNull(value))));
        filterBinder.forField(contentToField)
            .withValidator(new AmountValidator())
            .withValidator(getBetweenOperatorValidator(contentToField, contentOperatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .withValidator(value -> validateBigDecimalFromToValues(contentFromField, contentToField),
                ForeignUi.getMessage(GRATER_OR_EQUAL_VALIDATION_MESSAGE,
                    ForeignUi.getMessage("label.content_from")))
            .bind(filter -> Objects.toString(filter.getContentExpression().getFieldSecondValue(), StringUtils.EMPTY),
                (filter, value) -> filter.getContentExpression()
                    .setFieldSecondValue(NumberUtils.createBigDecimal(StringUtils.trimToNull(value))));
        filterBinder.forField(contentOperatorComboBox)
            .bind(filter -> ObjectUtils.defaultIfNull(
                    filter.getContentExpression().getOperator(), FilterOperatorEnum.valueOf(EQUALS)),
                (filter, value) -> filter.getContentExpression().setOperator(value));
        HorizontalLayout horizontalLayout =
            new HorizontalLayout(contentFromField, contentToField, contentOperatorComboBox);
        contentOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(contentFromField, contentToField, event.getValue()));
        setComponentsFullSize(contentFromField, contentToField, contentOperatorComboBox, horizontalLayout);
        VaadinUtils.addComponentStyle(contentFromField, "udm-baseline-value-content-from-filter");
        VaadinUtils.addComponentStyle(contentToField, "udm-baseline-value-content-to-filter");
        VaadinUtils.addComponentStyle(contentOperatorComboBox, "udm-baseline-value-content-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initContentUnitPriceLayout() {
        contentUnitPriceToField.setEnabled(false);
        filterBinder.forField(contentUnitPriceFromField)
            .withValidator(new AmountValidator())
            .withValidator(getBetweenOperatorValidator(contentUnitPriceFromField, contentUnitPriceComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .bind(filter ->
                    Objects.toString(filter.getContentUnitPriceExpression().getFieldFirstValue(), StringUtils.EMPTY),
                (filter, value) -> filter.getContentUnitPriceExpression()
                    .setFieldFirstValue(NumberUtils.createBigDecimal(StringUtils.trimToNull(value))));
        filterBinder.forField(contentUnitPriceToField)
            .withValidator(new AmountValidator())
            .withValidator(getBetweenOperatorValidator(contentUnitPriceToField, contentUnitPriceComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .withValidator(value -> validateBigDecimalFromToValues(contentUnitPriceFromField, contentUnitPriceToField),
                ForeignUi.getMessage(GRATER_OR_EQUAL_VALIDATION_MESSAGE,
                    ForeignUi.getMessage("label.content_unit_price_from")))
            .bind(filter ->
                    Objects.toString(filter.getContentUnitPriceExpression().getFieldSecondValue(), StringUtils.EMPTY),
                (filter, value) -> filter.getContentUnitPriceExpression()
                    .setFieldSecondValue(NumberUtils.createBigDecimal(StringUtils.trimToNull(value))));
        contentUnitPriceComboBox.addValueChangeListener(
            event -> updateOperatorField(contentUnitPriceFromField, contentUnitPriceToField, event.getValue()));
        filterBinder.forField(contentUnitPriceComboBox)
            .bind(filter -> ObjectUtils.defaultIfNull(
                    filter.getContentUnitPriceExpression().getOperator(), FilterOperatorEnum.valueOf(EQUALS)),
                (filter, value) -> filter.getContentUnitPriceExpression().setOperator(value));
        HorizontalLayout horizontalLayout =
            new HorizontalLayout(contentUnitPriceFromField, contentUnitPriceToField, contentUnitPriceComboBox);
        setComponentsFullSize(contentUnitPriceFromField, contentUnitPriceToField, contentUnitPriceComboBox,
            horizontalLayout);
        VaadinUtils.addComponentStyle(contentUnitPriceFromField, "udm-baseline-value-content-unit-price-from-filter");
        VaadinUtils.addComponentStyle(contentUnitPriceToField, "udm-baseline-value-content-unit-price-to-filter");
        VaadinUtils.addComponentStyle(contentUnitPriceComboBox,
            "udm-baseline-value-content-unit-price-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initCommentLayout() {
        filterBinder.forField(commentField)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage(LENGTH_VALIDATION_MESSAGE, 1024), 0, 1024))
            .bind(filter -> Objects.toString(filter.getCommentExpression().getFieldFirstValue(), null),
                (filter, value) -> filter.getCommentExpression().setFieldFirstValue(StringUtils.trimToNull(value)));
        filterBinder.forField(commentOperatorComboBox)
            .bind(filter -> ObjectUtils.defaultIfNull(
                filter.getCommentExpression().getOperator(), FilterOperatorEnum.valueOf(EQUALS)),
                (filter, value) -> filter.getCommentExpression().setOperator(value));
        HorizontalLayout horizontalLayout = new HorizontalLayout(commentField, commentOperatorComboBox);
        commentOperatorComboBox.addValueChangeListener(event -> updateOperatorField(commentField, event.getValue()));
        setComponentsFullSize(commentField, commentOperatorComboBox, horizontalLayout);
        VaadinUtils.addComponentStyle(commentField, "udm-baseline-value-comment-filter");
        VaadinUtils.addComponentStyle(commentOperatorComboBox, "udm-baseline-value-comment-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initButtonsLayout() {
        Button closeButton = Buttons.createCloseButton(this);
        Button saveButton = Buttons.createButton(ForeignUi.getMessage("button.save"));
        saveButton.addClickListener(event -> {
            try {
                filterBinder.writeBean(baselineValueFilter);
                close();
            } catch (ValidationException e) {
                Windows.showValidationErrorWindow(
                    Arrays.asList(wrWrkInstFromField, wrWrkInstToField, systemTitleField, priceFromField,
                        priceToField, contentFromField, contentToField, contentUnitPriceFromField,
                        contentUnitPriceToField, commentField));
            }
        });
        Button clearButton = Buttons.createButton(ForeignUi.getMessage("button.clear"));
        clearButton.addClickListener(event -> filterBinder.readBean(new UdmBaselineValueFilter()));
        return new HorizontalLayout(saveButton, clearButton, closeButton);
    }

    private ComboBox<FilterOperatorEnum> buildTextOperatorComboBox() {
        return buildOperatorComboBox(FilterOperatorEnum.EQUALS, FilterOperatorEnum.DOES_NOT_EQUAL,
            FilterOperatorEnum.CONTAINS, FilterOperatorEnum.IS_NULL, FilterOperatorEnum.IS_NOT_NULL);
    }

    private ComboBox<FilterOperatorEnum> buildNumericOperatorComboBox() {
        return buildOperatorComboBox(FilterOperatorEnum.EQUALS, FilterOperatorEnum.DOES_NOT_EQUAL,
            FilterOperatorEnum.GREATER_THAN, FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO,
            FilterOperatorEnum.LESS_THAN, FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO, FilterOperatorEnum.BETWEEN,
            FilterOperatorEnum.IS_NULL, FilterOperatorEnum.IS_NOT_NULL);
    }

    private ComboBox<FilterOperatorEnum> buildOperatorComboBox(FilterOperatorEnum... items) {
        ComboBox<FilterOperatorEnum> filterOperatorComboBox = new ComboBox<>(ForeignUi.getMessage("label.operator"));
        filterOperatorComboBox.setEmptySelectionAllowed(false);
        filterOperatorComboBox.setSizeFull();
        filterOperatorComboBox.setItems(items);
        filterOperatorComboBox.setSelectedItem(FilterOperatorEnum.EQUALS);
        return filterOperatorComboBox;
    }

    private void updateOperatorField(TextField textField, FilterOperatorEnum filterOperator) {
        if (0 == filterOperator.getArgumentsNumber()) {
            textField.clear();
            textField.setEnabled(false);
        } else {
            textField.setEnabled(true);
        }
        filterBinder.validate();
    }

    private void updateOperatorField(TextField fromField, TextField toField, FilterOperatorEnum filterOperator) {
        if (0 == filterOperator.getArgumentsNumber()) {
            fromField.clear();
            fromField.setEnabled(false);
            toField.clear();
            toField.setEnabled(false);
        } else if (ONE == filterOperator.getArgumentsNumber()) {
            fromField.setEnabled(true);
            toField.clear();
            toField.setEnabled(false);
        } else {
            fromField.setEnabled(true);
            toField.setEnabled(true);
        }
        filterBinder.validate();
    }

    private SerializablePredicate<String> getNumberValidator() {
        return value -> StringUtils.isEmpty(value) || StringUtils.isNumeric(value.trim());
    }

    private void setComponentsFullSize(Component... components) {
        Arrays.stream(components).forEach(Sizeable::setSizeFull);
    }

    private SerializablePredicate<String> getBetweenOperatorValidator(TextField fieldToValidate,
                                                                      ComboBox<FilterOperatorEnum> comboBox) {
        return value -> FilterOperatorEnum.BETWEEN != comboBox.getValue()
            || StringUtils.isNotBlank(fieldToValidate.getValue());
    }

    private boolean validateBigDecimalFromToValues(TextField fromField, TextField toField) {
        String fromValue = fromField.getValue();
        String toValue = toField.getValue();
        AmountZeroValidator amountZeroValidator = new AmountZeroValidator();
        return StringUtils.isBlank(fromValue)
            || StringUtils.isBlank(toValue)
            || !amountZeroValidator.isValid(fromValue)
            || !amountZeroValidator.isValid(toValue)
            || 0 <= new BigDecimal(toValue.trim()).compareTo(new BigDecimal(fromValue.trim()));
    }

    private boolean validateIntegerFromToValues(TextField fromField, TextField toField) {
        String fromValue = fromField.getValue();
        String toValue = toField.getValue();
        return StringUtils.isEmpty(fromValue)
            || StringUtils.isEmpty(toValue)
            || !getNumberValidator().test(fromValue)
            || !getNumberValidator().test(toValue)
            || 0 <= Integer.valueOf(toValue.trim()).compareTo(Integer.valueOf(fromValue.trim()));
    }

    private Boolean convertStringToBoolean(String value) {
        return "Y".equals(value);
    }
}
