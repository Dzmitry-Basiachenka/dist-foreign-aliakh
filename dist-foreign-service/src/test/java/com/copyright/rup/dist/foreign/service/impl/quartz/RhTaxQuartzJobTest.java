package com.copyright.rup.dist.foreign.service.impl.quartz;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.integration.camel.IProducer;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.service.api.IUsageService;

import org.easymock.Capture;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Verifies {@link RhTaxJob}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 12/05/2018
 *
 * @author Pavel Liakh
 */
public class RhTaxQuartzJobTest {

    @Test
    public void testExecuteInternal() {
        IUsageService usageServiceMock = createMock(IUsageService.class);
        IProducer<Usage> rhTaxProducerMock = createMock(IProducer.class);
        RhTaxJob rhTaxJob = new RhTaxJob();
        rhTaxJob.setRhTaxProducer(rhTaxProducerMock);
        rhTaxJob.setUsageService(usageServiceMock);
        Capture<UsageFilter> usageFilterCapture = new Capture<>();
        List<Usage> ntsRhFoundUsages = Arrays.asList(buildUsage(), buildUsage());
        expect(usageServiceMock.getUsagesByFilter(capture(usageFilterCapture))).andReturn(ntsRhFoundUsages).once();
        Capture<Usage> usageCapture1 = new Capture<>();
        rhTaxProducerMock.send(capture(usageCapture1));
        expectLastCall().once();
        Capture<Usage> usageCapture2 = new Capture<>();
        rhTaxProducerMock.send(capture(usageCapture2));
        expectLastCall().once();

        replay(usageServiceMock, rhTaxProducerMock);
        rhTaxJob.executeInternal(null);
        verify(usageServiceMock, rhTaxProducerMock);
        assertEquals(usageCapture1.getValue(), ntsRhFoundUsages.get(0));
        assertEquals(usageCapture2.getValue(), ntsRhFoundUsages.get(1));
    }

    private Usage buildUsage() {
        Usage usage = new Usage();
        usage.setId(RupPersistUtils.generateUuid());
        usage.setWrWrkInst(854030733L);
        usage.setProductFamily("NTS");
        usage.setStatus(UsageStatusEnum.RH_FOUND);
        return usage;
    }
}
