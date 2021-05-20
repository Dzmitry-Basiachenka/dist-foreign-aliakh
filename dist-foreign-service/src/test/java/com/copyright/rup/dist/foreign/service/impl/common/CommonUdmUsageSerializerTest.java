package com.copyright.rup.dist.foreign.service.impl.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * Verifies {@link CommonUdmUsageSerializer}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 05/20/21
 *
 * @author Uladzislau Shalamitski
 */
public class CommonUdmUsageSerializerTest {

    private final CommonUdmUsageSerializer serializer = new CommonUdmUsageSerializer();

    @Test
    public void testSerializeMessage() throws Exception {
        StringWriter stringWriter = new StringWriter();
        JsonGenerator jsonGenerator = new JsonFactory().createGenerator(stringWriter);
        jsonGenerator.setPrettyPrinter(new DefaultPrettyPrinter());
        serializer.serialize(buildUdmUsages(), jsonGenerator, new DefaultSerializerProvider.Impl());
        jsonGenerator.close();
        assertNotNull(stringWriter);
        assertEquals(StringUtils.strip(TestUtils.fileToString(this.getClass(), "udm_usage_message.json")),
            stringWriter.toString());
    }

    @Test
    public void testSerializeEmptyMessage() throws Exception {
        StringWriter stringWriter = new StringWriter();
        JsonGenerator jsonGenerator = new JsonFactory().createGenerator(stringWriter);
        serializer.serialize(null, jsonGenerator, new DefaultSerializerProvider.Impl());
        jsonGenerator.close();
        assertNotNull(stringWriter);
        assertEquals("{\"udmUsages\":[]}", stringWriter.toString());
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
        udmUsage.setBatchId("aa5751aa-2858-38c6-b0d9-51ec0edfcf4f");
        udmUsage.setOriginalDetailId("OGN674GHHSB0025");
        udmUsage.setStatus(UsageStatusEnum.WORK_FOUND);
        udmUsage.setPeriodEndDate(LocalDate.of(2021, 6, 30));
        udmUsage.setUsageDate(LocalDate.of(2021, 5, 10));
        udmUsage.setWrWrkInst(wrWrkInst);
        udmUsage.setStandardNumber("0927-7761");
        udmUsage.setReportedStandardNumber(reportedStandardNumber);
        udmUsage.setSystemTitle("Colloids and surfaces. C, Biointerfaces");
        udmUsage.setReportedTitle(reportedTitle);
        udmUsage.setReportedTypeOfUse("COPY_FOR_MYSELF");
        udmUsage.setTypeOfUse("PRINT");
        udmUsage.setReportedPubType("Journal");
        udmUsage.setPubFormat("Format");
        udmUsage.setArticle("Green chemistry");
        udmUsage.setLanguage("English");
        udmUsage.setCompanyId(77000456L);
        udmUsage.setSurveyRespondent("fa0276c3-55d6-42cd-8ffe-e9124acae02f");
        udmUsage.setIpAddress("ip24.12.119.203");
        udmUsage.setSurveyCountry("United States");
        udmUsage.setSurveyStartDate(LocalDate.of(2021, 5, 10));
        udmUsage.setSurveyEndDate(LocalDate.of(2021, 5, 20));
        udmUsage.setStatisticalMultiplier(BigDecimal.ONE);
        udmUsage.setAnnualMultiplier(25);
        udmUsage.setAnnualizedCopies(new BigDecimal("300.00000"));
        udmUsage.setQuantity(50);
        udmUsage.setVersion(3);
        return udmUsage;
    }
}
