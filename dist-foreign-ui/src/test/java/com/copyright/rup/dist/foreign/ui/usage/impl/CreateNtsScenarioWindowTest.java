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

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationResult;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Verifies {@link CreateNtsScenarioWindow}.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 04/15/2019
 *
 * @author Aliaksandr Liakh
 */
public class CreateNtsScenarioWindowTest {

    private static final String DATE =
        CommonDateUtils.format(LocalDate.now(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT);
    private static final String SCENARIO_NAME = "NTS Distribution " + DATE;
    private static final String NTS_PRODUCT_FAMILY = "NTS";

    private CreateNtsScenarioWindow window;

    @Test
    public void testComponentStructure() {
        IUsagesController controller = createMock(IUsagesController.class);
        IScenarioService scenarioService = createMock(IScenarioService.class);
        expect(controller.getSelectedProductFamily()).andReturn(NTS_PRODUCT_FAMILY).once();
        expect(controller.getScenarioService()).andReturn(scenarioService).times(1);
        expect(scenarioService.scenarioExists(SCENARIO_NAME)).andReturn(false).once();
        replay(controller, scenarioService);
        window = new CreateNtsScenarioWindow(controller);
        verify(controller, scenarioService);
        assertEquals("Create Scenario", window.getCaption());
        assertEquals(320, window.getWidth(), 0);
        assertEquals("create-scenario-window", window.getId());
        VerticalLayout content = (VerticalLayout) window.getContent();
        assertNotNull(content);
        assertTrue(content.isSpacing());
        assertEquals(4, content.getComponentCount());
        verifyScenarioNameField(content.getComponent(0));
        verifyRhMinimumAmountField(content.getComponent(1));
        verifyDescriptionArea(content.getComponent(2));
        verifyButtonsLayout(content.getComponent(3));
    }

    @Test
    public void testScenarioNameExists() {
        IUsagesController controller = createMock(IUsagesController.class);
        IScenarioService scenarioService = createMock(IScenarioService.class);
        expect(controller.getSelectedProductFamily()).andReturn(NTS_PRODUCT_FAMILY).once();
        expect(controller.getScenarioService()).andReturn(scenarioService).times(4);
        expect(scenarioService.scenarioExists(SCENARIO_NAME)).andReturn(true).times(4);
        replay(controller, scenarioService);
        window = new CreateNtsScenarioWindow(controller);
        TextField scenarioNameField = Whitebox.getInternalState(window, "scenarioNameField");
        Binder binder = Whitebox.getInternalState(window, "binder");
        validateScenarioNameExistence(scenarioNameField, binder, SCENARIO_NAME);
        validateScenarioNameExistence(scenarioNameField, binder, ' ' + SCENARIO_NAME + ' ');
        verify(controller, scenarioService);
    }

    private void validateScenarioNameExistence(TextField scenarioNameField, Binder binder, String scenarioName) {
        scenarioNameField.setValue(scenarioName);
        List<String> errorMessages = ((List<ValidationResult>) binder.validate().getValidationErrors())
            .stream().map(ValidationResult::getErrorMessage).collect(Collectors.toList());
        assertEquals(1, errorMessages.size());
        assertEquals("Scenario with such name already exists", errorMessages.get(0));
    }

    private void verifyScenarioNameField(Component component) {
        assertNotNull(component);
        TextField scenarioNameField = (TextField) component;
        assertEquals("Scenario name", scenarioNameField.getCaption());
        assertEquals(SCENARIO_NAME, scenarioNameField.getValue());
        assertEquals("scenario-name", scenarioNameField.getId());
    }

    private void verifyRhMinimumAmountField(Component component) {
        assertNotNull(component);
        TextField rhMinimumAmountField = (TextField) component;
        assertEquals("RH Minimum Amount", rhMinimumAmountField.getCaption());
        assertEquals("300", rhMinimumAmountField.getValue());
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
