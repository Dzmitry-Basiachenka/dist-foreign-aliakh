package com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.downloader;

/**
 * Exception is used to catch "Broken Pipe" or "Closed stream" exceptions while generating and downloading reports.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/02/2017
 *
 * @author Mikita Hladkikh
 */
public class FileDownloadException extends RuntimeException {

    /**
     * Constructor.
     *
     * @param cause the source {@link Throwable} exception.
     */
    public FileDownloadException(Throwable cause) {
        super(cause);
    }
}
