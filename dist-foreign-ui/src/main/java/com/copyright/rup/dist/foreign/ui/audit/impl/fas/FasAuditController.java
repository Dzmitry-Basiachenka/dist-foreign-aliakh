package com.copyright.rup.dist.foreign.ui.audit.impl.fas;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.ui.audit.api.ICommonAuditWidget;
import com.copyright.rup.dist.foreign.ui.audit.api.fas.IFasAuditController;
import com.copyright.rup.dist.foreign.ui.audit.impl.CommonAuditController;

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

    @Override
    public IStreamSource getCsvStreamSource() {
        return getStreamSourceHandler().getCsvStreamSource(() -> "export_usage_audit_",
            pos -> getReportService().writeAuditFasCsvReport(getFilter(), pos));
    }

    @Override
    protected ICommonAuditWidget instantiateWidget() {
        return new FasAuditWidget(this);
    }
}
