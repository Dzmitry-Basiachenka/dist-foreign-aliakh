package com.copyright.rup.dist.foreign.service.impl.common;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.AaclUsage;
import com.copyright.rup.dist.foreign.domain.SalUsage;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link CommonUsageChunkSerializer}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 01/17/19
 *
 * @author Uladzislau Shalamitski
 * @author Aliaksandr Liakh
 */
public class CommonUsageChunkDeserializerTest {

    private CommonUsageChunkDeserializer deserializer;

    @Before
    public void setUp() {
        deserializer = new CommonUsageChunkDeserializer();
    }

    @Test
    public void testDeserializeMessageFas() throws Exception {
        JsonParser parser = new JsonFactory().createParser(
            TestUtils.fileToString(this.getClass(), "usage_message_fas.json"));
        parser.setCodec(new ObjectMapper());
        assertEquals(buildFasUsages(), deserializer.deserialize(parser, null));
    }

    @Test
    public void testDeserializeMessageAacl() throws Exception {
        JsonParser parser = new JsonFactory().createParser(
            TestUtils.fileToString(this.getClass(), "usage_message_aacl.json"));
        parser.setCodec(new ObjectMapper());
        assertEquals(buildAaclUsages(), deserializer.deserialize(parser, null));
    }

    @Test
    public void testDeserializeMessageSal() throws Exception {
        JsonParser parser = new JsonFactory().createParser(
            TestUtils.fileToString(this.getClass(), "usage_message_sal.json"));
        parser.setCodec(new ObjectMapper());
        assertEquals(buildSalUsages(), deserializer.deserialize(parser, null));
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
