package com.copyright.rup.dist.foreign.vui.scenario.impl.fas;

import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.createMock;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.vui.UiTestHelper;
import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IFasExcludePayeeController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.splitlayout.SplitLayout;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Verifies {@link FasExcludePayeeWidget}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 10/14/19
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class})
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
public class FasExcludePayeeWidgetTest {

    private FasExcludePayeeWidget widget;

    @Before
    public void setUp() {
        IFasExcludePayeeController controller = createMock(IFasExcludePayeeController.class);
        widget = new FasExcludePayeeWidget();
        widget.setController(controller);
        widget.init();
    }

    @Test
    public void testStructure() {
        verifyWindow(widget, "Exclude Details By Payee", "1200px", "500px", Unit.PIXELS, true);
        assertEquals("exclude-details-by-payee-window", widget.getId().orElseThrow());
        assertTrue(widget.isDraggable());
        assertTrue(widget.isResizable());
        Component component = UiTestHelper.getDialogContent(widget);
        assertThat(component, instanceOf(SplitLayout.class));
    }
}
