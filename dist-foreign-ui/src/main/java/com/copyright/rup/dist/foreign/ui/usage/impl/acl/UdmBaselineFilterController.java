package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.service.api.ILicenseeClassService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmTypeOfUseService;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineFilterWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementation of {@link IUdmBaselineFilterController}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/01/2021
 *
 * @author Uladzislau Shalamitski
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UdmBaselineFilterController extends CommonController<IUdmBaselineFilterWidget>
    implements IUdmBaselineFilterController {

    @Autowired
    private IUdmTypeOfUseService udmTypeOfUseService;
    @Autowired
    private ILicenseeClassService licenseeClassService;

    @Override
    public List<String> getTypeOfUses() {
        return udmTypeOfUseService.getAllUdmTous();
    }

    @Override
    public List<DetailLicenseeClass> getDetailLicenseeClasses() {
        return licenseeClassService.getDetailLicenseeClasses(FdaConstants.ACL_PRODUCT_FAMILY);
    }

    @Override
    public List<AggregateLicenseeClass> getAggregateLicenseeClasses() {
        return licenseeClassService.getAggregateLicenseeClasses(FdaConstants.ACL_PRODUCT_FAMILY);
    }

    @Override
    protected IUdmBaselineFilterWidget instantiateWidget() {
        return new UdmBaselineFilterWidget(this);
    }
}
