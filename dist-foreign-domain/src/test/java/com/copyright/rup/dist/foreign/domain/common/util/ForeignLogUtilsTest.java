package com.copyright.rup.dist.foreign.domain.common.util;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Scenario.AaclFields;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.UsageAge;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * Verifies {@link ForeignLogUtils}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/09/18
 *
 * @author Ihar Suvorau
 */
public class ForeignLogUtilsTest {

    @Test
    public void testConstructor() {
        TestUtils.validatePrivateConstructor(ForeignLogUtils.class);
    }

    @Test
    public void testScenario() {
        Scenario scenario = new Scenario();
        scenario.setName("Scenario name");
        scenario.setStatus(ScenarioStatusEnum.SENT_TO_LM);
        assertEquals("ScenarioName='Scenario name', Status='SENT_TO_LM'",
            ForeignLogUtils.scenario(scenario).toString());
    }

    @Test
    public void testScenarioNullValue() {
        assertEquals("Scenario={NULL}", ForeignLogUtils.scenario(null).toString());
    }

    @Test
    public void testAaclFields() {
        AaclFields aaclFields = new AaclFields();
        aaclFields.setFundPoolId("cc3f6787-d8f5-4bbc-a7b1-268ebed28789");
        aaclFields.setUsageAges(Arrays.asList(
            buildUsageAge(2019, new BigDecimal("1.00")),
            buildUsageAge(2018, new BigDecimal("0.75"))
        ));
        aaclFields.setPublicationTypes(Arrays.asList(
            buildPublicationType("Book", new BigDecimal("3.12")),
            buildPublicationType("STMA Journal", new BigDecimal("0.99"))
        ));
        aaclFields.setDetailLicenseeClasses(Arrays.asList(
            buildDetailLicenseeClass(108, 141),
            buildDetailLicenseeClass(110, 143)
        ));
        String expectedString =
            "AaclFields[FundPoolId=cc3f6787-d8f5-4bbc-a7b1-268ebed28789, " +
                "UsageAges=[Period=2019, Weight=1.00], [Period=2018, Weight=0.75], " +
                "PublicationTypes=[Name=Book, Weight=3.12], [Name=STMA Journal, Weight=0.99], " +
                "DetailLicenseeClasses=[DLC=108, ALC=141], [DLC=110, ALC=143]]";
        assertEquals(expectedString, ForeignLogUtils.scenarioAaclFields(aaclFields).toString());
    }

    @Test
    public void testAaclFieldsWithNullValues() {
        AaclFields aaclFields = new AaclFields();
        aaclFields.setFundPoolId(null);
        aaclFields.setUsageAges(null);
        aaclFields.setPublicationTypes(null);
        aaclFields.setDetailLicenseeClasses(null);
        String expectedString =
            "AaclFields[FundPoolId=null, UsageAges=NULL, PublicationTypes=NULL, DetailLicenseeClasses=NULL]";
        assertEquals(expectedString, ForeignLogUtils.scenarioAaclFields(aaclFields).toString());
    }

    @Test
    public void testAaclFieldsWithNullValuesInCollections() {
        AaclFields aaclFields = new AaclFields();
        aaclFields.setFundPoolId("cc3f6787-d8f5-4bbc-a7b1-268ebed28789");
        aaclFields.setUsageAges(Arrays.asList(
            buildUsageAge(null, new BigDecimal("1.00")),
            null,
            buildUsageAge(2018, null)
        ));
        aaclFields.setPublicationTypes(Arrays.asList(
            buildPublicationType(null, new BigDecimal("3.12")),
            null,
            buildPublicationType("STMA Journal", null)
        ));
        aaclFields.setDetailLicenseeClasses(Arrays.asList(
            buildDetailLicenseeClass(null, 141),
            null,
            buildDetailLicenseeClass(110, null)
        ));
        String expectedString =
            "AaclFields[FundPoolId=cc3f6787-d8f5-4bbc-a7b1-268ebed28789, UsageAges=[Period=null, Weight=1.00], NULL, " +
                "[Period=2018, Weight=null], PublicationTypes=[Name=null, Weight=3.12], NULL, [Name=STMA Journal, " +
                "Weight=null], DetailLicenseeClasses=[DLC=null, ALC=141], NULL, [DLC=110, ALC=null]]";
        assertEquals(expectedString, ForeignLogUtils.scenarioAaclFields(aaclFields).toString());
    }

    @Test
    public void testAaclFieldsWithNullAaclFields() {
        assertEquals("AaclFields={NULL}", ForeignLogUtils.scenarioAaclFields(null).toString());
    }

    private UsageAge buildUsageAge(Integer period, BigDecimal weight) {
        UsageAge usageAge = new UsageAge();
        usageAge.setPeriod(period);
        usageAge.setWeight(weight);
        return usageAge;
    }

    private PublicationType buildPublicationType(String name, BigDecimal weight) {
        PublicationType publicationType = new PublicationType();
        publicationType.setName(name);
        publicationType.setWeight(weight);
        return publicationType;
    }

    private DetailLicenseeClass buildDetailLicenseeClass(Integer detailClassId, Integer aggregateClassId) {
        DetailLicenseeClass detailClass = new DetailLicenseeClass();
        detailClass.setId(detailClassId);
        detailClass.getAggregateLicenseeClass().setId(aggregateClassId);
        return detailClass;
    }
}
