package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;

/**
 * Verifies {@link RhNotFoundProcessor}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 05/23/2019
 *
 * @author Ihar Suvorau
 */
public class RhNotFoundProcessorTest {

    private static final String BATCH_ID = RupPersistUtils.generateUuid();
    private static final String USAGE_ID = RupPersistUtils.generateUuid();
    private static final String AUDIT_MESSAGE =
        "Detail was made eligible for NTS because sum of gross amounts, grouped by Wr Wrk Inst, is less than $100";
    private final RhNotFoundProcessor processor = new RhNotFoundProcessor();

    private IUsageService usageService;
    private IUsageAuditService auditService;

    @Before
    public void setUp() {
        usageService = createMock(IUsageService.class);
        auditService = createMock(IUsageAuditService.class);
        Whitebox.setInternalState(processor, usageService);
        Whitebox.setInternalState(processor, auditService);
    }

    @Test
    public void testProcessAboveMinimum() {
        expect(usageService.getTotalAmountByWrWrkInstAndBatchId(123456789L, BATCH_ID))
            .andReturn(new BigDecimal("50.00")).once();
        Capture<Usage> usageCapture = new Capture<>();
        usageService.updateProcessedUsage(capture(usageCapture));
        expectLastCall().once();
        auditService.logAction(USAGE_ID, UsageActionTypeEnum.ELIGIBLE_FOR_NTS, AUDIT_MESSAGE);
        expectLastCall().once();
        replay(usageService, auditService);
        processor.process(buildUsage());
        Usage actual = usageCapture.getValue();
        assertEquals(UsageStatusEnum.NTS_WITHDRAWN, actual.getStatus());
        assertEquals("NTS", actual.getProductFamily());
        verify(usageService, auditService);
    }

    @Test
    public void testProcessBelowMinimum() {
        expect(usageService.getTotalAmountByWrWrkInstAndBatchId(123456789L, BATCH_ID))
            .andReturn(new BigDecimal("100.00")).once();
        replay(usageService, auditService);
        processor.process(buildUsage());
        verify(usageService, auditService);
    }

    private Usage buildUsage() {
        Usage usage = new Usage();
        usage.setId(USAGE_ID);
        usage.setBatchId(BATCH_ID);
        usage.setWrWrkInst(123456789L);
        return usage;
    }
}
