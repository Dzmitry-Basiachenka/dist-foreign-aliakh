package com.copyright.rup.dist.foreign.ui.audit.impl.sal;

import com.copyright.rup.dist.foreign.domain.report.SalLicensee;
import com.copyright.rup.dist.foreign.ui.audit.api.ICommonAuditFilterWidget;
import com.copyright.rup.dist.foreign.ui.audit.api.sal.ISalAuditFilterController;
import com.copyright.rup.dist.foreign.ui.audit.impl.CommonAuditFilterController;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementation of {@link ISalAuditFilterController}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 12/16/2020
 *
 * @author Aliaksandr Liakh
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SalAuditFilterController extends CommonAuditFilterController implements ISalAuditFilterController {

    @Override
    protected ICommonAuditFilterWidget instantiateWidget() {
        return new SalAuditFilterWidget(this);
    }

    @Override
    public List<Integer> getUsagePeriods() {
        return getUsageBatchService().getSalUsagePeriods();
    }

    @Override
    public List<SalLicensee> getSalLicensees() {
        return getUsageBatchService().getSalLicensees();
    }
}
