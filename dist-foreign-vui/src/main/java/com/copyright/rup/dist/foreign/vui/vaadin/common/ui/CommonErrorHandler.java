package com.copyright.rup.dist.foreign.vui.vaadin.common.ui;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.downloader.FileDownloadException;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.ErrorWindow;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.server.DefaultErrorHandler;
import com.vaadin.flow.server.ErrorEvent;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;

import java.util.Objects;

/**
 * Custom error handler for vaadin.
 * Logs error to the log and shows modal window.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 02/01/2023
 *
 * @author Anton Azarenka
 */
public class CommonErrorHandler extends DefaultErrorHandler {

    private static final long serialVersionUID = -1910981884092111300L;
    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Override
    public void error(ErrorEvent event) {
        if (isRequestAbortedByRemoteClient(event.getThrowable())) {
            LOGGER.info("Request is aborted by remote client", event.getThrowable());
        } else {
            LOGGER.error("Exception occurred", event.getThrowable());
        }
        Dialog errorWindow = Objects.requireNonNull(initErrorWindow(event), "Error window shouldn't be null");
        errorWindow.open();
    }

    /**
     * Initializes errorWindow. Subclasses may override this behavior.
     * {@link ErrorWindow} is returned by default.
     *
     * @param event {@link ErrorEvent}.
     * @return error window that will be shown to the end user.
     */
    protected Dialog initErrorWindow(ErrorEvent event) {
        return new ErrorWindow("Exception occurred", ExceptionUtils.getStackTrace(event.getThrowable()));
    }

    private boolean isRequestAbortedByRemoteClient(Throwable t) {
        return t instanceof FileDownloadException
            && null != t.getCause()
            && "class org.apache.catalina.connector.ClientAbortException".equals(t.getCause().getClass().toString());
    }
}
