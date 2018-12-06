package com.copyright.rup.dist.foreign.service.impl.tax;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.integration.oracle.api.IOracleIntegrationService;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IRhTaxService;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

/**
 * Service to process RH taxes.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 12/05/18
 *
 * @author Uladzislau Shalamitski
 */
@Component
public class RhTaxService implements IRhTaxService {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IOracleIntegrationService oracleIntegrationService;
    @Autowired
    private IUsageRepository usageRepository;
    @Autowired
    private IUsageAuditService usageAuditService;

    @Override
    @Transactional
    public void applyRhTaxCountry(Usage usage) {
        String usageId = usage.getId();
        Long accountNumber = usage.getRightsholder().getAccountNumber();
        LOGGER.debug("Applying RH tax country. Started. UsageId={}, RhAccountNumber={}", usageId, accountNumber);
        if (oracleIntegrationService.isUsCountryCode(accountNumber)) {
            usageRepository.updateStatus(Collections.singleton(usageId), UsageStatusEnum.ELIGIBLE);
            usageAuditService.logAction(usageId, UsageActionTypeEnum.ELIGIBLE,
                "Usage has become eligible based on US rightsholder tax country");
        } else {
            usageAuditService.deleteActions(usageId);
            usageRepository.deleteById(usageId);
        }
        LOGGER.debug("Applying RH tax country. Finished. UsageId={}, RhAccountNumber={}", usageId, accountNumber);
    }
}
