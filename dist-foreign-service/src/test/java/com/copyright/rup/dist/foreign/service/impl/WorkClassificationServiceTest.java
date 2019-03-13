package com.copyright.rup.dist.foreign.service.impl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.WorkClassification;
import com.copyright.rup.dist.foreign.repository.api.IWorkClassificationRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageService;

import com.google.common.collect.Sets;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Verifies {@link WorkClassificationService}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 03/06/19
 *
 * @author Pavel Liakh
 */
public class WorkClassificationServiceTest {

    private static final String NON_STM = "NON-STM";
    private static final String STM = "STM";
    private static final String BATCH_ID = RupPersistUtils.generateUuid();
    private static final String SEARCH_VALUE = "test";
    private static final Long WR_WRK_INST_1 = 111111111L;
    private static final Long WR_WRK_INST_2 = 222222222L;
    private WorkClassificationService workClassificationService;
    private IWorkClassificationRepository workClassificationRepository;
    private IUsageService usageService;

    @Before
    public void setUp() {
        workClassificationService = new WorkClassificationService();
        workClassificationRepository = createMock(IWorkClassificationRepository.class);
        usageService = createMock(IUsageService.class);
        Whitebox.setInternalState(workClassificationService, usageService);
        Whitebox.setInternalState(workClassificationService, workClassificationRepository);
    }

    @Test
    public void testGetClassification() {
        expect(workClassificationRepository.findClassificationByWrWrkInst(WR_WRK_INST_1)).andReturn(NON_STM).once();
        replay(workClassificationRepository);
        assertEquals(NON_STM, workClassificationService.getClassification(WR_WRK_INST_1));
        verify(workClassificationRepository);
    }

    @Test
    public void testInsertOrUpdateClassifications() {
        Capture<WorkClassification> classificationCapture1 = new Capture<>();
        Capture<WorkClassification> classificationCapture2 = new Capture<>();
        workClassificationRepository.insertOrUpdate(capture(classificationCapture1));
        expectLastCall().once();
        workClassificationRepository.insertOrUpdate(capture(classificationCapture2));
        expectLastCall().once();
        usageService.updateUnclassifiedUsages();
        expectLastCall().once();
        replay(workClassificationRepository, usageService);
        workClassificationService.insertOrUpdateClassifications(
            Sets.newHashSet(buildClassification(WR_WRK_INST_1), buildClassification(WR_WRK_INST_2)), STM);
        WorkClassification classification1 = classificationCapture1.getValue();
        assertNotNull(classification1.getWrWrkInst());
        assertEquals(STM, classification1.getClassification());
        WorkClassification classification2 = classificationCapture2.getValue();
        assertNotNull(classification2.getWrWrkInst());
        assertEquals(STM, classification2.getClassification());
        verify(workClassificationRepository, usageService);
    }

    @Test
    public void testDeleteClassifications() {
        workClassificationRepository.deleteByWrWrkInst(WR_WRK_INST_1);
        expectLastCall().once();
        workClassificationRepository.deleteByWrWrkInst(WR_WRK_INST_2);
        expectLastCall().once();
        replay(workClassificationRepository);
        workClassificationService.deleteClassifications(
            Sets.newHashSet(buildClassification(WR_WRK_INST_1), buildClassification(WR_WRK_INST_2)));
        verify(workClassificationRepository);
    }

    @Test
    public void testGetClassificationCount() {
        Set<String> batchesIds = Collections.singleton(BATCH_ID);
        expect(workClassificationRepository.findCountByBatchIds(batchesIds, SEARCH_VALUE)).andReturn(2).once();
        replay(workClassificationRepository);
        assertEquals(2, workClassificationService.getClassificationCount(batchesIds, SEARCH_VALUE));
        verify(workClassificationRepository);
    }

    @Test
    public void testGetClassificationCountEmptyBatchesIds() {
        Set<String> batchesIds = Collections.emptySet();
        expect(workClassificationRepository.findCountBySearch(SEARCH_VALUE)).andReturn(2).once();
        replay(workClassificationRepository);
        assertEquals(2, workClassificationService.getClassificationCount(batchesIds, SEARCH_VALUE));
        verify(workClassificationRepository);
    }

    @Test
    public void testGetClassifications() {
        Set<String> batchesIds = Collections.singleton(BATCH_ID);
        List<WorkClassification> classifications = Collections.singletonList(new WorkClassification());
        expect(workClassificationRepository.findByBatchIds(batchesIds, SEARCH_VALUE, null, null))
            .andReturn(classifications).once();
        replay(workClassificationRepository);
        assertSame(classifications,
            workClassificationService.getClassifications(batchesIds, SEARCH_VALUE, null, null));
        verify(workClassificationRepository);
    }

    @Test
    public void testGetClassificationsEmptyBatchesIds() {
        Set<String> batchesIds = Collections.emptySet();
        List<WorkClassification> classifications = Collections.singletonList(new WorkClassification());
        expect(workClassificationRepository.findBySearch(SEARCH_VALUE, null, null))
            .andReturn(classifications).once();
        replay(workClassificationRepository);
        assertSame(classifications,
            workClassificationService.getClassifications(batchesIds, SEARCH_VALUE, null, null));
        verify(workClassificationRepository);
    }

    private WorkClassification buildClassification(Long wrWrkInst) {
        WorkClassification classification = new WorkClassification();
        classification.setWrWrkInst(wrWrkInst);
        classification.setClassification(NON_STM);
        return classification;
    }
}
