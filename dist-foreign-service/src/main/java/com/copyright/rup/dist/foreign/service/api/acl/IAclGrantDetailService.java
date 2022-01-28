package com.copyright.rup.dist.foreign.service.api.acl;

import com.copyright.rup.dist.foreign.domain.AclGrantDetail;

import java.util.List;

/**
 * Represents interface of repository for ACL grant sets and grant details.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/28/2022
 *
 * @author Aliaksandr Liakh
 */
public interface IAclGrantDetailService {

    /**
     * Inserts grant details.
     *
     * @param grantSetId   grant set id.
     * @param grantDetails list of {link GrantDetail}
     * @param userName     user name
     */
    void insert(String grantSetId, List<AclGrantDetail> grantDetails, String userName);
}
