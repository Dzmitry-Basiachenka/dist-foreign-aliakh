package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.baseline.value;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButton;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyFooterItems;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGridItems;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.UdmValueBaselineDto;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineValueController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineValueFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineValueFilterWidget;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.tuple.Triple;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Date;
import java.util.List;
import java.util.function.Supplier;

/**
 * Verifies {@link UdmBaselineValueWidget}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/23/2021
 *
 * @author Anton Azarenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(JavaScript.class)
public class UdmBaselineValueWidgetTest {

    private UdmBaselineValueWidget udmBaselineValueWidget;
    private IUdmBaselineValueController controller;
    private IStreamSource streamSource;

    @Before
    public void setUp() {
        controller = createMock(IUdmBaselineValueController.class);
        streamSource = createMock(IStreamSource.class);
        IUdmBaselineValueFilterWidget filterWidget =
            new UdmBaselineValueFilterWidget(createMock(IUdmBaselineValueFilterController.class));
        expect(controller.initBaselineValuesFilterWidget()).andReturn(filterWidget).once();
        expect(controller.getExportBaselineValuesStreamSource()).andReturn(streamSource).once();
        expect(streamSource.getSource()).andReturn(new SimpleImmutableEntry(createMock(Supplier.class),
            createMock(Supplier.class))).once();
    }

    @Test
    public void testGridValues() {
        mockStatic(JavaScript.class);
        List<UdmValueBaselineDto> udmValueBaselines = List.of(buildUdmValueBaselineDto());
        expect(JavaScript.getCurrent()).andReturn(createMock(JavaScript.class)).times(2);
        expect(controller.getBeansCount()).andReturn(1).once();
        expect(controller.loadBeans(0, Integer.MAX_VALUE, List.of())).andReturn(udmValueBaselines).once();
        replay(JavaScript.class, controller, streamSource);
        initWidget();
        Grid grid = (Grid) ((VerticalLayout) udmBaselineValueWidget.getSecondComponent()).getComponent(1);
        DataProvider dataProvider = grid.getDataProvider();
        dataProvider.refreshAll();
        Object[][] expectedCells = {
            {"04fec507-881f-4c4f-b31a-85230821dad0", 202806, 122890651L, "Journal of medical education", "BK",
                "379.3984392886", "Y", "100.01", "Y", "16.5833333333", "Y", "some comment", "user@copyright.com",
                "09/01/2022"}
        };
        verifyGridItems(grid, udmValueBaselines, expectedCells);
        verify(JavaScript.class, controller, streamSource);
        Object[][] expectedFooterColumns = {{"valueId", "Values Count: 1", null}};
        verifyFooterItems(grid, expectedFooterColumns);
    }

    @Test
    public void testWidgetStructure() {
        replay(controller, streamSource);
        initWidget();
        verify(controller, streamSource);
        assertTrue(udmBaselineValueWidget.isLocked());
        assertEquals(270, udmBaselineValueWidget.getSplitPosition(), 0);
        verifyWindow(udmBaselineValueWidget, null, 100, 100, Unit.PERCENTAGE);
        assertThat(udmBaselineValueWidget.getFirstComponent(), instanceOf(VerticalLayout.class));
        Component secondComponent = udmBaselineValueWidget.getSecondComponent();
        assertThat(secondComponent, instanceOf(VerticalLayout.class));
        VerticalLayout layout = (VerticalLayout) secondComponent;
        verifyWindow(layout, null, 100, 100, Unit.PERCENTAGE);
        assertEquals(2, layout.getComponentCount());
        verifyButtonsLayout((HorizontalLayout) layout.getComponent(0));
        verifyGrid((Grid) layout.getComponent(1), List.of(
            Triple.of("Value ID", 200.0, -1),
            Triple.of("Value Period", 120.0, -1),
            Triple.of("Wr Wrk Inst", 120.0, -1),
            Triple.of("System Title", 300.0, -1),
            Triple.of("Pub Type", 200.0, -1),
            Triple.of("Price", 120.0, -1),
            Triple.of("Price Flag", 120.0, -1),
            Triple.of("Content", 100.0, -1),
            Triple.of("Content Flag", 100.0, -1),
            Triple.of("Content Unit Price", 200.0, -1),
            Triple.of("CUP Flag", 90.0, -1),
            Triple.of("Comment", 300.0, -1),
            Triple.of("Updated By", 150.0, -1),
            Triple.of("Updated Date", 110.0, -1)));
        assertEquals(1, layout.getExpandRatio(layout.getComponent(1)), 0);
    }

    private void verifyButtonsLayout(HorizontalLayout layout) {
        assertTrue(layout.isSpacing());
        assertEquals(new MarginInfo(true), layout.getMargin());
        assertEquals(1, layout.getComponentCount());
        verifyButton(layout.getComponent(0), "Export", true);
    }

    private void initWidget() {
        udmBaselineValueWidget = new UdmBaselineValueWidget();
        udmBaselineValueWidget.setController(controller);
        udmBaselineValueWidget.init();
    }

    private UdmValueBaselineDto buildUdmValueBaselineDto() {
        UdmValueBaselineDto udmValueBaseline = new UdmValueBaselineDto();
        udmValueBaseline.setId("04fec507-881f-4c4f-b31a-85230821dad0");
        udmValueBaseline.setPeriod(202806);
        udmValueBaseline.setWrWrkInst(122890651L);
        udmValueBaseline.setSystemTitle("Journal of medical education");
        udmValueBaseline.setPublicationType("BK");
        udmValueBaseline.setPrice(new BigDecimal("379.3984392886"));
        udmValueBaseline.setPriceFlag(true);
        udmValueBaseline.setContent(new BigDecimal("100.01"));
        udmValueBaseline.setContentFlag(true);
        udmValueBaseline.setContentUnitPrice(new BigDecimal("16.5833333333"));
        udmValueBaseline.setContentUnitPriceFlag(true);
        udmValueBaseline.setComment("some comment");
        udmValueBaseline.setUpdateUser("user@copyright.com");
        udmValueBaseline.setUpdateDate(
            Date.from(LocalDate.of(2022, 9, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        return udmValueBaseline;
    }
}
