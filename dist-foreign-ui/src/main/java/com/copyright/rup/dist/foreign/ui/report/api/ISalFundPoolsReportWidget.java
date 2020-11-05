package com.copyright.rup.dist.foreign.ui.report.api;

import com.copyright.rup.vaadin.widget.api.IWidget;

/**
 * Interface for SAL fund pools report widget.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 11/05/20
 *
 * @author Anton Azarenka
 */
public interface ISalFundPoolsReportWidget extends IWidget<ISalFundPoolsReportController> {

    /**
     * @return selected distribution year.
     */
    int getDistributionYear();
}
