package com.copyright.rup.dist.foreign.repository.impl.converter.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.Scenario.AaclFields;
import com.copyright.rup.dist.foreign.domain.UsageAge;

import com.fasterxml.jackson.core.JsonParseException;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * Verifies {@link AaclScenarioFieldsJsonMapper}.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 03/11/2020
 *
 * @author Aliaksandr Liakh
 */
public class AaclScenarioFieldsJsonMapperTest {

    private final AaclScenarioFieldsJsonMapper jsonMapper = new AaclScenarioFieldsJsonMapper();

    private static final String ONE = "1.00";

    @Test
    public void testDeserialize() throws IOException {
        String json = TestUtils.fileToString(this.getClass(), "aacl_scenario_fields.json");
        AaclFields aaclFields = jsonMapper.deserialize(json);
        assertEquals("8ab155cb-c96d-4355-9f37-12d26ef4d765", aaclFields.getFundPoolId());
        List<PublicationType> pubTypes = aaclFields.getPublicationTypes();
        assertNotNull(pubTypes);
        assertEquals(5, pubTypes.size());
        assertEquals(buildPublicationType("2fe9c0a0-7672-4b56-bc64-9d4125fecf6e", ONE), pubTypes.get(0));
        assertEquals(buildPublicationType("68fd94c0-a8c0-4a59-bfe3-6674c4b12199", "1.50"), pubTypes.get(1));
        assertEquals(buildPublicationType("46634907-882e-4f91-b1ad-f57db945aff7", ONE), pubTypes.get(2));
        assertEquals(buildPublicationType("a3dff475-fc06-4d8c-b7cc-f093073ada6f", "4.00"), pubTypes.get(3));
        assertEquals(buildPublicationType("1f6f1925-7aa1-4b1a-b3a8-8903acc3d18e", "1.10"), pubTypes.get(4));
        List<UsageAge> usageAges = aaclFields.getUsageAges();
        assertEquals(5, CollectionUtils.size(usageAges));
        assertEquals(buildUsageAge(2020, ONE), usageAges.get(0));
        assertEquals(buildUsageAge(2019, "0.75"), usageAges.get(1));
        assertEquals(buildUsageAge(2017, "0.50"), usageAges.get(2));
        assertEquals(buildUsageAge(2015, "0.25"), usageAges.get(3));
        assertEquals(buildUsageAge(2014, "0.00"), usageAges.get(4));
        verifyLicenseeClassMapping(aaclFields);
    }

    @Test
    public void testSerialize() throws IOException {
        AaclFields aaclFields = new AaclFields();
        aaclFields.setFundPoolId("8ab155cb-c96d-4355-9f37-12d26ef4d765");
        List<PublicationType> pubTypes = aaclFields.getPublicationTypes();
        aaclFields.setPublicationTypes(pubTypes);
        pubTypes.add(buildPublicationType("2fe9c0a0-7672-4b56-bc64-9d4125fecf6e", ONE));
        pubTypes.add(buildPublicationType("68fd94c0-a8c0-4a59-bfe3-6674c4b12199", "1.50"));
        pubTypes.add(buildPublicationType("46634907-882e-4f91-b1ad-f57db945aff7", ONE));
        pubTypes.add(buildPublicationType("a3dff475-fc06-4d8c-b7cc-f093073ada6f", "4.00"));
        pubTypes.add(buildPublicationType("1f6f1925-7aa1-4b1a-b3a8-8903acc3d18e", "1.10"));
        List<UsageAge> usageAges = aaclFields.getUsageAges();
        usageAges.add(buildUsageAge(2020, ONE));
        usageAges.add(buildUsageAge(2019, "0.75"));
        usageAges.add(buildUsageAge(2017, "0.50"));
        usageAges.add(buildUsageAge(2015, "0.25"));
        usageAges.add(buildUsageAge(2014, "0.00"));
        List<DetailLicenseeClass> detailLicenseeClasses = aaclFields.getDetailLicenseeClasses();
        detailLicenseeClasses.add(buildDetailLicenseeClass(108, 110));
        detailLicenseeClasses.add(buildDetailLicenseeClass(110, 108));
        detailLicenseeClasses.add(buildDetailLicenseeClass(111, 111));
        detailLicenseeClasses.add(buildDetailLicenseeClass(113, 113));
        String actualJson = jsonMapper.serialize(aaclFields);
        assertEquals(aaclFields, jsonMapper.deserialize(actualJson));
    }

    @Test
    public void testDeserializeNull() throws IOException {
        assertNull(jsonMapper.deserialize(null));
    }

    @Test
    public void testSerializeNull() throws IOException {
        assertNull(jsonMapper.serialize(null));
    }

    @Test(expected = JsonParseException.class)
    public void testDeserializeException() throws IOException {
        jsonMapper.deserialize("{wrong JSON}");
    }

    private void verifyLicenseeClassMapping(AaclFields aaclFields) {
        List<DetailLicenseeClass> detailLicenseeClasses = aaclFields.getDetailLicenseeClasses();
        assertEquals(36, CollectionUtils.size(detailLicenseeClasses));
        assertEquals(buildDetailLicenseeClass(108, 110), detailLicenseeClasses.get(0));
        assertEquals(buildDetailLicenseeClass(110, 108), detailLicenseeClasses.get(1));
        assertEquals(buildDetailLicenseeClass(111, 111), detailLicenseeClasses.get(2));
        assertEquals(buildDetailLicenseeClass(113, 113), detailLicenseeClasses.get(3));
        assertEquals(buildDetailLicenseeClass(115, 115), detailLicenseeClasses.get(4));
        assertEquals(buildDetailLicenseeClass(117, 117), detailLicenseeClasses.get(5));
        assertEquals(buildDetailLicenseeClass(118, 118), detailLicenseeClasses.get(6));
        assertEquals(buildDetailLicenseeClass(120, 120), detailLicenseeClasses.get(7));
        assertEquals(buildDetailLicenseeClass(136, 136), detailLicenseeClasses.get(8));
        assertEquals(buildDetailLicenseeClass(138, 138), detailLicenseeClasses.get(9));
        assertEquals(buildDetailLicenseeClass(139, 139), detailLicenseeClasses.get(10));
        assertEquals(buildDetailLicenseeClass(141, 141), detailLicenseeClasses.get(11));
        assertEquals(buildDetailLicenseeClass(143, 143), detailLicenseeClasses.get(12));
        assertEquals(buildDetailLicenseeClass(145, 145), detailLicenseeClasses.get(13));
        assertEquals(buildDetailLicenseeClass(146, 146), detailLicenseeClasses.get(14));
        assertEquals(buildDetailLicenseeClass(148, 148), detailLicenseeClasses.get(15));
        assertEquals(buildDetailLicenseeClass(164, 164), detailLicenseeClasses.get(16));
        assertEquals(buildDetailLicenseeClass(166, 166), detailLicenseeClasses.get(17));
        assertEquals(buildDetailLicenseeClass(167, 167), detailLicenseeClasses.get(18));
        assertEquals(buildDetailLicenseeClass(169, 169), detailLicenseeClasses.get(19));
        assertEquals(buildDetailLicenseeClass(171, 171), detailLicenseeClasses.get(20));
        assertEquals(buildDetailLicenseeClass(173, 173), detailLicenseeClasses.get(21));
        assertEquals(buildDetailLicenseeClass(174, 174), detailLicenseeClasses.get(22));
        assertEquals(buildDetailLicenseeClass(176, 176), detailLicenseeClasses.get(23));
        assertEquals(buildDetailLicenseeClass(192, 192), detailLicenseeClasses.get(24));
        assertEquals(buildDetailLicenseeClass(194, 194), detailLicenseeClasses.get(25));
        assertEquals(buildDetailLicenseeClass(195, 195), detailLicenseeClasses.get(26));
        assertEquals(buildDetailLicenseeClass(197, 197), detailLicenseeClasses.get(27));
        assertEquals(buildDetailLicenseeClass(206, 206), detailLicenseeClasses.get(28));
        assertEquals(buildDetailLicenseeClass(208, 208), detailLicenseeClasses.get(29));
        assertEquals(buildDetailLicenseeClass(209, 209), detailLicenseeClasses.get(30));
        assertEquals(buildDetailLicenseeClass(211, 211), detailLicenseeClasses.get(31));
        assertEquals(buildDetailLicenseeClass(227, 227), detailLicenseeClasses.get(32));
        assertEquals(buildDetailLicenseeClass(229, 229), detailLicenseeClasses.get(33));
        assertEquals(buildDetailLicenseeClass(230, 230), detailLicenseeClasses.get(34));
        assertEquals(buildDetailLicenseeClass(232, 232), detailLicenseeClasses.get(35));
    }

    private PublicationType buildPublicationType(String id, String weight) {
        PublicationType pubType = new PublicationType();
        pubType.setId(id);
        pubType.setWeight(new BigDecimal(weight));
        return pubType;
    }

    private UsageAge buildUsageAge(int period, String weight) {
        UsageAge usageAge = new UsageAge();
        usageAge.setPeriod(period);
        usageAge.setWeight(new BigDecimal(weight));
        return usageAge;
    }

    private DetailLicenseeClass buildDetailLicenseeClass(int detailId, int aggregateId) {
        DetailLicenseeClass detailLicenseeClass = new DetailLicenseeClass();
        detailLicenseeClass.setId(detailId);
        detailLicenseeClass.getAggregateLicenseeClass().setId(aggregateId);
        return detailLicenseeClass;
    }
}
