package com.copyright.rup.dist.foreign.service.impl.tax;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.integration.oracle.api.IOracleIntegrationService;
import com.copyright.rup.dist.foreign.service.api.IRhTaxService;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;

import com.copyright.rup.dist.foreign.service.api.IUsageService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

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
    private IUsageService usageService;
    @Autowired
    private IUsageAuditService usageAuditService;

    @Override
    public void processTaxCountryCode(Usage usage) {
        String usageId = usage.getId();
        Long accountNumber = Objects.requireNonNull(usage.getRightsholder().getAccountNumber());
        LOGGER.debug("Processing RH tax country. Started. UsageId={}, RhAccountNumber={}", usageId, accountNumber);
        boolean isUsTaxCountry = oracleIntegrationService.isUsCountryCode(accountNumber);
        if (isUsTaxCountry) {
            usage.setStatus(UsageStatusEnum.US_TAX_COUNTRY);
            usageService.updateProcessedUsage(usage);
            usageAuditService.logAction(usageId, UsageActionTypeEnum.US_TAX_COUNTRY, "Rightsholder tax country is US");
        }
        LOGGER.debug("Processing RH tax country. Finished. UsageId={}, RhAccountNumber={}, IsUsTaxCountry={}", usageId,
            accountNumber, isUsTaxCountry);
    }

    void setOracleIntegrationService(IOracleIntegrationService oracleIntegrationService) {
        this.oracleIntegrationService = oracleIntegrationService;
    }

    void setUsageService(IUsageService usageService) {
        this.usageService = usageService;
    }

    void setUsageAuditService(IUsageAuditService usageAuditService) {
        this.usageAuditService = usageAuditService;
    }
}
