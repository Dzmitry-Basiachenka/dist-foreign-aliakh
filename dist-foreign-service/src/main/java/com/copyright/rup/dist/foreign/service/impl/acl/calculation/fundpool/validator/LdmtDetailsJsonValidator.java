package com.copyright.rup.dist.foreign.service.impl.acl.calculation.fundpool.validator;

import com.copyright.rup.dist.common.integration.camel.ValidationException;
import com.copyright.rup.dist.common.integration.util.JsonUtils;
import com.copyright.rup.dist.common.service.impl.csv.validator.AmountValidator;
import com.copyright.rup.dist.common.service.impl.csv.validator.RequiredValidator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableSet;

import org.apache.camel.Body;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import io.micrometer.core.annotation.Timed;

/**
 * Validator to perform validation for LDMT details JSON.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 05/14/2022
 *
 * @author Aliaksandr Liakh
 */
@Component("df.service.ldmtDetailsJsonValidator")
@Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
public class LdmtDetailsJsonValidator {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Set<String> TYPE_OF_USES = ImmutableSet.of("DIGITAL", "PHOTOCOPY");
    private final AmountValidator amountValidator = new AmountValidator(true);
    private final RequiredValidator requiredValidator = new RequiredValidator();

    /**
     * Validates LDMT details JSON.
     *
     * @param body message body
     * @throws IOException if JSON cannot be parsed
     */
    public void validate(@Body String body) throws IOException {
        List<JsonNode> jsonNodes = new ArrayList<>();
        JsonNode tree = JsonUtils.readJsonTree(MAPPER, body);
        tree.iterator().forEachRemaining(jsonNodes::add);
        Map<Integer, List<String>> indexToErrors = new LinkedHashMap<>();
        validateFields(jsonNodes, indexToErrors);
        if (!indexToErrors.isEmpty()) {
            throw new ValidationException(indexToErrors
                .entrySet()
                .stream()
                .map(entry -> String.format("LDMT detail #%d is not valid: %s", entry.getKey(), entry.getValue()))
                .collect(Collectors.joining("; ")));
        }
    }

    private void validateFields(List<JsonNode> jsonNodes, Map<Integer, List<String>> indexToErrors) {
        IntStream.range(0, jsonNodes.size()).forEach(i -> {
            JsonNode jsonNode = jsonNodes.get(i);
            List<String> errors = new ArrayList<>();
            checkIntegerValue(errors, jsonNode.get("ICODE"), "Licensee Class Id");
            checkRequiredValidation(errors, jsonNode.get("LICENCETYPE"), "License Type");
            checkTypeOfUseValidation(errors, jsonNode.get("TYPEOFUSE"));
            checkBigDecimalValue(errors, jsonNode.get("GROSSAMOUNT"), "Gross Amount");
            checkBigDecimalValue(errors, jsonNode.get("NETAMOUNT"), "Net Amount");
            if (!errors.isEmpty()) {
                indexToErrors.put(i, errors);
            }
        });
    }

    private boolean checkRequiredValidation(List<String> errors, JsonNode jsonNode, String fieldName) {
        boolean isValid = true;
        if (!requiredValidator.isValid(JsonUtils.getStringValue(jsonNode))) {
            errors.add(String.format("%s should not be null or blank", fieldName));
            isValid = false;
        }
        return isValid;
    }

    private void checkTypeOfUseValidation(List<String> errors, JsonNode jsonNode) {
        if (checkRequiredValidation(errors, jsonNode, "Type Of Use")) {
            String fieldValue = JsonUtils.getStringValue(jsonNode);
            if (!TYPE_OF_USES.contains(fieldValue)) {
                errors.add(String.format("Type Of Use should be either 'DIGITAL' or 'PHOTOCOPY': %s", fieldValue));
            }
        }
    }

    private void checkIntegerValue(List<String> errors, JsonNode jsonNode, String fieldName) {
        if (checkRequiredValidation(errors, jsonNode, fieldName)) {
            String fieldValue = JsonUtils.getStringValue(jsonNode);
            if (!StringUtils.isNumeric(fieldValue)) {
                errors.add(String.format("%s should be a valid integer number: %s", fieldName, fieldValue));
            }
        }
    }

    private void checkBigDecimalValue(List<String> errors, JsonNode jsonNode, String fieldName) {
        if (checkRequiredValidation(errors, jsonNode, fieldName)) {
            String fieldValue = JsonUtils.getStringValue(jsonNode);
            if (!amountValidator.isValid(fieldValue)) {
                errors.add(String.format("%s should be a positive number and not exceed 10 digits: %s", fieldName,
                    fieldValue));
            }
        }
    }
}
