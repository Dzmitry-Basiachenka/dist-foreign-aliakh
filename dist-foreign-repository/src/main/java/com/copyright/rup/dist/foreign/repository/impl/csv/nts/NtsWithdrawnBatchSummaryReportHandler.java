package com.copyright.rup.dist.foreign.repository.impl.csv.nts;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.report.NtsWithDrawnBatchSummaryReportDto;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Writes NTS Withdrawn Batch Summary Report into a {@link OutputStream} connected to the {@link java.io.InputStream}.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 10/30/19
 *
 * @author Anton Azarenka
 */
public class NtsWithdrawnBatchSummaryReportHandler extends BaseCsvReportHandler<NtsWithDrawnBatchSummaryReportDto> {

    private static final List<String> HEADERS = List.of("Usage Batch Name", "RRO Account Number", "RRO Name",
        "Payment Date", "Load Date", "Gross Fund Pool in USD", "# Details NTS WD", "Gross USD NTS WD",
        "# Details To Be Distributed", "Gross USD To Be Distributed");

    /**
     * Constructor.
     *
     * @param outputStream {@link OutputStream} instance
     */
    public NtsWithdrawnBatchSummaryReportHandler(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    protected List<String> getBeanProperties(NtsWithDrawnBatchSummaryReportDto bean) {
        List<String> beanProperties = new ArrayList<>();
        beanProperties.add(getBeanPropertyAsString(bean.getBatchName()));
        beanProperties.add(getBeanPropertyAsString(bean.getRroAccountNumber()));
        beanProperties.add(bean.getRroName());
        beanProperties.add(getBeanLocalDate(bean.getPaymentDate()));
        beanProperties.add(DateFormatUtils.format(bean.getCreateDate(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getBatchGrossAmount()));
        beanProperties.add(getBeanPropertyAsString(bean.getNtsDetailsCount()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getNtsDetailsGrossAmount()));
        beanProperties.add(getBeanPropertyAsString(bean.getToBeDistributedDetailsCount()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getToBeDistributedDetailsGrossAmount()));
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }
}
