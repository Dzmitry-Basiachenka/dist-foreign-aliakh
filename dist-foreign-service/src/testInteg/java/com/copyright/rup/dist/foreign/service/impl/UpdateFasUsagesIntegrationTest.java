package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.common.caching.api.ICacheService;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.service.api.fas.IFasUsageService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.List;

/**
 * Verifies FAS usages update.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 02/15/2023
 *
 * @author Aliaksandr Liakh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class UpdateFasUsagesIntegrationTest {

    private static final String FOLDER_NAME = "update-fas-usages-integration-test/";
    private static final String USAGE_ID_1 = "5be457bd-12c3-42d2-b62e-cc2f3e056566";
    private static final String USAGE_ID_2 = "da1a1603-ea75-4933-8f63-818e46f2d49a";
    private static final String USAGE_ID_3 = "4d50fb77-8766-42e5-bfd2-df56e6ba5605";
    private static final String USAGE_ID_4 = "bd8fcc73-bd03-46b4-8f6b-84586d598de9";
    private static final Long WR_WRK_INST_1 = 854030732L;
    private static final Long WR_WRK_INST_2 = 122820638L;
    private static final String REASON = "some reason";

    @Autowired
    private IFasUsageService fasUsageService;
    @Autowired
    private ServiceTestHelper testHelper;
    @Autowired
    private List<ICacheService<?, ?>> cacheServices;

    @Before
    public void setUp() {
        cacheServices.forEach(ICacheService::invalidateCache);
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "update-usages-work-found.groovy")
    public void testUpdateUsagesWorkFound() throws IOException {
        testHelper.createRestServer();
        testHelper.expectGetRmsRights("rights/fas/rms_grants_request_854030732.json",
            "rights/fas/rms_grants_response_854030732.json");
        testHelper.expectPrmCall("prm/rightsholder_1000023401_response.json", 1000023401L);
        List<String> usageIds = List.of(USAGE_ID_1, USAGE_ID_2);
        fasUsageService.updateUsages(usageIds, WR_WRK_INST_1, REASON);
        testHelper.assertUsages(testHelper.loadExpectedUsages("fas/usage/usages_5be457bd_da1a1603.json"));
        testHelper.assertAuditIgnoringOrder(USAGE_ID_1,
            testHelper.loadExpectedUsageAuditItems("fas/usage/usage_audit_5be457bd.json"));
        testHelper.assertAuditIgnoringOrder(USAGE_ID_2,
            testHelper.loadExpectedUsageAuditItems("fas/usage/usage_audit_da1a1603.json"));
        testHelper.verifyRestServer();
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "update-usages-work-not-found.groovy")
    public void testUpdateUsagesWorkNotFound() throws IOException {
        testHelper.createRestServer();
        List<String> usageIds = List.of(USAGE_ID_3, USAGE_ID_4);
        fasUsageService.updateUsages(usageIds, WR_WRK_INST_2, REASON);
        testHelper.assertUsages(testHelper.loadExpectedUsages("fas/usage/usages_4d50fb77_bd8fcc73.json"));
        testHelper.assertAuditIgnoringOrder(USAGE_ID_3,
            testHelper.loadExpectedUsageAuditItems("fas/usage/usage_audit_4d50fb77.json"));
        testHelper.assertAuditIgnoringOrder(USAGE_ID_4,
            testHelper.loadExpectedUsageAuditItems("fas/usage/usage_audit_bd8fcc73.json"));
        testHelper.verifyRestServer();
    }
}
