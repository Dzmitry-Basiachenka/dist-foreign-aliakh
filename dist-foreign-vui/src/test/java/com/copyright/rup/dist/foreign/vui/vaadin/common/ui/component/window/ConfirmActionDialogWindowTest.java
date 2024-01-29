package com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window;

import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getDialogContent;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getFooterComponent;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyButtonsLayout;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.data.value.ValueChangeMode;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.List;
import java.util.function.Consumer;

/**
 * Verifies {@link ConfirmActionDialogWindow}.
 * <p/>
 * Copyright (C) 2015 copyright.com
 * <p/>
 * Date: 11/11/15
 *
 * @author Mikalai Bezmen
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Windows.class)
public class ConfirmActionDialogWindowTest {

    private static final String WINDOW_CAPTION = "Window Caption";
    private static final String WINDOW_MESSAGE = "Window Message";
    private static final String CONFIRM_BUTTON_CAPTION = "Confirm Button";
    private static final String DECLINE_BUTTON_CAPTION = "Decline Button";
    private static final String SPECIFY_VALUE_ERROR_MESSAGE = "Please specify field value";

    @Test
    public void testConfirmDialogWidgetWithBlankCaption() {
        Consumer<String> reasonConsumer = createMock(Consumer.class);
        ConfirmActionDialogWindow confirmActionDialogWindow = new ConfirmActionDialogWindow(reasonConsumer,
            StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, List.of());
        verifyConfirmDialogWidgetContent(
            confirmActionDialogWindow, reasonConsumer, "Confirm action", StringUtils.EMPTY, ValueChangeMode.ON_CHANGE);
        verifyButtonsLayout(getFooterComponent(confirmActionDialogWindow, 1), true, "Yes", "Cancel");
    }

    @Test
    public void testConfirmDialogWidgetWithNonBlankCaption() {
        Consumer<String> reasonConsumer = createMock(Consumer.class);
        ConfirmActionDialogWindow confirmActionDialogWindow = new ConfirmActionDialogWindow(reasonConsumer,
            WINDOW_CAPTION, WINDOW_MESSAGE, CONFIRM_BUTTON_CAPTION, DECLINE_BUTTON_CAPTION, List.of());
        verifyConfirmDialogWidgetContent(
            confirmActionDialogWindow, reasonConsumer, WINDOW_CAPTION, WINDOW_MESSAGE, ValueChangeMode.ON_CHANGE);
        verifyButtonsLayout(
            getFooterComponent(confirmActionDialogWindow, 1), true, CONFIRM_BUTTON_CAPTION, DECLINE_BUTTON_CAPTION);
    }

    @Test
    public void testConfirmDialogWidgetWithValidators() {
        Consumer<String> reasonConsumer = createMock(Consumer.class);
        ConfirmActionDialogWindow confirmActionDialogWindow = new ConfirmActionDialogWindow(reasonConsumer,
            StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY,
            List.of(new StringLengthValidator(SPECIFY_VALUE_ERROR_MESSAGE, 1, null)));
        verifyConfirmDialogWidgetContent(
            confirmActionDialogWindow, reasonConsumer, "Confirm action", StringUtils.EMPTY, ValueChangeMode.LAZY);
        verifyButtonsLayout(getFooterComponent(confirmActionDialogWindow, 1), true, "Yes", "Cancel");
    }

    @Test
    public void testConfirmButtonListener() {
        Consumer<String> reasonConsumer = createMock(Consumer.class);
        ConfirmActionDialogWindow confirmActionDialogWindow = new ConfirmActionDialogWindow(reasonConsumer,
            StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, List.of());
        reasonConsumer.accept(StringUtils.EMPTY);
        expectLastCall().once();
        replay(reasonConsumer);
        Button confirmButton = getConfirmButton(confirmActionDialogWindow);
        confirmButton.click();
        Binder<String> binder = Whitebox.getInternalState(confirmActionDialogWindow, "binder");
        assertTrue(binder.isValid());
        assertTrue(confirmActionDialogWindow.getParent().isEmpty());
        verify(reasonConsumer);
    }

    @Test
    public void testShowValidationErrorWindow() {
        mockStatic(Windows.class);
        Consumer<String> reasonConsumer = createMock(Consumer.class);
        Windows.showValidationErrorWindow();
        expectLastCall().once();
        replay(Windows.class);
        ConfirmActionDialogWindow confirmActionDialogWindow = new ConfirmActionDialogWindow(reasonConsumer,
            WINDOW_CAPTION, WINDOW_MESSAGE, CONFIRM_BUTTON_CAPTION, DECLINE_BUTTON_CAPTION,
            List.of(new StringLengthValidator(SPECIFY_VALUE_ERROR_MESSAGE, 1, null)));
        Button confirmButton = getConfirmButton(confirmActionDialogWindow);
        confirmButton.click();
        Binder<String> binder = Whitebox.getInternalState(confirmActionDialogWindow, "binder");
        assertFalse(binder.isValid());
        TextField reasonField = Whitebox.getInternalState(confirmActionDialogWindow, "reasonField");
        assertEquals(SPECIFY_VALUE_ERROR_MESSAGE, reasonField.getErrorMessage());
        verify(Windows.class);
    }

    private void verifyConfirmDialogWidgetContent(ConfirmActionDialogWindow window, Consumer<String> reasonConsumer,
                                                  String headerTitle, String labelText,
                                                  ValueChangeMode valueChangeMode) {
        assertNotNull(window);
        assertEquals("confirm-action-dialog-window", window.getId().orElseThrow());
        assertEquals("confirm-action-dialog-window", window.getClassName());
        assertEquals(reasonConsumer, Whitebox.getInternalState(window, "action"));
        assertEquals(headerTitle, window.getHeaderTitle());
        assertFalse(window.isResizable());
        assertEquals("550.0px", window.getWidth());
        assertEquals(Unit.PIXELS, window.getWidthUnit().orElseThrow());
        Component component = getDialogContent(window);
        assertNotNull(component);
        assertEquals(VerticalLayout.class, component.getClass());
        VerticalLayout contentLayout = (VerticalLayout) component;
        assertEquals(2, contentLayout.getComponentCount());
        assertNotNull(contentLayout);
        assertEquals("100%", contentLayout.getWidth());
        assertEquals(Unit.PERCENTAGE, contentLayout.getWidthUnit().orElseThrow());
        assertTrue(contentLayout.isSpacing());
        assertEquals(2, contentLayout.getComponentCount());
        verifyContentLabelComponent(contentLayout.getComponentAt(0), labelText);
        verifyReasonTextFiledComponent(contentLayout.getComponentAt(1), valueChangeMode);
    }

    private void verifyContentLabelComponent(Component component, String labelText) {
        assertNotNull(component);
        assertEquals(Html.class, component.getClass());
        Html windowLabel = (Html) component;
        assertEquals(labelText, windowLabel.getInnerHtml());
    }

    private void verifyReasonTextFiledComponent(Component component, ValueChangeMode valueChangeMode) {
        assertNotNull(component);
        assertEquals(TextField.class, component.getClass());
        TextField reasonTextField = (TextField) component;
        assertEquals("Reason", reasonTextField.getLabel());
        assertEquals(valueChangeMode, reasonTextField.getValueChangeMode());
    }

    private Button getConfirmButton(Dialog dialog) {
        return (Button) ((HorizontalLayout) getFooterComponent(dialog, 1)).getComponentAt(0);
    }
}
