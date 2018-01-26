package com.copyright.rup.dist.foreign.integration.prm.impl;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.domain.RightsholderPreferences;
import com.copyright.rup.dist.common.integration.rest.prm.IPrmRhPreferenceService;
import com.copyright.rup.dist.common.integration.rest.prm.IPrmRightsholderService;
import com.copyright.rup.dist.common.integration.rest.prm.IPrmRollUpService;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;

import com.google.common.collect.Sets;
import com.google.common.collect.Table;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.perf4j.aop.Profiled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    /**
     * FAS Product Family.
     */
    public static final String FAS_PRODUCT_FAMILY = "FAS";

    @Autowired
    private IPrmRightsholderService prmRightsholderService;
    @Autowired
    @Qualifier("dist.common.integration.rest.prmRollUpService")
    private IPrmRollUpService prmRollUpService;
    @Autowired
    @Qualifier("dist.common.integration.rest.prmRhPreferenceService")
    private IPrmRhPreferenceService prmRhPreferenceService;
    @Value("$RUP{dist.foreign.service_fee.non_participating}")
    private BigDecimal rhNonParticipatingServiceFee;
    @Value("$RUP{dist.foreign.service_fee.participating}")
    private BigDecimal rhParticipatingServiceFee;

    @Override
    @Profiled(tag = "integration.PrmIntegrationService.getRightsholders")
    public List<Rightsholder> getRightsholders(Set<Long> accountNumbers) {
        return prmRightsholderService.getRightsholders(accountNumbers);
    }

    @Override
    public Rightsholder getRightsholder(Long accountNumber) {
        List<Rightsholder> rightsholders = getRightsholders(Sets.newHashSet(accountNumber));
        return !rightsholders.isEmpty() ? rightsholders.get(0) : null;
    }

    @Override
    @Profiled(tag = "integration.PrmIntegrationService.getRollUps")
    public Table<String, String, Long> getRollUps(Collection<String> rightsholdersIds) {
        return prmRollUpService.getRollUps(rightsholdersIds);
    }

    @Override
    @Profiled(tag = "integration.PrmIntegrationService.isRightsholderParticipating")
    public boolean isRightsholderParticipating(Long accountNumber) {
        boolean rhParticipatingFlag = false;
        Map<String, RightsholderPreferences> preferencesMap =
            prmRhPreferenceService.getRightsholderPreferences(accountNumber);
        if (MapUtils.isNotEmpty(preferencesMap)) {
            RightsholderPreferences preferences = ObjectUtils.defaultIfNull(preferencesMap.get(FAS_PRODUCT_FAMILY),
                preferencesMap.get(RightsholderPreferences.ALL_PRODUCTS_KEY));
            if (null != preferences && null != preferences.isRhParticipating()) {
                rhParticipatingFlag = preferences.isRhParticipating();
            }
        }
        return rhParticipatingFlag;
    }

    @Override
    public BigDecimal getRhParticipatingServiceFee(boolean rhParticipatingFlag) {
        return rhParticipatingFlag ? rhParticipatingServiceFee : rhNonParticipatingServiceFee;
    }
}
