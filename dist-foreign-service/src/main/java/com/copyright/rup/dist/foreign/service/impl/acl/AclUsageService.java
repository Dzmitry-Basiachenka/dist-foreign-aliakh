package com.copyright.rup.dist.foreign.service.impl.acl;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.AclUsageDto;
import com.copyright.rup.dist.foreign.domain.filter.AclUsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IAclUsageRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IAclUsageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Implementation of {@link IAclUsageService}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/01/2022
 *
 * @author Aliaksandr Liakh
 */
@Service
public class AclUsageService implements IAclUsageService {

    @Autowired
    private IAclUsageRepository aclUsageRepository;

    @Override
    public int populateAclUsages(String usageBatchId, Set<Integer> periods, String userName) {
        return aclUsageRepository.populateAclUsages(usageBatchId, periods, userName).size();
    }

    @Override
    public int getCount(AclUsageFilter filter) {
        return !filter.isEmpty() ? aclUsageRepository.findCountByFilter(filter) : 0;
    }

    @Override
    public List<AclUsageDto> getDtos(AclUsageFilter filter, Pageable pageable, Sort sort) {
        return !filter.isEmpty()
            ? aclUsageRepository.findDtosByFilter(filter, pageable, sort)
            : Collections.emptyList();
    }
}
