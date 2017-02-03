package com.copyright.rup.dist.foreign.repository.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.domain.Usage;

import org.junit.Before;
import org.junit.Test;
import org.mybatis.spring.SqlSessionTemplate;

/**
 * Verifies {@link UsageRepository}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/02/17
 *
 * @author Darya Baraukova
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
    public void testInsertUsage() {
        Usage usage = new Usage();
        expect(sqlSessionTemplate.insert("insertUsage", usage)).andReturn(1).once();
        replay(sqlSessionTemplate);
        assertEquals(1, usageRepository.insertUsage(usage));
        verify(sqlSessionTemplate);
    }
}
