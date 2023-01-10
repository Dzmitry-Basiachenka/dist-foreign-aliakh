package com.copyright.rup.dist.foreign.ui.scenario.impl.fas;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IFasScenarioController;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.google.common.collect.Lists;
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

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Verifies search functionality on {@link FasExcludeSourceRroWindow}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 11/9/17
 *
 * @author Ihar Suvorau
 */
@RunWith(Parameterized.class)
public class FasExcludeSourceRroSearchTest {

    private static final List<Rightsholder> CONTAINER_DATA = Lists.newArrayList(
        buildRightsholder(1000000413L, "Times Mirror Magazines, Inc. [T]"),
        buildRightsholder(1000004191L, "Klasing & Co [T]"),
        buildRightsholder(7000425425L, "Kelton Publications"),
        buildRightsholder(7000427902L, "Bruggemann US Inc."));
    private final String value;
    private final Set<Rightsholder> expectedResult;
    private FasExcludeSourceRroWindow window;

    /**
     * Constructor.
     *
     * @param value          value to verify
     * @param expectedResult expected result
     */
    public FasExcludeSourceRroSearchTest(String value, Set<Rightsholder> expectedResult) {
        this.value = value;
        this.expectedResult = expectedResult;
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(
            new Object[][]{{"Times Mirror Magazines, Inc. [T]", Set.of(CONTAINER_DATA.get(0))},
                {"Klasing", Set.of(CONTAINER_DATA.get(1))},
                {"Invalid name", Collections.emptySet()},
                {"Bru ggemann US Inc.", Collections.emptySet()},
                {"BruGgemAnn us Inc.", Set.of(CONTAINER_DATA.get(3))},
                {"7000427902", Set.of(CONTAINER_DATA.get(3))},
                {"70004 27902", Collections.emptySet()},
                {"7000427905", Collections.emptySet()},
                {"10000", Sets.newHashSet(CONTAINER_DATA.get(0), CONTAINER_DATA.get(1))}
            }
        );
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
        expect(scenarioController.getSourceRros()).andReturn(List.of()).once();
        replay(scenarioController);
        window = new FasExcludeSourceRroWindow(scenarioController);
        verify(scenarioController);
    }

    @Test
    @SuppressWarnings("unchecked")
    // Test cases IDs: '351993c0-bba7-449a-b2a5-65b69615979f', '7f2cb98c-7cce-4c7c-9d5a-110128ba0264'
    public void testSearch() {
        SearchWidget searchWidget = createMock(SearchWidget.class);
        Whitebox.setInternalState(window, searchWidget);
        Grid grid = createMock(Grid.class);
        Whitebox.setInternalState(window, grid);
        ListDataProvider provider = new ListDataProvider(Collections.EMPTY_LIST);
        expect(grid.getDataProvider()).andReturn(provider).once();
        expect(searchWidget.getSearchValue()).andReturn(value).once();
        replay(searchWidget, grid);
        window.performSearch();
        SerializablePredicate filter = provider.getFilter();
        expectedResult.forEach(result -> assertTrue(filter.test(result)));
        verify(searchWidget, grid);
    }
}
