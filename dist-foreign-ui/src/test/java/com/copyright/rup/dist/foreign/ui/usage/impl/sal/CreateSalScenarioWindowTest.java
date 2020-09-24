package com.copyright.rup.dist.foreign.ui.usage.impl.sal;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.ui.usage.api.sal.ISalUsageController;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationResult;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Verifies {@link CreateSalScenarioWindow}.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 09/23/2020
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class})
public class CreateSalScenarioWindowTest {

    private static final String DATE =
        CommonDateUtils.format(LocalDate.now(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT);
    private static final String SCENARIO_NAME = "SAL Distribution " + DATE;
    private static final String SAL_PRODUCT_FAMILY = "SAL";

    private ISalUsageController controller;
    private CreateSalScenarioWindow window;

    @Before
    public void setUp() {
        controller = createMock(ISalUsageController.class);
        expect(controller.getSelectedProductFamily()).andReturn(SAL_PRODUCT_FAMILY).anyTimes();
        expect(controller.getFundPoolsNotAttachedToScenario())
            .andReturn(Collections.singletonList(buildFundPool())).once();
    }

    @Test
    public void testComponentStructure() {
        expect(controller.scenarioExists(SCENARIO_NAME)).andReturn(false).once();
        replay(controller);
        window = new CreateSalScenarioWindow(controller);
        verify(controller);
        assertEquals("Create Scenario", window.getCaption());
        assertEquals(320, window.getWidth(), 0);
        assertEquals("create-scenario-window", window.getId());
        VerticalLayout content = (VerticalLayout) window.getContent();
        assertNotNull(content);
        assertTrue(content.isSpacing());
        assertEquals(4, content.getComponentCount());
        verifyScenarioNameField(content.getComponent(0));
        verifyFundPoolComboBox(content.getComponent(1));
        verifyDescriptionArea(content.getComponent(2));
        verifyButtonsLayout(content.getComponent(3));
    }

    @Test
    public void testScenarioNameExists() {
        expect(controller.scenarioExists(SCENARIO_NAME)).andReturn(true).times(4);
        replay(controller);
        window = new CreateSalScenarioWindow(controller);
        TextField scenarioNameField = Whitebox.getInternalState(window, "scenarioNameField");
        Binder binder = Whitebox.getInternalState(window, "scenarioBinder");
        validateScenarioNameExistence(scenarioNameField, binder, SCENARIO_NAME);
        validateScenarioNameExistence(scenarioNameField, binder, ' ' + SCENARIO_NAME + ' ');
        verify(controller);
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

    private void verifyFundPoolComboBox(Component component) {
        assertNotNull(component);
        ComboBox<FundPool> fundPoolComboBox = (ComboBox<FundPool>) component;
        assertEquals("Fund Pool", fundPoolComboBox.getCaption());
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

    private Button verifyButton(Component component, String caption) {
        assertNotNull(component);
        Button button = (Button) component;
        assertEquals(caption, button.getCaption());
        return button;
    }

    private FundPool buildFundPool() {
        FundPool fundPool = new FundPool();
        fundPool.setId("0493303b-4b33-47a8-83b4-0ba9f63189c2");
        fundPool.setName("SAL fund pool 2020");
        fundPool.setProductFamily(SAL_PRODUCT_FAMILY);
        return fundPool;
    }
}
