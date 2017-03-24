package com.copyright.rup.dist.foreign.service.impl.csvprocessor.validator;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

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
public class DuplicateInFileValidator implements IValidator<String> {

    private static final String ERROR_MESSAGE = "Duplicate with";
    /**
     * Key is item, value is the list with numbers of lines where it was met.
     */
    private Map<String, List<Integer>> existedLines = Maps.newHashMap();
    private int line = 1;
    private String errorMessage = StringUtils.EMPTY;

    @Override
    public boolean isValid(String item) {
        ++line;
        List<Integer> lines = existedLines.get(item);
        boolean valid = StringUtils.isEmpty(item) || CollectionUtils.isEmpty(lines);
        if (valid) {
            lines = Lists.newArrayList();
            existedLines.put(item, lines);
            errorMessage = StringUtils.EMPTY;
        } else {
            errorMessage = buildErrorMessage(lines);
        }
        lines.add(line);
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
