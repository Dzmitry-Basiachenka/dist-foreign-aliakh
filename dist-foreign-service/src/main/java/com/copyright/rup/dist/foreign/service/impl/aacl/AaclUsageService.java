package com.copyright.rup.dist.foreign.service.impl.aacl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IAaclUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.aacl.IAaclUsageService;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of {@link IAaclUsageService}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 01/22/2020
 *
 * @author Ihar Suvorau
 */
@Service
public class AaclUsageService implements IAaclUsageService {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IUsageAuditService usageAuditService;
    @Autowired
    private IAaclUsageRepository aaclUsageRepository;

    @Override
    public int insertUsages(UsageBatch usageBatch, Collection<Usage> usages) {
        String userName = RupContextUtils.getUserName();
        int size = usages.size();
        LOGGER.info("Insert AACL usages. Started. UsageBatchName={}, UsagesCount={}, UserName={}", usageBatch.getName(),
            size, userName);
        int period = usageBatch.getPaymentDate().getYear();
        usages.forEach(usage -> {
            usage.setBatchId(usageBatch.getId());
            usage.getAaclUsage().setUsagePeriod(period);
            usage.getAaclUsage().setBatchPeriodEndDate(usageBatch.getPaymentDate());
            usage.setCreateUser(userName);
            usage.setUpdateUser(userName);
            aaclUsageRepository.insert(usage);
        });
        String loadedReason = "Uploaded in '" + usageBatch.getName() + "' Batch";
        usages.forEach(usage -> usageAuditService.logAction(usage.getId(), UsageActionTypeEnum.LOADED, loadedReason));
        LOGGER.info("Insert AACL usages. Finished. UsageBatchName={}, UsagesCount={}, UserName={}",
            usageBatch.getName(), size, userName);
        return size;
    }

    @Override
    public List<UsageDto> getUsageDtos(UsageFilter filter, Pageable pageable, Sort sort) {
        return !filter.isEmpty()
            ? aaclUsageRepository.findDtosByFilter(filter, pageable, sort)
            : Collections.emptyList();
    }

    @Override
    public int getUsagesCount(UsageFilter filter) {
        return !filter.isEmpty() ? aaclUsageRepository.findCountByFilter(filter) : 0;
    }

    @Override
    public List<Integer> getUsagePeriods() {
        return aaclUsageRepository.findUsagePeriods();
    }

    @Override
    @Transactional
    public void deleteById(String usageId) {
        usageAuditService.deleteActionsByUsageId(usageId);
        aaclUsageRepository.deleteById(usageId);
    }
}
