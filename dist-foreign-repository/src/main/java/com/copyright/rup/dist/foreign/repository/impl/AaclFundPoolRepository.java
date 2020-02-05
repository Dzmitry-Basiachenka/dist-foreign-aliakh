package com.copyright.rup.dist.foreign.repository.impl;

import static com.google.common.base.Preconditions.checkArgument;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.repository.api.IAaclFundPoolRepository;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * Implementation of {@link IAaclFundPoolRepository}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 05/02/2020
 *
 * @author Aliaksandr Liakh
 */
@Repository
public class AaclFundPoolRepository extends BaseRepository implements IAaclFundPoolRepository {

    @Override
    public boolean aaclFundPoolExists(String name) {
        checkArgument(StringUtils.isNotBlank(name));
        return selectOne("IAaclFundPoolMapper.aaclFundPoolExists", escapeSqlLikePattern(name));
    }
}
