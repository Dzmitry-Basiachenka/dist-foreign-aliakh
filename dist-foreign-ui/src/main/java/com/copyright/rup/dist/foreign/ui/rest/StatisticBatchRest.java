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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;

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
        List<BatchStatistic> statistics = usageAuditService.getBatchesStatistic(name,
            parseDate(date), parseDate(dateFrom), parseDate(dateTo));
        if (CollectionUtils.isEmpty(statistics) && Objects.nonNull(name)) {
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
            stats.getStatistic().add(buildBatchStat(statistic));
        }
        return stats;
    }

    private BatchStat buildBatchStat(BatchStatistic statistic) {
        BatchStat stat = new BatchStat();
        stat.setBatchName(statistic.getBatchName());
        stat.setTotalCount(statistic.getTotalCount());
        stat.setLoadedCount(statistic.getLoadedCount());
        stat.setLoadedAmount(statistic.getLoadedAmount());
        stat.setLoadedPercent(statistic.getLoadedPercent());
        stat.setCreatedCount(statistic.getCreatedCount());
        stat.setCreatedAmount(statistic.getCreatedAmount());
        stat.setCreatedPercent(statistic.getCreatedPercent());
        stat.setMatchedCount(statistic.getMatchedCount());
        stat.setMatchedAmount(statistic.getMatchedAmount());
        stat.setMatchedPercent(statistic.getMatchedPercent());
        stat.setWorksNotFoundCount(statistic.getWorksNotFoundCount());
        stat.setWorksNotFoundAmount(statistic.getWorksNotFoundAmount());
        stat.setWorksNotFoundPercent(statistic.getWorksNotFoundPercent());
        stat.setMultipleMatchingCount(statistic.getMultipleMatchingCount());
        stat.setMultipleMatchingAmount(statistic.getMultipleMatchingAmount());
        stat.setMultipleMatchingPercent(statistic.getMultipleMatchingPercent());
        stat.setNtsWithDrawnCount(statistic.getNtsWithDrawnCount());
        stat.setNtsWithDrawnAmount(statistic.getNtsWithDrawnAmount());
        stat.setNtsWithDrawnPercent(statistic.getNtsWithDrawnPercent());
        stat.setRhNotFoundCount(statistic.getRhNotFoundCount());
        stat.setRhNotFoundAmount(statistic.getRhNotFoundAmount());
        stat.setRhNotFoundPercent(statistic.getRhNotFoundPercent());
        stat.setRhFoundCount(statistic.getRhFoundCount());
        stat.setRhFoundAmount(statistic.getRhFoundAmount());
        stat.setRhFoundPercent(statistic.getRhFoundPercent());
        stat.setEligibleCount(statistic.getEligibleCount());
        stat.setEligibleAmount(statistic.getEligibleAmount());
        stat.setEligiblePercent(statistic.getEligiblePercent());
        stat.setSendForRaCount(statistic.getSendForRaCount());
        stat.setSendForRaAmount(statistic.getSendForRaAmount());
        stat.setSendForRaPercent(statistic.getSendForRaPercent());
        stat.setPaidCount(statistic.getPaidCount());
        stat.setPaidAmount(statistic.getPaidAmount());
        stat.setPaidPercent(statistic.getPaidPercent());
        return stat;
    }
}
