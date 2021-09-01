package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import com.copyright.rup.dist.foreign.domain.UdmBaselineDto;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import com.vaadin.data.provider.QuerySortOrder;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link IUdmBaselineController}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/01/2021
 *
 * @author Dzmitry Basiachenka
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UdmBaselineController extends CommonController<IUdmBaselineWidget> implements IUdmBaselineController {

    @Override
    public int getBeansCount() {
        return 0; //TODO add implementation
    }

    @Override
    public List<UdmBaselineDto> loadBeans(int startIndex, int count, List<QuerySortOrder> sortOrders) {
        return new ArrayList<>(); //TODO add implementation
    }

    @Override
    protected IUdmBaselineWidget instantiateWidget() {
        return new UdmBaselineWidget();
    }
}
