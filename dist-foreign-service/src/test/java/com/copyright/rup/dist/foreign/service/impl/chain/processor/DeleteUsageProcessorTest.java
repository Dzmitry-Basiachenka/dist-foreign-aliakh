package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

/**
 * Verifies {@link DeleteUsageProcessor}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 02/01/2019
 *
 * @author Pavel Liakh
 * @author Aliaksandr Liakh
 */
public class DeleteUsageProcessorTest {

    private DeleteUsageProcessor deleteUsageProcessor;
    private IUsageService usageService;

    @Before
    public void setUp() {
        deleteUsageProcessor = new DeleteUsageProcessor();
        usageService = createMock(IUsageService.class);
        Whitebox.setInternalState(deleteUsageProcessor, usageService);
    }

    @Test
    public void testProcess() {
        Usage usage1 = buildUsage("b470e65d-f6f7-47a2-9b59-beeb1a9c39b3");
        Usage usage2 = buildUsage("0b8e70ac-e3cc-46b5-a09b-321dffc8f155");
        usageService.deleteById(usage1.getId());
        expectLastCall().once();
        usageService.deleteById(usage2.getId());
        expectLastCall().once();
        replay(usageService);
        deleteUsageProcessor.process(Lists.newArrayList(usage1, usage2));
        verify(usageService);
    }

    @Test
    public void testGetChainProcessorType() {
        assertEquals(ChainProcessorTypeEnum.DELETE, deleteUsageProcessor.getChainProcessorType());
    }

    private Usage buildUsage(String usageId) {
        Usage usage = new Usage();
        usage.setId(usageId);
        return usage;
    }
}
