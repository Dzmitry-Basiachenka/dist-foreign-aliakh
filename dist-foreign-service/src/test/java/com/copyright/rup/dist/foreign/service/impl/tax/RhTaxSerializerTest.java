package com.copyright.rup.dist.foreign.service.impl.tax;

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
 * Verifies {@link RhTaxSerializer}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 06/09/18
 *
 * @author Uladzislau Shalamitski
 */
public class RhTaxSerializerTest {

    private RhTaxSerializer eligibilitySerializer;

    @Before
    public void setUp() {
        eligibilitySerializer = new RhTaxSerializer();
    }

    @Test
    public void testSerializeMessage() throws Exception {
        StringWriter stringWriter = new StringWriter();
        JsonGenerator jsonGenerator = new JsonFactory().createGenerator(stringWriter);
        jsonGenerator.setPrettyPrinter(new DefaultPrettyPrinter());
        eligibilitySerializer.serialize(buildUsage(), jsonGenerator, new DefaultSerializerProvider.Impl());
        jsonGenerator.close();
        assertNotNull(stringWriter);
        assertEquals(
            StringUtils.strip(TestUtils.fileToString(this.getClass(), "rh_tax_message.json")), stringWriter.toString());
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
        usage.getRightsholder().setAccountNumber(1000009522L);
        usage.getRightsholder().setId("1e5ad7fa-15c1-4216-9f44-a6e8559d9f90");
        return usage;
    }
}
