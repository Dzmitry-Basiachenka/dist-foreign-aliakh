package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.proxy;

import com.copyright.rup.dist.foreign.domain.UdmProxyValueDto;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmProxyValueService;
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

    @Autowired
    private IUdmProxyValueFilterController udmProxyValueFilterController;
    @Autowired
    private IUdmProxyValueService udmProxyValueService;

    @Override
    public List<UdmProxyValueDto> getProxyValues() {
        return udmProxyValueService.getDtosByFilter(udmProxyValueFilterController.getWidget().getAppliedFilter());
    }

    @Override
    public IUdmProxyValueFilterWidget initProxyValueFilterWidget() {
        IUdmProxyValueFilterWidget result = udmProxyValueFilterController.initWidget();
        result.addListener(FilterChangedEvent.class, this, IUdmProxyValueController.ON_FILTER_CHANGED);
        return result;
    }

    @Override
    public void onFilterChanged(FilterChangedEvent event) {
        getWidget().refresh();
    }

    @Override
    public IUdmProxyValueWidget instantiateWidget() {
        return new UdmProxyValueWidget();
    }
}
