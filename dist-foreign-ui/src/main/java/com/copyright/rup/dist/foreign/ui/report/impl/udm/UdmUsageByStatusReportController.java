package com.copyright.rup.dist.foreign.ui.report.impl.udm;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageService;
import com.copyright.rup.dist.foreign.ui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmCommonStatusReportWidget;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmUsagesByStatusReportController;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementation of {@link IUdmUsagesByStatusReportController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/20/2022
 *
 * @author Ihar Suvorau
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UdmUsageByStatusReportController extends CommonController<IUdmCommonStatusReportWidget>
    implements IUdmUsagesByStatusReportController {

    @Autowired
    private IUdmUsageService udmUsageService;

    @Override
    public List<Integer> getPeriods() {
        return udmUsageService.getPeriods();
    }

    @Override
    public IStreamSource getCsvStreamSource() {
        return new ByteArrayStreamSource("usage_details_by_status_report_", os -> {
        });
    }

    @Override
    public IUdmCommonStatusReportWidget instantiateWidget() {
        return new UdmCommonStatusReportWidget();
    }
}
