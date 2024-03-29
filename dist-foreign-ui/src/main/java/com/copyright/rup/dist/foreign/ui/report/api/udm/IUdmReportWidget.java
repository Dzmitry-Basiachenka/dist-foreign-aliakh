package com.copyright.rup.dist.foreign.ui.report.api.udm;

import com.copyright.rup.vaadin.widget.api.IController;
import com.copyright.rup.vaadin.widget.api.IRefreshable;
import com.copyright.rup.vaadin.widget.api.IWidget;

/**
 * Represents interface for UDM report menu widget.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 12/16/2021
 *
 * @author Aliaksandr Liakh
 */
public interface IUdmReportWidget extends IWidget<IUdmReportController>, IRefreshable {

    /**
     * Opens a report window.
     *
     * @param reportCaption    the report window caption
     * @param reportController the report controller
     */
    void openReportWindow(String reportCaption, IController reportController);
}
