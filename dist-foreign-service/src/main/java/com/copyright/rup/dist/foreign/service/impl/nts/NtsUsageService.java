package com.copyright.rup.dist.foreign.service.impl.nts;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.repository.api.INtsUsageRepository;
import com.copyright.rup.dist.foreign.service.api.nts.INtsUsageService;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of {@link INtsUsageService}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 01/28/2020
 *
 * @author Aliaksandr Liakh
 */
@Service
public class NtsUsageService implements INtsUsageService {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private INtsUsageRepository ntsUsageRepository;

    @Override
    @Transactional
    public List<String> insertUsages(UsageBatch usageBatch) {
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Insert NTS usages. Started. UsageBatchName={}, UserName={}", usageBatch.getName(), userName);
        List<String> usageIds = ntsUsageRepository.insertUsages(usageBatch, userName);
        LOGGER.info("Insert NTS usages. Finished. UsageBatchName={}, UserName={}, InsertedUsageCount={}",
            usageBatch.getName(), userName, LogUtils.size(usageIds));
        return usageIds;
    }
}
