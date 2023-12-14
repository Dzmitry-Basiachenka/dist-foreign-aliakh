package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.domain.StoredEntity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
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

    private static final long serialVersionUID = 74985204900634092L;

    private String name;
    private String productFamily;
    private BigDecimal netTotal = FdaConstants.DEFAULT_AMOUNT_SCALE_10;
    private BigDecimal serviceFeeTotal = FdaConstants.DEFAULT_AMOUNT_SCALE_10;
    private BigDecimal grossTotal = FdaConstants.DEFAULT_AMOUNT_SCALE_10;
    private String description;
    private ScenarioStatusEnum status;
    private ScenarioAuditItem auditItem;
    private AaclFields aaclFields;
    private NtsFields ntsFields;
    private SalFields salFields;

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

    public SalFields getSalFields() {
        return salFields;
    }

    public void setSalFields(SalFields salFields) {
        this.salFields = salFields;
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
            .append(this.description, that.description)
            .append(this.status, that.status)
            .append(this.auditItem, that.auditItem)
            .append(this.aaclFields, that.aaclFields)
            .append(this.ntsFields, that.ntsFields)
            .append(this.salFields, that.salFields)
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
            .append(description)
            .append(status)
            .append(auditItem)
            .append(aaclFields)
            .append(ntsFields)
            .append(salFields)
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
            .append("description", description)
            .append("status", status)
            .append("auditItem", auditItem)
            .append("aaclFields", aaclFields)
            .append("ntsFields", ntsFields)
            .append("salFields", salFields)
            .toString();
    }

    /**
     * Represents fields specific for AACL scenario.
     */
    public static class AaclFields implements Serializable {

        private static final long serialVersionUID = 8405387501973148761L;

        private String fundPoolId;
        private List<PublicationType> publicationTypes = new ArrayList<>();
        private List<UsageAge> usageAges = new ArrayList<>();
        private List<DetailLicenseeClass> detailLicenseeClasses = new ArrayList<>();

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

        public List<UsageAge> getUsageAges() {
            return usageAges;
        }

        public void setUsageAges(List<UsageAge> usageAges) {
            this.usageAges = usageAges;
        }

        public List<DetailLicenseeClass> getDetailLicenseeClasses() {
            return detailLicenseeClasses;
        }

        public void setDetailLicenseeClasses(List<DetailLicenseeClass> detailLicenseeClasses) {
            this.detailLicenseeClasses = detailLicenseeClasses;
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
                .append(this.usageAges, that.usageAges)
                .append(this.detailLicenseeClasses, that.detailLicenseeClasses)
                .append(this.fundPoolId, that.fundPoolId)
                .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder()
                .append(publicationTypes)
                .append(usageAges)
                .append(detailLicenseeClasses)
                .append(fundPoolId)
                .toHashCode();
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("publicationTypes", publicationTypes)
                .append("usageAges", usageAges)
                .append("detailLicenseeClasses", detailLicenseeClasses)
                .append("fundPoolId", fundPoolId)
                .toString();
        }
    }

    /**
     * Represents fields specific for NTS scenario.
     */
    public static class NtsFields implements Serializable {

        private static final long serialVersionUID = 672166004447710796L;

        private BigDecimal rhMinimumAmount = FdaConstants.DEFAULT_AMOUNT_SCALE_2;
        private BigDecimal preServiceFeeAmount = FdaConstants.DEFAULT_AMOUNT_SCALE_2;
        private BigDecimal postServiceFeeAmount = FdaConstants.DEFAULT_AMOUNT_SCALE_2;
        private BigDecimal preServiceFeeFundTotal = FdaConstants.DEFAULT_AMOUNT_SCALE_2;
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

    /**
     * Represents fields specific for SAL scenario.
     */
    public static class SalFields implements Serializable {

        private static final long serialVersionUID = -8162708823658064076L;

        private String fundPoolId;

        public String getFundPoolId() {
            return fundPoolId;
        }

        public void setFundPoolId(String fundPoolId) {
            this.fundPoolId = fundPoolId;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (null == obj || this.getClass() != obj.getClass()) {
                return false;
            }
            SalFields that = (SalFields) obj;
            return new EqualsBuilder()
                .append(this.fundPoolId, that.fundPoolId)
                .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder()
                .append(fundPoolId)
                .toHashCode();
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("fundPoolId", fundPoolId)
                .toString();
        }
    }
}
