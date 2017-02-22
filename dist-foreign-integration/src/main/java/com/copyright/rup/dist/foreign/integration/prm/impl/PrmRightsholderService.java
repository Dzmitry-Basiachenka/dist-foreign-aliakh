package com.copyright.rup.dist.foreign.integration.prm.impl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.integration.rest.CommonRestHandler;
import com.copyright.rup.dist.common.integration.rest.prm.CommonPrmRestHandler;
import com.copyright.rup.dist.common.integration.util.JsonUtils;
import com.copyright.rup.dist.common.integration.util.RestUtils;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmRightsholderService;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

import org.apache.commons.lang3.StringUtils;
import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriTemplate;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

/**
 * Implementation of {@link IPrmRightsholderService}.
 * Uses rest calls.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/21/2017
 *
 * @author Mikalai Bezmen
 */
@Service
// TODO {mbezmen} move to dist-common
public class PrmRightsholderService implements IPrmRightsholderService {

    /**
     * The key to insert collection of account numbers into the URL.
     */
    static final String URL_VARIABLE_RIGHTSHOLDER_ACCOUNT_NUMBERS = "accountNumbers";
    private static final String RH_ACCOUNT_NUMBER = "extOrgKey";
    private static final String RIGHTSHOLDER_ID = "id";
    private static final String RIGHTSHOLDER_NAME = "name";
    private static final String COMMA = ",";
    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private RestTemplate restTemplate;

    @Value("$RUP{dist.common.integration.rest.prm.url}")
    private String baseUrl;

    private String rightsholdersUrl;

    @Override
    @Profiled(tag = "integration.PrmRightsholderService.getRightsholders")
    public Collection<Rightsholder> getRightsholders(final Set<Long> accountNumbers) {
        LOGGER.info("Get Rightsholders. Started. AccountNumbers={}", accountNumbers);
        Collection<Rightsholder> rightsholders = Lists.newArrayList();
        if (!accountNumbers.isEmpty()) {
            CommonRestHandler<Collection<Rightsholder>> handler =
                new CommonPrmRestHandler<Collection<Rightsholder>>(restTemplate) {
                    @Override
                    protected Collection<Rightsholder> doHandleResponse(JsonNode response) {
                        LOGGER.trace("Received response for {} request: {}",
                            new UriTemplate(rightsholdersUrl).expand(accountNumbers), response);
                        return buildRightsholders(response);
                    }

                    @Override
                    protected Collection<Rightsholder> getDefaultValue() {
                        return Collections.emptyList();
                    }
                };
            for (List<Long> batchAccountNumbers : RestUtils.partition(accountNumbers, rightsholdersUrl)) {
                rightsholders.addAll(doGetRightsholders(handler, batchAccountNumbers));
            }
            LOGGER.debug("Found Rightsholders in PRM. {}", rightsholders);
        }
        LOGGER.info("Get Rightsholders. Finished. AccountNumbers={}", accountNumbers);
        return rightsholders;
    }

    /**
     * Initializes urls.
     */
    @PostConstruct
    public void initializeUrls() {
        rightsholdersUrl = UriComponentsBuilder.fromHttpUrl(baseUrl)
            .pathSegment("organization/extorgkeys?extOrgKeys[]={accountNumbers}&fmt=json").build().toUriString();
    }

    /**
     * The REST template to simplify interaction with PRM.
     *
     * @param restTemplate the REST template.
     */
    void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * The base URL for PRM.
     *
     * @param baseUrl the base url.
     */
    void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    private Collection<Rightsholder> doGetRightsholders(CommonRestHandler<Collection<Rightsholder>> handler,
                                                        Collection<Long> accountNumbers) {
        return handler.handleResponse(rightsholdersUrl,
            ImmutableMap.of(URL_VARIABLE_RIGHTSHOLDER_ACCOUNT_NUMBERS, StringUtils.join(accountNumbers, COMMA)));
    }

    private Rightsholder buildRightsholder(JsonNode rightsholderJson) {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setId(JsonUtils.getStringValue(rightsholderJson.get(RIGHTSHOLDER_ID)));
        rightsholder.setAccountNumber(JsonUtils.getLongValue(rightsholderJson.get(RH_ACCOUNT_NUMBER)));
        rightsholder.setName(JsonUtils.getStringValue(rightsholderJson.get(RIGHTSHOLDER_NAME)));
        return rightsholder;
    }

    private Collection<Rightsholder> buildRightsholders(JsonNode response) {
        Collection<Rightsholder> rightsholders = Collections.emptyList();
        if (response.isArray()) {
            int size = response.size();
            rightsholders = Lists.newArrayListWithExpectedSize(size);
            for (int i = 0; i < size; i++) {
                rightsholders.add(buildRightsholder(response.get(i)));
            }
        }
        return rightsholders;
    }
}
