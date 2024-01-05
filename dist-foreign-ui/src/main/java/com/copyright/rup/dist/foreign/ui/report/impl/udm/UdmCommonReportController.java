package com.copyright.rup.dist.foreign.ui.report.impl.udm;

import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageService;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmCommonReportController;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmCommonReportWidget;
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

    private static final long serialVersionUID = 9205052365365882970L;

    @Autowired
    private IUdmUsageService udmUsageService;

    @Override
    public List<Integer> getAllPeriods() {
        return udmUsageService.getPeriods();
    }
}
