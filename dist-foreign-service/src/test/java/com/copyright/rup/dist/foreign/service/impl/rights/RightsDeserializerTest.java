package com.copyright.rup.dist.foreign.service.impl.rights;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;

/**
 * Verifies {@link RightsDeserializer}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 06/09/17
 *
 * @author Ihar Suvorau
 */
public class RightsDeserializerTest {

    private RightsUnmarshaller unmarshaller;

    @Before
    public void setUp() {
        unmarshaller = new RightsUnmarshaller();
    }

    @Test
    public void testDeserialize() {
        try (InputStream inputStream = RightsDeserializerTest.class.getResourceAsStream("rights_message.json")) {
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
        usage.setWrWrkInst(100010768L);
        usage.setBatchId("5da597e4-f418-4b70-b43a-7990e82e6367");
        usage.setGrossAmount(new BigDecimal("50.00"));
        usage.setStandardNumber("12345XX-190048");
        usage.setWorkTitle("True directions : living your sacred instructions");
        usage.setSystemTitle("True directions : living your sacred instructions");
        usage.setStatus(UsageStatusEnum.WORK_FOUND);
        return usage;
    }
}
