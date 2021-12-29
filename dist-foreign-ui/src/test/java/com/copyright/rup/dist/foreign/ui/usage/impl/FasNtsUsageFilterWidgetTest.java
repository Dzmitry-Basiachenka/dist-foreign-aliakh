package com.copyright.rup.dist.foreign.ui.usage.impl;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyComboBox;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyItemsFilterWidget;

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

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.ui.usage.api.IFasNtsUsageFilterController;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.widget.LocalDateWidget;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * Verifies {@link FasNtsUsageFilterWidget}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/12/19
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Windows.class)
public class FasNtsUsageFilterWidgetTest {

    private static final Integer FISCAL_YEAR = 2017;
    private static final Long ACCOUNT_NUMBER = 12345678L;
    private static final String FAS_PRODUCT_FAMILY = "FAS";

    private static final Set<UsageStatusEnum> FAS_FAS2_STATUSES = ImmutableSet.of(UsageStatusEnum.NEW,
        UsageStatusEnum.WORK_NOT_FOUND, UsageStatusEnum.WORK_RESEARCH, UsageStatusEnum.WORK_FOUND,
        UsageStatusEnum.RH_NOT_FOUND, UsageStatusEnum.RH_FOUND, UsageStatusEnum.SENT_FOR_RA, UsageStatusEnum.ELIGIBLE,
        UsageStatusEnum.NTS_WITHDRAWN, UsageStatusEnum.TO_BE_DISTRIBUTED);
    private static final Set<UsageStatusEnum> NTS_STATUSES =
        ImmutableSet.of(UsageStatusEnum.WORK_FOUND, UsageStatusEnum.RH_FOUND, UsageStatusEnum.UNCLASSIFIED,
            UsageStatusEnum.ELIGIBLE, UsageStatusEnum.SCENARIO_EXCLUDED);
    private IFasNtsUsageFilterController usagesFilterController;
    private FasNtsUsageFilterWidget widget;

    @Before
    public void setUp() {
        usagesFilterController = createMock(IFasNtsUsageFilterController.class);
        widget = new FasNtsUsageFilterWidget(usagesFilterController);
        widget.setController(usagesFilterController);
        expect(usagesFilterController.getFiscalYears()).andReturn(Collections.singletonList(FISCAL_YEAR)).once();
    }

    @Test
    public void testInit() {
        expect(usagesFilterController.getSelectedProductFamily()).andReturn(FAS_PRODUCT_FAMILY).times(2);
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
        expect(usagesFilterController.getSelectedProductFamily()).andReturn(FAS_PRODUCT_FAMILY).times(4);
        expect(usagesFilterController.getFiscalYears()).andReturn(Collections.singletonList(FISCAL_YEAR)).once();
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
        expect(usagesFilterController.getSelectedProductFamily()).andReturn(FAS_PRODUCT_FAMILY).times(2);
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
        expect(usagesFilterController.getSelectedProductFamily()).andReturn(FAS_PRODUCT_FAMILY).times(4);
        expect(usagesFilterController.getFiscalYears()).andReturn(Collections.singletonList(FISCAL_YEAR)).once();
        replay(usagesFilterController);
        widget.init();
        Button applyButton = getApplyButton();
        assertTrue(widget.getFilter().getRhAccountNumbers().isEmpty());
        assertTrue(widget.getAppliedFilter().getRhAccountNumbers().isEmpty());
        assertEquals(FAS_PRODUCT_FAMILY, widget.getFilter().getProductFamily());
        assertNull(widget.getAppliedFilter().getProductFamily());
        widget.getFilter().setRhAccountNumbers(Sets.newHashSet(ACCOUNT_NUMBER));
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
        verify(usagesFilterController);
    }

    @Test
    public void verifyApplyButtonClickListener() {
        expect(usagesFilterController.getSelectedProductFamily()).andReturn(FAS_PRODUCT_FAMILY).times(2);
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
        expect(usagesFilterController.getSelectedProductFamily()).andReturn(FAS_PRODUCT_FAMILY).times(4);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        expect(usagesFilterController.getFiscalYears()).andReturn(Collections.singletonList(FISCAL_YEAR)).once();
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

    @Test
    public void testGetAvailableStatusesFasProductFamily() {
        expect(usagesFilterController.getSelectedProductFamily()).andReturn(FAS_PRODUCT_FAMILY).times(3);
        replay(usagesFilterController);
        widget.init();
        assertEquals(FAS_FAS2_STATUSES, getGetStatuses());
    }

    @Test
    public void testGetAvailableStatusesNtsProductFamily() {
        expect(usagesFilterController.getSelectedProductFamily()).andReturn("NTS").times(3);
        expect(usagesFilterController.getFiscalYears()).andReturn(Collections.singletonList(FISCAL_YEAR)).once();
        replay(usagesFilterController);
        widget.init();
        assertEquals(NTS_STATUSES, getGetStatuses());
    }

    private void verifyFiltersLayout(Component layout) {
        assertTrue(layout instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) layout;
        assertEquals(6, verticalLayout.getComponentCount());
        verifyFiltersLabel(verticalLayout.getComponent(0));
        verifyItemsFilterWidget(verticalLayout.getComponent(1), "Batches");
        verifyItemsFilterWidget(verticalLayout.getComponent(2), "RROs");
        verifyDateWidget(verticalLayout.getComponent(3));
        verifyComboBox(verticalLayout.getComponent(4), "Status", true, FAS_FAS2_STATUSES);
        verifyComboBox(verticalLayout.getComponent(5), "Fiscal Year To", true, FISCAL_YEAR);
    }

    private void verifyFiltersLabel(Component component) {
        assertTrue(component instanceof Label);
        Label label = (Label) component;
        assertEquals("Filters", label.getValue());
        assertEquals(Cornerstone.LABEL_H2, label.getStyleName());
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
        return Whitebox.getInternalState(widget, "applyButton");
    }

    private Object getGetStatuses() {
        try {
            return Whitebox.invokeMethod(widget, "getStatuses");
        } catch (Exception e) {
            throw new RupRuntimeException(e);
        }
    }
}
