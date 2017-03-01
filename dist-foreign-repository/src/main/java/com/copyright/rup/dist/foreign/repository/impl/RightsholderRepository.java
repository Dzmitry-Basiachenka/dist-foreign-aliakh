package com.copyright.rup.dist.foreign.repository.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.repository.api.IRightsholderRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Implementation of {@link IRightsholderRepository} for MyBatis.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/07/2017
 *
 * @author Mikita Hladkikh
 */
@Repository
public class RightsholderRepository extends BaseRepository implements IRightsholderRepository {

    @Override
    public List<Rightsholder> findRros() {
        return selectList("IRightsholderMapper.findRros");
    }

    @Override
    public void insert(Rightsholder rightsholder) {
        insert("IRightsholderMapper.insert", checkNotNull(rightsholder));
    }

    @Override
    public Set<Long> findRightsholdersAccountNumbers() {
        return selectSet("IRightsholderMapper.findRightsholdersAccountNumbers");
    }

    @Override
    public void deleteAll() {
        delete("IRightsholderMapper.deleteAll");
    }

    @Override
    public void deleteRightsholderByAccountNumber(Long accountNumber) {
        delete("IRightsholderMapper.deleteRightsholderByAccountNumber", checkNotNull(accountNumber));
    }

    /**
     * @return list of all {@link Rightsholder}s from DB.
     */
    List<Rightsholder> findAll() {
        return selectList("IRightsholderMapper.findAll");
    }
}
