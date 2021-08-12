package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.CompanyInformation;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.UdmActionReason;
import com.copyright.rup.dist.foreign.domain.UdmIneligibleReason;
import com.copyright.rup.dist.foreign.domain.UdmUsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
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
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
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
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    private static final Range<BigDecimal> STATISTICAL_MULTIPLIER_RANGE =
        Range.closed(new BigDecimal("0.00001"), BigDecimal.ONE);
    private static final Range<Integer> ANNUAL_MULTIPLIER_RANGE = Range.closed(1, 25);
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
    private final ComboBox<DetailLicenseeClass> detailLicenseeClassComboBox =
        new ComboBox<>(ForeignUi.getMessage("label.det_lc"));
    private final Map<Integer, DetailLicenseeClass> idToLicenseeClassMap;
    private final Button saveButton = Buttons.createButton(ForeignUi.getMessage("button.save"));
    private final ClickListener saveButtonClickListener;

    /**
     * Constructor.
     *
     * @param usageController  instance of {@link IUdmUsageController}
     * @param selectedUdmUsage UDM usage to be displayed on the window
     * @param clickListener    action that should be performed after Save button was clicked
     */
    public UdmEditUsageWindow(IUdmUsageController usageController, UdmUsageDto selectedUdmUsage,
                              ClickListener clickListener) {
        controller = usageController;
        udmUsage = selectedUdmUsage;
        saveButtonClickListener = clickListener;
        idToLicenseeClassMap = controller.getDetailLicenseeClasses()
            .stream()
            .collect(Collectors.toMap(DetailLicenseeClass::getId, Function.identity()));
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
        editFieldsLayout.addComponents(ForeignSecurityUtils.hasResearcherPermission()
            ? getComponentsForResearcher() : getComponentsForSpecialistAndManager());
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
        binder.addValueChangeListener(event -> saveButton.setEnabled(binder.hasChanges()));
        return rootLayout;
    }

    private Component[] getComponentsForResearcher() {
        return new Component[]{
            buildReadOnlyLayout("label.detail_id", UdmUsageDto::getId),
            buildReadOnlyLayout("label.period", usage -> Objects.toString(usage.getPeriod())),
            buildReadOnlyLayout("label.usage_detail_id", UdmUsageDto::getOriginalDetailId),
            initDetailStatusLayout(),
            buildReadOnlyLayout("label.assignee", UdmUsageDto::getAssignee),
            buildReadOnlyLayout("label.rh_account_number",
                usage -> Objects.toString(usage.getRhAccountNumber(), StringUtils.EMPTY)),
            buildReadOnlyLayout("label.rh_name", UdmUsageDto::getRhName),
            buildWrWrkInstLayout(),
            buildReadOnlyLayout("label.reported_title", UdmUsageDto::getReportedTitle),
            buildReadOnlyLayout("label.system_title", UdmUsageDto::getSystemTitle),
            buildReadOnlyLayout("label.reported_standard_number", UdmUsageDto::getReportedStandardNumber),
            buildReadOnlyLayout("label.standard_number", UdmUsageDto::getStandardNumber),
            buildReadOnlyLayout("label.reported_pub_type", UdmUsageDto::getReportedPubType),
            buildReadOnlyLayout("label.publication_format", UdmUsageDto::getPubFormat),
            buildReadOnlyLayout("label.article", UdmUsageDto::getArticle),
            buildReadOnlyLayout("label.language", UdmUsageDto::getLanguage),
            initActionReasonLayout(),
            buildEditableStringLayout(commentField, "label.comment", 4000, UdmUsageDto::getComment,
                UdmUsageDto::setComment, "udm-edit-comment-field"),
            buildEditableStringLayout(researchUrlField, "label.research_url", 1000, UdmUsageDto::getResearchUrl,
                UdmUsageDto::setResearchUrl, "udm-edit-research-url-field"),
            buildReadOnlyLayout("label.det_lc", usage -> String.format("%s - %s",
                usage.getDetailLicenseeClass().getId(), usage.getDetailLicenseeClass().getDescription())),
            buildReadOnlyLayout("label.channel", usage -> usage.getChannel().name()),
            buildReadOnlyLayout("label.usage_date", usage -> getStringFromLocalDate(usage.getUsageDate())),
            buildReadOnlyLayout("label.survey_start_date", usage -> getStringFromLocalDate(usage.getSurveyStartDate())),
            buildReadOnlyLayout("label.survey_end_date", usage -> getStringFromLocalDate(usage.getSurveyEndDate())),
            buildReadOnlyLayout("label.reported_tou", UdmUsageDto::getReportedTypeOfUse),
            buildReadOnlyLayout("label.ineligible_reason", usage -> Objects.nonNull(usage.getIneligibleReason())
                ? usage.getIneligibleReason().getReason() : StringUtils.EMPTY),
            buildReadOnlyLayout("label.load_date", usage -> getStringFromDate(usage.getCreateDate())),
            buildReadOnlyLayout("label.updated_by", UdmUsageDto::getUpdateUser),
            buildReadOnlyLayout("label.updated_date", usage -> getStringFromDate(usage.getUpdateDate()))
        };
    }

    private Component[] getComponentsForSpecialistAndManager() {
        return new Component[]{
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
                UdmUsageDto::setReportedTitle, "udm-edit-reported-title-field"),
            buildReadOnlyLayout("label.system_title", UdmUsageDto::getSystemTitle),
            buildEditableStringLayout(reportedStandardNumberField, "label.reported_standard_number", 100,
                UdmUsageDto::getReportedStandardNumber, UdmUsageDto::setReportedStandardNumber,
                "udm-edit-reported-standard-number-field"),
            buildReadOnlyLayout("label.standard_number", UdmUsageDto::getStandardNumber),
            buildEditableStringLayout(reportedPubTypeField, "label.reported_pub_type", 100,
                UdmUsageDto::getReportedPubType, UdmUsageDto::setReportedPubType, "udm-edit-reported-pub-type-field"),
            buildReadOnlyLayout("label.publication_format", UdmUsageDto::getPubFormat),
            buildReadOnlyLayout("label.article", UdmUsageDto::getArticle),
            buildReadOnlyLayout("label.language", UdmUsageDto::getLanguage),
            initActionReasonLayout(),
            buildEditableStringLayout(commentField, "label.comment", 4000, UdmUsageDto::getComment,
                UdmUsageDto::setComment, "udm-edit-comment-field"),
            buildEditableStringLayout(researchUrlField, "label.research_url", 1000, UdmUsageDto::getResearchUrl,
                UdmUsageDto::setResearchUrl, "udm-edit-research-url-field"),
            buildCompanyLayout(),
            buildCompanyNameLayout(),
            initDetailLicenseeClassLayout(),
            buildReadOnlyLayout("label.survey_respondent", UdmUsageDto::getSurveyRespondent),
            buildReadOnlyLayout("label.ip_address", UdmUsageDto::getIpAddress),
            buildReadOnlyLayout("label.survey_country", UdmUsageDto::getSurveyCountry),
            buildReadOnlyLayout("label.channel", usage -> usage.getChannel().name()),
            buildReadOnlyLayout("label.usage_date", usage -> getStringFromLocalDate(usage.getUsageDate())),
            buildReadOnlyLayout("label.survey_start_date", usage -> getStringFromLocalDate(usage.getSurveyStartDate())),
            buildReadOnlyLayout("label.survey_end_date", usage -> getStringFromLocalDate(usage.getSurveyEndDate())),
            buildAnnualMultiplierLayout(),
            buildStatisticalMultiplier(),
            buildReadOnlyLayout("label.reported_tou", UdmUsageDto::getReportedTypeOfUse),
            buildQuantityLayout(),
            buildAnnualizedCopiesField(),
            initIneligibleReasonLayout(),
            buildReadOnlyLayout("label.load_date", usage -> getStringFromDate(usage.getCreateDate())),
            buildReadOnlyLayout("label.updated_by", UdmUsageDto::getUpdateUser),
            buildReadOnlyLayout("label.updated_date", usage -> getStringFromDate(usage.getUpdateDate()))
        };
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
                                                       Setter<UdmUsageDto, String> setter, String styleName) {
        textField.setSizeFull();
        binder.forField(textField)
            .withValidator(
                new StringLengthValidator(ForeignUi.getMessage("field.error.length", maxLength), 0, maxLength))
            .bind(getter, setter);
        VaadinUtils.addComponentStyle(textField, styleName);
        return buildCommonLayout(textField, caption);
    }

    private HorizontalLayout buildStatisticalMultiplier() {
        statisticalMultiplierField.setSizeFull();
        binder.forField(statisticalMultiplierField)
            .withValidator(StringUtils::isNotBlank, ForeignUi.getMessage(EMPTY_FIELD_MESSAGE))
            .withValidator(value -> NumberUtils.isNumber(value.trim()) &&
                    STATISTICAL_MULTIPLIER_RANGE.contains(NumberUtils.createBigDecimal(value.trim())),
                "Field value should be positive number between 0.00001 and 1.00000")
            .bind(usage -> Objects.toString(usage.getStatisticalMultiplier()),
                (usage, value) -> usage.setStatisticalMultiplier(NumberUtils.createBigDecimal(value.trim())));
        statisticalMultiplierField.addValueChangeListener(event -> recalculateAnnualizedCopies());
        VaadinUtils.addComponentStyle(statisticalMultiplierField, "udm-edit-statistical-multiplier-field");
        return buildCommonLayout(statisticalMultiplierField, "label.statistical_multiplier");
    }

    private HorizontalLayout buildAnnualMultiplierLayout() {
        annualMultiplierField.setSizeFull();
        binder.forField(annualMultiplierField)
            .withValidator(StringUtils::isNotBlank, ForeignUi.getMessage(EMPTY_FIELD_MESSAGE))
            .withValidator(value -> StringUtils.isNumeric(value.trim())
                    && ANNUAL_MULTIPLIER_RANGE.contains(NumberUtils.toInt(value.trim())),
                "Field value should be positive number between 1 and 25")
            .bind(usage -> usage.getAnnualMultiplier().toString(),
                (usage, value) -> usage.setAnnualMultiplier(NumberUtils.toInt(value.trim())));
        annualMultiplierField.addValueChangeListener(event -> recalculateAnnualizedCopies());
        VaadinUtils.addComponentStyle(annualMultiplierField, "udm-edit-annual-multiplier-field");
        return buildCommonLayout(annualMultiplierField, "label.annual_multiplier");
    }

    private HorizontalLayout buildQuantityLayout() {
        quantityField.setSizeFull();
        binder.forField(quantityField)
            .withValidator(StringUtils::isNotBlank, ForeignUi.getMessage(EMPTY_FIELD_MESSAGE))
            .withValidator(
                new StringLengthValidator(ForeignUi.getMessage("field.error.number_length", 9), 0, 9))
            .withValidator(value -> StringUtils.isNumeric(StringUtils.trim(value))
                && Integer.parseInt(StringUtils.trim(value)) > 0, NUMBER_VALIDATION_MESSAGE)
            .bind(usage -> usage.getQuantity().toString(),
                (usage, value) -> usage.setQuantity(NumberUtils.toLong(value.trim())));
        quantityField.addValueChangeListener(event -> recalculateAnnualizedCopies());
        VaadinUtils.addComponentStyle(quantityField, "udm-edit-quantity-field");
        return buildCommonLayout(quantityField, "label.quantity");
    }

    private HorizontalLayout buildAnnualizedCopiesField() {
        annualizedCopiesField.setReadOnly(true);
        annualizedCopiesField.setSizeFull();
        binder.forField(annualizedCopiesField)
            .withValidator(StringUtils::isNotBlank, ForeignUi.getMessage("field.error.annualized_copies.empty"))
            .bind(usage -> Objects.toString(usage.getAnnualizedCopies()),
                (usage, value) -> usage.setAnnualizedCopies(NumberUtils.createBigDecimal(value.trim())));
        return buildCommonLayout(annualizedCopiesField, "label.annualized_copies");
    }

    private HorizontalLayout buildWrWrkInstLayout() {
        wrWrkInstField.setSizeFull();
        binder.forField(wrWrkInstField)
            .withValidator(value -> StringUtils.isEmpty(value) || StringUtils.isNumeric(value.trim()),
                NUMBER_VALIDATION_MESSAGE)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.number_length", 9), 0, 9))
            .bind(usage -> Objects.toString(usage.getWrWrkInst(), StringUtils.EMPTY),
                (usage, value) -> usage.setWrWrkInst(NumberUtils.createLong(StringUtils.trimToNull(value))));
        VaadinUtils.addComponentStyle(wrWrkInstField, "udm-edit-wr-wrk-inst-field");
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
        companyIdField.addValueChangeListener(event -> {
            companyNameField.clear();
            detailLicenseeClassComboBox.setSelectedItem(null);
        });
        Button verifyButton = Buttons.createButton(ForeignUi.getMessage("button.verify"));
        verifyButton.addClickListener(event -> {
            if (Objects.isNull(companyIdField.getErrorMessage())) {
                CompanyInformation information =
                    controller.getCompanyInformation(Long.valueOf(companyIdField.getValue().trim()));
                if (StringUtils.isNotBlank(information.getName())) {
                    companyNameField.setValue(information.getName());
                    detailLicenseeClassComboBox.setSelectedItem(
                        idToLicenseeClassMap.get(information.getDetailLicenseeClassId()));
                } else {
                    companyNameField.clear();
                    detailLicenseeClassComboBox.setSelectedItem(null);
                }
            }
        });
        VaadinUtils.addComponentStyle(companyIdField, "udm-edit-company-id-field");
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
        comboBox.setItemCaptionGenerator(UdmIneligibleReason::getReason);
        comboBox.setItems(controller.getAllIneligibleReasons());
        binder.forField(comboBox).bind(UdmUsageDto::getIneligibleReason, UdmUsageDto::setIneligibleReason);
        VaadinUtils.addComponentStyle(comboBox, "udm-edit-ineligible-reason-combo-box");
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
        VaadinUtils.addComponentStyle(comboBox, "udm-edit-detail-status-combo-box");
        return buildCommonLayout(comboBox, "label.detail_status");
    }

    private HorizontalLayout initActionReasonLayout() {
        ComboBox<UdmActionReason> comboBox = new ComboBox<>();
        comboBox.setSizeFull();
        comboBox.setItemCaptionGenerator(UdmActionReason::getReason);
        comboBox.setItems(controller.getAllActionReasons());
        binder.forField(comboBox).bind(UdmUsageDto::getActionReason, UdmUsageDto::setActionReason);
        VaadinUtils.addComponentStyle(comboBox, "udm-edit-action-reason-combo-box");
        return buildCommonLayout(comboBox, "label.action_reason_udm");
    }

    private HorizontalLayout initDetailLicenseeClassLayout() {
        detailLicenseeClassComboBox.setSizeFull();
        detailLicenseeClassComboBox.setEmptySelectionAllowed(false);
        detailLicenseeClassComboBox.setItemCaptionGenerator(detailLicenseeClass ->
            String.format("%s - %s", detailLicenseeClass.getId(), detailLicenseeClass.getDescription()));
        detailLicenseeClassComboBox.setItems(idToLicenseeClassMap.values());
        binder.forField(detailLicenseeClassComboBox)
            .withValidator(Objects::nonNull, ForeignUi.getMessage(EMPTY_FIELD_MESSAGE))
            .bind(UdmUsageDto::getDetailLicenseeClass, UdmUsageDto::setDetailLicenseeClass);
        VaadinUtils.addComponentStyle(detailLicenseeClassComboBox, "udm-edit-detail-licensee-class-combo-box");
        return buildCommonLayout(detailLicenseeClassComboBox, "label.det_lc");
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
                binder.writeBean(udmUsage);
                controller.updateUsage(udmUsage);
                saveButtonClickListener.buttonClick(event);
                close();
            } catch (ValidationException e) {
                Windows.showValidationErrorWindow(Arrays.asList(wrWrkInstField, reportedTitleField,
                    reportedStandardNumberField, reportedPubTypeField, commentField, researchUrlField,
                    companyIdField, companyNameField, detailLicenseeClassComboBox, annualMultiplierField,
                    statisticalMultiplierField, quantityField, annualizedCopiesField));
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
                NumberUtils.toLong(quantityField.getValue().trim()),
                NumberUtils.toInt(annualMultiplierField.getValue().trim()),
                NumberUtils.createBigDecimal(
                    statisticalMultiplierField.getValue().trim()).setScale(5, RoundingMode.HALF_UP)).toString());
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
