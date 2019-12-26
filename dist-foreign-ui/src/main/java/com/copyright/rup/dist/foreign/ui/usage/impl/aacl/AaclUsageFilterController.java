package com.copyright.rup.dist.foreign.ui.usage.impl.aacl;

import com.copyright.rup.dist.foreign.ui.usage.api.aacl.IAaclUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.impl.CommonUsageFilterController;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

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

    @Override
    protected AaclUsageFilterWidget instantiateWidget() {
        return new AaclUsageFilterWidget(this);
    }
}
