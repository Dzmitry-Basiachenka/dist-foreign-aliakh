package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.foreign.domain.AclFundPool;
import com.copyright.rup.dist.foreign.domain.AclFundPoolDetail;

import java.util.List;

/**
 * Represents interface of repository for ACL fund pools.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/20/2022
 *
 * @author Anton Azarenka
 */
public interface IAclFundPoolRepository {

    /**
     * Inserts {@link AclFundPool}.
     *
     * @param fundPool instance of {@link AclFundPool}
     */
    void insert(AclFundPool fundPool);

    /**
     * Finds {@link AclFundPool} by id.
     *
     * @param fundPoolId fund pool id
     * @return instance of {@link AclFundPool} or null if nothing found
     */
    AclFundPool findById(String fundPoolId);

    /**
     * Checks whether {@link AclFundPool} with the name already exists.
     *
     * @param name fund pool name
     * @return {@code true} - if fund pool exists, {@code false} - otherwise
     */
    boolean isFundPoolExists(String name);

    /**
     * Inserts {@link AclFundPoolDetail}.
     *
     * @param detail instance of {@link AclFundPoolDetail}
     */
    void insertDetail(AclFundPoolDetail detail);

    /**
     * Finds {@link AclFundPoolDetail}s by {@link AclFundPool} id.
     *
     * @param fundPoolId {@link AclFundPool} id
     * @return list of {@link AclFundPoolDetail}s
     */
    List<AclFundPoolDetail> findDetailsByFundPoolId(String fundPoolId);
}