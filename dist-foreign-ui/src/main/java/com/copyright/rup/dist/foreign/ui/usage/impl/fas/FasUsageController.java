package com.copyright.rup.dist.foreign.ui.usage.impl.fas;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.reporting.api.IStreamSourceHandler;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.foreign.domain.ResearchedUsage;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.service.api.IResearchService;
import com.copyright.rup.dist.foreign.service.impl.csv.CsvProcessorFactory;
import com.copyright.rup.dist.foreign.service.impl.csv.ResearchedUsagesCsvProcessor;
import com.copyright.rup.dist.foreign.service.impl.csv.UsageCsvProcessor;
import com.copyright.rup.dist.foreign.ui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.fas.IFasUsageController;
import com.copyright.rup.dist.foreign.ui.usage.impl.CommonUsageController;

import com.google.common.io.Files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * Implementation of {@link IFasUsageController}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/16/19
 *
 * @author Darya Baraukova
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FasUsageController extends CommonUsageController implements IFasUsageController {

    @Autowired
    private CsvProcessorFactory csvProcessorFactory;
    @Autowired
    private IStreamSourceHandler streamSourceHandler;
    @Autowired
    private IResearchService researchService;

    @Override
    public int loadUsageBatch(UsageBatch usageBatch, Collection<Usage> usages) {
        int result = getUsageBatchService().insertFasBatch(usageBatch, usages);
        getUsageBatchService().sendForMatching(usages);
        getUsageBatchService().sendForGettingRights(usages, usageBatch.getName());
        getUsageFilterController().getWidget().clearFilter();
        return result;
    }

    @Override
    public Long getClaAccountNumber() {
        return getUsageService().getClaAccountNumber();
    }

    @Override
    public IStreamSource getExportUsagesStreamSource() {
        return streamSourceHandler.getCsvStreamSource(() -> "export_usage_",
            pos -> getReportService().writeFasUsageCsvReport(
                getUsageFilterController().getWidget().getAppliedFilter(), pos));
    }

    @Override
    public ResearchedUsagesCsvProcessor getResearchedUsagesCsvProcessor() {
        return csvProcessorFactory.getResearchedUsagesCsvProcessor();
    }

    @Override
    public UsageCsvProcessor getCsvProcessor(String productFamily) {
        return csvProcessorFactory.getUsageCsvProcessor(productFamily);
    }

    @Override
    public void loadResearchedUsages(List<ResearchedUsage> researchedUsages) {
        getUsageService().loadResearchedUsages(researchedUsages);
        getUsageFilterController().getWidget().clearFilter();
    }

    @Override
    public IStreamSource getSendForResearchUsagesStreamSource() {
        return new ByteArrayStreamSource("send_for_research_", pipedStream ->
            researchService.sendForResearch(getUsageFilterController().getWidget().getAppliedFilter(), pipedStream));
    }

    @Override
    public IStreamSource getErrorResultStreamSource(String fileName, ProcessingResult processingResult) {
        return streamSourceHandler.getCsvStreamSource(
            () -> String.format("Error_for_%s", Files.getNameWithoutExtension(fileName)), null,
            processingResult::writeToFile);
    }

    @Override
    protected ICommonUsageWidget instantiateWidget() {
        return new FasUsageWidget(this);
    }
}
