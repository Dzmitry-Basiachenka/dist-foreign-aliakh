package com.copyright.rup.dist.foreign.ui.report.api.udm;

import com.copyright.rup.vaadin.widget.api.IWidget;

/**
 * Interface for widget for UDM by Status Reports.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p/>
 * Date: 04/20/2022
 *
 * @author Ihar Suvorau
 */
public interface IUdmCommonStatusReportWidget extends IWidget<IUdmCommonStatusReportController> {

    /**
     * @return selected period to generate report, can be {@code null}.
     */
    Integer getSelectedPeriod();
}
