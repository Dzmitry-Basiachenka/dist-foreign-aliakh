package com.copyright.rup.dist.foreign.service.impl.csv;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.service.api.csv.ICsvConverter;
import com.copyright.rup.dist.common.service.impl.csv.CommonCsvConverter;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor;
import com.copyright.rup.dist.common.service.impl.csv.validator.LengthValidator;
import com.copyright.rup.dist.common.service.impl.csv.validator.PositiveNumberValidator;
import com.copyright.rup.dist.common.service.impl.csv.validator.RequiredValidator;
import com.copyright.rup.dist.foreign.domain.AclciLicenseTypeEnum;
import com.copyright.rup.dist.foreign.domain.AclciUsage;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.AclciGradeValidator;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.AclciLicenseTypeValidator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Processor for ACLCI {@link Usage}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 12/12/2022
 *
 * @author Aliaksandr Liakh
 */
public class AclciUsageCsvProcessor extends DistCsvProcessor<Usage> {

    @Override
    public List<String> getHeadersForValidation() {
        return Stream.of(Header.values()).map(Header::getColumnName).collect(Collectors.toList());
    }

    @Override
    public ICsvConverter<Usage> getConverter() {
        return new AclciUsageConverter();
    }

    @Override
    public void initPlainValidators() {
        RequiredValidator requiredValidator = new RequiredValidator();
        LengthValidator lengthValidator100 = new LengthValidator(100);
        LengthValidator lengthValidator1000 = new LengthValidator(1000);
        PositiveNumberValidator positiveNumberValidator = new PositiveNumberValidator();
        addPlainValidators(Header.COVERAGE_PERIOD, requiredValidator, new LengthValidator(9));
        addPlainValidators(Header.LICENSE_TYPE, requiredValidator, new AclciLicenseTypeValidator());
        addPlainValidators(Header.REPORTED_GRADE, requiredValidator, new AclciGradeValidator());
        addPlainValidators(Header.WR_WRK_INST, requiredValidator, positiveNumberValidator, new LengthValidator(9));
        addPlainValidators(Header.REPORTED_WORK_TITLE, new LengthValidator(2000));
        addPlainValidators(Header.REPORTED_STANDARD_NUMBER, lengthValidator1000);
        addPlainValidators(Header.REPORTED_ARTICLE, lengthValidator1000);
        addPlainValidators(Header.REPORTED_AUTHOR, lengthValidator1000);
        addPlainValidators(Header.REPORTED_PUBLISHER, lengthValidator1000);
        addPlainValidators(Header.REPORTED_PUBLICATION_DATE, lengthValidator100);
        addPlainValidators(Header.REPORTED_MEDIA_TYPE, requiredValidator, new LengthValidator(16));
        addPlainValidators(Header.COMMENT, lengthValidator100);
    }

    /**
     * CSV file headers.
     */
    private enum Header implements ICsvColumn {
        COVERAGE_PERIOD("Coverage Period"),
        LICENSE_TYPE("License Type"),
        REPORTED_GRADE("Reported Grade"),
        WR_WRK_INST("Wr Wrk Inst"),
        REPORTED_WORK_TITLE("Reported Work Title"),
        REPORTED_STANDARD_NUMBER("Reported Standard Number or Image ID Number"),
        REPORTED_ARTICLE("Reported Article or Chapter Title"),
        REPORTED_AUTHOR("Reported Author"),
        REPORTED_PUBLISHER("Reported Publisher"),
        REPORTED_PUBLICATION_DATE("Reported Publication Date"),
        REPORTED_MEDIA_TYPE("Reported Media Type"),
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
     * Converts row to ACLCI {@link Usage}.
     * <p/>
     * Copyright (C) 2022 copyright.com
     * <p>
     * Date: 12/12/2022
     *
     * @author Aliaksandr Liakh
     */
    private class AclciUsageConverter extends CommonCsvConverter<Usage> {

        private static final String IMAGE_MEDIA_TYPE = "IMAGE";

        @Override
        public Usage convert(String... row) {
            List<String> headers = getActualHeaders();
            Usage result = new Usage();
            result.setId(RupPersistUtils.generateUuid());
            result.setStatus(UsageStatusEnum.NEW);
            result.setProductFamily(FdaConstants.ACLCI_PRODUCT_FAMILY);
            result.setWrWrkInst(getLong(row, Header.WR_WRK_INST, headers));
            result.setWorkTitle(getString(row, Header.REPORTED_WORK_TITLE, headers));
            result.setComment(getString(row, Header.COMMENT, headers));
            AclciUsage aclciUsage = new AclciUsage();
            aclciUsage.setCoveragePeriod(getString(row, Header.COVERAGE_PERIOD, headers));
            String licenseType = getString(row, Header.LICENSE_TYPE, headers).toUpperCase(Locale.ROOT);
            aclciUsage.setLicenseType(AclciLicenseTypeEnum.valueOf(licenseType));
            aclciUsage.setReportedGrade(getString(row, Header.REPORTED_GRADE, headers));
            aclciUsage.setGradeGroup("GRADE6_8"); //TODO: will be implemented in a separate story
            aclciUsage.setReportedStandardNumber(getString(row, Header.REPORTED_STANDARD_NUMBER, headers));
            aclciUsage.setReportedArticle(getString(row, Header.REPORTED_ARTICLE, headers));
            aclciUsage.setReportedAuthor(getString(row, Header.REPORTED_AUTHOR, headers));
            aclciUsage.setReportedPublisher(getString(row, Header.REPORTED_PUBLISHER, headers));
            aclciUsage.setReportedPublicationDate(getString(row, Header.REPORTED_PUBLICATION_DATE, headers));
            aclciUsage.setReportedMediaType(getString(row, Header.REPORTED_MEDIA_TYPE, headers));
            aclciUsage.setMediaTypeWeight(IMAGE_MEDIA_TYPE.equalsIgnoreCase(aclciUsage.getReportedMediaType())
                ? new BigDecimal("0.3") : BigDecimal.ONE);
            result.setAclciUsage(aclciUsage);
            return result;
        }
    }
}
