package com.copyright.rup.dist.foreign.ui.report.impl;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.ui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.ui.report.api.ISalHistoricalItemBankDetailsReportController;
import com.copyright.rup.dist.foreign.ui.report.api.ISalHistoricalItemBankDetailsReportWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link ISalHistoricalItemBankDetailsReportController}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 11/25/2020
 *
 * @author Aliaksandr Liakh
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SalHistoricalItemBankDetailsReportController
    extends CommonController<ISalHistoricalItemBankDetailsReportWidget>
    implements ISalHistoricalItemBankDetailsReportController {

    @Override
    public IStreamSource getCsvStreamSource() {
        return new ByteArrayStreamSource("SAL_historical_item_bank_",
            os -> { /*TODO {aliakh} implement report generation */});
    }

    @Override
    protected ISalHistoricalItemBankDetailsReportWidget instantiateWidget() {
        return new SalHistoricalItemBankDetailsReportWidget();
    }

    @Override
    public List<String> getLicensees() {
        return new ArrayList<>(); // TODO {aliakh} implement loading of licensees
    }
}
