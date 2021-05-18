package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.AaclClassifiedUsage;
import com.copyright.rup.dist.foreign.service.api.ILicenseeClassService;

import org.junit.Before;
import org.junit.Test;

/**
 * Validates {@link AaclDetailLicenseeClassValidator}.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 01/27/2020
 *
 * @author Anton Azarenka
 */
public class AaclDetailLicenseeClassValidatorTest {

    private static final String ENROLLMENT_PROFILE = "EXGP";
    private static final String DISCIPLINE = "Physical Sciences & Mathematics";
    private static final String NO_VALID_DATA = "no valid";
    private static final String DISQUALIFIED = "Disqualified";
    private AaclDetailLicenseeClassValidator validator;
    private ILicenseeClassService licenseeClassService;

    @Before
    public void setUp() {
        licenseeClassService = createMock(ILicenseeClassService.class);
        validator = new AaclDetailLicenseeClassValidator(licenseeClassService);
    }

    @Test
    public void testIsValid() {
        AaclClassifiedUsage usage = buildClassifiedAaclUsage(ENROLLMENT_PROFILE, DISCIPLINE);
        expect(licenseeClassService.aaclDetailLicenseeClassExists(ENROLLMENT_PROFILE, DISCIPLINE))
            .andReturn(true).once();
        replay(licenseeClassService);
        assertTrue(validator.isValid(usage));
        verify(licenseeClassService);
    }

    @Test
    public void testIsValidEnrollmentProfileNotExist() {
        AaclClassifiedUsage usage = buildClassifiedAaclUsage(NO_VALID_DATA, DISCIPLINE);
        expect(licenseeClassService.aaclDetailLicenseeClassExists(NO_VALID_DATA, DISCIPLINE)).andReturn(false).once();
        replay(licenseeClassService);
        assertFalse(validator.isValid(usage));
        verify(licenseeClassService);
    }

    @Test
    public void testIsValidDisciplineProfileNotExist() {
        AaclClassifiedUsage usage = buildClassifiedAaclUsage(ENROLLMENT_PROFILE, NO_VALID_DATA);
        expect(licenseeClassService.aaclDetailLicenseeClassExists(ENROLLMENT_PROFILE, NO_VALID_DATA)).andReturn(false)
            .once();
        replay(licenseeClassService);
        assertFalse(validator.isValid(usage));
        verify(licenseeClassService);
    }

    @Test
    public void testIsValidDisqualifiedEnrollmentProfile() {
        AaclClassifiedUsage usage = buildClassifiedAaclUsage(DISQUALIFIED, DISCIPLINE);
        replay(licenseeClassService);
        assertTrue(validator.isValid(usage));
        verify(licenseeClassService);
    }

    @Test
    public void testIsValidDisqualifiedDiscipline() {
        AaclClassifiedUsage usage = buildClassifiedAaclUsage(ENROLLMENT_PROFILE, DISQUALIFIED);
        replay(licenseeClassService);
        assertTrue(validator.isValid(usage));
        verify(licenseeClassService);
    }

    @Test
    public void testGetErrorMessage() {
        assertEquals("Detail License Class Id with such enrolment profile and discipline doesn't exist in the system",
            validator.getErrorMessage());
    }

    @Test(expected = NullPointerException.class)
    public void testValidationNullUsage() {
        validator.isValid(null);
    }

    private AaclClassifiedUsage buildClassifiedAaclUsage(String enrollmentProfile, String discipline) {
        AaclClassifiedUsage aaclClassifiedUsage = new AaclClassifiedUsage();
        aaclClassifiedUsage.setEnrollmentProfile(enrollmentProfile);
        aaclClassifiedUsage.setDiscipline(discipline);
        return aaclClassifiedUsage;
    }
}
