package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.job.JobInfo;
import com.copyright.rup.dist.common.domain.job.JobStatusEnum;
import com.copyright.rup.dist.common.integration.camel.IProducer;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IUsageService;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Verifies {@link RhTaxProcessor}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 01/02/2019
 *
 * @author Pavel Liakh
 */
public class RhTaxProcessorTest {

    private static final String NTS_PRODUCT_FAMILY = "NTS";

    private RhTaxProcessor rhTaxProcessor;
    private IProducer<Usage> rhTaxProducerMock;
    private IUsageService usageServiceMock;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        rhTaxProcessor = new RhTaxProcessor();
        rhTaxProducerMock = createMock(IProducer.class);
        usageServiceMock = createMock(IUsageService.class);
        rhTaxProcessor.setRhTaxProducer(rhTaxProducerMock);
        rhTaxProcessor.setUsageService(usageServiceMock);
        rhTaxProcessor.setUsagesBatchSize(1000);
        rhTaxProcessor.setUsageStatus(UsageStatusEnum.RH_FOUND);
    }

    @Test
    public void testProcess() {
        Usage usage = new Usage();
        rhTaxProducerMock.send(usage);
        expectLastCall().once();
        replay(rhTaxProducerMock);
        rhTaxProcessor.process(usage);
        verify(rhTaxProducerMock);
    }

    @Test
    public void testJobProcess() {
        List<Usage> rhFoundUsages = Arrays.asList(buildUsage(), buildUsage());
        List<String> rhFoundUsageIds = rhFoundUsages.stream().map(Usage::getId).collect(Collectors.toList());
        expect(usageServiceMock.getUsageIdsByStatusAndProductFamily(
            eq(UsageStatusEnum.RH_FOUND), eq(NTS_PRODUCT_FAMILY)))
            .andReturn(rhFoundUsageIds).once();
        expect(usageServiceMock.getUsagesByIds(eq(rhFoundUsageIds)))
            .andReturn(rhFoundUsages).once();
        Capture<Usage> usageCapture1 = new Capture<>();
        rhTaxProducerMock.send(capture(usageCapture1));
        expectLastCall().once();
        Capture<Usage> usageCapture2 = new Capture<>();
        rhTaxProducerMock.send(capture(usageCapture2));
        expectLastCall().once();
        replay(usageServiceMock, rhTaxProducerMock);
        assertEquals(new JobInfo(JobStatusEnum.FINISHED, "ProductFamily=NTS, UsagesCount=2"),
            rhTaxProcessor.jobProcess(NTS_PRODUCT_FAMILY));
        verify(usageServiceMock, rhTaxProducerMock);
        assertEquals(usageCapture1.getValue(), rhFoundUsages.get(0));
        assertEquals(usageCapture2.getValue(), rhFoundUsages.get(1));
    }

    @Test
    public void testJobProcessSkipped() {
        expect(usageServiceMock.getUsageIdsByStatusAndProductFamily(
            eq(UsageStatusEnum.RH_FOUND), eq(NTS_PRODUCT_FAMILY)))
            .andReturn(Collections.emptyList()).once();
        replay(usageServiceMock, rhTaxProducerMock);
        assertEquals(new JobInfo(JobStatusEnum.SKIPPED, "ProductFamily=NTS, Reason=There are no usages"),
            rhTaxProcessor.jobProcess(NTS_PRODUCT_FAMILY));
        verify(usageServiceMock, rhTaxProducerMock);
    }

    private Usage buildUsage() {
        Usage usage = new Usage();
        usage.setId(RupPersistUtils.generateUuid());
        usage.setWrWrkInst(854030733L);
        usage.setProductFamily(NTS_PRODUCT_FAMILY);
        usage.setStatus(UsageStatusEnum.RH_FOUND);
        return usage;
    }
}
