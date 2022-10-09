package com.copyright.rup.dist.foreign.ui.report.impl.acl;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.service.api.acl.IAclCalculationReportService;
import com.copyright.rup.dist.foreign.ui.common.ByteArrayStreamSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Implementation of controller for {@link AclCommonReportController} to generate ACL Liabilities by Agg Lic Classes
 * report.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 10/03/2022
 *
 * @author Ihar Suvorau
 */
@Component("df.aclLiabilitiesByAggLicClassReportController")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AclLiabilitiesByAggLicClassReportController extends AclCommonReportController {

    @Autowired
    private IAclCalculationReportService reportService;

    @Override
    public IStreamSource getCsvStreamSource() {
        return new ByteArrayStreamSource("liabilities_by_agg_lic_class_report_", os ->
            reportService.writeAclLiabilitiesByAggLicClassReport(getWidget().getReportInfo(), os));
    }
}
