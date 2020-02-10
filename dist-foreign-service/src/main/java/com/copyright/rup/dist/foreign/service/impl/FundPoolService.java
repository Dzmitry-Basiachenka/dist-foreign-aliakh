package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.repository.api.IFundPoolRepository;
import com.copyright.rup.dist.foreign.service.api.IFundPoolService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.nts.INtsUsageService;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Implementation of {@link IFundPoolService}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 03/28/2019
 *
 * @author Ihar Suvorau
 */
@Service
public class FundPoolService implements IFundPoolService {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IFundPoolRepository fundPoolRepository;
    @Autowired
    private IUsageService usageService;
    @Autowired
    private INtsUsageService ntsUsageService;

    @Override
    @Transactional
    public void create(FundPool fundPool, Set<String> batchIds) {
        String userName = RupContextUtils.getUserName();
        fundPool.setCreateUser(userName);
        fundPool.setUpdateUser(userName);
        LOGGER.info(
            "Create Pre-Service fee fund. Started. FundPoolName={}, FundPoolAmount={}, BatchesCount={}, UserName={}",
            fundPool.getName(), fundPool.getTotalAmount(), LogUtils.size(batchIds), userName);
        fundPoolRepository.insert(fundPool);
        usageService.addWithdrawnUsagesToPreServiceFeeFund(fundPool.getId(), batchIds, userName);
        LOGGER.info(
            "Create Pre-Service fee fund. Finished. FundPoolName={}, FundPoolAmount={}, BatchesCount={}, UserName={}",
            fundPool.getName(), fundPool.getTotalAmount(), LogUtils.size(batchIds), userName);
    }

    @Override
    public List<FundPool> getPreServiceFeeFunds(String productFamily) {
        return fundPoolRepository.findByProductFamily(productFamily);
    }

    @Override
    public List<FundPool> getPreServiceFeeFundsNotAttachedToScenario() {
        return fundPoolRepository.findNotAttachedToScenario();
    }

    @Override
    @Transactional
    public void deletePreServiceFeeFund(FundPool fund) {
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Delete Pre-Service fee fund. Started. FundPoolName={}, UserName={}", fund.getName(), userName);
        ntsUsageService.deleteFromPreServiceFeeFund(fund.getId());
        fundPoolRepository.delete(fund.getId());
        LOGGER.info("Delete Pre-Service fee fund. Finished. FundPoolName={}, UserName={}", fund.getName(), userName);
    }

    @Override
    public List<String> getPreServiceFeeFundNamesByUsageBatchId(String batchId) {
        return fundPoolRepository.findNamesByUsageBatchId(batchId);
    }

    @Override
    public boolean fundPoolExists(String productFamily, String name) {
        return fundPoolRepository.fundPoolExists(productFamily, name);
    }
}
