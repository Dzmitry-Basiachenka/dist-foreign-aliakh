package com.copyright.rup.dist.foreign.service.impl.util;

import static com.google.common.base.Preconditions.checkArgument;

import com.copyright.rup.dist.foreign.repository.api.Pageable;

import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Creates list of {@link Pageable} to paginate PostgreSQL queries.
 * <a href="https://www.postgresql.org/docs/9.6/static/queries-limit.html">LIMIT and OFFSET/a>
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 02/28/2018
 *
 * @author Aliaksandr Liakh
 */
public final class Paginator {

    private final int count;
    private final int pageSize;
    private final int pagesCount;
    private final List<Pageable> pageables;

    /**
     * Constructor.
     *
     * @param count    items count.
     * @param pageSize page size.
     */
    public Paginator(int count, int pageSize) {
        checkArgument(count >= 0);
        checkArgument(pageSize > 0);
        this.count = count;
        this.pageSize = pageSize;
        this.pagesCount = 0 == count ? 0 : ((count - 1) / pageSize) + 1;
        this.pageables = IntStream.range(0, pagesCount)
            .mapToObj(page -> new Pageable(page * pageSize, pageSize))
            .collect(Collectors.collectingAndThen(Collectors.toList(), ImmutableList::copyOf));
    }

    int getCount() {
        return count;
    }

    int getPageSize() {
        return pageSize;
    }

    int getPagesCount() {
        return pagesCount;
    }

    public List<Pageable> getPageables() {
        return pageables;
    }
}
