package com.copyright.rup.dist.foreign.integration.lm.impl.serializer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.copyright.rup.dist.foreign.domain.PaidUsage;
import com.copyright.rup.dist.foreign.integration.lm.impl.marshaller.PaidUsageUnmarshaller;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.List;

/**
 * Verifies {@link PaidUsageDeserializer}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 02/21/18
 *
 * @author Darya Baraukova
 */
public class PaidUsageDeserializerTest {

    private PaidUsageUnmarshaller unmarshaller = new PaidUsageUnmarshaller();

    @Test
    @SuppressWarnings("unchecked")
    public void testDeserialize() throws Exception {
        try (InputStream inputStream = PaidUsageDeserializerTest.class.getResourceAsStream(
            "paid_usages_message.json")) {
            String jsonString = CharStreams.toString(new InputStreamReader(inputStream, Charsets.UTF_8));
            Object paidUsageMessage =
                unmarshaller.unmarshal(null, new ByteArrayInputStream(jsonString.getBytes(Charsets.UTF_8)));
            assertTrue(paidUsageMessage instanceof List);
            List<PaidUsage> usages = (List<PaidUsage>) paidUsageMessage;
            assertTrue(CollectionUtils.isNotEmpty(usages));
            assertEquals(1, usages.size());
            PaidUsage usage = usages.get(0);
            assertEquals(69977681, usage.getDetailId(), 0);
            assertEquals(1000002859, usage.getPayee().getAccountNumber(), 0);
            assertEquals("578945", usage.getCheckNumber());
            assertEquals(LocalDate.of(2017, 1, 15), usage.getCheckDate());
            assertEquals("53256", usage.getCccEventId());
            assertEquals("FDA March 17", usage.getDistributionName());
            assertEquals(LocalDate.of(2017, 1, 14), usage.getDistributionDate());
            assertEquals(LocalDate.of(2017, 1, 16), usage.getPeriodEndDate());
        } catch (IOException e) {
            fail();
        }
    }
}
