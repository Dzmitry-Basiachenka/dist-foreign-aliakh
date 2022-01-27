package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.foreign.domain.AclGrantDetail;

import java.util.List;

/**
 * Represents interface of repository for ACL grant details.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/27/2022
 *
 * @author Aliaksandr Liakh
 */
public interface IAclGrantDetailRepository {

    /**
     * Inserts ACL grant detail.
     *
     * @param grantDetail instance of {@link AclGrantDetail}
     */
    void insert(AclGrantDetail grantDetail);

    /**
     * Finds ACL grant details by their ids.
     *
     * @param grantDetailIds list of ids of the {@link AclGrantDetail}
     * @return list of {@link AclGrantDetail}s
     */
    List<AclGrantDetail> findByIds(List<String> grantDetailIds);
}
