package com.copyright.rup.dist.foreign.ui.report.api.udm;

import com.copyright.rup.dist.foreign.domain.filter.UdmReportFilter;
import com.copyright.rup.vaadin.widget.api.IWidget;

/**
 * Interface for widget for UDM common user names reports.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p/>
 * Date: 01/06/2022
 *
 * @author Ihar Suvorau
 */
public interface IUdmCommonUserNamesReportWidget extends IWidget<IUdmCommonUserNamesReportController> {

    /**
     * Returns reported filter.
     *
     * @return instance of {@link UdmReportFilter}
     */
    UdmReportFilter getReportFilter();
}
