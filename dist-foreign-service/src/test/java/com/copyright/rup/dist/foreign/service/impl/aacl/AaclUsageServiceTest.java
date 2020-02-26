package com.copyright.rup.dist.foreign.service.impl.aacl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.AaclClassifiedUsage;
import com.copyright.rup.dist.foreign.domain.AaclUsage;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IAaclUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.impl.InconsistentUsageStateException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link AaclUsageService}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 01/23/2020
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({RupContextUtils.class})
public class AaclUsageServiceTest {

    private static final String USER_NAME = "user@copyright.com";
    private static final String USAGE_ID = "d7d15c9f-39f5-4d51-b72b-48a80f7f5388";
    private final AaclUsageService aaclUsageService = new AaclUsageService();
    private IUsageAuditService usageAuditService;
    private IAaclUsageRepository aaclUsageRepository;

    @Rule
    private final ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {
        usageAuditService = createMock(IUsageAuditService.class);
        aaclUsageRepository = createMock(IAaclUsageRepository.class);
        Whitebox.setInternalState(aaclUsageService, usageAuditService);
        Whitebox.setInternalState(aaclUsageService, aaclUsageRepository);
    }

    @Test
    public void testInsertUsages() {
        mockStatic(RupContextUtils.class);
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setName("AACL product family");
        usageBatch.setPaymentDate(LocalDate.of(2019, 6, 30));
        Usage usage = new Usage();
        usage.setProductFamily("AACL");
        usage.setAaclUsage(new AaclUsage());
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        aaclUsageRepository.insert(usage);
        expectLastCall().once();
        usageAuditService.logAction(usage.getId(), UsageActionTypeEnum.LOADED,
            "Uploaded in 'AACL product family' Batch");
        expectLastCall().once();
        replay(aaclUsageRepository, usageAuditService, RupContextUtils.class);
        aaclUsageService.insertUsages(usageBatch, Collections.singleton(usage));
        verify(aaclUsageRepository, usageAuditService, RupContextUtils.class);
    }

    @Test
    public void testUpdateClassifiedUsages() {
        mockStatic(RupContextUtils.class);
        AaclClassifiedUsage usage1 = buildUsage();
        AaclClassifiedUsage usage2 = buildUsage();
        usage2.setPublicationType("disqualified");
        usage2.setDetailId(USAGE_ID);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        aaclUsageRepository.deleteById(USAGE_ID);
        expectLastCall().once();
        usageAuditService.deleteActionsByUsageId(USAGE_ID);
        expectLastCall().once();
        aaclUsageRepository.updateClassifiedUsages(Collections.singletonList(usage1), USER_NAME);
        expectLastCall().once();
        usageAuditService.logAction(usage1.getDetailId(), UsageActionTypeEnum.ELIGIBLE,
            "Usages has become eligible after classification");
        expectLastCall().once();
        replay(aaclUsageRepository, usageAuditService, RupContextUtils.class);
        assertEquals(1, aaclUsageService.updateClassifiedUsages(Arrays.asList(usage1, usage2)));
        verify(aaclUsageRepository, usageAuditService, RupContextUtils.class);
    }

    @Test
    public void testUpdateProcessedUsageProductFamily() {
        Usage usage = new Usage();
        usage.setProductFamily("AACL");
        expect(aaclUsageRepository.updateProcessedUsage(usage))
            .andReturn("fbf3b27f-2031-41a0-812e-111bb668e180").once();
        replay(aaclUsageRepository);
        aaclUsageService.updateProcessedUsage(usage);
        verify(aaclUsageRepository);
    }

    @Test
    public void testUpdateProcessedUsageWrongVersion() {
        Usage usage = new Usage();
        usage.setStatus(UsageStatusEnum.NEW);
        usage.setId("ca62ea7e-4185-4c56-b12b-c53fbad1d6b8");
        usage.setVersion(2);
        exception.expect(InconsistentUsageStateException.class);
        exception.expectMessage("Usage is in inconsistent state. UsageId=ca62ea7e-4185-4c56-b12b-c53fbad1d6b8," +
            " Status=NEW, RecordVersion=2");
        expect(aaclUsageRepository.updateProcessedUsage(usage)).andReturn(null).once();
        replay(aaclUsageRepository);
        aaclUsageService.updateProcessedUsage(usage);
        verify(aaclUsageRepository);
    }

    @Test
    public void testGetUsagesCount() {
        UsageFilter filter = new UsageFilter();
        filter.setFiscalYear(2017);
        expect(aaclUsageRepository.findCountByFilter(filter)).andReturn(1).once();
        replay(aaclUsageRepository);
        aaclUsageService.getUsagesCount(filter);
        verify(aaclUsageRepository);
    }

    @Test
    public void testGetUsageCountEmptyFilter() {
        assertEquals(0, aaclUsageService.getUsagesCount(new UsageFilter()));
    }

    @Test
    public void testGetUsageDtos() {
        List<UsageDto> usagesWithBatch = Collections.singletonList(new UsageDto());
        Pageable pageable = new Pageable(0, 1);
        Sort sort = new Sort("detailId", Sort.Direction.ASC);
        UsageFilter filter = new UsageFilter();
        filter.setFiscalYear(2017);
        expect(aaclUsageRepository.findDtosByFilter(filter, pageable, sort)).andReturn(usagesWithBatch).once();
        replay(aaclUsageRepository);
        List<UsageDto> result = aaclUsageService.getUsageDtos(filter, pageable, sort);
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(aaclUsageRepository);
    }

    @Test
    public void testGetUsagesDtosEmptyFilter() {
        List<UsageDto> result = aaclUsageService.getUsageDtos(new UsageFilter(), null, null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetUsagePeriods() {
        List<Integer> usagePeriods = Collections.singletonList(2020);
        expect(aaclUsageRepository.findUsagePeriods()).andReturn(usagePeriods).once();
        replay(aaclUsageRepository);
        assertEquals(usagePeriods, aaclUsageService.getUsagePeriods());
        verify(aaclUsageRepository);
    }

    @Test
    public void testDeleteById() {
        String usageId = "7adb441e-d709-4f58-8dc0-9264bfac2e19 ";
        usageAuditService.deleteActionsByUsageId(usageId);
        expectLastCall().once();
        aaclUsageRepository.deleteById(usageId);
        expectLastCall().once();
        replay(aaclUsageRepository, usageAuditService);
        aaclUsageService.deleteById(usageId);
        verify(aaclUsageRepository, usageAuditService);
    }

    @Test
    public void testDeleteUsageBatchDetails() {
        usageAuditService.deleteActionsByBatchId("0cfcf687-4005-4b4b-bbe1-612cc1a04f0c");
        expectLastCall().once();
        aaclUsageRepository.deleteByBatchId("0cfcf687-4005-4b4b-bbe1-612cc1a04f0c");
        expectLastCall().once();
        replay(aaclUsageRepository, usageAuditService);
        aaclUsageService.deleteUsageBatchDetails(buildUsageBatch());
        verify(aaclUsageRepository, usageAuditService);
    }

    private UsageBatch buildUsageBatch() {
        UsageBatch batch = new UsageBatch();
        batch.setId("0cfcf687-4005-4b4b-bbe1-612cc1a04f0c");
        batch.setName("AACL Batch");
        return batch;
    }

    private AaclClassifiedUsage buildUsage() {
        AaclClassifiedUsage usage = new AaclClassifiedUsage();
        usage.setDetailId(USAGE_ID);
        usage.setPublicationType("Book");
        usage.setDiscipline("Life Sciences");
        usage.setEnrollmentProfile("EXGP");
        usage.setWrWrkInst(122830308L);
        usage.setComment("Comment");
        return usage;
    }
}
