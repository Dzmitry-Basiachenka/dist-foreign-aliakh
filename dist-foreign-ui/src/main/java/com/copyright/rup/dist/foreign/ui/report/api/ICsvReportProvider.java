package com.copyright.rup.dist.foreign.ui.report.api;

import com.copyright.rup.vaadin.ui.component.downloader.IStreamSource;

/**
 * Interface for controllers that generates CSV reports.
 * <p/>
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 06/17/2019
 *
 * @author Aliaksandr Liakh
 */
public interface ICsvReportProvider {

    /**
     * Gets {@link IStreamSource} for CSV format.
     *
     * @return instance of {@link IStreamSource}
     */
    IStreamSource getCsvStreamSource();
}
