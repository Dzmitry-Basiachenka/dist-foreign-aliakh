package com.copyright.rup.dist.foreign.service.impl.tax;

import static com.google.common.base.Preconditions.checkArgument;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.domain.Country;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.RhTaxInformation;
import com.copyright.rup.dist.foreign.domain.RightsholderPayeeProductFamilyHolder;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.integration.oracle.api.IOracleRhTaxCountryService;
import com.copyright.rup.dist.foreign.integration.oracle.api.IOracleRhTaxInformationService;
import com.copyright.rup.dist.foreign.integration.oracle.api.domain.OracleRhTaxInformationRequest;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;
import com.copyright.rup.dist.foreign.service.api.IRhTaxService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioUsageService;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
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
    @Qualifier("df.integration.oracleRhTaxCountryCacheService")
    private IOracleRhTaxCountryService oracleRhTaxCountryService;
    @Autowired
    private IOracleRhTaxInformationService oracleRhTaxInformationService;
    @Autowired
    private IPrmIntegrationService prmIntegrationService;
    @Autowired
    private IUsageService usageService;
    @Autowired
    private IAclScenarioUsageService aclScenarioUsageService;

    @Override
    public void processTaxCountryCode(List<Usage> usages) {
        LogUtils.ILogWrapper usageIdsWrapper = LogUtils.ids(usages);
        Set<Long> accountNumbers = usages.stream()
            .map(usage -> Objects.requireNonNull(usage.getRightsholder().getAccountNumber()))
            .collect(Collectors.toCollection(TreeSet::new));
        LOGGER.debug("Processing RH tax country. Started. UsageIds={}, RhAccountNumbers={}", usageIdsWrapper,
            accountNumbers);
        Map<Long, Boolean> accountNumberToUsTaxCountryMap =
            oracleRhTaxCountryService.getAccountNumbersToUsTaxCountryFlags(accountNumbers);
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
    public List<RhTaxInformation> getRhTaxInformation(String productFamily, Set<String> scenarioIds, int numberOfDays) {
        checkArgument(CollectionUtils.isNotEmpty(scenarioIds));
        List<RightsholderPayeeProductFamilyHolder> holders =
            FdaConstants.ACL_PRODUCT_FAMILY.equals(productFamily)
                ? aclScenarioUsageService.getRightsholderPayeeProductFamilyHoldersByAclScenarioIds(scenarioIds)
                : usageService.getRightsholderPayeeProductFamilyHoldersByScenarioIds(scenarioIds);
        if (CollectionUtils.isNotEmpty(holders)) {
            Map<RightsholderPayeeProductFamilyHolder, Rightsholder> holderToTboMap = holders.stream()
                .collect(Collectors.toMap(Function.identity(), this::getTaxBeneficialOwner));
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
            Map<String, Country> countries = prmIntegrationService.getCountries();
            RhTaxInformationPredicate numberOfDaysPredicate = new RhTaxInformationPredicate(numberOfDays);
            List<RhTaxInformation> result = new ArrayList<>();
            holderToTboMap.forEach((holder, tbo) -> {
                Long tboAccountNumber = tbo.getAccountNumber();
                if (tboToTaxInfoMap.containsKey(tboAccountNumber)) {
                    RhTaxInformation rhTaxInfo =
                        buildRhTaxInformation(tboToTaxInfoMap.get(tboAccountNumber), holder, tbo, countries);
                    if (numberOfDaysPredicate.test(rhTaxInfo)) {
                        result.add(rhTaxInfo);
                    }
                }
            });
            result.sort(Comparator
                .comparing(RhTaxInformation::getTypeOfForm, Comparator.nullsFirst(String::compareToIgnoreCase))
                .thenComparing(RhTaxInformation::getPayeeName, Comparator.nullsFirst(String::compareToIgnoreCase))
                .thenComparing(RhTaxInformation::getRhName, Comparator.nullsFirst(String::compareToIgnoreCase))
                .thenComparing(RhTaxInformation::getProductFamily));
            return result;
        } else {
            return List.of();
        }
    }

    private Rightsholder getTaxBeneficialOwner(RightsholderPayeeProductFamilyHolder holder) {
        Rightsholder rh = holder.getRightsholder();
        Rightsholder payee = holder.getPayee();
        boolean isRhTbo = !Objects.equals(rh, payee) &&
            prmIntegrationService.isRightsholderTaxBeneficialOwner(rh.getId(), holder.getProductFamily());
        return isRhTbo ? rh : payee;
    }

    private RhTaxInformation buildRhTaxInformation(RhTaxInformation rhTaxInfo,
                                                   RightsholderPayeeProductFamilyHolder holder,
                                                   Rightsholder tbo,
                                                   Map<String, Country> countries) {
        RhTaxInformation result = new RhTaxInformation();
        result.setPayeeAccountNumber(holder.getPayee().getAccountNumber());
        result.setPayeeName(holder.getPayee().getName());
        result.setRhAccountNumber(holder.getRightsholder().getAccountNumber());
        result.setRhName(holder.getRightsholder().getName());
        result.setTboAccountNumber(tbo.getAccountNumber());
        result.setTboName(tbo.getName());
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
        String countryCode = rhTaxInfo.getCountryCode();
        result.setCountry(countries.containsKey(countryCode) ? countries.get(countryCode).getName() : countryCode);
        result.setCountryCode(countryCode);
        result.setWithHoldingIndicator(rhTaxInfo.getWithHoldingIndicator());
        result.setPayGroup(rhTaxInfo.getPayGroup());
        return result;
    }
}
