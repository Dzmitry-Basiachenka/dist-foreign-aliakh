package com.copyright.rup.dist.foreign.vui.audit.impl.fas;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.vui.audit.api.fas.IFasAuditController;
import com.copyright.rup.dist.foreign.vui.audit.api.fas.IFasAuditWidget;
import com.copyright.rup.dist.foreign.vui.audit.impl.CommonAuditWidget;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Implementation of {@link IFasAuditWidget}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/17/2019
 *
 * @author Aliaksanr Liakh
 */
public class FasAuditWidget extends CommonAuditWidget implements IFasAuditWidget {

    private static final long serialVersionUID = 1795603717468839053L;
    private static final String WIDTH_165 = "165px";
    private static final String WIDTH_300 = "300px";

    private final IFasAuditController controller;

    /**
     * Constructor.
     *
     * @param controller instance of {@link IFasAuditController}
     */
    FasAuditWidget(IFasAuditController controller) {
        this.controller = controller;
    }

    @Override
    protected void addColumns() {
        getAuditGrid().addComponentColumn(usage -> {
                String detailId = Objects.toString(usage.getId());
                var button = Buttons.createLinkButton(detailId);
                button.addClickListener(event -> controller.showUsageHistory(usage.getId(), detailId));
                return button;
            })
            .setHeader(ForeignUi.getMessage("table.column.detail_id"))
            .setSortable(true)
            .setSortProperty("detailId")
            .setWidth(WIDTH_300)
            .setFlexGrow(0)
            .setResizable(true);
        addColumn(UsageDto::getStatus, "table.column.usage_status", "status", "145px");
        addColumn(UsageDto::getProductFamily, "table.column.product_family", "productFamily", WIDTH_165);
        addColumn(UsageDto::getBatchName, "table.column.batch_name", "batchName", "180px");
        addColumn(usage -> CommonDateUtils.format(usage.getPaymentDate(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT),
            "table.column.payment_date", "paymentDate", "140px");
        addColumn(UsageDto::getRhAccountNumber, "table.column.rh_account_number", "rhAccountNumber", "140px");
        addColumn(UsageDto::getRhName, "table.column.rh_account_name", "rhName", WIDTH_300);
        addColumn(
            UsageDto::getPayeeAccountNumber, "table.column.payee_account_number", "payeeAccountNumber", WIDTH_165);
        addColumn(UsageDto::getPayeeName, "table.column.payee_name", "payeeName", WIDTH_300);
        addColumn(UsageDto::getWrWrkInst, "table.column.wr_wrk_inst", "wrWrkInst", "130px");
        addColumn(UsageDto::getSystemTitle, "table.column.system_title", "systemTitle", WIDTH_300);
        addColumn(UsageDto::getWorkTitle, "table.column.reported_title", "workTitle", WIDTH_300);
        addColumn(usageDto -> Objects.nonNull(usageDto.getFasUsage())
                ? usageDto.getFasUsage().getReportedStandardNumber() : null, "table.column.reported_standard_number",
            "reportedStandardNumber", "260px");
        addColumn(UsageDto::getStandardNumber, "table.column.standard_number", "standardNumber", "175px");
        addColumn(UsageDto::getStandardNumberType, "table.column.standard_number_type", "standardNumberType", "220px");
        addAmountColumn(UsageDto::getReportedValue, "table.column.reported_value", "reportedValue", WIDTH_165);
        addAmountColumn(UsageDto::getGrossAmount, "table.column.gross_amount_in_usd", "grossAmount", "170px");
        addAmountColumn(
            UsageDto::getBatchGrossAmount, "table.column.batch_gross_amount", "batchGrossAmount", WIDTH_165);
        addColumn(usage -> {
            var value = usage.getServiceFee();
            return Objects.nonNull(value)
                ? Objects.toString(value.multiply(new BigDecimal("100")).setScale(1, RoundingMode.HALF_UP))
                : StringUtils.EMPTY;
        }, "table.column.service_fee", "serviceFee", "145px");
        addColumn(UsageDto::getScenarioName, "table.column.scenario_name", "scenarioName", "155px");
        addColumn(UsageDto::getCheckNumber, "table.column.check_number", "checkNumber", "100px");
        addColumn(usage -> CommonDateUtils.format(usage.getCheckDate(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT),
            "table.column.check_date", "checkDate", "125px");
        addColumn(UsageDto::getCccEventId, "table.column.event_id", "cccEventId", "100px");
        addColumn(UsageDto::getDistributionName, "table.column.distribution_name", "distributionName", "115px");
        addColumn(usage ->
                CommonDateUtils.format(usage.getDistributionDate(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT),
            "table.column.distribution_date", "distributionDate", "110px");
        addColumn(usage -> CommonDateUtils.format(usage.getPeriodEndDate(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT),
            "table.column.period_ending", "periodEndDate", "150px");
        addColumn(UsageDto::getComment, "table.column.comment", "comment", "200px");
    }

    @Override
    public String initSearchMessage() {
        return "prompt.audit_search";
    }
}
