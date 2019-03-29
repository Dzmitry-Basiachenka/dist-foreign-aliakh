package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.api.Sort.Direction;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.ResearchedUsage;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.WithdrawnFundPool;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.service.api.IResearchService;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.IWithdrawnFundPoolService;
import com.copyright.rup.dist.foreign.service.impl.csv.CsvProcessorFactory;
import com.copyright.rup.dist.foreign.service.impl.csv.ResearchedUsagesCsvProcessor;
import com.copyright.rup.dist.foreign.service.impl.csv.UsageCsvProcessor;
import com.copyright.rup.dist.foreign.ui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.ui.common.ExportStreamSource;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesController;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.IWorkClassificationController;
import com.copyright.rup.dist.foreign.ui.usage.impl.CreateScenarioWindow.ScenarioCreateEvent;
import com.copyright.rup.vaadin.ui.component.downloader.IStreamSource;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.api.CommonController;

import com.google.common.base.MoreObjects;
import com.google.common.io.Files;
import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.shared.data.sort.SortDirection;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Collection;
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

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IUsageBatchService usageBatchService;
    @Autowired
    private IUsageService usageService;
    @Autowired
    private IResearchService researchService;
    @Autowired
    private IUsagesFilterController filterController;
    @Autowired
    private IPrmIntegrationService prmIntegrationService;
    @Autowired
    private IScenarioService scenarioService;
    @Autowired
    private CsvProcessorFactory csvProcessorFactory;
    @Autowired
    private IReportService reportService;
    @Autowired
    private IWorkClassificationController workClassificationController;
    @Autowired
    private IWithdrawnFundPoolService withdrawnFundPoolService;

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
    public List<UsageDto> loadBeans(int startIndex, int count, List<QuerySortOrder> sortOrders) {
        Sort sort = null;
        if (CollectionUtils.isNotEmpty(sortOrders)) {
            QuerySortOrder sortOrder = sortOrders.get(0);
            sort = new Sort(sortOrder.getSorted(), Direction.of(SortDirection.ASCENDING == sortOrder.getDirection()));
        }
        return usageService
            .getUsageDtos(filterController.getWidget().getAppliedFilter(), new Pageable(startIndex, count), sort);
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
    public int loadUsageBatch(UsageBatch usageBatch, Collection<Usage> usages) {
        int result = usageBatchService.insertFasBatch(usageBatch, usages);
        usageBatchService.sendForMatching(usages);
        usageBatchService.sendForGettingRights(usages, usageBatch.getName());
        filterController.getWidget().clearFilter();
        return result;
    }

    @Override
    public int getUsagesCountForNtsBatch(UsageBatch usageBatch) {
        return usageService.getUsagesCountForNtsBatch(usageBatch);
    }

    @Override
    public void loadNtsBatch(UsageBatch usageBatch) {
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Insert NTS batch. Started. UsageBatchName={}, UserName={}", usageBatch.getName(), userName);
        List<String> ntsUsageIds = usageBatchService.insertNtsBatch(usageBatch, userName);
        LOGGER.info("Insert NTS batch. Finished. UsageBatchName={}, UserName={}, UsagesCount={}",
            usageBatch.getName(), userName, LogUtils.size(ntsUsageIds));
        usageBatchService.getAndSendForGettingRights(ntsUsageIds, usageBatch.getName());
        filterController.getWidget().clearFilter();
    }

    @Override
    public List<WithdrawnFundPool> getAdditionalFunds() {
        return withdrawnFundPoolService.getAdditionalFunds();
    }

    @Override
    public void loadResearchedUsages(Collection<ResearchedUsage> researchedUsages) {
        usageService.loadResearchedUsages(researchedUsages);
        filterController.getWidget().clearFilter();
    }

    @Override
    public Scenario createScenario(String scenarioName, String description) {
        Scenario scenario = scenarioService.createScenario(scenarioName, description,
            filterController.getWidget().getAppliedFilter());
        filterController.getWidget().clearFilter();
        return scenario;
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
    public List<UsageBatch> getUsageBatches(String productFamily) {
        return usageBatchService.getUsageBatches(productFamily);
    }

    @Override
    public List<UsageBatch> getWithdrawnUsageBatches() {
        return usageBatchService.getWithdrawnUsageBatches();
    }

    @Override
    public List<String> getMarkets() {
        return usageService.getMarkets();
    }

    @Override
    public Long getClaAccountNumber() {
        return usageService.getClaAccountNumber();
    }

    @Override
    public void deleteUsageBatch(UsageBatch usageBatch) {
        usageBatchService.deleteUsageBatch(usageBatch);
        filterController.getWidget().clearFilter();
    }

    @Override
    public IStreamSource getExportUsagesStreamSource() {
        return new ExportStreamSource("export_usage_",
            pipedStream -> reportService.writeUsageCsvReport(filterController.getWidget().getAppliedFilter(),
                pipedStream));
    }

    @Override
    public IStreamSource getSendForResearchUsagesStreamSource() {
        return new ByteArrayStreamSource("send_for_research_", pipedStream ->
            researchService.sendForResearch(filterController.getWidget().getAppliedFilter(), pipedStream));
    }

    @Override
    public IWorkClassificationController getWorkClassificationController() {
        return workClassificationController;
    }

    @Override
    public boolean isValidUsagesState(UsageStatusEnum status) {
        return usageService.isValidUsagesState(filterController.getWidget().getAppliedFilter(), status);
    }

    @Override
    public IStreamSource getErrorResultStreamSource(String fileName, ProcessingResult processingResult) {
        return new ErrorResultStreamSource(fileName, processingResult);
    }

    @Override
    public void onScenarioCreated(ScenarioCreateEvent event) {
        getWidget().fireWidgetEvent(event);
    }

    @Override
    public UsageCsvProcessor getCsvProcessor(String productFamily) {
        return csvProcessorFactory.getUsageCsvProcessor(productFamily);
    }

    @Override
    public ResearchedUsagesCsvProcessor getResearchedUsagesCsvProcessor() {
        return csvProcessorFactory.getResearchedUsagesCsvProcessor();
    }

    @Override
    public String getSelectedProductFamily() {
        return filterController.getWidget().getSelectedProductFamily();
    }

    @Override
    public List<Long> getInvalidRightsholders() {
        return usageService.getInvalidRightsholdersByFilter(filterController.getWidget().getAppliedFilter());
    }

    @Override
    public void clearFilter() {
        filterController.getWidget().clearFilter();
    }

    @Override
    protected IUsagesWidget instantiateWidget() {
        return new UsagesWidget();
    }

    private static class ErrorResultStreamSource implements IStreamSource {

        private final ExecutorService executorService = Executors.newSingleThreadExecutor();
        private final ProcessingResult processingResult;
        private final String fileName;

        ErrorResultStreamSource(String fileName, ProcessingResult processingResult) {
            this.fileName = fileName;
            this.processingResult = processingResult;
        }

        @Override
        public String getFileName() {
            return VaadinUtils.encodeAndBuildFileName(
                String.format("Error_for_%s", Files.getNameWithoutExtension(fileName)), "csv");
        }

        @Override
        public InputStream getStream() {
            try {
                PipedOutputStream outputStream = new PipedOutputStream();
                PipedInputStream pipedInputStream = new PipedInputStream(outputStream);
                executorService.execute(() -> processingResult.writeToFile(outputStream));
                return pipedInputStream;
            } catch (IOException e) {
                throw new RupRuntimeException(e);
            }
        }
    }
}
