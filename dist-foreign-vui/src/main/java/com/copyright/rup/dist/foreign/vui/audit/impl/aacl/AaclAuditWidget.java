package com.copyright.rup.dist.foreign.vui.audit.impl.aacl;

import com.copyright.rup.dist.foreign.vui.audit.api.aacl.IAaclAuditWidget;
import com.copyright.rup.dist.foreign.vui.audit.impl.CommonAuditWidget;

/**
 * Implementation of {@link IAaclAuditWidget}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 03/12/2020
 *
 * @author Anton Azarenka
 */
public class AaclAuditWidget extends CommonAuditWidget implements IAaclAuditWidget {

    private static final long serialVersionUID = 3782190209610342273L;

    @Override
    public String initSearchMessage() {
        return "prompt.audit_search_aacl_sal";
    }

    @Override
    protected void addColumns() {
        //TODO: will implement later
    }
}
