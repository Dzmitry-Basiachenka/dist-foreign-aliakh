package com.copyright.rup.dist.foreign.service.impl.csv;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.service.api.csv.ICsvConverter;
import com.copyright.rup.dist.common.service.impl.csv.CommonCsvConverter;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor;
import com.copyright.rup.dist.common.service.impl.csv.validator.LengthValidator;
import com.copyright.rup.dist.common.service.impl.csv.validator.PositiveNumberValidator;
import com.copyright.rup.dist.common.service.impl.csv.validator.RequiredValidator;
import com.copyright.rup.dist.foreign.domain.UdmIneligibleReason;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.DateFormatValidator;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.DuplicateInFileValidator;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.NumberValidator;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Processor for  {@link UdmUsage}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 04/29/21
 *
 * @author Anton Azarenka
 */
public class UdmCsvProcessor extends DistCsvProcessor<UdmUsage> {

    private final UdmIneligibleReason ineligibleReasonNoReportedUse;

    /**
     * Constructor.
     *
     * @param ineligibleReasonNoReportedUse instance of {@link UdmIneligibleReason} for the case 'No Reported Use'
     */
    public UdmCsvProcessor(UdmIneligibleReason ineligibleReasonNoReportedUse) {
        this.ineligibleReasonNoReportedUse = ineligibleReasonNoReportedUse;
    }

    @Override
    public List<String> getHeadersForValidation() {
        return Stream.of(UdmCsvProcessor.Header.values()).map(UdmCsvProcessor.Header::getColumnName).collect(
            Collectors.toList());
    }

    @Override
    public ICsvConverter<UdmUsage> getConverter() {
        return new UdmUsageConverter();
    }

    @Override
    public void initPlainValidators() {
        RequiredValidator requiredValidator = new RequiredValidator();
        LengthValidator lengthValidator1000 = new LengthValidator(1000);
        LengthValidator lengthValidator100 = new LengthValidator(100);
        LengthValidator lengthValidator50 = new LengthValidator(50);
        LengthValidator lengthValidator20 = new LengthValidator(20);
        DateFormatValidator dateFormatValidator = new DateFormatValidator();
        PositiveNumberValidator positiveNumberValidator = new PositiveNumberValidator();
        addPlainValidators(UdmCsvProcessor.Header.USAGE_ORIGINAL_DETAIL_ID, requiredValidator,
            new DuplicateInFileValidator(), lengthValidator50);
        addPlainValidators(UdmCsvProcessor.Header.USAGE_DATE, requiredValidator, dateFormatValidator);
        addPlainValidators(UdmCsvProcessor.Header.WR_WRK_INST, positiveNumberValidator, new LengthValidator(15));
        addPlainValidators(UdmCsvProcessor.Header.REPORTED_STANDARD_NUMBER, lengthValidator100);
        addPlainValidators(UdmCsvProcessor.Header.REPORTED_TITLE, lengthValidator1000);
        addPlainValidators(UdmCsvProcessor.Header.ARTICLE_TITLE, new LengthValidator(500));
        addPlainValidators(UdmCsvProcessor.Header.REPORTED_PUB_TYPE, lengthValidator100);
        addPlainValidators(UdmCsvProcessor.Header.LANGUAGE, lengthValidator100);
        addPlainValidators(UdmCsvProcessor.Header.PUB_FORMAT, lengthValidator20);
        addPlainValidators(UdmCsvProcessor.Header.REPORTED_TYPE_OF_USE, lengthValidator100);
        addPlainValidators(UdmCsvProcessor.Header.QUANTITY, new NumberValidator(), requiredValidator,
            new LengthValidator(9));
        addPlainValidators(UdmCsvProcessor.Header.SURVEY_RESPONDED, new LengthValidator(200));
        addPlainValidators(UdmCsvProcessor.Header.COMPANY_ID, requiredValidator, positiveNumberValidator,
            new LengthValidator(10));
        addPlainValidators(UdmCsvProcessor.Header.SURVEY_COUNTRY, requiredValidator, lengthValidator100);
        addPlainValidators(UdmCsvProcessor.Header.IP_ADDRESS, lengthValidator20);
        addPlainValidators(UdmCsvProcessor.Header.SURVEY_START_DATE, requiredValidator, dateFormatValidator);
        addPlainValidators(UdmCsvProcessor.Header.SURVEY_END_DATE, requiredValidator, dateFormatValidator);
    }

    /**
     * CSV file headers.
     */
    private enum Header implements ICsvColumn {

        USAGE_ORIGINAL_DETAIL_ID("usage_detail_id"),
        USAGE_DATA_UID("rs_survey_usage_data_uid"),
        NAME("name"),
        USAGE_DATE("event_dtm"),
        WR_WRK_INST("wrk_inst"),
        REPORTED_STANDARD_NUMBER("std_no"),
        REPORTED_TITLE("main_title"),
        ARTICLE_TITLE("article_title"),
        REPORTED_PUB_TYPE("pub_type"),
        LANGUAGE("language"),
        PUB_FORMAT("pub_format"),
        REPORTED_TYPE_OF_USE("tou"),
        TYPE_OF_USE_DESC("tou_desc"),
        QUANTITY("quantity"),
        SURVEY_RESPONDED("user_id"),
        COMPANY_ID("telesales_org_id"),
        SURVEY_COUNTRY("country_name"),
        IP_ADDRESS("ip_address"),
        LOCATION_NAME("location_name"),
        SURVEY_START_DATE("survey_start_date"),
        SURVEY_END_DATE("survey_end_date");

        private final String columnName;

        Header(String columnName) {
            this.columnName = columnName;
        }

        @Override
        public String getColumnName() {
            return columnName;
        }
    }

    /**
     * Converts row to ACL usage detail.
     * <p/>
     * Copyright (C) 2021 copyright.com
     * <p/>
     * Date: 04/26/2021
     *
     * @author Anton Azarenka
     */
    private class UdmUsageConverter extends CommonCsvConverter<UdmUsage> {

        @Override
        public UdmUsage convert(String... row) {
            List<String> headers = getActualHeaders();
            UdmUsage result = new UdmUsage();
            result.setId(RupPersistUtils.generateUuid());
            result.setOriginalDetailId(getString(row, Header.USAGE_ORIGINAL_DETAIL_ID, headers));
            result.setUsageDate(getDate(row, Header.USAGE_DATE, headers));
            result.setWrWrkInst(getLong(row, Header.WR_WRK_INST, headers));
            result.setReportedStandardNumber(getString(row, Header.REPORTED_STANDARD_NUMBER, headers));
            result.setReportedTitle(getString(row, Header.REPORTED_TITLE, headers));
            result.setArticle(getString(row, Header.ARTICLE_TITLE, headers));
            result.setReportedPubType(getReportedPubTypeValue(getString(row, Header.REPORTED_PUB_TYPE, headers)));
            result.setLanguage(getString(row, Header.LANGUAGE, headers));
            result.setPubFormat(getString(row, Header.PUB_FORMAT, headers));
            result.setReportedTypeOfUse(getString(row, Header.REPORTED_TYPE_OF_USE, headers));
            result.setQuantity(getInteger(row, Header.QUANTITY, headers));
            result.setSurveyRespondent(getString(row, Header.SURVEY_RESPONDED, headers));
            result.setCompanyId(getLong(row, Header.COMPANY_ID, headers));
            result.setSurveyCountry(StringUtils.trim(getString(row, Header.SURVEY_COUNTRY, headers)));
            result.setStatus(isTitleNoneAndQuantityZero(result) ? UsageStatusEnum.INELIGIBLE : UsageStatusEnum.NEW);
            result.setIneligibleReason(isTitleNoneAndQuantityZero(result) ? ineligibleReasonNoReportedUse : null);
            result.setIpAddress(getString(row, Header.IP_ADDRESS, headers));
            result.setSurveyStartDate(getDate(row, Header.SURVEY_START_DATE, headers));
            result.setSurveyEndDate(getDate(row, Header.SURVEY_END_DATE, headers));
            return result;
        }

        private String getReportedPubTypeValue(String value) {
            return Objects.isNull(value) ? "Not Shared" : value;
        }

        private boolean isTitleNoneAndQuantityZero(UdmUsage udmUsage) {
            return "none".equalsIgnoreCase(StringUtils.trim(udmUsage.getReportedTitle()))
                && NumberUtils.INTEGER_ZERO.equals(udmUsage.getQuantity());
        }
    }
}
