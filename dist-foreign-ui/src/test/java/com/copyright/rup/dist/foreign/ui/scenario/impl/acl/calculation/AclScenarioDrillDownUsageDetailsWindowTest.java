package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButton;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyFooterItems;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGridItems;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyLabel;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.AclScenarioDetailDto;
import com.copyright.rup.dist.foreign.domain.filter.RightsholderResultsFilter;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioController;
import com.copyright.rup.dist.foreign.ui.usage.UiTestHelper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Verifies {@link AclScenarioDrillDownUsageDetailsWindow}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 08/24/2022
 *
 * @author Aliaksandr Liakh
 */
public class AclScenarioDrillDownUsageDetailsWindowTest {

    private static final String STYLE_ALIGN_RIGHT = "v-align-right";

    private final List<AclScenarioDetailDto> scenarioDetails =
        loadExpectedAclScenarioDetailDto("json/acl_scenario_detail_dto_112ff7b8_2d4b5a31.json");
    private AclScenarioDrillDownUsageDetailsWindow window;

    @Before
    public void setUp() {
        IAclScenarioController controller = createMock(IAclScenarioController.class);
        RightsholderResultsFilter filter = buildRightsholderResultsFilter();
        expect(controller.getRightsholderDetailsResults(filter)).andReturn(scenarioDetails).once();
        replay(controller);
        window = new AclScenarioDrillDownUsageDetailsWindow(controller, filter);
        verify(controller);
    }

    @Test
    public void testComponentStructure() {
        assertEquals("acl-scenario-drill-down-usage-details-window", window.getId());
        verifyWindow(window, "Results by Rightsholder: Usage Details", 1280, 600, Unit.PIXELS);
        VerticalLayout content = (VerticalLayout) window.getContent();
        assertEquals(new MarginInfo(true, true, true, true), content.getMargin());
        assertEquals(3, content.getComponentCount());
        verifyMetaInfoLayout(content.getComponent(0));
        verifyGrid(content.getComponent(1));
        verifyButton(content.getComponent(2), "Close", true);
    }

    @Test
    public void testGridValues() {
        Grid<?> grid = (Grid<?>) ((VerticalLayout) window.getContent()).getComponent(1);
        Object[][] expectedCells = {
            {"112ff7b8-8fe3-4b5a-a5da-978210889fce", 202206, "1.00", "United Kingdom", 22, "Banks/Ins/RE/Holding Cos",
                null, "44.00", "44.00", "TGB", "1.90", "", "N", "", "N", "1.6906798275", "N", "0.0000093612",
                "0.0000094213", "0.0000093912", "0.34", "0.0000100811", "0.000010214", "0.0000101476", "0.37", "0.70"},
            {"2d4b5a31-bbcd-44cf-b1e5-a9144c58a39c", 202206, "1.00", "Belarus", 22, "Banks/Ins/RE/Holding Cos",
                null, "60.00", "60.00", "TGB", "1.90", "", "N", "", "N", "1.6906798275", "N", "0.0000127652",
                "0.0000128472", "0.0000128062", "0.46", "0.000013747", "0.0000139282", "0.0000138376", "0.50", "0.96"}
        };
        verifyGridItems(grid, scenarioDetails, expectedCells);
        Object[][] expectedFooterColumns = {
            {"valueSharePrint", "0.0000221264", STYLE_ALIGN_RIGHT},
            {"volumeSharePrint", "0.0000222685", STYLE_ALIGN_RIGHT},
            {"detailSharePrint", "0.0000221974", STYLE_ALIGN_RIGHT},
            {"netAmountPrint", "0.80", STYLE_ALIGN_RIGHT},
            {"valueShareDigital", "0.0000238281", STYLE_ALIGN_RIGHT},
            {"volumeShareDigital", "0.0000241422", STYLE_ALIGN_RIGHT},
            {"detailShareDigital", "0.0000239852", STYLE_ALIGN_RIGHT},
            {"netAmountDigital", "0.86", STYLE_ALIGN_RIGHT},
            {"combinedNetAmount", "1.67", STYLE_ALIGN_RIGHT}
        };
        verifyFooterItems(grid, expectedFooterColumns);
    }

    private RightsholderResultsFilter buildRightsholderResultsFilter() {
        RightsholderResultsFilter filter = new RightsholderResultsFilter();
        filter.setScenarioId("8bda7618-29a3-4a59-8a13-8da45e2c7f3a");
        filter.setRhAccountNumber(7000447306L);
        filter.setRhName("Longhouse");
        filter.setWrWrkInst(303580015L);
        filter.setSystemTitle("Fortune");
        filter.setAggregateLicenseeClassId(12);
        filter.setAggregateLicenseeClassName("Machinery");
        return filter;
    }

    private void verifyMetaInfoLayout(Component component) {
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        String[][] expectedCaptions = {
            {"RH Account #:", "7000447306"},
            {"RH Name:", "Longhouse"},
            {"Wr Wrk Inst:", "303580015"},
            {"System Title:", "Fortune"},
            {"Agg Lic Class ID:", "12"},
            {"Agg Lic Class Name:", "Machinery"}
        };
        verifyLabelLayout(verticalLayout, expectedCaptions);
    }

    private void verifyLabelLayout(VerticalLayout verticalLayout, String[]... expectedCaptions) {
        int componentCount = expectedCaptions.length;
        assertEquals(componentCount, verticalLayout.getComponentCount());
        IntStream.range(0, componentCount).forEach(i -> {
            Component horizontalComponent = verticalLayout.getComponent(i);
            assertTrue(horizontalComponent instanceof HorizontalLayout);
            HorizontalLayout horizontalLayout = (HorizontalLayout) verticalLayout.getComponent(i);
            assertEquals(2, horizontalLayout.getComponentCount());
            verifyLabel(horizontalLayout.getComponent(0), expectedCaptions[i][0], ContentMode.TEXT, 125);
            verifyLabel(horizontalLayout.getComponent(1), expectedCaptions[i][1], ContentMode.TEXT, 1125);
        });
    }

    private void verifyGrid(Component component) {
        assertNotNull(component);
        assertTrue(component instanceof Grid);
        Grid<?> grid = (Grid<?>) component;
        assertTrue(grid.getStyleName().contains("acl-scenario-drill-down-usage-details-grid"));
        UiTestHelper.verifyGrid(grid, Arrays.asList(
            Triple.of("Detail ID", 130.0, -1),
            Triple.of("Usage Period", 125.0, -1),
            Triple.of("Usage Age Weight", 130.0, -1),
            Triple.of("Survey Country", 120.0, -1),
            Triple.of("Det LC ID", 100.0, -1),
            Triple.of("Det LC Name", 200.0, -1),
            Triple.of("TOU", 120.0, -1),
            Triple.of("# of Copies", 125.0, -1),
            Triple.of("# of Weighted Copies", 150.0, -1),
            Triple.of("Pub Type", 120.0, -1),
            Triple.of("Pub Type Weight", 120.0, -1),
            Triple.of("Price", 100.0, -1),
            Triple.of("Price Flag", 110.0, -1),
            Triple.of("Content", 100.0, -1),
            Triple.of("Content Flag", 110.0, -1),
            Triple.of("Content Unit Price", 150.0, -1),
            Triple.of("Content Unit Price Flag", 160.0, -1),
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
        grid.getColumns().forEach(column -> {
            assertTrue(column.isSortable());
            assertTrue(column.isResizable());
        });
    }

    private List<AclScenarioDetailDto> loadExpectedAclScenarioDetailDto(String fileName) {
        try {
            String content = TestUtils.fileToString(this.getClass(), fileName);
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
            return mapper.readValue(content, new TypeReference<List<AclScenarioDetailDto>>() {
            });
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
