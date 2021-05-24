package com.copyright.rup.dist.foreign.service.impl.acl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.UdmBatch;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.domain.UdmUsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IUdmUsageRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmTypeOfUseService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageService;
import com.copyright.rup.dist.foreign.service.api.executor.IChainExecutor;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;
import com.copyright.rup.dist.foreign.service.impl.InconsistentUsageStateException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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

    private static final BigDecimal DEFAULT_STATISTICAL_MULTIPLIER =
        BigDecimal.ONE.setScale(5, BigDecimal.ROUND_HALF_UP);
    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IUdmUsageRepository udmUsageRepository;
    @Autowired
    private UdmAnnualMultiplierCalculator udmAnnualMultiplierCalculator;
    @Autowired
    private IUdmTypeOfUseService udmTypeOfUseService;
    @Autowired
    @Qualifier("udmUsageChainChunkExecutor")
    private IChainExecutor<UdmUsage> chainExecutor;

    @Override
    @Transactional
    public void insertUdmUsages(UdmBatch udmBatch, List<UdmUsage> udmUsages) {
        String userName = RupContextUtils.getUserName();
        int size = udmUsages.size();
        LOGGER.info("Insert UDM usages. Started. UsageBatchName={}, UsagesCount={}, UserName={}", udmBatch.getName(),
            size, userName);
        LocalDate periodEndDate = createPeriodEndDate(udmBatch);
        Map<String, String> udmTouToRmsTou = udmTypeOfUseService.getUdmTouToRmsTouMap();
        udmUsages.forEach(usage -> {
            usage.setTypeOfUse(udmTouToRmsTou.get(usage.getReportedTypeOfUse()));
            usage.setBatchId(udmBatch.getId());
            usage.setPeriodEndDate(periodEndDate);
            usage.setAnnualMultiplier(udmAnnualMultiplierCalculator.calculate(usage.getSurveyStartDate(),
                usage.getSurveyEndDate()));
            usage.setStatisticalMultiplier(DEFAULT_STATISTICAL_MULTIPLIER);
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
    public void sendForMatching(List<UdmUsage> udmUsages) {
        chainExecutor.execute(() -> {
            List<UdmUsage> usagesInNewStatus = udmUsages.stream()
                .filter(usage -> UsageStatusEnum.NEW == usage.getStatus())
                .collect(Collectors.toList());
            chainExecutor.execute(usagesInNewStatus, ChainProcessorTypeEnum.MATCHING);
        });
    }

    @Override
    public void updateProcessedUsage(UdmUsage udmUsage) {
        String usageId = udmUsageRepository.updateProcessedUsage(udmUsage);
        if (Objects.isNull(usageId)) {
            throw new InconsistentUsageStateException(udmUsage);
        }
        udmUsage.setVersion(udmUsage.getVersion() + 1);
    }

    @Override
    public List<String> getUdmUsageIdsByStatus(UsageStatusEnum status) {
        return udmUsageRepository.findIdsByStatus(status);
    }

    @Override
    public List<UdmUsage> getUdmUsagesByIds(List<String> udmUsageIds) {
        return udmUsageRepository.findByIds(udmUsageIds);
    }

    private LocalDate createPeriodEndDate(UdmBatch udmBatch) {
        String stringPeriod = String.valueOf(udmBatch.getPeriod());
        int year = Integer.parseInt(stringPeriod.substring(0, 4));
        int month = Integer.parseInt(stringPeriod.substring(4, 6));
        return 6 == month ? LocalDate.of(year, month, 30) : LocalDate.of(year, month, 31);
    }
}
