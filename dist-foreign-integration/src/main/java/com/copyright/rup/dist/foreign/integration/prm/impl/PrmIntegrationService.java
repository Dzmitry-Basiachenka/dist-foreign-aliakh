package com.copyright.rup.dist.foreign.integration.prm.impl;

import com.copyright.rup.dist.common.domain.Country;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.integration.rest.prm.IPrmCountryService;
import com.copyright.rup.dist.common.integration.rest.prm.IPrmPreferenceService;
import com.copyright.rup.dist.common.integration.rest.prm.IPrmRightsholderService;
import com.copyright.rup.dist.common.integration.rest.prm.IPrmRollUpService;
import com.copyright.rup.dist.common.integration.rest.prm.PrmPreferenceService;
import com.copyright.rup.dist.foreign.domain.AclIneligibleRightsholder;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIneligibleRightsholderService;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;

import com.google.common.collect.Table;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Implementation of {@link IPrmIntegrationService} for PRM system.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/21/2017
 *
 * @author Mikalai Bezmen
 */
@Service
public class PrmIntegrationService implements IPrmIntegrationService {

    @Autowired
    @Qualifier("dist.common.integration.rest.prmRightsholderAsyncService")
    private IPrmRightsholderService prmRightsholderService;
    @Autowired
    @Qualifier("dist.common.integration.rest.prmRollUpAsyncService")
    private IPrmRollUpService prmRollUpAsyncService;
    @Autowired
    @Qualifier("dist.common.integration.rest.prmRollUpService")
    private IPrmRollUpService prmRollUpService;
    @Value("$RUP{dist.foreign.rest.prm.rollups.async}")
    private boolean prmRollUpAsync;
    @Autowired
    @Qualifier("dist.common.integration.rest.prmCountryService")
    private IPrmCountryService prmCountryService;
    @Autowired
    @Qualifier("dist.common.integration.rest.prmPreferenceCacheService")
    private IPrmPreferenceService prmPreferenceService;
    @Autowired
    private IPrmIneligibleRightsholderService ineligibleRightsholderService;
    @Value("$RUP{dist.foreign.service_fee.non_participating}")
    private BigDecimal rhNonParticipatingServiceFee;
    @Value("$RUP{dist.foreign.service_fee.participating}")
    private BigDecimal rhParticipatingServiceFee;

    @Override
    public List<Rightsholder> getRightsholders(Set<Long> accountNumbers) {
        return prmRightsholderService.getRightsholders(accountNumbers);
    }

    @Override
    public Rightsholder getRightsholder(Long accountNumber) {
        return getRightsholders(Set.of(accountNumber)).stream().findFirst().orElse(null);
    }

    @Override
    public Map<String, Map<String, Rightsholder>> getRollUps(Set<String> rightsholdersIds) {
        return prmRollUpAsync
            ? prmRollUpAsyncService.getRollUps(rightsholdersIds)
            : prmRollUpService.getRollUps(rightsholdersIds);
    }

    @Override
    public Map<String, Country> getCountries() {
        return prmCountryService.getCountries();
    }

    @Override
    public boolean isRightsholderParticipating(Map<String, Table<String, String, Object>> preferencesMap,
                                               String rightsholderId, String productFamily) {
        return getBooleanPreference(preferencesMap, rightsholderId, productFamily,
            FdaConstants.IS_RH_FDA_PARTICIPATING_PREFERENCE_CODE);
    }

    @Override
    public Map<String, Table<String, String, Object>> getPreferences(Set<String> rightsholderIds) {
        return prmPreferenceService.getPreferencesMap(rightsholderIds);
    }

    @Override
    public boolean isRightsholderEligibleForNtsDistribution(String rightsholderId) {
        return !getBooleanPreference(prmPreferenceService.getPreferencesMap(Set.of(rightsholderId)),
            rightsholderId, FdaConstants.NTS_PRODUCT_FAMILY, FdaConstants.IS_RH_DIST_INELIGIBLE_CODE);
    }

    @Override
    public BigDecimal getRhParticipatingServiceFee(boolean rhParticipating) {
        return rhParticipating ? rhParticipatingServiceFee : rhNonParticipatingServiceFee;
    }

    @Override
    public boolean isStmRightsholder(String rightsholderId, String productFamily) {
        return getBooleanPreference(prmPreferenceService.getPreferencesMap(Set.of(rightsholderId)), rightsholderId,
            productFamily, FdaConstants.IS_RH_STM_IPRO_CODE);
    }

    @Override
    public boolean isRightsholderTaxBeneficialOwner(String rightsholderId, String productFamily) {
        return getBooleanPreference(prmPreferenceService.getPreferencesMap(Set.of(rightsholderId)), rightsholderId,
            productFamily, FdaConstants.TAX_BENEFICIAL_OWNER_CODE);
    }

    @Override
    public Map<String, Boolean> getStmRightsholderPreferenceMap(Set<String> rightsholdersIds, String productFamily) {
        return getBooleanPreferencesMap(prmPreferenceService.getPreferencesMap(rightsholdersIds), rightsholdersIds,
            productFamily, FdaConstants.IS_RH_STM_IPRO_CODE);
    }

    @Override
    public Set<AclIneligibleRightsholder> getIneligibleRightsholders(LocalDate periodEndDate, String licenseType) {
        return ineligibleRightsholderService.getIneligibleRightsholders(periodEndDate, licenseType);
    }

    private boolean getBooleanPreference(Map<String, Table<String, String, Object>> preferencesMap,
                                         String rightsholderId, String productFamily, String preferenceCode) {
        Boolean preferenceValue = (Boolean) ObjectUtils.defaultIfNull(
            PrmPreferenceService.getPreferenceValue(preferencesMap, rightsholderId, productFamily, preferenceCode),
            PrmPreferenceService.getPreferenceValue(preferencesMap, rightsholderId, FdaConstants.ALL_PRODUCTS_KEY,
                preferenceCode));
        if (null != preferenceValue) {
            return preferenceValue;
        } else {
            return false;
        }
    }

    private Map<String, Boolean> getBooleanPreferencesMap(Map<String, Table<String, String, Object>> preferencesMap,
                                                          Set<String> rightsholdersIds, String productFamily,
                                                          String preferenceCode) {
        return rightsholdersIds
            .stream()
            .collect(Collectors.toMap(
                Function.identity(),
                rightsholdersId -> getBooleanPreference(preferencesMap, rightsholdersId, productFamily, preferenceCode)
            ));
    }
}
