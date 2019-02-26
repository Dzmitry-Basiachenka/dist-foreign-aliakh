package com.copyright.rup.dist.foreign.ui.rest;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.foreign.domain.report.UsageBatchStatistic;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.NotFoundException;
import com.copyright.rup.dist.foreign.ui.rest.gen.api.StatisticApiDelegate;
import com.copyright.rup.dist.foreign.ui.rest.gen.model.BatchStatistic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.validation.constraints.NotNull;
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
public class StatisticRest implements StatisticApiDelegate {

    private static final DateTimeFormatter FORMATTER =
        DateTimeFormatter.ofPattern(RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT);

    @Autowired
    private IUsageAuditService usageAuditService;

    @Override
    public ResponseEntity<BatchStatistic> getBatchStatistic(
        @NotNull String name, @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$") String date) {
        UsageBatchStatistic ubs = usageAuditService.getBatchStatistic(name,
            (null != date) ? LocalDate.parse(date, FORMATTER) : null);
        if (null == ubs) {
            throw new NotFoundException("Batch not found. BatchName=" + name);
        }
        return new ResponseEntity<>(buildBatchStatistic(ubs), HttpStatus.OK);
    }

    private BatchStatistic buildBatchStatistic(UsageBatchStatistic ubs) {
        BatchStatistic bs = new BatchStatistic();
        bs.setBatchName(ubs.getBatchName());
        bs.setDate((null != ubs.getDate()) ? FORMATTER.format(ubs.getDate()) : null);
        bs.setLoadedCount(ubs.getLoadedCount());
        bs.setLoadedAmount(ubs.getLoadedAmount());
        bs.setMatchedCount(ubs.getMatchedCount());
        bs.setMatchedAmount(ubs.getMatchedAmount());
        bs.setMatchedPercent(ubs.getMatchedPercent());
        bs.setWorksNotFoundCount(ubs.getWorksNotFoundCount());
        bs.setWorksNotFoundAmount(ubs.getWorksNotFoundAmount());
        bs.setWorksNotFoundPercent(ubs.getWorksNotFoundPercent());
        bs.setMultipleMatchingCount(ubs.getMultipleMatchingCount());
        bs.setMultipleMatchingAmount(ubs.getMultipleMatchingAmount());
        bs.setMultipleMatchingPercent(ubs.getMultipleMatchingPercent());
        bs.setNtsWithDrawnCount(ubs.getNtsWithDrawnCount());
        bs.setNtsWithDrawnAmount(ubs.getNtsWithDrawnAmount());
        bs.setNtsWithDrawnPercent(ubs.getNtsWithDrawnPercent());
        bs.setRhNotFoundCount(ubs.getRhNotFoundCount());
        bs.setRhNotFoundAmount(ubs.getRhNotFoundAmount());
        bs.setRhNotFoundPercent(ubs.getRhNotFoundPercent());
        bs.setEligibleCount(ubs.getEligibleCount());
        bs.setEligibleAmount(ubs.getEligibleAmount());
        bs.setEligiblePercent(ubs.getEligiblePercent());
        bs.setSendForRaCount(ubs.getSendForRaCount());
        bs.setSendForRaAmount(ubs.getSendForRaAmount());
        bs.setSendForRaPercent(ubs.getSendForRaPercent());
        bs.setPaidCount(ubs.getPaidCount());
        bs.setPaidAmount(ubs.getPaidAmount());
        bs.setPaidPercent(ubs.getPaidPercent());
        return bs;
    }
}

