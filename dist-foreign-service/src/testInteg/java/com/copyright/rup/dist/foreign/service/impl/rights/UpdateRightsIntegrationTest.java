package com.copyright.rup.dist.foreign.service.impl.rights;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.common.caching.api.ICacheService;
import com.copyright.rup.dist.common.domain.job.JobInfo;
import com.copyright.rup.dist.common.domain.job.JobStatusEnum;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.AaclUsage;
import com.copyright.rup.dist.foreign.domain.AclciLicenseTypeEnum;
import com.copyright.rup.dist.foreign.domain.AclciUsage;
import com.copyright.rup.dist.foreign.domain.SalUsage;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.domain.UdmValue;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IAaclUsageRepository;
import com.copyright.rup.dist.foreign.repository.api.IAclciUsageRepository;
import com.copyright.rup.dist.foreign.repository.api.ISalUsageRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IRightsService;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageService;
import com.copyright.rup.dist.foreign.service.impl.ServiceTestHelper;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * Verifies correctness of updating usages rights.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/26/18
 *
 * @author Pavel Liakh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class UpdateRightsIntegrationTest {

    private static final String FOLDER_NAME = "update-rights-integration-test/";
    private static final String UDM_RIGHTS_FILE = "test-update-udm-rights.groovy";
    private static final Long RH_ACCOUNT_NUMBER_1 = 1000023401L;
    private static final Long RH_ACCOUNT_NUMBER_2 = 1000000322L;
    private static final String FAS_PRODUCT_FAMILY = "FAS";
    private static final String FAS2_PRODUCT_FAMILY = "FAS2";
    private static final String NTS_PRODUCT_FAMILY = "NTS";
    private static final String AACL_PRODUCT_FAMILY = "AACL";
    private static final String PRINT_TYPE_OF_USE = "PRINT";
    private static final String DIGITAL_TYPE_OF_USE = "DIGITAL";
    private static final String UDM_USAGE_ID_1 = "acb53a42-7e8d-4a4a-8d72-6f794be2731c";
    private static final String UDM_USAGE_ID_2 = "1b348196-2193-46d7-b9df-2ba835189131";
    private static final String UDM_USAGE_ID_3 = "074749c5-08fa-4f57-8c3b-ecbc334a5c2a";
    private static final String RH_FOUND_REASON = "Rightsholder account 1000000322 was found in RMS";
    private static final String RMS_GRANTS_EMPTY_RESPONSE_JSON = "rights/rms_grants_empty_response.json";
    private static final String PRM_RIGHTSHOLDER_1000000322_RESPONSE_JSON = "prm/rightsholder_1000000322_response.json";
    private static final String RIGHTS_GRANTS_REQUEST_1 = "rights/udm/usage/rms_grants_request_1.json";
    private static final String RIGHTS_GRANTS_210001899_REQUEST = "rights/udm/usage/rms_grants_210001899_request.json";

    @Autowired
    private IUsageRepository usageRepository;
    @Autowired
    private IAaclUsageRepository aaclUsageRepository;
    @Autowired
    private ISalUsageRepository salUsageRepository;
    @Autowired
    private IAclciUsageRepository aclciUsageRepository;
    @Autowired
    private IRightsService rightsService;
    @Autowired
    private IUsageAuditService usageAuditService;
    @Autowired
    private IUdmUsageService udmUsageService;
    @Autowired
    private ServiceTestHelper testHelper;
    @Autowired
    private List<ICacheService<?, ?>> cacheServices;

    @Before
    public void setUp() {
        testHelper.reset();
        cacheServices.forEach(ICacheService::invalidateCache);
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "test-update-rights-sent-for-ra-usages.groovy")
    public void testUpdateRightsSentForRaUsages() {
        testHelper.createRestServer();
        testHelper.expectGetRmsRights("rights/rms_grants_sent_for_ra_request.json",
            "rights/rms_grants_sent_for_ra_response.json");
        testHelper.expectPrmCall(PRM_RIGHTSHOLDER_1000000322_RESPONSE_JSON, 1000000322L);
        JobInfo jobInfo = rightsService.updateRightsSentForRaUsages();
        assertEquals(JobStatusEnum.FINISHED, jobInfo.getStatus());
        assertEquals("ProductFamily=FAS, UsagesCount=2; ProductFamily=FAS2, Reason=There are no usages;",
            jobInfo.getResult());
        assertUsage("2de40e13-d353-44ce-b6bb-a11383ba9fb9", UsageStatusEnum.NEW, null);
        assertUsage("e6378e17-b0c9-420f-aa5c-a653156339d2", UsageStatusEnum.SENT_FOR_RA, null);
        assertUsage("11853c83-780a-4533-ad01-dde87c8b8592", UsageStatusEnum.ELIGIBLE, 1000000322L);
        assertUsage("37c4d727-caeb-4a7f-b11a-34e313b0bfcc", UsageStatusEnum.ELIGIBLE, 1000009522L);
        assertUsage("ff321d96-04bd-11e8-ba89-0ed5f89f718b", UsageStatusEnum.LOCKED, 1000009522L);
        assertUsage("19ca7776-48c8-472e-acfe-d49b6e8780ce", UsageStatusEnum.RH_NOT_FOUND, null);
        assertAudit("11853c83-780a-4533-ad01-dde87c8b8592", "Usage has become eligible", RH_FOUND_REASON);
        testHelper.verifyRestServer();
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "test-update-rights.groovy")
    public void testUpdateRights() {
        testHelper.createRestServer();
        testHelper.expectGetRmsRights("rights/fas/rms_grants_request_1.json",
            "rights/fas/rms_grants_response_1.json");
        testHelper.expectGetRmsRights("rights/fas/rms_grants_488824345_request.json",
            RMS_GRANTS_EMPTY_RESPONSE_JSON);
        testHelper.expectGetRmsRights("rights/nts/rms_grants_786768461_request.json",
            "rights/nts/rms_grants_786768461_response.json");
        rightsService.updateRights(List.of(
            buildUsage("b77e72d6-ef71-4f4b-a00b-5800e43e5bee", FAS_PRODUCT_FAMILY, 254030731L),
            buildUsage("8aded52d-9507-4883-ab4c-fd2e029298af", FAS_PRODUCT_FAMILY, 254030731L),
            buildUsage("74ded52a-4454-1225-ab4c-fA2e029298af", FAS_PRODUCT_FAMILY, 658824345L)), true);
        rightsService.updateRights(List.of(
            buildUsage("3a6b6f25-9f68-4da7-be4f-dd65574f5168", FAS_PRODUCT_FAMILY, 488824345L)), true);
        rightsService.updateRights(
            List.of(buildUsage("ede81bc0-a756-43a2-b236-05a0184384f4", "NTS", 786768461L)), false);
        assertUsage("ede81bc0-a756-43a2-b236-05a0184384f4", UsageStatusEnum.RH_FOUND, 1000023401L);
        assertUsage("b77e72d6-ef71-4f4b-a00b-5800e43e5bee", UsageStatusEnum.RH_FOUND, 1000010077L);
        assertUsage("8aded52d-9507-4883-ab4c-fd2e029298af", UsageStatusEnum.RH_FOUND, 1000010077L);
        assertUsage("74ded52a-4454-1225-ab4c-fA2e029298af", UsageStatusEnum.RH_FOUND, 1000023401L);
        assertUsage("3a6b6f25-9f68-4da7-be4f-dd65574f5168", UsageStatusEnum.RH_NOT_FOUND, null);
        assertAudit("b77e72d6-ef71-4f4b-a00b-5800e43e5bee", "Rightsholder account 1000010077 was found in RMS");
        assertAudit("8aded52d-9507-4883-ab4c-fd2e029298af", "Rightsholder account 1000010077 was found in RMS");
        assertAudit("74ded52a-4454-1225-ab4c-fA2e029298af", "Rightsholder account 1000023401 was found in RMS");
        assertAudit("3a6b6f25-9f68-4da7-be4f-dd65574f5168", "Rightsholder account for 488824345 was not found in RMS");
        assertAudit("ede81bc0-a756-43a2-b236-05a0184384f4");
        testHelper.verifyRestServer();
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "test-update-rights-fas.groovy")
    public void testUpdateRightsProductFamilyFasGrantProductFamilyFas() {
        testHelper.createRestServer();
        testHelper.expectGetRmsRights("rights/fas/rms_grants_123440502_122853015_request.json",
            "rights/fas/rms_grants_123440502_122853015_response.json");
        testHelper.expectGetRmsRights("rights/rms_grants_122799600_request.json",
            RMS_GRANTS_EMPTY_RESPONSE_JSON);
        rightsService.updateRights(List.of(
            buildUsage("1ad07d10-c545-4921-9273-cdf74350683d", FAS_PRODUCT_FAMILY, 123440502L),
            buildUsage("3319494e-b75c-41fb-aed0-f65c14356942", FAS_PRODUCT_FAMILY, 122853015L)), true);
        rightsService.updateRights(List.of(
            buildUsage("5ecad71e-0e13-40a8-9304-b93f37aa8358", FAS_PRODUCT_FAMILY, 122799600L)), true);
        assertUsage("1ad07d10-c545-4921-9273-cdf74350683d", UsageStatusEnum.RH_FOUND, 1000011806L);
        assertUsage("3319494e-b75c-41fb-aed0-f65c14356942", UsageStatusEnum.RH_FOUND, 1000027688L);
        assertUsage("5ecad71e-0e13-40a8-9304-b93f37aa8358", UsageStatusEnum.RH_NOT_FOUND, null);
        assertAudit("1ad07d10-c545-4921-9273-cdf74350683d", "Rightsholder account 1000011806 was found in RMS");
        assertAudit("3319494e-b75c-41fb-aed0-f65c14356942", "Rightsholder account 1000027688 was found in RMS");
        assertAudit("5ecad71e-0e13-40a8-9304-b93f37aa8358", "Rightsholder account for 122799600 was not found in RMS");
        testHelper.verifyRestServer();
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "test-update-rights-fas2.groovy")
    public void testUpdateRightsProductFamilyFas2GrantProductFamilyFas() {
        testHelper.createRestServer();
        testHelper.expectGetRmsRights("rights/fas/rms_grants_123440502_122853015_request.json",
            "rights/fas/rms_grants_123440502_122853015_response.json");
        testHelper.expectGetRmsRights("rights/rms_grants_122799600_request.json",
            RMS_GRANTS_EMPTY_RESPONSE_JSON);
        rightsService.updateRights(List.of(
            buildUsage("286b6f99-61dc-4aec-8921-1bb12a1af95c", FAS2_PRODUCT_FAMILY, 123440502L),
            buildUsage("439b553b-3df4-4b1d-b5c4-43e383614e2e", FAS2_PRODUCT_FAMILY, 122853015L)), true);
        rightsService.updateRights(List.of(
            buildUsage("6535b989-a9ee-4089-b815-1fee4c130e0b", FAS2_PRODUCT_FAMILY, 122799600L)), true);
        assertUsage("286b6f99-61dc-4aec-8921-1bb12a1af95c", UsageStatusEnum.RH_FOUND, 1000011806L);
        assertUsage("439b553b-3df4-4b1d-b5c4-43e383614e2e", UsageStatusEnum.RH_FOUND, 1000027688L);
        assertUsage("6535b989-a9ee-4089-b815-1fee4c130e0b", UsageStatusEnum.RH_NOT_FOUND, null);
        assertAudit("286b6f99-61dc-4aec-8921-1bb12a1af95c", "Rightsholder account 1000011806 was found in RMS");
        assertAudit("439b553b-3df4-4b1d-b5c4-43e383614e2e", "Rightsholder account 1000027688 was found in RMS");
        assertAudit("6535b989-a9ee-4089-b815-1fee4c130e0b", "Rightsholder account for 122799600 was not found in RMS");
        testHelper.verifyRestServer();
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "test-update-rights-nts.groovy")
    public void testUpdateRightsProductFamilyNtsGrantProductFamilyFas() {
        testHelper.createRestServer();
        testHelper.expectGetRmsRights("rights/fas/rms_grants_123440502_122853015_request.json",
            "rights/fas/rms_grants_123440502_122853015_response.json");
        testHelper.expectGetRmsRights("rights/rms_grants_122799600_request.json",
            RMS_GRANTS_EMPTY_RESPONSE_JSON);
        rightsService.updateRights(List.of(
            buildUsage("3810cb0f-1eea-4ad9-8a9c-dd96eecaa6f8", NTS_PRODUCT_FAMILY, 123440502L),
            buildUsage("579540ab-e49e-48ac-9623-3b5a9c0bc2b3", NTS_PRODUCT_FAMILY, 122853015L)), true);
        rightsService.updateRights(List.of(
            buildUsage("715f0c59-9af4-4ac7-95ff-dd957a7eecd6", NTS_PRODUCT_FAMILY, 122799600L)), true);
        assertUsage("3810cb0f-1eea-4ad9-8a9c-dd96eecaa6f8", UsageStatusEnum.RH_FOUND, 1000011806L);
        assertUsage("579540ab-e49e-48ac-9623-3b5a9c0bc2b3", UsageStatusEnum.RH_FOUND, 1000027688L);
        assertUsage("715f0c59-9af4-4ac7-95ff-dd957a7eecd6", UsageStatusEnum.RH_NOT_FOUND, null);
        assertAudit("3810cb0f-1eea-4ad9-8a9c-dd96eecaa6f8", "Rightsholder account 1000011806 was found in RMS");
        assertAudit("579540ab-e49e-48ac-9623-3b5a9c0bc2b3", "Rightsholder account 1000027688 was found in RMS");
        assertAudit("715f0c59-9af4-4ac7-95ff-dd957a7eecd6", "Rightsholder account for 122799600 was not found in RMS");
        testHelper.verifyRestServer();
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "test-update-aacl-rights.groovy")
    public void testUpdateAaclRights() {
        testHelper.createRestServer();
        testHelper.expectGetRmsRights("rights/aacl/rms_grants_request_1.json",
            "rights/aacl/rms_grants_response_1.json");
        testHelper.expectGetRmsRights("rights/aacl/rms_grants_200208329_request.json",
            RMS_GRANTS_EMPTY_RESPONSE_JSON);
        testHelper.expectPrmCall(PRM_RIGHTSHOLDER_1000000322_RESPONSE_JSON, 1000000322L);
        rightsService.updateAaclRights(List.of(
            buildAaclUsage("b23cb103-9242-4d58-a65d-2634b3e5a8cf", 122803735),
            buildAaclUsage("7e7b97d1-ad60-4d47-915b-2834c5cc056a", 130297955)));
        rightsService.updateAaclRights(List.of(buildAaclUsage("10c9a60f-28b6-466c-975c-3ea930089a9e", 200208329)));
        assertAaclUsage("b23cb103-9242-4d58-a65d-2634b3e5a8cf", UsageStatusEnum.RH_FOUND, 1000000322L, "ALL");
        assertAaclUsage("7e7b97d1-ad60-4d47-915b-2834c5cc056a", UsageStatusEnum.RH_FOUND, 1000023401L,
            PRINT_TYPE_OF_USE);
        assertAaclUsage("10c9a60f-28b6-466c-975c-3ea930089a9e", UsageStatusEnum.NEW, null, null);
        assertAudit("b23cb103-9242-4d58-a65d-2634b3e5a8cf", RH_FOUND_REASON);
        assertAudit("7e7b97d1-ad60-4d47-915b-2834c5cc056a", "Rightsholder account 1000023401 was found in RMS");
        assertAudit("10c9a60f-28b6-466c-975c-3ea930089a9e");
        testHelper.verifyRestServer();
    }

    @Test
    @TestData(fileName = FOLDER_NAME + UDM_RIGHTS_FILE)
    public void testUpdateUdmRights() {
        testHelper.createRestServer();
        testHelper.expectGetRmsRights(RIGHTS_GRANTS_REQUEST_1,
            "rights/udm/usage/rms_grants_response_1.json");
        testHelper.expectGetRmsRights(RIGHTS_GRANTS_210001899_REQUEST, RMS_GRANTS_EMPTY_RESPONSE_JSON);
        testHelper.expectPrmCall(PRM_RIGHTSHOLDER_1000000322_RESPONSE_JSON, 1000000322L);
        rightsService.updateUdmRights(List.of(
            buildUdmUsage(UDM_USAGE_ID_1, 122769421, DIGITAL_TYPE_OF_USE),
            buildUdmUsage(UDM_USAGE_ID_2, 210001133, PRINT_TYPE_OF_USE)));
        rightsService.updateUdmRights(List.of(
            buildUdmUsage(UDM_USAGE_ID_3, 210001899, DIGITAL_TYPE_OF_USE)));
        assertUdmUsage(UDM_USAGE_ID_1, UsageStatusEnum.RH_FOUND, 1000023401L);
        assertUdmUsage(UDM_USAGE_ID_2, UsageStatusEnum.RH_FOUND, 1000000322L);
        assertUdmUsage(UDM_USAGE_ID_3, UsageStatusEnum.RH_NOT_FOUND, null);
        testHelper.verifyRestServer();
    }

    @Test
    @TestData(fileName = FOLDER_NAME + UDM_RIGHTS_FILE)
    public void testUpdateUdmMaclRights() {
        testHelper.createRestServer();
        testHelper.expectGetRmsRights(RIGHTS_GRANTS_REQUEST_1,
            "rights/udm/usage/rms_grants_response_1_MACL.json");
        testHelper.expectGetRmsRights(RIGHTS_GRANTS_210001899_REQUEST, RMS_GRANTS_EMPTY_RESPONSE_JSON);
        testHelper.expectPrmCall(PRM_RIGHTSHOLDER_1000000322_RESPONSE_JSON, 1000000322L);
        rightsService.updateUdmRights(List.of(
            buildUdmUsage(UDM_USAGE_ID_1, 122769421, DIGITAL_TYPE_OF_USE),
            buildUdmUsage(UDM_USAGE_ID_2, 210001133, PRINT_TYPE_OF_USE)));
        rightsService.updateUdmRights(List.of(
            buildUdmUsage(UDM_USAGE_ID_3, 210001899, DIGITAL_TYPE_OF_USE)));
        assertUdmUsage(UDM_USAGE_ID_1, UsageStatusEnum.RH_FOUND, 1000000322L);
        assertUdmUsage(UDM_USAGE_ID_2, UsageStatusEnum.RH_FOUND, 1000023401L);
        assertUdmUsage(UDM_USAGE_ID_3, UsageStatusEnum.RH_NOT_FOUND, null);
        testHelper.verifyRestServer();
    }

    @Test
    @TestData(fileName = FOLDER_NAME + UDM_RIGHTS_FILE)
    public void testUpdateUdmJacdclRights() {
        testHelper.createRestServer();
        testHelper.expectGetRmsRights(RIGHTS_GRANTS_REQUEST_1,
            "rights/udm/usage/rms_grants_response_1_JACDCL.json");
        testHelper.expectGetRmsRights(RIGHTS_GRANTS_210001899_REQUEST, RMS_GRANTS_EMPTY_RESPONSE_JSON);
        rightsService.updateUdmRights(List.of(
            buildUdmUsage(UDM_USAGE_ID_1, 122769421, DIGITAL_TYPE_OF_USE),
            buildUdmUsage(UDM_USAGE_ID_2, 210001133, PRINT_TYPE_OF_USE)));
        rightsService.updateUdmRights(List.of(
            buildUdmUsage(UDM_USAGE_ID_3, 210001899, DIGITAL_TYPE_OF_USE)));
        assertUdmUsage(UDM_USAGE_ID_1, UsageStatusEnum.RH_FOUND, 1000023401L);
        assertUdmUsage(UDM_USAGE_ID_2, UsageStatusEnum.RH_FOUND, 1000023401L);
        assertUdmUsage(UDM_USAGE_ID_3, UsageStatusEnum.RH_NOT_FOUND, null);
        testHelper.verifyRestServer();
    }

    @Test
    @TestData(fileName = FOLDER_NAME + UDM_RIGHTS_FILE)
    public void testUpdateUdmVgwRights() {
        testHelper.createRestServer();
        testHelper.expectGetRmsRights(RIGHTS_GRANTS_REQUEST_1,
            "rights/udm/usage/rms_grants_response_1_VGW.json");
        testHelper.expectGetRmsRights(RIGHTS_GRANTS_210001899_REQUEST, RMS_GRANTS_EMPTY_RESPONSE_JSON);
        testHelper.expectPrmCall(PRM_RIGHTSHOLDER_1000000322_RESPONSE_JSON, 1000000322L);
        rightsService.updateUdmRights(List.of(
            buildUdmUsage(UDM_USAGE_ID_1, 122769421, DIGITAL_TYPE_OF_USE),
            buildUdmUsage(UDM_USAGE_ID_2, 210001133, PRINT_TYPE_OF_USE)));
        rightsService.updateUdmRights(List.of(
            buildUdmUsage(UDM_USAGE_ID_3, 210001899, DIGITAL_TYPE_OF_USE)));
        assertUdmUsage(UDM_USAGE_ID_1, UsageStatusEnum.RH_FOUND, 1000023401L);
        assertUdmUsage(UDM_USAGE_ID_2, UsageStatusEnum.RH_FOUND, 1000000322L);
        assertUdmUsage(UDM_USAGE_ID_3, UsageStatusEnum.RH_NOT_FOUND, null);
        testHelper.verifyRestServer();
    }

    @Test
    @TestData(fileName = FOLDER_NAME + UDM_RIGHTS_FILE)
    public void testUpdateUdmTrsRights() {
        testHelper.createRestServer();
        testHelper.expectGetRmsRights(RIGHTS_GRANTS_REQUEST_1,
            "rights/udm/usage/rms_grants_response_1_TRS.json");
        testHelper.expectGetRmsRights(RIGHTS_GRANTS_210001899_REQUEST, RMS_GRANTS_EMPTY_RESPONSE_JSON);
        testHelper.expectPrmCall(PRM_RIGHTSHOLDER_1000000322_RESPONSE_JSON, 1000000322L);
        rightsService.updateUdmRights(List.of(
            buildUdmUsage(UDM_USAGE_ID_1, 122769421, DIGITAL_TYPE_OF_USE),
            buildUdmUsage(UDM_USAGE_ID_2, 210001133, PRINT_TYPE_OF_USE)));
        rightsService.updateUdmRights(List.of(
            buildUdmUsage(UDM_USAGE_ID_3, 210001899, DIGITAL_TYPE_OF_USE)));
        assertUdmUsage(UDM_USAGE_ID_1, UsageStatusEnum.RH_FOUND, 1000000322L);
        assertUdmUsage(UDM_USAGE_ID_2, UsageStatusEnum.RH_FOUND, 1000023401L);
        assertUdmUsage(UDM_USAGE_ID_3, UsageStatusEnum.RH_NOT_FOUND, null);
        testHelper.verifyRestServer();
    }

    @Test
    @TestData(fileName = FOLDER_NAME + UDM_RIGHTS_FILE)
    public void testUpdateUdmDpsRights() {
        testHelper.createRestServer();
        testHelper.expectGetRmsRights(RIGHTS_GRANTS_REQUEST_1,
            "rights/udm/usage/rms_grants_response_1_DPS.json");
        testHelper.expectGetRmsRights(RIGHTS_GRANTS_210001899_REQUEST, RMS_GRANTS_EMPTY_RESPONSE_JSON);
        testHelper.expectPrmCall(PRM_RIGHTSHOLDER_1000000322_RESPONSE_JSON, 1000000322L);
        rightsService.updateUdmRights(List.of(
            buildUdmUsage(UDM_USAGE_ID_1, 122769421, DIGITAL_TYPE_OF_USE),
            buildUdmUsage(UDM_USAGE_ID_2, 210001133, PRINT_TYPE_OF_USE)));
        rightsService.updateUdmRights(List.of(
            buildUdmUsage(UDM_USAGE_ID_3, 210001899, DIGITAL_TYPE_OF_USE)));
        assertUdmUsage(UDM_USAGE_ID_1, UsageStatusEnum.RH_FOUND, 1000000322L);
        assertUdmUsage(UDM_USAGE_ID_2, UsageStatusEnum.RH_FOUND, 1000023401L);
        assertUdmUsage(UDM_USAGE_ID_3, UsageStatusEnum.RH_NOT_FOUND, null);
        testHelper.verifyRestServer();
    }

    @Test
    @TestData(fileName = FOLDER_NAME + UDM_RIGHTS_FILE)
    public void testUpdateUdmSalRights() {
        testHelper.createRestServer();
        testHelper.expectGetRmsRights(RIGHTS_GRANTS_REQUEST_1,
            "rights/udm/usage/rms_grants_response_1_SAL.json");
        testHelper.expectGetRmsRights(RIGHTS_GRANTS_210001899_REQUEST, RMS_GRANTS_EMPTY_RESPONSE_JSON);
        testHelper.expectPrmCall(PRM_RIGHTSHOLDER_1000000322_RESPONSE_JSON, 1000000322L);
        rightsService.updateUdmRights(List.of(
            buildUdmUsage(UDM_USAGE_ID_1, 122769421, DIGITAL_TYPE_OF_USE),
            buildUdmUsage(UDM_USAGE_ID_2, 210001133, PRINT_TYPE_OF_USE)));
        rightsService.updateUdmRights(List.of(
            buildUdmUsage(UDM_USAGE_ID_3, 210001899, DIGITAL_TYPE_OF_USE)));
        assertUdmUsage(UDM_USAGE_ID_1, UsageStatusEnum.RH_FOUND, 1000000322L);
        assertUdmUsage(UDM_USAGE_ID_2, UsageStatusEnum.RH_FOUND, 1000000322L);
        assertUdmUsage(UDM_USAGE_ID_3, UsageStatusEnum.RH_NOT_FOUND, null);
        testHelper.verifyRestServer();
    }

    @Test
    @TestData(fileName = FOLDER_NAME + UDM_RIGHTS_FILE)
    public void testUpdateUdmRlsRights() {
        testHelper.createRestServer();
        testHelper.expectGetRmsRights(RIGHTS_GRANTS_REQUEST_1, "rights/udm/usage/rms_grants_response_1_RLS.json");
        testHelper.expectGetRmsRights(RIGHTS_GRANTS_210001899_REQUEST, RMS_GRANTS_EMPTY_RESPONSE_JSON);
        testHelper.expectPrmCall(PRM_RIGHTSHOLDER_1000000322_RESPONSE_JSON, 1000000322L);
        rightsService.updateUdmRights(List.of(
            buildUdmUsage(UDM_USAGE_ID_1, 122769421, DIGITAL_TYPE_OF_USE),
            buildUdmUsage(UDM_USAGE_ID_2, 210001133, PRINT_TYPE_OF_USE)));
        rightsService.updateUdmRights(List.of(
            buildUdmUsage(UDM_USAGE_ID_3, 210001899, DIGITAL_TYPE_OF_USE)));
        assertUdmUsage(UDM_USAGE_ID_1, UsageStatusEnum.RH_FOUND, 1000000322L);
        assertUdmUsage(UDM_USAGE_ID_2, UsageStatusEnum.RH_FOUND, 1000023401L);
        assertUdmUsage(UDM_USAGE_ID_3, UsageStatusEnum.RH_NOT_FOUND, null);
        testHelper.verifyRestServer();
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "test-update-udm-values-rights.groovy")
    public void testUpdateUdmValuesRights() {
        testHelper.createRestServer();
        testHelper.expectGetRmsRights("rights/udm/value/rms_grants_202112_1_request.json",
            "rights/udm/value/rms_grants_202112_1_response.json");
        testHelper.expectGetRmsRights("rights/udm/value/rms_grants_202112_2_request.json",
            "rights/udm/value/rms_grants_202112_2_response.json");
        testHelper.expectPrmCall(PRM_RIGHTSHOLDER_1000000322_RESPONSE_JSON, 1000000322L);
        List<UdmValue> values = List.of(buildUdmValue(122769421), buildUdmValue(243618757),
            buildUdmValue(140160102), buildUdmValue(210001133));
        rightsService.updateUdmValuesRights(values, 202112);
        assertEquals(RH_ACCOUNT_NUMBER_1, values.get(0).getRhAccountNumber());
        assertEquals(RH_ACCOUNT_NUMBER_2, values.get(1).getRhAccountNumber());
        assertNull(values.get(2).getRhAccountNumber());
        assertEquals(RH_ACCOUNT_NUMBER_1, values.get(3).getRhAccountNumber());
        testHelper.verifyRestServer();
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "test-update-sal-rights.groovy")
    public void testUpdateSalRights() {
        testHelper.createRestServer();
        testHelper.expectGetRmsRights("rights/sal/rms_grants_request_1.json", "rights/sal/rms_grants_response_1.json");
        testHelper.expectGetRmsRights("rights/sal/rms_grants_140160102_request.json", RMS_GRANTS_EMPTY_RESPONSE_JSON);
        testHelper.expectPrmCall(PRM_RIGHTSHOLDER_1000000322_RESPONSE_JSON, 1000000322L);
        rightsService.updateSalRights(List.of(
            buildSalUsage("dcb53a42-7e8d-4a4a-8d72-6f794be2731c", 122769471),
            buildSalUsage("094749c5-08fa-4f57-8c3b-ecbc334a5c2a", 243618757)));
        rightsService.updateSalRights(List.of(buildSalUsage("ecf46bea-2baa-40c1-a5e1-769c78865b2c", 140160102)));
        assertSalUsage("dcb53a42-7e8d-4a4a-8d72-6f794be2731c", UsageStatusEnum.RH_FOUND, 1000000322L);
        assertSalUsage("094749c5-08fa-4f57-8c3b-ecbc334a5c2a", UsageStatusEnum.WORK_NOT_GRANTED, null);
        assertSalUsage("ecf46bea-2baa-40c1-a5e1-769c78865b2c", UsageStatusEnum.RH_NOT_FOUND, null);
        assertAudit("dcb53a42-7e8d-4a4a-8d72-6f794be2731c", RH_FOUND_REASON);
        assertAudit("094749c5-08fa-4f57-8c3b-ecbc334a5c2a",
            "Right for 243618757 is denied for rightsholder account 1000000322");
        assertAudit("ecf46bea-2baa-40c1-a5e1-769c78865b2c", "Rightsholder account for 140160102 was not found in RMS");
        testHelper.verifyRestServer();
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "test-update-aclci-rights.groovy")
    public void testUpdateAclciRights() {
        testHelper.createRestServer();
        testHelper.expectGetRmsRights("rights/aclci/rms_grants_request_1.json",
            "rights/udm/usage/rms_grants_response_1_ACLCI.json");
        testHelper.expectGetRmsRights("rights/aclci/rms_grants_140160102_request.json", RMS_GRANTS_EMPTY_RESPONSE_JSON);
        testHelper.expectPrmCall(PRM_RIGHTSHOLDER_1000000322_RESPONSE_JSON, 1000000322L);
        rightsService.updateAclciRights(List.of(
            buildAclciUsage("019af1aa-c178-467c-9015-c2d18db85229", 122769471, AclciLicenseTypeEnum.CURR_REPUB_K12),
            buildAclciUsage("db86af7e-c2ae-4cc6-b797-6214298b7113", 243618757, AclciLicenseTypeEnum.CURR_REPUB_K12)));
        rightsService.updateAclciRights(List.of(
            buildAclciUsage("65d36e80-8b5c-42cf-b543-4b9ee0aed0cb", 140160102, AclciLicenseTypeEnum.CURR_REPUB_HE)));
        assertAclciUsage("019af1aa-c178-467c-9015-c2d18db85229", UsageStatusEnum.RH_FOUND, 1000000322L);
        assertAclciUsage("db86af7e-c2ae-4cc6-b797-6214298b7113", UsageStatusEnum.WORK_NOT_GRANTED, null);
        assertAclciUsage("65d36e80-8b5c-42cf-b543-4b9ee0aed0cb", UsageStatusEnum.RH_NOT_FOUND, null);
        assertAudit("019af1aa-c178-467c-9015-c2d18db85229", RH_FOUND_REASON);
        assertAudit("db86af7e-c2ae-4cc6-b797-6214298b7113",
            "Right for 243618757 is denied for rightsholder account 1000000322");
        assertAudit("65d36e80-8b5c-42cf-b543-4b9ee0aed0cb", "Rightsholder account for 140160102 was not found in RMS");
        testHelper.verifyRestServer();
    }

    private Usage buildUsage(String usageId, String productFamily, Long wrWrkInst) {
        Usage usage = new Usage();
        usage.setId(usageId);
        usage.setWrWrkInst(wrWrkInst);
        usage.setProductFamily(productFamily);
        return usage;
    }

    private Usage buildAaclUsage(String usageId, long wrWrkInst) {
        Usage usage = buildUsage(usageId, AACL_PRODUCT_FAMILY, wrWrkInst);
        usage.setAaclUsage(new AaclUsage());
        usage.getAaclUsage().setBatchPeriodEndDate(LocalDate.of(2015, 6, 30));
        return usage;
    }

    private Usage buildSalUsage(String usageId, long wrWrkInst) {
        Usage usage = buildUsage(usageId, "SAL", wrWrkInst);
        usage.setSalUsage(new SalUsage());
        usage.getSalUsage().setBatchPeriodEndDate(LocalDate.of(2020, 6, 30));
        return usage;
    }

    private UdmValue buildUdmValue(long wrWrkInst) {
        UdmValue value = new UdmValue();
        value.setWrWrkInst(wrWrkInst);
        return value;
    }

    private UdmUsage buildUdmUsage(String usageId, long wrWrkInst, String typeOfUse) {
        UdmUsage udmUsage = new UdmUsage();
        udmUsage.setId(usageId);
        udmUsage.setWrWrkInst(wrWrkInst);
        udmUsage.setTypeOfUse(typeOfUse);
        udmUsage.setPeriodEndDate(LocalDate.of(2020, 6, 30));
        return udmUsage;
    }

    private Usage buildAclciUsage(String usageId, long wrWrkInst, AclciLicenseTypeEnum licenseType) {
        Usage usage = buildUsage(usageId, "ACLCI", wrWrkInst);
        usage.setAclciUsage(new AclciUsage());
        usage.getAclciUsage().setLicenseType(licenseType);
        usage.getAclciUsage().setBatchPeriodEndDate(LocalDate.of(2022, 6, 30));
        return usage;
    }

    private void assertUdmUsage(String usageId, UsageStatusEnum expectedStatus, Long expectedRhAccountNumber) {
        UdmUsage udmUsage = udmUsageService.getUdmUsagesByIds(List.of(usageId)).get(0);
        assertEquals(expectedStatus, udmUsage.getStatus());
        assertEquals(expectedRhAccountNumber, udmUsage.getRightsholder().getAccountNumber());
    }

    private void assertAaclUsage(String usageId, UsageStatusEnum expectedStatus, Long expectedRhAccountNumber,
                                 String expectedRightLimitation) {
        Usage usage = aaclUsageRepository.findByIds(List.of(usageId)).get(0);
        assertEquals(expectedStatus, usage.getStatus());
        assertEquals(expectedRhAccountNumber, usage.getRightsholder().getAccountNumber());
        assertEquals(expectedRightLimitation, usage.getAaclUsage().getRightLimitation());
    }

    private void assertSalUsage(String usageId, UsageStatusEnum expectedStatus, Long expectedRhAccountNumber) {
        Usage usage = salUsageRepository.findByIds(List.of(usageId)).get(0);
        assertEquals(expectedStatus, usage.getStatus());
        assertEquals(expectedRhAccountNumber, usage.getRightsholder().getAccountNumber());
    }

    private void assertUsage(String usageId, UsageStatusEnum expectedStatus, Long expectedRhAccountNumber) {
        Usage usage = usageRepository.findByIds(List.of(usageId)).get(0);
        assertEquals(expectedStatus, usage.getStatus());
        assertEquals(expectedRhAccountNumber, usage.getRightsholder().getAccountNumber());
    }

    private void assertAclciUsage(String usageId, UsageStatusEnum expectedStatus, Long expectedRhAccountNumber) {
        Usage usage = aclciUsageRepository.findByIds(List.of(usageId)).get(0);
        assertEquals(expectedStatus, usage.getStatus());
        assertEquals(expectedRhAccountNumber, usage.getRightsholder().getAccountNumber());
    }

    // TODO: check ordering of audit items
    private void assertAudit(String usageId, String... reasons) {
        List<UsageAuditItem> auditItems = usageAuditService.getUsageAudit(usageId);
        assertEquals(CollectionUtils.size(auditItems), ArrayUtils.getLength(reasons));
        Arrays.stream(reasons).forEach(expectedReason ->
            assertTrue(auditItems.stream().anyMatch(item -> expectedReason.equals(item.getActionReason()))));
    }
}
