package com.copyright.rup.dist.foreign.ui.usage.api.acl;

import com.copyright.rup.dist.foreign.domain.UdmBatch;
import com.copyright.rup.vaadin.widget.api.IFilterController;

import java.util.List;

/**
 * Interface for controller for UDM usage filtering.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 05/03/2021
 *
 * @author Dzmitry Basiachenka
 */
public interface IUdmUsageFilterController extends IFilterController<IUdmUsageFilterWidget> {

    /**
     * Gets all available periods.
     *
     * @return list of periods
     */
    List<Integer> getPeriods();

    /**
     * Gets list of {@link UdmBatch}es.
     *
     * @return list of {@link UdmBatch}es
     */
    List<UdmBatch> getUdmBatches();
}