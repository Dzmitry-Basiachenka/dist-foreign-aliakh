package com.copyright.rup.dist.foreign.service.impl.matching;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expectLastCall;
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

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * Verifies {@link AaclMatchingConsumer}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 01/15/2020
 *
 * @author Stanislau Rudak
 * @author Aliaksandr Liakh
 */
public class AaclMatchingConsumerTest {

    private static final Long VALID_WR_WRK_INST = 123456789L;

    private final AaclMatchingConsumer consumer = new AaclMatchingConsumer();
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
    public void testConsumeAaclUsages() {
        Usage usage = new Usage();
        usage.setId(RupPersistUtils.generateUuid());
        usage.setWrWrkInst(VALID_WR_WRK_INST);
        usage.setStatus(UsageStatusEnum.NEW);
        List<Usage> usages = Collections.singletonList(usage);
        matchingService.matchingAaclUsages(usages);
        expectLastCall().once();
        Capture<Predicate<Usage>> predicateCapture = new Capture<>();
        matchingProcessor.executeNextChainProcessor(eq(usages), capture(predicateCapture));
        expectLastCall().once();
        replay(matchingProcessor, matchingService);
        consumer.consume(usages);
        verify(matchingProcessor, matchingService);
    }
}
