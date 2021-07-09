package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.service.impl.csv.validator.AmountValidator;
import com.copyright.rup.dist.common.util.CommonDateUtils;
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

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.ValueProvider;
import com.vaadin.data.converter.StringToBigDecimalConverter;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.Setter;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;

/**
 * Window to edit UDM usage.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 06/30/2021
 *
 * @author Ihar Suvorau
 */
public class UdmEditUsageWindow extends Window {

    private static final List<UsageStatusEnum> EDIT_AVAILABLE_STATUSES =
        Arrays.asList(UsageStatusEnum.NEW, UsageStatusEnum.ELIGIBLE, UsageStatusEnum.INELIGIBLE,
            UsageStatusEnum.OPS_REVIEW, UsageStatusEnum.SPECIALIST_REVIEW);
    private static final String EMPTY_FIELD_MESSAGE = "field.error.empty";
    private static final String NUMBER_VALIDATION_MESSAGE = "Field value should contain numeric values only";
    private final Binder<UdmUsageDto> binder = new Binder<>();
    private final IUdmUsageController controller;
    private final UdmUsageDto udmUsage;
    private final TextField wrWrkInstField = new TextField(ForeignUi.getMessage("label.wr_wrk_inst"));
    private final TextField companyIdField = new TextField(ForeignUi.getMessage("label.company_id"));
    private final TextField companyNameField = new TextField(ForeignUi.getMessage("label.company_name"));
    private final TextField reportedTitleField = new TextField(ForeignUi.getMessage("label.reported_title"));
    private final TextField reportedStandardNumberField =
        new TextField(ForeignUi.getMessage("label.reported_standard_number"));
    private final TextField reportedPubTypeField = new TextField(ForeignUi.getMessage("label.reported_pub_type"));
    private final TextField commentField = new TextField(ForeignUi.getMessage("label.comment"));
    private final TextField researchUrlField = new TextField(ForeignUi.getMessage("label.research_url"));
    private final TextField annualMultiplierField = new TextField(ForeignUi.getMessage("label.annual_multiplier"));
    private final TextField statisticalMultiplierField =
        new TextField(ForeignUi.getMessage("label.statistical_multiplier"));
    private final TextField quantityField = new TextField(ForeignUi.getMessage("label.quantity"));
    private final TextField annualizedCopiesField = new TextField(ForeignUi.getMessage("label.annualized_copies"));

    /**
     * Constructor.
     *
     * @param usageController  instance of {@link IUdmUsageController}
     * @param selectedUdmUsage UDM usage to be displayed on the window
     */
    public UdmEditUsageWindow(IUdmUsageController usageController, UdmUsageDto selectedUdmUsage) {
        controller = usageController;
        udmUsage = selectedUdmUsage;
        setContent(initRootLayout());
        setCaption(ForeignUi.getMessage("window.edit_udm_usage"));
        setResizable(false);
        setWidth(650, Unit.PIXELS);
        setHeight(700, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "edit-udm-usage-window");
    }

    private ComponentContainer initRootLayout() {
        VerticalLayout rootLayout = new VerticalLayout();
        VerticalLayout editFieldsLayout = new VerticalLayout();
        editFieldsLayout.addComponents(
            buildReadOnlyLayout("label.detail_id", UdmUsageDto::getId),
            buildReadOnlyLayout("label.period", usage -> Objects.toString(usage.getPeriod())),
            buildReadOnlyLayout("label.usage_origin", usage -> usage.getUsageOrigin().name()),
            buildReadOnlyLayout("label.usage_detail_id", UdmUsageDto::getOriginalDetailId),
            initDetailStatusLayout(),
            buildReadOnlyLayout("label.assignee", UdmUsageDto::getAssignee),
            buildReadOnlyLayout("label.rh_account_number",
                usage -> Objects.toString(usage.getRhAccountNumber(), StringUtils.EMPTY)),
            buildReadOnlyLayout("label.rh_name", UdmUsageDto::getRhName),
            buildWrWrkInstLayout(),
            buildEditableStringLayout(reportedTitleField, "label.reported_title", 1000, UdmUsageDto::getReportedTitle,
                UdmUsageDto::setReportedTitle),
            buildReadOnlyLayout("label.system_title", UdmUsageDto::getSystemTitle),
            buildEditableStringLayout(reportedStandardNumberField, "label.reported_standard_number", 100,
                UdmUsageDto::getReportedStandardNumber, UdmUsageDto::setReportedStandardNumber),
            buildReadOnlyLayout("label.standard_number", UdmUsageDto::getStandardNumber),
            buildEditableStringLayout(reportedPubTypeField, "label.reported_pub_type", 100,
                UdmUsageDto::getReportedPubType, UdmUsageDto::setReportedPubType),
            buildReadOnlyLayout("label.publication_format", UdmUsageDto::getPubFormat),
            buildReadOnlyLayout("label.article", UdmUsageDto::getArticle),
            buildReadOnlyLayout("label.language", UdmUsageDto::getLanguage),
            initActionReasonLayout(),
            buildEditableStringLayout(commentField, "label.comment", 4000, UdmUsageDto::getComment,
                UdmUsageDto::setComment),
            buildEditableStringLayout(researchUrlField, "label.research_url", 1000, UdmUsageDto::getResearchUrl,
                UdmUsageDto::setResearchUrl),
            initDetailLicenseeClassLayout(),
            buildCompanyLayout(),
            buildCompanyNameLayout(),
            buildReadOnlyLayout("label.survey_respondent", UdmUsageDto::getSurveyRespondent),
            buildReadOnlyLayout("label.ip_address", UdmUsageDto::getIpAddress),
            buildReadOnlyLayout("label.survey_country", UdmUsageDto::getSurveyCountry),
            buildReadOnlyLayout("label.channel", usage -> usage.getChannel().name()),
            buildReadOnlyLayout("label.usage_date", usage -> getStringFromLocalDate(usage.getUsageDate())),
            buildReadOnlyLayout("label.survey_start_date", usage -> getStringFromLocalDate(usage.getSurveyStartDate())),
            buildReadOnlyLayout("label.survey_end_date", usage -> getStringFromLocalDate(usage.getSurveyEndDate())),
            buildEditableIntegerLayout(annualMultiplierField, "label.annual_multiplier",
                UdmUsageDto::getAnnualMultiplier, UdmUsageDto::setAnnualMultiplier),
            buildEditableBigDecimalLayout(UdmUsageDto::getStatisticalMultiplier, UdmUsageDto::setStatisticalMultiplier),
            buildReadOnlyLayout("label.reported_tou", UdmUsageDto::getReportedTypeOfUse),
            buildEditableIntegerLayout(quantityField, "label.quantity", UdmUsageDto::getQuantity,
                UdmUsageDto::setQuantity),
            buildAnnualizedCopiesField(),
            initIneligibleReasonLayout(),
            buildReadOnlyLayout("label.load_date", usage -> getStringFromDate(usage.getCreateDate())),
            buildReadOnlyLayout("label.updated_by", UdmUsageDto::getUpdateUser),
            buildReadOnlyLayout("label.updated_date", usage -> getStringFromDate(usage.getUpdateDate())));
        Panel panel = new Panel(editFieldsLayout);
        panel.setSizeFull();
        editFieldsLayout.setMargin(new MarginInfo(true));
        HorizontalLayout buttonsLayout = initButtonsLayout();
        rootLayout.addComponents(panel, buttonsLayout);
        rootLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        rootLayout.setExpandRatio(panel, 1f);
        rootLayout.setSizeFull();
        panel.setStyleName(Cornerstone.FORMLAYOUT_LIGHT);
        binder.validate();
        binder.readBean(udmUsage);
        return rootLayout;
    }

    private HorizontalLayout buildReadOnlyLayout(String caption, ValueProvider<UdmUsageDto, String> getter) {
        TextField textField = new TextField();
        textField.setReadOnly(true);
        textField.setSizeFull();
        binder.forField(textField).bind(getter, null);
        return buildCommonLayout(textField, caption);
    }

    private HorizontalLayout buildEditableStringLayout(TextField textField, String caption, int maxLength,
                                                       ValueProvider<UdmUsageDto, String> getter,
                                                       Setter<UdmUsageDto, String> setter) {
        textField.setSizeFull();
        binder.forField(textField)
            .withValidator(
                new StringLengthValidator(ForeignUi.getMessage("field.error.length", maxLength), 0, maxLength))
            .bind(getter, setter);
        return buildCommonLayout(textField, caption);
    }

    private HorizontalLayout buildEditableBigDecimalLayout(ValueProvider<UdmUsageDto, BigDecimal> getter,
                                                           Setter<UdmUsageDto, BigDecimal> setter) {
        statisticalMultiplierField.setSizeFull();
        binder.forField(statisticalMultiplierField)
            .withValidator(StringUtils::isNotBlank, ForeignUi.getMessage(EMPTY_FIELD_MESSAGE))
            .withValidator(value -> new AmountValidator().isValid(value.trim()),
                "Field value should be positive number and should not exceed 10 digits")
            .withConverter(new StringToBigDecimalConverter("Field should be numeric"))
            .bind(getter, setter);
        statisticalMultiplierField.addValueChangeListener(event -> recalculateAnnualizedCopies());
        return buildCommonLayout(statisticalMultiplierField, "label.statistical_multiplier");
    }

    private HorizontalLayout buildEditableIntegerLayout(TextField textField, String caption,
                                                        ValueProvider<UdmUsageDto, Integer> getter,
                                                        Setter<UdmUsageDto, Integer> setter) {
        textField.setSizeFull();
        binder.forField(textField)
            .withValidator(StringUtils::isNotBlank, ForeignUi.getMessage(EMPTY_FIELD_MESSAGE))
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.number_length", 9), 0, 9))
            .withValidator(value -> StringUtils.isNumeric(value.trim()), NUMBER_VALIDATION_MESSAGE)
            .withConverter(new StringToIntegerConverter("Field should be numeric"))
            .bind(getter, setter);
        textField.addValueChangeListener(event -> recalculateAnnualizedCopies());
        return buildCommonLayout(textField, caption);
    }

    private HorizontalLayout buildAnnualizedCopiesField() {
        annualizedCopiesField.setReadOnly(true);
        annualizedCopiesField.setSizeFull();
        binder.forField(annualizedCopiesField)
            .withValidator(StringUtils::isNotBlank, ForeignUi.getMessage("field.error.annualized_copies.empty"))
            .withConverter(new StringToBigDecimalConverter("Field should be numeric"))
            .bind(UdmUsageDto::getAnnualizedCopies, UdmUsageDto::setAnnualizedCopies);
        return buildCommonLayout(annualizedCopiesField, "label.annualized_copies");
    }

    private HorizontalLayout buildWrWrkInstLayout() {
        wrWrkInstField.setSizeFull();
        binder.forField(wrWrkInstField)
            .withValidator(value -> StringUtils.isEmpty(value) || StringUtils.isNumeric(value.trim()),
                NUMBER_VALIDATION_MESSAGE)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.number_length", 9), 0, 9))
            .bind(usage -> Objects.toString(usage.getWrWrkInst(), StringUtils.EMPTY),
                (usage, value) -> usage.setWrWrkInst(NumberUtils.createLong(value.trim())));
        return buildCommonLayout(wrWrkInstField, "label.wr_wrk_inst");
    }

    private HorizontalLayout buildCompanyLayout() {
        binder.forField(companyIdField)
            .withValidator(StringUtils::isNotBlank, ForeignUi.getMessage(EMPTY_FIELD_MESSAGE))
            .withValidator(value -> StringUtils.isNumeric(value.trim()), NUMBER_VALIDATION_MESSAGE)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.number_length", 10), 0, 10))
            .bind(usage -> Objects.toString(usage.getCompanyId(), StringUtils.EMPTY),
                (usage, value) -> usage.setCompanyId(NumberUtils.createLong(value.trim())));
        companyIdField.setSizeFull();
        companyIdField.addValueChangeListener(event -> companyNameField.clear());
        Button verifyButton = Buttons.createButton(ForeignUi.getMessage("button.verify"));
        verifyButton.addClickListener(event -> {
            if (Objects.isNull(companyIdField.getErrorMessage())) {
                CompanyInformation information =
                    controller.getCompanyInformation(Long.valueOf(companyIdField.getValue().trim()));
                if (StringUtils.isNotBlank(information.getName())) {
                    companyNameField.setValue(information.getName());
                } else {
                    companyNameField.clear();
                }
            }
        });
        HorizontalLayout layout = buildCommonLayout(companyIdField, "label.company_id");
        layout.addComponent(verifyButton);
        return layout;
    }

    private HorizontalLayout buildCompanyNameLayout() {
        companyNameField.setReadOnly(true);
        companyNameField.setSizeFull();
        binder.forField(companyNameField)
            .withValidator(StringUtils::isNotBlank, ForeignUi.getMessage("field.error.company_name.empty"))
            .bind(UdmUsageDto::getCompanyName, UdmUsageDto::setCompanyName);
        return buildCommonLayout(companyNameField, "label.company_name");
    }

    private HorizontalLayout initIneligibleReasonLayout() {
        ComboBox<UdmIneligibleReason> comboBox = new ComboBox<>();
        comboBox.setSizeFull();
        comboBox.setItemCaptionGenerator(UdmIneligibleReason::getText);
        comboBox.setItems(controller.getIneligibleReasons());
        binder.forField(comboBox).bind(UdmUsageDto::getIneligibleReason, UdmUsageDto::setIneligibleReason);
        return buildCommonLayout(comboBox, "label.ineligible_reason");
    }

    private HorizontalLayout initDetailStatusLayout() {
        ComboBox<UsageStatusEnum> comboBox = new ComboBox<>();
        comboBox.setSizeFull();
        comboBox.setEmptySelectionAllowed(false);
        LinkedHashSet<UsageStatusEnum> statuses = new LinkedHashSet<>();
        statuses.add(udmUsage.getStatus());
        statuses.addAll(EDIT_AVAILABLE_STATUSES);
        comboBox.setItems(statuses);
        binder.forField(comboBox).bind(UdmUsageDto::getStatus, UdmUsageDto::setStatus);
        return buildCommonLayout(comboBox, "label.detail_status");
    }

    private HorizontalLayout initActionReasonLayout() {
        ComboBox<UdmActionReason> comboBox = new ComboBox<>();
        comboBox.setSizeFull();
        comboBox.setItemCaptionGenerator(UdmActionReason::getText);
        comboBox.setItems(controller.getActionReasons());
        binder.forField(comboBox).bind(UdmUsageDto::getActionReason, UdmUsageDto::setActionReason);
        return buildCommonLayout(comboBox, "label.action_reason_udm");
    }

    private HorizontalLayout initDetailLicenseeClassLayout() {
        ComboBox<DetailLicenseeClass> comboBox = new ComboBox<>();
        comboBox.setSizeFull();
        comboBox.setItemCaptionGenerator(detailLicenseeClass ->
            String.format("%s - %s", detailLicenseeClass.getId(), detailLicenseeClass.getDescription()));
        comboBox.setItems(controller.getDetailLicenseeClasses());
        binder.forField(comboBox).bind(UdmUsageDto::getDetailLicenseeClass, UdmUsageDto::setDetailLicenseeClass);
        return buildCommonLayout(comboBox, "label.det_lc");
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
        Button saveButton = Buttons.createButton(ForeignUi.getMessage("button.save"));
        saveButton.addClickListener(event -> {
            try {
                binder.writeBean(udmUsage);
                controller.updateUsage(udmUsage);
                close();
            } catch (ValidationException e) {
                Windows.showValidationErrorWindow(Arrays.asList(wrWrkInstField, reportedTitleField,
                    reportedStandardNumberField, reportedPubTypeField, commentField, researchUrlField, companyIdField,
                    companyNameField, annualMultiplierField, statisticalMultiplierField, quantityField,
                    annualizedCopiesField));
            }
        });
        Button discardButton = Buttons.createButton(ForeignUi.getMessage("button.discard"));
        discardButton.addClickListener(event -> binder.readBean(udmUsage));
        return new HorizontalLayout(saveButton, discardButton, closeButton);
    }

    private void recalculateAnnualizedCopies() {
        if (Objects.isNull(quantityField.getErrorMessage())
            && Objects.isNull(annualMultiplierField.getErrorMessage())
            && Objects.isNull(statisticalMultiplierField.getErrorMessage())) {
            annualizedCopiesField.setValue(controller.calculateAnnualizedCopies(udmUsage.getReportedTypeOfUse(),
                NumberUtils.toInt(quantityField.getValue().trim()),
                NumberUtils.toInt(annualMultiplierField.getValue().trim()),
                NumberUtils.createBigDecimal(statisticalMultiplierField.getValue().trim())).toString());
        } else {
            annualizedCopiesField.clear();
        }
    }

    //TODO introduce Utils class to convert dates to String for all UI components
    private String getStringFromLocalDate(LocalDate date) {
        return CommonDateUtils.format(date, RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT);
    }

    private String getStringFromDate(Date date) {
        return Objects.nonNull(date)
            ? FastDateFormat.getInstance(RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT).format(date)
            : StringUtils.EMPTY;
    }
}
