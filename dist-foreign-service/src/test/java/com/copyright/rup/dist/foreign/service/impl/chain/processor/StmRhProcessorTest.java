package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.integration.camel.IProducer;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

/**
 * Verifies {@link StmRhProcessor}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 10/14/19
 *
 * @author Stanislau Rudak
 */
public class StmRhProcessorTest {

    private IProducer<Usage> producerMock;
    private StmRhProcessor processor;

    @Before
    public void setUp() {
        producerMock = createMock(IProducer.class);
        processor = new StmRhProcessor();
        Whitebox.setInternalState(processor, "producer", producerMock);
        processor.setUsagesBatchSize(1000);
        processor.setUsageStatus(UsageStatusEnum.US_TAX_COUNTRY);
    }

    @Test
    public void testProcess() {
        Usage usage = buildUsage();
        producerMock.send(usage);
        expectLastCall().once();
        replay(producerMock);
        processor.process(usage);
        verify(producerMock);
    }

    @Test
    public void testGetChainProcessorType() {
        assertEquals(ChainProcessorTypeEnum.STM_RH, processor.getChainProcessorType());
    }

    private Usage buildUsage() {
        Usage usage = new Usage();
        usage.setId(RupPersistUtils.generateUuid());
        usage.setProductFamily("NTS");
        usage.setStatus(UsageStatusEnum.US_TAX_COUNTRY);
        return usage;
    }
}
