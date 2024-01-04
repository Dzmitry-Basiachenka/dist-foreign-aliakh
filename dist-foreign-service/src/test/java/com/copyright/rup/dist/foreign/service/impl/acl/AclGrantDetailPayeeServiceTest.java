package com.copyright.rup.dist.foreign.service.impl.acl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.AclGrantDetailDto;
import com.copyright.rup.dist.foreign.domain.RightsholderTypeOfUsePair;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;
import com.copyright.rup.dist.foreign.repository.api.IAclGrantDetailRepository;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclGrantDetailPayeeService;
import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Verifies {@link IAclGrantDetailPayeeService}.
 * <p>
 * Copyright (C) 2024 copyright.com
 * <p>
 * Date: 01/04/2024
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(RupContextUtils.class)
public class AclGrantDetailPayeeServiceTest {

    private static final String RH_ID = "b0e6b1f6-89e9-4767-b143-db0f49f32769";
    private static final Long RH_ACCOUNT_NUMBER = 2000073957L;
    private static final String PAYEE_ID = "17e52d33-a059-46c2-9ecf-dc03a60d815b";
    private static final Long PAYEE_ACCOUNT_NUMBER = 1000023408L;
    private static final String ACL_GRANT_SET_ID = "87a48751-1ee5-49a4-ae0b-3d655731bc1e";
    private static final String ACL_GRANT_DETAIL_ID = "979c5888-d768-4983-bc11-6cec49657dc3";
    private static final String PRINT_TOU = "PRINT";
    private static final String USER_NAME = "user@copyright.com";

    private IAclGrantDetailPayeeService aclGrantDetailPayeeService;
    private IAclGrantDetailRepository aclGrantDetailRepository;
    private IRightsholderService rightsholderService;
    private IPrmIntegrationService prmIntegrationService;

    @Before
    public void setUp() {
        aclGrantDetailPayeeService = new AclGrantDetailPayeeService();
        aclGrantDetailRepository = createMock(IAclGrantDetailRepository.class);
        rightsholderService = createMock(IRightsholderService.class);
        prmIntegrationService = createMock(IPrmIntegrationService.class);
        Whitebox.setInternalState(aclGrantDetailPayeeService, aclGrantDetailRepository);
        Whitebox.setInternalState(aclGrantDetailPayeeService, rightsholderService);
        Whitebox.setInternalState(aclGrantDetailPayeeService, prmIntegrationService);
    }

    @Test
    public void testPopulatePayees() {
        mockStatic(RupContextUtils.class);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setId(RH_ID);
        rightsholder.setAccountNumber(RH_ACCOUNT_NUMBER);
        RightsholderTypeOfUsePair rightsholderTypeOfUsePair = new RightsholderTypeOfUsePair();
        rightsholderTypeOfUsePair.setRightsholder(rightsholder);
        rightsholderTypeOfUsePair.setTypeOfUse(PRINT_TOU);
        expect(rightsholderService.getByAclGrantSetId(ACL_GRANT_SET_ID)).andReturn(List.of(rightsholderTypeOfUsePair))
            .once();
        Map<String, Map<String, Rightsholder>> rollUps = new HashMap<>();
        Rightsholder payee = new Rightsholder();
        payee.setId(PAYEE_ID);
        payee.setAccountNumber(PAYEE_ACCOUNT_NUMBER);
        rollUps.put(RH_ID, ImmutableMap.of("ACLPRINT", payee));
        expect(prmIntegrationService.getRollUps(Set.of(RH_ID))).andReturn(rollUps).once();
        aclGrantDetailRepository.updatePayeeAccountNumber(ACL_GRANT_SET_ID, RH_ACCOUNT_NUMBER, PRINT_TOU,
            PAYEE_ACCOUNT_NUMBER, USER_NAME);
        expectLastCall().once();
        expect(rightsholderService.updateRightsholders(Set.of(PAYEE_ACCOUNT_NUMBER))).andReturn(List.of()).once();
        replay(RupContextUtils.class, rightsholderService, prmIntegrationService, aclGrantDetailRepository);
        aclGrantDetailPayeeService.populatePayees(ACL_GRANT_SET_ID);
        verify(RupContextUtils.class, rightsholderService, prmIntegrationService, aclGrantDetailRepository);
    }

    @Test
    public void testPopulatePayeesByGrantDetails() {
        mockStatic(RupContextUtils.class);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        var grantDetailDto = new AclGrantDetailDto();
        grantDetailDto.setId(ACL_GRANT_DETAIL_ID);
        grantDetailDto.setRhAccountNumber(RH_ACCOUNT_NUMBER);
        grantDetailDto.setTypeOfUse("PRINT");
        List<AclGrantDetailDto> grantDetailDtos = List.of(grantDetailDto);
        expect(rightsholderService.findRightsholderIdsByAccountNumbers(Set.of(RH_ACCOUNT_NUMBER)))
            .andReturn(Map.of(RH_ACCOUNT_NUMBER, RH_ID)).once();
        Map<String, Map<String, Rightsholder>> rollUps = new HashMap<>();
        Rightsholder payee = new Rightsholder();
        payee.setId(PAYEE_ID);
        payee.setAccountNumber(PAYEE_ACCOUNT_NUMBER);
        rollUps.put(RH_ID, ImmutableMap.of("ACLPRINT", payee));
        expect(prmIntegrationService.getRollUps(Set.of(RH_ID))).andReturn(rollUps).once();
        expect(rightsholderService.updateRightsholders(Set.of(PAYEE_ACCOUNT_NUMBER))).andReturn(List.of()).once();
        aclGrantDetailRepository.updatePayeeAccountNumberById(grantDetailDto.getId(), PAYEE_ACCOUNT_NUMBER, USER_NAME);
        expectLastCall().once();
        replay(RupContextUtils.class, rightsholderService, prmIntegrationService, aclGrantDetailRepository);
        aclGrantDetailPayeeService.populatePayees(grantDetailDtos);
        verify(RupContextUtils.class, rightsholderService, prmIntegrationService, aclGrantDetailRepository);
    }
}
