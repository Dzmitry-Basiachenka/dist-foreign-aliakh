package com.copyright.rup.dist.foreign.service.api.acl;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.AclGrantDetail;
import com.copyright.rup.dist.foreign.domain.AclGrantDetailDto;
import com.copyright.rup.dist.foreign.domain.AclGrantSet;
import com.copyright.rup.dist.foreign.domain.filter.AclGrantDetailFilter;

import java.util.Collection;
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
     * Inserts ACL grant details.
     *
     * @param grantDetails list of {@link AclGrantDetail}
     */
    void insert(List<AclGrantDetail> grantDetails);

    /**
     * Adds {@link AclGrantDetailDto}s to specified {@link AclGrantSet}.
     *
     * @param grantSet     {@link AclGrantSet} to be updated
     * @param grantDetails list of details to insert
     */
    void addToGrantSet(AclGrantSet grantSet, List<AclGrantDetailDto> grantDetails);

    /**
     * Gets count of ACL grant details based on applied filter.
     *
     * @param filter instance of {@link AclGrantDetailFilter}
     * @return the count of ACL grant details
     */
    int getCount(AclGrantDetailFilter filter);

    /**
     * Gets list of {@link AclGrantDetailDto}s based on applied filter.
     *
     * @param filter   instance of {@link AclGrantDetailFilter}
     * @param pageable instance of {@link Pageable}
     * @param sort     instance of {@link Sort}
     * @return the list of {@link AclGrantDetailDto}s
     */
    List<AclGrantDetailDto> getDtos(AclGrantDetailFilter filter, Pageable pageable, Sort sort);

    /**
     * Updates ACL grants.
     *
     * @param aclGrantDetailDtos collection of {@link AclGrantDetailDto}
     * @param doUpdateTouStatus  <code>true</code> if system should update TOU status
     */
    void updateGrants(Collection<AclGrantDetailDto> aclGrantDetailDtos, boolean doUpdateTouStatus);

    /**
     * Deletes ACL grant details by ACL grant set id.
     *
     * @param grantSetId id of the {@link com.copyright.rup.dist.foreign.domain.AclGrantSet}
     */
    void deleteGrantDetails(String grantSetId);

    /**
     * Checks whether ACL grant detail with provided Wr Wrk Inst and Type of Use exists for specified Grant Set.
     *
     * @param grantSetId id of the {@link @link com.copyright.rup.dist.foreign.domain.AclGrantSet}
     * @param wrWrkInst  Wr Wrk Inst
     * @param typeOfUse  Type of Use
     * @return {@code true} if grant detail with provided Wr Wrk Inst and Type of Use exists, otherwise {@code false}
     */
    boolean isGrantDetailExist(String grantSetId, Long wrWrkInst, String typeOfUse);
}
