package com.copyright.rup.dist.foreign.service.impl.common;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.integration.util.JsonUtils;
import com.copyright.rup.dist.foreign.domain.AaclUsage;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.SalUsage;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link JsonDeserializer} to parse JSON to list of {@link Usage}s.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 11/13/2018
 *
 * @author Ihar Suvorau
 * @author Aliaksandr Liakh
 */
@Component("df.service.commonUsageDeserializer")
public class CommonUsageDeserializer extends JsonDeserializer<List<Usage>> {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Override
    public List<Usage> deserialize(JsonParser parser, DeserializationContext context)
        throws IOException {
        List<Usage> usages = new ArrayList<>();
        try {
            JsonNode tree = parser.readValueAsTree();
            JsonNode usagesNode = tree.get("usages");
            if (usagesNode.isArray()) {
                for (JsonNode usageNode : usagesNode) {
                    usages.add(deserializeUsage(usageNode));
                }
            }
        } catch (JsonParseException e) {
            LOGGER.warn("Deserialize Usage message. Failed", e);
        }
        return usages;
    }

    private Usage deserializeUsage(JsonNode jsonNode) {
        Usage usage = new Usage();
        usage.setId(JsonUtils.getStringValue(jsonNode.get("id")));
        usage.getRightsholder().setId(JsonUtils.getStringValue(jsonNode.get("rh_uid")));
        usage.getRightsholder().setAccountNumber(JsonUtils.getLongValue(jsonNode.get("rh_account_number")));
        usage.setStandardNumber(JsonUtils.getStringValue(jsonNode.get("standard_number")));
        usage.setStandardNumberType(JsonUtils.getStringValue(jsonNode.get("standard_number_type")));
        usage.setWorkTitle(JsonUtils.getStringValue(jsonNode.get("work_title")));
        usage.setSystemTitle(JsonUtils.getStringValue(jsonNode.get("system_title")));
        usage.setWrWrkInst(JsonUtils.getLongValue(jsonNode.get("wr_wrk_inst")));
        usage.setBatchId(JsonUtils.getStringValue(jsonNode.get("batch_id")));
        usage.setGrossAmount(
            JsonUtils.getBigDecimalValue(jsonNode.get("gross_amount")).setScale(2, RoundingMode.HALF_UP));
        usage.setStatus(UsageStatusEnum.valueOf(JsonUtils.getStringValue(jsonNode.get("status"))));
        usage.setProductFamily(JsonUtils.getStringValue(jsonNode.get("product_family")));
        if (FdaConstants.AACL_PRODUCT_FAMILY.equals(usage.getProductFamily())) {
            usage.setAaclUsage(new AaclUsage());
            usage.getAaclUsage()
                .setBatchPeriodEndDate(JsonUtils.getLocalDateValue(jsonNode.get("batch_period_end_date")));
            usage.getAaclUsage().setBaselineId(JsonUtils.getStringValue(jsonNode.get("baselineId")));
        } else if (FdaConstants.SAL_PRODUCT_FAMILY.equals(usage.getProductFamily())) {
            usage.setSalUsage(new SalUsage());
            usage.getSalUsage()
                .setBatchPeriodEndDate(JsonUtils.getLocalDateValue(jsonNode.get("batch_period_end_date")));
        }
        usage.setVersion(JsonUtils.getIntegerValue(jsonNode.get("record_version")));
        return usage;
    }
}
