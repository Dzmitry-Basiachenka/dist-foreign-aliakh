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
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Verifies {@link StmRhProcessor}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 10/14/19
 *
 * @author Stanislau Rudak
 */
public class StmRhProcessorTest {

    private static final String NTS_PRODUCT_FAMILY = "NTS";

    private IProducer<Usage> producerMock;
    private IUsageService usageServiceMock;
    private StmRhProcessor processor;

    @Before
    public void setUp() {
        producerMock = createMock(IProducer.class);
        usageServiceMock = createMock(IUsageService.class);
        processor = new StmRhProcessor();
        Whitebox.setInternalState(processor, "producer", producerMock);
        Whitebox.setInternalState(processor, "usageService", usageServiceMock);
        processor.setUsagesBatchSize(1000);
        processor.setUsageStatus(UsageStatusEnum.US_TAX_COUNTRY);
    }

    @Test
    public void testProcess() {
        Usage usage = buildUsage();
        producerMock.send(usage);
        expectLastCall().once();
        replay(producerMock, usageServiceMock);
        processor.process(usage);
        verify(producerMock, usageServiceMock);
    }

    @Test
    public void testJobProcess() {
        List<Usage> usTaxCountryUsages = Arrays.asList(buildUsage(), buildUsage());
        List<String> usTaxCountryUsageIds = usTaxCountryUsages.stream().map(Usage::getId).collect(Collectors.toList());
        expect(usageServiceMock.getUsageIdsByStatusAndProductFamily(
            eq(UsageStatusEnum.US_TAX_COUNTRY), eq(NTS_PRODUCT_FAMILY)))
            .andReturn(usTaxCountryUsageIds).once();
        expect(usageServiceMock.getUsagesByIds(eq(usTaxCountryUsageIds)))
            .andReturn(usTaxCountryUsages).once();
        Capture<Usage> usageCapture1 = new Capture<>();
        producerMock.send(capture(usageCapture1));
        expectLastCall().once();
        Capture<Usage> usageCapture2 = new Capture<>();
        producerMock.send(capture(usageCapture2));
        expectLastCall().once();
        replay(producerMock, usageServiceMock);
        assertEquals(new JobInfo(JobStatusEnum.FINISHED, "ProductFamily=NTS, UsagesCount=2"),
            processor.jobProcess(NTS_PRODUCT_FAMILY));
        verify(producerMock, usageServiceMock);
        assertEquals(usageCapture1.getValue(), usTaxCountryUsages.get(0));
        assertEquals(usageCapture2.getValue(), usTaxCountryUsages.get(1));
    }

    @Test
    public void testJobProcessSkipped() {
        expect(usageServiceMock.getUsageIdsByStatusAndProductFamily(
            eq(UsageStatusEnum.US_TAX_COUNTRY), eq(NTS_PRODUCT_FAMILY)))
            .andReturn(Collections.emptyList()).once();
        replay(producerMock, usageServiceMock);
        assertEquals(new JobInfo(JobStatusEnum.SKIPPED, "ProductFamily=NTS, Reason=There are no usages"),
            processor.jobProcess(NTS_PRODUCT_FAMILY));
        verify(producerMock, usageServiceMock);
    }

    @Test
    public void testGetChainProcessorType() {
        assertEquals(ChainProcessorTypeEnum.STM_RH, processor.getChainProcessorType());
    }

    private Usage buildUsage() {
        Usage usage = new Usage();
        usage.setId(RupPersistUtils.generateUuid());
        usage.setProductFamily(NTS_PRODUCT_FAMILY);
        usage.setStatus(UsageStatusEnum.US_TAX_COUNTRY);
        return usage;
    }
}
