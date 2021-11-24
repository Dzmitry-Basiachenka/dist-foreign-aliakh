package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.baseline.value;

import static org.easymock.EasyMock.createMock;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.ui.usage.UiCommonHelper;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

/**
 * Verifies {@link UdmBaselineValueFilterWidget}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/23/2021
 *
 * @author Anton Azarenka
 */
public class UdmBaselineValueFilterWidgetTest {

    private UdmBaselineValueFilterWidget widget;
    private UdmBaselineValueFilterController controller;

    @Before
    public void setUp() {
        controller = createMock(UdmBaselineValueFilterController.class);
        widget = new UdmBaselineValueFilterWidget(controller);
        widget.setController(controller);
    }

    @Test
    public void testInit() {
        replay(controller);
        assertSame(widget, widget.init());
        assertEquals(2, widget.getComponentCount());
        assertEquals(new MarginInfo(true), widget.getMargin());
        verifyFiltersLayout(widget.getComponent(0));
        UiCommonHelper.verifyButtonsLayout(widget.getComponent(1), "Apply", "Clear");
        verify(controller);
    }

    private void verifyFiltersLayout(Component layout) {
        assertTrue(layout instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) layout;
        assertEquals(2, verticalLayout.getComponentCount());
        verifyFiltersLabel(verticalLayout.getComponent(0));
        verifyMoreFiltersButton(verticalLayout.getComponent(1));
    }

    private void verifyFiltersLabel(Component component) {
        assertTrue(component instanceof Label);
        Label label = (Label) component;
        assertEquals("Filters", label.getValue());
        assertEquals(Cornerstone.LABEL_H2, label.getStyleName());
    }

    private void verifyMoreFiltersButton(Component component) {
        assertTrue(component instanceof Button);
        Button button = (Button) component;
        assertEquals("More Filters", component.getCaption());
        assertTrue(StringUtils.contains(button.getStyleName(), Cornerstone.BUTTON_LINK));
        Collection<?> listeners = button.getListeners(Button.ClickEvent.class);
        assertEquals(1, listeners.size());
    }
}
