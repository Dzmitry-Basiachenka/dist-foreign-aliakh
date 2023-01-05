package com.copyright.rup.dist.foreign.ui.rest;

import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.report.UsageStatistic;
import com.copyright.rup.dist.foreign.repository.api.IUsageArchiveRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageAuditRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.NotFoundException;
import com.copyright.rup.dist.foreign.ui.rest.gen.api.StatisticUsageApiDelegate;
import com.copyright.rup.dist.foreign.ui.rest.gen.model.UsageStat;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * REST service for usage statistic.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 03/06/2019
 *
 * @author Aliaksanr Liakh
 */
@Component
public class StatisticUsageRest implements StatisticUsageApiDelegate {

    @Autowired
    private IUsageAuditRepository usageAuditRepository;

    @Autowired
    private IUsageRepository usageRepository;

    @Autowired
    private IUsageArchiveRepository usageArchiveRepository;

    @Override
    public ResponseEntity<UsageStat> getUsageStatistic(String usageId) {
        UsageStatistic statistic = usageAuditRepository.getUsageStatistic(usageId);
        if (null != statistic) {
            return new ResponseEntity<>(buildResponse(statistic), HttpStatus.OK);
        } else {
            List<Usage> usages = usageRepository.findByIds(List.of(usageId));
            if (CollectionUtils.isNotEmpty(usages)) {
                return new ResponseEntity<>(buildResponse(usages.get(0)), HttpStatus.OK);
            }
            usages = usageArchiveRepository.findByIds(List.of(usageId));
            if (CollectionUtils.isNotEmpty(usages)) {
                return new ResponseEntity<>(buildResponse(usages.get(0)), HttpStatus.OK);
            }
            throw new NotFoundException("Usage not found. UsageId=" + usageId);
        }
    }

    private UsageStat buildResponse(UsageStatistic statistic) {
        UsageStat stat = new UsageStat();
        stat.setUsageId(statistic.getUsageId());
        stat.setStatus(statistic.getStatus().name());
        stat.setMatchingMs(statistic.getMatchingMs());
        stat.setRightsMs(statistic.getRightsMs());
        stat.setEligibilityMs(statistic.getEligibilityMs());
        return stat;
    }

    private UsageStat buildResponse(Usage usage) {
        UsageStat stat = new UsageStat();
        stat.setUsageId(usage.getId());
        stat.setStatus(usage.getStatus().name());
        return stat;
    }
}
