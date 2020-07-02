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

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link RhTaxChunkProcessor}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 01/02/2019
 *
 * @author Pavel Liakh
 * @author Aliaksandr Liakh
 */
public class RhTaxProcessorChunkTest {

    private RhTaxChunkProcessor rhTaxProcessor;
    private IProducer<List<Usage>> rhTaxProducer;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        rhTaxProcessor = new RhTaxChunkProcessor();
        rhTaxProducer = createMock(IProducer.class);
        Whitebox.setInternalState(rhTaxProcessor, rhTaxProducer);
        rhTaxProcessor.setUsagesBatchSize(1000);
        rhTaxProcessor.setUsageStatus(UsageStatusEnum.RH_FOUND);
    }

    @Test
    public void testProcess() {
        List<Usage> usages = buildUsages();
        rhTaxProducer.send(usages);
        expectLastCall().once();
        replay(rhTaxProducer);
        rhTaxProcessor.process(usages);
        verify(rhTaxProducer);
    }

    @Test
    public void testGetChainProcessorType() {
        assertEquals(ChainProcessorTypeEnum.RH_TAX, rhTaxProcessor.getChainProcessorType());
    }

    private List<Usage> buildUsages() {
        Usage usage = new Usage();
        usage.setId(RupPersistUtils.generateUuid());
        usage.setWrWrkInst(854030733L);
        usage.setProductFamily("NTS");
        usage.setStatus(UsageStatusEnum.RH_FOUND);
        return Collections.singletonList(usage);
    }
}