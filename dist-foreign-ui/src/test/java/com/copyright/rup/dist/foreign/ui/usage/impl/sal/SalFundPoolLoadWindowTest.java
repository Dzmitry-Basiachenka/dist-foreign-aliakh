package com.copyright.rup.dist.foreign.ui.usage.impl.sal;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.ui.usage.api.sal.ISalUsageController;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.widget.LocalDateWidget;

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

import java.time.LocalDate;
import java.util.Collection;

/**
 * Verifies {@link SalFundPoolLoadWindow}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 09/25/20
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class})
public class SalFundPoolLoadWindowTest {

    private static final String ZERO = "0";
    private static final String FUND_POOL_NAME = "SAL Fund Pool";
    private static final String BINDER_FIELD = "binder";
    private static final String FUND_POOL_NAME_FIELD = "fundPoolNameField";
    private static final String ASSESSMENT_NAME_FIELD = "assessmentName";
    private static final String GROSS_AMOUNT_FIELD = "grossAmountField";
    private static final String ITEM_BANK_SPLIT_PERCENT_FIELD = "itemBankSplitPercent";
    private static final String ACCOUNT_NUMBER_FIELD = "accountNumberField";
    private static final String LICENSEE_NAME_FIELD = "licenseeNameField";
    private static final String DATE_RECEIVED_FIELD = "dateReceived";
    private static final String GRADE_K_TO_5_NUM_OF_STUDENTS_FIELD = "gradeKto5NumberOfStudents";
    private static final String GRADE_6_TO_8_NUM_OF_STUDENTS_FIELD = "grade6to8NumberOfStudents";
    private static final String GRADE_9_TO_12_NUM_OF_STUDENTS_FIELD = "grade9to12NumberOfStudents";
    private static final String EMPTY_FIELD_ERROR_MESSAGE = "Field value should be specified";
    private static final String FUND_POOL_EXISTS_ERROR_MESSAGE = "Fund Pool with such name already exists";
    private static final String NEGATIVE_OR_LONG_ERROR_MESSAGE =
        "Field value should be positive number and should not exceed 10 digits";
    private static final String ITEM_BANK_SPLIT_PERCENT_ERROR_MESSAGE =
        "Field value must be greater than 0 and less than or equal to 100 and allows entry for one decimal place";
    private static final String NO_NUM_OF_STUDENTS_ERROR_MESSAGE =
        "Number of students should be entered for at least one group of grades";
    private static final String NUM_OF_STUDENTS_NOT_ZERO_ERROR_MESSAGE =
        "Field value should be 0 when Item Bank Split % is 100";

    private SalFundPoolLoadWindow window;
    private ISalUsageController usagesController;

    @Before
    public void setUp() {
        usagesController = createMock(ISalUsageController.class);
        window = new SalFundPoolLoadWindow(usagesController);
    }

    @Test
    public void testConstructor() {
        replay(usagesController);
        assertEquals("Load Fund Pool", window.getCaption());
        assertEquals(500, window.getWidth(), 0);
        assertEquals(Sizeable.Unit.PIXELS, window.getWidthUnits());
        assertEquals(530, window.getHeight(), 0);
        assertEquals(Sizeable.Unit.PIXELS, window.getHeightUnits());
        verifyRootLayout(window.getContent());
        verify(usagesController);
    }

    @Test
    public void testIsValid() {
        expect(usagesController.fundPoolExists(FUND_POOL_NAME)).andReturn(false).times(6);
        replay(usagesController);
        Binder<FundPool> binder = Whitebox.getInternalState(window, BINDER_FIELD);
        assertFalse(binder.isValid());
        setTextFieldValue(FUND_POOL_NAME_FIELD, FUND_POOL_NAME);
        setTextFieldValue(ASSESSMENT_NAME_FIELD, "FY2020 GDC");
        setTextFieldValue(GROSS_AMOUNT_FIELD, "500");
        setTextFieldValue(ITEM_BANK_SPLIT_PERCENT_FIELD, "2.0");
        setTextFieldValue(ACCOUNT_NUMBER_FIELD, "100009522");
        setTextFieldValue(LICENSEE_NAME_FIELD, "Medical Journal of Pakistan");
        setTextFieldValue(GRADE_K_TO_5_NUM_OF_STUDENTS_FIELD, "10");
        setTextFieldValue(GRADE_6_TO_8_NUM_OF_STUDENTS_FIELD, "5");
        setTextFieldValue(GRADE_9_TO_12_NUM_OF_STUDENTS_FIELD, ZERO);
        setLocalDateWidgetValue(DATE_RECEIVED_FIELD, LocalDate.of(2020, 12, 12));
        assertTrue(binder.isValid());
        verify(usagesController);
    }

    @Test
    public void testIsValidFundPoolName() {
        expect(usagesController.fundPoolExists(FUND_POOL_NAME)).andReturn(true).times(2);
        expect(usagesController.fundPoolExists(FUND_POOL_NAME)).andReturn(false).times(1);
        replay(usagesController);
        Binder binder = Whitebox.getInternalState(window, BINDER_FIELD);
        setTextFieldValue(FUND_POOL_NAME_FIELD, StringUtils.EMPTY);
        verifyFieldErrorMessage(binder, FUND_POOL_NAME_FIELD, EMPTY_FIELD_ERROR_MESSAGE);
        setTextFieldValue(FUND_POOL_NAME_FIELD, FUND_POOL_NAME);
        verifyFieldErrorMessage(binder, FUND_POOL_NAME_FIELD, FUND_POOL_EXISTS_ERROR_MESSAGE);
        setTextFieldValue(FUND_POOL_NAME_FIELD, FUND_POOL_NAME);
        verifyFieldIsValid(binder, FUND_POOL_NAME_FIELD);
        verify(usagesController);
    }

    @Test
    public void testIsValidAssessmentName() {
        replay(usagesController);
        Binder binder = Whitebox.getInternalState(window, BINDER_FIELD);
        setTextFieldValue(ASSESSMENT_NAME_FIELD, StringUtils.EMPTY);
        verifyFieldErrorMessage(binder, ASSESSMENT_NAME_FIELD, EMPTY_FIELD_ERROR_MESSAGE);
        setTextFieldValue(ASSESSMENT_NAME_FIELD, "FY2019 HTR");
        verifyFieldIsValid(binder, ASSESSMENT_NAME_FIELD);
        verify(usagesController);
    }

    @Test
    public void testIsValidGrossAmount() {
        replay(usagesController);
        Binder binder = Whitebox.getInternalState(window, BINDER_FIELD);
        setTextFieldValue(GROSS_AMOUNT_FIELD, StringUtils.EMPTY);
        verifyFieldErrorMessage(binder, GROSS_AMOUNT_FIELD, EMPTY_FIELD_ERROR_MESSAGE);
        setTextFieldValue(GROSS_AMOUNT_FIELD, "-10");
        verifyFieldErrorMessage(binder, GROSS_AMOUNT_FIELD, NEGATIVE_OR_LONG_ERROR_MESSAGE);
        setTextFieldValue(GROSS_AMOUNT_FIELD, "10000000000.34");
        verifyFieldErrorMessage(binder, GROSS_AMOUNT_FIELD, NEGATIVE_OR_LONG_ERROR_MESSAGE);
        setTextFieldValue(GROSS_AMOUNT_FIELD, ZERO);
        verifyFieldErrorMessage(binder, GROSS_AMOUNT_FIELD, NEGATIVE_OR_LONG_ERROR_MESSAGE);
        setTextFieldValue(GROSS_AMOUNT_FIELD, "5000.56");
        verifyFieldIsValid(binder, GROSS_AMOUNT_FIELD);
        verify(usagesController);
    }

    @Test
    public void testIsValidItemBankSplitPercent() {
        replay(usagesController);
        Binder binder = Whitebox.getInternalState(window, BINDER_FIELD);
        setTextFieldValue(ITEM_BANK_SPLIT_PERCENT_FIELD, StringUtils.EMPTY);
        verifyFieldErrorMessage(binder, ITEM_BANK_SPLIT_PERCENT_FIELD, EMPTY_FIELD_ERROR_MESSAGE);
        setTextFieldValue(ITEM_BANK_SPLIT_PERCENT_FIELD, ZERO);
        verifyFieldErrorMessage(binder, ITEM_BANK_SPLIT_PERCENT_FIELD, ITEM_BANK_SPLIT_PERCENT_ERROR_MESSAGE);
        setTextFieldValue(ITEM_BANK_SPLIT_PERCENT_FIELD, "120.0");
        verifyFieldErrorMessage(binder, ITEM_BANK_SPLIT_PERCENT_FIELD, ITEM_BANK_SPLIT_PERCENT_ERROR_MESSAGE);
        setTextFieldValue(ITEM_BANK_SPLIT_PERCENT_FIELD, "97.34");
        verifyFieldErrorMessage(binder, ITEM_BANK_SPLIT_PERCENT_FIELD, ITEM_BANK_SPLIT_PERCENT_ERROR_MESSAGE);
        setTextFieldValue(ITEM_BANK_SPLIT_PERCENT_FIELD, "97.3");
        verifyFieldIsValid(binder, ITEM_BANK_SPLIT_PERCENT_FIELD);
        verify(usagesController);
    }

    @Test
    public void testIsValidGradeKto5NumberOfStudents() {
        replay(usagesController);
        Binder binder = Whitebox.getInternalState(window, BINDER_FIELD);
        setTextFieldValue(GRADE_K_TO_5_NUM_OF_STUDENTS_FIELD, StringUtils.EMPTY);
        setTextFieldValue(GRADE_6_TO_8_NUM_OF_STUDENTS_FIELD, ZERO);
        verifyFieldErrorMessage(binder, GRADE_K_TO_5_NUM_OF_STUDENTS_FIELD, EMPTY_FIELD_ERROR_MESSAGE);
        setTextFieldValue(GRADE_K_TO_5_NUM_OF_STUDENTS_FIELD, ZERO);
        setTextFieldValue(GRADE_6_TO_8_NUM_OF_STUDENTS_FIELD, ZERO);
        setTextFieldValue(GRADE_9_TO_12_NUM_OF_STUDENTS_FIELD, ZERO);
        setTextFieldValue(ITEM_BANK_SPLIT_PERCENT_FIELD, "97.34");
        verifyFieldErrorMessage(binder, GRADE_K_TO_5_NUM_OF_STUDENTS_FIELD, NO_NUM_OF_STUDENTS_ERROR_MESSAGE);
        setTextFieldValue(GRADE_K_TO_5_NUM_OF_STUDENTS_FIELD, "8");
        setTextFieldValue(ITEM_BANK_SPLIT_PERCENT_FIELD, "100.0");
        verifyFieldErrorMessage(binder, GRADE_K_TO_5_NUM_OF_STUDENTS_FIELD, NUM_OF_STUDENTS_NOT_ZERO_ERROR_MESSAGE);
        setTextFieldValue(GRADE_K_TO_5_NUM_OF_STUDENTS_FIELD, "5");
        setTextFieldValue(ITEM_BANK_SPLIT_PERCENT_FIELD, "2.0");
        verifyFieldIsValid(binder, GRADE_K_TO_5_NUM_OF_STUDENTS_FIELD);
        verify(usagesController);
    }

    @Test
    public void testIsValidGrade6to8NumberOfStudents() {
        replay(usagesController);
        Binder binder = Whitebox.getInternalState(window, BINDER_FIELD);
        setTextFieldValue(GRADE_6_TO_8_NUM_OF_STUDENTS_FIELD, StringUtils.EMPTY);
        setTextFieldValue(GRADE_9_TO_12_NUM_OF_STUDENTS_FIELD, ZERO);
        verifyFieldErrorMessage(binder, GRADE_6_TO_8_NUM_OF_STUDENTS_FIELD, EMPTY_FIELD_ERROR_MESSAGE);
        setTextFieldValue(GRADE_K_TO_5_NUM_OF_STUDENTS_FIELD, ZERO);
        setTextFieldValue(GRADE_6_TO_8_NUM_OF_STUDENTS_FIELD, ZERO);
        setTextFieldValue(GRADE_9_TO_12_NUM_OF_STUDENTS_FIELD, ZERO);
        setTextFieldValue(ITEM_BANK_SPLIT_PERCENT_FIELD, "98.34");
        verifyFieldErrorMessage(binder, GRADE_6_TO_8_NUM_OF_STUDENTS_FIELD, NO_NUM_OF_STUDENTS_ERROR_MESSAGE);
        setTextFieldValue(GRADE_6_TO_8_NUM_OF_STUDENTS_FIELD, "8");
        setTextFieldValue(ITEM_BANK_SPLIT_PERCENT_FIELD, "100.0");
        verifyFieldErrorMessage(binder, GRADE_6_TO_8_NUM_OF_STUDENTS_FIELD, NUM_OF_STUDENTS_NOT_ZERO_ERROR_MESSAGE);
        setTextFieldValue(GRADE_6_TO_8_NUM_OF_STUDENTS_FIELD, "6");
        setTextFieldValue(ITEM_BANK_SPLIT_PERCENT_FIELD, "3.0");
        verifyFieldIsValid(binder, GRADE_6_TO_8_NUM_OF_STUDENTS_FIELD);
        verify(usagesController);
    }

    @Test
    public void testIsValidGrade9to12NumberOfStudents() {
        replay(usagesController);
        Binder binder = Whitebox.getInternalState(window, BINDER_FIELD);
        setTextFieldValue(GRADE_9_TO_12_NUM_OF_STUDENTS_FIELD, StringUtils.EMPTY);
        setTextFieldValue(GRADE_K_TO_5_NUM_OF_STUDENTS_FIELD, ZERO);
        verifyFieldErrorMessage(binder, GRADE_9_TO_12_NUM_OF_STUDENTS_FIELD, EMPTY_FIELD_ERROR_MESSAGE);
        setTextFieldValue(GRADE_K_TO_5_NUM_OF_STUDENTS_FIELD, ZERO);
        setTextFieldValue(GRADE_6_TO_8_NUM_OF_STUDENTS_FIELD, ZERO);
        setTextFieldValue(GRADE_9_TO_12_NUM_OF_STUDENTS_FIELD, ZERO);
        setTextFieldValue(ITEM_BANK_SPLIT_PERCENT_FIELD, "97.34");
        verifyFieldErrorMessage(binder, GRADE_9_TO_12_NUM_OF_STUDENTS_FIELD, NO_NUM_OF_STUDENTS_ERROR_MESSAGE);
        setTextFieldValue(GRADE_9_TO_12_NUM_OF_STUDENTS_FIELD, "8");
        setTextFieldValue(ITEM_BANK_SPLIT_PERCENT_FIELD, "100");
        verifyFieldErrorMessage(binder, GRADE_9_TO_12_NUM_OF_STUDENTS_FIELD, NUM_OF_STUDENTS_NOT_ZERO_ERROR_MESSAGE);
        setTextFieldValue(GRADE_9_TO_12_NUM_OF_STUDENTS_FIELD, "8");
        setTextFieldValue(ITEM_BANK_SPLIT_PERCENT_FIELD, "5.0");
        verifyFieldIsValid(binder, GRADE_9_TO_12_NUM_OF_STUDENTS_FIELD);
        verify(usagesController);
    }

    private void verifyRootLayout(Component component) {
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(10, verticalLayout.getComponentCount());
        verifyTextFieldComponent(verticalLayout.getComponent(0), "Fund Pool Name", false);
        verifyTextFieldComponent(verticalLayout.getComponent(1), "Assessment Name", false);
        HorizontalLayout grossAndSplitPercentLayout = (HorizontalLayout) verticalLayout.getComponent(2);
        verifyTextFieldComponent(grossAndSplitPercentLayout.getComponent(0), "Gross Amount", false);
        verifyTextFieldComponent(grossAndSplitPercentLayout.getComponent(1), "Item Bank Split %", false);
        verifyLicenseeComponents(verticalLayout.getComponent(3));
        verifyDateComponent(verticalLayout.getComponent(4));
        HorizontalLayout gradeKto5Layout = (HorizontalLayout) verticalLayout.getComponent(5);
        verifyTextFieldComponent(gradeKto5Layout.getComponent(0), "Grade K-5 Number of Students", false);
        verifyTextFieldComponent(gradeKto5Layout.getComponent(1), "Grade K-5 Gross Amount", true);
        HorizontalLayout grade6to8Layout = (HorizontalLayout) verticalLayout.getComponent(6);
        verifyTextFieldComponent(grade6to8Layout.getComponent(0), "Grade 6-8 Number of Students", false);
        verifyTextFieldComponent(grade6to8Layout.getComponent(1), "Grade 6-8 Gross Amount", true);
        HorizontalLayout grade9to12Layout = (HorizontalLayout) verticalLayout.getComponent(7);
        verifyTextFieldComponent(grade9to12Layout.getComponent(0), "Grade 9-12 Number of Students", false);
        verifyTextFieldComponent(grade9to12Layout.getComponent(1), "Grade 9-12 Gross Amount", true);
        HorizontalLayout itemBankAndTotalAmountLayout = (HorizontalLayout) verticalLayout.getComponent(8);
        verifyTextFieldComponent(itemBankAndTotalAmountLayout.getComponent(0), "Item Bank Amount", true);
        verifyTextFieldComponent(itemBankAndTotalAmountLayout.getComponent(1), "Total Amount", true);
        verifyButtonsLayout(verticalLayout.getComponent(9));
    }

    private void verifyLicenseeComponents(Component component) {
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(2, verticalLayout.getComponentCount());
        HorizontalLayout horizontalLayout = (HorizontalLayout) verticalLayout.getComponent(0);
        verifyTextFieldComponent(horizontalLayout.getComponent(0), "Licensee Account #", false);
        Component verifyComponent = horizontalLayout.getComponent(1);
        assertTrue(verifyComponent instanceof Button);
        assertEquals("Verify", verifyComponent.getCaption());
        verifyTextFieldComponent(verticalLayout.getComponent(1), "Licensee Name", true);
        assertTrue(((TextField) verticalLayout.getComponent(1)).isReadOnly());
    }

    private void verifyDateComponent(Component component) {
        assertTrue(component instanceof LocalDateWidget);
        LocalDateWidget widget = (LocalDateWidget) component;
        assertEquals("Date Received", widget.getCaption());
    }

    private void verifyTextFieldComponent(Component component, String caption, boolean isReadonly) {
        assertTrue(component instanceof TextField);
        TextField textField = (TextField) component;
        assertEquals(isReadonly, textField.isReadOnly());
        assertEquals(caption, textField.getCaption());
        assertEquals(100, component.getWidth(), 0);
        assertEquals(StringUtils.EMPTY, textField.getValue());
    }

    private void verifyButtonsLayout(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        Button loadButton = verifyButton(layout.getComponent(0), "Upload");
        verifyButton(layout.getComponent(1), "Close");
        assertEquals(1, loadButton.getListeners(Button.ClickEvent.class).size());
        verifyLoadClickListener(loadButton);
    }

    private Button verifyButton(Component component, String caption) {
        assertTrue(component instanceof Button);
        assertEquals(caption, component.getCaption());
        return (Button) component;
    }

    private void verifyLoadClickListener(Button loadButton) {
        mockStatic(Windows.class);
        Collection<? extends AbstractField<?>> fields = Lists.newArrayList(
            Whitebox.getInternalState(window, FUND_POOL_NAME_FIELD),
            Whitebox.getInternalState(window, ASSESSMENT_NAME_FIELD),
            Whitebox.getInternalState(window, GROSS_AMOUNT_FIELD),
            Whitebox.getInternalState(window, ITEM_BANK_SPLIT_PERCENT_FIELD),
            Whitebox.getInternalState(window, ACCOUNT_NUMBER_FIELD),
            Whitebox.getInternalState(window, LICENSEE_NAME_FIELD),
            Whitebox.getInternalState(window, DATE_RECEIVED_FIELD),
            Whitebox.getInternalState(window, GRADE_K_TO_5_NUM_OF_STUDENTS_FIELD),
            Whitebox.getInternalState(window, GRADE_6_TO_8_NUM_OF_STUDENTS_FIELD),
            Whitebox.getInternalState(window, GRADE_9_TO_12_NUM_OF_STUDENTS_FIELD));
        Windows.showValidationErrorWindow(fields);
        expectLastCall().once();
        replay(Windows.class);
        loadButton.click();
        verify(Windows.class);
        reset(Windows.class);
    }

    private void setTextFieldValue(String field, String value) {
        ((TextField) Whitebox.getInternalState(window, field)).setValue(value);
    }

    private void setLocalDateWidgetValue(String field, LocalDate value) {
        ((LocalDateWidget) Whitebox.getInternalState(window, field)).setValue(value);
    }

    private void verifyFieldErrorMessage(Binder binder, String fieldName, String message) {
        binder.validate();
        AbstractComponent textField = Whitebox.getInternalState(window, fieldName);
        assertEquals(message, textField.getErrorMessage().toString());
    }

    private void verifyFieldIsValid(Binder binder, String fieldName) {
        binder.validate();
        AbstractComponent textField = Whitebox.getInternalState(window, fieldName);
        assertNull(textField.getErrorMessage());
    }
}
