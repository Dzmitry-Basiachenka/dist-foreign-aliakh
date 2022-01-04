package com.copyright.rup.dist.foreign.ui.report.api;

import com.copyright.rup.dist.foreign.domain.filter.UdmReportFilter;
import com.copyright.rup.vaadin.widget.api.IWidget;

/**
 * Interface for widget for UDM common Report.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/03/2022
 *
 * @author Anton Azarenka
 */
public interface IUdmCommonReportWidget extends IWidget<IUdmCommonReportController> {

    /**
     * Returns reported filter.
     *
     * @return instance of {@link UdmReportFilter}
     */
    UdmReportFilter getReportFilter();
}
