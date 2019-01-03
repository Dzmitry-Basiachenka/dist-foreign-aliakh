package com.copyright.rup.dist.foreign.service.impl.tax;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.integration.oracle.api.IOracleIntegrationService;
import com.copyright.rup.dist.foreign.service.api.IRhTaxService;

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

    @Override
    public boolean isUsCountryCodeUsage(Usage usage) {
        String usageId = usage.getId();
        Long accountNumber = Objects.requireNonNull(usage.getRightsholder().getAccountNumber());
        boolean isUsTaxCountry = oracleIntegrationService.isUsCountryCode(accountNumber);
        LOGGER.debug("Processing RH tax country. UsageId={}, RhAccountNumber={}, IsUsTaxCountry={}", usageId,
            accountNumber, isUsTaxCountry);
        return isUsTaxCountry;
    }

    void setOracleIntegrationService(IOracleIntegrationService oracleIntegrationService) {
        this.oracleIntegrationService = oracleIntegrationService;
    }
}
