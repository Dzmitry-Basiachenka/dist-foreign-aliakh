package com.copyright.rup.dist.foreign.repository.impl.csv.acl;

import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.AclFundPoolDetailDto;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Writes ACL fund pool details into an {@link OutputStream}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 05/16/2022
 *
 * @author Ihar Suvorau
 */
public class AclFundPoolDetailsCsvReportHandler extends BaseCsvReportHandler<AclFundPoolDetailDto> {

    private static final List<String> HEADERS = Arrays.asList("Fund Pool Name", "Period", "License Type", "Source",
        "Det LC ID", "Det LC Name", "Agg LC ID", "Agg LC Name", "Fund Pool Type", "Gross Amount", "Net Amount");

    /**
     * Constructor.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    public AclFundPoolDetailsCsvReportHandler(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    protected List<String> getBeanProperties(AclFundPoolDetailDto bean) {
        List<String> beanProperties = new ArrayList<>();
        beanProperties.add(bean.getFundPoolName());
        beanProperties.add(getBeanPropertyAsString(bean.getPeriod()));
        beanProperties.add(bean.getLicenseType());
        beanProperties.add(bean.isLdmtFlag() ? "LDMT" : "Manual");
        beanProperties.add(getBeanPropertyAsString(bean.getDetailLicenseeClass().getId()));
        beanProperties.add(bean.getDetailLicenseeClass().getDescription());
        beanProperties.add(getBeanPropertyAsString(bean.getAggregateLicenseeClass().getId()));
        beanProperties.add(bean.getAggregateLicenseeClass().getDescription());
        beanProperties.add(bean.getTypeOfUse());
        beanProperties.add(getBeanBigDecimal(bean.getGrossAmount()));
        beanProperties.add(getBeanBigDecimal(bean.getNetAmount()));
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }
}
