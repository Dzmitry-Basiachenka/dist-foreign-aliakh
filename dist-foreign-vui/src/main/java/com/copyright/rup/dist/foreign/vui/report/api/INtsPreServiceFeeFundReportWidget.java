package com.copyright.rup.dist.foreign.vui.report.api;

import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IWidget;

/**
 * Interface for NTS pre-service fee fund report widget.
 * <p/>
 * Copyright (C) 2024 copyright.com
 * <p/>
 * Date: 01/31/2024
 *
 * @author Dzmitry Basiachenka
 */
public interface INtsPreServiceFeeFundReportWidget extends IWidget<INtsPreServiceFeeFundReportController> {

    /**
     * @return the selected fund pool.
     */
    FundPool getFundPool();
}
