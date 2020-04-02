package com.copyright.rup.dist.foreign.ui.scenario.impl.aacl;

import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.ui.scenario.api.aacl.IAaclDrillDownByRightsholderController;
import com.copyright.rup.dist.foreign.ui.scenario.api.aacl.IAaclDrillDownByRightsholderWidget;
import com.copyright.rup.dist.foreign.ui.scenario.impl.CommonDrillDownByRightsholderController;

import com.vaadin.data.provider.QuerySortOrder;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link IAaclDrillDownByRightsholderController}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 04/01/20
 *
 * @author Uladzislau Shalamitski
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AaclDrillDownByRightsholderController extends CommonDrillDownByRightsholderController
    implements IAaclDrillDownByRightsholderController {

    @Override
    public int getSize() {
        //TODO use service logic here
        return 0;
    }

    @Override
    public List<UsageDto> loadBeans(int startIndex, int count, List<QuerySortOrder> sortOrders) {
        //TODO use service logic here
        return new ArrayList<>();
    }

    @Override
    protected IAaclDrillDownByRightsholderWidget instantiateWidget() {
        return new AaclDrillDownByRightsholderWidget();
    }
}
