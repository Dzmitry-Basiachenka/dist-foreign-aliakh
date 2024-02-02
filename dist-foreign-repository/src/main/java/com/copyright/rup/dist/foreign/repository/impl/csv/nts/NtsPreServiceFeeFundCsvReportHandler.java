package com.copyright.rup.dist.foreign.repository.impl.csv.nts;

import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.report.NtsPreServiceFeeFundReportDto;

import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Writes NTS pre-service fee fund into a {@link PipedOutputStream} connected to the {@link java.io.PipedInputStream}.
 * <p/>
 * Copyright (C) 2024 copyright.com
 * <p/>
 *Date: 02/01/2024
 *
 * @author Dzmitry Basiachenka
 */
public class NtsPreServiceFeeFundCsvReportHandler extends BaseCsvReportHandler<NtsPreServiceFeeFundReportDto> {

    private static final List<String> HEADERS =
        List.of("Usage Batch Name", "Payment Date", "# of Details", "Gross Amt in USD");

    /**
     * Constructor.
     *
     * @param pipedOutputStream instance of {@link PipedOutputStream}
     */
    public NtsPreServiceFeeFundCsvReportHandler(PipedOutputStream pipedOutputStream) {
        super(pipedOutputStream);
    }

    @Override
    protected List<String> getBeanProperties(NtsPreServiceFeeFundReportDto bean) {
        List<String> beanProperties = new ArrayList<>(HEADERS.size());
        beanProperties.add(bean.getBatchName());
        beanProperties.add(getBeanLocalDate(bean.getPaymentDate()));
        beanProperties.add(getBeanPropertyAsString(bean.getNumberOfDetails()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getGrossAmount()));
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }
}
