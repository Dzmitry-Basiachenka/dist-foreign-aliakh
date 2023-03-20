package com.copyright.rup.dist.foreign.ui.audit.impl.nts;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGridItems;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.newCapture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.ui.audit.api.ICommonAuditFilterController;
import com.copyright.rup.dist.foreign.ui.audit.api.ICommonAuditFilterWidget;
import com.copyright.rup.dist.foreign.ui.audit.api.nts.INtsAuditController;
import com.copyright.rup.dist.foreign.ui.audit.api.nts.INtsAuditFilterController;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vaadin.data.provider.CallbackDataProvider;
import com.vaadin.data.provider.Query;
import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.IOException;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Verifies {@link NtsAuditWidget}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/23/2018
 *
 * @author Aliaksandr Radkevich
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(JavaScript.class)
public class NtsAuditWidgetTest {

    private final List<UsageDto> usages = loadExpectedUsageDtos("usage_dto_0a9941d6.json");
    private NtsAuditWidget widget;
    private INtsAuditController controller;
    private ICommonAuditFilterWidget filterWidget;
    private ICommonAuditFilterController filterController;

    @Before
    public void setUp() {
        controller = createMock(INtsAuditController.class);
        widget = new NtsAuditWidget(controller);
        widget.setController(controller);
        filterController = createMock(INtsAuditFilterController.class);
        INtsAuditFilterController ntsAuditFilterController = createMock(INtsAuditFilterController.class);
        filterWidget = new NtsAuditFilterWidget(ntsAuditFilterController);
        filterWidget.setController(filterController);
    }

    @Test
    public void testGridValues() {
        initWidget();
        mockStatic(JavaScript.class);
        expect(JavaScript.getCurrent()).andReturn(createMock(JavaScript.class)).times(2);
        expect(controller.loadBeans(0, Integer.MAX_VALUE, List.of())).andReturn(usages).once();
        expect(controller.getSize()).andReturn(1).once();
        replay(JavaScript.class, controller);
        Grid<?> grid = (Grid<?>) ((VerticalLayout) widget.getSecondComponent()).getComponent(1);
        Object[][] expectedCells = {
            {"0a9941d6-254d-457a-a667-498c87e5df5e", UsageStatusEnum.PAID, "NTS", "Paid batch", "02/12/2021",
                1000002859L, "John Wiley & Sons - Books", 1000000001L, "Rothchild Consultants", 243904752L,
                "100 ROAD MOVIES", "100 ROAD MOVIES", "1008902112317555XX", "VALISBN13", "3,000.00", "500.00", "16.0",
                "scenario name", "650738", "02/13/2022", "97423", "dist name", "01/02/2022", "01/03/2022",
                "some comment"}
        };
        verifyGridItems(grid, usages, expectedCells);
        verify(JavaScript.class, controller);
    }

    @Test
    public void testInit() {
        initWidget();
        assertEquals(200, widget.getSplitPosition(), 0);
        assertTrue(widget.isLocked());
        assertEquals("audit-widget", widget.getStyleName());
        Component component = widget.getFirstComponent();
        assertThat(component, instanceOf(ICommonAuditFilterWidget.class));
        component = widget.getSecondComponent();
        assertThat(component, instanceOf(VerticalLayout.class));
        verifyGridLayout((VerticalLayout) component);
    }

    @Test
    public void testRefresh() {
        initWidget();
        mockStatic(JavaScript.class);
        expect(JavaScript.getCurrent()).andReturn(createMock(JavaScript.class)).times(2);
        List<UsageDto> liabilities = List.of(new UsageDto());
        Capture<List<QuerySortOrder>> orderCapture = newCapture();
        expect(controller.getSize()).andReturn(1).once();
        expect(controller.loadBeans(eq(0), eq(1), capture(orderCapture))).andReturn(liabilities).once();
        replay(controller, JavaScript.class);
        widget.refresh();
        Grid<?> grid = widget.getAuditGrid();
        CallbackDataProvider<?, ?> dataProvider = (CallbackDataProvider) grid.getDataProvider();
        assertEquals(1, dataProvider.size(new Query<>()));
        Stream<?> stream = dataProvider.fetch(new Query<>(0, 1, List.of(), null, null));
        assertEquals(liabilities, stream.collect(Collectors.toList()));
        assertFalse(grid.getStyleName().contains("empty-audit-grid"));
        assertEquals(0, orderCapture.getValue().size());
        verify(controller, JavaScript.class);
    }

    @Test
    public void testRefreshEmptyGrid() {
        initWidget();
        mockStatic(JavaScript.class);
        expect(JavaScript.getCurrent()).andReturn(createMock(JavaScript.class)).once();
        expect(controller.getSize()).andReturn(0).once();
        replay(controller, JavaScript.class);
        widget.refresh();
        Grid<?> grid = widget.getAuditGrid();
        CallbackDataProvider<?, ?> dataProvider = (CallbackDataProvider) grid.getDataProvider();
        assertEquals(0, dataProvider.size(new Query<>()));
        assertTrue(grid.getStyleName().contains("empty-audit-grid"));
        verify(controller, JavaScript.class);
    }

    private void verifyGridLayout(VerticalLayout layout) {
        verifySize(layout, 100, Unit.PERCENTAGE, 100);
        assertTrue(layout.isSpacing());
        assertEquals(2, layout.getComponentCount());
        verifyToolbar(layout.getComponent(0));
        verifyGrid(layout.getComponent(1));
    }

    private void verifyToolbar(Component component) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        HorizontalLayout layout = (HorizontalLayout) component;
        verifySize(layout, 100, Unit.PIXELS, -1);
        assertEquals(new MarginInfo(false, true, false, true), layout.getMargin());
        assertEquals(2, layout.getComponentCount());
        verifyExportButton(layout.getComponent(0));
        verifySearchWidget(layout.getComponent(1));
    }

    private void verifySearchWidget(Component component) {
        assertThat(component, instanceOf(SearchWidget.class));
        SearchWidget searchWidget = (SearchWidget) component;
        verifySize(searchWidget, 75, Unit.PIXELS, -1);
        assertEquals("Enter Detail ID or Wr Wrk Inst or System Title or Work Title",
            Whitebox.getInternalState(searchWidget, TextField.class).getPlaceholder());
    }

    private void verifyExportButton(Component component) {
        assertThat(component, instanceOf(Button.class));
        Button button = (Button) component;
        assertEquals("Export", button.getCaption());
        assertEquals(1, button.getExtensions().size());
    }

    private void verifyGrid(Component component) {
        assertThat(component, instanceOf(Grid.class));
        Grid grid = (Grid) component;
        verifySize(grid, 100, Unit.PERCENTAGE, 100);
        List<Column> columns = grid.getColumns();
        assertEquals(List.of("Detail ID", "Detail Status", "Product Family", "Usage Batch Name", "Payment Date",
            "RH Account #", "RH Name", "Payee Account #", "Payee Name", "Wr Wrk Inst", "System Title", "Title",
            "Standard Number", "Standard Number Type", "Reported Value", "Gross Amt in USD", "Service Fee %",
            "Scenario Name", "Check #", "Check Date", "Event ID", "Dist. Name", "Dist. Date", "Period Ending",
            "Comment"),
            columns.stream().map(Column::getCaption).collect(Collectors.toList()));
    }

    private void verifySize(Component component, float width, Unit heightUnit, float height) {
        assertEquals(width, component.getWidth(), 0);
        assertEquals(height, component.getHeight(), 0);
        assertEquals(heightUnit, component.getHeightUnits());
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
    }

    private void initWidget() {
        IStreamSource streamSource = createMock(IStreamSource.class);
        expect(streamSource.getSource()).andReturn(new SimpleImmutableEntry(createMock(Supplier.class),
            createMock(Supplier.class))).once();
        expect(controller.getCsvStreamSource()).andReturn(streamSource).once();
        expect(controller.getAuditFilterController()).andReturn(filterController).once();
        expect(filterController.initWidget()).andReturn(filterWidget).once();
        replay(controller, filterController, streamSource);
        widget.init();
        verify(controller, filterController, streamSource);
        reset(controller, filterController, streamSource);
    }

    private List<UsageDto> loadExpectedUsageDtos(String fileName) {
        try {
            String content = TestUtils.fileToString(this.getClass(), fileName);
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
            return mapper.readValue(content, new TypeReference<List<UsageDto>>() {
            });
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
