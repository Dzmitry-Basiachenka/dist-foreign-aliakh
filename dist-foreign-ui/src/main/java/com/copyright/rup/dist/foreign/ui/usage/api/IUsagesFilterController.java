package com.copyright.rup.dist.foreign.ui.usage.api;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.vaadin.widget.api.IFilterController;

import java.util.List;
import java.util.Set;

/**
 * Interface for controller for usage filtering.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 01/11/2017
 *
 * @author Mikita Hladkikh
 * @author Aliaksandr Radkevich
 */
public interface IUsagesFilterController extends IFilterController<IUsagesFilterWidget> {

    /**
     * Gets list of fiscal years associated with specified Product Family.
     *
     * @param productFamily Product Family
     * @return list of fiscal years
     */
    List<Integer> getFiscalYears(String productFamily);

    /**
     * Gets list of {@link UsageBatch}es related to selected Product Family.
     *
     * @param productFamily Product Family
     * @return list of {@link UsageBatch}es.
     */
    List<UsageBatch> getUsageBatches(String productFamily);

    /**
     * Gets list of RROs associated with selected Product Family.
     *
     * @param productFamily Product Family
     * @return list of RROs
     */
    List<Rightsholder> getRros(String productFamily);

    /**
     * Gets set of {@link UsageStatusEnum} available for selected Product Family.
     *
     * @param productFamily Product Family
     * @return set of {@link UsageStatusEnum}es
     */
    Set<UsageStatusEnum> getStatuses(String productFamily);

    /**
     * @return list of product families.
     */
    List<String> getProductFamilies();
}
