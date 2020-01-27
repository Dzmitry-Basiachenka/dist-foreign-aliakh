package com.copyright.rup.dist.foreign.ui.usage.impl.aacl;

import com.copyright.rup.dist.foreign.service.api.aacl.IAaclUsageService;
import com.copyright.rup.dist.foreign.ui.usage.api.aacl.IAaclUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.impl.CommonUsageFilterController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Controller for filtering AACL usages.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/23/2019
 *
 * @author Aliaksandr Liakh
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
class AaclUsageFilterController extends CommonUsageFilterController implements IAaclUsageFilterController {

    @Autowired
    private IAaclUsageService aaclUsageService;

    @Override
    protected AaclUsageFilterWidget instantiateWidget() {
        return new AaclUsageFilterWidget(this);
    }

    @Override
    public List<Integer> getUsagePeriods() {
        return aaclUsageService.getUsagePeriods();
    }
}
