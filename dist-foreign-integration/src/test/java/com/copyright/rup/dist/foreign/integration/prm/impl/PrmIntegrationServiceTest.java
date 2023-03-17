package com.copyright.rup.dist.foreign.integration.prm.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.Country;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.integration.rest.prm.IPrmCountryService;
import com.copyright.rup.dist.common.integration.rest.prm.IPrmPreferenceService;
import com.copyright.rup.dist.common.integration.rest.prm.IPrmRightsholderService;
import com.copyright.rup.dist.common.integration.rest.prm.IPrmRollUpService;
import com.copyright.rup.dist.foreign.domain.AclIneligibleRightsholder;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIneligibleRightsholderService;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Table;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Verifies {@link PrmIntegrationService}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/21/2017
 *
 * @author Mikalai Bezmen
 */
public class PrmIntegrationServiceTest {

    private static final long ACCOUNT_NUMBER = 1000001863L;
    private static final String RIGHTSHOLDER_ID = "66a7c2c0-3b09-48ad-9aa5-a6d0822226c7";
    private static final String RIGHTSHOLDER_NAME = "CANADIAN CERAMIC SOCIETY";
    private static final String FAS_PRODUCT_FAMILY = "FAS";
    private static final String NTS_PRODUCT_FAMILY = "NTS";
    private static final String ALL_PRODUCTS = "*";
    private IPrmRightsholderService prmRightsholderService;
    private PrmIntegrationService prmIntegrationService;
    private IPrmRollUpService prmRollUpService;
    private IPrmRollUpService prmRollUpAsyncService;
    private IPrmPreferenceService prmPreferenceService;
    private IPrmCountryService prmCountryService;
    private IPrmIneligibleRightsholderService ineligibleRightsholderService;

    @Before
    public void setUp() {
        prmIntegrationService = new PrmIntegrationService();
        prmRightsholderService = createMock(IPrmRightsholderService.class);
        prmRollUpService = createMock(IPrmRollUpService.class);
        prmRollUpAsyncService = createMock(IPrmRollUpService.class);
        prmPreferenceService = createMock(IPrmPreferenceService.class);
        prmCountryService = createMock(IPrmCountryService.class);
        ineligibleRightsholderService = createMock(IPrmIneligibleRightsholderService.class);
        Whitebox.setInternalState(prmIntegrationService, "prmRightsholderService", prmRightsholderService);
        Whitebox.setInternalState(prmIntegrationService, "prmRollUpService", prmRollUpService);
        Whitebox.setInternalState(prmIntegrationService, "prmRollUpAsyncService", prmRollUpAsyncService);
        Whitebox.setInternalState(prmIntegrationService, "prmPreferenceService", prmPreferenceService);
        Whitebox.setInternalState(prmIntegrationService, "prmCountryService", prmCountryService);
        Whitebox.setInternalState(prmIntegrationService, "ineligibleRightsholderService",
            ineligibleRightsholderService);
    }

    @Test
    public void testGetRightsholders() {
        Set<Long> accountNumbers = Set.of(ACCOUNT_NUMBER);
        Rightsholder rightsholder = buildRightsholder();
        expect(prmRightsholderService.getRightsholders(accountNumbers))
            .andReturn(List.of(rightsholder)).once();
        replay(prmRightsholderService);
        List<Rightsholder> actualResult = prmIntegrationService.getRightsholders(accountNumbers);
        assertFalse(actualResult.isEmpty());
        assertEquals(1, actualResult.size());
        assertTrue(actualResult.contains(rightsholder));
        verify(prmRightsholderService);
    }

    @Test
    public void testGetRightsholdersForEmptySet() {
        expect(prmRightsholderService.getRightsholders(new HashSet<>())).andReturn(new ArrayList<>()).once();
        replay(prmRightsholderService);
        assertTrue(prmIntegrationService.getRightsholders(Set.of()).isEmpty());
        verify(prmRightsholderService);
    }

    @Test
    public void testGetRightsholderName() {
        expect(prmRightsholderService.getRightsholders(Set.of(ACCOUNT_NUMBER)))
            .andReturn(List.of(buildRightsholder())).once();
        replay(prmRightsholderService);
        Rightsholder rightsholder = prmIntegrationService.getRightsholder(ACCOUNT_NUMBER);
        assertEquals(ACCOUNT_NUMBER, rightsholder.getAccountNumber(), 0);
        assertEquals(RIGHTSHOLDER_NAME, rightsholder.getName());
        verify(prmRightsholderService);
    }

    @Test
    public void testGetRightsholderNameNotPresentedInPrm() {
        expect(prmRightsholderService.getRightsholders(Set.of(ACCOUNT_NUMBER))).andReturn(List.of()).once();
        replay(prmRightsholderService);
        assertNull(prmIntegrationService.getRightsholder(ACCOUNT_NUMBER));
        verify(prmRightsholderService);
    }

    @Test
    public void testGetRollUps() {
        String rightsholderId = RupPersistUtils.generateUuid();
        Set<String> rightsholdersIds = Set.of(rightsholderId);
        Map<String, Map<String, Rightsholder>> result = new HashMap<>();
        result.put(rightsholderId, ImmutableMap.of(FAS_PRODUCT_FAMILY, buildRightsholder()));
        expect(prmRollUpService.getRollUps(rightsholdersIds)).andReturn(result).once();
        replay(prmRollUpService);
        assertEquals(result, prmIntegrationService.getRollUps(rightsholdersIds));
        verify(prmRollUpService);
    }

    @Test
    public void testGetRollUpsWithAsyncImpl() {
        Whitebox.setInternalState(prmIntegrationService, "prmRollUpAsync", true);
        String rightsholderId = RupPersistUtils.generateUuid();
        Set<String> rightsholdersIds = Set.of(rightsholderId);
        Map<String, Map<String, Rightsholder>> result = new HashMap<>();
        result.put(rightsholderId, ImmutableMap.of(FAS_PRODUCT_FAMILY, buildRightsholder()));
        expect(prmRollUpAsyncService.getRollUps(rightsholdersIds)).andReturn(result).once();
        replay(prmRollUpAsyncService);
        assertEquals(result, prmIntegrationService.getRollUps(rightsholdersIds));
        verify(prmRollUpAsyncService);
    }

    @Test
    public void testGetCountries() {
        Country country = new Country();
        country.setIsoCode("BLR");
        country.setName("Belarus");
        Map<String, Country> countries = Map.of("BY", country);
        expect(prmCountryService.getCountries()).andReturn(countries);
        replay(prmCountryService);
        assertEquals(countries, prmIntegrationService.getCountries());
        verify(prmCountryService);
    }

    @Test
    public void testIsRightsholderEligibleForNtsDistributionTrue() {
        Table<String, String, Object> preferencesTable = HashBasedTable.create();
        preferencesTable.put("NTS", FdaConstants.IS_RH_DIST_INELIGIBLE_CODE, true);
        expect(prmPreferenceService.getPreferencesMap(Set.of(RIGHTSHOLDER_ID)))
            .andReturn(ImmutableMap.of(RIGHTSHOLDER_ID, preferencesTable)).once();
        replay(prmPreferenceService);
        assertFalse(prmIntegrationService.isRightsholderEligibleForNtsDistribution(RIGHTSHOLDER_ID));
        verify(prmPreferenceService);
    }

    @Test
    public void testIsRightsholderEligibleForNtsDistributionFalse() {
        Table<String, String, Object> preferencesTable = HashBasedTable.create();
        preferencesTable.put("NTS", FdaConstants.IS_RH_DIST_INELIGIBLE_CODE, false);
        expect(prmPreferenceService.getPreferencesMap(Set.of(RIGHTSHOLDER_ID)))
            .andReturn(ImmutableMap.of(RIGHTSHOLDER_ID, preferencesTable)).once();
        replay(prmPreferenceService);
        assertTrue(prmIntegrationService.isRightsholderEligibleForNtsDistribution(RIGHTSHOLDER_ID));
        verify(prmPreferenceService);
    }

    @Test
    public void testIsRightsholderEligibleForNtsDistributionAllProductsTrue() {
        Table<String, String, Object> preferencesTable = HashBasedTable.create();
        preferencesTable.put("*", FdaConstants.IS_RH_DIST_INELIGIBLE_CODE, true);
        expect(prmPreferenceService.getPreferencesMap(Set.of(RIGHTSHOLDER_ID)))
            .andReturn(ImmutableMap.of(RIGHTSHOLDER_ID, preferencesTable)).once();
        replay(prmPreferenceService);
        assertFalse(prmIntegrationService.isRightsholderEligibleForNtsDistribution(RIGHTSHOLDER_ID));
        verify(prmPreferenceService);
    }

    @Test
    public void testIsRightsholderEligibleForNtsDistributionAllProductsFalse() {
        Table<String, String, Object> preferencesTable = HashBasedTable.create();
        preferencesTable.put("*", FdaConstants.IS_RH_DIST_INELIGIBLE_CODE, false);
        expect(prmPreferenceService.getPreferencesMap(Set.of(RIGHTSHOLDER_ID)))
            .andReturn(ImmutableMap.of(RIGHTSHOLDER_ID, preferencesTable)).once();
        replay(prmPreferenceService);
        assertTrue(prmIntegrationService.isRightsholderEligibleForNtsDistribution(RIGHTSHOLDER_ID));
        verify(prmPreferenceService);
    }

    @Test
    public void testIsRightsholderParticipatingByProductFamilyTrue() {
        assertRightsholderParticipation(FAS_PRODUCT_FAMILY, true);
    }

    @Test
    public void testIsRightsholderParticipatingByProductFamilyFalse() {
        assertRightsholderParticipation(FAS_PRODUCT_FAMILY, false);
    }

    @Test
    public void testIsRightsholderParticipatingByAllProductsTrue() {
        assertRightsholderParticipation("*", true);
    }

    @Test
    public void testIsRightsholderParticipatingByAllProductsFalse() {
        assertRightsholderParticipation("*", false);
    }

    @Test
    public void testIsStmRightsholderTrue() {
        Table<String, String, Object> preferencesTable = HashBasedTable.create();
        preferencesTable.put(NTS_PRODUCT_FAMILY, FdaConstants.IS_RH_STM_IPRO_CODE, true);
        expect(prmPreferenceService.getPreferencesMap(Set.of(RIGHTSHOLDER_ID)))
            .andReturn(ImmutableMap.of(RIGHTSHOLDER_ID, preferencesTable)).once();
        replay(prmPreferenceService);
        assertTrue(prmIntegrationService.isStmRightsholder(RIGHTSHOLDER_ID, NTS_PRODUCT_FAMILY));
        verify(prmPreferenceService);
    }

    @Test
    public void testIsStmRightsholderFalse() {
        Table<String, String, Object> preferencesTable = HashBasedTable.create();
        preferencesTable.put(NTS_PRODUCT_FAMILY, FdaConstants.IS_RH_STM_IPRO_CODE, false);
        expect(prmPreferenceService.getPreferencesMap(Set.of(RIGHTSHOLDER_ID)))
            .andReturn(ImmutableMap.of(RIGHTSHOLDER_ID, preferencesTable)).once();
        replay(prmPreferenceService);
        assertFalse(prmIntegrationService.isStmRightsholder(RIGHTSHOLDER_ID, NTS_PRODUCT_FAMILY));
        verify(prmPreferenceService);
    }

    @Test
    public void testIsStmRightsholderAllProductsTrue() {
        Table<String, String, Object> preferencesTable = HashBasedTable.create();
        preferencesTable.put(ALL_PRODUCTS, FdaConstants.IS_RH_STM_IPRO_CODE, true);
        expect(prmPreferenceService.getPreferencesMap(Set.of(RIGHTSHOLDER_ID)))
            .andReturn(ImmutableMap.of(RIGHTSHOLDER_ID, preferencesTable)).once();
        replay(prmPreferenceService);
        assertTrue(prmIntegrationService.isStmRightsholder(RIGHTSHOLDER_ID, NTS_PRODUCT_FAMILY));
        verify(prmPreferenceService);
    }

    @Test
    public void testIsStmRightsholderAllProductsFalse() {
        Table<String, String, Object> preferencesTable = HashBasedTable.create();
        preferencesTable.put(ALL_PRODUCTS, FdaConstants.IS_RH_STM_IPRO_CODE, false);
        expect(prmPreferenceService.getPreferencesMap(Set.of(RIGHTSHOLDER_ID)))
            .andReturn(ImmutableMap.of(RIGHTSHOLDER_ID, preferencesTable)).once();
        replay(prmPreferenceService);
        assertFalse(prmIntegrationService.isStmRightsholder(RIGHTSHOLDER_ID, NTS_PRODUCT_FAMILY));
        verify(prmPreferenceService);
    }

    @Test
    public void testIsStmRightsholderDefaultFalse() {
        Table<String, String, Object> preferencesTable = HashBasedTable.create();
        preferencesTable.put(FAS_PRODUCT_FAMILY, FdaConstants.IS_RH_STM_IPRO_CODE, true);
        expect(prmPreferenceService.getPreferencesMap(Set.of(RIGHTSHOLDER_ID)))
            .andReturn(ImmutableMap.of(RIGHTSHOLDER_ID, preferencesTable)).once();
        replay(prmPreferenceService);
        assertFalse(prmIntegrationService.isStmRightsholder(RIGHTSHOLDER_ID, NTS_PRODUCT_FAMILY));
        verify(prmPreferenceService);
    }

    @Test
    public void testIsRightsholderTaxBeneficialOwnerTrue() {
        Table<String, String, Object> preferencesTable = HashBasedTable.create();
        preferencesTable.put(FAS_PRODUCT_FAMILY, FdaConstants.TAX_BENEFICIAL_OWNER_CODE, true);
        expect(prmPreferenceService.getPreferencesMap(Set.of(RIGHTSHOLDER_ID)))
            .andReturn(ImmutableMap.of(RIGHTSHOLDER_ID, preferencesTable)).once();
        replay(prmPreferenceService);
        assertTrue(prmIntegrationService.isRightsholderTaxBeneficialOwner(RIGHTSHOLDER_ID, FAS_PRODUCT_FAMILY));
        verify(prmPreferenceService);
    }

    @Test
    public void testIsRightsholderTaxBeneficialOwnerFalse() {
        Table<String, String, Object> preferencesTable = HashBasedTable.create();
        preferencesTable.put(FAS_PRODUCT_FAMILY, FdaConstants.TAX_BENEFICIAL_OWNER_CODE, false);
        expect(prmPreferenceService.getPreferencesMap(Set.of(RIGHTSHOLDER_ID)))
            .andReturn(ImmutableMap.of(RIGHTSHOLDER_ID, preferencesTable)).once();
        replay(prmPreferenceService);
        assertFalse(prmIntegrationService.isRightsholderTaxBeneficialOwner(RIGHTSHOLDER_ID, FAS_PRODUCT_FAMILY));
        verify(prmPreferenceService);
    }

    @Test
    public void testIsRightsholderTaxBeneficialOwnerAllProductsTrue() {
        Table<String, String, Object> preferencesTable = HashBasedTable.create();
        preferencesTable.put(ALL_PRODUCTS, FdaConstants.TAX_BENEFICIAL_OWNER_CODE, true);
        expect(prmPreferenceService.getPreferencesMap(Set.of(RIGHTSHOLDER_ID)))
            .andReturn(ImmutableMap.of(RIGHTSHOLDER_ID, preferencesTable)).once();
        replay(prmPreferenceService);
        assertTrue(prmIntegrationService.isRightsholderTaxBeneficialOwner(RIGHTSHOLDER_ID, FAS_PRODUCT_FAMILY));
        verify(prmPreferenceService);
    }

    @Test
    public void testIsRightsholderTaxBeneficialOwnerAllProductsFalse() {
        Table<String, String, Object> preferencesTable = HashBasedTable.create();
        preferencesTable.put(ALL_PRODUCTS, FdaConstants.TAX_BENEFICIAL_OWNER_CODE, false);
        expect(prmPreferenceService.getPreferencesMap(Set.of(RIGHTSHOLDER_ID)))
            .andReturn(ImmutableMap.of(RIGHTSHOLDER_ID, preferencesTable)).once();
        replay(prmPreferenceService);
        assertFalse(prmIntegrationService.isRightsholderTaxBeneficialOwner(RIGHTSHOLDER_ID, FAS_PRODUCT_FAMILY));
        verify(prmPreferenceService);
    }

    @Test
    public void testIsRightsholderTaxBeneficialOwnerDifferentForAllAndProductSpecific() {
        Table<String, String, Object> preferencesTable = HashBasedTable.create();
        preferencesTable.put(ALL_PRODUCTS, FdaConstants.TAX_BENEFICIAL_OWNER_CODE, true);
        preferencesTable.put(FAS_PRODUCT_FAMILY, FdaConstants.TAX_BENEFICIAL_OWNER_CODE, false);
        expect(prmPreferenceService.getPreferencesMap(Set.of(RIGHTSHOLDER_ID)))
            .andReturn(ImmutableMap.of(RIGHTSHOLDER_ID, preferencesTable)).once();
        replay(prmPreferenceService);
        assertFalse(prmIntegrationService.isRightsholderTaxBeneficialOwner(RIGHTSHOLDER_ID, FAS_PRODUCT_FAMILY));
        verify(prmPreferenceService);
    }

    @Test
    public void testIsRightsholderTaxBeneficialOwnerDefaultFalse() {
        Table<String, String, Object> preferencesTable = HashBasedTable.create();
        preferencesTable.put(NTS_PRODUCT_FAMILY, FdaConstants.TAX_BENEFICIAL_OWNER_CODE, true);
        expect(prmPreferenceService.getPreferencesMap(Set.of(RIGHTSHOLDER_ID)))
            .andReturn(ImmutableMap.of(RIGHTSHOLDER_ID, preferencesTable)).once();
        replay(prmPreferenceService);
        assertFalse(prmIntegrationService.isRightsholderTaxBeneficialOwner(RIGHTSHOLDER_ID, FAS_PRODUCT_FAMILY));
        verify(prmPreferenceService);
    }

    @Test
    public void testGetIneligibleRightsholder() {
        AclIneligibleRightsholder expectedRightsholder = buildIneligibleRightsholder();
        Set<AclIneligibleRightsholder> ineligibleRightsholders = Set.of(expectedRightsholder);
        expect(ineligibleRightsholderService.getIneligibleRightsholders(LocalDate.now(), "ACL")).andReturn(
            ineligibleRightsholders);
        replay(ineligibleRightsholderService);
        Set<AclIneligibleRightsholder> actualRightsholders =
            prmIntegrationService.getIneligibleRightsholders(LocalDate.now(), "ACL");
        assertEquals(1, actualRightsholders.size());
        AclIneligibleRightsholder actualRightsholder = actualRightsholders.stream().findFirst().get();
        assertEquals(expectedRightsholder.getOrganizationId(), actualRightsholder.getOrganizationId());
        assertEquals(expectedRightsholder.getLicenseType(), actualRightsholder.getLicenseType());
        assertEquals(expectedRightsholder.getTypeOfUse(), actualRightsholder.getTypeOfUse());
        assertEquals(expectedRightsholder.getRhAccountNumber(), actualRightsholder.getRhAccountNumber());
        verify(ineligibleRightsholderService);
    }

    private void assertRightsholderParticipation(String productFamily, boolean preferenceValue) {
        Table<String, String, Object> preferencesTable = HashBasedTable.create();
        preferencesTable.put(productFamily, FdaConstants.IS_RH_FDA_PARTICIPATING_PREFERENCE_CODE, preferenceValue);
        Map<String, Table<String, String, Object>> preferences = ImmutableMap.of(RIGHTSHOLDER_ID, preferencesTable);
        replay(prmPreferenceService);
        assertEquals(preferenceValue,
            prmIntegrationService.isRightsholderParticipating(preferences, RIGHTSHOLDER_ID, FAS_PRODUCT_FAMILY));
        verify(prmPreferenceService);
    }

    private Rightsholder buildRightsholder() {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setId(RupPersistUtils.generateUuid());
        rightsholder.setAccountNumber(ACCOUNT_NUMBER);
        rightsholder.setName(RIGHTSHOLDER_NAME);
        return rightsholder;
    }

    private AclIneligibleRightsholder buildIneligibleRightsholder() {
        AclIneligibleRightsholder rightsholder = new AclIneligibleRightsholder();
        rightsholder.setOrganizationId("04b5a077-7f92-482e-a383-7e5f3d48626d");
        rightsholder.setLicenseType("ACL");
        rightsholder.setTypeOfUse("DIGITAL");
        rightsholder.setRhAccountNumber(1000000001L);
        return rightsholder;
    }
}
