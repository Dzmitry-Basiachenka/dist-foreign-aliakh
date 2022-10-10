package com.copyright.rup.dist.foreign.ui.audit.impl;

import com.copyright.rup.dist.foreign.domain.UdmValueDto;
import com.copyright.rup.dist.foreign.ui.common.utils.BigDecimalUtils;
import com.copyright.rup.dist.foreign.ui.common.utils.BooleanUtils;
import com.copyright.rup.dist.foreign.ui.common.utils.IDateFormatter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

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
public class UdmValueAuditFieldToValuesMap extends CommonAuditFieldToValuesMap<UdmValueDto> implements IDateFormatter {

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
        getFieldToValueChangesMap().put("Price", buildPair(valueDto,
            value -> BigDecimalUtils.formatCurrencyForDialog(value.getPrice())));
        getFieldToValueChangesMap().put("Currency", buildPair(valueDto, UdmValueDto::getCurrency));
        getFieldToValueChangesMap().put("Currency Exchange Rate", buildPair(valueDto,
            UdmValueDto::getCurrencyExchangeRate));
        getFieldToValueChangesMap().put("Currency Exchange Rate Date", buildPair(valueDto,
            value -> toShortFormat(value.getCurrencyExchangeRateDate())));
        getFieldToValueChangesMap().put("Price in USD", buildPair(valueDto,
            value -> BigDecimalUtils.formatCurrencyForDialog(value.getPriceInUsd())));
        getFieldToValueChangesMap().put("Price Type", buildPair(valueDto, UdmValueDto::getPriceType));
        getFieldToValueChangesMap().put("Price Access Type", buildPair(valueDto, UdmValueDto::getPriceAccessType));
        getFieldToValueChangesMap().put("Price Year", buildPair(valueDto, UdmValueDto::getPriceYear));
        getFieldToValueChangesMap().put("Price Source", buildPair(valueDto, UdmValueDto::getPriceSource));
        getFieldToValueChangesMap().put("Price Comment", buildPair(valueDto, UdmValueDto::getPriceComment));
        getFieldToValueChangesMap().put("Price Flag", buildPair(valueDto,
            value -> BooleanUtils.toYNString(value.isPriceFlag())));
        getFieldToValueChangesMap().put("Value Status", Objects.nonNull(valueDto.getStatus())
            ? buildPair(valueDto, value -> value.getStatus().name())
            : EMPTY_PAIR);
        getFieldToValueChangesMap().put("Pub Type", Objects.nonNull(valueDto.getPublicationType())
            ? buildPair(valueDto, value -> value.getPublicationType().getNameAndDescription())
            : EMPTY_PAIR);
        getFieldToValueChangesMap().put("Content", buildPair(valueDto,
            value -> BigDecimalUtils.formatCurrencyForDialog(value.getContent())));
        getFieldToValueChangesMap().put("Content Source", buildPair(valueDto, UdmValueDto::getContentSource));
        getFieldToValueChangesMap().put("Content Comment", buildPair(valueDto, UdmValueDto::getContentComment));
        getFieldToValueChangesMap().put("Content Flag", buildPair(valueDto,
            value -> BooleanUtils.toYNString(value.isContentFlag())));
        getFieldToValueChangesMap().put("Content Unit Price", buildPair(valueDto,
            value -> BigDecimalUtils.formatCurrencyForDialog(value.getContentUnitPrice())));
        getFieldToValueChangesMap().put("CUP Flag", buildPair(valueDto,
            value -> BooleanUtils.toYNString(value.isContentUnitPriceFlag())));
        getFieldToValueChangesMap().put("Comment", buildPair(valueDto, UdmValueDto::getComment));
    }

    private Pair<String, String> buildPair(UdmValueDto udmValueDto, Function<UdmValueDto, Object> function) {
        String stringRepresentation = Objects.toString(function.apply(udmValueDto), StringUtils.EMPTY);
        return Pair.of(stringRepresentation, stringRepresentation);
    }
}
