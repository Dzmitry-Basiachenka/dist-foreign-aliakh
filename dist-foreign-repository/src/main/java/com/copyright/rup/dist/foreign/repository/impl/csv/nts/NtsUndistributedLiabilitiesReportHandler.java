package com.copyright.rup.dist.foreign.repository.impl.csv.nts;

import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.report.UndistributedLiabilitiesReportDto;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Writes NTS Undistributed Liabilities Report into a {@link OutputStream} connected to the {@link java.io.InputStream}.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 06/30/20
 *
 * @author Anton Azarenka
 */
public class NtsUndistributedLiabilitiesReportHandler extends BaseCsvReportHandler<UndistributedLiabilitiesReportDto> {

    private static final List<String> HEADERS =
        List.of("Gross Amount", "Estimated Service Fee %", "Estimated Service Fee Amount", "Estimated Net Amount");

    /**
     * Constructor.
     *
     * @param outputStream {@link OutputStream} instance
     */
    public NtsUndistributedLiabilitiesReportHandler(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    protected List<String> getBeanProperties(UndistributedLiabilitiesReportDto bean) {
        List<String> beanProperties = new ArrayList<>();
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getGrossAmount()));
        beanProperties.add(getBeanServiceFeePercent(bean.getEstimatedServiceFee()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getEstimatedServiceFeeAmount()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getEstimatedNetAmount()));
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }
}
