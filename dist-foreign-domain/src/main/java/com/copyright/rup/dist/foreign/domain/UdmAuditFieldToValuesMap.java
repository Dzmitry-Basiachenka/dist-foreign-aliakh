package com.copyright.rup.dist.foreign.domain;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

/**
 * Domain object for holding map of field names and its old and new values.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 08/18/21
 *
 * @author Uladzislau Shalamitski
 */
public class UdmAuditFieldToValuesMap {

    private static final Pair<String, String> EMPTY_PAIR = Pair.of(null, null);
    private final Map<String, Pair<String, String>> fieldToValueChangesMap = new HashMap<>();

    /**
     * Constructor.
     */
    public UdmAuditFieldToValuesMap() {
    }

    /**
     * Constructor.
     *
     * @param usageDto instance of {@link UdmUsageDto}
     */
    public UdmAuditFieldToValuesMap(UdmUsageDto usageDto) {
        fieldToValueChangesMap.put("Detail Status", Objects.nonNull(usageDto.getStatus())
            ? buildPair(usageDto, usage -> usage.getStatus().name())
            : EMPTY_PAIR);
        fieldToValueChangesMap.put("Period", buildPair(usageDto, UdmUsageDto::getPeriod));
        fieldToValueChangesMap.put("Wr Wrk Inst", buildPair(usageDto, UdmUsageDto::getWrWrkInst));
        fieldToValueChangesMap.put("Reported Title", buildPair(usageDto, UdmUsageDto::getReportedTitle));
        fieldToValueChangesMap.put("Reported Standard Number",
            buildPair(usageDto, UdmUsageDto::getReportedStandardNumber));
        fieldToValueChangesMap.put("Reported Pub Type", buildPair(usageDto, UdmUsageDto::getReportedPubType));
        fieldToValueChangesMap.put("Action Reason", Objects.nonNull(usageDto.getActionReason())
            ? buildPair(usageDto, usage -> usage.getActionReason().getReason())
            : EMPTY_PAIR);
        fieldToValueChangesMap.put("Comment", buildPair(usageDto, UdmUsageDto::getComment));
        fieldToValueChangesMap.put("Research URL", buildPair(usageDto, UdmUsageDto::getResearchUrl));
        fieldToValueChangesMap.put("Company ID", buildPair(usageDto, UdmUsageDto::getCompanyId));
        fieldToValueChangesMap.put("Company Name", buildPair(usageDto, UdmUsageDto::getCompanyName));
        fieldToValueChangesMap.put("Detail Licensee Class", Objects.nonNull(usageDto.getDetailLicenseeClass())
            ? buildPair(usageDto, usage -> String.format("%s - %s", usage.getDetailLicenseeClass().getId(),
            usage.getDetailLicenseeClass().getDescription()))
            : EMPTY_PAIR);
        fieldToValueChangesMap.put("Annual Multiplier", buildPair(usageDto, UdmUsageDto::getAnnualMultiplier));
        fieldToValueChangesMap.put("Statistical Multiplier",
            buildPair(usageDto, UdmUsageDto::getStatisticalMultiplier));
        fieldToValueChangesMap.put("Quantity", buildPair(usageDto, UdmUsageDto::getQuantity));
        fieldToValueChangesMap.put("Annualized Copies", buildPair(usageDto, UdmUsageDto::getAnnualizedCopies));
        fieldToValueChangesMap.put("Ineligible Reason", Objects.nonNull(usageDto.getIneligibleReason())
            ? buildPair(usageDto, usage -> usage.getIneligibleReason().getReason())
            : EMPTY_PAIR);
    }

    /**
     * Updates field value for specified field name.
     *
     * @param fieldValue filed value
     * @param newValue   new value
     */
    public void updateFieldValue(String fieldValue, String newValue) {
        fieldToValueChangesMap.put(fieldValue, Pair.of(fieldToValueChangesMap.get(fieldValue).getLeft(), newValue));
    }

    /**
     * Puts field value as a key, old and new field values as a value into map.
     *
     * @param fieldValue field value as a key in map
     * @param oldValue   old value as a left pair value of value in map
     * @param newValue   new value as a right pair value of value in map
     */
    public void putFieldWithValues(String fieldValue, String oldValue, String newValue) {
        fieldToValueChangesMap.put(fieldValue, Pair.of(oldValue, newValue));
    }

    /**
     * @return a {@link Set} view of the mappings contained in this map.
     */
    public Set<Map.Entry<String, Pair<String, String>>> entrySet() {
        return fieldToValueChangesMap.entrySet();
    }

    private Pair<String, String> buildPair(UdmUsageDto udmUsageDto, Function<UdmUsageDto, Object> function) {
        String stringRepresentation = Objects.toString(function.apply(udmUsageDto), StringUtils.EMPTY);
        return Pair.of(stringRepresentation, stringRepresentation);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        UdmAuditFieldToValuesMap that = (UdmAuditFieldToValuesMap) obj;
        return new EqualsBuilder()
            .append(fieldToValueChangesMap, that.fieldToValueChangesMap)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(fieldToValueChangesMap)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("fieldToPairOfValuesMap", fieldToValueChangesMap)
            .toString();
    }
}
