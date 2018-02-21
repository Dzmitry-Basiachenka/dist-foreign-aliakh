package com.copyright.rup.dist.foreign.ui.audit.api;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.vaadin.widget.api.IFilterController;

import java.util.List;

/**
 * Audit filter controller.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 1/12/18
 *
 * @author Aliaksandr Radkevich
 */
public interface IAuditFilterController extends IFilterController<IAuditFilterWidget> {

    /**
     * @return list of {@link Rightsholder}s.
     */
    List<Rightsholder> getRightsholders();

    /**
     * @return list of {@link UsageBatch}es.
     */
    List<UsageBatch> getUsageBatches();

    /**
     * @return list of product families.
     */
    List<String> getProductFamilies();
}
