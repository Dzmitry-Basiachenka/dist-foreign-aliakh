package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.foreign.domain.AclGrantSet;

import java.util.List;

/**
 * Represents interface of repository for ACL grant sets.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/26/2022
 *
 * @author Aliaksandr Liakh
 */
public interface IAclGrantSetRepository {

    /**
     * Inserts ACL grant set.
     *
     * @param grantSet instance of {@link AclGrantSet}
     */
    void insert(AclGrantSet grantSet);

    /**
     * Gets ACL grant set by its id.
     *
     * @param grantSetId id of the {@link AclGrantSet}
     * @return {@link AclGrantSet} with the given id or {@code null} if none exists
     */
    AclGrantSet findById(String grantSetId);

    /**
     * Checks whether ACL grant set with provided name exists.
     *
     * @param grantSetName name of the {@link AclGrantSet}
     * @return {@code true} if grant set with provided name exists, otherwise {@code false}
     */
    boolean isGrantSetExist(String grantSetName);

    /**
     * Finds list of all ACL grant sets.
     *
     * @return list of all {@link AclGrantSet}s
     */
    List<AclGrantSet> findAll();

    /**
     * Finds list of grant periods.
     *
     * @return list of grant periods
     */
    List<Integer> findGrantPeriods();
}
