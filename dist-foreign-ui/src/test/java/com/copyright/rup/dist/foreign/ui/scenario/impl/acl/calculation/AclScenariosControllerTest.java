package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.AclFundPool;
import com.copyright.rup.dist.foreign.domain.AclGrantSet;
import com.copyright.rup.dist.foreign.domain.AclPublicationType;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.AclScenarioDto;
import com.copyright.rup.dist.foreign.domain.AclUsageBatch;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.UsageAge;
import com.copyright.rup.dist.foreign.service.api.ILicenseeClassService;
import com.copyright.rup.dist.foreign.service.api.IPublicationTypeService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclFundPoolService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclGrantSetService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclUsageService;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioController;
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

    private static final String SCENARIO_UID = "7ed0e17d-6baf-454c-803f-1d9be3cb3192";
    private static final String BATCH_UID = "47b4d40a-a6bd-4fe1-b463-4aa52bf0e56f";
    private static final String LICENSE_TYPE = "ACL";

    private IAclScenariosWidget scenariosWidget;
    private AclScenariosController aclScenariosController;
    private IAclScenarioService aclScenarioService;
    private IAclUsageBatchService usageBatchService;
    private IAclGrantSetService grantSetService;
    private IAclFundPoolService fundPoolService;
    private IPublicationTypeService publicationTypeService;
    private IAclScenarioController aclScenarioController;
    private ILicenseeClassService licenseeClassService;
    private IAclUsageService aclUsageService;

    @Before
    public void setUp() {
        aclScenariosController = new AclScenariosController();
        scenariosWidget = createMock(IAclScenariosWidget.class);
        aclScenarioService = createMock(IAclScenarioService.class);
        usageBatchService = createMock(IAclUsageBatchService.class);
        grantSetService = createMock(IAclGrantSetService.class);
        fundPoolService = createMock(IAclFundPoolService.class);
        publicationTypeService = createMock(IPublicationTypeService.class);
        licenseeClassService = createMock(ILicenseeClassService.class);
        aclUsageService = createMock(IAclUsageService.class);
        aclScenarioController = createMock(IAclScenarioController.class);
        Whitebox.setInternalState(aclScenariosController, "widget", scenariosWidget);
        Whitebox.setInternalState(aclScenariosController, aclScenarioService);
        Whitebox.setInternalState(aclScenariosController, usageBatchService);
        Whitebox.setInternalState(aclScenariosController, grantSetService);
        Whitebox.setInternalState(aclScenariosController, fundPoolService);
        Whitebox.setInternalState(aclScenariosController, publicationTypeService);
        Whitebox.setInternalState(aclScenariosController, licenseeClassService);
        Whitebox.setInternalState(aclScenariosController, aclUsageService);
        Whitebox.setInternalState(aclScenariosController, aclScenarioController);
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
        AclScenarioDto scenario = new AclScenarioDto();
        scenario.setId(SCENARIO_UID);
        expect(aclScenarioService.getAclScenarioWithAmountsAndLastAction(scenario.getId())).andReturn(scenario).once();
        replay(aclScenarioService);
        assertSame(scenario, aclScenariosController.getAclScenarioWithAmountsAndLastAction(scenario.getId()));
        verify(aclScenarioService);
    }

    @Test
    public void testGetCriteriaHtmlRepresentation() {
        AclScenario scenario = new AclScenario();
        scenario.setUsageBatchId("c5df0a33-c734-4b56-acbe-468142c0fde1");
        scenario.setGrantSetId("34503049-9022-4c81-a70f-f8d5ff68602f");
        scenario.setFundPoolId("416ed2e4-b2e6-4a70-96fe-2182c4dc0929");
        expect(scenariosWidget.getSelectedScenario()).andReturn(scenario).once();
        AclUsageBatch usageBatch = new AclUsageBatch();
        usageBatch.setName("ACL Usage Batch");
        expect(usageBatchService.getById(scenario.getUsageBatchId())).andReturn(usageBatch).once();
        AclGrantSet grantSet = new AclGrantSet();
        grantSet.setName("ACL Grant Set");
        expect(grantSetService.getById(scenario.getGrantSetId())).andReturn(grantSet).once();
        AclFundPool fundPool = new AclFundPool();
        fundPool.setName("ACL Fund Pool");
        expect(fundPoolService.getById(scenario.getFundPoolId())).andReturn(fundPool).once();
        replay(scenariosWidget, usageBatchService, grantSetService, fundPoolService);
        assertEquals("<b>Selection Criteria:</b><ul>" +
            "<li><b><i>Usage Batch </i></b>(ACL Usage Batch)</li>" +
            "<li><b><i>Grant Set </i></b>(ACL Grant Set)</li>" +
            "<li><b><i>Fund Pool </i></b>(ACL Fund Pool)</li>" +
            "</ul>", aclScenariosController.getCriteriaHtmlRepresentation());
        verify(scenariosWidget, usageBatchService, grantSetService, fundPoolService);
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

    @Test
    public void testGetDetailLicenseeClasses() {
        List<DetailLicenseeClass> licenseeClasses = Collections.singletonList(new DetailLicenseeClass());
        expect(licenseeClassService.getDetailLicenseeClasses("ACL")).andReturn(licenseeClasses).once();
        replay(licenseeClassService);
        assertSame(licenseeClasses, aclScenariosController.getDetailLicenseeClasses());
        verify(licenseeClassService);
    }

    @Test
    public void testCreateAclScenario() {
        List<DetailLicenseeClass> detailLicenseeClasses = Collections.emptyList();
        List<UsageAge> usageAges = Collections.emptyList();
        AclScenario scenario = new AclScenario();
        scenario.setDetailLicenseeClasses(detailLicenseeClasses);
        scenario.setUsageAges(usageAges);
        aclScenarioService.insertScenario(scenario);
        expectLastCall().once();
        expect(licenseeClassService.getDetailLicenseeClasses("ACL")).andReturn(detailLicenseeClasses).once();
        expect(aclUsageService.getDefaultUsageAgesWeights()).andReturn(usageAges).once();
        replay(aclScenarioService, licenseeClassService, aclUsageService);
        aclScenariosController.createAclScenario(scenario);
        verify(aclScenarioService, licenseeClassService, aclUsageService);
    }

    @Test
    public void testGetAclScenarioController() {
        assertSame(aclScenarioController, aclScenariosController.getAclScenarioController());
    }

    @Test
    public void testIsValidUsageBatch() {
        expect(aclUsageService.getCountInvalidUsages(BATCH_UID, "38931a03-d5a3-4576-99d3-722ef1ae49f9", 202212,
            Collections.singletonList(0))).andReturn(0).once();
        replay(aclUsageService);
        assertTrue(aclScenariosController.isValidUsageBatch(BATCH_UID, "38931a03-d5a3-4576-99d3-722ef1ae49f9", 202212,
            Collections.singletonList(0)));
        verify(aclUsageService);
    }

    @Test
    public void testGetAclHistoricalPublicationTypes() {
        List<AclPublicationType> publicationTypes = Collections.emptyList();
        expect(publicationTypeService.getAclHistoricalPublicationTypes()).andReturn(publicationTypes).once();
        replay(publicationTypeService);
        assertSame(publicationTypes, aclScenariosController.getAclHistoricalPublicationTypes());
        verify(publicationTypeService);
    }

    private AclScenario buildAclScenario() {
        AclScenario aclScenario = new AclScenario();
        aclScenario.setId(SCENARIO_UID);
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
