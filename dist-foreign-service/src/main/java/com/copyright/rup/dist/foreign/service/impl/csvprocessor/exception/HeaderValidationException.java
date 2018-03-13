package com.copyright.rup.dist.foreign.service.impl.csvprocessor.exception;

import org.apache.commons.lang3.ArrayUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents header validation exception.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/15/17
 *
 * @author Aliaksei Pchelnikau
 */
public class HeaderValidationException extends ValidationException {

    private static final String ERROR_MESSAGE = "Columns headers are incorrect. Expected columns headers are:%n%s";
    private static final String ERROR_MESSAGE_WITH_FILE_HEADERS = ERROR_MESSAGE + "%nFile headers:%n%s";
    private final List<String> expectedHeaders;

    /**
     * Constructor.
     *
     * @param expectedHeaders expected headers
     * @param fileHeaders     actual headers
     */
    public HeaderValidationException(List<String> expectedHeaders, String... fileHeaders) {
        super(String.format(ERROR_MESSAGE_WITH_FILE_HEADERS, String.join(", ", expectedHeaders),
            getJoinedHeaders(fileHeaders)));
        this.expectedHeaders = expectedHeaders;
    }

    private static String getJoinedHeaders(String... fileHeaders) {
        return ArrayUtils.isNotEmpty(fileHeaders) ? String.join(", ", fileHeaders) : "''";
    }

    @Override
    public String getHtmlMessage() {
        String joinedHeaders =
            expectedHeaders.stream().collect(Collectors.joining("</li><li>", "<ul><li>", "</li></ul>"));
        return String.format(ERROR_MESSAGE, joinedHeaders);
    }
}
