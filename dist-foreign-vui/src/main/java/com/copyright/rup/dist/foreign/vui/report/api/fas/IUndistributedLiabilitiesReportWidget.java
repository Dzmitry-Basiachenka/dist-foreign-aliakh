package com.copyright.rup.dist.foreign.vui.report.api.fas;

import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IWidget;

import java.time.LocalDate;

/**
 * Interface for undistributed liabilities report widget.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 5/10/2018
 *
 * @author Uladzislau_Shalamitski
 */
public interface IUndistributedLiabilitiesReportWidget extends IWidget<IUndistributedLiabilitiesReportController> {

    /**
     * @return selected payment date.
     */
    LocalDate getPaymentDate();
}
