package com.copyright.rup.dist.foreign.service.impl.common;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * Verifies {@link CommonUdmUsageDeserializer}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 05/20/21
 *
 * @author Uladzislau Shalamitski
 */
public class CommonUdmUsageDeserializerTest {

    private final CommonUdmUsageDeserializer deserializer = new CommonUdmUsageDeserializer();

    @Test
    public void testDeserializeMessage() throws Exception {
        JsonParser parser = new JsonFactory().createParser(
            TestUtils.fileToString(this.getClass(), "udm_usage_message.json"));
        parser.setCodec(new ObjectMapper());
        assertEquals(buildUdmUsages(), deserializer.deserialize(parser, null));
    }

    private List<UdmUsage> buildUdmUsages() {
        return Arrays.asList(
            buildUdmUsage("acd033da-cba1-4e85-adc9-0bcd00687d9d", 122825347L, "0927-7765", "Colloids and surfaces"),
            buildUdmUsage("955d8192-c80d-4be1-963e-12616f8e9c41", 100002555L, "2227-7589", "Brain surgery"));
    }

    private UdmUsage buildUdmUsage(String udmUsageId, long wrWrkInst, String reportedStandardNumber,
                                   String reportedTitle) {
        UdmUsage udmUsage = new UdmUsage();
        udmUsage.setId(udmUsageId);
        udmUsage.setStatus(UsageStatusEnum.WORK_FOUND);
        udmUsage.setPeriodEndDate(LocalDate.of(2021, 6, 30));
        udmUsage.setWrWrkInst(wrWrkInst);
        udmUsage.setReportedStandardNumber(reportedStandardNumber);
        udmUsage.setReportedTitle(reportedTitle);
        udmUsage.setTypeOfUse("PRINT");
        udmUsage.setVersion(3);
        return udmUsage;
    }
}
