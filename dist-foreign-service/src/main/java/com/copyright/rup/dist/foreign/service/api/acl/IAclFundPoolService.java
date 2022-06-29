package com.copyright.rup.dist.foreign.service.api.acl;

import com.copyright.rup.dist.foreign.domain.AclFundPool;
import com.copyright.rup.dist.foreign.domain.AclFundPoolDetail;
import com.copyright.rup.dist.foreign.domain.AclFundPoolDetailDto;
import com.copyright.rup.dist.foreign.domain.filter.AclFundPoolDetailFilter;

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
     * Inserts manual ACL fund pool and ACL fund pool details.
     *
     * @param fundPool        instance of {@link AclFundPool}
     * @param fundPoolDetails instance of {@link AclFundPoolDetail}
     */
    void insertManualAclFundPool(AclFundPool fundPool, List<AclFundPoolDetail> fundPoolDetails);

    /**
     * Inserts LDMT ACL fund pool and adds unused ACL fund pool details to it.
     *
     * @param fundPool instance of {@link AclFundPool}
     * @return count of added details
     */
    int insertLdmtAclFundPool(AclFundPool fundPool);

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

    /**
     * Checks whether LDMT {@link AclFundPoolDetail} exists for Fund Pool creation with provided license type.
     *
     * @param licenseType license type
     * @return {@code true} - if detail exists, {@code false} - otherwise
     */
    boolean isLdmtDetailExist(String licenseType);

    /**
     * Gets list of {@link AclFundPoolDetailDto}s by specified filter.
     *
     * @param filter applied {@link AclFundPoolDetailFilter}
     * @return list of {@link AclFundPoolDetailDto}s
     */
    List<AclFundPoolDetailDto> getDtosByFilter(AclFundPoolDetailFilter filter);

    /**
     * Gets all ACL fund pools.
     *
     * @return list of all {@link AclFundPool}s
     */
    List<AclFundPool> getAll();

    /**
     * Gets all available periods.
     *
     * @return list of periods
     */
    List<Integer> getPeriods();

    /**
     * Deletes ACL fund pool.
     *
     * @param fundPool instance of {@link AclFundPool}
     */
    void deleteAclFundPool(AclFundPool fundPool);

    /**
     * Gets list of all ACl fund pools by license type and period.
     *
     * @param licenseType license type
     * @param period      period end date
     * @return list of {@link AclFundPool}s
     */
    List<AclFundPool> getFundPoolsByLicenseTypeAndPeriod(String licenseType, Integer period);

    /**
     * Gets {@link AclFundPool} by its id.
     *
     * @param fundPoolId ACL fund pool id
     * @return instance of {@link AclFundPool} or {@code null} if none exists
     */
    AclFundPool getById(String fundPoolId);
}
