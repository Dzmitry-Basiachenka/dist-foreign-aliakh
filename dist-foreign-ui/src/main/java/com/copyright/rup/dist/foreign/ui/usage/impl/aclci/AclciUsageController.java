package com.copyright.rup.dist.foreign.ui.usage.impl.aclci;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.reporting.api.IStreamSourceHandler;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.api.Sort.Direction;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.integration.telesales.api.ITelesalesService;
import com.copyright.rup.dist.foreign.service.api.IFundPoolService;
import com.copyright.rup.dist.foreign.service.api.aclci.IAclciUsageService;
import com.copyright.rup.dist.foreign.service.impl.csv.AclciUsageCsvProcessor;
import com.copyright.rup.dist.foreign.service.impl.csv.CsvProcessorFactory;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.aclci.IAclciUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.aclci.IAclciUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.impl.CommonUsageController;

import com.google.common.io.Files;
import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.shared.data.sort.SortDirection;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Implementation of {@link IAclciUsageController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 11/21/2022
 *
 * @author Aliaksanr Liakh
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AclciUsageController extends CommonUsageController implements IAclciUsageController {

    @Autowired
    private IAclciUsageFilterController aclciUsageFilterController;
    @Autowired
    private CsvProcessorFactory csvProcessorFactory;
    @Autowired
    private IStreamSourceHandler streamSourceHandler;
    @Autowired
    private IAclciUsageService aclciUsageService;
    @Autowired
    @Qualifier("df.integration.telesalesCacheService")
    private ITelesalesService telesalesService;
    @Autowired
    private IFundPoolService fundPoolService;

    @Override
    public int getBeansCount() {
        return aclciUsageService.getUsagesCount(getUsageFilterController().getWidget().getAppliedFilter());
    }

    @Override
    public List<UsageDto> loadBeans(int startIndex, int count, List<QuerySortOrder> sortOrders) {
        Sort sort = null;
        if (CollectionUtils.isNotEmpty(sortOrders)) {
            QuerySortOrder sortOrder = sortOrders.get(0);
            sort = new Sort(sortOrder.getSorted(), Direction.of(SortDirection.ASCENDING == sortOrder.getDirection()));
        }
        return aclciUsageService.getUsageDtos(getUsageFilterController().getWidget().getAppliedFilter(),
            new Pageable(startIndex, count), sort);
    }

    @Override
    public ICommonUsageFilterController getUsageFilterController() {
        return aclciUsageFilterController;
    }

    @Override
    public boolean isValidFilteredUsageStatus(UsageStatusEnum status) {
        return false; //TODO: implement
    }

    @Override
    public IStreamSource getExportUsagesStreamSource() {
        return streamSourceHandler.getCsvStreamSource(() -> "export_usage_",
            pos -> getReportService().writeAaclUsageCsvReport(//TODO: implement writeAclciUsageCsvReport
                getUsageFilterController().getWidget().getAppliedFilter(), pos));
    }

    @Override
    public boolean isBatchProcessingCompleted(String batchId) {
        return false; //TODO: implement
    }

    @Override
    public void deleteUsageBatch(UsageBatch usageBatch) {
        //TODO: implement
    }

    @Override
    public AclciUsageCsvProcessor getAclciUsageCsvProcessor() {
        return csvProcessorFactory.getAclciUsageCsvProcessor();
    }

    @Override
    public void loadAclciUsageBatch(UsageBatch usageBatch, List<Usage> usages) {
        List<String> insertedUsageIds = getUsageBatchService().insertAclciBatch(usageBatch, usages);
        aclciUsageService.sendForMatching(insertedUsageIds, usageBatch.getName());
        getUsageFilterController().getWidget().clearFilter();
    }

    @Override
    public IStreamSource getErrorResultStreamSource(String fileName, ProcessingResult<Usage> processingResult) {
        return streamSourceHandler.getCsvStreamSource(
            () -> String.format("Error_for_%s", Files.getNameWithoutExtension(fileName)), null,
            processingResult::writeToFile);
    }

    @Override
    public String getLicenseeName(Long licenseeAccountNumber) {
        return telesalesService.getLicenseeName(licenseeAccountNumber);
    }

    @Override
    public boolean aclciFundPoolExists(String name) {
        return fundPoolService.fundPoolExists(FdaConstants.ACLCI_PRODUCT_FAMILY, name);
    }

    @Override
    public FundPool calculateAclciFundPoolAmounts(FundPool fundPool) {
        return fundPoolService.calculateAclciFundPoolAmounts(fundPool);
    }

    @Override
    public void createAclciFundPool(FundPool fundPool) {
        fundPoolService.createAclciFundPool(fundPool);
    }

    @Override
    public boolean isValidStatusFilterApplied() {
        UsageStatusEnum appliedStatus = getUsageFilterController().getWidget().getAppliedFilter().getUsageStatus();
        return Objects.nonNull(appliedStatus)
            && (UsageStatusEnum.RH_NOT_FOUND == appliedStatus || UsageStatusEnum.WORK_NOT_GRANTED == appliedStatus);
    }

    @Override
    public List<UsageDto> getUsageDtosToUpdate() {
        return aclciUsageService.getUsageDtos(getUsageFilterController().getWidget().getAppliedFilter(), null, null);
    }

    @Override
    public void updateToEligibleByIds(Set<String> usageIds, Long rhAccountNumber, Long wrWrkInst, String reason) {
        aclciUsageService.updateToEligibleByIds(usageIds, rhAccountNumber, wrWrkInst, reason);
    }

    @Override
    protected ICommonUsageWidget instantiateWidget() {
        return new AclciUsageWidget(this);
    }
}
