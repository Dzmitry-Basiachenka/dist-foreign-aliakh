package com.copyright.rup.dist.foreign.domain;

/**
 * Represents ineligible reason case of UDM usage.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 07/01/2021
 *
 * @author Aliaksandr Liakh
 */
public enum UdmIneligibleReasonEnum {

    /**
     * No Reported Use.
     */
    NO_REPORTED_USE("18fbee56-2f5c-450a-999e-54903c0bfb23");

    private final String id;

    /**
     * Constructor.
     *
     * @param id ineligible reason id
     */
    UdmIneligibleReasonEnum(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
