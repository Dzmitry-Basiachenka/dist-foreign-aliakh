package com.copyright.rup.dist.foreign.integration.lm.impl;

import com.copyright.rup.dist.common.integration.camel.IProducer;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.integration.lm.api.ILmIntegrationService;
import com.copyright.rup.dist.foreign.integration.lm.api.domain.ExternalUsage;
import com.copyright.rup.dist.foreign.integration.lm.api.domain.ExternalUsageMessage;

import com.google.common.collect.Iterables;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Implementation of {@link ILmIntegrationService}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/09/18
 *
 * @author Ihar Suvorau
 */
@Service
public class LmIntegrationService implements ILmIntegrationService {

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS Z");

    @Autowired
    @Qualifier("df.integration.externalUsageProducer")
    private IProducer<ExternalUsageMessage> externalUsageProducer;

    @Value("$RUP{dist.foreign.message_batch_size}")
    private int batchSize;

    @Override
    public void sendToLm(List<ExternalUsage> externalUsages, String scenarioId, String scenarioName,
                         String productFamily, int numberOfMessages) {
        Iterables.partition(externalUsages, batchSize)
            .forEach(partition -> externalUsageProducer.send(
                new ExternalUsageMessage(Map.of(
                    "source", "FDA",
                    "scenarioId", Objects.requireNonNull(scenarioId),
                    "scenarioName", Objects.requireNonNull(scenarioName),
                    "productFamily", Objects.requireNonNull(productFamily),
                    "numberOfMessages", numberOfMessages,
                    "sendDate", DATE_TIME_FORMATTER.format(OffsetDateTime.now())),
                    partition)));
    }

    @Override
    public void sendToLm(List<ExternalUsage> externalUsages, Scenario scenario, int numberOfMessages) {
        sendToLm(externalUsages, scenario.getId(), scenario.getName(), scenario.getProductFamily(), numberOfMessages);
    }
}
