package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.repository.api.IUsageBatchRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Implementation of {@link IUsageBatchService}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/03/2017
 *
 * @author Mikalai Bezmen
 * @author Aliaksandr Radkevich
 */
@Service
public class UsageBatchService implements IUsageBatchService {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IUsageBatchRepository usageBatchRepository;
    @Autowired
    private IUsageService usageService;

    @Override
    public List<Integer> getFiscalYears() {
        return usageBatchRepository.findFiscalYears();
    }

    @Override
    public List<UsageBatch> getUsageBatches() {
        return usageBatchRepository.findUsageBatches();
    }

    @Override
    public boolean usageBatchExists(String name) {
        return 0 < usageBatchRepository.getUsageBatchesCountByName(name);
    }

    @Override
    @Transactional
    public int insertUsages(UsageBatch usageBatch, List<Usage> usages, String userName) {
        usageBatch.setId(UUID.randomUUID().toString());
        usageBatch.setCreateUser(userName);
        usageBatch.setUpdateUser(userName);
        LOGGER.info("Insert usage batch. Started. UsageBatchBatch={}, UserName={}", usageBatch.getName(), userName);
        usageBatchRepository.insert(usageBatch);
        LOGGER.info("Insert usage batch. Finished. UsageBatchBatch={}, UserName={}", usageBatch.getName(), userName);
        return usageService.insertUsages(usageBatch, usages, userName);
    }

    @Transactional
    @Override
    public void deleteUsageBatch(String batchId, String userName) {
        LOGGER.info("Delete usage batch. Started. UsageBatchId={}, UserName={}", batchId, userName);
        usageService.deleteUsageBatchDetails(batchId);
        usageBatchRepository.deleteUsageBatch(batchId);
        LOGGER.info("Delete usage batch. Finished. UsageBatchId={}, UserName={}", batchId, userName);
    }
}
