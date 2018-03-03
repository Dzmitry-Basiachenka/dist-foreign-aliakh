package com.copyright.rup.dist.foreign.integration.pi.impl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.es.api.RupEsApi;
import com.copyright.rup.es.api.RupResponseBase;
import com.copyright.rup.es.api.RupSearchResponse;
import com.copyright.rup.es.api.domain.RupSearchHit;
import com.copyright.rup.es.api.domain.RupSearchResults;
import com.copyright.rup.es.api.request.RupSearchRequest;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * Verifies {@link PiIntegrationService}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 2/22/18
 *
 * @author Aliaksandr Radkevich
 */
public class PiIntegrationServiceTest {

    private PiIntegrationService piIntegrationService;
    private RupEsApi rupEsApi;
    private RupSearchHit searchHit1;
    private RupSearchHit searchHit2;
    private RupSearchHit searchHit3;
    private RupSearchHit searchHit4;
    private RupSearchHit searchHit5;
    private RupSearchHit searchHit6;
    private RupSearchHit searchHit7;
    private RupSearchResponse searchResponse;
    private RupSearchResults searchResults;
    private Capture<RupSearchRequest> requestCapture;

    @Before
    public void setUp() {
        piIntegrationService = new PiIntegrationServiceMock();
        piIntegrationService.init();
        rupEsApi = piIntegrationService.getRupEsApi();
        searchHit1 = createMock(RupSearchHit.class);
        searchHit2 = createMock(RupSearchHit.class);
        searchHit3 = createMock(RupSearchHit.class);
        searchHit4 = createMock(RupSearchHit.class);
        searchHit5 = createMock(RupSearchHit.class);
        searchHit6 = createMock(RupSearchHit.class);
        searchHit7 = createMock(RupSearchHit.class);
        searchResponse = createMock(RupSearchResponse.class);
        searchResults = createMock(RupSearchResults.class);
        requestCapture = new Capture<>();
    }

    @Test
    public void testFindWrWrkInstsByIdno() {
        expectGetSearchResponse();
        replay(rupEsApi, searchResponse, searchResults, searchHit1, searchHit2, searchHit3, searchHit4, searchHit5,
            searchHit6, searchHit7);
        Map<String, Long> result = piIntegrationService.findWrWrkInstsByIdnos(Sets.newHashSet("0-271-01750-3",
            "978-0-271-01751-8", " etocr-N06658249-8 ", "10.1353/PGN.1999.0081", "1140-9126", "978-0-08-027365-5",
            "1633-1370"));
        assertEquals(5, result.size());
        assertEquals(123059057L, result.get("0271017503"), 0);
        assertEquals(123059058L, result.get("9780271017518"), 0);
        assertEquals(421802499L, result.get("ETOCRN066582498"), 0);
        assertEquals(345993258L, result.get("10.1353/PGN.1999.0081"), 0);
        assertEquals(156427025L, result.get("11409126"), 0);
        verify(rupEsApi, searchResponse, searchResults, searchHit1, searchHit2, searchHit3, searchHit4, searchHit5,
            searchHit6, searchHit7);
    }

    @Test
    public void testFindWrWrkInstsByTitle() {
        expectGetSearchResponse();
        replay(rupEsApi, searchResponse, searchResults, searchHit1, searchHit2, searchHit3, searchHit4, searchHit5,
            searchHit6, searchHit7);
        Map<String, Long> result = piIntegrationService.findWrWrkInstsByTitles(Sets.newHashSet(
            "Forbidden rites",
            "Forbidden rites : a necromancer's manual of the fifteenth century",
            "Kieckhefer, Richard, Forbidden Rites: A Necromancer's Manual of the Fifteenth Century",
            "Forbidden Rites: A Necromancer's Manual of the Fifteenth Century (review)",
            "Annuaire de la communication en Rhône-Alpes",
            "Ocular Tissue Culture",
            "Ocular tissue culture",
            "Lieux du livre en Rhône-Alpes"));
        assertEquals(5, result.size());
        assertEquals(421802499L,
            result.get("Kieckhefer, Richard, Forbidden Rites: A Necromancer's Manual of the Fifteenth Century"), 0);
        assertEquals(345993258L,
            result.get("Forbidden Rites: A Necromancer's Manual of the Fifteenth Century (review)"), 0);
        assertEquals(156427025L, result.get("Annuaire de la communication en Rhône-Alpes"), 0);
        assertEquals(112942199L, result.get("Ocular Tissue Culture"), 0);
        assertEquals(113747840L, result.get("Ocular tissue culture"), 0);
        verify(rupEsApi, searchResponse, searchResults, searchHit1, searchHit2, searchHit3, searchHit4, searchHit5,
            searchHit6, searchHit7);
    }

    private void expectGetSearchResponse() {
        expect(searchResponse.getStatus()).andReturn(RupResponseBase.Status.SUCCESS).once();
        expect(searchResponse.getResults()).andReturn(searchResults).once();
        List<RupSearchHit> searchHits = Lists.newArrayList(searchHit1, searchHit2, searchHit3, searchHit4, searchHit5,
            searchHit6, searchHit7);
        expect(searchResults.getHits()).andReturn(searchHits).once();
        expect(rupEsApi.search(capture(requestCapture))).andReturn(searchResponse).once();
        expectSearchHits();
    }

    @Test
    public void testNormalizeIdno() {
        assertEquals("IDNO123456789", PiIntegrationService.normalizeIdno(" Id-no-123-456-789- "));
    }

    private void expectSearchHits() {
        expectSearchHitSource(searchHit1, "pi_search_hit1.json");
        expectSearchHitSource(searchHit2, "pi_search_hit2.json");
        expectSearchHitSource(searchHit3, "pi_search_hit3.json");
        expectSearchHitSource(searchHit4, "pi_search_hit4.json");
        expectSearchHitSource(searchHit5, "pi_search_hit5.json");
        expectSearchHitSource(searchHit6, "pi_search_hit6.json");
        expectSearchHitSource(searchHit7, "pi_search_hit7.json");
    }

    private void expectSearchHitSource(RupSearchHit searchHit, String sourceFileName) {
        expect(searchHit.getSource())
            .andReturn(TestUtils.fileToString(PiIntegrationServiceTest.class, sourceFileName))
            .times(2);
    }

    private static class PiIntegrationServiceMock extends PiIntegrationService {

        private RupEsApi rupEsApiMock = createMock(RupEsApi.class);

        @Override
        protected RupEsApi getRupEsApi() {
            return rupEsApiMock;
        }
    }
}
