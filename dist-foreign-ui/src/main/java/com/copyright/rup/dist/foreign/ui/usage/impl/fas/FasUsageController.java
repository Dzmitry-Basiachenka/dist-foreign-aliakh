package com.copyright.rup.dist.foreign.ui.usage.impl.fas;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.fas.IFasUsageController;
import com.copyright.rup.dist.foreign.ui.usage.impl.CommonUsageController;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link IFasUsageController}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/16/19
 *
 * @author Darya Baraukova
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FasUsageController extends CommonUsageController implements IFasUsageController {

    @Override
    public IStreamSource getExportUsagesStreamSource() {
        return getStreamSourceHandler().getCsvStreamSource(() -> "export_usage_",
            pos -> getReportService().writeFasUsageCsvReport(
                getUsageFilterController().getWidget().getAppliedFilter(), pos));
    }

    @Override
    protected ICommonUsageWidget instantiateWidget() {
        return new FasUsageWidget(this);
    }
}
