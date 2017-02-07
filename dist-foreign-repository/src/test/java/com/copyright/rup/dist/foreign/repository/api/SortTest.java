package com.copyright.rup.dist.foreign.repository.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

/**
 * Verifies {@link Sort}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 02/03/17
 *
 * @author Aliaksei Pchelnikau
 */
public class SortTest {

    private static final String PROPERTY = "name";
    private static final Sort.Direction DIRECTION = Sort.Direction.ASC;
    private Sort sort;

    @Before
    public void setUp() {
        sort = new Sort(PROPERTY, DIRECTION);
    }

    @Test
    public void testCreate() {
        sort = Sort.create(new Object[]{PROPERTY}, true);
        assertNotNull(sort);
        assertEquals(PROPERTY, sort.getProperty());
        assertEquals(DIRECTION.getValue(), sort.getDirection());
    }

    @Test
    public void testCreateWithoutSortStates() {
        assertNull(Sort.create(new Object[]{PROPERTY}));
    }

    @Test
    public void testCreateWithoutSortPropertyIds() {
        assertNull(Sort.create(null, true));
    }

    @Test
    public void testGetProperty() {
        assertEquals(PROPERTY, sort.getProperty());
    }

    @Test
    public void testGetDirection() {
        assertEquals(DIRECTION.getValue(), sort.getDirection());
    }

    @Test
    public void testSortDirectionOf() {
        assertEquals(Sort.Direction.ASC, Sort.Direction.of(true));
        assertEquals(Sort.Direction.DESC, Sort.Direction.of(false));
    }
}
