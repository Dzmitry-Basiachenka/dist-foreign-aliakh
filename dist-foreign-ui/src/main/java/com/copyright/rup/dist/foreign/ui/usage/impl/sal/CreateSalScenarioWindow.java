package com.copyright.rup.dist.foreign.ui.usage.impl.sal;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.SalGradeGroupEnum;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.ui.common.validator.RequiredValidator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.ScenarioCreateEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.sal.ISalUsageController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.google.common.collect.Maps;
import com.vaadin.data.Binder;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * Window to create SAL scenario.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 09/23/2020
 *
 * @author Ihar Suvorau
 */
class CreateSalScenarioWindow extends Window {

    private final ISalUsageController controller;
    private final Binder<Scenario> scenarioBinder = new Binder<>();
    private final Binder<AtomicReference<FundPool>> fundPoolBinder = new Binder<>();
    private TextField scenarioNameField;
    private ComboBox<FundPool> fundPoolComboBox;
    private TextArea descriptionArea;

    /**
     * Constructor.
     *
     * @param controller instance of {@link ISalUsageController}
     */
    CreateSalScenarioWindow(ISalUsageController controller) {
        this.controller = controller;
        setResizable(false);
        setWidth(320, Unit.PIXELS);
        setCaption(ForeignUi.getMessage("window.create_scenario"));
        initFields();
        HorizontalLayout buttonsLayout = initButtonsLayout();
        VerticalLayout layout = new VerticalLayout(scenarioNameField, fundPoolComboBox, descriptionArea, buttonsLayout);
        layout.setSpacing(true);
        layout.setMargin(true);
        layout.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_RIGHT);
        scenarioNameField.focus();
        setContent(layout);
        VaadinUtils.addComponentStyle(this, "create-scenario-window");
        scenarioBinder.validate();
        fundPoolBinder.validate();
    }

    private void initFields() {
        initScenarioNameField();
        initFundPoolCombobox();
        initDescriptionArea();
    }

    private void initScenarioNameField() {
        scenarioNameField = new TextField(ForeignUi.getMessage("field.scenario_name"));
        scenarioNameField.setValue(
            ForeignUi.getMessage("field.scenario_name.default", controller.getSelectedProductFamily(),
                CommonDateUtils.format(LocalDate.now(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT)));
        scenarioNameField.setRequiredIndicatorVisible(true);
        scenarioBinder.forField(scenarioNameField)
            .withValidator(new RequiredValidator())
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 50), 0, 50))
            .withValidator(value -> !controller.scenarioExists(StringUtils.trimToEmpty(value)),
                ForeignUi.getMessage("message.error.unique_name", "Scenario"))
            .bind(Scenario::getName, Scenario::setName);
        VaadinUtils.setMaxComponentsWidth(scenarioNameField);
        VaadinUtils.addComponentStyle(scenarioNameField, "scenario-name");
    }

    private void initFundPoolCombobox() {
        fundPoolComboBox = new ComboBox<>(ForeignUi.getMessage("label.fund_pool"));
        fundPoolComboBox.setItems(controller.getFundPoolsNotAttachedToScenario());
        fundPoolComboBox.setItemCaptionGenerator(FundPool::getName);
        fundPoolComboBox.setRequiredIndicatorVisible(true);
        fundPoolBinder.forField(fundPoolComboBox)
            .asRequired(ForeignUi.getMessage("field.error.fund_pool.empty"))
            .bind(AtomicReference::get, AtomicReference::set);
        VaadinUtils.setMaxComponentsWidth(fundPoolComboBox);
        VaadinUtils.addComponentStyle(fundPoolComboBox, "fund-pools-filter");
    }

    private void initDescriptionArea() {
        descriptionArea = new TextArea(ForeignUi.getMessage("field.description"));
        scenarioBinder.forField(descriptionArea)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 2000), 0, 2000))
            .bind(Scenario::getDescription, Scenario::setDescription);
        VaadinUtils.setMaxComponentsWidth(descriptionArea);
        VaadinUtils.addComponentStyle(descriptionArea, "scenario-description");
    }

    private HorizontalLayout initButtonsLayout() {
        Button confirmButton = Buttons.createButton(ForeignUi.getMessage("button.confirm"));
        confirmButton.addClickListener(listener -> onConfirmButtonClicked());
        HorizontalLayout layout = new HorizontalLayout(confirmButton, Buttons.createCancelButton(this));
        layout.setSpacing(true);
        return layout;
    }

    private void onConfirmButtonClicked() {
        if (scenarioBinder.isValid() && fundPoolBinder.isValid()) {
            fundPoolComboBox.getSelectedItem().ifPresent(fundPool -> {
                if (isValidLicenseeAccount(fundPool) && isValidGradeGroups(fundPool)) {
                    fireEvent(new ScenarioCreateEvent(this,
                        controller.createSalScenario(StringUtils.trimToEmpty(scenarioNameField.getValue()),
                            fundPool.getId(), StringUtils.trimToEmpty(descriptionArea.getValue()))));
                    close();
                }
            });
        } else {
            Windows.showValidationErrorWindow(Arrays.asList(scenarioNameField, fundPoolComboBox, descriptionArea));
        }
    }

    private boolean isValidLicenseeAccount(FundPool fundPool) {
        Long usageBatchLicensee = controller.getSelectedUsageBatch().getSalFields().getLicenseeAccountNumber();
        Long fundPoolLicensee = fundPool.getSalFields().getLicenseeAccountNumber();
        if (!fundPoolLicensee.equals(usageBatchLicensee)) {
            Windows.showNotificationWindow(
                ForeignUi.getMessage("message.error.invalid_licensee_account", usageBatchLicensee, fundPoolLicensee));
            return false;
        }
        return true;
    }

    private boolean isValidGradeGroups(FundPool fundPool) {
        List<SalGradeGroupEnum> existingGradeGroups = controller.getUsageDataGradeGroups();
        Map<SalGradeGroupEnum, BigDecimal> gradeGroupAmountMap = Maps.newHashMapWithExpectedSize(3);
        gradeGroupAmountMap.put(SalGradeGroupEnum.GRADEK_5, fundPool.getSalFields().getGradeKto5GrossAmount());
        gradeGroupAmountMap.put(SalGradeGroupEnum.GRADE6_8, fundPool.getSalFields().getGrade6to8GrossAmount());
        gradeGroupAmountMap.put(SalGradeGroupEnum.GRADE9_12, fundPool.getSalFields().getGrade9to12GrossAmount());
        return isValidGradeGroupAmounts(gradeGroupAmountMap, existingGradeGroups)
            && isValidGradeGroupDetails(gradeGroupAmountMap, existingGradeGroups);
    }

    private boolean isValidGradeGroupAmounts(Map<SalGradeGroupEnum, BigDecimal> gradeGroupAmountMap,
                                             List<SalGradeGroupEnum> existingGradeGroups) {
        List<SalGradeGroupEnum> invalidGradeGroupDetails = gradeGroupAmountMap.entrySet()
            .stream()
            .filter(entry -> 0 > BigDecimal.ZERO.compareTo(entry.getValue())
                && !existingGradeGroups.contains(entry.getKey()))
            .map(Entry::getKey)
            .sorted()
            .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(invalidGradeGroupDetails)) {
            showGradeGroupErrorWindow(invalidGradeGroupDetails, "message.error.invalid_grade_group_usages");
        }
        return CollectionUtils.isEmpty(invalidGradeGroupDetails);
    }

    private boolean isValidGradeGroupDetails(Map<SalGradeGroupEnum, BigDecimal> gradeGroupAmountMap,
                                             List<SalGradeGroupEnum> existingGradeGroups) {
        List<SalGradeGroupEnum> invalidGradeGroupAmounts = existingGradeGroups.stream()
            .filter(gradeGroup -> 0 == BigDecimal.ZERO.compareTo(gradeGroupAmountMap.get(gradeGroup)))
            .sorted()
            .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(invalidGradeGroupAmounts)) {
            showGradeGroupErrorWindow(invalidGradeGroupAmounts, "message.error.invalid_grade_group_amounts");
        }
        return CollectionUtils.isEmpty(invalidGradeGroupAmounts);
    }

    private void showGradeGroupErrorWindow(List<SalGradeGroupEnum> invalidGradeGroups, String errorMessage) {
        Windows.showNotificationWindow(ForeignUi.getMessage(errorMessage,
            invalidGradeGroups.stream().map(SalGradeGroupEnum::name).collect(Collectors.joining(", "))));
    }
}
