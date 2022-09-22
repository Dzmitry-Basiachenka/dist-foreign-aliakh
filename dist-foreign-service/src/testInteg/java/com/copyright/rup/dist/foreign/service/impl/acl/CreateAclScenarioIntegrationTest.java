package com.copyright.rup.dist.foreign.service.impl.acl;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.AclPublicationType;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.UsageAge;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.List;

/**
 * Verifies ACL scenario creation.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 07/05/2022
 *
 * @author Anton Azarenka
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml")
@TestData(fileName = "create-acl-scenario-data-init.groovy")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class CreateAclScenarioIntegrationTest {

    private static final String SCENARIO_NAME = "ACL Scenario";
    private static final String SCENARIO_DESCRIPTION = "ACL Scenario Description";
    private static final String LICENSE_TYPE = "ACL";
    private static final Integer PERIOD = 202212;

    @Autowired
    private CreateAclScenarioIntegrationTestBuilder builder;

    @Test
    public void testCreateAclScenario() throws IOException {
        AclScenario scenario =
            buildAclScenario("b6d2b116-a113-4013-b59a-fd166b9257d6", "87ee29b3-fdb6-4d67-a08d-99371aadca77",
                "df3096cb-eb6f-440f-9450-98293b45e81c", SCENARIO_NAME, SCENARIO_DESCRIPTION,
                ScenarioStatusEnum.IN_PROGRESS, true, PERIOD, LICENSE_TYPE);
        builder
            .withExpectedScenario(scenario)
            .withUsageAges(loadExpectedAclUsageAge("scenario/acl_scenario_usage_ages.json"))
            .withLicenseeClasses(loadExpectedAclLicenseeClass("scenario/acl_scenario_licensee_classes.json"))
            .withPublicationTypes(loadExpectedAclPublicationType("scenario/acl_scenario_publication_types.json"))
            .withAclScenarioDetails("acl/scenario/acl_scenario_details.json")
            .build()
            .run();
    }

    private AclScenario buildAclScenario(String fundPoolId, String usageBatchId, String grantSetId,
                                         String name, String description, ScenarioStatusEnum status, boolean editable,
                                         Integer periodEndDate, String licenseType) {
        AclScenario scenario = new AclScenario();
        scenario.setFundPoolId(fundPoolId);
        scenario.setUsageBatchId(usageBatchId);
        scenario.setGrantSetId(grantSetId);
        scenario.setName(name);
        scenario.setDescription(description);
        scenario.setStatus(status);
        scenario.setEditableFlag(editable);
        scenario.setPeriodEndDate(periodEndDate);
        scenario.setLicenseType(licenseType);
        return scenario;
    }

    public List<UsageAge> loadExpectedAclUsageAge(String fileName) throws IOException {
        String content = TestUtils.fileToString(this.getClass(), fileName);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(content, new TypeReference<List<UsageAge>>() {
        });
    }

    public List<AclPublicationType> loadExpectedAclPublicationType(String fileName) throws IOException {
        String content = TestUtils.fileToString(this.getClass(), fileName);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(content, new TypeReference<List<AclPublicationType>>() {
        });
    }

    public List<DetailLicenseeClass> loadExpectedAclLicenseeClass(String fileName) throws IOException {
        String content = TestUtils.fileToString(this.getClass(), fileName);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(content, new TypeReference<List<DetailLicenseeClass>>() {
        });
    }
}
