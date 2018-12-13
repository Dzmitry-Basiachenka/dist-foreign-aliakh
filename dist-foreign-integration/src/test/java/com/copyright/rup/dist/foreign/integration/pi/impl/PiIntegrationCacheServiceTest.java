package com.copyright.rup.dist.foreign.integration.pi.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.domain.Work;

import org.junit.Before;
import org.junit.Test;

/**
 * Verifies {@link PiIntegrationCacheService}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 11/28/2018
 *
 * @author Ihar Suvorau
 */
public class PiIntegrationCacheServiceTest {

    private static final String OCULAR_TITLE = "Ocular Tissue Culture";
    private static final String FORBIDDEN_RITES = "Forbidden rites";
    private static final String NECROMANCER = "A Necromancer's Manual of the Fifteenth Century";
    private static final String IDNO_1 = "0-271-01750-3";
    private static final String IDNO_2 = "978-0-271-01751-8";
    private PiIntegrationCacheService piIntegrationCacheService;
    private PiIntegrationService piIntegrationService;

    @Before
    public void setUp() {
        piIntegrationService = createMock(PiIntegrationService.class);
        piIntegrationCacheService = new PiIntegrationCacheService(piIntegrationService, 1);
        piIntegrationCacheService.createCache();
    }

    @Test
    public void testFindWorkByIdnoAndTitle() {
        Work work1 = new Work(1111L, NECROMANCER);
        Work work2 = new Work(2222L, FORBIDDEN_RITES);
        Work work3 = new Work(3333L, OCULAR_TITLE);
        expect(piIntegrationService.findWorkByIdnoAndTitle("1140-9126", null)).andReturn(work1).once();
        expect(piIntegrationService.findWorkByIdnoAndTitle(IDNO_1, FORBIDDEN_RITES)).andReturn(work2).once();
        expect(piIntegrationService.findWorkByIdnoAndTitle(IDNO_2, OCULAR_TITLE)).andReturn(work3).once();
        replay(piIntegrationService);
        assertEquals(work1, piIntegrationCacheService.findWorkByIdnoAndTitle("1140-9126", null));
        assertEquals(work2, piIntegrationCacheService.findWorkByIdnoAndTitle(IDNO_1, FORBIDDEN_RITES));
        assertEquals(work3, piIntegrationCacheService.findWorkByIdnoAndTitle(IDNO_2, OCULAR_TITLE));
        assertEquals(work2, piIntegrationCacheService.findWorkByIdnoAndTitle(IDNO_1, FORBIDDEN_RITES));
        assertEquals(work3, piIntegrationCacheService.findWorkByIdnoAndTitle(IDNO_2, OCULAR_TITLE));
        verify(piIntegrationService);
    }

    @Test
    public void testFindWrWrkInstsByTitle() {
        Work work1 = new Work(1111L, null);
        Work work2 = new Work(2222L, null);
        Work work3 = new Work(3333L, null);
        expect(piIntegrationService.findWorkByTitle(NECROMANCER)).andReturn(work1).once();
        expect(piIntegrationService.findWorkByTitle(FORBIDDEN_RITES)).andReturn(work2).once();
        expect(piIntegrationService.findWorkByTitle(OCULAR_TITLE)).andReturn(work3).once();
        replay(piIntegrationService);
        assertEquals(work1, piIntegrationCacheService.findWorkByTitle(NECROMANCER));
        assertEquals(work2, piIntegrationCacheService.findWorkByTitle(FORBIDDEN_RITES));
        assertEquals(work3, piIntegrationCacheService.findWorkByTitle(OCULAR_TITLE));
        assertEquals(work2, piIntegrationCacheService.findWorkByTitle(FORBIDDEN_RITES));
        assertEquals(work3, piIntegrationCacheService.findWorkByTitle(OCULAR_TITLE));
        verify(piIntegrationService);
    }
}
