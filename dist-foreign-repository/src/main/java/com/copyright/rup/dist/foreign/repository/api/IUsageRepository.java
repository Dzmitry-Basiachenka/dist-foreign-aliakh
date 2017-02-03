package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.foreign.domain.Usage;

/**
 * Interface for Usage repository.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/02/17
 *
 * @author Darya Baraukova
 */
public interface IUsageRepository {

    /**
     * Inserts Usage into database.
     *
     * @param usage {@link Usage} instance
     * @return count of inserted records
     */
    int insertUsage(Usage usage);
}
