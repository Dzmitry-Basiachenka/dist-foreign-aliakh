package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.ExcludePayeeFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.domain.report.BatchStatistic;
import com.copyright.rup.dist.foreign.domain.report.FasBatchSummaryReportDto;
import com.copyright.rup.dist.foreign.domain.report.NtsWithDrawnBatchSummaryReportDto;
import com.copyright.rup.dist.foreign.domain.report.ResearchStatusReportDto;
import com.copyright.rup.dist.foreign.domain.report.ServiceFeeTrueUpReportDto;
import com.copyright.rup.dist.foreign.domain.report.UndistributedLiabilitiesReportDto;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/**
 * Verifies {@link Object#equals(Object)}, {@link Object#hashCode()},
 * {@link Object#toString()}, getters, setters methods for domain classes.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 01/16/17
 *
 * @author Aliaksei Pchelnikau
 */
@RunWith(Parameterized.class)
public class DomainVerifierTest {

    private final Class classToVerify;

    /**
     * Constructs new test for given class.
     *
     * @param classToVerify class to verify
     */
    public DomainVerifierTest(Class classToVerify) {
        this.classToVerify = classToVerify;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] testData = new Object[][]{
            {UsageFilter.class},
            {AuditFilter.class},
            {UsageBatch.class},
            {Usage.class},
            {AaclUsage.class},
            {AaclFundPool.class},
            {UsageDto.class},
            {PaidUsage.class},
            {Scenario.class},
            {RightsholderTotalsHolder.class},
            {UsageAuditItem.class},
            {ScenarioAuditItem.class},
            {RightsholderPayeePair.class},
            {RightsholderDiscrepancy.class},
            {ResearchedUsage.class},
            {UndistributedLiabilitiesReportDto.class},
            {FasBatchSummaryReportDto.class},
            {NtsWithDrawnBatchSummaryReportDto.class},
            {ResearchStatusReportDto.class},
            {ServiceFeeTrueUpReportDto.class},
            {BatchStatistic.class},
            {Work.class},
            {WorkClassification.class},
            {PreServiceFeeFund.class},
            {ExcludePayeeFilter.class},
            {PayeeTotalHolder.class},
            {AaclClassifiedUsage.class},
            {PublicationType.class},
            {AggregateLicenseeClass.class},
            {AaclFundPoolDetail.class}
        };
        return Arrays.asList(testData);
    }

    @Test
    public void testPojoStructureAndBehavior() {
        TestUtils.validatePojo(classToVerify);
    }
}
