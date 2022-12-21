package com.copyright.rup.dist.foreign.ui.common;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.newCapture;
import static org.easymock.EasyMock.same;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.FilterSaveEvent;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.data.ValueProvider;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * Verifies {@link ScenarioFilterWidget}.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 06/17/2020
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class})
public class ScenarioFilterWidgetTest {

    private static final String SCENARIO_NAME = "FAS Scenario 2019";
    private static final String SCENARIO_ID = "e704a7d6-5a87-486c-b5ce-e3cbbf833932";

    private ScenarioFilterWidget scenarioFilterWidget;

    @Before
    public void setUp() {
        scenarioFilterWidget = new ScenarioFilterWidget(Collections::emptyList);
    }

    @Test
    public void testGetBeanClass() {
        assertEquals(Scenario.class, scenarioFilterWidget.getBeanClass());
    }

    @Test
    public void testGetBeanItemCaption() {
        assertEquals(SCENARIO_NAME, scenarioFilterWidget.getBeanItemCaption(buildScenario()));
    }

    @Test
    public void testOnSave() {
        FilterSaveEvent filterSaveEvent = createMock(FilterSaveEvent.class);
        expect(filterSaveEvent.getSelectedItemsIds()).andReturn(Collections.singleton(SCENARIO_NAME)).once();
        replay(filterSaveEvent);
        scenarioFilterWidget.onSave(filterSaveEvent);
        verify(filterSaveEvent);
    }

    @Test
    public void testShowFilterWindow() {
        FilterWindow filterWindow = createMock(FilterWindow.class);
        mockStatic(Windows.class);
        Capture<ValueProvider<Scenario, List<String>>> providerCapture = newCapture();
        Windows.showFilterWindow(eq("Scenarios filter"), same(scenarioFilterWidget), capture(providerCapture));
        expectLastCall().andReturn(filterWindow).once();
        filterWindow.setSelectedItemsIds(new HashSet<>());
        expectLastCall().once();
        expect(filterWindow.getId()).andReturn("id").once();
        filterWindow.addStyleName("scenarios-filter-window");
        expectLastCall().once();
        filterWindow.setSearchPromptString("Enter Scenario Name");
        expectLastCall().once();
        replay(filterWindow, Windows.class);
        scenarioFilterWidget.showFilterWindow();
        assertEquals(Collections.singletonList(SCENARIO_NAME), providerCapture.getValue().apply(buildScenario()));
        verify(filterWindow, Windows.class);
    }

    private Scenario buildScenario() {
        Scenario scenario = new Scenario();
        scenario.setName(SCENARIO_NAME);
        scenario.setId(SCENARIO_ID);
        return scenario;
    }
}
