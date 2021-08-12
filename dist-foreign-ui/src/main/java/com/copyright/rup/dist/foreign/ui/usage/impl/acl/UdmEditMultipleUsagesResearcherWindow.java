package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import com.copyright.rup.dist.foreign.domain.UdmActionReason;
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

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Window to edit multiple UDM usages for Researcher role.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 08/11/21
 *
 * @author Anton Azarenka
 */
public class UdmEditMultipleUsagesResearcherWindow extends Window {

    private static final List<UsageStatusEnum> EDIT_AVAILABLE_STATUSES =
        Arrays.asList(UsageStatusEnum.NEW, UsageStatusEnum.OPS_REVIEW, UsageStatusEnum.SPECIALIST_REVIEW);
    private final IUdmUsageController controller;
    private final ComboBox<UsageStatusEnum> statusComboBox =
        new ComboBox<>(ForeignUi.getMessage("label.detail_status"));
    private final TextField wrWrkInstField = new TextField(ForeignUi.getMessage("label.wr_wrk_inst"));
    private final ComboBox<UdmActionReason> actionReasonComboBox =
        new ComboBox<>(ForeignUi.getMessage("label.action_reason_udm"));
    private final TextField commentField = new TextField(ForeignUi.getMessage("label.comment"));
    private final Button saveButton = Buttons.createButton(ForeignUi.getMessage("button.save"));

    /**
     * Constructor.
     *
     * @param controller instance of {@link IUdmUsageController}
     */
    public UdmEditMultipleUsagesResearcherWindow(IUdmUsageController controller) {
        this.controller = controller;
        setContent(initRootLayout());
        setCaption(ForeignUi.getMessage("window.multiple.edit_udm_usage"));
        setResizable(false);
        setWidth(650, Unit.PIXELS);
        setHeight(215, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "multiple-edit-udm-usages-window");
    }

    private Component initRootLayout() {
        VerticalLayout rootLayout = new VerticalLayout();
        HorizontalLayout buttonsLayout = initButtonsLayout();
        rootLayout.addComponents(
            buildDetailStatusLayout(),
            buildWrWrkInstLayout(),
            buildActionReasonLayout(),
            buildCommonStringLayout(commentField, "label.comment", "udm-edit-comment-field"),
            buttonsLayout
        );
        rootLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
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

    private HorizontalLayout buildActionReasonLayout() {
        actionReasonComboBox.setSizeFull();
        actionReasonComboBox.setItemCaptionGenerator(UdmActionReason::getReason);
        actionReasonComboBox.setItems(controller.getAllActionReasons());
        VaadinUtils.addComponentStyle(actionReasonComboBox, "udm-multiple-edit-action-reason-combo-box");
        return buildCommonLayout(actionReasonComboBox, "label.action_reason_udm");
    }

    private HorizontalLayout buildWrWrkInstLayout() {
        wrWrkInstField.setSizeFull();
        VaadinUtils.addComponentStyle(wrWrkInstField, "udm-multiple-edit-wr-wrk-inst-field");
        return buildCommonLayout(wrWrkInstField, "label.wr_wrk_inst");
    }

    private HorizontalLayout buildCommonStringLayout(TextField textField, String caption, String styleName) {
        textField.setSizeFull();
        VaadinUtils.addComponentStyle(textField, styleName);
        return buildCommonLayout(textField, caption);
    }

    private HorizontalLayout buildCommonLayout(Component component, String labelCaption) {
        Label label = new Label(ForeignUi.getMessage(labelCaption));
        label.addStyleName(Cornerstone.LABEL_BOLD);
        label.setWidth(110, Unit.PIXELS);
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
