package com.copyright.rup.dist.foreign.repository.impl;

import static junit.framework.TestCase.assertNull;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.api.Sort.Direction;
import com.copyright.rup.dist.foreign.domain.WorkClassification;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

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
    private static final String TITLE_1 = "2001 IEEE Workshop on High Performance Switching";
    private static final String TITLE_2 = "Corporate identity manuals";
    private static final String TITLE_3 = "future of children";

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
    public void testFindByBatchIds() {
        List<WorkClassification> classifications = workClassificationRepository.findByBatchIds(
            Collections.singleton(BATCH_UID), StringUtils.EMPTY, null, new Sort("wrWrkInst", Direction.ASC));
        assertEquals(3, classifications.size());
        assertClassification(classifications.get(0), 180382914L, NON_STM, TITLE_1);
        assertClassification(classifications.get(1), 243904752L, STM, TITLE_2);
        assertClassification(classifications.get(2), 244614835L, null, TITLE_3);
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
    public void testFindBySearch() {
        List<WorkClassification> classifications =
            workClassificationRepository.findBySearch(StringUtils.EMPTY, null, new Sort("wrWrkInst", Direction.ASC));
        assertEquals(5, classifications.size());
        assertClassification(classifications.get(0), WR_WRK_INST_1, NON_STM, null);
        assertClassification(classifications.get(1), 122410079L, STM, null);
        assertClassification(classifications.get(2), 123345258L, NON_STM,
            "15th International Conference on Environmental Degradation");
        assertClassification(classifications.get(3), 180382914L, NON_STM, TITLE_1);
        assertClassification(classifications.get(4), 243904752L, STM, TITLE_2);
    }

    @Test
    public void testFindCountBySearch() {
        assertEquals(5, workClassificationRepository.findCountBySearch(StringUtils.EMPTY));
        assertEquals(1, workClassificationRepository.findCountBySearch("243904752"));
        assertEquals(3, workClassificationRepository.findCountBySearch("John Wiley & Sons - Books"));
    }

    private void assertClassification(WorkClassification workClassification, Long wrWrkInst, String classification,
                                      String title) {
        assertEquals(wrWrkInst, workClassification.getWrWrkInst());
        assertEquals(classification, workClassification.getClassification());
        assertEquals(title, workClassification.getSystemTitle());
    }

    private WorkClassification buildClassification(String classification, Long wrWkrInst) {
        WorkClassification workClassification = new WorkClassification();
        workClassification.setId(RupPersistUtils.generateUuid());
        workClassification.setClassification(classification);
        workClassification.setWrWrkInst(wrWkrInst);
        return workClassification;
    }
}
