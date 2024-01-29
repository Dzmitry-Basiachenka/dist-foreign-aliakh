package com.copyright.rup.dist.foreign.vui.usage.impl;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;
import com.copyright.rup.dist.foreign.service.api.IFundPoolService;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchStatusService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.vui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.vui.usage.api.ICommonUsageController;
import com.copyright.rup.dist.foreign.vui.usage.api.ICommonUsageFilterWidget;
import com.copyright.rup.dist.foreign.vui.usage.api.ICommonUsageWidget;
import com.copyright.rup.dist.foreign.vui.usage.api.ScenarioCreateEvent;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.CommonController;

import com.google.common.base.MoreObjects;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Common controller for {@link ICommonUsageWidget}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/05/2019
 *
 * @author Uladzislau Shalamitski
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public abstract class CommonUsageController extends CommonController<ICommonUsageWidget>
    implements ICommonUsageController {

    private static final long serialVersionUID = 8776033166851131516L;

    @Autowired
    private IUsageBatchService usageBatchService;
    @Autowired
    private IUsageBatchStatusService usageBatchStatusService;
    @Autowired
    private IUsageService usageService;
    @Autowired
    private IPrmIntegrationService prmIntegrationService;
    @Autowired
    private IScenarioService scenarioService;
    @Autowired
    private IReportService reportService;
    @Autowired
    private IFundPoolService fundPoolService;
    @Autowired
    private IProductFamilyProvider productFamilyProvider;

    @Override
    public ICommonUsageFilterWidget initUsagesFilterWidget() {
        ICommonUsageFilterWidget widget = getUsageFilterController().initWidget();
        widget.setFilterSaveAction(() -> getWidget().refresh());
        return widget;
    }

    @Override
    public Rightsholder getRightsholder(Long rhAccountNumber) {
        return MoreObjects.firstNonNull(prmIntegrationService.getRightsholder(rhAccountNumber), new Rightsholder());
    }

    @Override
    public List<String> getAdditionalFundNamesByUsageBatchId(String batchId) {
        return fundPoolService.getNtsFundPoolNamesByUsageBatchId(batchId);
    }

    @Override
    public boolean usageBatchExists(String name) {
        return usageBatchService.usageBatchExists(name);
    }

    @Override
    public Scenario createScenario(String scenarioName, String description) {
        var scenario = scenarioService.createScenario(scenarioName, description,
            getUsageFilterController().getWidget().getAppliedFilter());
        getUsageFilterController().getWidget().clearFilter();
        return scenario;
    }

    @Override
    public List<String> getScenariosNamesAssociatedWithUsageBatch(String batchId) {
        return ObjectUtils.defaultIfNull(scenarioService.getScenariosNamesByUsageBatchId(batchId), List.of());
    }

    @Override
    public List<UsageBatch> getUsageBatches(String productFamily) {
        return usageBatchService.getUsageBatches(productFamily);
    }

    @Override
    public void onScenarioCreated(ScenarioCreateEvent event) {
        getWidget().fireWidgetEvent(event);
    }

    @Override
    public String getSelectedProductFamily() {
        return productFamilyProvider.getSelectedProductFamily();
    }

    @Override
    public List<Long> getInvalidRightsholders() {
        return usageService.getInvalidRightsholdersByFilter(getUsageFilterController().getWidget().getAppliedFilter());
    }

    @Override
    public void clearFilter() {
        getUsageFilterController().getWidget().clearFilter();
    }

    @Override
    public boolean scenarioExists(String name) {
        return scenarioService.scenarioExists(name);
    }

    /**
     * @return {@link IScenarioService} instance.
     */
    protected IScenarioService getScenarioService() {
        return scenarioService;
    }

    /**
     * @return {@link IFundPoolService} instance.
     */
    protected IFundPoolService getFundPoolService() {
        return fundPoolService;
    }

    /**
     * @return {@link IUsageBatchService} instance.
     */
    protected IUsageBatchService getUsageBatchService() {
        return usageBatchService;
    }

    /**
     * @return {@link IUsageBatchStatusService} instance.
     */
    protected IUsageBatchStatusService getUsageBatchStatusService() {
        return usageBatchStatusService;
    }

    /**
     * @return {@link IUsageService} instance.
     */
    protected IUsageService getUsageService() {
        return usageService;
    }

    /**
     * @return {@link IReportService} instance.
     */
    protected IReportService getReportService() {
        return reportService;
    }

    /**
     * Instantiates widget.
     *
     * @return {@link ICommonUsageWidget} instance
     */
    @Override
    protected abstract ICommonUsageWidget instantiateWidget();
}
