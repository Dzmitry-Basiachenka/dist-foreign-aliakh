package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The validator to check whether value doesn't exist in the file.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/22/17
 *
 * @author Aliaksei Pchelnikau
 */
public class DuplicateInFileValidator implements DistCsvProcessor.IValidator<String> {

    private static final String ERROR_MESSAGE = "Duplicate with";
    /**
     * Key is item, value is the list with numbers of lines where it was met.
     */
    private final Map<String, List<Integer>> existingLines = new HashMap<>();
    private int line = 1;
    private String errorMessage = StringUtils.EMPTY;

    @Override
    public boolean isValid(String item) {
        ++line;
        errorMessage = StringUtils.EMPTY;
        boolean valid = StringUtils.isEmpty(item);
        if (!valid) {
            List<Integer> lines = existingLines.get(item);
            valid = CollectionUtils.isEmpty(lines);
            if (valid) {
                lines = new ArrayList<>();
                existingLines.put(item, lines);
            } else {
                errorMessage = buildErrorMessage(lines);
            }
            lines.add(line);
        }
        return valid;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

    private String buildErrorMessage(List<Integer> duplicatedLines) {
        return ERROR_MESSAGE + duplicatedLines.stream()
            .map(Object::toString)
            .collect(Collectors.joining(", ", " Line ", ""));
    }
}
