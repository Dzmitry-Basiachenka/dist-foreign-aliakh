package com.copyright.rup.dist.foreign.vui.main;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.integration.IntegrationConnectionException;
import com.copyright.rup.dist.common.integration.rest.prm.PrmConfigurationException;
import com.copyright.rup.dist.foreign.vui.UiTestHelper;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.downloader.FileDownloadException;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.ErrorWindow;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.ErrorEvent;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ResourceBundle;

/**
 * Verifies {@link ForeignErrorHandler}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 12/01/17
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ExceptionUtils.class})
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
public class ForeignErrorHandlerTest {

    private static final String ERROR_MESSAGE = "Not valid parameters passed";
    private static final String INTEGRATION_CONNECTION_ERROR_MESSAGE =
        "The application is currently not available due" +
            " to a system issue or routine maintenance. Please submit an IT Help Desk request and try again later.";
    private static final String EXPECTED_PRM_MESSAGE = "Process cannot be finished.<br>" +
        "Please check configuration in PRM: RHs have several identical preferences.";

    private ForeignErrorHandler errorHandler;

    @Before
    public void setUp() {
        mockStatic(ExceptionUtils.class);
        errorHandler = new ForeignErrorHandler(new TestMessageSource());
    }

    @Test
    public void testErrorWithIntegrationConnectionException() {
        ErrorEvent event =
            new ErrorEvent(new IntegrationConnectionException(INTEGRATION_CONNECTION_ERROR_MESSAGE, new Throwable()));
        Dialog widget = createMock(ErrorWindow.class);
        expect(ExceptionUtils.indexOfType(event.getThrowable(), IntegrationConnectionException.class)).andReturn(0)
            .once();
        replay(ErrorWindow.class, ExceptionUtils.class, widget);
        Dialog dialog = errorHandler.initErrorWindow(event);
        assertEquals(INTEGRATION_CONNECTION_ERROR_MESSAGE, getNotificationMessage(dialog));
        assertEquals("Connection problem", dialog.getHeaderTitle());
        verify(ErrorWindow.class, widget, ExceptionUtils.class);
    }

    @Test
    public void testCauseFileDownloadException() {
        ErrorEvent event = new ErrorEvent(new Throwable());
        Dialog widget = createMock(ErrorWindow.class);
        expect(ExceptionUtils.indexOfType(event.getThrowable(), IntegrationConnectionException.class)).andReturn(-1)
            .once();
        expect(ExceptionUtils.indexOfType(event.getThrowable(), FileDownloadException.class)).andReturn(0).once();
        replay(ErrorWindow.class, ExceptionUtils.class, widget);
        Dialog dialog = errorHandler.initErrorWindow(event);
        assertEquals("Sorry, couldn't generate report. Please try again later", getNotificationMessage(dialog));
        verify(ErrorWindow.class, widget, ExceptionUtils.class);
    }

    @Test
    public void testCausePrmConfigurationException() {
        ErrorEvent event =
            new ErrorEvent(new IntegrationConnectionException(ERROR_MESSAGE, new Throwable()));
        Dialog widget = createMock(ErrorWindow.class);
        expect(ExceptionUtils.indexOfType(event.getThrowable(), IntegrationConnectionException.class)).andReturn(-1)
            .once();
        expect(ExceptionUtils.indexOfType(event.getThrowable(), FileDownloadException.class)).andReturn(-1).once();
        expect(ExceptionUtils.indexOfType(event.getThrowable(), PrmConfigurationException.class)).andReturn(0)
            .once();
        replay(ErrorWindow.class, ExceptionUtils.class, widget);
        Dialog dialog = errorHandler.initErrorWindow(event);
        assertEquals(EXPECTED_PRM_MESSAGE, getNotificationMessage(dialog));
        assertEquals("Notification Window", dialog.getHeaderTitle());
        verify(ErrorWindow.class, widget, ExceptionUtils.class);
    }

    private String getNotificationMessage(Dialog dialog) {
        return ((Html) ((VerticalLayout) UiTestHelper.getDialogContent(dialog)).getComponentAt(0)).getInnerHtml();
    }

    private static class TestMessageSource implements IMessageSource {

        private static final ResourceBundle MESSAGES =
            ResourceBundle.getBundle("com.copyright.rup.dist.foreign.vui.messages");

        @Override
        public String getStringMessage(String key, Object... parameters) {
            return ArrayUtils.isNotEmpty(parameters) ? String.format(MESSAGES.getString(key), parameters)
                : MESSAGES.getString(key);
        }
    }
}
