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

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.AclGrantDetail;
import com.copyright.rup.dist.foreign.domain.AclGrantDetailDto;
import com.copyright.rup.dist.foreign.domain.AclGrantSet;
import com.copyright.rup.dist.foreign.domain.filter.AclGrantDetailFilter;
import com.copyright.rup.dist.foreign.repository.api.IAclGrantDetailRepository;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclGrantDetailService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmBaselineService;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

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

    private static final String ACL_GRANT_SET_NAME = "ACL Grant Set 2021";
    private static final String ACL_GRANT_ID_1 = "979c5888-d768-4983-bc11-6cec49657dc3";
    private static final String ACL_GRANT_ID_2 = "fe1d13bd-ce53-4bdc-84dd-8e505b734ac9";
    private static final String ACL_GRANT_ID_3 = "21afbfa6-daa5-458c-8501-2d056eaa5181";
    private static final String ACL_GRANT_ID_4 = "5384c03b-4a32-48c8-b56b-c6d430448bd6";
    private static final String PRINT_TOU = "PRINT";
    private static final String DIGITAL_TOU = "DIGITAL";
    private static final String GRANT_STATUS = "GRANT";
    private static final String PRINT_DIGITAL_TOU_STATUS = "Print&Digital";
    private static final String DIFFERENT_RH_TOU_STATUS = "Different RH";
    private static final String USER_NAME = "user@copyright.com";

    private IAclGrantDetailService aclGrantDetailService;
    private IAclGrantDetailRepository aclGrantDetailRepository;
    private IRightsholderService rightsholderService;
    private IUdmBaselineService udmBaselineService;

    @Before
    public void setUp() {
        aclGrantDetailService = new AclGrantDetailService();
        aclGrantDetailRepository = createMock(IAclGrantDetailRepository.class);
        rightsholderService = createMock(IRightsholderService.class);
        udmBaselineService = createMock(IUdmBaselineService.class);
        Whitebox.setInternalState(aclGrantDetailService, aclGrantDetailRepository);
        Whitebox.setInternalState(aclGrantDetailService, rightsholderService);
        Whitebox.setInternalState(aclGrantDetailService, udmBaselineService);
    }

    @Test
    public void testInsert() {
        AclGrantDetail grantDetail = new AclGrantDetail();
        aclGrantDetailRepository.insert(grantDetail);
        expectLastCall().once();
        replay(aclGrantDetailRepository);
        aclGrantDetailService.insert(Lists.newArrayList(grantDetail));
        verify(aclGrantDetailRepository);
    }

    @Test
    public void testGetCount() {
        AclGrantDetailFilter filter = new AclGrantDetailFilter();
        filter.setGrantSetNames(Collections.singleton(ACL_GRANT_SET_NAME));
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
        List<AclGrantDetailDto> grantDetails = Collections.singletonList(new AclGrantDetailDto());
        Pageable pageable = new Pageable(0, 1);
        Sort sort = new Sort("licenseType", Sort.Direction.ASC);
        AclGrantDetailFilter filter = new AclGrantDetailFilter();
        filter.setGrantSetNames(Collections.singleton(ACL_GRANT_SET_NAME));
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
        AclGrantDetailDto grantDetailDto = new AclGrantDetailDto();
        grantDetailDto.setGrantStatus("Grant");
        aclGrantDetailRepository.updateGrant(grantDetailDto);
        expectLastCall().once();
        replay(aclGrantDetailRepository, RupContextUtils.class);
        aclGrantDetailService.updateGrants(Collections.singleton(grantDetailDto), false);
        assertEquals(USER_NAME, grantDetailDto.getUpdateUser());
        verify(aclGrantDetailRepository, RupContextUtils.class);
    }

    @Test
    public void testInsertAclGrantDetails() {
        Capture<AclGrantDetail> grant1 = EasyMock.newCapture();
        Capture<AclGrantDetail> grant2 = EasyMock.newCapture();
        Capture<AclGrantDetail> grant3 = EasyMock.newCapture();
        Capture<AclGrantDetailDto> grantDto1 = EasyMock.newCapture();
        Capture<AclGrantDetailDto> grantDto2 = EasyMock.newCapture();
        Capture<AclGrantDetailDto> grantDto3 = EasyMock.newCapture();
        Capture<AclGrantDetailDto> grantDto4 = EasyMock.newCapture();
        AclGrantSet grantSet = new AclGrantSet();
        grantSet.setId("3eb28726-19de-487d-8397-02c1d5baa308");
        grantSet.setPeriods(Sets.newHashSet(202106, 202112));
        List<AclGrantDetailDto> aclGrantDetailDtos = Arrays.asList(
            buildGrantDto(ACL_GRANT_ID_1, 123456789L, null, 2000072827L, DIGITAL_TOU, GRANT_STATUS),
            buildGrantDto(ACL_GRANT_ID_2, 123456789L, null, 2000070936L, PRINT_TOU, GRANT_STATUS),
            buildGrantDto(ACL_GRANT_ID_3, 232167525L, null, 1000014080L, DIGITAL_TOU, GRANT_STATUS));
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
            buildGrantDto(ACL_GRANT_ID_4, 232167525L, "Print Only", 1000014080L, PRINT_TOU, GRANT_STATUS)).once();
        aclGrantDetailRepository.updateGrant(capture(grantDto3));
        expectLastCall().once();
        aclGrantDetailRepository.updateGrant(capture(grantDto4));
        expectLastCall().once();
        expect(rightsholderService.updateRightsholders(Sets.newHashSet(1000014080L, 2000072827L, 2000070936L)))
            .andReturn(Collections.emptyList()).once();
        replay(aclGrantDetailRepository, rightsholderService, udmBaselineService);
        aclGrantDetailService.addToGrantSet(grantSet, aclGrantDetailDtos);
        verifyGrantCapture(grant1,
            buildGrant(ACL_GRANT_ID_1, 123456789L, "BIOCHEMISTRY (MOSCOW)", 2000072827L, DIGITAL_TOU));
        verifyGrantCapture(grant2,
            buildGrant(ACL_GRANT_ID_2, 123456789L, "BIOCHEMISTRY (MOSCOW)", 2000070936L, PRINT_TOU));
        verifyGrantCapture(grant3, buildGrant(ACL_GRANT_ID_3, 232167525L, "100 ROAD MOVIES", 1000014080L, DIGITAL_TOU));
        verifyGrantDtoCapture(grantDto1, buildGrantDto(ACL_GRANT_ID_3, 232167525L, PRINT_DIGITAL_TOU_STATUS,
            1000014080L, DIGITAL_TOU, GRANT_STATUS));
        verifyGrantDtoCapture(grantDto2, buildGrantDto(ACL_GRANT_ID_4, 232167525L, PRINT_DIGITAL_TOU_STATUS,
            1000014080L, PRINT_TOU, GRANT_STATUS));
        verifyGrantDtoCapture(grantDto3, buildGrantDto(ACL_GRANT_ID_1, 123456789L, DIFFERENT_RH_TOU_STATUS,
            2000072827L, DIGITAL_TOU, GRANT_STATUS));
        verifyGrantDtoCapture(grantDto4, buildGrantDto(ACL_GRANT_ID_2, 123456789L, DIFFERENT_RH_TOU_STATUS,
            2000070936L, PRINT_TOU, GRANT_STATUS));
        verify(aclGrantDetailRepository, rightsholderService, udmBaselineService);
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
                GRANT_STATUS)).once();
        expect(aclGrantDetailRepository.findPairForGrantById("cb75a763-9317-4b8f-9f1c-d2c5f6c59a0c")).andReturn(
            buildGrantDto(ACL_GRANT_ID_3, 306985867L, PRINT_DIGITAL_TOU_STATUS, 1000014080L, DIGITAL_TOU,
                GRANT_STATUS)).once();
        expect(aclGrantDetailRepository.findPairForGrantById("20e05f23-eeef-45fd-89d2-1bc87efa98df")).andReturn(
            buildGrantDto(ACL_GRANT_ID_4, 232167525L, DIFFERENT_RH_TOU_STATUS, 2000072827L, PRINT_TOU,
                GRANT_STATUS)).once();
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
        expect(rightsholderService.updateRightsholders(Collections.singleton(2000072827L)))
            .andReturn(Collections.emptyList()).once();
        replay(aclGrantDetailRepository, rightsholderService, RupContextUtils.class);
        List<AclGrantDetailDto> aclGrantDetailDtos = Arrays.asList(
            buildGrantDto(ACL_GRANT_ID_2, 123456789L, PRINT_DIGITAL_TOU_STATUS, 2000072827L, DIGITAL_TOU, GRANT_STATUS),
            buildGrantDto("cb75a763-9317-4b8f-9f1c-d2c5f6c59a0c", 306985867L, PRINT_DIGITAL_TOU_STATUS, 2000072827L,
                PRINT_TOU, GRANT_STATUS),
            buildGrantDto("20e05f23-eeef-45fd-89d2-1bc87efa98df", 232167525L, DIFFERENT_RH_TOU_STATUS, 2000072827L,
                DIGITAL_TOU, GRANT_STATUS)
        );
        aclGrantDetailService.updateGrants(new HashSet<>(aclGrantDetailDtos), true);
        verifyGrantDtoCapture(grant1, buildGrantDto("20e05f23-eeef-45fd-89d2-1bc87efa98df", 232167525L,
            PRINT_DIGITAL_TOU_STATUS, 2000072827L, DIGITAL_TOU, GRANT_STATUS));
        verifyGrantDtoCapture(grant3, buildGrantDto("cb75a763-9317-4b8f-9f1c-d2c5f6c59a0c", 306985867L,
            DIFFERENT_RH_TOU_STATUS, 2000072827L, PRINT_TOU, GRANT_STATUS));
        verifyGrantDtoCapture(grant6,
            buildGrantDto(ACL_GRANT_ID_1, 123456789L, DIFFERENT_RH_TOU_STATUS, 2000070936L, PRINT_TOU, GRANT_STATUS));
        verifyGrantDtoCapture(grant4,
            buildGrantDto(ACL_GRANT_ID_3, 306985867L, DIFFERENT_RH_TOU_STATUS, 1000014080L, DIGITAL_TOU, GRANT_STATUS));
        verifyGrantDtoCapture(grant2,
            buildGrantDto(ACL_GRANT_ID_4, 232167525L, PRINT_DIGITAL_TOU_STATUS, 2000072827L, PRINT_TOU, GRANT_STATUS));
        verifyGrantDtoCapture(grant5,
            buildGrantDto(ACL_GRANT_ID_2, 123456789L, DIFFERENT_RH_TOU_STATUS, 2000072827L, DIGITAL_TOU, GRANT_STATUS));
        verify(aclGrantDetailRepository, rightsholderService, RupContextUtils.class);
    }

    @Test
    public void testUpdateGrantWithStatus() {
        mockStatic(RupContextUtils.class);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        AclGrantDetailDto grantDetailToUpdate = buildGrantDto(
            ACL_GRANT_ID_1, 123456789L, PRINT_DIGITAL_TOU_STATUS, 2000072827L, PRINT_TOU, GRANT_STATUS);
        Capture<AclGrantDetailDto> grantDetailDtoCapture1 = EasyMock.newCapture();
        Capture<AclGrantDetailDto> grantDetailDtoCapture2 = EasyMock.newCapture();
        expect(aclGrantDetailRepository.findPairForGrantById(ACL_GRANT_ID_2)).andReturn(grantDetailToUpdate).once();
        aclGrantDetailRepository.updateGrant(capture(grantDetailDtoCapture1));
        expectLastCall().once();
        aclGrantDetailRepository.updateGrant(capture(grantDetailDtoCapture2));
        expectLastCall().once();
        expect(rightsholderService.updateRightsholders(Collections.singleton(2000072827L)))
            .andReturn(Collections.emptyList()).once();
        replay(aclGrantDetailRepository, rightsholderService, RupContextUtils.class);
        aclGrantDetailService.updateGrants(Collections.singleton(buildGrantDto(ACL_GRANT_ID_2, 123456789L,
            PRINT_DIGITAL_TOU_STATUS, 2000072827L, DIGITAL_TOU, "DENY")), true);
        verifyGrantDtoCapture(grantDetailDtoCapture2,
            buildGrantDto(ACL_GRANT_ID_1, 123456789L, "Print Only", 2000072827L, PRINT_TOU, GRANT_STATUS));
        verifyGrantDtoCapture(grantDetailDtoCapture1,
            buildGrantDto(ACL_GRANT_ID_2, 123456789L, PRINT_DIGITAL_TOU_STATUS, 2000072827L, DIGITAL_TOU, "DENY"));
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
            buildGrantDto(ACL_GRANT_ID_2, 123456789L, PRINT_DIGITAL_TOU_STATUS, 2000072827L, DIGITAL_TOU, "DENY");
        replay(aclGrantDetailRepository, RupContextUtils.class);
        aclGrantDetailService.updateGrants(Collections.singleton(expectedGrantForUpdate), true);
        verifyGrantDtoCapture(grantDetailDtoCapture1, expectedGrantForUpdate);
        verify(aclGrantDetailRepository, RupContextUtils.class);
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

    private AclGrantDetail buildGrant(String id, Long wrWrkInst, String systemTitle, Long rhAccountNumber, String tou) {
        AclGrantDetail grantDetail = new AclGrantDetail();
        grantDetail.setId(id);
        grantDetail.setWrWrkInst(wrWrkInst);
        grantDetail.setSystemTitle(systemTitle);
        grantDetail.setRhAccountNumber(rhAccountNumber);
        grantDetail.setTypeOfUse(tou);
        grantDetail.setGrantStatus(GRANT_STATUS);
        grantDetail.setManualUploadFlag(true);
        return grantDetail;
    }

    private AclGrantDetailDto buildGrantDto(String id, Long wrWrkInst, String touStatus, Long rhAccountNumber,
                                            String tou, String grantStatus) {
        AclGrantDetailDto grantDetailDto = new AclGrantDetailDto();
        grantDetailDto.setId(id);
        grantDetailDto.setWrWrkInst(wrWrkInst);
        grantDetailDto.setTypeOfUseStatus(touStatus);
        grantDetailDto.setRhAccountNumber(rhAccountNumber);
        grantDetailDto.setTypeOfUse(tou);
        grantDetailDto.setGrantStatus(grantStatus);
        grantDetailDto.setManualUploadFlag(true);
        return grantDetailDto;
    }
}
