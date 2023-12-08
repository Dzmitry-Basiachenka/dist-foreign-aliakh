package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.domain.StoredEntity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Domain object to represent Fund Pool.
 *
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 02/07/20
 *
 * @author Darya Baraukova
 */
public class FundPool extends StoredEntity<String> {

    private static final long serialVersionUID = -503076720014487188L;

    private String productFamily;
    private String name;
    private String comment;
    private BigDecimal totalAmount = FdaConstants.DEFAULT_AMOUNT_SCALE_2;
    private BigDecimal serviceFeeAmount = FdaConstants.DEFAULT_AMOUNT_SCALE_2;
    private BigDecimal netAmount = FdaConstants.DEFAULT_AMOUNT_SCALE_2;
    private SalFields salFields;
    private AclciFields aclciFields;

    /**
     * Constructor.
     */
    public FundPool() {
    }

    /**
     * Constructor.
     *
     * @param fundPool instance of {@link FundPool}
     */
    public FundPool(FundPool fundPool) {
        this.productFamily = fundPool.productFamily;
        this.name = fundPool.name;
        this.comment = fundPool.comment;
        this.totalAmount = fundPool.totalAmount;
        this.serviceFeeAmount = fundPool.serviceFeeAmount;
        this.netAmount = fundPool.netAmount;
        this.salFields = fundPool.salFields;
        this.aclciFields = Objects.nonNull(fundPool.aclciFields) ? new AclciFields(fundPool.aclciFields) : null;
    }

    public String getProductFamily() {
        return productFamily;
    }

    public void setProductFamily(String productFamily) {
        this.productFamily = productFamily;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getServiceFeeAmount() {
        return serviceFeeAmount;
    }

    public void setServiceFeeAmount(BigDecimal serviceFeeAmount) {
        this.serviceFeeAmount = serviceFeeAmount;
    }

    public BigDecimal getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(BigDecimal netAmount) {
        this.netAmount = netAmount;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        FundPool that = (FundPool) obj;
        return new EqualsBuilder()
            .appendSuper(super.equals(obj))
            .append(productFamily, that.productFamily)
            .append(name, that.name)
            .append(comment, that.comment)
            .append(totalAmount, that.totalAmount)
            .append(serviceFeeAmount, that.serviceFeeAmount)
            .append(netAmount, that.netAmount)
            .append(salFields, that.salFields)
            .append(aclciFields, that.aclciFields)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(productFamily)
            .append(name)
            .append(comment)
            .append(totalAmount)
            .append(serviceFeeAmount)
            .append(netAmount)
            .append(salFields)
            .append(aclciFields)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .appendSuper(super.toString())
            .append("productFamily", productFamily)
            .append("name", name)
            .append("comment", comment)
            .append("totalAmount", totalAmount)
            .append("serviceFeeAmount", serviceFeeAmount)
            .append("netAmount", netAmount)
            .append("salFields", salFields)
            .append("aclciFields", aclciFields)
            .toString();
    }

    /**
     * Represents SAL specific fields.
     */
    public static class SalFields {

        private LocalDate dateReceived;
        private String assessmentName;
        private String licenseeName;
        private Long licenseeAccountNumber;
        private int gradeKto5NumberOfStudents;
        private int grade6to8NumberOfStudents;
        private int grade9to12NumberOfStudents;
        private BigDecimal grossAmount = FdaConstants.DEFAULT_AMOUNT_SCALE_2;
        private BigDecimal itemBankGrossAmount = FdaConstants.DEFAULT_AMOUNT_SCALE_2;
        private BigDecimal gradeKto5GrossAmount = FdaConstants.DEFAULT_AMOUNT_SCALE_2;
        private BigDecimal grade6to8GrossAmount = FdaConstants.DEFAULT_AMOUNT_SCALE_2;
        private BigDecimal grade9to12GrossAmount = FdaConstants.DEFAULT_AMOUNT_SCALE_2;
        private BigDecimal itemBankSplitPercent = FdaConstants.DEFAULT_AMOUNT_SCALE_2;
        private BigDecimal serviceFee = FdaConstants.DEFAULT_AMOUNT_SCALE_2;

        public LocalDate getDateReceived() {
            return dateReceived;
        }

        public void setDateReceived(LocalDate dateReceived) {
            this.dateReceived = dateReceived;
        }

        public String getAssessmentName() {
            return assessmentName;
        }

        public void setAssessmentName(String assessmentName) {
            this.assessmentName = assessmentName;
        }

        public String getLicenseeName() {
            return licenseeName;
        }

        public void setLicenseeName(String licenseeName) {
            this.licenseeName = licenseeName;
        }

        public Long getLicenseeAccountNumber() {
            return licenseeAccountNumber;
        }

        public void setLicenseeAccountNumber(Long licenseeAccountNumber) {
            this.licenseeAccountNumber = licenseeAccountNumber;
        }

        public int getGradeKto5NumberOfStudents() {
            return gradeKto5NumberOfStudents;
        }

        public void setGradeKto5NumberOfStudents(int gradeKto5NumberOfStudents) {
            this.gradeKto5NumberOfStudents = gradeKto5NumberOfStudents;
        }

        public int getGrade6to8NumberOfStudents() {
            return grade6to8NumberOfStudents;
        }

        public void setGrade6to8NumberOfStudents(int grade6to8NumberOfStudents) {
            this.grade6to8NumberOfStudents = grade6to8NumberOfStudents;
        }

        public int getGrade9to12NumberOfStudents() {
            return grade9to12NumberOfStudents;
        }

        public void setGrade9to12NumberOfStudents(int grade9to12NumberOfStudents) {
            this.grade9to12NumberOfStudents = grade9to12NumberOfStudents;
        }

        public BigDecimal getGrossAmount() {
            return grossAmount;
        }

        public void setGrossAmount(BigDecimal grossAmount) {
            this.grossAmount = grossAmount;
        }

        public BigDecimal getItemBankGrossAmount() {
            return itemBankGrossAmount;
        }

        public void setItemBankGrossAmount(BigDecimal itemBankGrossAmount) {
            this.itemBankGrossAmount = itemBankGrossAmount;
        }

        public BigDecimal getGradeKto5GrossAmount() {
            return gradeKto5GrossAmount;
        }

        public void setGradeKto5GrossAmount(BigDecimal gradeKto5GrossAmount) {
            this.gradeKto5GrossAmount = gradeKto5GrossAmount;
        }

        public BigDecimal getGrade6to8GrossAmount() {
            return grade6to8GrossAmount;
        }

        public void setGrade6to8GrossAmount(BigDecimal grade6to8GrossAmount) {
            this.grade6to8GrossAmount = grade6to8GrossAmount;
        }

        public BigDecimal getGrade9to12GrossAmount() {
            return grade9to12GrossAmount;
        }

        public void setGrade9to12GrossAmount(BigDecimal grade9to12GrossAmount) {
            this.grade9to12GrossAmount = grade9to12GrossAmount;
        }

        public BigDecimal getItemBankSplitPercent() {
            return itemBankSplitPercent;
        }

        public void setItemBankSplitPercent(BigDecimal itemBankSplitPercent) {
            this.itemBankSplitPercent = itemBankSplitPercent;
        }

        public BigDecimal getServiceFee() {
            return serviceFee;
        }

        public void setServiceFee(BigDecimal serviceFee) {
            this.serviceFee = serviceFee;
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
                .append(this.gradeKto5NumberOfStudents, that.gradeKto5NumberOfStudents)
                .append(this.grade6to8NumberOfStudents, that.grade6to8NumberOfStudents)
                .append(this.grade9to12NumberOfStudents, that.grade9to12NumberOfStudents)
                .append(this.dateReceived, that.dateReceived)
                .append(this.assessmentName, that.assessmentName)
                .append(this.licenseeName, that.licenseeName)
                .append(this.licenseeAccountNumber, that.licenseeAccountNumber)
                .append(this.grossAmount, that.grossAmount)
                .append(this.itemBankGrossAmount, that.itemBankGrossAmount)
                .append(this.gradeKto5GrossAmount, that.gradeKto5GrossAmount)
                .append(this.grade6to8GrossAmount, that.grade6to8GrossAmount)
                .append(this.grade9to12GrossAmount, that.grade9to12GrossAmount)
                .append(this.itemBankSplitPercent, that.itemBankSplitPercent)
                .append(this.serviceFee, that.serviceFee)
                .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder()
                .append(dateReceived)
                .append(assessmentName)
                .append(licenseeName)
                .append(licenseeAccountNumber)
                .append(gradeKto5NumberOfStudents)
                .append(grade6to8NumberOfStudents)
                .append(grade9to12NumberOfStudents)
                .append(grossAmount)
                .append(itemBankGrossAmount)
                .append(gradeKto5GrossAmount)
                .append(grade6to8GrossAmount)
                .append(grade9to12GrossAmount)
                .append(itemBankSplitPercent)
                .append(serviceFee)
                .toHashCode();
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("dateReceived", dateReceived)
                .append("assessmentName", assessmentName)
                .append("licenseeName", licenseeName)
                .append("licenseeAccountNumber", licenseeAccountNumber)
                .append("gradeKto5NumberOfStudents", gradeKto5NumberOfStudents)
                .append("grade6to8NumberOfStudents", grade6to8NumberOfStudents)
                .append("grade9to12NumberOfStudents", grade9to12NumberOfStudents)
                .append("grossAmount", grossAmount)
                .append("itemBankGrossAmount", itemBankGrossAmount)
                .append("gradeKto5GrossAmount", gradeKto5GrossAmount)
                .append("grade6to8GrossAmount", grade6to8GrossAmount)
                .append("grade9to12GrossAmount", grade9to12GrossAmount)
                .append("itemBankSplitPercent", itemBankSplitPercent)
                .append("serviceFee", serviceFee)
                .toString();
        }
    }

    /**
     * Represents ACLCI specific fields.
     */
    public static class AclciFields {

        private String coverageYears;
        private long gradeKto2NumberOfStudents;
        private long grade3to5NumberOfStudents;
        private long grade6to8NumberOfStudents;
        private long grade9to12NumberOfStudents;
        private long gradeHeNumberOfStudents;
        private BigDecimal grossAmount = FdaConstants.DEFAULT_AMOUNT_SCALE_2;
        private BigDecimal curriculumDbSplitPercent = FdaConstants.DEFAULT_AMOUNT_SCALE_2;
        private BigDecimal gradeKto2GrossAmount = FdaConstants.DEFAULT_AMOUNT_SCALE_2;
        private BigDecimal grade3to5GrossAmount = FdaConstants.DEFAULT_AMOUNT_SCALE_2;
        private BigDecimal grade6to8GrossAmount = FdaConstants.DEFAULT_AMOUNT_SCALE_2;
        private BigDecimal grade9to12GrossAmount = FdaConstants.DEFAULT_AMOUNT_SCALE_2;
        private BigDecimal gradeHeGrossAmount = FdaConstants.DEFAULT_AMOUNT_SCALE_2;
        private BigDecimal curriculumDbGrossAmount = FdaConstants.DEFAULT_AMOUNT_SCALE_2;

        /**
         * Constructor.
         */
        public AclciFields() {
        }

        /**
         * Constructor.
         *
         * @param aclciFields instance of {@link AclciFields}
         */
        public AclciFields(AclciFields aclciFields) {
            this.coverageYears = aclciFields.coverageYears;
            this.gradeKto2NumberOfStudents = aclciFields.gradeKto2NumberOfStudents;
            this.grade3to5NumberOfStudents = aclciFields.grade3to5NumberOfStudents;
            this.grade6to8NumberOfStudents = aclciFields.grade6to8NumberOfStudents;
            this.grade9to12NumberOfStudents = aclciFields.grade9to12NumberOfStudents;
            this.gradeHeNumberOfStudents = aclciFields.gradeHeNumberOfStudents;
            this.grossAmount = aclciFields.grossAmount;
            this.curriculumDbSplitPercent = aclciFields.curriculumDbSplitPercent;
            this.gradeKto2GrossAmount = aclciFields.gradeKto2GrossAmount;
            this.grade3to5GrossAmount = aclciFields.grade3to5GrossAmount;
            this.grade6to8GrossAmount = aclciFields.grade6to8GrossAmount;
            this.grade9to12GrossAmount = aclciFields.grade9to12GrossAmount;
            this.gradeHeGrossAmount = aclciFields.gradeHeGrossAmount;
            this.curriculumDbGrossAmount = aclciFields.curriculumDbGrossAmount;
        }

        public String getCoverageYears() {
            return coverageYears;
        }

        public void setCoverageYears(String coverageYears) {
            this.coverageYears = coverageYears;
        }

        public long getGradeKto2NumberOfStudents() {
            return gradeKto2NumberOfStudents;
        }

        public void setGradeKto2NumberOfStudents(long gradeKto2NumberOfStudents) {
            this.gradeKto2NumberOfStudents = gradeKto2NumberOfStudents;
        }

        public long getGrade3to5NumberOfStudents() {
            return grade3to5NumberOfStudents;
        }

        public void setGrade3to5NumberOfStudents(long grade3to5NumberOfStudents) {
            this.grade3to5NumberOfStudents = grade3to5NumberOfStudents;
        }

        public long getGrade6to8NumberOfStudents() {
            return grade6to8NumberOfStudents;
        }

        public void setGrade6to8NumberOfStudents(long grade6to8NumberOfStudents) {
            this.grade6to8NumberOfStudents = grade6to8NumberOfStudents;
        }

        public long getGrade9to12NumberOfStudents() {
            return grade9to12NumberOfStudents;
        }

        public void setGrade9to12NumberOfStudents(long grade9to12NumberOfStudents) {
            this.grade9to12NumberOfStudents = grade9to12NumberOfStudents;
        }

        public long getGradeHeNumberOfStudents() {
            return gradeHeNumberOfStudents;
        }

        public void setGradeHeNumberOfStudents(long gradeHeNumberOfStudents) {
            this.gradeHeNumberOfStudents = gradeHeNumberOfStudents;
        }

        public BigDecimal getGrossAmount() {
            return grossAmount;
        }

        public void setGrossAmount(BigDecimal grossAmount) {
            this.grossAmount = grossAmount;
        }

        public BigDecimal getCurriculumDbSplitPercent() {
            return curriculumDbSplitPercent;
        }

        public void setCurriculumDbSplitPercent(BigDecimal curriculumDbSplitPercent) {
            this.curriculumDbSplitPercent = curriculumDbSplitPercent;
        }

        public BigDecimal getGradeKto2GrossAmount() {
            return gradeKto2GrossAmount;
        }

        public void setGradeKto2GrossAmount(BigDecimal gradeKto2GrossAmount) {
            this.gradeKto2GrossAmount = gradeKto2GrossAmount;
        }

        public BigDecimal getGrade3to5GrossAmount() {
            return grade3to5GrossAmount;
        }

        public void setGrade3to5GrossAmount(BigDecimal grade3to5GrossAmount) {
            this.grade3to5GrossAmount = grade3to5GrossAmount;
        }

        public BigDecimal getGrade6to8GrossAmount() {
            return grade6to8GrossAmount;
        }

        public void setGrade6to8GrossAmount(BigDecimal grade6to8GrossAmount) {
            this.grade6to8GrossAmount = grade6to8GrossAmount;
        }

        public BigDecimal getGrade9to12GrossAmount() {
            return grade9to12GrossAmount;
        }

        public void setGrade9to12GrossAmount(BigDecimal grade9to12GrossAmount) {
            this.grade9to12GrossAmount = grade9to12GrossAmount;
        }

        public BigDecimal getGradeHeGrossAmount() {
            return gradeHeGrossAmount;
        }

        public void setGradeHeGrossAmount(BigDecimal gradeHeGrossAmount) {
            this.gradeHeGrossAmount = gradeHeGrossAmount;
        }

        public BigDecimal getCurriculumDbGrossAmount() {
            return curriculumDbGrossAmount;
        }

        public void setCurriculumDbGrossAmount(BigDecimal curriculumDbGrossAmount) {
            this.curriculumDbGrossAmount = curriculumDbGrossAmount;
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
                .append(coverageYears, that.coverageYears)
                .append(gradeKto2NumberOfStudents, that.gradeKto2NumberOfStudents)
                .append(grade3to5NumberOfStudents, that.grade3to5NumberOfStudents)
                .append(grade6to8NumberOfStudents, that.grade6to8NumberOfStudents)
                .append(grade9to12NumberOfStudents, that.grade9to12NumberOfStudents)
                .append(gradeHeNumberOfStudents, that.gradeHeNumberOfStudents)
                .append(grossAmount, that.grossAmount)
                .append(curriculumDbSplitPercent, that.curriculumDbSplitPercent)
                .append(gradeKto2GrossAmount, that.gradeKto2GrossAmount)
                .append(grade3to5GrossAmount, that.grade3to5GrossAmount)
                .append(grade6to8GrossAmount, that.grade6to8GrossAmount)
                .append(grade9to12GrossAmount, that.grade9to12GrossAmount)
                .append(gradeHeGrossAmount, that.gradeHeGrossAmount)
                .append(curriculumDbGrossAmount, that.curriculumDbGrossAmount)
                .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder()
                .append(coverageYears)
                .append(gradeKto2NumberOfStudents)
                .append(grade3to5NumberOfStudents)
                .append(grade6to8NumberOfStudents)
                .append(grade9to12NumberOfStudents)
                .append(gradeHeNumberOfStudents)
                .append(grossAmount)
                .append(curriculumDbSplitPercent)
                .append(gradeKto2GrossAmount)
                .append(grade3to5GrossAmount)
                .append(grade6to8GrossAmount)
                .append(grade9to12GrossAmount)
                .append(gradeHeGrossAmount)
                .append(curriculumDbGrossAmount)
                .toHashCode();
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                .append("coverageYears", coverageYears)
                .append("gradeKto2NumberOfStudents", gradeKto2NumberOfStudents)
                .append("grade3to5NumberOfStudents", grade3to5NumberOfStudents)
                .append("grade6to8NumberOfStudents", grade6to8NumberOfStudents)
                .append("grade9to12NumberOfStudents", grade9to12NumberOfStudents)
                .append("gradeHeNumberOfStudents", gradeHeNumberOfStudents)
                .append("grossAmount", grossAmount)
                .append("curriculumDbSplitPercent", curriculumDbSplitPercent)
                .append("gradeKto2GrossAmount", gradeKto2GrossAmount)
                .append("grade3to5GrossAmount", grade3to5GrossAmount)
                .append("grade6to8GrossAmount", grade6to8GrossAmount)
                .append("grade9to12GrossAmount", grade9to12GrossAmount)
                .append("gradeHeGrossAmount", gradeHeGrossAmount)
                .append("curriculumDbGrossAmount", curriculumDbGrossAmount)
                .toString();
        }
    }
}
