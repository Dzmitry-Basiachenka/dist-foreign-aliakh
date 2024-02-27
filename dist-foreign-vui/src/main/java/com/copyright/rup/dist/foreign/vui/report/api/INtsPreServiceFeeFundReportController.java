package com.copyright.rup.dist.foreign.vui.report.api;

import com.copyright.rup.dist.common.reporting.api.ICsvReportProvider;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IController;

import java.util.List;

/**
 * Interface for pre-service fee fund report controller for NTS.
 * <p/>
 * Copyright (C) 2024 copyright.com
 * <p/>
 * Date: 01/31/2024
 *
 * @author Dzmitry Basiachenka
 */
public interface INtsPreServiceFeeFundReportController extends IController<INtsPreServiceFeeFundReportWidget>,
    ICsvReportProvider {

    /**
     * Gets all NTS Pre-Service Fee Funds.
     *
     * @return list of {@link FundPool}s
     */
    List<FundPool> getPreServiceFeeFunds();
}
