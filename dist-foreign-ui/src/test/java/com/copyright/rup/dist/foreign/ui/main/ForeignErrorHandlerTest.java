package com.copyright.rup.dist.foreign.ui.main;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.newCapture;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import com.copyright.rup.dist.common.integration.IntegrationConnectionException;
import com.copyright.rup.dist.common.integration.rest.prm.PrmConfigurationException;
import com.copyright.rup.vaadin.ui.component.downloader.FileDownloadException;
import com.copyright.rup.vaadin.ui.component.window.ErrorWindow;
import com.copyright.rup.vaadin.ui.component.window.NotificationWindow;

import com.vaadin.server.ErrorEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Verifies {@link ForeignErrorHandler}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 12/01/17
 *
 * @author Ihar Suvorau
 */
public class ForeignErrorHandlerTest {

    private static final String ERROR_MESSAGE = "Not valid parameters passed";
    private static final String CONNECTION_ACCESSIBILITY_MESSAGE = "The application is currently not available due " +
        "to a system issue or routine maintenance. Please submit an IT Help Desk request and try again later.";
    private static final String CONNECTION_PROBLEM_CAPTION = "Connection problem";
    private static final String EXPECTED_PRM_MESSAGE = "Process cannot be finished.<br>" +
        "Please check configuration in PRM: RHs have several identical preferences.";
    private static final String COULD_NOT_GENERATE_REPORT_MESSAGE = "Sorry, couldn't generate report. Please try " +
        "again later";

    private ForeignCommonUi ui;

    @Before
    public void setUp() {
        ui = createMock(ForeignCommonUi.class);
    }

    @Test
    public void testError() {
        ui.addWindow(anyObject(ErrorWindow.class));
        expectLastCall().once();
        replay(ui);
        new ForeignErrorHandler(ui).error(new ErrorEvent(new Throwable(ERROR_MESSAGE)));
        verify(ui);
    }

    @Test
    public void testErrorWithIntegrationConnectionException() {
        ForeignErrorHandler errorHandler = new ForeignErrorHandler(ui);
        expect(ui.getStringMessage("label.content.accessibility")).andReturn(CONNECTION_ACCESSIBILITY_MESSAGE).once();
        expect(ui.getStringMessage("window.caption.connection_problem")).andReturn(CONNECTION_PROBLEM_CAPTION).once();
        Capture<Window> captureErrorWindow = newCapture();
        ui.addWindow(capture(captureErrorWindow));
        expectLastCall().once();
        replay(ui);
        errorHandler.error(new ErrorEvent(new IntegrationConnectionException(ERROR_MESSAGE, new IOException())));
        Window window = captureErrorWindow.getValue();
        assertNotNull(window);
        assertSame(NotificationWindow.class, window.getClass());
        String message = ((Label) ((VerticalLayout) window.getContent()).getComponent(0)).getValue();
        assertEquals(CONNECTION_ACCESSIBILITY_MESSAGE, message);
        assertEquals(CONNECTION_PROBLEM_CAPTION, window.getCaption());
        verify(ui);
    }

    @Test
    public void testCauseFileDownloadException() {
        ForeignErrorHandler errorHandler = new ForeignErrorHandler(ui);
        expect(ui.getStringMessage("message.report.generate_error"))
            .andReturn(COULD_NOT_GENERATE_REPORT_MESSAGE).once();
        Capture<Window> captureErrorWindow = newCapture();
        ui.addWindow(capture(captureErrorWindow));
        expectLastCall().once();
        replay(ui);
        errorHandler.error(new ErrorEvent(new FileDownloadException(new IOException())));
        verify(ui);
        Window window = captureErrorWindow.getValue();
        assertNotNull(window);
        assertSame(NotificationWindow.class, window.getClass());
        String message = ((Label) ((VerticalLayout) window.getContent()).getComponent(0)).getValue();
        assertEquals(COULD_NOT_GENERATE_REPORT_MESSAGE, message);
    }

    @Test
    public void testCausePrmConfigurationException() {
        ForeignErrorHandler errorHandler = new ForeignErrorHandler(ui);
        expect(ui.getStringMessage("message.error.incorrect_prm_configuration")).andReturn(EXPECTED_PRM_MESSAGE).once();
        Capture<Window> captureErrorWindow = newCapture();
        ui.addWindow(capture(captureErrorWindow));
        expectLastCall().once();
        replay(ui);
        errorHandler.error(new ErrorEvent(new PrmConfigurationException(ERROR_MESSAGE)));
        verify(ui);
        Window window = captureErrorWindow.getValue();
        assertNotNull(window);
        assertSame(NotificationWindow.class, window.getClass());
        String message = ((Label) ((VerticalLayout) window.getContent()).getComponent(0)).getValue();
        assertEquals(EXPECTED_PRM_MESSAGE, message);
    }
}
