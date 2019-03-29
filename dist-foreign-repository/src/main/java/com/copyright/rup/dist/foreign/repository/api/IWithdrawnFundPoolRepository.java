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
}
