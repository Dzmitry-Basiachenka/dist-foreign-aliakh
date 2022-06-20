package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.grant;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.validateFieldAndVerifyErrorMessage;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyComboBox;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyItemsFilterWidget;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;

import com.copyright.rup.dist.foreign.domain.filter.AclGrantDetailFilter;
import com.copyright.rup.dist.foreign.domain.filter.FilterExpression;
import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclGrantDetailFilterController;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.BaseUdmItemsFilterWidget;

import com.vaadin.data.Binder;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link AclGrantDetailFiltersWindow}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/10/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclGrantDetailFiltersWindowTest {

    private static final String UNCHECKED = "unchecked";
    private static final String CAPTION_OPERATOR = "Operator";
    private static final List<FilterOperatorEnum> BOOLEAN_ITEMS =
        Arrays.asList(FilterOperatorEnum.Y, FilterOperatorEnum.N);
    private static final String LICENSE_TYPE = "MACL";
    private static final String GRANT_STATUS = "DENY";
    private static final String TYPE_OF_USE = "PRINT";
    private static final Integer GRANT_SET_PERIOD = 202212;
    private static final Long WR_WRK_INST = 243904752L;
    private static final Long RH_ACCOUNT_NUMBER = 100000001L;
    private static final String RH_NAME = "Rothchild Consultants";
    private static final FilterExpression<Boolean> ELIGIBLE = new FilterExpression<>(FilterOperatorEnum.Y);
    private static final FilterExpression<Boolean> EDITABLE = new FilterExpression<>(FilterOperatorEnum.Y);
    private static final String VALID_INTEGER = "123456789";
    private static final String VALID_DECIMAL = "1.2345678";
    private static final String INVALID_NUMBER = "a12345678";
    private static final String INTEGER_WITH_SPACES_STRING = "  123  ";
    private static final String SPACES_STRING = "   ";
    private static final String NUMBER_VALIDATION_MESSAGE = "Field value should contain numeric values only";
    private static final String BETWEEN_OPERATOR_VALIDATION_MESSAGE =
        "Field value should be populated for Between Operator";

    private AclGrantDetailFiltersWindow window;
    private Binder<AclGrantDetailFilter> binder;

    @Before
    public void setUp() {
        IAclGrantDetailFilterController controller = createMock(IAclGrantDetailFilterController.class);
        expect(controller.getGrantPeriods()).andReturn(
            new ArrayList<>(Arrays.asList(202212, 202112))).once();
        replay(controller);
        window = new AclGrantDetailFiltersWindow(controller, new AclGrantDetailFilter());
        binder = Whitebox.getInternalState(window, "filterBinder");
        verify(controller);
    }

    @Test
    public void testConstructor() {
        verifyWindow(window, "ACL grant set additional filters", 600, 350, Unit.PIXELS);
        VerticalLayout verticalLayout = verifyRootLayout(window.getContent());
        verifyPanel(verticalLayout.getComponent(0));
    }

    @Test
    public void testConstructorWithPopulatedFilter() {
        AclGrantDetailFilter aclGrantDetailFilter = buildExpectedFilter();
        IAclGrantDetailFilterController controller = createMock(IAclGrantDetailFilterController.class);
        expect(controller.getGrantPeriods()).andReturn(
            new ArrayList<>(Collections.singletonList(GRANT_SET_PERIOD))).once();
        replay(controller);
        window = new AclGrantDetailFiltersWindow(controller, aclGrantDetailFilter);
        verify(controller);
        verifyFiltersData();
    }

    @Test
    public void testWrWrkInstFilterOperatorChangeListener() {
        testNumericFilterOperatorChangeListener(2);
    }

    @Test
    public void testRhAccountNumberOperatorChangeListener() {
        testNumericFilterOperatorChangeListener(3);
    }

    @Test
    public void testRhNameFilterOperatorChangeListener() {
        testTextFilterOperatorChangeListener(4);
    }

    @Test
    public void testSaveButtonClickListener() {
        AclGrantDetailFilter aclGrantDetailFilter = Whitebox.getInternalState(window, "aclGrantDetailFilter");
        assertTrue(aclGrantDetailFilter.isEmpty());
        populateData();
        HorizontalLayout buttonsLayout = (HorizontalLayout) ((VerticalLayout) window.getContent()).getComponent(1);
        Button saveButton = (Button) buttonsLayout.getComponent(0);
        saveButton.click();
        assertEquals(buildExpectedFilter(), aclGrantDetailFilter);
    }

    @Test
    public void testClearButtonClickListener() {
        AclGrantDetailFilter aclGrantDetailFilter = buildExpectedFilter();
        Whitebox.setInternalState(window, "aclGrantDetailFilter", aclGrantDetailFilter);
        assertFalse(aclGrantDetailFilter.isEmpty());
        HorizontalLayout buttonsLayout = (HorizontalLayout) ((VerticalLayout) window.getContent()).getComponent(1);
        Button clearButton = (Button) buttonsLayout.getComponent(1);
        clearButton.click();
        Button saveButton = (Button) buttonsLayout.getComponent(0);
        saveButton.click();
        assertTrue(aclGrantDetailFilter.isEmpty());
    }

    @Test
    public void testWrWrkInstValidation() {
        TextField wrWrkInstFromField = Whitebox.getInternalState(window, "wrWrkInstFromField");
        TextField wrWrkInstToField = Whitebox.getInternalState(window, "wrWrkInstToField");
        ComboBox<FilterOperatorEnum> wrWrkInstOperatorComboBox =
            Whitebox.getInternalState(window, "wrWrkInstOperatorComboBox");
        assertNumericOperatorComboBoxItems(wrWrkInstOperatorComboBox);
        verifyIntegerOperationValidations(wrWrkInstFromField, wrWrkInstToField, wrWrkInstOperatorComboBox,
            "Field value should be greater or equal to Wr Wrk Inst From", 9);
    }

    @Test
    public void testRhAccountNumberFieldValidation() {
        TextField rhAccountNumberFromField = Whitebox.getInternalState(window, "rhAccountNumberFromField");
        TextField rhAccountNumberToField = Whitebox.getInternalState(window, "rhAccountNumberToField");
        ComboBox<FilterOperatorEnum> rhAccountNumberOperatorComboBox =
            Whitebox.getInternalState(window, "rhAccountNumberOperatorComboBox");
        assertNumericOperatorComboBoxItems(rhAccountNumberOperatorComboBox);
        verifyIntegerOperationValidations(rhAccountNumberFromField, rhAccountNumberToField,
            rhAccountNumberOperatorComboBox, "Field value should be greater or equal to RH Account # From", 10);
    }

    @Test
    public void testRhNameValidation() {
        TextField rhNameField = Whitebox.getInternalState(window, "rhNameField");
        ComboBox<FilterOperatorEnum> rhNameOperatorComboBox =
            Whitebox.getInternalState(window, "rhNameOperatorComboBox");
        assertTextOperatorComboBoxItems(rhNameOperatorComboBox);
        validateFieldAndVerifyErrorMessage(rhNameField, buildStringWithExpectedLength(255), binder, null, true);
        validateFieldAndVerifyErrorMessage(rhNameField, buildStringWithExpectedLength(256), binder,
            "Field value should not exceed 255 characters", false);
    }

    private VerticalLayout verifyRootLayout(Component component) {
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(2, verticalLayout.getComponentCount());
        verifyButtonsLayout(verticalLayout.getComponent(1), "Save", "Clear", "Close");
        return verticalLayout;
    }

    private void verifyPanel(Component component) {
        assertTrue(component instanceof Panel);
        Component panelContent = ((Panel) component).getContent();
        assertTrue(panelContent instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) panelContent;
        assertEquals(6, verticalLayout.getComponentCount());
        verifyItemsFilterLayout(verticalLayout.getComponent(0), "License Types", "Grant Statuses", "Types of Use");
        verifyComboBox(verticalLayout.getComponent(1), "Grant Set Period", Unit.PERCENTAGE, 50, true,
            Arrays.asList(202212, 202112));
        verifyFieldWithNumericOperatorComponent(verticalLayout.getComponent(2), "Wr Wrk Inst From", "Wr Wrk Inst To");
        verifyFieldWithNumericOperatorComponent(verticalLayout.getComponent(3), "RH Account # From", "RH Account # To");
        verifyFieldWithTextOperatorComponent(verticalLayout.getComponent(4), "RH Name");
        verifyComboBoxLayout(verticalLayout.getComponent(5), "Eligible", BOOLEAN_ITEMS, "Editable",
            BOOLEAN_ITEMS);
    }

    private void verifyComboBoxLayout(Component component, String firstCaption, List<?> firstItemList,
                                      String secondCaption, List<?> secondItemList) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        verifyComboBox(layout.getComponent(0), firstCaption, true, firstItemList);
        verifyComboBox(layout.getComponent(1), secondCaption, true, secondItemList);
    }

    private void verifyFieldWithTextOperatorComponent(Component component, String caption) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        assertTrue(layout.getComponent(0) instanceof TextField);
        assertEquals(caption, layout.getComponent(0).getCaption());
        assertTrue(layout.getComponent(1) instanceof ComboBox);
        assertEquals(CAPTION_OPERATOR, layout.getComponent(1).getCaption());
    }

    private void verifyFieldWithNumericOperatorComponent(Component component, String captionFrom, String captionTo) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(3, layout.getComponentCount());
        assertTrue(layout.getComponent(0) instanceof TextField);
        assertEquals(captionFrom, layout.getComponent(0).getCaption());
        assertTrue(layout.getComponent(1) instanceof TextField);
        assertEquals(captionTo, layout.getComponent(1).getCaption());
        assertTrue(layout.getComponent(2) instanceof ComboBox);
        assertEquals(CAPTION_OPERATOR, layout.getComponent(2).getCaption());
    }

    private void verifyItemsFilterLayout(Component component, String firstCaption, String secondCaption,
                                         String thirdCaption) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(3, layout.getComponentCount());
        verifyItemsFilterWidget(layout.getComponent(0), firstCaption);
        verifyItemsFilterWidget(layout.getComponent(1), secondCaption);
        verifyItemsFilterWidget(layout.getComponent(2), thirdCaption);
    }

    @SuppressWarnings(UNCHECKED)
    private void testTextFilterOperatorChangeListener(int index) {
        VerticalLayout verticalLayout = (VerticalLayout) window.getContent();
        VerticalLayout panelContent = (VerticalLayout) ((Panel) verticalLayout.getComponent(0)).getContent();
        HorizontalLayout horizontalLayout = (HorizontalLayout) panelContent.getComponent(index);
        TextField textField = (TextField) horizontalLayout.getComponent(0);
        ComboBox<FilterOperatorEnum> operatorComboBox =
            (ComboBox<FilterOperatorEnum>) horizontalLayout.getComponent(1);
        assertEquals(3, ((ListDataProvider<?>) operatorComboBox.getDataProvider()).getItems().size());
        assertEquals(FilterOperatorEnum.EQUALS, operatorComboBox.getValue());
        assertTrue(textField.isEnabled());
        operatorComboBox.setValue(FilterOperatorEnum.DOES_NOT_EQUAL);
        assertTrue(textField.isEnabled());
        operatorComboBox.setValue(FilterOperatorEnum.CONTAINS);
        assertTrue(textField.isEnabled());
    }

    @SuppressWarnings(UNCHECKED)
    private void testNumericFilterOperatorChangeListener(int index) {
        VerticalLayout verticalLayout = (VerticalLayout) window.getContent();
        VerticalLayout panelContent = (VerticalLayout) ((Panel) verticalLayout.getComponent(0)).getContent();
        HorizontalLayout horizontalLayout = (HorizontalLayout) panelContent.getComponent(index);
        TextField fromField = (TextField) horizontalLayout.getComponent(0);
        TextField toField = (TextField) horizontalLayout.getComponent(1);
        ComboBox<FilterOperatorEnum> operatorComboBox =
            (ComboBox<FilterOperatorEnum>) horizontalLayout.getComponent(2);
        assertEquals(7, ((ListDataProvider<?>) operatorComboBox.getDataProvider()).getItems().size());
        assertEquals(FilterOperatorEnum.EQUALS, operatorComboBox.getValue());
        assertTrue(fromField.isEnabled());
        assertFalse(toField.isEnabled());
        operatorComboBox.setValue(FilterOperatorEnum.DOES_NOT_EQUAL);
        assertTrue(fromField.isEnabled());
        assertFalse(toField.isEnabled());
        operatorComboBox.setValue(FilterOperatorEnum.GREATER_THAN);
        assertTrue(fromField.isEnabled());
        assertFalse(toField.isEnabled());
        operatorComboBox.setValue(FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO);
        assertTrue(fromField.isEnabled());
        assertFalse(toField.isEnabled());
        operatorComboBox.setValue(FilterOperatorEnum.LESS_THAN);
        assertTrue(fromField.isEnabled());
        assertFalse(toField.isEnabled());
        operatorComboBox.setValue(FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO);
        assertTrue(fromField.isEnabled());
        assertFalse(toField.isEnabled());
        operatorComboBox.setValue(FilterOperatorEnum.BETWEEN);
        assertTrue(fromField.isEnabled());
        assertTrue(toField.isEnabled());
    }

    private void verifyIntegerOperationValidations(TextField fromField, TextField toField,
                                                   ComboBox<FilterOperatorEnum> operatorComboBox,
                                                   String fieldSpecificErrorMessage, int length) {
        verifyCommonOperationValidations(fromField, toField, operatorComboBox, NUMBER_VALIDATION_MESSAGE);
        validateFieldAndVerifyErrorMessage(fromField, SPACES_STRING, binder, NUMBER_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(fromField, "12345679", binder, null, true);
        validateFieldAndVerifyErrorMessage(toField, "12345678", binder, fieldSpecificErrorMessage, false);
        validateFieldAndVerifyErrorMessage(fromField, VALID_DECIMAL, binder, NUMBER_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(toField, VALID_DECIMAL, binder, NUMBER_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(fromField, INVALID_NUMBER, binder, NUMBER_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(toField, INVALID_NUMBER, binder, NUMBER_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(fromField, buildStringWithExpectedLength(length + 1), binder,
            String.format("Field value should not exceed %d digits", length), false);
        validateFieldAndVerifyErrorMessage(toField, buildStringWithExpectedLength(length + 1), binder,
            String.format("Field value should not exceed %d digits", length), false);
    }

    private void verifyCommonOperationValidations(TextField fromField, TextField toField,
                                                  ComboBox<FilterOperatorEnum> operatorComboBox,
                                                  String numberValidationMessage) {
        validateFieldAndVerifyErrorMessage(fromField, StringUtils.EMPTY, binder, null, true);
        validateFieldAndVerifyErrorMessage(fromField, INTEGER_WITH_SPACES_STRING, binder, null, true);
        operatorComboBox.setValue(FilterOperatorEnum.BETWEEN);
        validateFieldAndVerifyErrorMessage(
            fromField, StringUtils.EMPTY, binder, BETWEEN_OPERATOR_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(
            toField, StringUtils.EMPTY, binder, BETWEEN_OPERATOR_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(fromField, SPACES_STRING, binder, numberValidationMessage, false);
        validateFieldAndVerifyErrorMessage(toField, SPACES_STRING, binder, numberValidationMessage, false);
        operatorComboBox.setValue(FilterOperatorEnum.EQUALS);
        validateFieldAndVerifyErrorMessage(fromField, VALID_INTEGER, binder, null, true);
        validateFieldAndVerifyErrorMessage(toField, VALID_INTEGER, binder, null, true);
    }

    private AclGrantDetailFilter buildExpectedFilter() {
        AclGrantDetailFilter aclGrantDetailFilter = new AclGrantDetailFilter();
        aclGrantDetailFilter.setLicenseTypes(Collections.singleton(LICENSE_TYPE));
        aclGrantDetailFilter.setGrantStatuses(Collections.singleton(GRANT_STATUS));
        aclGrantDetailFilter.setTypeOfUses(Collections.singleton(TYPE_OF_USE));
        aclGrantDetailFilter.setGrantSetPeriod(GRANT_SET_PERIOD);
        aclGrantDetailFilter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, WR_WRK_INST, null));
        aclGrantDetailFilter.setRhAccountNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, RH_ACCOUNT_NUMBER, null));
        aclGrantDetailFilter.setRhNameExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, RH_NAME, null));
        aclGrantDetailFilter.setEligibleExpression(ELIGIBLE);
        aclGrantDetailFilter.setEditableExpression(EDITABLE);
        return aclGrantDetailFilter;
    }

    @SuppressWarnings(UNCHECKED)
    private void verifyFiltersData() {
        assertFilterWidgetLabelValue("licenseTypeFilterWidget", "(1)");
        assertFilterWidgetLabelValue("grantStatusFilterWidget", "(1)");
        assertFilterWidgetLabelValue("typeOfUseFilterWidget", "(1)");
        assertComboBoxValue("grantSetPeriodComboBox", GRANT_SET_PERIOD);
        assertTextFieldValue("wrWrkInstFromField", WR_WRK_INST.toString());
        assertComboBoxValue("wrWrkInstOperatorComboBox", FilterOperatorEnum.EQUALS);
        assertTextFieldValue("rhAccountNumberFromField", RH_ACCOUNT_NUMBER.toString());
        assertComboBoxValue("rhAccountNumberOperatorComboBox", FilterOperatorEnum.EQUALS);
        assertTextFieldValue("rhNameField", RH_NAME);
        assertComboBoxValue("rhNameOperatorComboBox", FilterOperatorEnum.EQUALS);
        assertComboBoxValue("eligibleComboBox", FilterOperatorEnum.Y);
        assertComboBoxValue("editableComboBox", FilterOperatorEnum.Y);
    }

    private void assertFilterWidgetLabelValue(String filterName, String value) {
        BaseUdmItemsFilterWidget filterWidget = Whitebox.getInternalState(window, filterName);
        assertEquals(value, ((Label) filterWidget.getComponent(0)).getValue());
    }

    private void assertTextFieldValue(String fieldName, Object value) {
        assertEquals(value, ((TextField) Whitebox.getInternalState(window, fieldName)).getValue());
    }

    @SuppressWarnings(UNCHECKED)
    private <T> void assertComboBoxValue(String fieldName, T value) {
        assertEquals(value, ((ComboBox<T>) Whitebox.getInternalState(window, fieldName)).getValue());
    }

    private void assertTextOperatorComboBoxItems(ComboBox<FilterOperatorEnum> operatorComboBox) {
        verifyComboBox(operatorComboBox, CAPTION_OPERATOR, Unit.PIXELS, 230, false,
            Arrays.asList(FilterOperatorEnum.EQUALS, FilterOperatorEnum.DOES_NOT_EQUAL, FilterOperatorEnum.CONTAINS));
    }

    private void assertNumericOperatorComboBoxItems(ComboBox<FilterOperatorEnum> operatorComboBox) {
        verifyComboBox(operatorComboBox, CAPTION_OPERATOR, Unit.PIXELS, 230, false,
            Arrays.asList(FilterOperatorEnum.EQUALS, FilterOperatorEnum.DOES_NOT_EQUAL,
                FilterOperatorEnum.GREATER_THAN, FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO,
                FilterOperatorEnum.LESS_THAN, FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO, FilterOperatorEnum.BETWEEN));
    }

    @SuppressWarnings(UNCHECKED)
    private void populateData() {
        AclGrantDetailFilter aclGrantDetailFilter = Whitebox.getInternalState(window, "aclGrantDetailFilter");
        aclGrantDetailFilter.setLicenseTypes(Collections.singleton(LICENSE_TYPE));
        aclGrantDetailFilter.setGrantStatuses(Collections.singleton(GRANT_STATUS));
        aclGrantDetailFilter.setTypeOfUses(Collections.singleton(TYPE_OF_USE));
        populateComboBox("grantSetPeriodComboBox", GRANT_SET_PERIOD);
        populateTextField("wrWrkInstFromField", String.valueOf(WR_WRK_INST));
        populateComboBox("wrWrkInstOperatorComboBox", FilterOperatorEnum.EQUALS);
        populateTextField("rhAccountNumberFromField", String.valueOf(RH_ACCOUNT_NUMBER));
        populateComboBox("rhAccountNumberOperatorComboBox", FilterOperatorEnum.EQUALS);
        populateTextField("rhNameField", RH_NAME);
        populateComboBox("rhNameOperatorComboBox", FilterOperatorEnum.EQUALS);
        populateComboBox("eligibleComboBox", FilterOperatorEnum.Y);
        populateComboBox("editableComboBox", FilterOperatorEnum.Y);
    }

    private void populateTextField(String fieldName, String value) {
        ((TextField) Whitebox.getInternalState(window, fieldName)).setValue(value);
    }

    @SuppressWarnings(UNCHECKED)
    private <T> void populateComboBox(String fieldName, T value) {
        ((ComboBox<T>) Whitebox.getInternalState(window, fieldName)).setValue(value);
    }

    private String buildStringWithExpectedLength(int length) {
        return StringUtils.repeat('1', length);
    }
}
