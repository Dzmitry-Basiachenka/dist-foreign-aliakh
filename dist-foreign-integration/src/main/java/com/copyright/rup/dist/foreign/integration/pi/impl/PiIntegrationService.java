package com.copyright.rup.dist.foreign.integration.pi.impl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.foreign.integration.pi.api.IPiIntegrationService;
import com.copyright.rup.es.api.RupEsApi;
import com.copyright.rup.es.api.RupEsApiRuntimeException;
import com.copyright.rup.es.api.RupQueryStringQueryBuilder;
import com.copyright.rup.es.api.RupResponseBase;
import com.copyright.rup.es.api.RupSearchResponse;
import com.copyright.rup.es.api.domain.RupSearchHit;
import com.copyright.rup.es.api.domain.RupSearchResults;
import com.copyright.rup.es.api.request.RupSearchRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.elasticsearch.ElasticsearchException;
import org.perf4j.StopWatch;
import org.perf4j.slf4j.Slf4JStopWatch;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
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

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Value("$RUP{dist.foreign.integration.works.cluster}")
    private String cluster;
    @Value("$RUP{dist.foreign.integration.works.node}")
    private String node;
    @Value("$RUP{dist.foreign.integration.works.pi.index}")
    private String piIndex;

    private ObjectMapper mapper;

    /**
     * Normalizes IDNO by the following rules:
     * 1. trims special characters on both ends of string
     * 2. removes all dashes
     * 3. makes all letters upper case.
     *
     * @param idno IDNO to be normalized
     * @return normalized IDNO
     */
    public static String normalizeIdno(String idno) {
        return StringUtils.upperCase(StringUtils.replace(StringUtils.trim(idno), "-", StringUtils.EMPTY));
    }

    @Override
    public Map<String, Long> findWrWrkInstsByIdnos(Set<String> idnos) {
        LOGGER.info("Search works by IDNOs. Started. IDNOsCount={}", idnos.size());
        Set<String> normalized = idnos.stream().map(PiIntegrationService::normalizeIdno).collect(Collectors.toSet());
        Map<String, Long> wrWrkInstMap = match("idno", normalized, Work::getIdnos);
        LOGGER.info("Search works by IDNOs. Finished. IDNOsCount={}", idnos.size());
        return wrWrkInstMap;
    }

    @Override
    public Map<String, Long> findWrWrkInstsByTitles(Set<String> titles) {
        LOGGER.info("Search works by titles. Started. TitlesCount={}", titles.size());
        Map<String, Long> wrWrkInstMap = match("title", titles, Work::getTitles);
        LOGGER.info("Search works by titles. Finished. TitlesCount={}", titles.size());
        return wrWrkInstMap;
    }

    /**
     * @return an instance of {@link RupEsApi}.
     */
    protected RupEsApi getRupEsApi() {
        return RupEsApi.of(cluster, node);
    }

    /**
     * Initializes search API.
     */
    @PostConstruct
    void init() {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private Map<String, Long> match(String field, Set<String> searchValues, Function<Work, List<String>> workFunction) {
        StopWatch stopWatch = new Slf4JStopWatch();
        Map<String, Long> result = Collections.emptyMap();
        RupSearchResponse searchResponse = doSearch(field, searchValues);
        stopWatch.lap("matchWorks.doSearch");
        if (Objects.nonNull(searchResponse)) {
            if (RupResponseBase.Status.SUCCESS == searchResponse.getStatus()) {
                RupSearchResults results = searchResponse.getResults();
                List<RupSearchHit> hits = results.getHits();
                if (CollectionUtils.isNotEmpty(hits)) {
                    result = mapWorks(searchValues, hits, workFunction);
                }
            } else {
                LOGGER.warn("Search works. Failed. SearchResponse={}", searchResponse);
            }
        }
        stopWatch.stop("matchWorks.handleResponse");
        return result;
    }

    private Map<String, Long> mapWorks(Set<String> searchValues,
                                       List<RupSearchHit> hits,
                                       Function<Work, List<String>> function) {
        Set<String> duplicates = Sets.newHashSet();
        Map<String, Long> wrWrkInstMap = Maps.newHashMap();
        hits.forEach(rupSearchHit -> {
            if (Objects.nonNull(rupSearchHit)) {
                try {
                    LOGGER.trace("Search hit={}", rupSearchHit.getSource());
                    Work work = readWork(rupSearchHit);
                    function.apply(work).forEach(key -> {
                        if (searchValues.contains(key)) {
                            Long newValue = work.getWrWrkInst();
                            Long oldValue = wrWrkInstMap.putIfAbsent(key, newValue);
                            if (Objects.nonNull(oldValue) && !oldValue.equals(newValue)) {
                                duplicates.add(key);
                            }
                        }
                    });
                } catch (IOException e) {
                    LOGGER.warn("Search works. Could not map results.", e);
                }
            }
        });
        if (CollectionUtils.isNotEmpty(duplicates)) {
            LOGGER.debug("Search works. More than one match found for the following search values: {}",
                duplicates.stream().collect(Collectors.joining(",")));
            duplicates.forEach(wrWrkInstMap::remove);
        }
        return wrWrkInstMap;
    }

    private Work readWork(RupSearchHit rupSearchHit) throws IOException {
        Work work = mapper.readValue(rupSearchHit.getSource(), Work.class);
        if (Objects.isNull(work.getIdnos())) {
            work.idnos = Collections.emptyList();
            LOGGER.warn("Search works. IDNOs list is NULL. RupSearchHit={}", rupSearchHit);
        } else {
            work.idnos = work.getIdnos().stream().map(PiIntegrationService::normalizeIdno).collect(Collectors.toList());
        }
        if (Objects.isNull(work.getTitles())) {
            work.titles = Collections.emptyList();
            LOGGER.warn("Search works. Titles list is NULL. RupSearchHit={}", rupSearchHit);
        }
        return work;
    }

    private RupSearchResponse doSearch(String field, Set<String> values) {
        RupSearchResponse searchResponse = null;
        String queryString = buildQueryString(field, values);
        if (StringUtils.isNotEmpty(queryString)) {
            LOGGER.debug("Search works. Query={}", queryString);
            RupSearchRequest request = RupSearchRequest.of(piIndex);
            RupQueryStringQueryBuilder builder = RupQueryStringQueryBuilder.of(queryString);
            request.setQueryBuilder(builder);
            request.setTypes("work");
            request.setSearchType(RupSearchRequest.RupSearchType.DFS_QUERY_AND_FETCH);
            request.setFields("wrWrkInst", "idno", "title");
            request.setFetchSource(true);
            try {
                searchResponse = getRupEsApi().search(request);
            } catch (RupEsApiRuntimeException | ElasticsearchException e) {
                LOGGER.warn("Search works failed. Unable to connect to RupEsApi.", e);
            }
        }
        return searchResponse;
    }

    private String buildQueryString(String field, Set<String> values) {
        StringBuilder builder = new StringBuilder();
        append(builder, field, values);
        return builder.toString();
    }

    private void append(StringBuilder builder, String field, Set<String> values) {
        if (CollectionUtils.isNotEmpty(values)) {
            Iterator<String> iterator = values.iterator();
            appendValue(builder, field, iterator.next());
            while (iterator.hasNext()) {
                builder.append(" OR ");
                appendValue(builder, field, iterator.next());
            }
        }
    }

    private void appendValue(StringBuilder builder, String field, String value) {
        builder.append(field)
            .append(":\"")
            .append(value)
            .append('"');
    }

    private static class Work {

        private Long wrWrkInst;
        @JsonProperty("idno")
        private List<String> idnos;
        @JsonProperty("title")
        private List<String> titles;

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
                .append(this.idnos, that.idnos)
                .append(this.titles, that.titles)
                .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder()
                .append(wrWrkInst)
                .append(idnos)
                .append(titles)
                .toHashCode();
        }

        public void setWrWrkInst(Long wrWrkInst) {
            this.wrWrkInst = wrWrkInst;
        }

        private Long getWrWrkInst() {
            return wrWrkInst;
        }

        private List<String> getIdnos() {
            return idnos;
        }

        private List<String> getTitles() {
            return titles;
        }
    }
}
