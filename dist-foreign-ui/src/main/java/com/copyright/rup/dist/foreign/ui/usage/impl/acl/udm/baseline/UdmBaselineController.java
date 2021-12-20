package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.baseline;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.reporting.api.IStreamSourceHandler;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.UdmBaselineDto;
import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineFilter;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmBaselineService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmReportService;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineWidget;
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
 * Implementation of {@link IUdmBaselineController}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/01/2021
 *
 * @author Dzmitry Basiachenka
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UdmBaselineController extends CommonController<IUdmBaselineWidget> implements IUdmBaselineController {

    @Autowired
    private IUdmBaselineFilterController udmBaselineFilterController;
    @Autowired
    private IUdmBaselineService udmBaselineService;
    @Autowired
    private IUdmReportService udmReportService;
    @Autowired
    private IStreamSourceHandler streamSourceHandler;

    @Override
    public int getBeansCount() {
        return udmBaselineService.getBaselineUsagesCount(getFilter());
    }

    @Override
    public List<UdmBaselineDto> loadBeans(int startIndex, int count, List<QuerySortOrder> sortOrders) {
        Sort sort = null;
        if (CollectionUtils.isNotEmpty(sortOrders)) {
            QuerySortOrder sortOrder = sortOrders.get(0);
            sort = new Sort(sortOrder.getSorted(),
                Sort.Direction.of(SortDirection.ASCENDING == sortOrder.getDirection()));
        }
        return udmBaselineService.getBaselineUsageDtos(getFilter(), new Pageable(startIndex, count), sort);
    }

    @Override
    protected IUdmBaselineWidget instantiateWidget() {
        return new UdmBaselineWidget();
    }

    @Override
    public void onFilterChanged(FilterChangedEvent event) {
        getWidget().refresh();
    }

    @Override
    public IUdmBaselineFilterWidget initBaselineFilterWidget() {
        IUdmBaselineFilterWidget result = udmBaselineFilterController.initWidget();
        result.addListener(FilterChangedEvent.class, this, IUdmBaselineController.ON_FILTER_CHANGED);
        return result;
    }

    @Override
    public IStreamSource getExportUdmBaselineUsagesStreamSource() {
        return streamSourceHandler.getCsvStreamSource(() -> "export_udm_baseline_usage_",
            pos -> udmReportService.writeUdmBaselineUsageCsvReport(getFilter(), pos));
    }

    @Override
    public void deleteFromBaseline(Set<String> usageIds, String reason) {
        udmBaselineService.deleteFromBaseline(usageIds, reason, RupContextUtils.getUserName());
    }

    @Override
    public int getUdmRecordThreshold() {
        return udmBaselineService.getUdmRecordThreshold();
    }

    private UdmBaselineFilter getFilter() {
        return udmBaselineFilterController.getWidget().getAppliedFilter();
    }
}
