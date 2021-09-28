package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.value;

import com.copyright.rup.dist.foreign.domain.UdmValueDto;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmBaselineService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmValueService;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;
import com.vaadin.data.provider.QuerySortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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

    @Override
    public List<Integer> getBaselinePeriods() {
        return baselineService.getPeriods();
    }

    @Override
    public int populatesValueBatch(Integer period) {
        //TODO: use service logic
        return 0;
    }

    @Override
    public int getBeansCount() {
        return 0; //TODO add implementation
    }

    @Override
    public List<UdmValueDto> loadBeans(int startIndex, int count, List<QuerySortOrder> sortOrders) {
        return new ArrayList<>(); //TODO add implementation
    }

    @Override
    public IUdmValueFilterWidget initValuesFilterWidget() {
        return udmValueFilterController.initWidget();
    }

    @Override
    protected IUdmValueWidget instantiateWidget() {
        return new UdmValueWidget();
    }

    @Override
    public void assignValues(Set<String> valueIds) {
        valueService.assignValues(valueIds);
    }

    @Override
    public void unassignValues(Set<String> valueIds) {
        valueService.unassignValues(valueIds);
    }
}
