package com.copyright.rup.dist.foreign.ui.usage.api;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.vaadin.widget.api.IFilterController;

import java.util.List;

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
     * @return list of fiscal years.
     */
    List<Integer> getFiscalYears();

    /**
     * @return list of all {@link UsageBatch}es.
     */
    List<UsageBatch> getUsageBatches();

    /**
     * @return list of rightsholders.
     */
    List<Rightsholder> getRros();

    /**
     * @return list of product families.
     */
    List<String> getProductFamilies();
}
