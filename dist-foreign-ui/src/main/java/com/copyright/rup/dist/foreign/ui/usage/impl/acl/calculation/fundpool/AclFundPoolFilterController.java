package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.fundpool;

import com.copyright.rup.dist.foreign.domain.AclFundPool;
import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.service.api.ILicenseeClassService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclFundPoolService;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclFundPoolFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclFundPoolFilterWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ILicenseeClassService licenseeClassService;
    @Autowired
    private IAclFundPoolService aclFundPoolService;

    @Override
    protected IAclFundPoolFilterWidget instantiateWidget() {
        return new AclFundPoolFilterWidget(this);
    }

    @Override
    public List<AclFundPool> getFundPools() {
        return aclFundPoolService.getAll();
    }

    @Override
    public List<Integer> getPeriods() {
        return aclFundPoolService.getPeriods();
    }

    @Override
    public List<DetailLicenseeClass> getDetailLicenseeClasses() {
        return licenseeClassService.getDetailLicenseeClasses("ACL");
    }

    @Override
    public List<AggregateLicenseeClass> getAggregateLicenseeClasses() {
        return licenseeClassService.getAggregateLicenseeClasses("ACL");
    }
}
