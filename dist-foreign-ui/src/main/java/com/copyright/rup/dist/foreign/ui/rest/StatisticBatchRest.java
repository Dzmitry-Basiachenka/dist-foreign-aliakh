package com.copyright.rup.dist.foreign.ui.rest;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.foreign.domain.report.BatchStatistic;
import com.copyright.rup.dist.foreign.service.api.BadRequestException;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.NotFoundException;
import com.copyright.rup.dist.foreign.ui.rest.gen.api.StatisticBatchApiDelegate;
import com.copyright.rup.dist.foreign.ui.rest.gen.model.BatchStat;
import com.copyright.rup.dist.foreign.ui.rest.gen.model.BatchStats;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.validation.constraints.Pattern;

/**
 * REST service for batch statistic.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 02/25/2019
 *
 * @author Aliaksanr Liakh
 */
@Component
public class StatisticBatchRest implements StatisticBatchApiDelegate {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(RupDateUtils.DATE_FORMAT_PATTERN);
    private static final String DATE_REGEXP = "^\\d{4}-\\d{2}-\\d{2}$";

    @Autowired
    private IUsageAuditService usageAuditService;

    @Override
    public ResponseEntity<BatchStats> getBatchesStatistic(
        String name, @Pattern(regexp = DATE_REGEXP) String date,
        @Pattern(regexp = DATE_REGEXP) String dateFrom, @Pattern(regexp = DATE_REGEXP) String dateTo) {
        List<BatchStatistic> statistics = StringUtils.isNotBlank(name)
            ? usageAuditService.getBatchesStatisticByBatchNameAndDate(name, parseDate(date))
            : usageAuditService.getBatchesStatisticByDateFromAndDateTo(parseDate(dateFrom), parseDate(dateTo));
        if (CollectionUtils.isEmpty(statistics) && StringUtils.isNotBlank(name)) {
            throw new NotFoundException("Batch not found. BatchName=" + name);
        }
        return new ResponseEntity<>(buildBatchStats(statistics, date, dateFrom, dateTo), HttpStatus.OK);
    }

    private LocalDate parseDate(String date) {
        try {
            return (null != date) ? LocalDate.parse(date, FORMATTER) : null;
        } catch (DateTimeParseException e) {
            throw new BadRequestException(e);
        }
    }

    private BatchStats buildBatchStats(List<BatchStatistic> statistics, String date, String dateFrom, String dateTo) {
        BatchStats stats = new BatchStats();
        stats.setDate(date);
        stats.setDateFrom(dateFrom);
        stats.setDateTo(dateTo);
        for (BatchStatistic statistic : statistics) {
            stats.addStatisticItem(buildBatchStat(statistic));
        }
        return stats;
    }

    private BatchStat buildBatchStat(BatchStatistic statistic) {
        return new BatchStat()
            .batchName(statistic.getBatchName())
            .totalCount(statistic.getTotalCount())
            .totalAmount(statistic.getTotalAmount())
            .matchedCount(statistic.getMatchedCount())
            .matchedAmount(statistic.getMatchedAmount())
            .matchedPercent(statistic.getMatchedPercent())
            .worksNotFoundCount(statistic.getWorksNotFoundCount())
            .worksNotFoundAmount(statistic.getWorksNotFoundAmount())
            .worksNotFoundPercent(statistic.getWorksNotFoundPercent())
            .multipleMatchingCount(statistic.getMultipleMatchingCount())
            .multipleMatchingAmount(statistic.getMultipleMatchingAmount())
            .multipleMatchingPercent(statistic.getMultipleMatchingPercent())
            .ntsWithdrawnCount(statistic.getNtsWithdrawnCount())
            .ntsWithdrawnAmount(statistic.getNtsWithdrawnAmount())
            .ntsWithdrawnPercent(statistic.getNtsWithdrawnPercent())
            .rhNotFoundCount(statistic.getRhNotFoundCount())
            .rhNotFoundAmount(statistic.getRhNotFoundAmount())
            .rhNotFoundPercent(statistic.getRhNotFoundPercent())
            .rhFoundCount(statistic.getRhFoundCount())
            .rhFoundAmount(statistic.getRhFoundAmount())
            .rhFoundPercent(statistic.getRhFoundPercent())
            .eligibleCount(statistic.getEligibleCount())
            .eligibleAmount(statistic.getEligibleAmount())
            .eligiblePercent(statistic.getEligiblePercent())
            .sendForRaCount(statistic.getSendForRaCount())
            .sendForRaAmount(statistic.getSendForRaAmount())
            .sendForRaPercent(statistic.getSendForRaPercent())
            .paidCount(statistic.getPaidCount())
            .paidAmount(statistic.getPaidAmount())
            .paidPercent(statistic.getPaidPercent());
    }
}
