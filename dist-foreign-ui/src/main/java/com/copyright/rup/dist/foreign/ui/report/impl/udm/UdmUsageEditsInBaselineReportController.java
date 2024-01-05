package com.copyright.rup.dist.foreign.ui.report.impl.udm;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmReportService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageService;
import com.copyright.rup.dist.foreign.ui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmUsageEditsInBaselineReportController;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmUsageEditsInBaselineReportWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementation of {@link IUdmUsageEditsInBaselineReportController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p/>
 * Date: 03/11/2022
 *
 * @author Aliaksandr Liakh
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UdmUsageEditsInBaselineReportController extends CommonController<IUdmUsageEditsInBaselineReportWidget>
    implements IUdmUsageEditsInBaselineReportController {

    private static final long serialVersionUID = -5039678015666577583L;

    @Autowired
    private IUdmReportService udmReportService;
    @Autowired
    private IUdmUsageService udmUsageService;

    @Override
    public List<Integer> getAllPeriods() {
        return udmUsageService.getPeriods();
    }

    @Override
    public IStreamSource getCsvStreamSource() {
        return new ByteArrayStreamSource("usage_edits_in_baseline_report_",
            os -> udmReportService.writeUdmUsageEditsInBaselineCsvReport(getWidget().getReportFilter(), os));
    }

    @Override
    public IUdmUsageEditsInBaselineReportWidget instantiateWidget() {
        return new UdmUsageEditsInBaselineReportWidget();
    }
}
