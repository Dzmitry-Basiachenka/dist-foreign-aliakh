package com.copyright.rup.dist.foreign.service.impl.chain.processor.chunk;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.HashSet;

/**
 * Verifies {@link EligibilityChunkProcessor}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 12/19/2018
 *
 * @author Uladzislau Shalamitski
 * @author Aliaksandr Liakh
 */
public class EligibilityChunkProcessorTest {

    private EligibilityChunkProcessor eligibilityProcessor;
    private IUsageRepository usageRepository;
    private IUsageAuditService usageAuditService;

    @Before
    public void setUp() {
        eligibilityProcessor = new EligibilityChunkProcessor();
        usageRepository = createMock(IUsageRepository.class);
        usageAuditService = createMock(IUsageAuditService.class);
        Whitebox.setInternalState(eligibilityProcessor, usageRepository);
        Whitebox.setInternalState(eligibilityProcessor, usageAuditService);
    }

    @Test
    public void testProcess() {
        Usage usage1 = buildUsage();
        Usage usage2 = buildUsage();
        HashSet<String> usageIds = Sets.newHashSet(usage1.getId(), usage2.getId());
        usageRepository.updateStatus(usageIds, UsageStatusEnum.ELIGIBLE);
        expectLastCall().once();
        usageAuditService.logAction(usageIds, UsageActionTypeEnum.ELIGIBLE, "Usage has become eligible");
        expectLastCall().once();
        replay(usageRepository, usageAuditService);
        eligibilityProcessor.process(Lists.newArrayList(usage1, usage2));
        verify(usageRepository, usageAuditService);
    }

    @Test
    public void testGetChainProcessorType() {
        assertEquals(ChainProcessorTypeEnum.ELIGIBILITY, eligibilityProcessor.getChainProcessorType());
    }

    private Usage buildUsage() {
        Usage usage = new Usage();
        usage.setId(RupPersistUtils.generateUuid());
        return usage;
    }
}
