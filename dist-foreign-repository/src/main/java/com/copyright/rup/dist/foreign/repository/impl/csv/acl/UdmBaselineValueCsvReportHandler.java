package com.copyright.rup.dist.foreign.repository.impl.csv.acl;

import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.UdmValueBaselineDto;
import org.apache.commons.lang3.StringUtils;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Writes UDM baseline values into an {@link OutputStream}.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 08/04/2023
 *
 * @author Aliaksandr Liakh
 */
public class UdmBaselineValueCsvReportHandler extends BaseCsvReportHandler<UdmValueBaselineDto> {

    private static final List<String> HEADERS =
        List.of("Value ID", "Value Period", "Wr Wrk Inst", "System Title", "Pub Type", "Price", "Price Flag",
            "Content", "Content Flag", "Content Unit Price", "CUP Flag", "Comment", "Updated By", "Updated Date");

    /**
     * Constructor.
     *
     * @param outputStream {@link OutputStream} instance
     */
    public UdmBaselineValueCsvReportHandler(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    protected List<String> getBeanProperties(UdmValueBaselineDto bean) {
        List<String> beanProperties = new ArrayList<>(HEADERS.size());
        beanProperties.add(bean.getId());
        beanProperties.add(getBeanPropertyAsString(bean.getPeriod()));
        beanProperties.add(getBeanPropertyAsString(bean.getWrWrkInst()));
        beanProperties.add(bean.getSystemTitle());
        beanProperties.add(bean.getPublicationType());
        beanProperties.add(getBeanBigDecimal(bean.getPrice()));
        beanProperties.add(getFlagAsString(bean.getPriceFlag()));
        beanProperties.add(getBeanBigDecimal(bean.getContent()));
        beanProperties.add(getFlagAsString(bean.getContentFlag()));
        beanProperties.add(getBeanBigDecimal(bean.getContentUnitPrice()));
        beanProperties.add(getFlagAsString(bean.getContentUnitPriceFlag()));
        beanProperties.add(bean.getComment());
        beanProperties.add(bean.getUpdateUser());
        beanProperties.add(convertDateToString(bean.getUpdateDate()));
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }

    private String getFlagAsString(Boolean flag) {
        return Objects.nonNull(flag)
            ? flag ? "Y" : "N"
            : StringUtils.EMPTY;
    }
}
