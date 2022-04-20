package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.filter.AclGrantDetailFilter;
import com.copyright.rup.dist.foreign.domain.filter.AclUsageFilter;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.ExcludePayeeFilter;
import com.copyright.rup.dist.foreign.domain.filter.FilterBetweenExpression;
import com.copyright.rup.dist.foreign.domain.filter.FilterEqualsExpression;
import com.copyright.rup.dist.foreign.domain.filter.FilterGreaterThanExpression;
import com.copyright.rup.dist.foreign.domain.filter.FilterLessThanExpression;
import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineValueFilter;
import com.copyright.rup.dist.foreign.domain.filter.UdmProxyValueFilter;
import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;
import com.copyright.rup.dist.foreign.domain.filter.UdmValueFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.domain.report.BatchStatistic;
import com.copyright.rup.dist.foreign.domain.report.FasBatchSummaryReportDto;
import com.copyright.rup.dist.foreign.domain.report.FasServiceFeeTrueUpReportDto;
import com.copyright.rup.dist.foreign.domain.report.NtsServiceFeeTrueUpReportDto;
import com.copyright.rup.dist.foreign.domain.report.NtsWithDrawnBatchSummaryReportDto;
import com.copyright.rup.dist.foreign.domain.report.ResearchStatusReportDto;
import com.copyright.rup.dist.foreign.domain.report.SalFundPoolReportDto;
import com.copyright.rup.dist.foreign.domain.report.SalHistoricalItemBankDetailsReportDto;
import com.copyright.rup.dist.foreign.domain.report.SalLiabilitiesByRhReportDto;
import com.copyright.rup.dist.foreign.domain.report.SalLiabilitiesSummaryByRhAndWorkReportDto;
import com.copyright.rup.dist.foreign.domain.report.SalLicensee;
import com.copyright.rup.dist.foreign.domain.report.SalUndistributedLiabilitiesReportDto;
import com.copyright.rup.dist.foreign.domain.report.UdmUsableDetailsByCountryReportDto;
import com.copyright.rup.dist.foreign.domain.report.UdmUsageEditsInBaselineReportDto;
import com.copyright.rup.dist.foreign.domain.report.UdmValuesByStatusReportDto;
import com.copyright.rup.dist.foreign.domain.report.UdmVerifiedDetailsBySourceReportDto;
import com.copyright.rup.dist.foreign.domain.report.UdmWeeklySurveyReportDto;
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
            {UsageBatch.NtsFields.class},
            {Usage.class},
            {AaclUsage.class},
            {UsageDto.class},
            {PaidUsage.class},
            {Scenario.class},
            {Scenario.AaclFields.class},
            {Scenario.NtsFields.class},
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
            {FasServiceFeeTrueUpReportDto.class},
            {NtsServiceFeeTrueUpReportDto.class},
            {BatchStatistic.class},
            {Work.class},
            {WorkClassification.class},
            {FundPool.class},
            {FundPool.SalFields.class},
            {ExcludePayeeFilter.class},
            {PayeeTotalHolder.class},
            {AaclClassifiedUsage.class},
            {PublicationType.class},
            {DetailLicenseeClass.class},
            {AggregateLicenseeClass.class},
            {FundPoolDetail.class},
            {RhTaxInformation.class},
            {RightsholderPayeeProductFamilyHolder.class},
            {SalUsage.class},
            {SalLiabilitiesByRhReportDto.class},
            {SalLiabilitiesSummaryByRhAndWorkReportDto.class},
            {SalUndistributedLiabilitiesReportDto.class},
            {SalFundPoolReportDto.class},
            {SalLicensee.class},
            {SalHistoricalItemBankDetailsReportDto.class},
            {UsageBatchStatus.class},
            {UdmBatch.class},
            {UdmUsage.class},
            {UdmUsageDto.class},
            {UdmUsageFilter.class},
            {FilterEqualsExpression.class},
            {FilterBetweenExpression.class},
            {FilterGreaterThanExpression.class},
            {FilterLessThanExpression.class},
            {UdmActionReason.class},
            {UdmIneligibleReason.class},
            {UdmValueDto.class},
            {UdmValueFilter.class},
            {UdmValue.class},
            {UdmValueAuditItem.class},
            {UdmValueBaselineDto.class},
            {UdmBaselineValueFilter.class},
            {UdmBaselineDto.class},
            {ExchangeRate.class},
            {UdmProxyValueFilter.class},
            {UdmWeeklySurveyReportDto.class},
            {UdmVerifiedDetailsBySourceReportDto.class},
            {AclGrantSet.class},
            {AclGrantDetail.class},
            {AclGrantDetailDto.class},
            {AclGrantDetailFilter.class},
            {UdmUsageEditsInBaselineReportDto.class},
            {UdmUsableDetailsByCountryReportDto.class},
            {AclUsageBatch.class},
            {AclUsageDto.class},
            {AclIneligibleRightsholder.class},
            {AclUsageFilter.class},
            {UdmValuesByStatusReportDto.class},
            {AclFundPool.class}
        };
        return Arrays.asList(testData);
    }

    @Test
    public void testPojoStructureAndBehavior() {
        TestUtils.validatePojo(classToVerify);
    }
}
