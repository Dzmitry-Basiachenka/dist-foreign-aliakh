package com.copyright.rup.dist.foreign.ui.audit.impl.nts;

import com.copyright.rup.dist.foreign.ui.audit.api.ICommonAuditFilterWidget;
import com.copyright.rup.dist.foreign.ui.audit.api.nts.INtsAuditFilterController;
import com.copyright.rup.dist.foreign.ui.audit.impl.CommonAuditFilterController;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link INtsAuditFilterController}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 03/16/2020
 *
 * @author Anton Azarenka
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class NtsAuditFilterController extends CommonAuditFilterController implements INtsAuditFilterController {

    private static final long serialVersionUID = 160199903523896116L;

    @Override
    protected ICommonAuditFilterWidget instantiateWidget() {
        return new NtsAuditFilterWidget(this);
    }
}
