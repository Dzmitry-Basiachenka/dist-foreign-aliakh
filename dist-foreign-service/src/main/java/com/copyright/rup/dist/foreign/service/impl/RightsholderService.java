package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;
import com.copyright.rup.dist.foreign.repository.api.IRightsholderRepository;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;

import com.google.common.collect.Sets;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Implementation of {@link IRightsholderService}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/07/2017
 *
 * @author Mikita Hladkikh
 */
@Service
public class RightsholderService implements IRightsholderService {

    private static final Logger LOGGER = RupLogUtils.getLogger();
    private ExecutorService executorService;

    @Autowired
    private IRightsholderRepository rightsholderRepository;
    @Autowired
    private IPrmIntegrationService prmIntegrationService;

    @Override
    public List<Rightsholder> getRros() {
        return rightsholderRepository.findRros();
    }

    @Override
    @Transactional
    public void updateRightsholders() {
        Set<Long> accountNumbers = rightsholderRepository.findAccountNumbers();
        LOGGER.info("Update Rightsholder information. Started. RHsCount={}", accountNumbers.size());
        List<Rightsholder> rightsholders = Collections.emptyList();
        if (CollectionUtils.isNotEmpty(accountNumbers)) {
            rightsholders = prmIntegrationService.getRightsholders(accountNumbers);
            if (CollectionUtils.isNotEmpty(rightsholders)) {
                rightsholderRepository.deleteAll();
                rightsholders.forEach(rightsholder -> rightsholderRepository.insert(rightsholder));
            } else {
                LOGGER.warn("Update rightsholders information. Skipped. RHsCount={}, Reason=No RHs found",
                    accountNumbers.size());
                return;
            }
        }
        LOGGER.info("Update rightsholders information. Finished. RHsCount={}, UpdatedCount={}",
            accountNumbers.size(), rightsholders.size());
    }

    @Override
    @Transactional
    public void updateRightsholders(Set<Long> accountNumbers) {
        executorService.execute(() -> doUpdateRightsholders(accountNumbers));
    }

    @Override
    public void updateRightsholder(Rightsholder rightsholder) {
        Objects.requireNonNull(rightsholder);
        LOGGER.info("Update rightsholder information. Started. RhAccount#={}", rightsholder.getAccountNumber());
        rightsholderRepository.deleteByAccountNumber(rightsholder.getAccountNumber());
        rightsholderRepository.insert(rightsholder);
        LOGGER.info("Update rightsholder information. Finished. RhAccount#={}", rightsholder.getAccountNumber());
    }

    /**
     * Post construct method.
     */
    @PostConstruct
    void postConstruct() {
        executorService = Executors.newSingleThreadExecutor();
    }

    /**
     * Pre destroy method.
     */
    @PreDestroy
    void preDestroy() {
        executorService.shutdown();
    }

    /**
     * Updates rightsholders by their account numbers. If there is no information about rightsholder
     * with some account number it will be retrieved from PRM using REST call.
     *
     * @param accountNumbers set of rightsholder account numbers
     */
    void doUpdateRightsholders(Set<Long> accountNumbers) {
        synchronized (this) {
            List<Rightsholder> rightsholders;
            LOGGER.info("Update rightsholders information. Started. RHsCount={}", accountNumbers.size());
            if (CollectionUtils.isNotEmpty(accountNumbers)) {
                rightsholders = rightsholderRepository.findRightsholdersByAccountNumbers(accountNumbers);
                Set<Long> difference = Sets.difference(accountNumbers,
                    rightsholders.stream().map(Rightsholder::getAccountNumber).collect(Collectors.toSet()));
                if (CollectionUtils.isNotEmpty(difference)) {
                    List<Rightsholder> prmRightsholders = prmIntegrationService.getRightsholders(difference);
                    if (CollectionUtils.isNotEmpty(prmRightsholders)) {
                        prmRightsholders.forEach(rightsholder -> rightsholderRepository.insert(rightsholder));
                        rightsholders.addAll(prmRightsholders);
                        LOGGER.info("Update rightsholders information. Finished. RHsCount={}, UpdatedCount={}",
                            accountNumbers.size(), prmRightsholders.size());
                    } else {
                        LOGGER.warn(
                            "Update rightsholders information. Skipped. RHsCount={}, Reason=No RHs found",
                            accountNumbers.size());
                    }
                } else {
                    LOGGER.info(
                        "Update rightsholders information. Skipped. RHsCount={}. Rightsholders are up to date",
                        accountNumbers.size());
                }
            }
        }
    }
}
