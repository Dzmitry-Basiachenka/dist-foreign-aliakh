package com.copyright.rup.dist.foreign.ui.report.api;

import com.copyright.rup.vaadin.ui.component.downloader.IStreamSource;
import com.copyright.rup.vaadin.widget.api.IController;

/**
 * Interface for undistributed liabilities reconciliation report controller.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 5/10/2018
 *
 * @author Uladzislau_Shalamitski
 */
public interface IUndistributedLiabilitiesReportController extends IController<IUndistributedLiabilitiesReportWidget> {

    /**
     * @return instance of {@link IStreamSource} for undistributed liabilities reconciliation report.
     */
    IStreamSource getUndistributedLiabilitiesReportStreamSource();
}
