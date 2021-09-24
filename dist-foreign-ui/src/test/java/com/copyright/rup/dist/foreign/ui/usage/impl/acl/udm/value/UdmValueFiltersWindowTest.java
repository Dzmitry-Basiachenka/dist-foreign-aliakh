package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.value;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;

import com.copyright.rup.dist.foreign.domain.filter.FilterExpression;
import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmValueFilter;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueFilterController;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.BaseUdmItemsFilterWidget;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Iterator;

/**
 * Verifies {@link UdmValueFiltersWindow}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/23/2021
 *
 * @author Aliaksandr Liakh
 */
public class UdmValueFiltersWindowTest {

    private static final String UNCHECKED = "unchecked";
    private static final String ASSIGNEE = "wjohn@copyright.com";
    private static final String LAST_VALUE_PERIOD = "062021";
    private static final Long WR_WRK_INST = 243904752L;
    private static final String SYSTEM_TITLE = "Medical Journal";
    private static final String SYSTEM_STANDARD_NUMBER = "0927-7765";
    private static final Long RH_ACCOUNT_NUMBER = 100000001L;
    private static final String RH_NAME = "Rothchild Consultants";
    private static final BigDecimal PRICE = new BigDecimal("100.00");
    private static final BigDecimal PRICE_IN_USD = new BigDecimal("200.00");
    private static final String LAST_PRICE_FLAG = "Y";
    private static final String LAST_PRICE_COMMENT = "last price comment";
    private static final String CONTENT = "content";
    private static final String LAST_CONTENT_FLAG = "N";
    private static final String LAST_CONTENT_COMMENT = "last content comment";
    private static final String PUB_TYPE = "BK";
    private static final String LAST_PUB_TYPE = "BK2";
    private static final String COMMENT = "comment";
    private UdmValueFiltersWindow window;

    @Before
    public void setUp() {
        window = new UdmValueFiltersWindow(createMock(IUdmValueFilterController.class), new UdmValueFilter());
    }

    @Test
    public void testConstructor() {
        assertEquals("UDM values additional filters", window.getCaption());
        assertEquals(550, window.getWidth(), 0);
        assertEquals(Unit.PIXELS, window.getWidthUnits());
        assertEquals(560, window.getHeight(), 0);
        assertEquals(Unit.PIXELS, window.getHeightUnits());
        verifyRootLayout(window.getContent());
    }

    @Test
    public void testConstructorWithPopulatedFilter() {
        UdmValueFilter valueFilter = buildExpectedFilter();
        valueFilter.setAssignees(Collections.singleton(ASSIGNEE));
        valueFilter.setLastValuePeriods(Collections.singleton(LAST_VALUE_PERIOD));
        valueFilter.setWrWrkInst(WR_WRK_INST);
        valueFilter.setSystemTitleExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_TITLE, null));
        valueFilter.setSystemStandardNumberExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS,
            SYSTEM_STANDARD_NUMBER, null));
        valueFilter.setRhAccountNumber(RH_ACCOUNT_NUMBER);
        valueFilter.setRhNameExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, RH_NAME, null));
        valueFilter.setPriceExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, PRICE, null));
        valueFilter.setPriceInUsdExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, PRICE_IN_USD, null));
        valueFilter.setLastPriceFlag(LAST_PRICE_FLAG);
        valueFilter.setLastPriceComment(LAST_PRICE_COMMENT);
        valueFilter.setContentExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, CONTENT, null));
        valueFilter.setLastContentFlag(LAST_CONTENT_FLAG);
        valueFilter.setLastContentComment(LAST_CONTENT_COMMENT);
        valueFilter.setPubType(PUB_TYPE);
        valueFilter.setLastPubType(LAST_PUB_TYPE);
        valueFilter.setComment(COMMENT);
        window = new UdmValueFiltersWindow(createMock(IUdmValueFilterController.class), valueFilter);
        verifyFiltersData();
    }

    @Test
    public void testSaveButtonClickListener() {
        UdmValueFilter appliedValueFilter = window.getAppliedValueFilter();
        assertTrue(appliedValueFilter.isEmpty());
        populateData();
        HorizontalLayout buttonsLayout = (HorizontalLayout) ((VerticalLayout) window.getContent()).getComponent(13);
        Button saveButton = (Button) buttonsLayout.getComponent(0);
        saveButton.click();
        assertEquals(buildExpectedFilter(), window.getAppliedValueFilter());
    }

    @Test
    public void testClearButtonClickListener() {
        populateData();
        HorizontalLayout buttonsLayout = (HorizontalLayout) ((VerticalLayout) window.getContent()).getComponent(13);
        Button clearButton = (Button) buttonsLayout.getComponent(1);
        clearButton.click();
        assertTrue(window.getAppliedValueFilter().isEmpty());
    }

    private void verifyRootLayout(Component component) {
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(14, verticalLayout.getComponentCount());
        verifyItemsFilterLayout(verticalLayout.getComponent(0), "Assignees", "Last Value Periods");
        verifyTextField(verticalLayout.getComponent(1), "Wr Wrk Inst");
        verifyFieldWithOperatorComponent(verticalLayout.getComponent(2), "System Title");
        verifyFieldWithOperatorComponent(verticalLayout.getComponent(3), "System Standard Number");
        verifyTextField(verticalLayout.getComponent(4), "RH Account #");
        verifyFieldWithOperatorComponent(verticalLayout.getComponent(5), "RH Name");
        verifyFieldWithOperatorComponent(verticalLayout.getComponent(6), "Price");
        verifyFieldWithOperatorComponent(verticalLayout.getComponent(7), "Price in USD");
        verifyTextFieldLayout(verticalLayout.getComponent(8), ComboBox.class, "Last Price Flag",
            TextField.class, "Last Price Comment");
        verifyFieldWithOperatorComponent(verticalLayout.getComponent(9), "Content");
        verifyTextFieldLayout(verticalLayout.getComponent(10), ComboBox.class, "Last Content Flag",
            TextField.class, "Last Content Comment");
        verifyTextFieldLayout(verticalLayout.getComponent(11), ComboBox.class, "Pub Type",
            ComboBox.class, "Last Pub Type");
        verifyTextField(verticalLayout.getComponent(12), "Comment");
        verifyButtonsLayout(verticalLayout.getComponent(13));
    }

    private void verifyItemsFilterLayout(Component component, String firstCaption, String secondCaption) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        verifyItemsFilterWidget(layout.getComponent(0), firstCaption);
        verifyItemsFilterWidget(layout.getComponent(1), secondCaption);
    }

    private void verifyItemsFilterWidget(Component component, String caption) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertTrue(layout.isEnabled());
        assertTrue(layout.isSpacing());
        Iterator<Component> iterator = layout.iterator();
        assertEquals("(0)", ((Label) iterator.next()).getValue());
        Button button = (Button) iterator.next();
        assertEquals(caption, button.getCaption());
        assertEquals(2, button.getListeners(ClickEvent.class).size());
        assertTrue(button.isDisableOnClick());
        assertTrue(StringUtils.contains(button.getStyleName(), Cornerstone.BUTTON_LINK));
        assertFalse(iterator.hasNext());
    }

    private void verifyFieldWithOperatorComponent(Component component, String caption) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        assertTrue(layout.getComponent(0) instanceof TextField);
        assertEquals(caption, layout.getComponent(0).getCaption());
        assertTrue(layout.getComponent(1) instanceof ComboBox);
        assertEquals("Operator", layout.getComponent(1).getCaption());
    }

    private void verifyTextFieldLayout(Component component, Class<?> firstClass, String firstCaption,
                                       Class<?> secondClass, String secondCaption) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        verifyComponent(layout.getComponent(0), firstClass, firstCaption, true);
        verifyComponent(layout.getComponent(1), secondClass, secondCaption, true);
    }

    private void verifyComponent(Component component, Class<?> clazz, String caption, boolean isEnabled) {
        assertTrue(clazz.isInstance(component));
        assertEquals(100, component.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
        assertEquals(component.getCaption(), caption);
        assertEquals(isEnabled, component.isEnabled());
    }

    private void verifyTextField(Component component, String caption) {
        assertTrue(component instanceof TextField);
        assertEquals(257, component.getWidth(), 0);
        assertEquals(Unit.PIXELS, component.getWidthUnits());
        assertEquals(component.getCaption(), caption);
    }

    private void verifyButtonsLayout(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(3, layout.getComponentCount());
        verifyButton(layout.getComponent(0), "Save");
        verifyButton(layout.getComponent(1), "Clear");
        verifyButton(layout.getComponent(2), "Close");
    }

    private void verifyButton(Component component, String caption) {
        assertTrue(component instanceof Button);
        assertEquals(caption, component.getCaption());
    }

    private UdmValueFilter buildExpectedFilter() {
        UdmValueFilter valueFilter = new UdmValueFilter();
        valueFilter.setWrWrkInst(WR_WRK_INST);
        valueFilter.setSystemTitleExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_TITLE, null));
        valueFilter.setSystemStandardNumberExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS,
            SYSTEM_STANDARD_NUMBER, null));
        valueFilter.setRhAccountNumber(RH_ACCOUNT_NUMBER);
        valueFilter.setRhNameExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, RH_NAME, null));
        valueFilter.setPriceExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, PRICE, null));
        valueFilter.setPriceInUsdExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, PRICE_IN_USD, null));
        valueFilter.setLastPriceFlag(LAST_PRICE_FLAG);
        valueFilter.setLastPriceComment(LAST_PRICE_COMMENT);
        valueFilter.setContentExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, CONTENT, null));
        valueFilter.setLastContentFlag(LAST_CONTENT_FLAG);
        valueFilter.setLastContentComment(LAST_CONTENT_COMMENT);
        valueFilter.setPubType(PUB_TYPE);
        valueFilter.setLastPubType(LAST_PUB_TYPE);
        valueFilter.setComment(COMMENT);
        return valueFilter;
    }

    @SuppressWarnings(UNCHECKED)
    private void verifyFiltersData() {
        assertFilterWidgetLabelValue("assigneeFilterWidget", "(1)");
        assertFilterWidgetLabelValue("lastValuePeriodFilterWidget", "(1)");
        assertTextFieldValue("wrWrkInstField", WR_WRK_INST.toString());
        assertTextFieldValue("systemTitleField", SYSTEM_TITLE);
        assertComboBoxValue("systemTitleOperatorComboBox", FilterOperatorEnum.EQUALS);
        assertTextFieldValue("systemStandardNumberField", SYSTEM_STANDARD_NUMBER);
        assertComboBoxValue("systemStandardNumberOperatorComboBox", FilterOperatorEnum.EQUALS);
        assertTextFieldValue("rhAccountNumberField", RH_ACCOUNT_NUMBER.toString());
        assertTextFieldValue("rhNameField", RH_NAME);
        assertComboBoxValue("rhNameOperatorComboBox", FilterOperatorEnum.EQUALS);
        assertTextFieldValue("priceField", PRICE.toString());
        assertComboBoxValue("priceOperatorComboBox", FilterOperatorEnum.EQUALS);
        assertTextFieldValue("priceInUsdField", PRICE_IN_USD.toString());
        assertComboBoxValue("priceInUsdOperatorComboBox", FilterOperatorEnum.EQUALS);
        assertComboBoxValue("lastPriceFlagComboBox", LAST_PRICE_FLAG);
        assertTextFieldValue("lastPriceCommentField", LAST_PRICE_COMMENT);
        assertTextFieldValue("contentField", CONTENT);
        assertComboBoxValue("contentOperatorComboBox", FilterOperatorEnum.EQUALS);
        assertComboBoxValue("lastContentFlagComboBox", LAST_CONTENT_FLAG);
        assertTextFieldValue("lastContentCommentField", LAST_CONTENT_COMMENT);
        assertComboBoxValue("pubTypeComboBox", PUB_TYPE);
        assertComboBoxValue("lastPubTypeComboBox", LAST_PUB_TYPE);
        assertTextFieldValue("commentField", COMMENT);
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

    @SuppressWarnings(UNCHECKED)
    private void populateData() {
        populateTextField("wrWrkInstField", String.valueOf(WR_WRK_INST));
        populateTextField("systemTitleField", SYSTEM_TITLE);
        populateComboBox("systemTitleOperatorComboBox", FilterOperatorEnum.EQUALS);
        populateTextField("systemStandardNumberField", SYSTEM_STANDARD_NUMBER);
        populateComboBox("systemStandardNumberOperatorComboBox", FilterOperatorEnum.EQUALS);
        populateTextField("rhAccountNumberField", String.valueOf(RH_ACCOUNT_NUMBER));
        populateTextField("rhNameField", RH_NAME);
        populateComboBox("rhNameOperatorComboBox", FilterOperatorEnum.EQUALS);
        populateTextField("priceField", PRICE.toString());
        populateComboBox("priceOperatorComboBox", FilterOperatorEnum.EQUALS);
        populateTextField("priceInUsdField", PRICE_IN_USD.toString());
        populateComboBox("priceInUsdOperatorComboBox", FilterOperatorEnum.EQUALS);
        populateComboBox("lastPriceFlagComboBox", LAST_PRICE_FLAG);
        populateTextField("lastPriceCommentField", LAST_PRICE_COMMENT);
        populateTextField("contentField", CONTENT);
        populateComboBox("contentOperatorComboBox", FilterOperatorEnum.EQUALS);
        populateComboBox("lastContentFlagComboBox", LAST_CONTENT_FLAG);
        populateTextField("lastContentCommentField", LAST_CONTENT_COMMENT);
        populateComboBox("pubTypeComboBox", PUB_TYPE);
        populateComboBox("lastPubTypeComboBox", LAST_PUB_TYPE);
        populateTextField("commentField", COMMENT);
    }

    private void populateTextField(String fieldName, String value) {
        ((TextField) Whitebox.getInternalState(window, fieldName)).setValue(value);
    }

    @SuppressWarnings(UNCHECKED)
    private <T> void populateComboBox(String fieldName, T value) {
        ((ComboBox<T>) Whitebox.getInternalState(window, fieldName)).setValue(value);
    }
}
