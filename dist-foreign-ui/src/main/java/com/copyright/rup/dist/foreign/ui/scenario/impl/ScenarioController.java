package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.RightsholderPayeePair;
import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.repository.api.Pageable;
import com.copyright.rup.dist.foreign.repository.api.Sort;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.ui.scenario.api.IDrillDownByRightsholderController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenarioController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenarioWidget;
import com.copyright.rup.dist.foreign.ui.scenario.impl.ExcludeRightsholdersWindow.ExcludeUsagesEvent;
import com.copyright.rup.vaadin.ui.VaadinUtils;
import com.copyright.rup.vaadin.ui.Windows;
import com.copyright.rup.vaadin.ui.component.downloader.IStreamSource;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Controller class for {@link ScenarioWidget}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 04/06/17
 *
 * @author Ihar Suvorau
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ScenarioController extends CommonController<IScenarioWidget> implements IScenarioController {

    private Scenario scenario;

    @Autowired
    private IUsageService usageService;
    @Autowired
    private IScenarioService scenarioService;
    @Autowired
    private IDrillDownByRightsholderController drillDownByRightsholderController;

    @Override
    public int getSize() {
        return usageService.getRightsholderTotalsHolderCountByScenario(scenario, getWidget().getSearchValue());
    }

    @Override
    public List<RightsholderTotalsHolder> loadBeans(int startIndex, int count, Object[] sortPropertyIds,
                                                    boolean... sortStates) {
        return usageService.getRightsholderTotalsHoldersByScenario(scenario, getWidget().getSearchValue(),
            new Pageable(startIndex, count), Sort.create(sortPropertyIds, sortStates));
    }

    @Override
    public void performSearch() {
        getWidget().applySearch();
    }

    @Override
    public void onRightsholderAccountNumberClicked(Long accountNumber, String rhName) {
        drillDownByRightsholderController.showWidget(accountNumber, rhName, scenario);
    }

    @Override
    public IStreamSource getExportScenarioUsagesStreamSource() {
        return new ExportScenarioUsagesStreamSource(usageService, getScenario());
    }

    @Override
    protected IScenarioWidget instantiateWidget() {
        return new ScenarioWidget();
    }

    @Override
    public void onExcludeDetailsClicked() {
        Windows.showModalWindow(new ExcludeSourceRroWindow(this));
    }

    @Override
    public boolean isScenarioEmpty() {
        return Objects.equals(0, usageService.getRightsholderTotalsHolderCountByScenario(getScenario(), null));
    }

    @Override
    public List<Rightsholder> getSourceRros() {
        return scenarioService.getSourceRros(getScenario().getId());
    }

    @Override
    public void deleteFromScenario(Long rroAccountNumber, List<Long> accountNumbers, String reason) {
        usageService.deleteFromScenario(getScenario(), rroAccountNumber, accountNumbers, reason);
    }

    @Override
    public List<RightsholderPayeePair> getRightsholdersPayeePairs(Long rroAccountNumber) {
        return scenarioService.getRightsholdersByScenarioAndSourceRro(getScenario().getId(), rroAccountNumber);
    }

    @Override
    public Scenario getScenario() {
        return scenario;
    }

    @Override
    public Scenario getScenarioWithAmountsAndLastAction() {
        return scenarioService.getScenarioWithAmountsAndLastAction(scenario.getId());
    }

    @Override
    public void fireWidgetEvent(ExcludeUsagesEvent event) {
        getWidget().fireWidgetEvent(event);
    }

    void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    private static class ExportScenarioUsagesStreamSource implements IStreamSource {

        private ExecutorService executorService = Executors.newSingleThreadExecutor();
        private IUsageService usageService;
        private Scenario scenario;

        private ExportScenarioUsagesStreamSource(IUsageService usageService, Scenario scenario) {
            this.usageService = usageService;
            this.scenario = scenario;
        }

        @Override
        public InputStream getStream() {
            try {
                PipedOutputStream pipedOutputStream = new PipedOutputStream();
                PipedInputStream pipedInputStream = new PipedInputStream(pipedOutputStream);
                executorService.execute(
                    () -> usageService.writeScenarioUsagesCsvReport(scenario, pipedOutputStream));
                return pipedInputStream;
            } catch (IOException e) {
                throw new RupRuntimeException(e);
            }
        }

        @Override
        public String getFileName() {
            return VaadinUtils.encodeAndBuildFileName(scenario.getName(), "csv");
        }
    }
}
