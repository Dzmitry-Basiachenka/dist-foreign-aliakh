package com.copyright.rup.dist.foreign.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletResponse;

/**
 * Verifies {@link com.copyright.rup.dist.foreign.ui.rest.JobRunnerRestFilter}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 04/13/2018
 *
 * @author Uladzislau Shalamitski
 */
public class JobRunnerIntegrationTest {

    private static final String ENDPOINT_URL = "http://localhost:18321/dist-foreign-ui";

    @Test
    public void testRunRightsholderJob() throws InterruptedException, URISyntaxException, IOException {
        runJob(RequestBuilder.get(ENDPOINT_URL + "/job/rightsholder"), HttpServletResponse.SC_OK);
    }

    @Test
    public void testRunRaJob() throws InterruptedException, URISyntaxException, IOException {
        runJob(RequestBuilder.get(ENDPOINT_URL + "/job/ra"), HttpServletResponse.SC_OK);
    }

    @Test
    public void testRunCrmJob() throws InterruptedException, URISyntaxException, IOException {
        runJob(RequestBuilder.get(ENDPOINT_URL + "/job/crm"), HttpServletResponse.SC_OK);
    }

    @Test
    public void testRunPiJob() throws InterruptedException, URISyntaxException, IOException {
        runJob(RequestBuilder.get(ENDPOINT_URL + "/job/pi"), HttpServletResponse.SC_OK);
    }

    @Test
    public void testCheckStatus() throws InterruptedException, URISyntaxException, IOException {
        runJob(RequestBuilder.get(ENDPOINT_URL + "/job/status"), HttpServletResponse.SC_OK);
    }

    @Test
    public void testWrongUrl() throws InterruptedException, URISyntaxException, IOException {
        runJob(RequestBuilder.get(ENDPOINT_URL + "/job/statuses"), HttpServletResponse.SC_NOT_FOUND);
    }

    private void runJob(RequestBuilder requestBuilder, int expectedStatus)
        throws InterruptedException, URISyntaxException, IOException {
        int status;
        while (HttpServletResponse.SC_ACCEPTED == (status = sendRequest(requestBuilder))) {
            Thread.sleep(50);
        }
        assertEquals(expectedStatus, status);
    }

    private int sendRequest(RequestBuilder requestBuilder) throws URISyntaxException, IOException {
        return requestBuilder.execute().getStatusLine().getStatusCode();
    }
}
