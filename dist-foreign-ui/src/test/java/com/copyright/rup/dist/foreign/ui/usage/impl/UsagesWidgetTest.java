package com.copyright.rup.dist.foreign.ui.usage.impl;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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

import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesController;
import com.copyright.rup.vaadin.ui.LocalDateColumnGenerator;
import com.copyright.rup.vaadin.ui.LongColumnGenerator;
import com.copyright.rup.vaadin.ui.MoneyColumnGenerator;
import com.copyright.rup.vaadin.ui.Windows;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.vaadin.ui.component.lazytable.IBeanLoader;
import com.copyright.rup.vaadin.ui.component.lazytable.LazyTable;

import com.vaadin.server.Extension;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.vaadin.addons.lazyquerycontainer.LazyQueryContainer;
import org.vaadin.addons.lazyquerycontainer.QueryView;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link UsagesWidget}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 1/18/17
 *
 * @author Aliaksandr Radkevich
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({UsagesWidget.class, Windows.class})
public class UsagesWidgetTest {

    private static final String DETAIL_ID_PROPERTY = "detailId";
    private static final String GROSS_AMOUNT_PROPERTY = "grossAmount";
    private static final String REPORTED_VALUE_PROPERTY = "reportedValue";
    private UsagesWidget usagesWidget;
    private IUsagesController controller;

    @Before
    public void setUp() {
        controller = createMock(IUsagesController.class);
        UsagesFilterWidget filterWidget = new UsagesFilterWidget();
        usagesWidget = new UsagesWidget();
        usagesWidget.setController(controller);
        expect(controller.initUsagesFilterWidget()).andReturn(filterWidget).once();
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
        assertTrue(usagesWidget.getFirstComponent() instanceof UsagesFilterWidget);
        Component secondComponent = usagesWidget.getSecondComponent();
        assertTrue(secondComponent instanceof VerticalLayout);
        VerticalLayout layout = (VerticalLayout) secondComponent;
        verifySize(layout);
        assertEquals(2, layout.getComponentCount());
        verifyButtonsLayout((HorizontalLayout) layout.getComponent(0));
        verifyTable((Table) layout.getComponent(1));
        assertEquals(1, layout.getExpandRatio(layout.getComponent(1)), 0);
    }

    @Test
    public void testGetController() {
        assertSame(controller, usagesWidget.getController());
    }

    @Test
    public void testLoadButtonClickListener() {
        mockStatic(Windows.class);
        IPrmIntegrationService prmIntegrationService = createMock(IPrmIntegrationService.class);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        Button loadButton = (Button) ((HorizontalLayout) ((VerticalLayout) usagesWidget.getSecondComponent())
            .getComponent(0)).getComponent(0);
        assertTrue(loadButton.isDisableOnClick());
        Windows.showModalWindow(anyObject(UsageBatchUploadWindow.class));
        expectLastCall().once();
        replay(clickEvent, Windows.class, controller, prmIntegrationService);
        Collection<?> listeners = loadButton.getListeners(ClickEvent.class);
        assertEquals(2, listeners.size());
        ClickListener clickListener = (ClickListener) listeners.iterator().next();
        clickListener.buttonClick(clickEvent);
        verify(clickEvent, Windows.class, controller, prmIntegrationService);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testAddToScenarioButtonEmptyUsagesTableClickListener() {
        mockStatic(Windows.class);
        LazyTable<UsageBeanQuery, UsageDto> usagesTable = new LazyTable<>(controller, UsageBeanQuery.class, 1);
        Whitebox.setInternalState(usagesWidget, "usagesTable", usagesTable);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        Button addToScenarioButton = (Button) ((HorizontalLayout) ((VerticalLayout) usagesWidget.getSecondComponent())
            .getComponent(0)).getComponent(1);
        assertTrue(addToScenarioButton.isDisableOnClick());
        Windows.showNotificationWindow("Please select at least one usage");
        expectLastCall().once();
        replay(clickEvent, Windows.class);
        Collection<?> listeners = addToScenarioButton.getListeners(ClickEvent.class);
        assertEquals(2, listeners.size());
        ClickListener clickListener = (ClickListener) listeners.iterator().next();
        clickListener.buttonClick(clickEvent);
        verify(clickEvent, Windows.class);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testAddToScenarioButtonTableClickListener() {
        mockStatic(Windows.class);
        LazyTable<UsageBeanQuery, UsageDto> usagesTable =
            new LazyTable<>(new UsageDtoBeanLoader(), UsageBeanQuery.class, 1);
        Whitebox.setInternalState(usagesWidget, "usagesTable", usagesTable);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        Button addToScenarioButton = (Button) ((HorizontalLayout) ((VerticalLayout) usagesWidget.getSecondComponent())
            .getComponent(0)).getComponent(1);
        assertTrue(addToScenarioButton.isDisableOnClick());
        Windows.showModalWindow(anyObject(CreateScenarioWindow.class));
        expectLastCall().once();
        replay(clickEvent, Windows.class);
        Collection<?> listeners = addToScenarioButton.getListeners(ClickEvent.class);
        assertEquals(2, listeners.size());
        ClickListener clickListener = (ClickListener) listeners.iterator().next();
        clickListener.buttonClick(clickEvent);
        verify(clickEvent, Windows.class);
    }

    @Test
    public void testDeleteButtonClickListener() {
        mockStatic(Windows.class);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        Button deleteButton = (Button) ((HorizontalLayout) ((VerticalLayout) usagesWidget.getSecondComponent())
            .getComponent(0)).getComponent(3);
        assertTrue(deleteButton.isDisableOnClick());
        expect(controller.getUsageBatches()).andReturn(Collections.EMPTY_LIST).once();
        Windows.showModalWindow(anyObject(DeleteUsageBatchWindow.class));
        expectLastCall().once();
        replay(clickEvent, Windows.class, controller);
        Collection<?> listeners = deleteButton.getListeners(ClickEvent.class);
        assertEquals(2, listeners.size());
        ClickListener clickListener = (ClickListener) listeners.iterator().next();
        clickListener.buttonClick(clickEvent);
        verify(clickEvent, Windows.class, controller);
    }

    @Test
    public void testRefresh() {
        LazyTable usagesTableMock = createMock(LazyTable.class);
        LazyQueryContainer containerMock = createMock(LazyQueryContainer.class);
        QueryView queryView = createMock(QueryView.class);
        Whitebox.setInternalState(containerMock, "itemSetChangeListeners", Collections.emptyList());
        Whitebox.setInternalState(containerMock, "queryView", queryView);
        Whitebox.setInternalState(usagesWidget, "usagesTable", usagesTableMock);
        expect(usagesTableMock.getContainerDataSource()).andReturn(containerMock).once();
        containerMock.refresh();
        expectLastCall().once();
        expect(containerMock.getItemIds()).andReturn(Collections.emptyList()).once();
        usagesTableMock.addStyleName("empty-usages-table");
        expectLastCall().once();
        replay(usagesTableMock, containerMock, queryView);
        usagesWidget.refresh();
        verify(usagesTableMock, containerMock, queryView);
    }

    @Test
    public void testInitMediator() throws Exception {
        UsagesMediator mediator = createMock(UsagesMediator.class);
        expectNew(UsagesMediator.class).andReturn(mediator).once();
        mediator.setDeleteUsageButton(anyObject(Button.class));
        expectLastCall().once();
        mediator.setLoadUsageButton(anyObject(Button.class));
        expectLastCall().once();
        mediator.setAddToScenarioButton(anyObject(Button.class));
        expectLastCall().once();
        replay(UsagesMediator.class, mediator);
        assertNotNull(usagesWidget.initMediator());
        verify(UsagesMediator.class, mediator);
    }

    private void verifyButtonsLayout(HorizontalLayout layout) {
        assertTrue(layout.isSpacing());
        assertEquals(new MarginInfo(true), layout.getMargin());
        assertEquals(4, layout.getComponentCount());
        assertEquals("Load", layout.getComponent(0).getCaption());
        assertEquals("Add To Scenario", layout.getComponent(1).getCaption());
        Component component = layout.getComponent(2);
        assertEquals("Export", component.getCaption());
        Collection<Extension> extensions = component.getExtensions();
        assertTrue(CollectionUtils.isNotEmpty(extensions));
        assertEquals(1, extensions.size());
        assertTrue(extensions.iterator().next() instanceof OnDemandFileDownloader);
        component = layout.getComponent(3);
        assertEquals("Delete Usage Batch", component.getCaption());
    }

    private void verifyTable(Table table) {
        assertArrayEquals(new Object[]{DETAIL_ID_PROPERTY, "status", "batchName", "fiscalYear", "rroAccountNumber",
            "rroName", "paymentDate", "workTitle", "article", "standardNumber", "wrWrkInst", "rhAccountNumber",
            "rhName", "publisher", "publicationDate", "numberOfCopies", REPORTED_VALUE_PROPERTY, GROSS_AMOUNT_PROPERTY,
            "market", "marketPeriodFrom", "marketPeriodTo", "author"}, table.getVisibleColumns());
        assertArrayEquals(
            new Object[]{"Detail ID", "Detail Status", "Usage Batch Name", "Fiscal Year", "RRO Account #",
                "RRO Name", "Payment Date", "Title", "Article", "Standard Number", "Wr Wrk Inst", "RH Account #",
                "RH Name", "Publisher", "Pub Date", "Number of Copies", "Reported value", "Amt in USD", "Market",
                "Market Period From", "Market Period To", "Author"}, table.getColumnHeaders());
        Collection<?> containerPropertyIds = table.getContainerPropertyIds();

        assertTrue(containerPropertyIds.contains("id"));
        assertTrue(containerPropertyIds.contains(DETAIL_ID_PROPERTY));
        assertTrue(containerPropertyIds.contains("batchName"));
        assertTrue(containerPropertyIds.contains("fiscalYear"));
        assertTrue(containerPropertyIds.contains("rroAccountNumber"));
        assertTrue(containerPropertyIds.contains("rroName"));
        assertTrue(containerPropertyIds.contains("paymentDate"));
        assertTrue(containerPropertyIds.contains("workTitle"));
        assertTrue(containerPropertyIds.contains("article"));
        assertTrue(containerPropertyIds.contains("standardNumber"));
        assertTrue(containerPropertyIds.contains("wrWrkInst"));
        assertTrue(containerPropertyIds.contains("rhAccountNumber"));
        assertTrue(containerPropertyIds.contains("rhName"));
        assertTrue(containerPropertyIds.contains("publisher"));
        assertTrue(containerPropertyIds.contains("publicationDate"));
        assertTrue(containerPropertyIds.contains("numberOfCopies"));
        assertTrue(containerPropertyIds.contains(REPORTED_VALUE_PROPERTY));
        assertTrue(containerPropertyIds.contains(GROSS_AMOUNT_PROPERTY));
        assertTrue(containerPropertyIds.contains("market"));
        assertTrue(containerPropertyIds.contains("marketPeriodFrom"));
        assertTrue(containerPropertyIds.contains("marketPeriodTo"));
        assertTrue(containerPropertyIds.contains("author"));
        assertTrue(containerPropertyIds.contains("status"));
        assertTrue(table.isColumnCollapsingAllowed());
        assertFalse(table.isColumnCollapsible(DETAIL_ID_PROPERTY));

        assertEquals(135, table.getColumnWidth("rroName"));
        assertEquals(135, table.getColumnWidth("article"));
        assertEquals(135, table.getColumnWidth("publisher"));
        assertEquals(135, table.getColumnWidth("market"));
        assertEquals(135, table.getColumnWidth("batchName"));
        assertEquals(300, table.getColumnWidth("workTitle"));
        assertEquals(300, table.getColumnWidth("rhName"));
        assertEquals(300, table.getColumnWidth("author"));
        assertEquals(95, table.getColumnWidth(REPORTED_VALUE_PROPERTY));
        assertEquals(95, table.getColumnWidth(GROSS_AMOUNT_PROPERTY));

        verifyGeneratedColumns(table);
        verifySize(table);
    }

    private void verifyGeneratedColumns(Table table) {
        verifyColumnGenerator(table.getColumnGenerator(DETAIL_ID_PROPERTY), LongColumnGenerator.class);
        verifyColumnGenerator(table.getColumnGenerator("wrWrkInst"), LongColumnGenerator.class);
        verifyColumnGenerator(table.getColumnGenerator("rhAccountNumber"), LongColumnGenerator.class);
        verifyColumnGenerator(table.getColumnGenerator("rroAccountNumber"), LongColumnGenerator.class);
        verifyColumnGenerator(table.getColumnGenerator("publicationDate"), LocalDateColumnGenerator.class);
        verifyColumnGenerator(table.getColumnGenerator("paymentDate"), LocalDateColumnGenerator.class);
        verifyColumnGenerator(table.getColumnGenerator(REPORTED_VALUE_PROPERTY), MoneyColumnGenerator.class);
        verifyColumnGenerator(table.getColumnGenerator(GROSS_AMOUNT_PROPERTY), MoneyColumnGenerator.class);
    }

    private void verifyColumnGenerator(Table.ColumnGenerator columnGenerator, Class clazz) {
        assertNotNull(columnGenerator);
        assertEquals(clazz, columnGenerator.getClass());
    }

    private void verifySize(Component component) {
        assertEquals(100, component.getWidth(), 0);
        assertEquals(100, component.getHeight(), 0);
        assertEquals(Unit.PERCENTAGE, component.getHeightUnits());
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
    }

    private static class UsageDtoBeanLoader implements IBeanLoader<UsageDto> {

        @Override
        public int getSize() {
            return 1;
        }

        @Override
        public List<UsageDto> loadBeans(int startIndex, int count, Object[] sortPropertyIds, boolean... sortStates) {
            return Collections.singletonList(new UsageDto());
        }
    }
}
