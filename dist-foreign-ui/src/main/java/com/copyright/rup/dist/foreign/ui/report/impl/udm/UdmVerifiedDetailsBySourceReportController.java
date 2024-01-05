package com.copyright.rup.dist.foreign.ui.report.impl.udm;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmReportService;
import com.copyright.rup.dist.foreign.ui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmCommonReportWidget;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmVerifiedDetailsBySourceReportController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link IUdmVerifiedDetailsBySourceReportController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/11/2022
 *
 * @author Mikita Maistrenka
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UdmVerifiedDetailsBySourceReportController extends UdmCommonReportController
    implements IUdmVerifiedDetailsBySourceReportController {

    private static final long serialVersionUID = -603944637965943303L;

    @Autowired
    private IUdmReportService udmReportService;

    @Override
    public IStreamSource getCsvStreamSource() {
        return new ByteArrayStreamSource("verified_details_by_source_report_",
            os -> udmReportService.writeUdmVerifiedDetailsBySourceReport(getWidget().getReportFilter(), os));
    }

    @Override
    public IUdmCommonReportWidget instantiateWidget() {
        return new UdmCommonReportWidget("Load");
    }
}
