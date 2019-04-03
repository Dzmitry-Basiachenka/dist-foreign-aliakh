package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.Work;
import com.copyright.rup.dist.foreign.integration.pi.api.IPiIntegrationService;

import org.junit.Before;
import org.junit.Test;

/**
 * Validates {@link WrWrkInstValidator}.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 04/01/2019
 *
 * @author Uladzislau Shalamitski
 */
public class WrWrkInstValidatorTest {

    private static final Long WR_WRK_INST = 185367895L;

    private IPiIntegrationService piIntegrationService;
    private WrWrkInstValidator validator;

    @Before
    public void setUp() {
        piIntegrationService = createMock(IPiIntegrationService.class);
        validator = new WrWrkInstValidator(piIntegrationService);
    }

    @Test
    public void testIsValid() {
        Usage usage = buildUsage(WR_WRK_INST, null);
        expect(piIntegrationService.findWorkByWrWrkInst(WR_WRK_INST))
            .andReturn(new Work(WR_WRK_INST, "Technical Journal", "VALISSN"))
            .once();
        replay(piIntegrationService);
        assertTrue(validator.isValid(usage));
        assertEquals("VALISSN", usage.getStandardNumberType());
        verify(piIntegrationService);
    }

    @Test
    public void testIsValidUsageWithoutWrWrkInstAndStandardNumberType() {
        Usage usage = buildUsage(null, null);
        replay(piIntegrationService);
        assertTrue(validator.isValid(usage));
        assertNull(usage.getStandardNumberType());
        verify(piIntegrationService);
    }

    @Test
    public void testIsValidUsageWithoutWrWrkInstAndWithStandardNumberType() {
        Usage usage = buildUsage(null, "VALISBN10");
        replay(piIntegrationService);
        assertTrue(validator.isValid(usage));
        assertEquals("VALISBN10", usage.getStandardNumberType());
        verify(piIntegrationService);
    }

    @Test
    public void testIsValidEmptyWork() {
        Usage usage = buildUsage(WR_WRK_INST, null);
        expect(piIntegrationService.findWorkByWrWrkInst(WR_WRK_INST))
            .andReturn(new Work())
            .once();
        replay(piIntegrationService);
        assertFalse(validator.isValid(usage));
        assertNull(usage.getStandardNumberType());
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

    private Usage buildUsage(Long wrWrkInst, String standardNumberType) {
        Usage usage = new Usage();
        usage.setWrWrkInst(wrWrkInst);
        usage.setWorkTitle("Technical Journal");
        usage.setStandardNumberType(standardNumberType);
        return usage;
    }
}
