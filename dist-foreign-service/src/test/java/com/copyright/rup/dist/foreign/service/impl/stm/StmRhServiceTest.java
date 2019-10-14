package com.copyright.rup.dist.foreign.service.impl.stm;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

/**
 * Verifies {@link StmRhService}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 10/14/19
 *
 * @author Stanislau Rudak
 */
public class StmRhServiceTest {

    private static final String RH_ID = RupPersistUtils.generateUuid();
    private static final String NTS_PRODUCT_FAMILY = "NTS";

    private IPrmIntegrationService prmIntegrationServiceMock;
    private IUsageService usageServiceMock;
    private StmRhService stmRhService;

    @Before
    public void setUp() {
        prmIntegrationServiceMock = createMock(IPrmIntegrationService.class);
        usageServiceMock = createMock(IUsageService.class);
        stmRhService = new StmRhService();
        Whitebox.setInternalState(stmRhService, prmIntegrationServiceMock);
        Whitebox.setInternalState(stmRhService, usageServiceMock);
    }

    @Test
    public void testProcessStmRhWithNonStmRh() {
        Usage usage = buildUsage();
        expect(prmIntegrationServiceMock.isStmRightsholder(eq(RH_ID), eq(NTS_PRODUCT_FAMILY))).andReturn(false).once();
        usageServiceMock.updateProcessedUsage(usage);
        expectLastCall().once();
        replay(prmIntegrationServiceMock, usageServiceMock);
        stmRhService.processStmRh(usage);
        verify(prmIntegrationServiceMock, usageServiceMock);
        assertEquals(UsageStatusEnum.NON_STM_RH, usage.getStatus());
    }

    @Test
    public void testProcessStmRhWithStmRh() {
        Usage usage = buildUsage();
        expect(prmIntegrationServiceMock.isStmRightsholder(eq(RH_ID), eq(NTS_PRODUCT_FAMILY))).andReturn(true).once();
        replay(prmIntegrationServiceMock, usageServiceMock);
        stmRhService.processStmRh(usage);
        verify(prmIntegrationServiceMock, usageServiceMock);
        assertEquals(UsageStatusEnum.US_TAX_COUNTRY, usage.getStatus());
    }

    private Usage buildUsage() {
        Usage usage = new Usage();
        usage.setId(RupPersistUtils.generateUuid());
        usage.setProductFamily(NTS_PRODUCT_FAMILY);
        usage.setStatus(UsageStatusEnum.US_TAX_COUNTRY);
        usage.setRightsholder(buildRightsholder());
        return usage;
    }

    private Rightsholder buildRightsholder() {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setId(RH_ID);
        rightsholder.setAccountNumber(1000023401L);
        rightsholder.setName("American College of Physicians - Journals");
        return rightsholder;
    }
}
