package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.IUsageService;

import org.junit.Test;

/**
 * Verifies {@link DeleteUsageProcessor}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 02/01/2019
 *
 * @author Pavel Liakh
 */
public class DeleteUsageProcessorTest {

    @Test
    public void testProcess() {
        DeleteUsageProcessor deleteUsageProcessor = new DeleteUsageProcessor();
        IUsageService usageServiceMock = createMock(IUsageService.class);
        deleteUsageProcessor.setUsageService(usageServiceMock);
        Usage usage = new Usage();
        String usageId = RupPersistUtils.generateUuid();
        usage.setId(usageId);
        usageServiceMock.deleteById(usageId);
        expectLastCall().once();
        replay(usageServiceMock);
        deleteUsageProcessor.process(usage);
        verify(usageServiceMock);
    }
}
