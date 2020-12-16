package com.copyright.rup.dist.foreign.ui.audit.impl.sal;

import com.copyright.rup.dist.foreign.ui.audit.api.sal.ISalAuditFilterController;
import com.copyright.rup.dist.foreign.ui.audit.impl.CommonAuditFilterWidget;

/**
 * Implementation of SAL product family widget.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 12/16/2020
 *
 * @author Aliaksandr Liakh
 */
@SuppressWarnings("all") // TODO {aliakh} to remove when the class is implemented
public class SalAuditFilterWidget extends CommonAuditFilterWidget {

    private final ISalAuditFilterController controller;

    /**
     * Constructor.
     *
     * @param controller instance of {@link ISalAuditFilterController}
     */
    public SalAuditFilterWidget(ISalAuditFilterController controller) {
        this.controller = controller;
    }

    @Override
    public void initFields() {
        // TODO {aliakh} to init fields
    }

    @Override
    public void clearFilter() {
        refreshFilter();
        // TODO {aliakh} to clear fields
        applyFilter();
    }

    @Override
    public void trimFilterValues() {
        // TODO {aliakh} to trim filter values
        applyFilter();
    }
}
