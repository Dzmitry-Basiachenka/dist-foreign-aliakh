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

    private static final String FORBIDDEN_RITES_REVIEW =
        "Forbidden Rites: A Necromancer's Manual of the Fifteenth Century (review)";
    private static final String ANNUAIRE_TITLE = "Annuaire de la communication en Rhône-Alpes";
    private static final String OCULAR_TITLE = "Ocular Tissue Culture";
    private static final String FORBIDDEN_RITES = "Forbidden rites";
    private static final String VALISBN10 = "VALISBN10";
    private static final String VALISBN13 = "VALISBN13";
    private static final String IDNO_1 = "1140-9126";
    private static final String IDNO_2 = "978-0-271-01751-8";
    private static final String IDNO_3 = "978-0-08-027365-5";
    private static final String IDNO_4 = "10.1353/PGN.1999.0081";
    private static final String IDNO_5 = "978-0-271-01750-1";
    private static final String SEARCH_HIT_1 = "pi_search_hit1.json";
    private static final String SEARCH_HIT_2 = "pi_search_hit2.json";
    private static final String SEARCH_HIT_3 = "pi_search_hit3.json";

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
    public void testFindWorkByStandardNumber() {
        expectGetSearchResponseByStandardNumber();
        replay(rupEsApi, searchResponse, searchResults, searchHit1, searchHit2, searchHit3, searchHit4, searchHit5);
        assertEquals(new Work(123059057L, ANNUAIRE_TITLE, IDNO_1, "VALISSN"),
            piIntegrationService.findWorkByStandardNumber(IDNO_1));
        assertEquals(new Work(123059058L, FORBIDDEN_RITES, IDNO_2, VALISBN10),
            piIntegrationService.findWorkByStandardNumber("0-271-01750-3"));
        assertEquals(new Work(156427025L, FORBIDDEN_RITES, IDNO_3, VALISBN13),
            piIntegrationService.findWorkByStandardNumber(IDNO_3));
        assertEquals(new Work(112942199L, FORBIDDEN_RITES_REVIEW, IDNO_4, "DOI"),
            piIntegrationService.findWorkByStandardNumber(IDNO_4));
        assertEquals(new Work(123067577L, OCULAR_TITLE, IDNO_3, VALISBN13),
            piIntegrationService.findWorkByStandardNumber(IDNO_3));
        assertEquals(new Work(), piIntegrationService.findWorkByStandardNumber(IDNO_2));
        verify(rupEsApi, searchResponse, searchResults, searchHit1, searchHit2, searchHit3, searchHit4, searchHit5);
    }

    @Test
    public void testFindWorkByIdnoWithSingleHostIndo() {
        expect(searchResponse.getResults()).andReturn(searchResults).times(2);
        expect(searchResults.getHits()).andReturn(Collections.singletonList(searchHit1)).once();
        expect(searchHit1.getFields()).andReturn(ImmutableMap.of("hostIdno", Collections.singletonList(IDNO_2))).once();
        expect(searchResults.getHits()).andReturn(Collections.singletonList(searchHit2)).once();
        expectSearchHitSource(searchHit2, SEARCH_HIT_2);
        expect(rupEsApi.search(capture(requestCapture))).andReturn(searchResponse).times(2);
        replay(rupEsApi, searchResponse, searchResults, searchHit1, searchHit2);
        Work work = new Work(123059058L, FORBIDDEN_RITES, IDNO_2, VALISBN10);
        work.setHostIdnoFlag(true);
        assertEquals(work, piIntegrationService.findWorkByStandardNumber(IDNO_1));
        verify(rupEsApi, searchResponse, searchResults, searchHit1, searchHit2);
    }

    @Test
    public void testFindWorkByIdnoWithSingleHostIndoMultipleResult() {
        expect(searchResponse.getResults()).andReturn(searchResults).times(6);
        expect(searchResults.getHits()).andReturn(Collections.singletonList(searchHit1)).once();
        expect(searchHit1.getFields()).andReturn(ImmutableMap.of("hostIdno", Collections.singletonList(IDNO_2))).once();
        expect(searchResults.getHits()).andReturn(Collections.emptyList()).times(4);
        expect(searchResults.getHits()).andReturn(Arrays.asList(searchHit2, searchHit3)).once();
        expect(rupEsApi.search(capture(requestCapture))).andReturn(searchResponse).times(6);
        replay(rupEsApi, searchResponse, searchResults, searchHit1, searchHit2, searchHit3);
        assertEquals(new Work(), piIntegrationService.findWorkByStandardNumber(IDNO_1));
        verify(rupEsApi, searchResponse, searchResults, searchHit1, searchHit2, searchHit3);
    }

    @Test
    public void testFindWorkByIdnoWithMultipleHostIndos() {
        expect(searchResponse.getResults()).andReturn(searchResults).once();
        expect(searchResults.getHits()).andReturn(Collections.singletonList(searchHit1)).once();
        expect(searchHit1.getFields()).andReturn(ImmutableMap.of("hostIdno", Arrays.asList(IDNO_2, IDNO_3))).once();
        expect(rupEsApi.search(capture(requestCapture))).andReturn(searchResponse).once();
        expectSearchHitSource(searchHit1, SEARCH_HIT_1);
        replay(rupEsApi, searchResponse, searchResults, searchHit1);
        Work expectedResult = new Work();
        expectedResult.setMultipleMatches(true);
        assertEquals(expectedResult, piIntegrationService.findWorkByStandardNumber(IDNO_1));
        verify(rupEsApi, searchResponse, searchResults, searchHit1);
    }

    @Test
    public void testFindWorkByTitle() {
        expectGetSearchResponseByTitle();
        replay(rupEsApi, searchResponse, searchResults, searchHit1, searchHit2, searchHit3, searchHit4, searchHit5,
            searchHit6);
        assertEquals(new Work(123059057L, ANNUAIRE_TITLE, IDNO_1, "VALISSN"),
            piIntegrationService.findWorkByTitle(FORBIDDEN_RITES));
        assertEquals(new Work(123059058L, FORBIDDEN_RITES, IDNO_2, VALISBN10),
            piIntegrationService.findWorkByTitle("Forbidden rites : a necromancer's manual of the fifteenth century"));
        assertEquals(new Work(), piIntegrationService.findWorkByTitle(
            "Kieckhefer, Richard, Forbidden Rites: A Necromancer's Manual of the Fifteenth Century"));
        assertEquals(new Work(), piIntegrationService.findWorkByTitle(
            "Forbidden Rites: A Necromancer's Manual of the Fifteenth Century (review)"));
        assertEquals(new Work(156427025L, FORBIDDEN_RITES, IDNO_5, VALISBN13),
            piIntegrationService.findWorkByTitle(ANNUAIRE_TITLE));
        assertEquals(new Work(123067577L, OCULAR_TITLE, IDNO_3, VALISBN13),
            piIntegrationService.findWorkByTitle(OCULAR_TITLE));
        verify(rupEsApi, searchResponse, searchResults, searchHit1, searchHit2, searchHit3, searchHit4, searchHit5,
            searchHit6);
    }

    @Test
    public void testFindWorkByWrWrkInst() {
        expectGetSearchResponseByWrWrkInst();
        replay(rupEsApi, searchResponse, searchResults, searchHit1, searchHit2, searchHit3);
        assertEquals(new Work(123059057L, ANNUAIRE_TITLE, IDNO_1, "VALISSN"),
            piIntegrationService.findWorkByWrWrkInst(123059057L));
        assertEquals(new Work(123059058L, FORBIDDEN_RITES, IDNO_2, VALISBN10),
            piIntegrationService.findWorkByWrWrkInst(123059058L));
        assertEquals(new Work(156427025L, FORBIDDEN_RITES, IDNO_3, VALISBN13),
            piIntegrationService.findWorkByWrWrkInst(156427025L));
        assertEquals(new Work(), piIntegrationService.findWorkByWrWrkInst(1000009552L));
        verify(rupEsApi, searchResponse, searchResults, searchHit1, searchHit2, searchHit3);
    }

    @Test
    public void testBuildQueryString() {
        assertEquals("issn:\"\\\"0.1353\\/PGN.1999.0081\"",
            piIntegrationService.buildQueryString("issn", "   \"0.1353/PGN.1999.0081   "));
    }

    private void expectGetSearchResponseByWrWrkInst() {
        expect(searchResponse.getResults()).andReturn(searchResults).times(4);
        expect(rupEsApi.search(capture(requestCapture))).andReturn(searchResponse).times(4);
        expect(searchResults.getHits()).andReturn(Collections.singletonList(searchHit1)).once();
        expect(searchResults.getHits()).andReturn(Collections.singletonList(searchHit2)).once();
        expect(searchResults.getHits()).andReturn(Collections.singletonList(searchHit3)).once();
        expect(searchResults.getHits()).andReturn(Collections.emptyList()).once();
        expectSearchHitSource(searchHit1, SEARCH_HIT_1);
        expectSearchHitSource(searchHit2, SEARCH_HIT_2);
        expectSearchHitSource(searchHit3, SEARCH_HIT_3);
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
        expectSearchHitSourceWithEmptyFields(searchHit1, SEARCH_HIT_1);
        expectSearchHitSourceWithEmptyFields(searchHit2, SEARCH_HIT_2);
        expectSearchHitSourceWithEmptyFields(searchHit5, "pi_search_hit5.json");
        expectSearchHitSourceWithEmptyFields(searchHit6, "pi_search_hit6.json");
    }

    private void expectGetSearchResponseByStandardNumber() {
        expectGetResponseWithIssn();
        expectGetResponseWithIsbn10();
        expectGetResponseWithIsbn13();
        expectGetResponseWithDoi();
        expectGetResponseWithStdId();
        expectEmptySearchResponseByStdId();
    }

    private void expectGetResponseWithIssn() {
        expect(searchResponse.getResults()).andReturn(searchResults).once();
        expect(searchResults.getHits()).andReturn(Collections.singletonList(searchHit1)).once();
        expectSearchHitSourceWithEmptyFields(searchHit1, SEARCH_HIT_1);
        expect(rupEsApi.search(capture(requestCapture))).andReturn(searchResponse).once();
    }

    private void expectGetResponseWithIsbn10() {
        expect(searchResponse.getResults()).andReturn(searchResults).times(2);
        expect(searchResults.getHits()).andReturn(Collections.emptyList()).once();
        expect(searchResults.getHits()).andReturn(Collections.singletonList(searchHit2)).once();
        expectSearchHitSourceWithEmptyFields(searchHit2, SEARCH_HIT_2);
        expect(rupEsApi.search(capture(requestCapture))).andReturn(searchResponse).times(2);
    }

    private void expectGetResponseWithIsbn13() {
        expect(searchResponse.getResults()).andReturn(searchResults).times(3);
        expect(searchResults.getHits()).andReturn(Collections.emptyList()).once();
        expect(searchResults.getHits()).andReturn(Collections.emptyList()).once();
        expect(searchResults.getHits()).andReturn(Collections.singletonList(searchHit3)).once();
        expectSearchHitSourceWithEmptyFields(searchHit3, SEARCH_HIT_3);
        expect(rupEsApi.search(capture(requestCapture))).andReturn(searchResponse).times(3);
    }

    private void expectGetResponseWithDoi() {
        expect(searchResponse.getResults()).andReturn(searchResults).times(4);
        expect(searchResults.getHits()).andReturn(Arrays.asList(searchHit1, searchHit2)).once();
        expect(searchResults.getHits()).andReturn(Arrays.asList(searchHit1, searchHit2)).once();
        expect(searchResults.getHits()).andReturn(Arrays.asList(searchHit1, searchHit2)).once();
        expect(searchResults.getHits()).andReturn(Collections.singletonList(searchHit4)).once();
        expectSearchHitSourceWithEmptyFields(searchHit4, "pi_search_hit4.json");
        expect(rupEsApi.search(capture(requestCapture))).andReturn(searchResponse).times(4);
    }

    private void expectGetResponseWithStdId() {
        expect(searchResponse.getResults()).andReturn(searchResults).times(5);
        expect(searchResults.getHits()).andReturn(Arrays.asList(searchHit1, searchHit2)).once();
        expect(searchResults.getHits()).andReturn(Arrays.asList(searchHit1, searchHit2)).once();
        expect(searchResults.getHits()).andReturn(Arrays.asList(searchHit1, searchHit2)).once();
        expect(searchResults.getHits()).andReturn(Arrays.asList(searchHit1, searchHit2)).once();
        expect(searchResults.getHits()).andReturn(Collections.singletonList(searchHit5)).once();
        expectSearchHitSourceWithEmptyFields(searchHit5, "pi_search_hit6.json");
        expect(rupEsApi.search(capture(requestCapture))).andReturn(searchResponse).times(5);
    }

    private void expectEmptySearchResponseByStdId() {
        expect(searchResponse.getResults()).andReturn(searchResults).times(5);
        expect(searchResults.getHits()).andReturn(Collections.emptyList()).times(5);
        expect(rupEsApi.search(capture(requestCapture))).andReturn(searchResponse).times(5);
    }

    private void expectSearchHitSourceWithEmptyFields(RupSearchHit searchHit, String sourceFileName) {
        expect(searchHit.getFields()).andReturn(Collections.emptyMap()).once();
        expectSearchHitSource(searchHit, sourceFileName);
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
