package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * Implementation of {@link AbstractUsageChainProcessor} to mark usages as {@link UsageStatusEnum#NTS_WITHDRAWN}
 * or {@link UsageStatusEnum#RH_NOT_FOUND} base on sum of usages gross amount grouped by Wr Wrk Inst.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 05/22/2019
 *
 * @author Ihar Suvorau
 */
public class RhNotFoundProcessor extends AbstractUsageJobProcessor {

    private static final BigDecimal GROSS_AMOUNT_LIMIT = new BigDecimal("100.00");
    private static final String AUDIT_MESSAGE =
        "Detail was made eligible for NTS because sum of gross amounts, grouped by Wr Wrk Inst, is less than $100";
    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IUsageService usageService;
    @Autowired
    private IUsageAuditService auditService;

    @Override
    public void process(Usage usage) {
        LOGGER.trace("Usage RhNotFound processor. Started. UsageId={}", usage.getId());
        BigDecimal totalGrossAmount =
            usageService.getTotalAmountByWrWrkInstAndBatchId(usage.getWrWrkInst(), usage.getBatchId());
        if (GROSS_AMOUNT_LIMIT.compareTo(totalGrossAmount) > 0) {
            usage.setStatus(UsageStatusEnum.NTS_WITHDRAWN);
            usage.setProductFamily(FdaConstants.NTS_PRODUCT_FAMILY);
            usageService.updateProcessedUsage(usage);
            auditService.logAction(usage.getId(), UsageActionTypeEnum.ELIGIBLE_FOR_NTS, AUDIT_MESSAGE);
        }
        LOGGER.trace("Usage RhNotFound processor. Finished. UsageId={}, WrWrkInst={}, TotalAmount={}", usage.getId(),
            usage.getWrWrkInst(), totalGrossAmount);
    }

    @Override
    public ChainProcessorTypeEnum getChainProcessorType() {
        return ChainProcessorTypeEnum.RH_NOT_FOUND;
    }
}
