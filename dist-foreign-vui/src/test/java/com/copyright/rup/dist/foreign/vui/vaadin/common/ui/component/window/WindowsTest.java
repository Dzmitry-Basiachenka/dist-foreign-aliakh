package com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.newCapture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.createNicePartialMock;
import static org.powermock.api.easymock.PowerMock.createPartialMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.vui.UiTestHelper;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.filter.FilterWindow;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.filter.IFilterWindowController;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import org.easymock.Capture;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Collections;

/**
 * Verifies {@link Windows} utility class.
 * <p/>
 * Copyright (C) 2014 copyright.com
 * <p/>
 * Date: 6/19/14
 *
 * @author Nikita Levyankov
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Dialog.class, Windows.class, ConfirmDialogWindow.class})
public class WindowsTest {

    private static final String WINDOW_CAPTION = "caption";
    private static final String WINDOW_MESSAGE = "message";
    private static final String SHOW_MODAL_WINDOW_METHOD_NAME = "showModalWindow";

    @Test(expected = NullPointerException.class)
    public void testShowNullModalWindow() {
        Windows.showModalWindow(null);
    }

    @Test
    public void testShowModalWindow() {
        mockStatic(Dialog.class);
        Dialog window = createMock(Dialog.class);
        window.setModal(true);
        window.open();
        expectLastCall().once();
        replay(Dialog.class, window);
        Windows.showModalWindow(window);
        verify(Dialog.class, window);
    }

    @Test
    public void showNotificationWindowWithCaptionAndMessage() {
        Capture<NotificationWindow> widgetCapture = newCapture();
        createNicePartialMock(Windows.class, SHOW_MODAL_WINDOW_METHOD_NAME, Dialog.class);
        Windows.showModalWindow(capture(widgetCapture));
        expectLastCall().once();
        replay(Windows.class);
        Windows.showNotificationWindow(WINDOW_CAPTION, WINDOW_MESSAGE);
        verify(Windows.class);
        NotificationWindow notificationWindow = widgetCapture.getValue();
        assertEquals(WINDOW_CAPTION, notificationWindow.getHeaderTitle());
        VerticalLayout widgetLayout = (VerticalLayout) UiTestHelper.getDialogContent(notificationWindow);
        assertEquals(WINDOW_MESSAGE, ((Html) widgetLayout.getComponentAt(0)).getInnerHtml());
    }

    @Test
    public void testShowConfirmDialogWithMessageAndListener() {
        ConfirmDialogWindow
            confirmDialogWindow = createMock(ConfirmDialogWindow.class);
        IConfirmCancelListener confirmDialogController = createMock(IConfirmCancelListener.class);
        Capture<String> messageCapture = newCapture();
        Capture<IConfirmCancelListener> confirmDialogControllerCapture = newCapture();
        createPartialMock(Windows.class, "showConfirmDialog", String.class, String.class, String.class,
            String.class, IConfirmCancelListener.class);
        expect(Windows.showConfirmDialog(anyObject(String.class), capture(messageCapture), anyObject(String.class),
            anyObject(String.class), capture(confirmDialogControllerCapture))).andReturn(confirmDialogWindow).once();
        replay(Windows.class, confirmDialogWindow);
        assertSame(confirmDialogWindow, Windows.showConfirmDialog(WINDOW_MESSAGE, confirmDialogController));
        verify(Windows.class, confirmDialogWindow);
        assertSame(WINDOW_MESSAGE, messageCapture.getValue());
        assertSame(confirmDialogController, confirmDialogControllerCapture.getValue());
    }

    @Test
    public void testShowFilterWindow() {
        String caption = "Caption";
        Capture<FilterWindow> filterWindowCapture = newCapture();
        createPartialMock(Windows.class, SHOW_MODAL_WINDOW_METHOD_NAME, Dialog.class);
        IFilterWindowController controller = createMock(IFilterWindowController.class);
        expect(controller.loadBeans()).andReturn(Collections.emptyList()).once();
        Windows.showModalWindow(capture(filterWindowCapture));
        replay(controller, Windows.class);
        FilterWindow filterWindow = Windows.showFilterWindow(caption, controller);
        assertNotNull(filterWindow);
        assertEquals(caption, filterWindow.getHeaderTitle());
        verify(controller, Windows.class);
    }
}
