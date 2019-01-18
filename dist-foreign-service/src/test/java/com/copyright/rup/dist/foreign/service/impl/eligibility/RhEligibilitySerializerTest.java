package com.copyright.rup.dist.foreign.service.impl.eligibility;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.Usage;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.StringWriter;

/**
 * Verifies {@link RhEligibilitySerializer}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 01/10/2019
 *
 * @author Uladzislau Shalamitski
 */
public class RhEligibilitySerializerTest {

    private RhEligibilitySerializer eligibilitySerializer;

    @Before
    public void setUp() {
        eligibilitySerializer = new RhEligibilitySerializer();
    }

    @Test
    public void testSerializeMessage() throws Exception {
        StringWriter stringWriter = new StringWriter();
        JsonGenerator jsonGenerator = new JsonFactory().createGenerator(stringWriter);
        jsonGenerator.setPrettyPrinter(new DefaultPrettyPrinter());
        eligibilitySerializer.serialize(buildUsage(), jsonGenerator, new DefaultSerializerProvider.Impl());
        jsonGenerator.close();
        assertNotNull(stringWriter);
        assertEquals(StringUtils.strip(TestUtils.fileToString(this.getClass(), "rh_eligibility_message.json")),
            stringWriter.toString());
    }

    @Test
    public void testSerializeEmptyMessage() throws Exception {
        StringWriter stringWriter = new StringWriter();
        JsonGenerator jsonGenerator = new JsonFactory().createGenerator(stringWriter);
        eligibilitySerializer.serialize(null, jsonGenerator, new DefaultSerializerProvider.Impl());
        jsonGenerator.close();
        assertNotNull(stringWriter);
        assertEquals("{}", stringWriter.toString());
    }

    private Usage buildUsage() {
        Usage usage = new Usage();
        usage.setId("ac00c194-5363-463a-a718-ff02643aebf3");
        usage.getRightsholder().setId("1e5ad7fa-15c1-4216-9f44-a6e8559d9f90");
        return usage;
    }
}
