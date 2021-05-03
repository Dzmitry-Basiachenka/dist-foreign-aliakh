package com.copyright.rup.dist.foreign.service.impl.acl;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.UdmBatch;
import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.domain.UdmUsageDto;
import com.copyright.rup.dist.foreign.domain.UdmUsageOriginEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IUdmUsageRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link UdmUsageService}.
 * <p>
 * Copyright (C) 20121 copyright.com
 * <p>
 * Date: 04/30/2021
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(RupContextUtils.class)
public class UdmUsageServiceTest {

    private static final String USER_NAME = "user@copyright.com";
    private static final String UDM_BATCH_UID = "aa5751aa-2858-38c6-b0d9-51ec0edfcf4f";
    private static final String UDM_USAGE_UID_1 = "85c8a2a6-1e9d-453b-947c-14dbd92c5e15";
    private static final String UDM_USAGE_UID_2 = "f9207059-af0a-440a-abc7-b6e016c64677";
    private static final String UDM_USAGE_ORIGIN_UID_1 = "b7b9293c-f7bf-4cba-b2b9-58db82ccb675";
    private static final String UDM_USAGE_ORIGIN_UID_2 = "d1f699a4-1c83-408e-a499-e6aee6d110ef";

    private final UdmUsageService udmUsageService = new UdmUsageService();
    private IUdmUsageRepository udmUsageRepository;

    @Before
    public void setUp() {
        udmUsageRepository = createMock(IUdmUsageRepository.class);
        Whitebox.setInternalState(udmUsageService, udmUsageRepository);
    }

    @Test
    public void testInsertUdmUsages() {
        mockStatic(RupContextUtils.class);
        UdmBatch udmBatch = buildUdmBatch();
        List<UdmUsage> udmUsages = Arrays.asList(
            buildUdmUsage(UDM_USAGE_UID_1, UDM_USAGE_ORIGIN_UID_1),
            buildUdmUsage(UDM_USAGE_UID_2, UDM_USAGE_ORIGIN_UID_2));
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        udmUsageRepository.insert(udmUsages.get(0));
        expectLastCall().once();
        udmUsageRepository.insert(udmUsages.get(1));
        expectLastCall().once();
        replay(udmUsageRepository, RupContextUtils.class);
        udmUsageService.insertUdmUsages(udmBatch, udmUsages);
        verify(udmUsageRepository, RupContextUtils.class);
        assertEquals(USER_NAME, udmUsages.get(0).getCreateUser());
        assertEquals(USER_NAME, udmUsages.get(0).getUpdateUser());
        assertEquals(USER_NAME, udmUsages.get(1).getCreateUser());
        assertEquals(USER_NAME, udmUsages.get(1).getUpdateUser());
        assertNotNull(udmBatch.getId());
    }

    @Test
    public void testIsOriginalDetailIdExist() {
        expect(udmUsageRepository.isOriginalDetailIdExist(UDM_USAGE_ORIGIN_UID_1)).andReturn(true).once();
        replay(udmUsageRepository);
        assertTrue(udmUsageService.isOriginalDetailIdExist(UDM_USAGE_ORIGIN_UID_1));
        verify(udmUsageRepository);
    }

    @Test
    public void testIsOriginalDetailIdNotExist() {
        expect(udmUsageRepository.isOriginalDetailIdExist(UDM_USAGE_ORIGIN_UID_1)).andReturn(false).once();
        replay(udmUsageRepository);
        assertFalse(udmUsageService.isOriginalDetailIdExist(UDM_USAGE_ORIGIN_UID_1));
        verify(udmUsageRepository);
    }

    private UdmBatch buildUdmBatch() {
        UdmBatch udmBatch = new UdmBatch();
        udmBatch.setId(UDM_BATCH_UID);
        udmBatch.setName("UDM Batch 2021 June");
        udmBatch.setPeriod(202006);
        udmBatch.setChannel(UdmChannelEnum.CCC);
        udmBatch.setUsageOrigin(UdmUsageOriginEnum.SS);
        return udmBatch;
    }

    private UdmUsage buildUdmUsage(String usageId, String originId) {
        UdmUsage udmUsage = new UdmUsage();
        udmUsage.setId(usageId);
        udmUsage.setOriginalDetailId(originId);
        udmUsage.setBatchId(UDM_BATCH_UID);
        udmUsage.setStatus(UsageStatusEnum.NEW);
        udmUsage.setPeriodEndDate(LocalDate.of(2021, 12, 12));
        udmUsage.setUsageDate(LocalDate.of(2021, 12, 12));
        udmUsage.setWrWrkInst(122825347L);
        udmUsage.setReportedStandardNumber("0927-7765");
        udmUsage.setReportedTitle("Colloids and surfaces. B, Biointerfaces");
        udmUsage.setReportedPubType("Journal");
        udmUsage.setPubFormat("format");
        udmUsage.setArticle("Green chemistry");
        udmUsage.setLanguage("English");
        udmUsage.setCompanyId(45489489L);
        udmUsage.setSurveyRespondent("fa0276c3-55d6-42cd-8ffe-e9124acae02f");
        udmUsage.setIpAddress("ip24.12.119.203");
        udmUsage.setSurveyCountry("United States");
        udmUsage.setSurveyStartDate(LocalDate.of(2021, 12, 12));
        udmUsage.setSurveyEndDate(LocalDate.of(2021, 12, 12));
        udmUsage.setReportedTypeOfUse("COPY_FOR_MYSELF");
        udmUsage.setQuantity(7);
        return udmUsage;
    }

    @Test
    public void testGetUsageDtos() {
        List<UdmUsageDto> udmUsages = Collections.singletonList(new UdmUsageDto());
        Pageable pageable = new Pageable(0, 1);
        Sort sort = new Sort("detailId", Sort.Direction.ASC);
        UsageFilter filter = new UsageFilter();
        filter.setProductFamily("ACL");
        expect(udmUsageRepository.findDtosByFilter(filter, pageable, sort)).andReturn(udmUsages).once();
        replay(udmUsageRepository);
        List<UdmUsageDto> result = udmUsageRepository.findDtosByFilter(filter, pageable, sort);
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(udmUsageRepository);
    }

    @Test
    public void testGetUsagesDtosEmptyFilter() {
        List<UdmUsageDto> result = udmUsageService.getUsageDtos(new UsageFilter(), null, null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetUsagesCount() {
        UsageFilter filter = new UsageFilter();
        filter.setProductFamily("ACL");
        expect(udmUsageRepository.findCountByFilter(filter)).andReturn(1).once();
        replay(udmUsageRepository);
        assertEquals(1, udmUsageRepository.findCountByFilter(filter));
        verify(udmUsageRepository);
    }

    @Test
    public void testGetUsageCountEmptyFilter() {
        assertEquals(0, udmUsageRepository.findCountByFilter(new UsageFilter()));
    }
}
