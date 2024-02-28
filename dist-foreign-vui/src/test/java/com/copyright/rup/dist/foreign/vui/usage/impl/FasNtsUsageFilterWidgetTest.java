package com.copyright.rup.dist.foreign.vui.usage.impl;

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

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.vui.usage.api.IFasNtsUsageFilterController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.LocalDateWidget;

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
 * Verifies {@link FasNtsUsageFilterWidget}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/12/2019
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Windows.class)
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
public class FasNtsUsageFilterWidgetTest {

    private static final Integer FISCAL_YEAR = 2017;
    private static final Long ACCOUNT_NUMBER = 12345678L;
    private static final String FAS_PRODUCT_FAMILY = "FAS";
    private static final List<UsageStatusEnum> FAS_FAS2_STATUSES = List.of(UsageStatusEnum.NEW,
        UsageStatusEnum.WORK_NOT_FOUND, UsageStatusEnum.WORK_RESEARCH, UsageStatusEnum.NTS_WITHDRAWN,
        UsageStatusEnum.TO_BE_DISTRIBUTED, UsageStatusEnum.WORK_FOUND, UsageStatusEnum.RH_NOT_FOUND,
        UsageStatusEnum.RH_FOUND, UsageStatusEnum.SENT_FOR_RA, UsageStatusEnum.ELIGIBLE);
    private static final List<UsageStatusEnum> NTS_STATUSES = List.of(UsageStatusEnum.WORK_FOUND,
        UsageStatusEnum.RH_FOUND, UsageStatusEnum.UNCLASSIFIED, UsageStatusEnum.ELIGIBLE,
        UsageStatusEnum.SCENARIO_EXCLUDED);

    private FasNtsUsageFilterWidget widget;
    private IFasNtsUsageFilterController controller;

    @Before
    public void setUp() {
        controller = createMock(IFasNtsUsageFilterController.class);
        widget = new FasNtsUsageFilterWidget(controller);
        widget.setController(controller);
        expect(controller.getFiscalYears()).andReturn(List.of(FISCAL_YEAR)).once();
    }

    @Test
    public void testInit() {
        expect(controller.getSelectedProductFamily()).andReturn(FAS_PRODUCT_FAMILY).times(2);
        replay(controller);
        assertSame(widget, widget.init());
        assertEquals(4, widget.getComponentCount());
        verifyFiltersLayout(widget.getComponentAt(0));
        verifyButtonsLayout(widget.getComponentAt(1), true, "Apply", "Clear");
        verify(controller);
    }

    @Test
    public void testApplyFilter() {
        expect(controller.getSelectedProductFamily()).andReturn(FAS_PRODUCT_FAMILY).times(4);
        expect(controller.getFiscalYears()).andReturn(List.of(FISCAL_YEAR)).once();
        expect(controller.getRightsholdersByAccountNumbers(Set.of(ACCOUNT_NUMBER))).andReturn(List.of()).once();
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
        expect(controller.getSelectedProductFamily()).andReturn(FAS_PRODUCT_FAMILY).times(2);
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
        expect(controller.getSelectedProductFamily()).andReturn(FAS_PRODUCT_FAMILY).times(4);
        expect(controller.getFiscalYears()).andReturn(List.of(FISCAL_YEAR)).once();
        expect(controller.getRightsholdersByAccountNumbers(Set.of(ACCOUNT_NUMBER))).andReturn(List.of()).once();
        replay(controller);
        widget.init();
        var applyButton = getApplyButton();
        assertTrue(widget.getFilter().getRhAccountNumbers().isEmpty());
        assertTrue(widget.getAppliedFilter().getRhAccountNumbers().isEmpty());
        assertEquals(FAS_PRODUCT_FAMILY, widget.getFilter().getProductFamily());
        assertNull(widget.getAppliedFilter().getProductFamily());
        widget.getFilter().setRhAccountNumbers(Set.of(ACCOUNT_NUMBER));
        widget.getFilter().setProductFamily(FAS_PRODUCT_FAMILY);
        applyButton.setEnabled(true);
        applyButton.click();
        assertFalse(widget.getFilter().getRhAccountNumbers().isEmpty());
        assertFalse(widget.getAppliedFilter().getRhAccountNumbers().isEmpty());
        assertEquals(FAS_PRODUCT_FAMILY, widget.getFilter().getProductFamily());
        assertEquals(FAS_PRODUCT_FAMILY, widget.getAppliedFilter().getProductFamily());
        widget.clearFilter();
        assertTrue(widget.getFilter().getRhAccountNumbers().isEmpty());
        assertTrue(widget.getAppliedFilter().getRhAccountNumbers().isEmpty());
        assertEquals(FAS_PRODUCT_FAMILY, widget.getFilter().getProductFamily());
        assertEquals(FAS_PRODUCT_FAMILY, widget.getAppliedFilter().getProductFamily());
        assertFalse(applyButton.isEnabled());
        LocalDateWidget localDateWidget = Whitebox.getInternalState(widget, "paymentDateWidget");
        assertNull(localDateWidget.getValue());
        ComboBox fiscalYearComboBox = Whitebox.getInternalState(widget, "fiscalYearComboBox");
        assertNull(fiscalYearComboBox.getValue());
        verify(controller);
    }

    @Test
    public void verifyApplyButtonClickListener() {
        expect(controller.getSelectedProductFamily()).andReturn(FAS_PRODUCT_FAMILY).times(2);
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
    public void verifyClearButtonClickListener() {
        expect(controller.getSelectedProductFamily()).andReturn(FAS_PRODUCT_FAMILY).times(4);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        expect(controller.getFiscalYears()).andReturn(List.of(FISCAL_YEAR)).once();
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

    @Test
    public void testGetAvailableStatusesFasProductFamily() {
        expect(controller.getSelectedProductFamily()).andReturn(FAS_PRODUCT_FAMILY).times(3);
        replay(controller);
        widget.init();
        assertEquals(FAS_FAS2_STATUSES, getGetStatuses());
    }

    @Test
    public void testGetAvailableStatusesNtsProductFamily() {
        expect(controller.getSelectedProductFamily()).andReturn("NTS").times(3);
        expect(controller.getFiscalYears()).andReturn(List.of(FISCAL_YEAR)).once();
        replay(controller);
        widget.init();
        assertEquals(NTS_STATUSES, getGetStatuses());
    }

    private void verifyFiltersLayout(Component component) {
        assertThat(component, instanceOf(VerticalLayout.class));
        var filtersLayout = (VerticalLayout) component;
        assertEquals(6, filtersLayout.getComponentCount());
        verifyFiltersLabel(filtersLayout.getComponentAt(0));
        verifyItemsFilterWidget(filtersLayout.getComponentAt(1), "Batches");
        verifyItemsFilterWidget(filtersLayout.getComponentAt(2), "RROs");
        verifyDateWidget(filtersLayout.getComponentAt(3));
        verifyComboBox(filtersLayout.getComponentAt(4), "Status", "100%", true,
            FAS_FAS2_STATUSES);
        verifyComboBox(filtersLayout.getComponentAt(5), "Fiscal Year To", "100%", true,
            List.of(FISCAL_YEAR));
    }

    private void verifyDateWidget(Component component) {
        assertThat(component, instanceOf(LocalDateWidget.class));
    }

    private Button getApplyButton() {
        return getButton(widget, "Apply");
    }

    private Object getGetStatuses() {
        try {
            return Whitebox.invokeMethod(widget, "getStatuses");
        } catch (Exception e) {
            throw new RupRuntimeException(e);
        }
    }
}
