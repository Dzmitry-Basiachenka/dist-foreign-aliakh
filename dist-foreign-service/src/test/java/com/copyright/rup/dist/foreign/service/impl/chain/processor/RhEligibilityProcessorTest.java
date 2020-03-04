package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.integration.camel.IProducer;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;

import org.junit.Before;
import org.junit.Test;

/**
 * Verifies {@link RhEligibilityProcessor}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 01/02/2019
 *
 * @author Uladzislau Shalamitski
 */
public class RhEligibilityProcessorTest {

    private RhEligibilityProcessor rhEligibilityProcessor;
    private IProducer<Usage> rhEligibilityProducerMock;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        rhEligibilityProcessor = new RhEligibilityProcessor();
        rhEligibilityProducerMock = createMock(IProducer.class);
        rhEligibilityProcessor.setRhEligibilityProducer(rhEligibilityProducerMock);
        rhEligibilityProcessor.setUsagesBatchSize(1000);
        rhEligibilityProcessor.setUsageStatus(UsageStatusEnum.US_TAX_COUNTRY);
    }

    @Test
    public void testProcess() {
        Usage usage = buildUsage();
        rhEligibilityProducerMock.send(usage);
        expectLastCall().once();
        replay(rhEligibilityProducerMock);
        rhEligibilityProcessor.process(usage);
        verify(rhEligibilityProducerMock);
    }

    private Usage buildUsage() {
        Usage usage = new Usage();
        usage.setId(RupPersistUtils.generateUuid());
        usage.setWrWrkInst(854030733L);
        usage.setProductFamily("NTS");
        usage.setStatus(UsageStatusEnum.US_TAX_COUNTRY);
        return usage;
    }
}
