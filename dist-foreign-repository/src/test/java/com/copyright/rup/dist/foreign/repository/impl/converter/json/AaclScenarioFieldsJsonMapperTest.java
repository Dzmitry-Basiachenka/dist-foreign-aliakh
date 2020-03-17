package com.copyright.rup.dist.foreign.repository.impl.converter.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.copyright.rup.dist.common.test.TestUtils;
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
        assertEquals(new BigDecimal("12.34"), aaclFields.getTitleCutoffAmount());
        assertEquals("8ab155cb-c96d-4355-9f37-12d26ef4d765", aaclFields.getFundPoolId());
        List<PublicationType> pubTypes = aaclFields.getPublicationTypes();
        assertNotNull(pubTypes);
        assertEquals(5, pubTypes.size());
        assertEquals(buildPublicationType("Book", ONE), pubTypes.get(0));
        assertEquals(buildPublicationType("Business or Trade Journal", "1.50"), pubTypes.get(1));
        assertEquals(buildPublicationType("Consumer Magazine", ONE), pubTypes.get(2));
        assertEquals(buildPublicationType("News Source", "4.00"), pubTypes.get(3));
        assertEquals(buildPublicationType("STMA Journal", "1.10"), pubTypes.get(4));
        List<UsageAge> usageAges = aaclFields.getUsageAges();
        assertEquals(5, CollectionUtils.size(usageAges));
        assertEquals(buildUsageAge(2020, ONE), usageAges.get(0));
        assertEquals(buildUsageAge(2019, "0.75"), usageAges.get(1));
        assertEquals(buildUsageAge(2017, "0.50"), usageAges.get(2));
        assertEquals(buildUsageAge(2015, "0.25"), usageAges.get(3));
        assertEquals(buildUsageAge(2014, "0.00"), usageAges.get(4));
    }

    @Test
    public void testSerialize() throws IOException {
        AaclFields aaclFields = new AaclFields();
        aaclFields.setTitleCutoffAmount(new BigDecimal("12.34"));
        aaclFields.setFundPoolId("8ab155cb-c96d-4355-9f37-12d26ef4d765");
        List<PublicationType> pubTypes = aaclFields.getPublicationTypes();
        aaclFields.setPublicationTypes(pubTypes);
        pubTypes.add(buildPublicationType("Book", ONE));
        pubTypes.add(buildPublicationType("Business or Trade Journal", "1.50"));
        pubTypes.add(buildPublicationType("Consumer Magazine", ONE));
        pubTypes.add(buildPublicationType("News Source", "4.00"));
        pubTypes.add(buildPublicationType("STMA Journal", "1.10"));
        List<UsageAge> usageAges = aaclFields.getUsageAges();
        usageAges.add(buildUsageAge(2020, ONE));
        usageAges.add(buildUsageAge(2019, "0.75"));
        usageAges.add(buildUsageAge(2017, "0.50"));
        usageAges.add(buildUsageAge(2015, "0.25"));
        usageAges.add(buildUsageAge(2014, "0.00"));
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

    private PublicationType buildPublicationType(String name, String weight) {
        PublicationType pubType = new PublicationType();
        pubType.setName(name);
        pubType.setWeight(new BigDecimal(weight));
        return pubType;
    }

    private UsageAge buildUsageAge(int period, String weight) {
        UsageAge usageAge = new UsageAge();
        usageAge.setPeriod(period);
        usageAge.setWeight(new BigDecimal(weight));
        return usageAge;
    }
}
