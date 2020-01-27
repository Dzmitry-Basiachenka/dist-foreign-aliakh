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
import com.copyright.rup.dist.foreign.domain.AaclUsage;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IAaclUsageRepository;

import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.time.LocalDate;
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
    private final AaclUsageService aaclUsageService = new AaclUsageService();
    private IUsageAuditService usageAuditService;
    private IAaclUsageRepository aaclUsageRepository;

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
        assertEquals(1, aaclUsageService.insertUsages(usageBatch, Collections.singleton(usage)));
        verify(aaclUsageRepository, usageAuditService, RupContextUtils.class);
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
}
