package com.copyright.rup.dist.foreign.service.impl.acl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.UdmBatch;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUdmBatchRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmBatchService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageService;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import io.micrometer.core.annotation.Timed;

/**
 * Implementation of {@link IUdmBatchService}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 04/30/2021
 *
 * @author Uladzislau Shalamitski
 */
@Service
public class UdmBatchService implements IUdmBatchService {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IUdmBatchRepository udmBatchRepository;
    @Autowired
    private IUdmUsageService udmUsageService;

    @Override
    @Transactional
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public void insertUdmBatch(UdmBatch udmBatch, List<UdmUsage> udmUsages) {
        String userName = RupContextUtils.getUserName();
        udmBatch.setId(RupPersistUtils.generateUuid());
        udmBatch.setCreateUser(userName);
        udmBatch.setUpdateUser(userName);
        LOGGER.info("Insert UDM batch. Started. UsageBatchName={}, UserName={}", udmBatch.getName(), userName);
        udmBatchRepository.insert(udmBatch);
        udmUsageService.insertUdmUsages(udmBatch, udmUsages);
        LOGGER.info("Insert UDM batch. Finished. UsageBatchName={}, UserName={}", udmBatch.getName(), userName);
    }

    @Override
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public List<UdmBatch> getUdmBatches() {
        return udmBatchRepository.findAll();
    }

    @Override
    public boolean udmBatchExists(String name) {
        return udmBatchRepository.udmBatchExists(name);
    }

    @Override
    public boolean isUdmBatchProcessingCompleted(String udmBatchId) {
        return udmBatchRepository.isUdmBatchProcessingCompleted(udmBatchId,
            Set.of(UsageStatusEnum.NEW, UsageStatusEnum.WORK_FOUND));
    }

    @Override
    @Transactional
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public void deleteUdmBatch(UdmBatch udmBatch) {
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Delete UDM batch. Started. UsageBatchName={}, UserName={}", udmBatch.getName(), userName);
        udmUsageService.deleteUdmBatchDetails(udmBatch);
        udmBatchRepository.deleteById(udmBatch.getId());
        LOGGER.info("Delete UDM batch. Finished. UsageBatchName={}, UserName={}", udmBatch.getName(), userName);
    }

    @Override
    public boolean isUdmBatchContainsBaselineUsages(String udmBatchId) {
        return udmBatchRepository.isUdmBatchContainsBaselineUsages(udmBatchId);
    }

    @Override
    public List<Integer> getPeriods() {
        return udmBatchRepository.findPeriods();
    }
}
