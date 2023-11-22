package com.copyright.rup.dist.foreign.vui.usage.impl.fas;

import com.copyright.rup.dist.foreign.vui.usage.api.ICommonUsageWidget;
import com.copyright.rup.dist.foreign.vui.usage.api.fas.IFasUsageController;
import com.copyright.rup.dist.foreign.vui.usage.impl.CommonUsageController;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link IFasUsageController}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/16/19
 *
 * @author Darya Baraukova
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FasUsageController extends CommonUsageController implements IFasUsageController {

    @Override
    protected ICommonUsageWidget instantiateWidget() {
        return new FasUsageWidget();
    }
}
