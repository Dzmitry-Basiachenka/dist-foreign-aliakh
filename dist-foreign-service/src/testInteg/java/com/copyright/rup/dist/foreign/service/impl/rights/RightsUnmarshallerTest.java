package com.copyright.rup.dist.foreign.service.impl.rights;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
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
import java.math.BigDecimal;

/**
 * Verifies unmarshaller rights functionality.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 06/09/17
 *
 * @author Ihar Suvorau
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml"})
public class RightsUnmarshallerTest {

    @Autowired
    @Qualifier("df.service.rightsUnmarshaller")
    private CommonUsageUnmarshaller unmarshaller;

    @Test
    public void testDeserialize() {
        try (InputStream inputStream = RightsUnmarshallerTest.class.getResourceAsStream("rights_message.json")) {
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
        usage.setProductFamily("NTS");
        return usage;
    }
}
