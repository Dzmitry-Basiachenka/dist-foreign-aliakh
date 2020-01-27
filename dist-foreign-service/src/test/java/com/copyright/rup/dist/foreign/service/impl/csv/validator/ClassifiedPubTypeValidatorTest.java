package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.AaclClassifiedUsage;
import com.copyright.rup.dist.foreign.service.api.IPublicationTypeService;

import org.junit.Before;
import org.junit.Test;

/**
 * Validates {@link ClassifiedPubTypeValidator}.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 01/24/2020
 *
 * @author Anton Azarenka
 */
public class ClassifiedPubTypeValidatorTest {

    private static final String PUB_TYPE = "Book";
    private IPublicationTypeService publicationTypeService;
    private ClassifiedPubTypeValidator validator;

    @Before
    public void setUp() {
        publicationTypeService = createMock(IPublicationTypeService.class);
        validator = new ClassifiedPubTypeValidator(publicationTypeService);
    }

    @Test
    public void testIsValid() {
        AaclClassifiedUsage usage = buildClassifiedAaclUsage(PUB_TYPE);
        expect(publicationTypeService.isPublicationTypeExist(PUB_TYPE)).andReturn(true).once();
        replay(publicationTypeService);
        assertTrue(validator.isValid(usage));
        verify(publicationTypeService);
    }

    @Test
    public void testIsValidPubTypeNotExist() {
        AaclClassifiedUsage usage = buildClassifiedAaclUsage(null);
        expect(publicationTypeService.isPublicationTypeExist(null)).andReturn(false).once();
        replay(publicationTypeService);
        assertFalse(validator.isValid(usage));
        verify(publicationTypeService);
    }

    @Test(expected = NullPointerException.class)
    public void testValidationNullUsage() {
        validator.isValid(null);
    }

    private AaclClassifiedUsage buildClassifiedAaclUsage(String pubTypeName) {
        AaclClassifiedUsage aaclClassifiedUsage = new AaclClassifiedUsage();
        aaclClassifiedUsage.setPublicationType(pubTypeName);
        return aaclClassifiedUsage;
    }
}
