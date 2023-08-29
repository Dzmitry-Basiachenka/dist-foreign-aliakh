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

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.LinkedHashSet;
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
    private IChainProcessor<Usage> nonBelletristicProcessor;

    @Before
    public void setUp() {
        workClassificationService = new WorkClassificationService();
        workClassificationRepository = createMock(IWorkClassificationRepository.class);
        usageRepository = createMock(IUsageRepository.class);
        ntsUsageRepository = createMock(INtsUsageRepository.class);
        nonBelletristicProcessor = createMock(IChainProcessor.class);
        Whitebox.setInternalState(workClassificationService, workClassificationRepository);
        Whitebox.setInternalState(workClassificationService, usageRepository);
        Whitebox.setInternalState(workClassificationService, ntsUsageRepository);
        Whitebox.setInternalState(workClassificationService, nonBelletristicProcessor);
        Whitebox.setInternalState(workClassificationService, "usagesBatchSize", 100);
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
        Whitebox.setInternalState(workClassificationService, "usagesBatchSize", 1);
        Usage usage1 = new Usage();
        usage1.setId(RupPersistUtils.generateUuid());
        Usage usage2 = new Usage();
        usage2.setId(RupPersistUtils.generateUuid());
        expect(ntsUsageRepository.findUsageIdsForClassificationUpdate())
            .andReturn(List.of(usage1.getId(), usage2.getId())).once();
        expect(usageRepository.findByIds(List.of(usage1.getId()))).andReturn(List.of(usage1)).once();
        nonBelletristicProcessor.process(List.of(usage1));
        expectLastCall().once();
        expect(usageRepository.findByIds(List.of(usage2.getId()))).andReturn(List.of(usage2)).once();
        nonBelletristicProcessor.process(List.of(usage2));
        expectLastCall().once();
        replay(workClassificationRepository, ntsUsageRepository, usageRepository, nonBelletristicProcessor);
        workClassificationService.insertOrUpdateClassifications(
            Set.of(buildClassification(WR_WRK_INST_1), buildClassification(WR_WRK_INST_2)), STM);
        WorkClassification classification1 = classificationCapture1.getValue();
        assertNotNull(classification1.getWrWrkInst());
        assertNotNull(classification1.getUpdateUser());
        assertNotNull(classification1.getUpdateDate());
        assertEquals(STM, classification1.getClassification());
        WorkClassification classification2 = classificationCapture2.getValue();
        assertNotNull(classification2.getWrWrkInst());
        assertEquals(STM, classification2.getClassification());
        verify(workClassificationRepository, ntsUsageRepository, usageRepository, nonBelletristicProcessor);
    }

    @Test
    public void testDeleteClassifications() throws ParseException {
        mockStatic(RupContextUtils.class);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        workClassificationRepository.deleteByWrWrkInst(WR_WRK_INST_1);
        expectLastCall().once();
        workClassificationRepository.deleteByWrWrkInst(WR_WRK_INST_2);
        expectLastCall().once();
        ntsUsageRepository.updateUsagesStatusToUnclassified(List.of(WR_WRK_INST_1, WR_WRK_INST_2), USER_NAME);
        expectLastCall().once();
        replay(RupContextUtils.class, workClassificationRepository, usageRepository);
        HashSet<WorkClassification> classifications = new LinkedHashSet<>();
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
