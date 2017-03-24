package com.copyright.rup.dist.foreign.repository.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.domain.UsageBatch;

import org.junit.Before;
import org.junit.Test;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link UsageBatchRepository}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/07/2017
 *
 * @author Mikita Hladkikh
 */
public class UsageBatchRepositoryTest {

    private SqlSessionTemplate sqlSessionTemplate;
    private UsageBatchRepository usageBatchRepository;

    @Before
    public void setUp() {
        sqlSessionTemplate = createMock(SqlSessionTemplate.class);
        usageBatchRepository = new UsageBatchRepository();
        usageBatchRepository.setSqlSessionTemplate(sqlSessionTemplate);
    }

    @Test
    public void testFindAll() {
        List<UsageBatch> usageBatches = Collections.singletonList(new UsageBatch());
        expect(sqlSessionTemplate.<UsageBatch>selectList("IUsageBatchMapper.findAll"))
            .andReturn(usageBatches).once();
        replay(sqlSessionTemplate);
        assertEquals(usageBatches, usageBatchRepository.findAll());
        verify(sqlSessionTemplate);
    }
}
