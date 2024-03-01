package com.copyright.rup.dist.foreign.vui.audit.impl.aacl;

import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.vui.audit.api.ICommonAuditFilterController;
import com.copyright.rup.dist.foreign.vui.audit.impl.CommonAuditAppliedFilterWidget;

/**
 * Widget for applied filters on Audit tab for AACL product family.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 03/14/2023
 *
 * @author Stepan Karakhanov
 */
public class AaclAuditAppliedFilterWidget extends CommonAuditAppliedFilterWidget {

    private static final long serialVersionUID = -4501260858745972136L;

    /**
     * Constructor.
     *
     * @param controller instance of {@link ICommonAuditFilterController}
     */
    public AaclAuditAppliedFilterWidget(ICommonAuditFilterController controller) {
        super(controller);
    }

    @Override
    public void refreshFilterPanel(AuditFilter filter) {
        //TODO: will implement later
    }
}
