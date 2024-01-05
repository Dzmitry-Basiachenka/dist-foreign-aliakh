package com.copyright.rup.dist.foreign.ui.audit.impl.aacl;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.ui.audit.api.aacl.IAaclAuditController;
import com.copyright.rup.dist.foreign.ui.audit.api.aacl.IAaclAuditWidget;
import com.copyright.rup.dist.foreign.ui.audit.impl.CommonAuditWidget;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.vaadin.ui.Buttons;

import com.vaadin.ui.Button;
import com.vaadin.ui.themes.ValoTheme;

import java.util.Objects;

/**
 * Implementation of {@link IAaclAuditWidget}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 03/12/2020
 *
 * @author Anton Azarenka
 */
public class AaclAuditWidget extends CommonAuditWidget implements IAaclAuditWidget {

    private static final long serialVersionUID = 3782190209610342273L;

    private final IAaclAuditController controller;

    /**
     * Constructor.
     *
     * @param controller instance of {@link IAaclAuditController}
     */
    AaclAuditWidget(IAaclAuditController controller) {
        this.controller = controller;
    }

    @Override
    protected void addColumns() {
        getAuditGrid().addComponentColumn(usage -> {
            String detailId = Objects.toString(usage.getId());
            Button button = Buttons.createButton(detailId);
            button.addStyleName(ValoTheme.BUTTON_LINK);
            button.addClickListener(event -> controller.showUsageHistory(usage.getId(), detailId));
            return button;
        })
            .setCaption(ForeignUi.getMessage("table.column.detail_id"))
            .setSortProperty("detailId")
            .setWidth(130);
        addColumn(usage -> usage.getAaclUsage().getBaselineId(), "table.column.baseline_id", "baselineId", 125);
        addColumn(UsageDto::getStatus, "table.column.usage_status", "status", 115);
        addColumn(UsageDto::getProductFamily, "table.column.product_family", "productFamily", 125);
        addColumn(UsageDto::getBatchName, "table.column.batch_name", "batchName", 140);
        addColumn(usage -> CommonDateUtils.format(usage.getAaclUsage().getBatchPeriodEndDate(),
            RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT), "table.column.period_end_date", "batchPeriodEndDate", 115);
        addColumn(UsageDto::getRhAccountNumber, "table.column.rh_account_number", "rhAccountNumber", 115);
        addColumn(UsageDto::getRhName, "table.column.rh_account_name", "rhName", 300);
        addColumn(UsageDto::getPayeeAccountNumber, "table.column.payee_account_number", "payeeAccountNumber", 115);
        addColumn(UsageDto::getPayeeName, "table.column.payee_name", "payeeName", 300);
        addColumn(UsageDto::getWrWrkInst, "table.column.wr_wrk_inst", "wrWrkInst", 110);
        addColumn(UsageDto::getSystemTitle, "table.column.system_title", "systemTitle", 300);
        addColumn(UsageDto::getStandardNumber, "table.column.standard_number", "standardNumber", 140);
        addColumn(UsageDto::getStandardNumberType, "table.column.standard_number_type", "standardNumberType", 155);
        addAmountColumn(UsageDto::getGrossAmount, "table.column.gross_amount_in_usd", "grossAmount", 130);
        addAmountColumn(UsageDto::getServiceFeeAmount, "table.column.service_fee_amount", "serviceFeeAmount", 150);
        addAmountColumn(UsageDto::getNetAmount, "table.column.net_amount_in_usd", "netAmount", 120);
        addColumn(UsageDto::getScenarioName, "table.column.scenario_name", "scenarioName", 125);
        addColumn(UsageDto::getCheckNumber, "table.column.check_number", "checkNumber", 85);
        addColumn(usage -> CommonDateUtils.format(usage.getCheckDate(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT),
            "table.column.check_date", "checkDate", 105);
        addColumn(UsageDto::getCccEventId, "table.column.event_id", "cccEventId", 85);
        addColumn(UsageDto::getDistributionName, "table.column.distribution_name", "distributionName", 110);
        addColumn(usage ->
                CommonDateUtils.format(usage.getDistributionDate(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT),
            "table.column.distribution_date", "distributionDate", 105);
        addColumn(usage -> usage.getAaclUsage().getDetailLicenseeClass().getId(), "table.column.det_lc_id",
            "detailLicenseeClassId", 80);
        addColumn(usage -> usage.getAaclUsage().getDetailLicenseeClass().getEnrollmentProfile(),
            "table.column.det_lc_enrollment", "enrollmentProfile", 140);
        addColumn(usage -> usage.getAaclUsage().getDetailLicenseeClass().getDiscipline(),
            "table.column.det_lc_discipline", "discipline", 140);
        addColumn(usage -> usage.getAaclUsage().getPublicationType().getName(), "table.column.publication_type",
            "publicationType", 140);
        addColumn(usage -> usage.getAaclUsage().getUsageAge().getPeriod(), "table.column.usage_period", "usagePeriod",
            100);
        addColumn(usage -> usage.getAaclUsage().getUsageSource(), "table.column.usage_source", "usageSource",
            140);
        addColumn(UsageDto::getComment, "table.column.comment", "comment", 200);
    }

    @Override
    public String initSearchMessage() {
        return "prompt.audit_search_aacl_sal";
    }
}
