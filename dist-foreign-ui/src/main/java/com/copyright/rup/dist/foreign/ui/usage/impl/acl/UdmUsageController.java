package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.reporting.api.IStreamSourceHandler;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.api.Sort.Direction;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.foreign.domain.UdmBatch;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.domain.UdmUsageDto;
import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmBatchService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageService;
import com.copyright.rup.dist.foreign.service.impl.csv.CsvProcessorFactory;
import com.copyright.rup.dist.foreign.service.impl.csv.UdmCsvProcessor;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import com.google.common.io.Files;
import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.shared.data.sort.SortDirection;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementation of {@link IUdmUsageController}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 04/26/2021
 *
 * @author Ihar Suvorau
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UdmUsageController extends CommonController<IUdmUsageWidget> implements IUdmUsageController {

    @Autowired
    private IUdmUsageService udmUsageService;
    @Autowired
    private IUdmBatchService udmBatchService;
    @Autowired
    private CsvProcessorFactory csvProcessorFactory;
    @Autowired
    private IStreamSourceHandler streamSourceHandler;
    @Autowired
    private IUdmUsageFilterController udmUsageFilterController;

    @Override
    public int getBeansCount() {
        UdmUsageFilter udmUsageFilter = udmUsageFilterController.getWidget().getAppliedFilter();
        return udmUsageService.getUsagesCount(udmUsageFilter);
    }

    @Override
    public List<UdmUsageDto> loadBeans(int startIndex, int count, List<QuerySortOrder> sortOrders) {
        Sort sort = null;
        if (CollectionUtils.isNotEmpty(sortOrders)) {
            QuerySortOrder sortOrder = sortOrders.get(0);
            sort = new Sort(sortOrder.getSorted(), Direction.of(SortDirection.ASCENDING == sortOrder.getDirection()));
        }
        UdmUsageFilter udmUsageFilter = udmUsageFilterController.getWidget().getAppliedFilter();
        return udmUsageService.getUsageDtos(udmUsageFilter, new Pageable(startIndex, count), sort);
    }

    @Override
    public IUdmUsageFilterWidget initUsagesFilterWidget() {
        IUdmUsageFilterWidget result = udmUsageFilterController.initWidget();
        result.addListener(FilterChangedEvent.class, this, IUdmUsageController.ON_FILTER_CHANGED);
        return result;
    }

    @Override
    public void onFilterChanged(FilterChangedEvent event) {
        getWidget().refresh();
    }

    @Override
    public IStreamSource getErrorResultStreamSource(String fileName, ProcessingResult processingResult) {
        return streamSourceHandler.getCsvStreamSource(
            () -> String.format("Error_for_%s", Files.getNameWithoutExtension(fileName)), null,
            processingResult::writeToFile);
    }

    @Override
    public UdmCsvProcessor getCsvProcessor() {
        return csvProcessorFactory.getUdmCsvProcessor();
    }

    @Override
    public int loadUdmBatch(UdmBatch udmBatch, List<UdmUsage> udmUsages) {
        return udmBatchService.insertUdmBatch(udmBatch, udmUsages);
    }

    @Override
    protected IUdmUsageWidget instantiateWidget() {
        return new UdmUsageWidget();
    }
}
