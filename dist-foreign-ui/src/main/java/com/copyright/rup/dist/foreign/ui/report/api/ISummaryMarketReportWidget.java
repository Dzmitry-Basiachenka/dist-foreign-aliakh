package com.copyright.rup.dist.foreign.ui.report.api;

import com.copyright.rup.vaadin.widget.api.IWidget;

import java.util.List;

/**
 * Interface for summary of market report widget.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 8/27/2018
 *
 * @author Ihar Suvorau
 */
public interface ISummaryMarketReportWidget extends IWidget<ISummaryMarketReportController> {

    /**
     * @return selected batch ids.
     */
    List<String> getBatchIds();
}
