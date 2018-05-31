package com.copyright.rup.dist.foreign.ui.report.impl;

import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.report.api.IReportController;
import com.copyright.rup.dist.foreign.ui.report.api.IReportWidget;
import com.copyright.rup.vaadin.ui.component.downloader.IStreamSource;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.api.IController;

import com.google.common.net.HttpHeaders;
import com.google.common.net.MediaType;
import com.vaadin.server.DownloadStream;
import com.vaadin.server.Page;
import com.vaadin.server.ResourceReference;
import com.vaadin.server.StreamResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Window;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Implementation of {@link IReportWidget}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 01/16/17
 *
 * @author Nikita Levyankov
 */
public class ReportWidget extends MenuBar implements IReportWidget {

    private IReportController controller;

    @Override
    @SuppressWarnings("unchecked")
    public ReportWidget init() {
        VaadinUtils.addComponentStyle(this, "reports-menu");
        MenuItem rootItem = addItem(ForeignUi.getMessage("tab.reports"), null);
        rootItem.setStyleName("reports-menu-root");
        String undistributedLiabilitiesReportCaption = ForeignUi.getMessage("menu.report.undistributed_liabilities");
        rootItem.addItem(undistributedLiabilitiesReportCaption, menuItem ->
            openReportWindow(undistributedLiabilitiesReportCaption,
                controller.getUndistributedLiabilitiesReportController()));
        rootItem.addItem(ForeignUi.getMessage("menu.report.batch_summary"),
            menuItem -> generateReport(controller.getBatchSummaryReportStreamSource()));
        rootItem.addItem(ForeignUi.getMessage("menu.report.research_status"),
            menuItem -> generateReport(controller.getResearchStatusReportStreamSource()));
        return this;
    }

    @Override
    public void setController(IReportController controller) {
        this.controller = controller;
    }

    private void openReportWindow(String reportCaption, IController reportController) {
        Window reportWindow = (Window) reportController.initWidget();
        reportWindow.setCaption(reportCaption);
        Windows.showModalWindow(reportWindow);
    }

    private void generateReport(IStreamSource streamSource) {
        VaadinSession session = VaadinSession.getCurrent();
        session.lock();
        try {
            ReportStreamSource resource = new ReportStreamSource(streamSource);
            setResource(resource.getFilename(), resource);
            ResourceReference resourceReference = ResourceReference.create(resource, this, resource.getFilename());
            Page.getCurrent().open(resourceReference.getURL(), null);
        } finally {
            session.unlock();
        }
    }

    /**
     * Class that represents a resource provided to the client directly by the application.
     */
    static class ReportStreamSource extends StreamResource {

        private DownloadStream downloadStream;

        /**
         * Creates a new stream resource for downloading from stream.
         *
         * @param streamSource the source Stream.
         */
        ReportStreamSource(IStreamSource streamSource) {
            super(streamSource, streamSource.getFileName());
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
            if (!(obj instanceof ReportStreamSource)) {
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
}
