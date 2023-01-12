package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGridItems;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.expect;
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
import com.copyright.rup.dist.foreign.domain.AclScenarioDetailDto;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.ui.usage.UiTestHelper;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.List;
import java.util.function.Supplier;

/**
 * Verifies {@link AclScenarioDetailsWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 11/17/2022
 *
 * @author Mikita Maistrenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(JavaScript.class)
public class AclScenarioDetailsWidgetTest {

    private static final String RH_NAME = "American Chemical Society";
    private static final String PAYEE_NAME = "John Wiley & Sons - Books";
    private static final String SEARCH_VALUE = "search";
    private static final String USAGE_BATCH_NAME = "Usage Batch Name";
    private static final String Y = "Y";

    private AclScenarioDetailsWidget widget;
    private AclScenarioDetailsController controller;

    @Before
    public void setUp() {
        controller = createMock(AclScenarioDetailsController.class);
        IStreamSource streamSource = createMock(IStreamSource.class);
        expect(streamSource.getSource()).andReturn(new SimpleImmutableEntry(createMock(Supplier.class),
            createMock(Supplier.class))).once();
        expect(controller.getExportAclScenarioDetailsStreamSource()).andReturn(streamSource).once();
        replay(controller, streamSource);
        widget = new AclScenarioDetailsWidget(controller);
        widget.setController(controller);
        widget.init();
        verify(controller, streamSource);
        reset(controller);
    }

    @Test
    public void testComponentStructure() {
        assertEquals("acl-scenario-details-widget", widget.getId());
        verifyWindow(widget, StringUtils.EMPTY, 1280, 600, Unit.PIXELS);
        VerticalLayout content = (VerticalLayout) widget.getContent();
        assertEquals(new MarginInfo(true, true, true, true), content.getMargin());
        assertEquals(3, content.getComponentCount());
        verifySearchWidget(content.getComponent(0), "Enter Usage Detail ID or Wr Wrk Inst or System Title " +
            "or Rightsholder Name/Account # or Payee Name/Account #");
        verifyTable(content.getComponent(1));
        UiTestHelper.verifyButtonsLayout(content.getComponent(2), "Export", "Close");
    }

    @Test
    public void testGetSearchValueByScenario() {
        SearchWidget searchWidget = new SearchWidget(() -> {/*stub*/});
        searchWidget.setSearchValue(SEARCH_VALUE);
        Whitebox.setInternalState(widget, searchWidget);
        assertEquals(SEARCH_VALUE, widget.getSearchValue());
    }

    @Test
    public void testGridValues() {
        mockStatic(JavaScript.class);
        expect(JavaScript.getCurrent()).andReturn(createMock(JavaScript.class)).times(2);
        List<AclScenarioDetailDto> scenarioDetails = List.of(buildAclScenarioDetailDto());
        expect(controller.getSize()).andReturn(1).once();
        expect(controller.loadBeans(0, Integer.MAX_VALUE, List.of())).andReturn(scenarioDetails).once();
        replay(JavaScript.class, controller);
        Grid grid = (Grid) ((VerticalLayout) widget.getContent()).getComponent(1);
        DataProvider dataProvider = grid.getDataProvider();
        dataProvider.refreshAll();
        Object[][] expectedCells = {
            {"73eb89d8-68e7-4f5c-a6a6-1c8c551e6de3", "Usage Detail Id", "ACL", USAGE_BATCH_NAME, 202206, 123090280L,
                "Langmuir", 1000009767L, RH_NAME, 1000002859L, PAYEE_NAME, 1000009767L, RH_NAME,
                1000002859L, PAYEE_NAME, 202206, "1.00", 22, "Banks/Ins/RE/Holding Cos", 56, "Financial", "Belgium",
                "EMAIL_COPY", "DIGITAL", "4.0001", "2.00001", "STND", "3.00", "120.54", Y, "4.00001", Y,
                "5.0123456789", Y, "6.01", "7.10", "8.123", "9.12", "10.123", "11.00001", "12.0000000009", "13.00",
                "14.00"}
        };
        verifyGridItems(grid, scenarioDetails, expectedCells);
        verify(JavaScript.class, controller);
    }

    private void verifySearchWidget(Component component, String searchPrompt) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        HorizontalLayout horizontalLayout = (HorizontalLayout) component;
        assertEquals(1, horizontalLayout.getComponentCount());
        SearchWidget searchWidget = (SearchWidget) horizontalLayout.getComponent(0);
        assertEquals(60, searchWidget.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, searchWidget.getWidthUnits());
        assertEquals(Alignment.MIDDLE_CENTER, horizontalLayout.getComponentAlignment(searchWidget));
        assertTrue(horizontalLayout.isSpacing());
        assertEquals(100, horizontalLayout.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, horizontalLayout.getWidthUnits());
        assertEquals(searchPrompt, ((TextField) searchWidget.getComponent(0)).getPlaceholder());
    }

    private void verifyTable(Component component) {
        assertNotNull(component);
        assertThat(component, instanceOf(Grid.class));
        Grid<UsageDto> grid = (Grid<UsageDto>) component;
        assertTrue(grid.getStyleName().contains("acl-scenario-details-grid"));
        verifyGrid(grid, List.of(
            Triple.of("Detail ID", 130.0, -1),
            Triple.of("Usage Detail ID", 130.0, -1),
            Triple.of("Product Family", 125.0, -1),
            Triple.of("Usage Batch Name", 145.0, -1),
            Triple.of("Period End Date", 125.0, -1),
            Triple.of("Wr Wrk Inst", 110.0, -1),
            Triple.of("System Title", 250.0, -1),
            Triple.of("Print RH Account #", 140.0, -1),
            Triple.of("Print RH Name", 150.0, -1),
            Triple.of("Print Payee Account #", 160.0, -1),
            Triple.of("Print Payee Name", 150.0, -1),
            Triple.of("Digital RH Account #", 140.0, -1),
            Triple.of("Digital RH Name", 150.0, -1),
            Triple.of("Digital Payee Account #", 160.0, -1),
            Triple.of("Digital Payee Name", 150.0, -1),
            Triple.of("Usage Period", 100.0, -1),
            Triple.of("Usage Age Weight", 130.0, -1),
            Triple.of("Det LC ID", 100.0, -1),
            Triple.of("Det LC Name", 200.0, -1),
            Triple.of("Agg LC ID", 100.0, -1),
            Triple.of("Agg LC Name", 200.0, -1),
            Triple.of("Survey Country", 120.0, -1),
            Triple.of("Reported TOU", 120.0, -1),
            Triple.of("TOU", 75.0, -1),
            Triple.of("# of Copies", 125.0, -1),
            Triple.of("# of Weighted Copies", 150.0, -1),
            Triple.of("Pub Type", 120.0, -1),
            Triple.of("Pub Type Weight", 120.0, -1),
            Triple.of("Price", 130.0, -1),
            Triple.of("Price Flag", 110.0, -1),
            Triple.of("Content", 100.0, -1),
            Triple.of("Content Flag", 110.0, -1),
            Triple.of("Content Unit Price", 150.0, -1),
            Triple.of("CUP Flag", 90.0, -1),
            Triple.of("Print Value Share", 140.0, -1),
            Triple.of("Print Volume Share", 140.0, -1),
            Triple.of("Print Detail Share", 140.0, -1),
            Triple.of("Print Net Amt", 150.0, -1),
            Triple.of("Digital Value Share", 150.0, -1),
            Triple.of("Digital Volume Share", 150.0, -1),
            Triple.of("Digital Detail Share", 150.0, -1),
            Triple.of("Digital Net Amt", 150.0, -1),
            Triple.of("Combined Net Amt", 170.0, -1)
        ));
        verifyWindow(grid, null, 100, 100, Unit.PERCENTAGE);
        List<Column<UsageDto, ?>> columns = grid.getColumns();
        columns.forEach(column -> {
            assertTrue(column.isSortable());
            assertTrue(column.isResizable());
        });
    }

    private AclScenarioDetailDto buildAclScenarioDetailDto() {
        PublicationType pubType = new PublicationType();
        pubType.setName("STND");
        pubType.setWeight(new BigDecimal("3.00000"));
        AclScenarioDetailDto scenarioDetail = new AclScenarioDetailDto();
        scenarioDetail.setId("73eb89d8-68e7-4f5c-a6a6-1c8c551e6de3");
        scenarioDetail.setOriginalDetailId("Usage Detail Id");
        scenarioDetail.setProductFamily("ACL");
        scenarioDetail.setUsageBatchName(USAGE_BATCH_NAME);
        scenarioDetail.setPeriodEndPeriod(202206);
        scenarioDetail.setWrWrkInst(123090280L);
        scenarioDetail.setSystemTitle("Langmuir");
        scenarioDetail.setRhAccountNumberPrint(1000009767L);
        scenarioDetail.setRhNamePrint(RH_NAME);
        scenarioDetail.setPayeeAccountNumberPrint(1000002859L);
        scenarioDetail.setPayeeNamePrint(PAYEE_NAME);
        scenarioDetail.setRhAccountNumberDigital(1000009767L);
        scenarioDetail.setRhNameDigital(RH_NAME);
        scenarioDetail.setPayeeAccountNumberDigital(1000002859L);
        scenarioDetail.setPayeeNameDigital(PAYEE_NAME);
        scenarioDetail.setUsagePeriod(202206);
        scenarioDetail.setUsageAgeWeight(new BigDecimal("1.00000"));
        scenarioDetail.setDetailLicenseeClassId(22);
        scenarioDetail.setDetailLicenseeClassName("Banks/Ins/RE/Holding Cos");
        scenarioDetail.setAggregateLicenseeClassId(56);
        scenarioDetail.setAggregateLicenseeClassName("Financial");
        scenarioDetail.setSurveyCountry("Belgium");
        scenarioDetail.setReportedTypeOfUse("EMAIL_COPY");
        scenarioDetail.setTypeOfUse("DIGITAL");
        scenarioDetail.setNumberOfCopies(new BigDecimal("4.00010"));
        scenarioDetail.setWeightedCopies(new BigDecimal("2.00001"));
        scenarioDetail.setPublicationType(pubType);
        scenarioDetail.setPrice(new BigDecimal("120.5400000000"));
        scenarioDetail.setPriceFlag(true);
        scenarioDetail.setContent(new BigDecimal("4.0000100000"));
        scenarioDetail.setContentFlag(true);
        scenarioDetail.setContentUnitPrice(new BigDecimal("5.0123456789"));
        scenarioDetail.setContentUnitPriceFlag(true);
        scenarioDetail.setValueSharePrint(new BigDecimal("6.0100000000"));
        scenarioDetail.setVolumeSharePrint(new BigDecimal("7.1000000000"));
        scenarioDetail.setDetailSharePrint(new BigDecimal("8.1230000000"));
        scenarioDetail.setNetAmountPrint(new BigDecimal("9.1230000000"));
        scenarioDetail.setValueShareDigital(new BigDecimal("10.1230000000"));
        scenarioDetail.setVolumeShareDigital(new BigDecimal("11.0000100000"));
        scenarioDetail.setDetailShareDigital(new BigDecimal("12.0000000009"));
        scenarioDetail.setNetAmountDigital(new BigDecimal("13.0000000123"));
        scenarioDetail.setCombinedNetAmount(new BigDecimal("14.0012300000"));
        return scenarioDetail;
    }
}
