package com.copyright.rup.dist.foreign.service.impl.tax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.impl.common.CommonUsageUnmarshaller;
import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Verifies unmarshaller for rh tax functionality.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 06/09/17
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml"})
public class RhTaxUnmarshallerTest {

    @Autowired
    @Qualifier("df.service.rhTaxUnmarshaller")
    private CommonUsageUnmarshaller unmarshaller;

    @Test
    public void testDeserialize() {
        try (InputStream inputStream =
                 RhTaxUnmarshallerTest.class.getResourceAsStream("rh_tax_message.json")) {
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
