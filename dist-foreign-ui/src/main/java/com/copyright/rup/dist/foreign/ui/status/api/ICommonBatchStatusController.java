package com.copyright.rup.dist.foreign.ui.status.api;

import com.copyright.rup.dist.foreign.domain.UsageBatchStatus;
import com.copyright.rup.vaadin.widget.api.IController;

import java.util.List;

/**
 * Common interface for usage batch status controllers.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 02/12/2021
 *
 * @author Ihar Suvorau
 */
public interface ICommonBatchStatusController extends IController<ICommonBatchStatusWidget> {

    /**
     * @return usage batch statuses.
     */
    List<UsageBatchStatus> getBatchStatuses();
}
