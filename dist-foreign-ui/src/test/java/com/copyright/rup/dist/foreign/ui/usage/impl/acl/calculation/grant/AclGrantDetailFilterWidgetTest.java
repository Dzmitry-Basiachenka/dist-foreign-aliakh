package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.grant;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyItemsFilterWidget;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclGrantDetailFilterController;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.Collections;

/**
 * Verifies {@link AclGrantDetailFilterWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/28/2022
 *
 * @author Dzmitry Basiachenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Windows.class)
public class AclGrantDetailFilterWidgetTest {

    private static final String ACL_GRANT_SET_NAME = "ACL Grant Set 2021";

    private AclGrantDetailFilterWidget widget;
    private IAclGrantDetailFilterController controller;

    @Before
    public void setUp() {
        controller = createMock(IAclGrantDetailFilterController.class);
        replay(controller);
        widget = new AclGrantDetailFilterWidget();
        widget.setController(controller);
    }

    @Test
    public void testInit() {
        assertSame(widget, widget.init());
        assertEquals(2, widget.getComponentCount());
        assertEquals(new MarginInfo(true), widget.getMargin());
        verifyFiltersLayout(widget.getComponent(0));
        verifyButtonsLayout(widget.getComponent(1), "Apply", "Clear");
        verify(controller);
    }

    @Test
    public void testApplyFilter() {
        widget.init();
        widget.clearFilter();
        verify(controller);
        Button applyButton = getApplyButton();
        assertFalse(applyButton.isEnabled());
        assertTrue(widget.getAppliedFilter().isEmpty());
        assertTrue(widget.getFilter().isEmpty());
        widget.getFilter().setGrantSetNames(Collections.singleton(ACL_GRANT_SET_NAME));
        assertTrue(widget.getAppliedFilter().getGrantSetNames().isEmpty());
        assertFalse(widget.getFilter().isEmpty());
        applyButton.setEnabled(true);
        assertTrue(applyButton.isEnabled());
        applyButton.click();
        assertFalse(applyButton.isEnabled());
        assertFalse(widget.getAppliedFilter().isEmpty());
    }

    @Test
    public void testFilterChangedEmptyFilter() {
        widget.init();
        Button applyButton = getApplyButton();
        assertFalse(applyButton.isEnabled());
        widget.applyFilter();
        verify(controller);
        assertFalse(applyButton.isEnabled());
    }

    @Test
    public void testClearFilter() {
        widget.init();
        Button applyButton = getApplyButton();
        assertTrue(widget.getFilter().isEmpty());
        assertTrue(widget.getAppliedFilter().isEmpty());
        widget.getFilter().setGrantSetNames(Collections.singleton(ACL_GRANT_SET_NAME));
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

    private void verifyFiltersLayout(Component layout) {
        assertTrue(layout instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) layout;
        assertEquals(2, verticalLayout.getComponentCount());
        verifyFiltersLabel(verticalLayout.getComponent(0));
        verifyItemsFilterWidget(verticalLayout.getComponent(1), "Grant Sets");
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