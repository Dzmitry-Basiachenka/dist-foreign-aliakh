package com.copyright.rup.dist.foreign.service.impl.acl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.UdmBatch;
import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.domain.UdmUsageOriginEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUdmBatchRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmBatchService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageService;

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
 * Verifies {@link UdmBatchService}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 04/30/2021
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(RupContextUtils.class)
public class UdmBatchServiceTest {

    private static final String USER_NAME = "user@copyright.com";
    private static final String UDM_BATCH_UID = "aa5751aa-2858-38c6-b0d9-51ec0edfcf4f";
    private static final String UDM_USAGE_UID_1 = "85c8a2a6-1e9d-453b-947c-14dbd92c5e15";
    private static final String UDM_USAGE_UID_2 = "f9207059-af0a-440a-abc7-b6e016c64677";
    private static final String UDM_USAGE_ORIGIN_UID_1 = "OGN674GHHSB421";
    private static final String UDM_USAGE_ORIGIN_UID_2 = "OGN674GHHSB421";

    private IUdmUsageService udmUsageService;
    private IUdmBatchService udmBatchService;
    private IUdmBatchRepository udmBatchRepository;

    @Before
    public void setUp() {
        udmBatchService = new UdmBatchService();
        udmUsageService = createMock(IUdmUsageService.class);
        udmBatchRepository = createMock(IUdmBatchRepository.class);
        Whitebox.setInternalState(udmBatchService, udmUsageService);
        Whitebox.setInternalState(udmBatchService, udmBatchRepository);
    }

    @Test
    public void testInsertUdmBatch() {
        mockStatic(RupContextUtils.class);
        UdmBatch udmBatch = buildUdmBatch();
        List<UdmUsage> udmUsages = Arrays.asList(
            buildUdmUsage(UDM_USAGE_UID_1, UDM_USAGE_ORIGIN_UID_1),
            buildUdmUsage(UDM_USAGE_UID_2, UDM_USAGE_ORIGIN_UID_2));
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        udmBatchRepository.insert(udmBatch);
        expectLastCall().once();
        udmUsageService.insertUdmUsages(udmBatch, udmUsages);
        expectLastCall().once();
        replay(udmUsageService, udmBatchRepository, RupContextUtils.class);
        udmBatchService.insertUdmBatch(udmBatch, udmUsages);
        verify(udmUsageService, udmBatchRepository, RupContextUtils.class);
        assertEquals(USER_NAME, udmBatch.getCreateUser());
        assertEquals(USER_NAME, udmBatch.getUpdateUser());
        assertNotNull(udmBatch.getId());
    }

    @Test
    public void testGetPeriods() {
        List<Integer> periods = Arrays.asList(202006, 202112);
        expect(udmBatchRepository.findPeriods()).andReturn(periods).once();
        replay(udmBatchRepository);
        assertEquals(periods, udmBatchService.getPeriods());
        verify(udmBatchRepository);
    }

    @Test
    public void testGetUdmBathes() {
        List<UdmBatch> udmBatches = Collections.singletonList(new UdmBatch());
        expect(udmBatchRepository.findAll()).andReturn(udmBatches).once();
        replay(udmBatchRepository);
        assertEquals(udmBatches, udmBatchService.getUdmBatches());
        verify(udmBatchRepository);
    }

    @Test
    public void testBatchExists() {
        String batchName = "Name";
        expect(udmBatchRepository.udmBatchExists(batchName)).andReturn(true).once();
        replay(udmBatchRepository);
        assertTrue(udmBatchService.udmBatchExists(batchName));
        verify(udmBatchRepository);
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

    private UdmUsage buildUdmUsage(String usageId, String originalDetailId) {
        UdmUsage udmUsage = new UdmUsage();
        udmUsage.setId(usageId);
        udmUsage.setOriginalDetailId(originalDetailId);
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
}
