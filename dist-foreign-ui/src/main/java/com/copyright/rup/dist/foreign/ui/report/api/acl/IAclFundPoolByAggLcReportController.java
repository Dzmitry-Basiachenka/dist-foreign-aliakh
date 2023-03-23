package com.copyright.rup.dist.foreign.ui.report.api.acl;

import com.copyright.rup.dist.common.reporting.api.ICsvReportProvider;
import com.copyright.rup.dist.foreign.domain.AclFundPool;
import com.copyright.rup.vaadin.widget.api.IController;

import java.util.List;
import java.util.Set;

/**
 * Interface for fund pool by Aggregate Licensee Class report controller.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 03/22/2023
 *
 * @author Ihar Suvorau
 */
public interface IAclFundPoolByAggLcReportController
    extends IController<IAclFundPoolByAggLcReportWidget>, ICsvReportProvider {

    /**
     * @return list of available periods
     */
    List<Integer> getPeriods();

    /**
     * Gets list of ACL fund pools for selected periods.
     *
     * @param periods set of selected periods
     * @return list of fund pools
     */
    List<AclFundPool> getFundPools(Set<Integer> periods);
}
