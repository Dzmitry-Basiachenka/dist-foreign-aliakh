package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

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
 * Verifies {@link EligibilityProcessor}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 12/19/2018
 *
 * @author Uladzislau Shalamitski
 * @author Aliaksandr Liakh
 */
public class EligibilityProcessorTest {

    private EligibilityProcessor eligibilityProcessor;
    private IUsageRepository usageRepository;
    private IUsageAuditService usageAuditService;

    @Before
    public void setUp() {
        eligibilityProcessor = new EligibilityProcessor();
        usageRepository = createMock(IUsageRepository.class);
        usageAuditService = createMock(IUsageAuditService.class);
        Whitebox.setInternalState(eligibilityProcessor, usageRepository);
        Whitebox.setInternalState(eligibilityProcessor, usageAuditService);
    }

    @Test
    public void testProcess() {
        Usage usage1 = buildUsage("709a9486-6d13-41be-8df9-9327934ed53d");
        Usage usage2 = buildUsage("9f9ada01-f27f-4c75-a794-4af0ec83db04");
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

    private Usage buildUsage(String usageId) {
        Usage usage = new Usage();
        usage.setId(usageId);
        return usage;
    }
}
