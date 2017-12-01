package com.copyright.rup.dist.foreign.ui.main;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import com.copyright.rup.dist.common.domain.BusinessValidationException;
import com.copyright.rup.vaadin.ui.ErrorWindow;
import com.copyright.rup.vaadin.ui.NotificationWindow;
import com.copyright.rup.vaadin.ui.component.downloader.FileDownloadException;

import com.vaadin.server.ErrorEvent;
import com.vaadin.ui.UI;
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
    private UI ui;
    private ForeignErrorHandler lmErrorHandler;

    @Before
    public void setUp() {
        ui = createMock(UI.class);
        lmErrorHandler = new ForeignErrorHandler(ui);
    }

    @Test
    public void testError() {
        ui.addWindow(anyObject(ErrorWindow.class));
        expectLastCall().once();
        replay(ui);
        lmErrorHandler.error(new ErrorEvent(new Throwable(ERROR_MESSAGE)));
        verify(ui);
    }

    @Test
    public void testErrorWithBusinessValidationException() {
        ui.addWindow(anyObject(ErrorWindow.class));
        expectLastCall().once();
        replay(ui);
        lmErrorHandler.error(new ErrorEvent(new BusinessValidationException(ERROR_MESSAGE, ERROR_MESSAGE)));
        verify(ui);
    }

    @Test
    public void testCauseFileDownloadException() {
        ForeignErrorHandler errorHandler = new ForeignErrorHandler(ui);
        Capture<Window> captureErrorWindow = new Capture<>();
        ui.addWindow(capture(captureErrorWindow));
        expectLastCall().once();
        replay(ui);
        errorHandler.error(new ErrorEvent(new FileDownloadException(new IOException())));
        verify(ui);
        Window window = captureErrorWindow.getValue();
        assertNotNull(window);
        assertSame(NotificationWindow.class, window.getClass());
    }
}
