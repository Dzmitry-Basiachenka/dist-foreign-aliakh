package com.copyright.rup.dist.foreign.vui.report.impl;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.vui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.vui.report.api.IUndistributedLiabilitiesReportController;
import com.copyright.rup.dist.foreign.vui.report.api.IUndistributedLiabilitiesReportWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Implementation of controller for {@link UndistributedLiabilitiesReportWidget}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 5/10/2018
 *
 * @author Uladzislau_Shalamitski
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UndistributedLiabilitiesReportController extends CommonController<IUndistributedLiabilitiesReportWidget>
    implements IUndistributedLiabilitiesReportController {

    private static final long serialVersionUID = 4236362850636742386L;

    @Autowired
    private IReportService reportService;

    @Override
    public IStreamSource getCsvStreamSource() {
        return new ByteArrayStreamSource("undistributed_liabilities_",
            os -> reportService.writeUndistributedLiabilitiesCsvReport(getWidget().getPaymentDate(), os,
                FdaConstants.FAS_FAS2_PRODUCT_FAMILY_SET));
    }

    @Override
    protected IUndistributedLiabilitiesReportWidget instantiateWidget() {
        return new UndistributedLiabilitiesReportWidget();
    }
}
