package com.copyright.rup.dist.foreign.service.impl.common;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.integration.util.JsonUtils;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link JsonDeserializer} to parse JSON to list of {@link UdmUsage}s.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 05/20/2021
 *
 * @author Uladzislau Shalamitski
 */
@Component("df.service.commonUdmUsageDeserializer")
public class CommonUdmUsageDeserializer extends JsonDeserializer<List<UdmUsage>> {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Override
    public List<UdmUsage> deserialize(JsonParser parser, DeserializationContext context)
        throws IOException {
        List<UdmUsage> udmUsages = new ArrayList<>();
        try {
            JsonNode tree = parser.readValueAsTree();
            JsonNode usagesNode = tree.get("udmUsages");
            if (usagesNode.isArray()) {
                for (JsonNode usageNode : usagesNode) {
                    udmUsages.add(deserializeUsage(usageNode));
                }
            }
        } catch (JsonParseException e) {
            LOGGER.warn("Deserialize UDM Usage message. Failed", e);
        }
        return udmUsages;
    }

    private UdmUsage deserializeUsage(JsonNode jsonNode) {
        UdmUsage udmUsage = new UdmUsage();
        udmUsage.setId(JsonUtils.getStringValue(jsonNode.get("id")));
        udmUsage.setWrWrkInst(JsonUtils.getLongValue(jsonNode.get("wr_wrk_inst")));
        udmUsage.setTypeOfUse(JsonUtils.getStringValue(jsonNode.get("type_of_use")));
        udmUsage.setReportedStandardNumber(JsonUtils.getStringValue(jsonNode.get("reported_standard_number")));
        udmUsage.setStandardNumber(JsonUtils.getStringValue(jsonNode.get("standard_number")));
        udmUsage.setReportedTitle(JsonUtils.getStringValue(jsonNode.get("reported_title")));
        udmUsage.setSystemTitle(JsonUtils.getStringValue(jsonNode.get("system_title")));
        udmUsage.setPeriodEndDate(JsonUtils.getLocalDateValue(jsonNode.get("period_end_date")));
        udmUsage.setStatus(UsageStatusEnum.valueOf(JsonUtils.getStringValue(jsonNode.get("status"))));
        udmUsage.setVersion(JsonUtils.getIntegerValue(jsonNode.get("record_version")));
        return udmUsage;
    }
}
