package com.copyright.rup.dist.foreign.ui.audit.impl.sal;

import com.copyright.rup.dist.foreign.ui.audit.api.sal.ISalAuditController;
import com.copyright.rup.dist.foreign.ui.audit.api.sal.ISalAuditWidget;
import com.copyright.rup.dist.foreign.ui.audit.impl.CommonAuditWidget;

/**
 * Implementation of {@link ISalAuditWidget}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 12/16/2020
 *
 * @author Aliaksandr Liakh
 */
@SuppressWarnings("all") // TODO {aliakh} remove when the widget is implemented
public class SalAuditWidget extends CommonAuditWidget implements ISalAuditWidget {

    private final ISalAuditController controller;

    /**
     * Constructor.
     *
     * @param controller instance of {@link ISalAuditController}
     */
    SalAuditWidget(ISalAuditController controller) {
        this.controller = controller;
    }

    @Override
    protected void addColumns() {
        // TODO {aliakh} to add grid columns
    }

    @Override
    public String initSearchMessage() {
        return "prompt.audit_search_aacl_sal";
    }
}
