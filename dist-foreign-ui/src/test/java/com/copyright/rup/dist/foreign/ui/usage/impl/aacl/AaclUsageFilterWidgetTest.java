package com.copyright.rup.dist.foreign.ui.usage.impl.aacl;

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

import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.ui.usage.api.aacl.IAaclUsageFilterController;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
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

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

/**
 * Verifies {@link AaclUsageFilterWidget}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/23/2019
 *
 * @author Aliaksandr Liakhi
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Windows.class)
public class AaclUsageFilterWidgetTest {

    private static final Long ACCOUNT_NUMBER = 12345678L;
    private static final String AACL_PRODUCT_FAMILY = "AACL";

    private static final Set<UsageStatusEnum> AACL_STATUSES = ImmutableSet.of(UsageStatusEnum.NEW,
        UsageStatusEnum.WORK_FOUND, UsageStatusEnum.RH_FOUND, UsageStatusEnum.WORK_NOT_FOUND,
        UsageStatusEnum.WORK_RESEARCH, UsageStatusEnum.ELIGIBLE);

    private IAaclUsageFilterController usagesFilterController;
    private AaclUsageFilterWidget widget;

    @Before
    public void setUp() {
        usagesFilterController = createMock(IAaclUsageFilterController.class);
        widget = new AaclUsageFilterWidget(usagesFilterController);
        widget.setController(usagesFilterController);
    }

    @Test
    public void testInit() {
        expect(usagesFilterController.getAaclUsagePeriods()).andReturn(Collections.singletonList(2020)).once();
        expect(usagesFilterController.getSelectedProductFamily()).andReturn(AACL_PRODUCT_FAMILY).once();
        replay(usagesFilterController);
        assertSame(widget, widget.init());
        assertEquals(2, widget.getComponentCount());
        assertEquals(new MarginInfo(true), widget.getMargin());
        verifyFiltersLayout(widget.getComponent(0));
        verifyButtonsLayout(widget.getComponent(1));
        verify(usagesFilterController);
    }

    @Test
    public void testApplyFilter() {
        expect(usagesFilterController.getAaclUsagePeriods()).andReturn(Collections.singletonList(2020)).times(2);
        expect(usagesFilterController.getSelectedProductFamily()).andReturn(AACL_PRODUCT_FAMILY).times(2);
        replay(usagesFilterController);
        widget.init();
        widget.clearFilter();
        verify(usagesFilterController);
        Button applyButton = getApplyButton();
        assertFalse(applyButton.isEnabled());
        assertTrue(widget.getAppliedFilter().getRhAccountNumbers().isEmpty());
        assertTrue(widget.getFilter().getRhAccountNumbers().isEmpty());
        Set<Long> accountNumbers = Sets.newHashSet(ACCOUNT_NUMBER);
        widget.getFilter().setRhAccountNumbers(accountNumbers);
        assertNotEquals(widget.getFilter(), widget.getAppliedFilter());
        applyButton.setEnabled(true);
        assertTrue(widget.getAppliedFilter().getRhAccountNumbers().isEmpty());
        assertFalse(widget.getFilter().getRhAccountNumbers().isEmpty());
        assertTrue(applyButton.isEnabled());
        applyButton.click();
        assertFalse(applyButton.isEnabled());
        assertFalse(widget.getAppliedFilter().getRhAccountNumbers().isEmpty());
    }

    @Test
    public void testFilterChangedEmptyFilter() {
        expect(usagesFilterController.getAaclUsagePeriods()).andReturn(Collections.singletonList(2020)).once();
        expect(usagesFilterController.getSelectedProductFamily()).andReturn(AACL_PRODUCT_FAMILY).once();
        replay(usagesFilterController);
        widget.init();
        Button applyButton = getApplyButton();
        assertFalse(applyButton.isEnabled());
        widget.applyFilter();
        verify(usagesFilterController);
        assertFalse(applyButton.isEnabled());
    }

    @Test
    public void testClearFilter() {
        expect(usagesFilterController.getAaclUsagePeriods()).andReturn(Collections.singletonList(2020)).times(2);
        expect(usagesFilterController.getSelectedProductFamily()).andReturn(AACL_PRODUCT_FAMILY).times(2);
        replay(usagesFilterController);
        widget.init();
        Button applyButton = getApplyButton();
        assertTrue(widget.getFilter().getRhAccountNumbers().isEmpty());
        assertTrue(widget.getAppliedFilter().getRhAccountNumbers().isEmpty());
        assertEquals(AACL_PRODUCT_FAMILY, widget.getFilter().getProductFamily());
        assertNull(widget.getAppliedFilter().getProductFamily());
        widget.getFilter().setRhAccountNumbers(Sets.newHashSet(ACCOUNT_NUMBER));
        widget.getFilter().setProductFamily(AACL_PRODUCT_FAMILY);
        applyButton.setEnabled(true);
        applyButton.click();
        assertFalse(widget.getFilter().getRhAccountNumbers().isEmpty());
        assertFalse(widget.getAppliedFilter().getRhAccountNumbers().isEmpty());
        assertEquals(AACL_PRODUCT_FAMILY, widget.getFilter().getProductFamily());
        assertEquals(AACL_PRODUCT_FAMILY, widget.getAppliedFilter().getProductFamily());
        widget.clearFilter();
        assertTrue(widget.getFilter().getRhAccountNumbers().isEmpty());
        assertTrue(widget.getAppliedFilter().getRhAccountNumbers().isEmpty());
        assertEquals(AACL_PRODUCT_FAMILY, widget.getFilter().getProductFamily());
        assertEquals(AACL_PRODUCT_FAMILY, widget.getAppliedFilter().getProductFamily());
        assertFalse(applyButton.isEnabled());
        ComboBox usagePeriodComboBox = Whitebox.getInternalState(widget, "usagePeriodComboBox");
        assertNull(usagePeriodComboBox.getValue());
        verify(usagesFilterController);
    }

    @Test
    public void verifyApplyButtonClickListener() {
        expect(usagesFilterController.getAaclUsagePeriods()).andReturn(Collections.singletonList(2020)).once();
        expect(usagesFilterController.getSelectedProductFamily()).andReturn(AACL_PRODUCT_FAMILY).once();
        mockStatic(Windows.class);
        ClickEvent clickEvent = createMock(ClickEvent.class);
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
        expect(usagesFilterController.getAaclUsagePeriods()).andReturn(Collections.singletonList(2020)).times(2);
        expect(usagesFilterController.getSelectedProductFamily()).andReturn(AACL_PRODUCT_FAMILY).times(2);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        replay(clickEvent, usagesFilterController);
        widget.init();
        Set<Long> accountNumbers = Sets.newHashSet(ACCOUNT_NUMBER);
        Button applyButton = getApplyButton();
        widget.getFilter().setRhAccountNumbers(accountNumbers);
        applyButton.setEnabled(true);
        assertFalse(widget.getFilter().isEmpty());
        Button clearButton = (Button) ((HorizontalLayout) widget.getComponent(1)).getComponent(1);
        ClickListener clickListener = (ClickListener) clearButton.getListeners(ClickEvent.class).iterator().next();
        clickListener.buttonClick(clickEvent);
        assertFalse(applyButton.isEnabled());
        assertTrue(widget.getFilter().isEmpty());
        verify(clickEvent, usagesFilterController);
    }

    private void verifyFiltersLayout(Component layout) {
        assertTrue(layout instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) layout;
        assertEquals(4, verticalLayout.getComponentCount());
        verifyFiltersLabel(verticalLayout.getComponent(0));
        verifyItemsFilterLayout(verticalLayout.getComponent(1), "Batches");
        verifyUsageStatusCombobox(verticalLayout.getComponent(2));
        verifyUsagePeriodCombobox(verticalLayout.getComponent(3));
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

    private void verifyUsageStatusCombobox(Component component) {
        assertTrue(component instanceof ComboBox);
        ComboBox comboBox = (ComboBox) component;
        assertEquals("Status", comboBox.getCaption());
        assertEquals(100, comboBox.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, comboBox.getWidthUnits());
        ListDataProvider<UsageStatusEnum> listDataProvider =
            (ListDataProvider<UsageStatusEnum>) comboBox.getDataProvider();
        Collection<?> actualStatuses = listDataProvider.getItems();
        assertEquals(6, actualStatuses.size());
        assertEquals(AACL_STATUSES, actualStatuses);
    }

    private void verifyUsagePeriodCombobox(Component component) {
        assertTrue(component instanceof ComboBox);
        ComboBox comboBox = (ComboBox) component;
        assertEquals("Usage Period", comboBox.getCaption());
        assertEquals(100, comboBox.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, comboBox.getWidthUnits());
        ListDataProvider<Integer> listDataProvider = (ListDataProvider<Integer>) comboBox.getDataProvider();
        Collection<?> actualUsagePeriods = listDataProvider.getItems();
        assertEquals(1, actualUsagePeriods.size());
        assertEquals(Collections.singletonList(2020), actualUsagePeriods);
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
