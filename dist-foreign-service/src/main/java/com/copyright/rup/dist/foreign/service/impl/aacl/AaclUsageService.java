package com.copyright.rup.dist.foreign.service.impl.aacl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.AaclClassifiedUsage;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IAaclUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.aacl.IAaclUsageService;
import com.copyright.rup.dist.foreign.service.impl.InconsistentUsageStateException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Implementation of {@link IAaclUsageService}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 01/22/2020
 *
 * @author Ihar Suvorau
 */
@Service
public class AaclUsageService implements IAaclUsageService {

    private static final String DISQUALIFIED_USAGE = "disqualified";
    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IUsageAuditService usageAuditService;
    @Autowired
    private IAaclUsageRepository aaclUsageRepository;

    @Override
    public void insertUsages(UsageBatch usageBatch, Collection<Usage> usages) {
        String userName = RupContextUtils.getUserName();
        int size = usages.size();
        LOGGER.info("Insert AACL usages. Started. UsageBatchName={}, UsagesCount={}, UserName={}", usageBatch.getName(),
            size, userName);
        int period = usageBatch.getPaymentDate().getYear();
        usages.forEach(usage -> {
            usage.setBatchId(usageBatch.getId());
            usage.getAaclUsage().setUsagePeriod(period);
            usage.getAaclUsage().setBatchPeriodEndDate(usageBatch.getPaymentDate());
            usage.setCreateUser(userName);
            usage.setUpdateUser(userName);
            aaclUsageRepository.insert(usage);
        });
        String loadedReason = "Uploaded in '" + usageBatch.getName() + "' Batch";
        usages.forEach(usage -> usageAuditService.logAction(usage.getId(), UsageActionTypeEnum.LOADED, loadedReason));
        LOGGER.info("Insert AACL usages. Finished. UsageBatchName={}, UsagesCount={}, UserName={}",
            usageBatch.getName(), size, userName);
    }

    @Override
    @Transactional
    public int updateClassifiedUsages(Collection<AaclClassifiedUsage> usages) {
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Update Classified AACL usages. Started. UsagesCount={}, UserName={}", LogUtils.size(usages),
            userName);
        long disqualifiedCount = usages.stream()
            .filter(this::isUsageDisqualified)
            .peek(usage -> {
                aaclUsageRepository.deleteById(usage.getDetailId());
                usageAuditService.deleteActionsByUsageId(usage.getDetailId());
            }).count();
        List<AaclClassifiedUsage> updateUsages = usages.stream()
            .filter(usage -> !isUsageDisqualified(usage))
            .collect(Collectors.toList());
        int updatedCount = updateUsages.size();
        aaclUsageRepository.updateClassifiedUsages(updateUsages, userName);
        updateUsages.forEach(usage -> usageAuditService.logAction(usage.getDetailId(), UsageActionTypeEnum.ELIGIBLE,
            "Usages has become eligible after classification"));
        LOGGER.info("Update Classified AACL usages. Finished. UsagesCount={}, UserName={}, DisqualifiedCount={}",
            updatedCount, userName, disqualifiedCount);
        return updatedCount;
    }

    @Override
    public List<UsageDto> getUsageDtos(UsageFilter filter, Pageable pageable, Sort sort) {
        return !filter.isEmpty()
            ? aaclUsageRepository.findDtosByFilter(filter, pageable, sort)
            : Collections.emptyList();
    }

    @Override
    public void updateProcessedUsage(Usage usage) {
        String usageId = aaclUsageRepository.updateProcessedUsage(usage);
        if (Objects.isNull(usageId)) {
            // throws an exception and stops usage processing when such usage has been already consumed and processed
            throw new InconsistentUsageStateException(usage);
        }
        usage.setVersion(usage.getVersion() + 1);
    }

    @Override
    public int getUsagesCount(UsageFilter filter) {
        return !filter.isEmpty() ? aaclUsageRepository.findCountByFilter(filter) : 0;
    }

    @Override
    public List<Integer> getUsagePeriods() {
        return aaclUsageRepository.findUsagePeriods();
    }

    @Override
    @Transactional
    public void deleteById(String usageId) {
        usageAuditService.deleteActionsByUsageId(usageId);
        aaclUsageRepository.deleteById(usageId);
    }

    @Override
    public boolean isValidFilteredUsageStatus(UsageFilter filter, UsageStatusEnum status) {
        return aaclUsageRepository.isValidFilteredUsageStatus(filter, status);
    }

    @Override
    @Transactional
    public void deleteUsageBatchDetails(UsageBatch usageBatch) {
        usageAuditService.deleteActionsByBatchId(usageBatch.getId());
        aaclUsageRepository.deleteByBatchId(usageBatch.getId());
    }

    private boolean isUsageDisqualified(AaclClassifiedUsage usage) {
        return DISQUALIFIED_USAGE.equalsIgnoreCase(usage.getDiscipline())
            || DISQUALIFIED_USAGE.equalsIgnoreCase(usage.getEnrollmentProfile())
            || DISQUALIFIED_USAGE.equalsIgnoreCase(usage.getPublicationType());
    }
}
