package com.copyright.rup.dist.foreign.ui.report.api.acl;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.vaadin.widget.api.IController;
import com.copyright.rup.vaadin.widget.api.IRefreshable;
import com.copyright.rup.vaadin.widget.api.IWidget;

/**
 * Represents interface for ACL report menu widget.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 10/03/2022
 *
 * @author Ihar Suvorau
 */
public interface IAclReportWidget extends IWidget<IAclReportController>, IRefreshable {

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
