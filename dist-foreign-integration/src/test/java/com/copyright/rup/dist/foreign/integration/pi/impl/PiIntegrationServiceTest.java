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

import com.google.common.collect.ImmutableMap;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
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
// TODO {isuvorau} update test after removing redundant logic from service
public class PiIntegrationServiceTest {

    private static final String OCULAR_TITLE = "Ocular Tissue Culture";
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
    public void testFindWorkByIdnoAndTitle() {
        expectGetSearchResponseByIdno();
        replay(rupEsApi, searchResponse, searchResults, searchHit1, searchHit2, searchHit3, searchHit4, searchHit5,
            searchHit6, searchHit7);
        Work work1 = piIntegrationService.findWorkByIdnoAndTitle("1140-9126", null);
        Work work2 = piIntegrationService.findWorkByIdnoAndTitle("0-271-01750-3", null);
        Work work3 = piIntegrationService.findWorkByIdnoAndTitle("978-0-08-027365-5", null);
        Work work4 = piIntegrationService.findWorkByIdnoAndTitle("10.1353/PGN.1999.0081", null);
        Work work5 = piIntegrationService.findWorkByIdnoAndTitle("978-0-271-01751-8", OCULAR_TITLE);
        assertEquals(123059057L, work1.getWrWrkInst(), 0);
        assertEquals(123059058L, work2.getWrWrkInst(), 0);
        assertEquals(156427025L, work3.getWrWrkInst(), 0);
        assertEquals(112942199L, work4.getWrWrkInst(), 0);
        assertEquals(123067577L, work5.getWrWrkInst(), 0);
        verify(rupEsApi, searchResponse, searchResults, searchHit1, searchHit2, searchHit3, searchHit4, searchHit5,
            searchHit6, searchHit7);
    }

    @Test
    public void testFindWrWrkInstsByTitle() {
        expectGetSearchResponseByTitle();
        replay(rupEsApi, searchResponse, searchResults, searchHit1, searchHit2, searchHit3, searchHit4, searchHit5,
            searchHit6, searchHit7);
        Set<String> titles = new LinkedHashSet<>();
        titles.add("Forbidden rites");
        titles.add("Forbidden rites : a necromancer's manual of the fifteenth century");
        titles.add("Kieckhefer, Richard, Forbidden Rites: A Necromancer's Manual of the Fifteenth Century");
        titles.add("Forbidden Rites: A Necromancer's Manual of the Fifteenth Century (review)");
        titles.add("Annuaire de la communication en Rhône-Alpes");
        titles.add(OCULAR_TITLE);
        titles.add("Ocular tissue culture");
        Map<String, Long> result = piIntegrationService.findWrWrkInstsByTitles(titles);
        assertEquals(5, result.size());
        assertEquals(123059057L, result.get("Forbidden rites"), 0);
        assertEquals(123059058L,
            result.get("Forbidden rites : a necromancer's manual of the fifteenth century"), 0);
        assertEquals(156427025L, result.get("Annuaire de la communication en Rhône-Alpes"), 0);
        assertEquals(123067577L, result.get(OCULAR_TITLE), 0);
        assertEquals(113747840L, result.get("Ocular tissue culture"), 0);
        verify(rupEsApi, searchResponse, searchResults, searchHit1, searchHit2, searchHit3, searchHit4, searchHit5,
            searchHit6, searchHit7);
    }

    private void expectGetSearchResponseByTitle() {
        expect(searchResponse.getResults()).andReturn(searchResults).times(7);
        expect(searchResults.getHits()).andReturn(Collections.singletonList(searchHit1)).once();
        expect(searchResults.getHits()).andReturn(Collections.singletonList(searchHit2)).once();
        expect(searchResults.getHits()).andReturn(Collections.emptyList()).once();
        expect(searchResults.getHits()).andReturn(Collections.emptyList()).once();
        expect(searchResults.getHits()).andReturn(Collections.singletonList(searchHit5)).once();
        expect(searchResults.getHits()).andReturn(Collections.singletonList(searchHit6)).once();
        expect(searchResults.getHits()).andReturn(Collections.singletonList(searchHit7)).once();
        expect(rupEsApi.search(capture(requestCapture))).andReturn(searchResponse).times(7);
        expectSearchHitSource(searchHit1, "pi_search_hit1.json");
        expectSearchHitSource(searchHit2, "pi_search_hit2.json");
        expectSearchHitSource(searchHit5, "pi_search_hit5.json");
        expectSearchHitSource(searchHit6, "pi_search_hit6.json");
        expectSearchHitSource(searchHit7, "pi_search_hit7.json");
    }

    private void expectGetSearchResponseByIdno() {
        expectGetResponseWithIssn();
        expectGetResponseWithIsbn10();
        expectGetResponseWithIsbn13();
        expectGetResponseWithIdno();
        expectGetResponseWithIdnoAndTitle();
    }

    private void expectGetResponseWithIssn() {
        expect(searchResponse.getResults()).andReturn(searchResults).once();
        expect(searchResults.getHits()).andReturn(Collections.singletonList(searchHit1)).once();
        expectSearchHitSource(searchHit1, "pi_search_hit1.json");
        expect(rupEsApi.search(capture(requestCapture))).andReturn(searchResponse).once();
    }

    private void expectGetResponseWithIsbn10() {
        expect(searchResponse.getResults()).andReturn(searchResults).times(2);
        expect(searchResults.getHits()).andReturn(Collections.emptyList()).once();
        expect(searchResults.getHits()).andReturn(Collections.singletonList(searchHit2)).once();
        expectSearchHitSource(searchHit2, "pi_search_hit2.json");
        expect(rupEsApi.search(capture(requestCapture))).andReturn(searchResponse).times(2);
    }

    private void expectGetResponseWithIsbn13() {
        expect(searchResponse.getResults()).andReturn(searchResults).times(3);
        expect(searchResults.getHits()).andReturn(Collections.emptyList()).once();
        expect(searchResults.getHits()).andReturn(Collections.emptyList()).once();
        expect(searchResults.getHits()).andReturn(Collections.singletonList(searchHit3)).once();
        expectSearchHitSource(searchHit3, "pi_search_hit3.json");
        expect(rupEsApi.search(capture(requestCapture))).andReturn(searchResponse).times(3);
    }

    private void expectGetResponseWithIdno() {
        expect(searchResponse.getResults()).andReturn(searchResults).times(4);
        expect(searchResults.getHits()).andReturn(Arrays.asList(searchHit1, searchHit2)).once();
        expect(searchResults.getHits()).andReturn(Arrays.asList(searchHit1, searchHit2)).once();
        expect(searchResults.getHits()).andReturn(Arrays.asList(searchHit1, searchHit2)).once();
        expect(searchResults.getHits()).andReturn(Collections.singletonList(searchHit4)).once();
        expectSearchHitSource(searchHit4, "pi_search_hit4.json");
        expect(rupEsApi.search(capture(requestCapture))).andReturn(searchResponse).times(4);
    }

    private void expectGetResponseWithIdnoAndTitle() {
        expect(searchResponse.getResults()).andReturn(searchResults).times(4);
        expect(searchResults.getHits()).andReturn(Arrays.asList(searchHit1, searchHit2)).once();
        expect(searchResults.getHits()).andReturn(Arrays.asList(searchHit1, searchHit2)).once();
        expect(searchResults.getHits()).andReturn(Arrays.asList(searchHit1, searchHit2)).once();
        expect(searchResults.getHits()).andReturn(Arrays.asList(searchHit5, searchHit6)).once();
        expect(searchHit5.getFields()).andReturn(
            ImmutableMap.of("mainTitle", Collections.singletonList("Forbidden rites"))).once();
        expect(searchHit6.getFields()).andReturn(
            ImmutableMap.of("mainTitle", Collections.singletonList(OCULAR_TITLE))).once();
        expectSearchHitSource(searchHit6, "pi_search_hit6.json");
        expect(rupEsApi.search(capture(requestCapture))).andReturn(searchResponse).times(4);
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
