package com.copyright.rup.dist.foreign.integration.lm.impl.serializer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import com.copyright.rup.dist.foreign.domain.LiabilityDetail;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.google.common.collect.Lists;
import com.google.common.io.Resources;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

/**
 * Verifies {@link LiabilityDetailSerializer}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/11/18
 *
 * @author Ihar Suvorau
 */
public class LiabilityDetailSerializerTest {

    private LiabilityDetailSerializer liabilityDetailSerializer;

    @Before
    public void setUp() throws Exception {
        liabilityDetailSerializer = new LiabilityDetailSerializer();
    }

    @Test
    public void testSerializeMessage() throws Exception {
        StringWriter stringWriter = new StringWriter();
        JsonGenerator jsonGenerator = new JsonFactory().createGenerator(stringWriter);
        jsonGenerator.setPrettyPrinter(new DefaultPrettyPrinter());
        liabilityDetailSerializer.serialize(
            Lists.newArrayList(buildLiabilityDetail(10000001L), buildLiabilityDetail(10000002L)), jsonGenerator,
            new DefaultSerializerProvider.Impl());
        jsonGenerator.close();
        assertNotNull(stringWriter);
        assertEquals(getFileAsString("liability_detail_message.json"), stringWriter.toString());
    }

    @Test
    public void testSerializeEmptyMessage() throws Exception {
        StringWriter stringWriter = new StringWriter();
        JsonGenerator jsonGenerator = new JsonFactory().createGenerator(stringWriter);
        liabilityDetailSerializer.serialize(Collections.emptyList(), jsonGenerator,
            new DefaultSerializerProvider.Impl());
        jsonGenerator.close();
        assertNotNull(stringWriter);
        assertEquals("{\"details\":[]}", stringWriter.toString());
    }

    private String getFileAsString(String resourceName) {
        String result = null;
        try {
            result = Resources.toString(LiabilityDetailSerializerTest.class.getResource(resourceName),
                StandardCharsets.UTF_8);
        } catch (IOException e) {
            fail();
        }
        return StringUtils.strip(result);
    }

    private LiabilityDetail buildLiabilityDetail(Long detailId) {
        LiabilityDetail liabilityDetail = new LiabilityDetail();
        liabilityDetail.setRhAccountNumber(1000010023L);
        liabilityDetail.setDetailId(detailId);
        liabilityDetail.setProductFamily("FAS");
        liabilityDetail.setRoyaltyAmount(new BigDecimal("100.00"));
        liabilityDetail.setWorkTitle("Work title");
        liabilityDetail.setWrWrkInst(123456789L);
        return liabilityDetail;
    }
}
