package com.copyright.rup.dist.foreign.service.impl;


import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.UsageBatchStatus;
import com.copyright.rup.dist.foreign.repository.api.IUsageBatchStatusRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchStatusService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
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
    public List<UsageBatchStatus> getUsageBatchStatusesFas() {
        Set<String> batchIds = usageBatchStatusRepository.findUsageBatchIdsByProductFamilyAndStartDateFrom(
            FdaConstants.FAS_PRODUCT_FAMILY, LocalDate.now().minusDays(numberOfDays));
        return !batchIds.isEmpty()
            ? usageBatchStatusRepository.findUsageBatchStatusesFas(batchIds)
            : Collections.emptyList();
    }

    @Override
    public List<UsageBatchStatus> getUsageBatchStatusesFas2() {
        return usageBatchStatusRepository.findUsageBatchStatusesFas2();
    }

    @Override
    public List<UsageBatchStatus> getUsageBatchStatusesNts() {
        return usageBatchStatusRepository.findUsageBatchStatusesNts();
    }

    @Override
    public List<UsageBatchStatus> getUsageBatchStatusesAacl() {
        return usageBatchStatusRepository.findUsageBatchStatusesAacl();
    }

    @Override
    public List<UsageBatchStatus> getUsageBatchStatusesSal() {
        return usageBatchStatusRepository.findUsageBatchStatusesSal();
    }
}
