package com.copyright.rup.dist.foreign.vui.audit.impl.aacl;

import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyFileDownloader;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyGridItems;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifySearchWidget;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.newCapture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.vui.audit.api.aacl.IAaclAuditController;
import com.copyright.rup.dist.foreign.vui.audit.api.nts.INtsAuditFilterController;
import com.copyright.rup.dist.foreign.vui.audit.impl.nts.NtsAuditFilterWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.QuerySortOrder;

import org.apache.commons.lang3.tuple.Pair;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Verifies {@link AaclAuditWidget}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 03/12/2020
 *
 * @author Anton Azarenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class, UI.class})
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
public class AaclAuditWidgetTest {

    private static final String WIDTH_300 = "300px";

    private AaclAuditWidget widget;
    private IAaclAuditController controller;

    @Before
    public void setUp() {
        mockStatic(UI.class);
        UI ui = createMock(UI.class);
        expect(UI.getCurrent()).andReturn(ui).once();
        expect(ui.getUIId()).andReturn(1).once();
        controller = createMock(IAaclAuditController.class);
        widget = new AaclAuditWidget(controller);
        widget.setController(controller);
        INtsAuditFilterController filterController = createMock(INtsAuditFilterController.class);
        var filterWidget = new NtsAuditFilterWidget(filterController);
        filterWidget.setController(filterController);
        expect(controller.getAuditFilterController()).andReturn(filterController).once();
        expect(filterController.initWidget()).andReturn(filterWidget).once();
        IStreamSource streamSource = createMock(IStreamSource.class);
        Map.Entry<Supplier<String>, Supplier<InputStream>> source =
            new SimpleImmutableEntry<>(() -> "file_name.txt", () -> new ByteArrayInputStream(new byte[]{}));
        expect(streamSource.getSource()).andReturn(source).once();
        expect(controller.getCsvStreamSource()).andReturn(streamSource).once();
        replay(UI.class, ui, controller, filterController, streamSource);
        widget.init();
        verify(UI.class, ui, controller, filterController, streamSource);
        reset(controller, filterController, streamSource);
    }

    @Test
    public void testWidgetStructure() {
        assertThat(widget.getPrimaryComponent(), instanceOf(NtsAuditFilterWidget.class));
        assertEquals("audit-widget", widget.getClassName());
        var component = widget.getPrimaryComponent();
        assertThat(component, instanceOf(VerticalLayout.class));
        component = widget.getSecondaryComponent();
        verifyContent(component);
    }

    @Test
    public void testRefresh() {
        List<UsageDto> liabilities = List.of(new UsageDto());
        Capture<List<QuerySortOrder>> orderCapture = newCapture();
        expect(controller.getSize()).andReturn(1).once();
        expect(controller.loadBeans(eq(0), eq(1), capture(orderCapture))).andReturn(liabilities).once();
        replay(controller);
        widget.refresh();
        Grid<?> grid = widget.getAuditGrid();
        CallbackDataProvider<?, ?> dataProvider = (CallbackDataProvider) grid.getDataProvider();
        assertEquals(1, dataProvider.size(new Query<>()));
        Stream<?> stream = dataProvider.fetch(new Query<>(0, 1, List.of(), null, null));
        assertEquals(liabilities, stream.collect(Collectors.toList()));
        assertEquals("audit-grid", grid.getClassName());
        assertEquals(0, orderCapture.getValue().size());
        verify(controller);
    }

    @Test
    public void testRefreshEmptyGrid() {
        expect(controller.getSize()).andReturn(0).once();
        replay(controller);
        widget.refresh();
        Grid<?> grid = widget.getAuditGrid();
        CallbackDataProvider<?, ?> dataProvider = (CallbackDataProvider) grid.getDataProvider();
        assertEquals(0, dataProvider.size(new Query<>()));
        assertTrue(grid.getClassNames().contains("empty-audit-grid"));
        verify(controller);
    }

    @Test
    public void testGridValues() {
        List<UsageDto> usages = loadExpectedUsageDtos("usage_dto_23b192ff.json");
        expect(controller.loadBeans(0, Integer.MAX_VALUE, List.of())).andReturn(usages).once();
        expect(controller.getSize()).andReturn(1).once();
        replay(controller);
        Grid<?> grid = (Grid<?>) ((VerticalLayout) widget.getSecondaryComponent()).getComponentAt(1);
        Object[][] expectedCells = {
            {"23b192ff-4d4d-4107-a113-46a3791dd5a5", "0a0485b1-6ab1-47a2-8bbb-1dee3f531f20", "PAID", "AACL",
                "Paid batch", "04/10/2022", "1000002859", "John Wiley & Sons - Books", "1000000001",
                "Rothchild Consultants", "243904752", "100 ROAD MOVIES", "1008902112317555XX", "VALISBN13", "500.00",
                "16.0", "420.00", "scenario name", "650738", "02/13/2022", "97423", "dist name", "01/02/2022", "143",
                "EXGP", "Engineering", "BK2", "2016", "google.com", "some comment"}
        };
        verifyGridItems(grid, usages, expectedCells);
        verify(controller);
    }

    private void verifyContent(Component component) {
        assertThat(component, instanceOf(VerticalLayout.class));
        var content = (VerticalLayout) component;
        assertEquals(2, content.getComponentCount());
        verifyToolbar(content.getComponentAt(0));
        verifyGrid((Grid) content.getComponentAt(1), List.of(
            Pair.of("Detail ID", WIDTH_300),
            Pair.of("Baseline ID", WIDTH_300),
            Pair.of("Detail Status", "180px"),
            Pair.of("Product Family", "160px"),
            Pair.of("Usage Batch Name", "200px"),
            Pair.of("Period End Date", "155px"),
            Pair.of("RH Account #", "150px"),
            Pair.of("RH Name", WIDTH_300),
            Pair.of("Payee Account #", "165px"),
            Pair.of("Payee Name", WIDTH_300),
            Pair.of("Wr Wrk Inst", "140px"),
            Pair.of("System Title", WIDTH_300),
            Pair.of("Standard Number", "180px"),
            Pair.of("Standard Number Type", "225px"),
            Pair.of("Gross Amt in USD", "170px"),
            Pair.of("Service Fee %", "145px"),
            Pair.of("Net Amt in USD", "150px"),
            Pair.of("Scenario Name", "155px"),
            Pair.of("Check #", "100px"),
            Pair.of("Check Date", "125px"),
            Pair.of("Event ID", "100px"),
            Pair.of("Dist. Name", "115px"),
            Pair.of("Dist. Date", "110px"),
            Pair.of("Det LC ID", "105px"),
            Pair.of("Det LC Enrollment", "180px"),
            Pair.of("Det LC Discipline", "155px"),
            Pair.of("Pub Type", "140px"),
            Pair.of("Usage Period", "135px"),
            Pair.of("Usage Source", "140px"),
            Pair.of("Comment", "200px")
        ));
    }

    private void verifyToolbar(Component component) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        var horizontalLayout = (HorizontalLayout) component;
        assertEquals(3, horizontalLayout.getComponentCount());
        assertTrue(horizontalLayout.isSpacing());
        assertEquals(JustifyContentMode.BETWEEN, horizontalLayout.getJustifyContentMode());
        assertEquals("100%", horizontalLayout.getWidth());
        assertEquals(Unit.PERCENTAGE, horizontalLayout.getWidthUnit().orElseThrow());
        assertEquals("audit-toolbar", horizontalLayout.getId().orElseThrow());
        assertEquals("audit-toolbar", horizontalLayout.getClassName());
        verifyFileDownloader(horizontalLayout.getComponentAt(0), "Export", true, true);
        verifySearchWidget(
            horizontalLayout.getComponentAt(1), "Enter Detail ID or Wr Wrk Inst or System Title", "70%");
        verifyMenuButton(horizontalLayout.getComponentAt(2));
    }

    private void verifyMenuButton(Component component) {
        assertThat(component, instanceOf(Button.class));
        var menuButton = (Button) component;
        assertNotNull(menuButton);
        assertEquals("Hide/Unhide", menuButton.getTooltip().getText());
    }

    private List<UsageDto> loadExpectedUsageDtos(String fileName) {
        try {
            var content = TestUtils.fileToString(this.getClass(), fileName);
            var mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
            return mapper.readValue(content, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
