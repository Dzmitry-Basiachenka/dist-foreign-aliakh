package com.copyright.rup.dist.foreign.repository.impl.csv.acl;

import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.AclUsageDto;

import org.apache.commons.lang3.StringUtils;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    private static final List<String> HEADERS = List.of("Detail ID", "Period", "Usage Origin", "Channel",
        "Usage Detail ID", "Wr Wrk Inst", "System Title", "Det LC ID", "Det LC Name", "Agg LC ID", "Agg LC Name",
        "Survey Country", "Pub Type", "Price", "Content", "Content Unit Price", "CUP Flag", "Reported TOU", "TOU",
        "Annualized Copies", "MDWMS Deleted", "Updated By", "Updated Date");

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
        beanProperties.add(getBeanPropertyAsString(bean.getDetailLicenseeClass().getId()));
        beanProperties.add(bean.getDetailLicenseeClass().getDescription());
        beanProperties.add(getBeanPropertyAsString(bean.getAggregateLicenseeClassId()));
        beanProperties.add(bean.getAggregateLicenseeClassName());
        beanProperties.add(bean.getSurveyCountry());
        beanProperties.add(
            Objects.nonNull(bean.getPublicationType()) ? bean.getPublicationType().getName() : StringUtils.EMPTY);
        beanProperties.add(getBeanBigDecimal(bean.getPrice()));
        beanProperties.add(getBeanBigDecimal(bean.getContent()));
        beanProperties.add(getBeanBigDecimal(bean.getContentUnitPrice()));
        beanProperties.add(bean.getContentUnitPriceFlag() ? "Y" : "N");
        beanProperties.add(bean.getReportedTypeOfUse());
        beanProperties.add(bean.getTypeOfUse());
        beanProperties.add(getBeanBigDecimal(bean.getAnnualizedCopies()));
        beanProperties.add(bean.isWorkDeletedFlag() ? "Y" : "N");
        beanProperties.add(bean.getUpdateUser());
        beanProperties.add(convertDateToString(bean.getUpdateDate()));
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }
}
