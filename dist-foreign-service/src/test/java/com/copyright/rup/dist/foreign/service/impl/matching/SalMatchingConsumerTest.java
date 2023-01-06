package com.copyright.rup.dist.foreign.service.impl.matching;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.newCapture;
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

import java.util.List;
import java.util.function.Predicate;

/**
 * Verifies {@link SalMatchingConsumer}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 08/19/2020
 *
 * @author Anton Azarenka
 */
public class SalMatchingConsumerTest {

    private final SalMatchingConsumer consumer = new SalMatchingConsumer();
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
    public void testConsumeSalUsages() {
        Usage usage = new Usage();
        usage.setId("49ead60e-97f4-47c8-8ebe-d19d07457cca");
        usage.setWrWrkInst(123456789L);
        usage.setStatus(UsageStatusEnum.NEW);
        List<Usage> usages = List.of(usage);
        matchingService.matchingSalUsages(usages);
        expectLastCall().once();
        Capture<Predicate<Usage>> predicateCapture = newCapture();
        matchingProcessor.executeNextChainProcessor(eq(usages), capture(predicateCapture));
        expectLastCall().once();
        replay(matchingProcessor, matchingService);
        consumer.consume(usages);
        verify(matchingProcessor, matchingService);
    }
}
