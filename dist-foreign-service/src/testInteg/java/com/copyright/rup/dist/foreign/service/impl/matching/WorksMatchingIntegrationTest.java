package com.copyright.rup.dist.foreign.service.impl.matching;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUsageAuditRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.impl.quartz.WorksMatchingJob;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Integration test to verify data is updated correctly while getting works and determining NTS usages.
 * {@link com.copyright.rup.dist.foreign.service.impl.mock.PiIntegrationServiceMock} is used to avoid calls to real
 * environment.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 2/27/18
 *
 * @author Aliaksandr Radkevich
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml"})
@TestPropertySource(properties = {"test.liquibase.changelog=works-matching-data-init.groovy"})
public class WorksMatchingIntegrationTest {

    private static final String NTS_BY_STANDARD_NUMBER_MESSAGE =
        "Detail was made eligible for NTS because sum of gross amounts, grouped by standard number, is less than $100";
    private static final String NTS_BY_WORK_TITLE_MESSAGE =
        "Detail was made eligible for NTS because sum of gross amounts, grouped by work title, is less than $100";
    private static final String NTS_BY_SELF_MESSAGE =
        "Detail was made eligible for NTS because gross amount is less than $100";
    private static final String NTS_PRODUCT_FAMILY = "NTS";
    private static final String FAS_PRODUCT_FAMILY = "FAS";
    @Autowired
    private WorksMatchingJob worksMatchingJob;
    @Autowired
    private IUsageRepository usageRepository;
    @Autowired
    private IUsageAuditRepository auditRepository;

    @Test
    public void testExecuteInternal() {
        worksMatchingJob.executeInternal(null);
        verifyUsage("4773573f-acd7-424f-8667-2828d30b5738", 123059057L, UsageStatusEnum.WORK_FOUND, FAS_PRODUCT_FAMILY,
            "Wr Wrk Inst 123059057 was found by standard number 978-0-271-01750-1");
        verifyUsage("c9ad10c6-eaeb-4485-b04b-d23d265f7bb5", 123059057L, UsageStatusEnum.WORK_FOUND, FAS_PRODUCT_FAMILY,
            "Wr Wrk Inst 123059057 was found by title \"Forbidden rites\"");
        verifyUsage("363ac3fe-20db-4db7-a967-57963c98aa05", 123059057L, UsageStatusEnum.WORK_FOUND, FAS_PRODUCT_FAMILY,
            "Wr Wrk Inst 123059057 was found by title \"Forbidden rites\"");
        verifyUsage("c25a3be4-138c-47b2-b7b6-41029b063679", 123059057L, UsageStatusEnum.WORK_FOUND, FAS_PRODUCT_FAMILY,
            "Wr Wrk Inst 123059057 was found by standard number 978-0-271-01750-1");
        verifyUsage("0ff04fa7-245c-4663-835b-48a7e0e7d9f9", null, UsageStatusEnum.WORK_NOT_FOUND, FAS_PRODUCT_FAMILY,
            "Wr Wrk Inst was not found by standard number 0-325-01548-2");
        verifyUsage("efd7813c-e4d6-41e3-824c-b22035af31d5", null, UsageStatusEnum.WORK_NOT_FOUND, FAS_PRODUCT_FAMILY,
            "Wr Wrk Inst was not found by standard number 0-325-01548-2");
        verifyUsage("aa8642d5-ac6f-4f38-8c93-5fef55dd37ce", 123050824L, UsageStatusEnum.WORK_FOUND, FAS_PRODUCT_FAMILY,
            "Usage assigned unidentified work due to empty standard number and title");
        verifyUsage("d906069c-3266-11e8-b467-0ed5f89f718b", 123050824L, UsageStatusEnum.WORK_FOUND, FAS_PRODUCT_FAMILY,
            "Usage assigned unidentified work due to empty standard number and title");
        verifyUsage("182c7557-e67a-4ac9-8f73-61972f1f5abb", null, UsageStatusEnum.WORK_NOT_FOUND, FAS_PRODUCT_FAMILY,
            "Wr Wrk Inst was not found by standard number 10.1353/PGN.1999.0081");
        verifyUsage("6e6c1852-70c3-4900-9b3d-47c6d3add697", null, UsageStatusEnum.WORK_NOT_FOUND, FAS_PRODUCT_FAMILY,
            "Wr Wrk Inst was not found by standard number 10.1353/PGN.1999.0081");
        verifyUsage("792252e1-9182-4e1a-a6dc-2b27b0e92c18", null, UsageStatusEnum.WORK_NOT_FOUND, FAS_PRODUCT_FAMILY,
            "Wr Wrk Inst was not found by standard number 978-0-08-027365-5");
        verifyUsage("032aaf56-61d1-4656-abe1-de2889f1214e", null, UsageStatusEnum.WORK_NOT_FOUND, FAS_PRODUCT_FAMILY,
            "Wr Wrk Inst was not found by standard number ETOCRN066582498");
        verifyUsage("58d7b428-d1fc-45b0-b190-ddef5e997f7d", null, UsageStatusEnum.WORK_NOT_FOUND, FAS_PRODUCT_FAMILY,
            "Wr Wrk Inst was not found by standard number ETOCRN066582498");
        verifyUsage("0949c35c-53f9-4aec-92d2-1e728235cd00", null, UsageStatusEnum.WORK_NOT_FOUND, FAS_PRODUCT_FAMILY,
            "Wr Wrk Inst was not found by standard number ETOCRN066582498");
        verifyUsage("cec47d5f-0df9-43df-a1e2-76b03fa0ce96", null, UsageStatusEnum.WORK_NOT_FOUND, FAS_PRODUCT_FAMILY,
            "Wr Wrk Inst was not found by standard number 0-325-01548-2");
        verifyUsage("68fb88b0-ed9b-4c36-93bc-2bf0b51c7223", null, UsageStatusEnum.WORK_NOT_FOUND, FAS_PRODUCT_FAMILY,
            "Wr Wrk Inst was not found by title \"Fall guys in the florentine flood\"");
        verifyUsage("c9ecd9ce-043e-4982-85c1-8e3aa97eaed6", null, UsageStatusEnum.ELIGIBLE, NTS_PRODUCT_FAMILY,
            NTS_BY_STANDARD_NUMBER_MESSAGE);
        verifyUsage("2186a1ed-62d1-4034-a6a6-ea9e61835b60", null, UsageStatusEnum.ELIGIBLE, NTS_PRODUCT_FAMILY,
            NTS_BY_STANDARD_NUMBER_MESSAGE);
        verifyUsage("8850b2f3-9fef-47da-a8f9-11143a892741", null, UsageStatusEnum.ELIGIBLE, NTS_PRODUCT_FAMILY,
            NTS_BY_STANDARD_NUMBER_MESSAGE);
        verifyUsage("223985fd-8033-42e6-afaf-4da73be804cf", null, UsageStatusEnum.ELIGIBLE, NTS_PRODUCT_FAMILY,
            NTS_BY_WORK_TITLE_MESSAGE);
        verifyUsage("4abbb5ea-516f-4b60-bfc4-e8a2d274bb34", null, UsageStatusEnum.ELIGIBLE, NTS_PRODUCT_FAMILY,
            NTS_BY_WORK_TITLE_MESSAGE);
        verifyUsage("dc9b3bb8-4135-472a-a7c3-50d800b88829", null, UsageStatusEnum.ELIGIBLE, NTS_PRODUCT_FAMILY,
            NTS_BY_WORK_TITLE_MESSAGE);
        verifyUsage("4336ca5e-44fe-46a9-996a-55bdd5967191", null, UsageStatusEnum.ELIGIBLE, NTS_PRODUCT_FAMILY,
            NTS_BY_WORK_TITLE_MESSAGE);
        verifyUsage("77a10e10-8154-49c1-88b8-7f5f0ad86c08", null, UsageStatusEnum.ELIGIBLE, NTS_PRODUCT_FAMILY,
            NTS_BY_SELF_MESSAGE);
        verifyUsage("3d85b292-3600-48e8-ba39-37d193afdfa6", null, UsageStatusEnum.ELIGIBLE, NTS_PRODUCT_FAMILY,
            NTS_BY_SELF_MESSAGE);
        verifyUsage("8d4006a3-cbae-48ab-a1e2-e0d5bd204f4d", null, UsageStatusEnum.ELIGIBLE, NTS_PRODUCT_FAMILY,
            NTS_BY_WORK_TITLE_MESSAGE);
    }

    private void verifyUsage(String usageId, Long wrWrkInst, UsageStatusEnum status, String productFamily,
                             String... auditMessages) {
        Usage usage = usageRepository.findById(usageId);
        assertEquals(status, usage.getStatus());
        assertEquals(productFamily, usage.getProductFamily());
        assertEquals(wrWrkInst, usage.getWrWrkInst());
        List<UsageAuditItem> auditItems = auditRepository.findByUsageId(usage.getId());
        assertEquals(auditMessages.length, auditItems.size());
        assertArrayEquals(auditMessages, auditItems.stream().map(UsageAuditItem::getActionReason).toArray());
    }
}
