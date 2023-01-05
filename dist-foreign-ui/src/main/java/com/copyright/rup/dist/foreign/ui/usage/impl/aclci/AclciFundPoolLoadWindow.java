package com.copyright.rup.dist.foreign.ui.usage.impl.aclci;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.FundPool.AclciFields;
import com.copyright.rup.dist.foreign.ui.common.validator.AmountValidator;
import com.copyright.rup.dist.foreign.ui.common.validator.AmountZeroValidator;
import com.copyright.rup.dist.foreign.ui.common.validator.CoverageYearsValidator;
import com.copyright.rup.dist.foreign.ui.common.validator.RequiredValidator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.aclci.IAclciUsageController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.vaadin.data.Binder;
import com.vaadin.data.Result;
import com.vaadin.data.StatusChangeEvent;
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
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * Window for loading ACLCI fund pool.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 12/29/2022
 *
 * @author Aliaksandr Liakh
 */
class AclciFundPoolLoadWindow extends Window {

    private static final String NOT_NUMERIC_MESSAGE = ForeignUi.getMessage("field.error.not_numeric");
    private static final String NUMBER_OF_STUDENTS_NOT_ZERO_MESSAGE =
        ForeignUi.getMessage("field.error.number_of_students_not_zero_when_curriculum_db_split_percent_100");
    private static final String NUMBER_OF_STUDENTS_NOT_ALL_ZERO_MESSAGE =
        ForeignUi.getMessage("field.error.number_of_students_not_all_zero");
    private static final BigDecimal HUNDRED = new BigDecimal("100");
    private static final int SCALE_2 = 2;
    private static final int SCALE_5 = 5;

    private final IAclciUsageController usageController;
    private final Binder<FundPool> binder = new Binder<>();

    private TextField fundPoolName;
    private TextField coverageYears;
    private TextField grossAmount;
    private TextField curriculumDbSplitPercent;
    private TextField gradeKto2NumberOfStudents;
    private TextField grade3to5NumberOfStudents;
    private TextField grade6to8NumberOfStudents;
    private TextField grade9to12NumberOfStudents;
    private TextField gradeHeNumberOfStudents;
    private TextField gradeKto2GrossAmount;
    private TextField grade3to5GrossAmount;
    private TextField grade6to8GrossAmount;
    private TextField grade9to12GrossAmount;
    private TextField gradeHeGrossAmount;
    private TextField curriculumDbGrossAmount;
    private TextField totalGrossAmount;
    //TODO: try to remove
    private Binder.Binding<FundPool, Integer> gradeKto2NumberOfStudentsBinding;
    private Binder.Binding<FundPool, Integer> grade3to5NumberOfStudentsBinding;
    private Binder.Binding<FundPool, Integer> grade6to8NumberOfStudentsBinding;
    private Binder.Binding<FundPool, Integer> grade9to12NumberOfStudentsBinding;
    private Binder.Binding<FundPool, Integer> gradeHeNumberOfStudentsBinding;

    /**
     * Constructor.
     *
     * @param usageController instance of {@link IAclciUsageController}
     */
    AclciFundPoolLoadWindow(IAclciUsageController usageController) {
        this.usageController = usageController;
        setContent(initRootLayout());
        setCaption(ForeignUi.getMessage("window.load_fund_pool"));
        setResizable(false);
        setWidth(500, Unit.PIXELS);
        setHeight(495, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "fund-pool-upload-window");
    }

    /**
     * Handles Upload button click.
     */
    void onUploadClicked() {
        if (binder.isValid()) {
            FundPool calculatedFundPool = usageController.calculateAclciFundPoolAmounts(buildFundPool());
            usageController.createAclciFundPool(calculatedFundPool);
            close();
            Windows.showNotificationWindow(ForeignUi.getMessage("message.upload_successfully_completed"));
        } else {
            Windows.showValidationErrorWindow(List.of(fundPoolName, coverageYears, grossAmount,
                curriculumDbSplitPercent, gradeKto2NumberOfStudents, grade3to5NumberOfStudents,
                grade6to8NumberOfStudents, grade9to12NumberOfStudents, gradeHeNumberOfStudents));
        }
    }

    private ComponentContainer initRootLayout() {
        HorizontalLayout buttonsLayout = initButtonsLayout();
        VerticalLayout rootLayout = new VerticalLayout();
        rootLayout.addComponents(
            initFundPoolNameField(),
            initCoverageYearsField(),
            initMultipleComponentsLayout(initGrossAmountField(), initCurriculumDbSplitPercentField()),
            initMultipleComponentsLayout(initGradeKto2NumberOfStudentsField(), initGradeKto2AmountField()),
            initMultipleComponentsLayout(initGrade3to5NumberOfStudentsField(), initGrade3to5AmountField()),
            initMultipleComponentsLayout(initGrade6to8NumberOfStudentsField(), initGrade6to8AmountField()),
            initMultipleComponentsLayout(initGrade9to12NumberOfStudentsField(), initGrade9to12AmountField()),
            initMultipleComponentsLayout(initGradeHeNumberOfStudentsField(), initGradeHeAmountField()),
            initMultipleComponentsLayout(initCurriculumDbAmountField(), initTotalGrossAmountField()),
            buttonsLayout);
        rootLayout.setMargin(new MarginInfo(true, true, false, true));
        VaadinUtils.setMaxComponentsWidth(rootLayout);
        rootLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        FundPool fundPool = new FundPool();
        fundPool.setAclciFields(new AclciFields());
        binder.setBean(fundPool);
        binder.validate();
        binder.addStatusChangeListener(this::binderStatusChangeListener);
        return rootLayout;
    }

    private HorizontalLayout initButtonsLayout() {
        Button closeButton = Buttons.createCloseButton(this);
        Button uploadButton = Buttons.createButton(ForeignUi.getMessage("button.upload"));
        uploadButton.addClickListener(event -> onUploadClicked());
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
        fundPoolName = new TextField(ForeignUi.getMessage("label.fund_pool.name"));
        binder.forField(fundPoolName)
            .withValidator(new RequiredValidator())
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 50), 0, 50))
            .withValidator(value -> !usageController.aclciFundPoolExists(StringUtils.trimToEmpty(value)),
                ForeignUi.getMessage("message.error.unique_name", ForeignUi.getMessage("label.fund_pool")))
            .bind(FundPool::getName, FundPool::setName);
        fundPoolName.setRequiredIndicatorVisible(true);
        fundPoolName.setSizeFull();
        VaadinUtils.setMaxComponentsWidth(fundPoolName);
        VaadinUtils.addComponentStyle(fundPoolName, "fund-pool-name-field");
        return fundPoolName;
    }

    private TextField initCoverageYearsField() {
        coverageYears = new TextField(ForeignUi.getMessage("label.fund_pool.coverage_years"));
        binder.forField(coverageYears)
            .withValidator(new RequiredValidator())
            .withValidator(new CoverageYearsValidator(ForeignUi.getMessage("field.error.coverage_years")))
            .bind(fundPool -> fundPool.getAclciFields().getCoverageYears(),
                (fundPool, string) -> fundPool.getAclciFields().setCoverageYears(string));
        coverageYears.setRequiredIndicatorVisible(true);
        coverageYears.setSizeFull();
        VaadinUtils.setMaxComponentsWidth(coverageYears);
        VaadinUtils.addComponentStyle(coverageYears, "coverage-years-field");
        return coverageYears;
    }

    private TextField initGrossAmountField() {
        grossAmount = new TextField(ForeignUi.getMessage("label.gross_amount"));
        binder.forField(grossAmount)
            .withValidator(new RequiredValidator())
            .withValidator(new AmountZeroValidator())
            .bind(fundPool -> Objects.toString(fundPool.getAclciFields().getGrossAmount(), StringUtils.EMPTY),
                (fundPool, value) -> fundPool.getAclciFields().setGrossAmount(
                    NumberUtils.createBigDecimal(StringUtils.trimToNull(value))));
        grossAmount.setRequiredIndicatorVisible(true);
        VaadinUtils.setMaxComponentsWidth(grossAmount);
        VaadinUtils.addComponentStyle(grossAmount, "gross-amount-field");
        return grossAmount;
    }

    private TextField initCurriculumDbSplitPercentField() {
        curriculumDbSplitPercent = new TextField(ForeignUi.getMessage("label.fund_pool.curriculum_db_split_percent"));
        binder.forField(curriculumDbSplitPercent)
            .withValidator(new RequiredValidator())
            .withConverter(new StringToBigDecimalConverter(NOT_NUMERIC_MESSAGE))
            .withValidator(curriculumSplitPercentValidator(),
                ForeignUi.getMessage("field.error.range_and_one_decimal_place"))
            .bind(fundPool -> fundPool.getAclciFields().getCurriculumSplitPercent(),
                (fundPool, percent) -> fundPool.getAclciFields().setCurriculumSplitPercent(percent));
        curriculumDbSplitPercent.setRequiredIndicatorVisible(true);
        curriculumDbSplitPercent.setSizeFull();
        curriculumDbSplitPercent.addValueChangeListener(event -> validateGradeNumberOfStudentsFields());
        VaadinUtils.setMaxComponentsWidth(curriculumDbSplitPercent);
        VaadinUtils.addComponentStyle(curriculumDbSplitPercent, "curriculum-split-percent-field");
        return curriculumDbSplitPercent;
    }

    private TextField initGradeKto2NumberOfStudentsField() {
        gradeKto2NumberOfStudents = new TextField(ForeignUi.getMessage("label.fund_pool.grade_k_2_number_of_students"));
        gradeKto2NumberOfStudentsBinding = binder.forField(gradeKto2NumberOfStudents)
            .withValidator(new RequiredValidator())
            .withValidator(new AmountValidator())
            .withConverter(new StringToIntegerConverter(NOT_NUMERIC_MESSAGE))
            .withValidator(gradeNumberOfStudentsAllZeroValidator(), NUMBER_OF_STUDENTS_NOT_ZERO_MESSAGE)
            .withValidator(gradeNumberOfStudentsAtLeastOneNotZeroValidator(), NUMBER_OF_STUDENTS_NOT_ALL_ZERO_MESSAGE)
            .bind(fundPool -> fundPool.getAclciFields().getGradeKto2NumberOfStudents(),
                (fundPool, value) -> fundPool.getAclciFields().setGradeKto2NumberOfStudents(value));
        gradeKto2NumberOfStudents.addValueChangeListener(event -> validateGradeNumberOfStudentsFields());
        gradeKto2NumberOfStudents.setRequiredIndicatorVisible(true);
        gradeKto2NumberOfStudents.setSizeFull();
        VaadinUtils.setMaxComponentsWidth(gradeKto2NumberOfStudents);
        VaadinUtils.addComponentStyle(gradeKto2NumberOfStudents, "grade-k-2-number-of-students-field");
        return gradeKto2NumberOfStudents;
    }

    private TextField initGrade3to5NumberOfStudentsField() {
        grade3to5NumberOfStudents = new TextField(ForeignUi.getMessage("label.fund_pool.grade_3_5_number_of_students"));
        grade3to5NumberOfStudentsBinding = binder.forField(grade3to5NumberOfStudents)
            .withValidator(new RequiredValidator())
            .withValidator(new AmountValidator())
            .withConverter(new StringToIntegerConverter(NOT_NUMERIC_MESSAGE))
            .withValidator(gradeNumberOfStudentsAllZeroValidator(), NUMBER_OF_STUDENTS_NOT_ZERO_MESSAGE)
            .withValidator(gradeNumberOfStudentsAtLeastOneNotZeroValidator(), NUMBER_OF_STUDENTS_NOT_ALL_ZERO_MESSAGE)
            .bind(fundPool -> fundPool.getAclciFields().getGrade3to5NumberOfStudents(),
                (fundPool, value) -> fundPool.getAclciFields().setGrade3to5NumberOfStudents(value));
        grade3to5NumberOfStudents.addValueChangeListener(event -> validateGradeNumberOfStudentsFields());
        grade3to5NumberOfStudents.setRequiredIndicatorVisible(true);
        grade3to5NumberOfStudents.setSizeFull();
        VaadinUtils.setMaxComponentsWidth(grade3to5NumberOfStudents);
        VaadinUtils.addComponentStyle(grade3to5NumberOfStudents, "grade-3-5-number-of-students-field");
        return grade3to5NumberOfStudents;
    }

    private TextField initGrade6to8NumberOfStudentsField() {
        grade6to8NumberOfStudents = new TextField(ForeignUi.getMessage("label.fund_pool.grade_6_8_number_of_students"));
        grade6to8NumberOfStudentsBinding = binder.forField(grade6to8NumberOfStudents)
            .withValidator(new RequiredValidator())
            .withValidator(new AmountValidator())
            .withConverter(new StringToIntegerConverter(NOT_NUMERIC_MESSAGE))
            .withValidator(gradeNumberOfStudentsAllZeroValidator(), NUMBER_OF_STUDENTS_NOT_ZERO_MESSAGE)
            .withValidator(gradeNumberOfStudentsAtLeastOneNotZeroValidator(), NUMBER_OF_STUDENTS_NOT_ALL_ZERO_MESSAGE)
            .bind(fundPool -> fundPool.getAclciFields().getGrade6to8NumberOfStudents(),
                (fundPool, value) -> fundPool.getAclciFields().setGrade6to8NumberOfStudents(value));
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
            .withValidator(gradeNumberOfStudentsAllZeroValidator(), NUMBER_OF_STUDENTS_NOT_ZERO_MESSAGE)
            .withValidator(gradeNumberOfStudentsAtLeastOneNotZeroValidator(), NUMBER_OF_STUDENTS_NOT_ALL_ZERO_MESSAGE)
            .bind(fundPool -> fundPool.getAclciFields().getGrade9to12NumberOfStudents(),
                (fundPool, value) -> fundPool.getAclciFields().setGrade9to12NumberOfStudents(value));
        grade9to12NumberOfStudents.addValueChangeListener(event -> validateGradeNumberOfStudentsFields());
        grade9to12NumberOfStudents.setRequiredIndicatorVisible(true);
        grade9to12NumberOfStudents.setSizeFull();
        VaadinUtils.setMaxComponentsWidth(grade9to12NumberOfStudents);
        VaadinUtils.addComponentStyle(grade9to12NumberOfStudents, "grade-9-12-number-of-students-field");
        return grade9to12NumberOfStudents;
    }

    private TextField initGradeHeNumberOfStudentsField() {
        gradeHeNumberOfStudents =
            new TextField(ForeignUi.getMessage("label.fund_pool.grade_he_number_of_students"));
        gradeHeNumberOfStudentsBinding = binder.forField(gradeHeNumberOfStudents)
            .withValidator(new RequiredValidator())
            .withValidator(new AmountValidator())
            .withConverter(new StringToIntegerConverter(NOT_NUMERIC_MESSAGE))
            .withValidator(gradeNumberOfStudentsAllZeroValidator(), NUMBER_OF_STUDENTS_NOT_ZERO_MESSAGE)
            .withValidator(gradeNumberOfStudentsAtLeastOneNotZeroValidator(), NUMBER_OF_STUDENTS_NOT_ALL_ZERO_MESSAGE)
            .bind(fundPool -> fundPool.getAclciFields().getGradeHeNumberOfStudents(),
                (fundPool, value) -> fundPool.getAclciFields().setGradeHeNumberOfStudents(value));
        gradeHeNumberOfStudents.addValueChangeListener(event -> validateGradeNumberOfStudentsFields());
        gradeHeNumberOfStudents.setRequiredIndicatorVisible(true);
        gradeHeNumberOfStudents.setSizeFull();
        VaadinUtils.setMaxComponentsWidth(gradeHeNumberOfStudents);
        VaadinUtils.addComponentStyle(gradeHeNumberOfStudents, "grade-he-number-of-students-field");
        return gradeHeNumberOfStudents;
    }

    private TextField initGradeKto2AmountField() {
        gradeKto2GrossAmount = new TextField(ForeignUi.getMessage("label.fund_pool.grade_k_2_gross_amount"));
        gradeKto2GrossAmount.setSizeFull();
        gradeKto2GrossAmount.setReadOnly(true);
        VaadinUtils.setMaxComponentsWidth(gradeKto2GrossAmount);
        VaadinUtils.addComponentStyle(gradeKto2GrossAmount, "grade-k-2-gross-amount-field");
        return gradeKto2GrossAmount;
    }

    private TextField initGrade3to5AmountField() {
        grade3to5GrossAmount = new TextField(ForeignUi.getMessage("label.fund_pool.grade_3_5_gross_amount"));
        grade3to5GrossAmount.setSizeFull();
        grade3to5GrossAmount.setReadOnly(true);
        VaadinUtils.setMaxComponentsWidth(grade3to5GrossAmount);
        VaadinUtils.addComponentStyle(grade3to5GrossAmount, "grade-3-5-gross-amount-field");
        return grade3to5GrossAmount;
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

    private TextField initGradeHeAmountField() {
        gradeHeGrossAmount = new TextField(ForeignUi.getMessage("label.fund_pool.grade_he_gross_amount"));
        gradeHeGrossAmount.setSizeFull();
        gradeHeGrossAmount.setReadOnly(true);
        VaadinUtils.setMaxComponentsWidth(gradeHeGrossAmount);
        VaadinUtils.addComponentStyle(gradeHeGrossAmount, "grade-he-gross-amount-field");
        return gradeHeGrossAmount;
    }

    private TextField initCurriculumDbAmountField() {
        curriculumDbGrossAmount = new TextField(ForeignUi.getMessage("label.fund_pool.curriculum_db_gross_amount"));
        curriculumDbGrossAmount.setSizeFull();
        curriculumDbGrossAmount.setReadOnly(true);
        VaadinUtils.setMaxComponentsWidth(curriculumDbGrossAmount);
        VaadinUtils.addComponentStyle(curriculumDbGrossAmount, "curriculum-db-gross-amount-field");
        return curriculumDbGrossAmount;
    }

    private TextField initTotalGrossAmountField() {
        totalGrossAmount = new TextField(ForeignUi.getMessage("label.total_gross_amount"));
        totalGrossAmount.setSizeFull();
        totalGrossAmount.setReadOnly(true);
        VaadinUtils.setMaxComponentsWidth(totalGrossAmount);
        VaadinUtils.addComponentStyle(totalGrossAmount, "total-gross-amount-field");
        return totalGrossAmount;
    }

    private SerializablePredicate<BigDecimal> curriculumSplitPercentValidator() {
        return value -> 0 > BigDecimal.ZERO.compareTo(value)
            && 0 <= HUNDRED.compareTo(value)
            && 1 >= value.scale();
    }

    private SerializablePredicate<Integer> gradeNumberOfStudentsAllZeroValidator() {
        return value -> isNotCurriculumSplitPercentEqualToHundred() || 0 == value;
    }

    //TODO: check if can be simplified
    private SerializablePredicate<Integer> gradeNumberOfStudentsAtLeastOneNotZeroValidator() {
        return value -> {
            String gradeKto2NumOfStudents = gradeKto2NumberOfStudents.getValue();
            String grade3to5NumOfStudents = grade3to5NumberOfStudents.getValue();
            String grade6to8NumOfStudents = grade6to8NumberOfStudents.getValue();
            String grade9to12NumOfStudents = grade9to12NumberOfStudents.getValue();
            String gradeHeNumOfStudents = gradeHeNumberOfStudents.getValue();
            if (isNotCurriculumSplitPercentEqualToHundred()
                && StringUtils.isNumeric(gradeKto2NumOfStudents) && 10 >= StringUtils.length(gradeKto2NumOfStudents)
                && StringUtils.isNumeric(grade3to5NumOfStudents) && 10 >= StringUtils.length(grade3to5NumOfStudents)
                && StringUtils.isNumeric(grade6to8NumOfStudents) && 10 >= StringUtils.length(grade6to8NumOfStudents)
                && StringUtils.isNumeric(grade9to12NumOfStudents) && 10 >= StringUtils.length(grade6to8NumOfStudents)
                && StringUtils.isNumeric(gradeHeNumOfStudents) && 10 >= StringUtils.length(gradeHeNumOfStudents)) {
                return 0 < Integer.parseInt(gradeKto2NumberOfStudents.getValue()) ||
                    0 < Integer.parseInt(grade3to5NumberOfStudents.getValue()) ||
                    0 < Integer.parseInt(grade6to8NumberOfStudents.getValue()) ||
                    0 < Integer.parseInt(grade9to12NumberOfStudents.getValue()) ||
                    0 < Integer.parseInt(gradeHeNumberOfStudents.getValue());
            } else {
                return true;
            }
        };
    }

    //TODO: check if can be simplified
    private boolean isNotCurriculumSplitPercentEqualToHundred() {
        if (StringUtils.isNotBlank(curriculumDbSplitPercent.getValue())) {
            Result<BigDecimal> decimalResult = new StringToBigDecimalConverter(StringUtils.EMPTY)
                .convertToModel(curriculumDbSplitPercent.getValue(), new ValueContext());
            return decimalResult.isError() ||
                0 != HUNDRED.compareTo(decimalResult.getOrThrow(s -> new RupRuntimeException()));
        } else {
            return true;
        }
    }

    private FundPool buildFundPool() {
        FundPool fundPool = binder.getBean();
        AclciFields aclciFields = fundPool.getAclciFields();
        aclciFields.setGrossAmount(aclciFields.getGrossAmount().setScale(SCALE_2, BigDecimal.ROUND_HALF_UP));
        aclciFields.setCurriculumSplitPercent(aclciFields.getCurriculumSplitPercent()
            .divide(HUNDRED, SCALE_5, BigDecimal.ROUND_HALF_UP));
        return fundPool;
    }

    private void binderStatusChangeListener(StatusChangeEvent event) {
        if (areRequiredFieldsNotEmpty() && event.getBinder().isValid()) {
            FundPool fundPool = usageController.calculateAclciFundPoolAmounts(buildFundPool());
            gradeKto2GrossAmount.setValue(fundPool.getAclciFields().getGradeKto2GrossAmount().toString());
            grade3to5GrossAmount.setValue(fundPool.getAclciFields().getGrade3to5GrossAmount().toString());
            grade6to8GrossAmount.setValue(fundPool.getAclciFields().getGrade6to8GrossAmount().toString());
            grade9to12GrossAmount.setValue(fundPool.getAclciFields().getGrade9to12GrossAmount().toString());
            gradeHeGrossAmount.setValue(fundPool.getAclciFields().getGradeHeGrossAmount().toString());
            curriculumDbGrossAmount.setValue(fundPool.getAclciFields().getCurriculumGrossAmount().toString());
            totalGrossAmount.setValue(fundPool.getTotalAmount().toString());
        } else {
            gradeKto2GrossAmount.clear();
            grade3to5GrossAmount.clear();
            grade6to8GrossAmount.clear();
            grade9to12GrossAmount.clear();
            gradeHeGrossAmount.clear();
            curriculumDbGrossAmount.clear();
            totalGrossAmount.clear();
        }
    }

    private boolean areRequiredFieldsNotEmpty() {
        return StringUtils.isNotBlank(fundPoolName.getValue())
            && StringUtils.isNotBlank(coverageYears.getValue())
            && StringUtils.isNotBlank(grossAmount.getValue())
            && StringUtils.isNotBlank(curriculumDbSplitPercent.getValue())
            && StringUtils.isNotBlank(gradeKto2NumberOfStudents.getValue())
            && StringUtils.isNotBlank(grade3to5NumberOfStudents.getValue())
            && StringUtils.isNotBlank(grade6to8NumberOfStudents.getValue())
            && StringUtils.isNotBlank(grade9to12NumberOfStudents.getValue())
            && StringUtils.isNotBlank(gradeHeNumberOfStudents.getValue());
    }

    private void validateGradeNumberOfStudentsFields() {
        gradeKto2NumberOfStudentsBinding.validate();
        grade3to5NumberOfStudentsBinding.validate();
        grade6to8NumberOfStudentsBinding.validate();
        grade9to12NumberOfStudentsBinding.validate();
        gradeHeNumberOfStudentsBinding.validate();
    }
}
