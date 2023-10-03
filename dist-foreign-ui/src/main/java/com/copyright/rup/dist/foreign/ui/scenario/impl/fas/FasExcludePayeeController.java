package com.copyright.rup.dist.foreign.ui.scenario.impl.fas;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.reporting.api.IStreamSourceHandler;
import com.copyright.rup.dist.foreign.domain.PayeeTotalHolder;
import com.copyright.rup.dist.foreign.domain.filter.ExcludePayeeFilter;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.fas.IFasUsageService;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IFasExcludePayeeController;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IFasExcludePayeeFilterController;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IFasExcludePayeeWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * Implementation of {@link IFasExcludePayeeController}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 10/14/19
 *
 * @author Uladzislau Shalamitski
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FasExcludePayeeController extends CommonController<IFasExcludePayeeWidget>
    implements IFasExcludePayeeController {

    @Autowired
    private IUsageService usageService;
    @Autowired
    private IFasUsageService fasUsageService;
    @Autowired
    private IFasExcludePayeeFilterController payeesFilterController;
    @Autowired
    private IReportService reportService;
    @Autowired
    private IStreamSourceHandler streamSourceHandler;

    @Override
    public IFasExcludePayeeFilterController getExcludePayeesFilterController() {
        return payeesFilterController;
    }

    @Override
    public void onFilterChanged() {
        getWidget().refresh();
    }

    @Override
    public List<PayeeTotalHolder> getPayeeTotalHolders() {
        return usageService.getPayeeTotalHoldersByFilter(payeesFilterController.getWidget().getAppliedFilter());
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
    protected IFasExcludePayeeWidget instantiateWidget() {
        return new FasExcludePayeeWidget();
    }

    @Override
    public IStreamSource getCsvStreamSource() {
        return streamSourceHandler.getCsvStreamSource(() -> "exclude_by_payee_",
            pos -> {
                ExcludePayeeFilter filter = payeesFilterController.getWidget().getAppliedFilter();
                filter.setSearchValue(getWidget().getSearchValue());
                reportService.writeFasExcludeDetailsByPayeeCsvReport(filter, getWidget().getSelectedAccountNumbers(),
                    pos);
            });
    }
}
