package com.copyright.rup.dist.foreign.ui.scenario.api.acl;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.AclScenario;

/**
 * Controller interface for {@link IAclScenarioDetailsWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 11/17/2022
 *
 * @author Mikita Maistrenka
 */
public interface IAclScenarioDetailsController extends IAclCommonScenarioDetailsController {

    /**
     * Initializes and shows the ACL scenario details widget.
     * Sets selected {@link AclScenario} to the widget.
     *
     * @param scenario      selected {@link AclScenario}
     */
    void showWidget(AclScenario scenario);

    /**
     * @return instance of {@link IStreamSource} for export details.
     */
    IStreamSource getExportAclScenarioDetailsStreamSource();
}
