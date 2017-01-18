package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.foreign.UsageDetail;
import com.copyright.rup.vaadin.ui.component.lazytable.CommonBeanQuery;

import org.vaadin.addons.lazyquerycontainer.QueryDefinition;

import java.util.Map;

/**
 * Bean query implementation for {@link UsageDetail}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 1/18/17
 *
 * @author Aliaksandr Radkevich
 */
public class UsageBeanQuery extends CommonBeanQuery<UsageDetail> {

    /**
     * Constructor.
     *
     * @param queryDefinition    query definition
     * @param queryConfiguration query configuration
     * @param sortPropertyIds    sort properties
     * @param sortStates         sort states
     */
    public UsageBeanQuery(QueryDefinition queryDefinition,
                          Map<String, Object> queryConfiguration, Object[] sortPropertyIds,
                          boolean... sortStates) {
        super(queryDefinition, queryConfiguration, sortPropertyIds, sortStates);
    }
}
