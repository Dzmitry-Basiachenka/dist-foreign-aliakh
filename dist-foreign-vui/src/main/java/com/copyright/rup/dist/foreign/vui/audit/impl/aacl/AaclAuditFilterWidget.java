package com.copyright.rup.dist.foreign.vui.audit.impl.aacl;

import com.copyright.rup.dist.foreign.vui.audit.api.aacl.IAaclAuditFilterController;
import com.copyright.rup.dist.foreign.vui.audit.impl.CommonAuditAppliedFilterWidget;
import com.copyright.rup.dist.foreign.vui.audit.impl.CommonAuditFilterWidget;

/**
 * Implementation of AACL product family widget.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 03/16/2020
 *
 * @author Anton Azarenka
 */
public class AaclAuditFilterWidget extends CommonAuditFilterWidget {

    private static final long serialVersionUID = 3421128322830506328L;

    private final IAaclAuditFilterController controller;

    /**
     * Constructor.
     *
     * @param controller instance of {@link IAaclAuditFilterController}
     */
    public AaclAuditFilterWidget(IAaclAuditFilterController controller) {
        this.controller = controller;
    }

    @Override
    public void initFields() {
        //TODO: will implement later
    }

    @Override
    public void clearFilter() {
        //TODO: will implement later
    }

    @Override
    public void trimFilterValues() {
        //TODO: will implement later
    }

    @Override
    protected CommonAuditAppliedFilterWidget initAppliedFilterWidget() {
        return new AaclAuditAppliedFilterWidget(controller);
    }
}
