package com.copyright.rup.dist.foreign.ui.report.impl.udm;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmReportService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageService;
import com.copyright.rup.dist.foreign.ui.common.ByteArrayStreamSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementation of controller for {@link UdmCommonUserNamesReportController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/06/2022
 *
 * @author Ihar Suvorau
 */
@Component("df.completedAssignmentsReportController")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CompletedAssignmentsReportController extends UdmCommonUserNamesReportController {

    @Autowired
    private IUdmReportService udmReportService;
    @Autowired
    private IUdmUsageService udmUsageService;

    @Override
    public List<Integer> getAllPeriods() {
        return udmUsageService.getPeriods();
    }

    @Override
    public List<String> getUserNames() {
        return udmUsageService.getUserNames();
    }

    @Override
    public IStreamSource getCsvStreamSource() {
        return new ByteArrayStreamSource("completed_assignments_by_employee_report_",
            os -> udmReportService.writeUdmCompletedAssignmentsCsvReport(getWidget().getReportFilter(), os));
    }
}
