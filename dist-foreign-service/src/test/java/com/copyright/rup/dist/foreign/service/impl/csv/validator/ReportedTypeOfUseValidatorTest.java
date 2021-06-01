package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmTypeOfUseService;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Validates {@link ReportedTypeOfUseValidator}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 05/21/2021
 *
 * @author Dzmitry Basiachenka
 */
public class ReportedTypeOfUseValidatorTest {

    private static final String COPY_FOR_MYSELF = "COPY_FOR_MYSELF";
    private static final String EMAIL_COPY = "EMAIL_COPY";
    private static final String TITLE_1 = "Colloids and surfaces. B, Biointerfaces";
    private static final String TITLE_2 = "Tenside, surfactants, detergents";
    private static final String NONE_TITLE_ERROR_MESSAGE = "Reported TOU should be blank if Reported Title equals to " +
        "'None', otherwise it should be filled in";
    private static final String TOU_NOT_EXIST_ERROR_MESSAGE = "Reported TOU doesn't exist in the system";

    private final List<String> udmTous = Arrays.asList(COPY_FOR_MYSELF, EMAIL_COPY);

    private ReportedTypeOfUseValidator validator;

    @Before
    public void setUp() {
        IUdmTypeOfUseService udmTypeOfUseService = createMock(IUdmTypeOfUseService.class);
        expect(udmTypeOfUseService.getAllUdmTous()).andReturn(udmTous).once();
        replay(udmTypeOfUseService);
        validator = new ReportedTypeOfUseValidator(udmTypeOfUseService);
        verify(udmTypeOfUseService);
    }

    @Test
    public void testIsValid() {
        UdmUsage usage = buildUdmUsage(COPY_FOR_MYSELF, TITLE_1);
        assertTrue(validator.isValid(usage));
    }

    @Test
    public void testIsValidNoneTitle() {
        UdmUsage usage = buildUdmUsage(null, "None");
        assertTrue(validator.isValid(usage));
        usage = buildUdmUsage(null, "NONE");
        assertTrue(validator.isValid(usage));
        usage = buildUdmUsage("", "NONE");
        assertTrue(validator.isValid(usage));
    }

    @Test
    public void testIsValidTitleNull() {
        UdmUsage usage = buildUdmUsage(EMAIL_COPY, null);
        assertTrue(validator.isValid(usage));
    }

    @Test
    public void testTouNullGetErrorMessage() {
        UdmUsage usage = buildUdmUsage(null, null);
        assertFalse(validator.isValid(usage));
        assertEquals(NONE_TITLE_ERROR_MESSAGE, validator.getErrorMessage());
    }

    @Test
    public void testTouEmptyGetErrorMessage() {
        UdmUsage usage = buildUdmUsage("", null);
        assertFalse(validator.isValid(usage));
        assertEquals(NONE_TITLE_ERROR_MESSAGE, validator.getErrorMessage());
    }

    @Test
    public void testTitleNullErrorMessage() {
        UdmUsage usage = buildUdmUsage(null, TITLE_1);
        assertFalse(validator.isValid(usage));
        assertEquals(NONE_TITLE_ERROR_MESSAGE, validator.getErrorMessage());
    }

    @Test
    public void testNoneTitleGetErrorMessage() {
        UdmUsage usage = buildUdmUsage(EMAIL_COPY, "None");
        assertFalse(validator.isValid(usage));
        assertEquals(NONE_TITLE_ERROR_MESSAGE, validator.getErrorMessage());
    }

    @Test
    public void testTouGetErrorMessage() {
        UdmUsage usage = buildUdmUsage("Email_COPY", TITLE_2);
        assertFalse(validator.isValid(usage));
        assertEquals(TOU_NOT_EXIST_ERROR_MESSAGE, validator.getErrorMessage());
    }

    @Test
    public void testTitleNullGetErrorMessage() {
        UdmUsage usage = buildUdmUsage("STORE COPY", null);
        assertFalse(validator.isValid(usage));
        assertEquals(TOU_NOT_EXIST_ERROR_MESSAGE, validator.getErrorMessage());
    }

    @Test
    public void testTitleEmptyGetErrorMessage() {
        UdmUsage usage = buildUdmUsage("FAX_COPY", "");
        assertFalse(validator.isValid(usage));
        assertEquals(TOU_NOT_EXIST_ERROR_MESSAGE, validator.getErrorMessage());
    }

    @Test(expected = NullPointerException.class)
    public void testValidationNullUsage() {
        validator.isValid(null);
    }

    private UdmUsage buildUdmUsage(String reportedTypeOfUse, String reportedTitle) {
        UdmUsage usage = new UdmUsage();
        usage.setId("6b171dde-16a1-444a-90c5-5faaa5dd8973");
        usage.setReportedTypeOfUse(reportedTypeOfUse);
        usage.setReportedTitle(reportedTitle);
        return usage;
    }
}
