package com.copyright.rup.dist.foreign.service.impl.csvprocessor;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * Processing Result for uploading data from CSV file.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 02/22/17
 *
 * @param <T> the type of uploading item
 * @author Aliaksei Pchelnikau
 */
public class CsvProcessingResult<T> {

    private List<T> result = Lists.newArrayList();
    private Map<Integer, String> errors = Maps.newTreeMap();

    /**
     * Adds valid item to the valid list.
     *
     * @param item item to add
     */
    public void addRecord(T item) {
        result.add(item);
    }

    /**
     * Gets list of valid items.
     *
     * @return the list of valid items
     */
    public List<T> getResult() {
        return result;
    }

    /**
     * Adds failed row to result.
     *
     * @param line     line number from CSV
     * @param errorRow original row from CSV
     */
    public void logError(Integer line, String errorRow) {
        errors.put(line, errorRow);
    }

    /**
     * @return {@code true} if result has no error(s), {@code false} - otherwise.
     */
    public boolean isSuccessful() {
        return errors.isEmpty();
    }

    /**
     * Checks whether processing result has information about valid items and invalid data.
     *
     * @return {@code true} - if processing result is empty, {@code false} - otherwise
     */
    public boolean isEmpty() {
        return result.isEmpty() && errors.isEmpty();
    }
}
