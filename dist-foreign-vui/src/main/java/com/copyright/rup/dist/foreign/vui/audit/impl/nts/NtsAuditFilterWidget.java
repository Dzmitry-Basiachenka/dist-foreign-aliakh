package com.copyright.rup.dist.foreign.vui.audit.impl.nts;

import com.copyright.rup.dist.foreign.vui.audit.api.nts.INtsAuditFilterController;
import com.copyright.rup.dist.foreign.vui.audit.impl.CommonAuditAppliedFilterWidget;
import com.copyright.rup.dist.foreign.vui.audit.impl.CommonAuditFilterWidget;

/**
 * Implementation of NTS product family widget.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 03/16/2020
 *
 * @author Anton Azarenka
 */
public class NtsAuditFilterWidget extends CommonAuditFilterWidget {

    private static final long serialVersionUID = 2374703703458839058L;

    private final INtsAuditFilterController controller;

    /**
     * Constructor.
     *
     * @param controller instance of {@link INtsAuditFilterController}
     */
    public NtsAuditFilterWidget(INtsAuditFilterController controller) {
        this.controller = controller;
    }

    @Override
    public void initFields() {
    }

    @Override
    public void clearFilter() {
    }

    @Override
    public void trimFilterValues() {
    }

    @Override
    protected CommonAuditAppliedFilterWidget initAppliedFilterWidget() {
        return new NtsAuditAppliedFilterWidget(controller);
    }
}
