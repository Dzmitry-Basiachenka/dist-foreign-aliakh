package com.copyright.rup.dist.foreign.service.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.repository.api.IPublicationTypeRepository;
import com.copyright.rup.dist.foreign.service.api.IPublicationTypeService;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * Validates {@link PublicationTypeService}.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 01/24/2020
 *
 * @author Anton Azarenka
 */
public class PublicationTypeServiceTest {

    private static final String AACL_PRODUCT_FAMILY = "AACL";
    private static final String PUB_TYPE = "Book";
    private IPublicationTypeService publicationTypeService;
    private IPublicationTypeRepository publicationTypeRepository;

    @Before
    public void setUp() {
        publicationTypeService = new PublicationTypeService();
        publicationTypeRepository = createMock(IPublicationTypeRepository.class);
        Whitebox.setInternalState(publicationTypeService, "pubTypeRepository", publicationTypeRepository);
    }

    @Test
    public void testIsPublicationTypeExist() {
        expect(publicationTypeRepository.isExistForProductFamily(PUB_TYPE, AACL_PRODUCT_FAMILY)).andReturn(true).once();
        replay(publicationTypeRepository);
        assertTrue(publicationTypeService.publicationTypeExist(PUB_TYPE, AACL_PRODUCT_FAMILY));
        verify(publicationTypeRepository);
    }

    @Test
    public void testIsPublicationTypeExistNameNull() {
        expect(publicationTypeRepository.isExistForProductFamily(null, AACL_PRODUCT_FAMILY)).andReturn(false).once();
        replay(publicationTypeRepository);
        assertFalse(publicationTypeService.publicationTypeExist(null, AACL_PRODUCT_FAMILY));
        verify(publicationTypeRepository);
    }

    @Test
    public void testGetPublicationTypes() {
        List<PublicationType> pubTypes = Collections.singletonList(buildPublicationType("Book", "1.00"));
        expect(publicationTypeRepository.findByProductFamily(AACL_PRODUCT_FAMILY)).andReturn(pubTypes).once();
        replay(publicationTypeRepository);
        assertEquals(pubTypes, publicationTypeService.getPublicationTypes(AACL_PRODUCT_FAMILY));
        verify(publicationTypeRepository);
    }

    private PublicationType buildPublicationType(String name, String weight) {
        PublicationType pubType = new PublicationType();
        pubType.setName(name);
        pubType.setWeight(new BigDecimal(weight));
        return pubType;
    }
}
