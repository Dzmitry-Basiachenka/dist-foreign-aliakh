package com.copyright.rup.dist.foreign.service.impl.tax;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.RhTaxInformation;
import com.copyright.rup.dist.foreign.domain.RightsholderPayeeProductFamilyHolder;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.integration.oracle.api.IOracleRhTaxCountryChunkService;
import com.copyright.rup.dist.foreign.integration.oracle.api.IOracleRhTaxCountryService;
import com.copyright.rup.dist.foreign.integration.oracle.api.IOracleRhTaxInformationService;
import com.copyright.rup.dist.foreign.integration.oracle.api.domain.OracleRhTaxInformationRequest;
import com.copyright.rup.dist.foreign.integration.oracle.impl.OracleRhTaxInformationService;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Verifies {@link RhTaxService}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 12/05/18
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({RhTaxService.class, RhTaxInformationPredicate.class, OffsetDateTime.class})
public class RhTaxServiceTest {

    private static final String RH_ID_1 = "5bcf2c37-2f32-48e9-90fe-c9d75298eeed";
    private static final String RH_ID_2 = "8a0dbf78-d9c9-49d9-a895-05f55cfc8329";
    private static final String RH_ID_3 = "e9c9f51b-6048-4474-848a-2db1c410e463";
    private static final long ACC_NUMBER_1 = 1000002859L;
    private static final long ACC_NUMBER_2 = 1000005413L;
    private static final long ACC_NUMBER_3 = 1000002797L;
    private static final String RH_NAME_1 = "John Wiley & Sons - Books";
    private static final String RH_NAME_2 = "Kluwer Academic Publishers - Dordrecht";
    private static final String RH_NAME_3 = "British Film Institute (BFI)";
    private static final String FAS_PRODUCT_FAMILY = "FAS";
    private static final String FAS2_PRODUCT_FAMILY = "FAS2";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final Set<String> SCENARIO_IDS =
        Sets.newHashSet("493a6870-51ca-42c6-9727-3a333fa55746", "86b62b54-2b1a-4a97-bb68-28a30538dce8");

    static {
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
    }

    private RhTaxService rhTaxService;
    private IUsageService usageService;
    private IPrmIntegrationService prmIntegrationService;
    private IOracleRhTaxInformationService oracleRhTaxInformationService;
    private IOracleRhTaxCountryService oracleRhTaxCountryService;
    private IOracleRhTaxCountryChunkService oracleRhTaxCountryChunkService;

    @Before
    public void setUp() {
        OffsetDateTime now = OffsetDateTime.parse("2020-05-22T12:26:30-04:00");
        mockStatic(OffsetDateTime.class);
        expect(OffsetDateTime.now()).andStubReturn(now);
        replay(OffsetDateTime.class);
        rhTaxService = new RhTaxService();
        usageService = createMock(IUsageService.class);
        prmIntegrationService = createMock(IPrmIntegrationService.class);
        oracleRhTaxInformationService = createMock(OracleRhTaxInformationService.class);
        oracleRhTaxCountryService = createMock(IOracleRhTaxCountryService.class);
        oracleRhTaxCountryChunkService = createMock(IOracleRhTaxCountryChunkService.class);
        Whitebox.setInternalState(rhTaxService, usageService);
        Whitebox.setInternalState(rhTaxService, prmIntegrationService);
        Whitebox.setInternalState(rhTaxService, oracleRhTaxInformationService);
        Whitebox.setInternalState(rhTaxService, "oracleRhTaxCountryService", oracleRhTaxCountryService);
        Whitebox.setInternalState(rhTaxService, "oracleRhTaxCountryChunkService", oracleRhTaxCountryChunkService);
    }

    @Test
    public void testProcessTaxCountryCode() {
        Usage usage = buildUsage(ACC_NUMBER_1);
        expect(oracleRhTaxCountryService.isUsTaxCountry(ACC_NUMBER_1)).andReturn(true).once();
        usageService.updateProcessedUsage(usage);
        expectLastCall().once();
        replay(oracleRhTaxCountryService, usageService);
        rhTaxService.processTaxCountryCode(usage);
        assertEquals(UsageStatusEnum.US_TAX_COUNTRY, usage.getStatus());
        verify(oracleRhTaxCountryService, usageService);
    }

    @Test
    public void testProcessTaxCountryCodeNonUs() {
        Usage usage = buildUsage(ACC_NUMBER_1);
        expect(oracleRhTaxCountryService.isUsTaxCountry(ACC_NUMBER_1)).andReturn(false).once();
        replay(oracleRhTaxCountryService, usageService);
        rhTaxService.processTaxCountryCode(usage);
        assertNull(usage.getStatus());
        verify(oracleRhTaxCountryService, usageService);
    }

    @Test(expected = NullPointerException.class)
    public void testProcessTaxCountryCodeNullUsage() {
        Usage usage = new Usage();
        rhTaxService.processTaxCountryCode(usage);
    }

    @Test
    public void testProcessTaxCountryCodeByChunks() {
        Usage usTaxUsage = buildUsage(ACC_NUMBER_2);
        Usage notUsTaxUsage = buildUsage(ACC_NUMBER_3);
        List<Usage> usages = Arrays.asList(usTaxUsage, notUsTaxUsage);
        expect(oracleRhTaxCountryChunkService.isUsTaxCountry(Sets.newHashSet(ACC_NUMBER_2, ACC_NUMBER_3)))
            .andReturn(ImmutableMap.of(ACC_NUMBER_2, Boolean.TRUE, ACC_NUMBER_3, Boolean.FALSE))
            .once();
        usageService.updateProcessedUsage(usTaxUsage);
        expectLastCall().once();
        replay(oracleRhTaxCountryChunkService, usageService);
        rhTaxService.processTaxCountryCode(usages);
        assertEquals(UsageStatusEnum.US_TAX_COUNTRY, usTaxUsage.getStatus());
        assertNotEquals(UsageStatusEnum.US_TAX_COUNTRY, notUsTaxUsage.getStatus());
        verify(oracleRhTaxCountryChunkService, usageService);
    }

    @Test
    public void testGetRhTaxInformation() throws IOException {
        Comparator<RhTaxInformation> comparator = Comparator
            .comparing(RhTaxInformation::getProductFamily)
            .thenComparing(RhTaxInformation::getRorAccountNumber)
            .thenComparing(RhTaxInformation::getPayeeAccountNumber);
        Rightsholder rh1 = buildRightsholder(RH_ID_1, ACC_NUMBER_1, RH_NAME_1);
        Rightsholder rh2 = buildRightsholder(RH_ID_2, ACC_NUMBER_2, RH_NAME_2);
        Rightsholder rh3 = buildRightsholder(RH_ID_3, ACC_NUMBER_3, RH_NAME_3);
        List<RightsholderPayeeProductFamilyHolder> holders = Arrays.asList(
            buildHolder(rh2, rh2, FAS_PRODUCT_FAMILY),   // TBO = RH_2, included in the result
            buildHolder(rh1, rh2, FAS_PRODUCT_FAMILY),   // TBO = RH_1, excluded as included in the result
            buildHolder(rh1, rh2, FAS2_PRODUCT_FAMILY),  // TBO = RH_1, excluded as included in the result
            buildHolder(rh2, rh3, FAS2_PRODUCT_FAMILY)); // TBO = RH_3, excluded as number of notifications > 3
        Set<OracleRhTaxInformationRequest> expectedOracleRequests = Sets.newHashSet(
            new OracleRhTaxInformationRequest(ACC_NUMBER_2, ACC_NUMBER_1),
            new OracleRhTaxInformationRequest(ACC_NUMBER_2, ACC_NUMBER_2),
            new OracleRhTaxInformationRequest(ACC_NUMBER_3, ACC_NUMBER_3));
        expect(usageService.getRightsholderPayeeProductFamilyHoldersByScenarioIds(SCENARIO_IDS))
            .andReturn(holders).once();
        expect(prmIntegrationService.isRightsholderTaxBeneficialOwner(RH_ID_1, FAS_PRODUCT_FAMILY))
            .andReturn(true).once();
        expect(prmIntegrationService.isRightsholderTaxBeneficialOwner(RH_ID_1, FAS2_PRODUCT_FAMILY))
            .andReturn(false).once();
        expect(prmIntegrationService.isRightsholderTaxBeneficialOwner(RH_ID_2, FAS2_PRODUCT_FAMILY))
            .andReturn(false).once();
        expect(oracleRhTaxInformationService.getRhTaxInformation(expectedOracleRequests))
            .andReturn(loadOracleRhTaxInformation("oracle_rh_tax_information.json")).once();
        replay(usageService, prmIntegrationService, oracleRhTaxInformationService);
        List<RhTaxInformation> actualRhTaxInformation = rhTaxService.getRhTaxInformation(SCENARIO_IDS, 5).stream()
            .sorted(comparator)
            .collect(Collectors.toList());
        assertEquals(loadExpectedRhTaxInformation("expected_rh_tax_information_1.json"), actualRhTaxInformation);
        verify(usageService, prmIntegrationService, oracleRhTaxInformationService);
    }

    @Test
    public void testGetRhTaxInformationWithFilteredByNotificationDate() throws IOException {
        Comparator<RhTaxInformation> comparator = Comparator
            .comparing(RhTaxInformation::getProductFamily)
            .thenComparing(RhTaxInformation::getRorAccountNumber)
            .thenComparing(RhTaxInformation::getPayeeAccountNumber);
        Rightsholder rh1 = buildRightsholder(RH_ID_1, ACC_NUMBER_1, RH_NAME_1);
        Rightsholder rh2 = buildRightsholder(RH_ID_2, ACC_NUMBER_2, RH_NAME_2);
        Rightsholder rh3 = buildRightsholder(RH_ID_3, ACC_NUMBER_3, RH_NAME_3);
        List<RightsholderPayeeProductFamilyHolder> holders = Arrays.asList(
            buildHolder(rh2, rh2, FAS_PRODUCT_FAMILY),   // TBO = RH_2, included in the result
            buildHolder(rh1, rh2, FAS_PRODUCT_FAMILY),   // TBO = RH_1, excluded as last not. date within numberOfDays
            buildHolder(rh1, rh2, FAS2_PRODUCT_FAMILY),  // TBO = RH_1, excluded as last not. date within numberOfDays
            buildHolder(rh2, rh3, FAS2_PRODUCT_FAMILY)); // TBO = RH_3, excluded as number of notifications > 3
        Set<OracleRhTaxInformationRequest> expectedOracleRequests = Sets.newHashSet(
            new OracleRhTaxInformationRequest(ACC_NUMBER_2, ACC_NUMBER_1),
            new OracleRhTaxInformationRequest(ACC_NUMBER_2, ACC_NUMBER_2),
            new OracleRhTaxInformationRequest(ACC_NUMBER_3, ACC_NUMBER_3));
        expect(usageService.getRightsholderPayeeProductFamilyHoldersByScenarioIds(SCENARIO_IDS))
            .andReturn(holders).once();
        expect(prmIntegrationService.isRightsholderTaxBeneficialOwner(RH_ID_1, FAS_PRODUCT_FAMILY))
            .andReturn(true).once();
        expect(prmIntegrationService.isRightsholderTaxBeneficialOwner(RH_ID_1, FAS2_PRODUCT_FAMILY))
            .andReturn(false).once();
        expect(prmIntegrationService.isRightsholderTaxBeneficialOwner(RH_ID_2, FAS2_PRODUCT_FAMILY))
            .andReturn(false).once();
        expect(oracleRhTaxInformationService.getRhTaxInformation(expectedOracleRequests))
            .andReturn(loadOracleRhTaxInformation("oracle_rh_tax_information.json")).once();
        replay(usageService, prmIntegrationService, oracleRhTaxInformationService);
        List<RhTaxInformation> actualRhTaxInformation = rhTaxService.getRhTaxInformation(SCENARIO_IDS, 7).stream()
            .sorted(comparator)
            .collect(Collectors.toList());
        assertEquals(loadExpectedRhTaxInformation("expected_rh_tax_information_2.json"), actualRhTaxInformation);
        verify(usageService, prmIntegrationService, oracleRhTaxInformationService);
    }

    @Test
    public void testGetRhTaxInformationWithNoUsagesInScenarios() {
        expect(usageService.getRightsholderPayeeProductFamilyHoldersByScenarioIds(SCENARIO_IDS))
            .andReturn(Collections.emptyList()).once();
        replay(usageService, prmIntegrationService, oracleRhTaxInformationService);
        assertEquals(Collections.emptyList(), rhTaxService.getRhTaxInformation(SCENARIO_IDS, 1));
        verify(usageService, prmIntegrationService, oracleRhTaxInformationService);
    }

    private Usage buildUsage(Long accountNumber) {
        Usage usage = new Usage();
        usage.setId(RupPersistUtils.generateUuid());
        usage.getRightsholder().setAccountNumber(accountNumber);
        return usage;
    }

    private RightsholderPayeeProductFamilyHolder buildHolder(Rightsholder rightsholder, Rightsholder payee,
                                                             String productFamily) {
        RightsholderPayeeProductFamilyHolder holder = new RightsholderPayeeProductFamilyHolder();
        holder.setRightsholder(rightsholder);
        holder.setPayee(payee);
        holder.setProductFamily(productFamily);
        return holder;
    }

    private Rightsholder buildRightsholder(String id, Long accountNumber, String name) {
        Rightsholder rh = new Rightsholder();
        rh.setId(id);
        rh.setAccountNumber(accountNumber);
        rh.setName(name);
        return rh;
    }

    private Map<Long, RhTaxInformation> loadOracleRhTaxInformation(String fileName) throws IOException {
        return loadExpectedRhTaxInformation(fileName).stream()
            .collect(Collectors.toMap(RhTaxInformation::getTboAccountNumber, item -> item));
    }

    private List<RhTaxInformation> loadExpectedRhTaxInformation(String fileName) throws IOException {
        String content = TestUtils.fileToString(this.getClass(), fileName);
        return OBJECT_MAPPER.readValue(content, new TypeReference<List<RhTaxInformation>>() {});
    }
}
