package com.copyright.rup.dist.foreign.service.impl.matching;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;

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

/**
 * Verifies {@link MatchingSerializer}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 06/09/17
 *
 * @author Ihar Suvorau
 */
public class MatchingSerializerTest {

    private MatchingSerializer matchingSerializer;

    @Before
    public void setUp() {
        matchingSerializer = new MatchingSerializer();
    }

    @Test
    public void testSerializeMessage() throws Exception {
        StringWriter stringWriter = new StringWriter();
        JsonGenerator jsonGenerator = new JsonFactory().createGenerator(stringWriter);
        jsonGenerator.setPrettyPrinter(new DefaultPrettyPrinter());
        matchingSerializer.serialize(buildUsage(), jsonGenerator, new DefaultSerializerProvider.Impl());
        jsonGenerator.close();
        assertNotNull(stringWriter);
        assertEquals(getFileAsString("matching_message.json"), stringWriter.toString());
    }

    @Test
    public void testSerializeEmptyMessage() throws Exception {
        StringWriter stringWriter = new StringWriter();
        JsonGenerator jsonGenerator = new JsonFactory().createGenerator(stringWriter);
        matchingSerializer.serialize(null, jsonGenerator, new DefaultSerializerProvider.Impl());
        jsonGenerator.close();
        assertNotNull(stringWriter);
        assertEquals("{}", stringWriter.toString());
    }

    private String getFileAsString(String resourceName) {
        String result = null;
        try {
            result = Resources.toString(MatchingSerializerTest.class.getResource(resourceName),
                StandardCharsets.UTF_8);
        } catch (IOException e) {
            fail();
        }
        return StringUtils.strip(result);
    }

    private Usage buildUsage() {
        Usage usage = new Usage();
        usage.setId("ac00c194-5363-463a-a718-ff02643aebf3");
        usage.setBatchId("5da597e4-f418-4b70-b43a-7990e82e6367");
        usage.setGrossAmount(new BigDecimal("50.00"));
        usage.setStandardNumber("12345XX-190048");
        usage.setWorkTitle("True directions : living your sacred instructions");
        usage.setStatus(UsageStatusEnum.NEW);
        return usage;
    }
}
