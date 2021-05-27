package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.common.persist.RupPersistUtils;
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
        Usage usage1 = buildUsage();
        Usage usage2 = buildUsage();
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

    private Usage buildUsage() {
        Usage usage = new Usage();
        usage.setId(RupPersistUtils.generateUuid());
        return usage;
    }
}
