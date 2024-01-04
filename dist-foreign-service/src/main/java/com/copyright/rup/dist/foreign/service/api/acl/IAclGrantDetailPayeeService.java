package com.copyright.rup.dist.foreign.service.api.acl;

import com.copyright.rup.dist.foreign.domain.AclGrantDetailDto;

import java.util.Collection;

/**
 * Represents interface of service for grant details payee business logic.
 * <p>
 * Copyright (C) 2024 copyright.com
 * <p>
 * Date: 01/04/2024
 *
 * @author Aliaksandr Liakh
 */
public interface IAclGrantDetailPayeeService {

    /**
     * Populates payees in ACL grant details.
     *
     * @param grantSetId grant set id
     */
    void populatePayees(String grantSetId);

    /**
     * Populates payees in ACL grant details.
     *
     * @param grantDetailDtos collection of {@link AclGrantDetailDto}
     */
    void populatePayees(Collection<AclGrantDetailDto> grantDetailDtos);
}
