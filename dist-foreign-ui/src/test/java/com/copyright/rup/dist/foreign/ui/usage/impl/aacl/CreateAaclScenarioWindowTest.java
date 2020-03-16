package com.copyright.rup.dist.foreign.ui.usage.impl.aacl;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.UsageAge;
import com.copyright.rup.dist.foreign.ui.usage.api.aacl.IAaclUsageController;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationResult;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Verifies {@link CreateAaclScenarioWindow}.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 03/11/2020
 *
 * @author Stanislau Rudak
 */
public class CreateAaclScenarioWindowTest {

    private static final String DATE =
        CommonDateUtils.format(LocalDate.now(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT);
    private static final String SCENARIO_NAME = "AACL Distribution " + DATE;
    private static final String AACL_PRODUCT_FAMILY = "AACL";
    private FundPool fundPool;

    private IAaclUsageController controller;
    private CreateAaclScenarioWindow window;

    @Before
    public void setUp() {
        controller = createMock(IAaclUsageController.class);
        fundPool = new FundPool();
        fundPool.setName("Fund Pool 1");
        expect(controller.getSelectedProductFamily()).andReturn(AACL_PRODUCT_FAMILY).anyTimes();
        expect(controller.getFundPools()).andReturn(Collections.singletonList(fundPool)).once();
        expect(controller.getUsageAges()).andReturn(Collections.singletonList(new UsageAge())).once();
        expect(controller.getPublicationTypes()).andReturn(Collections.singletonList(new PublicationType())).once();
    }

    @Test
    public void testComponentStructure() {
        expect(controller.scenarioExists(SCENARIO_NAME)).andReturn(false).once();
        replay(controller);
        window = new CreateAaclScenarioWindow(controller);
        verify(controller);
        assertEquals("Create Scenario", window.getCaption());
        assertEquals(320, window.getWidth(), 0);
        assertEquals("create-scenario-window", window.getId());
        VerticalLayout content = (VerticalLayout) window.getContent();
        assertNotNull(content);
        assertTrue(content.isSpacing());
        assertEquals(8, content.getComponentCount());
        verifyScenarioNameField(content.getComponent(0));
        verifyTitleCutoffAmountField(content.getComponent(1));
        verifyFundPoolComboBox(content.getComponent(2), fundPool);
        verifyScenarioParameterWidget(content.getComponent(3), "Usage Age Weights");
        verifyScenarioParameterWidget(content.getComponent(4), "Pub Type Weights");
        verifyScenarioParameterWidget(content.getComponent(5), "Licensee Class Mapping");
        verifyDescriptionArea(content.getComponent(6));
        verifyButtonsLayout(content.getComponent(7));
    }

    @Test
    public void testButtonCloseClick() {
        // TODO {srudak} implement
    }

    @Test
    public void testScenarioNameExists() {
        expect(controller.scenarioExists(SCENARIO_NAME)).andReturn(true).times(4);
        replay(controller);
        window = new CreateAaclScenarioWindow(controller);
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

    private void verifyTitleCutoffAmountField(Component component) {
        assertNotNull(component);
        TextField titleCutoffAmountField = (TextField) component;
        assertEquals("Title Cutoff Amount", titleCutoffAmountField.getCaption());
        assertEquals("50", titleCutoffAmountField.getValue());
    }

    private void verifyFundPoolComboBox(Component component, FundPool expectedFundPool) {
        assertNotNull(component);
        ComboBox<FundPool> fundPoolComboBox = (ComboBox<FundPool>) component;
        assertEquals("Fund Pool", fundPoolComboBox.getCaption());
        ListDataProvider<FundPool> listDataProvider = (ListDataProvider<FundPool>) fundPoolComboBox.getDataProvider();
        Collection<?> actualFundPool = listDataProvider.getItems();
        assertEquals(1, actualFundPool.size());
        assertEquals(expectedFundPool.getName(), fundPoolComboBox.getItemCaptionGenerator().apply(expectedFundPool));
    }

    private void verifyScenarioParameterWidget(Component component, String expectedCaption) {
        assertNotNull(component);
        AaclScenarioParameterWidget widget = (AaclScenarioParameterWidget) component;
        assertEquals(expectedCaption, widget.getComponent(0).getCaption());
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
}
