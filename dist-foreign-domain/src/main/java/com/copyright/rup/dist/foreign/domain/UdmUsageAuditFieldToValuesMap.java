package com.copyright.rup.dist.foreign.domain;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Objects;
import java.util.function.Function;

/**
 * Domain object for holding map of field names and its old and new values of UDM usages.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 08/18/21
 *
 * @author Uladzislau Shalamitski
 */
public class UdmUsageAuditFieldToValuesMap extends CommonAuditFieldToValuesMap {

    /**
     * Constructor.
     */
    public UdmUsageAuditFieldToValuesMap() {
    }

    /**
     * Constructor.
     *
     * @param usageDto instance of {@link UdmUsageDto}
     */
    public UdmUsageAuditFieldToValuesMap(UdmUsageDto usageDto) {
        getFieldToValueChangesMap().put("Detail Status", Objects.nonNull(usageDto.getStatus())
            ? buildPair(usageDto, usage -> usage.getStatus().name())
            : EMPTY_PAIR);
        getFieldToValueChangesMap().put("Period", buildPair(usageDto, UdmUsageDto::getPeriod));
        getFieldToValueChangesMap().put("Wr Wrk Inst", buildPair(usageDto, UdmUsageDto::getWrWrkInst));
        getFieldToValueChangesMap().put("Reported Title", buildPair(usageDto, UdmUsageDto::getReportedTitle));
        getFieldToValueChangesMap().put("Reported Standard Number",
            buildPair(usageDto, UdmUsageDto::getReportedStandardNumber));
        getFieldToValueChangesMap().put("Reported Pub Type", buildPair(usageDto, UdmUsageDto::getReportedPubType));
        getFieldToValueChangesMap().put("Action Reason", Objects.nonNull(usageDto.getActionReason())
            ? buildPair(usageDto, usage -> usage.getActionReason().getReason())
            : EMPTY_PAIR);
        getFieldToValueChangesMap().put("Comment", buildPair(usageDto, UdmUsageDto::getComment));
        getFieldToValueChangesMap().put("Research URL", buildPair(usageDto, UdmUsageDto::getResearchUrl));
        getFieldToValueChangesMap().put("Company ID", buildPair(usageDto, UdmUsageDto::getCompanyId));
        getFieldToValueChangesMap().put("Company Name", buildPair(usageDto, UdmUsageDto::getCompanyName));
        getFieldToValueChangesMap().put("Detail Licensee Class", Objects.nonNull(usageDto.getDetailLicenseeClass())
            ? buildPair(usageDto, usage -> usage.getDetailLicenseeClass().getIdAndDescription())
            : EMPTY_PAIR);
        getFieldToValueChangesMap().put("Annual Multiplier", buildPair(usageDto, UdmUsageDto::getAnnualMultiplier));
        getFieldToValueChangesMap().put("Statistical Multiplier",
            buildPair(usageDto, UdmUsageDto::getStatisticalMultiplier));
        getFieldToValueChangesMap().put("Quantity", buildPair(usageDto, UdmUsageDto::getQuantity));
        getFieldToValueChangesMap().put("Annualized Copies", buildPair(usageDto, UdmUsageDto::getAnnualizedCopies));
        getFieldToValueChangesMap().put("Ineligible Reason", Objects.nonNull(usageDto.getIneligibleReason())
            ? buildPair(usageDto, usage -> usage.getIneligibleReason().getReason())
            : EMPTY_PAIR);
    }

    private Pair<String, String> buildPair(UdmUsageDto udmUsageDto, Function<UdmUsageDto, Object> function) {
        String stringRepresentation = Objects.toString(function.apply(udmUsageDto), StringUtils.EMPTY);
        return Pair.of(stringRepresentation, stringRepresentation);
    }
}
