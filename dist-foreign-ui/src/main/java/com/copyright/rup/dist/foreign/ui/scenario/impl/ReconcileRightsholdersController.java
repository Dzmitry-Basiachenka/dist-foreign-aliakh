package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.api.Sort.Direction;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancy;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancyStatusEnum;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.service.api.IRightsholderDiscrepancyService;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.ui.report.api.IStreamSourceHandler;
import com.copyright.rup.dist.foreign.ui.scenario.api.IReconcileRightsholdersController;
import com.copyright.rup.vaadin.ui.component.downloader.IStreamSource;

import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.shared.data.sort.SortDirection;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.Collections;
import java.util.List;

/**
 * Implementation of {@link IReconcileRightsholdersController}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 1/26/18
 *
 * @author Ihar Suvorau
 */
@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReconcileRightsholdersController implements IReconcileRightsholdersController {

    private Scenario scenario;

    @Autowired
    private IScenarioService scenarioService;
    @Autowired
    private IRightsholderDiscrepancyService rightsholderDiscrepancyService;
    @Autowired
    private IReportService reportService;
    @Autowired
    private IStreamSourceHandler streamSourceHandler;

    @Override
    public void approveReconciliation() {
        scenarioService.approveOwnershipChanges(scenario);
    }

    @Override
    public Scenario getScenario() {
        return scenario;
    }

    @Override
    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    @Override
    public int getBeansCount() {
        return rightsholderDiscrepancyService.getCountByScenarioIdAndStatus(scenario.getId(),
            RightsholderDiscrepancyStatusEnum.DRAFT);
    }

    @Override
    public List<RightsholderDiscrepancy> loadBeans(int startIndex, int count, List<QuerySortOrder> sortOrders) {
        Sort sort = null;
        if (CollectionUtils.isNotEmpty(sortOrders)) {
            QuerySortOrder sortOrder = sortOrders.iterator().next();
            sort = new Sort(sortOrder.getSorted(), Direction.of(SortDirection.ASCENDING == sortOrder.getDirection()));
        }
        return rightsholderDiscrepancyService.getByScenarioIdAndStatus(scenario.getId(),
            RightsholderDiscrepancyStatusEnum.DRAFT, new Pageable(startIndex, count), sort);
    }

    @Override
    public List<Long> getProhibitedAccountNumbers() {
        return rightsholderDiscrepancyService.getProhibitedAccountNumbers(scenario.getId());
    }

    @Override
    public IStreamSource getCsvStreamSource() {
        return streamSourceHandler.getCsvStreamSource(
            () -> String.format("ownership_adjustment_report_%s_", scenario.getName()),
            pos -> reportService.writeOwnershipAdjustmentCsvReport(scenario.getId(),
                Collections.singleton(RightsholderDiscrepancyStatusEnum.DRAFT), pos));
    }
}
