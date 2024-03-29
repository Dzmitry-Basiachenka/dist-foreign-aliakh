package com.copyright.rup.dist.foreign.vui.report.impl;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioService;
import com.copyright.rup.dist.foreign.vui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.vui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.vui.report.api.ITaxNotificationReportController;
import com.copyright.rup.dist.foreign.vui.report.api.ITaxNotificationReportWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 * Implementation of {@link ITaxNotificationReportController}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 6/24/20
 *
 * @author Stanislau Rudak
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TaxNotificationReportController extends CommonController<ITaxNotificationReportWidget>
    implements ITaxNotificationReportController {

    private static final long serialVersionUID = 5435754508646932860L;

    @Autowired
    private IScenarioService scenarioService;
    @Autowired
    private IAclScenarioService aclScenarioService;
    @Autowired
    private IReportService reportService;
    @Autowired
    private IProductFamilyProvider productFamilyProvider;

    @Override
    public List<Scenario> getScenarios() {
        var productFamily = productFamilyProvider.getSelectedProductFamily();
        Set<String> productFamilies = FdaConstants.FAS_FAS2_PRODUCT_FAMILY_SET.contains(productFamily)
            ? FdaConstants.FAS_FAS2_PRODUCT_FAMILY_SET
            : Set.of(productFamily);
        Set<ScenarioStatusEnum> statuses = EnumSet.of(ScenarioStatusEnum.IN_PROGRESS, ScenarioStatusEnum.SUBMITTED,
            ScenarioStatusEnum.APPROVED);
        return FdaConstants.ACL_PRODUCT_FAMILY.equals(productFamily)
            ? aclScenarioService.getAclScenariosByStatuses(statuses)
            : scenarioService.getScenariosByProductFamiliesAndStatuses(productFamilies, statuses);
    }

    @Override
    public IStreamSource getCsvStreamSource() {
        var productFamily = productFamilyProvider.getSelectedProductFamily();
        return new ByteArrayStreamSource("tax_notification_report_",
            os -> reportService.writeTaxNotificationCsvReport(productFamily, getWidget().getSelectedScenarioIds(),
                getWidget().getNumberOfDays(), os));
    }

    @Override
    protected ITaxNotificationReportWidget instantiateWidget() {
        return new TaxNotificationReportWidget();
    }
}
