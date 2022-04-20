package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.fundpool;

import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclFundPoolController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclFundPoolFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclFundPoolFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclFundPoolWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link IAclFundPoolController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/17/2022
 *
 * @author Anton Azarenka
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AclFundPoolController extends CommonController<IAclFundPoolWidget> implements IAclFundPoolController {

    @Autowired
    private IAclFundPoolFilterController aclFundPoolFilterController;

    @Override
    public IAclFundPoolFilterWidget initAclFundPoolFilterWidget() {
        return aclFundPoolFilterController.initWidget();
    }

    @Override
    public boolean isFundPoolExist(String name) {
        //todo will implement later
        return false;
    }

    @Override
    protected IAclFundPoolWidget instantiateWidget() {
        return new AclFundPoolWidget();
    }
}
