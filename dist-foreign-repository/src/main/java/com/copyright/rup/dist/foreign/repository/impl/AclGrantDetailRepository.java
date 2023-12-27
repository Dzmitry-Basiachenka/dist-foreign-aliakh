package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.AclGrantDetail;
import com.copyright.rup.dist.foreign.domain.AclGrantDetailDto;
import com.copyright.rup.dist.foreign.domain.filter.AclGrantDetailFilter;
import com.copyright.rup.dist.foreign.repository.api.IAclGrantDetailRepository;

import com.google.common.collect.Maps;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Implementation of {@link IAclGrantDetailRepository}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/27/2022
 *
 * @author Aliaksandr Liakh
 */
@Repository
public class AclGrantDetailRepository extends AclBaseRepository implements IAclGrantDetailRepository {

    @Override
    public void insert(AclGrantDetail grantDetail) {
        insert("IAclGrantDetailMapper.insert", Objects.requireNonNull(grantDetail));
    }

    @Override
    public List<AclGrantDetail> findByIds(List<String> grantDetailIds) {
        return selectList("IAclGrantDetailMapper.findByIds", Objects.requireNonNull(grantDetailIds));
    }

    @Override
    public int findCountByFilter(AclGrantDetailFilter filter) {
        return selectOne("IAclGrantDetailMapper.findCountByFilter",
            Map.of("filter", escapeSqlLikePattern(Objects.requireNonNull(filter))));
    }

    @Override
    public List<AclGrantDetailDto> findDtosByFilter(AclGrantDetailFilter filter, Pageable pageable, Sort sort) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(3);
        params.put("filter", escapeSqlLikePattern(Objects.requireNonNull(filter)));
        params.put("pageable", pageable);
        params.put("sort", sort);
        return selectList("IAclGrantDetailMapper.findDtosByFilter", params);
    }

    @Override
    public void updateGrant(AclGrantDetailDto grant) {
        update("IAclGrantDetailMapper.update", Objects.requireNonNull(grant));
    }

    @Override
    public AclGrantDetailDto findPairForGrantById(String grantId) {
        return selectOne("IAclGrantDetailMapper.findPairForGrantById", Objects.requireNonNull(grantId));
    }

    @Override
    public void deleteByGrantSetId(String grantSetId) {
        delete("IAclGrantDetailMapper.deleteByGrantSetId", Objects.requireNonNull(grantSetId));
    }

    @Override
    public boolean isGrantDetailExist(String grantSetId, Long wrWrkInst, String typeOfUse) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(3);
        params.put("grantSetId", Objects.requireNonNull(grantSetId));
        params.put("wrWrkInst", Objects.requireNonNull(wrWrkInst));
        params.put("typeOfUse", Objects.requireNonNull(typeOfUse));
        return selectOne("IAclGrantDetailMapper.isGrantDetailExist", params);
    }

    @Override
    public List<String> copyGrantDetailsByGrantSetId(String sourceGrantSetId, String targetGrantSetId,
                                                     String userName) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(4);
        params.put("sourceGrantSetId", Objects.requireNonNull(sourceGrantSetId));
        params.put("targetGrantSetId", Objects.requireNonNull(targetGrantSetId));
        params.put("createUser", Objects.requireNonNull(userName));
        params.put("updateUser", userName);
        return selectList("IAclGrantDetailMapper.copyGrantDetails", params);
    }

    @Override
    public void updatePayeeAccountNumber(String grantSetId, Long rhAccountNumber, String typeOfUse,
                                         Long payeeAccountNumber) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(4);
        params.put("grantSetId", Objects.requireNonNull(grantSetId));
        params.put("rhAccountNumber", Objects.requireNonNull(rhAccountNumber));
        params.put("typeOfUse", Objects.requireNonNull(typeOfUse));
        params.put("payeeAccountNumber", Objects.requireNonNull(payeeAccountNumber));
        update("IAclGrantDetailMapper.updatePayeeAccountNumber", params);
    }

    private AclGrantDetailFilter escapeSqlLikePattern(AclGrantDetailFilter aclGrantDetailFilter) {
        AclGrantDetailFilter filterCopy = new AclGrantDetailFilter(aclGrantDetailFilter);
        filterCopy.setRhNameExpression(
            escapePropertyForMyBatisSqlFragment(filterCopy.getRhNameExpression()));
        return filterCopy;
    }
}
