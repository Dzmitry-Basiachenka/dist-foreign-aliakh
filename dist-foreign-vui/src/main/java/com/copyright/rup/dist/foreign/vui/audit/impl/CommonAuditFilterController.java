package com.copyright.rup.dist.foreign.vui.audit.impl;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.api.Sort.Direction;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.vui.audit.api.ICommonAuditFilterController;
import com.copyright.rup.dist.foreign.vui.audit.api.ICommonAuditFilterWidget;
import com.copyright.rup.dist.foreign.vui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.CommonController;

import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.SortDirection;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * Controller for audit filter.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 1/12/18
 *
 * @author Aliaksandr Radkevich
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public abstract class CommonAuditFilterController extends CommonController<ICommonAuditFilterWidget> implements
    ICommonAuditFilterController {

    private static final long serialVersionUID = 2212425997625038519L;

    @Autowired
    private IUsageBatchService usageBatchService;
    @Autowired
    private IRightsholderService rightsholderService;
    @Autowired
    private IProductFamilyProvider productFamilyProvider;

    @Override
    public List<Rightsholder> loadBeans(String searchValue, int startIndex, int count,
                                        List<QuerySortOrder> sortOrders) {
        Sort sort = null;
        if (CollectionUtils.isNotEmpty(sortOrders)) {
            QuerySortOrder sortOrder = sortOrders.get(0);
            sort = new Sort(sortOrder.getSorted(), Direction.of(SortDirection.ASCENDING == sortOrder.getDirection()));
        }
        return rightsholderService.getAllWithSearch(searchValue, new Pageable(startIndex, count), sort);
    }

    @Override
    public int getBeansCount(String searchValue) {
        return rightsholderService.getCountWithSearch(searchValue);
    }

    @Override
    public List<UsageBatch> getUsageBatches() {
        return usageBatchService.getUsageBatches(productFamilyProvider.getSelectedProductFamily());
    }

    @Override
    public String getProductFamily() {
        return productFamilyProvider.getSelectedProductFamily();
    }

    @Override
    public List<Rightsholder> getRightsholdersByAccountNumbers(Set<Long> accountNumbers) {
        return rightsholderService.getRightsholdersByAccountNumbers(accountNumbers);
    }
}
