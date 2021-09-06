package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.UdmUsageOriginEnum;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineFilterController;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
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

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * Verifies {@link UdmBaselineFilterWidget}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/01/2021
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class})
public class UdmBaselineFilterWidgetTest {

    private IUdmBaselineFilterController controller;
    private UdmBaselineFilterWidget widget;

    @Before
    public void setUp() {
        controller = createMock(IUdmBaselineFilterController.class);
        widget = new UdmBaselineFilterWidget(controller);
        widget.setController(controller);
    }

    @Test
    public void testInit() {
        expect(controller.getPeriods()).andReturn(Collections.singletonList(202012)).once();
        replay(controller);
        assertSame(widget, widget.init());
        assertEquals(2, widget.getComponentCount());
        assertEquals(new MarginInfo(true), widget.getMargin());
        verifyFiltersLayout(widget.getComponent(0));
        verifyButtonsLayout(widget.getComponent(1));
        verify(controller);
    }

    @Test
    public void testApplyFilter() {
        expect(controller.getPeriods()).andReturn(Collections.singletonList(202012)).times(2);
        replay(controller);
        widget.init();
        widget.clearFilter();
        verify(controller);
        Button applyButton = getApplyButton();
        assertFalse(applyButton.isEnabled());
        assertTrue(widget.getFilter().isEmpty());
        assertTrue(widget.getAppliedFilter().isEmpty());
        widget.getFilter().setChannel(UdmChannelEnum.CCC);
        assertNotEquals(widget.getFilter(), widget.getAppliedFilter());
        applyButton.setEnabled(true);
        assertFalse(widget.getFilter().isEmpty());
        assertTrue(widget.getAppliedFilter().isEmpty());
        applyButton.click();
        assertFalse(applyButton.isEnabled());
        assertFalse(widget.getAppliedFilter().isEmpty());
    }

    @Test
    public void testClearFilter() {
        expect(controller.getPeriods()).andReturn(Collections.singletonList(202012)).times(2);
        replay(controller);
        widget.init();
        Button applyButton = getApplyButton();
        assertTrue(widget.getFilter().isEmpty());
        assertTrue(widget.getAppliedFilter().isEmpty());
        widget.getFilter().setChannel(UdmChannelEnum.CCC);
        applyButton.setEnabled(true);
        applyButton.click();
        assertFalse(widget.getFilter().isEmpty());
        assertFalse(widget.getAppliedFilter().isEmpty());
        widget.clearFilter();
        assertTrue(widget.getFilter().isEmpty());
        assertTrue(widget.getAppliedFilter().isEmpty());
        assertFalse(applyButton.isEnabled());
        ComboBox<?> periodComboBox = Whitebox.getInternalState(widget, "periodComboBox");
        assertNull(periodComboBox.getValue());
        verify(controller);
    }

    @Test
    public void testFilterChangedEmptyFilter() {
        expect(controller.getPeriods()).andReturn(Collections.singletonList(202012)).once();
        replay(controller);
        widget.init();
        Button applyButton = getApplyButton();
        assertFalse(applyButton.isEnabled());
        widget.applyFilter();
        verify(controller);
        assertFalse(applyButton.isEnabled());
    }

    @Test
    public void verifyMoreFiltersButtonClickListener() {
        mockStatic(Windows.class);
        Button.ClickEvent clickEvent = createMock(Button.ClickEvent.class);
        expect(controller.getPeriods()).andReturn(Collections.singletonList(202012)).once();
        Windows.showModalWindow(anyObject(UdmBaselineFiltersWindow.class));
        expectLastCall().once();
        replay(clickEvent, Windows.class, controller);
        widget.init();
        Button.ClickListener clickListener =
            (Button.ClickListener) ((Button) Whitebox.getInternalState(widget, "moreFiltersButton"))
                .getListeners(Button.ClickEvent.class).iterator().next();
        clickListener.buttonClick(clickEvent);
        verify(clickEvent, Windows.class, controller);
    }

    private void verifyFiltersLayout(Component layout) {
        assertTrue(layout instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) layout;
        assertEquals(5, verticalLayout.getComponentCount());
        verifyFiltersLabel(verticalLayout.getComponent(0));
        verifyComboBox(verticalLayout.getComponent(1), "Period", 202012);
        verifyComboBox(verticalLayout.getComponent(2), "Usage Origin", UdmUsageOriginEnum.values());
        verifyComboBox(verticalLayout.getComponent(3), "Channel", UdmChannelEnum.values());
        verifyMoreFiltersButton(verticalLayout.getComponent(4));
    }

    private void verifyFiltersLabel(Component component) {
        assertTrue(component instanceof Label);
        Label label = (Label) component;
        assertEquals("Filters", label.getValue());
        assertEquals(Cornerstone.LABEL_H2, label.getStyleName());
    }

    @SuppressWarnings("unchecked")
    private <T> void verifyComboBox(Component component, String caption, T... arrayValues) {
        assertTrue(component instanceof ComboBox);
        ComboBox<?> comboBox = (ComboBox<?>) component;
        assertEquals(caption, comboBox.getCaption());
        assertEquals(100, comboBox.getWidth(), 0);
        assertEquals(Sizeable.Unit.PERCENTAGE, comboBox.getWidthUnits());
        ListDataProvider<Integer> listDataProvider = (ListDataProvider<Integer>) comboBox.getDataProvider();
        Collection<?> actualItems = listDataProvider.getItems();
        assertEquals(arrayValues.length, actualItems.size());
        assertEquals(Arrays.asList(arrayValues), actualItems);
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
        assertEquals(Sizeable.Unit.PERCENTAGE, button.getWidthUnits());
        verifyButtonClickListener(button);
    }

    private void verifyButtonClickListener(Button button) {
        Collection<?> listeners = button.getListeners(Button.ClickEvent.class);
        assertTrue(CollectionUtils.isNotEmpty(listeners));
        assertEquals(1, listeners.size());
        assertNotNull(listeners.iterator().next());
    }

    private void verifyMoreFiltersButton(Component component) {
        assertTrue(component instanceof Button);
        Button button = (Button) component;
        assertEquals("More Filters", component.getCaption());
        assertTrue(StringUtils.contains(button.getStyleName(), Cornerstone.BUTTON_LINK));
        Collection<?> listeners = button.getListeners(Button.ClickEvent.class);
        assertEquals(1, listeners.size());
    }

    private Button getApplyButton() {
        return Whitebox.getInternalState(widget, "applyButton");
    }
}
