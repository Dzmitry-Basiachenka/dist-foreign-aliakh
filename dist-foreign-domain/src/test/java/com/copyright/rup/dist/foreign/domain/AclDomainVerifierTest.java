package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.filter.AclFundPoolDetailFilter;
import com.copyright.rup.dist.foreign.domain.filter.AclGrantDetailFilter;
import com.copyright.rup.dist.foreign.domain.filter.AclScenarioFilter;
import com.copyright.rup.dist.foreign.domain.filter.AclUsageFilter;
import com.copyright.rup.dist.foreign.domain.report.AclCalculationReportsInfoDto;
import com.copyright.rup.dist.foreign.domain.report.AclComparisonByAggLcClassAndTitleReportDto;
import com.copyright.rup.dist.foreign.domain.report.AclFundPoolByAggLcReportDto;
import com.copyright.rup.dist.foreign.domain.report.AclLiabilitiesByAggLicClassReportDto;
import com.copyright.rup.dist.foreign.domain.report.AclLiabilitiesByRhReportDto;
import com.copyright.rup.dist.foreign.domain.report.AclLiabilityDetailsReportDto;
import com.copyright.rup.dist.foreign.domain.report.AclReportTotalAmountsDto;
import com.copyright.rup.dist.foreign.domain.report.AclSummaryOfWorkSharesByAggLcReportDto;
import com.copyright.rup.dist.foreign.domain.report.AclWorkSharesByAggLcReportDto;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;
import java.util.List;

/**
 * Verifies {@link Object#equals(Object)}, {@link Object#hashCode()},
 * {@link Object#toString()}, getters, setters methods for domain classes.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 01/27/2023
 *
 * @author Aliaksandr Liakh
 */
@RunWith(Parameterized.class)
public class AclDomainVerifierTest {

    private final Class<?> clazz;

    /**
     * Constructor.
     *
     * @param clazz class to verify
     */
    public AclDomainVerifierTest(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] testData = {
            {AclGrantSet.class},
            {AclGrantDetail.class},
            {AclGrantDetailDto.class},
            {AclGrantDetailFilter.class},
            {AclUsageBatch.class},
            {AclUsageDto.class},
            {AclIneligibleRightsholder.class},
            {AclUsageFilter.class},
            {AclFundPool.class},
            {AclFundPoolDetail.class},
            {AclFundPoolDetailDto.class},
            {AclFundPoolDetailFilter.class},
            {AclScenario.class},
            {AclScenarioDto.class},
            {AclRightsholderTotalsHolder.class},
            {AclScenarioDetail.class},
            {AclScenarioShareDetail.class},
            {AclPublicationType.class},
            {AclScenarioDetailDto.class},
            {AclRightsholderTotalsHolderDto.class},
            {AclCalculationReportsInfoDto.class},
            {AclSummaryOfWorkSharesByAggLcReportDto.class},
            {AclWorkSharesByAggLcReportDto.class},
            {AclLiabilityDetailsReportDto.class},
            {AclLiabilitiesByAggLicClassReportDto.class},
            {AclFundPoolByAggLcReportDto.class},
            {AclReportTotalAmountsDto.class},
            {AclLiabilitiesByRhReportDto.class},
            {AclScenarioFilter.class},
            {AclScenarioLiabilityDetail.class},
            {AclComparisonByAggLcClassAndTitleReportDto.class}
        };
        return List.of(testData);
    }

    @Test
    public void testPojoStructureAndBehavior() {
        TestUtils.validatePojo(clazz);
    }
}
