package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.grant;

import com.copyright.rup.dist.foreign.domain.AclGrantDetailDto;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmBaselineService;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclGrantDetailController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclGrantDetailFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclGrantDetailFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclGrantDetailWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import com.vaadin.data.provider.QuerySortOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Implementation of {@link IAclGrantDetailController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/27/2022
 *
 * @author Dzmitry Basiachenka
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AclGrantDetailController extends CommonController<IAclGrantDetailWidget>
    implements IAclGrantDetailController {

    @Autowired
    private IUdmBaselineService udmBaselineService;
    @Autowired
    private IAclGrantDetailFilterController aclGrantDetailFilterController;

    @Override
    public int getBeansCount() {
        // TODO {aliakh} implement for filter story
        return 0;
    }

    @Override
    public List<AclGrantDetailDto> loadBeans(int startIndex, int count, List<QuerySortOrder> sortOrders) {
        // TODO {aliakh} implement for filter story
        return Collections.emptyList();
    }

    @Override
    public List<Integer> getBaselinePeriods() {
        return udmBaselineService.getPeriods();
    }

    @Override
    public IAclGrantDetailFilterWidget initAclGrantDetailFilterWidget() {
        return aclGrantDetailFilterController.initWidget();
    }

    @Override
    protected IAclGrantDetailWidget instantiateWidget() {
        return new AclGrantDetailWidget();
    }
}
