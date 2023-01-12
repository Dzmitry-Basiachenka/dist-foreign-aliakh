package com.copyright.rup.dist.foreign.service.impl.csv;

import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportWriter;
import com.copyright.rup.dist.foreign.domain.RhTaxInformation;

import org.apache.commons.lang3.StringUtils;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Writes Tax Notification CSV report.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 6/22/20
 *
 * @author Stanislau Rudak
 */
public class RhTaxInformationCsvReportWriter extends BaseCsvReportWriter<RhTaxInformation> {

    private static final List<String> HEADERS =
        List.of("Type of Form", "Number of Letter", "Product Family", "Payee Account #", "Payee Name",
            "RH Account #", "RH Name", "TBO Account #", "TBO Name", "Attn:", "Recipient Address1", "Recipient Address2",
            "Recipient Address3", "Recipient Address4", "City", "State", "Zip Code", "Country", "Paygroup",
            "Witholding Indicator");

    /**
     * Constructor.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    public RhTaxInformationCsvReportWriter(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    protected List<String> getBeanProperties(RhTaxInformation bean) {
        List<String> beanProperties = new ArrayList<>();
        beanProperties.add(bean.getTypeOfForm());
        beanProperties.add(getBeanPropertyAsString(bean.getNotificationsSent()));
        beanProperties.add(bean.getProductFamily());
        beanProperties.add(getBeanPropertyAsString(bean.getPayeeAccountNumber()));
        beanProperties.add(bean.getPayeeName());
        beanProperties.add(getBeanPropertyAsString(bean.getRhAccountNumber()));
        beanProperties.add(bean.getRhName());
        beanProperties.add(getBeanPropertyAsString(bean.getTboAccountNumber()));
        beanProperties.add(bean.getTboName());
        beanProperties.add(StringUtils.EMPTY);
        beanProperties.add(bean.getAddressLine1());
        beanProperties.add(bean.getAddressLine2());
        beanProperties.add(bean.getAddressLine3());
        beanProperties.add(bean.getAddressLine4());
        beanProperties.add(bean.getCity());
        beanProperties.add(bean.getState());
        beanProperties.add(bean.getZip());
        beanProperties.add(bean.getCountry());
        beanProperties.add(bean.getPayGroup());
        beanProperties.add(bean.getWithHoldingIndicator());
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }
}
