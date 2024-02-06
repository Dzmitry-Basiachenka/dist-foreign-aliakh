package com.copyright.rup.dist.foreign.vui.audit.impl.fas;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.vui.audit.api.ICommonAuditWidget;
import com.copyright.rup.dist.foreign.vui.audit.api.fas.IFasAuditController;
import com.copyright.rup.dist.foreign.vui.audit.impl.CommonAuditController;
import com.copyright.rup.dist.foreign.vui.common.ByteArrayStreamSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link IFasAuditController}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/17/2019
 *
 * @author Aliaksanr Liakh
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FasAuditController extends CommonAuditController implements IFasAuditController {

    private static final long serialVersionUID = 1568758804212203021L;

    @Override
    public IStreamSource getCsvStreamSource() {
        //TODO: {dbasiachenka} implement
        return new ByteArrayStreamSource(StringUtils.EMPTY, os -> {});
    }

    @Override
    protected ICommonAuditWidget instantiateWidget() {
        return new FasAuditWidget();
    }
}
