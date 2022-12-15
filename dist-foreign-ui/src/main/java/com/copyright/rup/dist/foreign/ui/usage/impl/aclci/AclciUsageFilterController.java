package com.copyright.rup.dist.foreign.ui.usage.impl.aclci;

import com.copyright.rup.dist.foreign.ui.usage.api.aclci.IAclciUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.impl.CommonUsageFilterController;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link IAclciUsageFilterController}.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 11/18/2022
 *
 * @author Aliaksanr Liakh
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AclciUsageFilterController extends CommonUsageFilterController implements IAclciUsageFilterController {

    @Override
    public AclciUsageFilterWidget instantiateWidget() {
        return new AclciUsageFilterWidget(this);
    }
}
