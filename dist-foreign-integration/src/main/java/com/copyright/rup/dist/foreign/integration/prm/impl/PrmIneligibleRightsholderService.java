package com.copyright.rup.dist.foreign.integration.prm.impl;

import com.copyright.rup.dist.common.integration.rest.prm.CommonPrmRestHandler;
import com.copyright.rup.dist.common.integration.util.JsonUtils;
import com.copyright.rup.dist.foreign.domain.AclIneligibleRightsholder;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIneligibleRightsholderService;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of {@link IPrmIneligibleRightsholderService}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/04/2022
 *
 * @author Anton Azarenka
 */
@Service("df.integration.prmIneligibleRightsholderService")
public class PrmIneligibleRightsholderService implements IPrmIneligibleRightsholderService {

    @Autowired
    private RestTemplate restTemplate;
    @Value("$RUP{dist.foreign.rest.prm.ineligible.parent.rhs.url}")
    private String rightsholdersParentUrl;
    @Value("$RUP{dist.foreign.rest.prm.ineligible.rhs.url}")
    private String ineligibleRightsholdersUrl;

    @Override
    public Set<AclIneligibleRightsholder> getIneligibleRightsholders(LocalDate periodEndDate, String licenseType) {
        Map<String, List<AclIneligibleRightsholder>> parents =
            getParentsByLicenseType(periodEndDate, licenseType);
        IneligibleRightsholdersHandler handler = new IneligibleRightsholdersHandler(restTemplate, periodEndDate);
        Set<AclIneligibleRightsholder> rightsholders = new HashSet<>();
        parents.forEach((key, value) -> {
            handler.setTypeOfUse(getTypeOfUse(key));
            List<AclIneligibleRightsholder> ineligibleRightsholders =
                handler.handleResponse(ineligibleRightsholdersUrl,
                    buildParamsMap(key,
                        value.stream().map(AclIneligibleRightsholder::getOrganizationId).collect(Collectors.toList())));
            rightsholders.addAll(value);
            rightsholders.addAll(ineligibleRightsholders);
        });
        return rightsholders;
    }

    private Map<String, List<AclIneligibleRightsholder>> getParentsByLicenseType(LocalDate periodEndDate,
                                                                                 String licenseType) {
        CommonPrmRestHandler<Map<String, List<AclIneligibleRightsholder>>> prmRestHandler =
            new CommonPrmRestHandler<Map<String, List<AclIneligibleRightsholder>>>(restTemplate) {
                @Override
                protected Map<String, List<AclIneligibleRightsholder>> doHandleResponse(JsonNode response) {
                    return getRightsholdersParents(response, periodEndDate, licenseType);
                }

                @Override
                protected Map<String, List<AclIneligibleRightsholder>> getDefaultValue() {
                    return Collections.emptyMap();
                }
            };
        return prmRestHandler.handleResponse(rightsholdersParentUrl);
    }

    private Map<String, List<AclIneligibleRightsholder>> getRightsholdersParents(JsonNode jsonNode,
                                                                                 LocalDate periodEndDate,
                                                                                 String licenseType) {
        Map<String, List<AclIneligibleRightsholder>> parentRightsholders = new HashMap<>();
        if (Objects.nonNull(jsonNode) && jsonNode.isArray()) {
            for (JsonNode parentNode : jsonNode) {
                String product = JsonUtils.getStringValue(parentNode.findValue("pmLicenseProductUid"));
                if (isValidParent(periodEndDate, licenseType, parentNode, product)) {
                    parentRightsholders.computeIfAbsent(product, key -> new ArrayList<>())
                        .add(buildParentIneligibleRightsholder(parentNode, getTypeOfUse(product), product));
                }
            }
        }
        return parentRightsholders;
    }

    private boolean isValidParent(LocalDate periodEndDate, String licenseType, JsonNode parentNode,
                                  String product) {
        return "INELIGIBLEFORSHARES".equals(JsonUtils.getStringValue(parentNode.findValue("preferenceCode")))
            && JsonUtils.getBooleanValue(parentNode.findValue("preferenceValue"))
            && product.equals(getLicenseProduct(licenseType, product))
            && isRightsholderApplicableForPeriod(parentNode, periodEndDate);
    }

    private boolean isRightsholderApplicableForPeriod(JsonNode rightsholderNode, LocalDate periodEndDate) {
        return periodEndDate.compareTo(getLocalDateValue(rightsholderNode.findValue("validBeginDate"))) >= 0
            && periodEndDate.compareTo(getLocalDateValue(rightsholderNode.findValue("validEndDate"))) <= 0;
    }

    private AclIneligibleRightsholder buildParentIneligibleRightsholder(JsonNode rightsholderNode, String typeOfUse,
                                                                        String product) {
        AclIneligibleRightsholder rightsholder = new AclIneligibleRightsholder();
        rightsholder.setOrganizationId(JsonUtils.getStringValue(rightsholderNode.findValue("pmOrganizationUid")));
        rightsholder.setRhAccountNumber(JsonUtils.getLongValue(rightsholderNode.findValue("externalOrganizationKey")));
        rightsholder.setTypeOfUse(typeOfUse);
        rightsholder.setLicenseType(product);
        return rightsholder;
    }

    private String getTypeOfUse(String licenseProduct) {
        return StringUtils.contains(licenseProduct, "PRINT") ? "PRINT" : "DIGITAL";
    }

    private String getLicenseProduct(String licenseType, String product) {
        return licenseType + getTypeOfUse(product);
    }

    private Map<String, String> buildParamsMap(String productId, List<String> parentsIds) {
        String aclProductId = "ACL" + getTypeOfUse(productId);
        return ImmutableMap.of("productId", aclProductId, "rightsholdersIds", StringUtils.join(parentsIds, ","));
    }

    private List<AclIneligibleRightsholder> getIneligibleRightsholders(JsonNode jsonNode, String typeOfUse,
                                                                       LocalDate periodEndDate) {
        List<AclIneligibleRightsholder> aclIneligibleRightsholders = new ArrayList<>();
        if (Objects.nonNull(jsonNode) && jsonNode.isArray()) {
            for (JsonNode ineligibleRightsholderNode : jsonNode) {
                if (isRightsholderApplicableForPeriod(ineligibleRightsholderNode, periodEndDate)) {
                    aclIneligibleRightsholders.add(
                        buildIneligibleRightsholder(ineligibleRightsholderNode, typeOfUse));
                }
            }
        }
        return aclIneligibleRightsholders;
    }

    private AclIneligibleRightsholder buildIneligibleRightsholder(JsonNode rightsholderNode, String typeOfUse) {
        AclIneligibleRightsholder rightsholder = new AclIneligibleRightsholder();
        rightsholder.setRhAccountNumber(
            JsonUtils.getLongValue(rightsholderNode.findValue("relatedExternalOrganizationKey")));
        rightsholder.setTypeOfUse(typeOfUse);
        return rightsholder;
    }

    private LocalDate getLocalDateValue(JsonNode node) {
        String date = node.asText();
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss-SSSS"));
    }

    private class IneligibleRightsholdersHandler extends CommonPrmRestHandler<List<AclIneligibleRightsholder>> {

        private String typeOfUse;
        private final LocalDate periodEndDate;

        IneligibleRightsholdersHandler(RestTemplate restTemplate, LocalDate periodEndDate) {
            super(restTemplate);
            this.periodEndDate = periodEndDate;
        }

        @Override
        protected List<AclIneligibleRightsholder> doHandleResponse(JsonNode response) {
            return getIneligibleRightsholders(response, typeOfUse, periodEndDate);
        }

        @Override
        protected List<AclIneligibleRightsholder> getDefaultValue() {
            return List.of();
        }

        private void setTypeOfUse(String typeOfUse) {
            this.typeOfUse = typeOfUse;
        }
    }
}
