package com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.downloader;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Verifies {@link FileDownloadException}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/02/2017
 *
 * @author Mikita Hladkikh
 */
public class FileDownloadExceptionTest {

    @Test
    public void testConstructor() {
        NullPointerException nullPointerException = new NullPointerException();
        FileDownloadException exception = new FileDownloadException(nullPointerException);
        assertEquals(nullPointerException, exception.getCause());
    }
}
