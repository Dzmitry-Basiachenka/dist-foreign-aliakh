package com.copyright.rup.dist.foreign.vui.scenario.impl.nts;

import com.copyright.rup.dist.foreign.vui.scenario.api.nts.INtsExcludeRightsholderController;
import com.copyright.rup.dist.foreign.vui.scenario.api.nts.INtsExcludeRightsholderWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.CommonController;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link INtsExcludeRightsholderController}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 08/03/2020
 *
 * @author Anton Azarenka
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class NtsExcludeRightsholderController extends CommonController<INtsExcludeRightsholderWidget>
    implements INtsExcludeRightsholderController {

    private static final long serialVersionUID = 5961865593979822795L;

    @Override
    protected INtsExcludeRightsholderWidget instantiateWidget() {
        return new NtsExcludeRightsholderWidget();
    }
}
