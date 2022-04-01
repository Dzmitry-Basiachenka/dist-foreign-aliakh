package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.usage;

import com.copyright.rup.dist.foreign.domain.AclUsageBatch;
import com.copyright.rup.dist.foreign.service.api.acl.IAclUsageBatchService;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclUsageFilterWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementation of {@link IAclUsageFilterController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/31/2022
 *
 * @author Dzmitry Basiachenka
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AclUsageFilterController extends CommonController<IAclUsageFilterWidget>
    implements IAclUsageFilterController {

    @Autowired
    private IAclUsageBatchService aclUsageBatchService;

    @Override
    public List<AclUsageBatch> getAllAclUsageBatches() {
        return aclUsageBatchService.getAll();
    }

    @Override
    protected IAclUsageFilterWidget instantiateWidget() {
        return new AclUsageFilterWidget();
    }
}
