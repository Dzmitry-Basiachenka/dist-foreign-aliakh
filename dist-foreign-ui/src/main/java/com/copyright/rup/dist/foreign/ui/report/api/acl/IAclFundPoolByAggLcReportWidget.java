package com.copyright.rup.dist.foreign.ui.report.api.acl;

import com.copyright.rup.vaadin.widget.api.IWidget;

import java.util.Set;

/**
 * Interface for common ACL report widget.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 03/22/2023
 *
 * @author Ihar Suvorau
 */
public interface IAclFundPoolByAggLcReportWidget extends IWidget<IAclFundPoolByAggLcReportController> {

    /**
     * @return selected fund pool ids.
     */
    Set<String> getFundPoolIds();
}
