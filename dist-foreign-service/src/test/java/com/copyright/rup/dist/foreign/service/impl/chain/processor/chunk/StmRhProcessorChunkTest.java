package com.copyright.rup.dist.foreign.service.impl.chain.processor.chunk;

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

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link StmRhChunkProcessor}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 10/14/2019
 *
 * @author Stanislau Rudak
 * @author Aliaksandr Liakh
 */
public class StmRhProcessorChunkTest {

    private IProducer<List<Usage>> producer;
    private StmRhChunkProcessor processor;

    @Before
    public void setUp() {
        producer = createMock(IProducer.class);
        processor = new StmRhChunkProcessor();
        Whitebox.setInternalState(processor, producer);
        processor.setUsagesBatchSize(1000);
        processor.setUsageStatus(UsageStatusEnum.US_TAX_COUNTRY);
    }

    @Test
    public void testProcess() {
        List<Usage> usages = buildUsages();
        producer.send(usages);
        expectLastCall().once();
        replay(producer);
        processor.process(usages);
        verify(producer);
    }

    @Test
    public void testGetChainProcessorType() {
        assertEquals(ChainProcessorTypeEnum.STM_RH, processor.getChainProcessorType());
    }

    private List<Usage> buildUsages() {
        Usage usage = new Usage();
        usage.setId(RupPersistUtils.generateUuid());
        usage.setProductFamily("NTS");
        usage.setStatus(UsageStatusEnum.US_TAX_COUNTRY);
        return Collections.singletonList(usage);
    }
}
