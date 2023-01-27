package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineValueFilter;
import com.copyright.rup.dist.foreign.domain.filter.UdmProxyValueFilter;
import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;
import com.copyright.rup.dist.foreign.domain.filter.UdmValueFilter;
import com.copyright.rup.dist.foreign.domain.report.UdmAssigneeStatusReportDto;
import com.copyright.rup.dist.foreign.domain.report.UdmBaselineValueUpdatesReportDto;
import com.copyright.rup.dist.foreign.domain.report.UdmSurveyDashboardReportDto;
import com.copyright.rup.dist.foreign.domain.report.UdmUsableDetailsByCountryReportDto;
import com.copyright.rup.dist.foreign.domain.report.UdmUsageEditsInBaselineReportDto;
import com.copyright.rup.dist.foreign.domain.report.UdmVerifiedDetailsBySourceReportDto;
import com.copyright.rup.dist.foreign.domain.report.UdmWeeklySurveyReportDto;
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
public class UdmDomainVerifierTest {

    private final Class<?> clazz;

    /**
     * Constructor.
     *
     * @param clazz class to verify
     */
    public UdmDomainVerifierTest(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] testData = {
            {UdmBatch.class},
            {UdmUsage.class},
            {UdmUsageDto.class},
            {UdmUsageFilter.class},
            {UdmActionReason.class},
            {UdmIneligibleReason.class},
            {UdmValueDto.class},
            {UdmValueFilter.class},
            {UdmValue.class},
            {UdmValueAuditItem.class},
            {UdmValueBaselineDto.class},
            {UdmBaselineValueFilter.class},
            {UdmBaselineDto.class},
            {UdmProxyValueFilter.class},
            {UdmWeeklySurveyReportDto.class},
            {UdmVerifiedDetailsBySourceReportDto.class},
            {UdmUsageEditsInBaselineReportDto.class},
            {UdmUsableDetailsByCountryReportDto.class},
            {UdmAssigneeStatusReportDto.class},
            {UdmBaselineValueUpdatesReportDto.class},
            {UdmSurveyDashboardReportDto.class},
        };
        return List.of(testData);
    }

    @Test
    public void testPojoStructureAndBehavior() {
        TestUtils.validatePojo(clazz);
    }
}
