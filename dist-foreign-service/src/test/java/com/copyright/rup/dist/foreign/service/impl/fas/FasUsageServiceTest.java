package com.copyright.rup.dist.foreign.service.impl.fas;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.ResearchedUsage;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.Work;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.integration.pi.api.IPiIntegrationService;
import com.copyright.rup.dist.foreign.repository.api.IFasUsageRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageArchiveRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.executor.IChainExecutor;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Verifies {@link FasUsageService}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 01/23/2020
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({RupContextUtils.class})
public class FasUsageServiceTest {

    private static final String REASON = "reason";
    private static final String USER_NAME = "SYSTEM";

    private final FasUsageService usageService = new FasUsageService();
    private IUsageRepository usageRepository;
    private IFasUsageRepository fasUsageRepository;
    private IUsageArchiveRepository usageArchiveRepository;
    private IUsageAuditService usageAuditService;
    private IChainExecutor<Usage> chainExecutor;
    private IPiIntegrationService piIntegrationService;
    private Scenario scenario;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        scenario = new Scenario();
        scenario.setId("78179a10-ad9e-432e-8aae-30b91fd14ed1");
        scenario.setCreateUser("user@copyright.com");
        scenario.setProductFamily("FAS");
        usageRepository = createMock(IUsageRepository.class);
        usageArchiveRepository = createMock(IUsageArchiveRepository.class);
        fasUsageRepository = createMock(IFasUsageRepository.class);
        usageAuditService = createMock(IUsageAuditService.class);
        chainExecutor = EasyMock.createMock(IChainExecutor.class);
        piIntegrationService = EasyMock.createMock(IPiIntegrationService.class);
        Whitebox.setInternalState(usageService, usageRepository);
        Whitebox.setInternalState(usageService, usageArchiveRepository);
        Whitebox.setInternalState(usageService, fasUsageRepository);
        Whitebox.setInternalState(usageService, usageAuditService);
        Whitebox.setInternalState(usageService, piIntegrationService);
        Whitebox.setInternalState(usageService, chainExecutor);
    }

    @Test
    public void testGetUsagesCount() {
        UsageFilter filter = new UsageFilter();
        filter.setFiscalYear(2017);
        expect(usageRepository.findCountByFilter(filter)).andReturn(1).once();
        replay(usageRepository);
        usageService.getUsagesCount(filter);
        verify(usageRepository);
    }

    @Test
    public void testGetUsageCountEmptyFilter() {
        assertEquals(0, usageService.getUsagesCount(new UsageFilter()));
    }

    @Test
    public void testGetUsageDtos() {
        List<UsageDto> usagesWithBatch = Collections.singletonList(new UsageDto());
        Pageable pageable = new Pageable(0, 1);
        Sort sort = new Sort("detailId", Sort.Direction.ASC);
        UsageFilter filter = new UsageFilter();
        filter.setFiscalYear(2017);
        expect(usageRepository.findDtosByFilter(filter, pageable, sort)).andReturn(usagesWithBatch).once();
        replay(usageRepository);
        List<UsageDto> result = usageService.getUsageDtos(filter, pageable, sort);
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(usageRepository);
    }

    @Test
    public void testGetUsagesDtosEmptyFilter() {
        List<UsageDto> result = usageService.getUsageDtos(new UsageFilter(), null, null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testDeleteFromScenarioByPayees() {
        Set<String> usageIds = Sets.newHashSet(RupPersistUtils.generateUuid(), RupPersistUtils.generateUuid());
        Set<Long> accountNumbers = Sets.newHashSet(2000017001L, 2000078999L);
        expect(fasUsageRepository.deleteFromScenarioByPayees(scenario.getId(), accountNumbers, USER_NAME))
            .andReturn(usageIds)
            .once();
        usageAuditService.logAction(usageIds, UsageActionTypeEnum.EXCLUDED_FROM_SCENARIO, REASON);
        expectLastCall().once();
        replay(fasUsageRepository, usageAuditService);
        usageService.deleteFromScenarioByPayees(scenario.getId(), accountNumbers, REASON);
        verify(fasUsageRepository, usageAuditService);
    }

    @Test
    public void testRedisignateToNtsWithdrawnByPayees() {
        Set<String> usageIds = Sets.newHashSet(RupPersistUtils.generateUuid(), RupPersistUtils.generateUuid());
        Set<Long> accountNumbers = Sets.newHashSet(2000017001L, 2000078999L);
        expect(fasUsageRepository.redesignateToNtsWithdrawnByPayees(scenario.getId(), accountNumbers, USER_NAME))
            .andReturn(usageIds)
            .once();
        usageAuditService.logAction(usageIds, UsageActionTypeEnum.EXCLUDED_FROM_SCENARIO, REASON);
        expectLastCall().once();
        replay(fasUsageRepository, usageAuditService);
        usageService.redesignateToNtsWithdrawnByPayees(scenario.getId(), accountNumbers, REASON);
        verify(fasUsageRepository, usageAuditService);
    }

    @Test
    public void testLoadResearchedUsages() {
        String usageId1 = "721ca627-09bc-4204-99f4-6acae415fa5d";
        String usageId2 = "9c07f6dd-382e-4cbb-8cd1-ab9f51413e0a";
        ResearchedUsage researchedUsage1 = buildResearchedUsage(usageId1, "Title1", "742354894", 987654321L);
        ResearchedUsage researchedUsage2 = buildResearchedUsage(usageId2, "Title2", "879456165", 876543210L);
        List<ResearchedUsage> researchedUsages = ImmutableList.of(researchedUsage1, researchedUsage2);
        expect(piIntegrationService.findWorkByWrWrkInst(987654321L)).andReturn(buildWork("VALISSN")).once();
        expect(piIntegrationService.findWorkByWrWrkInst(876543210L)).andReturn(new Work()).once();
        fasUsageRepository.updateResearchedUsages(researchedUsages);
        expectLastCall().once();
        usageAuditService.logAction(usageId1, UsageActionTypeEnum.WORK_FOUND,
            "Wr Wrk Inst 987654321 was added based on research");
        expectLastCall().once();
        usageAuditService.logAction(usageId2, UsageActionTypeEnum.WORK_FOUND,
            "Wr Wrk Inst 876543210 was added based on research");
        expectLastCall().once();
        List<Usage> usages = Arrays.asList(buildUsage(usageId1), buildUsage(usageId2));
        expect(usageRepository.findByIds(Arrays.asList(usageId1, usageId2)))
            .andReturn(usages)
            .once();
        chainExecutor.execute(usages, ChainProcessorTypeEnum.RIGHTS);
        expectLastCall().once();
        replay(usageRepository, usageAuditService, chainExecutor, piIntegrationService, fasUsageRepository);
        usageService.loadResearchedUsages(researchedUsages);
        assertEquals("VALISSN", researchedUsage1.getStandardNumberType());
        assertNull(researchedUsage2.getStandardNumberType());
        verify(usageRepository, usageAuditService, chainExecutor, piIntegrationService, fasUsageRepository);
    }

    @Test
    public void testMoveToArchivedFas() {
        mockStatic(RupContextUtils.class);
        List<String> usageIds = Collections.singletonList(RupPersistUtils.generateUuid());
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        expect(usageArchiveRepository.copyToArchiveByScenarioId(scenario.getId(), USER_NAME))
            .andReturn(usageIds).once();
        usageRepository.deleteByScenarioId(scenario.getId());
        expectLastCall().once();
        replay(usageRepository, usageArchiveRepository, RupContextUtils.class);
        usageService.moveToArchive(scenario);
        verify(usageRepository, usageArchiveRepository, RupContextUtils.class);
    }

    private Work buildWork(String standardNumberType) {
        Work work = new Work();
        work.setMainIdnoType(standardNumberType);
        return work;
    }

    private Usage buildUsage(String usageId) {
        Usage usage = new Usage();
        usage.setId(usageId);
        usage.setWrWrkInst(123160519L);
        usage.getRightsholder().setId("0b09fd30-00d4-4b82-987f-2e258daa581c");
        usage.getRightsholder().setAccountNumber(1000001534L);
        usage.setGrossAmount(new BigDecimal("100.00"));
        usage.setNetAmount(new BigDecimal("68.0000000000"));
        usage.setServiceFeeAmount(new BigDecimal("32.0000000000"));
        usage.setServiceFee(new BigDecimal("0.32"));
        usage.setProductFamily("FAS");
        return usage;
    }

    private ResearchedUsage buildResearchedUsage(String usageId, String title, String standardNumber, Long wrWrkInst) {
        ResearchedUsage researchedUsage = new ResearchedUsage();
        researchedUsage.setUsageId(usageId);
        researchedUsage.setSystemTitle(title);
        researchedUsage.setStandardNumber(standardNumber);
        researchedUsage.setWrWrkInst(wrWrkInst);
        return researchedUsage;
    }
}
