package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.usage;

import com.copyright.rup.dist.foreign.domain.AclUsageBatch;
import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.service.api.ILicenseeClassService;
import com.copyright.rup.dist.foreign.service.api.IPublicationTypeService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclUsageService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmTypeOfUseService;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclUsageFilterWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementation of {@link IAclUsageFilterController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/31/2022
 *
 * @author Dzmitry Basiachenka
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AclUsageFilterController extends CommonController<IAclUsageFilterWidget>
    implements IAclUsageFilterController {

    @Autowired
    private IAclUsageBatchService aclUsageBatchService;
    @Autowired
    private IAclUsageService aclUsageService;
    @Autowired
    private ILicenseeClassService licenseeClassService;
    @Autowired
    private IPublicationTypeService publicationTypeService;
    @Autowired
    private IUdmTypeOfUseService udmTypeOfUseService;

    @Override
    public List<AclUsageBatch> getAllAclUsageBatches() {
        return aclUsageBatchService.getAll();
    }

    @Override
    public List<Integer> getPeriods() {
        return aclUsageService.getPeriods();
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
    public List<PublicationType> getPublicationTypes() {
        return publicationTypeService.getPublicationTypes(FdaConstants.ACL_PRODUCT_FAMILY);
    }

    @Override
    public List<String> getReportedTypeOfUses() {
        return udmTypeOfUseService.getAllUdmTous();
    }

    @Override
    protected IAclUsageFilterWidget instantiateWidget() {
        return new AclUsageFilterWidget(this);
    }
}
