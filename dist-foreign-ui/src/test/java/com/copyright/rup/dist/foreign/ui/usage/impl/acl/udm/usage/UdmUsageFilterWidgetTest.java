package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.usage;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyComboBox;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyFiltersLabel;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyItemsFilterWidget;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyLabel;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyMoreFiltersButton;

import static org.easymock.EasyMock.anyObject;
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

import com.copyright.rup.dist.foreign.domain.UdmUsageOriginEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.UiTestHelper;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageFilterController;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.google.common.collect.ImmutableSet;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

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
        UsageStatusEnum.RH_FOUND, UsageStatusEnum.RH_NOT_FOUND, UsageStatusEnum.OPS_REVIEW,
        UsageStatusEnum.SPECIALIST_REVIEW, UsageStatusEnum.ELIGIBLE);

    private UdmUsageFilterWidget widget;
    private UdmUsageAppliedFilterWidget appliedFilterWidget;

    @Before
    public void setUp() {
        mockStatic(ForeignSecurityUtils.class);
        IUdmUsageFilterController udmUsageFilterController = createMock(IUdmUsageFilterController.class);
        appliedFilterWidget = createMock(UdmUsageAppliedFilterWidget.class);
        expect(ForeignSecurityUtils.hasResearcherPermission()).andReturn(false).once();
        widget = new UdmUsageFilterWidget(udmUsageFilterController);
        widget.setController(udmUsageFilterController);
    }

    @Test
    public void testInit() {
        assertSame(widget, widget.init());
        assertEquals(4, widget.getComponentCount());
        assertEquals(new MarginInfo(true), widget.getMargin());
        verifyFiltersLayout(widget.getComponent(0));
        UiTestHelper.verifyButtonsLayout(widget.getComponent(1),  "Apply", "Clear");
        verifyLabel(widget.getComponent(2), "Applied Filters:", ContentMode.TEXT, -1.0f);
        assertThat(widget.getComponent(3), instanceOf(UdmUsageAppliedFilterWidget.class));
    }

    @Test
    public void testApplyFilter() {
        appliedFilterWidget.refreshFilterPanel(anyObject());
        expectLastCall();
        replay(appliedFilterWidget, ForeignSecurityUtils.class);
        widget.init();
        widget.clearFilter();
        Whitebox.setInternalState(widget, appliedFilterWidget);
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
        verify(appliedFilterWidget, ForeignSecurityUtils.class);
    }

    @Test
    public void testFilterChangedEmptyFilter() {
        replay(ForeignSecurityUtils.class);
        widget.init();
        Button applyButton = getApplyButton();
        assertFalse(applyButton.isEnabled());
        widget.applyFilter();
        verify(ForeignSecurityUtils.class);
        assertFalse(applyButton.isEnabled());
    }

    @Test
    public void testClearFilter() {
        appliedFilterWidget.refreshFilterPanel(anyObject());
        expectLastCall().times(2);
        replay(appliedFilterWidget, ForeignSecurityUtils.class);
        widget.init();
        Whitebox.setInternalState(widget, appliedFilterWidget);
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
        assertNull(Whitebox.<ComboBox<?>>getInternalState(widget, "statusComboBox").getValue());
        assertNull(Whitebox.<ComboBox<?>>getInternalState(widget, "usageOriginComboBox").getValue());
        verify(appliedFilterWidget, ForeignSecurityUtils.class);
    }

    @Test
    public void verifyMoreFiltersButtonClickListener() {
        mockStatic(ForeignSecurityUtils.class);
        expect(ForeignSecurityUtils.hasResearcherPermission()).andReturn(true).times(2);
        mockStatic(Windows.class);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        Windows.showModalWindow(anyObject(UdmUsageFiltersWindow.class));
        expectLastCall().once();
        replay(clickEvent, Windows.class, ForeignSecurityUtils.class);
        widget.init();
        ClickListener clickListener = (ClickListener) ((Button) Whitebox.getInternalState(widget, "moreFiltersButton"))
            .getListeners(ClickEvent.class).iterator().next();
        clickListener.buttonClick(clickEvent);
        verify(clickEvent, Windows.class, ForeignSecurityUtils.class);
    }

    @Test
    public void verifyApplyButtonClickListener() {
        appliedFilterWidget.refreshFilterPanel(anyObject());
        expectLastCall().once();
        replay(appliedFilterWidget);
        widget.init();
        widget.clearFilter();
        Whitebox.setInternalState(widget, appliedFilterWidget);
        Button applyButton = getApplyButton();
        assertFalse(applyButton.isEnabled());
        assertTrue(widget.getAppliedFilter().getUdmBatchesIds().isEmpty());
        assertTrue(widget.getFilter().getUdmBatchesIds().isEmpty());
        widget.getFilter().setUdmBatchesIds(Set.of("87a2f5f4-5663-4ae7-8765-3515c2a82918"));
        assertTrue(widget.getAppliedFilter().getPeriods().isEmpty());
        assertTrue(widget.getFilter().getPeriods().isEmpty());
        widget.getFilter().setPeriods(Set.of(202106));
        assertNotEquals(widget.getFilter(), widget.getAppliedFilter());
        applyButton.setEnabled(true);
        assertTrue(widget.getAppliedFilter().getUdmBatchesIds().isEmpty());
        assertFalse(widget.getFilter().getUdmBatchesIds().isEmpty());
        assertTrue(widget.getAppliedFilter().getPeriods().isEmpty());
        assertFalse(widget.getFilter().getPeriods().isEmpty());
        assertTrue(applyButton.isEnabled());
        applyButton.click();
        assertFalse(applyButton.isEnabled());
        assertFalse(widget.getAppliedFilter().getUdmBatchesIds().isEmpty());
        assertFalse(widget.getAppliedFilter().getPeriods().isEmpty());
        verify(appliedFilterWidget);
    }

    @Test
    public void verifyButtonClickListener() {
        ClickEvent clickEvent = createMock(ClickEvent.class);
        replay(clickEvent, ForeignSecurityUtils.class);
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
        verify(clickEvent, ForeignSecurityUtils.class);
    }

    private void verifyFiltersLayout(Component layout) {
        assertThat(layout, instanceOf(VerticalLayout.class));
        VerticalLayout verticalLayout = (VerticalLayout) layout;
        assertEquals(6, verticalLayout.getComponentCount());
        verifyFiltersLabel(verticalLayout.getComponent(0));
        verifyItemsFilterWidget(verticalLayout.getComponent(1), "Batches");
        verifyItemsFilterWidget(verticalLayout.getComponent(2), "Periods");
        verifyComboBox(verticalLayout.getComponent(3), "Status", true, ACL_STATUSES);
        verifyComboBox(verticalLayout.getComponent(4), "Usage Origin", true, UdmUsageOriginEnum.values());
        verifyMoreFiltersButton(verticalLayout.getComponent(5), 2);
    }

    private Button getApplyButton() {
        return Whitebox.getInternalState(widget, "applyButton");
    }
}
