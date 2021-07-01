package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

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

import java.util.Arrays;
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
            buildLayout("label.detail_id", udmUsage.getId(), false),
            buildLayout("label.period", Objects.toString(udmUsage.getPeriod(), StringUtils.EMPTY), false),
            buildLayout("label.usage_origin",
                Objects.nonNull(udmUsage.getUsageOrigin()) ? udmUsage.getUsageOrigin().name() : StringUtils.EMPTY,
                false),
            buildLayout("label.usage_detail_id", udmUsage.getOriginalDetailId(), false),
            initDetailStatusLayout(),
            buildLayout("label.assignee", udmUsage.getAssignee(), false),
            buildLayout("label.rh_account_number", Objects.toString(udmUsage.getRhAccountNumber(), StringUtils.EMPTY),
                false),
            buildLayout("label.rh_name", udmUsage.getRhName(), false),
            buildLayout("label.wr_wrk_inst", Objects.toString(udmUsage.getWrWrkInst(), StringUtils.EMPTY), true),
            buildLayout("label.reported_title", udmUsage.getReportedTitle(), true),
            buildLayout("label.system_title", udmUsage.getSystemTitle(), false),
            buildLayout("label.reported_standard_number", udmUsage.getReportedStandardNumber(), true),
            buildLayout("label.standard_number", udmUsage.getStandardNumber(), false),
            buildLayout("label.reported_pub_type", udmUsage.getReportedPubType(), true),
            buildLayout("label.publication_format", udmUsage.getPubFormat(), false),
            buildLayout("label.article", udmUsage.getArticle(), false),
            buildLayout("label.language", udmUsage.getLanguage(), false),
            initActionReasonLayout(),
            buildLayout("label.comment", udmUsage.getComment(), true),
            buildLayout("label.research_url", udmUsage.getResearchUrl(), true),
            initDetailLicenseeClassLayout(),
            buildCompanyLayout(),
            buildLayout("label.company_name", udmUsage.getCompanyName(), false),
            buildLayout("label.survey_respondent", udmUsage.getSurveyRespondent(), false),
            buildLayout("label.ip_address", udmUsage.getIpAddress(), false),
            buildLayout("label.survey_country", udmUsage.getSurveyCountry(), false),
            buildLayout("label.channel",
                Objects.nonNull(udmUsage.getChannel()) ? udmUsage.getChannel().name() : StringUtils.EMPTY, false),
            buildLayout("label.usage_date", Objects.toString(udmUsage.getUsageDate(), StringUtils.EMPTY), false),
            buildLayout("label.survey_start_date", Objects.toString(udmUsage.getSurveyStartDate(), StringUtils.EMPTY),
                false),
            buildLayout("label.survey_end_date", Objects.toString(udmUsage.getSurveyEndDate(), StringUtils.EMPTY),
                false),
            buildLayout("label.annual_multiplier", Objects.toString(udmUsage.getAnnualMultiplier(), StringUtils.EMPTY),
                true),
            buildLayout("label.statistical_multiplier",
                Objects.toString(udmUsage.getStatisticalMultiplier(), StringUtils.EMPTY), true),
            buildLayout("label.reported_tou", udmUsage.getReportedTypeOfUse(), false),
            buildLayout("label.quantity", Objects.toString(udmUsage.getQuantity(), StringUtils.EMPTY), true),
            buildLayout("label.annualized_copies",
                Objects.toString(udmUsage.getAnnualizedCopies(), StringUtils.EMPTY), false),
            initIneligibleReasonLayout(),
            buildLayout("label.load_date", Objects.toString(udmUsage.getCreateDate(), StringUtils.EMPTY), false),
            buildLayout("label.updated_by", udmUsage.getUpdateUser(), false),
            buildLayout("label.updated_date", Objects.toString(udmUsage.getUpdateDate(), StringUtils.EMPTY), false)
        );
        Panel panel = new Panel(editFieldsLayout);
        panel.setSizeFull();
        editFieldsLayout.setMargin(new MarginInfo(true, true, true, true));
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

    private HorizontalLayout buildLayout(String caption, String value, boolean isEditable) {
        TextField textField = new TextField();
        textField.setValue(null == value ? StringUtils.EMPTY : value);
        textField.setReadOnly(!isEditable);
        textField.setSizeFull();
        HorizontalLayout layout = new HorizontalLayout(buildLabel(caption), textField);
        layout.setSizeFull();
        layout.setExpandRatio(textField, 1);
        return layout;
    }

    private HorizontalLayout buildCompanyLayout() {
        TextField textField = new TextField();
        Long value = udmUsage.getCompanyId();
        textField.setValue(null == value ? StringUtils.EMPTY : value.toString());
        textField.setSizeFull();
        HorizontalLayout layout = new HorizontalLayout(buildLabel("label.company_id"), textField,
            Buttons.createButton(ForeignUi.getMessage("button.verify")));
        layout.setSizeFull();
        layout.setExpandRatio(textField, 1);
        return layout;
    }

    private Label buildLabel(String caption) {
        Label label = new Label(ForeignUi.getMessage(caption));
        label.addStyleName(Cornerstone.LABEL_BOLD);
        label.setWidth(165, Unit.PIXELS);
        return label;
    }

    private HorizontalLayout initIneligibleReasonLayout() {
        ComboBox<UdmIneligibleReason> comboBox = new ComboBox<>();
        comboBox.setSizeFull();
        comboBox.setItemCaptionGenerator(UdmIneligibleReason::getText);
        comboBox.setItems(controller.getIneligibleReasons());
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

    private HorizontalLayout initButtonsLayout() {
        Button closeButton = Buttons.createCloseButton(this);
        Button saveButton = Buttons.createButton(ForeignUi.getMessage("button.save"));
        Button discardButton = Buttons.createButton(ForeignUi.getMessage("button.discard"));
        return new HorizontalLayout(saveButton, discardButton, closeButton);
    }
}
