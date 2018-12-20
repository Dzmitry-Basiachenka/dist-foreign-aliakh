package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.integration.camel.IProducer;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.IRightsService;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

/**
 * Verifies {@link RightsProcessor}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 12/19/2018
 *
 * @author Uladzislau Shalamitski
 */
public class RightsProcessorTest {

    private RightsProcessor processor;
    private IProducer<Usage> rightsProducer;
    private IRightsService rightsService;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        processor = new RightsProcessor();
        rightsProducer = createMock(IProducer.class);
        rightsService = createMock(IRightsService.class);
        Whitebox.setInternalState(processor, rightsProducer);
        Whitebox.setInternalState(processor, rightsService);
    }

    @Test
    public void testProcess() {
        rightsService.updateRights();
        expectLastCall().once();
        replay(rightsService);
        processor.process();
        verify(rightsService);
    }

    @Test
    public void testProcessUsage() {
        Usage usage = buildUsage();
        rightsProducer.send(usage);
        expectLastCall().once();
        replay(rightsProducer);
        processor.process(usage);
        verify(rightsProducer);
    }

    private Usage buildUsage() {
        Usage usage = new Usage();
        usage.setId(RupPersistUtils.generateUuid());
        return usage;
    }
}
