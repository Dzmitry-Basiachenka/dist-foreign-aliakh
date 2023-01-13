package com.copyright.rup.dist.foreign.ui.scenario.impl.fas;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.RightsholderPayeePair;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IFasScenarioController;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.google.common.collect.Sets;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.SerializablePredicate;
import com.vaadin.ui.Grid;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.powermock.reflect.Whitebox;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Verifies search functionality on {@link FasExcludeRightsholdersWindow}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 11/9/17
 *
 * @author Ihar Suvorau
 */
@RunWith(Parameterized.class)
public class FasExcludeRightsholdersSearchTest {

    private static final List<RightsholderPayeePair> CONTAINER_DATA = List.of(
        buildPair(1000000413L, "Times Mirror Magazines, Inc. [T]", 1000004155L, "Spectator Limited"),
        buildPair(1000004191L, "Klasing & Co [T]", 1000004271L, "Keith-Stevens Inc"),
        buildPair(7000425425L, "Kelton Publications", 7000425807L, "Desktop Communications"),
        buildPair(7000427902L, "Bruggemann US Inc.", 7000427971L, "New York State College of Ceramics [T]"));
    private final String value;
    private final Set<RightsholderPayeePair> expectedResult;
    private FasExcludeRightsholdersWindow window;

    /**
     * Constructor.
     *
     * @param value          value to verify
     * @param expectedResult expected result
     */
    public FasExcludeRightsholdersSearchTest(String value, Set<RightsholderPayeePair> expectedResult) {
        this.value = value;
        this.expectedResult = expectedResult;
    }

    @Parameters
    public static Collection<Object[]> data() {
        return List.of(
            new Object[][]{{"Times Mirror Magazines, Inc. [T]", Set.of(CONTAINER_DATA.get(0))},
                {"Klasing", Set.of(CONTAINER_DATA.get(1))},
                {"Invalid name", Set.of()},
                {"Bru ggemann US Inc.", Set.of()},
                {"BruGgemAnn us Inc.", Set.of(CONTAINER_DATA.get(3))},
                {"Spectator Limited", Set.of(CONTAINER_DATA.get(0))},
                {"State College", Set.of(CONTAINER_DATA.get(3))},
                {"desktop coMmUniCations", Set.of(CONTAINER_DATA.get(2))},
                {"Keith-St evens Inc", Set.of()},
                {"7000427902", Set.of(CONTAINER_DATA.get(3))},
                {"70004 27902", Set.of()},
                {"10000", Sets.newHashSet(CONTAINER_DATA.get(0), CONTAINER_DATA.get(1))},
                {"42", Sets.newHashSet(CONTAINER_DATA.get(1), CONTAINER_DATA.get(2), CONTAINER_DATA.get(3))},
                {"7000425807", Sets.newHashSet(CONTAINER_DATA.get(2))},
                {"100000 4271", Set.of()}
            }
        );
    }

    private static RightsholderPayeePair buildPair(Long payeeAccountNumber, String payeeName, Long rhAccountNumber,
                                                   String rhName) {
        RightsholderPayeePair pair = new RightsholderPayeePair();
        pair.setPayee(buildRightsholder(payeeAccountNumber, payeeName));
        pair.setRightsholder(buildRightsholder(rhAccountNumber, rhName));
        return pair;
    }

    private static Rightsholder buildRightsholder(Long accountNumber, String name) {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(accountNumber);
        rightsholder.setName(name);
        return rightsholder;
    }

    @Before
    public void setUp() {
        IFasScenarioController scenarioController = createMock(IFasScenarioController.class);
        expect(scenarioController.getRightsholdersPayeePairs(1000009522L))
            .andReturn(CONTAINER_DATA).once();
        replay(scenarioController);
        window = new FasExcludeRightsholdersWindow(1000009522L, scenarioController);
        verify(scenarioController);
    }


    // Test case IDs: 'eb607c0b-0398-47cb-b492-e7adf1de6bc2', '4d96b57d-b1c0-44e2-b24c-29db8b1aef3d',
    // '6322bcf8-05cb-4a27-b97d-0332671edecc', '62e035f1-8d07-4121-b489-52028dcbfe7d'
    @Test
    @SuppressWarnings("unchecked")
    public void testSearch() {
        SearchWidget searchWidget = createMock(SearchWidget.class);
        Whitebox.setInternalState(window, searchWidget);
        Grid grid = createMock(Grid.class);
        Whitebox.setInternalState(window, grid);
        ListDataProvider provider = new ListDataProvider(List.of());
        expect(grid.getDataProvider()).andReturn(provider).once();
        expect(searchWidget.getSearchValue()).andReturn(value).once();
        replay(searchWidget, grid);
        window.performSearch();
        SerializablePredicate filter = provider.getFilter();
        expectedResult.forEach(result -> assertTrue(filter.test(result)));
        verify(searchWidget, grid);
    }
}
