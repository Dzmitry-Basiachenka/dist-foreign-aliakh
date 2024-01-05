package com.copyright.rup.dist.foreign.repository.impl.converter.json.common;

import com.copyright.rup.dist.common.util.CommonDateUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents common logic for deserialization of JSONB fields.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/23/2023
 *
 * @param <T> type of fields
 * @author Mikita Maistrenka
 */
public abstract class CommonJsonFieldsDeserializer<T> extends StdDeserializer<T> {

    private static final long serialVersionUID = -7376819371694590645L;

    /**
     * Constructor.
     *
     * @param fieldsClass instance of {@link Class}
     */
    protected CommonJsonFieldsDeserializer(Class<T> fieldsClass) {
        super(fieldsClass);
    }

    /**
     * Returns {@link String} from JsonNode.
     *
     * @param node node
     * @return {@link String} if the node is not null, otherwise {@code null}
     */
    protected static String getStringValue(JsonNode node) {
        return Objects.nonNull(node) ? node.textValue() : null;
    }

    /**
     * Returns {@link Integer} from JsonNode.
     *
     * @param node node
     * @return {@link Integer} if the node is not null, otherwise {@code null}
     */
    protected static Integer getIntegerValue(JsonNode node) {
        return Objects.nonNull(node) ? node.asInt() : null;
    }

    /**
     * Returns {@link Long} from JsonNode.
     *
     * @param node node
     * @return {@link Long} if the node is not null, otherwise {@code null}
     */
    protected static Long getLongValue(JsonNode node) {
        return Objects.nonNull(node) ? node.asLong() : null;
    }

    /**
     * Returns {@link BigDecimal} from JsonNode.
     *
     * @param node node
     * @return {@link BigDecimal} if the node is not null and is a number, otherwise {@code null}
     */
    protected static BigDecimal getBigDecimalValue(JsonNode node) {
        return Objects.nonNull(node) && NumberUtils.isNumber(node.asText()) ? new BigDecimal(node.asText()) : null;
    }

    /**
     * Returns boolean from JsonNode.
     *
     * @param node node
     * @return {@code true} if the node is not null and is a true, otherwise {@code false}
     */
    protected static boolean getBooleanValue(JsonNode node) {
        return Objects.nonNull(node) && Boolean.parseBoolean(node.asText());
    }

    /**
     * Gets {@link LocalDate} from JsonNode.
     *
     * @param node node
     * @return {@link LocalDate} from parsing if the node is not null and is not number,
     * otherwise {@link LocalDate} from long
     */
    protected static LocalDate getLocalDateValue(JsonNode node) {
        return Objects.nonNull(node) && !node.isNumber()
            ? CommonDateUtils.parseLocalDate(node.textValue())
            : CommonDateUtils.getLocalDateFromLong(node.longValue());
    }
}
