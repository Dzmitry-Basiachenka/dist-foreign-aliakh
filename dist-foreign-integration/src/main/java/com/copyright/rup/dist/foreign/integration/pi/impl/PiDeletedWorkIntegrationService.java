package com.copyright.rup.dist.foreign.integration.pi.impl;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.foreign.integration.pi.api.IPiDeletedWorkIntegrationService;
import com.copyright.rup.es.api.RupEsApi;
import com.copyright.rup.es.api.RupEsApiRuntimeException;
import com.copyright.rup.es.api.RupQueryStringQueryBuilder;
import com.copyright.rup.es.api.RupSearchResponse;
import com.copyright.rup.es.api.request.RupSearchRequest;

import org.opensearch.OpenSearchException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

    @Value("$RUP{dist.foreign.pi.index.del}")
    private String piIndex;
    @Autowired
    private RupEsApi rupEsApi;

    @Override
    public boolean isDeletedWork(Long value) {
        return !doSearch(value).getResults().getHits().isEmpty();
    }

    private RupSearchResponse doSearch(Long value) {
        RupSearchRequest request = RupSearchRequest.of(piIndex);
        RupQueryStringQueryBuilder builder = RupQueryStringQueryBuilder.of(String.format("wrWrkInst:%s", value));
        request.setQueryBuilder(builder);
        request.setSearchType(RupSearchRequest.RupSearchType.DFS_QUERY_AND_FETCH);
        request.setFetchSource(true);
        LOGGER.debug("Search deleted work. ParameterValue={}", value);
        try {
            return rupEsApi.search(request);
        } catch (RupEsApiRuntimeException | OpenSearchException e) {
            throw new RupRuntimeException("Unable to connect to RupEsApi", e);
        }
    }
}
