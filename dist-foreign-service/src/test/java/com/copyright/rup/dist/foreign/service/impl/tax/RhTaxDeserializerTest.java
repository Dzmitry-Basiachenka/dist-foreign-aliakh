package com.copyright.rup.dist.foreign.service.impl.tax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.copyright.rup.dist.foreign.domain.Usage;
import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Verifies {@link RhTaxMarshaller}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 06/09/17
 *
 * @author Uladzislau Shalamitski
 */
public class RhTaxDeserializerTest {

    private RhTaxUnmarshaller unmarshaller;

    @Before
    public void setUp() {
        unmarshaller = new RhTaxUnmarshaller();
    }

    @Test
    public void testDeserialize() {
        try (InputStream inputStream =
                 RhTaxDeserializerTest.class.getResourceAsStream("rh_tax_message.json")) {
            String jsonString = CharStreams.toString(new InputStreamReader(inputStream, Charsets.UTF_8));
            Object message =
                unmarshaller.unmarshal(null, new ByteArrayInputStream(jsonString.getBytes(Charsets.UTF_8)));
            assertTrue(message instanceof Usage);
            assertEquals(buildUsage(), message);
        } catch (IOException e) {
            fail();
        }
    }

    private Usage buildUsage() {
        Usage usage = new Usage();
        usage.setId("ac00c194-5363-463a-a718-ff02643aebf3");
        usage.getRightsholder().setAccountNumber(1000009522L);
        return usage;
    }
}
