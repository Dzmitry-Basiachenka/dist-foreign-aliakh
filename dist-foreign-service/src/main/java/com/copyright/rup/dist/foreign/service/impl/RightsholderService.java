package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.integration.rest.prm.IPrmRightsholderOrganizationService;
import com.copyright.rup.dist.common.integration.rest.prm.IPrmRightsholderService;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.service.impl.CommonRightsholderService;
import com.copyright.rup.dist.foreign.domain.RightsholderPayeePair;
import com.copyright.rup.dist.foreign.domain.RightsholderTypeOfUsePair;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.repository.api.IRightsholderRepository;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

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
public class RightsholderService extends CommonRightsholderService implements IRightsholderService {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    private final IRightsholderRepository rightsholderRepository;

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    /**
     * Constructor.
     *
     * @param rightsholderRepository             an instance of {@link IRightsholderRepository}
     * @param prmRightsholderService             an instance of {@link IPrmRightsholderService}
     * @param prmRightsholderOrganizationService an instance of {@link IPrmRightsholderOrganizationService}
     */
    @Autowired
    public RightsholderService(IRightsholderRepository rightsholderRepository,
                               @Qualifier("dist.common.integration.rest.prmRightsholderAsyncService")
                               IPrmRightsholderService prmRightsholderService,
                               @Qualifier("dist.common.integration.rest.prmRightsholderOrganizationAsyncService")
                               IPrmRightsholderOrganizationService prmRightsholderOrganizationService) {
        super(rightsholderRepository, prmRightsholderService, prmRightsholderOrganizationService);
        this.rightsholderRepository = rightsholderRepository;
    }

    @Override
    public List<Rightsholder> getRros(String productFamily) {
        return rightsholderRepository.findRros(productFamily);
    }

    @Override
    public void updateRightsholder(Rightsholder rightsholder) {
        Objects.requireNonNull(rightsholder);
        LOGGER.info("Update rightsholder information. Started. RhAccount#={}", rightsholder.getAccountNumber());
        rightsholderRepository.deleteByAccountNumber(rightsholder.getAccountNumber());
        rightsholderRepository.insert(rightsholder);
        LOGGER.info("Update rightsholder information. Finished. RhAccount#={}", rightsholder.getAccountNumber());
    }

    @Override
    public List<Rightsholder> getAllWithSearch(String searchValue, Pageable pageable, Sort sort) {
        return rightsholderRepository.findAllWithSearch(searchValue, pageable, sort);
    }

    @Override
    public int getCountWithSearch(String searchValue) {
        return rightsholderRepository.findCountWithSearch(searchValue);
    }

    @Override
    public void updateRighstholdersAsync(Set<Long> accountNumbers) {
        executorService.execute(() -> updateRightsholders(accountNumbers));
    }

    @Override
    public void updateUsagesPayeesAsync(List<Usage> usages) {
        updateRighstholdersAsync(
            usages.stream()
                .map(usage -> usage.getPayee().getAccountNumber())
                .filter(Objects::nonNull)
                .collect(Collectors.toSet()));
    }

    @Override
    public Map<String, Long> findAccountNumbersByRightsholderIds(Set<String> rhIds) {
        return rightsholderRepository.findByIds(rhIds)
            .stream()
            .collect(Collectors.toMap(Rightsholder::getId, Rightsholder::getAccountNumber));
    }

    @Override
    public List<Rightsholder> getByScenarioId(String scenarioId) {
        return rightsholderRepository.findByScenarioId(scenarioId);
    }

    @Override
    public List<RightsholderTypeOfUsePair> getByAclGrantSetId(String grantSetId) {
        return rightsholderRepository.findByAclGrantSetId(grantSetId);
    }

    @Override
    public List<RightsholderPayeePair> getRhPayeePairByScenarioId(String scenarioId) {
        return rightsholderRepository.findRhPayeePairByScenarioId(scenarioId);
    }

    @Override
    public List<Rightsholder> getRightsholdersByAccountNumbers(Set<Long> accountNumbers) {
        return rightsholderRepository.findByAccountNumbers(accountNumbers);
    }

    /**
     * Pre destroy method.
     */
    @PreDestroy
    void preDestroy() {
        executorService.shutdown();
    }
}
