package com.copyright.rup.dist.foreign.ui.report.api;

import com.copyright.rup.dist.foreign.domain.report.SalLicensee;
import com.copyright.rup.vaadin.widget.api.IWidget;

/**
 * Interface of SAL Historical Item Bank Details Report widget.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 11/25/2020
 *
 * @author Aliaksandr Liakh
 */
public interface ISalHistoricalItemBankDetailsReportWidget
    extends IWidget<ISalHistoricalItemBankDetailsReportController> {

    /**
     * Gets selected {@link SalLicensee}.
     *
     * @return the selected {@link SalLicensee}
     */
    SalLicensee getSalLicensee();
}
