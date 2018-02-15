package com.copyright.rup.dist.foreign.integration.lm.impl.serializer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.integration.lm.api.domain.ExternalUsage;
import com.copyright.rup.dist.foreign.integration.lm.api.domain.ExternalUsageWrapper;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.google.common.collect.Lists;
import com.google.common.io.Resources;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
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
    public void setUp() throws Exception {
        externalUsageSerializer = new ExternalUsageSerializer();
    }

    @Test
    public void testSerializeMessage() throws Exception {
        StringWriter stringWriter = new StringWriter();
        JsonGenerator jsonGenerator = new JsonFactory().createGenerator(stringWriter);
        jsonGenerator.setPrettyPrinter(new DefaultPrettyPrinter());
        externalUsageSerializer.serialize(
            new ExternalUsageWrapper(Lists.newArrayList(buildExternalUsage(10000001L), buildExternalUsage(10000002L))),
            jsonGenerator, new DefaultSerializerProvider.Impl());
        jsonGenerator.close();
        assertNotNull(stringWriter);
        assertEquals(getFileAsString("external_usage_message.json"), stringWriter.toString());
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

    private String getFileAsString(String resourceName) {
        String result = null;
        try {
            result = Resources.toString(ExternalUsageSerializerTest.class.getResource(resourceName),
                StandardCharsets.UTF_8);
        } catch (IOException e) {
            fail();
        }
        return StringUtils.strip(result);
    }

    private ExternalUsage buildExternalUsage(Long detailId) {
        Usage usage = new Usage();
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(1000010023L);
        usage.setRightsholder(rightsholder);
        usage.setDetailId(detailId);
        usage.setNetAmount(new BigDecimal("100.00"));
        usage.setWorkTitle("Work title");
        usage.setWrWrkInst(123456789L);
        usage.setProductFamily("FAS");
        return new ExternalUsage(usage);
    }
}
