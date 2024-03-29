package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.usage;

import com.copyright.rup.dist.foreign.domain.UdmActionReason;
import com.copyright.rup.dist.foreign.domain.UdmIneligibleReason;
import com.copyright.rup.dist.foreign.domain.UdmUsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.ui.audit.impl.CommonAuditFieldToValuesMap;
import com.copyright.rup.dist.foreign.ui.audit.impl.UdmUsageAuditFieldToValuesMap;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.validator.StringLengthValidator;
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
import com.vaadin.ui.themes.ValoTheme;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

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
        List.of(UsageStatusEnum.NEW, UsageStatusEnum.OPS_REVIEW, UsageStatusEnum.SPECIALIST_REVIEW);
    private static final long serialVersionUID = -1925945768528407133L;

    private final IUdmUsageController controller;
    private final ComboBox<UsageStatusEnum> statusComboBox =
        new ComboBox<>(ForeignUi.getMessage("label.detail_status"));
    private final TextField wrWrkInstField = new TextField(ForeignUi.getMessage("label.wr_wrk_inst"));
    private final ComboBox<UdmActionReason> actionReasonComboBox =
        new ComboBox<>(ForeignUi.getMessage("label.action_reason_udm"));
    private final TextField commentField = new TextField(ForeignUi.getMessage("label.comment"));
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
    public UdmEditMultipleUsagesResearcherWindow(IUdmUsageController usageController,
                                                 Set<UdmUsageDto> selectedUdmUsages, ClickListener clickListener) {
        this.controller = usageController;
        this.selectedUdmUsages = selectedUdmUsages;
        saveButtonClickListener = clickListener;
        bindedUsageDto = new UdmUsageDto();
        super.setContent(initRootLayout());
        super.setCaption(ForeignUi.getMessage("window.multiple.edit_udm_usages"));
        super.setResizable(false);
        super.setWidth(650, Unit.PIXELS);
        super.setHeight(215, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "multiple-edit-udm-usages-window");
    }

    private Component initRootLayout() {
        VerticalLayout rootLayout = new VerticalLayout();
        HorizontalLayout buttonsLayout = initButtonsLayout();
        rootLayout.addComponents(
            buildDetailStatusLayout(),
            buildWrWrkInstLayout(),
            buildActionReasonLayout(),
            buildCommentLayout(),
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
            .withValidator(value -> StringUtils.isEmpty(wrWrkInstField.getValue())
                    || value == UsageStatusEnum.NEW,
                ForeignUi.getMessage("field.error.not_set_new_detail_status_after_changes"))
            .bind(UdmUsageDto::getStatus, UdmUsageDto::setStatus);
        VaadinUtils.addComponentStyle(statusComboBox, "udm-multiple-edit-detail-status-combo-box");
        return buildCommonLayout(statusComboBox, "label.detail_status");
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

    private HorizontalLayout buildWrWrkInstLayout() {
        wrWrkInstField.setSizeFull();
        binder.forField(wrWrkInstField)
            .withValidator(value -> StringUtils.isEmpty(value) || StringUtils.isNumeric(value.trim()),
                ForeignUi.getMessage("field.error.not_numeric"))
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.number_length", 9), 0, 9))
            .bind(usage -> Objects.toString(usage.getWrWrkInst(), StringUtils.EMPTY),
                (usage, value) -> usage.setWrWrkInst(NumberUtils.createLong(StringUtils.trimToNull(value))));
        wrWrkInstField.addValueChangeListener(event -> binder.validate());
        VaadinUtils.addComponentStyle(wrWrkInstField, "udm-multiple-edit-wr-wrk-inst-field");
        return buildCommonLayout(wrWrkInstField, "label.wr_wrk_inst");
    }

    private HorizontalLayout buildCommentLayout() {
        commentField.setSizeFull();
        binder.forField(commentField)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 4000), 0, 4000))
            .bind(UdmUsageDto::getComment, (usage, value) -> usage.setComment(StringUtils.trimToNull(value)));
        VaadinUtils.addComponentStyle(commentField, "udm-edit-comment-field");
        return buildCommonLayout(commentField, "label.comment");
    }

    private HorizontalLayout buildCommonLayout(Component component, String labelCaption) {
        Label label = new Label(ForeignUi.getMessage(labelCaption));
        label.addStyleName(ValoTheme.LABEL_BOLD);
        label.setWidth(110, Unit.PIXELS);
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
                binder.writeBean(bindedUsageDto);
                updateUsagesFields();
                controller.updateUsages(
                    CommonAuditFieldToValuesMap.getDtoToAuditReasonsMap(udmUsageDtoToFieldValuesMap),
                    true, StringUtils.EMPTY);
                saveButtonClickListener.buttonClick(event);
                close();
            } catch (ValidationException e) {
                Windows.showValidationErrorWindow(List.of(statusComboBox, wrWrkInstField, commentField));
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

    private void updateUsagesFields() {
        udmUsageDtoToFieldValuesMap = new HashMap<>();
        checkStatusAndUpdateIneligibleReason();
        selectedUdmUsages.forEach(usageDto -> {
            UdmUsageAuditFieldToValuesMap valuesMap = new UdmUsageAuditFieldToValuesMap();
            setFieldAndAddAudit(usageDto::setStatus, UdmUsageDto::getStatus, bindedUsageDto, usageDto,
                "label.detail_status", valuesMap);
            setFieldAndAddAudit(usageDto::setWrWrkInst, UdmUsageDto::getWrWrkInst, bindedUsageDto, usageDto,
                "label.wr_wrk_inst", valuesMap);
            setFieldAndAddAudit(usageDto::setActionReason, UdmUsageDto::getActionReason,
                (usage) -> Objects.nonNull(usage.getActionReason()) ? usage.getActionReason().getReason() : null,
                bindedUsageDto, usageDto, "label.action_reason_udm", valuesMap);
            setFieldAndAddAudit(usageDto::setComment, UdmUsageDto::getComment, bindedUsageDto, usageDto,
                "label.comment", valuesMap);
            udmUsageDtoToFieldValuesMap.put(usageDto, valuesMap);
        });
    }

    private void checkStatusAndUpdateIneligibleReason() {
        if (Objects.nonNull(bindedUsageDto.getStatus())
            && !bindedUsageDto.getStatus().equals(UsageStatusEnum.INELIGIBLE)) {
            bindedUsageDto.setIneligibleReason(new UdmIneligibleReason());
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
}
