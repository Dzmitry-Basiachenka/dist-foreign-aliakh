package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.usage;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyComboBox;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.replay;

import com.copyright.rup.dist.foreign.domain.AclUsageBatch;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclUsageFilterController;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;

import com.google.common.collect.Sets;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collection;
import java.util.Collections;

/**
 * Verifies {@link AclUsageFilterWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/31/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclUsageFilterWidgetTest {

    private static final String ACL_USAGE_BATCH_NAME = "ACL Usage Batch 2022";
    private final AclUsageFilterWidget widget = new AclUsageFilterWidget();
    private IAclUsageFilterController controller;

    @Before
    public void setUp() {
        controller = createMock(IAclUsageFilterController.class);
        widget.setController(controller);
    }

    @Test
    public void testUpdateUsageBatchesInFilterWidget() {
        AclUsageBatch usageBatch = buildAclUsageBatch();
        expect(controller.getAllAclUsageBatches()).andReturn(Collections.singletonList(usageBatch)).times(2);
        replay(controller);
        widget.init();
        widget.updateUsageBatchesInFilterWidget();
        ComboBox<String> usageBatchNameComboBox = Whitebox.getInternalState(widget, "usageBatchNameComboBox");
        Collection<?> usageBatchNames = ((ListDataProvider<?>) usageBatchNameComboBox.getDataProvider()).getItems();
        assertEquals(1, usageBatchNames.size());
        assertTrue(usageBatchNames.contains(usageBatch.getName()));
        verify(controller);
    }

    @Test
    public void testInit() {
        expect(controller.getAllAclUsageBatches()).andReturn(Collections.singletonList(buildAclUsageBatch())).once();
        replay(controller);
        assertSame(widget, widget.init());
        assertEquals(2, widget.getComponentCount());
        assertEquals(new MarginInfo(true), widget.getMargin());
        verifyFiltersLayout(widget.getComponent(0));
        verifyButtonsLayout(widget.getComponent(1), "Apply", "Clear");
        verify(controller);
    }

    @Test
    public void testApplyFilter() {
        expect(controller.getAllAclUsageBatches()).andReturn(Collections.singletonList(buildAclUsageBatch())).times(2);
        replay(controller);
        widget.init();
        widget.clearFilter();
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
        verify(controller);
    }

    @Test
    public void testFilterChangedEmptyFilter() {
        expect(controller.getAllAclUsageBatches()).andReturn(Collections.singletonList(buildAclUsageBatch())).once();
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
        expect(controller.getAllAclUsageBatches()).andReturn(Collections.singletonList(buildAclUsageBatch())).times(2);
        replay(controller);
        widget.init();
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
        verify(controller);
    }

    private AclUsageBatch buildAclUsageBatch() {
        AclUsageBatch aclUsageBatch = new AclUsageBatch();
        aclUsageBatch.setId("7f2037ba-7fb0-4222-8454-cd84f7e1a617");
        aclUsageBatch.setName(ACL_USAGE_BATCH_NAME);
        aclUsageBatch.setDistributionPeriod(202212);
        aclUsageBatch.setPeriods(Sets.newHashSet(202206, 202212));
        aclUsageBatch.setEditable(true);
        return aclUsageBatch;
    }

    private void verifyFiltersLayout(Component layout) {
        assertTrue(layout instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) layout;
        assertEquals(2, verticalLayout.getComponentCount());
        verifyFiltersLabel(verticalLayout.getComponent(0));
        verifyComboBox(verticalLayout.getComponent(1), "Usage Batch Name", true,
            Collections.singletonList(ACL_USAGE_BATCH_NAME));
    }

    private void verifyFiltersLabel(Component component) {
        assertTrue(component instanceof Label);
        Label label = (Label) component;
        assertEquals("Filters", label.getValue());
        assertEquals(Cornerstone.LABEL_H2, label.getStyleName());
    }

    private Button getApplyButton() {
        return Whitebox.getInternalState(widget, "applyButton");
    }
}
