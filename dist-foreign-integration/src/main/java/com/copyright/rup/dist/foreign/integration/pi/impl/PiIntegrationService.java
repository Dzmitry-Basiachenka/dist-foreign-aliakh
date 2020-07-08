package com.copyright.rup.dist.foreign.integration.pi.impl;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.foreign.domain.Work;
import com.copyright.rup.dist.foreign.integration.pi.api.IPiIntegrationService;
import com.copyright.rup.es.api.RupEsApi;
import com.copyright.rup.es.api.RupEsApiRuntimeException;
import com.copyright.rup.es.api.RupQueryStringQueryBuilder;
import com.copyright.rup.es.api.RupSearchResponse;
import com.copyright.rup.es.api.domain.RupSearchHit;
import com.copyright.rup.es.api.request.RupSearchRequest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Iterables;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.elasticsearch.ElasticsearchException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * This service allows searching works in Published Inventory with help of RUP ES API.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 02/09/2018
 *
 * @author Aliaksandr Radkevich
 */
@Service("df.integration.piIntegrationService")
public class PiIntegrationService implements IPiIntegrationService {

    private static final int EXPECTED_SEARCH_HITS_COUNT = 1;
    private static final int EXPECTED_HOST_INDOS_COUNT = 1;
    private static final String MAIN_TITLE = "mainTitle";
    private static final String HOST_IDNO = "hostIdno";
    private static final String IDNO = "IDNO";
    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Value("$RUP{dist.foreign.pi.cluster}")
    private String cluster;
    @Value("$RUP{dist.foreign.pi.nodes}")
    private List<String> nodes;
    @Value("$RUP{dist.foreign.pi.index}")
    private String piIndex;

    private ObjectMapper mapper;
    private RupEsApi rupEsApi;

    @Override
    public Work findWorkByWrWrkInst(Long wrWrkInst) {
        return ObjectUtils.defaultIfNull(findByWrWrkInst(wrWrkInst), new Work());
    }

    @Override
    public Work findWorkByIdnoAndTitle(String idno, String title) {
        return findWorkByIdnoAndTitle(idno, title, false);
    }

    @Override
    public Work findWorkByTitle(String title) {
        Work result = new Work();
        RupSearchResponse searchResponse = doSearch(MAIN_TITLE, title);
        List<RupSearchHit> searchHits = searchResponse.getResults().getHits();
        if (CollectionUtils.isNotEmpty(searchHits) && EXPECTED_SEARCH_HITS_COUNT == searchHits.size()) {
            result = searchByHostIdnoIfExists(searchHits, MAIN_TITLE, title, false);
        } else if (CollectionUtils.isEmpty(searchHits)) {
            result.setMultipleMatches(false);
            LOGGER.debug("Search works. By MainTitle. Title={}, WWrWrkInst=Not Found, Hits=Empty", title);
        } else {
            result.setMultipleMatches(true);
            LOGGER.debug("Search works. By MainTitle. Title={}, WrWrkInst=MultiResults, Hits={}",
                title, searchHits.stream().map(RupSearchHit::getSource).collect(Collectors.joining(";")));
        }
        return result;
    }

    /**
     * @return an instance of {@link RupEsApi}.
     */
    protected RupEsApi getRupEsApi() {
        if (Objects.isNull(rupEsApi)) {
            rupEsApi = RupEsApi.of(cluster, Iterables.toArray(nodes, String.class));
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

    /**
     * Closes search API.
     */
    @PreDestroy
    void destroy() {
        if (Objects.nonNull(rupEsApi)) {
            rupEsApi.closeConnection();
        }
    }

    /**
     * Builds query string based on field to search and value.
     *
     * @param field field to search
     * @param value value
     * @return query string
     */
    String buildQueryString(String field, Object value) {
        return String.format("%s:\"%s\"", field, value instanceof String
            ? QueryParser.escape(((String) value).trim())
            : value);
    }

    private Work findWorkByIdnoAndTitle(String idno, String title, boolean isHostIdno) {
        Work result = findWork("issn", idno, isHostIdno);
        if (Objects.nonNull(result)) {
            return result;
        }
        result = findWork("isbn10", idno, isHostIdno);
        if (Objects.nonNull(result)) {
            return result;
        }
        result = findWork("isbn13", idno, isHostIdno);
        if (Objects.nonNull(result)) {
            return result;
        }
        return matchByIdnoAndTitle(idno, title, isHostIdno);
    }

    private Work matchByIdnoAndTitle(String idno, String title, boolean isHostIdno) {
        List<RupSearchHit> searchHits = doSearch("idno", idno).getResults().getHits();
        Work result = new Work();
        if (CollectionUtils.isNotEmpty(searchHits)) {
            if (EXPECTED_SEARCH_HITS_COUNT == searchHits.size()) {
                result = searchByHostIdnoIfExists(searchHits, idno, IDNO, isHostIdno);
            } else if (isHostIdno) {
                result.setMultipleMatches(true);
                LOGGER.debug("Search works. By IDNO and Title. IDNO={}, Title={}, WrWrkInst=MultiResults, Hits={}",
                    idno, title, searchHits.stream().map(RupSearchHit::getSource).collect(Collectors.joining(";")));
            } else {
                List<RupSearchHit> filteredByTitleHits = searchHits.stream().filter(searchHit -> {
                    List<Object> mainTitles = searchHit.getFields().get(MAIN_TITLE);
                    return CollectionUtils.isNotEmpty(mainTitles) && StringUtils.equalsIgnoreCase(title,
                        mainTitles.iterator().next().toString());
                }).collect(Collectors.toList());
                if (EXPECTED_SEARCH_HITS_COUNT == filteredByTitleHits.size()) {
                    return searchByHostIdnoIfExists(filteredByTitleHits, IDNO, idno, false);
                } else {
                    result.setMultipleMatches(true);
                    LOGGER.debug("Search works. By IDNO and Title. IDNO={}, Title={}, WrWrkInst=MultiResults, Hits={}",
                        idno, title, searchHits.stream().map(RupSearchHit::getSource).collect(Collectors.joining(";")));
                }
            }
        } else {
            result.setMultipleMatches(false);
            LOGGER.debug("Search works. By IDNO. IDNO={}, WrWrkInst=Not Found, Hits=Empty", idno);
        }
        return result;
    }

    private Work findWork(String parameter, Object value, boolean isHostIdno) {
        Work result = null;
        List<RupSearchHit> searchHits = doSearch(parameter, value).getResults().getHits();
        if (EXPECTED_SEARCH_HITS_COUNT == searchHits.size()) {
            result = searchByHostIdnoIfExists(searchHits, parameter, value, isHostIdno);
        }
        return result;
    }

    private Work searchByHostIdnoIfExists(List<RupSearchHit> searchHits, String parameter, Object value,
                                          boolean isHostIdno) {
        Work result = new Work();
        if (isHostIdno) {
            result = parseWorkFromSearchHit(searchHits, parameter, value, true);
        } else {
            List<Object> hostIdnos = searchHits.get(0).getFields().get(HOST_IDNO);
            if (Objects.isNull(hostIdnos)) {
                result = parseWorkFromSearchHit(searchHits, parameter, value, false);
            } else if (EXPECTED_HOST_INDOS_COUNT == hostIdnos.size()) {
                result = findWorkByIdnoAndTitle(hostIdnos.get(0).toString(), null, true);
            } else {
                result.setMultipleMatches(true);
                LOGGER.debug("Search works. By {}. ParameterValue={}, WrWrkInst=MultiResults, Hits={}", parameter,
                    value, searchHits.stream().map(RupSearchHit::getSource).collect(Collectors.joining(";")));
            }
        }
        return result;
    }

    private Work findByWrWrkInst(Object value) {
        List<RupSearchHit> searchHits = doSearch("wrWrkInst", value).getResults().getHits();
        return EXPECTED_SEARCH_HITS_COUNT == searchHits.size()
            ? parseWorkFromSearchHit(searchHits, "wrWrkInst", value, false)
            : null;
    }

    private Work parseWorkFromSearchHit(List<RupSearchHit> searchHits, String parameter, Object value,
                                        boolean isHostIdno) {
        try {
            String source = searchHits.get(0).getSource();
            Work result = mapper.readValue(source, Work.class);
            result.setHostIdnoFlag(isHostIdno);
            LOGGER.trace("Search works. By {}. ParameterValue={}, Hit={}", parameter, value, source);
            return result;
        } catch (IOException e) {
            throw new RupRuntimeException(
                String.format("Search works. By %s. Failed. ParameterValue=%s, Reason=Could not read response",
                    parameter, value), e);
        }
    }

    private RupSearchResponse doSearch(String field, Object value) {
        String queryString = buildQueryString(field, value);
        RupSearchRequest request = RupSearchRequest.of(piIndex);
        RupQueryStringQueryBuilder builder = RupQueryStringQueryBuilder.of(queryString);
        request.setQueryBuilder(builder);
        request.setSearchType(RupSearchRequest.RupSearchType.DFS_QUERY_AND_FETCH);
        request.setFields(MAIN_TITLE, HOST_IDNO);
        request.setFetchSource(true);
        try {
            return getRupEsApi().search(request);
        } catch (RupEsApiRuntimeException | ElasticsearchException e) {
            throw new RupRuntimeException("Unable to connect to RupEsApi", e);
        }
    }
}
