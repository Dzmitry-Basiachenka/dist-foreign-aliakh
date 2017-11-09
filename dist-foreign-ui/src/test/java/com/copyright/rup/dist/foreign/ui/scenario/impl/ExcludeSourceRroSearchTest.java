package com.copyright.rup.dist.foreign.ui.scenario.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenarioController;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.vaadin.data.util.BeanContainer;

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
 * Verifies search functionality on {@link ExcludeSourceRroWindow}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 11/9/17
 *
 * @author Ihar Suvorau
 */
@RunWith(Parameterized.class)
public class ExcludeSourceRroSearchTest {

    private static final List<Rightsholder> CONTAINER_DATA = Lists.newArrayList(
        buildRightsholder(1000000413L, "Times Mirror Magazines, Inc. [T]"),
        buildRightsholder(1000004191L, "Klasing & Co [T]"),
        buildRightsholder(7000425425L, "Kelton Publications"),
        buildRightsholder(7000427902L, "Bruggemann US Inc."));
    private String value;
    private Set<Rightsholder> expectedResult;
    private ExcludeSourceRroWindow window;

    /**
     * Constructor.
     *
     * @param value          value to verify
     * @param expectedResult expected result
     */
    public ExcludeSourceRroSearchTest(String value, Set<Rightsholder> expectedResult) {
        this.value = value;
        this.expectedResult = expectedResult;
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(
            new Object[][]{{"Times Mirror Magazines, Inc. [T]", Collections.singleton(CONTAINER_DATA.get(0))},
                {"Klasing", Collections.singleton(CONTAINER_DATA.get(1))},
                {"Invalid name", Collections.emptySet()},
                {"Bru ggemann US Inc.", Collections.emptySet()},
                {"BruGgemAnn us Inc.", Collections.singleton(CONTAINER_DATA.get(3))},
                {"7000427902", Collections.singleton(CONTAINER_DATA.get(3))},
                {"70004 27902", Collections.emptySet()},
                {"7000427905", Collections.emptySet()},
                {"10000", Sets.newHashSet(CONTAINER_DATA.get(0), CONTAINER_DATA.get(1))}
            }
        );
    }

    @Before
    public void setUp() {
        IScenarioController scenarioController = createMock(ScenarioController.class);
        expect(scenarioController.getSourceRros()).andReturn(Collections.emptyList()).once();
        replay(scenarioController);
        window = new ExcludeSourceRroWindow(scenarioController);
        verify(scenarioController);
    }

    @Test
    // Test cases IDs: '351993c0-bba7-449a-b2a5-65b69615979f', '7f2cb98c-7cce-4c7c-9d5a-110128ba0264'
    public void testSearch() {
        BeanContainer<Long, Rightsholder> container = buildContainer();
        Whitebox.setInternalState(window, container);
        SearchWidget searchWidget = createMock(SearchWidget.class);
        Whitebox.setInternalState(window, searchWidget);
        expect(searchWidget.getSearchValue()).andReturn(value).once();
        replay(searchWidget);
        window.performSearch();
        assertEquals(expectedResult.size(), container.size());
        expectedResult.forEach(result -> assertEquals(result, container.getItem(result.getAccountNumber()).getBean()));
        verify(searchWidget);
    }

    private BeanContainer<Long, Rightsholder> buildContainer() {
        BeanContainer<Long, Rightsholder> container = new BeanContainer<>(Rightsholder.class);
        container.setBeanIdProperty("accountNumber");
        container.addAll(CONTAINER_DATA);
        return container;
    }

    private static Rightsholder buildRightsholder(Long accountNumber, String name) {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(accountNumber);
        rightsholder.setName(name);
        return rightsholder;
    }
}
