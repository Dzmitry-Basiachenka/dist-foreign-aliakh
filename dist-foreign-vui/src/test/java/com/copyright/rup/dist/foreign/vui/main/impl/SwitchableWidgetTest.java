package com.copyright.rup.dist.foreign.vui.main.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.vui.main.api.IControllerProvider;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IRefreshable;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IWidget;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.html.Label;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

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
    private IControllerProvider<ITestController> controllerProviderMock;
    private Consumer<ITestWidget> listenerRegistererMock;

    private SwitchableWidget<ITestWidget, ITestController> widget;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        firstControllerMock = createMock(ITestController.class);
        secondControllerMock = createMock(ITestController.class);
        controllerProviderMock = createMock(IControllerProvider.class);
        listenerRegistererMock = createMock(Consumer.class);
        widget = new SwitchableWidget<>(controllerProviderMock, listenerRegistererMock);
    }

    @Test
    public void testLayout() {
        assertEquals("switchable-widget", widget.getClassName());
        assertEquals("100%", widget.getWidth());
        assertEquals("100%", widget.getHeight());
        assertEquals(Unit.PERCENTAGE, widget.getHeightUnit().orElseThrow());
        assertEquals(Unit.PERCENTAGE, widget.getWidthUnit().orElseThrow());
    }

    @Test
    public void testRefresh() {
        TestWidget testWidget = new TestWidget();
        prepareInitWidgetExpectations(firstControllerMock, testWidget);
        replay(firstControllerMock, controllerProviderMock, listenerRegistererMock);
        widget.updateProductFamily();
        assertFalse(testWidget.isRefreshed());
        widget.refresh();
        assertTrue(testWidget.isRefreshed());
        verify(firstControllerMock, controllerProviderMock, listenerRegistererMock);
    }

    @Test
    public void testUpdateProductFamily() {
        TestWidget firstWidget = new TestWidget();
        TestWidget secondWidget = new TestWidget();
        prepareInitWidgetExpectations(firstControllerMock, firstWidget);
        replay(firstControllerMock, secondControllerMock, controllerProviderMock, listenerRegistererMock);
        widget.updateProductFamily();
        assertFalse(firstWidget.isRefreshed());
        verify(firstControllerMock, secondControllerMock, controllerProviderMock, listenerRegistererMock);
        assertEquals(firstWidget, widget.getChildren().collect(Collectors.toList()).get(0));
        reset(firstControllerMock, secondControllerMock, controllerProviderMock, listenerRegistererMock);
        prepareInitWidgetExpectations(secondControllerMock, secondWidget);
        replay(firstControllerMock, secondControllerMock, controllerProviderMock, listenerRegistererMock);
        widget.updateProductFamily();
        assertFalse(firstWidget.isRefreshed());
        verify(firstControllerMock, secondControllerMock, controllerProviderMock, listenerRegistererMock);
        assertEquals(secondWidget, widget.getChildren().collect(Collectors.toList()).get(1));
    }

    private void prepareInitWidgetExpectations(ITestController controllerMock, ITestWidget content) {
        expect(controllerProviderMock.getController()).andReturn(Optional.of(controllerMock)).once();
        expect(controllerMock.initWidget()).andReturn(content).once();
        listenerRegistererMock.accept(content);
        expectLastCall().once();
    }

    private interface ITestWidget extends IWidget<ITestController> {
    }

    private interface ITestController extends IController<ITestWidget> {
    }

    private static class TestWidget extends Label implements ITestWidget, IRefreshable {

        private boolean refreshed;

        @Override
        public ITestWidget init() {
            return this;
        }

        @Override
        public void refresh() {
            refreshed = true;
        }

        @Override
        public void setController(ITestController controller) {
            // do nothing
        }

        boolean isRefreshed() {
            return refreshed;
        }
    }
}
