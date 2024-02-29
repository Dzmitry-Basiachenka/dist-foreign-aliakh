package com.copyright.rup.dist.foreign.vui.scenario.impl.fas;

import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyGridItems;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifySearchWidget;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.RightsholderPayeePair;
import com.copyright.rup.dist.foreign.vui.UiTestHelper;
import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IFasScenarioController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.SearchWidget;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.function.SerializablePredicate;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Verifies {@link FasExcludeRightsholdersWindow}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 11/2/2017
 *
 * @author Uladzislau_Shalamitski
 */
public class FasExcludeRightsholdersWindowTest {

    private final List<RightsholderPayeePair> rightsholderPayeePairs = List.of(
        buildRightsholderPayeePair(
            buildRightsholder(1000033963L, "Alfred R. Lindesmith"),
            buildRightsholder(2000148821L, "ABR Company, Ltd")),
        buildRightsholderPayeePair(
            buildRightsholder(7000425474L, "American Dialect Society"),
            buildRightsholder(2000196395L, "Advance Central Services")));
    private FasExcludeRightsholdersWindow window;

    @Before
    public void setUp() {
        IFasScenarioController scenarioController = createMock(IFasScenarioController.class);
        expect(scenarioController.getRightsholdersPayeePairs(1000009522L)).andReturn(rightsholderPayeePairs).once();
        replay(scenarioController);
        window = new FasExcludeRightsholdersWindow(1000009522L, scenarioController);
        verify(scenarioController);
    }

    @Test
    public void testStructure() {
        verifyWindow(window, "Exclude RH Details for Source RRO #: 1000009522", "830px", "500px", Unit.PIXELS, true);
        VerticalLayout content = (VerticalLayout) UiTestHelper.getDialogContent(window);
        assertEquals(2, content.getComponentCount());
        verifySearchWidget(content.getComponentAt(0), "Enter Payee/RH Name/Account #");
        verifyGrid(content.getComponentAt(1));
        verifyButtonsLayout(UiTestHelper.getFooterComponent(window, 1), true, "Confirm", "Clear", "Close");
    }

    @Test
    public void testGridValues() {
        Grid<?> grid = (Grid<?>) ((VerticalLayout) UiTestHelper.getDialogContent(window)).getComponentAt(1);
        Object[][] expectedCells = {
            {"2000148821", "ABR Company, Ltd", "1000033963", "Alfred R. Lindesmith",},
            {"2000196395", "Advance Central Services", "7000425474", "American Dialect Society"}
        };
        verifyGridItems(grid, rightsholderPayeePairs, expectedCells);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testPerformSearch() {
        SearchWidget searchWidget = createMock(SearchWidget.class);
        Whitebox.setInternalState(window, searchWidget);
        Grid grid = createMock(Grid.class);
        Whitebox.setInternalState(window, grid);
        ListDataProvider provider = new ListDataProvider(List.of());
        expect(grid.getDataProvider()).andReturn(provider).once();
        expect(searchWidget.getSearchValue()).andReturn("1000033963").once();
        replay(searchWidget, grid);
        window.performSearch();
        SerializablePredicate filter = provider.getFilter();
        assertTrue(filter.test(buildRightsholderPayeePair(
            buildRightsholder(1000033963L, "Alfred R. Lindesmith"),
            buildRightsholder(2000148821L, "ABR Company, Ltd"))));
        assertFalse(filter.test(buildRightsholderPayeePair(
            buildRightsholder(7000425474L, "American Dialect Society"),
            buildRightsholder(2000196395L, "Advance Central Services"))));
        verify(searchWidget, grid);
    }

    private void verifyGrid(Component component) {
        assertEquals(Grid.class, component.getClass());
        Grid grid = (Grid) component;
        List<Column> columns = grid.getColumns();
        assertEquals(List.of("Payee Account #", "Payee Name", "RH Account #", "RH Name"),
            columns.stream().map(Column::getHeaderText).collect(Collectors.toList()));
    }

    private RightsholderPayeePair buildRightsholderPayeePair(Rightsholder rightsholder, Rightsholder payee) {
        RightsholderPayeePair pair = new RightsholderPayeePair();
        pair.setPayee(payee);
        pair.setRightsholder(rightsholder);
        return pair;
    }

    private Rightsholder buildRightsholder(Long accountNumber, String name) {
        var rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(accountNumber);
        rightsholder.setName(name);
        return rightsholder;
    }
}
