package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.repository.api.Pageable;
import com.copyright.rup.dist.foreign.repository.api.Sort;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.ui.scenario.api.IDrillDownByRightsholderController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenarioController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenarioWidget;
import com.copyright.rup.vaadin.ui.VaadinUtils;
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
    private IDrillDownByRightsholderController drillDownByRightsholderController;

    @Override
    public int getSize() {
        return usageService.getRightsholderTotalsHolderCountByScenarioId(scenario.getId(),
            getWidget().getSearchValue());
    }

    @Override
    public List<RightsholderTotalsHolder> loadBeans(int startIndex, int count, Object[] sortPropertyIds,
                                                    boolean... sortStates) {
        return usageService.getRightsholderTotalsHoldersByScenarioId(scenario.getId(), getWidget().getSearchValue(),
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

    /**
     * Sets scenario.
     *
     * @param scenario instance of {@link Scenario}
     */
    void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    /**
     * Sets {@link IDrillDownByRightsholderController}.
     *
     * @param drillDownByRightsholderController {@link IDrillDownByRightsholderController}
     */
    void setDrillDownByRightsholderController(IDrillDownByRightsholderController drillDownByRightsholderController) {
        this.drillDownByRightsholderController = drillDownByRightsholderController;
    }

    /**
     * @return instance of {@link Scenario}.
     */
    Scenario getScenario() {
        return scenario;
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
                    () -> usageService.writeScenarioUsagesCsvReport(scenario.getId(), pipedOutputStream));
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
