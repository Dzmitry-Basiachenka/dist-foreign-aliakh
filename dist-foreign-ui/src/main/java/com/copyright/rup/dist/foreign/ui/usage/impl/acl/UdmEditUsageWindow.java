package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.UdmActionReason;
import com.copyright.rup.dist.foreign.domain.UdmIneligibleReason;
import com.copyright.rup.dist.foreign.domain.UdmUsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.Binder;
import com.vaadin.data.ValueProvider;
import com.vaadin.data.converter.StringToBigDecimalConverter;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.server.Setter;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
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
    private final Binder<UdmUsageDto> binder = new Binder<>();
    private final IUdmUsageController controller;
    private final UdmUsageDto udmUsage;

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
            buildEditableStringLayout("label.reported_title", UdmUsageDto::getReportedTitle,
                UdmUsageDto::setReportedTitle),
            buildReadOnlyLayout("label.system_title", UdmUsageDto::getSystemTitle),
            buildEditableStringLayout("label.reported_standard_number", UdmUsageDto::getReportedStandardNumber,
                UdmUsageDto::setReportedStandardNumber),
            buildReadOnlyLayout("label.standard_number", UdmUsageDto::getStandardNumber),
            buildEditableStringLayout("label.reported_pub_type", UdmUsageDto::getReportedPubType,
                UdmUsageDto::setReportedPubType),
            buildReadOnlyLayout("label.publication_format", UdmUsageDto::getPubFormat),
            buildReadOnlyLayout("label.article", UdmUsageDto::getArticle),
            buildReadOnlyLayout("label.language", UdmUsageDto::getLanguage),
            initActionReasonLayout(),
            buildEditableStringLayout("label.comment", UdmUsageDto::getComment, UdmUsageDto::setComment),
            buildEditableStringLayout("label.research_url", UdmUsageDto::getResearchUrl, UdmUsageDto::setResearchUrl),
            initDetailLicenseeClassLayout(),
            buildCompanyLayout(),
            buildReadOnlyLayout("label.company_name", UdmUsageDto::getCompanyName),
            buildReadOnlyLayout("label.survey_respondent", UdmUsageDto::getSurveyRespondent),
            buildReadOnlyLayout("label.ip_address", UdmUsageDto::getIpAddress),
            buildReadOnlyLayout("label.survey_country", UdmUsageDto::getSurveyCountry),
            buildReadOnlyLayout("label.channel", usage -> usage.getChannel().name()),
            buildReadOnlyLayout("label.usage_date", usage -> getStringFromLocalDate(usage.getUsageDate())),
            buildReadOnlyLayout("label.survey_start_date", usage -> getStringFromLocalDate(usage.getSurveyStartDate())),
            buildReadOnlyLayout("label.survey_end_date", usage -> getStringFromLocalDate(usage.getSurveyEndDate())),
            buildEditableIntegerLayout("label.annual_multiplier", UdmUsageDto::getAnnualMultiplier,
                UdmUsageDto::setAnnualMultiplier),
            buildEditableBigDecimalLayout("label.statistical_multiplier", UdmUsageDto::getStatisticalMultiplier,
                UdmUsageDto::setStatisticalMultiplier),
            buildReadOnlyLayout("label.reported_tou", UdmUsageDto::getReportedTypeOfUse),
            buildEditableIntegerLayout("label.quantity", UdmUsageDto::getQuantity, UdmUsageDto::setQuantity),
            buildReadOnlyLayout("label.annualized_copies", usage -> Objects.toString(usage.getAnnualizedCopies())),
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
        binder.readBean(udmUsage);
        binder.validate();
        return rootLayout;
    }

    private HorizontalLayout buildReadOnlyLayout(String caption, ValueProvider<UdmUsageDto, String> getter) {
        TextField textField = new TextField();
        textField.setReadOnly(true);
        textField.setSizeFull();
        binder.forField(textField).bind(getter, null);
        HorizontalLayout layout = new HorizontalLayout(buildLabel(caption), textField);
        layout.setSizeFull();
        layout.setExpandRatio(textField, 1);
        return layout;
    }

    private HorizontalLayout buildEditableStringLayout(String caption, ValueProvider<UdmUsageDto, String> getter,
                                                       Setter<UdmUsageDto, String> setter) {
        TextField textField = new TextField();
        textField.setSizeFull();
        binder.forField(textField).bind(getter, setter);
        HorizontalLayout layout = new HorizontalLayout(buildLabel(caption), textField);
        layout.setSizeFull();
        layout.setExpandRatio(textField, 1);
        return layout;
    }

    private HorizontalLayout buildEditableBigDecimalLayout(String caption,
                                                           ValueProvider<UdmUsageDto, BigDecimal> getter,
                                                           Setter<UdmUsageDto, BigDecimal> setter) {
        TextField textField = new TextField();
        textField.setSizeFull();
        binder.forField(textField)
            .withConverter(new StringToBigDecimalConverter("Field should be numeric")).bind(getter, setter);
        HorizontalLayout layout = new HorizontalLayout(buildLabel(caption), textField);
        layout.setSizeFull();
        layout.setExpandRatio(textField, 1);
        return layout;
    }

    private HorizontalLayout buildEditableIntegerLayout(String caption, ValueProvider<UdmUsageDto, Integer> getter,
                                                        Setter<UdmUsageDto, Integer> setter) {
        TextField textField = new TextField();
        textField.setSizeFull();
        binder.forField(textField)
            .withConverter(new StringToIntegerConverter("Field should be numeric"))
            .bind(getter, setter);
        HorizontalLayout layout = new HorizontalLayout(buildLabel(caption), textField);
        layout.setSizeFull();
        layout.setExpandRatio(textField, 1);
        return layout;
    }

    private HorizontalLayout buildWrWrkInstLayout() {
        TextField textField = new TextField();
        textField.setSizeFull();
        binder.forField(textField).bind(usage -> Objects.toString(usage.getWrWrkInst(), StringUtils.EMPTY),
            (usage, value) -> usage.setWrWrkInst(NumberUtils.createLong(value)));
        HorizontalLayout layout = new HorizontalLayout(buildLabel("label.wr_wrk_inst"), textField);
        layout.setSizeFull();
        layout.setExpandRatio(textField, 1);
        return layout;
    }

    private HorizontalLayout buildCompanyLayout() {
        TextField textField = new TextField();
        binder.forField(textField).bind(usage -> Objects.toString(usage.getCompanyId(), StringUtils.EMPTY),
            (usage, value) -> usage.setCompanyId(NumberUtils.createLong(value)));
        textField.setSizeFull();
        HorizontalLayout layout = new HorizontalLayout(buildLabel("label.company_id"), textField,
            Buttons.createButton(ForeignUi.getMessage("button.verify")));
        layout.setSizeFull();
        layout.setExpandRatio(textField, 1);
        return layout;
    }

    private HorizontalLayout initIneligibleReasonLayout() {
        ComboBox<UdmIneligibleReason> comboBox = new ComboBox<>();
        comboBox.setSizeFull();
        comboBox.setItemCaptionGenerator(UdmIneligibleReason::getText);
        comboBox.setItems(controller.getIneligibleReasons());
        binder.forField(comboBox).bind(UdmUsageDto::getIneligibleReason, UdmUsageDto::setIneligibleReason);
        HorizontalLayout layout = new HorizontalLayout(buildLabel("label.ineligible_reason"), comboBox);
        layout.setSizeFull();
        layout.setExpandRatio(comboBox, 1);
        return layout;
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
        HorizontalLayout layout = new HorizontalLayout(buildLabel("label.detail_status"), comboBox);
        layout.setSizeFull();
        layout.setExpandRatio(comboBox, 1);
        return layout;
    }

    private HorizontalLayout initActionReasonLayout() {
        ComboBox<UdmActionReason> comboBox = new ComboBox<>();
        comboBox.setSizeFull();
        comboBox.setItemCaptionGenerator(UdmActionReason::getText);
        comboBox.setItems(controller.getActionReasons());
        binder.forField(comboBox).bind(UdmUsageDto::getActionReason, UdmUsageDto::setActionReason);
        HorizontalLayout layout = new HorizontalLayout(buildLabel("label.action_reason_udm"), comboBox);
        layout.setSizeFull();
        layout.setExpandRatio(comboBox, 1);
        return layout;
    }

    private HorizontalLayout initDetailLicenseeClassLayout() {
        ComboBox<DetailLicenseeClass> comboBox = new ComboBox<>();
        comboBox.setSizeFull();
        comboBox.setItemCaptionGenerator(detailLicenseeClass ->
            String.format("%s - %s", detailLicenseeClass.getId(), detailLicenseeClass.getDescription()));
        comboBox.setItems(controller.getDetailLicenseeClasses());
        binder.forField(comboBox).bind(UdmUsageDto::getDetailLicenseeClass, UdmUsageDto::setDetailLicenseeClass);
        HorizontalLayout layout = new HorizontalLayout(buildLabel("label.det_lc"), comboBox);
        layout.setSizeFull();
        layout.setExpandRatio(comboBox, 1);
        return layout;
    }

    private Label buildLabel(String caption) {
        Label label = new Label(ForeignUi.getMessage(caption));
        label.addStyleName(Cornerstone.LABEL_BOLD);
        label.setWidth(165, Unit.PIXELS);
        return label;
    }

    private HorizontalLayout initButtonsLayout() {
        Button closeButton = Buttons.createCloseButton(this);
        Button saveButton = Buttons.createButton(ForeignUi.getMessage("button.save"));
        Button discardButton = Buttons.createButton(ForeignUi.getMessage("button.discard"));
        return new HorizontalLayout(saveButton, discardButton, closeButton);
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
