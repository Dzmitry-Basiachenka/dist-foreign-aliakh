package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.WithdrawnFundPool;
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
    private IUsageService usageService;

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

    void setWithdrawnFundPoolRepository(IWithdrawnFundPoolRepository withdrawnFundPoolRepository) {
        this.withdrawnFundPoolRepository = withdrawnFundPoolRepository;
    }

    void setUsageService(IUsageService usageService) {
        this.usageService = usageService;
    }
}
