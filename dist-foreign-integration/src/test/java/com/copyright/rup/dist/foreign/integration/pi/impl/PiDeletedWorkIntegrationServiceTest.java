package com.copyright.rup.dist.foreign.integration.pi.impl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.newCapture;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.es.api.RupEsApi;
import com.copyright.rup.es.api.RupSearchResponse;
import com.copyright.rup.es.api.domain.RupSearchHit;
import com.copyright.rup.es.api.domain.RupSearchResults;
import com.copyright.rup.es.api.request.RupSearchRequest;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Verifies {@link PiDeletedWorkIntegrationService}.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 03/06/23
 *
 * @author Mikita Maistrenka
 */
public class PiDeletedWorkIntegrationServiceTest {

    private PiDeletedWorkIntegrationService piDeletedWorkIntegrationService;
    private RupEsApi rupEsApi;
    private RupSearchHit searchHit;
    private RupSearchResponse searchResponse;
    private RupSearchResults searchResults;
    private Capture<RupSearchRequest> requestCapture;

    @Before
    public void setUp() {
        piDeletedWorkIntegrationService = new PiWorkDeletedIntegrationServiceMock();
        rupEsApi = piDeletedWorkIntegrationService.getRupEsApi();
        searchHit = createMock(RupSearchHit.class);
        searchResponse = createMock(RupSearchResponse.class);
        searchResults = createMock(RupSearchResults.class);
        requestCapture = newCapture();
    }

    @Test
    public void testIsDeletedWork() {
        expect(searchResponse.getResults()).andReturn(searchResults).times(2);
        expect(rupEsApi.search(capture(requestCapture))).andReturn(searchResponse).times(2);
        expect(searchResults.getHits()).andReturn(List.of(searchHit)).once();
        expect(searchResults.getHits()).andReturn(List.of()).once();
        replay(rupEsApi, searchResponse, searchResults, searchHit);
        assertTrue(piDeletedWorkIntegrationService.isDeletedWork(123059057L));
        assertFalse(piDeletedWorkIntegrationService.isDeletedWork(1000009552L));
        verify(rupEsApi, searchResponse, searchResults, searchHit);
    }

    private static class PiWorkDeletedIntegrationServiceMock extends PiDeletedWorkIntegrationService {

        private final RupEsApi rupEsApiMock = createMock(RupEsApi.class);

        @Override
        protected RupEsApi getRupEsApi() {
            return rupEsApiMock;
        }
    }
}
