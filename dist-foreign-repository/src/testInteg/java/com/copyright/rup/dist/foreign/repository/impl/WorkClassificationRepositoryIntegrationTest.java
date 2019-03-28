package com.copyright.rup.dist.foreign.repository.impl;

import static junit.framework.TestCase.assertNull;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.api.Sort.Direction;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.WorkClassification;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

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
@TestPropertySource(properties = {"test.liquibase.changelog=work-classification-repository-test-data-init.groovy"})
@TransactionConfiguration
@Transactional
public class WorkClassificationRepositoryIntegrationTest {

    private static final String BATCH_UID = "e17ebc80-e74e-436d-ba6e-acf3d355b7ff";
    private static final String NON_STM = "NON-STM";
    private static final String STM = "STM";
    private static final String BELLETRISTIC = "BELLETRISTIC";
    private static final Long WR_WRK_INST_1 = 111111111L;
    private static final Long WR_WRK_INST_2 = 222222222L;

    @Autowired
    private WorkClassificationRepository workClassificationRepository;

    @Test
    public void testFindClassificationByWrWrkInst() {
        assertEquals(NON_STM, workClassificationRepository.findClassificationByWrWrkInst(WR_WRK_INST_1));
    }

    @Test
    public void testFindClassificationByWrWrkInstWithNoClassification() {
        assertNull(workClassificationRepository.findClassificationByWrWrkInst(WR_WRK_INST_2));
    }

    @Test
    public void testInsertOrUpdateWithExistingClassification() {
        WorkClassification classification = buildClassification(BELLETRISTIC, WR_WRK_INST_1);
        assertEquals(NON_STM, workClassificationRepository.findClassificationByWrWrkInst(WR_WRK_INST_1));
        assertEquals(NON_STM, workClassificationRepository.findClassificationByWrWrkInst(180382914L));
        workClassificationRepository.insertOrUpdate(classification);
        assertEquals(BELLETRISTIC, workClassificationRepository.findClassificationByWrWrkInst(WR_WRK_INST_1));
        assertEquals(NON_STM, workClassificationRepository.findClassificationByWrWrkInst(180382914L));
    }

    @Test
    public void testInsertOrUpdateWithNewClassification() {
        WorkClassification classification = buildClassification(STM, WR_WRK_INST_2);
        assertNull(workClassificationRepository.findClassificationByWrWrkInst(WR_WRK_INST_2));
        assertEquals(NON_STM, workClassificationRepository.findClassificationByWrWrkInst(180382914L));
        workClassificationRepository.insertOrUpdate(classification);
        assertEquals(STM, workClassificationRepository.findClassificationByWrWrkInst(WR_WRK_INST_2));
        assertEquals(NON_STM, workClassificationRepository.findClassificationByWrWrkInst(180382914L));
    }

    @Test
    public void testDeleteByWrWkrInst() {
        assertEquals(NON_STM, workClassificationRepository.findClassificationByWrWrkInst(WR_WRK_INST_1));
        assertEquals(NON_STM, workClassificationRepository.findClassificationByWrWrkInst(180382914L));
        workClassificationRepository.deleteByWrWrkInst(WR_WRK_INST_1);
        assertNull(workClassificationRepository.findClassificationByWrWrkInst(WR_WRK_INST_1));
        assertEquals(NON_STM, workClassificationRepository.findClassificationByWrWrkInst(180382914L));
    }

    @Test
    public void testFindByBatchIds() throws IOException {
        List<WorkClassification> expectedClassifications =
            loadExpectedClassifications("json/work_classifications_find_by_batch_ids.json");
        List<WorkClassification> actualClassifications =
            workClassificationRepository.findByBatchIds(Collections.singleton(BATCH_UID), StringUtils.EMPTY, null,
                new Sort("wrWrkInst", Direction.ASC));
        assertEquals(expectedClassifications.size(), actualClassifications.size());
        IntStream.range(0, actualClassifications.size())
            .forEach(i -> assertClassification(expectedClassifications.get(i), actualClassifications.get(i)));
    }

    @Test
    public void testFindCountByBatchIds() {
        assertEquals(3,
            workClassificationRepository.findCountByBatchIds(Collections.singleton(BATCH_UID), StringUtils.EMPTY));
        assertEquals(1,
            workClassificationRepository.findCountByBatchIds(Collections.singleton(BATCH_UID), "243904752"));
        assertEquals(2, workClassificationRepository.findCountByBatchIds(Collections.singleton(BATCH_UID),
            "John Wiley & Sons - Books"));
    }

    @Test
    public void testFindBySearch() throws IOException {
        List<WorkClassification> expectedClassifications =
            loadExpectedClassifications("json/work_classifications_find_by_search.json");
        List<WorkClassification> actualClassifications =
            workClassificationRepository.findBySearch(StringUtils.EMPTY, null, new Sort("wrWrkInst", Direction.ASC));
        assertEquals(expectedClassifications.size(), actualClassifications.size());
        IntStream.range(0, actualClassifications.size())
            .forEach(i -> assertClassification(expectedClassifications.get(i), actualClassifications.get(i)));
    }

    @Test
    public void testFindCountBySearch() {
        assertEquals(5, workClassificationRepository.findCountBySearch(StringUtils.EMPTY));
        assertEquals(1, workClassificationRepository.findCountBySearch("243904752"));
        assertEquals(3, workClassificationRepository.findCountBySearch("John Wiley & Sons - Books"));
    }

    private List<WorkClassification> loadExpectedClassifications(String fileName)
        throws IOException {
        String content = TestUtils.fileToString(this.getClass(), fileName);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper.readValue(content, new TypeReference<List<WorkClassification>>() {
        });
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
    }

    private WorkClassification buildClassification(String classification, Long wrWkrInst) {
        WorkClassification workClassification = new WorkClassification();
        workClassification.setId(RupPersistUtils.generateUuid());
        workClassification.setClassification(classification);
        workClassification.setWrWrkInst(wrWkrInst);
        return workClassification;
    }
}
