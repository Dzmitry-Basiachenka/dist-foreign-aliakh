package com.copyright.rup.dist.foreign.integration.pi.impl;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.foreign.integration.pi.api.IPiDeletedWorkIntegrationService;
import com.copyright.rup.es.api.RupEsApi;
import com.copyright.rup.es.api.RupEsApiRuntimeException;
import com.copyright.rup.es.api.RupQueryStringQueryBuilder;
import com.copyright.rup.es.api.RupSearchResponse;
import com.copyright.rup.es.api.request.RupSearchRequest;

import com.google.common.collect.Iterables;

import org.opensearch.OpenSearchException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.PreDestroy;

/**
 * This service allows searching soft deleted works in Published Inventory.
 * <p/>
 * Copyright (C) 2023 copyright.com
 * <p/>
 * Date: 03/06/2023
 *
 * @author Mikita Maistrenka
 */
@Service("df.integration.piDeletedWorkIntegrationService")
public class PiDeletedWorkIntegrationService implements IPiDeletedWorkIntegrationService {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Value("$RUP{dist.foreign.pi.cluster}")
    private String cluster;
    @Value("$RUP{dist.foreign.pi.nodes}")
    private List<String> nodes;
    @Value("$RUP{dist.foreign.pi.index.del}")
    private String piIndex;
    @Value("$RUP{dist.foreign.search.ldap.username}")
    private String username;
    @Value("$RUP{dist.foreign.search.ldap.password}")
    private String password;

    private RupEsApi rupEsApi;

    @Override
    public boolean isDeletedWork(Long value) {
        return !doSearch(value).getResults().getHits().isEmpty();
    }

    /**
     * @return an instance of {@link RupEsApi}.
     */
    protected RupEsApi getRupEsApi() {
        if (Objects.isNull(rupEsApi)) {
            Map<String, String> credentialsMap = Map.of(RupEsApi.USERNAME, username, RupEsApi.PASSWORD, password);
            rupEsApi = RupEsApi.of(cluster, credentialsMap, Iterables.toArray(nodes, String.class));
        }
        return rupEsApi;
    }

    /**
     * Closes search API.
     */
    @PreDestroy
    void destroy() {
        if (Objects.nonNull(rupEsApi)) {
            rupEsApi.closeConnection();
        }
    }

    private RupSearchResponse doSearch(Long value) {
        RupSearchRequest request = RupSearchRequest.of(piIndex);
        RupQueryStringQueryBuilder builder = RupQueryStringQueryBuilder.of(String.format("wrWrkInst:%s", value));
        request.setQueryBuilder(builder);
        request.setSearchType(RupSearchRequest.RupSearchType.DFS_QUERY_AND_FETCH);
        request.setFetchSource(true);
        LOGGER.debug("Search deleted work. ParameterValue={}", value);
        try {
            return getRupEsApi().search(request);
        } catch (RupEsApiRuntimeException | OpenSearchException e) {
            throw new RupRuntimeException("Unable to connect to RupEsApi", e);
        }
    }
}
