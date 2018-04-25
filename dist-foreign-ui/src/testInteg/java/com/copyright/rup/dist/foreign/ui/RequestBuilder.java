package com.copyright.rup.dist.foreign.ui;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Rest request builder.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 04/13/2018
 *
 * @author Uladzislau Shalamitski
 */
final class RequestBuilder {

    private final HttpClient client;
    private HttpRequestBase request;

    private RequestBuilder() {
        client = HttpClientBuilder.create().build();
    }

    /**
     * Constructor for get method.
     *
     * @param uri uri
     * @return RequestBuilder instance
     */
    static RequestBuilder get(String uri) {
        RequestBuilder builder = new RequestBuilder();
        builder.request = new HttpGet(uri);
        return builder;
    }

    /**
     * Execute request.
     *
     * @return response
     * @throws IOException        exception
     * @throws URISyntaxException exception
     */
    HttpResponse execute() throws IOException, URISyntaxException {
        return client.execute(request);
    }
}
