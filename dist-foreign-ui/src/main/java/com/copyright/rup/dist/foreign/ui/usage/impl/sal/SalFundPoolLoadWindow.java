package com.copyright.rup.dist.foreign.ui.usage.impl.sal;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.ui.common.validator.AmountValidator;
import com.copyright.rup.dist.foreign.ui.common.validator.AmountZeroValidator;
import com.copyright.rup.dist.foreign.ui.common.validator.RequiredValidator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.sal.ISalUsageController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.LocalDateWidget;

import com.vaadin.data.Binder;
import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;
import com.vaadin.data.converter.StringToBigDecimalConverter;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.SerializablePredicate;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * Window for uploading a SAL Fund Pool.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 09/25/20
 *
 * @author Uladzislau Shalamitski
 */
class SalFundPoolLoadWindow extends Window {

    private static final String NOT_NUMERIC_MESSAGE = ForeignUi.getMessage("field.error.not_numeric");
    private static final BigDecimal HUNDRED = new BigDecimal("100");
    private static final int SCALE_2 = 2;
    private static final int SCALE_5 = 5;

    private final ISalUsageController usagesController;
    private final Binder<FundPool> binder = new Binder<>();

    private TextField fundPoolNameField;
    private TextField assessmentName;
    private TextField grossAmountField;
    private TextField itemBankSplitPercent;
    private TextField accountNumberField;
    private TextField licenseeNameField;
    private TextField gradeKto5NumberOfStudents;
    private TextField grade6to8NumberOfStudents;
    private TextField grade9to12NumberOfStudents;
    private TextField gradeKto5GrossAmount;
    private TextField grade6to8GrossAmount;
    private TextField grade9to12GrossAmount;
    private TextField totalAmount;
    private TextField itemBankGrossAmount;
    private LocalDateWidget dateReceived;
    private Binder.Binding<FundPool, Integer> gradeKto5NumberOfStudentsBinding;
    private Binder.Binding<FundPool, Integer> grade6to8NumberOfStudentsBinding;
    private Binder.Binding<FundPool, Integer> grade9to12NumberOfStudentsBinding;

    /**
     * Constructor.
     *
     * @param usagesController usages controller
     */
    SalFundPoolLoadWindow(ISalUsageController usagesController) {
        this.usagesController = usagesController;
        setContent(initRootLayout());
        setCaption(ForeignUi.getMessage("window.load_fund_pool"));
        setResizable(false);
        setWidth(500, Unit.PIXELS);
        setHeight(530, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "fund-pool-upload-window");
    }

    /**
     * Initiates file uploading.
     */
    void onUploadClicked() {
        if (binder.isValid()) {
            FundPool calculatedFundPool = usagesController.calculateFundPoolAmounts(buildFundPool());
            usagesController.createFundPool(calculatedFundPool);
            close();
            Windows.showNotificationWindow(ForeignUi.getMessage("message.upload_successfully_completed"));
        } else {
            Windows.showValidationErrorWindow(List.of(fundPoolNameField, assessmentName, grossAmountField,
                itemBankSplitPercent, accountNumberField, licenseeNameField, dateReceived, gradeKto5NumberOfStudents,
                grade6to8NumberOfStudents, grade9to12NumberOfStudents));
        }
    }

    private ComponentContainer initRootLayout() {
        HorizontalLayout buttonsLayout = initButtonsLayout();
        VerticalLayout rootLayout = new VerticalLayout();
        rootLayout.addComponents(
            initFundPoolNameField(),
            initAssessmentNameField(),
            initMultipleComponentsLayout(initGrossAmountField(), initSplitPercentField()),
            initLicenseeLayout(),
            initDateReceivedWidget(),
            initMultipleComponentsLayout(initGradeKto5NumberOfStudentsField(), initGradeKto5AmountField()),
            initMultipleComponentsLayout(initGrade6to8NumberOfStudentsField(), initGrade6to8AmountField()),
            initMultipleComponentsLayout(initGrade9to12NumberOfStudentsField(), initGrade9to12AmountField()),
            initMultipleComponentsLayout(initItemBankAmountField(), initTotalAmountField()),
            buttonsLayout);
        rootLayout.setMargin(new MarginInfo(true, true, false, true));
        VaadinUtils.setMaxComponentsWidth(rootLayout);
        rootLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        binder.validate();
        binder.addStatusChangeListener(event -> {
            if (areRequiredFieldsNotEmpty() && event.getBinder().isValid()) {
                FundPool fundPool = usagesController.calculateFundPoolAmounts(buildFundPool());
                totalAmount.setValue(fundPool.getTotalAmount().toString());
                itemBankGrossAmount.setValue(fundPool.getSalFields().getItemBankGrossAmount().toString());
                gradeKto5GrossAmount.setValue(fundPool.getSalFields().getGradeKto5GrossAmount().toString());
                grade6to8GrossAmount.setValue(fundPool.getSalFields().getGrade6to8GrossAmount().toString());
                grade9to12GrossAmount.setValue(fundPool.getSalFields().getGrade9to12GrossAmount().toString());
            } else {
                totalAmount.clear();
                itemBankGrossAmount.clear();
                gradeKto5GrossAmount.clear();
                grade6to8GrossAmount.clear();
                grade9to12GrossAmount.clear();
            }
        });
        return rootLayout;
    }

    private HorizontalLayout initButtonsLayout() {
        Button closeButton = Buttons.createCloseButton(this);
        Button uploadButton = Buttons.createButton(ForeignUi.getMessage("button.upload"));
        uploadButton.addClickListener(event -> onUploadClicked());
        VaadinUtils.setButtonsAutoDisabled(uploadButton);
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponents(uploadButton, closeButton);
        return horizontalLayout;
    }

    private HorizontalLayout initMultipleComponentsLayout(Component... components) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponents(components);
        horizontalLayout.setSizeFull();
        return horizontalLayout;
    }

    private TextField initFundPoolNameField() {
        fundPoolNameField = new TextField(ForeignUi.getMessage("label.fund_pool.name"));
        binder.forField(fundPoolNameField)
            .withValidator(new RequiredValidator())
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 50), 0, 50))
            .withValidator(value -> !usagesController.fundPoolExists(StringUtils.trimToEmpty(value)),
                ForeignUi.getMessage("message.error.unique_name", ForeignUi.getMessage("label.fund_pool")))
            .bind(FundPool::getName, FundPool::setName);
        fundPoolNameField.setRequiredIndicatorVisible(true);
        fundPoolNameField.setSizeFull();
        VaadinUtils.setMaxComponentsWidth(fundPoolNameField);
        VaadinUtils.addComponentStyle(fundPoolNameField, "fund-pool-name-field");
        return fundPoolNameField;
    }

    private TextField initAssessmentNameField() {
        assessmentName = new TextField(ForeignUi.getMessage("label.fund_pool.assessment_name"));
        binder.forField(assessmentName)
            .withValidator(new RequiredValidator())
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 255), 0, 255))
            .bind(fundPool -> fundPool.getSalFields().getAssessmentName(),
                (fundPool, string) -> fundPool.getSalFields().setAssessmentName(string));
        assessmentName.setRequiredIndicatorVisible(true);
        assessmentName.setSizeFull();
        VaadinUtils.setMaxComponentsWidth(assessmentName);
        VaadinUtils.addComponentStyle(assessmentName, "assessment-name-field");
        return assessmentName;
    }

    private TextField initGrossAmountField() {
        grossAmountField = new TextField(ForeignUi.getMessage("label.gross_amount"));
        binder.forField(grossAmountField)
            .withValidator(new RequiredValidator())
            .withValidator(new AmountZeroValidator())
            .withConverter(new StringToBigDecimalConverter(NOT_NUMERIC_MESSAGE))
            .bind(fundPool -> fundPool.getSalFields().getGrossAmount(),
                (fundPool, amount) -> fundPool.getSalFields().setGrossAmount(amount));
        grossAmountField.setRequiredIndicatorVisible(true);
        VaadinUtils.setMaxComponentsWidth(grossAmountField);
        VaadinUtils.addComponentStyle(grossAmountField, "gross-amount-field");
        return grossAmountField;
    }

    private TextField initSplitPercentField() {
        itemBankSplitPercent = new TextField(ForeignUi.getMessage("label.fund_pool.item_bank_split_percent"));
        binder.forField(itemBankSplitPercent)
            .withValidator(new RequiredValidator())
            .withConverter(new StringToBigDecimalConverter(NOT_NUMERIC_MESSAGE))
            .withValidator(itemBankSplitPercentValidator(),
                ForeignUi.getMessage("field.error.range_and_one_decimal_place"))
            .bind(fundPool -> fundPool.getSalFields().getItemBankSplitPercent(),
                (fundPool, percent) -> fundPool.getSalFields().setItemBankSplitPercent(percent));
        itemBankSplitPercent.setRequiredIndicatorVisible(true);
        itemBankSplitPercent.setSizeFull();
        itemBankSplitPercent.addValueChangeListener(event -> validateGradeNumberOfStudentsFields());
        VaadinUtils.setMaxComponentsWidth(itemBankSplitPercent);
        VaadinUtils.addComponentStyle(itemBankSplitPercent, "item-bank-split-percent-field");
        return itemBankSplitPercent;
    }

    private TextField initGradeKto5NumberOfStudentsField() {
        gradeKto5NumberOfStudents = new TextField(ForeignUi.getMessage("label.fund_pool.grade_k_5_number_of_students"));
        gradeKto5NumberOfStudentsBinding = binder.forField(gradeKto5NumberOfStudents)
            .withValidator(new RequiredValidator())
            .withValidator(new AmountValidator())
            .withConverter(new StringToIntegerConverter(NOT_NUMERIC_MESSAGE))
            .withValidator(gradeNumberOfStudentsAllZeroValidator(),
                ForeignUi.getMessage("field.error.number_of_students_not_zero_when_item_bank_split_percent_100"))
            .withValidator(gradeNumberOfStudentsAtLeastOneNotZeroValidator(),
                ForeignUi.getMessage("field.error.number_of_students_not_all_zero"))
            .bind(fundPool -> fundPool.getSalFields().getGradeKto5NumberOfStudents(),
                (fundPool, number) -> fundPool.getSalFields().setGradeKto5NumberOfStudents(number));
        gradeKto5NumberOfStudents.addValueChangeListener(event -> validateGradeNumberOfStudentsFields());
        gradeKto5NumberOfStudents.setRequiredIndicatorVisible(true);
        gradeKto5NumberOfStudents.setSizeFull();
        VaadinUtils.setMaxComponentsWidth(gradeKto5NumberOfStudents);
        VaadinUtils.addComponentStyle(gradeKto5NumberOfStudents, "grade-k-5-number-of-students-field");
        return gradeKto5NumberOfStudents;
    }

    private TextField initGrade6to8NumberOfStudentsField() {
        grade6to8NumberOfStudents = new TextField(ForeignUi.getMessage("label.fund_pool.grade_6_8_number_of_students"));
        grade6to8NumberOfStudentsBinding = binder.forField(grade6to8NumberOfStudents)
            .withValidator(new RequiredValidator())
            .withValidator(new AmountValidator())
            .withConverter(new StringToIntegerConverter(NOT_NUMERIC_MESSAGE))
            .withValidator(gradeNumberOfStudentsAllZeroValidator(),
                ForeignUi.getMessage("field.error.number_of_students_not_zero_when_item_bank_split_percent_100"))
            .withValidator(gradeNumberOfStudentsAtLeastOneNotZeroValidator(),
                ForeignUi.getMessage("field.error.number_of_students_not_all_zero"))
            .bind(fundPool -> fundPool.getSalFields().getGrade6to8NumberOfStudents(),
                (fundPool, number) -> fundPool.getSalFields().setGrade6to8NumberOfStudents(number));
        grade6to8NumberOfStudents.addValueChangeListener(event -> validateGradeNumberOfStudentsFields());
        grade6to8NumberOfStudents.setRequiredIndicatorVisible(true);
        grade6to8NumberOfStudents.setSizeFull();
        VaadinUtils.setMaxComponentsWidth(grade6to8NumberOfStudents);
        VaadinUtils.addComponentStyle(grade6to8NumberOfStudents, "grade-6-8-number-of-students-field");
        return grade6to8NumberOfStudents;
    }

    private TextField initGrade9to12NumberOfStudentsField() {
        grade9to12NumberOfStudents =
            new TextField(ForeignUi.getMessage("label.fund_pool.grade_9_12_number_of_students"));
        grade9to12NumberOfStudentsBinding = binder.forField(grade9to12NumberOfStudents)
            .withValidator(new RequiredValidator())
            .withValidator(new AmountValidator())
            .withConverter(new StringToIntegerConverter(NOT_NUMERIC_MESSAGE))
            .withValidator(gradeNumberOfStudentsAllZeroValidator(),
                ForeignUi.getMessage("field.error.number_of_students_not_zero_when_item_bank_split_percent_100"))
            .withValidator(gradeNumberOfStudentsAtLeastOneNotZeroValidator(),
                ForeignUi.getMessage("field.error.number_of_students_not_all_zero"))
            .bind(fundPool -> fundPool.getSalFields().getGrade9to12NumberOfStudents(),
                (fundPool, number) -> fundPool.getSalFields().setGrade9to12NumberOfStudents(number));
        grade9to12NumberOfStudents.addValueChangeListener(event -> validateGradeNumberOfStudentsFields());
        grade9to12NumberOfStudents.setRequiredIndicatorVisible(true);
        grade9to12NumberOfStudents.setSizeFull();
        VaadinUtils.setMaxComponentsWidth(grade9to12NumberOfStudents);
        VaadinUtils.addComponentStyle(grade9to12NumberOfStudents, "grade-9-12-number-of-students-field");
        return grade9to12NumberOfStudents;
    }

    private LocalDateWidget initDateReceivedWidget() {
        dateReceived = new LocalDateWidget(ForeignUi.getMessage("label.fund_pool.date_received"));
        binder.forField(dateReceived)
            .asRequired(ForeignUi.getMessage("field.error.empty"))
            .bind(fundPool -> fundPool.getSalFields().getDateReceived(),
                (fundPool, date) -> fundPool.getSalFields().setDateReceived(date));
        VaadinUtils.setMaxComponentsWidth(accountNumberField);
        VaadinUtils.addComponentStyle(dateReceived, "date-received-field");
        return dateReceived;
    }

    private TextField initGradeKto5AmountField() {
        gradeKto5GrossAmount = new TextField(ForeignUi.getMessage("label.fund_pool.grade_k_5_gross_amount"));
        gradeKto5GrossAmount.setSizeFull();
        gradeKto5GrossAmount.setReadOnly(true);
        VaadinUtils.setMaxComponentsWidth(gradeKto5GrossAmount);
        VaadinUtils.addComponentStyle(gradeKto5GrossAmount, "grade-k-5-gross-amount-field");
        return gradeKto5GrossAmount;
    }

    private TextField initGrade6to8AmountField() {
        grade6to8GrossAmount = new TextField(ForeignUi.getMessage("label.fund_pool.grade_6_8_gross_amount"));
        grade6to8GrossAmount.setSizeFull();
        grade6to8GrossAmount.setReadOnly(true);
        VaadinUtils.setMaxComponentsWidth(grade6to8GrossAmount);
        VaadinUtils.addComponentStyle(grade6to8GrossAmount, "grade-6-8-gross-amount-field");
        return grade6to8GrossAmount;
    }

    private TextField initGrade9to12AmountField() {
        grade9to12GrossAmount = new TextField(ForeignUi.getMessage("label.fund_pool.grade_9_12_gross_amount"));
        grade9to12GrossAmount.setSizeFull();
        grade9to12GrossAmount.setReadOnly(true);
        VaadinUtils.setMaxComponentsWidth(grade9to12GrossAmount);
        VaadinUtils.addComponentStyle(grade9to12GrossAmount, "grade-9-12-gross-amount-field");
        return grade9to12GrossAmount;
    }

    private TextField initItemBankAmountField() {
        itemBankGrossAmount = new TextField(ForeignUi.getMessage("label.fund_pool.item_bank_gross_amount"));
        itemBankGrossAmount.setSizeFull();
        itemBankGrossAmount.setReadOnly(true);
        VaadinUtils.setMaxComponentsWidth(itemBankGrossAmount);
        VaadinUtils.addComponentStyle(itemBankGrossAmount, "item-bank-amount-field");
        return itemBankGrossAmount;
    }

    private TextField initTotalAmountField() {
        totalAmount = new TextField(ForeignUi.getMessage("label.total_gross_amount"));
        totalAmount.setSizeFull();
        totalAmount.setReadOnly(true);
        VaadinUtils.setMaxComponentsWidth(totalAmount);
        VaadinUtils.addComponentStyle(totalAmount, "total-amount-field");
        return totalAmount;
    }

    private VerticalLayout initLicenseeLayout() {
        VerticalLayout licenseeLayout = new VerticalLayout();
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        TextField accountNumber = initLicenseeAccountNumberField();
        TextField licenseeName = initLicenseeNameField();
        Button verifyButton = getVerifyButton();
        verifyButton.setWidth(72, Unit.PIXELS);
        horizontalLayout.addComponents(accountNumber, verifyButton);
        horizontalLayout.setComponentAlignment(verifyButton, Alignment.BOTTOM_RIGHT);
        horizontalLayout.setExpandRatio(accountNumber, 1);
        horizontalLayout.setSizeFull();
        licenseeLayout.addComponents(horizontalLayout, licenseeName);
        licenseeLayout.setMargin(false);
        licenseeLayout.setSizeFull();
        return licenseeLayout;
    }

    private TextField initLicenseeAccountNumberField() {
        accountNumberField = new TextField(ForeignUi.getMessage("label.licensee_account_number"));
        accountNumberField.setRequiredIndicatorVisible(true);
        binder.forField(accountNumberField)
            .withValidator(new RequiredValidator())
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.number_length", 10), 0, 10))
            .withValidator(value -> StringUtils.isNumeric(StringUtils.trim(value)), NOT_NUMERIC_MESSAGE)
            .bind(fundPool -> fundPool.getSalFields().getLicenseeAccountNumber().toString(),
                (fundPool, string) -> fundPool.getSalFields().setLicenseeAccountNumber(Long.valueOf(string)));
        VaadinUtils.setMaxComponentsWidth(accountNumberField);
        VaadinUtils.addComponentStyle(accountNumberField, "licensee-account-number-field");
        accountNumberField.addValueChangeListener(event -> licenseeNameField.setValue(StringUtils.EMPTY));
        return accountNumberField;
    }

    private TextField initLicenseeNameField() {
        licenseeNameField = new TextField(ForeignUi.getMessage("label.licensee_name"));
        licenseeNameField.setRequiredIndicatorVisible(true);
        licenseeNameField.setReadOnly(true);
        binder.forField(licenseeNameField)
            .asRequired(ForeignUi.getMessage("field.error.licensee_name.empty"))
            .bind(fundPool -> fundPool.getSalFields().getLicenseeName(),
                (fundPool, string) -> fundPool.getSalFields().setLicenseeName(string));
        VaadinUtils.setMaxComponentsWidth(licenseeNameField);
        VaadinUtils.addComponentStyle(licenseeNameField, "licensee-name-field");
        return licenseeNameField;
    }

    private Button getVerifyButton() {
        Button button = Buttons.createButton(ForeignUi.getMessage("button.verify"));
        button.addClickListener(event -> {
            if (Objects.isNull(accountNumberField.getErrorMessage())) {
                String licenseeName =
                    usagesController.getLicenseeName(Long.valueOf(StringUtils.trim(accountNumberField.getValue())));
                if (StringUtils.isNotBlank(licenseeName)) {
                    licenseeNameField.setValue(licenseeName);
                } else {
                    licenseeNameField.clear();
                }
            }
        });
        return button;
    }

    private SerializablePredicate<BigDecimal> itemBankSplitPercentValidator() {
        return value -> 0 > BigDecimal.ZERO.compareTo(value)
            && 0 <= HUNDRED.compareTo(value)
            && 1 >= value.scale();
    }

    private SerializablePredicate<Integer> gradeNumberOfStudentsAllZeroValidator() {
        return value -> isItemBankSplitPercentNotEqualToHundred() || 0 == value;
    }

    private SerializablePredicate<Integer> gradeNumberOfStudentsAtLeastOneNotZeroValidator() {
        return value -> {
            String gradeKto5Number = gradeKto5NumberOfStudents.getValue();
            String grade6to8Number = grade6to8NumberOfStudents.getValue();
            String grade9to12Number = grade9to12NumberOfStudents.getValue();
            if (isItemBankSplitPercentNotEqualToHundred()
                && StringUtils.isNumeric(gradeKto5Number) && 10 >= StringUtils.length(gradeKto5Number)
                && StringUtils.isNumeric(grade6to8Number) && 10 >= StringUtils.length(grade6to8Number)
                && StringUtils.isNumeric(grade9to12Number) && 10 >= StringUtils.length(grade9to12Number)) {
                return 0 < Integer.parseInt(gradeKto5NumberOfStudents.getValue()) ||
                    0 < Integer.parseInt(grade6to8NumberOfStudents.getValue()) ||
                    0 < Integer.parseInt(grade9to12NumberOfStudents.getValue());
            } else {
                return true;
            }
        };
    }

    private boolean isItemBankSplitPercentNotEqualToHundred() {
        if (StringUtils.isNotBlank(itemBankSplitPercent.getValue())) {
            Result<BigDecimal> decimalResult = new StringToBigDecimalConverter(StringUtils.EMPTY)
                .convertToModel(itemBankSplitPercent.getValue(), new ValueContext());
            return decimalResult.isError() ||
                0 != HUNDRED.compareTo(decimalResult.getOrThrow(s -> new RupRuntimeException()));
        } else {
            return true;
        }
    }

    private FundPool buildFundPool() {
        FundPool fundPool = new FundPool();
        fundPool.setName(StringUtils.trimToEmpty(fundPoolNameField.getValue()));
        FundPool.SalFields salFields = new FundPool.SalFields();
        salFields.setAssessmentName(StringUtils.trimToEmpty(assessmentName.getValue()));
        salFields.setGrossAmount(new BigDecimal(StringUtils.trimToEmpty(grossAmountField.getValue()))
            .setScale(SCALE_2, BigDecimal.ROUND_HALF_UP));
        salFields.setItemBankSplitPercent(new BigDecimal(StringUtils.trimToEmpty(itemBankSplitPercent.getValue()))
            .divide(HUNDRED, SCALE_5, BigDecimal.ROUND_HALF_UP));
        salFields.setDateReceived(dateReceived.getValue());
        salFields.setLicenseeAccountNumber(Long.valueOf(StringUtils.trimToEmpty(accountNumberField.getValue())));
        salFields.setLicenseeName(licenseeNameField.getValue());
        salFields.setGradeKto5NumberOfStudents(
            Integer.parseInt(StringUtils.trimToEmpty(gradeKto5NumberOfStudents.getValue())));
        salFields.setGrade6to8NumberOfStudents(
            Integer.parseInt(StringUtils.trimToEmpty(grade6to8NumberOfStudents.getValue())));
        salFields.setGrade9to12NumberOfStudents(
            Integer.parseInt(StringUtils.trimToEmpty(grade9to12NumberOfStudents.getValue())));
        fundPool.setSalFields(salFields);
        return fundPool;
    }

    private boolean areRequiredFieldsNotEmpty() {
        return StringUtils.isNotBlank(fundPoolNameField.getValue())
            && StringUtils.isNotBlank(assessmentName.getValue())
            && StringUtils.isNotBlank(grossAmountField.getValue())
            && StringUtils.isNotBlank(itemBankSplitPercent.getValue())
            && StringUtils.isNotBlank(accountNumberField.getValue())
            && StringUtils.isNotBlank(licenseeNameField.getValue())
            && StringUtils.isNotBlank(gradeKto5NumberOfStudents.getValue())
            && StringUtils.isNotBlank(grade6to8NumberOfStudents.getValue())
            && StringUtils.isNotBlank(grade9to12NumberOfStudents.getValue())
            && !dateReceived.isEmpty();
    }

    private void validateGradeNumberOfStudentsFields() {
        gradeKto5NumberOfStudentsBinding.validate();
        grade6to8NumberOfStudentsBinding.validate();
        grade9to12NumberOfStudentsBinding.validate();
    }
}
