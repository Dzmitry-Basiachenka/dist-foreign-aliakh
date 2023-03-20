package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.usage;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyComboBox;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyFiltersLabel;
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

import com.copyright.rup.dist.foreign.domain.AclUsageBatch;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclUsageFilterController;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.List;
import java.util.Set;

/**
 * Verifies {@link AclUsageFilterWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/31/2022
 *
 * @author Dzmitry Basiachenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Windows.class)
public class AclUsageFilterWidgetTest {

    private static final String ACL_USAGE_BATCH_NAME = "ACL Usage Batch 2022";

    private AclUsageFilterWidget widget;
    private AclUsageAppliedFilterWidget appliedFilterWidget;
    private IAclUsageFilterController controller;

    @Before
    public void setUp() {
        appliedFilterWidget = createMock(AclUsageAppliedFilterWidget.class);
        controller = createMock(IAclUsageFilterController.class);
        widget = new AclUsageFilterWidget(controller);
        widget.setController(controller);
    }

    @Test
    public void testInit() {
        expect(controller.getAllAclUsageBatches()).andReturn(List.of(buildAclUsageBatch())).once();
        replay(controller);
        assertSame(widget, widget.init());
        assertEquals(4, widget.getComponentCount());
        assertEquals(new MarginInfo(true), widget.getMargin());
        verifyFiltersLayout(widget.getComponent(0));
        verifyButtonsLayout(widget.getComponent(1), "Apply", "Clear");
        verifyLabel(widget.getComponent(2), "Applied Filters:", ContentMode.TEXT, -1.0f);
        assertThat(widget.getComponent(3), instanceOf(AclUsageAppliedFilterWidget.class));
        verify(controller);
    }

    @Test
    public void testApplyFilter() {
        expect(controller.getAllAclUsageBatches()).andReturn(List.of(buildAclUsageBatch())).times(2);
        appliedFilterWidget.refreshFilterPanel(anyObject());
        expectLastCall();
        replay(controller, appliedFilterWidget);
        widget.init();
        widget.clearFilter();
        Whitebox.setInternalState(widget, appliedFilterWidget);
        Button applyButton = getApplyButton();
        assertFalse(applyButton.isEnabled());
        assertTrue(widget.getAppliedFilter().isEmpty());
        assertTrue(widget.getFilter().isEmpty());
        widget.getFilter().setUsageBatchName(ACL_USAGE_BATCH_NAME);
        assertNotEquals(widget.getFilter(), widget.getAppliedFilter());
        applyButton.setEnabled(true);
        assertFalse(widget.getFilter().isEmpty());
        assertTrue(applyButton.isEnabled());
        applyButton.click();
        assertFalse(applyButton.isEnabled());
        assertFalse(widget.getAppliedFilter().isEmpty());
        verify(controller, appliedFilterWidget);
    }

    @Test
    public void testFilterChangedEmptyFilter() {
        expect(controller.getAllAclUsageBatches()).andReturn(List.of(buildAclUsageBatch())).once();
        replay(controller);
        widget.init();
        Button applyButton = getApplyButton();
        assertFalse(applyButton.isEnabled());
        widget.applyFilter();
        assertFalse(applyButton.isEnabled());
        verify(controller);
    }

    @Test
    public void testClearFilter() {
        expect(controller.getAllAclUsageBatches()).andReturn(List.of(buildAclUsageBatch())).times(2);
        appliedFilterWidget.refreshFilterPanel(anyObject());
        expectLastCall().times(2);
        replay(controller, appliedFilterWidget);
        widget.init();
        Whitebox.setInternalState(widget, appliedFilterWidget);
        Button applyButton = getApplyButton();
        assertTrue(widget.getFilter().isEmpty());
        assertTrue(widget.getAppliedFilter().isEmpty());
        widget.getFilter().setUsageBatchName(ACL_USAGE_BATCH_NAME);
        applyButton.setEnabled(true);
        applyButton.click();
        assertFalse(widget.getFilter().isEmpty());
        assertFalse(widget.getAppliedFilter().isEmpty());
        widget.clearFilter();
        assertTrue(widget.getFilter().isEmpty());
        assertTrue(widget.getAppliedFilter().isEmpty());
        assertFalse(applyButton.isEnabled());
        verify(controller, appliedFilterWidget);
    }

    @Test
    public void verifyMoreFiltersButtonClickListener() {
        expect(controller.getAllAclUsageBatches()).andReturn(List.of(buildAclUsageBatch())).once();
        mockStatic(Windows.class);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        Windows.showModalWindow(anyObject(AclUsageFiltersWindow.class));
        expectLastCall().once();
        replay(clickEvent, Windows.class, controller);
        widget.init();
        ClickListener clickListener =
            (ClickListener) (getMoreFiltersButton()).getListeners(ClickEvent.class).iterator().next();
        clickListener.buttonClick(clickEvent);
        verify(clickEvent, Windows.class, controller);
    }

    @Test
    public void testMoreFiltersButton() {
        expect(controller.getAllAclUsageBatches()).andReturn(List.of(buildAclUsageBatch())).once();
        replay(controller);
        widget.init();
        Button moreFiltersButton = getMoreFiltersButton();
        ComboBox<String> usageBatchNameComboBox = Whitebox.getInternalState(widget, "usageBatchNameComboBox");
        assertNull(usageBatchNameComboBox.getValue());
        assertFalse(moreFiltersButton.isEnabled());
        usageBatchNameComboBox.setValue(ACL_USAGE_BATCH_NAME);
        assertTrue(moreFiltersButton.isEnabled());
        verify(controller);
    }

    private AclUsageBatch buildAclUsageBatch() {
        AclUsageBatch aclUsageBatch = new AclUsageBatch();
        aclUsageBatch.setId("7f2037ba-7fb0-4222-8454-cd84f7e1a617");
        aclUsageBatch.setName(ACL_USAGE_BATCH_NAME);
        aclUsageBatch.setDistributionPeriod(202212);
        aclUsageBatch.setPeriods(Set.of(202206, 202212));
        aclUsageBatch.setEditable(true);
        return aclUsageBatch;
    }

    private void verifyFiltersLayout(Component layout) {
        assertThat(layout, instanceOf(VerticalLayout.class));
        VerticalLayout verticalLayout = (VerticalLayout) layout;
        assertEquals(3, verticalLayout.getComponentCount());
        verifyFiltersLabel(verticalLayout.getComponent(0));
        verifyComboBox(verticalLayout.getComponent(1), "Usage Batch Name", true, List.of(ACL_USAGE_BATCH_NAME));
        verifyMoreFiltersButton(verticalLayout.getComponent(2), 2);
    }

    private Button getApplyButton() {
        return Whitebox.getInternalState(widget, "applyButton");
    }

    private Button getMoreFiltersButton() {
        return Whitebox.getInternalState(widget, "moreFiltersButton");
    }
}
