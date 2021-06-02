package com.copyright.rup.dist.foreign.service.impl.acl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.newCapture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.CompanyInformation;
import com.copyright.rup.dist.foreign.domain.UdmBatch;
import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.domain.UdmUsageDto;
import com.copyright.rup.dist.foreign.domain.UdmUsageOriginEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;
import com.copyright.rup.dist.foreign.integration.telesales.api.ITelesalesService;
import com.copyright.rup.dist.foreign.repository.api.IUdmUsageRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmTypeOfUseService;
import com.copyright.rup.dist.foreign.service.api.executor.IChainExecutor;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;

import com.google.common.collect.ImmutableMap;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
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
    private static final String UDM_USAGE_ORIGIN_UID_1 = "OGN674GHHSB321";
    private static final String UDM_USAGE_ORIGIN_UID_2 = "OGN674GHHSB322";
    private static final String FAX_PHOTOCOPIES = "FAX_PHOTOCOPIES";
    private static final String COPY_FOR_MYSELF = "COPY_FOR_MYSELF";
    private static final String PRINT = "PRINT";
    private static final String DIGITAL = "DIGITAL";
    private static final int ANNUAL_MULTIPLIER = 25;
    private static final BigDecimal STATISTICAL_MULTIPLIER = BigDecimal.ONE.setScale(5, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal ANNUALIZED_COPIES = new BigDecimal(175).setScale(5, BigDecimal.ROUND_HALF_UP);

    private final UdmUsageService udmUsageService = new UdmUsageService();
    private IUdmUsageRepository udmUsageRepository;
    private UdmAnnualMultiplierCalculator udmAnnualMultiplierCalculator;
    private UdmAnnualizedCopiesCalculator udmAnnualizedCopiesCalculator;
    private IUdmTypeOfUseService udmTypeOfUseService;
    private ITelesalesService telesalesService;
    private IChainExecutor<UdmUsage> chainExecutor;

    @Before
    public void setUp() {
        udmUsageRepository = createMock(IUdmUsageRepository.class);
        udmAnnualMultiplierCalculator = createMock(UdmAnnualMultiplierCalculator.class);
        udmAnnualizedCopiesCalculator = createMock(UdmAnnualizedCopiesCalculator.class);
        udmTypeOfUseService = createMock(IUdmTypeOfUseService.class);
        telesalesService = createMock(ITelesalesService.class);
        chainExecutor = createMock(IChainExecutor.class);
        Whitebox.setInternalState(udmUsageService, udmUsageRepository);
        Whitebox.setInternalState(udmUsageService, udmAnnualMultiplierCalculator);
        Whitebox.setInternalState(udmUsageService, udmAnnualizedCopiesCalculator);
        Whitebox.setInternalState(udmUsageService, chainExecutor);
        Whitebox.setInternalState(udmUsageService, udmTypeOfUseService);
        Whitebox.setInternalState(udmUsageService, telesalesService);
    }

    @Test
    public void testInsertUdmUsages() {
        mockStatic(RupContextUtils.class);
        UdmBatch udmBatch = buildUdmBatch();
        UdmUsage udmUsage1 = buildUdmUsage(UDM_USAGE_UID_1, UDM_USAGE_ORIGIN_UID_1);
        UdmUsage udmUsage2 = buildUdmUsage(UDM_USAGE_UID_2, UDM_USAGE_ORIGIN_UID_2);
        udmUsage2.setReportedTypeOfUse(null);
        udmUsage2.setReportedTitle("None");
        List<UdmUsage> udmUsages = Arrays.asList(udmUsage1, udmUsage2);
        CompanyInformation companyInformation = new CompanyInformation();
        companyInformation.setId(45489489L);
        companyInformation.setName("Skadden, Arps, Slate, Meagher & Flom LLP");
        companyInformation.setDetailedLicenseeClassId(1);
        expect(udmTypeOfUseService.getUdmTouToRmsTouMap())
            .andReturn(ImmutableMap.of(FAX_PHOTOCOPIES, PRINT, COPY_FOR_MYSELF, DIGITAL)).once();
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        expect(telesalesService.getCompanyInformation(45489489L)).andReturn(companyInformation).times(2);
        udmUsageRepository.insert(udmUsage1);
        expectLastCall().once();
        udmUsageRepository.insert(udmUsage2);
        expectLastCall().once();
        expect(udmAnnualMultiplierCalculator.calculate(LocalDate.of(2021, 12, 12), LocalDate.of(2021, 12, 12)))
            .andReturn(ANNUAL_MULTIPLIER).times(2);
        expect(udmAnnualizedCopiesCalculator.calculate(udmUsage1.getReportedTypeOfUse(), udmUsage1.getQuantity(),
            ANNUAL_MULTIPLIER, STATISTICAL_MULTIPLIER)).andReturn(ANNUALIZED_COPIES).once();
        replay(udmUsageRepository, udmTypeOfUseService, udmAnnualMultiplierCalculator, udmAnnualizedCopiesCalculator,
            telesalesService, RupContextUtils.class);
        udmUsageService.insertUdmUsages(udmBatch, udmUsages);
        verify(udmUsageRepository, udmTypeOfUseService, udmAnnualMultiplierCalculator, udmAnnualizedCopiesCalculator,
            telesalesService, RupContextUtils.class);
        assertUdmUsage(udmUsage1);
        assertEquals(DIGITAL, udmUsage1.getTypeOfUse());
        assertEquals(ANNUALIZED_COPIES, udmUsage1.getAnnualizedCopies());
        assertUdmUsage(udmUsage2);
        assertNull(udmUsage2.getTypeOfUse());
        assertNull(udmUsage2.getAnnualizedCopies());
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

    @Test
    public void testGetUdmUsageIdsByStatus() {
        List<String> udmUsageIds = Arrays.asList(UDM_USAGE_UID_1, UDM_USAGE_UID_2);
        expect(udmUsageRepository.findIdsByStatus(UsageStatusEnum.WORK_FOUND)).andReturn(udmUsageIds).once();
        replay(udmUsageRepository);
        assertEquals(udmUsageIds, udmUsageService.getUdmUsageIdsByStatus(UsageStatusEnum.WORK_FOUND));
        verify(udmUsageRepository);
    }

    @Test
    public void testGetUdmUsagesByIds() {
        List<String> udmUsageIds = Arrays.asList(UDM_USAGE_UID_1, UDM_USAGE_UID_2);
        UdmUsage udmUsage1 = buildUdmUsage(UDM_USAGE_UID_1, UDM_USAGE_ORIGIN_UID_1);
        UdmUsage udmUsage2 = buildUdmUsage(UDM_USAGE_UID_2, UDM_USAGE_ORIGIN_UID_2);
        List<UdmUsage> udmUsages = Arrays.asList(udmUsage1, udmUsage2);
        expect(udmUsageRepository.findByIds(udmUsageIds)).andReturn(udmUsages).once();
        replay(udmUsageRepository);
        assertEquals(udmUsages, udmUsageRepository.findByIds(udmUsageIds));
        verify(udmUsageRepository);
    }

    @Test
    public void testGetUsageDtos() {
        UdmUsageDto udmUsageDto = new UdmUsageDto();
        udmUsageDto.setUsageOrigin(UdmUsageOriginEnum.SS);
        List<UdmUsageDto> udmUsages = Collections.singletonList(udmUsageDto);
        Pageable pageable = new Pageable(0, 1);
        Sort sort = new Sort("detailId", Sort.Direction.ASC);
        UdmUsageFilter filter = new UdmUsageFilter();
        filter.setUdmUsageOrigin(UdmUsageOriginEnum.SS);
        expect(udmUsageRepository.findDtosByFilter(filter, pageable, sort)).andReturn(udmUsages).once();
        replay(udmUsageRepository);
        List<UdmUsageDto> result = udmUsageRepository.findDtosByFilter(filter, pageable, sort);
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(udmUsageRepository);
    }

    @Test
    public void testGetUsagesDtosEmptyFilter() {
        List<UdmUsageDto> result = udmUsageService.getUsageDtos(new UdmUsageFilter(), null, null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetUsagesCount() {
        UdmUsageFilter filter = new UdmUsageFilter();
        filter.setUdmUsageOrigin(UdmUsageOriginEnum.SS);
        expect(udmUsageRepository.findCountByFilter(filter)).andReturn(1).once();
        replay(udmUsageRepository);
        assertEquals(1, udmUsageService.getUsagesCount(filter));
        verify(udmUsageRepository);
    }

    @Test
    public void testGetUsageCountEmptyFilter() {
        assertEquals(0, udmUsageService.getUsagesCount(new UdmUsageFilter()));
    }

    @Test
    public void testSendForMatching() {
        UdmUsage usage1 = new UdmUsage();
        usage1.setStatus(UsageStatusEnum.NEW);
        UdmUsage usage2 = new UdmUsage();
        usage2.setStatus(UsageStatusEnum.INELIGIBLE);
        Capture<Runnable> captureRunnable = newCapture();
        chainExecutor.execute(capture(captureRunnable));
        expectLastCall().once();
        chainExecutor.execute(Collections.singletonList(usage1), ChainProcessorTypeEnum.MATCHING);
        expectLastCall().once();
        replay(chainExecutor);
        udmUsageService.sendForMatching(Arrays.asList(usage1, usage2));
        assertNotNull(captureRunnable);
        Runnable runnable = captureRunnable.getValue();
        assertNotNull(runnable);
        runnable.run();
        verify(chainExecutor);
    }

    @Test
    public void testUpdateProcessedUsage() {
        String usageId = "3d1f1d34-c307-42be-99d2-075dbcdb7838";
        UdmUsage udmUsage = buildUdmUsage(usageId, UDM_USAGE_ORIGIN_UID_1);
        expect(udmUsageRepository.updateProcessedUsage(udmUsage)).andReturn(usageId).once();
        replay(udmUsageRepository);
        udmUsageService.updateProcessedUsage(udmUsage);
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

    private UdmUsage buildUdmUsage(String usageId, String originalDetailId) {
        UdmUsage udmUsage = new UdmUsage();
        udmUsage.setId(usageId);
        udmUsage.setOriginalDetailId(originalDetailId);
        udmUsage.setBatchId(UDM_BATCH_UID);
        udmUsage.setStatus(UsageStatusEnum.NEW);
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

    private void assertUdmUsage(UdmUsage udmUsage) {
        assertEquals(UDM_BATCH_UID, udmUsage.getBatchId());
        assertEquals(LocalDate.of(2020, 6, 30), udmUsage.getPeriodEndDate());
        assertEquals(ANNUAL_MULTIPLIER, udmUsage.getAnnualMultiplier().intValue());
        assertEquals(STATISTICAL_MULTIPLIER, udmUsage.getStatisticalMultiplier());
        assertEquals(USER_NAME, udmUsage.getCreateUser());
        assertEquals(USER_NAME, udmUsage.getUpdateUser());
    }
}
