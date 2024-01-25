package com.copyright.rup.dist.foreign.vui.scenario.impl.fas;

import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyGridItems;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.vui.UiTestHelper;
import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IFasScenariosController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.util.List;

/**
 * Verifies {@link RefreshScenarioWindow}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 03/12/18
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class})
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
public class RefreshScenarioWindowTest {

    private final List<UsageDto> usages = loadExpectedUsageDto("usage_dto_377594f8.json");

    @Test
    public void testStructure() {
        var window = new RefreshScenarioWindow(value -> null, value -> 0, null);
        verifyWindow(window, "Refresh Scenario", "1000px", "600px", Unit.PIXELS, true);
        var content = (VerticalLayout) UiTestHelper.getDialogContent(window);
        assertEquals(2, content.getComponentCount());
        assertThat(content.getComponentAt(1), instanceOf(Grid.class));
        Grid grid = (Grid) content.getComponentAt(1);
        verifyGrid(grid, List.of(
            Pair.of("Detail ID", "135px"),
            Pair.of("Detail Status", null),
            Pair.of("Product Family", null),
            Pair.of("Usage Batch Name", "190px"),
            Pair.of("RRO Account #", null),
            Pair.of("RRO Name", "165px"),
            Pair.of("RH Account #", null),
            Pair.of("RH Name", "360px"),
            Pair.of("Wr Wrk Inst", null),
            Pair.of("System Title", "360px"),
            Pair.of("Reported Standard Number", "260px"),
            Pair.of("Standard Number", "185px"),
            Pair.of("Standard Number Type", "230px"),
            Pair.of("Fiscal Year", null),
            Pair.of("Payment Date", null),
            Pair.of("Reported Title", "350px"),
            Pair.of("Article", "185px"),
            Pair.of("Publisher", "140px"),
            Pair.of("Pub Date", "112px"),
            Pair.of("Number of Copies", null),
            Pair.of("Reported Value", "170px"),
            Pair.of("Gross Amt in USD", "175px"),
            Pair.of("Batch Amt in USD", "175px"),
            Pair.of("Market", "135px"),
            Pair.of("Market Period From", "205px"),
            Pair.of("Market Period To", "200px"),
            Pair.of("Author", "300px"),
            Pair.of("Comment", "200px")
        ));
        verifyButtonsLayout(UiTestHelper.getFooterComponent(window, 2), true, "Ok", "Cancel");
    }

    @Test
    public void testGridValues() {
        RefreshScenarioWindow window = new RefreshScenarioWindow(query -> usages.stream(), query -> 1, null);
        Grid<?> grid = (Grid<?>) ((VerticalLayout) UiTestHelper.getDialogContent(window)).getComponentAt(1);
        Object[][] expectedCells = {
            {"377594f8-a247-4277-b29f-0b33ff360a7e", UsageStatusEnum.PAID.name(), "FAS", "Paid batch", "1000000004",
                "Computers for Design and Construction", "1000002859", "John Wiley & Sons - Books", "243904752",
                "100 ROAD MOVIES", "1008902112317555XX", "1008902112317555XX", "VALISBN13", "FY2021", "02/12/2021",
                "100 ROAD MOVIES", "some article", "some publisher", "02/13/2021", "2", "3,000.00", "500.00",
                "1,000.00", "lib", "1980", "2000", "author", "usage from usages_10.csv"}
        };
        verifyGridItems(grid, usages, expectedCells);
    }

    @Test
    public void testOnOkButtonClick() {
        IFasScenariosController controller = createMock(IFasScenariosController.class);
        var window = new RefreshScenarioWindow(value -> null, value -> 0, controller);
        var buttonsLayout = (HorizontalLayout) UiTestHelper.getFooterComponent(window, 2);
        var okButton = (Button) buttonsLayout.getComponentAt(0);
        expect(controller.getInvalidRightsholders()).andReturn(List.of()).once();
        controller.refreshScenario();
        expectLastCall().once();
        replay(controller);
        okButton.click();
        verify(controller);
    }

    @Test
    public void testOnOkButtonClickInvalidRightsholders() {
        IFasScenariosController controller = createMock(IFasScenariosController.class);
        var window = new RefreshScenarioWindow(value -> null, value -> 0, controller);
        var buttonsLayout = (HorizontalLayout) UiTestHelper.getFooterComponent(window, 2);
        var okButton = (Button) buttonsLayout.getComponentAt(0);
        mockStatic(Windows.class);
        expect(controller.getInvalidRightsholders()).andReturn(List.of(100000000L)).once();
        Windows.showNotificationWindow("Scenario cannot be refreshed. The following rightsholder(s) are absent in " +
            "PRM: <i><b>[100000000]</b></i>");
        expectLastCall().once();
        replay(controller, Windows.class);
        okButton.click();
        verify(controller, Windows.class);
    }

    private List<UsageDto> loadExpectedUsageDto(String fileName) {
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
