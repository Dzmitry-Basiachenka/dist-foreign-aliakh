package com.copyright.rup.dist.foreign.integration.lm.impl.serializer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.AclScenarioLiabilityDetail;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.integration.lm.api.domain.ExternalUsage;
import com.copyright.rup.dist.foreign.integration.lm.api.domain.ExternalUsageWrapper;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.google.common.io.Resources;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;

/**
 * Verifies {@link ExternalUsageSerializer}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/11/18
 *
 * @author Ihar Suvorau
 */
public class ExternalUsageSerializerTest {

    private ExternalUsageSerializer externalUsageSerializer;

    @Before
    public void setUp() {
        externalUsageSerializer = new ExternalUsageSerializer();
    }

    @Test
    public void testSerializeMessage() throws Exception {
        StringWriter stringWriter = new StringWriter();
        JsonGenerator jsonGenerator = new JsonFactory().createGenerator(stringWriter);
        jsonGenerator.setPrettyPrinter(new DefaultPrettyPrinter());
        externalUsageSerializer.serialize(
            new ExternalUsageWrapper(Arrays.asList(
                buildExternalUsage("5bd86764-d57b-443f-9891-adb57adb22fb"),
                buildExternalUsage("59e217bf-8704-43a7-8272-9ca486ba21e3"))),
            jsonGenerator, new DefaultSerializerProvider.Impl());
        jsonGenerator.close();
        assertNotNull(stringWriter);
        assertEquals(getFileAsString("external_usage_message.json"), stringWriter.toString());
    }

    @Test
    public void testSerializeMessageForAclProductFamily() throws Exception {
        StringWriter stringWriter = new StringWriter();
        JsonGenerator jsonGenerator = new JsonFactory().createGenerator(stringWriter);
        jsonGenerator.setPrettyPrinter(new DefaultPrettyPrinter());
        externalUsageSerializer.serialize(
            new ExternalUsageWrapper(Arrays.asList(
                buildExternalUsage("bb5de01e-3375-4226-b484-d5f2c6f109c4", "PRINT"),
                buildExternalUsage("53b7f67e-d891-415d-82da-029f21b45802", "DIGITAL"))),
            jsonGenerator, new DefaultSerializerProvider.Impl());
        jsonGenerator.close();
        assertNotNull(stringWriter);
        assertEquals(getFileAsString("external_liability_detail_message.json"), stringWriter.toString());
    }

    @Test
    public void testSerializeEmptyMessage() throws Exception {
        StringWriter stringWriter = new StringWriter();
        JsonGenerator jsonGenerator = new JsonFactory().createGenerator(stringWriter);
        externalUsageSerializer.serialize(new ExternalUsageWrapper(Collections.emptyList()), jsonGenerator,
            new DefaultSerializerProvider.Impl());
        jsonGenerator.close();
        assertNotNull(stringWriter);
        assertEquals("{\"details\":[]}", stringWriter.toString());
    }

    private String getFileAsString(String fileName) {
        String result = null;
        try {
            result = Resources.toString(ExternalUsageSerializerTest.class.getResource(fileName),
                StandardCharsets.UTF_8);
        } catch (IOException e) {
            fail();
        }
        return StringUtils.strip(result);
    }

    private ExternalUsage buildExternalUsage(String usageId) {
        Usage usage = new Usage();
        usage.setId(usageId);
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setId("07529566-6ce4-11e9-a923-1681be663d3e");
        usage.setRightsholder(rightsholder);
        usage.setNetAmount(new BigDecimal("100.00"));
        usage.setServiceFeeAmount(new BigDecimal("20.00"));
        usage.setGrossAmount(new BigDecimal("120.00"));
        usage.setSystemTitle("System title");
        usage.setWrWrkInst(123456789L);
        usage.setProductFamily("FAS");
        return new ExternalUsage(usage);
    }

    private ExternalUsage buildExternalUsage(String usageId, String typeOfUse) {
        AclScenarioLiabilityDetail liabilityDetail = new AclScenarioLiabilityDetail();
        liabilityDetail.setLiabilityDetailId(usageId);
        liabilityDetail.setRhAccountNumber("07529566-6ce4-11e9-a923-1681be663d3e");
        liabilityDetail.setNetAmount(new BigDecimal("100.00"));
        liabilityDetail.setServiceFeeAmount(new BigDecimal("20.00"));
        liabilityDetail.setGrossAmount(new BigDecimal("120.00"));
        liabilityDetail.setSystemTitle("System title");
        liabilityDetail.setWrWrkInst(123456789L);
        liabilityDetail.setProductFamily("ACL" + typeOfUse);
        liabilityDetail.setTypeOfUse(typeOfUse);
        liabilityDetail.setLicenseType("ACL");
        liabilityDetail.setAggregateLicenseeClassName("Materials");
        return new ExternalUsage(liabilityDetail);
    }
}
