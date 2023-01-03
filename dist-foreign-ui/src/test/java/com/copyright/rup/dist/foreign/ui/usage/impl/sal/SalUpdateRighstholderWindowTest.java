package com.copyright.rup.dist.foreign.ui.usage.impl.sal;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.validateFieldAndVerifyErrorMessage;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyLoadClickListener;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyTextField;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.newCapture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.ui.usage.api.sal.ISalUsageController;
import com.copyright.rup.vaadin.ui.component.window.ConfirmActionDialogWindow;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.google.common.collect.Lists;
import com.vaadin.data.Binder;
import com.vaadin.data.HasValue;
import com.vaadin.data.Validator;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * Verifies {@link SalUpdateRighstholderWindow}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 11/30/20
 *
 * @author Darya Baraukova
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class, SalUpdateRighstholderWindowTest.class})
public class SalUpdateRighstholderWindowTest {

    private static final Set<String> USAGE_IDS = Collections.singleton("ebad7d68-b213-433f-8dbe-a581f6ba55a3");
    private static final Long RH_ACCOUNT_NUMBER = 2000047356L;
    private static final String RH_NAME = "National Geographic Partners";
    private static final String REASON = "Reason";
    private static final String RH_ACCOUNT_NUMBER_FIELD_NAME = "rhAccountNumberField";
    private ISalUsageController usageController;
    private SalDetailForRightsholderUpdateWindow detailsWindow;
    private SalUpdateRighstholderWindow window;

    @Before
    public void setUp() {
        usageController = createMock(ISalUsageController.class);
        detailsWindow = createMock(SalDetailForRightsholderUpdateWindow.class);
    }

    @Test
    public void testConstructor() {
        replay(usageController);
        window = new SalUpdateRighstholderWindow(usageController, detailsWindow, USAGE_IDS);
        assertEquals("Update Rightsholder", window.getCaption());
        assertEquals(440, window.getWidth(), 0);
        assertEquals(Sizeable.Unit.PIXELS, window.getWidthUnits());
        assertEquals(190, window.getHeight(), 0);
        assertEquals(Sizeable.Unit.PIXELS, window.getHeightUnits());
        Component content = window.getContent();
        assertThat(content, instanceOf(VerticalLayout.class));
        VerticalLayout contentLayout = (VerticalLayout) content;
        assertEquals(2, contentLayout.getComponentCount());
        Component rhLayout = contentLayout.getComponent(0);
        verifyRightsholderLayout(rhLayout);
        Component buttonsLayout = contentLayout.getComponent(1);
        verifyButtonsLayout(buttonsLayout);
        verify(usageController);
    }

    @Test
    public void testVerifyButtonClick() {
        Rightsholder rh = new Rightsholder();
        rh.setAccountNumber(RH_ACCOUNT_NUMBER);
        rh.setName(RH_NAME);
        expect(usageController.getRightsholder(RH_ACCOUNT_NUMBER)).andReturn(rh).once();
        expect(usageController.getRightsholder(20000170L)).andReturn(new Rightsholder()).once();
        replay(usageController);
        window = new SalUpdateRighstholderWindow(usageController, detailsWindow, USAGE_IDS);
        Component content = window.getContent();
        VerticalLayout rootLayout = (VerticalLayout) content;
        assertEquals(2, rootLayout.getComponentCount());
        VerticalLayout rhLayout = (VerticalLayout) rootLayout.getComponent(0);
        HorizontalLayout horizontalLayout = (HorizontalLayout) rhLayout.getComponent(0);
        TextField numberField = (TextField) horizontalLayout.getComponent(0);
        Button verifyButton = (Button) horizontalLayout.getComponent(1);
        TextField nameField = (TextField) rhLayout.getComponent(1);
        assertVerifyButton(verifyButton, numberField, nameField);
        verify(usageController);
    }

    @Test
    public void testSaveButtonClickNotSetValues() {
        mockStatic(Windows.class);
        Button.ClickEvent clickEvent = createMock(Button.ClickEvent.class);
        Rightsholder rh = new Rightsholder();
        rh.setAccountNumber(RH_ACCOUNT_NUMBER);
        rh.setName(RH_NAME);
        window = new SalUpdateRighstholderWindow(usageController, detailsWindow, USAGE_IDS);
        Whitebox.setInternalState(window, "rh", rh);
        Collection<? extends AbstractField<?>> fields = Lists.newArrayList(
            Whitebox.getInternalState(window, RH_ACCOUNT_NUMBER_FIELD_NAME),
            Whitebox.getInternalState(window, "rhNameField"));
        Windows.showValidationErrorWindow(fields);
        expectLastCall().once();
        replay(clickEvent, usageController, Windows.class);
        Component content = window.getContent();
        assertThat(content, instanceOf(VerticalLayout.class));
        VerticalLayout contentLayout = (VerticalLayout) content;
        assertEquals(2, contentLayout.getComponentCount());
        Component rhLayout = contentLayout.getComponent(0);
        verifyRightsholderLayout(rhLayout);
        HorizontalLayout buttonsLayout = (HorizontalLayout) contentLayout.getComponent(1);
        Button saveButton = (Button) buttonsLayout.getComponent(0);
        Collection<?> listeners = saveButton.getListeners(Button.ClickEvent.class);
        assertEquals(1, listeners.size());
        Button.ClickListener clickListener = (Button.ClickListener) listeners.iterator().next();
        clickListener.buttonClick(clickEvent);
        verify(clickEvent, usageController, Windows.class);
    }

    @Test
    public void testSaveButtonClick() {
        mockStatic(Windows.class);
        Button.ClickEvent clickEvent = createMock(Button.ClickEvent.class);
        Rightsholder rh = new Rightsholder();
        rh.setAccountNumber(RH_ACCOUNT_NUMBER);
        rh.setName(RH_NAME);
        Binder<UsageDto> binder = createMock(Binder.class);
        window = new SalUpdateRighstholderWindow(usageController, detailsWindow, USAGE_IDS);
        Whitebox.setInternalState(window, "usageBinder", binder);
        Whitebox.setInternalState(window, RH_ACCOUNT_NUMBER_FIELD_NAME,
            new TextField("RH Account #", RH_ACCOUNT_NUMBER.toString()));
        Whitebox.setInternalState(window, "rh", rh);
        Capture<ConfirmActionDialogWindow.IListener> actionDialogListenerCapture = newCapture();
        expect(binder.isValid()).andReturn(true).once();
        Windows.showConfirmDialogWithReason(eq("Confirm action"),
            eq("Are you sure you want to update selected detail with RH 2000047356?"), eq("Yes"), eq("Cancel"),
                capture(actionDialogListenerCapture), anyObject(Validator.class));
        expectLastCall().once();
        usageController.updateToEligibleWithRhAccountNumber(USAGE_IDS, RH_ACCOUNT_NUMBER, REASON);
        expectLastCall().once();
        usageController.refreshWidget();
        expectLastCall().once();
        detailsWindow.refreshDataProvider();
        expectLastCall().once();
        replay(clickEvent, usageController, detailsWindow, binder, Windows.class);
        Component content = window.getContent();
        assertThat(content, instanceOf(VerticalLayout.class));
        VerticalLayout contentLayout = (VerticalLayout) content;
        assertEquals(2, contentLayout.getComponentCount());
        Component rhLayout = contentLayout.getComponent(0);
        verifyRightsholderLayout(rhLayout);
        HorizontalLayout buttonsLayout = (HorizontalLayout) contentLayout.getComponent(1);
        Button saveButton = (Button) buttonsLayout.getComponent(0);
        Collection<?> listeners = saveButton.getListeners(Button.ClickEvent.class);
        assertEquals(1, listeners.size());
        Button.ClickListener clickListener = (Button.ClickListener) listeners.iterator().next();
        clickListener.buttonClick(clickEvent);
        actionDialogListenerCapture.getValue().onActionConfirmed(REASON);
        verify(clickEvent, usageController, detailsWindow, binder, Windows.class);
    }

    @Test
    public void testSaveButtonClickRhWithSpaces() {
        mockStatic(Windows.class);
        Button.ClickEvent clickEvent = createMock(Button.ClickEvent.class);
        Rightsholder rh = new Rightsholder();
        rh.setAccountNumber(38042L);
        rh.setName("Hopkins and Carley");
        Binder<UsageDto> binder = createMock(Binder.class);
        window = new SalUpdateRighstholderWindow(usageController, detailsWindow, USAGE_IDS);
        Whitebox.setInternalState(window, "usageBinder", binder);
        Whitebox.setInternalState(window, RH_ACCOUNT_NUMBER_FIELD_NAME,
            new TextField("RH Account #", "  38042  "));
        Whitebox.setInternalState(window, "rh", rh);
        Capture<ConfirmActionDialogWindow.IListener> actionDialogListenerCapture = newCapture();
        expect(binder.isValid()).andReturn(true).once();
        Windows.showConfirmDialogWithReason(eq("Confirm action"),
            eq("Are you sure you want to update selected detail with RH 38042?"), eq("Yes"), eq("Cancel"),
            capture(actionDialogListenerCapture), anyObject(Validator.class));
        expectLastCall().once();
        usageController.updateToEligibleWithRhAccountNumber(USAGE_IDS, 38042L, REASON);
        expectLastCall().once();
        usageController.refreshWidget();
        expectLastCall().once();
        detailsWindow.refreshDataProvider();
        expectLastCall().once();
        replay(clickEvent, usageController, detailsWindow, binder, Windows.class);
        Component content = window.getContent();
        assertThat(content, instanceOf(VerticalLayout.class));
        VerticalLayout contentLayout = (VerticalLayout) content;
        assertEquals(2, contentLayout.getComponentCount());
        Component rhLayout = contentLayout.getComponent(0);
        verifyRightsholderLayout(rhLayout);
        HorizontalLayout buttonsLayout = (HorizontalLayout) contentLayout.getComponent(1);
        Button saveButton = (Button) buttonsLayout.getComponent(0);
        Collection<?> listeners = saveButton.getListeners(Button.ClickEvent.class);
        assertEquals(1, listeners.size());
        Button.ClickListener clickListener = (Button.ClickListener) listeners.iterator().next();
        clickListener.buttonClick(clickEvent);
        actionDialogListenerCapture.getValue().onActionConfirmed(REASON);
        verify(clickEvent, usageController, detailsWindow, binder, Windows.class);
    }

    @Test
    public void testRhAccountNumberValidation() {
        replay(usageController);
        window = new SalUpdateRighstholderWindow(usageController, detailsWindow, USAGE_IDS);
        Binder binder = Whitebox.getInternalState(window, "usageBinder");
        TextField rhAccountNumberField = Whitebox.getInternalState(window, "rhAccountNumberField");
        validateFieldAndVerifyErrorMessage(
            rhAccountNumberField, StringUtils.EMPTY, binder, "Field value should be specified", false);
        validateFieldAndVerifyErrorMessage(
            rhAccountNumberField, "10000000000", binder, "Field value should not exceed 10 digits", false);
        validateFieldAndVerifyErrorMessage(
            rhAccountNumberField, "value", binder, "Field value should contain numeric values only", false);
        validateFieldAndVerifyErrorMessage(rhAccountNumberField, "1", binder, null, true);
        validateFieldAndVerifyErrorMessage(rhAccountNumberField, "1000000000", binder, null, true);
        verify(usageController);
    }

    private void verifyRightsholderLayout(Component component) {
        assertThat(component, instanceOf(VerticalLayout.class));
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(2, verticalLayout.getComponentCount());
        HorizontalLayout horizontalLayout = (HorizontalLayout) verticalLayout.getComponent(0);
        assertEquals(2, horizontalLayout.getComponentCount());
        TextField numberField = verifyTextField(horizontalLayout.getComponent(0), "RH Account #");
        Collection<?> listeners = numberField.getListeners(HasValue.ValueChangeEvent.class);
        assertTrue(CollectionUtils.isNotEmpty(listeners));
        assertEquals(2, listeners.size());
        TextField nameField = verifyTextField(verticalLayout.getComponent(1), "RH Name");
        assertTrue(nameField.isReadOnly());
        Component verifyComponent = horizontalLayout.getComponent(1);
        assertThat(verifyComponent, instanceOf(Button.class));
        Button verifyButton = (Button) verifyComponent;
        assertEquals("Verify", verifyComponent.getCaption());
        assertEquals(1, verifyButton.getListeners(Button.ClickEvent.class).size());
    }

    private void assertVerifyButton(Button verifyButton, TextField numberField, TextField nameField) {
        assertEquals(StringUtils.EMPTY, nameField.getValue());
        verifyButton.click();
        assertEquals(StringUtils.EMPTY, nameField.getValue());
        numberField.setValue("value");
        verifyButton.click();
        assertEquals(StringUtils.EMPTY, nameField.getValue());
        numberField.setValue("123456789874541246");
        verifyButton.click();
        assertEquals(StringUtils.EMPTY, nameField.getValue());
        numberField.setValue("2000047356");
        verifyButton.click();
        assertEquals("National Geographic Partners", nameField.getValue());
        numberField.setValue("20000170");
        verifyButton.click();
        assertEquals(StringUtils.EMPTY, nameField.getValue());
    }

    private void verifyButtonsLayout(Component component) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        Button saveButton = verifyButton(layout.getComponent(0), "Save");
        verifyButton(layout.getComponent(1), "Close");
        assertEquals(1, saveButton.getListeners(Button.ClickEvent.class).size());
        verifyLoadClickListener(saveButton, Lists.newArrayList(
            Whitebox.getInternalState(window, RH_ACCOUNT_NUMBER_FIELD_NAME),
            Whitebox.getInternalState(window, "rhNameField")));
    }

    private Button verifyButton(Component component, String caption) {
        assertThat(component, instanceOf(Button.class));
        assertEquals(caption, component.getCaption());
        return (Button) component;
    }
}
