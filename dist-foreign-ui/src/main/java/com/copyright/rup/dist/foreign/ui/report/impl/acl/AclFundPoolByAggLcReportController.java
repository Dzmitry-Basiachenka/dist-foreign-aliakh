package com.copyright.rup.dist.foreign.ui.report.impl.acl;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.AclFundPool;
import com.copyright.rup.dist.foreign.service.api.acl.IAclCalculationReportService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclFundPoolService;
import com.copyright.rup.dist.foreign.ui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.ui.report.api.acl.IAclFundPoolByAggLcReportController;
import com.copyright.rup.dist.foreign.ui.report.api.acl.IAclFundPoolByAggLcReportWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Set;

/**
 * Interface for fund pool by Aggregate Licensee Class report widget.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 03/22/2023
 *
 * @author Ihar Suvorau
 */
@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AclFundPoolByAggLcReportController extends CommonController<IAclFundPoolByAggLcReportWidget>
    implements IAclFundPoolByAggLcReportController {

    @Autowired
    private IAclCalculationReportService reportService;

    @Autowired
    private IAclFundPoolService fundPoolService;

    @Override
    public List<Integer> getPeriods() {
        return fundPoolService.getPeriods();
    }

    @Override
    public List<AclFundPool> getFundPools(Set<Integer> periods) {
        return fundPoolService.getFundPoolsByPeriods(periods);
    }

    @Override
    public IStreamSource getCsvStreamSource() {
        return new ByteArrayStreamSource("fund_pools_by_aggregate_licensee_class_report_", os ->
            reportService.writeAclFundPoolByAggLcReport(getWidget().getFundPoolIds(), os));
    }

    @Override
    protected IAclFundPoolByAggLcReportWidget instantiateWidget() {
        return new AclFundPoolByAggLcReportWidget();
    }
}
