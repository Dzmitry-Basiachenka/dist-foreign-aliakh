package com.copyright.rup.dist.foreign.integration.lm.impl;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.dist.common.integration.camel.IProducer;
import com.copyright.rup.dist.foreign.domain.LiabilityDetail;
import com.copyright.rup.dist.foreign.integration.lm.api.ILmIntegrationService;
import com.copyright.rup.dist.foreign.integration.lm.impl.domain.LiabilityDetailMessage;

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
    @Qualifier("df.integration.liabilityDetailProducer")
    private IProducer<LiabilityDetailMessage> liabilityDetailProducer;

    @Value("$RUP{dist.foreign.message_batch_size}")
    private int batchSize;

    @Override
    public void sendToLm(List<LiabilityDetail> liabilityDetails) throws RupRuntimeException {
        Iterables.partition(liabilityDetails, batchSize)
            .forEach(partition -> liabilityDetailProducer.send(
                new LiabilityDetailMessage(ImmutableMap.of("source", "FDA"), partition)));
    }
}
