package com.copyright.rup.dist.foreign.ui.main.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.vaadin.widget.api.IController;
import com.copyright.rup.vaadin.widget.api.IWidget;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Label;

import org.junit.Before;
import org.junit.Test;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Verifies {@link SwitchableWidget}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/5/19
 *
 * @author Stanislau Rudak
 */
public class SwitchableWidgetTest {

    private ITestController firstControllerMock;
    private ITestController secondControllerMock;
    private Supplier<ITestController> controllerSupplierMock;
    private Consumer<ITestWidget> listenerRegistererMock;

    private SwitchableWidget<ITestWidget, ITestController> widget;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        firstControllerMock = createMock(ITestController.class);
        secondControllerMock = createMock(ITestController.class);
        controllerSupplierMock = createMock(Supplier.class);
        listenerRegistererMock = createMock(Consumer.class);
        widget = new SwitchableWidget<>(controllerSupplierMock, listenerRegistererMock);
    }

    @Test
    public void testLayout() {
        assertEquals("switchable-widget", widget.getStyleName());
        assertEquals(100, widget.getWidth(), 0);
        assertEquals(100, widget.getHeight(), 0);
        assertEquals(Unit.PERCENTAGE, widget.getHeightUnits());
        assertEquals(Unit.PERCENTAGE, widget.getWidthUnits());
    }

    @Test
    public void testRefresh() {
        TestWidget firstWidget = new TestWidget();
        TestWidget secondWidget = new TestWidget();
        prepareRefreshExpectations(firstControllerMock, firstWidget);
        replay(firstControllerMock, secondControllerMock, controllerSupplierMock, listenerRegistererMock);
        widget.refresh();
        verify(firstControllerMock, secondControllerMock, controllerSupplierMock, listenerRegistererMock);
        assertEquals(firstWidget, widget.getContent());
        reset(firstControllerMock, secondControllerMock, controllerSupplierMock, listenerRegistererMock);
        prepareRefreshExpectations(secondControllerMock, secondWidget);
        replay(firstControllerMock, secondControllerMock, controllerSupplierMock, listenerRegistererMock);
        widget.refresh();
        verify(firstControllerMock, secondControllerMock, controllerSupplierMock, listenerRegistererMock);
        assertEquals(secondWidget, widget.getContent());
    }

    private void prepareRefreshExpectations(ITestController controllerMock, ITestWidget content) {
        expect(controllerSupplierMock.get()).andReturn(controllerMock).once();
        expect(controllerMock.initWidget()).andReturn(content).once();
        listenerRegistererMock.accept(content);
        expectLastCall().once();
        controllerMock.refreshWidget();
        expectLastCall().once();
    }

    private interface ITestWidget extends IWidget<ITestController> {
    }

    private interface ITestController extends IController<ITestWidget> {
    }

    private static class TestWidget extends Label implements ITestWidget {

        @Override
        public ITestWidget init() {
            return this;
        }

        @Override
        public void setController(ITestController controller) {
            // do nothing
        }
    }
}
