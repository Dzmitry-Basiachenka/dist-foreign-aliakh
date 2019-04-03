package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.WithdrawnFundPool;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.repository.api.IWithdrawnFundPoolRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.IWithdrawnFundPoolService;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of {@link IWithdrawnFundPoolService}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 03/28/2019
 *
 * @author Ihar Suvorau
 */
@Service
public class WithdrawnFundPoolService implements IWithdrawnFundPoolService {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IWithdrawnFundPoolRepository withdrawnFundPoolRepository;
    @Autowired
    private IUsageRepository usageRepository;
    @Autowired
    private IUsageService usageService;

    @Override
    @Transactional
    public void create(WithdrawnFundPool fundPool, List<String> batchIds) {
        String userName = RupContextUtils.getUserName();
        fundPool.setCreateUser(userName);
        fundPool.setUpdateUser(userName);
        LOGGER.info("Create fund pool. Started. FundPoolName={}, FundPoolAmount={}, BatchesCount={}, UserName={}",
            fundPool.getName(), fundPool.getAmount(), LogUtils.size(batchIds), userName);
        withdrawnFundPoolRepository.insert(fundPool);
        usageRepository.addWithdrawnUsagesToFundPool(fundPool.getId(), batchIds, userName);
        LOGGER.info("Create fund pool. Finished. FundPoolName={}, FundPoolAmount={}, BatchesCount={}, UserName={}",
            fundPool.getName(), fundPool.getAmount(), LogUtils.size(batchIds), userName);
    }

    @Override
    public List<WithdrawnFundPool> getAdditionalFunds() {
        return withdrawnFundPoolRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteAdditionalFund(WithdrawnFundPool fundPool) {
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Delete additional fund. Started. FundPoolName={}, UserName={}", fundPool.getName(), userName);
        usageService.deleteFromAdditionalFund(fundPool.getId());
        withdrawnFundPoolRepository.delete(fundPool.getId());
        LOGGER.info("Delete additional fund. Finished. FundPoolName={}, UserName={}", fundPool.getName(), userName);
    }

    @Override
    public List<String> getAdditionalFundNamesByUsageBatchId(String batchId) {
        return withdrawnFundPoolRepository.findNamesByUsageBatchId(batchId);
    }

    @Override
    public boolean fundPoolNameExists(String fundPoolName) {
        return 0 < withdrawnFundPoolRepository.findCountByName(fundPoolName);
    }
}
