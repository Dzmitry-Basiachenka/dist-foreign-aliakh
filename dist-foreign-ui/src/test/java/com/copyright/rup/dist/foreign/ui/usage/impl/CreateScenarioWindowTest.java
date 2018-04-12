package com.copyright.rup.dist.foreign.ui.usage.impl;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesController;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

/**
 * Verifies {@link CreateScenarioWindow}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 3/15/2017
 *
 * @author Mikalai Bezmen
 */
public class CreateScenarioWindowTest {

    private static final String DATE =
        CommonDateUtils.format(LocalDate.now(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT);
    private CreateScenarioWindow createScenarioWindow;

    @Before
    public void setUp() {
        IUsagesController controller = createMock(IUsagesController.class);
        IScenarioService scenarioService = createMock(IScenarioService.class);
        expect(controller.getSelectedProductFamily()).andReturn("FAS").once();
        expect(controller.getScenarioService()).andReturn(scenarioService).times(1);
        expect(scenarioService.scenarioExists("FAS Distribution " + DATE)).andReturn(false).once();
        replay(controller, scenarioService);
        createScenarioWindow = new CreateScenarioWindow(controller);
        verify(controller, scenarioService);
    }

    @Test
    public void testComponentStructure() {
        assertEquals("Create Scenario", createScenarioWindow.getCaption());
        assertEquals(320, createScenarioWindow.getWidth(), 0);
        assertEquals("create-scenario-window", createScenarioWindow.getId());
        VerticalLayout content = (VerticalLayout) createScenarioWindow.getContent();
        assertNotNull(content);
        assertTrue(content.isSpacing());
        assertEquals(3, content.getComponentCount());
        verifyScenarioNameField(content.getComponent(0));
        verifyDescriptionArea(content.getComponent(1));
        verifyButtonsLayout(content.getComponent(2));
    }

    private void verifyScenarioNameField(Component component) {
        assertNotNull(component);
        TextField scenarioNameField = (TextField) component;
        assertEquals("Scenario name", scenarioNameField.getCaption());
        assertEquals(String.format("FAS Distribution %s", CommonDateUtils.format(LocalDate.now(),
            RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT)), scenarioNameField.getValue());
        assertEquals("scenario-name", scenarioNameField.getId());
    }

    private void verifyDescriptionArea(Component component) {
        assertNotNull(component);
        TextArea descriptionArea = (TextArea) component;
        assertEquals("Description", descriptionArea.getCaption());
        assertEquals("scenario-description", descriptionArea.getId());
    }

    private void verifyButtonsLayout(Component component) {
        assertNotNull(component);
        HorizontalLayout buttonsLayout = (HorizontalLayout) component;
        assertTrue(buttonsLayout.isSpacing());
        assertEquals(2, buttonsLayout.getComponentCount());
        verifyButton(buttonsLayout.getComponent(0), "Confirm");
        verifyButton(buttonsLayout.getComponent(1), "Cancel");
    }

    private void verifyButton(Component component, String caption) {
        assertNotNull(component);
        Button cancelButton = (Button) component;
        assertEquals(caption, cancelButton.getCaption());
    }
}
