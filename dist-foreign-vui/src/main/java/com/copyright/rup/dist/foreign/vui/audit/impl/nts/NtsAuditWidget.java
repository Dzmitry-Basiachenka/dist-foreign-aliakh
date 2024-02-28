package com.copyright.rup.dist.foreign.vui.audit.impl.nts;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.vui.audit.api.nts.INtsAuditController;
import com.copyright.rup.dist.foreign.vui.audit.api.nts.INtsAuditWidget;
import com.copyright.rup.dist.foreign.vui.audit.impl.CommonAuditWidget;
import com.copyright.rup.dist.foreign.vui.common.utils.GridColumnEnum;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Implementation of {@link INtsAuditWidget}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/17/2019
 *
 * @author Aliaksanr Liakh
 */
public class NtsAuditWidget extends CommonAuditWidget implements INtsAuditWidget {

    private static final long serialVersionUID = -3481844881825089678L;

    private final INtsAuditController controller;

    /**
     * Constructor.
     *
     * @param controller instance of {@link INtsAuditController}
     */
    NtsAuditWidget(INtsAuditController controller) {
        this.controller = controller;
    }

    @Override
    protected void addColumns() {
        getAuditGrid().addComponentColumn(usage -> {
                var detailId = Objects.toString(usage.getId());
                var button = Buttons.createLinkButton(detailId);
                button.addClickListener(event -> controller.showUsageHistory(usage.getId(), detailId));
                return button;
            })
            .setHeader(ForeignUi.getMessage(GridColumnEnum.ID.getCaption()))
            .setWidth(GridColumnEnum.ID.getWidth())
            .setSortProperty(GridColumnEnum.ID.getSort())
            .setSortable(true)
            .setFlexGrow(0)
            .setResizable(true);
        addColumn(UsageDto::getStatus, GridColumnEnum.STATUS);
        addColumn(UsageDto::getProductFamily, GridColumnEnum.PRODUCT_FAMILY);
        addColumn(UsageDto::getBatchName, GridColumnEnum.BATCH_NAME);
        addColumn(usage -> CommonDateUtils.format(usage.getPaymentDate(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT),
            GridColumnEnum.PAYMENT_DATE);
        addColumn(UsageDto::getRhAccountNumber, GridColumnEnum.RH_ACCOUNT_NUMBER);
        addColumn(UsageDto::getRhName, GridColumnEnum.RH_NAME);
        addColumn(UsageDto::getPayeeAccountNumber, GridColumnEnum.PAYEE_ACCOUNT_NUMBER);
        addColumn(UsageDto::getPayeeName, GridColumnEnum.PAYEE_NAME);
        addColumn(UsageDto::getWrWrkInst, GridColumnEnum.WR_WRK_INST);
        addColumn(UsageDto::getSystemTitle, GridColumnEnum.SYSTEM_TITLE);
        addColumn(UsageDto::getWorkTitle, GridColumnEnum.WORK_TITLE);
        addColumn(UsageDto::getStandardNumber, GridColumnEnum.STANDARD_NUMBER);
        addColumn(UsageDto::getStandardNumberType, GridColumnEnum.STANDARD_NUMBER_TYPE);
        addAmountColumn(UsageDto::getReportedValue, GridColumnEnum.REPORTED_VALUE);
        addAmountColumn(UsageDto::getGrossAmount, GridColumnEnum.GROSS_AMOUNT_IN_USD);
        addColumn(usage -> {
            var value = usage.getServiceFee();
            return Objects.nonNull(value)
                ? Objects.toString(value.multiply(new BigDecimal("100")).setScale(1, RoundingMode.HALF_UP))
                : StringUtils.EMPTY;
        }, GridColumnEnum.SERVICE_FEE);
        addColumn(UsageDto::getScenarioName, GridColumnEnum.SCENARIO_NAME);
        addColumn(UsageDto::getCheckNumber, GridColumnEnum.CHECK_NUMBER);
        addColumn(usage -> CommonDateUtils.format(usage.getCheckDate(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT),
            GridColumnEnum.CHECK_DATE);
        addColumn(UsageDto::getCccEventId, GridColumnEnum.CCC_EVENT_ID);
        addColumn(UsageDto::getDistributionName, GridColumnEnum.DISTRIBUTION_NAME);
        addColumn(usage ->
                CommonDateUtils.format(usage.getDistributionDate(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT),
            GridColumnEnum.DISTRIBUTION_DATE);
        addColumn(usage -> CommonDateUtils.format(usage.getPeriodEndDate(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT),
            GridColumnEnum.PERIOD_END_DATE);
        addColumn(UsageDto::getComment, GridColumnEnum.COMMENT);
    }

    @Override
    public String initSearchMessage() {
        return "prompt.audit_search";
    }
}
