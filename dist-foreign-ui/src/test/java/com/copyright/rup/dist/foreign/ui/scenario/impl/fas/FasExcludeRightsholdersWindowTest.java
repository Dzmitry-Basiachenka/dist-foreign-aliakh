package com.copyright.rup.dist.foreign.ui.scenario.impl.fas;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGridItems;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.RightsholderPayeePair;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IFasScenarioController;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.google.common.collect.Lists;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.SerializablePredicate;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.VerticalLayout;

import org.junit.Before;
import org.junit.Test;
import org.powermock.api.easymock.PowerMock;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.Collections;
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

    private final List<RightsholderPayeePair> rightsholderPayeePairs = Lists.newArrayList(
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
        verifyWindow(window, "Exclude RH Details for Source RRO #: 1000009522", 830, 500, Unit.PIXELS);
        VerticalLayout content = (VerticalLayout) window.getContent();
        assertEquals(3, content.getComponentCount());
        verifyGrid(content.getComponent(1));
        verifyButtonsLayout(content.getComponent(2), "Confirm", "Clear", "Close");
    }

    @Test
    public void testGridValues() {
        Grid<?> grid = (Grid<?>) ((VerticalLayout) window.getContent()).getComponent(1);
        Object[][] expectedCells = {
            {2000148821L, "ABR Company, Ltd", 1000033963L, "Alfred R. Lindesmith",},
            {2000196395L, "Advance Central Services", 7000425474L, "American Dialect Society"}
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
        ListDataProvider provider = new ListDataProvider(Collections.EMPTY_LIST);
        expect(grid.getDataProvider()).andReturn(provider).once();
        expect(searchWidget.getSearchValue()).andReturn("1000033963").once();
        PowerMock.replay(searchWidget, grid);
        window.performSearch();
        SerializablePredicate filter = provider.getFilter();
        assertTrue(filter.test(buildRightsholderPayeePair(
            buildRightsholder(1000033963L, "Alfred R. Lindesmith"),
            buildRightsholder(2000148821L, "ABR Company, Ltd"))));
        assertFalse(filter.test(buildRightsholderPayeePair(
            buildRightsholder(7000425474L, "American Dialect Society"),
            buildRightsholder(2000196395L, "Advance Central Services"))));
        PowerMock.verify(searchWidget, grid);
    }

    private void verifyGrid(Component component) {
        assertEquals(Grid.class, component.getClass());
        Grid grid = (Grid) component;
        List<Column> columns = grid.getColumns();
        assertEquals(Arrays.asList("Payee Account #", "Payee Name", "RH Account #", "RH Name"),
            columns.stream().map(Column::getCaption).collect(Collectors.toList()));
    }

    private RightsholderPayeePair buildRightsholderPayeePair(Rightsholder rightsholder, Rightsholder payee) {
        RightsholderPayeePair pair = new RightsholderPayeePair();
        pair.setPayee(payee);
        pair.setRightsholder(rightsholder);
        return pair;
    }

    private Rightsholder buildRightsholder(Long accountNumber, String name) {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(accountNumber);
        rightsholder.setName(name);
        return rightsholder;
    }
}
