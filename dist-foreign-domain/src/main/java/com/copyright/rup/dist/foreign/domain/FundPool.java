package com.copyright.rup.dist.foreign.domain;

import com.copyright.rup.dist.common.domain.StoredEntity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.time.LocalDate;

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

    private String productFamily;
    private String name;
    private String comment;
    private BigDecimal totalAmount = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
    private BigDecimal serviceFeeAmount = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
    private BigDecimal netAmount = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
    private SalFields salFields;

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
        private BigDecimal grossAmount = BigDecimal.ZERO;
        private BigDecimal itemBankGrossAmount = BigDecimal.ZERO;
        private BigDecimal gradeKto5GrossAmount = BigDecimal.ZERO;
        private BigDecimal grade6to8GrossAmount = BigDecimal.ZERO;
        private BigDecimal grade9to12GrossAmount = BigDecimal.ZERO;
        private BigDecimal itemBankSplitPercent = BigDecimal.ZERO;
        private BigDecimal serviceFee = BigDecimal.ZERO;

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
            return new ToStringBuilder(this)
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
}
