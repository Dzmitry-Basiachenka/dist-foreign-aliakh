package com.copyright.rup.dist.foreign.repository.impl.csv.acl;

import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.AclUsageDto;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Writes ACL grant details into an {@link OutputStream}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/13/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclUsageCsvReportHandler extends BaseCsvReportHandler<AclUsageDto> {

    private static final List<String> HEADERS = Arrays.asList("Detail ID", "Period", "Usage Origin", "Channel",
        "Usage Detail ID", "Wr Wrk Inst", "System Title", "Det LC ID", "Det LC Name", "Agg LC ID", "Agg LC Name",
        "Survey Country", "Pub Type", "Content Unit Price", "TOU", "Annualized Copies", "Updated By", "Updated Date");

    /**
     * Constructor.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    public AclUsageCsvReportHandler(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    protected List<String> getBeanProperties(AclUsageDto bean) {
        List<String> beanProperties = new ArrayList<>();
        beanProperties.add(bean.getId());
        beanProperties.add(getBeanPropertyAsString(bean.getPeriod()));
        beanProperties.add(bean.getUsageOrigin().name());
        beanProperties.add(bean.getChannel().name());
        beanProperties.add(bean.getOriginalDetailId());
        beanProperties.add(getBeanPropertyAsString(bean.getWrWrkInst()));
        beanProperties.add(bean.getSystemTitle());
        beanProperties.add(getBeanPropertyAsString(bean.getDetailLicenseeClassId()));
        beanProperties.add(bean.getDetailLicenseeClassName());
        beanProperties.add(getBeanPropertyAsString(bean.getAggregateLicenseeClassId()));
        beanProperties.add(bean.getAggregateLicenseeClassName());
        beanProperties.add(bean.getSurveyCountry());
        beanProperties.add(bean.getPubTypeName());
        beanProperties.add(getBeanBigDecimal(bean.getContentUnitPrice()));
        beanProperties.add(bean.getTypeOfUse());
        beanProperties.add(getBeanBigDecimal(bean.getAnnualizedCopies()));
        beanProperties.add(bean.getUpdateUser());
        beanProperties.add(convertDateToString(bean.getUpdateDate()));
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }
}
