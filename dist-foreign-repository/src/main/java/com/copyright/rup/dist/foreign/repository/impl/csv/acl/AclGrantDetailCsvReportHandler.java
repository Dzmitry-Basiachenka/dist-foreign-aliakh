package com.copyright.rup.dist.foreign.repository.impl.csv.acl;

import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.AclGrantDetailDto;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Writes ACL grant details into an {@link OutputStream}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/15/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclGrantDetailCsvReportHandler extends BaseCsvReportHandler<AclGrantDetailDto> {

    private static final List<String> HEADERS = List.of("Grant Set Name", "Grant Period", "License Type",
        "TOU Status", "Grant Status", "Eligible", "Wr Wrk Inst", "System Title", "RH Account #", "RH Name", "TOU",
        "Created Date", "Updated Date", "Manual Upload Flag");

    /**
     * Constructor.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    public AclGrantDetailCsvReportHandler(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    protected List<String> getBeanProperties(AclGrantDetailDto bean) {
        List<String> beanProperties = new ArrayList<>();
        beanProperties.add(getBeanPropertyAsString(bean.getGrantSetName()));
        beanProperties.add(getBeanPropertyAsString(bean.getGrantPeriod()));
        beanProperties.add(bean.getLicenseType());
        beanProperties.add(bean.getTypeOfUseStatus());
        beanProperties.add(bean.getGrantStatus());
        beanProperties.add(bean.getEligible() ? "Y" : "N");
        beanProperties.add(getBeanPropertyAsString(bean.getWrWrkInst()));
        beanProperties.add(bean.getSystemTitle());
        beanProperties.add(getBeanPropertyAsString(bean.getRhAccountNumber()));
        beanProperties.add(bean.getRhName());
        beanProperties.add(bean.getTypeOfUse());
        beanProperties.add(convertDateToString(bean.getCreateDate()));
        beanProperties.add(convertDateToString(bean.getUpdateDate()));
        beanProperties.add(bean.getManualUploadFlag() ? "Y" : "N");
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }
}
