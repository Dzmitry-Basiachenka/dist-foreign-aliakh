package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.ui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageFilterWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

/**
 * Controller for filtering usages.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/12/2019
 *
 * @author Uladzislau Shalamitski
 */
public abstract class CommonUsageFilterController extends CommonController<ICommonUsageFilterWidget>
    implements ICommonUsageFilterController {

    @Autowired
    private IProductFamilyProvider productFamilyProvider;

    @Autowired
    private IUsageBatchService usageBatchService;

    @Override
    public String getSelectedProductFamily() {
        return productFamilyProvider.getSelectedProductFamily();
    }

    @Override
    public List<UsageBatch> getUsageBatches() {
        return usageBatchService.getUsageBatchesByProductFamilies(Collections.singleton(getSelectedProductFamily()));
    }

    protected IUsageBatchService getUsageBatchService() {
        return usageBatchService;
    }
}
