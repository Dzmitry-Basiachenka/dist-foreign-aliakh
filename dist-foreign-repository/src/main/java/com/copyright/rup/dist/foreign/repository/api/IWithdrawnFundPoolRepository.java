package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.foreign.domain.WithdrawnFundPool;

import java.util.List;

/**
 * Interface for {@link WithdrawnFundPool} repository.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 03/26/2019
 *
 * @author Aliaksandr Liakh
 */
public interface IWithdrawnFundPoolRepository {

    /**
     * Inserts NTS withdrawn fund pool.
     *
     * @param fundPool instance of {@link WithdrawnFundPool}
     */
    void insert(WithdrawnFundPool fundPool);

    /**
     * Finds NTS withdrawn fund pool by id.
     *
     * @param fundPoolId fund pool id
     * @return instance of {@link WithdrawnFundPool} or null if nothing found
     */
    WithdrawnFundPool findById(String fundPoolId);

    /**
     * Deletes NTS withdrawn fund pool by id.
     *
     * @param fundPoolId fund pool id
     * @return number of deleted records
     */
    int delete(String fundPoolId);

    /**
     * Finds all {@link WithdrawnFundPool}s.
     *
     * @return list of {@link WithdrawnFundPool}s
     */
    List<WithdrawnFundPool> findAll();

    /**
     * Find fund pool names associated with batch identifier.
     *
     * @param batchId batch identifier
     * @return list of NTS withdrawn fund pool names
     */
    List<String> findNamesByUsageBatchId(String batchId);

    /**
     * Finds {@link WithdrawnFundPool}s count by the name.
     *
     * @param fundPoolName {@link WithdrawnFundPool} name
     * @return count of found {@link WithdrawnFundPool}s
     */
    int findCountByName(String fundPoolName);
}
