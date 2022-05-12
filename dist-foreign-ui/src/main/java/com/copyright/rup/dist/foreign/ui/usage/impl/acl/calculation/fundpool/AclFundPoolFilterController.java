package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.fundpool;

import com.copyright.rup.dist.foreign.domain.AclFundPool;
import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclFundPoolFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclFundPoolFilterWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementation of {@link IAclFundPoolFilterController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/17/2022
 *
 * @author Anton Azarenka
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AclFundPoolFilterController extends CommonController<IAclFundPoolFilterWidget>
    implements IAclFundPoolFilterController {

    @Override
    protected IAclFundPoolFilterWidget instantiateWidget() {
        return new AclFundPoolFilterWidget(this);
    }

    @Override
    public List<AclFundPool> getFundPoolNames() {
        //TODO will be implemented later
        return null;
    }

    @Override
    public List<Integer> getPeriods() {
        //TODO will be implemented later
        return null;
    }

    @Override
    public List<DetailLicenseeClass> getDetailLicenseeClasses() {
        //TODO will be implemented later
        return null;
    }

    @Override
    public List<AggregateLicenseeClass> getAggregateLicenseeClasses() {
        //TODO will be implemented later
        return null;
    }
}
