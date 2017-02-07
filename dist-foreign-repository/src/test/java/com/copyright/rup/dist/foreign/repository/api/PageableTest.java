package com.copyright.rup.dist.foreign.repository.api;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Verifies {@link Pageable}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 02/03/17
 *
 * @author Aliaksei Pchelnikau
 */
public class PageableTest {

    private static final int OFFSET = 10;
    private static final int LIMIT = 20;
    private Pageable pageable;

    @Before
    public void setUp() {
        pageable = new Pageable(OFFSET, LIMIT);
    }

    @Test
    public void testGetOffset() {
        assertEquals(OFFSET, pageable.getOffset());
    }

    @Test
    public void testGetLimit() {
        assertEquals(LIMIT, pageable.getLimit());
    }
}
