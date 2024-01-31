package com.copyright.rup.dist.foreign.vui.report.api;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IRefreshable;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IWidget;

/**
 * Represents interface for report menu widget.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 01/16/17
 *
 * @author Nikita Levyankov
 */
public interface IReportWidget extends IWidget<IReportController>, IRefreshable {

    /**
     * Opens a report window.
     *
     * @param reportCaption    the report window caption
     * @param reportController the report controller
     */
    void openReportWindow(String reportCaption, IController reportController);

    /**
     * Generates a report.
     *
     * @param streamSource instance of {@link IStreamSource}
     */
    void generateReport(IStreamSource streamSource);
}
