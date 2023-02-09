package com.copyright.rup.dist.foreign.ui.usage.impl.sal;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.service.api.sal.ISalUsageService;
import com.copyright.rup.dist.foreign.ui.usage.api.sal.ISalUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.impl.CommonUsageFilterController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Controller for filtering SAL usages.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 07/28/2020
 *
 * @author Uladzislau Shalamitski
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SalUsageFilterController extends CommonUsageFilterController implements ISalUsageFilterController {

    @Autowired
    private ISalUsageService salUsageService;

    @Override
    public SalUsageFilterWidget instantiateWidget() {
        return new SalUsageFilterWidget(this);
    }

    @Override
    public List<Rightsholder> getRightsholders() {
        return salUsageService.getRightsholders();
    }
}
