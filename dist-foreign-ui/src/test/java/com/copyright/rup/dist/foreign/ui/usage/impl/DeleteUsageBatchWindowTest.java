package com.copyright.rup.dist.foreign.ui.usage.impl;

import static junit.framework.Assert.assertNotNull;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesController;
import com.copyright.rup.dist.foreign.ui.usage.impl.DeleteUsageBatchWindow.SearchController;
import com.copyright.rup.vaadin.ui.component.window.ConfirmDialogWindow.IListener;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.lang3.StringUtils;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Verifies {@link DeleteUsageBatchWindow}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 2/17/17
 *
 * @author Aliaksandr Radkevich
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Windows.class)
public class DeleteUsageBatchWindowTest {

    private static final String USAGE_BATCH_ID = RupPersistUtils.generateUuid();
    private static final String FAS_PRODUCT_FAMILY = "FAS";
    private UsageBatch usageBatch;
    private DeleteUsageBatchWindow deleteWindow;
    private IUsagesController controller;

    @Before
    public void setUp() {
        controller = createMock(IUsagesController.class);
        usageBatch = new UsageBatch();
        usageBatch.setId(USAGE_BATCH_ID);
        usageBatch.setName("Batch Name");
        usageBatch.setProductFamily(FAS_PRODUCT_FAMILY);
        usageBatch.setPaymentDate(LocalDate.now());
        expect(controller.getSelectedProductFamily()).andReturn(FAS_PRODUCT_FAMILY).once();
        expect(controller.getUsageBatches(FAS_PRODUCT_FAMILY)).andReturn(Collections.singletonList(usageBatch)).once();
        replay(controller);
        deleteWindow = new DeleteUsageBatchWindow(controller);
        verify(controller);
        reset(controller);
    }

    @Test
    public void testComponentStructure() {
        assertEquals("Delete Usage Batch", deleteWindow.getCaption());
        verifySize(deleteWindow, 700, Unit.PIXELS, 450, Unit.PIXELS);
        VerticalLayout content = (VerticalLayout) deleteWindow.getContent();
        assertEquals(new MarginInfo(true), content.getMargin());
        assertTrue(content.isSpacing());
        verifySize(content, 100, Unit.PERCENTAGE, 100, Unit.PERCENTAGE);
        assertEquals(3, content.getComponentCount());
        assertEquals(SearchWidget.class, content.getComponent(0).getClass());
        Component component = content.getComponent(1);
        assertEquals(Grid.class, component.getClass());
        verifyGrid((Grid) component);
        assertEquals(1, content.getExpandRatio(component), 0);
        component = content.getComponent(2);
        assertEquals(Button.class, component.getClass());
        assertEquals("Close", component.getCaption());
        assertEquals(Alignment.MIDDLE_RIGHT, content.getComponentAlignment(component));
    }

    @Test
    public void testDeleteClickListenerAssociatedFunds() {
        mockStatic(Windows.class);
        Window confirmWindowCapture = createMock(Window.class);
        VerticalLayout content = (VerticalLayout) deleteWindow.getContent();
        Grid grid = (Grid) content.getComponent(1);
        Button button = (Button) grid.getColumn("delete").getValueProvider().apply(usageBatch);
        assertEquals("Delete", button.getCaption());
        Collection<?> listeners = button.getListeners(ClickEvent.class);
        assertEquals(1, listeners.size());
        ClickListener listener = (ClickListener) listeners.iterator().next();
        expect(controller.getAdditionalFundNamesByUsageBatchId(USAGE_BATCH_ID))
            .andReturn(Arrays.asList("Fund 1", "Fund 2")).once();
        Windows.showNotificationWindow("Usage batch cannot be deleted because it is associated with the following " +
            "additional funds:<ul><li>Fund 1</li><li>Fund 2</li></ul>");
        expectLastCall().once();
        replay(controller, confirmWindowCapture, Windows.class);
        listener.buttonClick(null);
        verify(controller, confirmWindowCapture, Windows.class);
    }

    @Test
    public void testDeleteClickListenerEmptyAssociatedScenarios() {
        mockStatic(Windows.class);
        Capture<IListener> listenerCapture = new Capture<>();
        Window confirmWindowCapture = createMock(Window.class);
        VerticalLayout content = (VerticalLayout) deleteWindow.getContent();
        Grid grid = (Grid) content.getComponent(1);
        Button button = (Button) grid.getColumn("delete").getValueProvider().apply(usageBatch);
        assertEquals("Delete", button.getCaption());
        Collection<?> listeners = button.getListeners(ClickEvent.class);
        assertEquals(1, listeners.size());
        ClickListener listener = (ClickListener) listeners.iterator().next();
        expect(controller.getAdditionalFundNamesByUsageBatchId(USAGE_BATCH_ID))
            .andReturn(Collections.emptyList()).once();
        expect(controller.getScenariosNamesAssociatedWithUsageBatch(USAGE_BATCH_ID))
            .andReturn(Collections.emptyList()).once();
        expect(Windows.showConfirmDialog(eq("Are you sure you want to delete <i><b>'Batch Name'</b></i> usage batch?"),
            capture(listenerCapture))).andReturn(confirmWindowCapture).once();
        replay(controller, confirmWindowCapture, Windows.class);
        listener.buttonClick(null);
        verify(controller, confirmWindowCapture, Windows.class);
    }

    @Test
    public void testDeleteClickListenerWithAssociatedScenarios() {
        mockStatic(Windows.class);
        Window confirmWindowCapture = createMock(Window.class);
        VerticalLayout content = (VerticalLayout) deleteWindow.getContent();
        Grid grid = (Grid) content.getComponent(1);
        Button button = (Button) grid.getColumn("delete").getValueProvider().apply(usageBatch);
        assertEquals("Delete", button.getCaption());
        Collection<?> listeners = button.getListeners(ClickEvent.class);
        assertEquals(1, listeners.size());
        ClickListener listener = (ClickListener) listeners.iterator().next();
        expect(controller.getAdditionalFundNamesByUsageBatchId(USAGE_BATCH_ID))
            .andReturn(Collections.emptyList()).once();
        expect(controller.getScenariosNamesAssociatedWithUsageBatch(USAGE_BATCH_ID))
            .andReturn(Arrays.asList("Scenario 1", "Scenario 2")).once();
        Windows.showNotificationWindow("Usage batch cannot be deleted because it is associated with the following " +
            "scenarios:<ul><li>Scenario 1</li><li>Scenario 2</li></ul>");
        expectLastCall().once();
        replay(controller, confirmWindowCapture, Windows.class);
        listener.buttonClick(null);
        verify(controller, confirmWindowCapture, Windows.class);
    }

    @Test
    public void testSearchController() {
        SearchController searchController = deleteWindow.new SearchController();
        SearchWidget searchWidget = Whitebox.getInternalState(deleteWindow, "searchWidget");
        searchWidget.setSearchValue("value");
        Grid grid = Whitebox.getInternalState(deleteWindow, "grid");
        ListDataProvider dataProvider = (ListDataProvider) grid.getDataProvider();
        assertNull(dataProvider.getFilter());
        searchController.performSearch();
        assertNotNull(dataProvider.getFilter());
    }

    private void verifySize(Component component, float width, Unit widthUnit, float height, Unit heightUnit) {
        assertEquals(width, component.getWidth(), 0);
        assertEquals(height, component.getHeight(), 0);
        assertEquals(heightUnit, component.getHeightUnits());
        assertEquals(widthUnit, component.getWidthUnits());
    }

    @SuppressWarnings("unchecked")
    private void verifyGrid(Grid grid) {
        assertNull(grid.getCaption());
        verifySize(grid, 100, Unit.PERCENTAGE, 100, Unit.PERCENTAGE);
        List<Column> columns = grid.getColumns();
        assertEquals(Arrays.asList("Usage Batch Name", "Payment Date", "Fiscal Year", StringUtils.EMPTY),
            columns.stream().map(Column::getCaption).collect(Collectors.toList()));
        assertEquals(115, columns.get(1).getWidth(), 0);
        assertEquals(110, columns.get(2).getWidth(), 0);
        assertEquals(90, columns.get(3).getWidth(), 0);
    }
}
