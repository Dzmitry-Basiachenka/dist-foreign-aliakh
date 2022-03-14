package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.grant;

import com.copyright.rup.dist.foreign.domain.AclGrantSet;
import com.copyright.rup.dist.foreign.service.api.acl.IAclGrantSetService;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclGrantDetailFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclGrantDetailFilterWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementation of {@link IAclGrantDetailFilterController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/27/2022
 *
 * @author Dzmitry Basiachenka
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AclGrantDetailFilterController extends CommonController<IAclGrantDetailFilterWidget>
    implements IAclGrantDetailFilterController {

    @Autowired
    private IAclGrantSetService aclGrantSetService;

    @Override
    protected IAclGrantDetailFilterWidget instantiateWidget() {
        return new AclGrantDetailFilterWidget(this);
    }

    @Override
    public List<AclGrantSet> getAllAclGrantSets() {
        return aclGrantSetService.getAll();
    }

    @Override
    public List<Integer> getGrantPeriods() {
        return aclGrantSetService.getGrantPeriods();
    }
}
