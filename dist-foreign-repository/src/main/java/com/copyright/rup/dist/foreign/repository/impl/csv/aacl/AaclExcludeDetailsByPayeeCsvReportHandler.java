package com.copyright.rup.dist.foreign.repository.impl.csv.aacl;

import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.PayeeTotalHolder;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Writes AACL Exclude Details by Payee report into a {@link OutputStream} connected to the {@link java.io.InputStream}.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 11/27/2020
 *
 * @author Ihar Suvorau
 */
public class AaclExcludeDetailsByPayeeCsvReportHandler extends BaseCsvReportHandler<PayeeTotalHolder> {

    private static final List<String> HEADERS = List.of("Exclude Status", "Payee Account #", "Payee Name",
        "Gross Amt in USD", "Service Fee Amount", "Net Amt in USD");

    private final Set<Long> selectedAccountNumbers;

    /**
     * Constructor.
     *
     * @param outputStream           instance of {@link OutputStream}
     * @param selectedAccountNumbers set of account numbers of selected payees
     */
    public AaclExcludeDetailsByPayeeCsvReportHandler(OutputStream outputStream, Set<Long> selectedAccountNumbers) {
        super(outputStream);
        this.selectedAccountNumbers = selectedAccountNumbers;
    }

    @Override
    protected List<String> getBeanProperties(PayeeTotalHolder bean) {
        List<String> beanProperties = new ArrayList<>(HEADERS.size());
        beanProperties.add(selectedAccountNumbers.contains(bean.getPayee().getAccountNumber()) ? "[X]" : "[_]");
        beanProperties.add(getBeanPropertyAsString(bean.getPayee().getAccountNumber()));
        beanProperties.add(bean.getPayee().getName());
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getGrossTotal()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getServiceFeeTotal()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getNetTotal()));
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }
}
