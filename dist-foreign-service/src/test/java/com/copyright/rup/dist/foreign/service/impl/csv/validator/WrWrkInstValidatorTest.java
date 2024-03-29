package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
        Usage usage = buildUsage(WR_WRK_INST);
        expect(piIntegrationService.findWorkByWrWrkInst(WR_WRK_INST))
            .andReturn(new Work(WR_WRK_INST, "Technical Journal", null, "VALISSN")).once();
        replay(piIntegrationService);
        assertTrue(validator.isValid(usage));
        verify(piIntegrationService);
    }

    @Test
    public void testIsValidEmptyWork() {
        Usage usage = buildUsage(WR_WRK_INST);
        expect(piIntegrationService.findWorkByWrWrkInst(WR_WRK_INST))
            .andReturn(new Work(null, null, null, null)).once();
        replay(piIntegrationService);
        assertFalse(validator.isValid(usage));
        verify(piIntegrationService);
    }

    @Test
    public void testIsValidUsageNullWork() {
        Usage usage = buildUsage(null);
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

    private Usage buildUsage(Long wrWrkInst) {
        Usage usage = new Usage();
        usage.setWrWrkInst(wrWrkInst);
        usage.setWorkTitle("Technical Journal");
        return usage;
    }
}
