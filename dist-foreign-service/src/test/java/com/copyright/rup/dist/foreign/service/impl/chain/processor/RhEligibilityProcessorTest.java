package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.integration.camel.IProducer;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link RhEligibilityProcessorUsage}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 01/02/2019
 *
 * @author Uladzislau Shalamitski
 * @author Aliaksandr Liakh
 */
public class RhEligibilityProcessorTest {

    private RhEligibilityProcessorUsage rhEligibilityProcessor;
    private IProducer<List<Usage>> rhEligibilityProducer;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        rhEligibilityProcessor = new RhEligibilityProcessorUsage();
        rhEligibilityProducer = createMock(IProducer.class);
        Whitebox.setInternalState(rhEligibilityProcessor, rhEligibilityProducer);
        rhEligibilityProcessor.setUsagesBatchSize(1000);
        rhEligibilityProcessor.setUsageStatus(UsageStatusEnum.US_TAX_COUNTRY);
    }

    @Test
    public void testProcess() {
        List<Usage> usages = buildUsages();
        rhEligibilityProducer.send(usages);
        expectLastCall().once();
        replay(rhEligibilityProducer);
        rhEligibilityProcessor.process(usages);
        verify(rhEligibilityProducer);
    }

    @Test
    public void testGetChainProcessorType() {
        assertEquals(ChainProcessorTypeEnum.RH_ELIGIBILITY, rhEligibilityProcessor.getChainProcessorType());
    }

    private List<Usage> buildUsages() {
        Usage usage = new Usage();
        usage.setId(RupPersistUtils.generateUuid());
        usage.setWrWrkInst(854030733L);
        usage.setProductFamily("NTS");
        usage.setStatus(UsageStatusEnum.US_TAX_COUNTRY);
        return Collections.singletonList(usage);
    }
}
