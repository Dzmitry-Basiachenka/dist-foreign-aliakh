package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.usage;

import com.copyright.rup.dist.foreign.domain.CompanyInformation;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.UdmActionReason;
import com.copyright.rup.dist.foreign.domain.UdmIneligibleReason;
import com.copyright.rup.dist.foreign.domain.UdmUsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.ui.audit.impl.UdmUsageAuditFieldToValuesMap;
import com.copyright.rup.dist.foreign.ui.common.validator.PeriodValidator;
import com.copyright.rup.dist.foreign.ui.common.validator.RequiredValidator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.google.common.collect.Range;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.ValueProvider;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.Setter;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Window to edit multiple UDM usages.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 08/10/21
 *
 * @author Anton Azarenka
 */
public class UdmEditMultipleUsagesWindow extends Window {

    private static final long serialVersionUID = 4637923287336034491L;
    private static final Range<BigDecimal> STATISTICAL_MULTIPLIER_RANGE =
        Range.closed(new BigDecimal("0.00001"), BigDecimal.ONE);
    private static final Range<Integer> STATISTICAL_MULTIPLIER_SCALE_RANGE = Range.closed(0, 5);
    private static final Range<Integer> ANNUAL_MULTIPLIER_RANGE = Range.closed(1, 25);
    private static final List<UsageStatusEnum> EDIT_AVAILABLE_STATUSES =
        List.of(UsageStatusEnum.NEW, UsageStatusEnum.ELIGIBLE, UsageStatusEnum.INELIGIBLE,
            UsageStatusEnum.OPS_REVIEW, UsageStatusEnum.SPECIALIST_REVIEW);
    private static final String NUMBER_VALIDATION_MESSAGE = ForeignUi.getMessage("field.error.not_numeric");
    private static final String MAX_LENGTH_FIELD_MESSAGE = "field.error.number_length";
    private final IUdmUsageController controller;
    private final ComboBox<UsageStatusEnum> statusComboBox =
        new ComboBox<>(ForeignUi.getMessage("label.detail_status"));
    private final ComboBox<DetailLicenseeClass> detailLicenseeClassComboBox =
        new ComboBox<>(ForeignUi.getMessage("label.det_lc"));
    private final TextField wrWrkInstField = new TextField(ForeignUi.getMessage("label.wr_wrk_inst"));
    private final ComboBox<UdmActionReason> actionReasonComboBox =
        new ComboBox<>(ForeignUi.getMessage("label.action_reason_udm"));
    private final ComboBox<UdmIneligibleReason> ineligibleReasonComboBox =
        new ComboBox<>(ForeignUi.getMessage("label.ineligible_reason"));
    private final TextField periodField = new TextField(ForeignUi.getMessage("label.edit.period"));
    private final TextField annualMultiplierField = new TextField(ForeignUi.getMessage("label.annual_multiplier"));
    private final TextField statisticalMultiplierField =
        new TextField(ForeignUi.getMessage("label.statistical_multiplier"));
    private final TextField reportedStandardNumberField =
        new TextField(ForeignUi.getMessage("label.reported_standard_number"));
    private final TextField reportedTitleField = new TextField(ForeignUi.getMessage("label.reported_title"));
    private final TextField quantityField = new TextField(ForeignUi.getMessage("label.quantity"));
    private final TextField commentField = new TextField(ForeignUi.getMessage("label.comment"));
    private final TextField companyIdField = new TextField(ForeignUi.getMessage("label.company_id"));
    private final TextField companyNameField = new TextField(ForeignUi.getMessage("label.company_name"));
    private final Map<Integer, DetailLicenseeClass> idToLicenseeClassMap;
    private final Button saveButton = Buttons.createButton(ForeignUi.getMessage("button.save"));
    private final Binder<UdmUsageDto> binder = new Binder<>();
    private final Set<UdmUsageDto> selectedUdmUsages;
    private final ClickListener saveButtonClickListener;
    private final UdmUsageDto bindedUsageDto;
    private Map<UdmUsageDto, UdmUsageAuditFieldToValuesMap> udmUsageDtoToFieldValuesMap;

    /**
     * Constructor.
     *
     * @param usageController   instance of {@link IUdmUsageController}
     * @param selectedUdmUsages UDM usage to be displayed on the window
     * @param clickListener     action that should be performed after Save button was clicked
     */
    public UdmEditMultipleUsagesWindow(IUdmUsageController usageController, Set<UdmUsageDto> selectedUdmUsages,
                                       ClickListener clickListener) {
        this.controller = usageController;
        this.selectedUdmUsages = selectedUdmUsages;
        saveButtonClickListener = clickListener;
        idToLicenseeClassMap = controller.getIdsToDetailLicenseeClasses();
        bindedUsageDto = new UdmUsageDto();
        super.setContent(initRootLayout());
        super.setCaption(ForeignUi.getMessage("window.multiple.edit_udm_usages"));
        super.setResizable(false);
        super.setWidth(650, Unit.PIXELS);
        super.setHeight(530, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "multiple-edit-udm-usages-window");
    }

    private Component initRootLayout() {
        VerticalLayout rootLayout = new VerticalLayout();
        HorizontalLayout buttonsLayout = initButtonsLayout();
        rootLayout.addComponents(
            buildDetailStatusLayout(),
            buildPeriodLayout(),
            buildDetailLicenseeClassLayout(),
            buildCompanyLayout(),
            buildCompanyNameLayout(),
            buildWrWrkInstLayout(),
            buildCommonStringLayout(reportedStandardNumberField, "label.reported_standard_number", 100,
                UdmUsageDto::getReportedStandardNumber, (usage, value) -> usage.setReportedStandardNumber(
                    StringUtils.trimToNull(value)), "udm-edit-reported-standard-number-field"),
            buildCommonStringLayout(reportedTitleField, "label.reported_title", 1000, UdmUsageDto::getReportedTitle,
                (usage, value) -> usage.setReportedTitle(StringUtils.trimToNull(value)),
                "udm-edit-reported-title-field"),
            buildAnnualMultiplierLayout(),
            buildStatisticalMultiplier(),
            buildQuantityLayout(),
            buildActionReasonLayout(),
            buildIneligibleReasonLayout(),
            buildCommonStringLayout(commentField, "label.comment", 4000, UdmUsageDto::getComment,
                (usage, value) -> usage.setComment(StringUtils.trimToNull(value)), "udm-edit-comment-field"),
            buttonsLayout
        );
        rootLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        rootLayout.setSizeFull();
        binder.validate();
        binder.addValueChangeListener(event -> saveButton.setEnabled(binder.hasChanges()));
        return rootLayout;
    }

    private HorizontalLayout buildDetailStatusLayout() {
        statusComboBox.setSizeFull();
        statusComboBox.setItems(EDIT_AVAILABLE_STATUSES);
        statusComboBox.setEmptySelectionAllowed(false);
        binder.forField(statusComboBox)
            .withValidator(
                value -> UsageStatusEnum.INELIGIBLE != value || Objects.nonNull(ineligibleReasonComboBox.getValue()),
                ForeignUi.getMessage("field.error.ineligible_only_if_ineligible_reason_populated"))
            .withValidator(value -> StringUtils.isEmpty(wrWrkInstField.getValue())
                && StringUtils.isEmpty(reportedTitleField.getValue())
                && StringUtils.isEmpty(reportedStandardNumberField.getValue())
                || value == UsageStatusEnum.NEW,
                ForeignUi.getMessage("field.error.not_set_new_detail_status_after_changes"))
            .bind(UdmUsageDto::getStatus, UdmUsageDto::setStatus);
        statusComboBox.addValueChangeListener(event -> binder.validate());
        VaadinUtils.addComponentStyle(statusComboBox, "udm-multiple-edit-detail-status-combo-box");
        return buildCommonLayout(statusComboBox, "label.detail_status");
    }

    private HorizontalLayout buildPeriodLayout() {
        periodField.setSizeFull();
        binder.forField(periodField)
            .withValidator(value -> StringUtils.isEmpty(value) || StringUtils.isNumeric(value.trim()),
                NUMBER_VALIDATION_MESSAGE)
            .withValidator(value -> StringUtils.isEmpty(value) || value.trim().length() == PeriodValidator.VALID_LENGTH,
                ForeignUi.getMessage("field.error.period_length", PeriodValidator.VALID_LENGTH))
            .withValidator(value -> StringUtils.isEmpty(value) || PeriodValidator.isYearValid(value),
                ForeignUi.getMessage("field.error.year_not_in_range",
                    PeriodValidator.MIN_YEAR, PeriodValidator.MAX_YEAR))
            .withValidator(value -> StringUtils.isEmpty(value) || PeriodValidator.isMonthValid(value),
                ForeignUi.getMessage("field.error.month_invalid",
                    PeriodValidator.MONTH_6, PeriodValidator.MONTH_12))
            .bind(usage -> Objects.toString(usage.getPeriod(), StringUtils.EMPTY),
                (usage, value) -> usage.setPeriod(NumberUtils.createInteger(StringUtils.trimToNull(value))));
        VaadinUtils.addComponentStyle(periodField, "udm-multiple-edit-period-field");
        return buildCommonLayout(periodField, "label.edit.period");
    }

    private HorizontalLayout buildDetailLicenseeClassLayout() {
        detailLicenseeClassComboBox.setSizeFull();
        detailLicenseeClassComboBox.setEmptySelectionAllowed(true);
        detailLicenseeClassComboBox.setItemCaptionGenerator(detailLicenseeClass ->
            String.format("%s - %s", detailLicenseeClass.getId(), detailLicenseeClass.getDescription()));
        detailLicenseeClassComboBox.setItems(idToLicenseeClassMap.values());
        binder.forField(detailLicenseeClassComboBox)
            .bind(UdmUsageDto::getDetailLicenseeClass, UdmUsageDto::setDetailLicenseeClass);
        VaadinUtils.addComponentStyle(detailLicenseeClassComboBox, "udm-multiple-edit-detail-licensee-class-combo-box");
        return buildCommonLayout(detailLicenseeClassComboBox, "label.det_lc");
    }

    private HorizontalLayout buildCompanyLayout() {
        binder.forField(companyIdField)
            .withValidator(value -> StringUtils.isEmpty(value) || StringUtils.isNumeric(value.trim()),
                NUMBER_VALIDATION_MESSAGE)
            .withValidator(
                new StringLengthValidator(ForeignUi.getMessage(MAX_LENGTH_FIELD_MESSAGE, 10), 0, 10))
            .bind(usage -> Objects.toString(usage.getCompanyId()),
                (usage, value) -> usage.setCompanyId(NumberUtils.createLong(StringUtils.trimToNull(value))));
        companyIdField.setSizeFull();
        companyIdField.addValueChangeListener(event -> {
            companyNameField.clear();
            detailLicenseeClassComboBox.setSelectedItem(null);
        });
        Button verifyButton = Buttons.createButton(ForeignUi.getMessage("button.verify"));
        verifyButton.addClickListener(event -> {
            if (StringUtils.isNotEmpty(companyIdField.getValue()) && Objects.isNull(companyIdField.getErrorMessage())) {
                CompanyInformation information =
                    controller.getCompanyInformation(Long.valueOf(companyIdField.getValue().trim()));
                if (StringUtils.isNotBlank(information.getName())) {
                    companyNameField.setValue(information.getName());
                    detailLicenseeClassComboBox.setSelectedItem(
                        idToLicenseeClassMap.get(information.getDetailLicenseeClassId()));
                }
            } else {
                companyNameField.clear();
                detailLicenseeClassComboBox.setSelectedItem(null);
            }
        });
        VaadinUtils.addComponentStyle(companyIdField, "udm-multiple-edit-company-id-field");
        HorizontalLayout layout = buildCommonLayout(companyIdField, "label.company_id");
        layout.addComponent(verifyButton);
        return layout;
    }

    private HorizontalLayout buildCompanyNameLayout() {
        companyNameField.setReadOnly(true);
        companyNameField.setSizeFull();
        binder.forField(companyNameField)
            .withValidator(
                value -> StringUtils.isNotEmpty(value.trim()) && StringUtils.isNotEmpty(companyIdField.getValue())
                    || StringUtils.isEmpty(companyIdField.getValue()),
                ForeignUi.getMessage("field.error.company_name.empty"))
            .bind(UdmUsageDto::getCompanyName, (usage, value) -> usage.setCompanyName(StringUtils.trimToNull(value)));
        return buildCommonLayout(companyNameField, "label.company_name");
    }

    private HorizontalLayout buildWrWrkInstLayout() {
        wrWrkInstField.setSizeFull();
        binder.forField(wrWrkInstField)
            .withValidator(value -> StringUtils.isEmpty(value) || StringUtils.isNumeric(value.trim()),
                NUMBER_VALIDATION_MESSAGE)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage(MAX_LENGTH_FIELD_MESSAGE, 9), 0, 9))
            .bind(usage -> Objects.toString(usage.getWrWrkInst(), StringUtils.EMPTY),
                (usage, value) -> usage.setWrWrkInst(NumberUtils.createLong(StringUtils.trimToNull(value))));
        wrWrkInstField.addValueChangeListener(event -> binder.validate());
        VaadinUtils.addComponentStyle(wrWrkInstField, "udm-multiple-edit-wr-wrk-inst-field");
        return buildCommonLayout(wrWrkInstField, "label.wr_wrk_inst");
    }

    private HorizontalLayout buildAnnualMultiplierLayout() {
        annualMultiplierField.setSizeFull();
        binder.forField(annualMultiplierField)
            .withValidator(value -> StringUtils.isEmpty(value) || StringUtils.isNumeric(value.trim())
                    && ANNUAL_MULTIPLIER_RANGE.contains(NumberUtils.toInt(value.trim())),
                ForeignUi.getMessage("field.error.positive_number_between", "1", "25"))
            .bind(usage -> Objects.toString(usage.getAnnualMultiplier(), StringUtils.EMPTY),
                (usage, value) -> usage.setAnnualMultiplier(NumberUtils.createInteger(StringUtils.trimToNull(value))));
        VaadinUtils.addComponentStyle(annualMultiplierField, "udm-multiple-edit-annual-multiplier-field");
        return buildCommonLayout(annualMultiplierField, "label.annual_multiplier");
    }

    private HorizontalLayout buildStatisticalMultiplier() {
        statisticalMultiplierField.setSizeFull();
        binder.forField(statisticalMultiplierField)
            .withValidator(value -> StringUtils.isEmpty(value) || NumberUtils.isNumber(value.trim())
                    && STATISTICAL_MULTIPLIER_RANGE.contains(NumberUtils.createBigDecimal(value.trim())),
                ForeignUi.getMessage("field.error.positive_number_between", "0.00001", "1.00000"))
            .withValidator(value -> StringUtils.isEmpty(value) ||
                    STATISTICAL_MULTIPLIER_SCALE_RANGE.contains(NumberUtils.createBigDecimal(value.trim()).scale()),
                ForeignUi.getMessage("field.error.number_scale", 5))
            .bind(usage -> Objects.toString(usage.getStatisticalMultiplier(), StringUtils.EMPTY),
                (usage, value) -> usage.setStatisticalMultiplier(
                    NumberUtils.createBigDecimal(StringUtils.trimToNull(value))));
        VaadinUtils.addComponentStyle(statisticalMultiplierField, "udm-multiple-edit-statistical-multiplier-field");
        return buildCommonLayout(statisticalMultiplierField, "label.statistical_multiplier");
    }

    private HorizontalLayout buildQuantityLayout() {
        quantityField.setSizeFull();
        binder.forField(quantityField)
            .withValidator(
                new StringLengthValidator(ForeignUi.getMessage(MAX_LENGTH_FIELD_MESSAGE, 9), 0, 9))
            .withValidator(value -> StringUtils.isEmpty(value) || StringUtils.isNumeric(StringUtils.trim(value))
                && Integer.parseInt(StringUtils.trim(value)) > 0, ForeignUi.getMessage("field.error.positive_number"))
            .bind(usage -> Objects.toString(usage.getQuantity(), StringUtils.EMPTY),
                (usage, value) -> usage.setQuantity(NumberUtils.createLong(StringUtils.trimToNull(value))));
        VaadinUtils.addComponentStyle(quantityField, "udm-multiple-edit-quantity-field");
        return buildCommonLayout(quantityField, "label.quantity");
    }

    private HorizontalLayout buildActionReasonLayout() {
        actionReasonComboBox.setSizeFull();
        actionReasonComboBox.setItemCaptionGenerator(UdmActionReason::getReason);
        actionReasonComboBox.setItems(controller.getAllActionReasons());
        binder.forField(actionReasonComboBox)
            .bind(UdmUsageDto::getActionReason, UdmUsageDto::setActionReason);
        VaadinUtils.addComponentStyle(actionReasonComboBox, "udm-multiple-edit-action-reason-combo-box");
        return buildCommonLayout(actionReasonComboBox, "label.action_reason_udm");
    }

    private HorizontalLayout buildIneligibleReasonLayout() {
        ineligibleReasonComboBox.setSizeFull();
        ineligibleReasonComboBox.setItemCaptionGenerator(UdmIneligibleReason::getReason);
        ineligibleReasonComboBox.setItems(controller.getAllIneligibleReasons());
        binder.forField(ineligibleReasonComboBox)
            .withValidator(
                value -> Objects.isNull(value) || UsageStatusEnum.INELIGIBLE == statusComboBox.getValue(),
                ForeignUi.getMessage("field.error.usage_status_not_ineligible"))
            .bind(UdmUsageDto::getIneligibleReason, UdmUsageDto::setIneligibleReason);
        ineligibleReasonComboBox.addValueChangeListener(event -> binder.validate());
        VaadinUtils.addComponentStyle(ineligibleReasonComboBox, "udm-multiple-edit-ineligible-reason-combo-box");
        return buildCommonLayout(ineligibleReasonComboBox, "label.ineligible_reason");
    }

    private HorizontalLayout buildCommonStringLayout(TextField textField, String caption, int maxLength,
                                                     ValueProvider<UdmUsageDto, String> getter,
                                                     Setter<UdmUsageDto, String> setter, String styleName) {
        textField.setSizeFull();
        binder.forField(textField)
            .withValidator(
                new StringLengthValidator(ForeignUi.getMessage("field.error.length", maxLength), 0, maxLength))
            .bind(getter, setter);
        textField.addValueChangeListener(event -> binder.validate());
        VaadinUtils.addComponentStyle(textField, styleName);
        return buildCommonLayout(textField, caption);
    }

    private HorizontalLayout buildCommonLayout(Component component, String labelCaption) {
        Label label = new Label(ForeignUi.getMessage(labelCaption));
        label.addStyleName(ValoTheme.LABEL_BOLD);
        label.setWidth(165, Unit.PIXELS);
        HorizontalLayout layout = new HorizontalLayout(label, component);
        layout.setSizeFull();
        layout.setExpandRatio(component, 1);
        return layout;
    }

    private HorizontalLayout initButtonsLayout() {
        Button closeButton = Buttons.createCloseButton(this);
        saveButton.setEnabled(false);
        saveButton.addClickListener(event -> {
            binder.validate();
            if (binder.isValid()) {
                if (hasBaselineUsage()) {
                    saveBaselineUsages(event);
                } else {
                    saveUsages(event, StringUtils.EMPTY);
                }
            } else {
                showValidationErrorWindow();
            }
        });
        VaadinUtils.setButtonsAutoDisabled(saveButton);
        Button discardButton = Buttons.createButton(ForeignUi.getMessage("button.discard"));
        discardButton.addClickListener(event -> {
            binder.readBean(null);
            saveButton.setEnabled(false);
        });
        return new HorizontalLayout(saveButton, discardButton, closeButton);
    }

    private boolean hasBaselineUsage() {
        return Objects.nonNull(selectedUdmUsages
            .stream()
            .filter(UdmUsageDto::isBaselineFlag)
            .findFirst()
            .orElse(null));
    }

    private void saveBaselineUsages(ClickEvent event) {
        Windows.showConfirmDialogWithReason(
            ForeignUi.getMessage("window.confirm"),
            ForeignUi.getMessage("message.confirm.remove_usages_from_baseline", selectedUdmUsages
                .stream()
                .filter(UdmUsageDto::isBaselineFlag)
                .count()),
            ForeignUi.getMessage("button.yes"),
            ForeignUi.getMessage("button.cancel"),
            reason -> saveUsages(event, reason),
            List.of(new RequiredValidator(),
                new StringLengthValidator(ForeignUi.getMessage("field.error.length", 1000), 0, 1000)));
    }

    private void saveUsages(ClickEvent event, String reason) {
        try {
            binder.writeBean(bindedUsageDto);
            setPeriodEndDate(bindedUsageDto);
            updateUsagesFields();
            controller.updateUsages(UdmUsageAuditFieldToValuesMap.getDtoToAuditReasonsMap(udmUsageDtoToFieldValuesMap),
                false, reason.trim());
            saveButtonClickListener.buttonClick(event);
            close();
        } catch (ValidationException e) {
            showValidationErrorWindow();
        }
    }

    private void showValidationErrorWindow() {
        Windows.showValidationErrorWindow(
            List.of(statusComboBox, periodField, detailLicenseeClassComboBox, companyIdField,
                companyNameField, wrWrkInstField, reportedStandardNumberField, reportedTitleField,
                annualMultiplierField, statisticalMultiplierField, quantityField, ineligibleReasonComboBox,
                commentField));
    }

    private void checkStatusAndUpdateIneligibleReason() {
        if (Objects.nonNull(bindedUsageDto.getStatus())
            && !bindedUsageDto.getStatus().equals(UsageStatusEnum.INELIGIBLE)) {
            bindedUsageDto.setIneligibleReason(new UdmIneligibleReason());
        }
    }

    private void updateUsagesFields() {
        udmUsageDtoToFieldValuesMap = new HashMap<>();
        checkStatusAndUpdateIneligibleReason();
        selectedUdmUsages.forEach(usageDto -> {
            UdmUsageAuditFieldToValuesMap valuesMap = new UdmUsageAuditFieldToValuesMap();
            setFieldAndAddAudit(usageDto::setStatus, UdmUsageDto::getStatus, bindedUsageDto, usageDto,
                "label.detail_status", valuesMap);
            setFieldAndAddAudit(usageDto::setPeriod, UdmUsageDto::getPeriod, bindedUsageDto, usageDto, "label.period",
                valuesMap);
            setFieldAndAddAudit(usageDto::setDetailLicenseeClass, UdmUsageDto::getDetailLicenseeClass,
                this::buildDetailLicenseClassString, bindedUsageDto, usageDto, "label.det_lc", valuesMap);
            setFieldAndAddAudit(usageDto::setCompanyId, UdmUsageDto::getCompanyId, bindedUsageDto, usageDto,
                "label.company_id", valuesMap);
            setFieldAndAddAudit(usageDto::setCompanyName, UdmUsageDto::getCompanyName, bindedUsageDto, usageDto,
                "label.company_name", valuesMap);
            setFieldAndAddAudit(usageDto::setWrWrkInst, UdmUsageDto::getWrWrkInst, bindedUsageDto, usageDto,
                "label.wr_wrk_inst", valuesMap);
            setFieldAndAddAudit(usageDto::setReportedTitle, UdmUsageDto::getReportedTitle, bindedUsageDto, usageDto,
                "label.reported_title", valuesMap);
            setFieldAndAddAudit(usageDto::setReportedStandardNumber, UdmUsageDto::getReportedStandardNumber,
                bindedUsageDto, usageDto, "label.reported_standard_number", valuesMap);
            setFieldAndAddAudit(usageDto::setAnnualMultiplier, UdmUsageDto::getAnnualMultiplier, bindedUsageDto,
                usageDto, "label.annual_multiplier", valuesMap);
            setFieldAndAddAudit(usageDto::setStatisticalMultiplier, UdmUsageDto::getStatisticalMultiplier,
                bindedUsageDto, usageDto, "label.statistical_multiplier", valuesMap);
            setFieldAndAddAudit(usageDto::setQuantity, UdmUsageDto::getQuantity, bindedUsageDto, usageDto,
                "label.quantity", valuesMap);
            setFieldAndAddAudit(usageDto::setActionReason, UdmUsageDto::getActionReason, (usage) ->
                    Objects.nonNull(usage.getActionReason()) ? usage.getActionReason().getReason() : null,
                bindedUsageDto, usageDto, "label.action_reason_udm", valuesMap);
            setFieldAndAddAudit(usageDto::setIneligibleReason, UdmUsageDto::getIneligibleReason, (usage) ->
                    Objects.nonNull(usage.getIneligibleReason()) ? usage.getIneligibleReason().getReason() : null,
                bindedUsageDto, usageDto, "label.ineligible_reason", valuesMap);
            setFieldAndAddAudit(usageDto::setComment, UdmUsageDto::getComment, bindedUsageDto, usageDto,
                "label.comment", valuesMap);
            recalculateAnnualizedCopies(usageDto, valuesMap);
            usageDto.setPeriodEndDate(
                Objects.nonNull(bindedUsageDto.getPeriodEndDate()) ? bindedUsageDto.getPeriodEndDate() :
                    usageDto.getPeriodEndDate());
            udmUsageDtoToFieldValuesMap.put(usageDto, valuesMap);
        });
    }

    private void setPeriodEndDate(UdmUsageDto usageDto) {
        String period = Objects.toString(usageDto.getPeriod(), StringUtils.EMPTY);
        if (StringUtils.isNotEmpty(period)) {
            int year = Integer.parseInt(period.substring(0, 4));
            int month = Integer.parseInt(period.substring(4, 6));
            usageDto.setPeriodEndDate(6 == month ? LocalDate.of(year, month, 30) : LocalDate.of(year, month, 31));
        }
    }

    private <T> void setFieldAndAddAudit(Consumer<T> usageConsumer, Function<UdmUsageDto, T> usageFunction,
                                         UdmUsageDto newUsage, UdmUsageDto oldUsage, String fieldName,
                                         UdmUsageAuditFieldToValuesMap valuesMap) {
        setFieldAndAddAudit(usageConsumer, usageFunction, usageFunction, newUsage, oldUsage, fieldName, valuesMap);
    }

    private <T, K> void setFieldAndAddAudit(Consumer<T> usageConsumer, Function<UdmUsageDto, T> usageFunction,
                                            Function<UdmUsageDto, K> auditFunction, UdmUsageDto newUsage,
                                            UdmUsageDto oldUsage, String fieldName,
                                            UdmUsageAuditFieldToValuesMap valuesMap) {
        T newUsageValue = usageFunction.apply(newUsage);
        K oldAuditValue = auditFunction.apply(oldUsage);
        K newAuditValue = auditFunction.apply(newUsage);
        if (Objects.nonNull(newUsageValue) && !Objects.equals(oldAuditValue, newAuditValue)) {
            usageConsumer.accept(newUsageValue);
            valuesMap.putFieldWithValues(ForeignUi.getMessage(fieldName),
                Objects.toString(oldAuditValue, StringUtils.EMPTY),
                Objects.toString(newAuditValue, StringUtils.EMPTY));
        }
    }

    private void recalculateAnnualizedCopies(UdmUsageDto usageDto, UdmUsageAuditFieldToValuesMap fieldToValuesMap) {
        if (StringUtils.isNotEmpty(quantityField.getValue()) || StringUtils.isNotEmpty(annualMultiplierField.getValue())
            || StringUtils.isNotEmpty(statisticalMultiplierField.getValue())) {
            BigDecimal annualizedCopies = controller.calculateAnnualizedCopies(usageDto.getReportedTypeOfUse(),
                usageDto.getQuantity(), usageDto.getAnnualMultiplier(), usageDto.getStatisticalMultiplier());
            fieldToValuesMap.putFieldWithValues(ForeignUi.getMessage("label.annualized_copies"),
                usageDto.getAnnualizedCopies().toString(), annualizedCopies.toString());
            usageDto.setAnnualizedCopies(controller.calculateAnnualizedCopies(usageDto.getReportedTypeOfUse(),
                usageDto.getQuantity(), usageDto.getAnnualMultiplier(), usageDto.getStatisticalMultiplier()));
        }
    }

    private String buildDetailLicenseClassString(UdmUsageDto usageDto) {
        return Objects.nonNull(usageDto.getDetailLicenseeClass())
            ? String.format("%s - %s", usageDto.getDetailLicenseeClass().getId(),
            usageDto.getDetailLicenseeClass().getDescription()) : null;
    }
}
