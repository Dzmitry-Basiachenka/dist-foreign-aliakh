package com.copyright.rup.dist.foreign.vui.usage.impl;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.vui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.vui.usage.api.ICommonUsageFilterController;
import com.copyright.rup.dist.foreign.vui.usage.api.ICommonUsageFilterWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

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

    private static final long serialVersionUID = -9168818254812318926L;

    @Autowired
    private IProductFamilyProvider productFamilyProvider;

    @Autowired
    private IUsageBatchService usageBatchService;

    @Autowired
    private IRightsholderService rightsholderService;

    @Override
    public String getSelectedProductFamily() {
        return productFamilyProvider.getSelectedProductFamily();
    }

    @Override
    public List<UsageBatch> getUsageBatches() {
        return usageBatchService.getUsageBatches(getSelectedProductFamily());
    }

    @Override
    public List<Rightsholder> getRightsholdersByAccountNumbers(Set<Long> accountNumbers) {
        return rightsholderService.getRightsholdersByAccountNumbers(accountNumbers);
    }

    protected IUsageBatchService getUsageBatchService() {
        return usageBatchService;
    }

    protected IRightsholderService getRightsholderService() {
        return rightsholderService;
    }
}
