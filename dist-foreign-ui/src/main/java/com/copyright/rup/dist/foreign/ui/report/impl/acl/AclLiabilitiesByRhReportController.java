package com.copyright.rup.dist.foreign.ui.report.impl.acl;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.service.api.acl.IAclCalculationReportService;
import com.copyright.rup.dist.foreign.ui.common.ByteArrayStreamSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link AclCommonReportController} to generate ACL Liabilities by Rightsholder report.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 10/13/2022
 *
 * @author Dzmitry Basiachenka
 */
@Component("df.aclLiabilitiesByRhReportController")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AclLiabilitiesByRhReportController extends AclCommonReportController {

    private static final long serialVersionUID = -1822941057351563475L;

    @Autowired
    private IAclCalculationReportService reportService;

    @Override
    public IStreamSource getCsvStreamSource() {
        return new ByteArrayStreamSource("liabilities_by_rightsholder_report_", os ->
            reportService.writeAclLiabilitiesByRhReport(getWidget().getReportInfo(), os));
    }
}
