package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.proxy;

import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.service.api.IPublicationTypeService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmProxyValueService;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmProxyValueFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmProxyValueFilterWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of {@link IUdmProxyValueFilterController}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/23/2021
 *
 * @author Uladzislau Shalamitski
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UdmProxyValueFilterController extends CommonController<IUdmProxyValueFilterWidget>
    implements IUdmProxyValueFilterController {

    @Autowired
    private IPublicationTypeService publicationTypeService;
    @Autowired
    private IUdmProxyValueService udmProxyValueService;

    @Override
    public IUdmProxyValueFilterWidget instantiateWidget() {
        return new UdmProxyValueFilterWidget();
    }

    @Override
    public List<Integer> getPeriods() {
        return udmProxyValueService.findPeriods();
    }

    @Override
    public List<String> getPublicationTypeCodes() {
        return publicationTypeService.getPublicationTypes(FdaConstants.ACL_PRODUCT_FAMILY)
            .stream()
            .map(PublicationType::getName)
            .collect(Collectors.toList());
    }
}
