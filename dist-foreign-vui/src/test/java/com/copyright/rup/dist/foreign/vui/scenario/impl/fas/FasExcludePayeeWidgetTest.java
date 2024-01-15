package com.copyright.rup.dist.foreign.vui.scenario.impl.fas;

import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getDialogContent;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.vui.UiTestHelper;
import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IFasExcludePayeeController;
import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IFasExcludePayeeFilterController;
import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IFasExcludePayeeFilterWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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
    private IFasExcludePayeeController controller;
    private IFasExcludePayeeFilterController filterController;
    private IFasExcludePayeeFilterWidget filterWidget;

    @Before
    public void setUp() {
        controller = createMock(IFasExcludePayeeController.class);
        widget = new FasExcludePayeeWidget();
        widget.setController(controller);
        filterController = createMock(IFasExcludePayeeFilterController.class);
        filterWidget = new FasExcludePayeeFilterWidget();
        filterWidget.setController(filterController);
    }

    @Test
    public void testStructure() {
        initWidget();
        verifyWindow(widget, "Exclude Details By Payee", "1200px", "500px", Unit.PIXELS, true);
        assertEquals("exclude-details-by-payee-window", widget.getId().orElseThrow());
        assertTrue(widget.isDraggable());
        assertTrue(widget.isResizable());
        Component component = getDialogContent(widget);
        assertThat(component, instanceOf(SplitLayout.class));
        var splitLayout = (SplitLayout) component;
        assertThat(splitLayout.getPrimaryComponent(), instanceOf(VerticalLayout.class));
        verifyButtonsLayout(UiTestHelper.getFooterComponent(widget, 2), true, "Exclude Details",
            "Redesignate Details", "Clear", "Close");
    }

    @Test
    public void testGetFilterController() {
        expect(controller.getExcludePayeesFilterController()).andReturn(filterController).once();
        expect(filterController.initWidget()).andReturn(filterWidget).once();
        replay(controller, filterController);
        widget.init();
        verify(controller, filterController);
    }

    private void initWidget() {
        expect(controller.getExcludePayeesFilterController()).andReturn(filterController).once();
        expect(filterController.initWidget()).andReturn(filterWidget).once();
        replay(controller, filterController);
        widget.init();
        verify(controller, filterController);
        reset(controller, filterController);
    }
}
