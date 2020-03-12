package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.domain.StoredEntity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents scenario.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/15/17
 *
 * @author Ihar Suvorau
 */
public class Scenario extends StoredEntity<String> {

    private String name;
    private String productFamily;
    private BigDecimal netTotal = BigDecimal.ZERO.setScale(10, BigDecimal.ROUND_HALF_UP);
    private BigDecimal serviceFeeTotal = BigDecimal.ZERO.setScale(10, BigDecimal.ROUND_HALF_UP);
    private BigDecimal grossTotal = BigDecimal.ZERO.setScale(10, BigDecimal.ROUND_HALF_UP);
    private BigDecimal reportedTotal = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
    private String description;
    private ScenarioStatusEnum status;
    private ScenarioAuditItem auditItem;
    private AaclFields aaclFields;
    private NtsFields ntsFields;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductFamily() {
        return productFamily;
    }

    public void setProductFamily(String productFamily) {
        this.productFamily = productFamily;
    }

    public BigDecimal getNetTotal() {
        return netTotal;
    }

    public void setNetTotal(BigDecimal netTotal) {
        this.netTotal = netTotal;
    }

    public BigDecimal getServiceFeeTotal() {
        return serviceFeeTotal;
    }

    public void setServiceFeeTotal(BigDecimal serviceFeeTotal) {
        this.serviceFeeTotal = serviceFeeTotal;
    }

    public BigDecimal getGrossTotal() {
        return grossTotal;
    }

    public void setGrossTotal(BigDecimal grossTotal) {
        this.grossTotal = grossTotal;
    }

    public BigDecimal getReportedTotal() {
        return reportedTotal;
    }

    public void setReportedTotal(BigDecimal reportedTotal) {
        this.reportedTotal = reportedTotal;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ScenarioStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ScenarioStatusEnum status) {
        this.status = status;
    }

    public ScenarioAuditItem getAuditItem() {
        return auditItem;
    }

    public void setAuditItem(ScenarioAuditItem auditItem) {
        this.auditItem = auditItem;
    }

    public AaclFields getAaclFields() {
        return aaclFields;
    }

    public void setAaclFields(AaclFields aaclFields) {
        this.aaclFields = aaclFields;
    }

    public NtsFields getNtsFields() {
        return ntsFields;
    }

    public void setNtsFields(NtsFields ntsFields) {
        this.ntsFields = ntsFields;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (null == obj || this.getClass() != obj.getClass()) {
            return false;
        }
        Scenario that = (Scenario) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(this.name, that.name)
            .append(this.productFamily, that.productFamily)
            .append(this.netTotal, that.netTotal)
            .append(this.serviceFeeTotal, that.serviceFeeTotal)
            .append(this.grossTotal, that.grossTotal)
            .append(this.reportedTotal, that.reportedTotal)
            .append(this.description, that.description)
            .append(this.status, that.status)
            .append(this.auditItem, that.auditItem)
            .append(this.aaclFields, that.aaclFields)
            .append(this.ntsFields, that.ntsFields)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(name)
            .append(productFamily)
            .append(netTotal)
            .append(serviceFeeTotal)
            .append(grossTotal)
            .append(reportedTotal)
            .append(description)
            .append(status)
            .append(auditItem)
            .append(aaclFields)
            .append(ntsFields)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .appendSuper(super.toString())
            .append("name", name)
            .append("productFamily", productFamily)
            .append("netTotal", netTotal)
            .append("serviceFeeTotal", serviceFeeTotal)
            .append("grossTotal", grossTotal)
            .append("reportedTotal", reportedTotal)
            .append("description", description)
            .append("status", status)
            .append("auditItem", auditItem)
            .append("aaclFields", aaclFields)
            .append("ntsFields", ntsFields)
            .toString();
    }

    /**
     * Represents fields specific for AACL scenario.
     */
    public static class AaclFields {

        private BigDecimal titleCutoffAmount = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
        private String fundPoolId;
        private List<PublicationType> publicationTypes = new ArrayList<>();
        // TODO {aliakh} add Usage Age Weights, Licensee Classes mapping and other fields specific for AACL scenario

        public BigDecimal getTitleCutoffAmount() {
            return titleCutoffAmount;
        }

        public void setTitleCutoffAmount(BigDecimal titleCutoffAmount) {
            this.titleCutoffAmount = titleCutoffAmount;
        }

        public String getFundPoolId() {
            return fundPoolId;
        }

        public void setFundPoolId(String fundPoolId) {
            this.fundPoolId = fundPoolId;
        }

        public List<PublicationType> getPublicationTypes() {
            return publicationTypes;
        }

        public void setPublicationTypes(List<PublicationType> publicationTypes) {
            this.publicationTypes = publicationTypes;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (null == obj || this.getClass() != obj.getClass()) {
                return false;
            }
            AaclFields that = (AaclFields) obj;
            return new EqualsBuilder()
                .append(this.publicationTypes, that.publicationTypes)
                .append(this.titleCutoffAmount, that.titleCutoffAmount)
                .append(this.fundPoolId, that.fundPoolId)
                .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder()
                .append(publicationTypes)
                .append(titleCutoffAmount)
                .append(fundPoolId)
                .toHashCode();
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("publicationTypes", publicationTypes)
                .append("titleCutoffAmount", titleCutoffAmount)
                .append("fundPoolId", fundPoolId)
                .toString();
        }
    }

    /**
     * Represents fields specific for NTS scenario.
     */
    public static class NtsFields {

        private BigDecimal rhMinimumAmount = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
        private BigDecimal preServiceFeeAmount = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
        private BigDecimal postServiceFeeAmount = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
        private BigDecimal preServiceFeeFundTotal = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
        private String preServiceFeeFundId;
        private String preServiceFeeFundName;

        public BigDecimal getRhMinimumAmount() {
            return rhMinimumAmount;
        }

        public void setRhMinimumAmount(BigDecimal rhMinimumAmount) {
            this.rhMinimumAmount = rhMinimumAmount;
        }

        public BigDecimal getPreServiceFeeAmount() {
            return preServiceFeeAmount;
        }

        public void setPreServiceFeeAmount(BigDecimal preServiceFeeAmount) {
            this.preServiceFeeAmount = preServiceFeeAmount;
        }

        public BigDecimal getPostServiceFeeAmount() {
            return postServiceFeeAmount;
        }

        public void setPostServiceFeeAmount(BigDecimal postServiceFeeAmount) {
            this.postServiceFeeAmount = postServiceFeeAmount;
        }

        public String getPreServiceFeeFundId() {
            return preServiceFeeFundId;
        }

        public void setPreServiceFeeFundId(String preServiceFeeFundId) {
            this.preServiceFeeFundId = preServiceFeeFundId;
        }

        public String getPreServiceFeeFundName() {
            return preServiceFeeFundName;
        }

        public void setPreServiceFeeFundName(String preServiceFeeFundName) {
            this.preServiceFeeFundName = preServiceFeeFundName;
        }

        public BigDecimal getPreServiceFeeFundTotal() {
            return preServiceFeeFundTotal;
        }

        public void setPreServiceFeeFundTotal(BigDecimal preServiceFeeFundTotal) {
            this.preServiceFeeFundTotal = preServiceFeeFundTotal;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (null == obj || this.getClass() != obj.getClass()) {
                return false;
            }
            NtsFields that = (NtsFields) obj;
            return new EqualsBuilder()
                .append(this.rhMinimumAmount, that.rhMinimumAmount)
                .append(this.preServiceFeeAmount, that.preServiceFeeAmount)
                .append(this.postServiceFeeAmount, that.postServiceFeeAmount)
                .append(this.preServiceFeeFundId, that.preServiceFeeFundId)
                .append(this.preServiceFeeFundName, that.preServiceFeeFundName)
                .append(this.preServiceFeeFundTotal, that.preServiceFeeFundTotal)
                .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder()
                .append(rhMinimumAmount)
                .append(preServiceFeeAmount)
                .append(postServiceFeeAmount)
                .append(preServiceFeeFundId)
                .append(preServiceFeeFundName)
                .append(preServiceFeeFundTotal)
                .toHashCode();
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("rhMinimumAmount", rhMinimumAmount)
                .append("preServiceFeeAmount", preServiceFeeAmount)
                .append("postServiceFeeAmount", postServiceFeeAmount)
                .append("preServiceFeeFundId", preServiceFeeFundId)
                .append("preServiceFeeFundName", preServiceFeeFundName)
                .append("preServiceFeeFundTotal", preServiceFeeFundTotal)
                .toString();
        }
    }
}
