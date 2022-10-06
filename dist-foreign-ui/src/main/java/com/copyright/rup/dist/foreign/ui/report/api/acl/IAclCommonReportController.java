package com.copyright.rup.dist.foreign.ui.report.api.acl;

import com.copyright.rup.dist.common.reporting.api.ICsvReportProvider;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.vaadin.widget.api.IController;

import java.util.List;

/**
 * Interface for common ACL report controller.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 10/05/2022
 *
 * @author Ihar Suvorau
 */
public interface IAclCommonReportController extends IController<IAclCommonReportWidget>, ICsvReportProvider {

    /**
     * @return list of available periods in ACL scenarios.
     */
    List<Integer> getPeriods();

    /**
     * Get list of ACL scenarios by for specified period.
     *
     * @param period scenario period
     * @return list of available ACL scenarios
     */
    List<AclScenario> getScenarios(Integer period);
}
