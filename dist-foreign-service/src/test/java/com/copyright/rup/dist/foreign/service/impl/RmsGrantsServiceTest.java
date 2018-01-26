package com.copyright.rup.dist.foreign.service.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.RmsGrant;
import com.copyright.rup.dist.foreign.integration.rms.api.IRmsIntegrationService;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Verifies {@link RmsGrantsService}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 23/01/2018
 *
 * @author Pavel Liakh
 */
public class RmsGrantsServiceTest {

    private static final String INTERNAL = "INTERNAL";
    private static final List<String> CORPORATE_TRANSACTIONAL_PHOTOCOPY =
        ImmutableList.of("EXTERNAL", "CORPORATE", "NGT_PHOTOCOPY");
    private static final List<String> CORPORATE_REPERTORY_DIGITAL = ImmutableList.of(INTERNAL, "CORPORATE", "DIGITAL");
    private static final List<String> CORPORATE_REPERTORY_PRINT = ImmutableList.of(INTERNAL, "CORPORATE", "PRINT");
    private static final List<String> ACADEMIC_ANNUAL_PRINT = ImmutableList.of(INTERNAL, "ACADEMIC", "PRINT");
    private static final List<String> ACADEMIC_ANNUAL_DIGITAL = ImmutableList.of(INTERNAL, "ACADEMIC", "DIGITAL");
    private static final Long WR_WRK_INST_NO_GRANTS = 900000000L;
    private static final Long WR_WRK_INST_1 = 254030731L;
    private static final Long WR_WRK_INST_2 = 254030732L;
    private static final Long RH_ACCOUNT_1 = 1000010077L;
    private static final Long RH_ACCOUNT_2 = 1000010073L;
    private static final Long RH_ACCOUNT_3 = 1000010075L;
    private IRmsIntegrationService rmsIntegrationService;
    private RmsGrantsService rmsGrantsService;

    @Before
    public void setUp() {
        rmsIntegrationService = createMock(IRmsIntegrationService.class);
        rmsGrantsService = new RmsGrantsService();
        Whitebox.setInternalState(rmsGrantsService, "rmsIntegrationService", rmsIntegrationService);
    }

    @Test
    public void testReceiveAcademicAnnualDigitalGrant() {
        List<Long> wrWrkInsts = ImmutableList.of(WR_WRK_INST_1);
        Set<RmsGrant> rmsGrants = ImmutableSet.of(
            buildRmsGrant(WR_WRK_INST_1, RH_ACCOUNT_1, ACADEMIC_ANNUAL_DIGITAL));
        Map<Long, Long> result = getAccountNumbersByWrWrkInsts(wrWrkInsts, rmsGrants);
        assertEquals(1, result.size());
        assertEquals(RH_ACCOUNT_1, result.get(WR_WRK_INST_1));
    }

    @Test
    public void testReceiveAcademicAnnualPrintGrant() {
        List<Long> wrWrkInsts = ImmutableList.of(WR_WRK_INST_1);
        Set<RmsGrant> rmsGrants = ImmutableSet.of(
            buildRmsGrant(WR_WRK_INST_1, RH_ACCOUNT_2, ACADEMIC_ANNUAL_PRINT),
            buildRmsGrant(WR_WRK_INST_1, RH_ACCOUNT_1, ACADEMIC_ANNUAL_DIGITAL));
        Map<Long, Long> result = getAccountNumbersByWrWrkInsts(wrWrkInsts, rmsGrants);
        assertEquals(1, result.size());
        assertEquals(RH_ACCOUNT_2, result.get(WR_WRK_INST_1));
    }

    @Test
    public void testReceiveCorporateRepertoryDigitalGrant() {
        List<Long> wrWrkInsts = ImmutableList.of(WR_WRK_INST_1);
        Set<RmsGrant> rmsGrants = ImmutableSet.of(
            buildRmsGrant(WR_WRK_INST_1, RH_ACCOUNT_2, CORPORATE_REPERTORY_DIGITAL),
            buildRmsGrant(WR_WRK_INST_1, RH_ACCOUNT_1, ACADEMIC_ANNUAL_PRINT),
            buildRmsGrant(WR_WRK_INST_1, RH_ACCOUNT_1, ACADEMIC_ANNUAL_DIGITAL));
        Map<Long, Long> result = getAccountNumbersByWrWrkInsts(wrWrkInsts, rmsGrants);
        assertEquals(1, result.size());
        assertEquals(RH_ACCOUNT_2, result.get(WR_WRK_INST_1));
    }

    @Test
    public void testReceiveCorporateRepertoryPrintGrant() {
        List<Long> wrWrkInsts = ImmutableList.of(WR_WRK_INST_1);
        Set<RmsGrant> rmsGrants = ImmutableSet.of(
            buildRmsGrant(WR_WRK_INST_1, RH_ACCOUNT_1, CORPORATE_REPERTORY_PRINT),
            buildRmsGrant(WR_WRK_INST_1, RH_ACCOUNT_2, CORPORATE_REPERTORY_DIGITAL),
            buildRmsGrant(WR_WRK_INST_1, RH_ACCOUNT_2, ACADEMIC_ANNUAL_PRINT),
            buildRmsGrant(WR_WRK_INST_1, RH_ACCOUNT_2, ACADEMIC_ANNUAL_DIGITAL));
        Map<Long, Long> result = getAccountNumbersByWrWrkInsts(wrWrkInsts, rmsGrants);
        assertEquals(1, result.size());
        assertEquals(RH_ACCOUNT_1, result.get(WR_WRK_INST_1));
    }

    @Test
    public void testReceiveCorporateTransactionalPhotocopyGrant() {
        List<Long> wrWrkInsts = ImmutableList.of(WR_WRK_INST_1);
        Set<RmsGrant> rmsGrants = ImmutableSet.of(
            buildRmsGrant(WR_WRK_INST_1, RH_ACCOUNT_1, CORPORATE_TRANSACTIONAL_PHOTOCOPY),
            buildRmsGrant(WR_WRK_INST_1, RH_ACCOUNT_2, CORPORATE_REPERTORY_PRINT),
            buildRmsGrant(WR_WRK_INST_1, RH_ACCOUNT_2, CORPORATE_REPERTORY_DIGITAL),
            buildRmsGrant(WR_WRK_INST_1, RH_ACCOUNT_2, ACADEMIC_ANNUAL_PRINT),
            buildRmsGrant(WR_WRK_INST_1, RH_ACCOUNT_2, ACADEMIC_ANNUAL_DIGITAL));
        Map<Long, Long> result = getAccountNumbersByWrWrkInsts(wrWrkInsts, rmsGrants);
        assertEquals(1, result.size());
        assertEquals(RH_ACCOUNT_1, result.get(WR_WRK_INST_1));
    }

    @Test
    public void testReceiveNoGrantsForWrWrkInst() {
        List<Long> wrWrkInsts = ImmutableList.of(WR_WRK_INST_1, WR_WRK_INST_2, WR_WRK_INST_NO_GRANTS);
        Set<RmsGrant> rmsGrants = ImmutableSet.of(
            buildRmsGrant(WR_WRK_INST_1, RH_ACCOUNT_1, CORPORATE_TRANSACTIONAL_PHOTOCOPY),
            buildRmsGrant(WR_WRK_INST_1, RH_ACCOUNT_2, CORPORATE_REPERTORY_PRINT),
            buildRmsGrant(WR_WRK_INST_2, RH_ACCOUNT_2, ACADEMIC_ANNUAL_PRINT),
            buildRmsGrant(WR_WRK_INST_2, RH_ACCOUNT_3, CORPORATE_REPERTORY_DIGITAL));
        Map<Long, Long> result = getAccountNumbersByWrWrkInsts(wrWrkInsts, rmsGrants);
        assertEquals(2, result.size());
        assertEquals(RH_ACCOUNT_1, result.get(WR_WRK_INST_1));
        assertEquals(RH_ACCOUNT_3, result.get(WR_WRK_INST_2));
        assertNull(result.get(WR_WRK_INST_NO_GRANTS));
    }

    @Test
    public void testReceiveNoSuitableGrantsForWrWrkInst() {
        List<Long> wrWrkInsts = ImmutableList.of(WR_WRK_INST_NO_GRANTS);
        Set<RmsGrant> rmsGrants = ImmutableSet.of(
            buildRmsGrant(WR_WRK_INST_NO_GRANTS, RH_ACCOUNT_1, ImmutableList.of("EXTERNAL", "ACADEMIC", "PRINT")));
        Map<Long, Long> result = getAccountNumbersByWrWrkInsts(wrWrkInsts, rmsGrants);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    private Map<Long, Long> getAccountNumbersByWrWrkInsts(List<Long> wrWrkInsts, Set<RmsGrant> rmsGrants) {
        expect(rmsIntegrationService.getAllRmsGrants(eq(wrWrkInsts))).andReturn(rmsGrants);
        replay(rmsIntegrationService);
        Map<Long, Long> result = rmsGrantsService.getAccountNumbersByWrWrkInsts(wrWrkInsts);
        verify(rmsIntegrationService);
        return result;
    }

    private RmsGrant buildRmsGrant(Long wrWrkInst, Long rhAccountNumber, List<String> grantDistributionAttributes) {
        RmsGrant rmsGrant = new RmsGrant();
        rmsGrant.setId(RupPersistUtils.generateUuid());
        rmsGrant.setRightId(RupPersistUtils.generateUuid());
        rmsGrant.setWrWrkInst(wrWrkInst);
        rmsGrant.setWorkGroupOwnerOrgNumber(new BigDecimal(rhAccountNumber));
        rmsGrant.setDistribution(grantDistributionAttributes.get(0));
        rmsGrant.setMarket(grantDistributionAttributes.get(1));
        rmsGrant.setTypeOfUse(grantDistributionAttributes.get(2));
        rmsGrant.setStatus("GRANT");
        rmsGrant.setRightBeginDate(Date.valueOf(LocalDate.of(2013, 01, 05)));
        rmsGrant.setRightEndDate(Date.valueOf(LocalDate.of(3000, 12, 31)));
        rmsGrant.setAgreementBeginDate(Date.valueOf(LocalDate.of(2013, 01, 05)));
        rmsGrant.setAgreementEndDate(Date.valueOf(LocalDate.of(3000, 12, 31)));
        return rmsGrant;
    }
}
