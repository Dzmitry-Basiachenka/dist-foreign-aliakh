package com.copyright.rup.dist.foreign.service.impl.csvprocessor;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.impl.csvprocessor.exception.ValidationException;
import com.copyright.rup.dist.foreign.service.impl.csvprocessor.validator.DateFormatValidator;
import com.copyright.rup.dist.foreign.service.impl.csvprocessor.validator.DuplicateDetailIdValidator;
import com.copyright.rup.dist.foreign.service.impl.csvprocessor.validator.DuplicateInFileValidator;
import com.copyright.rup.dist.foreign.service.impl.csvprocessor.validator.LengthValidator;
import com.copyright.rup.dist.foreign.service.impl.csvprocessor.validator.MarketPeriodValidator;
import com.copyright.rup.dist.foreign.service.impl.csvprocessor.validator.PositiveNumberValidator;
import com.copyright.rup.dist.foreign.service.impl.csvprocessor.validator.ReportedValueValidator;
import com.copyright.rup.dist.foreign.service.impl.csvprocessor.validator.RequiredValidator;
import com.copyright.rup.dist.foreign.service.impl.csvprocessor.validator.RightsholderWrWrkInstValidator;
import com.copyright.rup.dist.foreign.service.impl.csvprocessor.validator.YearValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * CSV processor for uploading {@link Usage}s.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 02/21/17
 *
 * @author Aliaksei Pchelnikau
 * @author Mikalai Bezmen
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UsageCsvProcessor extends CommonCsvProcessor<Usage> {

    @Autowired
    private IUsageService usageService;

    @Override
    protected List<ICsvColumn> getCsvHeaders() {
        return Arrays.asList(Header.values());
    }

    @Override
    protected void initValidators() {
        RequiredValidator requiredValidator = new RequiredValidator();
        PositiveNumberValidator positiveNumberValidator = new PositiveNumberValidator();
        LengthValidator lengthValidator_1000 = new LengthValidator(1000);
        LengthValidator lengthValidator_2000 = new LengthValidator(2000);
        addPlainValidators(Header.DETAIL_ID, requiredValidator, positiveNumberValidator, new LengthValidator(10),
            new DuplicateInFileValidator());
        addPlainValidators(Header.TITLE, lengthValidator_2000);
        addPlainValidators(Header.ARTICLE, lengthValidator_1000);
        addPlainValidators(Header.STANDARD_NUMBER, lengthValidator_1000);
        addPlainValidators(Header.WR_WRK_INST, positiveNumberValidator, new LengthValidator(15));
        addPlainValidators(Header.RH_ACCT_NUMBER, positiveNumberValidator, new LengthValidator(22));
        addPlainValidators(Header.PUBLISHER, lengthValidator_1000);
        addPlainValidators(Header.PUB_DATE, new DateFormatValidator());
        addPlainValidators(Header.NUMBER_OF_COPIES, positiveNumberValidator, new LengthValidator(9));
        addPlainValidators(Header.REPORTED_VALUE, requiredValidator, new ReportedValueValidator());
        addPlainValidators(Header.MARKET, requiredValidator, new LengthValidator(200));
        addPlainValidators(Header.MARKET_PERIOD_FROM, requiredValidator, new YearValidator());
        addPlainValidators(Header.MARKET_PERIOD_TO, requiredValidator, new YearValidator());
        addPlainValidators(Header.AUTHOR, lengthValidator_2000);
    }

    @Override
    protected void validateBusinessRules() throws ValidationException {
        List<Long> detailIds = getResult().getResult().stream()
            .map(Usage::getDetailId)
            .collect(Collectors.toList());
        Set<Long> detailIdDuplicates = usageService.getDuplicateDetailIds(detailIds);
        addBusinessValidators(new DuplicateDetailIdValidator(detailIdDuplicates), new MarketPeriodValidator(),
            new RightsholderWrWrkInstValidator());
        super.validateBusinessRules();
    }

    @Override
    protected Usage build(List<String> params) {
        Usage result = new Usage();
        result.setId(RupPersistUtils.generateUuid());
        result.setDetailId(getLong(Header.DETAIL_ID, params));
        result.setWorkTitle(getString(Header.TITLE, params));
        result.setArticle(getString(Header.ARTICLE, params));
        result.setStandardNumber(getString(Header.STANDARD_NUMBER, params));
        result.setWrWrkInst(getLong(Header.WR_WRK_INST, params));
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(getLong(Header.RH_ACCT_NUMBER, params));
        result.setRightsholder(rightsholder);
        result.setPublisher(getString(Header.PUBLISHER, params));
        result.setPublicationDate(getDate(Header.PUB_DATE, params));
        result.setNumberOfCopies(getInteger(Header.NUMBER_OF_COPIES, params));
        result.setReportedValue(getBigDecimal(Header.REPORTED_VALUE, params));
        result.setMarket(getString(Header.MARKET, params));
        result.setMarketPeriodFrom(getInteger(Header.MARKET_PERIOD_FROM, params));
        result.setMarketPeriodTo(getInteger(Header.MARKET_PERIOD_TO, params));
        result.setAuthor(getString(Header.AUTHOR, params));
        result.setStatus(isEligible(result) ? UsageStatusEnum.ELIGIBLE
            : isWorkFound(result) ? UsageStatusEnum.WORK_FOUND : UsageStatusEnum.NEW);
        return result;
    }

    private boolean isEligible(Usage usage) {
        return Objects.nonNull(usage.getRightsholder().getAccountNumber()) && Objects.nonNull(usage.getWrWrkInst());
    }

    private boolean isWorkFound(Usage usage) {
        return Objects.isNull(usage.getRightsholder().getAccountNumber()) && Objects.nonNull(usage.getWrWrkInst());
    }

    private enum Header implements ICsvColumn {
        DETAIL_ID("Detail ID"),
        TITLE("Title"),
        ARTICLE("Article"),
        STANDARD_NUMBER("Standard Number"),
        WR_WRK_INST("Wr Wrk Inst"),
        RH_ACCT_NUMBER("RH Acct Number"),
        PUBLISHER("Publisher"),
        PUB_DATE("Pub Date"),
        NUMBER_OF_COPIES("Number of Copies"),
        REPORTED_VALUE("Reported Value"),
        MARKET("Market"),
        MARKET_PERIOD_FROM("Market Period From"),
        MARKET_PERIOD_TO("Market Period To"),
        AUTHOR("Author");

        private String columnName;

        Header(String columnName) {
            this.columnName = columnName;
        }

        @Override
        public String getColumnName() {
            return columnName;
        }
    }
}
