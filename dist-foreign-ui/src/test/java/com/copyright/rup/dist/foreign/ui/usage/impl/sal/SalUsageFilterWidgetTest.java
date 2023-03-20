package com.copyright.rup.dist.foreign.ui.usage.impl.sal;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyComboBox;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyFiltersLabel;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyItemsFilterWidget;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
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

import com.copyright.rup.dist.foreign.domain.SalDetailTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.ui.usage.api.sal.ISalUsageFilterController;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.google.common.collect.ImmutableSet;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Verifies {@link SalUsageFilterWidget}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 07/28/2020
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Windows.class)
public class SalUsageFilterWidgetTest {

    private static final Long ACCOUNT_NUMBER = 12345678L;
    private static final String SAL_PRODUCT_FAMILY = "SAL";
    private static final Set<UsageStatusEnum> SAL_STATUSES =
        ImmutableSet.of(UsageStatusEnum.NEW, UsageStatusEnum.WORK_FOUND, UsageStatusEnum.WORK_NOT_FOUND,
            UsageStatusEnum.RH_FOUND, UsageStatusEnum.WORK_NOT_GRANTED, UsageStatusEnum.RH_NOT_FOUND,
            UsageStatusEnum.ELIGIBLE);

    private ISalUsageFilterController usagesFilterController;
    private SalUsageFilterWidget widget;

    @Before
    public void setUp() {
        usagesFilterController = createMock(ISalUsageFilterController.class);
        widget = new SalUsageFilterWidget(usagesFilterController);
        widget.setController(usagesFilterController);
    }

    @Test
    public void testInit() {
        expect(usagesFilterController.getSelectedProductFamily()).andReturn(SAL_PRODUCT_FAMILY).once();
        replay(usagesFilterController);
        assertSame(widget, widget.init());
        assertEquals(4, widget.getComponentCount());
        assertEquals(new MarginInfo(true), widget.getMargin());
        verifyFiltersLayout(widget.getComponent(0));
        verifyButtonsLayout(widget.getComponent(1));
        verify(usagesFilterController);
    }

    @Test
    public void testApplyFilter() {
        expect(usagesFilterController.getSelectedProductFamily()).andReturn(SAL_PRODUCT_FAMILY).times(2);
        expect(usagesFilterController.getRightsholdersByAccountNumbers(Set.of(ACCOUNT_NUMBER)))
            .andReturn(List.of()).once();
        replay(usagesFilterController);
        widget.init();
        widget.clearFilter();
        Button applyButton = getApplyButton();
        assertFalse(applyButton.isEnabled());
        assertTrue(widget.getAppliedFilter().getRhAccountNumbers().isEmpty());
        assertTrue(widget.getFilter().getRhAccountNumbers().isEmpty());
        Set<Long> accountNumbers = Set.of(ACCOUNT_NUMBER);
        widget.getFilter().setRhAccountNumbers(accountNumbers);
        assertNotEquals(widget.getFilter(), widget.getAppliedFilter());
        applyButton.setEnabled(true);
        assertTrue(widget.getAppliedFilter().getRhAccountNumbers().isEmpty());
        assertFalse(widget.getFilter().getRhAccountNumbers().isEmpty());
        assertTrue(applyButton.isEnabled());
        applyButton.click();
        assertFalse(applyButton.isEnabled());
        assertFalse(widget.getAppliedFilter().getRhAccountNumbers().isEmpty());
        verify(usagesFilterController);
    }

    @Test
    public void testFilterChangedEmptyFilter() {
        expect(usagesFilterController.getSelectedProductFamily()).andReturn(SAL_PRODUCT_FAMILY).once();
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
        expect(usagesFilterController.getSelectedProductFamily()).andReturn(SAL_PRODUCT_FAMILY).times(2);
        replay(usagesFilterController);
        widget.init();
        Button applyButton = getApplyButton();
        assertTrue(widget.getFilter().isEmpty());
        assertTrue(widget.getAppliedFilter().isEmpty());
        assertEquals(SAL_PRODUCT_FAMILY, widget.getFilter().getProductFamily());
        assertNull(widget.getAppliedFilter().getProductFamily());
        widget.getFilter().setSalDetailType(SalDetailTypeEnum.IB);
        widget.getFilter().setProductFamily(SAL_PRODUCT_FAMILY);
        applyButton.setEnabled(true);
        applyButton.click();
        assertFalse(widget.getFilter().isEmpty());
        assertFalse(widget.getAppliedFilter().isEmpty());
        assertEquals(SAL_PRODUCT_FAMILY, widget.getFilter().getProductFamily());
        assertEquals(SAL_PRODUCT_FAMILY, widget.getAppliedFilter().getProductFamily());
        widget.clearFilter();
        assertTrue(widget.getFilter().isEmpty());
        assertTrue(widget.getAppliedFilter().isEmpty());
        assertEquals(SAL_PRODUCT_FAMILY, widget.getFilter().getProductFamily());
        assertEquals(SAL_PRODUCT_FAMILY, widget.getAppliedFilter().getProductFamily());
        assertFalse(applyButton.isEnabled());
        ComboBox detailTypeComboBox = Whitebox.getInternalState(widget, "detailTypeComboBox");
        assertNull(detailTypeComboBox.getValue());
        verify(usagesFilterController);
    }

    @Test
    public void verifyApplyButtonClickListener() {
        expect(usagesFilterController.getSelectedProductFamily()).andReturn(SAL_PRODUCT_FAMILY).once();
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
        expect(usagesFilterController.getSelectedProductFamily()).andReturn(SAL_PRODUCT_FAMILY).times(2);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        replay(clickEvent, usagesFilterController);
        widget.init();
        Button applyButton = getApplyButton();
        widget.getFilter().setSalDetailType(SalDetailTypeEnum.IB);
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
        assertThat(layout, instanceOf(VerticalLayout.class));
        VerticalLayout verticalLayout = (VerticalLayout) layout;
        assertEquals(5, verticalLayout.getComponentCount());
        verifyFiltersLabel(verticalLayout.getComponent(0));
        verifyItemsFilterWidget(verticalLayout.getComponent(1), "Batches");
        verifyItemsFilterWidget(verticalLayout.getComponent(2), "Rightsholders");
        verifyComboBox(verticalLayout.getComponent(3), "Status", true, SAL_STATUSES);
        verifyComboBox(verticalLayout.getComponent(4), "Detail Type", true, SalDetailTypeEnum.values());
    }

    private void verifyButtonsLayout(Component component) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        assertTrue(layout.isSpacing());
        verifyButton(layout.getComponent(0), "Apply");
        verifyButton(layout.getComponent(1), "Clear");
    }

    private void verifyButton(Component component, String caption) {
        assertThat(component, instanceOf(Button.class));
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
