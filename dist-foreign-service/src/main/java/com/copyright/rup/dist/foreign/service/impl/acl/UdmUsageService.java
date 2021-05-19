package com.copyright.rup.dist.foreign.service.impl.acl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.UdmBatch;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.domain.UdmUsageDto;
import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IUdmUsageRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageService;
import com.copyright.rup.dist.foreign.service.impl.InconsistentUsageStateException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Implementation of {@link IUdmUsageService}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 04/30/2021
 *
 * @author Uladzislau Shalamitski
 */
@Service
public class UdmUsageService implements IUdmUsageService {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IUdmUsageRepository udmUsageRepository;

    @Override
    @Transactional
    public void insertUdmUsages(UdmBatch udmBatch, List<UdmUsage> udmUsages) {
        String userName = RupContextUtils.getUserName();
        int size = udmUsages.size();
        LOGGER.info("Insert UDM usages. Started. UsageBatchName={}, UsagesCount={}, UserName={}", udmBatch.getName(),
            size, userName);
        LocalDate periodEndDate = createPeriodEndDate(udmBatch);
        udmUsages.forEach(usage -> {
            usage.setBatchId(udmBatch.getId());
            usage.setPeriodEndDate(periodEndDate);
            usage.setCreateUser(userName);
            usage.setUpdateUser(userName);
            udmUsageRepository.insert(usage);
        });
        LOGGER.info("Insert UDM usages. Finished. UsageBatchName={}, UsagesCount={}, UserName={}", udmBatch.getName(),
            size, userName);
    }

    @Override
    public boolean isOriginalDetailIdExist(String originalDetailId) {
        return udmUsageRepository.isOriginalDetailIdExist(originalDetailId);
    }

    @Override
    public List<UdmUsageDto> getUsageDtos(UdmUsageFilter filter, Pageable pageable, Sort sort) {
        return !filter.isEmpty()
            ? udmUsageRepository.findDtosByFilter(filter, pageable, sort)
            : Collections.emptyList();
    }

    @Override
    public int getUsagesCount(UdmUsageFilter filter) {
        return !filter.isEmpty() ? udmUsageRepository.findCountByFilter(filter) : 0;
    }

    @Override
    public void updateProcessedUsage(UdmUsage udmUsage) {
        String usageId = udmUsageRepository.updateProcessedUsage(udmUsage);
        if (Objects.isNull(usageId)) {
            throw new InconsistentUsageStateException(udmUsage);
        }
        udmUsage.setVersion(udmUsage.getVersion() + 1);
    }

    private LocalDate createPeriodEndDate(UdmBatch udmBatch) {
        String stringPeriod = String.valueOf(udmBatch.getPeriod());
        int year = Integer.parseInt(stringPeriod.substring(0, 4));
        int month = Integer.parseInt(stringPeriod.substring(4, 6));
        return 6 == month ? LocalDate.of(year, month, 30) : LocalDate.of(year, month, 31);
    }
}
