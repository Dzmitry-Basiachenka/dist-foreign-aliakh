package com.copyright.rup.dist.foreign.service.impl;


import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.UsageBatchStatus;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUsageBatchStatusRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchStatusService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * Implementation of {@link IUsageBatchStatusService}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 02/12/2021
 *
 * @author Ihar Suvorau
 */
@Service
public class UsageBatchStatusService implements IUsageBatchStatusService {

    @Autowired
    private IUsageBatchStatusRepository usageBatchStatusRepository;
    @Value("$RUP{dist.foreign.batch_status.number_of_days}")
    private long numberOfDays;

    @Override
    public List<UsageBatchStatus> getUsageBatchStatusesFas(String productFamily) {
        Set<String> batchIds = usageBatchStatusRepository.findFasUsageBatchIdsEligibleForStatistic(
            productFamily, LocalDate.now().minusDays(numberOfDays));
        return !batchIds.isEmpty()
            ? usageBatchStatusRepository.findUsageBatchStatusesFas(batchIds)
            : List.of();
    }

    @Override
    public List<UsageBatchStatus> getUsageBatchStatusesNts() {
        Set<String> batchIds = usageBatchStatusRepository.findUsageBatchIdsEligibleForStatistic(
            FdaConstants.NTS_PRODUCT_FAMILY, LocalDate.now().minusDays(numberOfDays));
        return !batchIds.isEmpty()
            ? usageBatchStatusRepository.findUsageBatchStatusesNts(batchIds)
            : List.of();
    }

    @Override
    public List<UsageBatchStatus> getUsageBatchStatusesAacl() {
        Set<String> batchIds = usageBatchStatusRepository.findUsageBatchIdsEligibleForStatistic(
            FdaConstants.AACL_PRODUCT_FAMILY, LocalDate.now().minusDays(numberOfDays));
        return !batchIds.isEmpty()
            ? usageBatchStatusRepository.findUsageBatchStatusesAacl(batchIds)
            : List.of();
    }

    @Override
    public List<UsageBatchStatus> getUsageBatchStatusesSal() {
        Set<String> batchIds = usageBatchStatusRepository.findUsageBatchIdsEligibleForStatistic(
            FdaConstants.SAL_PRODUCT_FAMILY, LocalDate.now().minusDays(numberOfDays));
        return !batchIds.isEmpty()
            ? usageBatchStatusRepository.findUsageBatchStatusesSal(batchIds)
            : List.of();
    }

    @Override
    public List<UsageBatchStatus> getUsageBatchStatusesUdm() {
        // TODO: {dbasiachenka} implement
        return List.of();
    }

    @Override
    public boolean isBatchProcessingCompleted(String batchId, Set<UsageStatusEnum> statuses) {
        return usageBatchStatusRepository.isBatchProcessingCompleted(batchId, statuses);
    }
}
