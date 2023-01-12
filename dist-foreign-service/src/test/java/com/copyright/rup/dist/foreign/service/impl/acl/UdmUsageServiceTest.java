package com.copyright.rup.dist.foreign.service.impl.acl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.eq;
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
import com.copyright.rup.dist.foreign.domain.UdmActionReason;
import com.copyright.rup.dist.foreign.domain.UdmBatch;
import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.UdmIneligibleReason;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.domain.UdmUsageDto;
import com.copyright.rup.dist.foreign.domain.UdmUsageOriginEnum;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;
import com.copyright.rup.dist.foreign.integration.telesales.api.ITelesalesService;
import com.copyright.rup.dist.foreign.repository.api.IUdmActionReasonRepository;
import com.copyright.rup.dist.foreign.repository.api.IUdmIneligibleReasonRepository;
import com.copyright.rup.dist.foreign.repository.api.IUdmUsageRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmBaselineService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmTypeOfUseService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.executor.IChainExecutor;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import org.apache.commons.lang3.StringUtils;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Verifies {@link UdmUsageService}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 04/30/2021
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(RupContextUtils.class)
public class UdmUsageServiceTest {

    private static final String USER_NAME = "user@copyright.com";
    private static final String ASSIGNEE = "wjohn@copyright.com";
    private static final String UDM_BATCH_UID = "aa5751aa-2858-38c6-b0d9-51ec0edfcf4f";
    private static final String UDM_USAGE_UID_1 = "85c8a2a6-1e9d-453b-947c-14dbd92c5e15";
    private static final String UDM_USAGE_UID_2 = "f9207059-af0a-440a-abc7-b6e016c64677";
    private static final String UDM_USAGE_UID_3 = "2c2196c5-cfd7-4b8b-bade-9634dcba0f4f";
    private static final String UDM_USAGE_ORIGIN_UID_1 = "OGN674GHHSB321";
    private static final String UDM_USAGE_ORIGIN_UID_2 = "OGN674GHHSB322";
    private static final String FAX_PHOTOCOPIES = "FAX_PHOTOCOPIES";
    private static final String COPY_FOR_MYSELF = "COPY_FOR_MYSELF";
    private static final String PRINT = "PRINT";
    private static final String DIGITAL = "DIGITAL";
    private static final int ANNUAL_MULTIPLIER = 25;
    private static final BigDecimal STATISTICAL_MULTIPLIER = BigDecimal.ONE.setScale(5, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal ANNUALIZED_COPIES = new BigDecimal(175).setScale(5, BigDecimal.ROUND_HALF_UP);
    private static final String NO_REPORTED_USE_UID = "18fbee56-2f5c-450a-999e-54903c0bfb23";
    private static final String USAGE_UID = "a40e5ab4-7591-4e7f-8cab-34a9ff893e15";
    private static final String REASON = "Reason";
    private static final String ACTION_REASON_1 =
        "The field 'Detail Status' was edited. Old Value is 'OPS_REVIEW'. New Value is 'ELIGIBLE'";
    private static final String ACTION_REASON_2 =
        "The field 'Action Reason' was edited. Old Value is not specified. New Value is 'Public Domain'";
    private static final String ACTION_REASON_3 =
        "The field 'Reported Standard Number' was edited. Old Value is '0927-7765'. New Value is not specified";

    private final UdmUsageService udmUsageService = new UdmUsageService();
    private IUdmUsageRepository udmUsageRepository;
    private IUdmActionReasonRepository udmActionReasonRepository;
    private IUdmIneligibleReasonRepository udmIneligibleReasonRepository;
    private UdmAnnualMultiplierCalculator udmAnnualMultiplierCalculator;
    private UdmAnnualizedCopiesCalculator udmAnnualizedCopiesCalculator;
    private IUdmTypeOfUseService udmTypeOfUseService;
    private ITelesalesService telesalesService;
    private IUdmUsageAuditService udmUsageAuditService;
    private IChainExecutor<UdmUsage> chainExecutor;
    private IUdmBaselineService baselineService;

    @Before
    public void setUp() {
        udmUsageRepository = createMock(IUdmUsageRepository.class);
        udmActionReasonRepository = createMock(IUdmActionReasonRepository.class);
        udmIneligibleReasonRepository = createMock(IUdmIneligibleReasonRepository.class);
        udmAnnualMultiplierCalculator = createMock(UdmAnnualMultiplierCalculator.class);
        udmAnnualizedCopiesCalculator = createMock(UdmAnnualizedCopiesCalculator.class);
        udmTypeOfUseService = createMock(IUdmTypeOfUseService.class);
        telesalesService = createMock(ITelesalesService.class);
        udmUsageAuditService = createMock(IUdmUsageAuditService.class);
        chainExecutor = createMock(IChainExecutor.class);
        baselineService = createMock(IUdmBaselineService.class);
        Whitebox.setInternalState(udmUsageService, udmUsageRepository);
        Whitebox.setInternalState(udmUsageService, udmActionReasonRepository);
        Whitebox.setInternalState(udmUsageService, udmIneligibleReasonRepository);
        Whitebox.setInternalState(udmUsageService, udmAnnualMultiplierCalculator);
        Whitebox.setInternalState(udmUsageService, udmAnnualizedCopiesCalculator);
        Whitebox.setInternalState(udmUsageService, chainExecutor);
        Whitebox.setInternalState(udmUsageService, udmTypeOfUseService);
        Whitebox.setInternalState(udmUsageService, telesalesService);
        Whitebox.setInternalState(udmUsageService, udmUsageAuditService);
        Whitebox.setInternalState(udmUsageService, baselineService);
    }

    @Test
    public void testPublishUdmUsageToBaseline() {
        mockStatic(RupContextUtils.class);
        Set<String> usageIds = Set.of("367233a7-702f-4a88-82b4-b95a5508ab52");
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        expect(udmUsageRepository.publishUdmUsagesToBaseline(202106, USER_NAME)).andReturn(usageIds).once();
        udmUsageAuditService.logAction("367233a7-702f-4a88-82b4-b95a5508ab52", UsageActionTypeEnum.PUBLISH_TO_BASELINE,
            "UDM usage was published to baseline by 'user@copyright.com'");
        expectLastCall().once();
        replay(udmUsageRepository, udmUsageAuditService, RupContextUtils.class);
        assertEquals(1, udmUsageService.publishUdmUsagesToBaseline(202106));
        verify(udmUsageRepository, udmUsageAuditService, RupContextUtils.class);
    }

    @Test
    public void testInsertUdmUsages() {
        mockStatic(RupContextUtils.class);
        UdmBatch udmBatch = buildUdmBatch();
        UdmUsage udmUsage1 = buildUdmUsage(UDM_USAGE_UID_1, UDM_USAGE_ORIGIN_UID_1);
        UdmUsage udmUsage2 = buildUdmUsage(UDM_USAGE_UID_2, UDM_USAGE_ORIGIN_UID_2);
        udmUsage2.setStatus(UsageStatusEnum.INELIGIBLE);
        udmUsage2.setIneligibleReasonId(NO_REPORTED_USE_UID);
        udmUsage2.setReportedTypeOfUse(null);
        udmUsage2.setReportedTitle("None");
        List<UdmUsage> udmUsages = List.of(udmUsage1, udmUsage2);
        CompanyInformation companyInformation = new CompanyInformation();
        companyInformation.setId(45489489L);
        companyInformation.setName("Skadden, Arps, Slate, Meagher & Flom LLP");
        companyInformation.setDetailLicenseeClassId(1);
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
        udmUsageAuditService.logAction(udmUsage1.getId(), UsageActionTypeEnum.LOADED,
            "Uploaded in 'UDM Batch 2021 June' Batch");
        expectLastCall().once();
        udmUsageAuditService.logAction(udmUsage2.getId(), UsageActionTypeEnum.LOADED,
            "Uploaded in 'UDM Batch 2021 June' Batch");
        expectLastCall().once();
        udmUsageAuditService.logAction(udmUsage2.getId(), UsageActionTypeEnum.INELIGIBLE, "No reported use");
        expectLastCall().once();
        replay(udmUsageRepository, udmTypeOfUseService, udmAnnualMultiplierCalculator, udmAnnualizedCopiesCalculator,
            telesalesService, udmUsageAuditService, RupContextUtils.class);
        udmUsageService.insertUdmUsages(udmBatch, udmUsages);
        verify(udmUsageRepository, udmTypeOfUseService, udmAnnualMultiplierCalculator, udmAnnualizedCopiesCalculator,
            telesalesService, udmUsageAuditService, RupContextUtils.class);
        assertUdmUsage(udmUsage1);
        assertEquals(DIGITAL, udmUsage1.getTypeOfUse());
        assertEquals(ANNUALIZED_COPIES, udmUsage1.getAnnualizedCopies());
        assertUdmUsage(udmUsage2);
        assertNull(udmUsage2.getTypeOfUse());
        assertNull(udmUsage2.getAnnualizedCopies());
        assertNotNull(udmBatch.getId());
    }

    @Test
    public void testUpdateUsageSpecialistOrManager() {
        mockStatic(RupContextUtils.class);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        UdmUsageDto udmUsageDto = buildUsageDto(UsageStatusEnum.WORK_NOT_FOUND);
        udmUsageDto.setAssignee(ASSIGNEE);
        List<String> actionReasons = List.of(ACTION_REASON_1, ACTION_REASON_2, ACTION_REASON_3);
        udmUsageRepository.update(udmUsageDto);
        expectLastCall().once();
        udmUsageAuditService.logAction(USAGE_UID, UsageActionTypeEnum.USAGE_EDIT, ACTION_REASON_1);
        expectLastCall().once();
        udmUsageAuditService.logAction(USAGE_UID, UsageActionTypeEnum.USAGE_EDIT, ACTION_REASON_2);
        expectLastCall().once();
        udmUsageAuditService.logAction(USAGE_UID, UsageActionTypeEnum.USAGE_EDIT, ACTION_REASON_3);
        expectLastCall().once();
        replay(udmUsageRepository, udmUsageAuditService, RupContextUtils.class);
        udmUsageService.updateUsage(udmUsageDto, actionReasons, false, StringUtils.EMPTY);
        assertEquals(USER_NAME, udmUsageDto.getUpdateUser());
        assertEquals(ASSIGNEE, udmUsageDto.getAssignee());
        assertEquals(UsageStatusEnum.WORK_NOT_FOUND, udmUsageDto.getStatus());
        verify(udmUsageRepository, udmUsageAuditService, RupContextUtils.class);
    }

    @Test
    public void testUpdateBaselineUsage() {
        mockStatic(RupContextUtils.class);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        UdmUsageDto udmUsageDto = buildUsageDto(UsageStatusEnum.ELIGIBLE);
        udmUsageDto.setAssignee(ASSIGNEE);
        udmUsageDto.setBaselineFlag(true);
        List<String> actionReasons = List.of(ACTION_REASON_1, ACTION_REASON_2, ACTION_REASON_3);
        baselineService.deleteFromBaseline(ImmutableSet.of(USAGE_UID), REASON, USER_NAME);
        expectLastCall().once();
        udmUsageRepository.update(udmUsageDto);
        expectLastCall().once();
        udmUsageAuditService.logAction(USAGE_UID, UsageActionTypeEnum.USAGE_EDIT, ACTION_REASON_1);
        expectLastCall().once();
        udmUsageAuditService.logAction(USAGE_UID, UsageActionTypeEnum.USAGE_EDIT, ACTION_REASON_2);
        expectLastCall().once();
        udmUsageAuditService.logAction(USAGE_UID, UsageActionTypeEnum.USAGE_EDIT, ACTION_REASON_3);
        expectLastCall().once();
        replay(udmUsageRepository, baselineService, udmUsageAuditService, RupContextUtils.class);
        udmUsageService.updateUsage(udmUsageDto, actionReasons, false, REASON);
        assertEquals(USER_NAME, udmUsageDto.getUpdateUser());
        assertEquals(ASSIGNEE, udmUsageDto.getAssignee());
        assertEquals(UsageStatusEnum.ELIGIBLE, udmUsageDto.getStatus());
        verify(udmUsageRepository, baselineService, udmUsageAuditService, RupContextUtils.class);
    }

    @Test
    public void testUpdateUsageResearcherStatusNew() {
        mockStatic(RupContextUtils.class);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        UdmUsageDto udmUsageDto = buildUsageDto(UsageStatusEnum.WORK_NOT_FOUND);
        udmUsageDto.setAssignee(null);
        udmUsageAuditService.logAction(USAGE_UID, UsageActionTypeEnum.UNASSIGN,
            "Usage was unassigned from 'wjohn@copyright.com'");
        expectLastCall().once();
        List<String> actionReasons = List.of(
            "The field 'Detail Status' was edited. Old Value is 'WORK_NOT_FOUND'. New Value is 'NEW'");
        udmUsageDto.setStatus(UsageStatusEnum.NEW);
        udmUsageRepository.update(udmUsageDto);
        expectLastCall().once();
        udmUsageAuditService.logAction(USAGE_UID, UsageActionTypeEnum.USAGE_EDIT,
            "The field 'Detail Status' was edited. Old Value is 'WORK_NOT_FOUND'. New Value is 'NEW'");
        expectLastCall().once();
        replay(udmUsageRepository, udmUsageAuditService, RupContextUtils.class);
        udmUsageDto.setAssignee(ASSIGNEE);
        udmUsageService.updateUsage(udmUsageDto, actionReasons, true, StringUtils.EMPTY);
        assertEquals("SYSTEM", udmUsageDto.getUpdateUser());
        assertNull(udmUsageDto.getAssignee());
        assertEquals(UsageStatusEnum.NEW, udmUsageDto.getStatus());
        verify(udmUsageRepository, udmUsageAuditService, RupContextUtils.class);
    }

    @Test
    public void testUpdateUsageResearcherStatusOpsReview() {
        mockStatic(RupContextUtils.class);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        UdmUsageDto udmUsageDto = buildUsageDto(UsageStatusEnum.WORK_NOT_FOUND);
        udmUsageDto.setAssignee(ASSIGNEE);
        List<String> actionReasons = List.of(
            "The field 'Detail Status' was edited. Old Value is 'WORK_NOT_FOUND'. New Value is 'OPS_REVIEW'");
        udmUsageDto.setStatus(UsageStatusEnum.OPS_REVIEW);
        udmUsageRepository.update(udmUsageDto);
        expectLastCall().once();
        udmUsageAuditService.logAction(USAGE_UID, UsageActionTypeEnum.USAGE_EDIT,
            "The field 'Detail Status' was edited. Old Value is 'WORK_NOT_FOUND'. New Value is 'OPS_REVIEW'");
        expectLastCall().once();
        replay(udmUsageRepository, udmUsageAuditService, RupContextUtils.class);
        udmUsageService.updateUsage(udmUsageDto, actionReasons, true, StringUtils.EMPTY);
        assertEquals(USER_NAME, udmUsageDto.getUpdateUser());
        assertEquals(ASSIGNEE, udmUsageDto.getAssignee());
        assertEquals(UsageStatusEnum.OPS_REVIEW, udmUsageDto.getStatus());
        verify(udmUsageRepository, udmUsageAuditService, RupContextUtils.class);
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
        List<String> udmUsageIds = List.of(UDM_USAGE_UID_1, UDM_USAGE_UID_2);
        expect(udmUsageRepository.findIdsByStatus(UsageStatusEnum.WORK_FOUND)).andReturn(udmUsageIds).once();
        replay(udmUsageRepository);
        assertEquals(udmUsageIds, udmUsageService.getUdmUsageIdsByStatus(UsageStatusEnum.WORK_FOUND));
        verify(udmUsageRepository);
    }

    @Test
    public void testGetUdmUsagesByIds() {
        List<String> udmUsageIds = List.of(UDM_USAGE_UID_1, UDM_USAGE_UID_2);
        UdmUsage udmUsage1 = buildUdmUsage(UDM_USAGE_UID_1, UDM_USAGE_ORIGIN_UID_1);
        UdmUsage udmUsage2 = buildUdmUsage(UDM_USAGE_UID_2, UDM_USAGE_ORIGIN_UID_2);
        List<UdmUsage> udmUsages = List.of(udmUsage1, udmUsage2);
        expect(udmUsageRepository.findByIds(udmUsageIds)).andReturn(udmUsages).once();
        replay(udmUsageRepository);
        assertEquals(udmUsages, udmUsageService.getUdmUsagesByIds(udmUsageIds));
        verify(udmUsageRepository);
    }

    @Test
    public void testGetUsageDtos() {
        UdmUsageDto udmUsageDto = new UdmUsageDto();
        udmUsageDto.setUsageOrigin(UdmUsageOriginEnum.SS);
        List<UdmUsageDto> udmUsages = List.of(udmUsageDto);
        Pageable pageable = new Pageable(0, 1);
        Sort sort = new Sort("detailId", Sort.Direction.ASC);
        UdmUsageFilter filter = new UdmUsageFilter();
        filter.setUdmUsageOrigin(UdmUsageOriginEnum.SS);
        expect(udmUsageRepository.findDtosByFilter(filter, pageable, sort)).andReturn(udmUsages).once();
        replay(udmUsageRepository);
        List<UdmUsageDto> result = udmUsageService.getUsageDtos(filter, pageable, sort);
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
        chainExecutor.execute(List.of(usage1), ChainProcessorTypeEnum.MATCHING);
        expectLastCall().once();
        replay(chainExecutor);
        udmUsageService.sendForMatching(List.of(usage1, usage2));
        assertNotNull(captureRunnable);
        Runnable runnable = captureRunnable.getValue();
        assertNotNull(runnable);
        runnable.run();
        verify(chainExecutor);
    }

    @Test
    public void testSendForMatchingUsageDto() {
        Capture<Runnable> captureRunnable = newCapture();
        chainExecutor.execute(capture(captureRunnable));
        expectLastCall().once();
        Capture<List<UdmUsage>> usageCapture = newCapture();
        chainExecutor.execute(capture(usageCapture), eq(ChainProcessorTypeEnum.MATCHING));
        expectLastCall().once();
        replay(chainExecutor);
        UdmUsageDto udmUsageDto = buildUsageDto(UsageStatusEnum.NEW);
        udmUsageService.sendForMatching(Set.of(udmUsageDto));
        Runnable runnable = captureRunnable.getValue();
        assertNotNull(runnable);
        runnable.run();
        List<UdmUsage> actualUsages = usageCapture.getValue();
        assertEquals(1, actualUsages.size());
        UdmUsage actualUsage = actualUsages.get(0);
        assertEquals(udmUsageDto.getId(), actualUsage.getId());
        assertEquals(udmUsageDto.getWrWrkInst(), actualUsage.getWrWrkInst());
        assertEquals(udmUsageDto.getTypeOfUse(), actualUsage.getTypeOfUse());
        assertEquals(udmUsageDto.getReportedStandardNumber(), actualUsage.getReportedStandardNumber());
        assertEquals(udmUsageDto.getStandardNumber(), actualUsage.getStandardNumber());
        assertEquals(udmUsageDto.getReportedTitle(), actualUsage.getReportedTitle());
        assertEquals(udmUsageDto.getSystemTitle(), actualUsage.getSystemTitle());
        assertEquals(udmUsageDto.getPeriodEndDate(), actualUsage.getPeriodEndDate());
        assertEquals(udmUsageDto.getStatus(), actualUsage.getStatus());
        assertEquals(udmUsageDto.getVersion() + 1, actualUsage.getVersion());
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

    @Test
    public void testGetPeriods() {
        List<Integer> periods = List.of(202006, 202112);
        expect(udmUsageRepository.findPeriods()).andReturn(periods).once();
        replay(udmUsageRepository);
        assertEquals(periods, udmUsageService.getPeriods());
        verify(udmUsageRepository);
    }

    @Test
    public void testGetUserNames() {
        List<String> userNames = List.of("jjohn@copyright.com", "wjohn@copyright.com");
        expect(udmUsageRepository.findUserNames()).andReturn(userNames).once();
        replay(udmUsageRepository);
        assertEquals(userNames, udmUsageService.getUserNames());
        verify(udmUsageRepository);
    }

    @Test
    public void testGetAssignees() {
        List<String> assignees = List.of("jjohn@copyright.com", "wjohn@copyright.com");
        expect(udmUsageRepository.findAssignees()).andReturn(assignees).once();
        replay(udmUsageRepository);
        assertEquals(assignees, udmUsageService.getAssignees());
        verify(udmUsageRepository);
    }

    @Test
    public void testGetPublicationTypes() {
        List<String> pubTypes = List.of("Book", "Not Shared");
        expect(udmUsageRepository.findPublicationTypes()).andReturn(pubTypes).once();
        replay(udmUsageRepository);
        assertEquals(pubTypes, udmUsageService.getPublicationTypes());
        verify(udmUsageRepository);
    }

    @Test
    public void testGetPublicationFormats() {
        List<String> pubFormats = List.of("Digital", "Not Specified");
        expect(udmUsageRepository.findPublicationFormats()).andReturn(pubFormats).once();
        replay(udmUsageRepository);
        assertEquals(pubFormats, udmUsageService.getPublicationFormats());
        verify(udmUsageRepository);
    }

    @Test
    public void testGetAllActionReasons() {
        List<UdmActionReason> actionReasons = List.of(
            new UdmActionReason("1c8f6e43-2ca8-468d-8700-ce855e6cd8c0", "Aggregated Content"),
            new UdmActionReason("97fd8093-7f36-4a09-99f1-1bfe36a5c3f4", "Arbitrary RFA search result order"));
        expect(udmActionReasonRepository.findAll()).andReturn(actionReasons).once();
        replay(udmActionReasonRepository);
        assertEquals(actionReasons, udmUsageService.getAllActionReasons());
        verify(udmActionReasonRepository);
    }

    @Test
    public void testGetAllIneligibleReasons() {
        List<UdmIneligibleReason> ineligibleReasons = List.of(
            new UdmIneligibleReason("b60a726a-39e8-4303-abe1-6816da05b858", "Invalid survey"),
            new UdmIneligibleReason("0d5a129c-0f8f-4e48-98b2-8b980cdb9333", "Misc - See Comments"));
        expect(udmIneligibleReasonRepository.findAll()).andReturn(ineligibleReasons).once();
        replay(udmIneligibleReasonRepository);
        assertEquals(ineligibleReasons, udmUsageService.getAllIneligibleReasons());
        verify(udmIneligibleReasonRepository);
    }

    @Test
    public void testDeleteUsageBatchDetails() {
        UdmBatch udmBatch = new UdmBatch();
        udmBatch.setId(UDM_BATCH_UID);
        udmUsageRepository.deleteByBatchId(udmBatch.getId());
        expectLastCall().once();
        udmUsageAuditService.deleteActionsByBatchId(udmBatch.getId());
        expectLastCall().once();
        replay(udmUsageRepository);
        udmUsageService.deleteUdmBatchDetails(udmBatch);
        verify(udmUsageRepository);
    }

    @Test
    public void testAssignUsages() {
        mockStatic(RupContextUtils.class);
        UdmUsageDto udmUsage1 = new UdmUsageDto();
        udmUsage1.setId(UDM_USAGE_UID_1);
        UdmUsageDto udmUsage2 = new UdmUsageDto();
        udmUsage2.setId(UDM_USAGE_UID_2);
        udmUsage2.setAssignee(ASSIGNEE);
        UdmUsageDto udmUsage3 = new UdmUsageDto();
        udmUsage3.setId(UDM_USAGE_UID_3);
        udmUsage3.setAssignee(USER_NAME);
        Set<UdmUsageDto> udmUsages = new LinkedHashSet<>(List.of(udmUsage1, udmUsage2, udmUsage3));
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        udmUsageRepository.updateAssignee(new HashSet<>(List.of(udmUsage1.getId(), udmUsage2.getId())),
            USER_NAME, USER_NAME);
        expectLastCall().once();
        udmUsageAuditService.logAction(udmUsage1.getId(), UsageActionTypeEnum.ASSIGNEE_CHANGE,
            "Assignment was changed. Usage was assigned to ‘user@copyright.com’");
        expectLastCall().once();
        udmUsageAuditService.logAction(udmUsage2.getId(), UsageActionTypeEnum.ASSIGNEE_CHANGE,
            "Assignment was changed. Old assignee is 'wjohn@copyright.com'. " +
                "New assignee is 'user@copyright.com'");
        expectLastCall().once();
        replay(udmUsageRepository, udmUsageAuditService, RupContextUtils.class);
        udmUsageService.assignUsages(udmUsages);
        verify(udmUsageRepository, udmUsageAuditService, RupContextUtils.class);
    }

    @Test
    public void testUnAssignUsages() {
        mockStatic(RupContextUtils.class);
        UdmUsageDto udmUsage = new UdmUsageDto();
        udmUsage.setId(UDM_USAGE_UID_1);
        udmUsage.setAssignee(ASSIGNEE);
        Set<UdmUsageDto> udmUsages = Set.of(udmUsage);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        udmUsageRepository.updateAssignee(Set.of(udmUsage.getId()), null, USER_NAME);
        expectLastCall().once();
        udmUsageAuditService.logAction(udmUsage.getId(), UsageActionTypeEnum.UNASSIGN,
            "Usage was unassigned from 'wjohn@copyright.com'");
        expectLastCall().once();
        replay(udmUsageRepository, udmUsageAuditService, RupContextUtils.class);
        udmUsageService.unassignUsages(udmUsages);
        verify(udmUsageRepository, udmUsageAuditService, RupContextUtils.class);
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
        udmUsage.setQuantity(7L);
        return udmUsage;
    }

    private UdmUsageDto buildUsageDto(UsageStatusEnum statusEnum) {
        UdmUsageDto udmUsageDto = new UdmUsageDto();
        udmUsageDto.setId(USAGE_UID);
        udmUsageDto.setWrWrkInst(122825347L);
        udmUsageDto.setTypeOfUse("PRINT");
        udmUsageDto.setReportedStandardNumber("0927-7765");
        udmUsageDto.setStandardNumber("0927-7765");
        udmUsageDto.setReportedTitle("Colloids and surfaces. B, Biointerfaces");
        udmUsageDto.setSystemTitle("Colloids and surfaces. B, Biointerfaces");
        udmUsageDto.setPeriodEndDate(LocalDate.of(2021, 12, 31));
        udmUsageDto.setStatus(statusEnum);
        udmUsageDto.setVersion(2);
        return udmUsageDto;
    }

    private void assertUdmUsage(UdmUsage udmUsage) {
        assertEquals(UDM_BATCH_UID, udmUsage.getBatchId());
        assertEquals(202006, udmUsage.getPeriod(), 0);
        assertEquals(LocalDate.of(2020, 6, 30), udmUsage.getPeriodEndDate());
        assertEquals(ANNUAL_MULTIPLIER, udmUsage.getAnnualMultiplier().intValue());
        assertEquals(STATISTICAL_MULTIPLIER, udmUsage.getStatisticalMultiplier());
        assertEquals(USER_NAME, udmUsage.getCreateUser());
        assertEquals(USER_NAME, udmUsage.getUpdateUser());
    }
}
