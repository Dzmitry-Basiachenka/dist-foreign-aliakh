package com.copyright.rup.dist.foreign.integration.pi.impl;

import com.copyright.rup.es.api.RupEsApi;

import com.google.common.collect.Iterables;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.PreDestroy;

/**
 * Configuration for RupEsApi.
 * <p/>
 * Copyright (C) 2023 copyright.com
 * <p/>
 * Date: 03/17/2023
 *
 * @author Dzmitry Basiachenka
 */
@Configuration
public class RupEsApiConfiguration {

    @Value("$RUP{dist.foreign.pi.cluster}")
    private String cluster;
    @Value("$RUP{dist.foreign.pi.nodes}")
    private List<String> nodes;
    @Value("$RUP{dist.foreign.search.ldap.username}")
    private String username;
    @Value("$RUP{dist.foreign.search.ldap.password}")
    private String password;

    private RupEsApi rupEsApi;

    /**
     * @return the rupEsApi bean
     */
    @Bean(name = "rupEsApi")
    public RupEsApi getRupEsApi() {
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
    public void destroy() {
        if (Objects.nonNull(rupEsApi)) {
            rupEsApi.closeConnection();
        }
    }
}
