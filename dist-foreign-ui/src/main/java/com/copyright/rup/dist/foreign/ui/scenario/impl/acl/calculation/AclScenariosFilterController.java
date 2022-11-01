package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenariosFilterController;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenariosFilterWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Implementation of {@link IAclScenariosFilterController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 10/31/2022
 *
 * @author Mikita Maistrenka
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AclScenariosFilterController extends CommonController<IAclScenariosFilterWidget>
    implements IAclScenariosFilterController {

    @Override
    protected IAclScenariosFilterWidget instantiateWidget() {
        return new AclScenariosFilterWidget(this);
    }

    @Override
    public List<Integer> getPeriods() {
        //TODO will be implemented with backend logic
        return Collections.emptyList();
    }
}
