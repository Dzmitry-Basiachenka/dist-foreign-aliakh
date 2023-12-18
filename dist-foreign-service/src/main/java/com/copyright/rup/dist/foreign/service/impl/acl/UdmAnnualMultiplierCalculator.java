package com.copyright.rup.dist.foreign.service.impl.acl;

import com.copyright.rup.common.exception.RupRuntimeException;

import com.google.common.collect.Maps;
import com.google.common.collect.Range;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Calculator of annual multiplier for UDM usages.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 05/19/2021
 *
 * @author Aliaksandr Liakh
 */
@Component
public class UdmAnnualMultiplierCalculator {

    private final List<Map.Entry<Range<Integer>, Integer>> intervalInDaysToAnnualMultipliers;

    /**
     * Constructor.
     *
     * @param differenceInDaysToAnnualMultiplierMap map from difference in days to annual multiplier
     */
    @Autowired
    UdmAnnualMultiplierCalculator(@Value("#{$RUP{dist.foreign.udm.difference_in_days.to.annual_multiplier}}")
                                  Map<String, Integer> differenceInDaysToAnnualMultiplierMap) {
        this.intervalInDaysToAnnualMultipliers =
            differenceInDaysToAnnualMultiplierMap
                .entrySet()
                .stream()
                .map(entry -> {
                    String range = entry.getKey();
                    Integer annualMultiplier = entry.getValue();
                    if (range.contains("-")) {
                        int i = range.indexOf('-');
                        int minDifferenceInDays = Integer.parseInt(range.substring(0, i));
                        int maxDifferenceInDays = Integer.parseInt(range.substring(i + 1));
                        return Maps.immutableEntry(Range.closed(minDifferenceInDays, maxDifferenceInDays),
                            annualMultiplier);
                    } else if (0 == range.indexOf('>')) {
                        int minDifferenceInDays = Integer.parseInt(range.substring(1)) + 1;
                        return Maps.immutableEntry(Range.atLeast(minDifferenceInDays), annualMultiplier);
                    } else {
                        throw new RupRuntimeException("Unable to parse the range of the annual multiplier: " + range);
                    }
                })
                .collect(Collectors.toUnmodifiableList());
    }

    /**
     * Calculates annual multiplier.
     *
     * @param surveyStartDate survey start date
     * @param surveyEndDate   survey end date
     * @return annual multiplier
     */
    public int calculate(LocalDate surveyStartDate, LocalDate surveyEndDate) {
        int differenceInDays = (int) (ChronoUnit.DAYS.between(surveyStartDate, surveyEndDate) + 1);
        return intervalInDaysToAnnualMultipliers
            .stream()
            .filter(entry -> entry.getKey().contains(differenceInDays))
            .findFirst()
            .map(Map.Entry::getValue)
            .orElseThrow(() -> new RupRuntimeException("Unable to get an annual multiplier for the difference: " +
                differenceInDays));
    }
}
