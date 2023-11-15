package com.copyright.rup.dist.foreign.vui.vaadin.common.widget;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import org.junit.Test;

/**
 * Verify {@link RootWidget}.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 07/12/2023
 *
 * @author Anton Azarenka
 */
public class RootWidgetTest {

    private final RootWidget rootWidget = new RootWidget(new VerticalLayout());

    @Test
    public void testInit() {
        assertThat(rootWidget.getComponentAt(0), instanceOf(VerticalLayout.class));
        assertFalse(rootWidget.isPadding());
        assertFalse(rootWidget.isMargin());
        assertEquals(JustifyContentMode.CENTER, rootWidget.getJustifyContentMode());
        assertEquals(Alignment.STRETCH, rootWidget.getDefaultHorizontalComponentAlignment());
        assertEquals(Alignment.STRETCH, rootWidget.getAlignItems());
        assertEquals(JustifyContentMode.CENTER, rootWidget.getJustifyContentMode());
    }
}
