package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;
import com.copyright.rup.dist.foreign.repository.api.IRightsholderRepository;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;

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

    @Autowired
    private IRightsholderRepository repository;

    @Autowired
    private IPrmIntegrationService prmIntegrationService;

    @Override
    public List<Rightsholder> getRros() {
        return repository.findRros();
    }

    @Override
    @Transactional
    public int updateRightsholdersInformation() {
        Set<Long> accountNumbers = repository.findRightsholdersAccountNumbers();
        int accountNumbersSize = accountNumbers.size();
        LOGGER.info("Update Rightsholder information. Started. RHs count={}", accountNumbersSize);
        Collection<Rightsholder> rightsholders = prmIntegrationService.getRightsholders(accountNumbers);
        int rightsholdersSize = rightsholders.size();
        repository.deleteAll();
        for (Rightsholder rightsholder : rightsholders) {
            repository.insert(rightsholder);
            LOGGER.debug("Insert Rightsholder. {}", rightsholder);
        }
        LOGGER.info("Update Rightsholder information. Finished. RHs Count={}, Updated Count={}", accountNumbersSize,
            rightsholdersSize);
        return rightsholdersSize;
    }
}
