package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.api.Sort.Direction;
import com.copyright.rup.dist.foreign.domain.WorkClassification;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.IWorkClassificationService;
import com.copyright.rup.dist.foreign.ui.usage.api.IWorkClassificationController;

import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.shared.data.sort.SortDirection;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Controller for {@link WorkClassificationWindow}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 03/14/2019
 *
 * @author Ihar Suvorau
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class WorkClassificationController implements IWorkClassificationController {

    @Autowired
    private IWorkClassificationService workClassificationService;
    @Autowired
    private IUsageService usageService;

    @Override
    public int getClassificationCount(Set<String> batchesIds, String searchValue) {
        return workClassificationService.getClassificationCount(batchesIds, searchValue);
    }

    @Override
    public List<WorkClassification> getClassifications(Set<String> batchesIds, String searchValue, int startIndex,
                                                       int count, List<QuerySortOrder> sortOrders) {
        Sort sort = null;
        if (CollectionUtils.isNotEmpty(sortOrders)) {
            QuerySortOrder sortOrder = sortOrders.get(0);
            sort = new Sort(sortOrder.getSorted(), Direction.of(SortDirection.ASCENDING == sortOrder.getDirection()));
        }
        return workClassificationService.getClassifications(batchesIds, searchValue,
            new Pageable(startIndex, count), sort);
    }

    @Override
    public void updateClassifications(Set<WorkClassification> classifications, String newClassification) {
        workClassificationService.insertOrUpdateClassifications(classifications, newClassification);
    }

    @Override
    public void deleteClassification(Set<WorkClassification> classifications) {
        workClassificationService.deleteClassifications(classifications);
    }

    @Override
    public int getCountToUpdate(Set<WorkClassification> classifications) {
        return usageService.getUnclassifiedUsagesCount(
            classifications.stream().map(WorkClassification::getWrWrkInst).collect(Collectors.toSet()));
    }
}
