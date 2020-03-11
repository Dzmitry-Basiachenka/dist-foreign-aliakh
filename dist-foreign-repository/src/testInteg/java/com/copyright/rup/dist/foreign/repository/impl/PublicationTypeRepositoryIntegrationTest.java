package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.repository.api.IPublicationTypeRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

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

    @Test
    public void testFindPublicationTypes() {
        List<PublicationType> pubTypes = publicationTypeRepository.findPublicationTypes();
        assertNotNull(pubTypes);
        assertEquals(5, pubTypes.size());
        assertEquals(buildPublicationType("2fe9c0a0-7672-4b56-bc64-9d4125fecf6e", "Book", "1.00"),
            pubTypes.get(0));
        assertEquals(buildPublicationType("68fd94c0-a8c0-4a59-bfe3-6674c4b12199", "Business or Trade Journal", "1.50"),
            pubTypes.get(1));
        assertEquals(buildPublicationType("46634907-882e-4f91-b1ad-f57db945aff7", "Consumer Magazine", "1.00"),
            pubTypes.get(2));
        assertEquals(buildPublicationType("a3dff475-fc06-4d8c-b7cc-f093073ada6f", "News Source", "4.00"),
            pubTypes.get(3));
        assertEquals(buildPublicationType("1f6f1925-7aa1-4b1a-b3a8-8903acc3d18e", "STMA Journal", "1.10"),
            pubTypes.get(4));
    }

    private PublicationType buildPublicationType(String id, String name, String weight) {
        PublicationType pubType = new PublicationType();
        pubType.setId(id);
        pubType.setName(name);
        pubType.setWeight(new BigDecimal(weight));
        return pubType;
    }
}
