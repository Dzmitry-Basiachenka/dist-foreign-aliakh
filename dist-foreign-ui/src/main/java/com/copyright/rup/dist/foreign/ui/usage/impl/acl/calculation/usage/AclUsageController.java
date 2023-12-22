package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.usage;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.reporting.api.IStreamSourceHandler;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.api.Sort.Direction;
import com.copyright.rup.dist.foreign.domain.AclUsageBatch;
import com.copyright.rup.dist.foreign.domain.AclUsageDto;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.filter.AclUsageFilter;
import com.copyright.rup.dist.foreign.service.api.ILicenseeClassService;
import com.copyright.rup.dist.foreign.service.api.IPublicationTypeService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclCalculationReportService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclUsageService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageService;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclUsageFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclUsageWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

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
 * Implementation of {@link IAclUsageController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/30/2022
 *
 * @author Dzmitry Basiachenka
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AclUsageController extends CommonController<IAclUsageWidget> implements IAclUsageController {

    private static final long serialVersionUID = 6676434173301243305L;

    @Autowired
    private IAclUsageFilterController aclUsageFilterController;
    @Autowired
    private IUdmUsageService udmUsageService;
    @Autowired
    private IAclUsageBatchService aclUsageBatchService;
    @Autowired
    private IAclUsageService aclUsageService;
    @Autowired
    private IAclCalculationReportService aclCalculationReportService;
    @Autowired
    private IStreamSourceHandler streamSourceHandler;
    @Autowired
    private IPublicationTypeService publicationTypeService;
    @Autowired
    private ILicenseeClassService licenseeClassService;
    @Autowired
    private IAclScenarioService aclScenarioService;

    @Override
    public int getBeansCount() {
        return aclUsageService.getCount(getFilter());
    }

    @Override
    public List<AclUsageDto> loadBeans(int startIndex, int count, List<QuerySortOrder> sortOrders) {
        Sort sort = null;
        if (CollectionUtils.isNotEmpty(sortOrders)) {
            var sortOrder = sortOrders.get(0);
            sort = new Sort(sortOrder.getSorted(), Direction.of(SortDirection.ASCENDING == sortOrder.getDirection()));
        }
        return aclUsageService.getDtos(getFilter(), new Pageable(startIndex, count), sort);
    }

    @Override
    public List<Integer> getAllPeriods() {
        return udmUsageService.getPeriods();
    }

    @Override
    public boolean isAclUsageBatchExist(String usageBatchName) {
        return aclUsageBatchService.isAclUsageBatchExist(usageBatchName);
    }

    @Override
    public int insertAclUsageBatch(AclUsageBatch usageBatch) {
        int usagesCount = aclUsageBatchService.insert(usageBatch);
        aclUsageFilterController.getWidget().clearFilter();
        return usagesCount;
    }

    @Override
    public IAclUsageFilterWidget initAclUsageFilterWidget() {
        IAclUsageFilterWidget widget = aclUsageFilterController.initWidget();
        widget.addListener(FilterChangedEvent.class, this, IAclUsageController.ON_FILTER_CHANGED);
        return widget;
    }

    @Override
    public void onFilterChanged(FilterChangedEvent event) {
        getWidget().refresh();
    }

    @Override
    public IStreamSource getExportAclUsagesStreamSource() {
        return streamSourceHandler.getCsvStreamSource(() -> "export_acl_usage_",
            pos -> aclCalculationReportService.writeAclUsageCsvReport(getFilter(), pos));
    }

    @Override
    public List<PublicationType> getPublicationTypes() {
        return publicationTypeService.getPublicationTypes(FdaConstants.ACL_PRODUCT_FAMILY);
    }

    @Override
    public List<DetailLicenseeClass> getDetailLicenseeClasses() {
        return licenseeClassService.getDetailLicenseeClasses(FdaConstants.ACL_PRODUCT_FAMILY);
    }

    @Override
    public void updateUsages(Set<AclUsageDto> aclUsageDtos) {
        aclUsageService.updateUsages(aclUsageDtos);
    }

    @Override
    public int getRecordThreshold() {
        return aclUsageService.getRecordThreshold();
    }

    @Override
    public List<AclUsageBatch> getAllAclUsageBatches() {
        return aclUsageBatchService.getAll();
    }

    @Override
    public int copyAclUsageBatch(String sourceUsageBatchId, AclUsageBatch aclUsageBatch) {
        int usagesCount = aclUsageBatchService.copyUsageBatch(sourceUsageBatchId, aclUsageBatch);
        aclUsageFilterController.getWidget().clearFilter();
        return usagesCount;
    }

    @Override
    public void deleteAclUsageBatch(AclUsageBatch usageBatch) {
        aclUsageBatchService.deleteAclUsageBatch(usageBatch);
        aclUsageFilterController.getWidget().clearFilter();
    }

    @Override
    public List<String> getScenarioNamesAssociatedWithUsageBatch(String usageBatchId) {
        return aclScenarioService.getScenarioNamesByUsageBatchId(usageBatchId);
    }

    @Override
    protected IAclUsageWidget instantiateWidget() {
        return new AclUsageWidget();
    }

    private AclUsageFilter getFilter() {
        return aclUsageFilterController.getWidget().getAppliedFilter();
    }
}
