package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.List;
import java.util.Set;

/**
 * Verifies {@link UnclassifiedStatusProcessor}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 03/22/2019
 *
 * @author Pavel Liakh
 * @author Aliaksandr Liakh
 */
public class UnclassifiedStatusProcessorTest {

    private UnclassifiedStatusProcessor unclassifiedStatusProcessor;
    private IUsageRepository usageRepository;

    @Before
    public void setUp() {
        unclassifiedStatusProcessor = new UnclassifiedStatusProcessor();
        usageRepository = createMock(IUsageRepository.class);
        Whitebox.setInternalState(unclassifiedStatusProcessor, usageRepository);
    }

    @Test
    public void testProcess() {
        Usage usage1 = buildUsage("1cd430df-f874-4881-a307-1835bee3c2e5");
        Usage usage2 = buildUsage("2b2a450e-9b7d-441d-a75b-428a3fef8055");
        usageRepository.updateStatus(Set.of(usage1.getId(), usage2.getId()), UsageStatusEnum.UNCLASSIFIED);
        expectLastCall().once();
        replay(usageRepository);
        unclassifiedStatusProcessor.process(List.of(usage1, usage2));
        verify(usageRepository);
    }

    @Test
    public void testGetChainProcessorType() {
        assertEquals(ChainProcessorTypeEnum.CLASSIFICATION, unclassifiedStatusProcessor.getChainProcessorType());
    }

    private Usage buildUsage(String usageId) {
        Usage usage = new Usage();
        usage.setId(usageId);
        return usage;
    }
}
