package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
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
 * Verifies {@link EligibilityProcessor}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 12/19/2018
 *
 * @author Uladzislau Shalamitski
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
        Usage usage = buildUsage();
        usageRepository.updateStatus(Collections.singleton(usage.getId()), UsageStatusEnum.ELIGIBLE);
        expectLastCall().once();
        usageAuditService
            .logAction(usage.getId(), UsageActionTypeEnum.ELIGIBLE, "Usage has become eligible");
        expectLastCall().once();
        replay(usageRepository, usageAuditService);
        eligibilityProcessor.process(usage);
        verify(usageRepository, usageAuditService);
    }

    private Usage buildUsage() {
        Usage usage = new Usage();
        usage.setId(RupPersistUtils.generateUuid());
        return usage;
    }
}
