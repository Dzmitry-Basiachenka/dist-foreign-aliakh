package com.copyright.rup.dist.foreign.ui.scenario.impl.fas;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.reporting.api.IStreamSourceHandler;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.api.Sort.Direction;
import com.copyright.rup.dist.foreign.domain.PayeeTotalHolder;
import com.copyright.rup.dist.foreign.domain.filter.ExcludePayeeFilter;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.fas.IFasUsageService;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IExcludePayeeController;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IExcludePayeeFilterController;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IExcludePayeeWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.shared.data.sort.SortDirection;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Set;

/**
 * Implementation of {@link IExcludePayeeController}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 10/14/19
 *
 * @author Uladzislau Shalamitski
 */
@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ExcludePayeeController extends CommonController<IExcludePayeeWidget> implements IExcludePayeeController {

    @Autowired
    private IUsageService usageService;
    @Autowired
    private IFasUsageService fasUsageService;
    @Autowired
    private IExcludePayeeFilterController payeesFilterController;
    @Autowired
    private IReportService reportService;
    @Autowired
    private IStreamSourceHandler streamSourceHandler;

    @Override
    public IExcludePayeeFilterController getExcludePayeesFilterController() {
        return payeesFilterController;
    }

    @Override
    public void onFilterChanged() {
        getWidget().refresh();
    }

    @Override
    public List<PayeeTotalHolder> getPayeeTotalHolders(int startIndex, int count, List<QuerySortOrder> sortOrders) {
        ExcludePayeeFilter filter = payeesFilterController.getWidget().getAppliedFilter();
        filter.setSearchValue(getWidget().getSearchValue());
        Sort sort = null;
        if (CollectionUtils.isNotEmpty(sortOrders)) {
            QuerySortOrder sortOrder = sortOrders.get(0);
            sort = new Sort(sortOrder.getSorted(), Direction.of(SortDirection.ASCENDING == sortOrder.getDirection()));
        }
        return usageService.getPayeeTotalHoldersByFilter(filter, new Pageable(startIndex, count), sort);
    }

    @Override
    public int getPayeeTotalHoldersCount() {
        ExcludePayeeFilter filter = payeesFilterController.getWidget().getAppliedFilter();
        filter.setSearchValue(getWidget().getSearchValue());
        return usageService.getPayeeTotalHoldersCountByFilter(filter);
    }

    @Override
    public void excludeDetails(Set<Long> payeeAccountNumbers, String reason) {
        fasUsageService.deleteFromScenarioByPayees(
            payeesFilterController.getWidget().getAppliedFilter().getScenarioIds(), payeeAccountNumbers, reason);
    }

    @Override
    public void redesignateDetails(Set<Long> payeeAccountNumbers, String reason) {
        fasUsageService.redesignateToNtsWithdrawnByPayees(
            payeesFilterController.getWidget().getAppliedFilter().getScenarioIds(), payeeAccountNumbers, reason);
    }

    @Override
    public Set<Long> getAccountNumbersInvalidForExclude(Set<Long> accountNumbers) {
        return fasUsageService.getAccountNumbersInvalidForExclude(
            payeesFilterController.getWidget().getAppliedFilter().getScenarioIds(), accountNumbers);
    }

    @Override
    protected IExcludePayeeWidget instantiateWidget() {
        return new ExcludePayeeWidget();
    }

    @Override
    public IStreamSource getCsvStreamSource() {
        return streamSourceHandler.getCsvStreamSource(() -> "exclude_by_payee_",
            pos -> {
                ExcludePayeeFilter filter = payeesFilterController.getWidget().getAppliedFilter();
                filter.setSearchValue(getWidget().getSearchValue());
                reportService.writeExcludeDetailsByPayeeCsvReport(filter, getWidget().getSelectedAccountNumbers(), pos);
            });
    }
}
