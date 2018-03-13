package com.copyright.rup.dist.foreign.ui.scenario.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.RightsholderPayeePair;
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
 * Verifies search functionality on {@link ExcludeRightsholdersWindow}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 11/9/17
 *
 * @author Ihar Suvorau
 */
@RunWith(Parameterized.class)
public class ExcludeRightsholdersSearchTest {

    private static final List<RightsholderPayeePair> CONTAINER_DATA = Lists.newArrayList(
        buildPair(1000000413L, "Times Mirror Magazines, Inc. [T]", 1000004155L, "Spectator Limited"),
        buildPair(1000004191L, "Klasing & Co [T]", 1000004271L, "Keith-Stevens Inc"),
        buildPair(7000425425L, "Kelton Publications", 7000425807L, "Desktop Communications"),
        buildPair(7000427902L, "Bruggemann US Inc.", 7000427971L, "New York State College of Ceramics [T]"));
    private final String value;
    private final Set<RightsholderPayeePair> expectedResult;
    private ExcludeRightsholdersWindow window;

    /**
     * Constructor.
     *
     * @param value          value to verify
     * @param expectedResult expected result
     */
    public ExcludeRightsholdersSearchTest(String value, Set<RightsholderPayeePair> expectedResult) {
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
                {"Spectator Limited", Collections.singleton(CONTAINER_DATA.get(0))},
                {"State College", Collections.singleton(CONTAINER_DATA.get(3))},
                {"desktop coMmUniCations", Collections.singleton(CONTAINER_DATA.get(0))},
                {"Keith-St evens Inc", Collections.emptySet()},
                {"7000427902", Collections.singleton(CONTAINER_DATA.get(3))},
                {"70004 27902", Collections.emptySet()},
                {"10000", Sets.newHashSet(CONTAINER_DATA.get(0), CONTAINER_DATA.get(1))},
                {"42", Sets.newHashSet(CONTAINER_DATA.get(1), CONTAINER_DATA.get(2), CONTAINER_DATA.get(3))},
                {"7000425807", Sets.newHashSet(CONTAINER_DATA.get(2))},
                {"100000 4271", Collections.emptySet()}
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
        IScenarioController scenarioController = createMock(ScenarioController.class);
        expect(scenarioController.getRightsholdersPayeePairs(1000009522L))
            .andReturn(Collections.emptyList()).once();
        replay(scenarioController);
        window = new ExcludeRightsholdersWindow(1000009522L, scenarioController);
        verify(scenarioController);
    }

    @Test
    // Test case IDs: 'eb607c0b-0398-47cb-b492-e7adf1de6bc2', '4d96b57d-b1c0-44e2-b24c-29db8b1aef3d',
    // '6322bcf8-05cb-4a27-b97d-0332671edecc', '62e035f1-8d07-4121-b489-52028dcbfe7d'
    public void testSearch() {
        BeanContainer<Long, RightsholderPayeePair> container = buildContainer();
        Whitebox.setInternalState(window, container);
        SearchWidget searchWidget = createMock(SearchWidget.class);
        Whitebox.setInternalState(window, searchWidget);
        expect(searchWidget.getSearchValue()).andReturn(value).once();
        replay(searchWidget);
        window.performSearch();
        assertEquals(expectedResult.size(), container.size());
        expectedResult.forEach(result -> assertEquals(result,
            container.getItem(result.getRightsholder().getAccountNumber()).getBean()));
        verify(searchWidget);
    }

    private BeanContainer<Long, RightsholderPayeePair> buildContainer() {
        BeanContainer<Long, RightsholderPayeePair> container = new BeanContainer<>(RightsholderPayeePair.class);
        container.addNestedContainerBean("payee");
        container.addNestedContainerBean("rightsholder");
        container.setBeanIdProperty("rightsholder.accountNumber");
        container.addAll(CONTAINER_DATA);
        return container;
    }
}
