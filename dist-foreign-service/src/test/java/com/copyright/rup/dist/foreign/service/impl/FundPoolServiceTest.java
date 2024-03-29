package com.copyright.rup.dist.foreign.service.impl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.newCapture;
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
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.FundPool.AclciFields;
import com.copyright.rup.dist.foreign.domain.FundPool.SalFields;
import com.copyright.rup.dist.foreign.domain.FundPoolDetail;
import com.copyright.rup.dist.foreign.repository.api.IFundPoolRepository;
import com.copyright.rup.dist.foreign.service.api.ILicenseeClassService;
import com.copyright.rup.dist.foreign.service.api.nts.INtsUsageService;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    private static final String FUND_POOL_NAME = "Fund Pool";
    private static final String FUND_POOL_ID = "ce468e8a-fdea-4d0c-a8f2-0e8c9f479bc3";
    private static final String BATCH_POOL_ID = "d3da0c18-e6af-4e7c-8b67-795f93e38e90";
    private static final String NTS_PRODUCT_FAMILY = "NTS";
    private static final String SAL_PRODUCT_FAMILY = "SAL";
    private static final String AACL_PRODUCT_FAMILY = "AACL";
    private static final String ACLCI_PRODUCT_FAMILY = "ACLCI";
    private static final String COVERAGE_YEARS = "2021-2022";
    private static final String USER_NAME = "User Name";
    private static final BigDecimal AMOUNT_1000 = new BigDecimal("1000.00");
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
        Whitebox.setInternalState(fundPoolService, "salServiceFee", SAL_SERVICE_FEE);
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
        Set<String> batchIds = Set.of(RupPersistUtils.generateUuid());
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
        List<FundPoolDetail> details = List.of(detail1, detail2);
        Capture<FundPool> fundPoolCapture = newCapture();
        Capture<FundPoolDetail> detailCapture1 = newCapture();
        Capture<FundPoolDetail> detailCapture2 = newCapture();
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
        List<FundPool> funds = List.of(buildNtsFundPool());
        expect(fundPoolRepository.findByProductFamily(NTS_PRODUCT_FAMILY)).andReturn(funds).once();
        replay(fundPoolRepository);
        assertEquals(funds, fundPoolService.getFundPools(NTS_PRODUCT_FAMILY));
        verify(fundPoolRepository);
    }

    @Test
    public void testGetNtsNotAttachedToScenario() {
        List<FundPool> funds = List.of(buildNtsFundPool());
        expect(fundPoolRepository.findNtsNotAttachedToScenario()).andReturn(funds).once();
        replay(fundPoolRepository);
        assertEquals(funds, fundPoolService.getNtsNotAttachedToScenario());
        verify(fundPoolRepository);
    }

    @Test
    public void testGetAaclNotAttachedToScenario() {
        List<FundPool> funds = List.of(buildAaclFundPool());
        expect(fundPoolRepository.findAaclNotAttachedToScenario()).andReturn(funds).once();
        replay(fundPoolRepository);
        assertEquals(funds, fundPoolService.getAaclNotAttachedToScenario());
        verify(fundPoolRepository);
    }

    @Test
    public void testGetSalNotAttachedToScenario() {
        List<FundPool> funds = List.of(buildSalFundPool());
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
        List<String> names = List.of("Test 1", "Test 2");
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
            List.of(detail1, buildZeroDetail(AGG_LICENSEE_CLASS_110), detail2, buildZeroDetail(AGG_LICENSEE_CLASS_113));
        expect(licenseeClassService.getAggregateLicenseeClasses(AACL_PRODUCT_FAMILY))
            .andReturn(List.of(AGG_LICENSEE_CLASS_108, AGG_LICENSEE_CLASS_110,
                AGG_LICENSEE_CLASS_111, AGG_LICENSEE_CLASS_113)).once();
        expect(fundPoolRepository.findDetailsByFundPoolId(FUND_POOL_ID)).andReturn(List.of(detail1, detail2)).once();
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
    public void testDeleteAclciFundPoolById() {
        FundPool fundPool = new FundPool();
        fundPool.setId(FUND_POOL_ID);
        expect(fundPoolRepository.delete(fundPool.getId())).andReturn(1).once();
        replay(fundPoolRepository);
        fundPoolService.deleteAclciFundPool(fundPool);
        verify(fundPoolRepository);
    }

    @Test
    public void testCalculateSalFundPool() {
        BigDecimal splitPercent = new BigDecimal("0.10");
        FundPool fundPool = fundPoolService.calculateSalFundPoolAmounts(buildSalFundPool(splitPercent, 9, 6, 3));
        assertEquals(AMOUNT_1000, fundPool.getTotalAmount());
        assertEquals(new BigDecimal("100.00"), fundPool.getSalFields().getItemBankGrossAmount());
        assertEquals(new BigDecimal("450.00"), fundPool.getSalFields().getGradeKto5GrossAmount());
        assertEquals(new BigDecimal("300.00"), fundPool.getSalFields().getGrade6to8GrossAmount());
        assertEquals(new BigDecimal("150.00"), fundPool.getSalFields().getGrade9to12GrossAmount());
        assertEquals(AMOUNT_1000, fundPool.getSalFields().getGrossAmount());
        assertEquals(splitPercent, fundPool.getSalFields().getItemBankSplitPercent());
        assertEquals(9, fundPool.getSalFields().getGradeKto5NumberOfStudents());
        assertEquals(6, fundPool.getSalFields().getGrade6to8NumberOfStudents());
        assertEquals(3, fundPool.getSalFields().getGrade9to12NumberOfStudents());
        assertEquals("FY2020 COG", fundPool.getSalFields().getAssessmentName());
        assertEquals(SAL_SERVICE_FEE, fundPool.getSalFields().getServiceFee());
    }

    @Test
    public void testCalculateSalFundPoolSplitPercentIsHundred() {
        BigDecimal splitPercent = new BigDecimal("1.00");
        BigDecimal gradeGrossAmount = new BigDecimal("0.00");
        FundPool fundPool = fundPoolService.calculateSalFundPoolAmounts(buildSalFundPool(splitPercent, 0, 0, 0));
        assertEquals(AMOUNT_1000, fundPool.getTotalAmount());
        assertEquals(AMOUNT_1000, fundPool.getSalFields().getItemBankGrossAmount());
        assertEquals(gradeGrossAmount, fundPool.getSalFields().getGradeKto5GrossAmount());
        assertEquals(gradeGrossAmount, fundPool.getSalFields().getGrade6to8GrossAmount());
        assertEquals(gradeGrossAmount, fundPool.getSalFields().getGrade9to12GrossAmount());
        assertEquals(AMOUNT_1000, fundPool.getSalFields().getGrossAmount());
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

    @Test
    public void testCalculateAclciFundPool() {
        BigDecimal splitPercent = new BigDecimal("0.10");
        FundPool fundPool = fundPoolService.calculateAclciFundPoolAmounts(
            buildAclciFundPool(splitPercent, 8, 4, 3, 2, 1));
        assertEquals(ACLCI_PRODUCT_FAMILY, fundPool.getProductFamily());
        assertEquals(FUND_POOL_NAME, fundPool.getName());
        assertEquals(AMOUNT_1000, fundPool.getTotalAmount());
        assertEquals(COVERAGE_YEARS, fundPool.getAclciFields().getCoverageYears());
        assertEquals(8, fundPool.getAclciFields().getGradeKto2NumberOfStudents());
        assertEquals(4, fundPool.getAclciFields().getGrade3to5NumberOfStudents());
        assertEquals(3, fundPool.getAclciFields().getGrade6to8NumberOfStudents());
        assertEquals(2, fundPool.getAclciFields().getGrade9to12NumberOfStudents());
        assertEquals(1, fundPool.getAclciFields().getGradeHeNumberOfStudents());
        assertEquals(AMOUNT_1000, fundPool.getAclciFields().getGrossAmount());
        assertEquals(splitPercent, fundPool.getAclciFields().getCurriculumDbSplitPercent());
        assertEquals(new BigDecimal("400.00"), fundPool.getAclciFields().getGradeKto2GrossAmount());
        assertEquals(new BigDecimal("200.00"), fundPool.getAclciFields().getGrade3to5GrossAmount());
        assertEquals(new BigDecimal("150.00"), fundPool.getAclciFields().getGrade6to8GrossAmount());
        assertEquals(new BigDecimal("100.00"), fundPool.getAclciFields().getGrade9to12GrossAmount());
        assertEquals(new BigDecimal("50.00"), fundPool.getAclciFields().getGradeHeGrossAmount());
        assertEquals(new BigDecimal("100.00"), fundPool.getAclciFields().getCurriculumDbGrossAmount());
    }

    @Test
    public void testCalculateAclciFundPoolSplitPercentIsHundred() {
        BigDecimal splitPercent = new BigDecimal("1.00");
        BigDecimal gradeGrossAmount = new BigDecimal("0.00");
        FundPool fundPool = fundPoolService.calculateAclciFundPoolAmounts(
            buildAclciFundPool(splitPercent, 0, 0, 0, 0, 0));
        assertEquals(ACLCI_PRODUCT_FAMILY, fundPool.getProductFamily());
        assertEquals(FUND_POOL_NAME, fundPool.getName());
        assertEquals(AMOUNT_1000, fundPool.getTotalAmount());
        assertEquals(COVERAGE_YEARS, fundPool.getAclciFields().getCoverageYears());
        assertEquals(0, fundPool.getAclciFields().getGradeKto2NumberOfStudents());
        assertEquals(0, fundPool.getAclciFields().getGrade3to5NumberOfStudents());
        assertEquals(0, fundPool.getAclciFields().getGrade6to8NumberOfStudents());
        assertEquals(0, fundPool.getAclciFields().getGrade9to12NumberOfStudents());
        assertEquals(0, fundPool.getAclciFields().getGradeHeNumberOfStudents());
        assertEquals(AMOUNT_1000, fundPool.getAclciFields().getGrossAmount());
        assertEquals(splitPercent, fundPool.getAclciFields().getCurriculumDbSplitPercent());
        assertEquals(gradeGrossAmount, fundPool.getAclciFields().getGradeKto2GrossAmount());
        assertEquals(gradeGrossAmount, fundPool.getAclciFields().getGrade3to5GrossAmount());
        assertEquals(gradeGrossAmount, fundPool.getAclciFields().getGrade6to8GrossAmount());
        assertEquals(gradeGrossAmount, fundPool.getAclciFields().getGrade9to12GrossAmount());
        assertEquals(gradeGrossAmount, fundPool.getAclciFields().getGradeHeGrossAmount());
        assertEquals(AMOUNT_1000, fundPool.getAclciFields().getCurriculumDbGrossAmount());
    }

    @Test
    public void testCreateAclciFundPool() {
        mockStatic(RupContextUtils.class);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        FundPool fundPool = new FundPool();
        fundPoolRepository.insert(fundPool);
        expectLastCall().once();
        replay(RupContextUtils.class, fundPoolRepository);
        fundPoolService.createAclciFundPool(fundPool);
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
        fundPool.setProductFamily(AACL_PRODUCT_FAMILY);
        fundPool.setName(FUND_POOL_NAME);
        fundPool.setTotalAmount(BigDecimal.TEN);
        return fundPool;
    }

    private FundPool buildSalFundPool() {
        FundPool fundPool = new FundPool();
        fundPool.setId(FUND_POOL_ID);
        fundPool.setProductFamily(SAL_PRODUCT_FAMILY);
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
        detail.setGrossAmount(FdaConstants.DEFAULT_AMOUNT_SCALE_2);
        return detail;
    }

    private FundPool buildSalFundPool(BigDecimal splitPercent, int gradeKto5NumberOfStudents,
                                      int grade6to8NumberOfStudents, int grade9to12NumberOfStudents) {
        FundPool fundPool = new FundPool();
        SalFields salFields = new SalFields();
        salFields.setDateReceived(LocalDate.of(2020, 12, 24));
        salFields.setAssessmentName("FY2020 COG");
        salFields.setLicenseeAccountNumber(1000008985L);
        salFields.setLicenseeName("FarmField Inc.");
        salFields.setGradeKto5NumberOfStudents(gradeKto5NumberOfStudents);
        salFields.setGrade6to8NumberOfStudents(grade6to8NumberOfStudents);
        salFields.setGrade9to12NumberOfStudents(grade9to12NumberOfStudents);
        salFields.setGrossAmount(AMOUNT_1000);
        salFields.setItemBankSplitPercent(splitPercent);
        salFields.setServiceFee(SAL_SERVICE_FEE);
        fundPool.setSalFields(salFields);
        return fundPool;
    }

    private FundPool buildAclciFundPool(BigDecimal splitPercent, int gradeKto2NumberOfStudents,
                                        int grade3to5NumberOfStudents, int grade6to8NumberOfStudents,
                                        int grade9to12NumberOfStudents, int gradeHeNumberOfStudents) {
        FundPool fundPool = new FundPool();
        fundPool.setProductFamily(ACLCI_PRODUCT_FAMILY);
        fundPool.setName(FUND_POOL_NAME);
        AclciFields aclciFields = new AclciFields();
        aclciFields.setCoverageYears(COVERAGE_YEARS);
        aclciFields.setGradeKto2NumberOfStudents(gradeKto2NumberOfStudents);
        aclciFields.setGrade3to5NumberOfStudents(grade3to5NumberOfStudents);
        aclciFields.setGrade6to8NumberOfStudents(grade6to8NumberOfStudents);
        aclciFields.setGrade9to12NumberOfStudents(grade9to12NumberOfStudents);
        aclciFields.setGradeHeNumberOfStudents(gradeHeNumberOfStudents);
        aclciFields.setGrossAmount(AMOUNT_1000);
        aclciFields.setCurriculumDbSplitPercent(splitPercent);
        fundPool.setAclciFields(aclciFields);
        return fundPool;
    }
}
