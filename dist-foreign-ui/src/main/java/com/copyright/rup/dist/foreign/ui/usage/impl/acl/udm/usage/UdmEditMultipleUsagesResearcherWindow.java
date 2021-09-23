package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.usage;

import com.copyright.rup.dist.foreign.domain.UdmActionReason;
import com.copyright.rup.dist.foreign.domain.UdmAuditFieldToValuesMap;
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

import java.util.Arrays;
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
        Arrays.asList(UsageStatusEnum.NEW, UsageStatusEnum.OPS_REVIEW, UsageStatusEnum.SPECIALIST_REVIEW);
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
    private Map<UdmUsageDto, UdmAuditFieldToValuesMap> udmUsageDtoToFieldValuesMap;

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
        statusComboBox.setItems(EDIT_AVAILABLE_STATUSES);
        statusComboBox.setEmptySelectionAllowed(false);
        binder.forField(statusComboBox).bind(UdmUsageDto::getStatus, UdmUsageDto::setStatus);
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
        VaadinUtils.addComponentStyle(wrWrkInstField, "udm-multiple-edit-wr-wrk-inst-field");
        return buildCommonLayout(wrWrkInstField, "label.wr_wrk_inst");
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
                controller.updateUsages(udmUsageDtoToFieldValuesMap, true);
                saveButtonClickListener.buttonClick(event);
                close();
            } catch (ValidationException e) {
                Windows.showValidationErrorWindow(Arrays.asList(wrWrkInstField, commentField));
            }
        });
        Button discardButton = Buttons.createButton(ForeignUi.getMessage("button.discard"));
        discardButton.addClickListener(event -> binder.readBean(null));
        return new HorizontalLayout(saveButton, discardButton, closeButton);
    }

    private void updateUsagesFields() {
        udmUsageDtoToFieldValuesMap = new HashMap<>();
        checkStatusAndUpdateIneligibleReason();
        selectedUdmUsages.forEach(usageDto -> {
            UdmAuditFieldToValuesMap valuesMap = new UdmAuditFieldToValuesMap();
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
                                         UdmAuditFieldToValuesMap valuesMap) {
        setFieldAndAddAudit(usageConsumer, usageFunction, usageFunction, newUsage, oldUsage, fieldName, valuesMap);
    }

    private <T, K> void setFieldAndAddAudit(Consumer<T> usageConsumer, Function<UdmUsageDto, T> usageFunction,
                                            Function<UdmUsageDto, K> auditFunction, UdmUsageDto newUsage,
                                            UdmUsageDto oldUsage, String fieldName,
                                            UdmAuditFieldToValuesMap valuesMap) {
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
