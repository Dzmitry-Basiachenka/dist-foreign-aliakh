package com.copyright.rup.dist.foreign.service.impl;


import com.copyright.rup.dist.foreign.domain.UsageBatchStatus;
import com.copyright.rup.dist.foreign.repository.api.IUsageBatchStatusRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchStatusService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<UsageBatchStatus> getUsageBatchStatusesFas() {
        return usageBatchStatusRepository.findUsageBatchStatusesFas();
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