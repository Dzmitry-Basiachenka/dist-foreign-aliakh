package com.copyright.rup.dist.foreign.integration.pi.impl;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.foreign.integration.pi.api.IPiIntegrationService;
import com.copyright.rup.es.api.RupEsApi;
import com.copyright.rup.es.api.RupEsApiRuntimeException;
import com.copyright.rup.es.api.RupQueryStringQueryBuilder;
import com.copyright.rup.es.api.RupResponseBase;
import com.copyright.rup.es.api.RupSearchResponse;
import com.copyright.rup.es.api.domain.RupSearchHit;
import com.copyright.rup.es.api.request.RupSearchRequest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.elasticsearch.ElasticsearchException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

/**
 * This service allows searching works in Published Inventory with help of RUP ES API.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 02/09/2018
 *
 * @author Aliaksandr Radkevich
 */
@Service
public class PiIntegrationService implements IPiIntegrationService {

    private static final int EXPECTED_SEARCH_HITS_COUNT = 1;
    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Value("$RUP{dist.foreign.integration.works.cluster}")
    private String cluster;
    @Value("$RUP{dist.foreign.integration.works.node}")
    private String node;
    @Value("$RUP{dist.foreign.integration.works.pi.index}")
    private String piIndex;

    private ObjectMapper mapper;
    private RupEsApi rupEsApi;

    @Override
    public Map<String, Long> findWrWrkInstsByIdnos(Map<String, String> idnoToTitleMap) {
        return matchByIdno(idnoToTitleMap);
    }

    @Override
    public Map<String, Long> findWrWrkInstsByTitles(Set<String> titles) {
        return matchByTitle(titles);
    }

    /**
     * @return an instance of {@link RupEsApi}.
     */
    protected RupEsApi getRupEsApi() {
        if (null == rupEsApi) {
            rupEsApi = RupEsApi.of(cluster, node);
        }
        return rupEsApi;
    }

    /**
     * Initializes search API.
     */
    @PostConstruct
    void init() {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private Map<String, Long> matchByIdno(Map<String, String> idnoToTitleMap) {
        Map<String, Long> result = new HashMap<>();
        idnoToTitleMap.forEach((idno, title) -> {
            RupSearchResponse searchMainResponse = doSearch("idno", idno);
            if (Objects.nonNull(searchMainResponse)
                && RupResponseBase.Status.SUCCESS == searchMainResponse.getStatus()) {
                List<RupSearchHit> searchHits = searchMainResponse.getResults().getHits();
                if (CollectionUtils.isNotEmpty(searchHits)) {
                    if (EXPECTED_SEARCH_HITS_COUNT == searchHits.size()) {
                        Long wrWrkInst = getWrWrkInstFromSearchHit(searchHits, idno, title);
                        LOGGER.trace("Search works. By IDNO. IDNO={}, Title={}, WrWrkInst={}, Hits={}", idno, title,
                            wrWrkInst, searchHits);
                        result.put(idno, wrWrkInst);
                    } else {
                        List<RupSearchHit> filteredByTitleHits = searchHits.stream()
                            .filter(searchHit -> {
                                List<Object> mainTitles = searchHit.getFields().get("mainTitle");
                                return CollectionUtils.isNotEmpty(mainTitles)
                                    && StringUtils.equalsIgnoreCase(title, mainTitles.get(0).toString());
                            }).collect(Collectors.toList());
                        if (EXPECTED_SEARCH_HITS_COUNT == filteredByTitleHits.size()) {
                            Long wrWrkInst = getWrWrkInstFromSearchHit(filteredByTitleHits, idno, title);
                            LOGGER.trace("Search works. By IDNO and Title. IDNO={}, Title={}, WrWrkInst={}, Hits={}",
                                idno, title, wrWrkInst, searchHits);
                            result.put(idno, wrWrkInst);
                        } else {
                            LOGGER.debug(
                                "Search works. By IDNO and Title. IDNO={}, Title={}, WrWrkInst=MultiResults, Hits={}",
                                idno, title,
                                searchHits.stream().map(RupSearchHit::getSource).collect(Collectors.joining(";")));
                        }
                    }
                } else {
                    LOGGER.debug("Search works. By IDNO and Title. IDNO={}, Title={}, WrWrkInst=Not Found, Hits=Empty",
                        idno, title);
                }
            } else {
                throw new RupRuntimeException("Search works failed due to request did not succeed");
            }
        });
        return result;
    }

    private Long getWrWrkInstFromSearchHit(List<RupSearchHit> searchHits, String idno, String title) {
        Long wrWrkInst;
        try {
            wrWrkInst = mapper.readValue(searchHits.iterator().next().getSource(), Work.class).getWrWrkInst();
        } catch (IOException e) {
            throw new RupRuntimeException(
                String.format("Search works. Failed. IDNO=%s, Title=%s, Reason=Could read response", idno, title), e);
        }
        return wrWrkInst;
    }

    private Map<String, Long> matchByTitle(Set<String> titles) {
        Map<String, Long> result = new HashMap<>();
        titles.forEach(title -> {
            RupSearchResponse searchResponse = doSearch("mainTitle", title);
            if (Objects.nonNull(searchResponse) && RupResponseBase.Status.SUCCESS == searchResponse.getStatus()) {
                List<RupSearchHit> searchHits = searchResponse.getResults().getHits();
                if (CollectionUtils.isNotEmpty(searchHits)) {
                    Long wrWrkInst = getWrWrkInstByTitle(title, searchHits);
                    if (Objects.nonNull(wrWrkInst)) {
                        result.put(title, wrWrkInst);
                    }
                }
            } else {
                throw new RupRuntimeException("Search works failed due to request did not succeed");
            }
        });
        return result;
    }

    private Long getWrWrkInstByTitle(String title, List<RupSearchHit> hits) {
        Long wrWrkInst = null;
        if (EXPECTED_SEARCH_HITS_COUNT == hits.size()) {
            RupSearchHit rupSearchHit = hits.iterator().next();
            if (Objects.nonNull(rupSearchHit)) {
                try {
                    wrWrkInst = mapper.readValue(rupSearchHit.getSource(), Work.class).getWrWrkInst();
                } catch (IOException e) {
                    throw new RupRuntimeException(
                        String.format("Search works. Failed. Title=%s, Reason=Could read response", title), e);
                }
            }
        }
        return wrWrkInst;
    }

    private RupSearchResponse doSearch(String field, String value) {
        RupSearchResponse searchResponse = null;
        String queryString = buildQueryString(field, value);
        if (StringUtils.isNotEmpty(queryString)) {
            RupSearchRequest request = RupSearchRequest.of(piIndex);
            RupQueryStringQueryBuilder builder = RupQueryStringQueryBuilder.of(queryString);
            request.setQueryBuilder(builder);
            request.setTypes("work");
            request.setSearchType(RupSearchRequest.RupSearchType.DFS_QUERY_AND_FETCH);
            request.setFields("idno", "mainTitle");
            request.setFetchSource(true);
            try {
                searchResponse = getRupEsApi().search(request);
            } catch (RupEsApiRuntimeException | ElasticsearchException e) {
                throw new RupRuntimeException("Unable to connect to RupEsApi", e);
            }
        }
        return searchResponse;
    }

    private String buildQueryString(String field, String value) {
        StringBuilder builder = new StringBuilder();
        append(builder, field, value);
        return builder.toString();
    }

    private void append(StringBuilder builder, String field, String value) {
        appendValue(builder, field, value);
    }

    private void appendValue(StringBuilder builder, String field, String value) {
        builder.append(field)
            .append(":\"")
            .append(value)
            .append('"');
    }

    private static class Work {

        private Long wrWrkInst;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Work that = (Work) o;
            return new EqualsBuilder()
                .append(this.wrWrkInst, that.wrWrkInst)
                .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder()
                .append(wrWrkInst)
                .toHashCode();
        }

        private Long getWrWrkInst() {
            return wrWrkInst;
        }

        public void setWrWrkInst(Long wrWrkInst) {
            this.wrWrkInst = wrWrkInst;
        }
    }
}
