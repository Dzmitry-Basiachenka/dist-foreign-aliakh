package com.copyright.rup.dist.foreign.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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

    private final Map<String, Pair<Object, Object>> fieldToValueChangesMap = new HashMap<>();

    /**
     * Constructor.
     *
     * @param usage instance of {@link UdmUsageDto}
     */
    public UdmAuditFieldToValuesMap(UdmUsageDto usage) {
        fieldToValueChangesMap.put("Detail Status", Pair.of(usage.getStatus(), usage.getStatus()));
        fieldToValueChangesMap.put("Wr Wrk Inst", Pair.of(usage.getWrWrkInst(), usage.getWrWrkInst()));
        fieldToValueChangesMap.put("Reported Title", Pair.of(usage.getReportedTitle(), usage.getReportedTitle()));
        fieldToValueChangesMap.put("Reported Standard Number",
            Pair.of(usage.getReportedStandardNumber(), usage.getReportedStandardNumber()));
        fieldToValueChangesMap.put("Reported Pub Type",
            Pair.of(usage.getReportedPubType(), usage.getReportedPubType()));
        fieldToValueChangesMap.put("Action Reason", Objects.nonNull(usage.getActionReason())
            ? Pair.of(usage.getActionReason().getReason(), usage.getActionReason().getReason())
            : Pair.of(null, null));
        fieldToValueChangesMap.put("Comment", Pair.of(usage.getComment(), usage.getComment()));
        fieldToValueChangesMap.put("Research URL", Pair.of(usage.getResearchUrl(), usage.getResearchUrl()));
        fieldToValueChangesMap.put("Company ID", Pair.of(usage.getCompanyId(), usage.getCompanyId()));
        fieldToValueChangesMap.put("Company Name", Pair.of(usage.getCompanyName(), usage.getCompanyName()));
        fieldToValueChangesMap.put("Detail Licensee Class", Objects.nonNull(usage.getDetailLicenseeClass())
            ? Pair.of(usage.getDetailLicenseeClass().getId() + " - " + usage.getDetailLicenseeClass().getDescription(),
            usage.getDetailLicenseeClass().getId() + " - " + usage.getDetailLicenseeClass().getDescription())
            : Pair.of(null, null));
        fieldToValueChangesMap.put("Annual Multiplier",
            Pair.of(usage.getAnnualMultiplier(), usage.getAnnualMultiplier()));
        fieldToValueChangesMap.put("Statistical Multiplier",
            Pair.of(usage.getStatisticalMultiplier(), usage.getStatisticalMultiplier()));
        fieldToValueChangesMap.put("Quantity", Pair.of(usage.getQuantity(), usage.getQuantity()));
        fieldToValueChangesMap.put("Annualized Copies",
            Pair.of(usage.getAnnualizedCopies(), usage.getAnnualizedCopies()));
        fieldToValueChangesMap.put("Ineligible Reason", Objects.nonNull(usage.getIneligibleReason())
            ? Pair.of(usage.getIneligibleReason().getReason(), usage.getIneligibleReason().getReason())
            : Pair.of(null, null));
    }

    /**
     * Updates field value for specified field name.
     *
     * @param fieldValue filed value
     * @param newValue   new value
     */
    public void updateFieldValue(String fieldValue, Object newValue) {
        fieldToValueChangesMap.put(fieldValue, Pair.of(fieldToValueChangesMap.get(fieldValue).getLeft(), newValue));
    }

    /**
     * @return a {@link Set} view of the mappings contained in this map.
     */
    public Set<Map.Entry<String, Pair<Object, Object>>> entrySet() {
        return fieldToValueChangesMap.entrySet();
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
