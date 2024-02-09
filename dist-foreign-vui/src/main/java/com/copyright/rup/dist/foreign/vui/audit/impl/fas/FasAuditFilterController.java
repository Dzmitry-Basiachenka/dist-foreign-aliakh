package com.copyright.rup.dist.foreign.vui.audit.impl.fas;

import com.copyright.rup.dist.foreign.vui.audit.api.ICommonAuditFilterWidget;
import com.copyright.rup.dist.foreign.vui.audit.api.fas.IFasAuditFilterController;
import com.copyright.rup.dist.foreign.vui.audit.impl.CommonAuditFilterController;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link IFasAuditFilterController}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 03/16/2020
 *
 * @author Anton Azarenka
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FasAuditFilterController extends CommonAuditFilterController implements IFasAuditFilterController {

    private static final long serialVersionUID = -9141406475444598809L;

    @Override
    protected ICommonAuditFilterWidget instantiateWidget() {
        return new FasAuditFilterWidget();
    }
}
