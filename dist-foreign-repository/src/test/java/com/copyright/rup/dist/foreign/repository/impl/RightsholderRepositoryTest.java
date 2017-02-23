package com.copyright.rup.dist.foreign.repository.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.common.domain.Rightsholder;

import org.junit.Before;
import org.junit.Test;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link RightsholderRepository}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/07/2017
 *
 * @author Mikita Hladkikh
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
    public void testGetRros() {
        List<Rightsholder> rros = Collections.singletonList(new Rightsholder());
        expect(sqlSessionTemplate.<Rightsholder>selectList("IRightsholderMapper.findRros")).andReturn(rros).once();
        replay(sqlSessionTemplate);
        assertEquals(rros, rightsholderRepository.findRros());
        verify(sqlSessionTemplate);
    }
}
