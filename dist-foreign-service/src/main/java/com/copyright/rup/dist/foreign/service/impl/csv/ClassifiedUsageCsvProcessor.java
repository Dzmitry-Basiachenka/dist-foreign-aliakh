package com.copyright.rup.dist.foreign.service.impl.csv;

import com.copyright.rup.dist.common.service.api.csv.ICsvConverter;
import com.copyright.rup.dist.common.service.impl.csv.CommonCsvConverter;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor;
import com.copyright.rup.dist.common.service.impl.csv.validator.LengthValidator;
import com.copyright.rup.dist.common.service.impl.csv.validator.PositiveNumberValidator;
import com.copyright.rup.dist.common.service.impl.csv.validator.RequiredValidator;
import com.copyright.rup.dist.foreign.domain.AaclClassifiedUsage;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.DuplicateInFileValidator;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Processor for {@link AaclClassifiedUsage}.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 01/24/2020
 *
 * @author Uladzislau Shalamitski
 */
public class ClassifiedUsageCsvProcessor extends DistCsvProcessor<AaclClassifiedUsage> {

    /**
     * Constructor.
     */
    ClassifiedUsageCsvProcessor() {
        super();
    }

    @Override
    public List<String> getHeadersForValidation() {
        return Stream.of(Header.values()).map(Header::getColumnName).collect(Collectors.toList());
    }

    @Override
    public ICsvConverter<AaclClassifiedUsage> getConverter() {
        return new ClassifiedUsageConverter();
    }

    @Override
    public void initPlainValidators() {
        var requiredValidator = new RequiredValidator();
        var positiveNumberValidator = new PositiveNumberValidator();
        addPlainValidators(Header.DETAIL_ID, requiredValidator, new LengthValidator(36),
            new DuplicateInFileValidator());
        addPlainValidators(Header.WR_WRK_INST, requiredValidator, positiveNumberValidator, new LengthValidator(9));
        addPlainValidators(Header.ENROLLMENT_PROFILE, requiredValidator, new LengthValidator(20));
        addPlainValidators(Header.DISCIPLINE, requiredValidator, new LengthValidator(100));
        addPlainValidators(Header.PUBLICATION_TYPE, requiredValidator, new LengthValidator(255));
        addPlainValidators(Header.COMMENT, new LengthValidator(100));
    }

    /**
     * CSV file headers.
     */
    private enum Header implements ICsvColumn {
        DETAIL_ID("Detail ID"),
        STATUS("Detail Status"),
        PRODUCT_FAMILY("Product Family"),
        BATCH_NAME("Usage Batch Name"),
        PERIOD_END_DATE("Period End Date"),
        RH_ACCOUNT_NUMBER("RH Account #"),
        RH_NAME("RH Name"),
        WR_WRK_INST("Wr Wrk Inst"),
        SYSTEM_TITLE("System Title"),
        STANDARD_NUMBER("Standard Number"),
        STANDARD_NUMBER_TYPE("Standard Number Type"),
        DETAIL_LICENSEE_CLASS_ID("Det LC ID"),
        ENROLLMENT_PROFILE("Det LC Enrollment"),
        DISCIPLINE("Det LC Discipline"),
        PUBLICATION_TYPE("Pub Type"),
        INSTITUTION("Institution"),
        USAGE_PERIOD("Usage Period"),
        USAGE_SOURCE("Usage Source"),
        NUMBER_OF_COPIES("Number of Copies"),
        NUMBER_OF_PAGES("Number of Pages"),
        RIGHTS_LIMITATION("Right Limitation"),
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
     * Converts row to classified AACL usage detail.
     * <p/>
     * Copyright (C) 2020 copyright.com
     * <p/>
     * Date: 01/24/2020
     *
     * @author Uladzislau Shalamitski
     */
    private class ClassifiedUsageConverter extends CommonCsvConverter<AaclClassifiedUsage> {

        @Override
        public AaclClassifiedUsage convert(String... row) {
            List<String> headers = getActualHeaders();
            AaclClassifiedUsage classifiedUsage = new AaclClassifiedUsage();
            classifiedUsage.setDetailId(getString(row, Header.DETAIL_ID, headers));
            classifiedUsage.setWrWrkInst(getLong(row, Header.WR_WRK_INST, headers));
            classifiedUsage.setEnrollmentProfile(getString(row, Header.ENROLLMENT_PROFILE, headers));
            classifiedUsage.setDiscipline(getString(row, Header.DISCIPLINE, headers));
            classifiedUsage.setPublicationType(getString(row, Header.PUBLICATION_TYPE, headers));
            classifiedUsage.setComment(getString(row, Header.COMMENT, headers));
            return classifiedUsage;
        }
    }
}
