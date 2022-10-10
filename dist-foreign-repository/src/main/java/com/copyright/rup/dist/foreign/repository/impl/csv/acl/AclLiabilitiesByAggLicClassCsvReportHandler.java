package com.copyright.rup.dist.foreign.repository.impl.csv.acl;

import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.report.AclLiabilitiesByAggLicClassReportDto;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Writes ACL Liabilities By Agg Licensee Class Report {@link OutputStream}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 10/10/2022
 *
 * @author Ihar Suvorau
 */
public class AclLiabilitiesByAggLicClassCsvReportHandler
    extends BaseCsvReportHandler<AclLiabilitiesByAggLicClassReportDto> {

    private static final List<String> HEADERS =
        Arrays.asList("Agg LC ID", "Agg LC Name", "Gross Amount", "Net Amount", "Print Net Amount",
            "Digital Net Amount", "ACL Net Amount", "MACL Net Amount", "VGW Net Amount", "JACDCL Net Amount");

    /**
     * Constructor.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    public AclLiabilitiesByAggLicClassCsvReportHandler(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    protected List<String> getBeanProperties(AclLiabilitiesByAggLicClassReportDto bean) {
        List<String> beanProperties = new ArrayList<>();
        beanProperties.add(getBeanPropertyAsString(bean.getAggregateLicenseeClassId()));
        beanProperties.add(bean.getAggregateLicenseeClassName());
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
