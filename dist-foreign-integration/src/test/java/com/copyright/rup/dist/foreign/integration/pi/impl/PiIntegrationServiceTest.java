package com.copyright.rup.dist.foreign.integration.pi.impl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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

    private static final String OCULAR_TITLE = "Ocular Tissue Culture";
    private PiIntegrationProxyService piIntegrationProxyService;
    private RupEsApi rupEsApi;
    private RupSearchHit searchHit1;
    private RupSearchHit searchHit2;
    private RupSearchHit searchHit3;
    private RupSearchHit searchHit4;
    private RupSearchHit searchHit5;
    private RupSearchHit searchHit6;
    private RupSearchResponse searchResponse;
    private RupSearchResults searchResults;
    private Capture<RupSearchRequest> requestCapture;

    @Before
    public void setUp() {
        PiIntegrationService piIntegrationService = new PiIntegrationServiceMock();
        piIntegrationService.init();
        rupEsApi = piIntegrationService.getRupEsApi();
        piIntegrationProxyService = new PiIntegrationProxyService(piIntegrationService, 1);
        searchHit1 = createMock(RupSearchHit.class);
        searchHit2 = createMock(RupSearchHit.class);
        searchHit3 = createMock(RupSearchHit.class);
        searchHit4 = createMock(RupSearchHit.class);
        searchHit5 = createMock(RupSearchHit.class);
        searchHit6 = createMock(RupSearchHit.class);
        searchResponse = createMock(RupSearchResponse.class);
        searchResults = createMock(RupSearchResults.class);
        requestCapture = new Capture<>();
    }

    @Test
    public void testFindWorkByIdnoAndTitle() {
        expectGetSearchResponseByIdno();
        replay(rupEsApi, searchResponse, searchResults, searchHit1, searchHit2, searchHit3, searchHit4, searchHit5,
            searchHit6);
        Work work1 = piIntegrationProxyService.findWorkByIdnoAndTitle("1140-9126", null);
        Work work2 = piIntegrationProxyService.findWorkByIdnoAndTitle("0-271-01750-3", null);
        Work work3 = piIntegrationProxyService.findWorkByIdnoAndTitle("978-0-08-027365-5", null);
        Work work4 = piIntegrationProxyService.findWorkByIdnoAndTitle("10.1353/PGN.1999.0081", null);
        Work work5 = piIntegrationProxyService.findWorkByIdnoAndTitle("978-0-271-01751-8", OCULAR_TITLE);
        Work work6 = piIntegrationProxyService.findWorkByIdnoAndTitle("978-0-08-027365-5", null);
        Work work7 = piIntegrationProxyService.findWorkByIdnoAndTitle("1140-9126", null);
        Work work8 = piIntegrationProxyService.findWorkByIdnoAndTitle("1140-9126", null);
        Work work9 = piIntegrationProxyService.findWorkByIdnoAndTitle("978-0-271-01751-8", OCULAR_TITLE);
        assertEquals(123059057L, work1.getWrWrkInst(), 0);
        assertEquals(123059058L, work2.getWrWrkInst(), 0);
        assertEquals(156427025L, work3.getWrWrkInst(), 0);
        assertEquals(112942199L, work4.getWrWrkInst(), 0);
        assertEquals(123067577L, work5.getWrWrkInst(), 0);
        assertEquals(156427025L, work6.getWrWrkInst(), 0);
        assertEquals(123059057L, work7.getWrWrkInst(), 0);
        assertEquals(123059057L, work8.getWrWrkInst(), 0);
        assertEquals(123067577L, work9.getWrWrkInst(), 0);
        verify(rupEsApi, searchResponse, searchResults, searchHit1, searchHit2, searchHit3, searchHit4, searchHit5,
            searchHit6);
    }

    @Test
    public void testFindWrWrkInstsByTitle() {
        expectGetSearchResponseByTitle();
        replay(rupEsApi, searchResponse, searchResults, searchHit1, searchHit2, searchHit3, searchHit4, searchHit5,
            searchHit6);
        Long result1 = piIntegrationProxyService.findWrWrkInstByTitle("Forbidden rites");
        Long result2 = piIntegrationProxyService.findWrWrkInstByTitle(
            "Forbidden rites : a necromancer's manual of the fifteenth century");
        Long result3 = piIntegrationProxyService.findWrWrkInstByTitle(
            "Kieckhefer, Richard, Forbidden Rites: A Necromancer's Manual of the Fifteenth Century");
        Long result4 = piIntegrationProxyService.findWrWrkInstByTitle(
            "Forbidden Rites: A Necromancer's Manual of the Fifteenth Century (review)");
        Long result5 = piIntegrationProxyService.findWrWrkInstByTitle("Annuaire de la communication en Rhône-Alpes");
        Long result6 = piIntegrationProxyService.findWrWrkInstByTitle(OCULAR_TITLE);
        Long result7 = piIntegrationProxyService.findWrWrkInstByTitle("Forbidden rites");
        Long result8 = piIntegrationProxyService.findWrWrkInstByTitle(OCULAR_TITLE);
        assertEquals(123059057L, result1, 0);
        assertEquals(123059058L, result2, 0);
        assertNull(result3);
        assertNull(result4);
        assertEquals(156427025L, result5, 0);
        assertEquals(123067577L, result6, 0);
        assertEquals(123059057L, result7, 0);
        assertEquals(123067577L, result8, 0);
        verify(rupEsApi, searchResponse, searchResults, searchHit1, searchHit2, searchHit3, searchHit4, searchHit5,
            searchHit6);
    }

    private void expectGetSearchResponseByTitle() {
        expect(searchResponse.getResults()).andReturn(searchResults).times(6);
        expect(searchResults.getHits()).andReturn(Collections.singletonList(searchHit1)).once();
        expect(searchResults.getHits()).andReturn(Collections.singletonList(searchHit2)).once();
        expect(searchResults.getHits()).andReturn(Collections.emptyList()).once();
        expect(searchResults.getHits()).andReturn(Collections.emptyList()).once();
        expect(searchResults.getHits()).andReturn(Collections.singletonList(searchHit5)).once();
        expect(searchResults.getHits()).andReturn(Collections.singletonList(searchHit6)).once();
        expect(rupEsApi.search(capture(requestCapture))).andReturn(searchResponse).times(6);
        expectSearchHitSource(searchHit1, "pi_search_hit1.json");
        expectSearchHitSource(searchHit2, "pi_search_hit2.json");
        expectSearchHitSource(searchHit5, "pi_search_hit5.json");
        expectSearchHitSource(searchHit6, "pi_search_hit6.json");
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
