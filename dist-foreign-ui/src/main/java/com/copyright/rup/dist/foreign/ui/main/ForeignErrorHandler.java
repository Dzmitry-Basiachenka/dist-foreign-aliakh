package com.copyright.rup.dist.foreign.ui.main;

import com.copyright.rup.vaadin.ui.CommonErrorHandler;
import com.copyright.rup.vaadin.ui.NotificationWindow;
import com.copyright.rup.vaadin.ui.component.downloader.FileDownloadException;

import com.vaadin.server.ErrorEvent;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * Custom error handler for vaadin.
 * Logs error to the log and shows modal window.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 11/28/17
 *
 * @author Ihar Suvorau
 */
public class ForeignErrorHandler extends CommonErrorHandler {

    /**
     * Default constructor.
     * Consumes UI instance as the parameter.
     *
     * @param owner the owner.
     */
    public ForeignErrorHandler(UI owner) {
        super(owner);
    }

    @Override
    protected Window initErrorWindow(ErrorEvent event) {
        if (fileDownloadExceptionPresent(event)) {
            Window window = new NotificationWindow(ForeignUi.getMessage("message.report.generate_error"));
            window.setImmediate(true);
            return window;
        }
        return super.initErrorWindow(event);
    }

    private boolean fileDownloadExceptionPresent(ErrorEvent event) {
        return -1 != ExceptionUtils.indexOfType(event.getThrowable(), FileDownloadException.class);
    }
}
