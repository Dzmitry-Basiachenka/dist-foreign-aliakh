package com.copyright.rup.dist.foreign.ui.report.impl.acl;

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

import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.FilterSaveEvent;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.data.ValueProvider;

import org.easymock.Capture;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * Verifies {@link AclScenarioFilterWidget}.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p/>
 * Date: 10/05/2022
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class})
public class AclScenarioFilterWidgetTest {

    private static final String SCENARIO_NAME = "MACL Scenario 202212";
    private static final String SCENARIO_ID = "17a915c3-3f78-412b-8a00-29e7f5e9b965";

    private final AclScenarioFilterWidget scenarioFilterWidget = new AclScenarioFilterWidget(Collections::emptyList);

    @Test
    public void testGetBeanClass() {
        assertEquals(AclScenario.class, scenarioFilterWidget.getBeanClass());
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
        Capture<ValueProvider<AclScenario, List<String>>> providerCapture = newCapture();
        Windows.showFilterWindow(eq("Scenarios filter"), same(scenarioFilterWidget), capture(providerCapture));
        expectLastCall().andReturn(filterWindow).once();
        filterWindow.setSelectedItemsIds(new HashSet<>());
        expectLastCall().once();
        filterWindow.setSelectAllButtonVisible();
        expectLastCall().once();
        expect(filterWindow.getId()).andReturn("id").once();
        filterWindow.addStyleName("acl-scenarios-filter-window");
        expectLastCall().once();
        filterWindow.setSearchPromptString("Enter Scenario Name");
        expectLastCall().once();
        replay(filterWindow, Windows.class);
        scenarioFilterWidget.showFilterWindow();
        assertEquals(List.of(SCENARIO_NAME), providerCapture.getValue().apply(buildScenario()));
        verify(filterWindow, Windows.class);
    }

    private AclScenario buildScenario() {
        AclScenario scenario = new AclScenario();
        scenario.setName(SCENARIO_NAME);
        scenario.setId(SCENARIO_ID);
        return scenario;
    }
}
