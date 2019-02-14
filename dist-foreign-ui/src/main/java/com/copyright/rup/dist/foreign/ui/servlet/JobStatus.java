package com.copyright.rup.dist.foreign.ui.servlet;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Scheduled job status.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 02/14/2019
 *
 * @author Aliaksanr Liakh
 */
class JobStatus {

    @JsonProperty("status")
    private String status;

    String getStatus() {
        return status;
    }

    void setStatus(String status) {
        this.status = status;
    }
}

