package com.copyright.rup.dist.foreign.ui.audit.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Domain object for holding map of field names and its old and new values.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 12/06/2021
 *
 * @param <T> type of domain objects
 * @author Aliaksandr Liakh
 */
public class CommonAuditFieldToValuesMap<T> {

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
     * Gets list of audit action reasons.
     *
     * @return list of audit action reasons
     */
    public List<String> getActionReasons() {
        List<String> result = new ArrayList<>();
        fieldToValueChangesMap.forEach((key, value) -> {
            if (!Objects.equals(value.getLeft(), value.getRight())) {
                String oldValue = StringUtils.isBlank(value.getLeft())
                    ? NOT_SPECIFIED : String.format("'%s'", value.getLeft());
                String newValue = StringUtils.isBlank(value.getRight())
                    ? NOT_SPECIFIED : String.format("'%s'", value.getRight());
                result.add(String.format(USAGE_EDIT_REASON, key, oldValue, newValue));
            }
        });
        return result;
    }

    /**
     * Gets maps of DTOs to list of audit action reasons.
     *
     * @param commonAuditFieldToValuesMap instance of {@link CommonAuditFieldToValuesMap}
     * @param <T>                         type of domain objects
     * @return list of audit action reasons
     */
    public static <T> Map<T, List<String>> getDtoToAuditReasonsMap(
        Map<T, ? extends CommonAuditFieldToValuesMap<T>> commonAuditFieldToValuesMap) {
        return commonAuditFieldToValuesMap
            .entrySet()
            .stream()
            .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().getActionReasons()));
    }

    protected Map<String, Pair<String, String>> getFieldToValueChangesMap() {
        return fieldToValueChangesMap;
    }
}
