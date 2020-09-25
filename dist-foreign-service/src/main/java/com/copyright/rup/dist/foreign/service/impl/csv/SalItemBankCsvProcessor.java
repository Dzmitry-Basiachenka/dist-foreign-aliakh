package com.copyright.rup.dist.foreign.service.impl.csv;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.service.api.csv.ICsvConverter;
import com.copyright.rup.dist.common.service.impl.csv.CommonCsvConverter;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor;
import com.copyright.rup.dist.common.service.impl.csv.validator.LengthValidator;
import com.copyright.rup.dist.common.service.impl.csv.validator.PositiveNumberValidator;
import com.copyright.rup.dist.common.service.impl.csv.validator.RequiredValidator;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.SalDetailTypeEnum;
import com.copyright.rup.dist.foreign.domain.SalUsage;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.DateFormatValidator;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.DuplicateInFileValidator;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.SalGradeValidator;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Processor for SAL Item Bank {@link Usage}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 07/30/2020
 *
 * @author Ihar Suvorau
 */
public class SalItemBankCsvProcessor extends DistCsvProcessor<Usage> {

    /**
     * Constructor.
     */
    SalItemBankCsvProcessor() {
        super();
    }

    @Override
    public List<String> getHeadersForValidation() {
        return Stream.of(Header.values()).map(Header::getColumnName).collect(Collectors.toList());
    }

    @Override
    public ICsvConverter<Usage> getConverter() {
        return new SalUsageConverter();
    }

    @Override
    public void initPlainValidators() {
        RequiredValidator requiredValidator = new RequiredValidator();
        LengthValidator lengthValidator1000 = new LengthValidator(1000);
        PositiveNumberValidator positiveNumberValidator = new PositiveNumberValidator();
        addPlainValidators(Header.ASSESSMENT_NAME, requiredValidator, lengthValidator1000);
        addPlainValidators(Header.COVERAGE_YEAR, requiredValidator, new LengthValidator(100));
        addPlainValidators(Header.GRADE, new SalGradeValidator(), new LengthValidator(32));
        addPlainValidators(Header.WR_WRK_INST, requiredValidator, positiveNumberValidator, new LengthValidator(9));
        addPlainValidators(Header.REPORTED_WORK_PORTION_ID, requiredValidator, new DuplicateInFileValidator(),
            lengthValidator1000);
        addPlainValidators(Header.REPORTED_STANDARD_NUMBER, lengthValidator1000);
        addPlainValidators(Header.REPORTED_TITLE, requiredValidator, lengthValidator1000);
        addPlainValidators(Header.REPORTED_MEDIA_TYPE, requiredValidator, new LengthValidator(16));
        addPlainValidators(Header.REPORTED_ARTICLE, lengthValidator1000);
        addPlainValidators(Header.REPORTED_AUTHOR, lengthValidator1000);
        addPlainValidators(Header.REPORTED_PUBLISHER, lengthValidator1000);
        addPlainValidators(Header.REPORTED_PUBLICATION_DATE, new DateFormatValidator());
        addPlainValidators(Header.REPORTED_PAGE_RANGE, lengthValidator1000);
        addPlainValidators(Header.REPORTED_VOL_NUMBER_SERIES, lengthValidator1000);
        addPlainValidators(Header.COMMENT, new LengthValidator(100));
    }

    /**
     * CSV file headers.
     */
    private enum Header implements ICsvColumn {
        ASSESSMENT_NAME("Assessment Name"),
        COVERAGE_YEAR("Coverage Year"),
        GRADE("Grade"),
        WR_WRK_INST("Wr Wrk Inst"),
        REPORTED_WORK_PORTION_ID("Reported Work Portion ID"),
        REPORTED_STANDARD_NUMBER("Reported Standard Number"),
        REPORTED_TITLE("Reported Title"),
        REPORTED_MEDIA_TYPE("Reported Media Type"),
        REPORTED_ARTICLE("Reported Article or Chapter Title"),
        REPORTED_AUTHOR("Reported Author"),
        REPORTED_PUBLISHER("Reported Publisher"),
        REPORTED_PUBLICATION_DATE("Reported Publication Date"),
        REPORTED_PAGE_RANGE("Reported Page Range"),
        REPORTED_VOL_NUMBER_SERIES("Reported Vol/Number/Series"),
        COMMENT("Comment");

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
     * Converts row to SAL usage detail.
     * <p/>
     * Copyright (C) 2020 copyright.com
     * <p/>
     * Date: 07/30/2020
     *
     * @author Ihar Suvorau
     */
    private class SalUsageConverter extends CommonCsvConverter<Usage> {

        private static final String IMAGE_MEDIA_TYPE = "IMAGE";

        @Override
        public Usage convert(String... row) {
            List<String> headers = getActualHeaders();
            Usage result = new Usage();
            result.setId(RupPersistUtils.generateUuid());
            result.setWrWrkInst(getLong(row, Header.WR_WRK_INST, headers));
            result.setWorkTitle(getString(row, Header.REPORTED_TITLE, headers));
            result.setStatus(UsageStatusEnum.NEW);
            result.setProductFamily(FdaConstants.SAL_PRODUCT_FAMILY);
            result.setComment(getString(row, Header.COMMENT, headers));
            SalUsage salUsage = new SalUsage();
            salUsage.setAssessmentName(getString(row, Header.ASSESSMENT_NAME, headers));
            salUsage.setCoverageYear(getString(row, Header.COVERAGE_YEAR, headers));
            salUsage.setGrade(getString(row, Header.GRADE, headers));
            salUsage.setGradeGroup("ITEM_BANK");
            salUsage.setReportedWorkPortionId(getString(row, Header.REPORTED_WORK_PORTION_ID, headers));
            salUsage.setReportedStandardNumber(getString(row, Header.REPORTED_STANDARD_NUMBER, headers));
            salUsage.setReportedMediaType(getString(row, Header.REPORTED_MEDIA_TYPE, headers));
            salUsage.setMediaTypeWeight(IMAGE_MEDIA_TYPE.equalsIgnoreCase(salUsage.getReportedMediaType())
                ? new BigDecimal("0.3") : BigDecimal.ONE);
            salUsage.setReportedArticle(getString(row, Header.REPORTED_ARTICLE, headers));
            salUsage.setReportedAuthor(getString(row, Header.REPORTED_AUTHOR, headers));
            salUsage.setReportedPublisher(getString(row, Header.REPORTED_PUBLISHER, headers));
            salUsage.setReportedPublicationDate(getDate(row, Header.REPORTED_PUBLICATION_DATE, headers));
            salUsage.setReportedVolNumberSeries(getString(row, Header.REPORTED_VOL_NUMBER_SERIES, headers));
            salUsage.setReportedPageRange(getString(row, Header.REPORTED_PAGE_RANGE, headers));
            salUsage.setDetailType(SalDetailTypeEnum.IB);
            result.setSalUsage(salUsage);
            return result;
        }
    }
}
