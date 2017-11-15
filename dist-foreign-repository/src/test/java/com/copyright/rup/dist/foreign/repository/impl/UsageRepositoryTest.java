package com.copyright.rup.dist.foreign.repository.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.notNull;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * Verifies {@link UsageRepository}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 15/11/2017
 *
 * @author Aliaksandra Bayanouskaya
 */
public class UsageRepositoryTest {

    private SqlSessionTemplate sqlSessionTemplate;
    private UsageRepository usageRepository;

    @Before
    public void setUp() {
        sqlSessionTemplate = createMock(SqlSessionTemplate.class);
        usageRepository = new UsageRepository();
        usageRepository.setSqlSessionTemplate(sqlSessionTemplate);
    }

    @Test
    public void testFindDuplicateDetailIds() {
        List<Long> detailIds = LongStream.range(1000000000, 1000016001).boxed().collect(Collectors.toList());
        expect(sqlSessionTemplate.selectList(eq("IUsageMapper.findDuplicateDetailIds"),
            notNull(List.class))).andReturn(Lists.newArrayList()).times(2);
        replay(sqlSessionTemplate);
        usageRepository.findDuplicateDetailIds(detailIds);
        verify(sqlSessionTemplate);
    }

    @Test
    public void testFindIdsByScenarioIdRroAccountNumberRhAccountNumbers() {
        List<Long> accountNumbers = LongStream.range(1000000000, 1000032001).boxed().collect(Collectors.toList());
        expect(sqlSessionTemplate.selectList(eq("IUsageMapper.findIdsByScenarioIdRroAccountNumberRhAccountNumbers"),
            notNull(List.class))).andReturn(Lists.newArrayList()).times(2);
        replay(sqlSessionTemplate);
        usageRepository.findIdsByScenarioIdRroAccountNumberRhAccountNumbers("b1f0b236-3ae9-4a60-9fab-61db84199d6f",
            1000000000L, accountNumbers);
        verify(sqlSessionTemplate);
    }
}
