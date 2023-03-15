package com.copyright.rup.dist.foreign.ui.audit.impl.fas;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.ui.audit.api.fas.IFasAuditController;
import com.copyright.rup.dist.foreign.ui.audit.api.fas.IFasAuditWidget;
import com.copyright.rup.dist.foreign.ui.audit.impl.CommonAuditWidget;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.vaadin.ui.Buttons;

import com.vaadin.ui.Button;
import com.vaadin.ui.themes.ValoTheme;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
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
            Button button = Buttons.createButton(detailId);
            button.addStyleName(ValoTheme.BUTTON_LINK);
            button.addClickListener(event -> controller.showUsageHistory(usage.getId(), detailId));
            return button;
        })
            .setCaption(ForeignUi.getMessage("table.column.detail_id"))
            .setSortProperty("detailId")
            .setWidth(130);
        addColumn(UsageDto::getStatus, "table.column.usage_status", "status", 115);
        addColumn(UsageDto::getProductFamily, "table.column.product_family", "productFamily", 125);
        addColumn(UsageDto::getBatchName, "table.column.batch_name", "batchName", 140);
        addColumn(usage -> CommonDateUtils.format(usage.getPaymentDate(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT),
            "table.column.payment_date", "paymentDate", 115);
        addColumn(UsageDto::getRhAccountNumber, "table.column.rh_account_number", "rhAccountNumber", 115);
        addColumn(UsageDto::getRhName, "table.column.rh_account_name", "rhName", 300);
        addColumn(UsageDto::getPayeeAccountNumber, "table.column.payee_account_number", "payeeAccountNumber", 115);
        addColumn(UsageDto::getPayeeName, "table.column.payee_name", "payeeName", 300);
        addColumn(UsageDto::getWrWrkInst, "table.column.wr_wrk_inst", "wrWrkInst", 110);
        addColumn(UsageDto::getSystemTitle, "table.column.system_title", "systemTitle", 300);
        addColumn(UsageDto::getWorkTitle, "table.column.reported_title", "workTitle", 300);
        addColumn(usageDto -> Objects.nonNull(usageDto.getFasUsage())
                ? usageDto.getFasUsage().getReportedStandardNumber() : null, "table.column.reported_standard_number",
            "reportedStandardNumber", 190);
        addColumn(UsageDto::getStandardNumber, "table.column.standard_number", "standardNumber", 140);
        addColumn(UsageDto::getStandardNumberType, "table.column.standard_number_type", "standardNumberType", 155);
        addAmountColumn(UsageDto::getReportedValue, "table.column.reported_value", "reportedValue", 115);
        addAmountColumn(UsageDto::getGrossAmount, "table.column.gross_amount_in_usd", "grossAmount", 130);
        addAmountColumn(UsageDto::getBatchGrossAmount, "table.column.batch_gross_amount", "batchGrossAmount", 130);
        addColumn(usage -> {
            BigDecimal value = usage.getServiceFee();
            return Objects.nonNull(value)
                ? Objects.toString(value.multiply(new BigDecimal("100")).setScale(1, BigDecimal.ROUND_HALF_UP))
                : StringUtils.EMPTY;
        }, "table.column.service_fee", "serviceFee", 115);
        addColumn(UsageDto::getScenarioName, "table.column.scenario_name", "scenarioName", 125);
        addColumn(UsageDto::getCheckNumber, "table.column.check_number", "checkNumber", 85);
        addColumn(usage -> CommonDateUtils.format(usage.getCheckDate(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT),
            "table.column.check_date", "checkDate", 105);
        addColumn(UsageDto::getCccEventId, "table.column.event_id", "cccEventId", 85);
        addColumn(UsageDto::getDistributionName, "table.column.distribution_name", "distributionName", 110);
        addColumn(usage ->
                CommonDateUtils.format(usage.getDistributionDate(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT),
            "table.column.distribution_date", "distributionDate", 105);
        addColumn(usage -> CommonDateUtils.format(usage.getPeriodEndDate(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT),
            "table.column.period_ending", "periodEndDate", 115);
        addColumn(UsageDto::getComment, "table.column.comment", "comment", 200);
    }

    @Override
    public String initSearchMessage() {
        return "prompt.audit_search";
    }
}
