package com.copyright.rup.dist.foreign.service.impl.acl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.UdmBaselineDto;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineFilter;
import com.copyright.rup.dist.foreign.repository.api.IUdmBaselineRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmBaselineService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageAuditService;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of {@link IUdmBaselineService}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/03/21
 *
 * @author Anton Azarenka
 */
@Service
public class UdmBaselineService implements IUdmBaselineService {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Value("$RUP{dist.foreign.grid.multi.select.record.threshold}")
    private int udmRecordsThreshold;
    @Autowired
    private IUdmUsageAuditService udmUsageAuditService;
    @Autowired
    private IUdmBaselineRepository baselineRepository;

    @Override
    public List<UdmBaselineDto> getBaselineUsageDtos(UdmBaselineFilter filter, Pageable pageable, Sort sort) {
        return !filter.isEmpty()
            ? baselineRepository.findDtosByFilter(filter, pageable, sort)
            : List.of();
    }

    @Override
    public int getBaselineUsagesCount(UdmBaselineFilter filter) {
        return !filter.isEmpty() ? baselineRepository.findCountByFilter(filter) : 0;
    }

    @Override
    public List<Integer> getPeriods() {
        return baselineRepository.findPeriods();
    }

    @Override
    public int getUdmRecordThreshold() {
        return udmRecordsThreshold;
    }

    @Override
    public Map<Long, String> getWrWrkInstToSystemTitles(Set<Integer> periods) {
        return baselineRepository.findWrWrkInstToSystemTitles(periods);
    }

    @Override
    @Transactional
    public void deleteFromBaseline(Set<String> usageIds, String reason, String userName) {
        LOGGER.info("Delete UDM usages from baseline. Started. UsageIds={}, Reason={}, UserName={}", usageIds, reason,
            userName);
        usageIds.forEach(usageId -> {
            baselineRepository.removeUdmUsageFromBaselineById(usageId);
            udmUsageAuditService.logAction(usageId, UsageActionTypeEnum.REMOVE_FROM_BASELINE, reason);
        });
        LOGGER.info("Delete UDM usages from baseline. Finished. UsageIds={}, Reason={}, UserName={}", usageIds, reason,
            userName);
    }
}
