package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.baseline.value;

import com.copyright.rup.dist.foreign.domain.UdmValueBaselineDto;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineValueController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineValueFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineValueFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineValueWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import com.vaadin.data.provider.QuerySortOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Implementation of {@link IUdmBaselineValueController}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/23/2021
 *
 * @author Anton Azarenka
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UdmBaselineValueController extends CommonController<IUdmBaselineValueWidget> implements
    IUdmBaselineValueController {

    @Autowired
    private IUdmBaselineValueFilterController udmBaselineValueFilterController;

    @Override
    public void onFilterChanged(FilterChangedEvent event) {
        //TODO will implement later
    }

    @Override
    public List<UdmValueBaselineDto> loadBeans(int startIndex, int count, List<QuerySortOrder> sortOrders) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public int getBeansCount() {
        return 0;
    }

    @Override
    public IUdmBaselineValueFilterWidget initBaselineValuesFilterWidget() {
        IUdmBaselineValueFilterWidget widget = udmBaselineValueFilterController.initWidget();
        widget.addListener(FilterChangedEvent.class, this, IUdmBaselineValueController.ON_FILTER_CHANGED);
        return widget;
    }

    @Override
    protected IUdmBaselineValueWidget instantiateWidget() {
        return new UdmBaselineValueWidget();
    }
}
