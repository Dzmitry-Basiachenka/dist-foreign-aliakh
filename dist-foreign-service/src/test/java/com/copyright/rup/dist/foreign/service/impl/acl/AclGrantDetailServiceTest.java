package com.copyright.rup.dist.foreign.service.impl.acl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.AclGrantDetail;
import com.copyright.rup.dist.foreign.domain.AclGrantDetailDto;
import com.copyright.rup.dist.foreign.domain.filter.AclGrantDetailFilter;
import com.copyright.rup.dist.foreign.repository.api.IAclGrantDetailRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IAclGrantDetailService;

import com.google.common.collect.Lists;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
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
public class AclGrantDetailServiceTest {

    private static final String ACL_GRANT_SET_NAME = "ACL Grant Set 2021";
    private static final String ACL_GRANT_ID_1 = "979c5888-d768-4983-bc11-6cec49657dc3";
    private static final String ACL_GRANT_ID_2 = "fe1d13bd-ce53-4bdc-84dd-8e505b734ac9";
    private static final String PRINT_TOU = "PRINT";
    private static final String DIGITAL_TOU = "DIGITAL";
    private static final String GRANT_STATUS = "GRANT";
    private static final String PRINT_DIGITAL_TOU_STATUS = "Print&Digital";
    private static final String DIFFERENT_RH_TOU_STATUS = "Different RH";

    private IAclGrantDetailService aclGrantDetailService;
    private IAclGrantDetailRepository aclGrantDetailRepository;

    @Before
    public void setUp() {
        aclGrantDetailService = new AclGrantDetailService();
        aclGrantDetailRepository = createMock(IAclGrantDetailRepository.class);
        Whitebox.setInternalState(aclGrantDetailService, aclGrantDetailRepository);
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
        AclGrantDetailDto grantDetailDto = new AclGrantDetailDto();
        aclGrantDetailRepository.updateGrant(grantDetailDto);
        replay(aclGrantDetailRepository);
        aclGrantDetailService.updateGrants(Collections.singleton(grantDetailDto), false);
        verify(aclGrantDetailRepository);
    }

    @Test
    public void testUpdateGrantWithUpdatedRh() {
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
            buildGrantDto("21afbfa6-daa5-458c-8501-2d056eaa5181", 306985867L, PRINT_DIGITAL_TOU_STATUS, 1000014080L,
                DIGITAL_TOU, GRANT_STATUS)).once();
        expect(aclGrantDetailRepository.findPairForGrantById("20e05f23-eeef-45fd-89d2-1bc87efa98df")).andReturn(
            buildGrantDto("5384c03b-4a32-48c8-b56b-c6d430448bd6", 232167525L, DIFFERENT_RH_TOU_STATUS, 2000072827L,
                PRINT_TOU, GRANT_STATUS)).once();
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
        List<AclGrantDetailDto> aclGrantDetailDtos = Arrays.asList(
            buildGrantDto(ACL_GRANT_ID_2, 123456789L, PRINT_DIGITAL_TOU_STATUS, 2000072827L, DIGITAL_TOU, GRANT_STATUS),
            buildGrantDto(
                "cb75a763-9317-4b8f-9f1c-d2c5f6c59a0c", 306985867L, PRINT_DIGITAL_TOU_STATUS, 2000072827L, PRINT_TOU,
                GRANT_STATUS),
            buildGrantDto("20e05f23-eeef-45fd-89d2-1bc87efa98df", 232167525L, DIFFERENT_RH_TOU_STATUS, 2000072827L,
                DIGITAL_TOU, GRANT_STATUS)
        );
        replay(aclGrantDetailRepository);
        aclGrantDetailService.updateGrants(new HashSet<>(aclGrantDetailDtos), true);
        verifyCaptureItems(grant1, buildGrantDto(
            "20e05f23-eeef-45fd-89d2-1bc87efa98df", 232167525L, PRINT_DIGITAL_TOU_STATUS, 2000072827L, DIGITAL_TOU,
            GRANT_STATUS));
        verifyCaptureItems(grant3, buildGrantDto(
            "cb75a763-9317-4b8f-9f1c-d2c5f6c59a0c", 306985867L, DIFFERENT_RH_TOU_STATUS, 2000072827L, PRINT_TOU,
            GRANT_STATUS));
        verifyCaptureItems(grant6, buildGrantDto(
            ACL_GRANT_ID_1, 123456789L, DIFFERENT_RH_TOU_STATUS, 2000070936L, PRINT_TOU, GRANT_STATUS));
        verifyCaptureItems(grant4, buildGrantDto(
            "21afbfa6-daa5-458c-8501-2d056eaa5181", 306985867L, DIFFERENT_RH_TOU_STATUS, 1000014080L, DIGITAL_TOU,
            GRANT_STATUS));
        verifyCaptureItems(grant2, buildGrantDto(
            "5384c03b-4a32-48c8-b56b-c6d430448bd6", 232167525L, PRINT_DIGITAL_TOU_STATUS, 2000072827L, PRINT_TOU,
            GRANT_STATUS));
        verifyCaptureItems(grant5, buildGrantDto(
            ACL_GRANT_ID_2, 123456789L, DIFFERENT_RH_TOU_STATUS, 2000072827L, DIGITAL_TOU, GRANT_STATUS));
        verify(aclGrantDetailRepository);
    }

    @Test
    public void testUpdateGrantWithStatus() {
        AclGrantDetailDto grantDetailToUpdate = buildGrantDto(
            ACL_GRANT_ID_1, 123456789L, PRINT_DIGITAL_TOU_STATUS, 2000072827L, PRINT_TOU, GRANT_STATUS);
        Capture<AclGrantDetailDto> grantDetailDtoCapture1 = EasyMock.newCapture();
        Capture<AclGrantDetailDto> grantDetailDtoCapture2 = EasyMock.newCapture();
        expect(aclGrantDetailRepository.findPairForGrantById(ACL_GRANT_ID_2)).andReturn(grantDetailToUpdate).once();
        aclGrantDetailRepository.updateGrant(capture(grantDetailDtoCapture1));
        expectLastCall().once();
        aclGrantDetailRepository.updateGrant(capture(grantDetailDtoCapture2));
        expectLastCall().once();
        replay(aclGrantDetailRepository);
        aclGrantDetailService.updateGrants(Collections.singleton(
                buildGrantDto(ACL_GRANT_ID_2, 123456789L, PRINT_DIGITAL_TOU_STATUS, 2000072827L, DIGITAL_TOU, "DENY")),
            true);
        verifyCaptureItems(grantDetailDtoCapture2,
            buildGrantDto(ACL_GRANT_ID_1, 123456789L, "Print Only", 2000072827L, PRINT_TOU, GRANT_STATUS));
        verifyCaptureItems(grantDetailDtoCapture1,
            buildGrantDto(ACL_GRANT_ID_2, 123456789L, PRINT_DIGITAL_TOU_STATUS, 2000072827L, DIGITAL_TOU, "DENY"));
        verify(aclGrantDetailRepository);
    }

    @Test
    public void testUpdateSingleGrant() {
        expect(aclGrantDetailRepository.findPairForGrantById(ACL_GRANT_ID_2)).andReturn(null).once();
        Capture<AclGrantDetailDto> grantDetailDtoCapture1 = EasyMock.newCapture();
        aclGrantDetailRepository.updateGrant(capture(grantDetailDtoCapture1));
        expectLastCall().once();
        AclGrantDetailDto expectedGrantForUpdate =
            buildGrantDto(ACL_GRANT_ID_2, 123456789L, PRINT_DIGITAL_TOU_STATUS, 2000072827L, DIGITAL_TOU, "DENY");
        replay(aclGrantDetailRepository);
        aclGrantDetailService.updateGrants(Collections.singleton(expectedGrantForUpdate), true);
        verifyCaptureItems(grantDetailDtoCapture1, expectedGrantForUpdate);
        verify(aclGrantDetailRepository);
    }

    private void verifyCaptureItems(Capture<AclGrantDetailDto> capture, AclGrantDetailDto expectedGrantDetailDto) {
        AclGrantDetailDto actualGrantDetailDto = capture.getValue();
        assertEquals(expectedGrantDetailDto.getId(), actualGrantDetailDto.getId());
        assertEquals(expectedGrantDetailDto.getGrantStatus(), actualGrantDetailDto.getGrantStatus());
        assertEquals(expectedGrantDetailDto.getRhAccountNumber(), actualGrantDetailDto.getRhAccountNumber());
        assertEquals(expectedGrantDetailDto.getTypeOfUse(), actualGrantDetailDto.getTypeOfUse());
        assertEquals(expectedGrantDetailDto.getTypeOfUseStatus(), actualGrantDetailDto.getTypeOfUseStatus());
        assertEquals(expectedGrantDetailDto.getEligible(), actualGrantDetailDto.getEligible());
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
        return grantDetailDto;
    }
}
