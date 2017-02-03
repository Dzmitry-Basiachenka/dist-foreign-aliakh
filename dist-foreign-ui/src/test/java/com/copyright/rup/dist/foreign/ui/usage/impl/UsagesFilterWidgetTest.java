package com.copyright.rup.dist.foreign.ui.usage.impl;

import static org.easymock.EasyMock.expect;
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

    private static final Integer FISCAL_YEAR = 2017;
    private IUsagesFilterController usagesFilterController;
    private UsagesFilterWidget widget;

    @Before
    public void setUp() {
        usagesFilterController = createMock(IUsagesFilterController.class);
        widget = new UsagesFilterWidget();
        widget.setController(usagesFilterController);
    }

    @Test
    public void testInit() {
        expect(usagesFilterController.getFiscalYears()).andReturn(Collections.singletonList(FISCAL_YEAR)).once();
        replay(usagesFilterController);
        assertSame(widget, widget.init());
        assertEquals(2, widget.getComponentCount());
        assertEquals(new MarginInfo(true), widget.getMargin());
        verifyFiltersLayout(widget.getComponent(0));
        verifyButtonsLayout(widget.getComponent(1));
        verify(usagesFilterController);
    }

    @Test
    public void testSetSelectedUsageBatches() {
        expect(usagesFilterController.getFiscalYears()).andReturn(Collections.singletonList(FISCAL_YEAR)).once();
        replay(usagesFilterController);
        widget.init();
        Button applyButton = Whitebox.getInternalState(widget, "applyButton", UsagesFilterWidget.class);
        Set<String> usageBatchesIds = Sets.newHashSet(RupPersistUtils.generateUuid());
        assertFalse(applyButton.isEnabled());
        widget.setSelectedUsageBatches(usageBatchesIds);
        verify(usagesFilterController);
        assertEquals(usageBatchesIds, widget.getFilter().getUsageBatchesIds());
        assertTrue(applyButton.isEnabled());
        verifyCountLabelValue("(1)", "batchesCountLabel");
    }

    @Test
    public void testSetSelectedRightsholders() {
        expect(usagesFilterController.getFiscalYears()).andReturn(Collections.singletonList(FISCAL_YEAR)).once();
        replay(usagesFilterController);
        widget.init();
        Button applyButton = getApplyButton();
        Set<Long> accountNumbers = Sets.newHashSet(10000L);
        assertFalse(applyButton.isEnabled());
        widget.setSelectedRightsholders(accountNumbers);
        verify(usagesFilterController);
        assertEquals(accountNumbers, widget.getFilter().getRhAccountNumbers());
        assertTrue(applyButton.isEnabled());
        verifyCountLabelValue("(1)", "rightsholdersCountLabel");
    }

    @Test
    @PrepareForTest(Windows.class)
    public void testApplyFilter() {
        mockStatic(Windows.class);
        expect(usagesFilterController.getFiscalYears()).andReturn(Collections.singletonList(FISCAL_YEAR)).once();
        Windows.showNotificationWindow("Apply filter clicked");
        expectLastCall().once();
        replay(Windows.class, usagesFilterController);
        widget.init();
        Button applyButton = getApplyButton();
        assertFalse(applyButton.isEnabled());
        widget.setSelectedRightsholders(Sets.newHashSet(10000L));
        assertTrue(applyButton.isEnabled());
        widget.applyFilter();
        verify(Windows.class, usagesFilterController);
        assertFalse(applyButton.isEnabled());
    }

    @Test
    @PrepareForTest(Windows.class)
    public void testFilterChangedEmptyFilter() {
        mockStatic(Windows.class);
        expect(usagesFilterController.getFiscalYears()).andReturn(Collections.singletonList(FISCAL_YEAR)).once();
        Windows.showNotificationWindow("Apply filter clicked");
        expectLastCall().once();
        replay(Windows.class, usagesFilterController);
        widget.init();
        Button applyButton = getApplyButton();
        assertFalse(applyButton.isEnabled());
        widget.setSelectedRightsholders(Collections.emptySet());
        widget.applyFilter();
        verify(Windows.class, usagesFilterController);
        assertFalse(applyButton.isEnabled());
    }

    @Test
    public void testGetController() {
        expect(usagesFilterController.getFiscalYears()).andReturn(Collections.singletonList(FISCAL_YEAR)).once();
        replay(usagesFilterController);
        widget.init();
        UsagesFilterController controller = new UsagesFilterController();
        widget.setController(controller);
        verify(usagesFilterController);
        assertSame(controller, widget.getController());
    }

    @Test
    public void testClearFilter() {
        expect(usagesFilterController.getFiscalYears()).andReturn(Collections.singletonList(FISCAL_YEAR)).once();
        replay(usagesFilterController);
        widget.init();
        widget.clearFilter();
        verify(usagesFilterController);
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
        ComboBox fiscalYearComboBox = Whitebox.getInternalState(widget, "fiscalYearComboBox", UsagesFilterWidget.class);
        assertNull(fiscalYearComboBox.getValue());
    }

    @Test
    @PrepareForTest(Windows.class)
    public void verifyApplyButtonClickListener() {
        mockStatic(Windows.class);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        expect(usagesFilterController.getFiscalYears()).andReturn(Collections.singletonList(FISCAL_YEAR)).once();
        Windows.showNotificationWindow("Apply filter clicked");
        expectLastCall().once();
        replay(clickEvent, Windows.class, usagesFilterController);
        widget.init();
        ClickListener clickListener = (ClickListener) getApplyButton().getListeners(ClickEvent.class).iterator().next();
        clickListener.buttonClick(clickEvent);
        verify(clickEvent, usagesFilterController);
    }

    @Test
    public void verifyButtonClickListener() {
        ClickEvent clickEvent = createMock(ClickEvent.class);
        expect(usagesFilterController.getFiscalYears()).andReturn(Collections.singletonList(FISCAL_YEAR)).once();
        replay(clickEvent, usagesFilterController);
        widget.init();
        widget.setSelectedRightsholders(Sets.newHashSet(10000L));
        Button applyButton = getApplyButton();
        assertTrue(applyButton.isEnabled());
        assertFalse(widget.getFilter().isEmpty());
        Button clearButton = (Button) ((HorizontalLayout) widget.getComponent(1)).getComponent(1);
        ClickListener clickListener = (ClickListener) clearButton.getListeners(ClickEvent.class).iterator().next();
        clickListener.buttonClick(clickEvent);
        assertFalse(applyButton.isEnabled());
        assertTrue(widget.getFilter().isEmpty());
        verify(clickEvent, usagesFilterController);
    }

    @Test
    public void testBatchesButtonClickListener() {
        ClickEvent clickEvent = createMock(ClickEvent.class);
        IUsagesFilterController filterController = createMock(IUsagesFilterController.class);
        expect(usagesFilterController.getFiscalYears()).andReturn(Collections.singletonList(FISCAL_YEAR)).once();
        filterController.onUsageBatchFilterClick();
        expectLastCall().once();
        replay(clickEvent, filterController, usagesFilterController);
        widget.init();
        widget.setController(filterController);
        VerticalLayout layout = (VerticalLayout) widget.getComponent(0);
        HorizontalLayout horizontalLayout = (HorizontalLayout) layout.getComponent(1);
        Button button = (Button) horizontalLayout.getComponent(1);
        Collection<?> listeners = button.getListeners(ClickEvent.class);
        ClickListener clickListener = (ClickListener) listeners.iterator().next();
        clickListener.buttonClick(clickEvent);
        verify(clickEvent, filterController, usagesFilterController);
    }

    @Test
    public void testRightsholdersButtonClickListener() {
        IUsagesFilterController filterController = createMock(IUsagesFilterController.class);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        expect(usagesFilterController.getFiscalYears()).andReturn(Collections.singletonList(FISCAL_YEAR)).once();
        filterController.onRightsholderFilterClick();
        expectLastCall().once();
        replay(clickEvent, filterController, usagesFilterController);
        widget.init();
        widget.setController(filterController);
        VerticalLayout layout = (VerticalLayout) widget.getComponent(0);
        HorizontalLayout horizontalLayout = (HorizontalLayout) layout.getComponent(2);
        Button button = (Button) horizontalLayout.getComponent(1);
        Collection<?> listeners = button.getListeners(ClickEvent.class);
        ClickListener clickListener = (ClickListener) listeners.iterator().next();
        clickListener.buttonClick(clickEvent);
        verify(clickEvent, filterController, usagesFilterController);
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
        verifyComboboxComponent(verticalLayout.getComponent(5), "Fiscal Year", Collections.singleton(FISCAL_YEAR));
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
