package com.copyright.rup.dist.foreign.repository.impl.csv.acl;

import com.copyright.rup.dist.foreign.domain.report.AclLiabilitiesByRhReportDto;
import com.copyright.rup.dist.foreign.domain.report.AclReportTotalAmountsDto;

import org.apache.commons.lang3.StringUtils;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Writes ACL Liabilities by Rightsholder Report {@link OutputStream}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 10/18/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclLiabilitiesByRhCsvReportHandler
    extends AclCommonCalculationsCsvReportHandler<AclLiabilitiesByRhReportDto> {

    private static final List<String> HEADERS =
        List.of("RH Account #", "RH Name", "Print Payee Account #", "Print Payee Account Name",
            "Digital Payee Account #", "Digital Payee Account Name", "Gross Amount", "Net Amount", "Print Net Amount",
            "Digital Net Amount", "ACL Net Amount", "MACL Net Amount", "VGW Net Amount", "JACDCL Net Amount");

    /**
     * Constructor.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    public AclLiabilitiesByRhCsvReportHandler(OutputStream outputStream) {
        super(outputStream, "Liabilities by Rightsholder Report");
    }

    @Override
    public void writeTotals(AclReportTotalAmountsDto totalAmountsDto) {
        writeStringRow(List.of("Grand Total", StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY,
            StringUtils.EMPTY, StringUtils.EMPTY, getBeanBigDecimal(totalAmountsDto.getGrossAmount()),
            getBeanBigDecimal(totalAmountsDto.getNetAmount()),
            getBeanBigDecimal(totalAmountsDto.getPrintNetAmount()),
            getBeanBigDecimal(totalAmountsDto.getDigitalNetAmount()),
            getBeanBigDecimal(totalAmountsDto.getAclNetAmount()),
            getBeanBigDecimal(totalAmountsDto.getMaclNetAmount()),
            getBeanBigDecimal(totalAmountsDto.getVgwNetAmount()),
            getBeanBigDecimal(totalAmountsDto.getJacdclNetAmount())));
    }

    @Override
    protected List<String> getBeanProperties(AclLiabilitiesByRhReportDto bean) {
        List<String> beanProperties = new ArrayList<>();
        beanProperties.add(getBeanPropertyAsString(bean.getRhAccountNumber()));
        beanProperties.add(bean.getRhName());
        beanProperties.add(getBeanPropertyAsString(bean.getPrintPayeeAccountNumber()));
        beanProperties.add(bean.getPrintPayeeName());
        beanProperties.add(getBeanPropertyAsString(bean.getDigitalPayeeAccountNumber()));
        beanProperties.add(bean.getDigitalPayeeName());
        beanProperties.add(getBeanBigDecimal(bean.getGrossAmount()));
        beanProperties.add(getBeanBigDecimal(bean.getNetAmount()));
        beanProperties.add(getBeanBigDecimal(bean.getPrintNetAmount()));
        beanProperties.add(getBeanBigDecimal(bean.getDigitalNetAmount()));
        beanProperties.add(getBeanBigDecimal(bean.getAclNetAmount()));
        beanProperties.add(getBeanBigDecimal(bean.getMaclNetAmount()));
        beanProperties.add(getBeanBigDecimal(bean.getVgwNetAmount()));
        beanProperties.add(getBeanBigDecimal(bean.getJacdclNetAmount()));
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }
}
