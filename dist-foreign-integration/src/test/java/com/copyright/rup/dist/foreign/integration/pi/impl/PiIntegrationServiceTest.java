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

    private static final String ANNUAIRE_TITLE = "Annuaire de la communication en Rhône-Alpes";
    private static final String OCULAR_TITLE = "Ocular Tissue Culture";
    private static final String FORBIDDEN_RIGHTS = "Forbidden rites";
    private static final String VALISBN13 = "VALISBN13";

    private PiIntegrationService piIntegrationService;
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
        piIntegrationService = new PiIntegrationServiceMock();
        piIntegrationService.init();
        rupEsApi = piIntegrationService.getRupEsApi();
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
        assertEquals(new Work(123059057L, ANNUAIRE_TITLE, "VALISSN"),
            piIntegrationService.findWorkByIdnoAndTitle("1140-9126", null));
        assertEquals(new Work(123059058L, FORBIDDEN_RIGHTS, "VALISBN10"),
            piIntegrationService.findWorkByIdnoAndTitle("0-271-01750-3", null));
        assertEquals(new Work(156427025L, FORBIDDEN_RIGHTS, VALISBN13),
            piIntegrationService.findWorkByIdnoAndTitle("978-0-08-027365-5", null));
        assertEquals(
            new Work(112942199L, "Forbidden Rites: A Necromancer's Manual of the Fifteenth Century (review)", "DOI"),
            piIntegrationService.findWorkByIdnoAndTitle("10.1353/PGN.1999.0081", null));
        assertEquals(new Work(123067577L, OCULAR_TITLE, VALISBN13),
            piIntegrationService.findWorkByIdnoAndTitle("978-0-271-01751-8", OCULAR_TITLE));
        assertEquals(new Work(), piIntegrationService.findWorkByIdnoAndTitle("978-0-271-01751-8", "Medical Journal"));
        verify(rupEsApi, searchResponse, searchResults, searchHit1, searchHit2, searchHit3, searchHit4, searchHit5,
            searchHit6);
    }

    @Test
    public void testFindWorkByTitle() {
        expectGetSearchResponseByTitle();
        replay(rupEsApi, searchResponse, searchResults, searchHit1, searchHit2, searchHit3, searchHit4, searchHit5,
            searchHit6);
        assertEquals(new Work(123059057L, ANNUAIRE_TITLE, "VALISSN"),
            piIntegrationService.findWorkByTitle(FORBIDDEN_RIGHTS));
        assertEquals(new Work(123059058L, FORBIDDEN_RIGHTS, "VALISBN10"),
            piIntegrationService.findWorkByTitle("Forbidden rites : a necromancer's manual of the fifteenth century"));
        assertEquals(new Work(), piIntegrationService.findWorkByTitle(
            "Kieckhefer, Richard, Forbidden Rites: A Necromancer's Manual of the Fifteenth Century"));
        assertEquals(new Work(), piIntegrationService.findWorkByTitle(
            "Forbidden Rites: A Necromancer's Manual of the Fifteenth Century (review)"));
        assertEquals(new Work(156427025L, FORBIDDEN_RIGHTS, VALISBN13),
            piIntegrationService.findWorkByTitle(ANNUAIRE_TITLE));
        assertEquals(new Work(123067577L, OCULAR_TITLE, VALISBN13),
            piIntegrationService.findWorkByTitle(OCULAR_TITLE));
        verify(rupEsApi, searchResponse, searchResults, searchHit1, searchHit2, searchHit3, searchHit4, searchHit5,
            searchHit6);
    }

    @Test
    public void testFindWorkByWrWrkInst() {
        expectGetSearchResponseByWrWrkInst();
        replay(rupEsApi, searchResponse, searchResults, searchHit1, searchHit2, searchHit3);
        assertEquals(new Work(123059057L, ANNUAIRE_TITLE, "VALISSN"),
            piIntegrationService.findWorkByWrWrkInst(123059057L));
        assertEquals(new Work(123059058L, FORBIDDEN_RIGHTS, "VALISBN10"),
            piIntegrationService.findWorkByWrWrkInst(123059058L));
        assertEquals(new Work(156427025L, FORBIDDEN_RIGHTS, VALISBN13),
            piIntegrationService.findWorkByWrWrkInst(156427025L));
        assertEquals(new Work(), piIntegrationService.findWorkByWrWrkInst(1000009552L));
        verify(rupEsApi, searchResponse, searchResults, searchHit1, searchHit2, searchHit3);
    }

    @Test
    public void testBuildQueryString() {
        assertEquals("idno:\"\\\"0.1353\\/PGN.1999.0081\"",
            piIntegrationService.buildQueryString("idno", "   \"0.1353/PGN.1999.0081   "));
    }

    private void expectGetSearchResponseByWrWrkInst() {
        expect(searchResponse.getResults()).andReturn(searchResults).times(4);
        expect(rupEsApi.search(capture(requestCapture))).andReturn(searchResponse).times(4);
        expect(searchResults.getHits()).andReturn(Collections.singletonList(searchHit1)).once();
        expect(searchResults.getHits()).andReturn(Collections.singletonList(searchHit2)).once();
        expect(searchResults.getHits()).andReturn(Collections.singletonList(searchHit3)).once();
        expect(searchResults.getHits()).andReturn(Collections.emptyList()).once();
        expectSearchHitSource(searchHit1, "pi_search_hit1.json");
        expectSearchHitSource(searchHit2, "pi_search_hit2.json");
        expectSearchHitSource(searchHit3, "pi_search_hit3.json");
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
        getEmptySearchResponseByIdnoAndTitle();
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
            ImmutableMap.of("mainTitle", Collections.singletonList(FORBIDDEN_RIGHTS))).once();
        expect(searchHit6.getFields()).andReturn(
            ImmutableMap.of("mainTitle", Collections.singletonList(OCULAR_TITLE))).once();
        expectSearchHitSource(searchHit6, "pi_search_hit6.json");
        expect(rupEsApi.search(capture(requestCapture))).andReturn(searchResponse).times(4);
    }

    private void getEmptySearchResponseByIdnoAndTitle() {
        expect(searchResponse.getResults()).andReturn(searchResults).times(4);
        expect(searchResults.getHits()).andReturn(Collections.emptyList()).times(4);
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
