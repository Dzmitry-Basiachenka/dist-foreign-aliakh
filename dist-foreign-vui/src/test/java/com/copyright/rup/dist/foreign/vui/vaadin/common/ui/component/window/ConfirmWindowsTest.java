package com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.newCapture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.createPartialMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.data.binder.Validator;

import org.easymock.Capture;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * Verifies {@link ConfirmWindows}.
 * <p/>
 * Copyright (C) 2023 copyright.com
 * <p/>
 * Date: 11/22/2023
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Dialog.class, Windows.class, ConfirmWindows.class, ConfirmDialogWindow.class})
public class ConfirmWindowsTest {

    private static final String WINDOW_CAPTION = "caption";
    private static final String WINDOW_MESSAGE = "message";
    private static final String SHOW_CONFIRM_DIALOG_WITH_REASON_METHOD_NAME = "showConfirmDialogWithReason";
    private static final String WINDOW_CONFIRM_CAPTION = "confirmCaption";
    private static final String WINDOW_DECLINE_CAPTION = "declineCaption";

    @Test
    public void testShowConfirmDialogWithReason() {
        Consumer<String> reasonConsumer = createMock(Consumer.class);
        Capture<Dialog> dialogCapture = newCapture();
        createPartialMock(Windows.class, "showModalWindow", Dialog.class);
        Windows.showModalWindow(capture(dialogCapture));
        expectLastCall().once();
        replay(Windows.class);
        ConfirmWindows.showConfirmDialogWithReason(null, WINDOW_MESSAGE, null, null,  reasonConsumer);
        assertEquals(ConfirmActionDialogWindow.class, dialogCapture.getValue().getClass());
        verify(Windows.class);
    }

    @Test
    public void testShowConfirmDialogWithoutValidator() {
        Consumer<String> reasonConsumer = createMock(Consumer.class);
        Capture<String> messageCapture = newCapture();
        Capture<Consumer<String>> reasonConsumerCapture = newCapture();
        Capture<List<Validator<String>>> validatorsCapture = newCapture();
        createPartialMock(ConfirmWindows.class, SHOW_CONFIRM_DIALOG_WITH_REASON_METHOD_NAME, String.class, String.class,
            String.class, String.class, Consumer.class, List.class);
        ConfirmWindows.showConfirmDialogWithReason(anyObject(String.class), capture(messageCapture),
            anyObject(String.class), anyObject(String.class), capture(reasonConsumerCapture),
            capture(validatorsCapture));
        expectLastCall().once();
        replay(ConfirmWindows.class);
        ConfirmWindows.showConfirmDialogWithReason(WINDOW_CAPTION, WINDOW_MESSAGE, WINDOW_CONFIRM_CAPTION,
            WINDOW_DECLINE_CAPTION, reasonConsumer);
        verify(ConfirmWindows.class);
        assertSame(WINDOW_MESSAGE, messageCapture.getValue());
        assertSame(reasonConsumer, reasonConsumerCapture.getValue());
        assertTrue(validatorsCapture.getValue().isEmpty());
    }

    @Test
    public void testShowConfirmDialogWithValidators() {
        Consumer<String> reasonConsumer = createMock(Consumer.class);
        List<Validator<String>> validators = Collections.singletonList(createMock(Validator.class));
        Capture<String> messageCapture = newCapture();
        Capture<Consumer<String>> reasonConsumerCapture = newCapture();
        Capture<List<Validator<String>>> validatorsCapture = newCapture();
        createPartialMock(ConfirmWindows.class, SHOW_CONFIRM_DIALOG_WITH_REASON_METHOD_NAME, String.class, String.class,
            String.class, String.class, Consumer.class, List.class);
        ConfirmWindows.showConfirmDialogWithReason(anyObject(String.class), capture(messageCapture),
            anyObject(String.class), anyObject(String.class), capture(reasonConsumerCapture),
            capture(validatorsCapture));
        expectLastCall().once();
        replay(ConfirmWindows.class);
        ConfirmWindows.showConfirmDialogWithReason(WINDOW_CAPTION, WINDOW_MESSAGE, WINDOW_CONFIRM_CAPTION,
            WINDOW_DECLINE_CAPTION, reasonConsumer, validators);
        assertSame(WINDOW_MESSAGE, messageCapture.getValue());
        assertSame(reasonConsumer, reasonConsumerCapture.getValue());
        assertSame(validators, validatorsCapture.getValue());
        verify(ConfirmWindows.class);
    }
}
