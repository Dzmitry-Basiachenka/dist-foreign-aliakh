package com.copyright.rup.dist.foreign.repository.impl.csv.acl;

import com.copyright.rup.dist.common.repository.impl.csv.BaseCsvReportHandler;
import com.copyright.rup.dist.foreign.domain.AclScenarioDetailDto;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Writes ACL scenario details into an {@link OutputStream}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 07/20/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclScenarioDetailCsvReportHandler extends BaseCsvReportHandler<AclScenarioDetailDto> {

    private static final List<String> HEADERS = Arrays.asList("Detail ID", "Usage Detail ID", "Product Family",
        "Usage Batch Name", "Period End Date", "Wr Wrk Inst", "System Title", "Print RH Account #", "Print RH Name",
        "Print Payee Account #", "Print Payee Name", "Digital RH Account #", "Digital RH Name",
        "Digital Payee Account #", "Digital Payee Name", "Usage Period", "Usage Age Weight", "Det LC ID", "Det LC Name",
        "Agg LC ID", "Agg LC Name", "Survey Country", "Reported TOU", "TOU", "# of Copies", "# of Weighted Copies",
        "Pub Type", "Pub Type Weight", "Price", "Price Flag", "Content", "Content Flag", "Content Unit Price",
        "CUP Flag", "Print Value Share", "Print Volume Share", "Print Detail Share", "Print Net Amt in USD",
        "Digital Value Share", "Digital Volume Share", "Digital Detail Share", "Digital Net Amt in USD",
        "Combined Net Amt in USD");

    /**
     * Constructor.
     *
     * @param outputStream instance of {@link OutputStream}
     */
    public AclScenarioDetailCsvReportHandler(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    protected List<String> getBeanProperties(AclScenarioDetailDto bean) {
        List<String> beanProperties = new ArrayList<>();
        beanProperties.add(bean.getId());
        beanProperties.add(bean.getOriginalDetailId());
        beanProperties.add(bean.getProductFamily());
        beanProperties.add(bean.getUsageBatchName());
        beanProperties.add(getBeanPropertyAsString(bean.getPeriodEndPeriod()));
        beanProperties.add(getBeanPropertyAsString(bean.getWrWrkInst()));
        beanProperties.add(bean.getSystemTitle());
        beanProperties.add(getBeanPropertyAsString(bean.getRhAccountNumberPrint()));
        beanProperties.add(bean.getRhNamePrint());
        beanProperties.add(getBeanPropertyAsString(bean.getPayeeAccountNumberPrint()));
        beanProperties.add(bean.getPayeeNamePrint());
        beanProperties.add(getBeanPropertyAsString(bean.getRhAccountNumberDigital()));
        beanProperties.add(bean.getRhNameDigital());
        beanProperties.add(getBeanPropertyAsString(bean.getPayeeAccountNumberDigital()));
        beanProperties.add(bean.getPayeeNameDigital());
        beanProperties.add(getBeanPropertyAsString(bean.getUsagePeriod()));
        beanProperties.add(getBeanBigDecimal(bean.getUsageAgeWeight()));
        beanProperties.add(getBeanPropertyAsString(bean.getDetailLicenseeClassId()));
        beanProperties.add(bean.getDetailLicenseeClassName());
        beanProperties.add(getBeanPropertyAsString(bean.getAggregateLicenseeClassId()));
        beanProperties.add(bean.getAggregateLicenseeClassName());
        beanProperties.add(bean.getSurveyCountry());
        beanProperties.add(bean.getReportedTypeOfUse());
        beanProperties.add(bean.getTypeOfUse());
        beanProperties.add(getBeanBigDecimal(bean.getNumberOfCopies()));
        beanProperties.add(getBeanBigDecimal(bean.getWeightedCopies()));
        beanProperties.add(bean.getPublicationType().getName());
        beanProperties.add(getBeanBigDecimal(bean.getPublicationType().getWeight()));
        beanProperties.add(getBeanBigDecimal(bean.getPrice()));
        beanProperties.add(getFlagAsString(bean.isPriceFlag()));
        beanProperties.add(getBeanBigDecimal(bean.getContent()));
        beanProperties.add(getFlagAsString(bean.isContentFlag()));
        beanProperties.add(getBeanBigDecimal(bean.getContentUnitPrice()));
        beanProperties.add(getFlagAsString(bean.isContentUnitPriceFlag()));
        beanProperties.add(getBeanBigDecimal(bean.getValueSharePrint()));
        beanProperties.add(getBeanBigDecimal(bean.getVolumeSharePrint()));
        beanProperties.add(getBeanBigDecimal(bean.getDetailSharePrint()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getNetAmountPrint()));
        beanProperties.add(getBeanBigDecimal(bean.getValueShareDigital()));
        beanProperties.add(getBeanBigDecimal(bean.getVolumeShareDigital()));
        beanProperties.add(getBeanBigDecimal(bean.getDetailShareDigital()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getNetAmountDigital()));
        beanProperties.add(roundAndGetBeanBigDecimal(bean.getCombinedNetAmount()));
        return beanProperties;
    }

    @Override
    protected List<String> getBeanHeaders() {
        return HEADERS;
    }

    private String getFlagAsString(boolean flag) {
        return flag ? "Y" : "N";
    }
}
