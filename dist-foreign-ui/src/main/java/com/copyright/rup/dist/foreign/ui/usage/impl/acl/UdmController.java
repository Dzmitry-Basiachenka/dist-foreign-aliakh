package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link IUdmController}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 08/27/2021
 *
 * @author Aliaksandr Liakh
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UdmController extends CommonController<IUdmWidget> implements IUdmController {

    @Autowired
    private IUdmUsageController udmUsageController;

    @Override
    public IUdmUsageController getUdmUsageController() {
        return udmUsageController;
    }

    @Override
    protected IUdmWidget instantiateWidget() {
        return new UdmWidget();
    }
}
