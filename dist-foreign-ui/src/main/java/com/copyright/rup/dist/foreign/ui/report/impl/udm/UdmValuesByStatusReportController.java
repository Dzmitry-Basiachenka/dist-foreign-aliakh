package com.copyright.rup.dist.foreign.ui.report.impl.udm;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmReportService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmValueService;
import com.copyright.rup.dist.foreign.ui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmCommonStatusReportWidget;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmValuesByStatusReportController;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementation of {@link IUdmValuesByStatusReportController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/21/2022
 *
 * @author Mikita Maistrenka
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UdmValuesByStatusReportController extends CommonController<IUdmCommonStatusReportWidget>
    implements IUdmValuesByStatusReportController {

    @Autowired
    private IUdmValueService udmValueService;
    @Autowired
    private IUdmReportService udmReportService;

    @Override
    public List<Integer> getPeriods() {
        return udmValueService.getPeriods();
    }

    @Override
    public IStreamSource getCsvStreamSource() {
        return new ByteArrayStreamSource("values_by_status_report_",
            os -> udmReportService.writeUdmValuesByStatusCsvReport(getWidget().getSelectedPeriod(), os));
    }

    @Override
    public IUdmCommonStatusReportWidget instantiateWidget() {
        return new UdmCommonStatusReportWidget();
    }
}
