package com.copyright.rup.dist.foreign.ui.usage.impl.aacl;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.reporting.api.IStreamSourceHandler;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.api.Sort.Direction;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.foreign.domain.AaclClassifiedUsage;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.FundPoolDetail;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IFundPoolService;
import com.copyright.rup.dist.foreign.service.api.IResearchService;
import com.copyright.rup.dist.foreign.service.api.aacl.IAaclFundPoolService;
import com.copyright.rup.dist.foreign.service.api.aacl.IAaclUsageService;
import com.copyright.rup.dist.foreign.service.impl.csv.AaclFundPoolCsvProcessor;
import com.copyright.rup.dist.foreign.service.impl.csv.AaclUsageCsvProcessor;
import com.copyright.rup.dist.foreign.service.impl.csv.ClassifiedUsageCsvProcessor;
import com.copyright.rup.dist.foreign.service.impl.csv.CsvProcessorFactory;
import com.copyright.rup.dist.foreign.ui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.aacl.IAaclUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.aacl.IAaclUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.impl.CommonUsageController;

import com.google.common.io.Files;
import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.shared.data.sort.SortDirection;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * Implementation of {@link IAaclUsageController}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/23/2019
 *
 * @author Aliaksandr Liakh
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AaclUsageController extends CommonUsageController implements IAaclUsageController {

    @Autowired
    private IAaclUsageFilterController aaclUsageFilterController;
    @Autowired
    private CsvProcessorFactory csvProcessorFactory;
    @Autowired
    private IStreamSourceHandler streamSourceHandler;
    @Autowired
    private IResearchService researchService;
    @Autowired
    private IAaclUsageService aaclUsageService;
    @Autowired
    private IAaclFundPoolService aaclFundPoolService;
    @Autowired
    private IFundPoolService fundPoolService;

    @Override
    public ICommonUsageFilterController getUsageFilterController() {
        return aaclUsageFilterController;
    }

    @Override
    public int getBeansCount() {
        return aaclUsageService.getUsagesCount(getUsageFilterController().getWidget().getAppliedFilter());
    }

    @Override
    public List<UsageDto> loadBeans(int startIndex, int count, List<QuerySortOrder> sortOrders) {
        Sort sort = null;
        if (CollectionUtils.isNotEmpty(sortOrders)) {
            QuerySortOrder sortOrder = sortOrders.get(0);
            sort = new Sort(sortOrder.getSorted(), Direction.of(SortDirection.ASCENDING == sortOrder.getDirection()));
        }
        return aaclUsageService.getUsageDtos(getUsageFilterController().getWidget().getAppliedFilter(),
            new Pageable(startIndex, count), sort);
    }

    @Override
    public AaclUsageCsvProcessor getCsvProcessor() {
        return csvProcessorFactory.getAaclUsageCsvProcessor();
    }

    @Override
    public ClassifiedUsageCsvProcessor getClassifiedUsageCsvProcessor() {
        return csvProcessorFactory.getClassifiedUsageCsvProcessor();
    }

    @Override
    public int loadUsageBatch(UsageBatch usageBatch, Collection<Usage> usages) {
        int result = getUsageBatchService().insertAaclBatch(usageBatch, usages);
        getUsageBatchService().sendForMatching(usages);
        aaclUsageFilterController.getWidget().clearFilter();
        return result;
    }

    @Override
    public List<FundPool> getFundPools() {
        return aaclFundPoolService.getFundPools();
    }

    @Override
    public List<FundPoolDetail> getFundPoolDetails(String fundPoolId) {
        return aaclFundPoolService.getDetailsByFundPoolId(fundPoolId);
    }

    @Override
    public void deleteFundPoolById(String fundPoolId) {
        aaclFundPoolService.deleteFundPoolById(fundPoolId);
    }

    @Override
    public boolean isValidFilteredUsageStatus(UsageStatusEnum status) {
        return aaclUsageService
            .isValidFilteredUsageStatus(getUsageFilterController().getWidget().getAppliedFilter(), status);
    }

    @Override
    public IStreamSource getSendForClassificationUsagesStreamSource() {
        return new ByteArrayStreamSource("send_for_classification_",
            pipedStream -> researchService.sendForClassification(
                getUsageFilterController().getWidget().getAppliedFilter(), pipedStream));
    }

    @Override
    public IStreamSource getExportUsagesStreamSource() {
        return streamSourceHandler.getCsvStreamSource(() -> "export_usage_",
            pos -> getReportService().writeAaclUsageCsvReport(
                getUsageFilterController().getWidget().getAppliedFilter(), pos));
    }

    @Override
    public int loadClassifiedUsages(List<AaclClassifiedUsage> classifiedUsages) {
        int updatedCount = aaclUsageService.updateClassifiedUsages(classifiedUsages);
        getUsageFilterController().getWidget().clearFilter();
        return updatedCount;
    }

    @Override
    public IStreamSource getErrorResultStreamSource(String fileName, ProcessingResult processingResult) {
        return streamSourceHandler.getCsvStreamSource(
            () -> String.format("Error_for_%s", Files.getNameWithoutExtension(fileName)), null,
            processingResult::writeToFile);
    }

    @Override
    public boolean fundPoolExists(String name) {
        return fundPoolService.fundPoolExists(FdaConstants.AACL_PRODUCT_FAMILY, name);
    }

    @Override
    public AaclFundPoolCsvProcessor getAaclFundPoolCsvProcessor() {
        return csvProcessorFactory.getAaclFundPoolCsvProcessor();
    }

    @Override
    public int insertFundPool(FundPool fundPool, List<FundPoolDetail> details) {
        return aaclFundPoolService.insertFundPool(fundPool, details);
    }

    @Override
    protected ICommonUsageWidget instantiateWidget() {
        return new AaclUsageWidget(this);
    }
}
