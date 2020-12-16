package com.copyright.rup.dist.foreign.ui.audit.impl.sal;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.ui.audit.api.ICommonAuditFilterController;
import com.copyright.rup.dist.foreign.ui.audit.api.ICommonAuditWidget;
import com.copyright.rup.dist.foreign.ui.audit.api.sal.ISalAuditController;
import com.copyright.rup.dist.foreign.ui.audit.api.sal.ISalAuditFilterController;
import com.copyright.rup.dist.foreign.ui.audit.impl.CommonAuditController;
import com.vaadin.data.provider.QuerySortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Implementation of {@link ISalAuditController}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 12/16/2020
 *
 * @author Aliaksandr Liakh
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SalAuditController extends CommonAuditController implements ISalAuditController {

    @Autowired
    private ISalAuditFilterController controller;

    @Override
    public ICommonAuditFilterController getAuditFilterController() {
        return controller;
    }

    @Override
    protected ICommonAuditWidget instantiateWidget() {
        return new SalAuditWidget(this);
    }

    @Override
    public IStreamSource getCsvStreamSource() {
        return getStreamSourceHandler().getCsvStreamSource(() -> "export_usage_audit_",
            pos -> { /* TODO to implement generation of the report*/ });
    }

    @Override
    public int getSize() {
        return 0; // TODO {aliakh} to implement
    }

    @Override
    public List<UsageDto> loadBeans(int startIndex, int count, List<QuerySortOrder> sortOrders) {
        return Collections.emptyList(); // TODO {aliakh} to implement
    }
}
