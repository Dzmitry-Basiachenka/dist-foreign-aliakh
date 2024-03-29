package com.copyright.rup.dist.foreign.service.impl.acl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
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

import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import io.micrometer.core.annotation.Timed;

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
    @Value("$RUP{dist.foreign.grid.multi.select.record.threshold}")
    private int recordsThreshold;

    @Override
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public int populateAclUsages(String usageBatchId, Set<Integer> periods, String userName,
                                 Set<Long> wrWrkInsts) {
        return aclUsageRepository.populateAclUsages(usageBatchId, periods, userName, wrWrkInsts).size();
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
            : List.of();
    }

    @Override
    public List<Integer> getPeriods() {
        return aclUsageRepository.findPeriods();
    }

    @Override
    public boolean usageExistForLicenseeClassesAndTypeOfUse(String batchId, String grantSetId,
                                                            Set<Integer> licenseeClassIds, String typeOfUse) {
        return aclUsageRepository.usageExistForLicenseeClassesAndTypeOfUse(batchId, grantSetId, licenseeClassIds,
            typeOfUse);
    }

    @Override
    public int getRecordThreshold() {
        return recordsThreshold;
    }

    @Override
    public List<UsageAge> getDefaultUsageAgesWeights() {
        return aclUsageRepository.findDefaultUsageAgesWeights();
    }

    @Override
    public int getCountInvalidUsages(String batchId, String grantSetId, Integer distributionPeriod,
                                     List<Integer> periodPriors) {
        return aclUsageRepository.findCountInvalidUsages(batchId, grantSetId, distributionPeriod, periodPriors);
    }

    @Override
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public void writeInvalidUsagesCsvReport(String batchId, String grantSetId, Integer distributionPeriod,
                                            List<Integer> periodPriors, OutputStream outputStream) {
        aclUsageRepository.writeInvalidUsagesCsvReport(batchId, grantSetId, distributionPeriod, periodPriors,
            outputStream);
    }

    @Override
    public int copyAclUsages(String sourceUsageBatchId, String targetUsageBatchId, String userName) {
        return aclUsageRepository.copyAclUsages(sourceUsageBatchId, targetUsageBatchId, userName).size();
    }

    @Override
    public void deleteUsages(String usageBatchId) {
        aclUsageRepository.deleteByUsageBatchId(usageBatchId);
    }
}
