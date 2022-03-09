package com.copyright.rup.dist.foreign.ui.report.api.udm;

import com.copyright.rup.dist.foreign.domain.filter.UdmReportFilter;
import com.copyright.rup.vaadin.widget.api.IWidget;

/**
 * Interface for widget for UDM Usage Edits in Baseline Report.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p/>
 * Date: 03/11/2022
 *
 * @author Aliaksandr Liakh
 */
public interface IUdmUsageEditsInBaselineReportWidget extends IWidget<IUdmUsageEditsInBaselineReportController> {

    /**
     * Returns reported filter.
     *
     * @return instance of {@link UdmReportFilter}
     */
    UdmReportFilter getReportFilter();
}
