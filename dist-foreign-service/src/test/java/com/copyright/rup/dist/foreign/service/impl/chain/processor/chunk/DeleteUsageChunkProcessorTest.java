package com.copyright.rup.dist.foreign.service.impl.chain.processor.chunk;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.IUsageService;

import com.google.common.collect.Lists;

import org.junit.Test;
import org.powermock.reflect.Whitebox;

/**
 * Verifies {@link DeleteUsageChunkProcessor}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 02/01/2019
 *
 * @author Pavel Liakh
 * @author Aliaksandr Liakh
 */
public class DeleteUsageChunkProcessorTest {

    @Test
    public void testProcess() {
        DeleteUsageChunkProcessor deleteUsageProcessor = new DeleteUsageChunkProcessor();
        IUsageService usageService = createMock(IUsageService.class);
        Whitebox.setInternalState(deleteUsageProcessor, usageService);
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

    private Usage buildUsage() {
        Usage usage = new Usage();
        usage.setId(RupPersistUtils.generateUuid());
        return usage;
    }
}
