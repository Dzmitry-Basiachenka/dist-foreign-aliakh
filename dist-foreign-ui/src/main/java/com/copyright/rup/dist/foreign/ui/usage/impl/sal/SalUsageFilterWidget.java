package com.copyright.rup.dist.foreign.ui.usage.impl.sal;

import com.copyright.rup.dist.foreign.ui.usage.api.aacl.IAaclUsageFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.CommonUsageFilterWidget;

import com.vaadin.ui.VerticalLayout;

/**
 * Widget for filtering SAL usages.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 07/28/2020
 *
 * @author Uladzislau Shalamitski
 */
public class SalUsageFilterWidget extends CommonUsageFilterWidget implements IAaclUsageFilterWidget {

    @Override
    protected void refreshFilterValues() {
        //TODO: implement refresh of filter values
    }

    @Override
    protected void clearFilterValues() {
        //TODO: implement clear of filter values
    }

    @Override
    protected VerticalLayout initFiltersLayout() {
        //TODO: implement filters
        VerticalLayout verticalLayout = new VerticalLayout(buildFiltersHeaderLabel());
        verticalLayout.setMargin(false);
        return verticalLayout;
    }
}
