package com.copyright.rup.dist.foreign.ui.report.impl.udm;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmReportService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageService;
import com.copyright.rup.dist.foreign.ui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmUsableDetailsByCountryReportController;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmUsableDetailsByCountryReportWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementation of {@link IUdmUsableDetailsByCountryReportController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/09/2022
 *
 * @author Mikita Maistrenka
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UdmUsableDetailsByCountryReportController extends CommonController<IUdmUsableDetailsByCountryReportWidget>
    implements IUdmUsableDetailsByCountryReportController {

    @Autowired
    private IUdmUsageService udmUsageService;
    @Autowired
    private IUdmReportService udmReportService;

    @Override
    public List<Integer> getAllPeriods() {
        return udmUsageService.getPeriods();
    }

    @Override
    public IStreamSource getCsvStreamSource() {
        return new ByteArrayStreamSource("usable_details_by_country_report_",
            os -> udmReportService.writeUdmUsableDetailsByCountryCsvReport(getWidget().getReportFilter(), os));
    }

    @Override
    public IUdmUsableDetailsByCountryReportWidget instantiateWidget() {
        return new UdmUsableDetailsByCountryReportWidget("Loaded");
    }
}
