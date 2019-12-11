package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.reporting.api.IStreamSourceHandler;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor;
import com.copyright.rup.dist.foreign.domain.ResearchedUsage;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.service.api.IResearchService;
import com.copyright.rup.dist.foreign.service.impl.csv.CsvProcessorFactory;
import com.copyright.rup.dist.foreign.service.impl.csv.ResearchedUsagesCsvProcessor;
import com.copyright.rup.dist.foreign.service.impl.csv.UsageCsvProcessor;
import com.copyright.rup.dist.foreign.ui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.IFasUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.IFasUsageWidget;

import com.google.common.base.MoreObjects;
import com.google.common.io.Files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * Implementation of {@link com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageController} for FAS and FAS2
 * product families.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/5/19
 *
 * @author Uladzislau Shalamitski
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FasUsageController extends CommonUsageController<IFasUsageWidget, IFasUsageController>
    implements IFasUsageController {

    @Autowired
    private IResearchService researchService;
    @Autowired
    private IPrmIntegrationService prmIntegrationService;
    @Autowired
    private CsvProcessorFactory csvProcessorFactory;
    @Autowired
    private IReportService reportService;
    @Autowired
    private IStreamSourceHandler streamSourceHandler;

    @Override
    public void onFilterChanged(FilterChangedEvent event) {
        getWidget().refresh();
    }

    @Override
    public Rightsholder getRro(Long rroAccountNumber) {
        return MoreObjects.firstNonNull(prmIntegrationService.getRightsholder(rroAccountNumber), new Rightsholder());
    }

    @Override
    public int loadUsageBatch(UsageBatch usageBatch, Collection<Usage> usages) {
        int result = getUsageBatchService().insertFasBatch(usageBatch, usages);
        getUsageBatchService().sendForMatching(usages);
        getUsageBatchService().sendForGettingRights(usages, usageBatch.getName());
        getFilterController().getWidget().clearFilter();
        return result;
    }

    @Override
    public void loadResearchedUsages(List<ResearchedUsage> researchedUsages) {
        getUsageService().loadResearchedUsages(researchedUsages);
        getFilterController().getWidget().clearFilter();
    }

    @Override
    public Scenario createScenario(String scenarioName, String description) {
        Scenario scenario = getScenarioService().createScenario(scenarioName, description,
            getFilterController().getWidget().getAppliedFilter());
        getFilterController().getWidget().clearFilter();
        return scenario;
    }

    @Override
    public Long getClaAccountNumber() {
        return getUsageService().getClaAccountNumber();
    }

    @Override
    public IStreamSource getExportUsagesStreamSource() {
        return streamSourceHandler.getCsvStreamSource(() -> "export_usage_",
            pos -> reportService.writeUsageCsvReport(getFilterController().getWidget().getAppliedFilter(), pos));
    }

    @Override
    public IStreamSource getSendForResearchUsagesStreamSource() {
        return new ByteArrayStreamSource("send_for_research_", pipedStream ->
            researchService.sendForResearch(getFilterController().getWidget().getAppliedFilter(), pipedStream));
    }

    @Override
    public IStreamSource getErrorResultStreamSource(String fileName,
                                                    DistCsvProcessor.ProcessingResult processingResult) {
        return streamSourceHandler.getCsvStreamSource(
            () -> String.format("Error_for_%s", Files.getNameWithoutExtension(fileName)), null,
            processingResult::writeToFile);
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
    public List<Long> getInvalidRightsholders() {
        return getUsageService().getInvalidRightsholdersByFilter(getFilterController().getWidget().getAppliedFilter());
    }

    @Override
    protected IFasUsageWidget instantiateWidget() {
        return new FasUsageWidget();
    }
}
