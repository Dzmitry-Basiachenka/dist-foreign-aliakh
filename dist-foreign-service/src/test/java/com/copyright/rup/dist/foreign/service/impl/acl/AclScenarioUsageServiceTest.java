package com.copyright.rup.dist.foreign.service.impl.acl;

import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.powermock.api.easymock.PowerMock.createMock;

import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IAclUsageRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioUsageService;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Verifies {@link AclScenarioUsageService}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 07/13/2022
 *
 * @author Ihar Suvorau
 */
public class AclScenarioUsageServiceTest {

    private static final String SCENARIO_UID = "c3077cca-09a0-454f-8b9f-bf6ecb2fbe66";
    private static final String USER_NAME = "SYSTEM";

    private IAclScenarioUsageService aclScenarioUsageService;
    private IAclUsageRepository aclUsageRepository;

    @Before
    public void setUp() {
        aclScenarioUsageService = new AclScenarioUsageService();
        aclUsageRepository = createMock(IAclUsageRepository.class);
        Whitebox.setInternalState(aclScenarioUsageService, aclUsageRepository);
    }

    @Test
    public void testAddUsagesToAclScenario() {
        AclScenario scenario = buildAclScenario();
        aclUsageRepository.addToAclScenario(scenario, USER_NAME);
        expectLastCall().once();
        replay(aclUsageRepository);
         aclScenarioUsageService.addUsagesToAclScenario(scenario, USER_NAME);
        verify(aclUsageRepository);
    }

    @Test
    public void testAddScenarioShares() {
        AclScenario scenario = buildAclScenario();
        aclUsageRepository.addScenarioShares(scenario, USER_NAME);
        expectLastCall().once();
        replay(aclUsageRepository);
        aclScenarioUsageService.addScenarioShares(scenario, USER_NAME);
        verify(aclUsageRepository);
    }

    private AclScenario buildAclScenario() {
        AclScenario aclScenario = new AclScenario();
        aclScenario.setId(SCENARIO_UID);
        aclScenario.setName("ACL Scenario name");
        aclScenario.setDescription("Description");
        aclScenario.setStatus(ScenarioStatusEnum.IN_PROGRESS);
        aclScenario.setEditableFlag(false);
        aclScenario.setPeriodEndDate(202212);
        aclScenario.setLicenseType("ACL");
        aclScenario.setCreateDate(Date.from(LocalDate.of(2022, 6, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        aclScenario.setCreateUser("user@copyright.com");
        return aclScenario;
    }
}
