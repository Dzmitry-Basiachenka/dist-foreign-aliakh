package com.copyright.rup.dist.foreign.ui.report.api;

import com.copyright.rup.dist.common.reporting.api.ICsvReportProvider;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.vaadin.widget.api.IController;

import java.util.List;

/**
 * Interface for summary of market report controller.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 8/27/2018
 *
 * @author Ihar Suvorau
 */
public interface ISummaryMarketReportController extends IController<ISummaryMarketReportWidget>, ICsvReportProvider {

    /**
     * @return list of usage bathes.
     */
    List<UsageBatch> getUsageBatches();
}
