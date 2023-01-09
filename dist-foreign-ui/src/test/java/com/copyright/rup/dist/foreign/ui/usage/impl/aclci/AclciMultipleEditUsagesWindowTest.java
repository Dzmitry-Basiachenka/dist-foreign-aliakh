package com.copyright.rup.dist.foreign.ui.usage.impl.aclci;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.validateFieldAndVerifyErrorMessage;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButton;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyLoadClickListener;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyTextField;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

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
import com.copyright.rup.dist.foreign.ui.usage.UiTestHelper;
import com.copyright.rup.dist.foreign.ui.usage.api.aclci.IAclciUsageController;
import com.copyright.rup.vaadin.ui.component.window.ConfirmActionDialogWindow.IListener;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.google.common.collect.Lists;
import com.vaadin.data.Binder;
import com.vaadin.data.HasValue;
import com.vaadin.data.Validator;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
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
import java.util.Objects;
import java.util.Set;

/**
 * Verifies {@link AclciMultipleEditUsagesWindow}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 12/30/2022
 *
 * @author Dzmitry Basiachenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Windows.class)
public class AclciMultipleEditUsagesWindowTest {

    private static final Set<String> USAGE_IDS = Set.of("d9070305-6bc3-46b8-903c-d4310aeedd9b");
    private static final Long RH_ACCOUNT_NUMBER = 2000047356L;
    private static final String RH_NAME = "National Geographic Partners";
    private static final String RH_ACCOUNT_NUMBER_FIELD_NAME = "rhAccountNumberField";
    private static final String WR_WRK_INST_FIELD_NAME = "wrWrkInstField";
    private static final String NUMBER_VALIDATION_MESSAGE = "Field value should contain numeric values only";
    private IAclciUsageController controller;
    private AclciUsageUpdateWindow usageUpdateWindow;
    private AclciMultipleEditUsagesWindow window;

    @Before
    public void setUp() {
        controller = createMock(IAclciUsageController.class);
        usageUpdateWindow = createMock(AclciUsageUpdateWindow.class);
    }

    @Test
    public void testConstructor() {
        replay(controller);
        window = new AclciMultipleEditUsagesWindow(controller, usageUpdateWindow, USAGE_IDS);
        verifyWindow(window, "Edit multiple ACLCI Usages", 440, 235, Unit.PIXELS);
        Component content = window.getContent();
        assertThat(content, instanceOf(VerticalLayout.class));
        VerticalLayout contentLayout = (VerticalLayout) content;
        assertEquals(4, contentLayout.getComponentCount());
        verifyRhAccountNumberLayout(contentLayout.getComponent(0));
        verifyRhNameField(contentLayout.getComponent(1));
        verifyTextField(contentLayout.getComponent(2), "Wr Wrk Inst");
        verifyButtonsLayout(contentLayout.getComponent(3));
        verify(controller);
    }

    @Test
    public void testVerifyButtonClick() {
        Rightsholder rh = buildRightsholder();
        expect(controller.getRightsholder(RH_ACCOUNT_NUMBER)).andReturn(rh).once();
        expect(controller.getRightsholder(20000170L)).andReturn(new Rightsholder()).once();
        replay(controller);
        window = new AclciMultipleEditUsagesWindow(controller, usageUpdateWindow, USAGE_IDS);
        Component content = window.getContent();
        VerticalLayout rootLayout = (VerticalLayout) content;
        HorizontalLayout rhAccountNumberLayout = (HorizontalLayout) rootLayout.getComponent(0);
        TextField numberField = (TextField) rhAccountNumberLayout.getComponent(0);
        Button verifyButton = (Button) rhAccountNumberLayout.getComponent(1);
        TextField nameField = (TextField) rootLayout.getComponent(1);
        assertVerifyButton(verifyButton, numberField, nameField);
        verify(controller);
    }

    @Test
    public void testSaveButtonClickNotSetValues() {
        mockStatic(Windows.class);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        Rightsholder rh = buildRightsholder();
        window = new AclciMultipleEditUsagesWindow(controller, usageUpdateWindow, USAGE_IDS);
        Whitebox.setInternalState(window, "rh", rh);
        Collection<? extends AbstractField<?>> fields = Lists.newArrayList(
            Whitebox.getInternalState(window, RH_ACCOUNT_NUMBER_FIELD_NAME),
            Whitebox.getInternalState(window, "rhNameField"),
            Whitebox.getInternalState(window, WR_WRK_INST_FIELD_NAME));
        Windows.showValidationErrorWindow(fields);
        expectLastCall().once();
        replay(clickEvent, controller, Windows.class);
        Component content = window.getContent();
        VerticalLayout contentLayout = (VerticalLayout) content;
        HorizontalLayout buttonsLayout = (HorizontalLayout) contentLayout.getComponent(3);
        Button saveButton = (Button) buttonsLayout.getComponent(0);
        Collection<?> listeners = saveButton.getListeners(Button.ClickEvent.class);
        assertEquals(1, listeners.size());
        Button.ClickListener clickListener = (Button.ClickListener) listeners.iterator().next();
        clickListener.buttonClick(clickEvent);
        verify(clickEvent, controller, Windows.class);
    }

    @Test
    public void testRhAccountNumberValidation() {
        replay(controller);
        window = new AclciMultipleEditUsagesWindow(controller, usageUpdateWindow, USAGE_IDS);
        Binder binder = Whitebox.getInternalState(window, "binder");
        TextField rhAccountNumberField = Whitebox.getInternalState(window, "rhAccountNumberField");
        validateFieldAndVerifyErrorMessage(
            rhAccountNumberField, StringUtils.EMPTY, binder, "Field value should be specified", false);
        validateFieldAndVerifyErrorMessage(
            rhAccountNumberField, "10000000000", binder, "Field value should not exceed 10 digits", false);
        validateFieldAndVerifyErrorMessage(
            rhAccountNumberField, "value", binder, "Field value should contain numeric values only", false);
        validateFieldAndVerifyErrorMessage(rhAccountNumberField, "1", binder, null, true);
        validateFieldAndVerifyErrorMessage(rhAccountNumberField, "1000000000", binder, null, true);
        verify(controller);
    }

    @Test
    public void testWrWrkInstValidation() {
        replay(controller);
        window = new AclciMultipleEditUsagesWindow(controller, usageUpdateWindow, USAGE_IDS);
        Binder binder = Whitebox.getInternalState(window, "binder");
        TextField wrWrkInstField = Whitebox.getInternalState(window, WR_WRK_INST_FIELD_NAME);
        validateFieldAndVerifyErrorMessage(wrWrkInstField, StringUtils.EMPTY, binder, null, true);
        validateFieldAndVerifyErrorMessage(wrWrkInstField, "25", binder, null, true);
        validateFieldAndVerifyErrorMessage(wrWrkInstField, " 1 ", binder, null, true);
        validateFieldAndVerifyErrorMessage(wrWrkInstField, "123456789", binder, null, true);
        validateFieldAndVerifyErrorMessage(wrWrkInstField, "1234567890", binder,
            "Field value should not exceed 9 digits", false);
        validateFieldAndVerifyErrorMessage(wrWrkInstField, "0.1", binder, NUMBER_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(wrWrkInstField, "   ", binder, NUMBER_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(wrWrkInstField, "12a", binder, NUMBER_VALIDATION_MESSAGE, false);
        verify(controller);
    }

    @Test
    public void testSaveButtonClick() {
        verifySaveButtonClick(210001899L);
    }

    @Test
    public void testSaveButtonClickEmptyWrWrlInst() {
        verifySaveButtonClick(null);
    }

    private void verifyRhAccountNumberLayout(Component component) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        HorizontalLayout horizontalLayout = (HorizontalLayout) component;
        assertEquals(2, horizontalLayout.getComponentCount());
        TextField numberField = verifyTextField(horizontalLayout.getComponent(0), "RH Account #");
        Collection<?> listeners = numberField.getListeners(HasValue.ValueChangeEvent.class);
        assertTrue(CollectionUtils.isNotEmpty(listeners));
        assertEquals(2, listeners.size());
        verifyTextField(numberField, "RH Account #");
        Button verifyButton = verifyButton(horizontalLayout.getComponent(1), "Verify", true);
        assertEquals(1, verifyButton.getListeners(Button.ClickEvent.class).size());
    }

    private void verifyRhNameField(Component component) {
        TextField nameField = verifyTextField(component, "RH Name");
        assertTrue(nameField.isReadOnly());
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
        UiTestHelper.verifyButtonsLayout(component, "Save", "Close");
        Button saveButton = (Button) ((HorizontalLayout) component).getComponent(0);
        assertEquals(1, saveButton.getListeners(Button.ClickEvent.class).size());
        verifyLoadClickListener(saveButton, Lists.newArrayList(
            Whitebox.getInternalState(window, RH_ACCOUNT_NUMBER_FIELD_NAME),
            Whitebox.getInternalState(window, "rhNameField"),
            Whitebox.getInternalState(window, WR_WRK_INST_FIELD_NAME)));
    }

    private Rightsholder buildRightsholder() {
        Rightsholder rh = new Rightsholder();
        rh.setAccountNumber(RH_ACCOUNT_NUMBER);
        rh.setName(RH_NAME);
        return rh;
    }

    @SuppressWarnings("unchecked")
    private void verifySaveButtonClick(Long wrWrkInst) {
        mockStatic(Windows.class);
        Button.ClickEvent clickEvent = createMock(Button.ClickEvent.class);
        Rightsholder rh = new Rightsholder();
        rh.setAccountNumber(RH_ACCOUNT_NUMBER);
        rh.setName(RH_NAME);
        Binder<UsageDto> binder = createMock(Binder.class);
        window = new AclciMultipleEditUsagesWindow(controller, usageUpdateWindow, USAGE_IDS);
        Whitebox.setInternalState(window, "binder", binder);
        Whitebox.setInternalState(window, RH_ACCOUNT_NUMBER_FIELD_NAME,
            new TextField("RH Account #", RH_ACCOUNT_NUMBER.toString()));
        Whitebox.setInternalState(window, WR_WRK_INST_FIELD_NAME,
            new TextField("Wr Wrk Inst", Objects.isNull(wrWrkInst) ? StringUtils.EMPTY : wrWrkInst.toString()));
        Whitebox.setInternalState(window, "rh", rh);
        Capture<IListener> actionDialogListenerCapture = newCapture();
        expect(binder.isValid()).andReturn(true).once();
        Windows.showConfirmDialogWithReason(eq("Confirm action"),
            eq("Are you sure you want to update selected usage(s)?"), eq("Yes"), eq("Cancel"),
            capture(actionDialogListenerCapture), anyObject(Validator.class));
        expectLastCall().once();
        controller.updateToEligibleByIds(USAGE_IDS, RH_ACCOUNT_NUMBER, wrWrkInst, "Reason");
        expectLastCall().once();
        controller.refreshWidget();
        expectLastCall().once();
        usageUpdateWindow.refreshDataProvider();
        expectLastCall().once();
        replay(clickEvent, controller, usageUpdateWindow, binder, Windows.class);
        Component content = window.getContent();
        assertThat(content, instanceOf(VerticalLayout.class));
        VerticalLayout contentLayout = (VerticalLayout) content;
        assertEquals(4, contentLayout.getComponentCount());
        HorizontalLayout buttonsLayout = (HorizontalLayout) contentLayout.getComponent(3);
        Button saveButton = (Button) buttonsLayout.getComponent(0);
        Collection<?> listeners = saveButton.getListeners(Button.ClickEvent.class);
        assertEquals(1, listeners.size());
        Button.ClickListener clickListener = (Button.ClickListener) listeners.iterator().next();
        clickListener.buttonClick(clickEvent);
        actionDialogListenerCapture.getValue().onActionConfirmed("Reason");
        verify(clickEvent, controller, usageUpdateWindow, binder, Windows.class);
    }
}
