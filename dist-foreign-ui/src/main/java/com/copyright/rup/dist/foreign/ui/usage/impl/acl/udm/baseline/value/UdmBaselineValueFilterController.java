package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.baseline.value;

import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.service.api.IPublicationTypeService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmBaselineValueService;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineValueFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineValueFilterWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementation of {@link IUdmBaselineValueFilterController}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/23/2021
 *
 * @author Anton Azarenka
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UdmBaselineValueFilterController extends CommonController<IUdmBaselineValueFilterWidget>
    implements IUdmBaselineValueFilterController {

    @Autowired
    private IUdmBaselineValueService udmBaselineValueService;
    @Autowired
    private IPublicationTypeService publicationTypeService;

    @Override
    protected IUdmBaselineValueFilterWidget instantiateWidget() {
        return new UdmBaselineValueFilterWidget(this);
    }

    @Override
    public List<Integer> getPeriods() {
        return udmBaselineValueService.getPeriods();
    }

    @Override
    public List<PublicationType> getPublicationTypes() {
        return publicationTypeService.getPublicationTypes(FdaConstants.ACL_PRODUCT_FAMILY);
    }
}
