package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.usage;

import com.copyright.rup.dist.foreign.domain.AclUsageDto;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclUsageWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import com.vaadin.data.provider.QuerySortOrder;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link IAclUsageController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/30/2022
 *
 * @author Dzmitry Basiachenka
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AclUsageController extends CommonController<IAclUsageWidget> implements IAclUsageController {

    @Override
    public int getBeansCount() {
        //TODO {dbasiachenka} implement
        return 0;
    }

    @Override
    public List<AclUsageDto> loadBeans(int startIndex, int count, List<QuerySortOrder> sortOrders) {
        //TODO {dbasiachenka} implement
        return new ArrayList<>();
    }

    @Override
    protected IAclUsageWidget instantiateWidget() {
        return new AclUsageWidget();
    }
}
