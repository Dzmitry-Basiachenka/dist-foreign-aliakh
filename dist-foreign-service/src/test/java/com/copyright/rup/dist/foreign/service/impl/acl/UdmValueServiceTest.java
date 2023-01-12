package com.copyright.rup.dist.foreign.service.impl.acl;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.Currency;
import com.copyright.rup.dist.foreign.domain.UdmValue;
import com.copyright.rup.dist.foreign.domain.UdmValueActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UdmValueDto;
import com.copyright.rup.dist.foreign.domain.UdmValueStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmValueFilter;
import com.copyright.rup.dist.foreign.repository.api.IUdmBaselineRepository;
import com.copyright.rup.dist.foreign.repository.api.IUdmValueRepository;
import com.copyright.rup.dist.foreign.service.api.IRightsService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmValueAuditService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmValueService;

import com.google.common.collect.ImmutableMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Verifies {@link UdmValueService}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/23/21
 *
 * @author Anton Azarenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({RupContextUtils.class, RupPersistUtils.class})
public class UdmValueServiceTest {

    private static final String USER_NAME = "user@copyright.com";
    private static final String ASSIGNEE = "wjohn@copyright.com";
    private static final String UDM_VALUE_UID_1 = "15c8a2a6-1e9d-453b-947c-14dbd92c5e15";
    private static final String UDM_VALUE_UID_2 = "29207059-af0a-440a-abc7-b6e016c64677";
    private static final String UDM_VALUE_UID_3 = "33228700-ed0d-44ae-a3b7-2f1bb3440f8e";

    private IUdmValueRepository udmValueRepository;
    private IUdmValueAuditService udmValueAuditService;
    private IUdmBaselineRepository udmBaselineRepository;
    private IRightsService rightsService;
    private IUdmValueService udmValueService;

    @Before
    public void setUp() {
        udmValueRepository = createMock(IUdmValueRepository.class);
        udmValueAuditService = createMock(IUdmValueAuditService.class);
        udmBaselineRepository = createMock(IUdmBaselineRepository.class);
        rightsService = createMock(IRightsService.class);
        udmValueService = new UdmValueService();
        Whitebox.setInternalState(udmValueService, "currencyCodesToCurrencyNamesMap",
            ImmutableMap.of("USD", "US Dollar", "EUR", "Euro"));
        Whitebox.setInternalState(udmValueService, udmValueRepository);
        Whitebox.setInternalState(udmValueService, udmValueAuditService);
        Whitebox.setInternalState(udmValueService, udmBaselineRepository);
        Whitebox.setInternalState(udmValueService, rightsService);
    }

    @Test
    public void testUpdateValue() {
        mockStatic(RupContextUtils.class);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        UdmValueDto udmValueDto = new UdmValueDto();
        udmValueDto.setId(UDM_VALUE_UID_1);
        udmValueDto.setPriceComment("old price comment");
        udmValueDto.setContentComment("old content comment");
        List<String> actionReasons = List.of(
            "The field 'Price Comment' was edited. Old Value is 'old price comment'. New Value is 'new price comment'",
            "The field 'Content Comment' was edited. Old Value is 'old content comment'. New Value is not specified",
            "The field 'Comment' was edited. Old Value is not specified. New Value is 'new comment'");
        udmValueRepository.update(udmValueDto);
        expectLastCall().once();
        udmValueAuditService.logAction(UDM_VALUE_UID_1, UdmValueActionTypeEnum.VALUE_EDIT,
            "The field 'Price Comment' was edited. Old Value is 'old price comment'. New Value is 'new price comment'");
        expectLastCall().once();
        udmValueAuditService.logAction(UDM_VALUE_UID_1, UdmValueActionTypeEnum.VALUE_EDIT,
            "The field 'Content Comment' was edited. Old Value is 'old content comment'. New Value is not specified");
        expectLastCall().once();
        udmValueAuditService.logAction(UDM_VALUE_UID_1, UdmValueActionTypeEnum.VALUE_EDIT,
            "The field 'Comment' was edited. Old Value is not specified. New Value is 'new comment'");
        expectLastCall().once();
        replay(udmValueRepository, udmValueAuditService, RupContextUtils.class);
        udmValueService.updateValue(udmValueDto, actionReasons);
        assertEquals(USER_NAME, udmValueDto.getUpdateUser());
        verify(udmValueRepository, udmValueAuditService, RupContextUtils.class);
    }

    @Test
    public void testGetAllCurrencies() {
        assertEquals(List.of(new Currency("USD", "US Dollar"), new Currency("EUR", "Euro")),
            udmValueService.getAllCurrencies());
    }

    @Test
    public void testGetPeriods() {
        List<Integer> periods = List.of(202006, 202112);
        expect(udmValueRepository.findPeriods()).andReturn(periods).once();
        replay(udmValueRepository);
        assertEquals(periods, udmValueService.getPeriods());
        verify(udmValueRepository);
    }

    @Test
    public void testGetValueDtos() {
        List<UdmValueDto> udmValues = List.of(new UdmValueDto());
        Pageable pageable = new Pageable(0, 1);
        Sort sort = new Sort("valueId", Sort.Direction.ASC);
        UdmValueFilter filter = new UdmValueFilter();
        filter.setStatus(UdmValueStatusEnum.NEW);
        expect(udmValueRepository.findDtosByFilter(filter, pageable, sort)).andReturn(udmValues).once();
        replay(udmValueRepository);
        assertSame(udmValues, udmValueService.getValueDtos(filter, pageable, sort));
        verify(udmValueRepository);
    }

    @Test
    public void testGetValueDtosEmptyFilter() {
        List<UdmValueDto> result = udmValueService.getValueDtos(new UdmValueFilter(), null, null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetValueCount() {
        UdmValueFilter filter = new UdmValueFilter();
        filter.setStatus(UdmValueStatusEnum.NEW);
        expect(udmValueRepository.findCountByFilter(filter)).andReturn(1).once();
        replay(udmValueRepository);
        assertEquals(1, udmValueService.getValueCount(filter));
        verify(udmValueRepository);
    }

    @Test
    public void testGetValueCountEmptyFilter() {
        assertEquals(0, udmValueService.getValueCount(new UdmValueFilter()));
    }

    @Test
    public void testAssignValues() {
        mockStatic(RupContextUtils.class);
        UdmValueDto udmValue1 = new UdmValueDto();
        udmValue1.setId(UDM_VALUE_UID_1);
        UdmValueDto udmValue2 = new UdmValueDto();
        udmValue2.setId(UDM_VALUE_UID_2);
        udmValue2.setAssignee(ASSIGNEE);
        UdmValueDto udmValue3 = new UdmValueDto();
        udmValue3.setId(UDM_VALUE_UID_3);
        udmValue3.setAssignee(USER_NAME);
        Set<UdmValueDto> udmValues = new LinkedHashSet<>(List.of(udmValue1, udmValue2, udmValue3));
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        udmValueRepository.updateAssignee(new HashSet<>(List.of(udmValue1.getId(), udmValue2.getId())),
            USER_NAME, USER_NAME);
        expectLastCall().once();
        udmValueAuditService.logAction(udmValue1.getId(), UdmValueActionTypeEnum.ASSIGNEE_CHANGE,
            "Assignment was changed. Value was assigned to ‘user@copyright.com’");
        expectLastCall().once();
        udmValueAuditService.logAction(udmValue2.getId(), UdmValueActionTypeEnum.ASSIGNEE_CHANGE,
            "Assignment was changed. Old assignee is 'wjohn@copyright.com'. " +
                "New assignee is 'user@copyright.com'");
        expectLastCall().once();
        replay(udmValueRepository, udmValueAuditService, RupContextUtils.class);
        udmValueService.assignValues(udmValues);
        verify(udmValueRepository, udmValueAuditService, RupContextUtils.class);
    }

    @Test
    public void testUnassignValues() {
        mockStatic(RupContextUtils.class);
        UdmValueDto udmValue = new UdmValueDto();
        udmValue.setId(UDM_VALUE_UID_1);
        udmValue.setAssignee(ASSIGNEE);
        Set<UdmValueDto> udmValues = Set.of(udmValue);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        udmValueRepository.updateAssignee(Set.of(udmValue.getId()), null, USER_NAME);
        expectLastCall().once();
        udmValueAuditService.logAction(udmValue.getId(), UdmValueActionTypeEnum.UNASSIGN,
            "Value was unassigned from 'wjohn@copyright.com'");
        expectLastCall().once();
        replay(udmValueRepository, udmValueAuditService, RupContextUtils.class);
        udmValueService.unassignValues(udmValues);
        verify(udmValueRepository, udmValueAuditService, RupContextUtils.class);
    }

    @Test
    public void testGetAssignees() {
        List<String> assignees = List.of("wjohn@copyright.com");
        expect(udmValueRepository.findAssignees()).andReturn(assignees).once();
        replay(udmValueRepository);
        assertEquals(assignees, udmValueService.getAssignees());
        verify(udmValueRepository);
    }

    @Test
    public void testPopulateValueBatchFirstPeriodOfTheYear() {
        mockStatic(RupContextUtils.class);
        mockStatic(RupPersistUtils.class);
        UdmValue value1 = buildUdmValue(2365985896L);
        UdmValue value2 = buildUdmValue(3000985896L);
        List<UdmValue> values = List.of(value1, value2);
        expect(udmBaselineRepository.findNotPopulatedValuesFromBaseline(202006)).andReturn(values).once();
        rightsService.updateUdmValuesRights(values, 202006);
        expectLastCall().andAnswer(() -> {
            values.get(0).setRhAccountNumber(1000002958L);
            return null;
        }).once();
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        expect(RupPersistUtils.generateUuid()).andReturn("fdd6a424-fa0c-4ced-b3c3-3804b36ae275").once();
        udmValueRepository.insert(value1);
        expectLastCall().once();
        expect(udmBaselineRepository.populateValueId(202006,
            ImmutableMap.of(2365985896L, "fdd6a424-fa0c-4ced-b3c3-3804b36ae275"), USER_NAME)).andReturn(1).once();
        replay(udmBaselineRepository, udmValueRepository, rightsService, RupContextUtils.class, RupPersistUtils.class);
        assertEquals(1, udmValueService.populateValueBatch(202006));
        verify(udmBaselineRepository, udmValueRepository, rightsService, RupContextUtils.class, RupPersistUtils.class);
    }

    @Test
    public void testPopulateValueBatchSecondPeriodOfTheYear() {
        mockStatic(RupContextUtils.class);
        mockStatic(RupPersistUtils.class);
        UdmValue value1 = buildUdmValue(2365985896L);
        UdmValue value2 = buildUdmValue(3000985896L);
        List<UdmValue> values = List.of(value1, value2);
        expect(udmBaselineRepository.findNotPopulatedValuesFromBaseline(202012)).andReturn(values).once();
        rightsService.updateUdmValuesRights(values, 202012);
        expectLastCall().andAnswer(() -> {
            values.get(0).setRhAccountNumber(1000002958L);
            return null;
        }).once();
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        expect(RupPersistUtils.generateUuid()).andReturn("064573cf-9043-42ae-a73a-25592ea4a01c").once();
        udmValueRepository.insert(value1);
        expectLastCall().once();
        udmValueRepository.updateResearchedInPrevPeriod(202012, USER_NAME);
        expectLastCall().once();
        expect(udmBaselineRepository.populateValueId(202012,
            ImmutableMap.of(2365985896L, "064573cf-9043-42ae-a73a-25592ea4a01c"), USER_NAME)).andReturn(1).once();
        replay(udmBaselineRepository, udmValueRepository, rightsService, RupContextUtils.class, RupPersistUtils.class);
        assertEquals(1, udmValueService.populateValueBatch(202012));
        verify(udmBaselineRepository, udmValueRepository, rightsService, RupContextUtils.class, RupPersistUtils.class);
    }

    @Test
    public void testPopulateValueBatchWhenNoWorksToPopulate() {
        mockStatic(RupContextUtils.class);
        mockStatic(RupPersistUtils.class);
        List<UdmValue> values = List.of(buildUdmValue(2365985896L), buildUdmValue(3000985896L));
        expect(udmBaselineRepository.findNotPopulatedValuesFromBaseline(202012)).andReturn(values).once();
        rightsService.updateUdmValuesRights(values, 202012);
        expectLastCall().once();
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        replay(udmBaselineRepository, udmValueRepository, rightsService, RupContextUtils.class, RupPersistUtils.class);
        assertEquals(0, udmValueService.populateValueBatch(202012));
        verify(udmBaselineRepository, udmValueRepository, rightsService, RupContextUtils.class, RupPersistUtils.class);
    }

    @Test
    public void testPublishToBaseline() {
        mockStatic(RupContextUtils.class);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        expect(udmValueRepository.publishToBaseline(202012, USER_NAME))
            .andReturn(List.of("c1e388e9-6571-46df-9982-11b849b92424")).once();
        replay(udmValueRepository, RupContextUtils.class);
        assertEquals(1, udmValueService.publishToBaseline(202012));
        verify(udmValueRepository, RupContextUtils.class);
    }

    @Test
    public void testIsAllowedForPublishingTrue() {
        expect(udmValueRepository.isAllowedForPublishing(202106)).andReturn(true).once();
        replay(udmValueRepository);
        assertTrue(udmValueService.isAllowedForPublishing(202106));
        verify(udmValueRepository);
    }

    @Test
    public void testIsAllowedForPublishingFalse() {
        expect(udmValueRepository.isAllowedForPublishing(202106)).andReturn(false).once();
        replay(udmValueRepository);
        assertFalse(udmValueService.isAllowedForPublishing(202106));
        verify(udmValueRepository);
    }

    private UdmValue buildUdmValue(Long wrWrkInst) {
        UdmValue value = new UdmValue();
        value.setWrWrkInst(wrWrkInst);
        return value;
    }
}
