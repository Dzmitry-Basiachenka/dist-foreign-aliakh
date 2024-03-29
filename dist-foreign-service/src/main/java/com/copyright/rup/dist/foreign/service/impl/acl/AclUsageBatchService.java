package com.copyright.rup.dist.foreign.service.impl.acl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.AclUsageBatch;
import com.copyright.rup.dist.foreign.integration.pi.api.IPiDeletedWorkIntegrationService;
import com.copyright.rup.dist.foreign.repository.api.IAclUsageBatchRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IAclUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclUsageService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageService;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import io.micrometer.core.annotation.Timed;

/**
 * Implementation of {@link IAclUsageBatchService}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/01/2022
 *
 * @author Aliaksandr Liakh
 */
@Service
public class AclUsageBatchService implements IAclUsageBatchService {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    @Qualifier("df.integration.piDeletedWorkIntegrationService")
    private IPiDeletedWorkIntegrationService piIntegrationService;
    @Autowired
    private IAclUsageBatchRepository aclUsageBatchRepository;
    @Autowired
    private IAclUsageService aclUsageService;
    @Autowired
    private IUdmUsageService udmUsageService;

    @Override
    public boolean isAclUsageBatchExist(String usageBatchName) {
        return aclUsageBatchRepository.isAclUsageBatchExist(usageBatchName);
    }

    @Transactional
    @Override
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public int insert(AclUsageBatch usageBatch) {
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Insert ACL usage batch. Started. AclUsageBatch={}, UserName={}", usageBatch, userName);
        usageBatch.setId(RupPersistUtils.generateUuid());
        usageBatch.setCreateUser(userName);
        usageBatch.setUpdateUser(userName);
        Set<Integer> periods = usageBatch.getPeriods();
        Set<Long> wrWrkInsts = udmUsageService.getWrWrkInstPublishedToBaselineUdmUsages(periods);
        Set<Long> deletedWrWrkInsts = wrWrkInsts.stream()
            .filter(wrWrkInst -> piIntegrationService.isDeletedWork(wrWrkInst))
            .collect(Collectors.toSet());
        aclUsageBatchRepository.insert(usageBatch);
        int count = aclUsageService.populateAclUsages(usageBatch.getId(), periods, userName, deletedWrWrkInsts);
        LOGGER.info("Insert ACL usage batch. Finished. AclUsageBatch={}, AclUsagesCount={}, UserName={}",
            usageBatch, count, userName);
        return count;
    }

    @Override
    public List<AclUsageBatch> getAll() {
        return aclUsageBatchRepository.findAll();
    }

    @Override
    public List<AclUsageBatch> getUsageBatchesByPeriod(Integer period, boolean editableFlag) {
        return aclUsageBatchRepository.findUsageBatchesByPeriod(period, editableFlag);
    }

    @Override
    public List<Integer> getPeriods() {
        return aclUsageBatchRepository.findPeriods();
    }

    @Override
    public AclUsageBatch getById(String usageBatchId) {
        return aclUsageBatchRepository.findById(usageBatchId);
    }

    @Transactional
    @Override
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public int copyUsageBatch(String sourceUsageBatchId, AclUsageBatch aclUsageBatch) {
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Copy ACL usage batch. Started. SourceBatchId={}, AclUsageBatch={}, UserName={}",
            sourceUsageBatchId, aclUsageBatch, userName);
        aclUsageBatch.setId(RupPersistUtils.generateUuid());
        aclUsageBatch.setCreateUser(userName);
        aclUsageBatch.setUpdateUser(userName);
        aclUsageBatchRepository.insert(aclUsageBatch);
        int count = aclUsageService.copyAclUsages(sourceUsageBatchId, aclUsageBatch.getId(), userName);
        LOGGER.info("Copy ACL usage batch. Finished. SourceBatchId={}, AclUsageBatch={}, UserName={}, UsagesCount={}",
            sourceUsageBatchId, aclUsageBatch, userName, count);
        return count;
    }

    @Override
    @Transactional
    @Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
    public void deleteAclUsageBatch(AclUsageBatch usageBatch) {
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Delete ACL usage batch. Started. UsageBatchName={}, UserName={}", usageBatch.getName(), userName);
        aclUsageService.deleteUsages(usageBatch.getId());
        aclUsageBatchRepository.deleteById(usageBatch.getId());
        LOGGER.info("Delete ACL usage batch. Finished. UsageBatchName={}, UserName={}", usageBatch.getName(), userName);
    }
}
