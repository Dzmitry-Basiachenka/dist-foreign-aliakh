package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.copyright.rup.dist.foreign.repository.api.IPublicationTypeRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration test for {@link IPublicationTypeRepository}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 01/21/2020
 *
 * @author Anton Azarenka
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml"})
@Transactional
public class PublicationTypeRepositoryIntegrationTest {

    @Autowired
    private IPublicationTypeRepository publicationTypeRepository;

    @Test
    public void testFindIdByName() {
        assertEquals("2fe9c0a0-7672-4b56-bc64-9d4125fecf6e", publicationTypeRepository.findIdByName("Book"));
    }

    @Test
    public void testFindIdByNameInMixedCase() {
        assertEquals("2fe9c0a0-7672-4b56-bc64-9d4125fecf6e", publicationTypeRepository.findIdByName("boOK"));
    }

    @Test
    public void testFindIdByNameNotExist() {
        assertNull(publicationTypeRepository.findIdByName("Books"));
    }
}
