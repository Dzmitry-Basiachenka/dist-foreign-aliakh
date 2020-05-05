package com.copyright.rup.dist.foreign.service.impl.tax;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.integration.oracle.api.IOracleRhTaxChunkService;
import com.copyright.rup.dist.foreign.integration.oracle.api.IOracleRhTaxService;
import com.copyright.rup.dist.foreign.service.api.IRhTaxService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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
    @Qualifier("df.integration.oracleRhTaxChunkCacheService")
    private IOracleRhTaxChunkService oracleRhTaxChunkService;
    @Autowired
    @Qualifier("df.integration.oracleRhTaxCacheService")
    private IOracleRhTaxService oracleRhTaxService;
    @Autowired
    private IUsageService usageService;

    @Override
    public void processTaxCountryCode(Usage usage) {
        String usageId = usage.getId();
        Long accountNumber = Objects.requireNonNull(usage.getRightsholder().getAccountNumber());
        LOGGER.debug("Processing RH tax country. Started. UsageId={}, RhAccountNumber={}", usageId, accountNumber);
        boolean isUsTaxCountry = oracleRhTaxService.isUsTaxCountry(accountNumber);
        if (isUsTaxCountry) {
            usage.setStatus(UsageStatusEnum.US_TAX_COUNTRY);
            usageService.updateProcessedUsage(usage);
        }
        LOGGER.debug("Processing RH tax country. Finished. UsageId={}, RhAccountNumber={}, IsUsTaxCountry={}", usageId,
            accountNumber, isUsTaxCountry);
    }

    @Override
    public void processTaxCountryCode(List<Usage> usages) {
        LogUtils.ILogWrapper usageIdsWrapper = LogUtils.ids(usages);
        Set<Long> accountNumbers = usages.stream()
            .map(usage -> Objects.requireNonNull(usage.getRightsholder().getAccountNumber()))
            .collect(Collectors.toSet());
        LOGGER.debug("Processing RH tax country. Started. UsageIds={}, RhAccountNumbers={}", usageIdsWrapper,
            accountNumbers);
        Map<Long, Boolean> accountNumberToUsTaxCountryMap = oracleRhTaxChunkService.isUsTaxCountry(accountNumbers);
        accountNumberToUsTaxCountryMap.entrySet().stream()
            .filter(Map.Entry::getValue)
            .forEach(entry -> {
                usages.stream()
                    .filter(usage -> usage.getRightsholder().getAccountNumber().equals(entry.getKey()))
                    .forEach(usage -> {
                        usage.setStatus(UsageStatusEnum.US_TAX_COUNTRY);
                        usageService.updateProcessedUsage(usage);
                    });
            });
        LOGGER.debug("Processing RH tax country. Finished. UsageIds={}, RhAccountNumbers={}", usageIdsWrapper,
            accountNumbers);
    }
}
