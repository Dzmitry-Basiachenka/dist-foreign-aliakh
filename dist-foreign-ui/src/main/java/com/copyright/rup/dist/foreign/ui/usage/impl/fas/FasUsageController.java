package com.copyright.rup.dist.foreign.ui.usage.impl.fas;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.reporting.api.IStreamSourceHandler;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.api.Sort.Direction;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.foreign.domain.ResearchedUsage;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IResearchService;
import com.copyright.rup.dist.foreign.service.api.fas.IFasUsageService;
import com.copyright.rup.dist.foreign.service.impl.csv.CsvProcessorFactory;
import com.copyright.rup.dist.foreign.service.impl.csv.ResearchedUsagesCsvProcessor;
import com.copyright.rup.dist.foreign.service.impl.csv.UsageCsvProcessor;
import com.copyright.rup.dist.foreign.ui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.IFasNtsUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.fas.IFasUsageController;
import com.copyright.rup.dist.foreign.ui.usage.impl.CommonUsageController;

import com.google.common.io.Files;
import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.shared.data.sort.SortDirection;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

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
    @Autowired
    private IFasNtsUsageFilterController fasNtsUsageFilterController;
    @Autowired
    private IFasUsageService fasUsageService;

    @Override
    public ICommonUsageFilterController getUsageFilterController() {
        return fasNtsUsageFilterController;
    }

    @Override
    public int getBeansCount() {
        return fasUsageService.getUsagesCount(getUsageFilterController().getWidget().getAppliedFilter());
    }

    @Override
    public List<UsageDto> loadBeans(int startIndex, int count, List<QuerySortOrder> sortOrders) {
        Sort sort = null;
        if (CollectionUtils.isNotEmpty(sortOrders)) {
            QuerySortOrder sortOrder = sortOrders.get(0);
            sort = new Sort(sortOrder.getSorted(), Direction.of(SortDirection.ASCENDING == sortOrder.getDirection()));
        }
        return fasUsageService.getUsageDtos(getUsageFilterController().getWidget().getAppliedFilter(),
            new Pageable(startIndex, count), sort);
    }

    @Override
    public int loadUsageBatch(UsageBatch usageBatch, List<Usage> usages) {
        int result = getUsageBatchService().insertFasBatch(usageBatch, usages);
        getUsageService().sendForMatching(usages);
        getUsageService().sendForGettingRights(usages, usageBatch.getName());
        getUsageFilterController().getWidget().clearFilter();
        return result;
    }

    @Override
    public boolean isBatchProcessingCompleted(String batchId) {
        return getUsageBatchStatusService().isBatchProcessingCompleted(batchId,
            Set.of(UsageStatusEnum.NEW, UsageStatusEnum.WORK_FOUND, UsageStatusEnum.RH_FOUND));
    }

    @Override
    public void deleteUsageBatch(UsageBatch usageBatch) {
        getUsageBatchService().deleteUsageBatch(usageBatch);
        getUsageFilterController().getWidget().clearFilter();
    }

    @Override
    public boolean isValidFilteredUsageStatus(UsageStatusEnum status) {
        return getUsageService()
            .isValidFilteredUsageStatus(getUsageFilterController().getWidget().getAppliedFilter(), status);
    }

    @Override
    public Long getClaAccountNumber() {
        return fasUsageService.getClaAccountNumber();
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
        fasUsageService.loadResearchedUsages(researchedUsages);
        fasUsageService.sendForGettingRights(researchedUsages);
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
    public int getRecordsThreshold() {
        return fasUsageService.getRecordsThreshold();
    }

    @Override
    public List<UsageDto> getUsageDtosToUpdate() {
        return fasUsageService.getUsageDtos(getUsageFilterController().getWidget().getAppliedFilter(), null, null);
    }

    @Override
    public void updateUsages(List<String> usageIds, Long wrWrkInst, String reason) {
        fasUsageService.updateUsages(usageIds, wrWrkInst, reason);
    }

    @Override
    protected ICommonUsageWidget instantiateWidget() {
        return new FasUsageWidget(this);
    }
}
