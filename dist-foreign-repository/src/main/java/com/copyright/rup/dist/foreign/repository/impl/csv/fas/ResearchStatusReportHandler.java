package com.copyright.rup.dist.foreign.repository.impl.csv.fas;

import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.report.ResearchStatusReportDto;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Writes calculated research amounts into a {@link OutputStream} connected to the {@link java.io.InputStream}.
 * Is used to generate Research Status Report.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 05/30/18
 *
 * @author Ihar Suvorau
 */
public class ResearchStatusReportHandler extends BaseCsvReportHandler<ResearchStatusReportDto> {

    private static final List<String> HEADERS =
        List.of("Usage Batch Name", "RRO Account Number", "RRO Name", "Payment Date", "# Details Work Not Found",
            "Gross USD Work Not Found", "# Details Work Research", "Gross USD Work Research", "# Details Sent for RA",
            "Gross USD Sent for RA", "# Details RH Not Found", "Gross USD RH Not Found");

    /**
     * Constructor.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    public ResearchStatusReportHandler(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    protected List<String> getBeanProperties(ResearchStatusReportDto bean) {
        List<String> beanProperties = new ArrayList<>();
        beanProperties.add(bean.getBatchName());
        beanProperties.add(getBeanPropertyAsString(bean.getRroAccountNumber()));
        beanProperties.add(bean.getRroName());
        beanProperties.add(getBeanLocalDate(bean.getPaymentDate()));
        beanProperties.add(getBeanPropertyAsString(bean.getWorkNotFoundDetailsCount()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getWorkNotFoundGrossAmount()));
        beanProperties.add(getBeanPropertyAsString(bean.getWorkResearchDetailsCount()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getWorkResearchGrossAmount()));
        beanProperties.add(getBeanPropertyAsString(bean.getSendForRaDetailsCount()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getSendForRaGrossAmount()));
        beanProperties.add(getBeanPropertyAsString(bean.getRhNotFoundDetailsCount()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getRhNotFoundGrossAmount()));
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }
}
