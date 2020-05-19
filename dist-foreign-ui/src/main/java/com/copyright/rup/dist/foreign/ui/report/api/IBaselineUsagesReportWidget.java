package com.copyright.rup.dist.foreign.ui.report.api;

import com.copyright.rup.vaadin.widget.api.IWidget;

/**
 * Interface for baseline usages report controller.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 5/19/2020
 *
 * @author Ihar Suvorau
 */
public interface IBaselineUsagesReportWidget extends IWidget<IBaselineUsagesReportController> {

    /**
     * @return selected number of baseline years.
     */
    int getNumberOfBaselineYears();
}
