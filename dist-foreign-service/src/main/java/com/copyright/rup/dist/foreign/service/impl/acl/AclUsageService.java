package com.copyright.rup.dist.foreign.service.impl.acl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.AclRightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.AclUsageDto;
import com.copyright.rup.dist.foreign.domain.UsageAge;
import com.copyright.rup.dist.foreign.domain.filter.AclUsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IAclUsageRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IAclUsageService;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
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

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IAclUsageRepository aclUsageRepository;
    @Value("$RUP{dist.foreign.udm.record.threshold}")
    private int recordsThreshold;

    @Override
    public int populateAclUsages(String usageBatchId, Set<Integer> periods, String userName) {
        return aclUsageRepository.populateAclUsages(usageBatchId, periods, userName).size();
    }

    @Transactional
    @Override
    public void updateUsages(Collection<AclUsageDto> aclUsageDtos) {
        String userName = RupContextUtils.getUserName();
        LOGGER.debug("Update ACL usages. Started. UsagesCount={}, UserName={}", aclUsageDtos.size(), userName);
        aclUsageDtos.forEach(aclUsageDto -> {
            aclUsageDto.setUpdateUser(userName);
            aclUsageRepository.update(aclUsageDto);
        });
        LOGGER.debug("Update ACL usages. Finished. UsagesCount={}, UserName={}", aclUsageDtos.size(), userName);
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

    @Override
    public List<Integer> getPeriods() {
        return aclUsageRepository.findPeriods();
    }

    @Override
    public int getRecordThreshold() {
        return recordsThreshold;
    }

    @Override
    public List<AclRightsholderTotalsHolder> getAclRightsholderTotalsHoldersByScenarioId(String scenarioId,
                                                                                         String searchValue,
                                                                                         Pageable pageable, Sort sort) {
        return aclUsageRepository.findAclRightsholderTotalsHoldersByScenarioId(scenarioId, searchValue, pageable, sort);
    }

    @Override
    public int getAclRightsholderTotalsHolderCountByScenarioId(String scenarioId, String searchValue) {
        return aclUsageRepository.findAclRightsholderTotalsHolderCountByScenarioId(scenarioId, searchValue);
    }

    @Override
    public List<UsageAge> getDefaultUsageAgesWeights() {
        return aclUsageRepository.findDefaultUsageAgesWeights();
    }
}
