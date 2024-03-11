package com.copyright.rup.dist.foreign.vui.usage.impl.aacl;

import com.copyright.rup.dist.foreign.service.api.aacl.IAaclUsageService;
import com.copyright.rup.dist.foreign.vui.usage.api.aacl.IAaclUsageFilterController;
import com.copyright.rup.dist.foreign.vui.usage.impl.CommonUsageFilterController;

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

    private static final long serialVersionUID = -8175240439383713007L;

    @Autowired
    private IAaclUsageService aaclUsageService;

    @Override
    public List<Integer> getUsagePeriods() {
        return aaclUsageService.getUsagePeriods();
    }

    @Override
    protected AaclUsageFilterWidget instantiateWidget() {
        return new AaclUsageFilterWidget(this);
    }
}
