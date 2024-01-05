package com.copyright.rup.dist.foreign.ui.report.impl.udm;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmValueAuditService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmValueService;
import com.copyright.rup.dist.foreign.ui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmCommonUserNamesReportWidget;

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
 * Date: 10/26/2022
 *
 * @author Dzmitry Basiachenka
 */
@Component("df.udmBaselineValueUpdatesReportController")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UdmBaselineValueUpdatesReportController extends UdmCommonUserNamesReportController {

    private static final long serialVersionUID = 3936436708507290908L;

    @Autowired
    private IUdmValueService udmValueService;
    @Autowired
    private IUdmValueAuditService udmValueAuditService;

    @Override
    public List<Integer> getAllPeriods() {
        return udmValueService.getPeriods();
    }

    @Override
    public List<String> getUserNames() {
        return udmValueAuditService.getUserNames();
    }

    @Override
    public IStreamSource getCsvStreamSource() {
        return new ByteArrayStreamSource("baseline_value_updates_report_",
            os -> getUdmReportService().writeUdmBaselineValueUpdatesCsvReport(getWidget().getReportFilter(), os));
    }

    @Override
    protected IUdmCommonUserNamesReportWidget instantiateWidget() {
        return new UdmCommonUserNamesReportWidget("label.updated_date_from", "label.updated_date_to");
    }
}
