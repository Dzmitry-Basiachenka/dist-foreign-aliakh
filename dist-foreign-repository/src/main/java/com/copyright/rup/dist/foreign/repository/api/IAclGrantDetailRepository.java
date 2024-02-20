package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.AclGrantDetail;
import com.copyright.rup.dist.foreign.domain.AclGrantDetailDto;
import com.copyright.rup.dist.foreign.domain.filter.AclGrantDetailFilter;

import java.io.Serializable;
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
public interface IAclGrantDetailRepository extends Serializable {

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
     * Finds pair for grant by id.
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

    /**
     * Checks whether ACL grant detail with provided Wr Wrk Inst and Type of Use exists for specified Grant Set.
     *
     * @param grantSetId id of the {@link com.copyright.rup.dist.foreign.domain.AclGrantSet}
     * @param wrWrkInst Wr Wrk Inst
     * @param typeOfUse Type of Use
     * @return {@code true} if grant detail with provided Wr Wrk Inst and Type of Use exists, otherwise {@code false}
     */
    boolean isGrantDetailExist(String grantSetId, Long wrWrkInst, String typeOfUse);

    /**
     * Copies grant details by grant set id.
     *
     * @param sourceGrantSetId source grant set id
     * @param targetGrantSetId target grant set id
     * @param userName         username
     * @return list of inserted grant details ids
     */
    List<String> copyGrantDetailsByGrantSetId(String sourceGrantSetId, String targetGrantSetId, String userName);

    /**
     * Sets payee account number for ACL grant detail by ACL grant set id, RH account number, and type of use.
     *
     * @param grantSetId         grant set id
     * @param rhAccountNumber    RH account number
     * @param typeOfUse          type of use
     * @param payeeAccountNumber payee account number
     * @param userName           user name
     */
    void updatePayeeAccountNumber(String grantSetId, Long rhAccountNumber, String typeOfUse, Long payeeAccountNumber,
                                  String userName);

    /**
     * Sets payee account number for ACL grant detail by ACL grant set id, RH account number, and type of use.
     *
     * @param grantDetailId      grant detail id
     * @param payeeAccountNumber payee account number
     * @param userName           user name
     */
    void updatePayeeAccountNumberById(String grantDetailId, Long payeeAccountNumber, String userName);
}
