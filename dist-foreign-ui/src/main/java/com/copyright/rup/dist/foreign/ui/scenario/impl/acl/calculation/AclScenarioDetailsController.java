package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.reporting.api.IStreamSourceHandler;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.api.Sort.Direction;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.AclScenarioDetailDto;
import com.copyright.rup.dist.foreign.service.api.acl.IAclCalculationReportService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioUsageService;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioDetailsController;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.Window;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * Implementation of {@link IAclScenarioDetailsController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 11/17/2022
 *
 * @author Mikita Maistrenka
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AclScenarioDetailsController extends AclCommonScenarioDetailsController
    implements IAclScenarioDetailsController {

    @Autowired
    private IAclScenarioUsageService aclScenarioUsageService;
    @Autowired
    private IStreamSourceHandler streamSourceHandler;
    @Autowired
    private IAclCalculationReportService aclCalculationReportService;
    private AclScenario selectedScenario;

    @Override
    public List<AclScenarioDetailDto> loadBeans(int startIndex, int count, List<QuerySortOrder> sortOrders) {
        Sort sort = null;
        if (CollectionUtils.isNotEmpty(sortOrders)) {
            QuerySortOrder sortOrder = sortOrders.get(0);
            sort = new Sort(sortOrder.getSorted(), Direction.of(SortDirection.ASCENDING == sortOrder.getDirection()));
        }
        return aclScenarioUsageService.getByScenarioId(selectedScenario.getId(), getWidget().getSearchValue(),
            new Pageable(startIndex, count), sort);
    }

    @Override
    public int getSize() {
        return aclScenarioUsageService.getCountByScenarioId(selectedScenario.getId(), getWidget().getSearchValue());
    }

    @Override
    public void showWidget(AclScenario scenario) {
        selectedScenario = Objects.requireNonNull(scenario);
        Window windowByScenario = (Window) initWidget();
        windowByScenario.setCaption(ForeignUi.getMessage("window.scenario_details"));
        Windows.showModalWindow(windowByScenario);
    }

    @Override
    public IStreamSource getExportAclScenarioDetailsStreamSource() {
        return streamSourceHandler.getCsvStreamSource(() -> selectedScenario.getName() + "_Details_",
            pos -> aclCalculationReportService.writeAclScenarioDetailsCsvReport(selectedScenario.getId(), pos));
    }

    @Override
    protected AclScenarioDetailsWidget instantiateWidget() {
        return new AclScenarioDetailsWidget(this);
    }
}
