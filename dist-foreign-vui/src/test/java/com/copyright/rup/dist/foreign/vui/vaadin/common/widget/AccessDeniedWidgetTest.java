package com.copyright.rup.dist.foreign.vui.vaadin.common.widget;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;

import org.junit.Test;

/**
 * Verify {@link AccessDeniedWidget}.
 * <p/>
 * Copyright (C) 2014 copyright.com
 * <p/>
 * Date: 01/22/2014
 *
 * @author Anton Azarenka
 */
public class AccessDeniedWidgetTest {

    private static final String ACCESS_DENIED_WIDGET_SIZE = "100%";

    @Test
    public void testAccessDeniedWidget() {
        AccessDeniedWidget accessDeniedWidget = new AccessDeniedWidget();
        assertNotNull(accessDeniedWidget);
        assertEquals(1, accessDeniedWidget.getComponentCount());
        Label accessDeniedWidgetLabel = (Label) accessDeniedWidget.getComponentAt(0);
        assertEquals("You have no permission to access application. Please log out.",
            accessDeniedWidgetLabel.getText());
        assertEquals("access-denied-label", accessDeniedWidgetLabel.getClassName());
        assertEquals("access-denied-layout", accessDeniedWidget.getClassName());
        assertEquals(Alignment.CENTER, accessDeniedWidget.getAlignSelf(accessDeniedWidgetLabel));
        assertEquals(ACCESS_DENIED_WIDGET_SIZE, accessDeniedWidget.getHeight());
        assertEquals(Unit.PERCENTAGE, accessDeniedWidget.getHeightUnit().orElseThrow());
        assertEquals(ACCESS_DENIED_WIDGET_SIZE, accessDeniedWidget.getWidth());
        assertEquals(Unit.PERCENTAGE, accessDeniedWidget.getWidthUnit().orElseThrow());
    }
}
