package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.proxy;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.reporting.api.IStreamSourceHandler;
import com.copyright.rup.dist.foreign.domain.UdmProxyValueDto;
import com.copyright.rup.dist.foreign.domain.filter.UdmProxyValueFilter;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmProxyValueService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmReportService;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmProxyValueController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmProxyValueFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmProxyValueFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmProxyValueWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementation of {@link IUdmProxyValueController}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/23/2021
 *
 * @author Uladzislau Shalamitski
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UdmProxyValueController extends CommonController<IUdmProxyValueWidget>
    implements IUdmProxyValueController {

    private static final long serialVersionUID = -2957759946773313712L;

    @Autowired
    private IUdmProxyValueFilterController udmProxyValueFilterController;
    @Autowired
    private IUdmProxyValueService udmProxyValueService;
    @Autowired
    private IStreamSourceHandler streamSourceHandler;
    @Autowired
    private IUdmReportService udmReportService;

    @Override
    public List<UdmProxyValueDto> getProxyValues() {
        return udmProxyValueService.getDtosByFilter(getFilter());
    }

    @Override
    public IUdmProxyValueFilterWidget initProxyValueFilterWidget() {
        IUdmProxyValueFilterWidget widget = udmProxyValueFilterController.initWidget();
        widget.addListener(FilterChangedEvent.class, this, IUdmProxyValueController.ON_FILTER_CHANGED);
        return widget;
    }

    @Override
    public IStreamSource getExportUdmProxyValuesStreamSource() {
        return streamSourceHandler.getCsvStreamSource(() -> "export_udm_proxy_value_",
            pos -> udmReportService.writeUdmProxyValueCsvReport(getFilter(), pos));
    }

    @Override
    public void onFilterChanged(FilterChangedEvent event) {
        getWidget().refresh();
    }

    @Override
    public IUdmProxyValueWidget instantiateWidget() {
        return new UdmProxyValueWidget();
    }

    private UdmProxyValueFilter getFilter() {
        return udmProxyValueFilterController.getWidget().getAppliedFilter();
    }
}
