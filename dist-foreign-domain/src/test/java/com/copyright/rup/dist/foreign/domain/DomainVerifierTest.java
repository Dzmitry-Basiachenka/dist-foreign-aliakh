package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.ExcludePayeeFilter;
import com.copyright.rup.dist.foreign.domain.filter.FilterBetweenExpression;
import com.copyright.rup.dist.foreign.domain.filter.FilterEqualsExpression;
import com.copyright.rup.dist.foreign.domain.filter.FilterGreaterThanExpression;
import com.copyright.rup.dist.foreign.domain.filter.FilterLessThanExpression;
import com.copyright.rup.dist.foreign.domain.filter.RightsholderResultsFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.domain.report.BatchStatistic;
import com.copyright.rup.dist.foreign.domain.report.ResearchStatusReportDto;
import com.copyright.rup.dist.foreign.domain.report.UndistributedLiabilitiesReportDto;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;
import java.util.List;

/**
 * Verifies {@link Object#equals(Object)}, {@link Object#hashCode()},
 * {@link Object#toString()}, getters, setters methods for domain classes.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 01/16/2017
 *
 * @author Aliaksei Pchelnikau
 */
@RunWith(Parameterized.class)
public class DomainVerifierTest {

    private final Class<?> clazz;

    /**
     * Constructor.
     *
     * @param clazz class to verify
     */
    public DomainVerifierTest(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] testData = {
            {UsageFilter.class},
            {AuditFilter.class},
            {UsageBatch.class},
            {Usage.class},
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
            {ResearchStatusReportDto.class},
            {BatchStatistic.class},
            {Work.class},
            {WorkClassification.class},
            {FundPool.class},
            {ExcludePayeeFilter.class},
            {PayeeTotalHolder.class},
            {PublicationType.class},
            {DetailLicenseeClass.class},
            {AggregateLicenseeClass.class},
            {FundPoolDetail.class},
            {RhTaxInformation.class},
            {RightsholderPayeeProductFamilyHolder.class},
            {UsageBatchStatus.class},
            {FilterEqualsExpression.class},
            {FilterBetweenExpression.class},
            {FilterGreaterThanExpression.class},
            {FilterLessThanExpression.class},
            {ExchangeRate.class},
            {LdmtDetail.class},
            {RightsholderResultsFilter.class},
            {RightsholderTypeOfUsePair.class},
        };
        return List.of(testData);
    }

    @Test
    public void testPojoStructureAndBehavior() {
        TestUtils.validatePojo(clazz);
    }
}
