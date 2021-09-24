package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.value;

import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueFilterWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link IUdmValueFilterController}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/01/2021
 *
 * @author Aliaksandr Liakh
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UdmValueFilterController extends CommonController<IUdmValueFilterWidget>
    implements IUdmValueFilterController {

    @Override
    protected IUdmValueFilterWidget instantiateWidget() {
        return new UdmValueFilterWidget(this);
    }

    @Override
    public List<String> getAssignees() {
        return new ArrayList<>(); // TODO implement udmValueService.getAssignees();
    }

    @Override
    public List<String> getLastValuePeriods() {
        return new ArrayList<>(); // TODO implement udmValueService.getLastValuePeriods();
    }

    @Override
    public List<Integer> getPeriods() {
        //todo {aazarenka} will implement later
        return new ArrayList<>();
    }
}
