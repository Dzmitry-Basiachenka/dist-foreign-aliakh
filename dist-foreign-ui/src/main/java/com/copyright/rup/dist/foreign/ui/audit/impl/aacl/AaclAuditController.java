package com.copyright.rup.dist.foreign.ui.audit.impl.aacl;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.ui.audit.api.ICommonAuditWidget;
import com.copyright.rup.dist.foreign.ui.audit.api.aacl.IAaclAuditController;
import com.copyright.rup.dist.foreign.ui.audit.impl.CommonAuditController;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link IAaclAuditController}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 03/12/2020
 *
 * @author Anton Azarenka
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AaclAuditController extends CommonAuditController implements IAaclAuditController {

    @Override
    protected ICommonAuditWidget instantiateWidget() {
        return new AaclAuditWidget(this);
    }

    @Override
    public IStreamSource getCsvStreamSource() {
        return getStreamSourceHandler().getCsvStreamSource(() -> "export_usage_audit_",
            pos -> getReportService().writeAuditAaclCsvReport(getFilter(), pos));
    }
}
