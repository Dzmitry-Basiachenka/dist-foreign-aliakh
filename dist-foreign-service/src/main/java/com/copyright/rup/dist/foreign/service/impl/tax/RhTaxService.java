package com.copyright.rup.dist.foreign.service.impl.tax;

import static com.google.common.base.Preconditions.checkArgument;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.RhTaxInformation;
import com.copyright.rup.dist.foreign.domain.RightsholderPayeeProductFamilyHolder;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.integration.oracle.api.IOracleRhTaxCountryChunkService;
import com.copyright.rup.dist.foreign.integration.oracle.api.IOracleRhTaxCountryService;
import com.copyright.rup.dist.foreign.integration.oracle.api.IOracleRhTaxInformationService;
import com.copyright.rup.dist.foreign.integration.oracle.api.domain.OracleRhTaxInformationRequest;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;
import com.copyright.rup.dist.foreign.service.api.IRhTaxService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
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
    @Qualifier("df.integration.oracleRhTaxCountryChunkCacheService")
    private IOracleRhTaxCountryChunkService oracleRhTaxCountryChunkService;
    @Autowired
    @Qualifier("df.integration.oracleRhTaxCountryCacheService")
    private IOracleRhTaxCountryService oracleRhTaxCountryService;
    @Autowired
    private IOracleRhTaxInformationService oracleRhTaxInformationService;
    @Autowired
    private IPrmIntegrationService prmIntegrationService;
    @Autowired
    private IUsageService usageService;

    @Override
    public void processTaxCountryCode(Usage usage) {
        String usageId = usage.getId();
        Long accountNumber = Objects.requireNonNull(usage.getRightsholder().getAccountNumber());
        LOGGER.debug("Processing RH tax country. Started. UsageId={}, RhAccountNumber={}", usageId, accountNumber);
        boolean isUsTaxCountry = oracleRhTaxCountryService.isUsTaxCountry(accountNumber);
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
            .collect(Collectors.toCollection(TreeSet::new));
        LOGGER.debug("Processing RH tax country. Started. UsageIds={}, RhAccountNumbers={}", usageIdsWrapper,
            accountNumbers);
        Map<Long, Boolean> accountNumberToUsTaxCountryMap =
            oracleRhTaxCountryChunkService.isUsTaxCountry(accountNumbers);
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

    @Override
    public List<RhTaxInformation> getRhTaxInformation(Set<String> scenarioIds, int numberOfDays) {
        checkArgument(CollectionUtils.isNotEmpty(scenarioIds));
        List<RhTaxInformation> result;
        List<RightsholderPayeeProductFamilyHolder> rhPayeeProductHolders =
            usageService.getRightsholderPayeeProductFamilyHoldersByScenarioIds(scenarioIds);
        if (CollectionUtils.isNotEmpty(rhPayeeProductHolders)) {
            Map<RightsholderPayeeProductFamilyHolder, Rightsholder> holderToTboMap = rhPayeeProductHolders.stream()
                .collect(Collectors.toMap(holder -> holder, this::getTaxBeneficialOwner));
            Set<OracleRhTaxInformationRequest> oracleRequests = holderToTboMap.entrySet().stream()
                .map(entry -> {
                    RightsholderPayeeProductFamilyHolder holder = entry.getKey();
                    Rightsholder tbo = entry.getValue();
                    return new OracleRhTaxInformationRequest(
                        holder.getPayee().getAccountNumber(), tbo.getAccountNumber());
                })
                .collect(Collectors.toSet());
            Map<Long, RhTaxInformation> tboToTaxInfoMap =
                oracleRhTaxInformationService.getRhTaxInformation(oracleRequests);
            result = holderToTboMap.entrySet().stream()
                .map(entry -> {
                    RightsholderPayeeProductFamilyHolder holder = entry.getKey();
                    Rightsholder tbo = entry.getValue();
                    return buildRhTaxInformation(tboToTaxInfoMap.get(tbo.getAccountNumber()), holder);
                })
                .filter(new RhTaxInformationPredicate(numberOfDays))
                .collect(Collectors.toList());
        } else {
            result = Collections.emptyList();
        }
        return result;
    }

    private Rightsholder getTaxBeneficialOwner(RightsholderPayeeProductFamilyHolder rhPayeeProductHolder) {
        Rightsholder rh = rhPayeeProductHolder.getRightsholder();
        Rightsholder payee = rhPayeeProductHolder.getPayee();
        boolean isRhTbo = !Objects.equals(rh, payee) &&
            prmIntegrationService.isRightsholderTaxBeneficialOwner(rh.getId(), rhPayeeProductHolder.getProductFamily());
        return isRhTbo ? rh : payee;
    }

    private RhTaxInformation buildRhTaxInformation(RhTaxInformation rhTaxInfo,
                                                   RightsholderPayeeProductFamilyHolder holder) {
        RhTaxInformation result = new RhTaxInformation();
        result.setPayeeAccountNumber(holder.getPayee().getAccountNumber());
        result.setPayeeName(holder.getPayee().getName());
        result.setRorAccountNumber(holder.getRightsholder().getAccountNumber());
        result.setRorName(holder.getRightsholder().getName());
        result.setTboAccountNumber(rhTaxInfo.getTboAccountNumber());
        result.setTboName(rhTaxInfo.getTboName());
        result.setProductFamily(holder.getProductFamily());
        result.setTaxOnFile(rhTaxInfo.getTaxOnFile());
        result.setNotificationsSent(rhTaxInfo.getNotificationsSent());
        result.setDateOfLastNotificationSent(rhTaxInfo.getDateOfLastNotificationSent());
        result.setTypeOfForm(rhTaxInfo.getTypeOfForm());
        result.setAddressLine1(rhTaxInfo.getAddressLine1());
        result.setAddressLine2(rhTaxInfo.getAddressLine2());
        result.setAddressLine3(rhTaxInfo.getAddressLine3());
        result.setAddressLine4(rhTaxInfo.getAddressLine4());
        result.setCity(rhTaxInfo.getCity());
        result.setState(rhTaxInfo.getState());
        result.setZip(rhTaxInfo.getZip());
        result.setCountryCode(rhTaxInfo.getCountryCode());
        result.setCountry(rhTaxInfo.getCountry());
        result.setWithHoldingIndicator(rhTaxInfo.getWithHoldingIndicator());
        result.setPayGroup(rhTaxInfo.getPayGroup());
        return result;
    }
}
