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

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * Verifies {@link RightsholderRepository}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 14/11/2017
 *
 * @author Aliaksandra Bayanouskaya
 */
public class RightsholderRepositoryTest {

    private SqlSessionTemplate sqlSessionTemplate;
    private RightsholderRepository rightsholderRepository;

    @Before
    public void setUp() {
        sqlSessionTemplate = createMock(SqlSessionTemplate.class);
        rightsholderRepository = new RightsholderRepository();
        rightsholderRepository.setSqlSessionTemplate(sqlSessionTemplate);
    }

    @Test
    public void testFindRightsholdersByAccountNumbers() {
        Set<Long> accountNumbers = LongStream.range(1000000000, 1000032001).boxed().collect(Collectors.toSet());
        expect(sqlSessionTemplate.selectList(eq("IRightsholderMapper.findRightsholdersByAccountNumbers"),
            notNull(Set.class))).andReturn(Lists.newArrayList()).times(2);
        replay(sqlSessionTemplate);
        rightsholderRepository.findRightsholdersByAccountNumbers(accountNumbers);
        verify(sqlSessionTemplate);
    }
}
