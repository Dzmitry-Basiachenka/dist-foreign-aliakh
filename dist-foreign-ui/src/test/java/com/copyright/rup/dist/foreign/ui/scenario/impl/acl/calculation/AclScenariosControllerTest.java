package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.AclFundPool;
import com.copyright.rup.dist.foreign.domain.AclGrantSet;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.AclUsageBatch;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.service.api.acl.IAclFundPoolService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclGrantSetService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclUsageBatchService;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenariosWidget;

import com.google.common.collect.Sets;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Verifies {@link AclScenariosController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/22/2022
 *
 * @author Dzmitry Basiahenka
 */
public class AclScenariosControllerTest {

    private static final String LICENSE_TYPE = "ACL";

    private AclScenariosController aclScenariosController;
    private IAclScenarioService aclScenarioService;
    private IAclUsageBatchService usageBatchService;
    private IAclGrantSetService grantSetService;
    private IAclFundPoolService fundPoolService;

    @Before
    public void setUp() {
        aclScenariosController = new AclScenariosController();
        aclScenarioService = createMock(IAclScenarioService.class);
        usageBatchService = createMock(IAclUsageBatchService.class);
        grantSetService = createMock(IAclGrantSetService.class);
        fundPoolService = createMock(IAclFundPoolService.class);
        Whitebox.setInternalState(aclScenariosController, aclScenarioService);
        Whitebox.setInternalState(aclScenariosController, usageBatchService);
        Whitebox.setInternalState(aclScenariosController, grantSetService);
        Whitebox.setInternalState(aclScenariosController, fundPoolService);
    }

    @Test
    public void testGetScenarios() {
        List<AclScenario> scenarios = Collections.singletonList(buildAclScenario());
        expect(aclScenarioService.getScenarios()).andReturn(scenarios).once();
        replay(aclScenarioService);
        assertSame(scenarios, aclScenariosController.getScenarios());
        verify(aclScenarioService);
    }

    @Test
    public void testInstantiateWidget() {
        IAclScenariosWidget widget = aclScenariosController.instantiateWidget();
        assertNotNull(widget);
        assertEquals(AclScenariosWidget.class, widget.getClass());
    }

    @Test
    public void testGetScenarioWithAmountsAndLastAction() {
        // TODO {aliakh} implement
    }

    @Test
    public void testGetCriteriaHtmlRepresentation() {
        // TODO {aliakh} implement
    }

    @Test
    public void testGetFundPoolsByLicenseTypeAndPeriod() {
        List<AclFundPool> fundPools = Collections.singletonList(buildFundPool());
        expect(fundPoolService.getFundPoolsByLicenseTypeAndPeriod(LICENSE_TYPE, 202212)).andReturn(fundPools).once();
        replay(fundPoolService);
        assertSame(fundPools, aclScenariosController.getFundPoolsByLicenseTypeAndPeriod(LICENSE_TYPE, 202212));
        verify(fundPoolService);
    }

    @Test
    public void testGetUsageBatchesByPeriod() {
        List<AclUsageBatch> usageBatches = Collections.singletonList(buildAclUsageBatch());
        expect(usageBatchService.getUsageBatchesByPeriod(202212, true)).andReturn(usageBatches).once();
        replay(usageBatchService);
        assertSame(usageBatches, aclScenariosController.getUsageBatchesByPeriod(202212, true));
        verify(usageBatchService);
    }

    @Test
    public void testAclScenarioExists() {
        expect(aclScenarioService.aclScenarioExists("ACL Scenario 2022")).andReturn(true).once();
        replay(aclScenarioService);
        assertTrue(aclScenariosController.aclScenarioExists("ACL Scenario 2022"));
        verify(aclScenarioService);
    }

    @Test
    public void testGetGrantSetsByLicenseTypeAndPeriod() {
        List<AclGrantSet> grantSets = Collections.singletonList(buildAclGrantSet());
        expect(grantSetService.getGrantSetsByLicenseTypeAndPeriod(LICENSE_TYPE, 202212, true)).andReturn(grantSets)
            .once();
        replay(grantSetService);
        assertSame(grantSets, aclScenariosController.getGrantSetsByLicenseTypeAndPeriod(LICENSE_TYPE, 202212, true));
        verify(grantSetService);
    }

    @Test
    public void testGetAclUsagePeriods() {
        List<Integer> periods = Collections.singletonList(202106);
        expect(usageBatchService.getPeriods()).andReturn(periods).once();
        replay(usageBatchService);
        assertEquals(periods, aclScenariosController.getAllPeriods());
        verify(usageBatchService);
    }

    private AclScenario buildAclScenario() {
        AclScenario aclScenario = new AclScenario();
        aclScenario.setId("7ed0e17d-6baf-454c-803f-1d9be3cb3192");
        aclScenario.setName("ACL Scenario name");
        aclScenario.setDescription("Description");
        aclScenario.setStatus(ScenarioStatusEnum.IN_PROGRESS);
        aclScenario.setEditableFlag(false);
        aclScenario.setPeriodEndDate(202212);
        aclScenario.setLicenseType(LICENSE_TYPE);
        aclScenario.setCreateDate(Date.from(LocalDate.of(2022, 6, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        aclScenario.setCreateUser("user@copyright.com");
        return aclScenario;
    }

    private AclGrantSet buildAclGrantSet() {
        AclGrantSet grantSet = new AclGrantSet();
        grantSet.setName("ACL_GRANT_SET_NAME");
        grantSet.setGrantPeriod(202112);
        grantSet.setPeriods(Sets.newHashSet(202106, 202112));
        grantSet.setLicenseType(LICENSE_TYPE);
        grantSet.setEditable(true);
        return grantSet;
    }

    private AclFundPool buildFundPool() {
        AclFundPool aclFundPool = new AclFundPool();
        aclFundPool.setName("Fund Pool Name");
        aclFundPool.setManualUploadFlag(true);
        aclFundPool.setLicenseType(LICENSE_TYPE);
        return aclFundPool;
    }

    private AclUsageBatch buildAclUsageBatch() {
        AclUsageBatch usageBatch = new AclUsageBatch();
        usageBatch.setName("ACL_USAGE_BATCH_NAME");
        usageBatch.setDistributionPeriod(202112);
        usageBatch.setPeriods(Sets.newHashSet(202106, 202112));
        usageBatch.setEditable(true);
        return usageBatch;
    }
}
