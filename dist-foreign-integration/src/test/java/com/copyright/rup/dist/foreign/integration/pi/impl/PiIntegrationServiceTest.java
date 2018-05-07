package com.copyright.rup.dist.foreign.integration.pi.impl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.Work;
import com.copyright.rup.es.api.RupEsApi;
import com.copyright.rup.es.api.RupSearchResponse;
import com.copyright.rup.es.api.domain.RupSearchHit;
import com.copyright.rup.es.api.domain.RupSearchResults;
import com.copyright.rup.es.api.request.RupSearchRequest;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

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
        Map<String, String> idnoToTitleMap = new LinkedHashMap<>();
        idnoToTitleMap.put("978-0-271-01751-8", null);
        idnoToTitleMap.put("0-271-01750-3", null);
        idnoToTitleMap.put(" etocr-N06658249-8 ", null);
        idnoToTitleMap.put("10.1353/PGN.1999.0081", null);
        idnoToTitleMap.put("1140-9126", null);
        idnoToTitleMap.put("978-0-08-027365-5", null);
        idnoToTitleMap.put("1633-1370", null);
        Map<String, Work> result = piIntegrationService.findWorksByIdnos(idnoToTitleMap);
        assertEquals(5, result.size());
        Work work1 = result.get("978-0-271-01751-8");
        Work work2 = result.get("0-271-01750-3");
        Work work3 = result.get("1140-9126");
        Work work4 = result.get("978-0-08-027365-5");
        Work work5 = result.get("1633-1370");
        assertEquals(123059057L, work1.getWrWrkInst(), 0);
        assertEquals(123059058L, work2.getWrWrkInst(), 0);
        assertEquals(156427025L, work3.getWrWrkInst(), 0);
        assertEquals(112942199L, work4.getWrWrkInst(), 0);
        assertEquals(113747840L, work5.getWrWrkInst(), 0);
        verify(rupEsApi, searchResponse, searchResults, searchHit1, searchHit2, searchHit3, searchHit4, searchHit5,
            searchHit6, searchHit7);
    }

    @Test
    public void testFindWrWrkInstsByTitle() {
        expectGetSearchResponse();
        replay(rupEsApi, searchResponse, searchResults, searchHit1, searchHit2, searchHit3, searchHit4, searchHit5,
            searchHit6, searchHit7);
        Set<String> titles = new LinkedHashSet<>();
        titles.add("Forbidden rites");
        titles.add("Forbidden rites : a necromancer's manual of the fifteenth century");
        titles.add("Kieckhefer, Richard, Forbidden Rites: A Necromancer's Manual of the Fifteenth Century");
        titles.add("Forbidden Rites: A Necromancer's Manual of the Fifteenth Century (review)");
        titles.add("Annuaire de la communication en Rhône-Alpes");
        titles.add("Ocular Tissue Culture");
        titles.add("Ocular tissue culture");
        Map<String, Long> result = piIntegrationService.findWrWrkInstsByTitles(titles);
        assertEquals(5, result.size());
        assertEquals(123059057L, result.get("Forbidden rites"), 0);
        assertEquals(123059058L,
            result.get("Forbidden rites : a necromancer's manual of the fifteenth century"), 0);
        assertEquals(156427025L, result.get("Annuaire de la communication en Rhône-Alpes"), 0);
        assertEquals(112942199L, result.get("Ocular Tissue Culture"), 0);
        assertEquals(113747840L, result.get("Ocular tissue culture"), 0);
        verify(rupEsApi, searchResponse, searchResults, searchHit1, searchHit2, searchHit3, searchHit4, searchHit5,
            searchHit6, searchHit7);
    }

    private void expectGetSearchResponse() {
        expect(searchResponse.getResults()).andReturn(searchResults).times(7);
        expect(searchResults.getHits()).andReturn(Collections.singletonList(searchHit1)).once();
        expect(searchResults.getHits()).andReturn(Collections.singletonList(searchHit2)).once();
        expect(searchResults.getHits()).andReturn(Collections.emptyList()).once();
        expect(searchResults.getHits()).andReturn(Collections.emptyList()).once();
        expect(searchResults.getHits()).andReturn(Collections.singletonList(searchHit5)).once();
        expect(searchResults.getHits()).andReturn(Collections.singletonList(searchHit6)).once();
        expect(searchResults.getHits()).andReturn(Collections.singletonList(searchHit7)).once();
        expect(rupEsApi.search(capture(requestCapture))).andReturn(searchResponse).times(7);
        expectSearchHits();
    }

    private void expectSearchHits() {
        expectSearchHitSource(searchHit1, "pi_search_hit1.json");
        expectSearchHitSource(searchHit2, "pi_search_hit2.json");
        expectSearchHitSource(searchHit5, "pi_search_hit5.json");
        expectSearchHitSource(searchHit6, "pi_search_hit6.json");
        expectSearchHitSource(searchHit7, "pi_search_hit7.json");
    }

    private void expectSearchHitSource(RupSearchHit searchHit, String sourceFileName) {
        expect(searchHit.getSource())
            .andReturn(TestUtils.fileToString(PiIntegrationServiceTest.class, sourceFileName)).once();
    }

    private static class PiIntegrationServiceMock extends PiIntegrationService {

        private final RupEsApi rupEsApiMock = createMock(RupEsApi.class);

        @Override
        protected RupEsApi getRupEsApi() {
            return rupEsApiMock;
        }
    }
}
