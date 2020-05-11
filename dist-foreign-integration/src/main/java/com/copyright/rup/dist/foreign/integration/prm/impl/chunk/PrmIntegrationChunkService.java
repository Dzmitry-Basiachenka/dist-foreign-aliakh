package com.copyright.rup.dist.foreign.integration.prm.impl.chunk;

import com.copyright.rup.dist.common.integration.rest.prm.IPrmPreferenceService;
import com.copyright.rup.dist.common.integration.rest.prm.PrmPreferenceService;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.integration.prm.api.chunk.IPrmIntegrationChunkService;

import com.google.common.collect.Table;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Implementation of {@link IPrmIntegrationChunkService} for PRM system.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/21/2017
 *
 * @author Mikalai Bezmen
 * @author Aliaksandr Liakh
 */
@Service
public class PrmIntegrationChunkService implements IPrmIntegrationChunkService {

    @Autowired
    @Qualifier("dist.common.integration.rest.prmPreferenceCacheService")
    private IPrmPreferenceService prmPreferenceService;

    @Override
    public Map<String, Boolean> getStmRightsholderPreferenceMap(Set<String> rightsholdersIds, String productFamily) {
        return getBooleanPreferencesMap(
            prmPreferenceService.getPreferencesMap(rightsholdersIds), rightsholdersIds,
            productFamily, FdaConstants.IS_RH_STM_IPRO_CODE);
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
