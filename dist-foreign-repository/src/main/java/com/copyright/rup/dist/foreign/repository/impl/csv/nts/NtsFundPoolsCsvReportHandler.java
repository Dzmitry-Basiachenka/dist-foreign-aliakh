package com.copyright.rup.dist.foreign.repository.impl.csv.nts;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.common.util.UsageBatchUtils;

import org.apache.commons.lang3.StringUtils;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * Writes NTC Fund Pools into a {@link java.io.OutputStream} connected to the {@link java.io.InputStream}.
 * <p/>
 * Copyright (C) 2023 copyright.com
 * <p/>
 * Date: 02/15/2023
 *
 * @author Mikita Maistrenka
 */
public class NtsFundPoolsCsvReportHandler extends BaseCsvReportHandler<UsageBatch> {

    private static final List<String> HEADERS =
        List.of("Fund Pool Name", "RRO Account Number", "RRO Name", "Payment Date", "Fiscal Year", "STM Amount",
            "Non-STM Amount", "STM Minimum Amount", "Non-STM Minimum Amount", "Market(s)", "Market Period From",
            "Market Period To", "Created By", "Created Date");

    /**
     * Constructor.
     *
     * @param outputStream {@link OutputStream} instance
     */
    public NtsFundPoolsCsvReportHandler(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    protected List<String> getBeanProperties(UsageBatch bean) {
        List<String> beanProperties = new ArrayList<>(HEADERS.size());
        beanProperties.add(getBeanPropertyAsString(bean.getName()));
        beanProperties.add(getBeanPropertyAsString(bean.getRro().getAccountNumber()));
        beanProperties.add(getBeanPropertyAsString(bean.getRro().getName()));
        beanProperties.add(getBeanLocalDate(bean.getPaymentDate()));
        beanProperties.add(UsageBatchUtils.getFiscalYear(bean.getFiscalYear()));
        beanProperties.add(getBeanBigDecimal(bean.getNtsFields().getStmAmount()));
        beanProperties.add(getBeanBigDecimal(bean.getNtsFields().getNonStmAmount()));
        beanProperties.add(getBeanBigDecimal(bean.getNtsFields().getStmMinimumAmount()));
        beanProperties.add(getBeanBigDecimal(bean.getNtsFields().getNonStmMinimumAmount()));
        beanProperties.add(getBeanPropertyAsString(String.join(", ", bean.getNtsFields().getMarkets())));
        beanProperties.add(getBeanPropertyAsString(bean.getNtsFields().getFundPoolPeriodFrom()));
        beanProperties.add(getBeanPropertyAsString(bean.getNtsFields().getFundPoolPeriodTo()));
        beanProperties.add(getBeanPropertyAsString(bean.getCreateUser()));
        beanProperties.add(getBeanPropertyAsString(toLongFormat(bean.getCreateDate())));
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }

    private String toLongFormat(Date date) {
        return Objects.nonNull(date)
            ? new SimpleDateFormat(RupDateUtils.US_DATETIME_FORMAT_PATTERN_LONG, Locale.getDefault()).format(date)
            : StringUtils.EMPTY;
    }
}
