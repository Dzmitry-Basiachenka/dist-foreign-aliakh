package com.copyright.rup.dist.foreign.integration.oracle.impl.handler;

import com.copyright.rup.dist.common.integration.rest.CommonRestHandler;
import com.copyright.rup.dist.common.integration.util.JsonUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.RhTaxInformation;

import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.web.client.RestTemplate;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Handler for processing rightsholders tax information response.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 6/18/20
 *
 * @author Stanislau Rudak
 */
public class OracleRhTaxInformationRestHandler extends CommonRestHandler<Map<Long, RhTaxInformation>> {

    private static final String ORACLE_DATE_TIME_FORMAT = "[yyyy-M-d[['T'][' ']HH:mm:ss]][M/d/yyyy]";

    /**
     * Constructor.
     *
     * @param restTemplate restTemplate.
     */
    public OracleRhTaxInformationRestHandler(RestTemplate restTemplate) {
        super(restTemplate);
    }

    @Override
    protected Map<Long, RhTaxInformation> doHandleResponse(JsonNode response) {
        Map<Long, RhTaxInformation> result = new HashMap<>();
        if (null != response && response.isArray()) {
            for (JsonNode thTaxInformationNode : response) {
                RhTaxInformation rhTaxInformation = buildRhTaxInformation(thTaxInformationNode);
                result.put(rhTaxInformation.getTboAccountNumber(), rhTaxInformation);
            }
        }
        return result;
    }

    @Override
    protected String getSystemName() {
        return "Oracle";
    }

    @Override
    protected Map<Long, RhTaxInformation> getDefaultValue() {
        return Map.of();
    }

    private RhTaxInformation buildRhTaxInformation(JsonNode thTaxInformationNode) {
        RhTaxInformation rhTaxInformation = new RhTaxInformation();
        rhTaxInformation.setZip(JsonUtils.getStringValue(thTaxInformationNode.get("zip")));
        rhTaxInformation.setWithHoldingIndicator(
            JsonUtils.getStringValue(thTaxInformationNode.get("withHoldingIndicator")));
        rhTaxInformation.setTypeOfForm(JsonUtils.getStringValue(thTaxInformationNode.get("typeOfForm")));
        rhTaxInformation.setState(JsonUtils.getStringValue(thTaxInformationNode.get("state")));
        rhTaxInformation.setTboAccountNumber(JsonUtils.getLongValue(thTaxInformationNode.get("tboAccountNumber")));
        rhTaxInformation.setAddressLine1(JsonUtils.getStringValue(thTaxInformationNode.get("addressLine1")));
        rhTaxInformation.setAddressLine2(JsonUtils.getStringValue(thTaxInformationNode.get("addressLine2")));
        rhTaxInformation.setAddressLine3(JsonUtils.getStringValue(thTaxInformationNode.get("addressLine3")));
        rhTaxInformation.setAddressLine4(JsonUtils.getStringValue(thTaxInformationNode.get("addressLine4")));
        rhTaxInformation.setCity(JsonUtils.getStringValue(thTaxInformationNode.get("city")));
        rhTaxInformation.setPayGroup(JsonUtils.getStringValue(thTaxInformationNode.get("payGroup")));
        rhTaxInformation.setNotificationsSent(JsonUtils.getIntegerValue(thTaxInformationNode.get("notificationsSent")));
        rhTaxInformation.setDateOfLastNotificationSent(
            getOffsetDateTimeValue(thTaxInformationNode.get("dateOfLastNotificationSent")));
        rhTaxInformation.setCountryCode(JsonUtils.getStringValue(thTaxInformationNode.get("country")));
        rhTaxInformation.setTaxOnFile(JsonUtils.getStringValue(thTaxInformationNode.get("taxOnFile")));
        return rhTaxInformation;
    }

    private OffsetDateTime getOffsetDateTimeValue(JsonNode node) {
        OffsetDateTime oracleDate = null;
        if (Objects.nonNull(node)) {
            oracleDate = CommonDateUtils.parseOffsetDateTime(node.asText(), ORACLE_DATE_TIME_FORMAT);
        }
        return oracleDate;
    }
}
