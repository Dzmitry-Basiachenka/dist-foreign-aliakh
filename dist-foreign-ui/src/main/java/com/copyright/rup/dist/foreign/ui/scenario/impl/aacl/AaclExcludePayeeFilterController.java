package com.copyright.rup.dist.foreign.ui.scenario.impl.aacl;

import com.copyright.rup.dist.foreign.ui.scenario.api.aacl.IAaclExcludePayeeFilterController;
import com.copyright.rup.dist.foreign.ui.scenario.api.aacl.IAaclExcludePayeeFilterWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Controller for AACL exclude payees filter.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 11/05/2020
 *
 * @author Ihar Suvorau
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AaclExcludePayeeFilterController extends CommonController<IAaclExcludePayeeFilterWidget>
    implements IAaclExcludePayeeFilterController {

    private static final long serialVersionUID = -7280002847103913180L;

    @Override
    protected IAaclExcludePayeeFilterWidget instantiateWidget() {
        return new AaclExcludePayeeFilterWidget();
    }
}
