package com.copyright.rup.dist.foreign.service.impl.acl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.AclRightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.AclUsageDto;
import com.copyright.rup.dist.foreign.domain.UsageAge;
import com.copyright.rup.dist.foreign.domain.filter.AclUsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IAclUsageRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IAclUsageService;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Verifies {@link AclUsageService}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/01/2022
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(RupContextUtils.class)
public class AclUsageServiceTest {

    private static final String USER_NAME = "user@copyright.com";
    private static final String ACL_SCENARIO_UID = "8dbe8fa6-9700-4822-9be3-7075ce4ea378";
    private static final String ACL_BATCH_UID = "73408702-2d7c-48c6-ae7e-a62aa3940ee0";
    private static final String ACL_GRANT_SET_UID = "6fbd9c98-c119-448a-b0a7-3795319dce00";

    private IAclUsageService aclUsageService;
    private IAclUsageRepository aclUsageRepository;

    @Before
    public void setUp() {
        aclUsageService = new AclUsageService();
        aclUsageRepository = createMock(IAclUsageRepository.class);
        Whitebox.setInternalState(aclUsageService, aclUsageRepository);
    }

    @Test
    public void testPopulateAclUsages() {
        String usageBatchId = "3e85d243-63a6-4145-9df6-91f6b2cada53";
        Set<Integer> periods = Collections.singleton(202106);
        String userName = "user@copyright.com";
        List<String> usageIds = Collections.singletonList("8b705e49-85fe-4851-a051-08109d159c7d");
        expect(aclUsageRepository.populateAclUsages(usageBatchId, periods, userName)).andReturn(usageIds).once();
        replay(aclUsageRepository);
        assertEquals(usageIds.size(), aclUsageService.populateAclUsages(usageBatchId, periods, userName));
        verify(aclUsageRepository);
    }

    @Test
    public void testUpdateUsages() {
        mockStatic(RupContextUtils.class);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        AclUsageDto aclUsageDto = new AclUsageDto();
        aclUsageRepository.update(aclUsageDto);
        expectLastCall().once();
        replay(aclUsageRepository, RupContextUtils.class);
        aclUsageService.updateUsages(Collections.singleton(aclUsageDto));
        assertEquals(USER_NAME, aclUsageDto.getUpdateUser());
        verify(aclUsageRepository, RupContextUtils.class);
    }

    @Test
    public void testGetCount() {
        AclUsageFilter aclUsageFilter = buildAclUsageFilter();
        expect(aclUsageRepository.findCountByFilter(aclUsageFilter)).andReturn(10).once();
        replay(aclUsageRepository);
        assertEquals(10, aclUsageService.getCount(aclUsageFilter));
        verify(aclUsageRepository);
    }

    @Test
    public void testGetCountEmptyFilter() {
        assertEquals(0, aclUsageService.getCount(new AclUsageFilter()));
    }

    @Test
    public void testGetDtos() {
        List<AclUsageDto> aclUsageDtos = Collections.singletonList(new AclUsageDto());
        Pageable pageable = new Pageable(0, 1);
        Sort sort = new Sort("detailId", Sort.Direction.ASC);
        AclUsageFilter filter = buildAclUsageFilter();
        expect(aclUsageRepository.findDtosByFilter(filter, pageable, sort)).andReturn(aclUsageDtos).once();
        replay(aclUsageRepository);
        assertSame(aclUsageDtos, aclUsageService.getDtos(filter, pageable, sort));
        verify(aclUsageRepository);
    }

    @Test
    public void testGetDtosEmptyFilter() {
        List<AclUsageDto> result = aclUsageService.getDtos(new AclUsageFilter(), null, null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetPeriods() {
        List<Integer> periods = Collections.singletonList(202006);
        expect(aclUsageRepository.findPeriods()).andReturn(periods).once();
        replay(aclUsageRepository);
        assertSame(periods, aclUsageService.getPeriods());
        verify(aclUsageRepository);
    }

    @Test
    public void testUsageExistForLicenseeClassesAndTypeOfUse() {
        String usageBatchId = "ab3602c3-41d7-449b-be4d-e59e28bb46ee";
        String grantSetId = "914fd4af-cd98-4c34-a629-b78b12cf5c7e";
        Set<Integer> licenseeClassIds = Collections.singleton(2);
        expect(aclUsageRepository.usageExistForLicenseeClassesAndTypeOfUse(usageBatchId, grantSetId, licenseeClassIds,
            "PRINT")).andReturn(true).once();
        expect(aclUsageRepository.usageExistForLicenseeClassesAndTypeOfUse(usageBatchId, grantSetId, licenseeClassIds,
            "DIGITAL")).andReturn(false).once();
        replay(aclUsageRepository);
        assertTrue(aclUsageService.usageExistForLicenseeClassesAndTypeOfUse(usageBatchId, grantSetId, licenseeClassIds,
            "PRINT"));
        assertFalse(aclUsageService.usageExistForLicenseeClassesAndTypeOfUse(usageBatchId, grantSetId, licenseeClassIds,
            "DIGITAL"));
        verify(aclUsageRepository);
    }

    @Test
    public void testGetAclRightsholderTotalsHoldersByScenarioId() {
        List<AclRightsholderTotalsHolder> holders = Collections.singletonList(new AclRightsholderTotalsHolder());
        expect(aclUsageRepository.findAclRightsholderTotalsHoldersByScenarioId(ACL_SCENARIO_UID, StringUtils.EMPTY,
            null, null)).andReturn(holders).once();
        replay(aclUsageRepository);
        assertSame(holders, aclUsageService.getAclRightsholderTotalsHoldersByScenarioId(ACL_SCENARIO_UID,
            StringUtils.EMPTY, null, null));
        verify(aclUsageRepository);
    }

    @Test
    public void testGetAclRightsholderTotalsHolderCountByScenarioId() {
        expect(aclUsageRepository.findAclRightsholderTotalsHolderCountByScenarioId(ACL_SCENARIO_UID, StringUtils.EMPTY))
            .andReturn(5).once();
        replay(aclUsageRepository);
        assertEquals(5, aclUsageService.getAclRightsholderTotalsHolderCountByScenarioId(ACL_SCENARIO_UID,
            StringUtils.EMPTY));
        verify(aclUsageRepository);
    }

    @Test
    public void testGetDefaultUsageAgesWeights() {
        UsageAge usageAge = buildUsageAge(2019, new BigDecimal("1.00"));
        expect(aclUsageRepository.findDefaultUsageAgesWeights()).andReturn(Collections.singletonList(usageAge)).once();
        replay(aclUsageRepository);
        assertSame(usageAge, aclUsageService.getDefaultUsageAgesWeights().get(0));
        verify(aclUsageRepository);
    }

    @Test
    public void testGetCountWithNullPubTypeOrContentUnitPriceByBatchId() {
        expect(aclUsageRepository.findCountInvalidUsages(ACL_BATCH_UID, ACL_GRANT_SET_UID, 202212,
            Collections.singletonList(0))).andReturn(0).once();
        replay(aclUsageRepository);
        assertEquals(0, aclUsageService.getCountInvalidUsages(
            ACL_BATCH_UID, ACL_GRANT_SET_UID, 202212, Collections.singletonList(0)));
        verify(aclUsageRepository);
    }

    private AclUsageFilter buildAclUsageFilter() {
        AclUsageFilter aclUsageFilter = new AclUsageFilter();
        aclUsageFilter.setUsageBatchName("ACL Usage Batch 2021");
        return aclUsageFilter;
    }

    private UsageAge buildUsageAge(Integer period, BigDecimal weight) {
        UsageAge usageAge = new UsageAge();
        usageAge.setPeriod(period);
        usageAge.setWeight(weight);
        return usageAge;
    }
}
