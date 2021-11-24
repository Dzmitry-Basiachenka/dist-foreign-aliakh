package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm;

import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineValueController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueController;
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
    @Autowired
    private IUdmValueController udmValueController;
    @Autowired
    private IUdmBaselineController udmBaselineController;
    @Autowired
    private IUdmBaselineValueController udmBaselineValueController;

    @Override
    public IUdmUsageController getUdmUsageController() {
        return udmUsageController;
    }

    @Override
    public IUdmValueController getUdmValueController() {
        return udmValueController;
    }

    @Override
    public IUdmBaselineController getUdmBaselineController() {
        return udmBaselineController;
    }

    @Override
    public IUdmBaselineValueController getUdmBaselineValueController() {
        return udmBaselineValueController;
    }

    @Override
    protected IUdmWidget instantiateWidget() {
        return new UdmWidget();
    }
}
