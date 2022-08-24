package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGridItems;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.AclScenarioDetailDto;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioController;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link AclScenarioDetailsByRhAndTitleAndAggLicClassWindow}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 08/24/2022
 *
 * @author Aliaksandr Liakh
 */
public class AclScenarioDetailsByRhAndTitleAndAggLicClassWindowTest {

    private static final String SCENARIO_ID = "49f6841b-2be8-4b8a-962d-f995e9fe52cc";
    private static final Long ACCOUNT_NUMBER = 1000001863L;
    private static final String TITLE = "Langmuir";
    private static final Integer AGG_LIC_CLASS_ID = 56;

    private final List<AclScenarioDetailDto> scenarioDetails = Collections.singletonList(buildAclScenarioDetailDto());
    private AclScenarioDetailsByRhAndTitleAndAggLicClassWindow window;

    @Before
    public void setUp() {
        IAclScenarioController controller = createMock(IAclScenarioController.class);
        expect(controller.getByScenarioIdAndRhAccountNumberAndTitleAndAggLicClass(SCENARIO_ID, ACCOUNT_NUMBER, TITLE,
            AGG_LIC_CLASS_ID)).andReturn(scenarioDetails).once();
        replay(controller);
        window = new AclScenarioDetailsByRhAndTitleAndAggLicClassWindow(controller, SCENARIO_ID, ACCOUNT_NUMBER, TITLE,
            AGG_LIC_CLASS_ID);
        verify(controller);
    }

    @Test
    public void testComponentStructure() {
        assertEquals("acl-view-scenario-details-by-rh-title-agg-lic-class-window", window.getId());
        verifyWindow(window, StringUtils.EMPTY, 100, 95, Unit.PERCENTAGE);
        VerticalLayout content = (VerticalLayout) window.getContent();
        assertEquals(new MarginInfo(false, false, false, false), content.getMargin());
        assertEquals(2, content.getComponentCount());
        verifyTable(content.getComponent(0));
        verifyButtonsLayout(content.getComponent(1), "Close");
    }

    @Test
    public void testGridValues() {
        Grid<?> grid = (Grid<?>) ((VerticalLayout) window.getContent()).getComponent(0);
        Object[][] expectedCells = {{
            "73eb89d8-68e7-4f5c-a6a6-1c8c551e6de3", 202206, "1.00", "Belgium", 22, "Banks/Ins/RE/Holding Cos",
            "COPY_FOR_MYSELF", "4.0001", "2.00001", "STND", "3.00", "120.54", "N", "4.00001", "N", "5.0123456789",
            "N", "6.01", "7.10", "8.123", "9.12", "10.123", "11.00001", "12.0000000009", "13.00", "14.00"}};
        verifyGridItems(grid, scenarioDetails, expectedCells);
    }

    private void verifyTable(Component component) {
        assertNotNull(component);
        assertTrue(component instanceof Grid);
        Grid<?> grid = (Grid<?>) component;
        assertTrue(grid.getStyleName().contains("acl-view-scenario-details-by-rh-title-agg-lic-class-table"));
        verifyGrid(grid, Arrays.asList(
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

    private AclScenarioDetailDto buildAclScenarioDetailDto() {
        PublicationType publicationType = new PublicationType();
        publicationType.setName("STND");
        publicationType.setWeight(new BigDecimal("3.00000"));
        AclScenarioDetailDto scenarioDetail = new AclScenarioDetailDto();
        scenarioDetail.setId("73eb89d8-68e7-4f5c-a6a6-1c8c551e6de3");
        scenarioDetail.setPeriodEndPeriod(202206);
        scenarioDetail.setSystemTitle("Langmuir");
        scenarioDetail.setUsagePeriod(202206);
        scenarioDetail.setUsageAgeWeight(new BigDecimal("1.00000"));
        scenarioDetail.setDetailLicenseeClassId(22);
        scenarioDetail.setDetailLicenseeClassName("Banks/Ins/RE/Holding Cos");
        scenarioDetail.setSurveyCountry("Belgium");
        scenarioDetail.setReportedTypeOfUse("COPY_FOR_MYSELF");
        scenarioDetail.setNumberOfCopies(new BigDecimal("4.00010"));
        scenarioDetail.setWeightedCopies(new BigDecimal("2.00001"));
        scenarioDetail.setPublicationType(publicationType);
        scenarioDetail.setPrice(new BigDecimal("120.5400000000"));
        scenarioDetail.setPriceFlag(false);
        scenarioDetail.setContent(new BigDecimal("4.0000100000"));
        scenarioDetail.setContentFlag(false);
        scenarioDetail.setContentUnitPrice(new BigDecimal("5.0123456789"));
        scenarioDetail.setContentUnitPriceFlag(false);
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
