package com.copyright.rup.dist.foreign.ui.usage.impl.sal;

import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.ui.usage.api.sal.ISalUsageController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link ISalUsageController}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 07/29/20
 *
 * @author Ihar Suvorau
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SalUsageController implements ISalUsageController {

    @Autowired
    private IUsageBatchService usageBatchService;

    @Override
    //TODO {isuvorau} reuse CommonUsageController#usageBatchExists method after extending CommonUsageController
    public boolean itemBankExists(String name) {
        return usageBatchService.usageBatchExists(name);
    }
}
