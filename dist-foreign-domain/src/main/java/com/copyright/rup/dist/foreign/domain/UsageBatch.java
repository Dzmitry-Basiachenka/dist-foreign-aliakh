package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.domain.StoredEntity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents usage batch.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 01/13/17
 *
 * @author Aliaksei Pchelnikau
 */
public class UsageBatch extends StoredEntity<String> {

    private static final long serialVersionUID = 1512685144463514107L;

    private String name;
    private Rightsholder rro;
    private String productFamily;
    private LocalDate paymentDate;
    private Integer fiscalYear;
    private BigDecimal grossAmount;
    private NtsFields ntsFields;
    private SalFields salFields;
    private AclciFields aclciFields;
    private Integer numberOfBaselineYears;
    private int initialUsagesCount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Rightsholder getRro() {
        return rro;
    }

    public void setRro(Rightsholder rro) {
        this.rro = rro;
    }

    public String getProductFamily() {
        return productFamily;
    }

    public void setProductFamily(String productFamily) {
        this.productFamily = productFamily;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Integer getFiscalYear() {
        return fiscalYear;
    }

    public void setFiscalYear(Integer fiscalYear) {
        this.fiscalYear = fiscalYear;
    }

    public BigDecimal getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(BigDecimal grossAmount) {
        this.grossAmount = grossAmount;
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

    public AclciFields getAclciFields() {
        return aclciFields;
    }

    public void setAclciFields(AclciFields aclciFields) {
        this.aclciFields = aclciFields;
    }

    public Integer getNumberOfBaselineYears() {
        return numberOfBaselineYears;
    }

    public void setNumberOfBaselineYears(Integer numberOfBaselineYears) {
        this.numberOfBaselineYears = numberOfBaselineYears;
    }

    public int getInitialUsagesCount() {
        return initialUsagesCount;
    }

    public void setInitialUsagesCount(int initialUsagesCount) {
        this.initialUsagesCount = initialUsagesCount;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (null == obj || this.getClass() != obj.getClass()) {
            return false;
        }
        UsageBatch that = (UsageBatch) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(this.name, that.name)
            .append(this.rro, that.rro)
            .append(this.productFamily, that.productFamily)
            .append(this.paymentDate, that.paymentDate)
            .append(this.fiscalYear, that.fiscalYear)
            .append(this.grossAmount, that.grossAmount)
            .append(this.ntsFields, that.ntsFields)
            .append(this.salFields, that.salFields)
            .append(this.aclciFields, that.aclciFields)
            .append(this.numberOfBaselineYears, that.numberOfBaselineYears)
            .append(this.initialUsagesCount, that.initialUsagesCount)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(name)
            .append(rro)
            .append(productFamily)
            .append(paymentDate)
            .append(fiscalYear)
            .append(grossAmount)
            .append(ntsFields)
            .append(salFields)
            .append(aclciFields)
            .append(numberOfBaselineYears)
            .append(initialUsagesCount)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .appendSuper(super.toString())
            .append("name", name)
            .append("rro", rro)
            .append("productFamily", productFamily)
            .append("paymentDate", paymentDate)
            .append("fiscalYear", fiscalYear)
            .append("grossAmount", grossAmount)
            .append("ntsFields", ntsFields)
            .append("salFields", salFields)
            .append("aclciFields", aclciFields)
            .append("numberOfBaselineYears", numberOfBaselineYears)
            .append("initialUsagesCount", initialUsagesCount)
            .toString();
    }

    /**
     * Represents NTS specific fields.
     */
    public static class NtsFields implements Serializable {

        private static final long serialVersionUID = -3496067088605156453L;

        private Integer fundPoolPeriodFrom;
        private Integer fundPoolPeriodTo;
        private BigDecimal stmAmount;
        private BigDecimal nonStmAmount;
        private BigDecimal stmMinimumAmount;
        private BigDecimal nonStmMinimumAmount;
        private Set<String> markets = new HashSet<>();
        private boolean excludingStm;

        public Integer getFundPoolPeriodFrom() {
            return fundPoolPeriodFrom;
        }

        public void setFundPoolPeriodFrom(Integer fundPoolPeriodFrom) {
            this.fundPoolPeriodFrom = fundPoolPeriodFrom;
        }

        public Integer getFundPoolPeriodTo() {
            return fundPoolPeriodTo;
        }

        public void setFundPoolPeriodTo(Integer fundPoolPeriodTo) {
            this.fundPoolPeriodTo = fundPoolPeriodTo;
        }

        public BigDecimal getStmAmount() {
            return stmAmount;
        }

        public void setStmAmount(BigDecimal stmAmount) {
            this.stmAmount = stmAmount;
        }

        public BigDecimal getNonStmAmount() {
            return nonStmAmount;
        }

        public void setNonStmAmount(BigDecimal nonStmAmount) {
            this.nonStmAmount = nonStmAmount;
        }

        public BigDecimal getStmMinimumAmount() {
            return stmMinimumAmount;
        }

        public void setStmMinimumAmount(BigDecimal stmMinimumAmount) {
            this.stmMinimumAmount = stmMinimumAmount;
        }

        public BigDecimal getNonStmMinimumAmount() {
            return nonStmMinimumAmount;
        }

        public void setNonStmMinimumAmount(BigDecimal nonStmMinimumAmount) {
            this.nonStmMinimumAmount = nonStmMinimumAmount;
        }

        public Set<String> getMarkets() {
            return markets;
        }

        public void setMarkets(Set<String> markets) {
            this.markets = markets;
        }

        public boolean isExcludingStm() {
            return excludingStm;
        }

        public void setExcludingStm(boolean excludingStm) {
            this.excludingStm = excludingStm;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (null == obj || getClass() != obj.getClass()) {
                return false;
            }
            NtsFields that = (NtsFields) obj;
            return new EqualsBuilder()
                .append(fundPoolPeriodFrom, that.fundPoolPeriodFrom)
                .append(fundPoolPeriodTo, that.fundPoolPeriodTo)
                .append(stmAmount, that.stmAmount)
                .append(nonStmAmount, that.nonStmAmount)
                .append(stmMinimumAmount, that.stmMinimumAmount)
                .append(nonStmMinimumAmount, that.nonStmMinimumAmount)
                .append(markets, that.markets)
                .append(excludingStm, that.excludingStm)
                .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder()
                .append(fundPoolPeriodFrom)
                .append(fundPoolPeriodTo)
                .append(stmAmount)
                .append(nonStmAmount)
                .append(stmMinimumAmount)
                .append(nonStmMinimumAmount)
                .append(markets)
                .append(excludingStm)
                .toHashCode();
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("fundPoolPeriodFrom", fundPoolPeriodFrom)
                .append("fundPoolPeriodTo", fundPoolPeriodTo)
                .append("stmAmount", stmAmount)
                .append("nonStmAmount", nonStmAmount)
                .append("stmMinimumAmount", stmMinimumAmount)
                .append("nonStmMinimumAmount", nonStmMinimumAmount)
                .append("markets", markets)
                .append("excludingStm", excludingStm)
                .toString();
        }
    }

    /**
     * Represents SAL specific fields.
     */
    public static class SalFields implements Serializable {

        private static final long serialVersionUID = -4023149073249005829L;

        private Long licenseeAccountNumber;
        private String licenseeName;

        public Long getLicenseeAccountNumber() {
            return licenseeAccountNumber;
        }

        public void setLicenseeAccountNumber(Long licenseeAccountNumber) {
            this.licenseeAccountNumber = licenseeAccountNumber;
        }

        public String getLicenseeName() {
            return licenseeName;
        }

        public void setLicenseeName(String licenseeName) {
            this.licenseeName = licenseeName;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (null == obj || getClass() != obj.getClass()) {
                return false;
            }
            SalFields that = (SalFields) obj;
            return new EqualsBuilder()
                .append(licenseeAccountNumber, that.licenseeAccountNumber)
                .append(licenseeName, that.licenseeName)
                .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder()
                .append(licenseeAccountNumber)
                .append(licenseeName)
                .toHashCode();
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("licenseeAccountNumber", licenseeAccountNumber)
                .append("licenseeName", licenseeName)
                .toString();
        }
    }

    /**
     * Represents ACLCI specific fields.
     */
    public static class AclciFields implements Serializable {

        private static final long serialVersionUID = 380874807509289559L;

        private Long licenseeAccountNumber;
        private String licenseeName;

        public Long getLicenseeAccountNumber() {
            return licenseeAccountNumber;
        }

        public void setLicenseeAccountNumber(Long licenseeAccountNumber) {
            this.licenseeAccountNumber = licenseeAccountNumber;
        }

        public String getLicenseeName() {
            return licenseeName;
        }

        public void setLicenseeName(String licenseeName) {
            this.licenseeName = licenseeName;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (null == obj || getClass() != obj.getClass()) {
                return false;
            }
            AclciFields that = (AclciFields) obj;
            return new EqualsBuilder()
                .append(licenseeAccountNumber, that.licenseeAccountNumber)
                .append(licenseeName, that.licenseeName)
                .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder()
                .append(licenseeAccountNumber)
                .append(licenseeName)
                .toHashCode();
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("licenseeAccountNumber", licenseeAccountNumber)
                .append("licenseeName", licenseeName)
                .toString();
        }
    }
}
