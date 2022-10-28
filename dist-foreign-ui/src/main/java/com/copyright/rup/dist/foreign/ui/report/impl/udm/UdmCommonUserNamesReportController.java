package com.copyright.rup.dist.foreign.ui.report.impl.udm;

import com.copyright.rup.dist.foreign.service.api.acl.IUdmReportService;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmCommonUserNamesReportController;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmCommonUserNamesReportWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Implementation of controller for {@link IUdmCommonUserNamesReportController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 10/26/2022
 *
 * @author Dzmitry Basiachenka
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public abstract class UdmCommonUserNamesReportController extends CommonController<IUdmCommonUserNamesReportWidget>
    implements IUdmCommonUserNamesReportController {

    @Autowired
    private IUdmReportService udmReportService;

    @Override
    protected abstract IUdmCommonUserNamesReportWidget instantiateWidget();

    protected IUdmReportService getUdmReportService() {
        return udmReportService;
    }
}
