package com.copyright.rup.dist.foreign.service.impl.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.AaclUsage;
import com.copyright.rup.dist.foreign.domain.SalUsage;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link CommonUsageSerializer}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 01/17/19
 *
 * @author Uladzislau Shalamitski
 * @author Aliaksandr Liakh
 */
public class CommonUsageSerializerTest {

    private CommonUsageSerializer serializer;

    @Before
    public void setUp() {
        serializer = new CommonUsageSerializer();
    }

    @Test
    public void testSerializeMessageFas() throws Exception {
        StringWriter stringWriter = new StringWriter();
        JsonGenerator jsonGenerator = new JsonFactory().createGenerator(stringWriter);
        jsonGenerator.setPrettyPrinter(new DefaultPrettyPrinter());
        serializer.serialize(buildFasUsages(), jsonGenerator, new DefaultSerializerProvider.Impl());
        jsonGenerator.close();
        assertNotNull(stringWriter);
        assertEquals(StringUtils.strip(TestUtils.fileToString(this.getClass(), "usage_message_fas.json")),
            stringWriter.toString());
    }

    @Test
    public void testSerializeMessageAacl() throws Exception {
        StringWriter stringWriter = new StringWriter();
        JsonGenerator jsonGenerator = new JsonFactory().createGenerator(stringWriter);
        jsonGenerator.setPrettyPrinter(new DefaultPrettyPrinter());
        serializer.serialize(buildAaclUsages(), jsonGenerator, new DefaultSerializerProvider.Impl());
        jsonGenerator.close();
        assertNotNull(stringWriter);
        assertEquals(StringUtils.strip(TestUtils.fileToString(this.getClass(), "usage_message_aacl.json")),
            stringWriter.toString());
    }

    @Test
    public void testSerializeMessageSal() throws Exception {
        StringWriter stringWriter = new StringWriter();
        JsonGenerator jsonGenerator = new JsonFactory().createGenerator(stringWriter);
        jsonGenerator.setPrettyPrinter(new DefaultPrettyPrinter());
        serializer.serialize(buildSalUsages(), jsonGenerator, new DefaultSerializerProvider.Impl());
        jsonGenerator.close();
        assertNotNull(stringWriter);
        assertEquals(StringUtils.strip(TestUtils.fileToString(this.getClass(), "usage_message_sal.json")),
            stringWriter.toString());
    }

    @Test
    public void testSerializeEmptyMessage() throws Exception {
        StringWriter stringWriter = new StringWriter();
        JsonGenerator jsonGenerator = new JsonFactory().createGenerator(stringWriter);
        serializer.serialize(null, jsonGenerator, new DefaultSerializerProvider.Impl());
        jsonGenerator.close();
        assertNotNull(stringWriter);
        assertEquals("{\"usages\":[]}", stringWriter.toString());
    }

    private List<Usage> buildFasUsages() {
        Usage usage = buildUsage();
        usage.setProductFamily("FAS");
        return Collections.singletonList(usage);
    }

    private List<Usage> buildAaclUsages() {
        Usage usage = buildUsage();
        usage.setProductFamily("AACL");
        usage.setAaclUsage(new AaclUsage());
        usage.getAaclUsage().setBatchPeriodEndDate(LocalDate.of(2019, 6, 30));
        usage.getAaclUsage().setBaselineId("e98d77bf-af0a-4c40-a46a-f211607e239f");
        return Collections.singletonList(usage);
    }

    private List<Usage> buildSalUsages() {
        Usage usage = buildUsage();
        usage.setProductFamily("SAL");
        usage.setSalUsage(new SalUsage());
        usage.getSalUsage().setBatchPeriodEndDate(LocalDate.of(2019, 6, 30));
        return Collections.singletonList(usage);
    }

    private Usage buildUsage() {
        Usage usage = new Usage();
        usage.setId("ac00c194-5363-463a-a718-ff02643aebf3");
        usage.setBatchId("5da597e4-f418-4b70-b43a-7990e82e6367");
        usage.setWrWrkInst(100010768L);
        usage.setGrossAmount(new BigDecimal("50.00"));
        usage.setStandardNumber("12345XX-190048");
        usage.setStandardNumberType("STDID");
        usage.setWorkTitle("True directions : living your sacred instructions");
        usage.setSystemTitle("True directions : living your sacred instructions");
        usage.setStatus(UsageStatusEnum.WORK_FOUND);
        usage.setRightsholder(buildRightsholder());
        usage.setVersion(5);
        return usage;
    }

    private Rightsholder buildRightsholder() {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setId("4c4d1354-bff2-4f70-987b-f996bade22ba");
        rightsholder.setAccountNumber(2000017005L);
        return rightsholder;
    }
}
