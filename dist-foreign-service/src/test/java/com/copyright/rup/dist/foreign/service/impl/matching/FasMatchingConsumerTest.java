package com.copyright.rup.dist.foreign.service.impl.matching;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.newCapture;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
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
 * Verifies {@link FasMatchingConsumer}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 11/21/2018
 *
 * @author Ihar Suvorau
 * @author Aliaksandr Liakh
 */
public class FasMatchingConsumerTest {

    private FasMatchingConsumer consumer;
    private IChainProcessor<Usage> matchingProcessor;
    private IWorkMatchingService matchingService;

    @Before
    public void setUp() {
        consumer = new FasMatchingConsumer();
        matchingProcessor = createMock(IChainProcessor.class);
        matchingService = createMock(IWorkMatchingService.class);
        Whitebox.setInternalState(consumer, matchingService);
        Whitebox.setInternalState(consumer, matchingProcessor);
    }

    @Test
    public void testConsumeFasUsages() {
        Usage usage = new Usage();
        usage.setId(RupPersistUtils.generateUuid());
        usage.setStatus(UsageStatusEnum.NEW);
        List<Usage> usages = List.of(usage);
        matchingService.matchingFasUsages(usages);
        expectLastCall().once();
        Capture<Predicate<Usage>> predicateCapture = newCapture();
        matchingProcessor.executeNextChainProcessor(eq(usages), capture(predicateCapture));
        expectLastCall().once();
        replay(matchingProcessor, matchingService);
        consumer.consume(usages);
        verify(matchingProcessor, matchingService);
    }
}
