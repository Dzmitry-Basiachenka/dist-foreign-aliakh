package com.copyright.rup.dist.foreign.service.impl.matching;

import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.IWorkMatchingService;

import org.apache.commons.collections4.CollectionUtils;
import org.perf4j.StopWatch;
import org.perf4j.slf4j.Slf4JStopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    @Autowired
    private IUsageService usageService;
    @Autowired
    private IWorkMatchingService matchingService;

    /**
     * Finds works and updates WrWrkInsts and statuses of {@link Usage}s.
     */
    @Scheduled(cron = "$RUP{dist.foreign.service.schedule.works_match}")
    public void findWorksAndUpdateStatuses() {
        StopWatch stopWatch = new Slf4JStopWatch();
        List<Usage> usages = usageService.getUsagesWithBlankWrWrkInst();
        stopWatch.lap("usage.matchWorks_1_getUsagesWithBlankWrWrkInst");
        if (CollectionUtils.isNotEmpty(usages)) {
            Map<UsageGroupEnum, List<Usage>> usageGroups = usages.stream()
                .collect(Collectors.groupingBy(UsageGroupEnum.getGroupingFunction()));
            List<Usage> usagesByStandardNumber = usageGroups.get(UsageGroupEnum.STANDARD_NUMBER);
            if (CollectionUtils.isNotEmpty(usagesByStandardNumber)) {
                matchingService.matchByIdno(usagesByStandardNumber);
                stopWatch.lap("usage.matchWorks_2_matchByIdno");
            }
            List<Usage> usagesByTitle = usageGroups.get(UsageGroupEnum.TITLE);
            if (CollectionUtils.isNotEmpty(usagesByTitle)) {
                matchingService.matchByTitle(usagesByTitle);
                stopWatch.lap("usage.matchWorks_3_matchByTitle");
            }
            List<Usage> usagesWithNoStandardNumberAndTitle = usageGroups.get(UsageGroupEnum.SINGLE_USAGE);
            if (CollectionUtils.isNotEmpty(usagesWithNoStandardNumberAndTitle)) {
                matchingService.updateStatusForUsagesWithNoStandardNumberAndTitle(usagesWithNoStandardNumberAndTitle);
                stopWatch.lap("usage.matchWorks_4_updateStatusForUsagesWithNoStandardNumberAndTitle");
            }
        }
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
            Function<Usage, String> getMappingFunction() {
                return Usage::getStandardNumber;
            }

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
            Function<Usage, String> getMappingFunction() {
                return Usage::getWorkTitle;
            }

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
            Function<Usage, Usage> getMappingFunction() {
                return usage -> usage;
            }

            @Override
            String getNtsEligibleReason() {
                return "Detail was made eligible for NTS because gross amount is less than $100";
            }

            @Override
            Function<Usage, String> getWorkNotFoundReasonFunction() {
                return usage -> "Usage has no standard number and title";
            }
        };

        private static Function<Usage, UsageGroupEnum> getGroupingFunction() {
            return usage -> Objects.isNull(usage.getStandardNumber())
                ? Objects.isNull(usage.getWorkTitle()) ? SINGLE_USAGE : TITLE
                : STANDARD_NUMBER;
        }

        /**
         * @return {@link Function} for grouping {@link Usage}s.
         */
        abstract Function<Usage, ?> getMappingFunction();

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
