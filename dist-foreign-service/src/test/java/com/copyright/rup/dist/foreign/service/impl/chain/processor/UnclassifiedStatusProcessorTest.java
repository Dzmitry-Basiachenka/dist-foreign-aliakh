package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

/**
 * Verifies {@link UnclassifiedStatusProcessor}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 03/22/2019
 *
 * @author Pavel Liakh
 */
public class UnclassifiedStatusProcessorTest {

    private UnclassifiedStatusProcessor unclassifiedStatusProcessor;
    private IUsageRepository usageRepositoryMock;

    @Before
    public void setUp() {
        unclassifiedStatusProcessor = new UnclassifiedStatusProcessor();
        usageRepositoryMock = createMock(IUsageRepository.class);
        unclassifiedStatusProcessor.setUsageRepository(usageRepositoryMock);
    }

    @Test
    public void testProcess() {
        Usage usage = new Usage();
        usage.setId(RupPersistUtils.generateUuid());
        usageRepositoryMock.updateStatus(Collections.singleton(usage.getId()), UsageStatusEnum.UNCLASSIFIED);
        expectLastCall().once();
        replay(usageRepositoryMock);
        unclassifiedStatusProcessor.process(usage);
        verify(usageRepositoryMock);
    }
}
