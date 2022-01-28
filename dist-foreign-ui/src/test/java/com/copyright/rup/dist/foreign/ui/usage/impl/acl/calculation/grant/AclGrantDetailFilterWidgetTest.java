package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.grant;

import static org.easymock.EasyMock.createMock;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclGrantDetailFilterController;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import org.junit.Before;
import org.junit.Test;

/**
 * Verifies {@link AclGrantDetailFilterWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/28/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclGrantDetailFilterWidgetTest {

    private AclGrantDetailFilterWidget widget;

    @Before
    public void setUp() {
        IAclGrantDetailFilterController controller = createMock(IAclGrantDetailFilterController.class);
        widget = new AclGrantDetailFilterWidget();
        widget.setController(controller);
    }

    @Test
    public void testInit() {
        assertSame(widget, widget.init());
        assertEquals(1, widget.getComponentCount());
        assertEquals(new MarginInfo(true), widget.getMargin());
        verifyFiltersLayout(widget.getComponent(0));
    }

    private void verifyFiltersLayout(Component layout) {
        assertTrue(layout instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) layout;
        assertEquals(1, verticalLayout.getComponentCount());
        verifyFiltersLabel(verticalLayout.getComponent(0));
    }

    private void verifyFiltersLabel(Component component) {
        assertTrue(component instanceof Label);
        Label label = (Label) component;
        assertEquals("Filters", label.getValue());
        assertEquals(Cornerstone.LABEL_H2, label.getStyleName());
    }
}
