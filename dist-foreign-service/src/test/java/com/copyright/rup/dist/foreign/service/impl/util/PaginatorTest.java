package com.copyright.rup.dist.foreign.service.impl.util;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.repository.api.Pageable;

import org.junit.Test;

import java.util.List;

/**
 * Verifies {@link Paginator}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 02/28/2018
 *
 * @author Aliaksandr Liakh
 */
public class PaginatorTest {

    @Test(expected = IllegalArgumentException.class)
    public void testBadCount() {
        new Paginator(-1, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBadPageSize() {
        new Paginator(1, 0);
    }

    @Test
    public void testCount0() {
        Paginator paginator = new Paginator(0, 2);
        List<Pageable> pageables = paginator.getPageables();
        assertEquals(0, pageables.size());
    }

    @Test
    public void testCount1() {
        Paginator paginator = new Paginator(1, 2);
        List<Pageable> pageables = paginator.getPageables();
        assertEquals(1, pageables.size());
        assertPageable(pageables.get(0), 0, 2);
    }

    @Test
    public void testCount2() {
        Paginator paginator = new Paginator(2, 2);
        List<Pageable> pageables = paginator.getPageables();
        assertEquals(1, pageables.size());
        assertPageable(pageables.get(0), 0, 2);
    }

    @Test
    public void testCount3() {
        Paginator paginator = new Paginator(3, 2);
        List<Pageable> pageables = paginator.getPageables();
        assertEquals(2, pageables.size());
        assertPageable(pageables.get(0), 0, 2);
        assertPageable(pageables.get(1), 2, 2);
    }

    @Test
    public void testCount4() {
        Paginator paginator = new Paginator(4, 2);
        List<Pageable> pageables = paginator.getPageables();
        assertEquals(2, pageables.size());
        assertPageable(pageables.get(0), 0, 2);
        assertPageable(pageables.get(1), 2, 2);
    }

    @Test
    public void testCount5() {
        Paginator paginator = new Paginator(5, 2);
        List<Pageable> pageables = paginator.getPageables();
        assertEquals(3, pageables.size());
        assertPageable(pageables.get(0), 0, 2);
        assertPageable(pageables.get(1), 2, 2);
        assertPageable(pageables.get(2), 4, 2);
    }

    private void assertPageable(Pageable page, int offset, int limit) {
        assertEquals(offset, page.getOffset());
        assertEquals(limit, page.getLimit());
    }
}
