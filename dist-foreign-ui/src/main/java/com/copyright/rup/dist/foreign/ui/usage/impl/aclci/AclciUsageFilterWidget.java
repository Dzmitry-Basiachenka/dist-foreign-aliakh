package com.copyright.rup.dist.foreign.ui.usage.impl.aclci;

import com.copyright.rup.dist.foreign.ui.usage.api.aclci.IAclciUsageFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.CommonUsageFilterWidget;
import com.vaadin.ui.VerticalLayout;

/**
 * Implementation of {@link IAclciUsageFilterWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 11/18/2022
 *
 * @author Aliaksanr Liakh
 */
public class AclciUsageFilterWidget extends CommonUsageFilterWidget implements IAclciUsageFilterWidget {

    @Override
    protected void refreshFilterValues() {
        //TODO: implement
    }

    @Override
    protected void clearFilterValues() {
        //TODO: implement
    }

    @Override
    protected VerticalLayout initFiltersLayout() {
        return new VerticalLayout(); //TODO: implement
    }
}
