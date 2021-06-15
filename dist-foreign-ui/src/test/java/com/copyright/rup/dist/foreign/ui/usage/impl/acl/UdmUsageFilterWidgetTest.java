package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

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
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.UdmUsageOriginEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageFilterController;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;

import com.google.common.collect.ImmutableSet;
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
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

/**
 * Verifies {@link UdmUsageFilterWidget}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 05/04/2021
 *
 * @author Dzmitry Basiachenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class, ForeignSecurityUtils.class, UdmUsageFilterWidget.class, UdmUsageFilterWidgetTest.class})
public class UdmUsageFilterWidgetTest {

    private static final Set<UsageStatusEnum> ACL_STATUSES = ImmutableSet.of(UsageStatusEnum.NEW,
        UsageStatusEnum.INELIGIBLE, UsageStatusEnum.WORK_FOUND, UsageStatusEnum.WORK_NOT_FOUND,
        UsageStatusEnum.RH_FOUND, UsageStatusEnum.RH_NOT_FOUND);

    private IUdmUsageFilterController udmUsageFilterController;
    private UdmUsageFilterWidget widget;

    @Before
    public void setUp() {
        mockStatic(ForeignSecurityUtils.class);
        expect(ForeignSecurityUtils.hasResearcherPermission()).andReturn(true).once();
        udmUsageFilterController = createMock(IUdmUsageFilterController.class);
        widget = new UdmUsageFilterWidget(udmUsageFilterController);
        widget.setController(udmUsageFilterController);
    }

    @Test
    public void testInit() {
        expect(udmUsageFilterController.getPeriods()).andReturn(Collections.singletonList(202012)).once();
        replay(udmUsageFilterController, ForeignSecurityUtils.class);
        assertSame(widget, widget.init());
        assertEquals(2, widget.getComponentCount());
        assertEquals(new MarginInfo(true), widget.getMargin());
        verifyFiltersLayout(widget.getComponent(0));
        verifyButtonsLayout(widget.getComponent(1));
        verify(udmUsageFilterController, ForeignSecurityUtils.class);
    }

    @Test
    public void testApplyFilter() {
        expect(udmUsageFilterController.getPeriods()).andReturn(Collections.singletonList(202012)).times(2);
        replay(udmUsageFilterController, ForeignSecurityUtils.class);
        widget.init();
        widget.clearFilter();
        verify(udmUsageFilterController, ForeignSecurityUtils.class);
        Button applyButton = getApplyButton();
        assertFalse(applyButton.isEnabled());
        assertTrue(widget.getAppliedFilter().isEmpty());
        assertTrue(widget.getFilter().isEmpty());
        widget.getFilter().setUsageStatus(UsageStatusEnum.NEW);
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
        expect(udmUsageFilterController.getPeriods()).andReturn(Collections.singletonList(202012)).once();
        replay(udmUsageFilterController, ForeignSecurityUtils.class);
        widget.init();
        Button applyButton = getApplyButton();
        assertFalse(applyButton.isEnabled());
        widget.applyFilter();
        verify(udmUsageFilterController, ForeignSecurityUtils.class);
        assertFalse(applyButton.isEnabled());
    }

    @Test
    public void testClearFilter() {
        expect(udmUsageFilterController.getPeriods()).andReturn(Collections.singletonList(202012)).times(2);
        replay(udmUsageFilterController, ForeignSecurityUtils.class);
        widget.init();
        Button applyButton = getApplyButton();
        assertTrue(widget.getFilter().isEmpty());
        assertTrue(widget.getAppliedFilter().isEmpty());
        widget.getFilter().setUsageStatus(UsageStatusEnum.NEW);
        applyButton.setEnabled(true);
        applyButton.click();
        assertFalse(widget.getFilter().isEmpty());
        assertFalse(widget.getAppliedFilter().isEmpty());
        widget.clearFilter();
        assertTrue(widget.getFilter().isEmpty());
        assertTrue(widget.getAppliedFilter().isEmpty());
        assertFalse(applyButton.isEnabled());
        ComboBox periodComboBox = Whitebox.getInternalState(widget, "periodComboBox");
        assertNull(periodComboBox.getValue());
        verify(udmUsageFilterController, ForeignSecurityUtils.class);
    }

    @Test
    public void verifyMoreFiltersButtonClickListener() {
        expect(udmUsageFilterController.getPeriods()).andReturn(Collections.singletonList(202012)).once();
        mockStatic(Windows.class);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        Windows.showModalWindow(anyObject(UdmFiltersWindow.class));
        expectLastCall().once();
        replay(clickEvent, Windows.class, udmUsageFilterController, ForeignSecurityUtils.class);
        widget.init();
        ClickListener clickListener = (ClickListener) ((Button) Whitebox.getInternalState(widget, "moreFiltersButton"))
            .getListeners(ClickEvent.class).iterator().next();
        clickListener.buttonClick(clickEvent);
        verify(clickEvent, Windows.class, udmUsageFilterController, ForeignSecurityUtils.class);
    }

    @Test
    @Ignore
    //TODO reimplement this test (example - FasNtsUsageFilterWidgetTest#testApplyFilter)
    public void verifyApplyButtonClickListener() {
        expect(udmUsageFilterController.getPeriods()).andReturn(Collections.singletonList(202012)).once();
        ClickEvent clickEvent = createMock(ClickEvent.class);
        replay(clickEvent, udmUsageFilterController, ForeignSecurityUtils.class);
        widget.init();
        ClickListener clickListener = (ClickListener) getApplyButton().getListeners(ClickEvent.class).iterator().next();
        clickListener.buttonClick(clickEvent);
        verify(clickEvent, udmUsageFilterController, ForeignSecurityUtils.class);
    }

    @Test
    public void verifyButtonClickListener() {
        expect(udmUsageFilterController.getPeriods()).andReturn(Collections.singletonList(202012)).times(2);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        replay(clickEvent, udmUsageFilterController, ForeignSecurityUtils.class);
        widget.init();
        Button applyButton = getApplyButton();
        widget.getFilter().setUsageStatus(UsageStatusEnum.NEW);
        applyButton.setEnabled(true);
        assertFalse(widget.getFilter().isEmpty());
        Button clearButton = (Button) ((HorizontalLayout) widget.getComponent(1)).getComponent(1);
        ClickListener clickListener = (ClickListener) clearButton.getListeners(ClickEvent.class).iterator().next();
        clickListener.buttonClick(clickEvent);
        assertFalse(applyButton.isEnabled());
        assertTrue(widget.getFilter().isEmpty());
        verify(clickEvent, udmUsageFilterController, ForeignSecurityUtils.class);
    }

    private void verifyFiltersLayout(Component layout) {
        assertTrue(layout instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) layout;
        assertEquals(6, verticalLayout.getComponentCount());
        verifyFiltersLabel(verticalLayout.getComponent(0));
        verifyItemsFilterLayout(verticalLayout.getComponent(1), "Batches");
        verifyPeriodComboBox(verticalLayout.getComponent(2));
        verifyUsageStatusComboBox(verticalLayout.getComponent(3));
        verifyUsageOriginComboBox(verticalLayout.getComponent(4));
        verifyMoreFiltersButton(verticalLayout.getComponent(5));
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
        assertEquals(2, button.getListeners(ClickEvent.class).size());
        assertTrue(button.isDisableOnClick());
        assertTrue(StringUtils.contains(button.getStyleName(), Cornerstone.BUTTON_LINK));
        assertFalse(iterator.hasNext());
    }

    private void verifyPeriodComboBox(Component component) {
        assertTrue(component instanceof ComboBox);
        ComboBox comboBox = (ComboBox) component;
        assertEquals("Period", comboBox.getCaption());
        assertEquals(100, comboBox.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, comboBox.getWidthUnits());
        ListDataProvider<Integer> listDataProvider = (ListDataProvider<Integer>) comboBox.getDataProvider();
        Collection<?> actualPeriods = listDataProvider.getItems();
        assertEquals(1, actualPeriods.size());
        assertEquals(Collections.singletonList(202012), actualPeriods);
    }

    private void verifyUsageStatusComboBox(Component component) {
        assertTrue(component instanceof ComboBox);
        ComboBox comboBox = (ComboBox) component;
        assertEquals("Status", comboBox.getCaption());
        assertEquals(100, comboBox.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, comboBox.getWidthUnits());
        ListDataProvider<UsageStatusEnum> listDataProvider =
            (ListDataProvider<UsageStatusEnum>) comboBox.getDataProvider();
        Collection<?> actualStatuses = listDataProvider.getItems();
        assertEquals(6, actualStatuses.size());
        assertEquals(ACL_STATUSES, actualStatuses);
    }

    private void verifyMoreFiltersButton(Component component) {
        assertTrue(component instanceof Button);
        Button button = (Button) component;
        assertEquals("More Filters", component.getCaption());
        assertTrue(StringUtils.contains(button.getStyleName(), Cornerstone.BUTTON_LINK));
        Collection<?> listeners = button.getListeners(ClickEvent.class);
        assertTrue(CollectionUtils.isNotEmpty(listeners));
        assertEquals(1, listeners.size());
    }

    private void verifyUsageOriginComboBox(Component component) {
        assertTrue(component instanceof ComboBox);
        ComboBox comboBox = (ComboBox) component;
        assertEquals("Usage Origin", comboBox.getCaption());
        assertEquals(100, comboBox.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, comboBox.getWidthUnits());
        ListDataProvider<Integer> listDataProvider = (ListDataProvider<Integer>) comboBox.getDataProvider();
        Collection<?> actualUsageOrigins = listDataProvider.getItems();
        assertEquals(2, actualUsageOrigins.size());
        assertEquals(Arrays.asList(UdmUsageOriginEnum.values()), actualUsageOrigins);
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
        return Whitebox.getInternalState(widget, "applyButton");
    }
}
