package com.copyright.rup.dist.foreign.domain;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Objects;

/**
 * Verifies {@link UdmValueAuditFieldToValuesMap}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 12/06/2021
 *
 * @author Aliaksandr Liakh
 */
public class UdmValueAuditFieldToValuesMapTest {

    private static final BigDecimal PRICE_OLD = new BigDecimal("3000.00");
    private static final BigDecimal PRICE_NEW = new BigDecimal("4000.00");
    private static final String CURRENCY_OLD = "EUR";
    private static final String CURRENCY_NEW = "GBP";
    private static final BigDecimal CURRENCY_EXCHANGE_RATE_OLD = new BigDecimal("1.1294040715");
    private static final BigDecimal CURRENCY_EXCHANGE_RATE_NEW = new BigDecimal("1.3285703846");
    private static final LocalDate CURRENCY_EXCHANGE_RATE_DATE_OLD = LocalDate.of(2021, 12, 1);
    private static final LocalDate CURRENCY_EXCHANGE_RATE_DATE_NEW = LocalDate.of(2021, 12, 31);
    private static final BigDecimal PRICE_IN_USD_OLD = new BigDecimal("3388.2122145");
    private static final BigDecimal PRICE_IN_USD_NEW = new BigDecimal("5314.2815384");
    private static final String PRICE_TYPE_OLD = "Individual";
    private static final String PRICE_TYPE_NEW = "Institution";
    private static final String PRICE_ACCESS_TYPE_OLD = "Print";
    private static final String PRICE_ACCESS_TYPE_NEW = "Digital";
    private static final Integer PRICE_YEAR_OLD = 2020;
    private static final Integer PRICE_YEAR_NEW = 2021;
    private static final String PRICE_SOURCE_OLD = "price source 1";
    private static final String PRICE_SOURCE_NEW = "price source 2";
    private static final String PRICE_COMMENT_OLD = "price comment 1";
    private static final String PRICE_COMMENT_NEW = "price comment 2";
    private static final Boolean PRICE_FLAG_OLD = false;
    private static final Boolean PRICE_FLAG_NEW = true;
    private static final UdmValueStatusEnum STATUS_OLD = UdmValueStatusEnum.RESEARCH_COMPLETE;
    private static final UdmValueStatusEnum STATUS_NEW = UdmValueStatusEnum.PUBLISHED;
    private static final PublicationType PUBLICATION_TYPE_OLD;
    private static final PublicationType PUBLICATION_TYPE_NEW;
    private static final BigDecimal CONTENT_OLD = new BigDecimal("2");
    private static final BigDecimal CONTENT_NEW = new BigDecimal("3");
    private static final String CONTENT_SOURCE_OLD = "content source 1";
    private static final String CONTENT_SOURCE_NEW = "content source 2";
    private static final String CONTENT_COMMENT_OLD = "content comment 1";
    private static final String CONTENT_COMMENT_NEW = "content comment 2";
    private static final Boolean CONTENT_FLAG_OLD = true;
    private static final Boolean CONTENT_FLAG_NEW = false;
    private static final BigDecimal CONTENT_UNIT_PRICE_OLD = new BigDecimal("1694.10610725");
    private static final BigDecimal CONTENT_UNIT_PRICE_NEW = new BigDecimal("1771.4271794667");
    private static final String COMMENT_OLD = "comment 1";
    private static final String COMMENT_NEW = "comment 2";

    static {
        PUBLICATION_TYPE_OLD = new PublicationType();
        PUBLICATION_TYPE_OLD.setName("BK");
        PUBLICATION_TYPE_OLD.setDescription("Book");
        PUBLICATION_TYPE_NEW = new PublicationType();
        PUBLICATION_TYPE_NEW.setName("BK2");
        PUBLICATION_TYPE_NEW.setDescription("Book series");
    }

    @Test
    public void testPrice() {
        UdmValueDto valueDto = new UdmValueDto();
        UdmValueAuditFieldToValuesMap fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        assertEquals(Collections.emptyList(), fieldToValuesMap.getEditAuditReasons());
        fieldToValuesMap.updateFieldValue("Price", PRICE_OLD.toString());
        assertEquals(Collections.singletonList("The field 'Price' was edited. " +
                "Old Value is not specified. New Value is '3000.00'"),
            fieldToValuesMap.getEditAuditReasons());
        valueDto.setPrice(PRICE_OLD);
        fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        fieldToValuesMap.updateFieldValue("Price", PRICE_NEW.toString());
        assertEquals(Collections.singletonList("The field 'Price' was edited. " +
                "Old Value is '3000.00'. New Value is '4000.00'"),
            fieldToValuesMap.getEditAuditReasons());
        valueDto.setPrice(PRICE_NEW);
        fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        fieldToValuesMap.updateFieldValue("Price", null);
        assertEquals(Collections.singletonList("The field 'Price' was edited. " +
                "Old Value is '4000.00'. New Value is not specified"),
            fieldToValuesMap.getEditAuditReasons());
    }

    @Test
    public void testCurrency() {
        UdmValueDto valueDto = new UdmValueDto();
        UdmValueAuditFieldToValuesMap fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        assertEquals(Collections.emptyList(), fieldToValuesMap.getEditAuditReasons());
        fieldToValuesMap.updateFieldValue("Currency", CURRENCY_OLD);
        assertEquals(Collections.singletonList("The field 'Currency' was edited. " +
                "Old Value is not specified. New Value is 'EUR'"),
            fieldToValuesMap.getEditAuditReasons());
        valueDto.setCurrency(CURRENCY_OLD);
        fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        fieldToValuesMap.updateFieldValue("Currency", CURRENCY_NEW);
        assertEquals(Collections.singletonList("The field 'Currency' was edited. " +
                "Old Value is 'EUR'. New Value is 'GBP'"),
            fieldToValuesMap.getEditAuditReasons());
        valueDto.setCurrency(CURRENCY_NEW);
        fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        fieldToValuesMap.updateFieldValue("Currency", null);
        assertEquals(Collections.singletonList("The field 'Currency' was edited. " +
                "Old Value is 'GBP'. New Value is not specified"),
            fieldToValuesMap.getEditAuditReasons());
    }

    @Test
    public void testCurrencyExchangeRate() {
        UdmValueDto valueDto = new UdmValueDto();
        UdmValueAuditFieldToValuesMap fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        assertEquals(Collections.emptyList(), fieldToValuesMap.getEditAuditReasons());
        fieldToValuesMap.updateFieldValue("Currency Exchange Rate", CURRENCY_EXCHANGE_RATE_OLD.toString());
        assertEquals(Collections.singletonList("The field 'Currency Exchange Rate' was edited. " +
                "Old Value is not specified. New Value is '1.1294040715'"),
            fieldToValuesMap.getEditAuditReasons());
        valueDto.setCurrencyExchangeRate(CURRENCY_EXCHANGE_RATE_OLD);
        fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        fieldToValuesMap.updateFieldValue("Currency Exchange Rate", CURRENCY_EXCHANGE_RATE_NEW.toString());
        assertEquals(Collections.singletonList("The field 'Currency Exchange Rate' was edited. " +
                "Old Value is '1.1294040715'. New Value is '1.3285703846'"),
            fieldToValuesMap.getEditAuditReasons());
        valueDto.setCurrencyExchangeRate(CURRENCY_EXCHANGE_RATE_NEW);
        fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        fieldToValuesMap.updateFieldValue("Currency Exchange Rate", null);
        assertEquals(Collections.singletonList("The field 'Currency Exchange Rate' was edited. " +
                "Old Value is '1.3285703846'. New Value is not specified"),
            fieldToValuesMap.getEditAuditReasons());
    }

    @Test
    public void testCurrencyExchangeRateDate() {
        UdmValueDto valueDto = new UdmValueDto();
        UdmValueAuditFieldToValuesMap fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        assertEquals(Collections.emptyList(), fieldToValuesMap.getEditAuditReasons());
        fieldToValuesMap.updateFieldValue("Currency Exchange Rate Date",
            formatLocalDate(CURRENCY_EXCHANGE_RATE_DATE_OLD));
        assertEquals(Collections.singletonList("The field 'Currency Exchange Rate Date' was edited. " +
                "Old Value is not specified. New Value is '12/01/2021'"),
            fieldToValuesMap.getEditAuditReasons());
        valueDto.setCurrencyExchangeRateDate(CURRENCY_EXCHANGE_RATE_DATE_OLD);
        fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        fieldToValuesMap.updateFieldValue("Currency Exchange Rate Date",
            formatLocalDate(CURRENCY_EXCHANGE_RATE_DATE_NEW));
        assertEquals(Collections.singletonList("The field 'Currency Exchange Rate Date' was edited. " +
                "Old Value is '12/01/2021'. New Value is '12/31/2021'"),
            fieldToValuesMap.getEditAuditReasons());
        valueDto.setCurrencyExchangeRateDate(CURRENCY_EXCHANGE_RATE_DATE_NEW);
        fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        fieldToValuesMap.updateFieldValue("Currency Exchange Rate Date", null);
        assertEquals(Collections.singletonList("The field 'Currency Exchange Rate Date' was edited. " +
                "Old Value is '12/31/2021'. New Value is not specified"),
            fieldToValuesMap.getEditAuditReasons());
    }

    @Test
    public void testPriceInUsd() {
        UdmValueDto valueDto = new UdmValueDto();
        UdmValueAuditFieldToValuesMap fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        assertEquals(Collections.emptyList(), fieldToValuesMap.getEditAuditReasons());
        fieldToValuesMap.updateFieldValue("Price in USD", PRICE_IN_USD_OLD.toString());
        assertEquals(Collections.singletonList("The field 'Price in USD' was edited. " +
                "Old Value is not specified. New Value is '3388.2122145'"),
            fieldToValuesMap.getEditAuditReasons());
        valueDto.setPriceInUsd(PRICE_IN_USD_OLD);
        fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        fieldToValuesMap.updateFieldValue("Price in USD", PRICE_IN_USD_NEW.toString());
        assertEquals(Collections.singletonList("The field 'Price in USD' was edited. " +
                "Old Value is '3388.2122145'. New Value is '5314.2815384'"),
            fieldToValuesMap.getEditAuditReasons());
        valueDto.setPriceInUsd(PRICE_IN_USD_NEW);
        fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        fieldToValuesMap.updateFieldValue("Price in USD", null);
        assertEquals(Collections.singletonList("The field 'Price in USD' was edited. " +
                "Old Value is '5314.2815384'. New Value is not specified"),
            fieldToValuesMap.getEditAuditReasons());
    }

    @Test
    public void testPriceType() {
        UdmValueDto valueDto = new UdmValueDto();
        UdmValueAuditFieldToValuesMap fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        assertEquals(Collections.emptyList(), fieldToValuesMap.getEditAuditReasons());
        fieldToValuesMap.updateFieldValue("Price Type", PRICE_TYPE_OLD);
        assertEquals(Collections.singletonList("The field 'Price Type' was edited. " +
                "Old Value is not specified. New Value is 'Individual'"),
            fieldToValuesMap.getEditAuditReasons());
        valueDto.setPriceType(PRICE_TYPE_OLD);
        fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        fieldToValuesMap.updateFieldValue("Price Type", PRICE_TYPE_NEW);
        assertEquals(Collections.singletonList("The field 'Price Type' was edited. " +
                "Old Value is 'Individual'. New Value is 'Institution'"),
            fieldToValuesMap.getEditAuditReasons());
        valueDto.setPriceType(PRICE_TYPE_NEW);
        fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        fieldToValuesMap.updateFieldValue("Price Type", null);
        assertEquals(Collections.singletonList("The field 'Price Type' was edited. " +
                "Old Value is 'Institution'. New Value is not specified"),
            fieldToValuesMap.getEditAuditReasons());
    }

    @Test
    public void testPriceAccessType() {
        UdmValueDto valueDto = new UdmValueDto();
        UdmValueAuditFieldToValuesMap fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        assertEquals(Collections.emptyList(), fieldToValuesMap.getEditAuditReasons());
        fieldToValuesMap.updateFieldValue("Price Access Type", PRICE_ACCESS_TYPE_OLD);
        assertEquals(Collections.singletonList("The field 'Price Access Type' was edited. " +
                "Old Value is not specified. New Value is 'Print'"),
            fieldToValuesMap.getEditAuditReasons());
        valueDto.setPriceAccessType(PRICE_ACCESS_TYPE_OLD);
        fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        fieldToValuesMap.updateFieldValue("Price Access Type", PRICE_ACCESS_TYPE_NEW);
        assertEquals(Collections.singletonList("The field 'Price Access Type' was edited. " +
                "Old Value is 'Print'. New Value is 'Digital'"),
            fieldToValuesMap.getEditAuditReasons());
        valueDto.setPriceAccessType(PRICE_ACCESS_TYPE_NEW);
        fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        fieldToValuesMap.updateFieldValue("Price Access Type", null);
        assertEquals(Collections.singletonList("The field 'Price Access Type' was edited. " +
                "Old Value is 'Digital'. New Value is not specified"),
            fieldToValuesMap.getEditAuditReasons());
    }

    @Test
    public void testPriceYear() {
        UdmValueDto valueDto = new UdmValueDto();
        UdmValueAuditFieldToValuesMap fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        assertEquals(Collections.emptyList(), fieldToValuesMap.getEditAuditReasons());
        fieldToValuesMap.updateFieldValue("Price Year", PRICE_YEAR_OLD.toString());
        assertEquals(Collections.singletonList("The field 'Price Year' was edited. " +
                "Old Value is not specified. New Value is '2020'"),
            fieldToValuesMap.getEditAuditReasons());
        valueDto.setPriceYear(PRICE_YEAR_OLD);
        fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        fieldToValuesMap.updateFieldValue("Price Year", PRICE_YEAR_NEW.toString());
        assertEquals(Collections.singletonList("The field 'Price Year' was edited. " +
                "Old Value is '2020'. New Value is '2021'"),
            fieldToValuesMap.getEditAuditReasons());
        valueDto.setPriceYear(PRICE_YEAR_NEW);
        fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        fieldToValuesMap.updateFieldValue("Price Year", null);
        assertEquals(Collections.singletonList("The field 'Price Year' was edited. " +
                "Old Value is '2021'. New Value is not specified"),
            fieldToValuesMap.getEditAuditReasons());
    }

    @Test
    public void testPriceSource() {
        UdmValueDto valueDto = new UdmValueDto();
        UdmValueAuditFieldToValuesMap fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        assertEquals(Collections.emptyList(), fieldToValuesMap.getEditAuditReasons());

        fieldToValuesMap.updateFieldValue("Price Source", PRICE_SOURCE_OLD);
        assertEquals(Collections.singletonList("The field 'Price Source' was edited. Old Value is not specified. " +
                "New Value is 'price source 1'"),
            fieldToValuesMap.getEditAuditReasons());

        valueDto.setPriceSource(PRICE_SOURCE_OLD);
        fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        fieldToValuesMap.updateFieldValue("Price Source", PRICE_SOURCE_NEW);
        assertEquals(Collections.singletonList("The field 'Price Source' was edited. Old Value is 'price source 1'. " +
                "New Value is 'price source 2'"),
            fieldToValuesMap.getEditAuditReasons());

        valueDto.setPriceSource(PRICE_SOURCE_NEW);
        fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        fieldToValuesMap.updateFieldValue("Price Source", null);
        assertEquals(Collections.singletonList("The field 'Price Source' was edited. " +
                "Old Value is 'price source 2'. New Value is not specified"),
            fieldToValuesMap.getEditAuditReasons());
    }

    @Test
    public void testPriceComment() {
        UdmValueDto valueDto = new UdmValueDto();
        UdmValueAuditFieldToValuesMap fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        assertEquals(Collections.emptyList(), fieldToValuesMap.getEditAuditReasons());
        fieldToValuesMap.updateFieldValue("Price Comment", PRICE_COMMENT_OLD);
        assertEquals(Collections.singletonList("The field 'Price Comment' was edited. " +
                "Old Value is not specified. New Value is 'price comment 1'"),
            fieldToValuesMap.getEditAuditReasons());
        valueDto.setPriceComment(PRICE_COMMENT_OLD);
        fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        fieldToValuesMap.updateFieldValue("Price Comment", PRICE_COMMENT_NEW);
        assertEquals(Collections.singletonList("The field 'Price Comment' was edited. " +
                "Old Value is 'price comment 1'. New Value is 'price comment 2'"),
            fieldToValuesMap.getEditAuditReasons());
        valueDto.setPriceComment(PRICE_COMMENT_NEW);
        fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        fieldToValuesMap.updateFieldValue("Price Comment", null);
        assertEquals(Collections.singletonList("The field 'Price Comment' was edited. " +
                "Old Value is 'price comment 2'. New Value is not specified"),
            fieldToValuesMap.getEditAuditReasons());
    }

    @Test
    public void testPriceFlag() {
        UdmValueDto valueDto = new UdmValueDto();
        UdmValueAuditFieldToValuesMap fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        assertEquals(Collections.emptyList(), fieldToValuesMap.getEditAuditReasons());
        fieldToValuesMap.updateFieldValue("Price Flag", fromBooleanToYNString(PRICE_FLAG_OLD));
        assertEquals(Collections.singletonList("The field 'Price Flag' was edited. " +
                "Old Value is not specified. New Value is 'N'"),
            fieldToValuesMap.getEditAuditReasons());
        valueDto.setPriceFlag(PRICE_FLAG_OLD);
        fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        fieldToValuesMap.updateFieldValue("Price Flag", fromBooleanToYNString(PRICE_FLAG_NEW));
        assertEquals(Collections.singletonList("The field 'Price Flag' was edited. " +
                "Old Value is 'N'. New Value is 'Y'"),
            fieldToValuesMap.getEditAuditReasons());
        valueDto.setPriceFlag(PRICE_FLAG_NEW);
        fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        fieldToValuesMap.updateFieldValue("Price Flag", null);
        assertEquals(Collections.singletonList("The field 'Price Flag' was edited. " +
                "Old Value is 'Y'. New Value is not specified"),
            fieldToValuesMap.getEditAuditReasons());
    }

    @Test
    public void testValueStatus() {
        UdmValueDto valueDto = new UdmValueDto();
        UdmValueAuditFieldToValuesMap fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        assertEquals(Collections.emptyList(), fieldToValuesMap.getEditAuditReasons());
        fieldToValuesMap.updateFieldValue("Value Status", STATUS_OLD.toString());
        assertEquals(Collections.singletonList("The field 'Value Status' was edited. " +
                "Old Value is not specified. New Value is 'RESEARCH_COMPLETE'"),
            fieldToValuesMap.getEditAuditReasons());
        valueDto.setStatus(STATUS_OLD);
        fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        fieldToValuesMap.updateFieldValue("Value Status", STATUS_NEW.toString());
        assertEquals(Collections.singletonList("The field 'Value Status' was edited. " +
                "Old Value is 'RESEARCH_COMPLETE'. New Value is 'PUBLISHED'"),
            fieldToValuesMap.getEditAuditReasons());
        valueDto.setStatus(STATUS_NEW);
        fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        fieldToValuesMap.updateFieldValue("Value Status", null);
        assertEquals(Collections.singletonList("The field 'Value Status' was edited. " +
                "Old Value is 'PUBLISHED'. New Value is not specified"),
            fieldToValuesMap.getEditAuditReasons());
    }

    @Test
    public void testPubType() {
        UdmValueDto valueDto = new UdmValueDto();
        UdmValueAuditFieldToValuesMap fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        assertEquals(Collections.emptyList(), fieldToValuesMap.getEditAuditReasons());
        fieldToValuesMap.updateFieldValue("Pub Type", PUBLICATION_TYPE_OLD.getNameAndDescription());
        assertEquals(Collections.singletonList("The field 'Pub Type' was edited. " +
                "Old Value is not specified. New Value is 'BK - Book'"),
            fieldToValuesMap.getEditAuditReasons());
        valueDto.setPublicationType(PUBLICATION_TYPE_OLD);
        fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        fieldToValuesMap.updateFieldValue("Pub Type", PUBLICATION_TYPE_NEW.getNameAndDescription());
        assertEquals(Collections.singletonList("The field 'Pub Type' was edited. " +
                "Old Value is 'BK - Book'. New Value is 'BK2 - Book series'"),
            fieldToValuesMap.getEditAuditReasons());
        valueDto.setPublicationType(PUBLICATION_TYPE_NEW);
        fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        fieldToValuesMap.updateFieldValue("Pub Type", null);
        assertEquals(Collections.singletonList("The field 'Pub Type' was edited. " +
                "Old Value is 'BK2 - Book series'. New Value is not specified"),
            fieldToValuesMap.getEditAuditReasons());
    }

    @Test
    public void testContent() {
        UdmValueDto valueDto = new UdmValueDto();
        UdmValueAuditFieldToValuesMap fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        assertEquals(Collections.emptyList(), fieldToValuesMap.getEditAuditReasons());
        fieldToValuesMap.updateFieldValue("Content", CONTENT_OLD.toString());
        assertEquals(Collections.singletonList("The field 'Content' was edited. " +
                "Old Value is not specified. New Value is '2'"),
            fieldToValuesMap.getEditAuditReasons());
        valueDto.setContent(CONTENT_OLD);
        fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        fieldToValuesMap.updateFieldValue("Content", CONTENT_NEW.toString());
        assertEquals(Collections.singletonList("The field 'Content' was edited. " +
                "Old Value is '2'. New Value is '3'"),
            fieldToValuesMap.getEditAuditReasons());
        valueDto.setContent(CONTENT_NEW);
        fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        fieldToValuesMap.updateFieldValue("Content", null);
        assertEquals(Collections.singletonList("The field 'Content' was edited. " +
                "Old Value is '3'. New Value is not specified"),
            fieldToValuesMap.getEditAuditReasons());
    }

    @Test
    public void testContentSource() {
        UdmValueDto valueDto = new UdmValueDto();
        UdmValueAuditFieldToValuesMap fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        assertEquals(Collections.emptyList(), fieldToValuesMap.getEditAuditReasons());
        fieldToValuesMap.updateFieldValue("Content Source", CONTENT_SOURCE_OLD);
        assertEquals(Collections.singletonList("The field 'Content Source' was edited. " +
                "Old Value is not specified. New Value is 'content source 1'"),
            fieldToValuesMap.getEditAuditReasons());
        valueDto.setContentSource(CONTENT_SOURCE_OLD);
        fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        fieldToValuesMap.updateFieldValue("Content Source", CONTENT_SOURCE_NEW);
        assertEquals(Collections.singletonList("The field 'Content Source' was edited. " +
                "Old Value is 'content source 1'. New Value is 'content source 2'"),
            fieldToValuesMap.getEditAuditReasons());
        valueDto.setContentSource(CONTENT_SOURCE_NEW);
        fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        fieldToValuesMap.updateFieldValue("Content Source", null);
        assertEquals(Collections.singletonList("The field 'Content Source' was edited. " +
                "Old Value is 'content source 2'. New Value is not specified"),
            fieldToValuesMap.getEditAuditReasons());
    }

    @Test
    public void testContentComment() {
        UdmValueDto valueDto = new UdmValueDto();
        UdmValueAuditFieldToValuesMap fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        assertEquals(Collections.emptyList(), fieldToValuesMap.getEditAuditReasons());
        fieldToValuesMap.updateFieldValue("Content Comment", CONTENT_COMMENT_OLD);
        assertEquals(Collections.singletonList("The field 'Content Comment' was edited. " +
                "Old Value is not specified. New Value is 'content comment 1'"),
            fieldToValuesMap.getEditAuditReasons());
        valueDto.setContentComment(CONTENT_COMMENT_OLD);
        fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        fieldToValuesMap.updateFieldValue("Content Comment", CONTENT_COMMENT_NEW);
        assertEquals(Collections.singletonList("The field 'Content Comment' was edited. " +
                "Old Value is 'content comment 1'. New Value is 'content comment 2'"),
            fieldToValuesMap.getEditAuditReasons());
        valueDto.setContentComment(CONTENT_COMMENT_NEW);
        fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        fieldToValuesMap.updateFieldValue("Content Comment", null);
        assertEquals(Collections.singletonList("The field 'Content Comment' was edited. " +
                "Old Value is 'content comment 2'. New Value is not specified"),
            fieldToValuesMap.getEditAuditReasons());
    }

    @Test
    public void testContentFlag() {
        UdmValueDto valueDto = new UdmValueDto();
        UdmValueAuditFieldToValuesMap fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        assertEquals(Collections.emptyList(), fieldToValuesMap.getEditAuditReasons());
        fieldToValuesMap.updateFieldValue("Content Flag", fromBooleanToYNString(CONTENT_FLAG_OLD));
        assertEquals(Collections.singletonList("The field 'Content Flag' was edited. " +
                "Old Value is not specified. New Value is 'Y'"),
            fieldToValuesMap.getEditAuditReasons());
        valueDto.setContentFlag(CONTENT_FLAG_OLD);
        fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        fieldToValuesMap.updateFieldValue("Content Flag", fromBooleanToYNString(CONTENT_FLAG_NEW));
        assertEquals(Collections.singletonList("The field 'Content Flag' was edited. " +
                "Old Value is 'Y'. New Value is 'N'"),
            fieldToValuesMap.getEditAuditReasons());
        valueDto.setContentFlag(CONTENT_FLAG_NEW);
        fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        fieldToValuesMap.updateFieldValue("Content Flag", null);
        assertEquals(Collections.singletonList("The field 'Content Flag' was edited. " +
                "Old Value is 'N'. New Value is not specified"),
            fieldToValuesMap.getEditAuditReasons());
    }

    @Test
    public void testContentUnitPrice() {
        UdmValueDto valueDto = new UdmValueDto();
        UdmValueAuditFieldToValuesMap fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        assertEquals(Collections.emptyList(), fieldToValuesMap.getEditAuditReasons());
        fieldToValuesMap.updateFieldValue("Content Unit Price", CONTENT_UNIT_PRICE_OLD.toString());
        assertEquals(Collections.singletonList("The field 'Content Unit Price' was edited. " +
                "Old Value is not specified. New Value is '1694.10610725'"),
            fieldToValuesMap.getEditAuditReasons());
        valueDto.setContentUnitPrice(CONTENT_UNIT_PRICE_OLD);
        fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        fieldToValuesMap.updateFieldValue("Content Unit Price", CONTENT_UNIT_PRICE_NEW.toString());
        assertEquals(Collections.singletonList("The field 'Content Unit Price' was edited. " +
                "Old Value is '1694.10610725'. New Value is '1771.4271794667'"),
            fieldToValuesMap.getEditAuditReasons());
        valueDto.setContentUnitPrice(CONTENT_UNIT_PRICE_NEW);
        fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        fieldToValuesMap.updateFieldValue("Content Unit Price", null);
        assertEquals(Collections.singletonList("The field 'Content Unit Price' was edited. " +
                "Old Value is '1771.4271794667'. New Value is not specified"),
            fieldToValuesMap.getEditAuditReasons());
    }

    @Test
    public void testComment() {
        UdmValueDto valueDto = new UdmValueDto();
        UdmValueAuditFieldToValuesMap fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        assertEquals(Collections.emptyList(), fieldToValuesMap.getEditAuditReasons());
        fieldToValuesMap.updateFieldValue("Comment", COMMENT_OLD);
        assertEquals(Collections.singletonList("The field 'Comment' was edited. " +
                "Old Value is not specified. New Value is 'comment 1'"),
            fieldToValuesMap.getEditAuditReasons());
        valueDto.setComment(COMMENT_OLD);
        fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        fieldToValuesMap.updateFieldValue("Comment", COMMENT_NEW);
        assertEquals(Collections.singletonList("The field 'Comment' was edited. " +
                "Old Value is 'comment 1'. New Value is 'comment 2'"),
            fieldToValuesMap.getEditAuditReasons());
        valueDto.setComment(COMMENT_NEW);
        fieldToValuesMap = new UdmValueAuditFieldToValuesMap(valueDto);
        fieldToValuesMap.updateFieldValue("Comment", null);
        assertEquals(Collections.singletonList("The field 'Comment' was edited. " +
                "Old Value is 'comment 2'. New Value is not specified"),
            fieldToValuesMap.getEditAuditReasons());
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
