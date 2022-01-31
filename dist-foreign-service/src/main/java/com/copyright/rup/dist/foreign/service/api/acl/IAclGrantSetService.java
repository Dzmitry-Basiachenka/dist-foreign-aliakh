package com.copyright.rup.dist.foreign.service.api.acl;

import com.copyright.rup.dist.foreign.domain.AclGrantSet;

import java.util.List;

/**
 * Represents interface of repository for ACL grant sets and grant details.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/27/2022
 *
 * @author Aliaksandr Liakh
 */
public interface IAclGrantSetService {

    /**
     * Inserts ACL grant sets with grant details read from RMS.
     *
     * @param grantSet instance of {@link AclGrantSet}
     * @return count of inserted grant details
     */
    int insert(AclGrantSet grantSet);

    /**
     * Checks whether ACL grant set with provided name exists.
     *
     * @param grantSetName name of the {@link AclGrantSet}
     * @return {@code true} if grant set with provided name exists, otherwise {@code false}
     */
    boolean isGrantSetExist(String grantSetName);

    /**
     * Gets list of all ACL grant sets.
     *
     * @return list of all {@link AclGrantSet}s
     */
    List<AclGrantSet> getAll();
}
