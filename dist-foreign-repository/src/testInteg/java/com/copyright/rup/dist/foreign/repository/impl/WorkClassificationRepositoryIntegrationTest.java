package com.copyright.rup.dist.foreign.repository.impl;

import static junit.framework.TestCase.assertNull;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.api.Sort.Direction;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.WorkClassification;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Verifies {@link WorkClassificationRepository}.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 03/06/2019
 *
 * @author Pavel Liakh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml"})
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class WorkClassificationRepositoryIntegrationTest {

    private static final String FOLDER_NAME = "work-classification-repository-integration-test/";
    private static final String BATCH_UID = "e17ebc80-e74e-436d-ba6e-acf3d355b7ff";
    private static final String NON_STM = "NON-STM";
    private static final String STM = "STM";
    private static final String BELLETRISTIC = "BELLETRISTIC";
    private static final Long WR_WRK_INST_1 = 111111111L;
    private static final Long WR_WRK_INST_2 = 222222222L;

    @Autowired
    private WorkClassificationRepository workClassificationRepository;

    @Test
    @TestData(fileName = FOLDER_NAME + "find-classification-by-work.groovy")
    public void testFindClassificationByWrWrkInst() {
        assertEquals(NON_STM, workClassificationRepository.findClassificationByWrWrkInst(WR_WRK_INST_1));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-classification-by-missing-work.groovy")
    public void testFindClassificationByWrWrkInstWithNoClassification() {
        assertNull(workClassificationRepository.findClassificationByWrWrkInst(WR_WRK_INST_2));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "insert-or-update-existing-classification.groovy")
    public void testInsertOrUpdateWithExistingClassification() {
        WorkClassification classification = buildClassification(BELLETRISTIC, WR_WRK_INST_1);
        assertEquals(NON_STM, workClassificationRepository.findClassificationByWrWrkInst(WR_WRK_INST_1));
        assertEquals(NON_STM, workClassificationRepository.findClassificationByWrWrkInst(180382914L));
        workClassificationRepository.insertOrUpdate(classification);
        assertEquals(BELLETRISTIC, workClassificationRepository.findClassificationByWrWrkInst(WR_WRK_INST_1));
        assertEquals(NON_STM, workClassificationRepository.findClassificationByWrWrkInst(180382914L));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "insert-or-update-with-new-classification.groovy")
    public void testInsertOrUpdateWithNewClassification() {
        WorkClassification classification = buildClassification(STM, WR_WRK_INST_2);
        assertNull(workClassificationRepository.findClassificationByWrWrkInst(WR_WRK_INST_2));
        assertEquals(NON_STM, workClassificationRepository.findClassificationByWrWrkInst(180382914L));
        workClassificationRepository.insertOrUpdate(classification);
        assertEquals(STM, workClassificationRepository.findClassificationByWrWrkInst(WR_WRK_INST_2));
        assertEquals(NON_STM, workClassificationRepository.findClassificationByWrWrkInst(180382914L));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "delete-by-work.groovy")
    public void testDeleteByWrWkrInst() {
        assertEquals(NON_STM, workClassificationRepository.findClassificationByWrWrkInst(WR_WRK_INST_1));
        assertEquals(NON_STM, workClassificationRepository.findClassificationByWrWrkInst(180382914L));
        workClassificationRepository.deleteByWrWrkInst(WR_WRK_INST_1);
        assertNull(workClassificationRepository.findClassificationByWrWrkInst(WR_WRK_INST_1));
        assertEquals(NON_STM, workClassificationRepository.findClassificationByWrWrkInst(180382914L));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-by-batch-ids.groovy")
    public void testFindByBatchIds() throws IOException {
        List<WorkClassification> expectedClassifications =
            loadExpectedClassifications("json/work_classifications_find_by_batch_ids.json");
        findByBatchIdsAndAssertResult(expectedClassifications, null, 3);
        findByBatchIdsAndAssertResult(expectedClassifications, new Pageable(0, 3), 3);
        findByBatchIdsAndAssertResult(expectedClassifications, new Pageable(0, 2), 2);
        findByBatchIdsAndAssertResult(expectedClassifications, new Pageable(0, 1), 1);
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-by-batch-ids.groovy")
    public void testFindCountByBatchIds() {
        assertEquals(3,
            workClassificationRepository.findCountByBatchIds(Collections.singleton(BATCH_UID), StringUtils.EMPTY));
        assertEquals(1,
            workClassificationRepository.findCountByBatchIds(Collections.singleton(BATCH_UID), "243904752"));
        assertEquals(2, workClassificationRepository.findCountByBatchIds(Collections.singleton(BATCH_UID),
            "John Wiley & Sons - Books"));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-by-search.groovy")
    public void testFindBySearch() throws IOException {
        List<WorkClassification> expectedClassifications =
            loadExpectedClassifications("json/work_classifications_find_by_search.json");
        findAndAssertResult(expectedClassifications, null, 5);
        findAndAssertResult(expectedClassifications, new Pageable(0, 4), 4);
        findAndAssertResult(expectedClassifications, new Pageable(0, 3), 3);
        findAndAssertResult(expectedClassifications, new Pageable(0, 2), 2);
        findAndAssertResult(expectedClassifications, new Pageable(0, 1), 1);
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-by-search.groovy")
    public void testFindCountBySearch() {
        assertEquals(5, workClassificationRepository.findCountBySearch(StringUtils.EMPTY));
        assertEquals(1, workClassificationRepository.findCountBySearch("243904752"));
        assertEquals(2, workClassificationRepository.findCountBySearch("John Wiley & Sons - Books"));
    }

    private List<WorkClassification> loadExpectedClassifications(String fileName)
        throws IOException {
        String content = TestUtils.fileToString(this.getClass(), fileName);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper.readValue(content, new TypeReference<List<WorkClassification>>() {
        });
    }

    private void findByBatchIdsAndAssertResult(List<WorkClassification> expectedClassifications, Pageable pageable,
                                               int count) {
        List<WorkClassification> actualClassifications =
            workClassificationRepository.findByBatchIds(Collections.singleton(BATCH_UID), StringUtils.EMPTY, pageable,
                new Sort("wrWrkInst", Direction.ASC));
        assertEquals(count, actualClassifications.size());
        IntStream.range(0, actualClassifications.size())
            .forEach(i -> assertClassification(expectedClassifications.get(i), actualClassifications.get(i)));
    }

    private void findAndAssertResult(List<WorkClassification> expectedClassifications, Pageable pageable,
                                     int count) {
        List<WorkClassification> actualClassifications =
            workClassificationRepository.findBySearch(StringUtils.EMPTY, pageable, new Sort("wrWrkInst",
                Direction.ASC));
        assertEquals(count, actualClassifications.size());
        IntStream.range(0, actualClassifications.size())
            .forEach(i -> assertClassification(expectedClassifications.get(i), actualClassifications.get(i)));
    }

    private void assertClassification(WorkClassification expectedClassification,
                                      WorkClassification actualClassification) {
        assertEquals(expectedClassification.getId(), actualClassification.getId());
        assertEquals(expectedClassification.getWrWrkInst(), actualClassification.getWrWrkInst());
        assertEquals(expectedClassification.getClassification(), actualClassification.getClassification());
        assertEquals(expectedClassification.getSystemTitle(), actualClassification.getSystemTitle());
        assertEquals(expectedClassification.getRhAccountNumber(), actualClassification.getRhAccountNumber());
        assertEquals(expectedClassification.getRhName(), actualClassification.getRhName());
        assertEquals(expectedClassification.getStandardNumber(), actualClassification.getStandardNumber());
        assertEquals(expectedClassification.getStandardNumberType(), actualClassification.getStandardNumberType());
        assertEquals(expectedClassification.getUpdateDate(), actualClassification.getUpdateDate());
        assertEquals(expectedClassification.getUpdateUser(), actualClassification.getUpdateUser());
    }

    private WorkClassification buildClassification(String classification, Long wrWkrInst) {
        WorkClassification workClassification = new WorkClassification();
        workClassification.setId(RupPersistUtils.generateUuid());
        workClassification.setClassification(classification);
        workClassification.setWrWrkInst(wrWkrInst);
        return workClassification;
    }
}
