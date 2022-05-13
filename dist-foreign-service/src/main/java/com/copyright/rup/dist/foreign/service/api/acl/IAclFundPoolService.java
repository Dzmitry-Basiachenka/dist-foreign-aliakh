package com.copyright.rup.dist.foreign.service.api.acl;

import com.copyright.rup.dist.foreign.domain.AclFundPool;
import com.copyright.rup.dist.foreign.domain.AclFundPoolDetail;

import java.util.List;

/**
 * Represents interface of service for fund pool business logic.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/27/2022
 *
 * @author Anton Azarenka
 */
public interface IAclFundPoolService {

    /**
     * Inserts ACL fund pool with manual details.
     *
     * @param fundPool        instance of {@link AclFundPool}
     * @param fundPoolDetails instance of {@link AclFundPoolDetail}
     */
    void insertAclFundPool(AclFundPool fundPool, List<AclFundPoolDetail> fundPoolDetails);

    /**
     * Inserts ACL fund pool and adds unused LDMT details to it.
     *
     * @param fundPool instance of {@link AclFundPool}
     * @return count of added details
     */
    int insertAclFundPoolWithLdmtDetails(AclFundPool fundPool);

    /**
     * Inserts ACL fund pool details.
     *
     * @param fundPoolDetails instance of {@link AclFundPoolDetail}
     */
    void insertAclFundPoolDetails(List<AclFundPoolDetail> fundPoolDetails);

    /**
     * Checks whether {@link AclFundPool} with provided name already exists.
     *
     * @param name fund pool name
     * @return {@code true} - if fund pool exists, {@code false} - otherwise
     */
    boolean fundPoolExists(String name);
}
