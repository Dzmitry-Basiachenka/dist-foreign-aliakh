package com.copyright.rup.dist.foreign.service.impl.rights;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.service.api.discrepancy.IRmsGrantsProcessorService;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.integration.rms.api.IRmsIntegrationService;
import com.copyright.rup.dist.foreign.integration.rms.api.RightsAssignmentResult;
import com.copyright.rup.dist.foreign.integration.rms.api.RightsAssignmentResult.RightsAssignmentResultStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IRightsService;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
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
    private IRightsService rightsAssignmentService;
    private IUsageRepository usageRepository;
    private IUsageAuditService usageAuditService;
    private IRmsIntegrationService rmsIntegrationService;
    private IRmsGrantsProcessorService rmsGrantsProcessorService;
    private IRightsholderService rightsholderService;
    private RightsProducer rightsProducer;

    @Before
    public void setUp() {
        usageRepository = createMock(IUsageRepository.class);
        usageAuditService = createMock(IUsageAuditService.class);
        rmsIntegrationService = createMock(IRmsIntegrationService.class);
        rmsGrantsProcessorService = createMock(IRmsGrantsProcessorService.class);
        rightsholderService = createMock(IRightsholderService.class);
        rightsProducer = createMock(RightsProducer.class);
        rightsAssignmentService = new RightsService();
        Whitebox.setInternalState(rightsAssignmentService, usageRepository);
        Whitebox.setInternalState(rightsAssignmentService, usageAuditService);
        Whitebox.setInternalState(rightsAssignmentService, rmsIntegrationService);
        Whitebox.setInternalState(rightsAssignmentService, rmsGrantsProcessorService);
        Whitebox.setInternalState(rightsAssignmentService, rightsholderService);
        Whitebox.setInternalState(rightsAssignmentService, rightsProducer);
    }

    @Test
    public void testSendForRightsAssignment() {
        RightsAssignmentResult result = new RightsAssignmentResult(RightsAssignmentResultStatusEnum.SUCCESS);
        result.setJobId("b5015e54-c38a-4fc8-b889-c644640085a4");
        expect(usageRepository.findByStatuses(UsageStatusEnum.RH_NOT_FOUND))
            .andReturn(Lists.newArrayList(buildUsage(USAGE_ID_1, UsageStatusEnum.RH_NOT_FOUND))).once();
        expect(rmsIntegrationService.sendForRightsAssignment(Sets.newHashSet(123160519L)))
            .andReturn(result).once();
        Set<String> usageIds = new HashSet<>();
        Collections.addAll(usageIds, USAGE_ID_1);
        usageRepository.updateStatus(usageIds, UsageStatusEnum.SENT_FOR_RA);
        expectLastCall().once();
        usageAuditService.logAction(usageIds, UsageActionTypeEnum.SENT_FOR_RA,
            "Sent for RA: job id 'b5015e54-c38a-4fc8-b889-c644640085a4'");
        replay(usageRepository, usageAuditService, rmsIntegrationService);
        rightsAssignmentService.sendForRightsAssignment();
        verify(usageRepository, usageAuditService, rmsIntegrationService);
    }

    @Test
    public void testSendForRightsAssignmentRaError() {
        expect(usageRepository.findByStatuses(UsageStatusEnum.RH_NOT_FOUND))
            .andReturn(Lists.newArrayList(buildUsage(USAGE_ID_1, UsageStatusEnum.RH_NOT_FOUND))).once();
        expect(rmsIntegrationService.sendForRightsAssignment(Sets.newHashSet(123160519L)))
            .andReturn(new RightsAssignmentResult(RightsAssignmentResultStatusEnum.RA_ERROR)).once();
        replay(usageRepository, usageAuditService, rmsIntegrationService);
        rightsAssignmentService.sendForRightsAssignment();
        verify(usageRepository, usageAuditService, rmsIntegrationService);
    }

    @Test
    public void testSendForRightsAssignmentNoUsagesForRa() {
        expect(usageRepository.findByStatuses(UsageStatusEnum.RH_NOT_FOUND))
            .andReturn(Collections.emptyList()).once();
        replay(usageRepository, usageAuditService, rmsIntegrationService);
        rightsAssignmentService.sendForRightsAssignment();
        verify(usageRepository, usageAuditService, rmsIntegrationService);
    }

    @Test
    public void testUpdateRights() {
        Usage workFoundUsage = buildUsage(RupPersistUtils.generateUuid(), UsageStatusEnum.WORK_FOUND);
        Usage sentForRaUsage = buildUsage(RupPersistUtils.generateUuid(), UsageStatusEnum.SENT_FOR_RA);
        Map<Long, Long> wrWrkInstToRhAccountNumberMap = Maps.newHashMap();
        wrWrkInstToRhAccountNumberMap.put(123160519L, RH_ACCOUNT_NUMBER);
        expect(usageRepository.findByStatuses(UsageStatusEnum.WORK_FOUND))
            .andReturn(Collections.singletonList(workFoundUsage)).once();
        rightsProducer.send(workFoundUsage);
        expectLastCall().once();
        expect(usageRepository.findByStatuses(UsageStatusEnum.SENT_FOR_RA))
            .andReturn(Collections.singletonList(sentForRaUsage)).once();
        expect(rmsGrantsProcessorService.getAccountNumbersByWrWrkInsts(Collections.singletonList(123160519L)))
            .andReturn(wrWrkInstToRhAccountNumberMap).once();
        usageRepository.updateStatusAndRhAccountNumber(Collections.singleton(sentForRaUsage.getId()),
            UsageStatusEnum.ELIGIBLE, RH_ACCOUNT_NUMBER);
        expectLastCall().once();
        usageAuditService.logAction(Collections.singleton(sentForRaUsage.getId()), UsageActionTypeEnum.RH_FOUND,
            String.format("Rightsholder account %s was found in RMS", RH_ACCOUNT_NUMBER));
        expectLastCall().once();
        rightsholderService.updateRightsholders(Collections.singleton(RH_ACCOUNT_NUMBER));
        expectLastCall().once();
        replay(usageRepository, rmsGrantsProcessorService, usageAuditService, rightsholderService, rightsProducer);
        rightsAssignmentService.updateRights();
        verify(usageRepository, rmsGrantsProcessorService, usageAuditService, rightsholderService, rightsProducer);
    }

    private Usage buildUsage(String usageId, UsageStatusEnum status) {
        Usage usage = new Usage();
        usage.setId(usageId);
        usage.setWrWrkInst(123160519L);
        usage.getRightsholder().setId(RH_ID);
        usage.getRightsholder().setAccountNumber(RH_ACCOUNT_NUMBER);
        usage.setGrossAmount(new BigDecimal("100.00"));
        usage.setNetAmount(new BigDecimal("68.0000000000"));
        usage.setServiceFeeAmount(new BigDecimal("32.0000000000"));
        usage.setServiceFee(new BigDecimal("0.32"));
        usage.setProductFamily("FAS");
        usage.setStatus(status);
        return usage;
    }
}
