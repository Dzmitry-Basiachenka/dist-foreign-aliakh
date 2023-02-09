package com.copyright.rup.dist.foreign.ui.status.api;

import com.copyright.rup.dist.foreign.domain.UsageBatchStatus;
import com.copyright.rup.vaadin.widget.api.IController;

import java.util.List;

/**
 * Interface for UDM usage batch status controller.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 02/08/2023
 *
 * @author Dzmitry Basiachenka
 */
public interface IUdmBatchStatusController extends IController<IUdmBatchStatusWidget> {

    /**
     * @return usage batch statuses.
     */
    List<UsageBatchStatus> getBatchStatuses();
}
