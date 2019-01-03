package com.copyright.rup.dist.foreign.service.impl.rights;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.service.api.discrepancy.IRmsGrantProcessorService;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.integration.rms.api.IRmsIntegrationService;
import com.copyright.rup.dist.foreign.integration.rms.api.RightsAssignmentResult;
import com.copyright.rup.dist.foreign.integration.rms.api.RightsAssignmentResult.RightsAssignmentResultStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.ChainProcessorTypeEnum;
import com.copyright.rup.dist.foreign.service.api.IChainExecutor;
import com.copyright.rup.dist.foreign.service.api.IRightsService;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.impl.common.CommonUsageProducer;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Verifies {@link RightsService}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 03/22/2018
 *
 * @author Nikita Levyankov
 */
public class RightsServiceTest {

    private static final String USAGE_ID_1 = "Usage id 1";
    private static final Long RH_ACCOUNT_NUMBER = 1000001534L;
    private static final String RH_ID = RupPersistUtils.generateUuid();
    private static final String FAS_PRODUCT_FAMILY = "FAS";
    private static final String NTS_PRODUCT_FAMILY = "NTS";
    private IRightsService rightsService;
    private IUsageRepository usageRepository;
    private IUsageAuditService usageAuditService;
    private IRmsIntegrationService rmsIntegrationService;
    private IRmsGrantProcessorService rmsGrantProcessorService;
    private IRightsholderService rightsholderService;
    private CommonUsageProducer rightsProducer;
    private IUsageService usageService;
    private IChainExecutor<Usage> chainExecutor;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        usageRepository = createMock(IUsageRepository.class);
        usageAuditService = createMock(IUsageAuditService.class);
        rmsIntegrationService = createMock(IRmsIntegrationService.class);
        rmsGrantProcessorService = createMock(IRmsGrantProcessorService.class);
        rightsholderService = createMock(IRightsholderService.class);
        rightsProducer = createMock(CommonUsageProducer.class);
        usageService = createMock(IUsageService.class);
        chainExecutor = createMock(IChainExecutor.class);
        rightsService = new RightsService();
        Whitebox.setInternalState(rightsService, "usageRepository", usageRepository);
        Whitebox.setInternalState(rightsService, "auditService", usageAuditService);
        Whitebox.setInternalState(rightsService, "rmsIntegrationService", rmsIntegrationService);
        Whitebox.setInternalState(rightsService, "rmsGrantProcessorService", rmsGrantProcessorService);
        Whitebox.setInternalState(rightsService, "rightsholderService", rightsholderService);
        Whitebox.setInternalState(rightsService, "rightsProducer", rightsProducer);
        Whitebox.setInternalState(rightsService, "usageService", usageService);
        Whitebox.setInternalState(rightsService, "chainExecutor", chainExecutor);
    }

    @Test
    public void testSendForRightsAssignment() {
        RightsAssignmentResult result = new RightsAssignmentResult(RightsAssignmentResultStatusEnum.SUCCESS);
        result.setJobId("b5015e54-c38a-4fc8-b889-c644640085a4");
        expect(usageRepository.findByStatuses(UsageStatusEnum.RH_NOT_FOUND))
            .andReturn(Arrays.asList(buildUsage(USAGE_ID_1, FAS_PRODUCT_FAMILY, UsageStatusEnum.RH_NOT_FOUND)))
            .once();
        expect(rmsIntegrationService.sendForRightsAssignment(Sets.newHashSet(123160519L)))
            .andReturn(result).once();
        Set<String> usageIds = new HashSet<>();
        Collections.addAll(usageIds, USAGE_ID_1);
        usageRepository.updateStatus(usageIds, UsageStatusEnum.SENT_FOR_RA);
        expectLastCall().once();
        usageAuditService.logAction(usageIds, UsageActionTypeEnum.SENT_FOR_RA,
            "Sent for RA: job id 'b5015e54-c38a-4fc8-b889-c644640085a4'");
        replay(usageRepository, usageAuditService, rmsIntegrationService);
        rightsService.sendForRightsAssignment();
        verify(usageRepository, usageAuditService, rmsIntegrationService);
    }

    @Test
    public void testSendForRightsAssignmentRaError() {
        expect(usageRepository.findByStatuses(UsageStatusEnum.RH_NOT_FOUND))
            .andReturn(Arrays.asList(buildUsage(USAGE_ID_1, FAS_PRODUCT_FAMILY, UsageStatusEnum.RH_NOT_FOUND)))
            .once();
        expect(rmsIntegrationService.sendForRightsAssignment(Sets.newHashSet(123160519L)))
            .andReturn(new RightsAssignmentResult(RightsAssignmentResultStatusEnum.RA_ERROR)).once();
        replay(usageRepository, usageAuditService, rmsIntegrationService);
        rightsService.sendForRightsAssignment();
        verify(usageRepository, usageAuditService, rmsIntegrationService);
    }

    @Test
    public void testSendForRightsAssignmentNoUsagesForRa() {
        expect(usageRepository.findByStatuses(UsageStatusEnum.RH_NOT_FOUND))
            .andReturn(Collections.emptyList()).once();
        replay(usageRepository, usageAuditService, rmsIntegrationService);
        rightsService.sendForRightsAssignment();
        verify(usageRepository, usageAuditService, rmsIntegrationService);
    }

    @Test
    public void testUpdateRights() {
        Usage workFoundUsage =
            buildUsage(RupPersistUtils.generateUuid(), FAS_PRODUCT_FAMILY, UsageStatusEnum.WORK_FOUND);
        Usage sentForRaUsage =
            buildUsage(RupPersistUtils.generateUuid(), FAS_PRODUCT_FAMILY, UsageStatusEnum.SENT_FOR_RA);
        Map<Long, Long> wrWrkInstToRhAccountNumberMap = Maps.newHashMap();
        wrWrkInstToRhAccountNumberMap.put(123160519L, RH_ACCOUNT_NUMBER);
        expect(usageService.getUsagesByStatusAndProductFamily(UsageStatusEnum.WORK_FOUND, FAS_PRODUCT_FAMILY))
            .andReturn(Collections.singletonList(workFoundUsage)).once();
        rightsProducer.send(workFoundUsage);
        expectLastCall().once();
        expect(usageService.getUsagesByStatusAndProductFamily(UsageStatusEnum.SENT_FOR_RA, FAS_PRODUCT_FAMILY))
            .andReturn(Collections.singletonList(sentForRaUsage)).once();
        expect(rmsGrantProcessorService.getAccountNumbersByWrWrkInsts(Collections.singletonList(123160519L),
            FAS_PRODUCT_FAMILY)).andReturn(wrWrkInstToRhAccountNumberMap).once();
        usageRepository.updateStatusAndRhAccountNumber(Collections.singleton(sentForRaUsage.getId()),
            UsageStatusEnum.RH_FOUND, RH_ACCOUNT_NUMBER);
        expectLastCall().once();
        usageAuditService.logAction(Collections.singleton(sentForRaUsage.getId()), UsageActionTypeEnum.RH_FOUND,
            String.format("Rightsholder account %s was found in RMS", RH_ACCOUNT_NUMBER));
        expectLastCall().once();
        rightsholderService.updateRightsholders(Collections.singleton(RH_ACCOUNT_NUMBER));
        expectLastCall().once();
        chainExecutor.execute(Collections.singletonList(sentForRaUsage), ChainProcessorTypeEnum.ELIGIBILITY);
        expectLastCall().once();
        replay(usageRepository, usageService, rmsGrantProcessorService, usageAuditService, rightsholderService,
            rightsProducer, chainExecutor);
        rightsService.updateRights(FAS_PRODUCT_FAMILY);
        verify(usageRepository, usageService, rmsGrantProcessorService, usageAuditService, rightsholderService,
            rightsProducer, chainExecutor);
    }

    @Test
    public void testUpdateRight() {
        expect(rmsGrantProcessorService.getAccountNumbersByWrWrkInsts(Collections.singletonList(123160519L),
            FAS_PRODUCT_FAMILY)).andReturn(ImmutableMap.of(123160519L, 1000009522L)).once();
        String usageId = RupPersistUtils.generateUuid();
        Set<String> usageIdsSet = Collections.singleton(usageId);
        usageRepository.updateStatusAndRhAccountNumber(usageIdsSet, UsageStatusEnum.RH_FOUND, 1000009522L);
        expectLastCall().once();
        usageAuditService.logAction(usageIdsSet, UsageActionTypeEnum.RH_FOUND,
            "Rightsholder account 1000009522 was found in RMS");
        expectLastCall().once();
        rightsholderService.updateRightsholders(Collections.singleton(1000009522L));
        expectLastCall().once();
        replay(rmsGrantProcessorService, rightsholderService, usageRepository, usageAuditService);
        rightsService.updateRight(buildUsage(usageId, FAS_PRODUCT_FAMILY, UsageStatusEnum.WORK_FOUND));
        verify(rmsGrantProcessorService, rightsholderService, usageRepository, usageAuditService);
    }

    @Test
    public void testUpdateRightWithNtsUsage() {
        String usageId = RupPersistUtils.generateUuid();
        Usage usage = buildUsage(usageId, NTS_PRODUCT_FAMILY, UsageStatusEnum.WORK_FOUND);
        usage.getRightsholder().setAccountNumber(null);
        expect(rmsGrantProcessorService.getAccountNumbersByWrWrkInsts(Collections.singletonList(123160519L),
            NTS_PRODUCT_FAMILY)).andReturn(ImmutableMap.of(123160519L, 1000009522L)).once();
        Set<String> usageIdsSet = Collections.singleton(usageId);
        usageRepository.updateStatusAndRhAccountNumber(usageIdsSet, UsageStatusEnum.RH_FOUND, 1000009522L);
        expectLastCall().once();
        usageAuditService.logAction(usageIdsSet, UsageActionTypeEnum.RH_FOUND,
            "Rightsholder account 1000009522 was found in RMS");
        expectLastCall().once();
        rightsholderService.updateRightsholders(Collections.singleton(1000009522L));
        expectLastCall().once();
        replay(rmsGrantProcessorService, rightsholderService, usageRepository, usageAuditService);
        assertNull(usage.getRightsholder().getAccountNumber());
        rightsService.updateRight(usage);
        assertEquals(1000009522L, usage.getRightsholder().getAccountNumber(), 0);
        verify(rmsGrantProcessorService, rightsholderService, usageRepository, usageAuditService);
    }

    @Test
    public void testUpdateRightWithNotFound() {
        String usageId = RupPersistUtils.generateUuid();
        Set<String> usageIdsSet = Collections.singleton(usageId);
        expect(rmsGrantProcessorService.getAccountNumbersByWrWrkInsts(Collections.singletonList(123160519L),
            FAS_PRODUCT_FAMILY)).andReturn(Collections.EMPTY_MAP).once();
        usageRepository.updateStatus(usageIdsSet, UsageStatusEnum.RH_NOT_FOUND);
        expectLastCall().once();
        usageAuditService.logAction(usageIdsSet, UsageActionTypeEnum.RH_NOT_FOUND,
            "Rightsholder account for 123160519 was not found in RMS");
        expectLastCall().once();
        replay(rmsGrantProcessorService, rightsholderService, usageRepository, usageAuditService);
        rightsService.updateRight(buildUsage(usageId, FAS_PRODUCT_FAMILY, UsageStatusEnum.WORK_FOUND));
        verify(rmsGrantProcessorService, rightsholderService, usageRepository, usageAuditService);
    }

    @Test
    public void testUpdateRightWithNtsAndNotFound() {
        String usageId = RupPersistUtils.generateUuid();
        expect(rmsGrantProcessorService.getAccountNumbersByWrWrkInsts(Collections.singletonList(123160519L),
            NTS_PRODUCT_FAMILY)).andReturn(Collections.EMPTY_MAP).once();
        usageService.deleteById(usageId);
        expectLastCall().once();
        replay(rmsGrantProcessorService, usageService);
        rightsService.updateRight(buildUsage(usageId, NTS_PRODUCT_FAMILY, UsageStatusEnum.WORK_FOUND));
        verify(rmsGrantProcessorService, usageService);
    }

    private Usage buildUsage(String usageId, String productFamily, UsageStatusEnum status) {
        Usage usage = new Usage();
        usage.setId(usageId);
        usage.setWrWrkInst(123160519L);
        usage.getRightsholder().setId(RH_ID);
        usage.getRightsholder().setAccountNumber(RH_ACCOUNT_NUMBER);
        usage.setGrossAmount(new BigDecimal("100.00"));
        usage.setNetAmount(new BigDecimal("68.0000000000"));
        usage.setServiceFeeAmount(new BigDecimal("32.0000000000"));
        usage.setServiceFee(new BigDecimal("0.32"));
        usage.setProductFamily(productFamily);
        usage.setStatus(status);
        return usage;
    }
}
