package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation;

import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenariosController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclCalculationController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclCalculationWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclFundPoolController;
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

    private static final long serialVersionUID = -3385047080298474063L;

    @Autowired
    private IAclUsageController aclUsageController;
    @Autowired
    private IAclGrantDetailController aclGrantDetailController;
    @Autowired
    private IAclFundPoolController aclFundPoolController;
    @Autowired
    private IAclScenariosController aclScenariosController;

    @Override
    public IAclUsageController getAclUsageController() {
        return aclUsageController;
    }

    @Override
    public IAclGrantDetailController getAclGrantDetailController() {
        return aclGrantDetailController;
    }

    @Override
    public IAclFundPoolController getAclFundPoolController() {
        return aclFundPoolController;
    }

    @Override
    protected IAclCalculationWidget instantiateWidget() {
        return new AclCalculationWidget();
    }

    @Override
    public IAclScenariosController getAclScenariosController() {
        return aclScenariosController;
    }
}
