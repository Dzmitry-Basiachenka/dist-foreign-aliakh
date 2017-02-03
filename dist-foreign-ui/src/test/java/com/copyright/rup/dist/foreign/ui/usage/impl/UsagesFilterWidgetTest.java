package com.copyright.rup.dist.foreign.ui.usage.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.ui.component.LocalDateWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesFilterController;
import com.copyright.rup.vaadin.ui.Windows;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;

import com.google.common.collect.Sets;
import com.vaadin.data.Property;
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
import com.vaadin.ui.themes.BaseTheme;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Verifies {@link UsagesFilterWidget}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 01/12/2017
 *
 * @author Mikita Hladkikh
 * @author Aliaksandr Radkevich
 */
@RunWith(PowerMockRunner.class)
public class UsagesFilterWidgetTest {

    private UsagesFilterWidget widget;

    @Before
    public void setUp() {
        widget = new UsagesFilterWidget();
    }

    @Test
    public void testInit() {
        assertSame(widget, widget.init());
        assertEquals(2, widget.getComponentCount());
        assertEquals(new MarginInfo(true), widget.getMargin());
        verifyFiltersLayout(widget.getComponent(0));
        verifyButtonsLayout(widget.getComponent(1));
    }

    @Test
    public void testSetSelectedUsageBatches() {
        widget.init();
        Button applyButton = Whitebox.getInternalState(widget, "applyButton", UsagesFilterWidget.class);
        Set<String> usageBatchesIds = Sets.newHashSet(RupPersistUtils.generateUuid());
        assertFalse(applyButton.isEnabled());
        widget.setSelectedUsageBatches(usageBatchesIds);
        assertEquals(usageBatchesIds, widget.getFilter().getUsageBatchesIds());
        assertTrue(applyButton.isEnabled());
        verifyCountLabelValue("(1)", "batchesCountLabel");
    }

    @Test
    public void testSetSelectedRightsholders() {
        widget.init();
        Button applyButton = getApplyButton();
        Set<Long> accountNumbers = Sets.newHashSet(10000L);
        assertFalse(applyButton.isEnabled());
        widget.setSelectedRightsholders(accountNumbers);
        assertEquals(accountNumbers, widget.getFilter().getRhAccountNumbers());
        assertTrue(applyButton.isEnabled());
        verifyCountLabelValue("(1)", "rightsholdersCountLabel");
    }

    @Test
    @PrepareForTest(Windows.class)
    public void testApplyFilter() {
        mockStatic(Windows.class);
        widget.init();
        Button applyButton = getApplyButton();
        assertFalse(applyButton.isEnabled());
        widget.setSelectedRightsholders(Sets.newHashSet(10000L));
        assertTrue(applyButton.isEnabled());
        Windows.showNotificationWindow("Apply filter clicked");
        expectLastCall().once();
        replay(Windows.class);
        widget.applyFilter();
        verify(Windows.class);
        assertFalse(applyButton.isEnabled());
    }

    @Test
    @PrepareForTest(Windows.class)
    public void testFilterChangedEmptyFilter() {
        mockStatic(Windows.class);
        widget.init();
        Button applyButton = getApplyButton();
        assertFalse(applyButton.isEnabled());
        widget.setSelectedRightsholders(Collections.emptySet());
        Windows.showNotificationWindow("Apply filter clicked");
        expectLastCall().once();
        replay(Windows.class);
        widget.applyFilter();
        verify(Windows.class);
        assertFalse(applyButton.isEnabled());
    }

    @Test
    public void testGetController() {
        widget.init();
        UsagesFilterController controller = new UsagesFilterController();
        widget.setController(controller);
        assertSame(controller, widget.getController());
    }

    @Test
    public void testClearFilter() {
        widget.init();
        widget.clearFilter();
        assertFalse(getApplyButton().isEnabled());
        assertTrue(widget.getFilter().isEmpty());
        String labelValue = "(0)";
        verifyCountLabelValue(labelValue, "batchesCountLabel");
        verifyCountLabelValue(labelValue, "rightsholdersCountLabel");
        Property<LocalDate> paymentDateProperty =
            Whitebox.getInternalState(widget, "paymentDateProperty", UsagesFilterWidget.class);
        assertNull(paymentDateProperty.getValue());
        ComboBox statusComboBox = Whitebox.getInternalState(widget, "statusComboBox", UsagesFilterWidget.class);
        assertNull(statusComboBox.getValue());
        ComboBox taxCountryComboBox = Whitebox.getInternalState(widget, "taxCountryComboBox", UsagesFilterWidget.class);
        assertNull(taxCountryComboBox.getValue());
    }

    @Test
    @PrepareForTest(Windows.class)
    public void verifyApplyButtonClickListener() {
        widget.init();
        ClickListener clickListener = (ClickListener) getApplyButton().getListeners(ClickEvent.class).iterator().next();
        mockStatic(Windows.class);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        Windows.showNotificationWindow("Apply filter clicked");
        expectLastCall().once();
        replay(clickEvent, Windows.class);
        clickListener.buttonClick(clickEvent);
        verify(clickEvent);
    }

    @Test
    public void verifyButtonClickListener() {
        widget.init();
        widget.setSelectedRightsholders(Sets.newHashSet(10000L));
        Button applyButton = getApplyButton();
        assertTrue(applyButton.isEnabled());
        assertFalse(widget.getFilter().isEmpty());
        Button clearButton = (Button) ((HorizontalLayout) widget.getComponent(1)).getComponent(1);
        ClickListener clickListener = (ClickListener) clearButton.getListeners(ClickEvent.class).iterator().next();
        ClickEvent clickEvent = createMock(ClickEvent.class);
        replay(clickEvent);
        clickListener.buttonClick(clickEvent);
        assertFalse(applyButton.isEnabled());
        assertTrue(widget.getFilter().isEmpty());
        verify(clickEvent);
    }

    @Test
    public void testBatchesButtonClickListener() {
        widget.init();
        IUsagesFilterController filterController = createMock(IUsagesFilterController.class);
        widget.setController(filterController);
        VerticalLayout layout = (VerticalLayout) widget.getComponent(0);
        HorizontalLayout horizontalLayout = (HorizontalLayout) layout.getComponent(1);
        Button button = (Button) horizontalLayout.getComponent(1);
        Collection<?> listeners = button.getListeners(ClickEvent.class);
        ClickListener clickListener = (ClickListener) listeners.iterator().next();
        ClickEvent clickEvent = createMock(ClickEvent.class);
        filterController.onUsageBatchFilterClick();
        expectLastCall().once();
        replay(clickEvent, filterController);
        clickListener.buttonClick(clickEvent);
        verify(clickEvent, filterController);
    }

    @Test
    public void testRightsholdersButtonClickListener() {
        widget.init();
        IUsagesFilterController filterController = createMock(IUsagesFilterController.class);
        widget.setController(filterController);
        VerticalLayout layout = (VerticalLayout) widget.getComponent(0);
        HorizontalLayout horizontalLayout = (HorizontalLayout) layout.getComponent(2);
        Button button = (Button) horizontalLayout.getComponent(1);
        Collection<?> listeners = button.getListeners(ClickEvent.class);
        ClickListener clickListener = (ClickListener) listeners.iterator().next();
        ClickEvent clickEvent = createMock(ClickEvent.class);
        filterController.onRightsholderFilterClick();
        expectLastCall().once();
        replay(clickEvent, filterController);
        clickListener.buttonClick(clickEvent);
        verify(clickEvent, filterController);
    }

    private void verifyFiltersLayout(Component layout) {
        assertTrue(layout instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) layout;
        assertEquals(6, verticalLayout.getComponentCount());
        verifyFiltersLabel(verticalLayout.getComponent(0));
        verifyItemsFilterLayout(verticalLayout.getComponent(1), "Batches");
        verifyItemsFilterLayout(verticalLayout.getComponent(2), "RROs");
        verifyComboboxComponent(verticalLayout.getComponent(3), "Status",
            EnumSet.of(UsageStatusEnum.ELIGIBLE, UsageStatusEnum.INELIGIBLE));
        verifyDateWidget(verticalLayout.getComponent(4));
        verifyComboboxComponent(verticalLayout.getComponent(5), "Tax Country", Sets.newHashSet("Non-U.S.", "U.S."));
    }

    private void verifyFiltersLabel(Component component) {
        assertTrue(component instanceof Label);
        Label label = (Label) component;
        assertEquals("Filters", label.getValue());
        assertEquals(Cornerstone.LABEL_H2, label.getStyleName());
    }

    private void verifyItemsFilterLayout(Component component, String buttonCaption) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertTrue(layout.isSpacing());
        Iterator<Component> iterator = layout.iterator();
        assertEquals("(0)", ((Label) iterator.next()).getValue());
        Button button = (Button) iterator.next();
        assertEquals(buttonCaption, button.getCaption());
        assertEquals(2, button.getListeners(Button.ClickEvent.class).size());
        assertTrue(button.isDisableOnClick());
        String buttonStyleName = button.getStyleName();
        assertTrue(StringUtils.contains(buttonStyleName, buttonCaption));
        assertTrue(StringUtils.contains(buttonStyleName, BaseTheme.BUTTON_LINK));
        assertFalse(iterator.hasNext());
    }

    private void verifyComboboxComponent(Component component, String caption, Set<?> values) {
        assertTrue(component instanceof ComboBox);
        ComboBox comboBox = (ComboBox) component;
        assertEquals(caption, comboBox.getCaption());
        assertEquals(100, comboBox.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, comboBox.getWidthUnits());
        assertTrue(CollectionUtils.isEqualCollection(values, comboBox.getItemIds()));
    }

    private void verifyDateWidget(Component component) {
        assertTrue(component instanceof LocalDateWidget);
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

    private Button getApplyButton() {
        return Whitebox.getInternalState(widget, "applyButton", UsagesFilterWidget.class);
    }

    private void verifyCountLabelValue(String value, String fieldName) {
        Label label = Whitebox.getInternalState(widget, fieldName, UsagesFilterWidget.class);
        assertEquals(value, label.getValue());
    }
}
