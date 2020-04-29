package com.copyright.rup.dist.foreign.service.impl.common.chunk;

import org.springframework.stereotype.Component;

/**
 * Formatter for Camel endpoint URIs for chunks-based processing.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 04/29/2020
 *
 * @author Aliaksandr Liakh
 */
@Component
public class ChunkUriFormatter {

    /**
     * Formats the URI for chunks-based processing from the common URI.
     *
     * @param uri the common URI
     * @return the URI for chunks-based processing
     */
    public String format(String uri) {
        int i = uri.indexOf('?');
        if (i >= 0) {
            return uri.substring(0, i) + "-chunk" + uri.substring(i);
        } else {
            return uri + "-chunk";
        }
    }
}
