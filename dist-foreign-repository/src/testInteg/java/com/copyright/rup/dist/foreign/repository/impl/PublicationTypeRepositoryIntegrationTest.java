package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
    public void testIsExistByName() {
        assertTrue(publicationTypeRepository.isPublicationTypeExist("Book"));
    }

    @Test
    public void testIsExistByNameInMixedCase() {
        assertTrue(publicationTypeRepository.isPublicationTypeExist("boOK"));
    }

    @Test
    public void testIsExistByNameNotExist() {
        assertFalse(publicationTypeRepository.isPublicationTypeExist("Books"));
    }
}
