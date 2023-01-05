package com.copyright.rup.dist.foreign.service.impl.acl.calculation.fundpool;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.LdmtDetail;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * Verifies {@link LdmtDetailsDeserializer}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 05/11/2022
 *
 * @author Aliaksandr Liakh
 */
public class LdmtDetailsDeserializerTest {

    private final LdmtDetailsDeserializer deserializer = new LdmtDetailsDeserializer();

    @Test
    public void testDeserialize() throws IOException {
        JsonParser parser = new JsonFactory().createParser(TestUtils.fileToString(this.getClass(), "ldmt_detail.json"));
        parser.setCodec(new ObjectMapper());
        assertEquals(buildLdmtDetail(), deserializer.deserialize(parser, null));
    }

    private List<LdmtDetail> buildLdmtDetail() {
        LdmtDetail ldmtDetail = new LdmtDetail();
        ldmtDetail.setDetailLicenseeClassId(1);
        ldmtDetail.setLicenseType("ACL");
        ldmtDetail.setTypeOfUse("PRINT");
        ldmtDetail.setGrossAmount(new BigDecimal("634420.48"));
        ldmtDetail.setNetAmount(new BigDecimal("450799.88"));
        return List.of(ldmtDetail);
    }
}
