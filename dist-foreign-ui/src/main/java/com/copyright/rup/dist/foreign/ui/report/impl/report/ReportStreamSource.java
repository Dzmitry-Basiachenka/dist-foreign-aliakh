package com.copyright.rup.dist.foreign.ui.report.impl.report;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;

import com.google.common.net.HttpHeaders;
import com.google.common.net.MediaType;
import com.vaadin.server.DownloadStream;
import com.vaadin.server.StreamResource;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Implementations of {@link StreamResource} to download reports.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/12/2019
 *
 * @author Aliaksanr Liakh
 */
public class ReportStreamSource extends StreamResource {

    private DownloadStream downloadStream;

    /**
     * Creates a new stream resource for downloading from stream.
     *
     * @param streamSource instance of {@link IStreamSource}.
     */
    public ReportStreamSource(IStreamSource streamSource) {
        super((StreamSource) streamSource.getSource().getValue()::get, streamSource.getSource().getKey().get());
    }

    @Override
    public DownloadStream getStream() {
        if (Objects.isNull(downloadStream)) {
            downloadStream = super.getStream();
            downloadStream.setParameter(HttpHeaders.CONTENT_DISPOSITION,
                String.format("attachment; filename=\"%s\"", getFilename()));
            downloadStream.setContentType(MediaType.OCTET_STREAM.withCharset(StandardCharsets.UTF_8).toString());
            downloadStream.setParameter(HttpHeaders.CACHE_CONTROL, "private,no-cache,no-store");
            downloadStream.setCacheTime(0);
        }
        return downloadStream;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        ReportStreamSource that = (ReportStreamSource) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(downloadStream, that.downloadStream)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(downloadStream)
            .toHashCode();
    }
}
