package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.reporting.api.IStreamSourceHandler;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.api.Sort.Direction;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.foreign.domain.CompanyInformation;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.UdmActionReason;
import com.copyright.rup.dist.foreign.domain.UdmBatch;
import com.copyright.rup.dist.foreign.domain.UdmIneligibleReason;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.domain.UdmUsageDto;
import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;
import com.copyright.rup.dist.foreign.integration.telesales.api.ITelesalesService;
import com.copyright.rup.dist.foreign.service.api.ILicenseeClassService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmBatchService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmReportService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageService;
import com.copyright.rup.dist.foreign.service.impl.acl.UdmAnnualizedCopiesCalculator;
import com.copyright.rup.dist.foreign.service.impl.csv.CsvProcessorFactory;
import com.copyright.rup.dist.foreign.service.impl.csv.UdmCsvProcessor;
import com.copyright.rup.dist.foreign.ui.audit.impl.UsageHistoryWindow;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageWidget;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.widget.api.CommonController;

import com.google.common.io.Files;
import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.shared.data.sort.SortDirection;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * Implementation of {@link IUdmUsageController}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 04/26/2021
 *
 * @author Ihar Suvorau
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UdmUsageController extends CommonController<IUdmUsageWidget> implements IUdmUsageController {

    @Autowired
    private IUdmUsageService udmUsageService;
    @Autowired
    private IUdmBatchService udmBatchService;
    @Autowired
    private IUdmUsageAuditService udmUsageAuditService;
    @Autowired
    private ILicenseeClassService licenseeClassService;
    @Autowired
    private IUdmReportService udmReportService;
    @Autowired
    private CsvProcessorFactory csvProcessorFactory;
    @Autowired
    private IStreamSourceHandler streamSourceHandler;
    @Autowired
    private IUdmUsageFilterController udmUsageFilterController;
    @Autowired
    @Qualifier("df.integration.telesalesCacheService")
    private ITelesalesService telesalesService;
    @Autowired
    private UdmAnnualizedCopiesCalculator annualizedCopiesCalculator;

    @Override
    public int getBeansCount() {
        return udmUsageService.getUsagesCount(getFilter());
    }

    @Override
    public List<UdmUsageDto> loadBeans(int startIndex, int count, List<QuerySortOrder> sortOrders) {
        Sort sort = null;
        if (CollectionUtils.isNotEmpty(sortOrders)) {
            QuerySortOrder sortOrder = sortOrders.get(0);
            sort = new Sort(sortOrder.getSorted(), Direction.of(SortDirection.ASCENDING == sortOrder.getDirection()));
        }
        return udmUsageService.getUsageDtos(getFilter(), new Pageable(startIndex, count), sort);
    }

    @Override
    public IUdmUsageFilterWidget initUsagesFilterWidget() {
        IUdmUsageFilterWidget result = udmUsageFilterController.initWidget();
        result.addListener(FilterChangedEvent.class, this, IUdmUsageController.ON_FILTER_CHANGED);
        return result;
    }

    @Override
    public void onFilterChanged(FilterChangedEvent event) {
        getWidget().refresh();
    }

    @Override
    public IStreamSource getErrorResultStreamSource(String fileName, ProcessingResult processingResult) {
        return streamSourceHandler.getCsvStreamSource(
            () -> String.format("Error_for_%s", Files.getNameWithoutExtension(fileName)), null,
            processingResult::writeToFile);
    }

    @Override
    public UdmCsvProcessor getCsvProcessor() {
        return csvProcessorFactory.getUdmCsvProcessor();
    }

    @Override
    public int loadUdmBatch(UdmBatch udmBatch, List<UdmUsage> udmUsages) {
        udmBatchService.insertUdmBatch(udmBatch, udmUsages);
        udmUsageService.sendForMatching(udmUsages);
        udmUsageFilterController.getWidget().clearFilter();
        getWidget().clearSearch();
        return udmUsages.size();
    }

    @Override
    public boolean udmBatchExists(String name) {
        return udmBatchService.udmBatchExists(name);
    }

    @Override
    public void showUdmUsageHistory(String udmUsageId) {
        Windows.showModalWindow(new UsageHistoryWindow(udmUsageId, udmUsageAuditService.getUdmUsageAudit(udmUsageId)));
    }

    @Override
    public void assignUsages(Set<String> usageIds) {
        udmUsageService.assignUsages(usageIds);
    }

    @Override
    public void unassignUsages(Set<String> usageIds) {
        udmUsageService.unassignUsages(usageIds);
    }

    @Override
    public void updateUsage(UdmUsageDto udmUsageDto) {
        udmUsageService.updateUsage(udmUsageDto);
    }

    public List<UdmActionReason> getActionReasons() {
        return udmUsageService.getActionReasons();
    }

    @Override
    public List<UdmIneligibleReason> getIneligibleReasons() {
        return udmUsageService.getIneligibleReasons();
    }

    @Override
    public List<DetailLicenseeClass> getDetailLicenseeClasses() {
        return licenseeClassService.getDetailLicenseeClasses(FdaConstants.ACL_PRODUCT_FAMILY);
    }

    @Override
    public List<UdmBatch> getUdmBatches() {
        return udmBatchService.getUdmBatches();
    }

    @Override
    public void deleteUdmBatch(UdmBatch udmBatch) {
        udmBatchService.deleteUdmBatch(udmBatch);
        udmUsageFilterController.getWidget().clearFilter();
    }

    @Override
    public boolean isUdmBatchProcessingCompleted(String udmBatchId) {
        return udmBatchService.isUdmBatchProcessingCompleted(udmBatchId);
    }

    @Override
    public IStreamSource getExportUdmUsagesStreamSourceSpecialistManagerRoles() {
        return streamSourceHandler.getCsvStreamSource(() -> "export_udm_usage_",
            pos -> udmReportService.writeUdmUsageCsvReportSpecialistManagerRoles(getFilter(), pos));
    }

    @Override
    public IStreamSource getExportUdmUsagesStreamSourceResearcherRole() {
        return streamSourceHandler.getCsvStreamSource(() -> "export_udm_usage_",
            pos -> udmReportService.writeUdmUsageCsvReportResearcherRole(getFilter(), pos));
    }

    @Override
    public IStreamSource getExportUdmUsagesStreamSourceViewRole() {
        return streamSourceHandler.getCsvStreamSource(() -> "export_udm_usage_",
            pos -> udmReportService.writeUdmUsageCsvReportViewRole(getFilter(), pos));
    }

    @Override
    public CompanyInformation getCompanyInformation(Long companyId) {
        return telesalesService.getCompanyInformation(companyId);
    }

    @Override
    public BigDecimal calculateAnnualizedCopies(String reportedTypeOfUse, Integer quantity, Integer annualMultiplier,
                                                BigDecimal statisticalMultiplier) {
        return annualizedCopiesCalculator.calculate(reportedTypeOfUse, quantity, annualMultiplier,
            statisticalMultiplier);
    }

    @Override
    protected IUdmUsageWidget instantiateWidget() {
        return new UdmUsageWidget();
    }

    private UdmUsageFilter getFilter() {
        UdmUsageFilter udmUsageFilter = udmUsageFilterController.getWidget().getAppliedFilter();
        udmUsageFilter.setSearchValue(getWidget().getSearchValue());
        return udmUsageFilter;
    }
}
