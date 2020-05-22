package com.copyright.rup.dist.foreign.ui.report.api;

import com.copyright.rup.vaadin.widget.api.IWidget;

/**
 * Interface for AACL baseline usages report controller.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 5/19/2020
 *
 * @author Ihar Suvorau
 */
public interface IAaclBaselineUsagesReportWidget extends IWidget<IAaclBaselineUsagesReportController> {

    /**
     * @return selected number of baseline years.
     */
    int getNumberOfBaselineYears();
}
