package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.usage;

import com.copyright.rup.dist.foreign.domain.CompanyInformation;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.UdmActionReason;
import com.copyright.rup.dist.foreign.domain.UdmIneligibleReason;
import com.copyright.rup.dist.foreign.domain.UdmUsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.ui.audit.impl.UdmUsageAuditFieldToValuesMap;
import com.copyright.rup.dist.foreign.ui.common.utils.IDateFormatter;
import com.copyright.rup.dist.foreign.ui.common.validator.RequiredValidator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageController;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.CommonUdmUsageWindow;
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
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
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
public class UdmEditUsageWindow extends CommonUdmUsageWindow implements IDateFormatter {

    private static final Range<BigDecimal> STATISTICAL_MULTIPLIER_RANGE =
        Range.closed(new BigDecimal("0.00001"), BigDecimal.ONE);
    private static final Range<Integer> STATISTICAL_MULTIPLIER_SCALE_RANGE = Range.closed(0, 5);
    private static final Range<Integer> ANNUAL_MULTIPLIER_RANGE = Range.closed(1, 25);
    private static final List<UsageStatusEnum> EDIT_AVAILABLE_STATUSES_RESEARCHER =
        List.of(UsageStatusEnum.OPS_REVIEW, UsageStatusEnum.SPECIALIST_REVIEW, UsageStatusEnum.NEW);
    private static final List<UsageStatusEnum> EDIT_AVAILABLE_STATUSES_SPECIALIST_AND_MANAGER =
        List.of(UsageStatusEnum.NEW, UsageStatusEnum.ELIGIBLE, UsageStatusEnum.INELIGIBLE,
            UsageStatusEnum.OPS_REVIEW, UsageStatusEnum.SPECIALIST_REVIEW);
    private static final String NUMBER_VALIDATION_MESSAGE = ForeignUi.getMessage("field.error.not_numeric");
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
    private final ComboBox<UsageStatusEnum> usageStatusComboBox =
        new ComboBox<>(ForeignUi.getMessage("label.detail_status"));
    private final ComboBox<UdmIneligibleReason> ineligibleReasonComboBox =
        new ComboBox<>(ForeignUi.getMessage("label.ineligible_reason"));
    private final Map<Integer, DetailLicenseeClass> idToLicenseeClassMap;
    private final Button saveButton = Buttons.createButton(ForeignUi.getMessage("button.save"));
    private final ClickListener saveButtonClickListener;
    private final boolean hasResearcherPermission = ForeignSecurityUtils.hasResearcherPermission();
    private final UdmUsageAuditFieldToValuesMap fieldToValueChangesMap;
    private UdmUsageFieldsForStatusValidation udmUsageFieldsBeforeChanges;

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
        fieldToValueChangesMap = new UdmUsageAuditFieldToValuesMap(udmUsage);
        saveButtonClickListener = clickListener;
        idToLicenseeClassMap = controller.getIdsToDetailLicenseeClasses();
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
        editFieldsLayout.addComponents(hasResearcherPermission
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
        binder.readBean(udmUsage);
        binder.validate();
        binder.addValueChangeListener(event -> saveButton.setEnabled(binder.hasChanges()));
        return rootLayout;
    }

    private Component[] getComponentsForResearcher() {
        udmUsageFieldsBeforeChanges = new UdmUsageFieldsForStatusValidation(udmUsage.getWrWrkInst());
        return new Component[]{
            buildReadOnlyLayout("label.detail_id", UdmUsageDto::getId, binder),
            buildReadOnlyLayout("label.period", usage -> Objects.toString(usage.getPeriod()), binder),
            buildReadOnlyLayout("label.usage_detail_id", UdmUsageDto::getOriginalDetailId, binder),
            initDetailStatusLayout(),
            buildReadOnlyLayout("label.assignee", UdmUsageDto::getAssignee, binder),
            buildReadOnlyLayout("label.rh_account_number",
                usage -> Objects.toString(usage.getRhAccountNumber(), StringUtils.EMPTY), binder),
            buildReadOnlyLayout("label.rh_name", UdmUsageDto::getRhName, binder),
            buildWrWrkInstLayout(),
            buildReadOnlyLayout("label.reported_title", UdmUsageDto::getReportedTitle, binder),
            buildReadOnlyLayout("label.system_title", UdmUsageDto::getSystemTitle, binder),
            buildReadOnlyLayout("label.reported_standard_number", UdmUsageDto::getReportedStandardNumber, binder),
            buildReadOnlyLayout("label.standard_number", UdmUsageDto::getStandardNumber, binder),
            buildReadOnlyLayout("label.reported_pub_type", UdmUsageDto::getReportedPubType, binder),
            buildReadOnlyLayout("label.publication_format", UdmUsageDto::getPubFormat, binder),
            buildReadOnlyLayout("label.article", UdmUsageDto::getArticle, binder),
            buildReadOnlyLayout("label.language", UdmUsageDto::getLanguage, binder),
            initActionReasonLayout(),
            buildEditableStringLayout(commentField, "label.comment", 4000, UdmUsageDto::getComment,
                UdmUsageDto::setComment, "udm-edit-comment-field"),
            buildEditableStringLayout(researchUrlField, "label.research_url", 1000, UdmUsageDto::getResearchUrl,
                UdmUsageDto::setResearchUrl, "udm-edit-research-url-field"),
            buildReadOnlyLayout("label.det_lc", buildDetailLicenseeClassValueProvider(), binder),
            buildReadOnlyLayout("label.channel", usage -> usage.getChannel().name(), binder),
            buildReadOnlyLayout("label.usage_date", toShortFormat(UdmUsageDto::getUsageDate), binder),
            buildReadOnlyLayout("label.survey_start_date", toShortFormat(UdmUsageDto::getSurveyStartDate), binder),
            buildReadOnlyLayout("label.survey_end_date", toShortFormat(UdmUsageDto::getSurveyEndDate), binder),
            buildReadOnlyLayout("label.reported_tou", UdmUsageDto::getReportedTypeOfUse, binder),
            buildReadOnlyLayout("label.ineligible_reason", buildIneligibleReasonValueProvider(), binder),
            buildReadOnlyLayout("label.load_date", toShortFormat(UdmUsageDto::getCreateDate), binder),
            buildReadOnlyLayout("label.updated_by", UdmUsageDto::getUpdateUser, binder),
            buildReadOnlyLayout("label.updated_date", toShortFormat(UdmUsageDto::getUpdateDate), binder)
        };
    }

    private Component[] getComponentsForSpecialistAndManager() {
        udmUsageFieldsBeforeChanges = new UdmUsageFieldsForStatusValidation(udmUsage.getWrWrkInst(),
            udmUsage.getReportedTitle(), udmUsage.getReportedStandardNumber());
        return new Component[]{
            buildReadOnlyLayout("label.detail_id", UdmUsageDto::getId, binder),
            buildReadOnlyLayout("label.period", usage -> Objects.toString(usage.getPeriod()), binder),
            buildReadOnlyLayout("label.usage_origin", usage -> usage.getUsageOrigin().name(), binder),
            buildReadOnlyLayout("label.usage_detail_id", UdmUsageDto::getOriginalDetailId, binder),
            initDetailStatusLayout(),
            buildReadOnlyLayout("label.assignee", UdmUsageDto::getAssignee, binder),
            buildReadOnlyLayout("label.rh_account_number",
                usage -> Objects.toString(usage.getRhAccountNumber(), StringUtils.EMPTY), binder),
            buildReadOnlyLayout("label.rh_name", UdmUsageDto::getRhName, binder),
            buildWrWrkInstLayout(),
            buildEditableStringLayoutWithValidation(reportedTitleField, "label.reported_title", 1000,
                UdmUsageDto::getReportedTitle,
                UdmUsageDto::setReportedTitle, "udm-edit-reported-title-field"),
            buildReadOnlyLayout("label.system_title", UdmUsageDto::getSystemTitle, binder),
            buildEditableStringLayoutWithValidation(reportedStandardNumberField, "label.reported_standard_number", 100,
                UdmUsageDto::getReportedStandardNumber, UdmUsageDto::setReportedStandardNumber,
                "udm-edit-reported-standard-number-field"),
            buildReadOnlyLayout("label.standard_number", UdmUsageDto::getStandardNumber, binder),
            buildEditableStringLayout(reportedPubTypeField, "label.reported_pub_type", 100,
                UdmUsageDto::getReportedPubType, UdmUsageDto::setReportedPubType, "udm-edit-reported-pub-type-field"),
            buildReadOnlyLayout("label.publication_format", UdmUsageDto::getPubFormat, binder),
            buildReadOnlyLayout("label.article", UdmUsageDto::getArticle, binder),
            buildReadOnlyLayout("label.language", UdmUsageDto::getLanguage, binder),
            initActionReasonLayout(),
            buildEditableStringLayout(commentField, "label.comment", 4000, UdmUsageDto::getComment,
                UdmUsageDto::setComment, "udm-edit-comment-field"),
            buildEditableStringLayout(researchUrlField, "label.research_url", 1000, UdmUsageDto::getResearchUrl,
                UdmUsageDto::setResearchUrl, "udm-edit-research-url-field"),
            buildCompanyLayout(),
            buildCompanyNameLayout(),
            initDetailLicenseeClassLayout(),
            buildReadOnlyLayout("label.survey_respondent", UdmUsageDto::getSurveyRespondent, binder),
            buildReadOnlyLayout("label.ip_address", UdmUsageDto::getIpAddress, binder),
            buildReadOnlyLayout("label.survey_country", UdmUsageDto::getSurveyCountry, binder),
            buildReadOnlyLayout("label.channel", usage -> usage.getChannel().name(), binder),
            buildReadOnlyLayout("label.usage_date", toShortFormat(UdmUsageDto::getUsageDate), binder),
            buildReadOnlyLayout("label.survey_start_date", toShortFormat(UdmUsageDto::getSurveyStartDate), binder),
            buildReadOnlyLayout("label.survey_end_date", toShortFormat(UdmUsageDto::getSurveyEndDate), binder),
            buildAnnualMultiplierLayout(),
            buildStatisticalMultiplier(),
            buildReadOnlyLayout("label.reported_tou", UdmUsageDto::getReportedTypeOfUse, binder),
            buildQuantityLayout(),
            buildAnnualizedCopiesField(),
            initIneligibleReasonLayout(),
            buildReadOnlyLayout("label.load_date", toShortFormat(UdmUsageDto::getCreateDate), binder),
            buildReadOnlyLayout("label.updated_by", UdmUsageDto::getUpdateUser, binder),
            buildReadOnlyLayout("label.updated_date", toShortFormat(UdmUsageDto::getUpdateDate), binder)
        };
    }

    private HorizontalLayout buildEditableStringLayout(TextField textField, String caption, int maxLength,
                                                       ValueProvider<UdmUsageDto, String> getter,
                                                       Setter<UdmUsageDto, String> setter, String styleName) {
        String fieldName = ForeignUi.getMessage(caption);
        textField.setSizeFull();
        binder.forField(textField)
            .withValidator(
                new StringLengthValidator(ForeignUi.getMessage("field.error.length", maxLength), 0, maxLength))
            .bind(getter, (usage, fieldValue) -> setter.accept(usage, StringUtils.trimToNull(fieldValue)));
        textField.addValueChangeListener(event ->
            fieldToValueChangesMap.updateFieldValue(fieldName, event.getValue().trim()));
        VaadinUtils.addComponentStyle(textField, styleName);
        return buildCommonLayout(textField, fieldName);
    }

    private HorizontalLayout buildEditableStringLayoutWithValidation(TextField textField, String caption, int maxLength,
                                                                     ValueProvider<UdmUsageDto, String> getter,
                                                                     Setter<UdmUsageDto, String> setter,
                                                                     String styleName) {
        textField.addValueChangeListener(event -> binder.validate());
        return buildEditableStringLayout(textField, caption, maxLength, getter, setter, styleName);
    }

    private HorizontalLayout buildStatisticalMultiplier() {
        String fieldName = ForeignUi.getMessage("label.statistical_multiplier");
        statisticalMultiplierField.setSizeFull();
        binder.forField(statisticalMultiplierField)
            .withValidator(new RequiredValidator())
            .withValidator(value -> NumberUtils.isNumber(value.trim()) &&
                    STATISTICAL_MULTIPLIER_RANGE.contains(NumberUtils.createBigDecimal(value.trim())),
                ForeignUi.getMessage("field.error.positive_number_between", "0.00001", "1.00000"))
            .withValidator(value ->
                    STATISTICAL_MULTIPLIER_SCALE_RANGE.contains(NumberUtils.createBigDecimal(value.trim()).scale()),
                ForeignUi.getMessage("field.error.number_scale", 5))
            .bind(usage -> Objects.toString(usage.getStatisticalMultiplier()),
                (usage, value) -> usage.setStatisticalMultiplier(NumberUtils.createBigDecimal(value.trim())));
        statisticalMultiplierField.addValueChangeListener(event -> {
            fieldToValueChangesMap.updateFieldValue(fieldName, event.getValue().trim());
            recalculateAnnualizedCopies();
        });
        VaadinUtils.addComponentStyle(statisticalMultiplierField, "udm-edit-statistical-multiplier-field");
        return buildCommonLayout(statisticalMultiplierField, fieldName);
    }

    private HorizontalLayout buildAnnualMultiplierLayout() {
        String fieldName = ForeignUi.getMessage("label.annual_multiplier");
        annualMultiplierField.setSizeFull();
        binder.forField(annualMultiplierField)
            .withValidator(new RequiredValidator())
            .withValidator(value -> StringUtils.isNumeric(value.trim())
                    && ANNUAL_MULTIPLIER_RANGE.contains(NumberUtils.toInt(value.trim())),
                ForeignUi.getMessage("field.error.positive_number_between", "1", "25"))
            .bind(usage -> usage.getAnnualMultiplier().toString(),
                (usage, value) -> usage.setAnnualMultiplier(NumberUtils.toInt(value.trim())));
        annualMultiplierField.addValueChangeListener(event -> {
            fieldToValueChangesMap.updateFieldValue(fieldName, event.getValue().trim());
            recalculateAnnualizedCopies();
        });
        VaadinUtils.addComponentStyle(annualMultiplierField, "udm-edit-annual-multiplier-field");
        return buildCommonLayout(annualMultiplierField, fieldName);
    }

    private HorizontalLayout buildQuantityLayout() {
        String fieldName = ForeignUi.getMessage("label.quantity");
        quantityField.setSizeFull();
        binder.forField(quantityField)
            .withValidator(new RequiredValidator())
            .withValidator(
                new StringLengthValidator(ForeignUi.getMessage("field.error.number_length", 9), 0, 9))
            .withValidator(value -> StringUtils.isNumeric(StringUtils.trim(value))
                && Integer.parseInt(StringUtils.trim(value)) > 0, ForeignUi.getMessage("field.error.positive_number"))
            .bind(usage -> usage.getQuantity().toString(),
                (usage, value) -> usage.setQuantity(NumberUtils.toLong(value.trim())));
        quantityField.addValueChangeListener(event -> {
            fieldToValueChangesMap.updateFieldValue(fieldName, event.getValue().trim());
            recalculateAnnualizedCopies();
        });
        VaadinUtils.addComponentStyle(quantityField, "udm-edit-quantity-field");
        return buildCommonLayout(quantityField, fieldName);
    }

    private HorizontalLayout buildAnnualizedCopiesField() {
        String fieldName = ForeignUi.getMessage("label.annualized_copies");
        annualizedCopiesField.setReadOnly(true);
        annualizedCopiesField.setSizeFull();
        binder.forField(annualizedCopiesField)
            .withValidator(StringUtils::isNotBlank, ForeignUi.getMessage("field.error.annualized_copies.empty"))
            .bind(usage -> Objects.toString(usage.getAnnualizedCopies()),
                (usage, value) -> usage.setAnnualizedCopies(NumberUtils.createBigDecimal(value.trim())));
        annualizedCopiesField.addValueChangeListener(event ->
            fieldToValueChangesMap.updateFieldValue(fieldName, event.getValue().trim()));
        return buildCommonLayout(annualizedCopiesField, fieldName);
    }

    private HorizontalLayout buildWrWrkInstLayout() {
        String fieldName = ForeignUi.getMessage("label.wr_wrk_inst");
        wrWrkInstField.setSizeFull();
        wrWrkInstField.addValueChangeListener(event -> {
            fieldToValueChangesMap.updateFieldValue(fieldName, StringUtils.trimToNull(event.getValue()));
            binder.validate();
        });
        binder.forField(wrWrkInstField)
            .withValidator(value -> StringUtils.isEmpty(value) || StringUtils.isNumeric(value.trim()),
                NUMBER_VALIDATION_MESSAGE)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.number_length", 9), 0, 9))
            .withValidator(value -> hasResearcherPermission
                || StringUtils.isNotEmpty(value.trim())
                || StringUtils.isNotEmpty(reportedTitleField.getValue().trim())
                || StringUtils.isNotEmpty(reportedStandardNumberField.getValue().trim()),
                ForeignUi.getMessage("field.error.work_information_not_found"))
            .bind(usage -> Objects.toString(usage.getWrWrkInst(), StringUtils.EMPTY),
                (usage, value) -> usage.setWrWrkInst(NumberUtils.createLong(StringUtils.trimToNull(value))));
        VaadinUtils.addComponentStyle(wrWrkInstField, "udm-edit-wr-wrk-inst-field");
        return buildCommonLayout(wrWrkInstField, fieldName);
    }

    private HorizontalLayout buildCompanyLayout() {
        String fieldName = ForeignUi.getMessage("label.company_id");
        binder.forField(companyIdField)
            .withValidator(new RequiredValidator())
            .withValidator(value -> StringUtils.isNumeric(value.trim()), NUMBER_VALIDATION_MESSAGE)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.number_length", 10), 0, 10))
            .bind(usage -> Objects.toString(usage.getCompanyId(), StringUtils.EMPTY),
                (usage, value) -> usage.setCompanyId(NumberUtils.createLong(value.trim())));
        companyIdField.setSizeFull();
        companyIdField.addValueChangeListener(event -> {
            companyNameField.clear();
            detailLicenseeClassComboBox.setSelectedItem(null);
            fieldToValueChangesMap.updateFieldValue(fieldName, event.getValue().trim());
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
        HorizontalLayout layout = buildCommonLayout(companyIdField, fieldName);
        layout.addComponent(verifyButton);
        return layout;
    }

    private HorizontalLayout buildCompanyNameLayout() {
        String fieldName = ForeignUi.getMessage("label.company_name");
        companyNameField.setReadOnly(true);
        companyNameField.setSizeFull();
        companyNameField.addValueChangeListener(event ->
            fieldToValueChangesMap.updateFieldValue(fieldName, event.getValue()));
        binder.forField(companyNameField)
            .withValidator(StringUtils::isNotBlank, ForeignUi.getMessage("field.error.company_name.empty"))
            .bind(UdmUsageDto::getCompanyName, UdmUsageDto::setCompanyName);
        return buildCommonLayout(companyNameField, fieldName);
    }

    private HorizontalLayout initIneligibleReasonLayout() {
        String fieldName = ForeignUi.getMessage("label.ineligible_reason");
        ineligibleReasonComboBox.setSizeFull();
        ineligibleReasonComboBox.setItemCaptionGenerator(UdmIneligibleReason::getReason);
        ineligibleReasonComboBox.setItems(controller.getAllIneligibleReasons());
        ineligibleReasonComboBox.addValueChangeListener(event -> {
            fieldToValueChangesMap.updateFieldValue(fieldName, Objects.nonNull(event.getValue())
                ? event.getValue().getReason() : null);
            binder.validate();
        });
        binder.forField(ineligibleReasonComboBox)
            .withValidator(
                value -> Objects.isNull(value) || UsageStatusEnum.INELIGIBLE == usageStatusComboBox.getValue(),
                ForeignUi.getMessage("field.error.usage_status_not_ineligible"))
            .bind(UdmUsageDto::getIneligibleReason, UdmUsageDto::setIneligibleReason);
        VaadinUtils.addComponentStyle(ineligibleReasonComboBox, "udm-edit-ineligible-reason-combo-box");
        return buildCommonLayout(ineligibleReasonComboBox, fieldName);
    }

    private HorizontalLayout initDetailStatusLayout() {
        String fieldName = ForeignUi.getMessage("label.detail_status");
        usageStatusComboBox.setSizeFull();
        usageStatusComboBox.setEmptySelectionAllowed(false);
        HashSet<UsageStatusEnum> statuses = new LinkedHashSet<>();
        statuses.add(udmUsage.getStatus());
        statuses.addAll(hasResearcherPermission
            ? EDIT_AVAILABLE_STATUSES_RESEARCHER : EDIT_AVAILABLE_STATUSES_SPECIALIST_AND_MANAGER);
        usageStatusComboBox.setItems(statuses);
        usageStatusComboBox.addValueChangeListener(event -> {
            fieldToValueChangesMap.updateFieldValue(fieldName, event.getValue().name());
            binder.validate();
        });
        binder.forField(usageStatusComboBox)
            .withValidator(
                value -> UsageStatusEnum.INELIGIBLE != value || Objects.nonNull(ineligibleReasonComboBox.getValue()),
                ForeignUi.getMessage("field.error.ineligible_only_if_ineligible_reason_populated"))
            .withValidator(this::validateUsageStatus,
                ForeignUi.getMessage("field.error.not_set_new_detail_status_after_changes"))
            .bind(UdmUsageDto::getStatus, UdmUsageDto::setStatus);
        VaadinUtils.addComponentStyle(usageStatusComboBox, "udm-edit-detail-status-combo-box");
        return buildCommonLayout(usageStatusComboBox, fieldName);
    }

    private HorizontalLayout initActionReasonLayout() {
        String fieldName = ForeignUi.getMessage("label.action_reason_udm");
        ComboBox<UdmActionReason> comboBox = new ComboBox<>();
        comboBox.setSizeFull();
        comboBox.setItemCaptionGenerator(UdmActionReason::getReason);
        comboBox.setItems(controller.getAllActionReasons());
        comboBox.addValueChangeListener(event -> {
            fieldToValueChangesMap.updateFieldValue(fieldName, Objects.nonNull(event.getValue())
                ? event.getValue().getReason() : null);
            binder.validate();
        });
        binder.forField(comboBox).bind(UdmUsageDto::getActionReason, UdmUsageDto::setActionReason);
        VaadinUtils.addComponentStyle(comboBox, "udm-edit-action-reason-combo-box");
        return buildCommonLayout(comboBox, fieldName);
    }

    private HorizontalLayout initDetailLicenseeClassLayout() {
        String fieldName = ForeignUi.getMessage("label.det_lc");
        detailLicenseeClassComboBox.setSizeFull();
        detailLicenseeClassComboBox.setEmptySelectionAllowed(false);
        detailLicenseeClassComboBox.setItemCaptionGenerator(this::buildDetailLicenseeClassString);
        detailLicenseeClassComboBox.setItems(idToLicenseeClassMap.values());
        detailLicenseeClassComboBox.addValueChangeListener(event ->
            fieldToValueChangesMap.updateFieldValue(fieldName, buildDetailLicenseeClassString(event.getValue())));
        binder.forField(detailLicenseeClassComboBox)
            .withValidator(Objects::nonNull, ForeignUi.getMessage("field.error.empty"))
            .bind(UdmUsageDto::getDetailLicenseeClass, UdmUsageDto::setDetailLicenseeClass);
        VaadinUtils.addComponentStyle(detailLicenseeClassComboBox, "udm-edit-detail-licensee-class-combo-box");
        return buildCommonLayout(detailLicenseeClassComboBox, fieldName);
    }

    private HorizontalLayout initButtonsLayout() {
        Button closeButton = Buttons.createCloseButton(this);
        saveButton.setEnabled(false);
        saveButton.addClickListener(event -> {
            if (binder.isValid()) {
                if (udmUsage.isBaselineFlag()) {
                    saveBaselineUsage(event);
                } else {
                    saveUsage(event, StringUtils.EMPTY);
                }
            } else {
                showValidationErrorWindow();
            }
        });
        VaadinUtils.setButtonsAutoDisabled(saveButton);
        Button discardButton = Buttons.createButton(ForeignUi.getMessage("button.discard"));
        discardButton.addClickListener(event -> {
            binder.readBean(udmUsage);
            binder.validate();
            saveButton.setEnabled(false);
        });
        return new HorizontalLayout(saveButton, discardButton, closeButton);
    }

    private void saveBaselineUsage(ClickEvent event) {
        Windows.showConfirmDialogWithReason(
            ForeignUi.getMessage("window.confirm"),
            ForeignUi.getMessage("message.confirm.remove_usage_from_baseline"),
            ForeignUi.getMessage("button.yes"),
            ForeignUi.getMessage("button.cancel"),
            reason -> saveUsage(event, reason),
            List.of(new RequiredValidator(),
                new StringLengthValidator(ForeignUi.getMessage("field.error.length", 1000), 0, 1000)));
    }

    private void saveUsage(ClickEvent event, String reason) {
        try {
            binder.writeBean(udmUsage);
            controller.updateUsage(udmUsage, fieldToValueChangesMap.getActionReasons(),
                hasResearcherPermission, reason.trim());
            saveButtonClickListener.buttonClick(event);
            close();
        } catch (ValidationException e) {
            showValidationErrorWindow();
        }
    }

    private void showValidationErrorWindow() {
        Windows.showValidationErrorWindow(List.of(usageStatusComboBox, wrWrkInstField, reportedTitleField,
            reportedStandardNumberField, reportedPubTypeField, commentField, researchUrlField,
            companyIdField, companyNameField, detailLicenseeClassComboBox, annualMultiplierField,
            statisticalMultiplierField, quantityField, annualizedCopiesField, ineligibleReasonComboBox));
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

    private String buildDetailLicenseeClassString(DetailLicenseeClass detailLicenseeClass) {
        return Objects.nonNull(detailLicenseeClass) ? detailLicenseeClass.getIdAndDescription() : null;
    }

    private boolean validateUsageStatus(UsageStatusEnum detailStatus) {
        UdmUsageFieldsForStatusValidation udmUsageFieldsAfterChanges =
            new UdmUsageFieldsForStatusValidation(
                NumberUtils.createLong(StringUtils.trimToNull(wrWrkInstField.getValue())),
                StringUtils.trimToNull(reportedTitleField.getValue()),
                StringUtils.trimToNull(reportedStandardNumberField.getValue()));
        return udmUsageFieldsAfterChanges.equals(udmUsageFieldsBeforeChanges)
            || detailStatus == UsageStatusEnum.NEW;
    }

    private static class UdmUsageFieldsForStatusValidation {
        private Long wrWrkInst;
        private String reportedTitle;
        private String reportedStandardNumber;

        UdmUsageFieldsForStatusValidation(Long wrWrkInst, String reportedTitle, String reportedStandardNumber) {
            this.wrWrkInst = wrWrkInst;
            this.reportedTitle = reportedTitle;
            this.reportedStandardNumber = reportedStandardNumber;
        }

        UdmUsageFieldsForStatusValidation(Long wrWrkInst) {
            this.wrWrkInst = wrWrkInst;
        }

        public Long getWrWrkInst() {
            return wrWrkInst;
        }

        public void setWrWrkInst(Long wrWrkInst) {
            this.wrWrkInst = wrWrkInst;
        }

        public String getReportedTitle() {
            return reportedTitle;
        }

        public void setReportedTitle(String reportedTitle) {
            this.reportedTitle = reportedTitle;
        }

        public String getReportedStandardNumber() {
            return reportedStandardNumber;
        }

        public void setReportedStandardNumber(String reportedStandardNumber) {
            this.reportedStandardNumber = reportedStandardNumber;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (null == obj || getClass() != obj.getClass()) {
                return false;
            }
            UdmUsageFieldsForStatusValidation that = (UdmUsageFieldsForStatusValidation) obj;
            return new EqualsBuilder()
                .append(this.wrWrkInst, that.wrWrkInst)
                .append(this.reportedTitle, that.reportedTitle)
                .append(this.reportedStandardNumber, that.reportedStandardNumber)
                .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder()
                .append(wrWrkInst)
                .append(reportedTitle)
                .append(reportedStandardNumber)
                .toHashCode();
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                .append("wrWrkInst", wrWrkInst)
                .append("reportedTitle", reportedTitle)
                .append("reportedStandardNumber", reportedStandardNumber)
                .toString();
        }
    }
}
