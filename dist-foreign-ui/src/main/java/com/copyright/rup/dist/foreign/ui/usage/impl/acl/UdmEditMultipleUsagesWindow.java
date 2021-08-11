package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import com.copyright.rup.dist.foreign.domain.CompanyInformation;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.UdmActionReason;
import com.copyright.rup.dist.foreign.domain.UdmIneligibleReason;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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

    private static final List<UsageStatusEnum> EDIT_AVAILABLE_STATUSES =
        Arrays.asList(UsageStatusEnum.NEW, UsageStatusEnum.ELIGIBLE, UsageStatusEnum.INELIGIBLE,
            UsageStatusEnum.OPS_REVIEW, UsageStatusEnum.SPECIALIST_REVIEW);
    private final IUdmUsageController controller;
    private final ComboBox<UsageStatusEnum> statusComboBox =
        new ComboBox<>(ForeignUi.getMessage("label.detail_status"));
    private final ComboBox<DetailLicenseeClass> detailLicenseeClassComboBox =
        new ComboBox<>(ForeignUi.getMessage("label.det_lc"));
    private final TextField wrWrkInstField = new TextField(ForeignUi.getMessage("label.wr_wrk_inst"));
    private final TextField periodField = new TextField(ForeignUi.getMessage("label.period"));
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

    /**
     * Constructor.
     *
     * @param usageController instance of {@link IUdmUsageController}
     */
    public UdmEditMultipleUsagesWindow(IUdmUsageController usageController) {
        this.controller = usageController;
        idToLicenseeClassMap = controller.getDetailLicenseeClasses()
            .stream()
            .collect(Collectors.toMap(DetailLicenseeClass::getId, Function.identity()));
        setContent(initRootLayout());
        setCaption(ForeignUi.getMessage("window.multiple.edit_udm_usage"));
        setResizable(false);
        setWidth(650, Unit.PIXELS);
        setHeight(530, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "multiple-edit-udm-usages-window");
    }

    private Component initRootLayout() {
        VerticalLayout rootLayout = new VerticalLayout();
        VerticalLayout editFieldsLayout = new VerticalLayout();
        editFieldsLayout.addComponents(
            buildDetailStatusLayout(),
            buildPeriodLayout(),
            buildDetailLicenseeClassLayout(),
            buildCompanyLayout(),
            buildCompanyNameLayout(),
            buildWrWrkInstLayout(),
            buildCommonStringLayout(reportedStandardNumberField, "label.reported_standard_number",
                "udm-edit-reported-standard-number-field"),
            buildCommonStringLayout(reportedTitleField, "label.reported_title", "udm-edit-reported-title-field"),
            buildAnnualMultiplierLayout(),
            buildStatisticalMultiplier(),
            buildQuantityLayout(),
            buildActionReasonLayout(),
            buildIneligibleReasonLayout(),
            buildCommonStringLayout(commentField, "label.comment", "udm-edit-comment-field")
        );
        HorizontalLayout buttonsLayout = initButtonsLayout();
        rootLayout.addComponents(editFieldsLayout, buttonsLayout);
        rootLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        rootLayout.setExpandRatio(editFieldsLayout, 1f);
        rootLayout.setSizeFull();
        return rootLayout;
    }

    private HorizontalLayout buildDetailStatusLayout() {
        statusComboBox.setSizeFull();
        statusComboBox.setItems(new LinkedHashSet<>(EDIT_AVAILABLE_STATUSES));
        statusComboBox.setEmptySelectionAllowed(false);
        VaadinUtils.addComponentStyle(statusComboBox, "udm-multiple-edit-detail-status-combo-box");
        return buildCommonLayout(statusComboBox, "label.detail_status");
    }

    private HorizontalLayout buildPeriodLayout() {
        periodField.setSizeFull();
        VaadinUtils.addComponentStyle(wrWrkInstField, "udm-multiple-edit-period-field");
        return buildCommonLayout(periodField, "label.period");
    }

    private HorizontalLayout buildDetailLicenseeClassLayout() {
        detailLicenseeClassComboBox.setSizeFull();
        detailLicenseeClassComboBox.setEmptySelectionAllowed(true);
        detailLicenseeClassComboBox.setItemCaptionGenerator(detailLicenseeClass ->
            String.format("%s - %s", detailLicenseeClass.getId(), detailLicenseeClass.getDescription()));
        detailLicenseeClassComboBox.setItems(idToLicenseeClassMap.values());
        VaadinUtils.addComponentStyle(detailLicenseeClassComboBox, "udm-multiple-edit-detail-licensee-class-combo-box");
        return buildCommonLayout(detailLicenseeClassComboBox, "label.det_lc");
    }

    private HorizontalLayout buildCompanyLayout() {
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
        return buildCommonLayout(companyNameField, "label.company_name");
    }

    private HorizontalLayout buildWrWrkInstLayout() {
        wrWrkInstField.setSizeFull();
        VaadinUtils.addComponentStyle(wrWrkInstField, "udm-multiple-edit-wr-wrk-inst-field");
        return buildCommonLayout(wrWrkInstField, "label.wr_wrk_inst");
    }

    private HorizontalLayout buildAnnualMultiplierLayout() {
        annualMultiplierField.setSizeFull();
        VaadinUtils.addComponentStyle(annualMultiplierField, "udm-multiple-edit-annual-multiplier-field");
        return buildCommonLayout(annualMultiplierField, "label.annual_multiplier");
    }

    private HorizontalLayout buildStatisticalMultiplier() {
        statisticalMultiplierField.setSizeFull();
        VaadinUtils.addComponentStyle(statisticalMultiplierField, "udm-multiple-edit-statistical-multiplier-field");
        return buildCommonLayout(statisticalMultiplierField, "label.statistical_multiplier");
    }

    private HorizontalLayout buildQuantityLayout() {
        quantityField.setSizeFull();
        VaadinUtils.addComponentStyle(quantityField, "udm-multiple-edit-quantity-field");
        return buildCommonLayout(quantityField, "label.quantity");
    }

    private HorizontalLayout buildActionReasonLayout() {
        ComboBox<UdmActionReason> comboBox = new ComboBox<>(ForeignUi.getMessage("label.action_reason_udm"));
        comboBox.setSizeFull();
        comboBox.setItemCaptionGenerator(UdmActionReason::getReason);
        comboBox.setItems(controller.getAllActionReasons());
        VaadinUtils.addComponentStyle(comboBox, "udm-multiple-edit-action-reason-combo-box");
        return buildCommonLayout(comboBox, "label.action_reason_udm");
    }

    private HorizontalLayout buildIneligibleReasonLayout() {
        ComboBox<UdmIneligibleReason> comboBox = new ComboBox<>(ForeignUi.getMessage("label.ineligible_reason"));
        comboBox.setSizeFull();
        comboBox.setItemCaptionGenerator(UdmIneligibleReason::getReason);
        comboBox.setItems(controller.getAllIneligibleReasons());
        VaadinUtils.addComponentStyle(comboBox, "udm-multiple-edit-ineligible-reason-combo-box");
        return buildCommonLayout(comboBox, "label.ineligible_reason");
    }

    private HorizontalLayout buildCommonStringLayout(TextField textField, String caption, String styleName) {
        textField.setSizeFull();
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
        return new HorizontalLayout(saveButton, closeButton);
    }
}
