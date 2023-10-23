package com.copyright.rup.dist.foreign.ui.usage.impl.aclci;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButton;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyMenuBar;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.expectNew;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.ui.usage.api.aclci.IAclciUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.aclci.IAclciUsageFilterController;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.tuple.Triple;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

/**
 * Verifies {@link AclciUsageWidget}.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 11/21/2022
 *
 * @author Aliaksanr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({AclciUsageWidget.class, Windows.class})
public class AclciUsageWidgetTest {

    private AclciUsageWidget widget;
    private IAclciUsageController controller;

    @Before
    public void setUp() {
        controller = createMock(IAclciUsageController.class);
        expect(controller.initUsagesFilterWidget()).andReturn(new AclciUsageFilterWidget(
            createMock(IAclciUsageFilterController.class))).once();
        IStreamSource streamSource = createMock(IStreamSource.class);
        expect(streamSource.getSource()).andReturn(new SimpleImmutableEntry(createMock(Supplier.class),
            createMock(Supplier.class))).once();
        expect(controller.getExportUsagesStreamSource()).andReturn(streamSource).once();
        widget = new AclciUsageWidget(controller);
        widget.setController(controller);
        replay(controller, streamSource);
        widget.init();
        verify(controller, streamSource);
        reset(controller, streamSource);
    }

    @Test
    public void testWidgetStructure() {
        assertTrue(widget.isLocked());
        assertEquals(200, widget.getSplitPosition(), 0);
        verifyWindow(widget, null, 100, 100, Sizeable.Unit.PERCENTAGE);
        assertThat(widget.getFirstComponent(), instanceOf(AclciUsageFilterWidget.class));
        Component secondComponent = widget.getSecondComponent();
        assertThat(secondComponent, instanceOf(VerticalLayout.class));
        VerticalLayout layout = (VerticalLayout) secondComponent;
        verifyWindow(layout, null, 100, 100, Sizeable.Unit.PERCENTAGE);
        assertEquals(2, layout.getComponentCount());
        verifyButtonsLayout((HorizontalLayout) layout.getComponent(0));
        Grid grid = (Grid) layout.getComponent(1);
        verifyGrid(grid, List.of(
            Triple.of("Detail ID", 200.0, -1),
            Triple.of("Detail Status", 165.0, -1),
            Triple.of("License Type", 150.0, -1),
            Triple.of("Product Family", 125.0, -1),
            Triple.of("Usage Batch Name", 200.0, -1),
            Triple.of("Period End Date", 115.0, -1),
            Triple.of("Coverage Period", 130.0, -1),
            Triple.of("Licensee Account #", 150.0, -1),
            Triple.of("Licensee Name", 300.0, -1),
            Triple.of("RH Account #", 115.0, -1),
            Triple.of("RH Name", 300.0, -1),
            Triple.of("Wr Wrk Inst", 110.0, -1),
            Triple.of("System Title", 300.0, -1),
            Triple.of("Standard Number", 210.0, -1),
            Triple.of("Standard Number Type", 155.0, -1),
            Triple.of("Reported Title", 300.0, -1),
            Triple.of("Reported Media Type", 150.0, -1),
            Triple.of("Media Type Weight", 130.0, -1),
            Triple.of("Reported Article or Chapter Title", 240.0, -1),
            Triple.of("Reported Standard Number or Image ID Number", 315.0, -1),
            Triple.of("Reported Author", 150.0, -1),
            Triple.of("Reported Publisher", 150.0, -1),
            Triple.of("Reported Publication Date", 200.0, -1),
            Triple.of("Reported Grade", 120.0, -1),
            Triple.of("Grade Group", 115.0, -1),
            Triple.of("Reported Number of Students", 210.0, -1),
            Triple.of("Comment", 115.0, -1)));
        assertEquals(1, layout.getExpandRatio(grid), 0);
    }

    @Test
    public void testGetController() {
        assertSame(controller, widget.getController());
    }

    @Test
    public void testRefresh() {
        DataProvider dataProvider = createMock(DataProvider.class);
        Grid usagesGrid = new Grid(dataProvider);
        AclciUsageMediator mediator = createMock(AclciUsageMediator.class);
        Whitebox.setInternalState(widget, usagesGrid);
        Whitebox.setInternalState(widget, mediator);
        dataProvider.refreshAll();
        expectLastCall().once();
        replay(dataProvider, controller, mediator);
        widget.refresh();
        verify(dataProvider, controller, mediator);
    }

    @Test
    public void testInitMediator() throws Exception {
        AclciUsageMediator mediator = createMock(AclciUsageMediator.class);
        expectNew(AclciUsageMediator.class).andReturn(mediator).once();
        mediator.setLoadUsageBatchMenuItem(anyObject(MenuBar.MenuItem.class));
        expectLastCall().once();
        mediator.setLoadFundPoolMenuItem(anyObject(MenuBar.MenuItem.class));
        expectLastCall().once();
        mediator.setUpdateUsagesButton(anyObject(Button.class));
        expectLastCall().once();
        replay(AclciUsageMediator.class, mediator, controller);
        assertNotNull(widget.initMediator());
        verify(AclciUsageMediator.class, mediator, controller);
    }

    @Test
    public void testUpdateUsagesButtonClickListener() {
        mockStatic(Windows.class);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        expect(controller.isValidStatusFilterApplied()).andReturn(true).once();
        expect(controller.getBeansCount()).andReturn(1).once();
        expect(controller.getUsageDtosToUpdate()).andReturn(List.of(new UsageDto())).once();
        Windows.showModalWindow(anyObject(AclciUsageUpdateWindow.class));
        expectLastCall().once();
        replay(controller, clickEvent, Windows.class);
        Button updateUsagesButton =
            (Button) ((HorizontalLayout) ((VerticalLayout) widget.getSecondComponent())
                .getComponent(0)).getComponent(2);
        Collection<?> listeners = updateUsagesButton.getListeners(ClickEvent.class);
        assertEquals(2, listeners.size());
        ClickListener clickListener = (ClickListener) listeners.iterator().next();
        clickListener.buttonClick(clickEvent);
        verify(controller, clickEvent, Windows.class);
    }

    @Test
    public void testUpdateUsagesButtonClickListenerNoUsages() {
        mockStatic(Windows.class);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        expect(controller.isValidStatusFilterApplied()).andReturn(true).once();
        expect(controller.getBeansCount()).andReturn(0).once();
        Windows.showNotificationWindow("There are no usages to update");
        expectLastCall().once();
        replay(controller, clickEvent, Windows.class);
        Button updateUsagesButton =
            (Button) ((HorizontalLayout) ((VerticalLayout) widget.getSecondComponent())
                .getComponent(0)).getComponent(2);
        Collection<?> listeners = updateUsagesButton.getListeners(ClickEvent.class);
        assertEquals(2, listeners.size());
        ClickListener clickListener = (ClickListener) listeners.iterator().next();
        clickListener.buttonClick(clickEvent);
        verify(controller, clickEvent, Windows.class);
    }

    @Test
    public void testUpdateUsagesButtonClickListenerStatusNotApplied() {
        mockStatic(Windows.class);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        expect(controller.isValidStatusFilterApplied()).andReturn(false).once();
        Windows.showNotificationWindow("RH_NOT_FOUND or WORK_NOT_GRANTED status filter should be applied");
        expectLastCall().once();
        replay(controller, clickEvent, Windows.class);
        Button updateUsagesButton =
            (Button) ((HorizontalLayout) ((VerticalLayout) widget.getSecondComponent())
                .getComponent(0)).getComponent(2);
        Collection<?> listeners = updateUsagesButton.getListeners(ClickEvent.class);
        assertEquals(2, listeners.size());
        ClickListener clickListener = (ClickListener) listeners.iterator().next();
        clickListener.buttonClick(clickEvent);
        verify(controller, clickEvent, Windows.class);
    }

    private void verifyButtonsLayout(HorizontalLayout layout) {
        assertEquals(4, layout.getComponentCount());
        verifyMenuBar(layout.getComponent(0), "Usage Batch", true, List.of("Load", "View"));
        verifyMenuBar(layout.getComponent(1), "Fund Pool", true, List.of("Load"));
        verifyButton(layout.getComponent(2), "Update Usages", true);
        verifyButton(layout.getComponent(3), "Export", true);
    }
}
