package com.copyright.rup.dist.foreign.vui.usage.impl.aacl;

import static com.copyright.rup.dist.foreign.vui.IVaadinComponentFinder.getButton;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyComboBox;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyFiltersLabel;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyItemsFilterWidget;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.vui.usage.api.aacl.IAaclUsageFilterController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.List;
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
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
public class AaclUsageFilterWidgetTest {

    private static final int USAGE_PERIOD = 2020;
    private static final Long ACCOUNT_NUMBER = 12345678L;
    private static final String AACL_PRODUCT_FAMILY = "AACL";
    private static final Set<UsageStatusEnum> AACL_STATUSES = Set.of(UsageStatusEnum.NEW,
        UsageStatusEnum.WORK_FOUND, UsageStatusEnum.RH_FOUND, UsageStatusEnum.WORK_NOT_FOUND,
        UsageStatusEnum.WORK_RESEARCH, UsageStatusEnum.ELIGIBLE, UsageStatusEnum.SCENARIO_EXCLUDED);

    private AaclUsageFilterWidget widget;
    private IAaclUsageFilterController controller;

    @Before
    public void setUp() {
        controller = createMock(IAaclUsageFilterController.class);
        widget = new AaclUsageFilterWidget(controller);
        widget.setController(controller);
    }

    @Test
    public void testInit() {
        expect(controller.getUsagePeriods()).andReturn(List.of(USAGE_PERIOD)).once();
        expect(controller.getSelectedProductFamily()).andReturn(AACL_PRODUCT_FAMILY).once();
        replay(controller);
        assertSame(widget, widget.init());
        assertEquals(4, widget.getComponentCount());
        verifyFiltersLayout(widget.getComponentAt(0));
        verifyButtonsLayout(widget.getComponentAt(1), true, "Apply", "Clear");
        verify(controller);
    }

    @Test
    public void testApplyFilter() {
        expect(controller.getUsagePeriods()).andReturn(List.of(USAGE_PERIOD)).times(2);
        expect(controller.getSelectedProductFamily()).andReturn(AACL_PRODUCT_FAMILY).times(2);
        replay(controller);
        widget.init();
        widget.clearFilter();
        var applyButton = getApplyButton();
        assertFalse(applyButton.isEnabled());
        assertTrue(widget.getAppliedFilter().getRhAccountNumbers().isEmpty());
        assertTrue(widget.getFilter().getRhAccountNumbers().isEmpty());
        var accountNumbers = Set.of(ACCOUNT_NUMBER);
        widget.getFilter().setRhAccountNumbers(accountNumbers);
        assertNotEquals(widget.getFilter(), widget.getAppliedFilter());
        applyButton.setEnabled(true);
        assertTrue(widget.getAppliedFilter().getRhAccountNumbers().isEmpty());
        assertFalse(widget.getFilter().getRhAccountNumbers().isEmpty());
        assertTrue(applyButton.isEnabled());
        applyButton.click();
        assertFalse(applyButton.isEnabled());
        assertFalse(widget.getAppliedFilter().getRhAccountNumbers().isEmpty());
        verify(controller);
    }

    @Test
    public void testFilterChangedEmptyFilter() {
        expect(controller.getUsagePeriods()).andReturn(List.of(USAGE_PERIOD)).once();
        expect(controller.getSelectedProductFamily()).andReturn(AACL_PRODUCT_FAMILY).once();
        replay(controller);
        widget.init();
        var applyButton = getApplyButton();
        assertFalse(applyButton.isEnabled());
        widget.applyFilter();
        verify(controller);
        assertFalse(applyButton.isEnabled());
    }

    @Test
    public void testClearFilter() {
        expect(controller.getUsagePeriods()).andReturn(List.of(USAGE_PERIOD)).times(2);
        expect(controller.getSelectedProductFamily()).andReturn(AACL_PRODUCT_FAMILY).times(2);
        replay(controller);
        widget.init();
        var applyButton = getApplyButton();
        assertTrue(widget.getFilter().getRhAccountNumbers().isEmpty());
        assertTrue(widget.getAppliedFilter().getRhAccountNumbers().isEmpty());
        assertEquals(AACL_PRODUCT_FAMILY, widget.getFilter().getProductFamily());
        assertNull(widget.getAppliedFilter().getProductFamily());
        widget.getFilter().setRhAccountNumbers(Set.of(ACCOUNT_NUMBER));
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
        verify(controller);
    }

    @Test
    public void verifyApplyButtonClickListener() {
        expect(controller.getUsagePeriods()).andReturn(List.of(USAGE_PERIOD)).once();
        expect(controller.getSelectedProductFamily()).andReturn(AACL_PRODUCT_FAMILY).once();
        mockStatic(Windows.class);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        Windows.showNotificationWindow("Apply filter clicked");
        expectLastCall().once();
        replay(clickEvent, Windows.class, controller);
        widget.init();
        var applyButton = getApplyButton();
        applyButton.click();
        verify(clickEvent, controller);
    }

    @Test
    public void verifyButtonClickListener() {
        expect(controller.getUsagePeriods()).andReturn(List.of(USAGE_PERIOD)).times(2);
        expect(controller.getSelectedProductFamily()).andReturn(AACL_PRODUCT_FAMILY).times(2);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        replay(clickEvent, controller);
        widget.init();
        var accountNumbers = Set.of(ACCOUNT_NUMBER);
        var applyButton = getApplyButton();
        widget.getFilter().setRhAccountNumbers(accountNumbers);
        applyButton.setEnabled(true);
        assertFalse(widget.getFilter().isEmpty());
        var clearButton = getButton(widget, "Clear");
        clearButton.click();
        assertFalse(applyButton.isEnabled());
        assertTrue(widget.getFilter().isEmpty());
        verify(clickEvent, controller);
    }

    private void verifyFiltersLayout(Component component) {
        assertThat(component, instanceOf(VerticalLayout.class));
        var filterLayout = (VerticalLayout) component;
        assertEquals(4, filterLayout.getComponentCount());
        verifyFiltersLabel(filterLayout.getComponentAt(0));
        verifyItemsFilterWidget(filterLayout.getComponentAt(1), "Batches");
        verifyComboBox(filterLayout.getComponentAt(2), "Status", true, AACL_STATUSES);
        verifyComboBox(filterLayout.getComponentAt(3), "Usage Period", true, USAGE_PERIOD);
    }

    private Button getApplyButton() {
        return getButton(widget, "Apply");
    }
}
