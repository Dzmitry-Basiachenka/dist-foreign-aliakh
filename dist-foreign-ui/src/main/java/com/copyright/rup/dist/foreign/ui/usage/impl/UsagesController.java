package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageFilter;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;
import com.copyright.rup.dist.foreign.repository.api.Pageable;
import com.copyright.rup.dist.foreign.repository.api.Sort;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.impl.csvprocessor.CsvProcessingResult;
import com.copyright.rup.dist.foreign.service.impl.csvprocessor.UsageCsvProcessor;
import com.copyright.rup.dist.foreign.service.impl.csvprocessor.UsageCsvProcessorFactory;
import com.copyright.rup.dist.foreign.ui.common.ExportStreamSource;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesController;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.CreateScenarioWindow.ScenarioCreateEvent;
import com.copyright.rup.vaadin.ui.VaadinUtils;
import com.copyright.rup.vaadin.ui.component.downloader.IStreamSource;
import com.copyright.rup.vaadin.widget.api.CommonController;

import com.google.common.base.MoreObjects;
import com.google.common.io.Files;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Set;
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
    @Autowired
    private UsageCsvProcessorFactory usageCsvProcessorFactory;

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
    public Rightsholder getRro(Long rroAccountNumber) {
        return MoreObjects.firstNonNull(prmIntegrationService.getRightsholder(rroAccountNumber), new Rightsholder());
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
        return new ExportStreamSource("export_usage_",
            pipedStream -> usageService.writeUsageCsvReport(filterController.getWidget().getAppliedFilter(),
                pipedStream));
    }

    @Override
    public IStreamSource getSendForResearchUsagesStreamSource() {
        return new ExportStreamSource("send_for_research_", pipedStream ->
            usageService.sendForResearch(filterController.getWidget().getAppliedFilter(), pipedStream));
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
    public UsageCsvProcessor getCsvProcessor() {
        return usageCsvProcessorFactory.getProcessor();
    }

    @Override
    public boolean isProductFamilyAndStatusFiltersApplied() {
        UsageFilter filter = filterController.getWidget().getAppliedFilter();
        return UsageStatusEnum.ELIGIBLE == filter.getUsageStatus()
            && CollectionUtils.isNotEmpty(filter.getProductFamilies());
    }

    @Override
    public boolean isSigleProductFamilySelected() {
        Set<String> productFamilies = filterController.getWidget().getAppliedFilter().getProductFamilies();
        return 1 == CollectionUtils.size(productFamilies) && !productFamilies.contains("NTS");
    }

    @Override
    public boolean isWorkNotFoundStatusApplied() {
        return UsageStatusEnum.WORK_NOT_FOUND == filterController.getWidget().getAppliedFilter().getUsageStatus();
    }

    @Override
    protected IUsagesWidget instantiateWidget() {
        return new UsagesWidget();
    }

    private static class ErrorResultStreamSource implements IStreamSource {

        private final ExecutorService executorService = Executors.newSingleThreadExecutor();
        private final IUsageService usageService;
        private final CsvProcessingResult csvProcessingResult;

        ErrorResultStreamSource(IUsageService usageService, CsvProcessingResult csvProcessingResult) {
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
                executorService.execute(() -> usageService.writeErrorsToFile(csvProcessingResult, outputStream));
                return pipedInputStream;
            } catch (IOException e) {
                throw new RupRuntimeException(e);
            }
        }
    }
}
