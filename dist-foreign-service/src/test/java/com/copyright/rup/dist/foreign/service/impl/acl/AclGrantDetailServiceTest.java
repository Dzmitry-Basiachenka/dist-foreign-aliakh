package com.copyright.rup.dist.foreign.service.impl.acl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.AclGrantDetail;
import com.copyright.rup.dist.foreign.domain.AclGrantDetailDto;
import com.copyright.rup.dist.foreign.domain.AclGrantSet;
import com.copyright.rup.dist.foreign.domain.AclIneligibleRightsholder;
import com.copyright.rup.dist.foreign.domain.RightsholderTypeOfUsePair;
import com.copyright.rup.dist.foreign.domain.filter.AclGrantDetailFilter;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;
import com.copyright.rup.dist.foreign.repository.api.IAclGrantDetailRepository;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclGrantDetailService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmBaselineService;

import com.google.common.collect.ImmutableMap;

import org.apache.camel.util.concurrent.SynchronousExecutorService;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Verifies {@link AclGrantDetailService}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/28/2022
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(RupContextUtils.class)
public class AclGrantDetailServiceTest {

    private static final String RH_ID = "b0e6b1f6-89e9-4767-b143-db0f49f32769";
    private static final Long RH_ACCOUNT_NUMBER = 2000073957L;
    private static final String PAYEE_ID = "17e52d33-a059-46c2-9ecf-dc03a60d815b";
    private static final Long PAYEE_ACCOUNT_NUMBER = 1000023408L;
    private static final String ACL_GRANT_SET_ID = "87a48751-1ee5-49a4-ae0b-3d655731bc1e";
    private static final String ACL_GRANT_SET_NAME = "ACL Grant Set 2021";
    private static final String ACL_GRANT_ID_1 = "979c5888-d768-4983-bc11-6cec49657dc3";
    private static final String ACL_GRANT_ID_2 = "fe1d13bd-ce53-4bdc-84dd-8e505b734ac9";
    private static final String ACL_GRANT_ID_3 = "21afbfa6-daa5-458c-8501-2d056eaa5181";
    private static final String ACL_GRANT_ID_4 = "5384c03b-4a32-48c8-b56b-c6d430448bd6";
    private static final String PRINT_TOU = "PRINT";
    private static final String DIGITAL_TOU = "DIGITAL";
    private static final String GRANT_STATUS = "GRANT";
    private static final String ACL = "ACL";
    private static final String PRINT_DIGITAL_TOU_STATUS = "Print&Digital";
    private static final String DIFFERENT_RH_TOU_STATUS = "Different RH";
    private static final String USER_NAME = "user@copyright.com";

    private IAclGrantDetailService aclGrantDetailService;
    private IAclGrantDetailRepository aclGrantDetailRepository;
    private IRightsholderService rightsholderService;
    private IUdmBaselineService udmBaselineService;
    private IPrmIntegrationService prmIntegrationService;

    @Before
    public void setUp() {
        aclGrantDetailService = new AclGrantDetailService();
        Whitebox.setInternalState(aclGrantDetailService, "executorService", new SynchronousExecutorService());
        aclGrantDetailRepository = createMock(IAclGrantDetailRepository.class);
        rightsholderService = createMock(IRightsholderService.class);
        udmBaselineService = createMock(IUdmBaselineService.class);
        prmIntegrationService = createMock(IPrmIntegrationService.class);
        Whitebox.setInternalState(aclGrantDetailService, aclGrantDetailRepository);
        Whitebox.setInternalState(aclGrantDetailService, rightsholderService);
        Whitebox.setInternalState(aclGrantDetailService, udmBaselineService);
        Whitebox.setInternalState(aclGrantDetailService, prmIntegrationService);
    }

    @Test
    public void testInsert() {
        AclGrantDetail grantDetail = new AclGrantDetail();
        aclGrantDetailRepository.insert(grantDetail);
        expectLastCall().once();
        replay(aclGrantDetailRepository);
        aclGrantDetailService.insert(List.of(grantDetail));
        verify(aclGrantDetailRepository);
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
        aclGrantDetailService.populatePayees(ACL_GRANT_SET_ID);
        verify(RupContextUtils.class, rightsholderService, prmIntegrationService, aclGrantDetailRepository);
    }

    @Test
    public void testPopulatePayeesAsync() {
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
        aclGrantDetailService.populatePayeesAsync(ACL_GRANT_SET_ID);
        verify(RupContextUtils.class, rightsholderService, prmIntegrationService, aclGrantDetailRepository);
    }

    @Test
    public void testPopulatePayeesByGrantDetails() {
        mockStatic(RupContextUtils.class);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        var grantDetailDto = new AclGrantDetailDto();
        grantDetailDto.setId(ACL_GRANT_ID_1);
        grantDetailDto.setRhAccountNumber(RH_ACCOUNT_NUMBER);
        grantDetailDto.setTypeOfUse("PRINT");
        List<AclGrantDetailDto> grantDetailDtos = List.of(grantDetailDto);
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setId(RH_ID);
        rightsholder.setAccountNumber(RH_ACCOUNT_NUMBER);
        expect(rightsholderService.updateRightsholders(Set.of(RH_ACCOUNT_NUMBER))).andReturn(List.of(rightsholder))
            .once();
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
        aclGrantDetailService.populatePayees(grantDetailDtos);
        verify(RupContextUtils.class, rightsholderService, prmIntegrationService, aclGrantDetailRepository);
    }

    @Test
    public void testGetCount() {
        AclGrantDetailFilter filter = new AclGrantDetailFilter();
        filter.setGrantSetNames(Set.of(ACL_GRANT_SET_NAME));
        expect(aclGrantDetailRepository.findCountByFilter(filter)).andReturn(1).once();
        replay(aclGrantDetailRepository);
        assertEquals(1, aclGrantDetailService.getCount(filter));
        verify(aclGrantDetailRepository);
    }

    @Test
    public void testGetCountEmptyFilter() {
        assertEquals(0, aclGrantDetailService.getCount(new AclGrantDetailFilter()));
    }

    @Test
    public void testGetDtos() {
        List<AclGrantDetailDto> grantDetails = List.of(new AclGrantDetailDto());
        Pageable pageable = new Pageable(0, 1);
        Sort sort = new Sort("licenseType", Sort.Direction.ASC);
        AclGrantDetailFilter filter = new AclGrantDetailFilter();
        filter.setGrantSetNames(Set.of(ACL_GRANT_SET_NAME));
        expect(aclGrantDetailRepository.findDtosByFilter(filter, pageable, sort)).andReturn(grantDetails).once();
        replay(aclGrantDetailRepository);
        assertSame(grantDetails, aclGrantDetailService.getDtos(filter, pageable, sort));
        verify(aclGrantDetailRepository);
    }

    @Test
    public void testGetDtosEmptyFilter() {
        List<AclGrantDetailDto> result = aclGrantDetailService.getDtos(new AclGrantDetailFilter(), null, null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testUpdateGrants() {
        mockStatic(RupContextUtils.class);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        var grantDetailDto = new AclGrantDetailDto();
        grantDetailDto.setGrantStatus("Grant");
        aclGrantDetailRepository.updateGrant(grantDetailDto);
        expectLastCall().once();
        replay(aclGrantDetailRepository, RupContextUtils.class);
        aclGrantDetailService.updateGrants(Set.of(grantDetailDto), false);
        assertEquals(USER_NAME, grantDetailDto.getUpdateUser());
        verify(aclGrantDetailRepository, RupContextUtils.class);
    }

    @Test
    public void testAddToGrantSet() {
        Capture<AclGrantDetail> grant1 = EasyMock.newCapture();
        Capture<AclGrantDetail> grant2 = EasyMock.newCapture();
        Capture<AclGrantDetail> grant3 = EasyMock.newCapture();
        Capture<AclGrantDetailDto> grantDto1 = EasyMock.newCapture();
        Capture<AclGrantDetailDto> grantDto2 = EasyMock.newCapture();
        Capture<AclGrantDetailDto> grantDto3 = EasyMock.newCapture();
        Capture<AclGrantDetailDto> grantDto4 = EasyMock.newCapture();
        List<AclGrantDetailDto> aclGrantDetailDtos = List.of(
            buildGrantDto(ACL_GRANT_ID_1, 123456789L, null, 2000072827L, DIGITAL_TOU, GRANT_STATUS, true),
            buildGrantDto(ACL_GRANT_ID_2, 123456789L, null, 2000070936L, PRINT_TOU, GRANT_STATUS, true),
            buildGrantDto(ACL_GRANT_ID_3, 232167525L, null, 1000014080L, DIGITAL_TOU, GRANT_STATUS, false));
        AclGrantSet grantSet = buildGrantSet();
        expect(prmIntegrationService.getIneligibleRightsholders(LocalDate.of(2021, 12, 31), ACL))
            .andReturn(Set.of(buildIneligibleRightsholder())).once();
        aclGrantDetailRepository.insert(capture(grant1));
        expectLastCall().once();
        aclGrantDetailRepository.insert(capture(grant2));
        expectLastCall().once();
        aclGrantDetailRepository.insert(capture(grant3));
        expectLastCall().once();
        expect(udmBaselineService.getWrWrkInstToSystemTitles(grantSet.getPeriods())).andReturn(
            ImmutableMap.of(
                123456789L, "BIOCHEMISTRY (MOSCOW)",
                306985867L, "Tenside, surfactants, detergents",
                232167525L, "100 ROAD MOVIES")).once();
        aclGrantDetailRepository.updateGrant(capture(grantDto1));
        expectLastCall().once();
        aclGrantDetailRepository.updateGrant(capture(grantDto2));
        expectLastCall().once();
        expect(aclGrantDetailRepository.findPairForGrantById(ACL_GRANT_ID_3)).andReturn(
            buildGrantDto(ACL_GRANT_ID_4, 232167525L, "Print Only", 1000014080L, PRINT_TOU, GRANT_STATUS, true)).once();
        aclGrantDetailRepository.updateGrant(capture(grantDto3));
        expectLastCall().once();
        aclGrantDetailRepository.updateGrant(capture(grantDto4));
        expectLastCall().once();
        expect(rightsholderService.updateRightsholders(Set.of(1000014080L, 2000072827L, 2000070936L)))
            .andReturn(List.of()).once();
        replay(aclGrantDetailRepository, rightsholderService, udmBaselineService, prmIntegrationService);
        aclGrantDetailService.addToGrantSet(grantSet, aclGrantDetailDtos);
        verifyGrantCapture(grant1,
            buildGrant(ACL_GRANT_ID_1, 123456789L, "BIOCHEMISTRY (MOSCOW)", 2000072827L, DIGITAL_TOU, true));
        verifyGrantCapture(grant2,
            buildGrant(ACL_GRANT_ID_2, 123456789L, "BIOCHEMISTRY (MOSCOW)", 2000070936L, PRINT_TOU, true));
        verifyGrantCapture(grant3,
            buildGrant(ACL_GRANT_ID_3, 232167525L, "100 ROAD MOVIES", 1000014080L, DIGITAL_TOU, false));
        verifyGrantDtoCapture(grantDto1, buildGrantDto(ACL_GRANT_ID_3, 232167525L, PRINT_DIGITAL_TOU_STATUS,
            1000014080L, DIGITAL_TOU, GRANT_STATUS, false));
        verifyGrantDtoCapture(grantDto2, buildGrantDto(ACL_GRANT_ID_4, 232167525L, PRINT_DIGITAL_TOU_STATUS,
            1000014080L, PRINT_TOU, GRANT_STATUS, true));
        verifyGrantDtoCapture(grantDto3, buildGrantDto(ACL_GRANT_ID_1, 123456789L, DIFFERENT_RH_TOU_STATUS,
            2000072827L, DIGITAL_TOU, GRANT_STATUS, true));
        verifyGrantDtoCapture(grantDto4, buildGrantDto(ACL_GRANT_ID_2, 123456789L, DIFFERENT_RH_TOU_STATUS,
            2000070936L, PRINT_TOU, GRANT_STATUS, true));
        verify(aclGrantDetailRepository, rightsholderService, udmBaselineService, prmIntegrationService);
    }

    @Test
    public void testUpdateGrantWithUpdatedRh() {
        mockStatic(RupContextUtils.class);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        Capture<AclGrantDetailDto> grant1 = EasyMock.newCapture();
        Capture<AclGrantDetailDto> grant2 = EasyMock.newCapture();
        Capture<AclGrantDetailDto> grant3 = EasyMock.newCapture();
        Capture<AclGrantDetailDto> grant4 = EasyMock.newCapture();
        Capture<AclGrantDetailDto> grant5 = EasyMock.newCapture();
        Capture<AclGrantDetailDto> grant6 = EasyMock.newCapture();
        expect(aclGrantDetailRepository.findPairForGrantById(ACL_GRANT_ID_2)).andReturn(
            buildGrantDto(ACL_GRANT_ID_1, 123456789L, PRINT_DIGITAL_TOU_STATUS, 2000070936L, PRINT_TOU,
                GRANT_STATUS, true)).once();
        expect(aclGrantDetailRepository.findPairForGrantById("cb75a763-9317-4b8f-9f1c-d2c5f6c59a0c")).andReturn(
            buildGrantDto(ACL_GRANT_ID_3, 306985867L, PRINT_DIGITAL_TOU_STATUS, 1000014080L, DIGITAL_TOU,
                GRANT_STATUS, true)).once();
        expect(aclGrantDetailRepository.findPairForGrantById("20e05f23-eeef-45fd-89d2-1bc87efa98df")).andReturn(
            buildGrantDto(ACL_GRANT_ID_4, 232167525L, DIFFERENT_RH_TOU_STATUS, 2000072827L, PRINT_TOU,
                GRANT_STATUS, true)).once();
        aclGrantDetailRepository.updateGrant(capture(grant1));
        expectLastCall().once();
        aclGrantDetailRepository.updateGrant(capture(grant2));
        expectLastCall().once();
        aclGrantDetailRepository.updateGrant(capture(grant3));
        expectLastCall().once();
        aclGrantDetailRepository.updateGrant(capture(grant4));
        expectLastCall().once();
        aclGrantDetailRepository.updateGrant(capture(grant5));
        expectLastCall().once();
        aclGrantDetailRepository.updateGrant(capture(grant6));
        expectLastCall().once();
        expect(rightsholderService.updateRightsholders(Set.of(2000072827L))).andReturn(List.of()).once();
        replay(aclGrantDetailRepository, rightsholderService, RupContextUtils.class);
        List<AclGrantDetailDto> aclGrantDetailDtos = List.of(
            buildGrantDto(ACL_GRANT_ID_2, 123456789L, PRINT_DIGITAL_TOU_STATUS, 2000072827L, DIGITAL_TOU, GRANT_STATUS,
                true),
            buildGrantDto("cb75a763-9317-4b8f-9f1c-d2c5f6c59a0c", 306985867L, PRINT_DIGITAL_TOU_STATUS, 2000072827L,
                PRINT_TOU, GRANT_STATUS, true),
            buildGrantDto("20e05f23-eeef-45fd-89d2-1bc87efa98df", 232167525L, DIFFERENT_RH_TOU_STATUS, 2000072827L,
                DIGITAL_TOU, GRANT_STATUS, true)
        );
        aclGrantDetailService.updateGrants(new HashSet<>(aclGrantDetailDtos), true);
        verifyGrantDtoCapture(grant1, buildGrantDto("20e05f23-eeef-45fd-89d2-1bc87efa98df", 232167525L,
            PRINT_DIGITAL_TOU_STATUS, 2000072827L, DIGITAL_TOU, GRANT_STATUS, true));
        verifyGrantDtoCapture(grant3, buildGrantDto("cb75a763-9317-4b8f-9f1c-d2c5f6c59a0c", 306985867L,
            DIFFERENT_RH_TOU_STATUS, 2000072827L, PRINT_TOU, GRANT_STATUS, true));
        verifyGrantDtoCapture(grant6, buildGrantDto(ACL_GRANT_ID_1, 123456789L, DIFFERENT_RH_TOU_STATUS, 2000070936L,
            PRINT_TOU, GRANT_STATUS, true));
        verifyGrantDtoCapture(grant4, buildGrantDto(ACL_GRANT_ID_3, 306985867L, DIFFERENT_RH_TOU_STATUS, 1000014080L,
            DIGITAL_TOU, GRANT_STATUS, true));
        verifyGrantDtoCapture(grant2, buildGrantDto(ACL_GRANT_ID_4, 232167525L, PRINT_DIGITAL_TOU_STATUS, 2000072827L,
            PRINT_TOU, GRANT_STATUS, true));
        verifyGrantDtoCapture(grant5, buildGrantDto(ACL_GRANT_ID_2, 123456789L, DIFFERENT_RH_TOU_STATUS, 2000072827L,
            DIGITAL_TOU, GRANT_STATUS, true));
        verify(aclGrantDetailRepository, rightsholderService, RupContextUtils.class);
    }

    @Test
    public void testUpdateGrantWithStatus() {
        mockStatic(RupContextUtils.class);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        AclGrantDetailDto grantDetailToUpdate = buildGrantDto(
            ACL_GRANT_ID_1, 123456789L, PRINT_DIGITAL_TOU_STATUS, 2000072827L, PRINT_TOU, GRANT_STATUS, true);
        Capture<AclGrantDetailDto> grantDetailDtoCapture1 = EasyMock.newCapture();
        Capture<AclGrantDetailDto> grantDetailDtoCapture2 = EasyMock.newCapture();
        expect(aclGrantDetailRepository.findPairForGrantById(ACL_GRANT_ID_2)).andReturn(grantDetailToUpdate).once();
        aclGrantDetailRepository.updateGrant(capture(grantDetailDtoCapture1));
        expectLastCall().once();
        aclGrantDetailRepository.updateGrant(capture(grantDetailDtoCapture2));
        expectLastCall().once();
        expect(rightsholderService.updateRightsholders(Set.of(2000072827L))).andReturn(List.of()).once();
        replay(aclGrantDetailRepository, rightsholderService, RupContextUtils.class);
        aclGrantDetailService.updateGrants(Set.of(buildGrantDto(ACL_GRANT_ID_2, 123456789L,
            PRINT_DIGITAL_TOU_STATUS, 2000072827L, DIGITAL_TOU, "DENY", true)), true);
        verifyGrantDtoCapture(grantDetailDtoCapture2,
            buildGrantDto(ACL_GRANT_ID_1, 123456789L, "Print Only", 2000072827L, PRINT_TOU, GRANT_STATUS, true));
        verifyGrantDtoCapture(grantDetailDtoCapture1,
            buildGrantDto(ACL_GRANT_ID_2, 123456789L, PRINT_DIGITAL_TOU_STATUS, 2000072827L, DIGITAL_TOU, "DENY",
                true));
        verify(aclGrantDetailRepository, rightsholderService, RupContextUtils.class);
    }

    @Test
    public void testUpdateSingleGrant() {
        mockStatic(RupContextUtils.class);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        expect(aclGrantDetailRepository.findPairForGrantById(ACL_GRANT_ID_2)).andReturn(null).once();
        Capture<AclGrantDetailDto> grantDetailDtoCapture1 = EasyMock.newCapture();
        aclGrantDetailRepository.updateGrant(capture(grantDetailDtoCapture1));
        expectLastCall().once();
        AclGrantDetailDto expectedGrantForUpdate =
            buildGrantDto(ACL_GRANT_ID_2, 123456789L, PRINT_DIGITAL_TOU_STATUS, 2000072827L, DIGITAL_TOU, "DENY", true);
        replay(aclGrantDetailRepository, RupContextUtils.class);
        aclGrantDetailService.updateGrants(Set.of(expectedGrantForUpdate), true);
        verifyGrantDtoCapture(grantDetailDtoCapture1, expectedGrantForUpdate);
        verify(aclGrantDetailRepository, RupContextUtils.class);
    }

    @Test
    public void testSetEligibleFlag() {
        Set<AclIneligibleRightsholder> ineligibleRightsholders = Set.of(buildIneligibleRightsholder());
        expect(prmIntegrationService.getIneligibleRightsholders(LocalDate.of(2020, 1, 1), ACL))
            .andReturn(ineligibleRightsholders).once();
        List<AclGrantDetail> grantDetailDtos = List.of(
            buildGrant("20f14f70-8f35-478d-98d9-dde62ab5754c", 1000000001L, "100 ROAD MOVIES", 1000014080L, DIGITAL_TOU,
                true),
            buildGrant("52cc9af9-4e35-45ac-91f8-4882af975044", 1000025853L, "I've discovered energy!", 144114260L,
                DIGITAL_TOU, true)
        );
        replay(prmIntegrationService);
        aclGrantDetailService.setEligibleFlag(grantDetailDtos, LocalDate.of(2020, 1, 1), ACL);
        assertFalse(grantDetailDtos.get(0).getEligible());
        assertTrue(grantDetailDtos.get(1).getEligible());
        verify(prmIntegrationService);
    }

    @Test
    public void testDeleteGrantDetails() {
        String grantSetId = "5a7b38d3-55e2-44a1-bb0a-62948c81b2d6";
        aclGrantDetailRepository.deleteByGrantSetId(grantSetId);
        expectLastCall().once();
        replay(aclGrantDetailRepository);
        aclGrantDetailService.deleteGrantDetails(grantSetId);
        verify(aclGrantDetailRepository);
    }

    @Test
    public void testIsGrantDetailExist() {
        String grantSetId = "5a7b38d3-55e2-44a1-bb0a-62948c81b2d6";
        expect(aclGrantDetailRepository.isGrantDetailExist(grantSetId, 123456789L, PRINT_TOU)).andReturn(true).once();
        expect(aclGrantDetailRepository.isGrantDetailExist(grantSetId, 123456789L, DIGITAL_TOU))
            .andReturn(false).once();
        replay(aclGrantDetailRepository);
        assertTrue(aclGrantDetailService.isGrantDetailExist(grantSetId, 123456789L, PRINT_TOU));
        assertFalse(aclGrantDetailService.isGrantDetailExist(grantSetId, 123456789L, DIGITAL_TOU));
        verify(aclGrantDetailRepository);
    }

    @Test
    public void testCopyGrantDetails() {
        mockStatic(RupContextUtils.class);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        String sourceGrantSetId = "22552173-acfe-45d5-a026-3ded88e38266";
        String targetGrantSetId = "65f9c87a-cd1e-4854-a76e-cbbeb2122d3c";
        List<String> grantDetailIds = List.of("47388a61-7aa7-439b-a831-d3dfb4a69638");
        expect(aclGrantDetailRepository.copyGrantDetailsByGrantSetId(sourceGrantSetId, targetGrantSetId, USER_NAME))
            .andReturn(grantDetailIds);
        expectLastCall().once();
        replay(aclGrantDetailRepository);
        assertEquals(grantDetailIds.size(),
            aclGrantDetailService.copyGrantDetails(sourceGrantSetId, targetGrantSetId, USER_NAME));
        verify(aclGrantDetailRepository);
    }

    private void verifyGrantDtoCapture(Capture<AclGrantDetailDto> capture, AclGrantDetailDto expectedGrantDetailDto) {
        AclGrantDetailDto actualGrantDetailDto = capture.getValue();
        assertEquals(expectedGrantDetailDto.getId(), actualGrantDetailDto.getId());
        assertEquals(expectedGrantDetailDto.getGrantStatus(), actualGrantDetailDto.getGrantStatus());
        assertEquals(expectedGrantDetailDto.getRhAccountNumber(), actualGrantDetailDto.getRhAccountNumber());
        assertEquals(expectedGrantDetailDto.getTypeOfUse(), actualGrantDetailDto.getTypeOfUse());
        assertEquals(expectedGrantDetailDto.getTypeOfUseStatus(), actualGrantDetailDto.getTypeOfUseStatus());
        assertEquals(expectedGrantDetailDto.getEligible(), actualGrantDetailDto.getEligible());
        assertTrue(actualGrantDetailDto.getManualUploadFlag());
    }

    private void verifyGrantCapture(Capture<AclGrantDetail> capture, AclGrantDetail expectedGrantDetail) {
        AclGrantDetail actualGrantDetail = capture.getValue();
        assertEquals(expectedGrantDetail.getId(), actualGrantDetail.getId());
        assertEquals(expectedGrantDetail.getGrantStatus(), actualGrantDetail.getGrantStatus());
        assertEquals(expectedGrantDetail.getRhAccountNumber(), actualGrantDetail.getRhAccountNumber());
        assertEquals(expectedGrantDetail.getTypeOfUse(), actualGrantDetail.getTypeOfUse());
        assertEquals(expectedGrantDetail.getTypeOfUseStatus(), actualGrantDetail.getTypeOfUseStatus());
        assertEquals(expectedGrantDetail.getEligible(), actualGrantDetail.getEligible());
        assertTrue(actualGrantDetail.getManualUploadFlag());
    }

    private AclGrantDetail buildGrant(String id, Long wrWrkInst, String systemTitle, Long rhAccountNumber, String tou,
                                      boolean eligibleFlag) {
        AclGrantDetail grantDetail = new AclGrantDetail();
        grantDetail.setId(id);
        grantDetail.setWrWrkInst(wrWrkInst);
        grantDetail.setSystemTitle(systemTitle);
        grantDetail.setRhAccountNumber(rhAccountNumber);
        grantDetail.setTypeOfUse(tou);
        grantDetail.setGrantStatus(GRANT_STATUS);
        grantDetail.setManualUploadFlag(true);
        grantDetail.setEligible(eligibleFlag);
        return grantDetail;
    }

    private AclGrantDetailDto buildGrantDto(String id, Long wrWrkInst, String touStatus, Long rhAccountNumber,
                                            String tou, String grantStatus, boolean eligibleFlag) {
        AclGrantDetailDto grantDetailDto = new AclGrantDetailDto();
        grantDetailDto.setId(id);
        grantDetailDto.setWrWrkInst(wrWrkInst);
        grantDetailDto.setTypeOfUseStatus(touStatus);
        grantDetailDto.setRhAccountNumber(rhAccountNumber);
        grantDetailDto.setTypeOfUse(tou);
        grantDetailDto.setGrantStatus(grantStatus);
        grantDetailDto.setManualUploadFlag(true);
        grantDetailDto.setEligible(eligibleFlag);
        return grantDetailDto;
    }

    private AclGrantSet buildGrantSet() {
        AclGrantSet grantSet = new AclGrantSet();
        grantSet.setId("3eb28726-19de-487d-8397-02c1d5baa308");
        grantSet.setPeriods(Set.of(202106, 202112));
        grantSet.setGrantPeriod(202112);
        grantSet.setLicenseType(ACL);
        return grantSet;
    }

    private AclIneligibleRightsholder buildIneligibleRightsholder() {
        AclIneligibleRightsholder rightsholder = new AclIneligibleRightsholder();
        rightsholder.setOrganizationId("87ab019d-6a93-4296-9ad6-c6340f3d9509");
        rightsholder.setLicenseType(ACL);
        rightsholder.setTypeOfUse(DIGITAL_TOU);
        rightsholder.setRhAccountNumber(1000014080L);
        return rightsholder;
    }
}
