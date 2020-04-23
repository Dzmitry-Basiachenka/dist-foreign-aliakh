package com.copyright.rup.dist.foreign.service.impl.chain.processor.chunk;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.AaclUsage;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;

import com.google.common.collect.Lists;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;

/**
 * Verifies {@link BaselineEligibilityChunkProcessor}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 02/28/2020
 *
 * @author Anton Azarenka
 * @author Aliaksandr Liakh
 */
public class BaselineEligibilityChunkProcessorTest {

    private BaselineEligibilityChunkProcessor baselineEligibilityProcessor;
    private IUsageRepository usageRepository;
    private IUsageAuditService usageAuditService;

    @Before
    public void setUp() {
        baselineEligibilityProcessor = new BaselineEligibilityChunkProcessor();
        usageRepository = createMock(IUsageRepository.class);
        usageAuditService = createMock(IUsageAuditService.class);
        Whitebox.setInternalState(baselineEligibilityProcessor, usageRepository);
        Whitebox.setInternalState(baselineEligibilityProcessor, usageAuditService);
    }

    @Test
    public void testProcess() {
        Usage usage1 = buildUsage(RupPersistUtils.generateUuid());
        Usage usage2 = buildUsage(null);
        usageRepository.updateStatus(Collections.singleton(usage1.getId()), UsageStatusEnum.ELIGIBLE);
        expectLastCall().once();
        usageAuditService.logAction(Collections.singleton(usage1.getId()), UsageActionTypeEnum.ELIGIBLE,
            "Usage has become eligible");
        expectLastCall().once();
        replay(usageRepository, usageAuditService);
        baselineEligibilityProcessor.process(Lists.newArrayList(usage1, usage2));
        verify(usageRepository, usageAuditService);
    }

    @Test
    public void testGetChainProcessorType() {
        assertEquals(ChainProcessorTypeEnum.ELIGIBILITY, baselineEligibilityProcessor.getChainProcessorType());
    }

    private Usage buildUsage(String baselineId) {
        Usage usage = new Usage();
        usage.setId(RupPersistUtils.generateUuid());
        usage.setAaclUsage(buildAaclUsage(baselineId));
        return usage;
    }

    private AaclUsage buildAaclUsage(String baselineId) {
        AaclUsage aaclUsage = new AaclUsage();
        aaclUsage.setBaselineId(baselineId);
        return aaclUsage;
    }
}
