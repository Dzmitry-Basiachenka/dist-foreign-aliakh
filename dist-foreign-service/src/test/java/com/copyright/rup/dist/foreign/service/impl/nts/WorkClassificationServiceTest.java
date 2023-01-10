package com.copyright.rup.dist.foreign.service.impl.nts;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.newCapture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.WorkClassification;
import com.copyright.rup.dist.foreign.repository.api.INtsUsageRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.repository.api.IWorkClassificationRepository;
import com.copyright.rup.dist.foreign.service.api.processor.IChainProcessor;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
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
@RunWith(PowerMockRunner.class)
@PrepareForTest(RupContextUtils.class)
public class WorkClassificationServiceTest {

    private static final String NON_STM = "NON-STM";
    private static final String STM = "STM";
    private static final String BATCH_ID = RupPersistUtils.generateUuid();
    private static final String SEARCH_VALUE = "test";
    private static final Long WR_WRK_INST_1 = 111111111L;
    private static final Long WR_WRK_INST_2 = 222222222L;
    private static final String USER_NAME = "user@copyright.com";

    private WorkClassificationService workClassificationService;
    private IWorkClassificationRepository workClassificationRepository;
    private IUsageRepository usageRepository;
    private INtsUsageRepository ntsUsageRepository;
    private IChainProcessor<Usage> nonBelletristicProcessorMock;

    @Before
    public void setUp() {
        workClassificationService = new WorkClassificationService();
        workClassificationRepository = createMock(IWorkClassificationRepository.class);
        usageRepository = createMock(IUsageRepository.class);
        ntsUsageRepository = createMock(INtsUsageRepository.class);
        nonBelletristicProcessorMock = createMock(IChainProcessor.class);
        workClassificationService.setWorkClassificationRepository(workClassificationRepository);
        workClassificationService.setUsageRepository(usageRepository);
        workClassificationService.setNonBelletristicProcessor(nonBelletristicProcessorMock);
        Whitebox.setInternalState(workClassificationService, "usagesBatchSize", 100);
        Whitebox.setInternalState(workClassificationService, "ntsUsageRepository", ntsUsageRepository);
    }

    @Test
    public void testGetClassification() {
        expect(workClassificationRepository.findClassificationByWrWrkInst(WR_WRK_INST_1)).andReturn(NON_STM).once();
        replay(workClassificationRepository);
        assertEquals(NON_STM, workClassificationService.getClassification(WR_WRK_INST_1));
        verify(workClassificationRepository);
    }

    @Test
    public void testInsertOrUpdateClassifications() throws ParseException {
        Capture<WorkClassification> classificationCapture1 = newCapture();
        Capture<WorkClassification> classificationCapture2 = newCapture();
        workClassificationRepository.insertOrUpdate(capture(classificationCapture1));
        expectLastCall().once();
        workClassificationRepository.insertOrUpdate(capture(classificationCapture2));
        expectLastCall().once();
        workClassificationService.setUsagesBatchSize(1);
        Usage usage1 = new Usage();
        usage1.setId(RupPersistUtils.generateUuid());
        Usage usage2 = new Usage();
        usage2.setId(RupPersistUtils.generateUuid());
        expect(ntsUsageRepository.findUsageIdsForClassificationUpdate())
            .andReturn(Arrays.asList(usage1.getId(), usage2.getId())).once();
        expect(usageRepository.findByIds(List.of(usage1.getId()))).andReturn(List.of(usage1)).once();
        nonBelletristicProcessorMock.process(List.of(usage1));
        expectLastCall().once();
        expect(usageRepository.findByIds(List.of(usage2.getId()))).andReturn(List.of(usage2)).once();
        nonBelletristicProcessorMock.process(List.of(usage2));
        expectLastCall().once();
        replay(workClassificationRepository, ntsUsageRepository, usageRepository, nonBelletristicProcessorMock);
        workClassificationService.insertOrUpdateClassifications(
            Sets.newHashSet(buildClassification(WR_WRK_INST_1), buildClassification(WR_WRK_INST_2)), STM);
        WorkClassification classification1 = classificationCapture1.getValue();
        assertNotNull(classification1.getWrWrkInst());
        assertNotNull(classification1.getUpdateUser());
        assertNotNull(classification1.getUpdateDate());
        assertEquals(STM, classification1.getClassification());
        WorkClassification classification2 = classificationCapture2.getValue();
        assertNotNull(classification2.getWrWrkInst());
        assertEquals(STM, classification2.getClassification());
        verify(workClassificationRepository, ntsUsageRepository, usageRepository, nonBelletristicProcessorMock);
    }

    @Test
    public void testDeleteClassifications() throws ParseException {
        mockStatic(RupContextUtils.class);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        workClassificationRepository.deleteByWrWrkInst(WR_WRK_INST_1);
        expectLastCall().once();
        workClassificationRepository.deleteByWrWrkInst(WR_WRK_INST_2);
        expectLastCall().once();
        ntsUsageRepository.updateUsagesStatusToUnclassified(Lists.newArrayList(WR_WRK_INST_1, WR_WRK_INST_2),
            USER_NAME);
        expectLastCall().once();
        replay(RupContextUtils.class, workClassificationRepository, usageRepository);
        HashSet<WorkClassification> classifications = Sets.newLinkedHashSet();
        classifications.add(buildClassification(WR_WRK_INST_1));
        classifications.add(buildClassification(WR_WRK_INST_2));
        workClassificationService.deleteClassifications(classifications);
        verify(RupContextUtils.class, workClassificationRepository, usageRepository);
    }

    @Test
    public void testGetClassificationCount() {
        Set<String> batchesIds = Set.of(BATCH_ID);
        expect(workClassificationRepository.findCountByBatchIds(batchesIds, SEARCH_VALUE)).andReturn(2).once();
        replay(workClassificationRepository);
        assertEquals(2, workClassificationService.getClassificationCount(batchesIds, SEARCH_VALUE));
        verify(workClassificationRepository);
    }

    @Test
    public void testGetClassificationCountEmptyBatchesIds() {
        Set<String> batchesIds = Set.of();
        expect(workClassificationRepository.findCountBySearch(SEARCH_VALUE)).andReturn(2).once();
        replay(workClassificationRepository);
        assertEquals(2, workClassificationService.getClassificationCount(batchesIds, SEARCH_VALUE));
        verify(workClassificationRepository);
    }

    @Test
    public void testGetClassifications() {
        Set<String> batchesIds = Set.of(BATCH_ID);
        List<WorkClassification> classifications = List.of(new WorkClassification());
        expect(workClassificationRepository.findByBatchIds(batchesIds, SEARCH_VALUE, null, null))
            .andReturn(classifications).once();
        replay(workClassificationRepository);
        assertSame(classifications,
            workClassificationService.getClassifications(batchesIds, SEARCH_VALUE, null, null));
        verify(workClassificationRepository);
    }

    @Test
    public void testGetClassificationsEmptyBatchesIds() {
        Set<String> batchesIds = Set.of();
        List<WorkClassification> classifications = List.of(new WorkClassification());
        expect(workClassificationRepository.findBySearch(SEARCH_VALUE, null, null))
            .andReturn(classifications).once();
        replay(workClassificationRepository);
        assertSame(classifications,
            workClassificationService.getClassifications(batchesIds, SEARCH_VALUE, null, null));
        verify(workClassificationRepository);
    }

    private WorkClassification buildClassification(Long wrWrkInst) throws ParseException {
        WorkClassification classification = new WorkClassification();
        classification.setWrWrkInst(wrWrkInst);
        classification.setClassification(NON_STM);
        classification.setUpdateDate(new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse("2019-02-01"));
        classification.setUpdateUser("user@copyright.com");
        return classification;
    }
}
