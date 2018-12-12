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
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.ElasticsearchException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
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
@Service("df.integration.piIntegrationService")
public class PiIntegrationService implements IPiIntegrationService {

    private static final int EXPECTED_SEARCH_HITS_COUNT = 1;
    private static final String MAIN_TITLE = "mainTitle";
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
    public Work findWorkByIdnoAndTitle(String idno, String title) {
        Work result = matchByStandardNumber(idno, "issn");
        if (Objects.nonNull(result)) {
            return result;
        }
        result = matchByStandardNumber(idno, "isbn10");
        if (Objects.nonNull(result)) {
            return result;
        }
        result = matchByStandardNumber(idno, "isbn13");
        if (Objects.nonNull(result)) {
            return result;
        }
        return matchByIdnoAndTitle(idno, title);
    }

    @Override
    public Work findWorkByTitle(String title) {
        Work result = new Work();
        RupSearchResponse searchResponse = doSearch(MAIN_TITLE, title);
        List<RupSearchHit> searchHits = searchResponse.getResults().getHits();
        if (CollectionUtils.isNotEmpty(searchHits) && EXPECTED_SEARCH_HITS_COUNT == searchHits.size()) {
            try {
                String source = searchHits.get(0).getSource();
                result = mapper.readValue(source, Work.class);
                LOGGER.trace("Search works. By MainTitle. Title={}, WrWrkInst={}, Hit={}", title, result.getWrWrkInst(),
                    source);
            } catch (IOException e) {
                throw new RupRuntimeException(
                    String.format("Search works. By MainTitle. Failed. Title=%s, Reason=Could not read response",
                        title), e);
            }
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

    private Work matchByIdnoAndTitle(String idno, String title) {
        List<RupSearchHit> searchHits = doSearch("idno", idno).getResults().getHits();
        Work result = new Work();
        if (CollectionUtils.isNotEmpty(searchHits)) {
            if (EXPECTED_SEARCH_HITS_COUNT == searchHits.size()) {
                return parseWorkFromSearchHit(searchHits, idno, "IDNO");
            } else {
                List<RupSearchHit> filteredByTitleHits = searchHits.stream().filter(searchHit -> {
                    List<Object> mainTitles = searchHit.getFields().get(MAIN_TITLE);
                    return CollectionUtils.isNotEmpty(mainTitles) && StringUtils.equalsIgnoreCase(title,
                        mainTitles.iterator().next().toString());
                }).collect(Collectors.toList());
                if (EXPECTED_SEARCH_HITS_COUNT == filteredByTitleHits.size()) {
                    try {
                        String source = filteredByTitleHits.get(0).getSource();
                        result = mapper.readValue(source, Work.class);
                        LOGGER.trace("Search works. By IDNO and Title. IDNO={}, Title={}, WrWrkInst={}, Hit={}", idno,
                            title, result.getWrWrkInst(), source);
                    } catch (IOException e) {
                        throw new RupRuntimeException(
                            String.format("Search works. Failed. IDNO=%s, Title=%s, Reason=Could not read response",
                                idno, title), e);
                    }
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

    private Work matchByStandardNumber(String idno, String parameter) {
        List<RupSearchHit> searchHits = doSearch(parameter, idno).getResults().getHits();
        return EXPECTED_SEARCH_HITS_COUNT == searchHits.size()
            ? parseWorkFromSearchHit(searchHits, idno, parameter)
            : null;
    }

    private Work parseWorkFromSearchHit(List<RupSearchHit> searchHits, String idno, String parameter) {
        try {
            String source = searchHits.get(0).getSource();
            Work result = mapper.readValue(source, Work.class);
            LOGGER.trace("Search works. By {}. IDNO={}, WrWrkInst={}, Hit={}", parameter, idno, result.getWrWrkInst(),
                source);
            return result;
        } catch (IOException e) {
            throw new RupRuntimeException(
                String.format("Search works. By %s. Failed. IDNO=%s, Reason=Could not read response", parameter, idno),
                e);
        }
    }

    private RupSearchResponse doSearch(String field, String value) {
        String queryString = buildQueryString(field, value);
        RupSearchRequest request = RupSearchRequest.of(piIndex);
        RupQueryStringQueryBuilder builder = RupQueryStringQueryBuilder.of(queryString);
        request.setQueryBuilder(builder);
        request.setTypes("work");
        request.setSearchType(RupSearchRequest.RupSearchType.DFS_QUERY_AND_FETCH);
        request.setFields(MAIN_TITLE);
        request.setFetchSource(true);
        try {
            return getRupEsApi().search(request);
        } catch (RupEsApiRuntimeException | ElasticsearchException e) {
            throw new RupRuntimeException("Unable to connect to RupEsApi", e);
        }
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
}
