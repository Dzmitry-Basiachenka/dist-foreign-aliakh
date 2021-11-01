package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.value;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.Currency;
import com.copyright.rup.dist.foreign.domain.UdmValueStatusEnum;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueFilterController;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * Verifies {@link UdmValueFilterWidget}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/01/2021
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Windows.class)
public class UdmValueFilterWidgetTest {

    private static final Set<UdmValueStatusEnum> VALUE_STATUSES =
        new LinkedHashSet<>(Arrays.asList(UdmValueStatusEnum.NEW,
            UdmValueStatusEnum.RSCHD_IN_THE_PREV_PERIOD, UdmValueStatusEnum.PRELIM_RESEARCH_COMPLETE,
            UdmValueStatusEnum.NEEDS_FURTHER_REVIEW, UdmValueStatusEnum.RESEARCH_COMPLETE));
    private static final List<Currency> CURRENCIES =
        Arrays.asList(new Currency("USD", "US Dollar"), new Currency("AUD", "Australian Dollar"),
            new Currency("CAD", "Canadian Dollar"), new Currency("EUR", "Euro"), new Currency("GBP", "Pound Sterling"),
            new Currency("JPY", "Yen"), new Currency("BRL", "Brazilian Real"), new Currency("CNY", "Yuan Renminbi"),
            new Currency("CZK", "Czech Koruna"), new Currency("DKK", "Danish Krone"),
            new Currency("NZD", "New Zealand Dollar"), new Currency("NOK", "Norwegian Kron"),
            new Currency("ZAR", "Rand"), new Currency("CHF", "Swiss Franc"), new Currency("INR", "Indian Rupee"));

    private UdmValueFilterWidget widget;
    private IUdmValueFilterController controller;

    @Before
    public void setUp() {
        controller = createMock(IUdmValueFilterController.class);
        expect(controller.getAllCurrencies()).andReturn(CURRENCIES).once();
        replay(controller);
        widget = new UdmValueFilterWidget(controller);
        widget.setController(controller);
    }

    @Test
    public void testInit() {
        assertSame(widget, widget.init());
        assertEquals(2, widget.getComponentCount());
        assertEquals(new MarginInfo(true), widget.getMargin());
        verifyFiltersLayout(widget.getComponent(0));
        verifyButtonsLayout(widget.getComponent(1));
        verify(controller);
    }

    @Test
    public void testApplyFilter() {
        widget.init();
        widget.clearFilter();
        verify(controller);
        Button applyButton = getApplyButton();
        assertFalse(applyButton.isEnabled());
        assertTrue(widget.getAppliedFilter().isEmpty());
        assertTrue(widget.getFilter().isEmpty());
        widget.getFilter().setStatus(UdmValueStatusEnum.NEW);
        assertNotEquals(widget.getFilter(), widget.getAppliedFilter());
        applyButton.setEnabled(true);
        assertFalse(widget.getFilter().isEmpty());
        assertTrue(applyButton.isEnabled());
        applyButton.click();
        assertFalse(applyButton.isEnabled());
        assertFalse(widget.getAppliedFilter().isEmpty());
    }

    @Test
    public void testFilterChangedEmptyFilter() {
        widget.init();
        Button applyButton = getApplyButton();
        assertFalse(applyButton.isEnabled());
        widget.applyFilter();
        verify(controller);
        assertFalse(applyButton.isEnabled());
    }

    @Test
    public void testClearFilter() {
        widget.init();
        Button applyButton = getApplyButton();
        assertTrue(widget.getFilter().isEmpty());
        assertTrue(widget.getAppliedFilter().isEmpty());
        widget.getFilter().setStatus(UdmValueStatusEnum.NEW);
        applyButton.setEnabled(true);
        applyButton.click();
        assertFalse(widget.getFilter().isEmpty());
        assertFalse(widget.getAppliedFilter().isEmpty());
        widget.clearFilter();
        assertTrue(widget.getFilter().isEmpty());
        assertTrue(widget.getAppliedFilter().isEmpty());
        assertFalse(applyButton.isEnabled());
        assertNull(Whitebox.<ComboBox<?>>getInternalState(widget, "statusComboBox").getValue());
        assertNull(Whitebox.<ComboBox<?>>getInternalState(widget, "currencyComboBox").getValue());
        verify(controller);
    }

    @Test
    public void verifyMoreFiltersButtonClickListener() {
        reset(controller);
        expect(controller.getAllCurrencies()).andReturn(CURRENCIES).once();
        expect(controller.getPublicationTypes()).andReturn(new ArrayList<>()).once();
        replay(controller);
        mockStatic(Windows.class);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        Windows.showModalWindow(anyObject(UdmValueFiltersWindow.class));
        expectLastCall().once();
        replay(clickEvent, Windows.class, controller);
        widget.init();
        ClickListener clickListener = (ClickListener) ((Button) Whitebox.getInternalState(widget, "moreFiltersButton"))
            .getListeners(ClickEvent.class).iterator().next();
        clickListener.buttonClick(clickEvent);
        verify(clickEvent, Windows.class, controller);
    }

    @Test
    public void verifyApplyButtonClickListener() {
        widget.init();
        widget.clearFilter();
        verify(controller);
        Button applyButton = getApplyButton();
        assertFalse(applyButton.isEnabled());
        assertTrue(widget.getAppliedFilter().getPeriods().isEmpty());
        assertTrue(widget.getFilter().getPeriods().isEmpty());
        widget.getFilter().setPeriods(Collections.singleton(202106));
        assertNotEquals(widget.getFilter(), widget.getAppliedFilter());
        applyButton.setEnabled(true);
        assertTrue(widget.getAppliedFilter().getPeriods().isEmpty());
        assertFalse(widget.getFilter().getPeriods().isEmpty());
        assertTrue(applyButton.isEnabled());
        applyButton.click();
        assertFalse(applyButton.isEnabled());
        assertFalse(widget.getAppliedFilter().getPeriods().isEmpty());
    }

    @Test
    public void verifyButtonClickListener() {
        ClickEvent clickEvent = createMock(ClickEvent.class);
        replay(clickEvent, controller);
        widget.init();
        Button applyButton = getApplyButton();
        widget.getFilter().setStatus(UdmValueStatusEnum.NEW);
        applyButton.setEnabled(true);
        assertFalse(widget.getFilter().isEmpty());
        Button clearButton = (Button) ((HorizontalLayout) widget.getComponent(1)).getComponent(1);
        ClickListener clickListener = (ClickListener) clearButton.getListeners(ClickEvent.class).iterator().next();
        clickListener.buttonClick(clickEvent);
        assertFalse(applyButton.isEnabled());
        assertTrue(widget.getFilter().isEmpty());
        verify(clickEvent, controller);
    }

    private void verifyFiltersLayout(Component layout) {
        assertTrue(layout instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) layout;
        assertEquals(5, verticalLayout.getComponentCount());
        verifyFiltersLabel(verticalLayout.getComponent(0));
        verifyPeriodsFilterLayout(verticalLayout.getComponent(1));
        verifyValuesStatusComboBox(verticalLayout.getComponent(2));
        verifyCurrencyComboBox(verticalLayout.getComponent(3));
        verifyMoreFiltersButton(verticalLayout.getComponent(4));
    }

    private void verifyPeriodsFilterLayout(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertTrue(layout.isSpacing());
        Iterator<Component> iterator = layout.iterator();
        assertEquals("(0)", ((Label) iterator.next()).getValue());
        Button button = (Button) iterator.next();
        assertEquals("Periods", button.getCaption());
        assertEquals(2, button.getListeners(ClickEvent.class).size());
        assertTrue(button.isDisableOnClick());
        assertTrue(StringUtils.contains(button.getStyleName(), Cornerstone.BUTTON_LINK));
        assertFalse(iterator.hasNext());
    }

    @SuppressWarnings("unchecked")
    private void verifyCurrencyComboBox(Component component) {
        assertTrue(component instanceof ComboBox);
        ComboBox<?> comboBox = (ComboBox<?>) component;
        assertEquals("Currency", comboBox.getCaption());
        assertEquals(100, comboBox.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, comboBox.getWidthUnits());
        ListDataProvider<Currency> listDataProvider =
            (ListDataProvider<Currency>) comboBox.getDataProvider();
        Object[] items = listDataProvider.getItems().toArray();
        assertEquals(CURRENCIES.size(), items.length);
        IntStream.range(0, CURRENCIES.size()).forEach(i -> verifyCurrencies(CURRENCIES.get(i), (Currency) items[i]));
    }

    private void verifyFiltersLabel(Component component) {
        assertTrue(component instanceof Label);
        Label label = (Label) component;
        assertEquals("Filters", label.getValue());
        assertEquals(Cornerstone.LABEL_H2, label.getStyleName());
    }

    private void verifyValuesStatusComboBox(Component component) {
        assertTrue(component instanceof ComboBox);
        ComboBox<?> comboBox = (ComboBox<?>) component;
        assertEquals("Status", comboBox.getCaption());
        assertEquals(100, comboBox.getWidth(), 0);
        assertEquals("220px", comboBox.getPopupWidth());
        assertEquals(Unit.PERCENTAGE, comboBox.getWidthUnits());
        ListDataProvider<UdmValueStatusEnum> listDataProvider =
            (ListDataProvider<UdmValueStatusEnum>) comboBox.getDataProvider();
        assertEquals(VALUE_STATUSES, listDataProvider.getItems());
    }

    private void verifyMoreFiltersButton(Component component) {
        assertTrue(component instanceof Button);
        Button button = (Button) component;
        assertEquals("More Filters", component.getCaption());
        assertTrue(StringUtils.contains(button.getStyleName(), Cornerstone.BUTTON_LINK));
    }

    private void verifyButtonsLayout(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        assertTrue(layout.isSpacing());
        verifyButton(layout.getComponent(0), "Apply");
        verifyButton(layout.getComponent(1), "Clear");
    }

    private void verifyButton(Component component, String caption) {
        assertTrue(component instanceof Button);
        Button button = (Button) component;
        assertEquals(caption, button.getCaption());
        assertEquals(100, button.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, button.getWidthUnits());
        verifyButtonClickListener(button);
    }

    private void verifyButtonClickListener(Button button) {
        Collection<?> listeners = button.getListeners(ClickEvent.class);
        assertTrue(CollectionUtils.isNotEmpty(listeners));
        assertEquals(1, listeners.size());
        assertNotNull(listeners.iterator().next());
    }

    private void verifyCurrencies(Currency expectedCurrency, Currency actualCurrency) {
        assertEquals(expectedCurrency.getCode(), actualCurrency.getCode());
        assertEquals(expectedCurrency.getDescription(), actualCurrency.getDescription());
    }

    private Button getApplyButton() {
        return Whitebox.getInternalState(widget, "applyButton");
    }
}
