package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.value;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.reporting.api.IStreamSourceHandler;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.api.Sort.Direction;
import com.copyright.rup.dist.foreign.domain.Currency;
import com.copyright.rup.dist.foreign.domain.ExchangeRate;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.UdmValueDto;
import com.copyright.rup.dist.foreign.domain.filter.UdmValueFilter;
import com.copyright.rup.dist.foreign.integration.rfex.api.IRfexIntegrationService;
import com.copyright.rup.dist.foreign.service.api.IPublicationTypeService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmBaselineService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmPriceTypeService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmProxyValueService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmReportService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmValueAuditService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmValueService;
import com.copyright.rup.dist.foreign.ui.audit.impl.UdmValueHistoryWindow;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueWidget;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.widget.api.CommonController;

import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.Window;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * Implementation of {@link IUdmValueController}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/01/2021
 *
 * @author Aliaksandr Liakh
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UdmValueController extends CommonController<IUdmValueWidget> implements IUdmValueController {

    @Autowired
    private IUdmValueFilterController udmValueFilterController;
    @Autowired
    private IUdmBaselineService baselineService;
    @Autowired
    private IUdmValueService valueService;
    @Autowired
    private IUdmValueAuditService udmValueAuditService;
    @Autowired
    private IUdmProxyValueService udmProxyValueService;
    @Autowired
    private IPublicationTypeService publicationTypeService;
    @Autowired
    private IUdmReportService udmReportService;
    @Autowired
    private IUdmPriceTypeService udmPriceTypeService;
    @Qualifier("df.integration.rfexIntegrationCacheService")
    @Autowired
    private IRfexIntegrationService rfexIntegrationService;
    @Autowired
    private IStreamSourceHandler streamSourceHandler;

    @Override
    public List<Integer> getBaselinePeriods() {
        return baselineService.getPeriods();
    }

    @Override
    public List<Integer> getPeriods() {
        return valueService.getPeriods();
    }

    @Override
    public int publishToBaseline(Integer period) {
        return valueService.publishToBaseline(period);
    }

    @Override
    public boolean isAllowedForPublishing(Integer period) {
        return valueService.isAllowedForPublishing(period);
    }

    @Override
    public int populatesValueBatch(Integer period) {
        return valueService.populateValueBatch(period);
    }

    @Override
    public int getBeansCount() {
        return valueService.getValueCount(getFilter());
    }

    @Override
    public List<UdmValueDto> loadBeans(int startIndex, int count, List<QuerySortOrder> sortOrders) {
        Sort sort = null;
        if (CollectionUtils.isNotEmpty(sortOrders)) {
            QuerySortOrder sortOrder = sortOrders.get(0);
            sort = new Sort(sortOrder.getSorted(), Direction.of(SortDirection.ASCENDING == sortOrder.getDirection()));
        }
        return valueService.getValueDtos(getFilter(), new Pageable(startIndex, count), sort);
    }

    @Override
    public IUdmValueFilterWidget initValuesFilterWidget() {
        IUdmValueFilterWidget widget = udmValueFilterController.initWidget();
        widget.addListener(FilterChangedEvent.class, this, IUdmValueController.ON_FILTER_CHANGED);
        return widget;
    }

    @Override
    public void onFilterChanged(FilterChangedEvent event) {
        getWidget().refresh();
    }

    @Override
    protected IUdmValueWidget instantiateWidget() {
        return new UdmValueWidget();
    }

    @Override
    public void assignValues(Set<UdmValueDto> udmValues) {
        valueService.assignValues(udmValues);
    }

    @Override
    public void unassignValues(Set<UdmValueDto> udmValues) {
        valueService.unassignValues(udmValues);
    }

    @Override
    public void updateValue(UdmValueDto udmValueDto, List<String> actionReasons) {
        valueService.updateValue(udmValueDto, actionReasons);
    }

    @Override
    public List<Currency> getAllCurrencies() {
        return valueService.getAllCurrencies();
    }

    @Override
    public List<PublicationType> getAllPublicationTypes() {
        return publicationTypeService.getPublicationTypes(FdaConstants.ACL_PRODUCT_FAMILY);
    }

    @Override
    public int getUdmRecordThreshold() {
        return valueService.getUdmRecordThreshold();
    }

    @Override
    public List<String> getAllPriceTypes() {
        return udmPriceTypeService.getAllPriceTypes();
    }

    @Override
    public List<String> getAllPriceAccessTypes() {
        return udmPriceTypeService.getAllPriceAccessTypes();
    }

    @Override
    public ExchangeRate getExchangeRate(String foreignCurrencyCode, LocalDate date) {
        return rfexIntegrationService.getExchangeRate(foreignCurrencyCode, date);
    }

    @Override
    public int calculateProxyValues(Integer period) {
        return udmProxyValueService.calculateProxyValues(period);
    }

    @Override
    public void showUdmValueHistory(String udmValueId, Window.CloseListener closeListener) {
        UdmValueHistoryWindow historyWindow =
            new UdmValueHistoryWindow(udmValueId, udmValueAuditService.getUdmValueAudit(udmValueId));
        historyWindow.addCloseListener(closeListener);
        Windows.showModalWindow(historyWindow);
    }

    @Override
    public IStreamSource getExportValuesStreamSource() {
        return streamSourceHandler.getCsvStreamSource(() -> "export_udm_value_", pos ->
            udmReportService.writeUdmValuesCsvReport(getFilter(), pos));
    }

    @Override
    public boolean isAllowedForRecalculating(Integer period) {
        return valueService.isAllowedForRecalculating(period);
    }

    private UdmValueFilter getFilter() {
        return udmValueFilterController.getWidget().getAppliedFilter();
    }
}
