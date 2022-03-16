package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.AclGrantDetail;
import com.copyright.rup.dist.foreign.domain.AclGrantDetailDto;
import com.copyright.rup.dist.foreign.domain.filter.AclGrantDetailFilter;

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

    /**
     * Finds count of ACL grant details based on applied filter.
     *
     * @param filter instance of {@link AclGrantDetailFilter}
     * @return the count of ACL grant details
     */
    int findCountByFilter(AclGrantDetailFilter filter);

    /**
     * Finds list of {@link AclGrantDetailDto}s based on applied filter.
     *
     * @param filter   instance of {@link AclGrantDetailFilter}
     * @param pageable instance of {@link Pageable}
     * @param sort     instance of {@link Sort}
     * @return the list of {@link AclGrantDetailDto}
     */
    List<AclGrantDetailDto> findDtosByFilter(AclGrantDetailFilter filter, Pageable pageable, Sort sort);

    /**
     * Updates ACL grant.
     *
     * @param grant set of {@link AclGrantDetailDto}
     */
    void updateGrant(AclGrantDetailDto grant);

    /**
     * Finds grants by WrWrkInsts.
     *
     * @param grantId uid of grant detail
     * @return instance of {@link AclGrantDetailDto}
     */
    AclGrantDetailDto findPairForGrantById(String grantId);

    /**
     * Deletes ACL grant details by ACL grant set id.
     *
     * @param grantSetId id of the {@link com.copyright.rup.dist.foreign.domain.AclGrantSet}
     */
    void deleteByGrantSetId(String grantSetId);
}
