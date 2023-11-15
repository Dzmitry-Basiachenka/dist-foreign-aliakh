package com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertSame;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Verify {@link TabController}.
 * <p/>
 * Copyright (C) 2014 copyright.com
 * <p/>
 * Date: 01/31/14
 *
 * @author Mikalai Bezmen
 * @author Anton Azarenka
 */
public class TabControllerTest {

    private MockTabController mockTabController;
    private TabSheet tabSheet;

    @Before
    public void setUp() {
        tabSheet = createMock(TabSheet.class);
        mockTabController = new MockTabController(tabSheet);
    }

    @Test
    public void testRefreshWidgetWithRefreshableTab() {
        MockRefreshableWidget mockRefreshableWidget = createMock(MockRefreshableWidget.class);
        List<Component> components = new ArrayList<>();
        expect(tabSheet.getChildren()).andReturn(components.stream()).once();
        components.add(mockRefreshableWidget);
        mockRefreshableWidget.refresh();
        expectLastCall().once();
        replay(tabSheet, mockRefreshableWidget);
        mockTabController.refreshWidget();
        verify(tabSheet, mockRefreshableWidget);
    }

    @Test
    public void testRefreshWidgetWithNotRefreshableTab() {
        expect(tabSheet.getChildren()).andReturn(new ArrayList<Component>().stream()).once();
        replay(tabSheet);
        mockTabController.refreshWidget();
        verify(tabSheet);
    }

    @Test
    public void testGetTabSheet() {
        assertSame(tabSheet, mockTabController.getTabSheet());
    }

    private static class MockTabController extends TabController {

        private final TabSheet tabSheet;

        MockTabController(TabSheet mockTabSheet) {
            this.tabSheet = mockTabSheet;
        }

        @Override
        protected TabSheet getTabSheet() {
            return tabSheet;
        }

        @Override
        protected IWidget instantiateWidget() {
            return null;
        }
    }

    private static class MockRefreshableWidget extends VerticalLayout implements IRefreshable {
        @Override
        public void refresh() {
            //Stub implementation
        }
    }
}
