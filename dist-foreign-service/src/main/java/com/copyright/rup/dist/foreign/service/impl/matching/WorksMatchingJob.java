package com.copyright.rup.dist.foreign.service.impl.matching;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.IWorkMatchingService;

import org.apache.commons.collections4.CollectionUtils;
import org.perf4j.StopWatch;
import org.perf4j.slf4j.Slf4JStopWatch;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

/**
 * This class is responsible for running scheduled processes for getting works and setting statuses for {@link Usage}s.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 3/6/18
 *
 * @author Aliaksandr Radkevich
 */
@Component
public class WorksMatchingJob {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IUsageService usageService;
    @Autowired
    private IWorkMatchingService matchingService;
    @Value("$RUP{dist.foreign.integration.works.pi.batch_size}")
    private int batchSize;

    /**
     * Finds works and updates WrWrkInsts and statuses of {@link Usage}s.
     */
    @Scheduled(cron = "$RUP{dist.foreign.service.schedule.works_match}")
    public void findWorksAndUpdateStatuses() {
        StopWatch stopWatch = new Slf4JStopWatch();
        int standardNumbersCount = usageService.getStandardNumbersCount();
        LOGGER.info("Search works. Started. UsagesCount={}", standardNumbersCount);
        for (int i = 0; i < standardNumbersCount; i += batchSize) {
            List<Usage> usagesByStandardNumber = usageService.getUsagesWithStandardNumber(batchSize);
            if (CollectionUtils.isNotEmpty(usagesByStandardNumber)) {
                matchingService.matchByIdno(usagesByStandardNumber);
            }
        }
        int titlesCount = usageService.getTitlesCount();
        for (int i = 0; i < titlesCount; i += batchSize) {
            List<Usage> usagesByTitle = usageService.getUsagesWithTitle(batchSize);
            if (CollectionUtils.isNotEmpty(usagesByTitle)) {
                matchingService.matchByTitle(usagesByTitle);
            }
        }
        matchingService.updateStatusForUsagesWithNoStandardNumberAndTitle(
            usageService.getUsagesWithoutStandardNumberAndTitle());
        LOGGER.info("Search works. Finished. UsagesCount={}", standardNumbersCount);
        stopWatch.stop("usage.matchWorks_findWorksAndUpdateStatuses");
    }

    /**
     * Enum of usage groups.
     */
    enum UsageGroupEnum {

        /**
         * Enum constant to separate groups of {@link Usage}s with standard number.
         */
        STANDARD_NUMBER {

            @Override
            String getNtsEligibleReason() {
                return "Detail was made eligible for NTS because sum of gross amounts, grouped by standard number, " +
                    "is less than $100";
            }

            @Override
            Function<Usage, String> getWorkNotFoundReasonFunction() {
                return usage -> "Wr Wrk Inst was not found by standard number " + usage.getStandardNumber();
            }
        },
        /**
         * Enum constant to separate groups of {@link Usage}s with work title.
         */
        TITLE {

            @Override
            String getNtsEligibleReason() {
                return "Detail was made eligible for NTS because sum of gross amounts, grouped by work title, " +
                    "is less than $100";
            }

            @Override
            Function<Usage, String> getWorkNotFoundReasonFunction() {
                return usage -> "Wr Wrk Inst was not found by title \"" + usage.getWorkTitle() + "\"";
            }
        },
        /**
         * Enum constant to separate groups of {@link Usage}s that don't have standard number and work title.
         */
        SINGLE_USAGE {

            @Override
            String getNtsEligibleReason() {
                return "Detail was made eligible for NTS because gross amount is less than $100";
            }

            @Override
            Function<Usage, String> getWorkNotFoundReasonFunction() {
                return usage -> "Usage has no standard number and title";
            }
        };

        /**
         * @return reason for making {@link Usage}
         * {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#ELIGIBLE} for NTS.
         */
        abstract String getNtsEligibleReason();

        /**
         * @return {@link Function} for building reason for setting status
         * {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#WORK_NOT_FOUND} for {@link Usage}.
         */
        abstract Function<Usage, String> getWorkNotFoundReasonFunction();
    }
}
