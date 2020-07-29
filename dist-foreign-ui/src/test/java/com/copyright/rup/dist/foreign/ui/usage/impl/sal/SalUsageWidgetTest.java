package com.copyright.rup.dist.foreign.ui.usage.impl.sal;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.expectNew;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.ui.usage.api.sal.ISalUsageController;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

/**
 * Verifies {@link SalUsageWidget}.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 07/28/2020
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({SalUsageWidget.class})
public class SalUsageWidgetTest {

    private SalUsageWidget usagesWidget;
    private ISalUsageController controller;

    @Before
    public void setUp() {
        controller = createMock(ISalUsageController.class);
        usagesWidget = new SalUsageWidget();
        usagesWidget.setController(controller);
        expect(controller.initUsagesFilterWidget()).andReturn(new SalUsageFilterWidget()).once();
        replay(controller);
        usagesWidget.init();
        verify(controller);
        reset(controller);
    }

    @Test
    public void testWidgetStructure() {
        assertTrue(usagesWidget.isLocked());
        assertEquals(200, usagesWidget.getSplitPosition(), 0);
        verifySize(usagesWidget);
        assertTrue(usagesWidget.getFirstComponent() instanceof SalUsageFilterWidget);
        Component secondComponent = usagesWidget.getSecondComponent();
        assertTrue(secondComponent instanceof VerticalLayout);
        VerticalLayout layout = (VerticalLayout) secondComponent;
        verifySize(layout);
        assertEquals(2, layout.getComponentCount());
        verifyButtonsLayout((HorizontalLayout) layout.getComponent(0));
        verifyGrid((Grid) layout.getComponent(1));
        assertEquals(1, layout.getExpandRatio(layout.getComponent(1)), 0);
    }

    @Test
    public void testGetController() {
        assertSame(controller, usagesWidget.getController());
    }

    @Test
    public void testRefresh() {
        DataProvider dataProvider = createMock(DataProvider.class);
        SalUsageMediator mediator = createMock(SalUsageMediator.class);
        Whitebox.setInternalState(usagesWidget, dataProvider);
        Whitebox.setInternalState(usagesWidget, mediator);
        dataProvider.refreshAll();
        expectLastCall().once();
        replay(dataProvider, controller, mediator);
        usagesWidget.refresh();
        verify(dataProvider, controller, mediator);
    }

    @Test
    public void testInitMediator() throws Exception {
        SalUsageMediator mediator = createMock(SalUsageMediator.class);
        expectNew(SalUsageMediator.class).andReturn(mediator).once();
        replay(SalUsageMediator.class, mediator, controller);
        assertNotNull(usagesWidget.initMediator());
        verify(SalUsageMediator.class, mediator, controller);
    }

    private void verifyButtonsLayout(HorizontalLayout layout) {
        assertTrue(layout.isSpacing());
        assertEquals(new MarginInfo(true), layout.getMargin());
        assertEquals(0, layout.getComponentCount());
    }

    private void verifyGrid(Grid grid) {
        verifySize(grid);
    }

    private void verifySize(Component component) {
        assertEquals(100, component.getWidth(), 0);
        assertEquals(100, component.getHeight(), 0);
        assertEquals(Unit.PERCENTAGE, component.getHeightUnits());
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
    }
}
