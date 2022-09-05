package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButton;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyFooterItems;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGridItems;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyLabel;

import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectNew;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.AclRightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.AclRightsholderTotalsHolderDto;
import com.copyright.rup.dist.foreign.domain.filter.RightsholderResultsFilter;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioController;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vaadin.data.ValueProvider;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.tuple.Triple;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Verifies {@link AclScenarioDrillDownAggLcClassesWindow}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 08/24/2022
 *
 * @author Mikita Maistrenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({AclScenarioDrillDownAggLcClassesWindow.class, Windows.class})
public class AclScenarioDrillDownAggLcClassesWindowTest {

    private static final String STYLE_ALIGN_RIGHT = "v-align-right";
    private static final String SCENARIO_UID = "986a6b68-a8c1-41b9-b7fb-4002cd700337";
    private static final Long RH_ACCOUNT_NUMBER = 1000010022L;
    private static final String RH_NAME = "Yale University Press";
    private static final Long WR_WRK_INST = 127778306L;
    private static final String SYSTEM_TITLE = "Adaptations";
    private static final Integer AGG_LIC_CLASS_ID = 1;
    private static final String AGG_LIC_CLASS_NAME = "Food and Tobacco";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final List<AclRightsholderTotalsHolderDto> rightsholderTotalsHolderDtos =
        loadExpectedAclRightsholderTotalsHolderDto("json/acl_rightsholder_totals_holder_dto_agg_lc_class.json");

    static {
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
    }

    private AclScenarioDrillDownAggLcClassesWindow window;
    private IAclScenarioController controller;

    @Before
    public void setUp() {
        controller = createMock(IAclScenarioController.class);
        RightsholderResultsFilter filter = buildRightsholderResultsFilter(WR_WRK_INST, SYSTEM_TITLE);
        expect(controller.getRightsholderAggLcClassResults(filter)).andReturn(rightsholderTotalsHolderDtos)
            .once();
        replay(controller);
        window = new AclScenarioDrillDownAggLcClassesWindow(controller, filter);
        verify(controller);
        reset(controller);
    }

    @Test
    public void testStructure() {
        assertEquals("Results by Rightsholder: Aggregate Licensee Class", window.getCaption());
        assertEquals(1280, window.getWidth(), 0);
        assertEquals(Sizeable.Unit.PIXELS, window.getWidthUnits());
        assertEquals(75, window.getHeight(), 0);
        assertEquals(Sizeable.Unit.PERCENTAGE, window.getHeightUnits());
        VerticalLayout content = (VerticalLayout) window.getContent();
        assertEquals(3, content.getComponentCount());
        verifyMetaInfoLayout(content.getComponent(0));
        Component component = content.getComponent(1);
        assertTrue(component instanceof Grid);
        verifyGrid((Grid) component, Arrays.asList(
            Triple.of("Agg LC ID", 110.0, -1),
            Triple.of("Agg LC Name", 256.0, -1),
            Triple.of("Print Gross Amt", 150.0, -1),
            Triple.of("Print Net Amt", 150.0, -1),
            Triple.of("Digital Gross Amt", 150.0, -1),
            Triple.of("Digital Net Amt", 150.0, -1),
            Triple.of("Total Gross Amt", 150.0, -1),
            Triple.of("Total Net Amt", 140.0, -1)
        ));
        verifyButton(content.getComponent(2), "Close", true);
        assertEquals("acl-scenario-drill-down-aggregate-licensee-class-window", window.getStyleName());
        assertEquals("acl-scenario-drill-down-aggregate-licensee-class-window", window.getId());
    }

    @Test
    public void testGridValues() {
        Grid<?> grid = (Grid<?>) ((VerticalLayout) window.getContent()).getComponent(1);
        Object[][] expectedCells = {
            {"1", "Food and Tobacco", "1.00", "3.70", "2.95", "4.57", "3.95", "8.27"},
            {"12", "Machinery", "2.01", "2.74", "4.00", "5.00", "6.01", "7.74"}
        };
        verifyGridItems(grid, rightsholderTotalsHolderDtos, expectedCells);
        Object[][] expectedFooterColumns = {
            {"grossTotalPrint", "3.01", STYLE_ALIGN_RIGHT},
            {"netTotalPrint", "6.44", STYLE_ALIGN_RIGHT},
            {"grossTotalDigital", "6.95", STYLE_ALIGN_RIGHT},
            {"netTotalDigital", "9.57", STYLE_ALIGN_RIGHT},
            {"grossTotal", "9.96", STYLE_ALIGN_RIGHT},
            {"netTotal", "16.01", STYLE_ALIGN_RIGHT},

        };
        verifyFooterItems(grid, expectedFooterColumns);
    }

    @Test
    public void testAggLcClassCellClickToDrillDownTitlesWindow() throws Exception {
        RightsholderResultsFilter filter = buildRightsholderResultsFilter(null, null);
        expect(controller.getRightsholderAggLcClassResults(filter)).andReturn(rightsholderTotalsHolderDtos)
            .once();
        AclScenarioDrillDownTitlesWindow mockWindow = createMock(AclScenarioDrillDownTitlesWindow.class);
        expectNew(AclScenarioDrillDownTitlesWindow.class, eq(controller), eq(filter)).andReturn(mockWindow).once();
        mockStatic(Windows.class);
        Windows.showModalWindow(mockWindow);
        expectLastCall().once();
        replay(Windows.class, AclScenarioDrillDownTitlesWindow.class, controller);
        window = new AclScenarioDrillDownAggLcClassesWindow(controller, filter);
        Grid grid = Whitebox.getInternalState(window, "grid");
        Grid.Column column = (Grid.Column) grid.getColumns().get(0);
        ValueProvider<AclRightsholderTotalsHolder, Button> provider = column.getValueProvider();
        Button button = provider.apply(rightsholderTotalsHolderDtos.get(0));
        button.click();
        verify(Windows.class, AclScenarioDrillDownTitlesWindow.class, controller);
    }

    @Test
    public void testAggLcClassCellClickToDrillDownUsageDetailsWindow() throws Exception {
        RightsholderResultsFilter filter = buildRightsholderResultsFilter(WR_WRK_INST, SYSTEM_TITLE);
        AclScenarioDrillDownUsageDetailsWindow mockWindow = createMock(AclScenarioDrillDownUsageDetailsWindow.class);
        expectNew(AclScenarioDrillDownUsageDetailsWindow.class, eq(controller), eq(filter)).andReturn(mockWindow)
            .once();
        mockStatic(Windows.class);
        Windows.showModalWindow(mockWindow);
        expectLastCall().once();
        replay(Windows.class, AclScenarioDrillDownUsageDetailsWindow.class, controller);
        Grid grid = Whitebox.getInternalState(window, "grid");
        Grid.Column column = (Grid.Column) grid.getColumns().get(0);
        ValueProvider<AclRightsholderTotalsHolder, Button> provider = column.getValueProvider();
        Button button = provider.apply(rightsholderTotalsHolderDtos.get(0));
        button.click();
        verify(Windows.class, AclScenarioDrillDownUsageDetailsWindow.class, controller);
    }

    private RightsholderResultsFilter buildRightsholderResultsFilter(Long wrWrkInst, String systemTitle) {
        RightsholderResultsFilter filter = new RightsholderResultsFilter();
        filter.setScenarioId(SCENARIO_UID);
        filter.setRhAccountNumber(RH_ACCOUNT_NUMBER);
        filter.setRhName(RH_NAME);
        filter.setWrWrkInst(wrWrkInst);
        filter.setSystemTitle(systemTitle);
        filter.setAggregateLicenseeClassId(AGG_LIC_CLASS_ID);
        filter.setAggregateLicenseeClassName(AGG_LIC_CLASS_NAME);
        return filter;
    }

    private void verifyMetaInfoLayout(Component component) {
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(4, verticalLayout.getComponentCount());
        String[][] expectedMetaInfoCaptions = {
            {"RH Account #:", RH_ACCOUNT_NUMBER.toString()},
            {"RH Name:", RH_NAME},
            {"Wr Wrk Inst:", WR_WRK_INST.toString()},
            {"System Title:", SYSTEM_TITLE}
        };
        verifyLabelLayout(verticalLayout, expectedMetaInfoCaptions, verticalLayout.getComponentCount());
    }

    private void verifyLabelLayout(VerticalLayout verticalLayout, String[][] expectedCaptions, int componentCount) {
        IntStream.range(0, componentCount).forEach(i -> {
            Component horizontalComponent = verticalLayout.getComponent(i);
            assertTrue(horizontalComponent instanceof HorizontalLayout);
            HorizontalLayout horizontalLayout = (HorizontalLayout) verticalLayout.getComponent(i);
            assertEquals(2, horizontalLayout.getComponentCount());
            verifyLabel(horizontalLayout.getComponent(0), expectedCaptions[i][0], ContentMode.TEXT, 125);
            verifyLabel(horizontalLayout.getComponent(1), expectedCaptions[i][1], ContentMode.TEXT, 1125);
        });
    }

    private List<AclRightsholderTotalsHolderDto> loadExpectedAclRightsholderTotalsHolderDto(String fileName) {
        try {
            String content = TestUtils.fileToString(this.getClass(), fileName);
            return OBJECT_MAPPER.readValue(content, new TypeReference<List<AclRightsholderTotalsHolderDto>>() {
            });
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
