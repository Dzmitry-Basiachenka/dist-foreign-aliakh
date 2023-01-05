package com.copyright.rup.dist.foreign.service.impl.matching;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.newCapture;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IWorkMatchingService;
import com.copyright.rup.dist.foreign.service.api.processor.IChainProcessor;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.List;
import java.util.function.Predicate;

/**
 * Verifies {@link UdmMatchingConsumer}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 05/20/2021
 *
 * @author Ihar Suvorau
 */
public class UdmMatchingConsumerTest {

    private UdmMatchingConsumer consumer;
    private IChainProcessor<UdmUsage> matchingProcessor;
    private IWorkMatchingService matchingService;

    @Before
    public void setUp() {
        consumer = new UdmMatchingConsumer();
        matchingProcessor = createMock(IChainProcessor.class);
        matchingService = createMock(IWorkMatchingService.class);
        Whitebox.setInternalState(consumer, matchingProcessor);
        Whitebox.setInternalState(consumer, matchingService);
    }

    @Test
    public void testConsumeUdmUsages() {
        UdmUsage usage = new UdmUsage();
        usage.setId("45103131-f5bf-4246-b3eb-12f5702b17b9");
        usage.setStatus(UsageStatusEnum.NEW);
        List<UdmUsage> usages = List.of(usage);
        matchingService.matchingUdmUsages(usages);
        expectLastCall().once();
        Capture<Predicate<UdmUsage>> predicateCapture = newCapture();
        matchingProcessor.executeNextChainProcessor(eq(usages), capture(predicateCapture));
        expectLastCall().once();
        replay(matchingProcessor, matchingService);
        consumer.consume(usages);
        verify(matchingProcessor, matchingService);
    }
}
