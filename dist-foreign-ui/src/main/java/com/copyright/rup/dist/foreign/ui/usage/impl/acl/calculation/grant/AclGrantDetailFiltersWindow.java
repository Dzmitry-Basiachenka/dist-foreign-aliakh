package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.grant;

import com.copyright.rup.dist.foreign.domain.filter.AclGrantDetailFilter;
import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
import com.copyright.rup.dist.foreign.ui.common.GrantStatusFilterWidget;
import com.copyright.rup.dist.foreign.ui.common.LicenseTypeFilterWidget;
import com.copyright.rup.dist.foreign.ui.common.validator.NumericValidator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclGrantDetailFilterController;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.CommonAclFiltersWindow;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.TypeOfUseFilterWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.IFilterSaveListener;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * Window to apply additional filters for {@link AclGrantDetailFilterWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/09/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclGrantDetailFiltersWindow extends CommonAclFiltersWindow {

    private static final String BETWEEN_OPERATOR_VALIDATION_MESSAGE =
        ForeignUi.getMessage("field.error.populated_for_between_operator");
    private static final String GRATER_OR_EQUAL_VALIDATION_MESSAGE = "field.error.greater_or_equal_to";
    private static final String EQUALS = "EQUALS";
    private static final FilterOperatorEnum[] NUMERIC_OPERATOR_ITEMS = {
            FilterOperatorEnum.EQUALS, FilterOperatorEnum.DOES_NOT_EQUAL,
            FilterOperatorEnum.GREATER_THAN, FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO, FilterOperatorEnum.LESS_THAN,
            FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO, FilterOperatorEnum.BETWEEN};

    private final Binder<AclGrantDetailFilter> filterBinder = new Binder<>();
    private final ComboBox<Integer> grantSetPeriodComboBox =
        new ComboBox<>(ForeignUi.getMessage("label.grant_set_period"));
    private final TextField wrWrkInstFromField = new TextField(ForeignUi.getMessage("label.wr_wrk_inst_from"));
    private final TextField wrWrkInstToField = new TextField(ForeignUi.getMessage("label.wr_wrk_inst_to"));
    private final ComboBox<FilterOperatorEnum> wrWrkInstOperatorComboBox =
        buildNumericOperatorComboBox(NUMERIC_OPERATOR_ITEMS);
    private final TextField rhAccountNumberFromField =
        new TextField(ForeignUi.getMessage("label.rh_account_number_from"));
    private final TextField rhAccountNumberToField = new TextField(ForeignUi.getMessage("label.rh_account_number_to"));
    private final ComboBox<FilterOperatorEnum> rhAccountNumberOperatorComboBox =
        buildNumericOperatorComboBox(NUMERIC_OPERATOR_ITEMS);
    private final TextField rhNameField = new TextField(ForeignUi.getMessage("label.rh_name"));
    private final ComboBox<FilterOperatorEnum> rhNameOperatorComboBox =
        buildTextOperatorComboBox(FilterOperatorEnum.EQUALS, FilterOperatorEnum.DOES_NOT_EQUAL,
            FilterOperatorEnum.CONTAINS);
    private final ComboBox<FilterOperatorEnum> eligibleComboBox =
        new ComboBox<>(ForeignUi.getMessage("label.eligible"));
    private final ComboBox<FilterOperatorEnum> editableComboBox =
        new ComboBox<>(ForeignUi.getMessage("label.editable"));
    private final AclGrantDetailFilter aclGrantDetailFilter;
    private final IAclGrantDetailFilterController controller;

    private LicenseTypeFilterWidget licenseTypeFilterWidget;
    private GrantStatusFilterWidget grantStatusFilterWidget;
    private TypeOfUseFilterWidget typeOfUseFilterWidget;

    /**
     * Constructor.
     *
     * @param controller           instance of {@link AclGrantDetailFilterController}
     * @param aclGrantDetailFilter instance of {@link AclGrantDetailFilter} to be displayed on window
     */
    public AclGrantDetailFiltersWindow(IAclGrantDetailFilterController controller,
                                       AclGrantDetailFilter aclGrantDetailFilter) {
        this.controller = controller;
        this.aclGrantDetailFilter = new AclGrantDetailFilter(aclGrantDetailFilter);
        setContent(initRootLayout());
        setCaption(ForeignUi.getMessage("window.acl_grant_details_additional_filters"));
        setResizable(false);
        setWidth(600, Unit.PIXELS);
        setHeight(350, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "acl-grant-details-additional-filters-window");
    }

    /**
     * @return applied ACL grant detail filter.
     */
    public AclGrantDetailFilter getAppliedGrantDetailFilter() {
        return aclGrantDetailFilter;
    }

    private ComponentContainer initRootLayout() {
        initLicenseTypeFilterWidget();
        initGrantStatusFilterWidget();
        initTypeOfUseFilterWidget();
        VerticalLayout rootLayout = new VerticalLayout();
        VerticalLayout fieldsLayout = new VerticalLayout();
        fieldsLayout.addComponents(initMultipleSelectFiltersLayout(), initGrantSetPeriodComboBox(),
            initWrWrkInstLayout(), initRhAccountNumberLayout(), initRhNameLayout(), initEligibleEditableLayout());
        Panel panel = new Panel(fieldsLayout);
        panel.setSizeFull();
        fieldsLayout.setMargin(new MarginInfo(true));
        HorizontalLayout buttonsLayout = initButtonsLayout();
        rootLayout.addComponents(panel, buttonsLayout);
        rootLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        rootLayout.setExpandRatio(panel, 1f);
        rootLayout.setSizeFull();
        panel.setStyleName(Cornerstone.FORMLAYOUT_LIGHT);
        filterBinder.readBean(aclGrantDetailFilter);
        filterBinder.validate();
        return rootLayout;
    }

    private HorizontalLayout initMultipleSelectFiltersLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout(licenseTypeFilterWidget, grantStatusFilterWidget,
            typeOfUseFilterWidget);
        horizontalLayout.setSizeFull();
        horizontalLayout.setSpacing(true);
        return horizontalLayout;
    }

    private void initLicenseTypeFilterWidget() {
        licenseTypeFilterWidget = new LicenseTypeFilterWidget(() -> List.of("ACL", "MACL", "VGW", "JACDCL"),
            aclGrantDetailFilter.getLicenseTypes());
        licenseTypeFilterWidget.addFilterSaveListener((IFilterSaveListener<String>) saveEvent ->
            aclGrantDetailFilter.setLicenseTypes(saveEvent.getSelectedItemsIds()));
    }

    private void initGrantStatusFilterWidget() {
        grantStatusFilterWidget = new GrantStatusFilterWidget(() -> List.of("GRANT", "DENY"),
            aclGrantDetailFilter.getGrantStatuses());
        grantStatusFilterWidget.addFilterSaveListener((IFilterSaveListener<String>) saveEvent ->
            aclGrantDetailFilter.setGrantStatuses(saveEvent.getSelectedItemsIds()));
    }

    private void initTypeOfUseFilterWidget() {
        typeOfUseFilterWidget =
            new TypeOfUseFilterWidget(() -> List.of("PRINT", "DIGITAL"), aclGrantDetailFilter.getTypeOfUses());
        typeOfUseFilterWidget.addFilterSaveListener((IFilterSaveListener<String>) saveEvent ->
            aclGrantDetailFilter.setTypeOfUses(saveEvent.getSelectedItemsIds()));
    }

    private ComboBox<Integer> initGrantSetPeriodComboBox() {
        filterBinder.forField(grantSetPeriodComboBox)
            .bind(AclGrantDetailFilter::getGrantSetPeriod, AclGrantDetailFilter::setGrantSetPeriod);
        grantSetPeriodComboBox.setItems(controller.getGrantPeriods());
        grantSetPeriodComboBox.setSelectedItem(aclGrantDetailFilter.getGrantSetPeriod());
        grantSetPeriodComboBox.setSizeFull();
        grantSetPeriodComboBox.setWidth(50, Unit.PERCENTAGE);
        VaadinUtils.addComponentStyle(grantSetPeriodComboBox, "acl-grant-detail-grant-set-period-filter");
        return grantSetPeriodComboBox;
    }

    private HorizontalLayout initWrWrkInstLayout() {
        wrWrkInstToField.setEnabled(false);
        filterBinder.forField(wrWrkInstFromField)
            .withValidator(getNumberStringLengthValidator(9))
            .withValidator(new NumericValidator())
            .withValidator(getBetweenOperatorValidator(wrWrkInstFromField, wrWrkInstOperatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .bind(filter -> Objects.toString(filter.getWrWrkInstExpression().getFieldFirstValue(), StringUtils.EMPTY),
                (filter, value) -> filter.getWrWrkInstExpression()
                    .setFieldFirstValue(NumberUtils.createLong(StringUtils.trimToNull(value))));
        filterBinder.forField(wrWrkInstToField)
            .withValidator(getNumberStringLengthValidator(9))
            .withValidator(new NumericValidator())
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
            event -> updateOperatorField(filterBinder, wrWrkInstFromField, wrWrkInstToField, event.getValue()));
        HorizontalLayout horizontalLayout =
            new HorizontalLayout(wrWrkInstFromField, wrWrkInstToField, wrWrkInstOperatorComboBox);
        applyCommonNumericFieldFormatting(horizontalLayout, wrWrkInstFromField, wrWrkInstToField);
        VaadinUtils.addComponentStyle(wrWrkInstFromField, "acl-grant-detail-wr-wrk-inst-from-filter");
        VaadinUtils.addComponentStyle(wrWrkInstToField, "acl-grant-detail-wr-wrk-inst-to-filter");
        VaadinUtils.addComponentStyle(wrWrkInstOperatorComboBox, "acl-grant-detail-wr-wrk-inst-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initRhAccountNumberLayout() {
        rhAccountNumberToField.setEnabled(false);
        filterBinder.forField(rhAccountNumberFromField)
            .withValidator(getNumberStringLengthValidator(10))
            .withValidator(new NumericValidator())
            .withValidator(getBetweenOperatorValidator(rhAccountNumberFromField, rhAccountNumberOperatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .bind(filter -> Objects.toString(
                filter.getRhAccountNumberExpression().getFieldFirstValue(), StringUtils.EMPTY),
                (filter, value) -> filter.getRhAccountNumberExpression()
                    .setFieldFirstValue(NumberUtils.createLong(StringUtils.trimToNull(value))));
        filterBinder.forField(rhAccountNumberToField)
            .withValidator(getNumberStringLengthValidator(10))
            .withValidator(new NumericValidator())
            .withValidator(getBetweenOperatorValidator(rhAccountNumberToField, rhAccountNumberOperatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .withValidator(value -> validateIntegerFromToValues(rhAccountNumberFromField, rhAccountNumberToField),
                ForeignUi.getMessage(GRATER_OR_EQUAL_VALIDATION_MESSAGE,
                    ForeignUi.getMessage("label.rh_account_number_from")))
            .bind(filter -> Objects.toString(
                filter.getRhAccountNumberExpression().getFieldSecondValue(), StringUtils.EMPTY),
                (filter, value) -> filter.getRhAccountNumberExpression()
                    .setFieldSecondValue(NumberUtils.createLong(StringUtils.trimToNull(value))));
        filterBinder.forField(rhAccountNumberOperatorComboBox)
            .bind(filter -> ObjectUtils.defaultIfNull(
                    filter.getRhAccountNumberExpression().getOperator(), FilterOperatorEnum.valueOf(EQUALS)),
                (filter, value) -> filter.getRhAccountNumberExpression().setOperator(value));
        rhAccountNumberOperatorComboBox.addValueChangeListener(event ->
            updateOperatorField(filterBinder, rhAccountNumberFromField, rhAccountNumberToField, event.getValue()));
        HorizontalLayout horizontalLayout =
            new HorizontalLayout(rhAccountNumberFromField, rhAccountNumberToField, rhAccountNumberOperatorComboBox);
        applyCommonNumericFieldFormatting(horizontalLayout, rhAccountNumberFromField, rhAccountNumberToField);
        VaadinUtils.addComponentStyle(rhAccountNumberFromField, "acl-grant-detail-rh-account-number-from-filter");
        VaadinUtils.addComponentStyle(rhAccountNumberToField, "acl-grant-detail-rh-account-number-to-filter");
        VaadinUtils.addComponentStyle(rhAccountNumberOperatorComboBox,
            "acl-grant-detail-rh-account-number-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initRhNameLayout() {
        filterBinder.forField(rhNameField)
            .withValidator(getTextStringLengthValidator(255))
            .bind(filter -> Objects.toString(filter.getRhNameExpression().getFieldFirstValue(), StringUtils.EMPTY),
                (filter, value) -> filter.getRhNameExpression().setFieldFirstValue(StringUtils.trimToNull(value)));
        filterBinder.forField(rhNameOperatorComboBox)
            .bind(filter -> ObjectUtils.defaultIfNull(
                    filter.getRhNameExpression().getOperator(), FilterOperatorEnum.valueOf(EQUALS)),
                (filter, value) -> filter.getRhNameExpression().setOperator(value));
        rhNameOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(filterBinder, rhNameField, event.getValue()));
        HorizontalLayout horizontalLayout = new HorizontalLayout(rhNameField, rhNameOperatorComboBox);
        applyCommonTextFieldFormatting(horizontalLayout, rhNameField);
        VaadinUtils.addComponentStyle(rhNameField, "acl-grant-detail-rh-name-filter");
        VaadinUtils.addComponentStyle(rhNameOperatorComboBox, "acl-grant-detail-rh-name-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initEligibleEditableLayout() {
        filterBinder.forField(eligibleComboBox)
            .bind(filter -> filter.getEligibleExpression().getOperator(),
                (filter, value) -> filter.getEligibleExpression().setOperator(value));
        filterBinder.forField(editableComboBox)
            .bind(filter -> filter.getEditableExpression().getOperator(),
                (filter, value) -> filter.getEditableExpression().setOperator(value));
        populateFlagComboBox(eligibleComboBox, aclGrantDetailFilter.getEligibleExpression().getOperator(),
            "acl-grant-detail-eligible-filter");
        populateFlagComboBox(editableComboBox, aclGrantDetailFilter.getEditableExpression().getOperator(),
            "acl-grant-detail-editable-filter");
        HorizontalLayout horizontalLayout = new HorizontalLayout(eligibleComboBox, editableComboBox);
        horizontalLayout.setSizeFull();
        return horizontalLayout;
    }

    private HorizontalLayout initButtonsLayout() {
        Button closeButton = Buttons.createCloseButton(this);
        Button saveButton = Buttons.createButton(ForeignUi.getMessage("button.save"));
        saveButton.addClickListener(event -> {
            try {
                filterBinder.writeBean(aclGrantDetailFilter);
                close();
            } catch (ValidationException e) {
                Windows.showValidationErrorWindow(List.of(wrWrkInstFromField, wrWrkInstToField,
                    rhAccountNumberFromField, rhAccountNumberToField, rhNameField));
            }
        });
        Button clearButton = Buttons.createButton(ForeignUi.getMessage("button.clear"));
        clearButton.addClickListener(event -> clearFilters());
        return new HorizontalLayout(saveButton, clearButton, closeButton);
    }

    private void clearFilters() {
        aclGrantDetailFilter.setLicenseTypes(new HashSet<>());
        aclGrantDetailFilter.setGrantStatuses(new HashSet<>());
        aclGrantDetailFilter.setTypeOfUses(new HashSet<>());
        licenseTypeFilterWidget.reset();
        grantStatusFilterWidget.reset();
        typeOfUseFilterWidget.reset();
        filterBinder.readBean(new AclGrantDetailFilter());
    }
}
