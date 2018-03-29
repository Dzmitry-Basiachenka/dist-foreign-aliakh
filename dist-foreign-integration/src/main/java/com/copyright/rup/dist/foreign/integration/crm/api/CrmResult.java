package com.copyright.rup.dist.foreign.integration.crm.api;

import com.google.common.collect.Sets;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Set;

/**
 * Represents result of CRM process.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 03/29/18
 *
 * @author Darya Baraukova
 */
public class CrmResult {

    private final Set<String> invalidDetailIds = Sets.newHashSet();
    private CrmResultStatusEnum crmResultStatus;

    /**
     * Constructor.
     *
     * @param crmResultStatus {@link CrmResultStatusEnum} instance
     */
    public CrmResult(CrmResultStatusEnum crmResultStatus) {
        this.crmResultStatus = crmResultStatus;
    }

    /**
     * @return {@link CrmResultStatusEnum} instance.
     */
    public CrmResultStatusEnum getCrmResultStatus() {
        return crmResultStatus;
    }

    /**
     * Sets {@link CrmResultStatusEnum} instance.
     *
     * @param crmResultStatus {@link CrmResultStatusEnum} instance
     */
    public void setCrmResultStatus(CrmResultStatusEnum crmResultStatus) {
        this.crmResultStatus = crmResultStatus;
    }

    /**
     * @return set of invalid detail ids.
     */
    public Set<String> getInvalidDetailIds() {
        return invalidDetailIds;
    }

    /**
     * Adds the {@code detailId} into CRM response and associates it with obtained messages.
     *
     * @param detailId detail id
     */
    public void addInvalidDetailId(String detailId) {
        if (StringUtils.isNotBlank(detailId)) {
            invalidDetailIds.add(detailId);
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("crmResultStatus", crmResultStatus)
            .append("invalidDetailIds", invalidDetailIds)
            .toString();
    }
}
