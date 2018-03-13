package com.copyright.rup.dist.foreign.service.impl.serializer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.copyright.rup.dist.foreign.domain.PaidUsage;
import com.copyright.rup.dist.foreign.service.impl.marshaller.PaidUsageUnmarshaller;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
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

    private final PaidUsageUnmarshaller unmarshaller = new PaidUsageUnmarshaller();

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
            assertEquals(OffsetDateTime.of(2017, 1, 15, 5, 0, 0, 0, ZoneOffset.ofHours(0)).toInstant(),
                usage.getCheckDate().toInstant());
            assertEquals("53256", usage.getCccEventId());
            assertEquals("FDA March 17", usage.getDistributionName());
            assertEquals(OffsetDateTime.of(2017, 1, 14, 5, 0, 0, 0, ZoneOffset.ofHours(0)).toInstant(),
                usage.getDistributionDate().toInstant());
            assertEquals(OffsetDateTime.of(2017, 1, 16, 5, 0, 0, 0, ZoneOffset.ofHours(0)).toInstant(),
                usage.getPeriodEndDate().toInstant());
        } catch (IOException e) {
            fail();
        }
    }
}
