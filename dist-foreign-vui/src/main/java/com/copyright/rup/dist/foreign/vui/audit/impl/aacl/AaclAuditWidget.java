package com.copyright.rup.dist.foreign.vui.audit.impl.aacl;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.vui.audit.api.aacl.IAaclAuditController;
import com.copyright.rup.dist.foreign.vui.audit.api.aacl.IAaclAuditWidget;
import com.copyright.rup.dist.foreign.vui.audit.impl.CommonAuditWidget;
import com.copyright.rup.dist.foreign.vui.common.utils.GridColumnEnum;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;

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
    public String initSearchMessage() {
        return "prompt.audit_search_aacl_sal";
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
        addColumn(usage -> usage.getAaclUsage().getBaselineId(), GridColumnEnum.BASELINE_ID);
        addColumn(UsageDto::getStatus, GridColumnEnum.STATUS);
        addColumn(UsageDto::getProductFamily, GridColumnEnum.PRODUCT_FAMILY);
        addColumn(UsageDto::getBatchName, GridColumnEnum.BATCH_NAME);
        addColumn(usage -> CommonDateUtils.format(usage.getAaclUsage().getBatchPeriodEndDate(),
            RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT), GridColumnEnum.BATCH_PERIOD_END_DATE);
        addColumn(UsageDto::getRhAccountNumber, GridColumnEnum.RH_ACCOUNT_NUMBER);
        addColumn(UsageDto::getRhName, GridColumnEnum.RH_NAME);
        addColumn(UsageDto::getPayeeAccountNumber, GridColumnEnum.PAYEE_ACCOUNT_NUMBER);
        addColumn(UsageDto::getPayeeName, GridColumnEnum.PAYEE_NAME);
        addColumn(UsageDto::getWrWrkInst, GridColumnEnum.WR_WRK_INST);
        addColumn(UsageDto::getSystemTitle, GridColumnEnum.SYSTEM_TITLE);
        addColumn(UsageDto::getStandardNumber, GridColumnEnum.STANDARD_NUMBER);
        addColumn(UsageDto::getStandardNumberType, GridColumnEnum.STANDARD_NUMBER_TYPE);
        addAmountColumn(UsageDto::getGrossAmount, GridColumnEnum.GROSS_AMOUNT_IN_USD);
        addServiceFeeColumn();
        addAmountColumn(UsageDto::getNetAmount, GridColumnEnum.NET_AMOUNT);
        addColumn(UsageDto::getScenarioName, GridColumnEnum.SCENARIO_NAME);
        addColumn(UsageDto::getCheckNumber, GridColumnEnum.CHECK_NUMBER);
        addColumn(usage -> CommonDateUtils.format(usage.getCheckDate(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT),
            GridColumnEnum.CHECK_DATE);
        addColumn(UsageDto::getCccEventId, GridColumnEnum.CCC_EVENT_ID);
        addColumn(UsageDto::getDistributionName, GridColumnEnum.DISTRIBUTION_NAME);
        addColumn(usage ->
                CommonDateUtils.format(usage.getDistributionDate(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT),
            GridColumnEnum.DISTRIBUTION_DATE);
        addColumn(usage -> usage.getAaclUsage().getDetailLicenseeClass().getId(), GridColumnEnum.DET_LC_ID);
        addColumn(usage -> usage.getAaclUsage().getDetailLicenseeClass().getEnrollmentProfile(),
            GridColumnEnum.DET_LC_ENROLLMENT);
        addColumn(usage -> usage.getAaclUsage().getDetailLicenseeClass().getDiscipline(),
            GridColumnEnum.DET_LC_DISCIPLINE);
        addColumn(usage -> usage.getAaclUsage().getPublicationType().getName(), GridColumnEnum.PUB_TYPE);
        addColumn(usage -> usage.getAaclUsage().getUsageAge().getPeriod(), GridColumnEnum.PERIOD);
        addColumn(usage -> usage.getAaclUsage().getUsageSource(), GridColumnEnum.USAGE_SOURCE);
        addColumn(UsageDto::getComment, GridColumnEnum.COMMENT);
    }
}
