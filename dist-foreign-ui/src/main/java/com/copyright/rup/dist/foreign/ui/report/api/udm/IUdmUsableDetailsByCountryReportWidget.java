package com.copyright.rup.dist.foreign.ui.report.api.udm;

import com.copyright.rup.dist.foreign.domain.filter.UdmReportFilter;
import com.copyright.rup.vaadin.widget.api.IWidget;

/**
 * Interface for Udm Usable Details by Country Report widget.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/09/2022
 *
 * @author Mikita Maistrenka
 */
public interface IUdmUsableDetailsByCountryReportWidget extends IWidget<IUdmUsableDetailsByCountryReportController> {

    /**
     * Returns reported filter.
     *
     * @return instance of {@link UdmReportFilter}
     */
    UdmReportFilter getReportFilter();
}
