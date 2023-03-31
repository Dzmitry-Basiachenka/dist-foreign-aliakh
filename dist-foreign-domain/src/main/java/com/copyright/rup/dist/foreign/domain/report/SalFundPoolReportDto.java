package com.copyright.rup.dist.foreign.domain.report;

import com.copyright.rup.dist.common.domain.StoredEntity;
import com.copyright.rup.dist.foreign.domain.FdaConstants;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents SAL Fund Pool report record.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 11/05/20
 *
 * @author Anton Azarenka
 */
public class SalFundPoolReportDto extends StoredEntity<String> {

    private String fundPoolName;
    private String scenarioName;
    private LocalDate dateReceived;
    private String assessmentName;
    private Long licenseeAccountNumber;
    private String licenseeName;
    private BigDecimal serviceFee = FdaConstants.DEFAULT_AMOUNT_SCALE_2;
    private BigDecimal netAmount = FdaConstants.DEFAULT_AMOUNT_SCALE_2;
    private BigDecimal grossAmount = FdaConstants.DEFAULT_AMOUNT_SCALE_2;
    private BigDecimal itemBankSplitPercent = FdaConstants.DEFAULT_AMOUNT_SCALE_2;
    private BigDecimal itemBankGrossAmount = FdaConstants.DEFAULT_AMOUNT_SCALE_2;
    private BigDecimal gradeKto5GrossAmount = FdaConstants.DEFAULT_AMOUNT_SCALE_2;
    private BigDecimal grade6to8GrossAmount = FdaConstants.DEFAULT_AMOUNT_SCALE_2;
    private BigDecimal grade9to12GrossAmount = FdaConstants.DEFAULT_AMOUNT_SCALE_2;
    private BigDecimal totalGrossAmount = FdaConstants.DEFAULT_AMOUNT_SCALE_2;
    private int gradeKto5NumberOfStudents;
    private int grade6to8NumberOfStudents;
    private int grade9to12NumberOfStudents;

    public BigDecimal getTotalGrossAmount() {
        return totalGrossAmount;
    }

    public void setTotalGrossAmount(BigDecimal totalGrossAmount) {
        this.totalGrossAmount = totalGrossAmount;
    }

    public BigDecimal getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(BigDecimal netAmount) {
        this.netAmount = netAmount;
    }

    public static BigDecimal getDefaultAmount() {
        return FdaConstants.DEFAULT_AMOUNT_SCALE_2;
    }

    public String getFundPoolName() {
        return fundPoolName;
    }

    public void setFundPoolName(String fundPoolName) {
        this.fundPoolName = fundPoolName;
    }

    public String getScenarioName() {
        return scenarioName;
    }

    public void setScenarioName(String scenarioName) {
        this.scenarioName = scenarioName;
    }

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

    public BigDecimal getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(BigDecimal grossAmount) {
        this.grossAmount = grossAmount;
    }

    public BigDecimal getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(BigDecimal serviceFee) {
        this.serviceFee = serviceFee;
    }

    public BigDecimal getItemBankSplitPercent() {
        return itemBankSplitPercent;
    }

    public void setItemBankSplitPercent(BigDecimal itemBankSplitPercent) {
        this.itemBankSplitPercent = itemBankSplitPercent;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (null == o || getClass() != o.getClass()) {
            return false;
        }

        SalFundPoolReportDto that = (SalFundPoolReportDto) o;

        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(gradeKto5NumberOfStudents, that.gradeKto5NumberOfStudents)
            .append(grade6to8NumberOfStudents, that.grade6to8NumberOfStudents)
            .append(grade9to12NumberOfStudents, that.grade9to12NumberOfStudents)
            .append(fundPoolName, that.fundPoolName)
            .append(scenarioName, that.scenarioName)
            .append(dateReceived, that.dateReceived)
            .append(assessmentName, that.assessmentName)
            .append(licenseeAccountNumber, that.licenseeAccountNumber)
            .append(licenseeName, that.licenseeName)
            .append(serviceFee, that.serviceFee)
            .append(netAmount, that.netAmount)
            .append(grossAmount, that.grossAmount)
            .append(totalGrossAmount, that.totalGrossAmount)
            .append(itemBankSplitPercent, that.itemBankSplitPercent)
            .append(itemBankGrossAmount, that.itemBankGrossAmount)
            .append(gradeKto5GrossAmount, that.gradeKto5GrossAmount)
            .append(grade6to8GrossAmount, that.grade6to8GrossAmount)
            .append(grade9to12GrossAmount, that.grade9to12GrossAmount)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(fundPoolName)
            .append(scenarioName)
            .append(dateReceived)
            .append(assessmentName)
            .append(licenseeAccountNumber)
            .append(licenseeName)
            .append(serviceFee)
            .append(netAmount)
            .append(grossAmount)
            .append(itemBankSplitPercent)
            .append(itemBankGrossAmount)
            .append(gradeKto5GrossAmount)
            .append(grade6to8GrossAmount)
            .append(grade9to12GrossAmount)
            .append(gradeKto5NumberOfStudents)
            .append(grade6to8NumberOfStudents)
            .append(grade9to12NumberOfStudents)
            .append(totalGrossAmount)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("fundPoolName", fundPoolName)
            .append("scenarioName", scenarioName)
            .append("dateReceived", dateReceived)
            .append("assessmentName", assessmentName)
            .append("licenseeAccountNumber", licenseeAccountNumber)
            .append("licenseeName", licenseeName)
            .append("serviceFee", serviceFee)
            .append("netAmount", netAmount)
            .append("grossAmount", grossAmount)
            .append("itemBankSplitPercent", itemBankSplitPercent)
            .append("gradeKto5GrossAmount", gradeKto5GrossAmount)
            .append("grade6to8GrossAmount", grade6to8GrossAmount)
            .append("grade9to12GrossAmount", grade9to12GrossAmount)
            .append("itemBankGrossAmount", itemBankGrossAmount)
            .append("gradeKto5NumberOfStudents", gradeKto5NumberOfStudents)
            .append("grade6to8NumberOfStudents", grade6to8NumberOfStudents)
            .append("grade9to12NumberOfStudents", grade9to12NumberOfStudents)
            .append("totalGrossAmount", totalGrossAmount)
            .toString();
    }
}
