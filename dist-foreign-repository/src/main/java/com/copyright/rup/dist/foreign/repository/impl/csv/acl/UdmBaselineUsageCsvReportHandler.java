package com.copyright.rup.dist.foreign.repository.impl.csv.acl;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.UdmBaselineDto;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Writes UDM baseline usages into an {@link OutputStream}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/22/2021
 *
 * @author Dzmitry Basiachenka
 */
public class UdmBaselineUsageCsvReportHandler extends BaseCsvReportHandler<UdmBaselineDto> {

    private static final List<String> HEADERS = Arrays.asList("Detail ID", "Period", "Usage Origin", "Usage Detail ID",
        "Wr Wrk Inst", "System Title", "Det LC ID", "Det LC Name", "Agg LC ID", "Agg LC Name", "Survey Country",
        "Channel", "TOU", "Annualized Copies", "Created By", "Created Date", "Updated By", "Updated Date");

    /**
     * Constructor.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    public UdmBaselineUsageCsvReportHandler(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    protected List<String> getBeanProperties(UdmBaselineDto bean) {
        List<String> beanProperties = new ArrayList<>();
        beanProperties.add(bean.getId());
        beanProperties.add(getBeanPropertyAsString(bean.getPeriod()));
        beanProperties.add(bean.getUsageOrigin().name());
        beanProperties.add(bean.getOriginalDetailId());
        beanProperties.add(getBeanPropertyAsString(bean.getWrWrkInst()));
        beanProperties.add(bean.getSystemTitle());
        beanProperties.add(getBeanPropertyAsString(bean.getDetailLicenseeClassId()));
        beanProperties.add(bean.getDetailLicenseeClassName());
        beanProperties.add(getBeanPropertyAsString(bean.getAggregateLicenseeClassId()));
        beanProperties.add(bean.getAggregateLicenseeClassName());
        beanProperties.add(bean.getSurveyCountry());
        beanProperties.add(bean.getChannel().name());
        beanProperties.add(bean.getTypeOfUse());
        beanProperties.add(Objects.nonNull(bean.getAnnualizedCopies())
            ? roundAndGetBeanBigDecimal(bean.getAnnualizedCopies())
            : StringUtils.EMPTY);
        beanProperties.add(bean.getCreateUser());
        beanProperties.add(getStringFromDate(bean.getCreateDate()));
        beanProperties.add(bean.getUpdateUser());
        beanProperties.add(getStringFromDate(bean.getUpdateDate()));
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }

    private String getStringFromDate(Date date) {
        return Objects.nonNull(date)
            ? FastDateFormat.getInstance(RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT).format(date)
            : StringUtils.EMPTY;
    }
}
