package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.AclRightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.repository.api.IAclScenarioUsageRepository;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;

/**
 * Verifies {@link AclUsageRepository}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 07/22/2022
 *
 * @author Mikita Maistrenka
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml"})
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class AclScenarioUsageRepositoryIntegrationTest {

    private static final String FOLDER_NAME = "acl-usage-repository-integration-test/";
    private static final String FIND_ACL_RH_TOTALS_HOLDERS_BY_SCENARIO_ID =
        FOLDER_NAME + "find-acl-rh-totals-holders-by-scenario-id.groovy";
    private static final String ACL_SCENARIO_UID = "0d0041a3-833e-463e-8ad4-f28461dc961d";
    private static final String RH_NAME = "John Wiley & Sons - Books";

    @Autowired
    private IAclScenarioUsageRepository aclScenarioUsageRepository;

    @Test
    @TestData(fileName = FIND_ACL_RH_TOTALS_HOLDERS_BY_SCENARIO_ID)
    public void testFindAclRightsholderTotalsHoldersByScenarioIdEmptySearchValue() {
        List<AclRightsholderTotalsHolder> holders =
            aclScenarioUsageRepository.findAclRightsholderTotalsHoldersByScenarioId(
                ACL_SCENARIO_UID, StringUtils.EMPTY, null, null);
        assertEquals(2, holders.size());
        verifyAclRightsholderTotalsHolder(buildAclRightsholderTotalsHolder(1000002859L, RH_NAME, 100.00,
            16.00, 84.00, 220.00, 35.00, 178.00, 2, 2), holders.get(0));
        verifyAclRightsholderTotalsHolder(buildAclRightsholderTotalsHolder(1000000026L, null, 20.00, 3.00, 10.00,
            0.00, 0.00, 0.00, 1, 1), holders.get(1));
    }

    @Test
    @TestData(fileName = FIND_ACL_RH_TOTALS_HOLDERS_BY_SCENARIO_ID)
    public void testFindAclRightsholderTotalsHoldersByScenarioIdNotEmptySearchValue() {
        List<AclRightsholderTotalsHolder> holders =
            aclScenarioUsageRepository.findAclRightsholderTotalsHoldersByScenarioId(
                ACL_SCENARIO_UID, "JohN", null, null);
        assertEquals(1, holders.size());
        verifyAclRightsholderTotalsHolder(buildAclRightsholderTotalsHolder(1000002859L, RH_NAME, 100.00,
            16.00, 84.00, 220.00, 35.00, 178.00, 2, 2), holders.get(0));
    }

    @Test
    @TestData(fileName = FIND_ACL_RH_TOTALS_HOLDERS_BY_SCENARIO_ID)
    public void testSortingFindAclRightsholderTotalsHoldersByScenarioId() {
        AclRightsholderTotalsHolder holder1 = buildAclRightsholderTotalsHolder(1000002859L, RH_NAME, 100.00,
            16.00, 84.00, 220.00, 35.00, 178.00, 2, 2);
        AclRightsholderTotalsHolder holder2 = buildAclRightsholderTotalsHolder(1000000026L, null, 20.00, 3.00, 10.00,
            0.00, 0.00, 0.00, 1, 1);
        assertSortingAclRightsholderTotalsHolder(holder2, holder1, "rightsholder.accountNumber");
        assertSortingAclRightsholderTotalsHolder(holder1, holder2, "rightsholder.name");
        assertSortingAclRightsholderTotalsHolder(holder2, holder1, "grossTotalPrint");
        assertSortingAclRightsholderTotalsHolder(holder2, holder1, "serviceFeeTotalPrint");
        assertSortingAclRightsholderTotalsHolder(holder2, holder1, "netTotalPrint");
        assertSortingAclRightsholderTotalsHolder(holder2, holder1, "grossTotalDigital");
        assertSortingAclRightsholderTotalsHolder(holder2, holder1, "serviceFeeTotalDigital");
        assertSortingAclRightsholderTotalsHolder(holder2, holder1, "netTotalDigital");
        assertSortingAclRightsholderTotalsHolder(holder2, holder1, "numberOfTitles");
        assertSortingAclRightsholderTotalsHolder(holder2, holder1, "numberOfAggLcClasses");
        assertSortingAclRightsholderTotalsHolder(holder1, holder1, "licenseType");
    }

    @Test
    @TestData(fileName = FIND_ACL_RH_TOTALS_HOLDERS_BY_SCENARIO_ID)
    public void testFindAclRightsholderTotalsHolderCountByScenarioIdEmptySearchValue() {
        assertEquals(2, aclScenarioUsageRepository.findAclRightsholderTotalsHolderCountByScenarioId(ACL_SCENARIO_UID,
            StringUtils.EMPTY));
    }

    @Test
    @TestData(fileName = FIND_ACL_RH_TOTALS_HOLDERS_BY_SCENARIO_ID)
    public void testFindAclRightsholderTotalsHolderCountByScenarioIdNullSearchValue() {
        assertEquals(2,
            aclScenarioUsageRepository.findAclRightsholderTotalsHolderCountByScenarioId(ACL_SCENARIO_UID, null));
    }

    private AclRightsholderTotalsHolder buildAclRightsholderTotalsHolder(Long rhAccountNumber, String rhName,
                                                                         Double grossTotalPrint,
                                                                         Double serviceFeeTotalPrint,
                                                                         Double netTotalPrint, Double grossTotalDigital,
                                                                         Double serviceFeeTotalDigital,
                                                                         Double netTotalDigital,
                                                                         int numberOfTitles, int numberOfAggLcClasses) {
        AclRightsholderTotalsHolder holder = new AclRightsholderTotalsHolder();
        holder.getRightsholder().setAccountNumber(rhAccountNumber);
        holder.getRightsholder().setName(rhName);
        holder.setGrossTotalPrint(BigDecimal.valueOf(grossTotalPrint).setScale(10, BigDecimal.ROUND_HALF_UP));
        holder.setServiceFeeTotalPrint(
            BigDecimal.valueOf(serviceFeeTotalPrint).setScale(10, BigDecimal.ROUND_HALF_UP));
        holder.setNetTotalPrint(BigDecimal.valueOf(netTotalPrint).setScale(10, BigDecimal.ROUND_HALF_UP));
        holder.setGrossTotalDigital(
            BigDecimal.valueOf(grossTotalDigital).setScale(10, BigDecimal.ROUND_HALF_UP));
        holder.setServiceFeeTotalDigital(
            BigDecimal.valueOf(serviceFeeTotalDigital).setScale(10, BigDecimal.ROUND_HALF_UP));
        holder.setNetTotalDigital(BigDecimal.valueOf(netTotalDigital).setScale(10, BigDecimal.ROUND_HALF_UP));
        holder.setNumberOfTitles(numberOfTitles);
        holder.setNumberOfAggLcClasses(numberOfAggLcClasses);
        holder.setLicenseType("ACL");
        return holder;
    }

    private void verifyAclRightsholderTotalsHolder(AclRightsholderTotalsHolder expectedHolder,
                                                   AclRightsholderTotalsHolder actualHolder) {
        assertEquals(
            expectedHolder.getRightsholder().getAccountNumber(), actualHolder.getRightsholder().getAccountNumber());
        assertEquals(expectedHolder.getRightsholder().getName(), actualHolder.getRightsholder().getName());
        assertEquals(expectedHolder.getGrossTotalPrint(),
            actualHolder.getGrossTotalPrint().setScale(10, BigDecimal.ROUND_HALF_UP));
        assertEquals(expectedHolder.getServiceFeeTotalPrint(),
            actualHolder.getServiceFeeTotalPrint().setScale(10, BigDecimal.ROUND_HALF_UP));
        assertEquals(expectedHolder.getNetTotalPrint(),
            actualHolder.getNetTotalPrint().setScale(10, BigDecimal.ROUND_HALF_UP));
        assertEquals(expectedHolder.getGrossTotalDigital(),
            actualHolder.getGrossTotalDigital().setScale(10, BigDecimal.ROUND_HALF_UP));
        assertEquals(expectedHolder.getServiceFeeTotalDigital(),
            actualHolder.getServiceFeeTotalDigital().setScale(10, BigDecimal.ROUND_HALF_UP));
        assertEquals(expectedHolder.getNetTotalDigital(),
            actualHolder.getNetTotalDigital().setScale(10, BigDecimal.ROUND_HALF_UP));
        assertEquals(expectedHolder.getNumberOfTitles(), actualHolder.getNumberOfTitles());
        assertEquals(expectedHolder.getNumberOfAggLcClasses(), actualHolder.getNumberOfAggLcClasses());
        assertEquals(expectedHolder.getLicenseType(), actualHolder.getLicenseType());
    }

    private void assertSortingAclRightsholderTotalsHolder(AclRightsholderTotalsHolder holderAsc,
                                                          AclRightsholderTotalsHolder holderDesc, String sortProperty) {
        List<AclRightsholderTotalsHolder> holders =
            aclScenarioUsageRepository.findAclRightsholderTotalsHoldersByScenarioId(
            ACL_SCENARIO_UID, StringUtils.EMPTY, null, new Sort(sortProperty, Sort.Direction.ASC));
        verifyAclRightsholderTotalsHolder(holderAsc, holders.get(0));
        holders = aclScenarioUsageRepository.findAclRightsholderTotalsHoldersByScenarioId(
            ACL_SCENARIO_UID, StringUtils.EMPTY, null, new Sort(sortProperty, Sort.Direction.DESC));
        verifyAclRightsholderTotalsHolder(holderDesc, holders.get(0));
    }
}
