package com.copyright.rup.dist.foreign.service.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.repository.api.IPublicationTypeRepository;
import com.copyright.rup.dist.foreign.service.api.IPublicationTypeService;
import com.copyright.rup.dist.foreign.service.impl.aacl.PublicationTypeService;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

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
        expect(publicationTypeRepository.isPublicationTypeExist(PUB_TYPE)).andReturn(true)
            .once();
        replay(publicationTypeRepository);
        assertTrue(publicationTypeService.publicationTypeExist(PUB_TYPE));
        verify(publicationTypeRepository);
    }

    @Test
    public void testIsPublicationTypeExistNameNull() {
        expect(publicationTypeRepository.isPublicationTypeExist(null)).andReturn(false).once();
        replay(publicationTypeRepository);
        assertFalse(publicationTypeService.publicationTypeExist(null));
        verify(publicationTypeRepository);
    }
}
