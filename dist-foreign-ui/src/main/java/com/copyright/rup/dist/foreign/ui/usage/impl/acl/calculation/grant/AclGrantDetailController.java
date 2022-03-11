package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.grant;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.api.Sort.Direction;
import com.copyright.rup.dist.foreign.domain.AclGrantDetailDto;
import com.copyright.rup.dist.foreign.domain.AclGrantSet;
import com.copyright.rup.dist.foreign.domain.filter.AclGrantDetailFilter;
import com.copyright.rup.dist.foreign.service.api.acl.IAclGrantDetailService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclGrantSetService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmBaselineService;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclGrantDetailController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclGrantDetailFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclGrantDetailFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclGrantDetailWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.shared.data.sort.SortDirection;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

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
    private IAclGrantSetService aclGrantSetService;
    @Autowired
    private IAclGrantDetailService aclGrantDetailService;
    @Autowired
    private IAclGrantDetailFilterController aclGrantDetailFilterController;

    @Override
    public int getBeansCount() {
        return aclGrantDetailService.getCount(getFilter());
    }

    @Override
    public List<AclGrantDetailDto> loadBeans(int startIndex, int count, List<QuerySortOrder> sortOrders) {
        Sort sort = null;
        if (CollectionUtils.isNotEmpty(sortOrders)) {
            QuerySortOrder sortOrder = sortOrders.get(0);
            sort = new Sort(sortOrder.getSorted(), Direction.of(SortDirection.ASCENDING == sortOrder.getDirection()));
        }
        return aclGrantDetailService.getDtos(getFilter(), new Pageable(startIndex, count), sort);
    }

    @Override
    public List<Integer> getBaselinePeriods() {
        return udmBaselineService.getPeriods();
    }

    @Override
    public IAclGrantDetailFilterWidget initAclGrantDetailFilterWidget() {
        IAclGrantDetailFilterWidget widget = aclGrantDetailFilterController.initWidget();
        widget.addListener(FilterChangedEvent.class, this, IAclGrantDetailController.ON_FILTER_CHANGED);
        return widget;
    }

    @Override
    public void updateAclGrants(Set<AclGrantDetailDto> aclGrantDetailDtos) {
        //TODO will implement later
    }

    @Override
    public boolean isGrantSetExist(String name) {
        return aclGrantSetService.isGrantSetExist(name);
    }

    @Override
    public int insertAclGrantSet(AclGrantSet aclGrantSet) {
        return aclGrantSetService.insert(aclGrantSet);
    }

    @Override
    public void onFilterChanged(FilterChangedEvent event) {
        getWidget().refresh();
    }

    @Override
    protected IAclGrantDetailWidget instantiateWidget() {
        return new AclGrantDetailWidget();
    }

    private AclGrantDetailFilter getFilter() {
        return aclGrantDetailFilterController.getWidget().getAppliedFilter();
    }
}
