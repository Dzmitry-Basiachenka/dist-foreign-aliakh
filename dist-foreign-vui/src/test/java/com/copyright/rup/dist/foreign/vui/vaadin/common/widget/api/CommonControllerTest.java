package com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.Element;

import org.junit.Test;

/**
 * Verify {@link CommonController}.
 * <p/>
 * Copyright (C) 2023 copyright.com
 * <p/>
 * Date: 07/12/2023
 *
 * @author Anton Azarenka
 */
public class CommonControllerTest {

    private IWidget expectedWidget;
    private MockCommonController mockCommonController;

    @Test
    public void testGetWidgetWithoutInitializedWidget() {
        expectedWidget = createMock(IWidget.class);
        mockCommonController = new MockCommonController(expectedWidget);
        expectedWidget.setController(mockCommonController);
        expectLastCall().once();
        replay(expectedWidget);
        IWidget actualWidget = mockCommonController.getWidget();
        assertNotNull(actualWidget);
        assertSame(expectedWidget, actualWidget);
        verify(expectedWidget);
    }

    @Test
    public void testGetWidgetWithInitializedWidget() {
        expectedWidget = createMock(IWidget.class);
        mockCommonController = new MockCommonController(expectedWidget);
        expectedWidget.setController(mockCommonController);
        expectLastCall().once();
        replay(expectedWidget);
        assertNotNull(mockCommonController.getWidget());
        assertSame(expectedWidget, mockCommonController.getWidget());
        verify(expectedWidget);
    }

    @Test(expected = NullPointerException.class)
    public void testGetWidgetWithThrowsNullPointerException() {
        mockCommonController = new MockCommonController(null);
        mockCommonController.getWidget();
    }

    @Test
    public void testInitWidget() {
        expectedWidget = createMock(IWidget.class);
        MockWidget mockWidget = createMock(MockWidget.class);
        IMediator
            iMediator = createMock(IMediator.class);
        mockCommonController = new MockCommonController(expectedWidget);
        expectedWidget.setController(mockCommonController);
        expectLastCall().once();
        expect(expectedWidget.init()).andReturn(mockWidget).once();
        expect(mockWidget.initMediator()).andReturn(iMediator).once();
        iMediator.applyPermissions();
        expectLastCall().once();
        replay(expectedWidget, mockWidget, iMediator);
        IWidget actualWidget = mockCommonController.initWidget();
        assertNotNull(actualWidget);
        assertSame(mockWidget, actualWidget);
        verify(expectedWidget, mockWidget, iMediator);
    }

    @Test(expected = NullPointerException.class)
    public void testInitWidgetThrowsNullPointerException() {
        expectedWidget = createMock(IWidget.class);
        MockWidget mockWidget = createMock(MockWidget.class);
        mockCommonController = new MockCommonController(expectedWidget);
        expectedWidget.setController(mockCommonController);
        expectLastCall().once();
        expect(expectedWidget.init()).andReturn(mockWidget).once();
        expect(mockWidget.initMediator()).andReturn(null).once();
        replay(expectedWidget, mockWidget);
        mockCommonController.initWidget();
    }

    @Test
    public void testInitWidgetWithoutMediator() {
        expectedWidget = createMock(IWidget.class);
        mockCommonController = new MockCommonController(expectedWidget);
        expectedWidget.setController(mockCommonController);
        expectLastCall().once();
        expect(expectedWidget.init()).andReturn(expectedWidget).once();
        replay(expectedWidget);
        IWidget actualWidget = mockCommonController.initWidget();
        assertNotNull(actualWidget);
        assertSame(expectedWidget, actualWidget);
        verify(expectedWidget);
    }

    @Test
    public void testInitWidgetWithTrueInitializedWidgetFlagAndFalseUseCachedFlag() {
        expectedWidget = createMock(IWidget.class);
        mockCommonController = new MockCommonController(expectedWidget);
        expectedWidget.setController(mockCommonController);
        expectLastCall().times(2);
        expect(expectedWidget.init()).andReturn(expectedWidget).times(2);
        replay(expectedWidget);
        //Sets widgetInitialized flag to true
        mockCommonController.initWidget();
        IWidget actualWidget = mockCommonController.initWidget(false);
        assertNotNull(actualWidget);
        assertSame(expectedWidget, actualWidget);
        verify(expectedWidget);
    }

    @Test
    public void testInitWidgetWithTrueInitializedWidgetFlagAndTrueUseCachedFlag() {
        expectedWidget = createMock(IWidget.class);
        mockCommonController = new MockCommonController(expectedWidget);
        expectedWidget.setController(mockCommonController);
        expectLastCall().once();
        expect(expectedWidget.init()).andReturn(expectedWidget).once();
        replay(expectedWidget);
        //Sets widgetInitialized flag to true
        mockCommonController.initWidget();
        IWidget actualWidget = mockCommonController.initWidget(true);
        assertNotNull(actualWidget);
        assertSame(expectedWidget, actualWidget);
        verify(expectedWidget);
    }

    @Test
    public void testRefreshWidgetWithNotIRefreshable() {
        expectedWidget = createMock(IWidget.class);
        mockCommonController = new MockCommonController(expectedWidget);
        expectedWidget.setController(mockCommonController);
        expectLastCall().once();
        replay(expectedWidget);
        mockCommonController.refreshWidget();
        verify(expectedWidget);
    }

    @Test
    public void testRefreshWidgetWithIRefreshable() {
        MockWidget mockWidget = createMock(MockWidget.class);
        mockCommonController = new MockCommonController(mockWidget);
        mockWidget.setController(mockCommonController);
        expectLastCall().once();
        mockWidget.refresh();
        expectLastCall().once();
        replay(mockWidget);
        mockCommonController.refreshWidget();
        verify(mockWidget);
    }

    @Test
    public void testIsWidgetInitializedWithNotWidgetInitialized() {
        mockCommonController = new MockCommonController(null);
        assertFalse(mockCommonController.isWidgetInitialized());
    }

    @Test
    public void testIsWidgetInitializedWithWidgetInitialized() {
        MockWidget mockWidget = createMock(MockWidget.class);
        mockCommonController = new MockCommonController(mockWidget);
        mockWidget.setController(mockCommonController);
        expectLastCall().once();
        mockCommonController.initWidget();
        assertTrue(mockCommonController.isWidgetInitialized());
    }

    private static class MockCommonController extends CommonController<IWidget> {

        private final IWidget iWidget;

        MockCommonController(IWidget widget) {
            this.iWidget = widget;
        }

        @Override
        protected IWidget instantiateWidget() {
            return iWidget;
        }
    }

    private static class MockWidget extends VerticalLayout
        implements IWidget<MockCommonController>, IRefreshable, IMediatorProvider {


        @Override
        public IMediator initMediator() {
            return null;
        }

        @Override
        public void refresh() {
            //stub.
        }

        @Override
        public MockWidget init() {
            return null;
        }

        @Override
        public void setController(MockCommonController controller) {
            //stub.
        }

        @Override
        public Element getElement() {
            return null;
        }
    }
}
