package com.copyright.rup.dist.foreign.ui.common.component;

import com.copyright.rup.dist.foreign.ui.report.api.ICsvReportProvider;
import com.copyright.rup.vaadin.ui.component.downloader.IStreamSource;

import java.io.InputStream;

/**
 * Implementation of {@link IStreamSource} for CSV reports.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 06/17/2019
 *
 * @author Aliaksandr Liakh
 */
public class CsvStreamSource implements IStreamSource {

    private final ICsvReportProvider csvReportProvider;

    /**
     * Constructor.
     *
     * @param csvReportProvider CSV report provider
     */
    public CsvStreamSource(ICsvReportProvider csvReportProvider) {
        this.csvReportProvider = csvReportProvider;
    }

    @Override
    public InputStream getStream() {
        return csvReportProvider.getCsvStreamSource().getStream();
    }

    @Override
    public String getFileName() {
        return csvReportProvider.getCsvStreamSource().getFileName();
    }
}
