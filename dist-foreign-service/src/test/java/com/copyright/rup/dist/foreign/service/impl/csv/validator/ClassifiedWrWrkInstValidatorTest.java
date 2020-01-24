package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.AaclClassifiedUsage;
import com.copyright.rup.dist.foreign.domain.Work;
import com.copyright.rup.dist.foreign.integration.pi.api.IPiIntegrationService;

import org.junit.Before;
import org.junit.Test;

/**
 * Validates {@link ClassifiedWrWrkInstValidator}.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 01/23/2020
 *
 * @author Anton Azarenka
 */
public class ClassifiedWrWrkInstValidatorTest {

    private static final Long WR_WRK_INST = 185367895L;
    private IPiIntegrationService piIntegrationService;
    private ClassifiedWrWrkInstValidator validator;

    @Before
    public void setUp() {
        piIntegrationService = createMock(IPiIntegrationService.class);
        validator = new ClassifiedWrWrkInstValidator(piIntegrationService);
    }

    @Test
    public void testIsValid() {
        AaclClassifiedUsage usage = buildClassifiedAaclUsage(WR_WRK_INST);
        expect(piIntegrationService.findWorkByWrWrkInst(WR_WRK_INST))
            .andReturn(new Work(WR_WRK_INST, "Technical Journal", null, "VALISSN")).once();
        replay(piIntegrationService);
        assertTrue(validator.isValid(usage));
        verify(piIntegrationService);
    }

    @Test
    public void testIsValidEmptyWork() {
        AaclClassifiedUsage usage = buildClassifiedAaclUsage(WR_WRK_INST);
        expect(piIntegrationService.findWorkByWrWrkInst(WR_WRK_INST))
            .andReturn(new Work(null, null, null, null)).once();
        replay(piIntegrationService);
        assertFalse(validator.isValid(usage));
        verify(piIntegrationService);
    }

    @Test
    public void testIsValidUsageNullWork() {
        AaclClassifiedUsage usage = buildClassifiedAaclUsage(null);
        replay(piIntegrationService);
        assertTrue(validator.isValid(usage));
        verify(piIntegrationService);
    }

    @Test(expected = NullPointerException.class)
    public void testValidationNullUsage() {
        validator.isValid(null);
    }

    @Test
    public void testGetErrorMessage() {
        assertEquals("Loaded Wr Wrk Inst is missing in PI", validator.getErrorMessage());
    }

    private AaclClassifiedUsage buildClassifiedAaclUsage(Long wrWrkInst) {
        AaclClassifiedUsage aaclClassifiedUsage = new AaclClassifiedUsage();
        aaclClassifiedUsage.setWrWrkInst(wrWrkInst);
        return aaclClassifiedUsage;
    }
}
