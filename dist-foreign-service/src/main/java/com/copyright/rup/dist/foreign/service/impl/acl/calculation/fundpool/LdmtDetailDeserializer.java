package com.copyright.rup.dist.foreign.service.impl.acl.calculation.fundpool;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.integration.util.JsonUtils;
import com.copyright.rup.dist.foreign.domain.LdmtDetail;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Implementation of {@link JsonDeserializer} for list of {@link LdmtDetail}s.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 05/11/2022
 *
 * @author Aliaksandr Liakh
 */
public class LdmtDetailDeserializer extends JsonDeserializer<List<LdmtDetail>> {

    private static final Map<String, String> TYPE_OF_USE_MAPPING = ImmutableMap.of(
        "PHOTOCOPY", "PRINT", "DIGITAL", "DIGITAL");
    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Override
    public List<LdmtDetail> deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        List<LdmtDetail> ldmtDetails = Lists.newArrayList();
        try {
            JsonNode tree = parser.readValueAsTree();
            LOGGER.trace("Deserialize LDMT details. Received JSON={}", tree);
            for (JsonNode jsonNode : tree) {
                ldmtDetails.add(buildLdmtDetail(jsonNode));
            }
        } catch (JsonParseException e) {
            LOGGER.warn("Deserialize LDMT details. Failed", e);
        }
        return ldmtDetails;
    }

    private LdmtDetail buildLdmtDetail(JsonNode jsonNode) {
        LdmtDetail ldmtDetail = new LdmtDetail();
        ldmtDetail.setDetailLicenseeClassId(JsonUtils.getIntegerValue(jsonNode.get("ICODE")));
        ldmtDetail.setLicenseType(JsonUtils.getStringValue(jsonNode.get("LICENCETYPE")));
        ldmtDetail.setTypeOfUse(TYPE_OF_USE_MAPPING.get(JsonUtils.getStringValue(jsonNode.get("TYPEOFUSE"))));
        ldmtDetail.setGrossAmount(JsonUtils.getBigDecimalValue(jsonNode.get("GROSSAMOUNT")));
        ldmtDetail.setNetAmount(JsonUtils.getBigDecimalValue(jsonNode.get("NETAMOUNT")));
        return ldmtDetail;
    }
}
