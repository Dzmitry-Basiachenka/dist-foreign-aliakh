package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation;

import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclCalculationController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclCalculationWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclGrantDetailController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclUsageController;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link IAclCalculationController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/26/2022
 *
 * @author Dzmitry Basiachenka
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AclCalculationController extends CommonController<IAclCalculationWidget>
    implements IAclCalculationController {

    @Autowired
    private IAclUsageController aclUsageController;
    @Autowired
    private IAclGrantDetailController aclGrantDetailController;

    @Override
    public IAclUsageController getAclUsageController() {
        return aclUsageController;
    }

    @Override
    public IAclGrantDetailController getAclGrantDetailController() {
        return aclGrantDetailController;
    }

    @Override
    protected IAclCalculationWidget instantiateWidget() {
        return new AclCalculationWidget();
    }
}
