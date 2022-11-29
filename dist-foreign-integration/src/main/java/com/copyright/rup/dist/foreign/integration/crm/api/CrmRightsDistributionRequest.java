package com.copyright.rup.dist.foreign.integration.crm.api;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.PaidUsage;
import com.copyright.rup.dist.foreign.domain.SalDetailTypeEnum;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Objects;

/**
 * Represents request to CRM.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 03/28/18
 *
 * @author Darya Baraukova
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CrmRightsDistributionRequest {

    @JsonProperty("cccEventId")
    private String cccEventId;

    @JsonProperty("product")
    private String productFamily;

    @JsonProperty("rorAccountNumber")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long rorAccountNumber;

    @JsonProperty("payeeAccountNumber")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long payeeAccountNumber;

    @JsonProperty("royaltyAmount")
    private BigDecimal royaltyAmount;

    @JsonProperty("currency")
    private String currency = "USD";

    @JsonProperty("licenseCreateDate")
    @JsonFormat(pattern = "MM-dd-yyyy'T'hh:mm:ss a")
    private OffsetDateTime licenseCreateDate;

    @JsonProperty("serviceFeeAmount")
    private BigDecimal serviceFeeAmount;

    @JsonProperty("wrWrkInst")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long wrWrkInst;

    @JsonProperty("publicationName")
    private String publicationName;

    @JsonProperty("author")
    private String author;

    @JsonProperty("chapterArticleTitle")
    private String chapterArticleTitle;

    @JsonProperty("omOrderDetailNumber")
    private String omOrderDetailNumber;

    @JsonProperty("totalAmount")
    private BigDecimal totalAmount;

    @JsonProperty("periodEndDate")
    @JsonFormat(pattern = RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT)
    private OffsetDateTime periodEndDate;

    @JsonProperty("publicationDate")
    @JsonFormat(pattern = RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT)
    private LocalDate publicationDate;

    @JsonProperty("numberOfCopies")
    private Integer numberOfCopies;

    @JsonProperty("adjustmentFlag")
    private boolean adjustmentFlag;

    @JsonProperty("passThroughFlag")
    private boolean passThroughFlag = true;

    @JsonProperty("market")
    private String market;

    @JsonProperty("marketPeriodFrom")
    private Integer marketPeriodFrom;

    @JsonProperty("marketPeriodTo")
    private Integer marketPeriodTo;

    @JsonProperty("arAccountNumber")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long arAccountNumber;

    @JsonProperty("serviceNameReporting")
    private String serviceNameReporting;

    @JsonProperty("createUser")
    private String createUser = "FDA Distribution";

    @JsonProperty("status")
    private String status = "D - Distributed";

    @JsonProperty("issueVolumeSeries")
    private String issueVolumeSeries;

    @JsonProperty("grade")
    private String grade;

    @JsonProperty("academicYear")
    private String academicYear;

    @JsonProperty("state")
    private String state;

    @JsonProperty("assessmentType")
    private String assessmentType;

    @JsonProperty("publicationIssueDate")
    private String publicationIssueDate;

    @JsonProperty("distributionDate")
    @JsonFormat(pattern = "MM-dd-yyyy'T'hh:mm:ss a")
    private OffsetDateTime distributionDate;

    @JsonProperty("typeOfUse")
    private String typeOfUse;

    /**
     * Constructor for creating request to the CRM service.
     *
     * @param usage {@link PaidUsage} instance
     */
    public CrmRightsDistributionRequest(PaidUsage usage) {
        Objects.requireNonNull(usage);
        this.cccEventId = usage.getCccEventId();
        this.productFamily = usage.getProductFamily();
        this.rorAccountNumber = usage.getRightsholder().getAccountNumber();
        this.payeeAccountNumber = usage.getPayee().getAccountNumber();
        this.royaltyAmount = usage.getNetAmount();
        this.licenseCreateDate =
            OffsetDateTime.ofInstant(usage.getCreateDate().toInstant(), ZoneId.systemDefault());
        this.serviceFeeAmount = usage.getServiceFeeAmount();
        this.wrWrkInst = usage.getWrWrkInst();
        this.publicationName = usage.getSystemTitle();
        this.omOrderDetailNumber = usage.getId();
        this.totalAmount = usage.getGrossAmount();
        this.periodEndDate = usage.getPeriodEndDate();
        this.publicationDate = usage.getPublicationDate();
        this.numberOfCopies = usage.getNumberOfCopies();
        this.market = usage.getMarket();
        this.marketPeriodFrom = usage.getMarketPeriodFrom();
        this.marketPeriodTo = usage.getMarketPeriodTo();
        this.arAccountNumber = usage.getRroAccountNumber();
        this.distributionDate = usage.getDistributionDate();
        if (FdaConstants.FAS_FAS2_PRODUCT_FAMILY_SET.contains(usage.getProductFamily())) {
            this.serviceNameReporting = "FAS";
            this.author = usage.getAuthor();
            this.chapterArticleTitle = usage.getArticle();
        }
        if (FdaConstants.SAL_PRODUCT_FAMILY.equals(usage.getProductFamily())) {
            this.chapterArticleTitle = usage.getSalUsage().getReportedArticle();
            this.issueVolumeSeries = usage.getSalUsage().getReportedVolNumberSeries();
            this.grade = usage.getSalUsage().getGrade();
            this.academicYear = usage.getSalUsage().getCoverageYear();
            this.author = usage.getSalUsage().getReportedAuthor();
            this.state = usage.getSalUsage().getStates();
            this.assessmentType = usage.getSalUsage().getAssessmentType();
            this.publicationIssueDate = usage.getSalUsage().getReportedPublicationDate();
            this.typeOfUse = SalDetailTypeEnum.IB == usage.getSalUsage().getDetailType()
                ? "Assessment Item Bank" : "Assessment";
        }
    }

    public String getCccEventId() {
        return cccEventId;
    }

    public void setCccEventId(String cccEventId) {
        this.cccEventId = cccEventId;
    }

    public String getProductFamily() {
        return productFamily;
    }

    public void setProductFamily(String productFamily) {
        this.productFamily = productFamily;
    }

    public Long getRorAccountNumber() {
        return rorAccountNumber;
    }

    public void setRorAccountNumber(Long rorAccountNumber) {
        this.rorAccountNumber = rorAccountNumber;
    }

    public Long getPayeeAccountNumber() {
        return payeeAccountNumber;
    }

    public void setPayeeAccountNumber(Long payeeAccountNumber) {
        this.payeeAccountNumber = payeeAccountNumber;
    }

    public BigDecimal getRoyaltyAmount() {
        return royaltyAmount;
    }

    public void setRoyaltyAmount(BigDecimal royaltyAmount) {
        this.royaltyAmount = royaltyAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public OffsetDateTime getLicenseCreateDate() {
        return licenseCreateDate;
    }

    public void setLicenseCreateDate(OffsetDateTime licenseCreateDate) {
        this.licenseCreateDate = licenseCreateDate;
    }

    public BigDecimal getServiceFeeAmount() {
        return serviceFeeAmount;
    }

    public void setServiceFeeAmount(BigDecimal serviceFeeAmount) {
        this.serviceFeeAmount = serviceFeeAmount;
    }

    public Long getWrWrkInst() {
        return wrWrkInst;
    }

    public void setWrWrkInst(Long wrWrkInst) {
        this.wrWrkInst = wrWrkInst;
    }

    public String getPublicationName() {
        return publicationName;
    }

    public void setPublicationName(String publicationName) {
        this.publicationName = publicationName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getChapterArticleTitle() {
        return chapterArticleTitle;
    }

    public void setChapterArticleTitle(String chapterArticleTitle) {
        this.chapterArticleTitle = chapterArticleTitle;
    }

    public String getOmOrderDetailNumber() {
        return omOrderDetailNumber;
    }

    public void setOmOrderDetailNumber(String omOrderDetailNumber) {
        this.omOrderDetailNumber = omOrderDetailNumber;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public OffsetDateTime getPeriodEndDate() {
        return periodEndDate;
    }

    public void setPeriodEndDate(OffsetDateTime periodEndDate) {
        this.periodEndDate = periodEndDate;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Integer getNumberOfCopies() {
        return numberOfCopies;
    }

    public void setNumberOfCopies(Integer numberOfCopies) {
        this.numberOfCopies = numberOfCopies;
    }

    public boolean isAdjustmentFlag() {
        return adjustmentFlag;
    }

    public void setAdjustmentFlag(boolean adjustmentFlag) {
        this.adjustmentFlag = adjustmentFlag;
    }

    public boolean isPassThroughFlag() {
        return passThroughFlag;
    }

    public void setPassThroughFlag(boolean passThroughFlag) {
        this.passThroughFlag = passThroughFlag;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public Integer getMarketPeriodFrom() {
        return marketPeriodFrom;
    }

    public void setMarketPeriodFrom(Integer marketPeriodFrom) {
        this.marketPeriodFrom = marketPeriodFrom;
    }

    public Integer getMarketPeriodTo() {
        return marketPeriodTo;
    }

    public void setMarketPeriodTo(Integer marketPeriodTo) {
        this.marketPeriodTo = marketPeriodTo;
    }

    public Long getArAccountNumber() {
        return arAccountNumber;
    }

    public void setArAccountNumber(Long arAccountNumber) {
        this.arAccountNumber = arAccountNumber;
    }

    public String getServiceNameReporting() {
        return serviceNameReporting;
    }

    public void setServiceNameReporting(String serviceNameReporting) {
        this.serviceNameReporting = serviceNameReporting;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIssueVolumeSeries() {
        return issueVolumeSeries;
    }

    public void setIssueVolumeSeries(String issueVolumeSeries) {
        this.issueVolumeSeries = issueVolumeSeries;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public String getPublicationIssueDate() {
        return publicationIssueDate;
    }

    public void setPublicationIssueDate(String publicationIssueDate) {
        this.publicationIssueDate = publicationIssueDate;
    }

    public OffsetDateTime getDistributionDate() {
        return distributionDate;
    }

    public void setDistributionDate(OffsetDateTime distributionDate) {
        this.distributionDate = distributionDate;
    }

    public String getTypeOfUse() {
        return typeOfUse;
    }

    public void setTypeOfUse(String typeOfUse) {
        this.typeOfUse = typeOfUse;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || this.getClass() != obj.getClass()) {
            return false;
        }
        CrmRightsDistributionRequest that = (CrmRightsDistributionRequest) obj;
        return new EqualsBuilder()
            .append(this.cccEventId, that.cccEventId)
            .append(this.productFamily, that.productFamily)
            .append(this.rorAccountNumber, that.rorAccountNumber)
            .append(this.payeeAccountNumber, that.payeeAccountNumber)
            .append(this.royaltyAmount, that.royaltyAmount)
            .append(this.currency, that.currency)
            .append(this.licenseCreateDate, that.licenseCreateDate)
            .append(this.serviceFeeAmount, that.serviceFeeAmount)
            .append(this.wrWrkInst, that.wrWrkInst)
            .append(this.publicationName, that.publicationName)
            .append(this.author, that.author)
            .append(this.chapterArticleTitle, that.chapterArticleTitle)
            .append(this.omOrderDetailNumber, that.omOrderDetailNumber)
            .append(this.totalAmount, that.totalAmount)
            .append(this.periodEndDate, that.periodEndDate)
            .append(this.publicationDate, that.publicationDate)
            .append(this.numberOfCopies, that.numberOfCopies)
            .append(this.passThroughFlag, that.passThroughFlag)
            .append(this.adjustmentFlag, that.adjustmentFlag)
            .append(this.market, that.market)
            .append(this.marketPeriodFrom, that.marketPeriodFrom)
            .append(this.marketPeriodTo, that.marketPeriodTo)
            .append(this.arAccountNumber, that.arAccountNumber)
            .append(this.serviceNameReporting, that.serviceNameReporting)
            .append(this.createUser, that.createUser)
            .append(this.status, that.status)
            .append(this.issueVolumeSeries, that.issueVolumeSeries)
            .append(this.grade, that.grade)
            .append(this.academicYear, that.academicYear)
            .append(this.state, that.state)
            .append(this.assessmentType, that.assessmentType)
            .append(this.publicationIssueDate, that.publicationIssueDate)
            .append(this.distributionDate, that.distributionDate)
            .append(this.typeOfUse, that.typeOfUse)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(cccEventId)
            .append(productFamily)
            .append(rorAccountNumber)
            .append(payeeAccountNumber)
            .append(royaltyAmount)
            .append(currency)
            .append(licenseCreateDate)
            .append(serviceFeeAmount)
            .append(wrWrkInst)
            .append(publicationName)
            .append(author)
            .append(chapterArticleTitle)
            .append(omOrderDetailNumber)
            .append(totalAmount)
            .append(periodEndDate)
            .append(publicationDate)
            .append(numberOfCopies)
            .append(passThroughFlag)
            .append(adjustmentFlag)
            .append(market)
            .append(marketPeriodFrom)
            .append(marketPeriodTo)
            .append(arAccountNumber)
            .append(serviceNameReporting)
            .append(createUser)
            .append(status)
            .append(issueVolumeSeries)
            .append(grade)
            .append(academicYear)
            .append(state)
            .append(assessmentType)
            .append(publicationIssueDate)
            .append(distributionDate)
            .append(typeOfUse)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("cccEventId", cccEventId)
            .append("productFamily", productFamily)
            .append("rorAccountNumber", rorAccountNumber)
            .append("payeeAccountNumber", payeeAccountNumber)
            .append("royaltyAmount", royaltyAmount)
            .append("currency", currency)
            .append("licenseCreateDate", licenseCreateDate)
            .append("serviceFeeAmount", serviceFeeAmount)
            .append("wrWrkInst", wrWrkInst)
            .append("publicationName", publicationName)
            .append("author", author)
            .append("chapterArticleTitle", chapterArticleTitle)
            .append("omOrderDetailNumber", omOrderDetailNumber)
            .append("totalAmount", totalAmount)
            .append("periodEndDate", periodEndDate)
            .append("publicationDate", publicationDate)
            .append("numberOfCopies", numberOfCopies)
            .append("passThroughFlag", passThroughFlag)
            .append("adjustmentFlag", adjustmentFlag)
            .append("market", market)
            .append("marketPeriodFrom", marketPeriodFrom)
            .append("marketPeriodTo", marketPeriodTo)
            .append("arAccountNumber", arAccountNumber)
            .append("serviceNameReporting", serviceNameReporting)
            .append("createUser", createUser)
            .append("status", status)
            .append("issueVolumeSeries", issueVolumeSeries)
            .append("grade", grade)
            .append("academicYear", academicYear)
            .append("state", state)
            .append("assessmentType", assessmentType)
            .append("publicationIssueDate", publicationIssueDate)
            .append("distributionDate", distributionDate)
            .append("typeOfUse", typeOfUse)
            .toString();
    }
}
