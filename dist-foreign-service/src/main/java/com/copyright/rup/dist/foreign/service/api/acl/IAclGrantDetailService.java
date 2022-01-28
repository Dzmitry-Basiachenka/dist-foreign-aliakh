package com.copyright.rup.dist.foreign.service.api.acl;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.AclGrantDetail;
import com.copyright.rup.dist.foreign.domain.AclGrantDetailDto;
import com.copyright.rup.dist.foreign.domain.filter.AclGrantDetailFilter;

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
     * @param grantDetails list of {link GrantDetail}
     */
    void insert(List<AclGrantDetail> grantDetails);

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
}
