package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;
import com.copyright.rup.dist.foreign.repository.api.Pageable;
import com.copyright.rup.dist.foreign.repository.api.Sort;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.impl.csvprocessor.CsvProcessingResult;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesController;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.CreateScenarioWindow.ScenarioCreateEvent;
import com.copyright.rup.vaadin.ui.VaadinUtils;
import com.copyright.rup.vaadin.ui.component.downloader.IStreamSource;
import com.copyright.rup.vaadin.widget.api.CommonController;

import com.google.common.io.Files;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Controller for {@link UsagesWidget}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 01/16/17
 *
 * @author Mikita Hladkikh
 * @author Aliaksandr Radkevich
 * @author Mikalai Bezmen
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UsagesController extends CommonController<IUsagesWidget> implements IUsagesController {

    @Autowired
    private IUsageBatchService usageBatchService;

    @Autowired
    private IUsageService usageService;

    @Autowired
    private IUsagesFilterController filterController;

    @Autowired
    private IPrmIntegrationService prmIntegrationService;

    @Autowired
    private IScenarioService scenarioService;

    @Override
    public IUsagesFilterWidget initUsagesFilterWidget() {
        IUsagesFilterWidget result = filterController.initWidget();
        result.addListener(FilterChangedEvent.class, this, IUsagesController.ON_FILTER_CHANGED);
        return result;
    }

    @Override
    public void onFilterChanged(FilterChangedEvent event) {
        getWidget().refresh();
    }

    @Override
    public int getSize() {
        return usageService.getUsagesCount(filterController.getWidget().getAppliedFilter());
    }

    @Override
    public List<UsageDto> loadBeans(int startIndex, int count, Object[] sortPropertyIds, boolean... sortStates) {
        return usageService.getUsages(filterController.getWidget().getAppliedFilter(), new Pageable(startIndex, count),
            Sort.create(sortPropertyIds, sortStates));
    }

    @Override
    public String getRroName(Long rroAccountNumber) {
        Rightsholder rro = prmIntegrationService.getRightsholder(rroAccountNumber);
        return null != rro ? rro.getName() : null;
    }

    @Override
    public boolean usageBatchExists(String name) {
        return usageBatchService.usageBatchExists(name);
    }

    @Override
    public int loadUsageBatch(UsageBatch usageBatch, List<Usage> usages) {
        int result = usageBatchService.insertUsageBatch(usageBatch, usages);
        filterController.getWidget().clearFilter();
        return result;
    }

    @Override
    public String createScenario(String scenarioName, String description) {
        String scenarioId =
            scenarioService.createScenario(scenarioName, description, filterController.getWidget().getAppliedFilter());
        filterController.getWidget().clearFilter();
        return scenarioId;
    }

    @Override
    public IScenarioService getScenarioService() {
        return scenarioService;
    }

    @Override
    public List<String> getScenariosNamesAssociatedWithUsageBatch(String batchId) {
        return ObjectUtils.defaultIfNull(scenarioService.getScenariosNamesByUsageBatchId(batchId),
            Collections.emptyList());
    }

    @Override
    public List<UsageBatch> getUsageBatches() {
        return usageBatchService.getUsageBatches();
    }

    @Override
    public void deleteUsageBatch(UsageBatch usageBatch) {
        usageBatchService.deleteUsageBatch(usageBatch);
        filterController.getWidget().clearFilter();
    }

    @Override
    public IStreamSource getExportUsagesStreamSource() {
        return new ExportStreamSource(usageService, filterController);
    }

    @Override
    public IStreamSource getErrorResultStreamSource(CsvProcessingResult csvProcessingResult) {
        return new ErrorResultStreamSource(usageService, csvProcessingResult);
    }

    @Override
    public void onScenarioCreated(ScenarioCreateEvent event) {
        getWidget().fireWidgetEvent(event);
    }

    @Override
    protected IUsagesWidget instantiateWidget() {
        return new UsagesWidget();
    }

    /**
     * Export stream source.
     */
    public static class ExportStreamSource implements IStreamSource {

        private ExecutorService executorService = Executors.newSingleThreadExecutor();
        private IUsageService usageService;
        private IUsagesFilterController controller;

        /**
         * Constructor.
         *
         * @param usageService instance of {@link IUsageService}
         * @param controller   instance of {@link IUsagesController}
         */
        public ExportStreamSource(IUsageService usageService, IUsagesFilterController controller) {
            this.usageService = usageService;
            this.controller = controller;
        }

        @Override
        public InputStream getStream() {
            try {
                PipedOutputStream outputStream = new PipedOutputStream();
                PipedInputStream pipedInputStream = new PipedInputStream(outputStream);
                executorService.execute(
                    () -> usageService.writeUsageCsvReport(controller.getWidget().getAppliedFilter(), outputStream));
                return pipedInputStream;
            } catch (IOException e) {
                throw new RupRuntimeException(e);
            }
        }

        @Override
        public String getFileName() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM_dd_YYYY");
            return VaadinUtils.encodeAndBuildFileName(
                String.format("export_usage_%s", LocalDate.now().format(formatter)), "csv");
        }
    }

    /**
     * Error results stream source.
     */
    public static class ErrorResultStreamSource implements IStreamSource {

        private ExecutorService executorService = Executors.newSingleThreadExecutor();
        private CsvProcessingResult csvProcessingResult;
        private IUsageService usageService;

        /**
         * Constructor.
         *
         * @param csvProcessingResult information about errors
         * @param usageService        instance of {@link IUsageService}
         */
        public ErrorResultStreamSource(IUsageService usageService, CsvProcessingResult csvProcessingResult) {
            this.csvProcessingResult = csvProcessingResult;
            this.usageService = usageService;
        }

        @Override
        public String getFileName() {
            return VaadinUtils.encodeAndBuildFileName(
                String.format("Error_for_%s", Files.getNameWithoutExtension(csvProcessingResult.getFileName())), "csv");
        }

        @Override
        public InputStream getStream() {
            try {
                PipedOutputStream outputStream = new PipedOutputStream();
                PipedInputStream pipedInputStream = new PipedInputStream(outputStream);
                executorService.execute(() -> usageService.writeErrorsCsvReport(csvProcessingResult, outputStream));
                return pipedInputStream;
            } catch (IOException e) {
                throw new RupRuntimeException(e);
            }
        }
    }
}
