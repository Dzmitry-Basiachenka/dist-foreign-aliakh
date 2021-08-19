package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import com.copyright.rup.dist.foreign.domain.CompanyInformation;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.UdmActionReason;
import com.copyright.rup.dist.foreign.domain.UdmIneligibleReason;
import com.copyright.rup.dist.foreign.domain.UdmUsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.google.common.collect.Range;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.ValueProvider;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.Setter;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    private static final Range<BigDecimal> STATISTICAL_MULTIPLIER_RANGE =
        Range.closed(new BigDecimal("0.00001"), BigDecimal.ONE);
    private static final Range<Integer> ANNUAL_MULTIPLIER_RANGE = Range.closed(1, 25);
    private static final List<UsageStatusEnum> EDIT_AVAILABLE_STATUSES =
        Arrays.asList(UsageStatusEnum.NEW, UsageStatusEnum.ELIGIBLE, UsageStatusEnum.INELIGIBLE,
            UsageStatusEnum.OPS_REVIEW, UsageStatusEnum.SPECIALIST_REVIEW);
    private static final String NUMBER_VALIDATION_MESSAGE = "Field value should contain numeric values only";
    private static final String MAX_LENGTH_FIELD_MESSAGE = "field.error.number_length";
    private static final int MIN_YEAR = 1950;
    private static final int MAX_YEAR = 2099;
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
    private final UdmUsageDto udmUsageDto;

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
        idToLicenseeClassMap = controller.getDetailLicenseeClasses()
            .stream()
            .collect(Collectors.toMap(DetailLicenseeClass::getId, Function.identity()));
        udmUsageDto = new UdmUsageDto();
        setContent(initRootLayout());
        setCaption(ForeignUi.getMessage("window.multiple.edit_udm_usage"));
        setResizable(false);
        setWidth(650, Unit.PIXELS);
        setHeight(530, Unit.PIXELS);
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
                UdmUsageDto::getReportedStandardNumber, UdmUsageDto::setReportedStandardNumber,
                "udm-edit-reported-standard-number-field"),
            buildCommonStringLayout(reportedTitleField, "label.reported_title", 1000, UdmUsageDto::getReportedTitle,
                UdmUsageDto::setReportedTitle, "udm-edit-reported-title-field"),
            buildAnnualMultiplierLayout(),
            buildStatisticalMultiplier(),
            buildQuantityLayout(),
            buildActionReasonLayout(),
            buildIneligibleReasonLayout(),
            buildCommonStringLayout(commentField, "label.comment", 4000, UdmUsageDto::getComment,
                UdmUsageDto::setComment, "udm-edit-comment-field"),
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
        statusComboBox.setItems(new LinkedHashSet<>(EDIT_AVAILABLE_STATUSES));
        statusComboBox.setEmptySelectionAllowed(false);
        binder.forField(statusComboBox).bind(UdmUsageDto::getStatus, UdmUsageDto::setStatus);
        VaadinUtils.addComponentStyle(statusComboBox, "udm-multiple-edit-detail-status-combo-box");
        return buildCommonLayout(statusComboBox, "label.detail_status");
    }

    private HorizontalLayout buildPeriodLayout() {
        periodField.setSizeFull();
        binder.forField(periodField)
            .withValidator(value -> StringUtils.isEmpty(value) || StringUtils.isNumeric(value.trim()),
                NUMBER_VALIDATION_MESSAGE)
            .withValidator(value -> StringUtils.isEmpty(value) || value.trim().length() == 6,
                "Period value should contain 6 digits")
            .withValidator(value -> StringUtils.isEmpty(value) || periodYearValidator(value),
                "Year value should be in range from 1950 to 2099")
            .withValidator(value -> StringUtils.isEmpty(value) || periodMonthValidator(value),
                "Month value should be 06 or 12")
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
        binder.forField(companyNameField).bind(UdmUsageDto::getCompanyName, UdmUsageDto::setCompanyName);
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
        VaadinUtils.addComponentStyle(wrWrkInstField, "udm-multiple-edit-wr-wrk-inst-field");
        return buildCommonLayout(wrWrkInstField, "label.wr_wrk_inst");
    }

    private HorizontalLayout buildAnnualMultiplierLayout() {
        annualMultiplierField.setSizeFull();
        binder.forField(annualMultiplierField)
            .withValidator(value -> StringUtils.isEmpty(value) || StringUtils.isNumeric(value.trim())
                    && ANNUAL_MULTIPLIER_RANGE.contains(NumberUtils.toInt(value.trim())),
                "Field value should be positive number between 1 and 25")
            .bind(usage -> Objects.toString(usage.getAnnualMultiplier(), StringUtils.EMPTY),
                (usage, value) -> usage.setAnnualMultiplier(NumberUtils.toInt(value.trim())));
        VaadinUtils.addComponentStyle(annualMultiplierField, "udm-multiple-edit-annual-multiplier-field");
        return buildCommonLayout(annualMultiplierField, "label.annual_multiplier");
    }

    private HorizontalLayout buildStatisticalMultiplier() {
        statisticalMultiplierField.setSizeFull();
        binder.forField(statisticalMultiplierField)
            .withValidator(value -> StringUtils.isEmpty(value) || NumberUtils.isNumber(value.trim())
                    && STATISTICAL_MULTIPLIER_RANGE.contains(NumberUtils.createBigDecimal(value.trim())),
                "Field value should be positive number between 0.00001 and 1.00000")
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
                && Integer.parseInt(StringUtils.trim(value)) > 0, NUMBER_VALIDATION_MESSAGE)
            .bind(usage -> Objects.toString(usage.getQuantity(), StringUtils.EMPTY),
                (usage, value) -> usage.setQuantity(NumberUtils.toLong(value.trim())));
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
            .bind(UdmUsageDto::getIneligibleReason, UdmUsageDto::setIneligibleReason);
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
        VaadinUtils.addComponentStyle(textField, styleName);
        return buildCommonLayout(textField, caption);
    }

    private HorizontalLayout buildCommonLayout(Component component, String labelCaption) {
        Label label = new Label(ForeignUi.getMessage(labelCaption));
        label.addStyleName(Cornerstone.LABEL_BOLD);
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
            try {
                binder.writeBean(udmUsageDto);
                updateUsagesFields();
                controller.updateUsages(selectedUdmUsages, false);
                saveButtonClickListener.buttonClick(event);
                close();
            } catch (ValidationException e) {
                Windows.showValidationErrorWindow(
                    Arrays.asList(periodField, wrWrkInstField, reportedTitleField,
                        reportedStandardNumberField, commentField, companyIdField, companyNameField,
                        detailLicenseeClassComboBox, annualMultiplierField, statisticalMultiplierField, quantityField));
            }
        });
        Button discardButton = Buttons.createButton(ForeignUi.getMessage("button.discard"));
        discardButton.addClickListener(event -> binder.readBean(null));
        return new HorizontalLayout(saveButton, discardButton, closeButton);
    }

    private void recalculateAnnualizedCopies(UdmUsageDto usageDto) {
        if (StringUtils.isNotEmpty(quantityField.getValue()) || StringUtils.isNotEmpty(annualMultiplierField.getValue())
            || StringUtils.isNotEmpty(statisticalMultiplierField.getValue())) {
            usageDto.setAnnualizedCopies(
                controller.calculateAnnualizedCopies(usageDto.getReportedTypeOfUse(), usageDto.getQuantity(),
                    usageDto.getAnnualMultiplier(), usageDto.getStatisticalMultiplier()));
        }
    }

    private <T> void setField(Consumer<T> usageDtoConsumer, T value) {
        if (Objects.nonNull(value)) {
            usageDtoConsumer.accept(value);
        }
    }

    private void updateUsagesFields() {
        selectedUdmUsages.forEach(usageDto -> {
            setField(usageDto::setPeriod, udmUsageDto.getPeriod());
            setField(usageDto::setStatus, udmUsageDto.getStatus());
            setPeriodEndDate(usageDto);
            setField(usageDto::setQuantity, udmUsageDto.getQuantity());
            setField(usageDto::setStatisticalMultiplier, udmUsageDto.getStatisticalMultiplier());
            setField(usageDto::setAnnualMultiplier, udmUsageDto.getAnnualMultiplier());
            recalculateAnnualizedCopies(usageDto);
            setField(usageDto::setCompanyId, udmUsageDto.getCompanyId());
            setField(usageDto::setDetailLicenseeClass, udmUsageDto.getDetailLicenseeClass());
            setField(usageDto::setWrWrkInst, udmUsageDto.getWrWrkInst());
            setField(usageDto::setCompanyName, udmUsageDto.getCompanyName());
            setField(usageDto::setReportedTitle, udmUsageDto.getReportedTitle());
            setField(usageDto::setReportedStandardNumber, udmUsageDto.getReportedStandardNumber());
            setField(usageDto::setComment, udmUsageDto.getComment());
            setField(usageDto::setActionReason, udmUsageDto.getActionReason());
            setField(usageDto::setIneligibleReason, udmUsageDto.getIneligibleReason());
        });
    }

    private boolean periodYearValidator(String value) {
        int year = Integer.parseInt(value.trim().substring(0, 4));
        return year >= MIN_YEAR && year <= MAX_YEAR;
    }

    private boolean periodMonthValidator(String value) {
        int month = Integer.parseInt(value.trim().substring(4, 6));
        return month == 6 || month == 12;
    }

    private void setPeriodEndDate(UdmUsageDto usageDto) {
        String period = periodField.getValue();
        if (StringUtils.isNotEmpty(period)) {
            int year = Integer.parseInt(period.substring(0, 4));
            int month = Integer.parseInt(period.substring(4, 6));
            usageDto.setPeriodEndDate(6 == month ? LocalDate.of(year, month, 30) : LocalDate.of(year, month, 31));
        }
    }
}
