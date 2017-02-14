package com.copyright.rup.dist.foreign.repository.api;

/**
 * Represents domain to hold offset and limit values.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 02/03/17
 *
 * @author Aliaksei Pchelnikau
 */
// TODO {mbezmen} move to dist-common
public class Pageable {

    private int offset;
    private int limit;

    /**
     * Constructor.
     *
     * @param offset offset
     * @param limit  limit
     */
    public Pageable(int offset, int limit) {
        this.offset = offset;
        this.limit = limit;
    }

    /**
     * @return offset.
     */
    public int getOffset() {
        return offset;
    }

    /**
     * @return limit.
     */
    public int getLimit() {
        return limit;
    }
}
