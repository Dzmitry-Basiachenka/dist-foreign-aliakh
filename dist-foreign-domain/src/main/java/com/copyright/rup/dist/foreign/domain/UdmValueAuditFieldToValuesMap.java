package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;
import java.util.Objects;
import java.util.function.Function;

/**
 * Domain object for holding map of field names and its old and new values of UDM values.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 12/06/2021
 *
 * @author Aliaksandr Liakh
 */
public class UdmValueAuditFieldToValuesMap extends CommonAuditFieldToValuesMap {

    /**
     * Constructor.
     */
    public UdmValueAuditFieldToValuesMap() {
    }

    /**
     * Constructor.
     *
     * @param valueDto instance of {@link UdmValueDto}
     */
    public UdmValueAuditFieldToValuesMap(UdmValueDto valueDto) {
        getFieldToValueChangesMap().put("Price", buildPair(valueDto, UdmValueDto::getPrice));
        getFieldToValueChangesMap().put("Currency", buildPair(valueDto, UdmValueDto::getCurrency));
        getFieldToValueChangesMap().put("Currency Exchange Rate", buildPair(valueDto,
            UdmValueDto::getCurrencyExchangeRate));
        getFieldToValueChangesMap().put("Currency Exchange Rate Date", buildPair(valueDto,
            value -> formatLocalDate(value.getCurrencyExchangeRateDate())));
        getFieldToValueChangesMap().put("Price in USD", buildPair(valueDto, UdmValueDto::getPriceInUsd));
        getFieldToValueChangesMap().put("Price Type", buildPair(valueDto, UdmValueDto::getPriceType));
        getFieldToValueChangesMap().put("Price Access Type", buildPair(valueDto, UdmValueDto::getPriceAccessType));
        getFieldToValueChangesMap().put("Price Year", buildPair(valueDto, UdmValueDto::getPriceYear));
        getFieldToValueChangesMap().put("Price Source", buildPair(valueDto, UdmValueDto::getPriceSource));
        getFieldToValueChangesMap().put("Price Comment", buildPair(valueDto, UdmValueDto::getPriceComment));
        getFieldToValueChangesMap().put("Price Flag", buildPair(valueDto,
            value -> fromBooleanToYNString(value.isPriceFlag())));
        getFieldToValueChangesMap().put("Value Status", Objects.nonNull(valueDto.getStatus())
            ? buildPair(valueDto, value -> value.getStatus().name())
            : EMPTY_PAIR);
        getFieldToValueChangesMap().put("Pub Type", Objects.nonNull(valueDto.getPublicationType())
            ? buildPair(valueDto, value -> value.getPublicationType().getNameAndDescription())
            : EMPTY_PAIR);
        getFieldToValueChangesMap().put("Content", buildPair(valueDto, UdmValueDto::getContent));
        getFieldToValueChangesMap().put("Content Source", buildPair(valueDto, UdmValueDto::getContentSource));
        getFieldToValueChangesMap().put("Content Comment", buildPair(valueDto, UdmValueDto::getContentComment));
        getFieldToValueChangesMap().put("Content Flag", buildPair(valueDto,
            value -> fromBooleanToYNString(value.isContentFlag())));
        getFieldToValueChangesMap().put("Content Unit Price", buildPair(valueDto, UdmValueDto::getContentUnitPrice));
        getFieldToValueChangesMap().put("Comment", buildPair(valueDto, UdmValueDto::getComment));
    }

    private Pair<String, String> buildPair(UdmValueDto udmValueDto, Function<UdmValueDto, Object> function) {
        String stringRepresentation = Objects.toString(function.apply(udmValueDto), StringUtils.EMPTY);
        return Pair.of(stringRepresentation, stringRepresentation);
    }

    private String formatLocalDate(LocalDate date) {
        return CommonDateUtils.format(date, RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT);
    }

    private String fromBooleanToYNString(Boolean flag) {
        if (Objects.isNull(flag)) {
            return null;
        } else {
            return flag ? "Y" : "N";
        }
    }
}
