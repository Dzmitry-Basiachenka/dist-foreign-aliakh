package com.copyright.rup.dist.foreign.vui.main;

import com.copyright.rup.dist.common.integration.IntegrationConnectionException;
import com.copyright.rup.dist.common.integration.rest.prm.PrmConfigurationException;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.CommonErrorHandler;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.downloader.FileDownloadException;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.NotificationWindow;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.server.ErrorEvent;

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

    private final IMessageSource messageSource;

    /**
     * Constructor.
     *
     * @param messageSource message source.
     */
    public ForeignErrorHandler(IMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    protected Dialog initErrorWindow(ErrorEvent event) {
        if (integrationConnectionExceptionPresent(event)) {
            Dialog window = new NotificationWindow(messageSource.getStringMessage("label.content.accessibility"));
            window.setHeaderTitle(ForeignUi.getMessage("window.caption.connection_problem"));
            return window;
        } else if (fileDownloadExceptionPresent(event)) {
            return new NotificationWindow(messageSource.getStringMessage("message.report.generate_error"));
        } else if (prmConfigurationExceptionPresent(event)) {
            return new NotificationWindow(messageSource.getStringMessage("message.error.incorrect_prm_configuration"));
        }
        return super.initErrorWindow(event);
    }

    private boolean fileDownloadExceptionPresent(ErrorEvent event) {
        return -1 != ExceptionUtils.indexOfType(event.getThrowable(), FileDownloadException.class);
    }

    private boolean integrationConnectionExceptionPresent(ErrorEvent event) {
        return -1 != ExceptionUtils.indexOfType(event.getThrowable(), IntegrationConnectionException.class);
    }

    private boolean prmConfigurationExceptionPresent(ErrorEvent event) {
        return -1 != ExceptionUtils.indexOfType(event.getThrowable(), PrmConfigurationException.class);
    }
}
