package com.copyright.rup.dist.foreign.service.impl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.FundPoolDetail;
import com.copyright.rup.dist.foreign.repository.api.IFundPoolRepository;
import com.copyright.rup.dist.foreign.service.api.ILicenseeClassService;
import com.copyright.rup.dist.foreign.service.api.nts.INtsUsageService;

import com.google.common.collect.Lists;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * Verifies {@link FundPoolService}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 03/28/2019
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(RupContextUtils.class)
public class FundPoolServiceTest {

    private static final AggregateLicenseeClass AGG_LICENSEE_CLASS_108 =
        buildAggregateLicenseeClass(108, "EXGP", "Life Sciences");
    private static final AggregateLicenseeClass AGG_LICENSEE_CLASS_110 =
        buildAggregateLicenseeClass(110, "EXU4", " Life Sciences");
    private static final AggregateLicenseeClass AGG_LICENSEE_CLASS_111 =
        buildAggregateLicenseeClass(111, "HGP", "Life Sciences");
    private static final AggregateLicenseeClass AGG_LICENSEE_CLASS_113 =
        buildAggregateLicenseeClass(113, "MU", "Life Sciences");
    private static final String FUND_POOL_ID = "ce468e8a-fdea-4d0c-a8f2-0e8c9f479bc3";
    private static final String BATCH_POOL_ID = "d3da0c18-e6af-4e7c-8b67-795f93e38e90";
    private static final String NTS_PRODUCT_FAMILY = "NTS";
    private static final String FUND_POOL_NAME = "FAS Q3 2019";
    private static final String USER_NAME = "User Name";
    private static final String AMOUNT_1000 = "1000.00";
    private static final BigDecimal SAL_SERVICE_FEE = new BigDecimal("0.25");

    private FundPoolService fundPoolService;
    private INtsUsageService ntsUsageService;
    private IFundPoolRepository fundPoolRepository;
    private ILicenseeClassService licenseeClassService;

    private static AggregateLicenseeClass buildAggregateLicenseeClass(Integer id, String enrollmentProfile,
                                                                      String discipline) {
        AggregateLicenseeClass alc = new AggregateLicenseeClass();
        alc.setId(id);
        alc.setEnrollmentProfile(enrollmentProfile);
        alc.setDiscipline(discipline);
        return alc;
    }

    @Before
    public void setUp() {
        fundPoolService = new FundPoolService();
        fundPoolRepository = createMock(IFundPoolRepository.class);
        ntsUsageService = createMock(INtsUsageService.class);
        licenseeClassService = createMock(ILicenseeClassService.class);
        Whitebox.setInternalState(fundPoolService, ntsUsageService);
        Whitebox.setInternalState(fundPoolService, fundPoolRepository);
        Whitebox.setInternalState(fundPoolService, licenseeClassService);
    }

    @Test
    public void testGetFundPoolById() {
        FundPool fundPool = new FundPool();
        expect(fundPoolRepository.findById(FUND_POOL_ID)).andReturn(fundPool).once();
        replay(fundPoolRepository);
        assertSame(fundPool, fundPoolService.getFundPoolById(FUND_POOL_ID));
        verify(fundPoolRepository);
    }

    @Test
    public void testCreateNtsFundPool() {
        mockStatic(RupContextUtils.class);
        FundPool fund = buildNtsFundPool();
        Set<String> batchIds = Collections.singleton(RupPersistUtils.generateUuid());
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        fundPoolRepository.insert(fund);
        expectLastCall().once();
        ntsUsageService.addWithdrawnUsagesToNtsFundPool(fund.getId(), batchIds, USER_NAME);
        expectLastCall().once();
        replay(RupContextUtils.class, fundPoolRepository, ntsUsageService);
        fundPoolService.createNtsFundPool(fund, batchIds);
        verify(RupContextUtils.class, fundPoolRepository, ntsUsageService);
    }

    @Test
    public void testCreateAaclFundPool() {
        mockStatic(RupContextUtils.class);
        FundPool fundPool = new FundPool();
        fundPool.setId("5b3aae10-df25-43d3-8a7a-1cce60030827");
        FundPoolDetail detail1 = new FundPoolDetail();
        detail1.setGrossAmount(BigDecimal.ONE);
        FundPoolDetail detail2 = new FundPoolDetail();
        detail2.setGrossAmount(BigDecimal.ONE);
        List<FundPoolDetail> details = Lists.newArrayList(detail1, detail2);
        Capture<FundPool> fundPoolCapture = new Capture<>();
        Capture<FundPoolDetail> detailCapture1 = new Capture<>();
        Capture<FundPoolDetail> detailCapture2 = new Capture<>();
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        fundPoolRepository.insert(capture(fundPoolCapture));
        expectLastCall().once();
        fundPoolRepository.insertDetail(capture(detailCapture1));
        expectLastCall().once();
        fundPoolRepository.insertDetail(capture(detailCapture2));
        expectLastCall().once();
        replay(fundPoolRepository, RupContextUtils.class);
        assertEquals(2, fundPoolService.createAaclFundPool(fundPool, details));
        verify(fundPoolRepository, RupContextUtils.class);
        FundPool fundPoolActual = fundPoolCapture.getValue();
        assertNotNull(fundPoolActual.getId());
        assertEquals(new BigDecimal(2), fundPoolActual.getTotalAmount());
        assertEquals(USER_NAME, fundPoolActual.getCreateUser());
        assertEquals(USER_NAME, fundPoolActual.getUpdateUser());
        FundPoolDetail detailActual1 = detailCapture1.getValue();
        assertNotNull(detailActual1.getId());
        assertEquals(fundPool.getId(), detailActual1.getFundPoolId());
        assertEquals(USER_NAME, detailActual1.getCreateUser());
        assertEquals(USER_NAME, detailActual1.getUpdateUser());
        FundPoolDetail detailActual2 = detailCapture2.getValue();
        assertNotNull(detailActual2.getId());
        assertEquals(fundPool.getId(), detailActual2.getFundPoolId());
        assertEquals(USER_NAME, detailActual2.getCreateUser());
        assertEquals(USER_NAME, detailActual2.getUpdateUser());
    }

    @Test
    public void testGetFundPools() {
        List<FundPool> funds = Collections.singletonList(buildNtsFundPool());
        expect(fundPoolRepository.findByProductFamily(NTS_PRODUCT_FAMILY)).andReturn(funds).once();
        replay(fundPoolRepository);
        assertEquals(funds, fundPoolService.getFundPools(NTS_PRODUCT_FAMILY));
        verify(fundPoolRepository);
    }

    @Test
    public void testGetNtsNotAttachedToScenario() {
        List<FundPool> funds = Collections.singletonList(buildNtsFundPool());
        expect(fundPoolRepository.findNtsNotAttachedToScenario()).andReturn(funds).once();
        replay(fundPoolRepository);
        assertEquals(funds, fundPoolService.getNtsNotAttachedToScenario());
        verify(fundPoolRepository);
    }

    @Test
    public void testGetAaclNotAttachedToScenario() {
        List<FundPool> funds = Collections.singletonList(buildAaclFundPool());
        expect(fundPoolRepository.findAaclNotAttachedToScenario()).andReturn(funds).once();
        replay(fundPoolRepository);
        assertEquals(funds, fundPoolService.getAaclNotAttachedToScenario());
        verify(fundPoolRepository);
    }

    @Test
    public void testGetSalNotAttachedToScenario() {
        List<FundPool> funds = Collections.singletonList(buildSalFundPool());
        expect(fundPoolRepository.findSalNotAttachedToScenario()).andReturn(funds).once();
        replay(fundPoolRepository);
        assertEquals(funds, fundPoolService.getSalNotAttachedToScenario());
        verify(fundPoolRepository);
    }

    @Test
    public void testDeleteNtsFundPool() {
        ntsUsageService.deleteFromNtsFundPool(FUND_POOL_ID);
        expectLastCall().once();
        expect(fundPoolRepository.delete(FUND_POOL_ID)).andReturn(2).once();
        replay(fundPoolRepository, ntsUsageService);
        fundPoolService.deleteNtsFundPool(buildNtsFundPool());
        verify(fundPoolRepository, ntsUsageService);
    }

    @Test
    public void testGetNtsFundPoolNamesByUsageBatchId() {
        List<String> names = Arrays.asList("Test 1", "Test 2");
        expect(fundPoolRepository.findNamesByUsageBatchId(BATCH_POOL_ID)).andReturn(names).once();
        replay(fundPoolRepository);
        assertEquals(names, fundPoolService.getNtsFundPoolNamesByUsageBatchId(BATCH_POOL_ID));
        verify(fundPoolRepository);
    }

    @Test
    public void testFundPoolExists() {
        expect(fundPoolRepository.fundPoolExists(NTS_PRODUCT_FAMILY, FUND_POOL_NAME)).andReturn(true).once();
        replay(fundPoolRepository);
        assertTrue(fundPoolService.fundPoolExists(NTS_PRODUCT_FAMILY, FUND_POOL_NAME));
        verify(fundPoolRepository);
    }

    @Test
    public void testGetAaclDetailsByFundPoolId() {
        FundPoolDetail detail1 =
            buildDetail("c6fa3d97-2b70-4a7e-a4ee-e8c405caf54c", AGG_LICENSEE_CLASS_108, BigDecimal.ONE);
        FundPoolDetail detail2 =
            buildDetail("9ce7ad63-c335-477a-a39b-43d5cc9c4cd8", AGG_LICENSEE_CLASS_111, BigDecimal.TEN);
        List<FundPoolDetail> expectedDetails =
            Arrays.asList(detail1, buildZeroDetail(AGG_LICENSEE_CLASS_110), detail2, buildZeroDetail(
                AGG_LICENSEE_CLASS_113));
        expect(licenseeClassService.getAggregateLicenseeClasses())
            .andReturn(Arrays.asList(AGG_LICENSEE_CLASS_108, AGG_LICENSEE_CLASS_110,
                AGG_LICENSEE_CLASS_111, AGG_LICENSEE_CLASS_113)).once();
        expect(fundPoolRepository.findDetailsByFundPoolId(FUND_POOL_ID))
            .andReturn(Arrays.asList(detail1, detail2)).once();
        replay(fundPoolRepository, licenseeClassService);
        List<FundPoolDetail> actualDetails = fundPoolService.getDetailsByFundPoolId(FUND_POOL_ID);
        verify(fundPoolRepository, licenseeClassService);
        assertEquals(expectedDetails.size(), actualDetails.size());
        IntStream.range(0, expectedDetails.size())
            .forEach(i -> verifyDetail(expectedDetails.get(i), actualDetails.get(i)));
    }

    @Test
    public void testDeleteAaclFundPoolById() {
        fundPoolRepository.deleteDetailsByFundPoolId(FUND_POOL_ID);
        expectLastCall().once();
        expect(fundPoolRepository.delete(FUND_POOL_ID)).andReturn(2).once();
        replay(fundPoolRepository);
        fundPoolService.deleteAaclFundPool(buildAaclFundPool());
        verify(fundPoolRepository);
    }

    @Test
    public void testDeleteSalFundPoolById() {
        FundPool fundPool = new FundPool();
        fundPool.setId(FUND_POOL_ID);
        expect(fundPoolRepository.delete(fundPool.getId())).andReturn(1).once();
        replay(fundPoolRepository);
        fundPoolService.deleteSalFundPool(fundPool);
        verify(fundPoolRepository);
    }

    @Test
    public void testCalculateSalFundPool() {
        BigDecimal splitPercent = new BigDecimal("0.02");
        FundPool fundPool = fundPoolService.calculateSalFundPoolAmounts(buildFundPool(splitPercent, 10, 5));
        assertEquals(new BigDecimal("750.00"), fundPool.getTotalAmount());
        assertEquals(new BigDecimal("15.02"), fundPool.getSalFields().getItemBankAmount());
        assertEquals(new BigDecimal("20.00"), fundPool.getSalFields().getItemBankGrossAmount());
        assertEquals(new BigDecimal("489.99"), fundPool.getSalFields().getGradeKto5GrossAmount());
        assertEquals(new BigDecimal("244.99"), fundPool.getSalFields().getGrade6to8GrossAmount());
        assertEquals(BigDecimal.ZERO, fundPool.getSalFields().getGrade9to12GrossAmount());
        assertEquals(new BigDecimal(AMOUNT_1000), fundPool.getSalFields().getGrossAmount());
        assertEquals(splitPercent, fundPool.getSalFields().getItemBankSplitPercent());
        assertEquals(10, fundPool.getSalFields().getGradeKto5NumberOfStudents());
        assertEquals(5, fundPool.getSalFields().getGrade6to8NumberOfStudents());
        assertEquals(0, fundPool.getSalFields().getGrade9to12NumberOfStudents());
        assertEquals("FY2020 COG", fundPool.getSalFields().getAssessmentName());
        assertEquals(SAL_SERVICE_FEE, fundPool.getSalFields().getServiceFee());
    }

    @Test
    public void testCalculateSalFundPoolSplitPercentIsHundred() {
        BigDecimal splitPercent = new BigDecimal("1.00");
        FundPool fundPool = fundPoolService.calculateSalFundPoolAmounts(buildFundPool(splitPercent, 0, 0));
        assertEquals(new BigDecimal("750.00"), fundPool.getTotalAmount());
        assertEquals(new BigDecimal("750.00"), fundPool.getSalFields().getItemBankAmount());
        assertEquals(new BigDecimal(AMOUNT_1000), fundPool.getSalFields().getItemBankGrossAmount());
        assertEquals(BigDecimal.ZERO, fundPool.getSalFields().getGradeKto5GrossAmount());
        assertEquals(BigDecimal.ZERO, fundPool.getSalFields().getGrade6to8GrossAmount());
        assertEquals(BigDecimal.ZERO, fundPool.getSalFields().getGrade9to12GrossAmount());
        assertEquals(new BigDecimal(AMOUNT_1000), fundPool.getSalFields().getGrossAmount());
        assertEquals(splitPercent, fundPool.getSalFields().getItemBankSplitPercent());
        assertEquals(0, fundPool.getSalFields().getGradeKto5NumberOfStudents());
        assertEquals(0, fundPool.getSalFields().getGrade6to8NumberOfStudents());
        assertEquals(0, fundPool.getSalFields().getGrade9to12NumberOfStudents());
        assertEquals("FY2020 COG", fundPool.getSalFields().getAssessmentName());
        assertEquals(SAL_SERVICE_FEE, fundPool.getSalFields().getServiceFee());
    }

    @Test
    public void testCreateSalFundPool() {
        mockStatic(RupContextUtils.class);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        FundPool fundPool = new FundPool();
        fundPoolRepository.insert(fundPool);
        expectLastCall().once();
        replay(RupContextUtils.class, fundPoolRepository);
        fundPoolService.createSalFundPool(fundPool);
        assertEquals(fundPool.getCreateUser(), USER_NAME);
        assertEquals(fundPool.getUpdateUser(), USER_NAME);
        assertNotNull(fundPool.getId());
        verify(RupContextUtils.class, fundPoolRepository);
    }

    private void verifyDetail(FundPoolDetail expected, FundPoolDetail actual) {
        assertEquals(expected.getId(), actual.getId());
        AggregateLicenseeClass expectedAggregate = expected.getAggregateLicenseeClass();
        AggregateLicenseeClass actualAggregate = actual.getAggregateLicenseeClass();
        assertEquals(expectedAggregate.getId(), actualAggregate.getId());
        assertEquals(expectedAggregate.getEnrollmentProfile(), actualAggregate.getEnrollmentProfile());
        assertEquals(expectedAggregate.getDiscipline(), actualAggregate.getDiscipline());
        assertEquals(expected.getGrossAmount(), actual.getGrossAmount());
    }

    private FundPool buildNtsFundPool() {
        FundPool fundPool = new FundPool();
        fundPool.setId(FUND_POOL_ID);
        fundPool.setProductFamily(NTS_PRODUCT_FAMILY);
        fundPool.setName(FUND_POOL_NAME);
        fundPool.setTotalAmount(BigDecimal.TEN);
        return fundPool;
    }

    private FundPool buildAaclFundPool() {
        FundPool fundPool = new FundPool();
        fundPool.setId(FUND_POOL_ID);
        fundPool.setProductFamily("AACL");
        fundPool.setName(FUND_POOL_NAME);
        fundPool.setTotalAmount(BigDecimal.TEN);
        return fundPool;
    }

    private FundPool buildSalFundPool() {
        FundPool fundPool = new FundPool();
        fundPool.setId(FUND_POOL_ID);
        fundPool.setProductFamily("SAL");
        fundPool.setName(FUND_POOL_NAME);
        fundPool.setTotalAmount(BigDecimal.TEN);
        return fundPool;
    }

    private FundPoolDetail buildDetail(String id, AggregateLicenseeClass alc, BigDecimal grossAmount) {
        FundPoolDetail detail = new FundPoolDetail();
        detail.setId(id);
        detail.setAggregateLicenseeClass(alc);
        detail.setGrossAmount(grossAmount);
        return detail;
    }

    private FundPoolDetail buildZeroDetail(AggregateLicenseeClass alc) {
        FundPoolDetail detail = new FundPoolDetail();
        detail.setAggregateLicenseeClass(alc);
        detail.setGrossAmount(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
        return detail;
    }

    private FundPool buildFundPool(BigDecimal splitPercent, int gradeKto5NumberOfStudents,
                                   int grade6to8NumberOfStudents) {
        FundPool fundPool = new FundPool();
        FundPool.SalFields salFields = new FundPool.SalFields();
        salFields.setDateReceived(LocalDate.of(2020, 12, 24));
        salFields.setAssessmentName("FY2020 COG");
        salFields.setLicenseeAccountNumber(1000008985L);
        salFields.setLicenseeName("FarmField Inc.");
        salFields.setGradeKto5NumberOfStudents(gradeKto5NumberOfStudents);
        salFields.setGrade6to8NumberOfStudents(grade6to8NumberOfStudents);
        salFields.setGrossAmount(new BigDecimal(AMOUNT_1000));
        salFields.setItemBankSplitPercent(splitPercent);
        salFields.setServiceFee(SAL_SERVICE_FEE);
        fundPool.setSalFields(salFields);
        return fundPool;
    }
}
