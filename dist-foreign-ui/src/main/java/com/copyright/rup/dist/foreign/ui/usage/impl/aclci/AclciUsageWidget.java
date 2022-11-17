package com.copyright.rup.dist.foreign.ui.usage.impl.aclci;

import com.copyright.rup.dist.foreign.ui.usage.api.aclci.IAclciUsageWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.CommonUsageWidget;
import com.copyright.rup.vaadin.widget.api.IMediator;
import com.vaadin.ui.HorizontalLayout;

/**
 * Implementation of {@link IAclciUsageWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 11/21/2022
 *
 * @author Aliaksanr Liakh
 */
public class AclciUsageWidget extends CommonUsageWidget implements IAclciUsageWidget {

    @Override
    public IMediator initMediator() {
        return new AclciUsageMediator();
    }

    @Override
    protected void addGridColumns() {
        //TODO: implement
    }

    @Override
    protected HorizontalLayout initButtonsLayout() {
        return new HorizontalLayout(); //TODO: implement
    }

    @Override
    protected String getProductFamilySpecificScenarioValidationMessage() {
        return null; //TODO: implement
    }
}
