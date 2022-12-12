package com.copyright.rup.dist.foreign.service.impl.matching;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IWorkMatchingService;
import com.copyright.rup.dist.foreign.service.api.processor.IChainProcessor;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * Verifies {@link AclciMatchingConsumer}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 12/09/2022
 *
 * @author Mikita Maistrenka
 */
public class AclciMatchingConsumerTest {

    private final AclciMatchingConsumer consumer = new AclciMatchingConsumer();
    private IChainProcessor<Usage> matchingProcessor;
    private IWorkMatchingService matchingService;

    @Before
    public void setUp() {
        matchingProcessor = createMock(IChainProcessor.class);
        matchingService = createMock(IWorkMatchingService.class);
        Whitebox.setInternalState(consumer, matchingService);
        Whitebox.setInternalState(consumer, matchingProcessor);
    }

    @Test
    public void testConsumeAclciUsages() {
        Usage usage = new Usage();
        usage.setId("fb577694-f67c-4689-85d5-42c376bb91c3");
        usage.setWrWrkInst(123456789L);
        usage.setStatus(UsageStatusEnum.NEW);
        List<Usage> usages = Collections.singletonList(usage);
        matchingService.matchingAclciUsages(usages);
        expectLastCall().once();
        Capture<Predicate<Usage>> predicateCapture = new Capture<>();
        matchingProcessor.executeNextChainProcessor(eq(usages), capture(predicateCapture));
        expectLastCall().once();
        replay(matchingProcessor, matchingService);
        consumer.consume(usages);
        verify(matchingProcessor, matchingService);
    }
}
