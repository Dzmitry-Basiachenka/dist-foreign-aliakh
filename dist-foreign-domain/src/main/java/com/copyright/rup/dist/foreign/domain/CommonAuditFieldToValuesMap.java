package com.copyright.rup.dist.foreign.domain;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Domain object for holding map of field names and its old and new values.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 12/06/2021
 *
 * @author Aliaksandr Liakh
 */
class CommonAuditFieldToValuesMap {

    /**
     * Empty old and new values pair.
     */
    protected static final Pair<String, String> EMPTY_PAIR = Pair.of(null, null);

    private static final String USAGE_EDIT_REASON = "The field '%s' was edited. Old Value is %s. New Value is %s";
    private static final String NOT_SPECIFIED = "not specified";

    private final Map<String, Pair<String, String>> fieldToValueChangesMap = new LinkedHashMap<>();

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
     * Gets list of audit reasons.
     *
     * @return list of audit reasons
     */
    public List<String> getEditAuditReasons() {
        List<String> result = new ArrayList<>();
        fieldToValueChangesMap.entrySet().forEach(fieldToValueChangesMapEntry -> {
            Pair<String, String> valuePair = fieldToValueChangesMapEntry.getValue();
            if (!Objects.equals(valuePair.getLeft(), valuePair.getRight())) {
                String oldValue = StringUtils.isBlank(valuePair.getLeft())
                    ? NOT_SPECIFIED : String.format("'%s'", valuePair.getLeft());
                String newValue = StringUtils.isBlank(valuePair.getRight())
                    ? NOT_SPECIFIED : String.format("'%s'", valuePair.getRight());
                result.add(String.format(USAGE_EDIT_REASON, fieldToValueChangesMapEntry.getKey(), oldValue, newValue));
            }
        });
        return result;
    }

    protected Map<String, Pair<String, String>> getFieldToValueChangesMap() {
        return fieldToValueChangesMap;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        CommonAuditFieldToValuesMap that = (CommonAuditFieldToValuesMap) obj;
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
