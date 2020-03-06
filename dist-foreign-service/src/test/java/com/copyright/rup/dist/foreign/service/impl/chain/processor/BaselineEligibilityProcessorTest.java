package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.AaclUsage;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;

/**
 * Verifies {@link BaselineEligibilityProcessor}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 02/28/2020
 *
 * @author Anton Azarenka
 */
public class BaselineEligibilityProcessorTest {

    private BaselineEligibilityProcessor baselineEligibilityProcessor;
    private IUsageRepository usageRepository;
    private IUsageAuditService usageAuditService;

    @Before
    public void setUp() {
        baselineEligibilityProcessor = new BaselineEligibilityProcessor();
        usageRepository = createMock(IUsageRepository.class);
        usageAuditService = createMock(IUsageAuditService.class);
        Whitebox.setInternalState(baselineEligibilityProcessor, usageRepository);
        Whitebox.setInternalState(baselineEligibilityProcessor, usageAuditService);
    }

    @Test
    public void testProcessBaselineUsage() {
        Usage usage = buildUsage(true);
        usageRepository.updateStatus(Collections.singleton(usage.getId()), UsageStatusEnum.ELIGIBLE);
        expectLastCall().once();
        usageAuditService.logAction(usage.getId(), UsageActionTypeEnum.ELIGIBLE, "Usage has become eligible");
        expectLastCall().once();
        replay(usageRepository, usageAuditService);
        baselineEligibilityProcessor.process(usage);
        verify(usageRepository, usageAuditService);
    }

    @Test
    public void testProcessNotBaselineUsage() {
        Usage usage = buildUsage(false);
        replay(usageRepository, usageAuditService);
        baselineEligibilityProcessor.process(usage);
        verify(usageRepository, usageAuditService);
    }

    private Usage buildUsage(boolean flag) {
        Usage usage = new Usage();
        usage.setId(RupPersistUtils.generateUuid());
        usage.setAaclUsage(buildAaclUsage(flag));
        return usage;
    }

    private AaclUsage buildAaclUsage(boolean flag) {
        AaclUsage aaclUsage = new AaclUsage();
        aaclUsage.setBaseline(flag);
        return aaclUsage;
    }
}
