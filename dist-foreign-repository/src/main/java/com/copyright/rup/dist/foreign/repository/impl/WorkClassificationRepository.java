package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.repository.api.IWorkClassificationRepository;

import org.springframework.stereotype.Repository;

import java.util.Objects;

/**
 * Implementation of {@link IWorkClassificationRepository}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 03/06/19
 *
 * @author Pavel Liakh
 */
@Repository
public class WorkClassificationRepository extends BaseRepository implements IWorkClassificationRepository {

    @Override
    public String findClassificationByWrWrkInst(Long wrWrkInst) {
        return selectOne("IWorkClassificationMapper.findClassificationByWrWrkInst", Objects.requireNonNull(wrWrkInst));
    }
}
