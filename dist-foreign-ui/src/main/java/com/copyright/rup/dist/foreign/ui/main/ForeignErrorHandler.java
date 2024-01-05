package com.copyright.rup.dist.foreign.ui.main;

import com.copyright.rup.dist.common.integration.IntegrationConnectionException;
import com.copyright.rup.dist.common.integration.rest.prm.PrmConfigurationException;
import com.copyright.rup.vaadin.ui.CommonErrorHandler;
import com.copyright.rup.vaadin.ui.component.downloader.FileDownloadException;
import com.copyright.rup.vaadin.ui.component.window.NotificationWindow;

import com.vaadin.server.ErrorEvent;
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

    private static final long serialVersionUID = 7445034268691085766L;

    private final ForeignCommonUi owner;

    /**
     * Default constructor.
     * Consumes UI instance as the parameter.
     *
     * @param owner the owner.
     */
    public ForeignErrorHandler(ForeignCommonUi owner) {
        super(owner);
        this.owner = owner;
    }

    @Override
    protected Window initErrorWindow(ErrorEvent event) {
        if (integrationConnectionExceptionPresent(event)) {
            Window window = new NotificationWindow(owner.getStringMessage("label.content.accessibility"));
            window.setCaption(owner.getStringMessage("window.caption.connection_problem"));
            return window;
        } else if (fileDownloadExceptionPresent(event)) {
            return new NotificationWindow(owner.getStringMessage("message.report.generate_error"));
        } else if (prmConfigurationExceptionPresent(event)) {
            return new NotificationWindow(owner.getStringMessage("message.error.incorrect_prm_configuration"));
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
