package com.copyright.rup.dist.foreign.service.impl.acl.calculation.fundpool.validator;

import com.copyright.rup.dist.common.integration.camel.ValidationException;
import com.copyright.rup.dist.common.integration.util.JsonUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableSet;
import org.apache.camel.Body;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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
public class LdmtDetailsJsonValidator {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Set<String> TYPE_OF_USES = ImmutableSet.of("DIGITAL", "PHOTOCOPY");

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
        for (int i = 0; i < jsonNodes.size(); i++) {
            JsonNode jsonNode = jsonNodes.get(i);
            List<String> errors = new ArrayList<>();
            checkIntegerValue(errors, jsonNode.get("ICODE"), "Licensee Class Id");
            checkStringValue(errors, jsonNode.get("LICENCETYPE"), "License Type");
            String typeOfUse = checkStringValue(errors, jsonNode.get("TYPEOFUSE"), "Type Of Use");
            if (!StringUtils.isBlank(typeOfUse) && !TYPE_OF_USES.contains(typeOfUse)) {
                errors.add(String.format("Type Of Use should be either 'DIGITAL' or 'PHOTOCOPY': %s", typeOfUse));
            }
            checkBigDecimalValue(errors, jsonNode.get("GROSSAMOUNT"), "Gross Amount");
            checkBigDecimalValue(errors, jsonNode.get("NETAMOUNT"), "Net Amount");
            if (!errors.isEmpty()) {
                indexToErrors.put(i, errors);
            }
        }
        if (!indexToErrors.isEmpty()) {
            throw new ValidationException(indexToErrors
                .entrySet()
                .stream()
                .map(entry -> String.format("LDMT detail #%d is not valid: %s", entry.getKey(), entry.getValue()))
                .collect(Collectors.joining("\n")));
        }
    }

    private String checkStringValue(List<String> errors, JsonNode jsonNode, String fieldName) {
        String s = JsonUtils.getStringValue(jsonNode);
        if (StringUtils.isBlank(s)) {
            errors.add(String.format("%s should not be null or blank", fieldName));
        }
        return s;
    }

    private void checkIntegerValue(List<String> errors, JsonNode jsonNode, String fieldName) {
        String s = JsonUtils.getStringValue(jsonNode);
        if (StringUtils.isBlank(s)) {
            errors.add(String.format("%s should not be null or blank", fieldName));
        } else {
            if (!StringUtils.isNumeric(s)) {
                errors.add(String.format("%s should be a valid integer number: %s", fieldName, s));
            }
        }
    }

    private void checkBigDecimalValue(List<String> errors, JsonNode jsonNode, String fieldName) {
        String s = JsonUtils.getStringValue(jsonNode);
        if (StringUtils.isBlank(s)) {
            errors.add(String.format("%s should not be null or blank", fieldName));
        } else {
            BigDecimal value = JsonUtils.getBigDecimalValue(jsonNode);
            if (Objects.isNull(value)) {
                errors.add(String.format("%s should be a valid decimal number: %s", fieldName, s));
            }
        }
    }
}
