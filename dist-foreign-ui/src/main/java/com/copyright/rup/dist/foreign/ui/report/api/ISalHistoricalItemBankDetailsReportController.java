package com.copyright.rup.dist.foreign.ui.report.api;

import com.copyright.rup.dist.common.reporting.api.ICsvReportProvider;
import com.copyright.rup.vaadin.widget.api.IController;

import java.util.List;

/**
 * Interface of SAL Historical Item Bank Details Report controller.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 11/25/2020
 *
 * @author Aliaksandr Liakh
 */
public interface ISalHistoricalItemBankDetailsReportController
    extends IController<ISalHistoricalItemBankDetailsReportWidget>, ICsvReportProvider {

    /**
     * Gets list of licensees.
     *
     * @return list of licensees
     */
    List<String> getLicensees();
}
