package com.copyright.rup.dist.foreign.integration.crm.api;

import com.google.common.collect.Sets;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;
import java.util.Set;

/**
 * Represents 'insert rights distribution' response from CRM system.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 03/29/18
 *
 * @author Darya Baraukova
 */
public class InsertRightsDistributionResponse {

    private final Set<String> invalidUsageIds = Sets.newHashSet();
    private InsertRightsDistributionResponseStatusEnum status;

    /**
     * Constructor.
     *
     * @param status {@link InsertRightsDistributionResponseStatusEnum} instance
     */
    public InsertRightsDistributionResponse(InsertRightsDistributionResponseStatusEnum status) {
        this.status = status;
    }

    public InsertRightsDistributionResponseStatusEnum getStatus() {
        return status;
    }

    public void setStatus(InsertRightsDistributionResponseStatusEnum status) {
        this.status = status;
    }

    public Set<String> getInvalidUsageIds() {
        return invalidUsageIds;
    }

    /**
     * Adds the {@code detailId} into CRM response and associates it with obtained messages.
     *
     * @param usageId usage id
     */
    public void addInvalidUsageId(String usageId) {
        if (Objects.nonNull(usageId)) {
            invalidUsageIds.add(usageId);
        }
    }

    /**
     * Checks whether send to CRM process was successful or not.
     *
     * @return {@code true} - if status is {@link InsertRightsDistributionResponseStatusEnum#SUCCESS}
     * and list of invalid detail ids is empty, {@code false} - otherwise
     */
    public boolean isSuccessful() {
        return InsertRightsDistributionResponseStatusEnum.SUCCESS == status && CollectionUtils.isEmpty(invalidUsageIds);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("status", status)
            .append("invalidUsageIds", invalidUsageIds)
            .toString();
    }
}
