package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.baseline.value;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.api.Sort.Direction;
import com.copyright.rup.dist.foreign.domain.UdmValueBaselineDto;
import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineValueFilter;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmBaselineValueService;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineValueController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineValueFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineValueFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineValueWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.shared.data.sort.SortDirection;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementation of {@link IUdmBaselineValueController}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/23/2021
 *
 * @author Anton Azarenka
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UdmBaselineValueController extends CommonController<IUdmBaselineValueWidget> implements
    IUdmBaselineValueController {

    @Autowired
    private IUdmBaselineValueFilterController udmBaselineValueFilterController;
    @Autowired
    private IUdmBaselineValueService udmBaselineValueService;

    @Override
    public void onFilterChanged(FilterChangedEvent event) {
        getWidget().refresh();
    }

    @Override
    public List<UdmValueBaselineDto> loadBeans(int startIndex, int count, List<QuerySortOrder> sortOrders) {
        Sort sort = null;
        if (CollectionUtils.isNotEmpty(sortOrders)) {
            QuerySortOrder sortOrder = sortOrders.get(0);
            sort = new Sort(sortOrder.getSorted(), Direction.of(SortDirection.ASCENDING == sortOrder.getDirection()));
        }
        return udmBaselineValueService.getValueDtos(getFilter(), new Pageable(startIndex, count), sort);
    }

    @Override
    public int getBeansCount() {
        return udmBaselineValueService.getBaselineValueCount(getFilter());
    }

    @Override
    public IUdmBaselineValueFilterWidget initBaselineValuesFilterWidget() {
        IUdmBaselineValueFilterWidget widget = udmBaselineValueFilterController.initWidget();
        widget.addListener(FilterChangedEvent.class, this, IUdmBaselineValueController.ON_FILTER_CHANGED);
        return widget;
    }

    @Override
    protected IUdmBaselineValueWidget instantiateWidget() {
        return new UdmBaselineValueWidget();
    }

    private UdmBaselineValueFilter getFilter() {
        return udmBaselineValueFilterController.getWidget().getAppliedFilter();
    }
}
