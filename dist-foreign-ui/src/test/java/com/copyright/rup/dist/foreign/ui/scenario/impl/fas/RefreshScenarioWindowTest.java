package com.copyright.rup.dist.foreign.ui.scenario.impl.fas;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGridItems;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IFasScenariosController;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.tuple.Triple;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
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
@PrepareForTest({Windows.class, JavaScript.class})
public class RefreshScenarioWindowTest {

    private final List<UsageDto> usages = loadExpectedUsageDto("usage_dto_377594f8.json");

    @Test
    public void testStructure() {
        RefreshScenarioWindow window = new RefreshScenarioWindow(value -> null, value -> 0, null);
        verifyWindow(window, "Refresh Scenario", 800, 400, Unit.PIXELS);
        VerticalLayout content = (VerticalLayout) window.getContent();
        assertEquals(3, content.getComponentCount());
        assertThat(content.getComponent(1), instanceOf(Grid.class));
        Grid grid = (Grid) content.getComponent(1);
        verifyGrid(grid, Arrays.asList(
            Triple.of("Detail ID", 130.0, -1),
            Triple.of("Detail Status", -1.0, -1),
            Triple.of("Product Family", -1.0, -1),
            Triple.of("Usage Batch Name", 135.0, -1),
            Triple.of("RRO Account #", -1.0, -1),
            Triple.of("RRO Name", 135.0, -1),
            Triple.of("RH Account #", -1.0, -1),
            Triple.of("RH Name", 300.0, -1),
            Triple.of("Wr Wrk Inst", -1.0, -1),
            Triple.of("System Title", 300.0, -1),
            Triple.of("Standard Number", 125.0, -1),
            Triple.of("Standard Number Type", 155.0, -1),
            Triple.of("Fiscal Year", -1.0, -1),
            Triple.of("Payment Date", -1.0, -1),
            Triple.of("Title", 300.0, -1),
            Triple.of("Article", 135.0, -1),
            Triple.of("Publisher", 135.0, -1),
            Triple.of("Pub Date", 80.0, -1),
            Triple.of("Number of Copies", -1.0, -1),
            Triple.of("Reported Value", 113.0, -1),
            Triple.of("Gross Amt in USD", 130.0, -1),
            Triple.of("Batch Amt in USD", 130.0, -1),
            Triple.of("Market", 135.0, -1),
            Triple.of("Market Period From", 150.0, -1),
            Triple.of("Market Period To", 145.0, -1),
            Triple.of("Author", 300.0, -1),
            Triple.of("Comment", 200.0, -1)
        ));
        verifyWindow(grid, null, 100, 100, Unit.PERCENTAGE);
        verifyButtonsLayout(content.getComponent(2), "Ok", "Cancel");
        HorizontalLayout horizontalLayout = (HorizontalLayout) content.getComponent(2);
        assertTrue(horizontalLayout.isSpacing());
        assertEquals(new MarginInfo(false, false, false, false), horizontalLayout.getMargin());
    }

    @Test
    public void testGridValues() {
        mockStatic(JavaScript.class);
        RefreshScenarioWindow window = new RefreshScenarioWindow(query -> usages.stream(), query -> 1, null);
        expect(JavaScript.getCurrent()).andReturn(createMock(JavaScript.class)).times(2);
        replay(JavaScript.class);
        Grid<?> grid = (Grid<?>) ((VerticalLayout) window.getContent()).getComponent(1);
        Object[][] expectedCells = {
            {"377594f8-a247-4277-b29f-0b33ff360a7e", UsageStatusEnum.PAID, "FAS", "Paid batch", 1000000004L,
                "Computers for Design and Construction", 1000002859L, "John Wiley & Sons - Books", 243904752L,
                "100 ROAD MOVIES", "1008902112317555XX", "VALISBN13", "FY2021", "02/12/2021", "100 ROAD MOVIES",
                "some article", "some publisher", "02/13/2021", 2, "3,000.00", "500.00", "1,000.00", "lib", 1980, 2000,
                "author", "usage from usages_10.csv"}
        };
        verifyGridItems(grid, usages, expectedCells);
        verify(JavaScript.class);
    }

    @Test
    public void testOnOkButtonClick() {
        IFasScenariosController controller = createMock(IFasScenariosController.class);
        RefreshScenarioWindow window = new RefreshScenarioWindow(value -> null, value -> 0, controller);
        VerticalLayout layout = (VerticalLayout) window.getContent();
        HorizontalLayout buttonsLayout = (HorizontalLayout) layout.getComponent(2);
        Button okButton = (Button) buttonsLayout.getComponent(0);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        expect(controller.getInvalidRightsholders()).andReturn(Collections.emptyList()).once();
        controller.refreshScenario();
        expectLastCall().once();
        replay(controller, clickEvent);
        Collection<?> listeners = okButton.getListeners(ClickEvent.class);
        assertEquals(1, listeners.size());
        ClickListener clickListener = (ClickListener) listeners.iterator().next();
        clickListener.buttonClick(clickEvent);
        verify(controller, clickEvent);
    }

    @Test
    public void testOnOkButtonClickInvalidRightsholders() {
        IFasScenariosController controller = createMock(IFasScenariosController.class);
        RefreshScenarioWindow window = new RefreshScenarioWindow(value -> null, value -> 0, controller);
        VerticalLayout layout = (VerticalLayout) window.getContent();
        HorizontalLayout buttonsLayout = (HorizontalLayout) layout.getComponent(2);
        Button okButton = (Button) buttonsLayout.getComponent(0);
        mockStatic(Windows.class);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        expect(controller.getInvalidRightsholders()).andReturn(Collections.singletonList(100000000L)).once();
        Windows.showNotificationWindow("Scenario cannot be refreshed. The following rightsholder(s) are absent in " +
            "PRM: <i><b>[100000000]</b></i>");
        replay(controller, clickEvent, Windows.class);
        Collection<?> listeners = okButton.getListeners(ClickEvent.class);
        assertEquals(1, listeners.size());
        ClickListener clickListener = (ClickListener) listeners.iterator().next();
        clickListener.buttonClick(clickEvent);
        verify(controller, clickEvent, Windows.class);
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
