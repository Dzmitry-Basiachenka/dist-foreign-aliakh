package com.copyright.rup.dist.foreign.ui.servlet;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Error status.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 02/14/2019
 *
 * @author Aliaksanr Liakh
 */
class ErrorStatus {

    @JsonProperty("error")
    private String error;

    @JsonProperty("message")
    private String message;

    @JsonProperty("stackTrace")
    private String stackTrace;

    String getError() {
        return error;
    }

    void setError(String error) {
        this.error = error;
    }

    String getMessage() {
        return message;
    }

    void setMessage(String message) {
        this.message = message;
    }

    String getStackTrace() {
        return stackTrace;
    }

    void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }
}

