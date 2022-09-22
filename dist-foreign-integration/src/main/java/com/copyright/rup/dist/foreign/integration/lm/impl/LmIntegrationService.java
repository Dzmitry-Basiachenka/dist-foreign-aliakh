package com.copyright.rup.dist.foreign.integration.lm.impl;

import com.copyright.rup.dist.common.integration.camel.IProducer;
import com.copyright.rup.dist.foreign.integration.lm.api.ILmIntegrationService;
import com.copyright.rup.dist.foreign.integration.lm.api.domain.ExternalUsage;
import com.copyright.rup.dist.foreign.integration.lm.api.domain.ExternalUsageMessage;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Autowired
    @Qualifier("df.integration.externalUsageProducer")
    private IProducer<ExternalUsageMessage> externalUsageProducer;

    @Value("$RUP{dist.foreign.message_batch_size}")
    private int batchSize;

    @Override
    public void sendToLm(List<ExternalUsage> externalUsages) {
        Iterables.partition(externalUsages, batchSize)
            .forEach(partition -> externalUsageProducer.send(
                new ExternalUsageMessage(ImmutableMap.of("source", "FDA"), partition)));
    }
}
