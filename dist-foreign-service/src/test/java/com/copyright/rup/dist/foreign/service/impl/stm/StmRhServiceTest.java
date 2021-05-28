package com.copyright.rup.dist.foreign.service.impl.stm;

import static org.easymock.EasyMock.createMock;
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

import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link StmRhService}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 10/14/2019
 *
 * @author Stanislau Rudak
 * @author Aliaksandr Liakh
 */
public class StmRhServiceTest {

    private static final String RH_ID = RupPersistUtils.generateUuid();
    private static final String NTS_PRODUCT_FAMILY = "NTS";

    private IPrmIntegrationService prmIntegrationService;
    private IUsageService usageServiceMock;
    private StmRhService stmRhService;

    @Before
    public void setUp() {
        prmIntegrationService = createMock(IPrmIntegrationService.class);
        usageServiceMock = createMock(IUsageService.class);
        stmRhService = new StmRhService();
        Whitebox.setInternalState(stmRhService, prmIntegrationService);
        Whitebox.setInternalState(stmRhService, usageServiceMock);
    }

    @Test
    public void testProcessStmRhsWithNonStmRh() {
        Usage usage = buildUsage();
        List<Usage> usages = Collections.singletonList(usage);
        expect(prmIntegrationService.getStmRightsholderPreferenceMap(Collections.singleton(RH_ID), NTS_PRODUCT_FAMILY))
            .andReturn(ImmutableMap.of(RH_ID, false)).once();
        usageServiceMock.updateProcessedUsage(usage);
        expectLastCall().once();
        replay(prmIntegrationService, usageServiceMock);
        stmRhService.processStmRhs(usages, NTS_PRODUCT_FAMILY);
        verify(prmIntegrationService, usageServiceMock);
        assertEquals(UsageStatusEnum.NON_STM_RH, usage.getStatus());
    }

    @Test
    public void testProcessStmRhsWithStmRh() {
        Usage usage = buildUsage();
        List<Usage> usages = Collections.singletonList(usage);
        expect(prmIntegrationService.getStmRightsholderPreferenceMap(Collections.singleton(RH_ID), NTS_PRODUCT_FAMILY))
            .andReturn(ImmutableMap.of(RH_ID, true)).once();
        replay(prmIntegrationService, usageServiceMock);
        stmRhService.processStmRhs(usages, NTS_PRODUCT_FAMILY);
        verify(prmIntegrationService, usageServiceMock);
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
