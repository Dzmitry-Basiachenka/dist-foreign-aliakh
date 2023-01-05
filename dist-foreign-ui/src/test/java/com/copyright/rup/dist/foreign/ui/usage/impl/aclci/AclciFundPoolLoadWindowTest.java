package com.copyright.rup.dist.foreign.ui.usage.impl.aclci;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.setTextFieldValue;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.validateFieldAndVerifyErrorMessage;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyLoadClickListener;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyTextField;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.FundPool.AclciFields;
import com.copyright.rup.dist.foreign.ui.usage.api.aclci.IAclciUsageController;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.google.common.collect.Lists;
import com.vaadin.data.Binder;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;

/**
 * Verifies {@link AclciFundPoolLoadWindow}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 12/29/2022
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Windows.class)
public class AclciFundPoolLoadWindowTest {

    private static final String FUND_POOL_NAME = "ACLCI Fund Pool";
    private static final String COVERAGE_YEARS = "2021-2022";
    private static final BigDecimal GROSS_AMOUNT = new BigDecimal("1000.1234567890");
    private static final BigDecimal CURRICULUM_DB_SPLIT_PERCENT = new BigDecimal("20.0");
    private static final Integer GRADE_K_TO_2_NUMBER_OF_STUDENTS = 1;
    private static final Integer GRADE_3_TO_5_NUMBER_OF_STUDENTS = 2;
    private static final Integer GRADE_6_TO_8_NUMBER_OF_STUDENTS = 3;
    private static final Integer GRADE_9_TO_12_NUMBER_OF_STUDENTS = 4;
    private static final Integer GRADE_HE_NUMBER_OF_STUDENTS = 5;
    private static final String BINDER = "binder";
    private static final String FUND_POOL_NAME_FIELD = "fundPoolName";
    private static final String COVERAGE_YEARS_FIELD = "coverageYears";
    private static final String GROSS_AMOUNT_FIELD = "grossAmount";
    private static final String CURRICULUM_DB_SPLIT_PERCENT_FIELD = "curriculumDbSplitPercent";
    private static final String GRADE_K_TO_2_NUMBER_OF_STUDENTS_FIELD = "gradeKto2NumberOfStudents";
    private static final String GRADE_3_TO_5_NUMBER_OF_STUDENTS_FIELD = "grade3to5NumberOfStudents";
    private static final String GRADE_6_TO_8_NUMBER_OF_STUDENTS_FIELD = "grade6to8NumberOfStudents";
    private static final String GRADE_9_TO_12_NUMBER_OF_STUDENTS_FIELD = "grade9to12NumberOfStudents";
    private static final String GRADE_HE_NUMBER_OF_STUDENTS_FIELD = "gradeHeNumberOfStudents";
    private static final String SPACES_STRING = "   ";
    private static final String INVALID_NUMBER = "not_a_number";
    private static final String NEGATIVE_NUMBER = "-1";
    private static final String INTEGER_WITH_SPACES_STRING = " 1 ";
    private static final String INTEGER_WITH_11_DIGITS = "12345678901";
    private static final String ZERO = "0";
    private static final String ONE = "1";
    private static final String HUNDRED = "100";
    private static final String EMPTY_FIELD_ERROR = "Field value should be specified";
    private static final String INVALID_NUMBER_ERROR = "Field value should contain numeric values only";
    private static final String FUND_POOL_EXISTS_ERROR = "Fund Pool with such name already exists";
    private static final String POSITIVE_AND_LENGTH_ERROR =
        "Field value should be positive number and should not exceed 10 digits";
    private static final String POSITIVE_OR_ZERO_AND_LENGTH_ERROR =
        "Field value should be positive number or zero and should not exceed 10 digits";
    private static final String SPLIT_PERCENT_ERROR =
        "Field value must be greater than 0 and less than or equal to 100 and allows entry for one decimal place";
    private static final String NUMBER_OF_STUDENTS_NOT_ZERO_ERROR =
        "Field value should be 0 when Curriculum DB Split % is 100";
    private static final String NUMBER_OF_STUDENTS_NOT_ALL_ZERO_ERROR =
        "Number of students should be entered for at least one grade group";
    private static final String CHARACTERS_LENGTH_ERROR = "Field value should not exceed %d characters";
    private static final String COVERAGE_YEARS_ERROR =
        "Coverage years should be in the range 1950-2099, with the second year being greater than the first";

    private AclciFundPoolLoadWindow window;
    private IAclciUsageController usageController;

    @Before
    public void setUp() {
        usageController = createMock(IAclciUsageController.class);
        window = new AclciFundPoolLoadWindow(usageController);
    }

    @Test
    public void testConstructor() {
        replay(usageController);
        assertEquals("Load Fund Pool", window.getCaption());
        assertEquals(500, window.getWidth(), 0);
        assertEquals(Sizeable.Unit.PIXELS, window.getWidthUnits());
        assertEquals(495, window.getHeight(), 0);
        assertEquals(Sizeable.Unit.PIXELS, window.getHeightUnits());
        verifyRootLayout(window.getContent());
        verify(usageController);
    }

    @Test
    public void testIsValid() {
        FundPool fundPool = new FundPool();
        fundPool.setAclciFields(new AclciFields());
        expect(usageController.aclciFundPoolExists(FUND_POOL_NAME)).andReturn(false).times(46);
        expect(usageController.calculateAclciFundPoolAmounts(anyObject(FundPool.class))).andReturn(fundPool).times(30);
        replay(usageController);
        Binder<FundPool> binder = Whitebox.getInternalState(window, BINDER);
        assertFalse(binder.isValid());
        setTextFieldValue(window, FUND_POOL_NAME_FIELD, FUND_POOL_NAME);
        setTextFieldValue(window, COVERAGE_YEARS_FIELD, COVERAGE_YEARS);
        setTextFieldValue(window, GROSS_AMOUNT_FIELD, GROSS_AMOUNT.toString());
        setTextFieldValue(window, CURRICULUM_DB_SPLIT_PERCENT_FIELD, CURRICULUM_DB_SPLIT_PERCENT.toString());
        setTextFieldValue(window, GRADE_K_TO_2_NUMBER_OF_STUDENTS_FIELD, GRADE_K_TO_2_NUMBER_OF_STUDENTS.toString());
        setTextFieldValue(window, GRADE_3_TO_5_NUMBER_OF_STUDENTS_FIELD, GRADE_3_TO_5_NUMBER_OF_STUDENTS.toString());
        setTextFieldValue(window, GRADE_6_TO_8_NUMBER_OF_STUDENTS_FIELD, GRADE_6_TO_8_NUMBER_OF_STUDENTS.toString());
        setTextFieldValue(window, GRADE_9_TO_12_NUMBER_OF_STUDENTS_FIELD, GRADE_9_TO_12_NUMBER_OF_STUDENTS.toString());
        setTextFieldValue(window, GRADE_HE_NUMBER_OF_STUDENTS_FIELD, GRADE_HE_NUMBER_OF_STUDENTS.toString());
        assertTrue(binder.isValid());
        verify(usageController);
    }

    @Test
    public void testFundPoolNameFieldValidation() {
        expect(usageController.aclciFundPoolExists(FUND_POOL_NAME)).andReturn(true).times(2);
        expect(usageController.aclciFundPoolExists(FUND_POOL_NAME)).andReturn(false).times(1);
        replay(usageController);
        Binder<?> binder = Whitebox.getInternalState(window, BINDER);
        TextField textField = Whitebox.getInternalState(window, FUND_POOL_NAME_FIELD);
        validateFieldAndVerifyErrorMessage(textField, StringUtils.EMPTY, binder, EMPTY_FIELD_ERROR, false);
        validateFieldAndVerifyErrorMessage(textField, SPACES_STRING, binder, EMPTY_FIELD_ERROR, false);
        validateFieldAndVerifyErrorMessage(textField,
            "a".repeat(51), binder, String.format(CHARACTERS_LENGTH_ERROR, 50), false);
        validateFieldAndVerifyErrorMessage(textField, FUND_POOL_NAME, binder, FUND_POOL_EXISTS_ERROR, false);
        validateFieldAndVerifyErrorMessage(textField, FUND_POOL_NAME, binder, null, true);
        verify(usageController);
    }

    @Test
    public void testCoverageYearsFieldValidation() {
        replay(usageController);
        Binder<?> binder = Whitebox.getInternalState(window, BINDER);
        TextField textField = Whitebox.getInternalState(window, COVERAGE_YEARS_FIELD);
        validateFieldAndVerifyErrorMessage(textField, StringUtils.EMPTY, binder, EMPTY_FIELD_ERROR, false);
        validateFieldAndVerifyErrorMessage(textField, SPACES_STRING, binder, EMPTY_FIELD_ERROR, false);
        validateFieldAndVerifyErrorMessage(textField, "12345678", binder, COVERAGE_YEARS_ERROR, false);
        validateFieldAndVerifyErrorMessage(textField, "123456789", binder, COVERAGE_YEARS_ERROR, false);
        validateFieldAndVerifyErrorMessage(textField, "1234567890", binder, COVERAGE_YEARS_ERROR, false);
        validateFieldAndVerifyErrorMessage(textField, "1950 2099", binder, COVERAGE_YEARS_ERROR, false);
        validateFieldAndVerifyErrorMessage(textField, "1949-2099", binder, COVERAGE_YEARS_ERROR, false);
        validateFieldAndVerifyErrorMessage(textField, "1950-2100", binder, COVERAGE_YEARS_ERROR, false);
        validateFieldAndVerifyErrorMessage(textField, "1950-2099", binder, null, true);
        validateFieldAndVerifyErrorMessage(textField, "2022-2021", binder, COVERAGE_YEARS_ERROR, false);
        validateFieldAndVerifyErrorMessage(textField, "2021-2021", binder, COVERAGE_YEARS_ERROR, false);
        validateFieldAndVerifyErrorMessage(textField, "2021-2022", binder, null, true);
        validateFieldAndVerifyErrorMessage(textField, "  2021-2022  ", binder, null, true);
        validateFieldAndVerifyErrorMessage(textField, COVERAGE_YEARS, binder, null, true);
        verify(usageController);
    }

    @Test
    public void testGrossAmountFieldValidation() {
        replay(usageController);
        Binder<?> binder = Whitebox.getInternalState(window, BINDER);
        TextField textField = Whitebox.getInternalState(window, GROSS_AMOUNT_FIELD);
        validateFieldAndVerifyErrorMessage(textField, StringUtils.EMPTY, binder, EMPTY_FIELD_ERROR, false);
        validateFieldAndVerifyErrorMessage(textField, SPACES_STRING, binder, EMPTY_FIELD_ERROR, false);
        validateFieldAndVerifyErrorMessage(textField, INVALID_NUMBER, binder, POSITIVE_AND_LENGTH_ERROR, false);
        validateFieldAndVerifyErrorMessage(textField, NEGATIVE_NUMBER, binder, POSITIVE_AND_LENGTH_ERROR, false);
        validateFieldAndVerifyErrorMessage(textField, ZERO, binder, POSITIVE_AND_LENGTH_ERROR, false);
        validateFieldAndVerifyErrorMessage(textField, INTEGER_WITH_SPACES_STRING, binder, null, true);
        validateFieldAndVerifyErrorMessage(textField, "0.1", binder, null, true);
        validateFieldAndVerifyErrorMessage(textField, "0.1234567890", binder, null, true);
        validateFieldAndVerifyErrorMessage(textField, "0.12345678901", binder, POSITIVE_AND_LENGTH_ERROR, false);
        validateFieldAndVerifyErrorMessage(textField, "1.1234567890", binder, null, true);
        validateFieldAndVerifyErrorMessage(textField, "1234567890.1234567890", binder, null, true);
        validateFieldAndVerifyErrorMessage(textField,
            "12345678901.1234567890", binder, POSITIVE_AND_LENGTH_ERROR, false);
        verify(usageController);
    }

    @Test
    public void testIsValidCurriculumDbSplitPercent() {
        replay(usageController);
        Binder<?> binder = Whitebox.getInternalState(window, BINDER);
        TextField textField = Whitebox.getInternalState(window, CURRICULUM_DB_SPLIT_PERCENT_FIELD);
        validateFieldAndVerifyErrorMessage(textField, StringUtils.EMPTY, binder, EMPTY_FIELD_ERROR, false);
        validateFieldAndVerifyErrorMessage(textField, SPACES_STRING, binder, EMPTY_FIELD_ERROR, false);
        validateFieldAndVerifyErrorMessage(textField, INVALID_NUMBER, binder, INVALID_NUMBER_ERROR, false);
        validateFieldAndVerifyErrorMessage(textField, NEGATIVE_NUMBER, binder, SPLIT_PERCENT_ERROR, false);
        validateFieldAndVerifyErrorMessage(textField, ZERO, binder, SPLIT_PERCENT_ERROR, false);
        setTextFieldValue(window, CURRICULUM_DB_SPLIT_PERCENT_FIELD, INTEGER_WITH_SPACES_STRING);
        verifyFieldIsValid(binder, CURRICULUM_DB_SPLIT_PERCENT_FIELD, ONE);
        validateFieldAndVerifyErrorMessage(textField, "0.1", binder, null, true);
        validateFieldAndVerifyErrorMessage(textField, "0.10", binder, SPLIT_PERCENT_ERROR, false);
        validateFieldAndVerifyErrorMessage(textField, "100", binder, null, true);
        validateFieldAndVerifyErrorMessage(textField, "101", binder, SPLIT_PERCENT_ERROR, false);
        validateFieldAndVerifyErrorMessage(textField, "100.00", binder, SPLIT_PERCENT_ERROR, false);
        verify(usageController);
    }

    @Test
    public void testGradeKto2NumberOfStudentsFieldValidation() {
        testGradeNumberOfStudentsFieldValidation(GRADE_K_TO_2_NUMBER_OF_STUDENTS_FIELD);
    }

    @Test
    public void testGrade3to5NumberOfStudentsFieldValidation() {
        testGradeNumberOfStudentsFieldValidation(GRADE_3_TO_5_NUMBER_OF_STUDENTS_FIELD);
    }

    @Test
    public void testGrade6to8NumberOfStudentsFieldValidation() {
        testGradeNumberOfStudentsFieldValidation(GRADE_6_TO_8_NUMBER_OF_STUDENTS_FIELD);
    }

    @Test
    public void testGrade9to12NumberOfStudentsFieldValidation() {
        testGradeNumberOfStudentsFieldValidation(GRADE_9_TO_12_NUMBER_OF_STUDENTS_FIELD);
    }

    @Test
    public void testGradeHeNumberOfStudentsFieldValidation() {
        testGradeNumberOfStudentsFieldValidation(GRADE_HE_NUMBER_OF_STUDENTS_FIELD);
    }

    @Test
    public void testOnUploadButtonClick() {
        mockStatic(Windows.class);
        FundPool fundPool = buildFundPool();
        expect(usageController.aclciFundPoolExists(FUND_POOL_NAME)).andReturn(false).times(46);
        expect(usageController.calculateAclciFundPoolAmounts(anyObject(FundPool.class))).andReturn(fundPool).times(31);
        usageController.createAclciFundPool(fundPool);
        expectLastCall().once();
        Windows.showNotificationWindow("Upload has been successfully completed");
        expectLastCall().once();
        replay(usageController, Windows.class);
        setTextFieldValue(window, FUND_POOL_NAME_FIELD, FUND_POOL_NAME);
        setTextFieldValue(window, COVERAGE_YEARS_FIELD, COVERAGE_YEARS);
        setTextFieldValue(window, GROSS_AMOUNT_FIELD, GROSS_AMOUNT.toString());
        setTextFieldValue(window, CURRICULUM_DB_SPLIT_PERCENT_FIELD, CURRICULUM_DB_SPLIT_PERCENT.toString());
        setTextFieldValue(window, GRADE_K_TO_2_NUMBER_OF_STUDENTS_FIELD, GRADE_K_TO_2_NUMBER_OF_STUDENTS.toString());
        setTextFieldValue(window, GRADE_3_TO_5_NUMBER_OF_STUDENTS_FIELD, GRADE_3_TO_5_NUMBER_OF_STUDENTS.toString());
        setTextFieldValue(window, GRADE_6_TO_8_NUMBER_OF_STUDENTS_FIELD, GRADE_6_TO_8_NUMBER_OF_STUDENTS.toString());
        setTextFieldValue(window, GRADE_9_TO_12_NUMBER_OF_STUDENTS_FIELD, GRADE_9_TO_12_NUMBER_OF_STUDENTS.toString());
        setTextFieldValue(window, GRADE_HE_NUMBER_OF_STUDENTS_FIELD, GRADE_HE_NUMBER_OF_STUDENTS.toString());
        VerticalLayout rootLayout = (VerticalLayout) window.getContent();
        HorizontalLayout buttonsLayout = (HorizontalLayout) rootLayout.getComponent(9);
        Button uploadButton = (Button) buttonsLayout.getComponent(0);
        uploadButton.click();
        verify(usageController, Windows.class);
    }

    private void verifyRootLayout(Component component) {
        assertThat(component, instanceOf(VerticalLayout.class));
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(10, verticalLayout.getComponentCount());
        verifyTextFieldComponent(verticalLayout.getComponent(0), "Fund Pool Name", false);
        verifyTextFieldComponent(verticalLayout.getComponent(1), "Coverage Years (YYYY-YYYY)", false);
        HorizontalLayout grossAmountLayout = (HorizontalLayout) verticalLayout.getComponent(2);
        verifyAmountFieldComponent(grossAmountLayout.getComponent(0), "Gross Amount", false);
        verifyAmountFieldComponent(grossAmountLayout.getComponent(1), "Curriculum DB Split %", false);
        HorizontalLayout gradeKto2Layout = (HorizontalLayout) verticalLayout.getComponent(3);
        verifyAmountFieldComponent(gradeKto2Layout.getComponent(0), "Grade K-2 Number of Students", false);
        verifyTextFieldComponent(gradeKto2Layout.getComponent(1), "Grade K-2 Gross Amount", true);
        HorizontalLayout grade3to5Layout = (HorizontalLayout) verticalLayout.getComponent(4);
        verifyAmountFieldComponent(grade3to5Layout.getComponent(0), "Grade 3-5 Number of Students", false);
        verifyTextFieldComponent(grade3to5Layout.getComponent(1), "Grade 3-5 Gross Amount", true);
        HorizontalLayout grade6to8Layout = (HorizontalLayout) verticalLayout.getComponent(5);
        verifyAmountFieldComponent(grade6to8Layout.getComponent(0), "Grade 6-8 Number of Students", false);
        verifyTextFieldComponent(grade6to8Layout.getComponent(1), "Grade 6-8 Gross Amount", true);
        HorizontalLayout grade9to12Layout = (HorizontalLayout) verticalLayout.getComponent(6);
        verifyAmountFieldComponent(grade9to12Layout.getComponent(0), "Grade 9-12 Number of Students", false);
        verifyTextFieldComponent(grade9to12Layout.getComponent(1), "Grade 9-12 Gross Amount", true);
        HorizontalLayout totalGrossAmountLayout = (HorizontalLayout) verticalLayout.getComponent(8);
        verifyTextFieldComponent(totalGrossAmountLayout.getComponent(0), "Curriculum DB Gross Amount", true);
        verifyTextFieldComponent(totalGrossAmountLayout.getComponent(1), "Total Gross Amount", true);
        verifyButtonsLayout(verticalLayout.getComponent(9));
    }

    private void verifyTextFieldComponent(Component component, String caption, boolean isReadonly) {
        TextField textField = verifyTextField(component, caption);
        assertEquals(isReadonly, textField.isReadOnly());
        assertEquals(StringUtils.EMPTY, textField.getValue());
    }

    private void verifyAmountFieldComponent(Component component, String caption, boolean isReadonly) {
        TextField textField = verifyTextField(component, caption);
        assertEquals(isReadonly, textField.isReadOnly());
        assertEquals(ZERO, textField.getValue());
    }

    private void verifyButtonsLayout(Component component) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        Button loadButton = verifyButton(layout.getComponent(0), "Upload");
        verifyButton(layout.getComponent(1), "Close");
        assertEquals(1, loadButton.getListeners(Button.ClickEvent.class).size());
        verifyLoadClickListener(loadButton, Lists.newArrayList(
            Whitebox.getInternalState(window, FUND_POOL_NAME_FIELD),
            Whitebox.getInternalState(window, COVERAGE_YEARS_FIELD),
            Whitebox.getInternalState(window, GROSS_AMOUNT_FIELD),
            Whitebox.getInternalState(window, CURRICULUM_DB_SPLIT_PERCENT_FIELD),
            Whitebox.getInternalState(window, GRADE_K_TO_2_NUMBER_OF_STUDENTS_FIELD),
            Whitebox.getInternalState(window, GRADE_3_TO_5_NUMBER_OF_STUDENTS_FIELD),
            Whitebox.getInternalState(window, GRADE_6_TO_8_NUMBER_OF_STUDENTS_FIELD),
            Whitebox.getInternalState(window, GRADE_9_TO_12_NUMBER_OF_STUDENTS_FIELD),
            Whitebox.getInternalState(window, GRADE_HE_NUMBER_OF_STUDENTS_FIELD)));
    }

    private Button verifyButton(Component component, String caption) {
        assertThat(component, instanceOf(Button.class));
        assertEquals(caption, component.getCaption());
        return (Button) component;
    }

    private void verifyFieldErrorMessage(Binder<?> binder, String fieldName, String message) {
        binder.validate();
        AbstractComponent textField = Whitebox.getInternalState(window, fieldName);
        assertEquals(message, textField.getErrorMessage().toString());
    }

    private void verifyFieldIsValid(Binder<?> binder, String fieldName, Object expectedValue) {
        binder.validate();
        AbstractComponent textField = Whitebox.getInternalState(window, fieldName);
        assertNull(textField.getErrorMessage());
        assertEquals(expectedValue, ((AbstractField<?>) textField).getValue());
    }

    //TODO: simplify the number of students tests
    private void testGradeNumberOfStudentsFieldValidation(String fieldName) {
        replay(usageController);
        Binder<?> binder = Whitebox.getInternalState(window, BINDER);
        setTextFieldValue(window, CURRICULUM_DB_SPLIT_PERCENT_FIELD, CURRICULUM_DB_SPLIT_PERCENT.toString());
        setTextFieldValue(window, fieldName, StringUtils.EMPTY);
        verifyFieldErrorMessage(binder, fieldName, EMPTY_FIELD_ERROR);
        setTextFieldValue(window, fieldName, SPACES_STRING);
        verifyFieldErrorMessage(binder, fieldName, EMPTY_FIELD_ERROR);
        setTextFieldValue(window, fieldName, INTEGER_WITH_11_DIGITS);
        verifyFieldErrorMessage(binder, fieldName, POSITIVE_OR_ZERO_AND_LENGTH_ERROR);
        setTextFieldValue(window, GRADE_K_TO_2_NUMBER_OF_STUDENTS_FIELD, ZERO);
        setTextFieldValue(window, GRADE_3_TO_5_NUMBER_OF_STUDENTS_FIELD, ZERO);
        setTextFieldValue(window, GRADE_6_TO_8_NUMBER_OF_STUDENTS_FIELD, ZERO);
        setTextFieldValue(window, GRADE_9_TO_12_NUMBER_OF_STUDENTS_FIELD, ZERO);
        setTextFieldValue(window, GRADE_HE_NUMBER_OF_STUDENTS_FIELD, ZERO);
        verifyFieldErrorMessage(binder, fieldName, NUMBER_OF_STUDENTS_NOT_ALL_ZERO_ERROR);
        setTextFieldValue(window, fieldName, ONE);
        setTextFieldValue(window, CURRICULUM_DB_SPLIT_PERCENT_FIELD, HUNDRED);
        verifyFieldErrorMessage(binder, fieldName, NUMBER_OF_STUDENTS_NOT_ZERO_ERROR);
        setTextFieldValue(window, CURRICULUM_DB_SPLIT_PERCENT_FIELD, CURRICULUM_DB_SPLIT_PERCENT.toString());
        verifyFieldIsValid(binder, fieldName, ONE);
        verify(usageController);
    }

    private FundPool buildFundPool() {
        FundPool fundPool = new FundPool();
        fundPool.setName(FUND_POOL_NAME);
        AclciFields aclciFields = new AclciFields();
        aclciFields.setCoverageYears(COVERAGE_YEARS);
        aclciFields.setGrossAmount(new BigDecimal("1000.12"));
        aclciFields.setCurriculumSplitPercent(new BigDecimal("0.20000"));
        aclciFields.setGradeKto2NumberOfStudents(GRADE_K_TO_2_NUMBER_OF_STUDENTS);
        aclciFields.setGrade3to5NumberOfStudents(GRADE_3_TO_5_NUMBER_OF_STUDENTS);
        aclciFields.setGrade6to8NumberOfStudents(GRADE_6_TO_8_NUMBER_OF_STUDENTS);
        aclciFields.setGrade9to12NumberOfStudents(GRADE_9_TO_12_NUMBER_OF_STUDENTS);
        aclciFields.setGradeHeNumberOfStudents(GRADE_HE_NUMBER_OF_STUDENTS);
        fundPool.setAclciFields(aclciFields);
        return fundPool;
    }
}
