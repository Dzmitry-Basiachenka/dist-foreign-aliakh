package com.copyright.rup.dist.foreign.ui.report.impl.udm;

import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageService;
import com.copyright.rup.dist.foreign.ui.report.api.IUdmCommonReportController;
import com.copyright.rup.dist.foreign.ui.report.api.IUdmCommonReportWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementation of {@link IUdmCommonReportController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/05/2022
 *
 * @author Anton Azarenka
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public abstract class UdmCommonReportController extends CommonController<IUdmCommonReportWidget>
    implements IUdmCommonReportController {

    @Autowired
    private IUdmUsageService udmUsageService;

    @Override
    public List<Integer> getAllPeriods() {
        return udmUsageService.getPeriods();
    }
}
