package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.ResearchedUsage;
import com.copyright.rup.dist.foreign.domain.Work;
import com.copyright.rup.dist.foreign.integration.pi.api.IPiIntegrationService;

import org.junit.Before;
import org.junit.Test;

/**
 * Validates {@link StandardNumberValidator}.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 04/01/2019
 *
 * @author Uladzislau Shalamitski
 */
public class StandardNumberValidatorTest {

    private IPiIntegrationService piIntegrationService;
    private StandardNumberValidator validator;

    @Before
    public void setUp() {
        piIntegrationService = createMock(IPiIntegrationService.class);
        validator = new StandardNumberValidator(piIntegrationService);
    }

    @Test
    public void testIsValid() {
        ResearchedUsage usage = buildResearchedUsage();
        expect(piIntegrationService.findWorkByIdnoAndTitle(usage.getStandardNumber(), null))
            .andReturn(new Work(185367895L, "Technical Journal", "VALISSN"))
            .once();
        replay(piIntegrationService);
        assertTrue(validator.isValid(usage));
        verify(piIntegrationService);
    }

    @Test
    public void testIsValidEmptyWork() {
        ResearchedUsage usage = buildResearchedUsage();
        expect(piIntegrationService.findWorkByIdnoAndTitle(usage.getStandardNumber(), null))
            .andReturn(new Work())
            .once();
        replay(piIntegrationService);
        assertFalse(validator.isValid(usage));
        verify(piIntegrationService);
    }

    @Test(expected = NullPointerException.class)
    public void testValidationNullUsage() {
        validator.isValid(null);
    }

    @Test
    public void testGetErrorMessage() {
        assertEquals("Loaded Standard Number is missing in PI or there are multiple matches",
            validator.getErrorMessage());
    }

    private ResearchedUsage buildResearchedUsage() {
        ResearchedUsage researchedUsage = new ResearchedUsage();
        researchedUsage.setWrWrkInst(1000009522L);
        researchedUsage.setSystemTitle("Technical Journal");
        researchedUsage.setStandardNumber("978-0-7695-2365-2");
        return researchedUsage;
    }
}
