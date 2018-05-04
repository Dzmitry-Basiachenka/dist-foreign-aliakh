package com.copyright.rup.dist.foreign.repository.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.impl.CommonRightsholderRepository;
import com.copyright.rup.dist.foreign.repository.api.IRightsholderRepository;

import com.google.common.collect.Maps;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of {@link IRightsholderRepository} for MyBatis.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/07/2017
 *
 * @author Mikita Hladkikh
 * @author Aliaksandr Radkevich
 */
@Repository
public class RightsholderRepository extends CommonRightsholderRepository implements IRightsholderRepository {

    @Override
    public List<Rightsholder> findRros() {
        return selectList("RightsholderMapper.findRros");
    }

    @Override
    public Set<Long> findAccountNumbers() {
        return selectSet("RightsholderMapper.findAccountNumbers");
    }

    @Override
    public void deleteByAccountNumber(Long accountNumber) {
        delete("RightsholderMapper.deleteByAccountNumber", checkNotNull(accountNumber));
    }

    @Override
    public List<Rightsholder> findFromUsages(String searchValue, Pageable pageable, Sort sort) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put("searchValue", searchValue);
        parameters.put("pageable", pageable);
        parameters.put("sort", sort);
        return selectList("RightsholderMapper.findFromUsages", parameters);
    }

    @Override
    public int findCountFromUsages(String searchValue) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(1);
        parameters.put("searchValue", searchValue);
        return selectOne("RightsholderMapper.findCountFromUsages", parameters);
    }

    @Override
    protected String getPrefix() {
        return "df";
    }
}
