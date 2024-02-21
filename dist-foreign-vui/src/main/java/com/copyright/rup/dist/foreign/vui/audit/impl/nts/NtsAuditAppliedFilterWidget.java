package com.copyright.rup.dist.foreign.vui.audit.impl.nts;

import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.vui.audit.api.ICommonAuditFilterController;
import com.copyright.rup.dist.foreign.vui.audit.impl.CommonAuditAppliedFilterWidget;

/**
 * Widget for applied filters on Audit tab for NTS product family.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 03/14/2023
 *
 * @author Stepan Karakhanov
 */
public class NtsAuditAppliedFilterWidget extends CommonAuditAppliedFilterWidget {

    private static final long serialVersionUID = -3875780924310059268L;

    /**
     * Constructor.
     *
     * @param controller instance of {@link ICommonAuditFilterController}
     */
    public NtsAuditAppliedFilterWidget(ICommonAuditFilterController controller) {
        super(controller);
    }

    @Override
    public void refreshFilterPanel(AuditFilter filter) {
    }
}
