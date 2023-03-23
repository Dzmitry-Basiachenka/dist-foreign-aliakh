package com.copyright.rup.dist.foreign.ui.report.impl.acl;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.service.api.acl.IAclCalculationReportService;
import com.copyright.rup.dist.foreign.ui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.ui.report.api.acl.IAclCommonReportWidget;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Implementation of controller for {@link AclCommonReportController} to generate
 * ACL Comparison by Aggregate Licensee Class and Title report.
 * <p/>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 03/22/2023
 *
 * @author Mikita Maistrenka
 */
@Component("df.comparisonByAggLcClassAndTitleReportController")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AclComparisonByAggLcClassAndTitleReportController extends AclCommonReportController {

    @Autowired
    private IAclCalculationReportService reportService;

    @Override
    public IAclCommonReportWidget instantiateWidget() {
        return new AclComparisonByAggLcClassAndTitleReportWidget();
    }

    @Override
    public IStreamSource getCsvStreamSource() {
        return new ByteArrayStreamSource("comparison_by_agg_lc_class_and_title_report_", os ->
            reportService.writeAclComparisonByAggLcClassAndTitleReport(getWidget().getReportInfo(), os));
    }
}
