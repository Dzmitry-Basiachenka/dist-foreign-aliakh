package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

/**
 * Verifies application workflow.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 4/10/18
 *
 * @author Ihar Suvorau
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml")
@TestPropertySource(properties = {"test.liquibase.changelog=workflow-data-init.groovy"})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class WorkflowIntegrationTest {

    private static final LocalDate PAYMENT_DATE = LocalDate.now();

    @Autowired
    private WorkflowIntegrationTestBuilder testBuilder;

    @Test
    public void testClaWorkflow() throws Exception {
        testBuilder
            .withUsagesCsvFile("workflow/usages.csv")
            .withUsageBatch(buildUsageBatch())
            .withUsageFilter(buildUsageFilter())
            .expectInsertedUsagesCount(5)
            .expectPreferences("prm/not_found_response.json")
            .expectRollups("prm/cla_rollups_response.json", "b0e6b1f6-89e9-4767-b143-db0f49f32769",
                "624dcf73-a30f-4381-b6aa-c86d17198bd5", "60080587-a225-439c-81af-f016cb33aeac",
                "37338ed1-7083-45e2-a96b-5872a7de3a98", "f366285a-ce46-48b0-96ee-cd35d62fb243")
            .expectLmDetails("workflow/cla_details_to_lm.json")
            .build()
            .run();
    }

    private UsageFilter buildUsageFilter() {
        UsageFilter filter = new UsageFilter();
        filter.setProductFamilies(Collections.singleton("CLA_FAS"));
        filter.setUsageStatus(UsageStatusEnum.ELIGIBLE);
        return filter;
    }

    private UsageBatch buildUsageBatch() {
        UsageBatch batch = new UsageBatch();
        batch.setName("Test_Batch");
        batch.setRro(buildRro());
        batch.setPaymentDate(PAYMENT_DATE);
        batch.setFiscalYear(2018);
        batch.setGrossAmount(BigDecimal.valueOf(199));
        return batch;
    }

    private Rightsholder buildRro() {
        Rightsholder rro = new Rightsholder();
        rro.setId("77b111d3-9eea-49af-b815-100b9716c1b3");
        rro.setAccountNumber(2000017000L);
        rro.setName("CLA, The Copyright Licensing Agency Ltd.");
        return rro;
    }
}
