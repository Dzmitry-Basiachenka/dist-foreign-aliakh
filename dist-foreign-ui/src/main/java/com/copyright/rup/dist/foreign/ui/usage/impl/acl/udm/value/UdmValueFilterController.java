package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.value;

import com.copyright.rup.dist.foreign.domain.Currency;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.service.api.IPublicationTypeService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmValueService;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueFilterWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

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

    @Autowired
    private IUdmValueService udmValueService;
    @Autowired
    private IPublicationTypeService publicationTypeService;

    @Override
    protected IUdmValueFilterWidget instantiateWidget() {
        return new UdmValueFilterWidget(this);
    }

    @Override
    public List<String> getAssignees() {
        return udmValueService.getAssignees();
    }

    @Override
    public List<String> getLastValuePeriods() {
        return udmValueService.getLastValuePeriods();
    }

    @Override
    public List<Integer> getPeriods() {
        return udmValueService.getPeriods();
    }

    @Override
    public List<PublicationType> getPublicationTypes() {
        return publicationTypeService.getPublicationTypes(FdaConstants.ACL_PRODUCT_FAMILY);
    }

    @Override
    public List<Currency> getAllCurrencies() {
        return udmValueService.getAllCurrencies();
    }
}
