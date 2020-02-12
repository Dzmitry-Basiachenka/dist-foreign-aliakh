package com.copyright.rup.dist.foreign.service.impl.aacl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.FundPoolDetail;
import com.copyright.rup.dist.foreign.repository.api.IAaclFundPoolRepository;
import com.copyright.rup.dist.foreign.repository.api.IFundPoolRepository;
import com.copyright.rup.dist.foreign.service.api.ILicenseeClassService;

import com.google.common.collect.Lists;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Verifies {@link AaclFundPoolService}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 05/02/2020
 *
 * @author Aliaksandr Liakh
 */
public class AaclFundPoolServiceTest {

    private static final AggregateLicenseeClass AGG_LICENSEE_CLASS_108 =
        buildAggregateLicenseeClass(108, "EXGP - Life Sciences");
    private static final AggregateLicenseeClass AGG_LICENSEE_CLASS_110 =
        buildAggregateLicenseeClass(110, "EXU4 - Life Sciences");
    private static final AggregateLicenseeClass AGG_LICENSEE_CLASS_111 =
        buildAggregateLicenseeClass(111, "HGP - Life Sciences");
    private static final AggregateLicenseeClass AGG_LICENSEE_CLASS_113 =
        buildAggregateLicenseeClass(113, "MU - Life Sciences");
    private static final String FUND_POOL_ID = "5a40ff60-31d2-4bab-9871-60cff88b7889";
    private static final String USER_NAME = "SYSTEM";

    private AaclFundPoolService aaclFundPoolService;
    private IAaclFundPoolRepository aaclFundPoolRepository;
    private IFundPoolRepository fundPoolRepository;
    private ILicenseeClassService licenseeClassService;

    private static AggregateLicenseeClass buildAggregateLicenseeClass(Integer id, String name) {
        AggregateLicenseeClass alc = new AggregateLicenseeClass();
        alc.setId(id);
        alc.setName(name);
        return alc;
    }

    @Before
    public void setUp() {
        aaclFundPoolRepository = createStrictMock(IAaclFundPoolRepository.class);
        fundPoolRepository = createMock(IFundPoolRepository.class);
        licenseeClassService = createMock(ILicenseeClassService.class);
        aaclFundPoolService = new AaclFundPoolService();
        Whitebox.setInternalState(aaclFundPoolService, aaclFundPoolRepository);
        Whitebox.setInternalState(aaclFundPoolService, fundPoolRepository);
        Whitebox.setInternalState(aaclFundPoolService, licenseeClassService);
    }

    @Test
    public void testGetFundPools() {
        List<FundPool> fundPools = Collections.singletonList(new FundPool());
        expect(aaclFundPoolRepository.findAll()).andReturn(fundPools).once();
        replay(aaclFundPoolRepository);
        assertEquals(fundPools, aaclFundPoolService.getFundPools());
        verify(aaclFundPoolRepository);
    }

    @Test
    public void testGetDetailsByFundPoolId() {
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
        expect(aaclFundPoolRepository.findDetailsByFundPoolId(FUND_POOL_ID))
            .andReturn(Arrays.asList(detail1, detail2)).once();
        replay(aaclFundPoolRepository, licenseeClassService);
        List<FundPoolDetail> actualDetails = aaclFundPoolService.getDetailsByFundPoolId(FUND_POOL_ID);
        verify(aaclFundPoolRepository, licenseeClassService);
        assertEquals(expectedDetails.size(), actualDetails.size());
        IntStream.range(0, expectedDetails.size())
            .forEach(i -> verifyDetail(expectedDetails.get(i), actualDetails.get(i)));
    }

    @Test
    public void testDeleteFundPoolById() {
        aaclFundPoolRepository.deleteDetailsByFundPoolId(FUND_POOL_ID);
        expectLastCall().once();
        aaclFundPoolRepository.deleteById(FUND_POOL_ID);
        expectLastCall().once();
        replay(aaclFundPoolRepository);
        aaclFundPoolService.deleteFundPoolById(FUND_POOL_ID);
        verify(aaclFundPoolRepository);
    }

    @Test
    public void testInsertFundPool() {
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
        fundPoolRepository.insert(capture(fundPoolCapture));
        expectLastCall().once();
        aaclFundPoolRepository.insertDetail(capture(detailCapture1));
        expectLastCall().once();
        aaclFundPoolRepository.insertDetail(capture(detailCapture2));
        expectLastCall().once();
        replay(fundPoolRepository, aaclFundPoolRepository);
        assertEquals(2, aaclFundPoolService.insertFundPool(fundPool, details));
        verify(fundPoolRepository, aaclFundPoolRepository);
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

    private void verifyDetail(FundPoolDetail expected, FundPoolDetail actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getAggregateLicenseeClass().getId(), actual.getAggregateLicenseeClass().getId());
        assertEquals(expected.getAggregateLicenseeClass().getName(), actual.getAggregateLicenseeClass().getName());
        assertEquals(expected.getGrossAmount(), actual.getGrossAmount());
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
}
