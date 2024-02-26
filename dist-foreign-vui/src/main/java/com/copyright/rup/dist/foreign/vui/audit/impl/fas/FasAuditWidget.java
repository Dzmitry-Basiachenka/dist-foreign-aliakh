package com.copyright.rup.dist.foreign.vui.audit.impl.fas;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.vui.audit.api.fas.IFasAuditController;
import com.copyright.rup.dist.foreign.vui.audit.api.fas.IFasAuditWidget;
import com.copyright.rup.dist.foreign.vui.audit.impl.CommonAuditWidget;
import com.copyright.rup.dist.foreign.vui.common.utils.GridColumnEnum;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;

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
        addColumn(UsageDto::getWorkTitle, GridColumnEnum.REPORTED_TITLE);
        addColumn(usageDto -> Objects.nonNull(usageDto.getFasUsage())
            ? usageDto.getFasUsage().getReportedStandardNumber() : null, GridColumnEnum.REPORTED_STANDARD_NUMBER);
        addColumn(UsageDto::getStandardNumber, GridColumnEnum.STANDARD_NUMBER);
        addColumn(UsageDto::getStandardNumberType, GridColumnEnum.STANDARD_NUMBER_TYPE);
        addAmountColumn(UsageDto::getReportedValue, GridColumnEnum.REPORTED_VALUE);
        addAmountColumn(UsageDto::getGrossAmount, GridColumnEnum.GROSS_AMOUNT);
        addAmountColumn(UsageDto::getBatchGrossAmount, GridColumnEnum.BATCH_GROSS_AMOUNT);
        addServiceFeeColumn();
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
