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
import com.copyright.rup.dist.foreign.service.impl.csv.validator.SalGradeValidator;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Processor for SAL Usage Data.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 09/24/2020
 *
 * @author Stanislau Rudak
 */
public class SalUsageDataCsvProcessor extends DistCsvProcessor<Usage> {

    /**
     * Constructor.
     */
    SalUsageDataCsvProcessor() {
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
        addPlainValidators(Header.DATE_OF_SCORED_ASSESSMENT, requiredValidator, new DateFormatValidator());
        addPlainValidators(Header.REPORTED_WORK_PORTION_ID, requiredValidator, lengthValidator1000);
        addPlainValidators(Header.ASSESSMENT_TYPE, requiredValidator, lengthValidator1000);
        addPlainValidators(Header.QUESTION_IDENTIFIER, requiredValidator, lengthValidator1000);
        addPlainValidators(Header.GRADE, new SalGradeValidator(), new LengthValidator(32));
        addPlainValidators(Header.STATES, requiredValidator, lengthValidator1000);
        addPlainValidators(Header.NUMBER_OF_VIEWS, requiredValidator, new PositiveNumberValidator(),
            new LengthValidator(9));
        addPlainValidators(Header.COMMENT, new LengthValidator(100));
    }

    /**
     * CSV file headers.
     */
    private enum Header implements ICsvColumn {
        DATE_OF_SCORED_ASSESSMENT("Date of Scored Assessment"),
        REPORTED_WORK_PORTION_ID("Reported Work Portion ID"),
        ASSESSMENT_TYPE("Assessment Type"),
        QUESTION_IDENTIFIER("Question Identifier"),
        GRADE("Grade"),
        STATES("States"),
        NUMBER_OF_VIEWS("Number of Views"),
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
     * Date: 09/24/2020
     *
     * @author Stanislau Rudak
     */
    private class SalUsageConverter extends CommonCsvConverter<Usage> {

        @Override
        public Usage convert(String... row) {
            List<String> headers = getActualHeaders();
            Usage result = new Usage();
            result.setId(RupPersistUtils.generateUuid());
            result.setStatus(UsageStatusEnum.ELIGIBLE);
            result.setProductFamily(FdaConstants.SAL_PRODUCT_FAMILY);
            result.setComment(getString(row, Header.COMMENT, headers));
            SalUsage salUsage = new SalUsage();
            salUsage.setScoredAssessmentDate(getDate(row, Header.DATE_OF_SCORED_ASSESSMENT, headers));
            salUsage.setReportedWorkPortionId(getString(row, Header.REPORTED_WORK_PORTION_ID, headers));
            salUsage.setAssessmentType(getString(row, Header.ASSESSMENT_TYPE, headers));
            salUsage.setQuestionIdentifier(getString(row, Header.QUESTION_IDENTIFIER, headers));
            salUsage.setGrade(getString(row, Header.GRADE, headers));
            salUsage.setStates(getString(row, Header.STATES, headers));
            salUsage.setNumberOfViews(getInteger(row, Header.NUMBER_OF_VIEWS, headers));
            salUsage.setDetailType(SalDetailTypeEnum.UD);
            result.setSalUsage(salUsage);
            return result;
        }
    }
}
