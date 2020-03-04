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
 * Verifies {@link RhTaxProcessor}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 01/02/2019
 *
 * @author Pavel Liakh
 */
public class RhTaxProcessorTest {

    private RhTaxProcessor rhTaxProcessor;
    private IProducer<Usage> rhTaxProducerMock;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        rhTaxProcessor = new RhTaxProcessor();
        rhTaxProducerMock = createMock(IProducer.class);
        rhTaxProcessor.setRhTaxProducer(rhTaxProducerMock);
        rhTaxProcessor.setUsagesBatchSize(1000);
        rhTaxProcessor.setUsageStatus(UsageStatusEnum.RH_FOUND);
    }

    @Test
    public void testProcess() {
        Usage usage = buildUsage();
        rhTaxProducerMock.send(usage);
        expectLastCall().once();
        replay(rhTaxProducerMock);
        rhTaxProcessor.process(usage);
        verify(rhTaxProducerMock);
    }

    private Usage buildUsage() {
        Usage usage = new Usage();
        usage.setId(RupPersistUtils.generateUuid());
        usage.setWrWrkInst(854030733L);
        usage.setProductFamily("NTS");
        usage.setStatus(UsageStatusEnum.RH_FOUND);
        return usage;
    }
}
