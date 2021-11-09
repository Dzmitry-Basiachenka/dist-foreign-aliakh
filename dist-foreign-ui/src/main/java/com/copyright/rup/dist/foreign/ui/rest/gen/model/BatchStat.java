package com.copyright.rup.dist.foreign.ui.rest.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Batch statistic
 */
@ApiModel(description = "Batch statistic")
@JsonInclude(JsonInclude.Include.NON_NULL)

public class BatchStat   {
  @JsonProperty("batchName")
  private String batchName;

  @JsonProperty("totalCount")
  private Integer totalCount;

  @JsonProperty("totalAmount")
  private BigDecimal totalAmount;

  @JsonProperty("matchedCount")
  private Integer matchedCount;

  @JsonProperty("matchedAmount")
  private BigDecimal matchedAmount;

  @JsonProperty("matchedPercent")
  private BigDecimal matchedPercent;

  @JsonProperty("worksNotFoundCount")
  private Integer worksNotFoundCount;

  @JsonProperty("worksNotFoundAmount")
  private BigDecimal worksNotFoundAmount;

  @JsonProperty("worksNotFoundPercent")
  private BigDecimal worksNotFoundPercent;

  @JsonProperty("multipleMatchingCount")
  private Integer multipleMatchingCount;

  @JsonProperty("multipleMatchingAmount")
  private BigDecimal multipleMatchingAmount;

  @JsonProperty("multipleMatchingPercent")
  private BigDecimal multipleMatchingPercent;

  @JsonProperty("ntsWithdrawnCount")
  private Integer ntsWithdrawnCount;

  @JsonProperty("ntsWithdrawnAmount")
  private BigDecimal ntsWithdrawnAmount;

  @JsonProperty("ntsWithdrawnPercent")
  private BigDecimal ntsWithdrawnPercent;

  @JsonProperty("rhNotFoundCount")
  private Integer rhNotFoundCount;

  @JsonProperty("rhNotFoundAmount")
  private BigDecimal rhNotFoundAmount;

  @JsonProperty("rhNotFoundPercent")
  private BigDecimal rhNotFoundPercent;

  @JsonProperty("rhFoundCount")
  private Integer rhFoundCount;

  @JsonProperty("rhFoundAmount")
  private BigDecimal rhFoundAmount;

  @JsonProperty("rhFoundPercent")
  private BigDecimal rhFoundPercent;

  @JsonProperty("eligibleCount")
  private Integer eligibleCount;

  @JsonProperty("eligibleAmount")
  private BigDecimal eligibleAmount;

  @JsonProperty("eligiblePercent")
  private BigDecimal eligiblePercent;

  @JsonProperty("sendForRaCount")
  private Integer sendForRaCount;

  @JsonProperty("sendForRaAmount")
  private BigDecimal sendForRaAmount;

  @JsonProperty("sendForRaPercent")
  private BigDecimal sendForRaPercent;

  @JsonProperty("paidCount")
  private Integer paidCount;

  @JsonProperty("paidAmount")
  private BigDecimal paidAmount;

  @JsonProperty("paidPercent")
  private BigDecimal paidPercent;

  public BatchStat batchName(String batchName) {
    this.batchName = batchName;
    return this;
  }

  /**
   * Get batchName
   * @return batchName
  */
  @ApiModelProperty(value = "")


  public String getBatchName() {
    return batchName;
  }

  public void setBatchName(String batchName) {
    this.batchName = batchName;
  }

  public BatchStat totalCount(Integer totalCount) {
    this.totalCount = totalCount;
    return this;
  }

  /**
   * Get totalCount
   * @return totalCount
  */
  @ApiModelProperty(value = "")


  public Integer getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(Integer totalCount) {
    this.totalCount = totalCount;
  }

  public BatchStat totalAmount(BigDecimal totalAmount) {
    this.totalAmount = totalAmount;
    return this;
  }

  /**
   * Get totalAmount
   * @return totalAmount
  */
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(BigDecimal totalAmount) {
    this.totalAmount = totalAmount;
  }

  public BatchStat matchedCount(Integer matchedCount) {
    this.matchedCount = matchedCount;
    return this;
  }

  /**
   * Get matchedCount
   * @return matchedCount
  */
  @ApiModelProperty(value = "")


  public Integer getMatchedCount() {
    return matchedCount;
  }

  public void setMatchedCount(Integer matchedCount) {
    this.matchedCount = matchedCount;
  }

  public BatchStat matchedAmount(BigDecimal matchedAmount) {
    this.matchedAmount = matchedAmount;
    return this;
  }

  /**
   * Get matchedAmount
   * @return matchedAmount
  */
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getMatchedAmount() {
    return matchedAmount;
  }

  public void setMatchedAmount(BigDecimal matchedAmount) {
    this.matchedAmount = matchedAmount;
  }

  public BatchStat matchedPercent(BigDecimal matchedPercent) {
    this.matchedPercent = matchedPercent;
    return this;
  }

  /**
   * Get matchedPercent
   * @return matchedPercent
  */
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getMatchedPercent() {
    return matchedPercent;
  }

  public void setMatchedPercent(BigDecimal matchedPercent) {
    this.matchedPercent = matchedPercent;
  }

  public BatchStat worksNotFoundCount(Integer worksNotFoundCount) {
    this.worksNotFoundCount = worksNotFoundCount;
    return this;
  }

  /**
   * Get worksNotFoundCount
   * @return worksNotFoundCount
  */
  @ApiModelProperty(value = "")


  public Integer getWorksNotFoundCount() {
    return worksNotFoundCount;
  }

  public void setWorksNotFoundCount(Integer worksNotFoundCount) {
    this.worksNotFoundCount = worksNotFoundCount;
  }

  public BatchStat worksNotFoundAmount(BigDecimal worksNotFoundAmount) {
    this.worksNotFoundAmount = worksNotFoundAmount;
    return this;
  }

  /**
   * Get worksNotFoundAmount
   * @return worksNotFoundAmount
  */
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getWorksNotFoundAmount() {
    return worksNotFoundAmount;
  }

  public void setWorksNotFoundAmount(BigDecimal worksNotFoundAmount) {
    this.worksNotFoundAmount = worksNotFoundAmount;
  }

  public BatchStat worksNotFoundPercent(BigDecimal worksNotFoundPercent) {
    this.worksNotFoundPercent = worksNotFoundPercent;
    return this;
  }

  /**
   * Get worksNotFoundPercent
   * @return worksNotFoundPercent
  */
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getWorksNotFoundPercent() {
    return worksNotFoundPercent;
  }

  public void setWorksNotFoundPercent(BigDecimal worksNotFoundPercent) {
    this.worksNotFoundPercent = worksNotFoundPercent;
  }

  public BatchStat multipleMatchingCount(Integer multipleMatchingCount) {
    this.multipleMatchingCount = multipleMatchingCount;
    return this;
  }

  /**
   * Get multipleMatchingCount
   * @return multipleMatchingCount
  */
  @ApiModelProperty(value = "")


  public Integer getMultipleMatchingCount() {
    return multipleMatchingCount;
  }

  public void setMultipleMatchingCount(Integer multipleMatchingCount) {
    this.multipleMatchingCount = multipleMatchingCount;
  }

  public BatchStat multipleMatchingAmount(BigDecimal multipleMatchingAmount) {
    this.multipleMatchingAmount = multipleMatchingAmount;
    return this;
  }

  /**
   * Get multipleMatchingAmount
   * @return multipleMatchingAmount
  */
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getMultipleMatchingAmount() {
    return multipleMatchingAmount;
  }

  public void setMultipleMatchingAmount(BigDecimal multipleMatchingAmount) {
    this.multipleMatchingAmount = multipleMatchingAmount;
  }

  public BatchStat multipleMatchingPercent(BigDecimal multipleMatchingPercent) {
    this.multipleMatchingPercent = multipleMatchingPercent;
    return this;
  }

  /**
   * Get multipleMatchingPercent
   * @return multipleMatchingPercent
  */
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getMultipleMatchingPercent() {
    return multipleMatchingPercent;
  }

  public void setMultipleMatchingPercent(BigDecimal multipleMatchingPercent) {
    this.multipleMatchingPercent = multipleMatchingPercent;
  }

  public BatchStat ntsWithdrawnCount(Integer ntsWithdrawnCount) {
    this.ntsWithdrawnCount = ntsWithdrawnCount;
    return this;
  }

  /**
   * Get ntsWithdrawnCount
   * @return ntsWithdrawnCount
  */
  @ApiModelProperty(value = "")


  public Integer getNtsWithdrawnCount() {
    return ntsWithdrawnCount;
  }

  public void setNtsWithdrawnCount(Integer ntsWithdrawnCount) {
    this.ntsWithdrawnCount = ntsWithdrawnCount;
  }

  public BatchStat ntsWithdrawnAmount(BigDecimal ntsWithdrawnAmount) {
    this.ntsWithdrawnAmount = ntsWithdrawnAmount;
    return this;
  }

  /**
   * Get ntsWithdrawnAmount
   * @return ntsWithdrawnAmount
  */
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getNtsWithdrawnAmount() {
    return ntsWithdrawnAmount;
  }

  public void setNtsWithdrawnAmount(BigDecimal ntsWithdrawnAmount) {
    this.ntsWithdrawnAmount = ntsWithdrawnAmount;
  }

  public BatchStat ntsWithdrawnPercent(BigDecimal ntsWithdrawnPercent) {
    this.ntsWithdrawnPercent = ntsWithdrawnPercent;
    return this;
  }

  /**
   * Get ntsWithdrawnPercent
   * @return ntsWithdrawnPercent
  */
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getNtsWithdrawnPercent() {
    return ntsWithdrawnPercent;
  }

  public void setNtsWithdrawnPercent(BigDecimal ntsWithdrawnPercent) {
    this.ntsWithdrawnPercent = ntsWithdrawnPercent;
  }

  public BatchStat rhNotFoundCount(Integer rhNotFoundCount) {
    this.rhNotFoundCount = rhNotFoundCount;
    return this;
  }

  /**
   * Get rhNotFoundCount
   * @return rhNotFoundCount
  */
  @ApiModelProperty(value = "")


  public Integer getRhNotFoundCount() {
    return rhNotFoundCount;
  }

  public void setRhNotFoundCount(Integer rhNotFoundCount) {
    this.rhNotFoundCount = rhNotFoundCount;
  }

  public BatchStat rhNotFoundAmount(BigDecimal rhNotFoundAmount) {
    this.rhNotFoundAmount = rhNotFoundAmount;
    return this;
  }

  /**
   * Get rhNotFoundAmount
   * @return rhNotFoundAmount
  */
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getRhNotFoundAmount() {
    return rhNotFoundAmount;
  }

  public void setRhNotFoundAmount(BigDecimal rhNotFoundAmount) {
    this.rhNotFoundAmount = rhNotFoundAmount;
  }

  public BatchStat rhNotFoundPercent(BigDecimal rhNotFoundPercent) {
    this.rhNotFoundPercent = rhNotFoundPercent;
    return this;
  }

  /**
   * Get rhNotFoundPercent
   * @return rhNotFoundPercent
  */
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getRhNotFoundPercent() {
    return rhNotFoundPercent;
  }

  public void setRhNotFoundPercent(BigDecimal rhNotFoundPercent) {
    this.rhNotFoundPercent = rhNotFoundPercent;
  }

  public BatchStat rhFoundCount(Integer rhFoundCount) {
    this.rhFoundCount = rhFoundCount;
    return this;
  }

  /**
   * Get rhFoundCount
   * @return rhFoundCount
  */
  @ApiModelProperty(value = "")


  public Integer getRhFoundCount() {
    return rhFoundCount;
  }

  public void setRhFoundCount(Integer rhFoundCount) {
    this.rhFoundCount = rhFoundCount;
  }

  public BatchStat rhFoundAmount(BigDecimal rhFoundAmount) {
    this.rhFoundAmount = rhFoundAmount;
    return this;
  }

  /**
   * Get rhFoundAmount
   * @return rhFoundAmount
  */
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getRhFoundAmount() {
    return rhFoundAmount;
  }

  public void setRhFoundAmount(BigDecimal rhFoundAmount) {
    this.rhFoundAmount = rhFoundAmount;
  }

  public BatchStat rhFoundPercent(BigDecimal rhFoundPercent) {
    this.rhFoundPercent = rhFoundPercent;
    return this;
  }

  /**
   * Get rhFoundPercent
   * @return rhFoundPercent
  */
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getRhFoundPercent() {
    return rhFoundPercent;
  }

  public void setRhFoundPercent(BigDecimal rhFoundPercent) {
    this.rhFoundPercent = rhFoundPercent;
  }

  public BatchStat eligibleCount(Integer eligibleCount) {
    this.eligibleCount = eligibleCount;
    return this;
  }

  /**
   * Get eligibleCount
   * @return eligibleCount
  */
  @ApiModelProperty(value = "")


  public Integer getEligibleCount() {
    return eligibleCount;
  }

  public void setEligibleCount(Integer eligibleCount) {
    this.eligibleCount = eligibleCount;
  }

  public BatchStat eligibleAmount(BigDecimal eligibleAmount) {
    this.eligibleAmount = eligibleAmount;
    return this;
  }

  /**
   * Get eligibleAmount
   * @return eligibleAmount
  */
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getEligibleAmount() {
    return eligibleAmount;
  }

  public void setEligibleAmount(BigDecimal eligibleAmount) {
    this.eligibleAmount = eligibleAmount;
  }

  public BatchStat eligiblePercent(BigDecimal eligiblePercent) {
    this.eligiblePercent = eligiblePercent;
    return this;
  }

  /**
   * Get eligiblePercent
   * @return eligiblePercent
  */
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getEligiblePercent() {
    return eligiblePercent;
  }

  public void setEligiblePercent(BigDecimal eligiblePercent) {
    this.eligiblePercent = eligiblePercent;
  }

  public BatchStat sendForRaCount(Integer sendForRaCount) {
    this.sendForRaCount = sendForRaCount;
    return this;
  }

  /**
   * Get sendForRaCount
   * @return sendForRaCount
  */
  @ApiModelProperty(value = "")


  public Integer getSendForRaCount() {
    return sendForRaCount;
  }

  public void setSendForRaCount(Integer sendForRaCount) {
    this.sendForRaCount = sendForRaCount;
  }

  public BatchStat sendForRaAmount(BigDecimal sendForRaAmount) {
    this.sendForRaAmount = sendForRaAmount;
    return this;
  }

  /**
   * Get sendForRaAmount
   * @return sendForRaAmount
  */
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getSendForRaAmount() {
    return sendForRaAmount;
  }

  public void setSendForRaAmount(BigDecimal sendForRaAmount) {
    this.sendForRaAmount = sendForRaAmount;
  }

  public BatchStat sendForRaPercent(BigDecimal sendForRaPercent) {
    this.sendForRaPercent = sendForRaPercent;
    return this;
  }

  /**
   * Get sendForRaPercent
   * @return sendForRaPercent
  */
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getSendForRaPercent() {
    return sendForRaPercent;
  }

  public void setSendForRaPercent(BigDecimal sendForRaPercent) {
    this.sendForRaPercent = sendForRaPercent;
  }

  public BatchStat paidCount(Integer paidCount) {
    this.paidCount = paidCount;
    return this;
  }

  /**
   * Get paidCount
   * @return paidCount
  */
  @ApiModelProperty(value = "")


  public Integer getPaidCount() {
    return paidCount;
  }

  public void setPaidCount(Integer paidCount) {
    this.paidCount = paidCount;
  }

  public BatchStat paidAmount(BigDecimal paidAmount) {
    this.paidAmount = paidAmount;
    return this;
  }

  /**
   * Get paidAmount
   * @return paidAmount
  */
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getPaidAmount() {
    return paidAmount;
  }

  public void setPaidAmount(BigDecimal paidAmount) {
    this.paidAmount = paidAmount;
  }

  public BatchStat paidPercent(BigDecimal paidPercent) {
    this.paidPercent = paidPercent;
    return this;
  }

  /**
   * Get paidPercent
   * @return paidPercent
  */
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getPaidPercent() {
    return paidPercent;
  }

  public void setPaidPercent(BigDecimal paidPercent) {
    this.paidPercent = paidPercent;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BatchStat batchStat = (BatchStat) o;
    return Objects.equals(this.batchName, batchStat.batchName) &&
        Objects.equals(this.totalCount, batchStat.totalCount) &&
        Objects.equals(this.totalAmount, batchStat.totalAmount) &&
        Objects.equals(this.matchedCount, batchStat.matchedCount) &&
        Objects.equals(this.matchedAmount, batchStat.matchedAmount) &&
        Objects.equals(this.matchedPercent, batchStat.matchedPercent) &&
        Objects.equals(this.worksNotFoundCount, batchStat.worksNotFoundCount) &&
        Objects.equals(this.worksNotFoundAmount, batchStat.worksNotFoundAmount) &&
        Objects.equals(this.worksNotFoundPercent, batchStat.worksNotFoundPercent) &&
        Objects.equals(this.multipleMatchingCount, batchStat.multipleMatchingCount) &&
        Objects.equals(this.multipleMatchingAmount, batchStat.multipleMatchingAmount) &&
        Objects.equals(this.multipleMatchingPercent, batchStat.multipleMatchingPercent) &&
        Objects.equals(this.ntsWithdrawnCount, batchStat.ntsWithdrawnCount) &&
        Objects.equals(this.ntsWithdrawnAmount, batchStat.ntsWithdrawnAmount) &&
        Objects.equals(this.ntsWithdrawnPercent, batchStat.ntsWithdrawnPercent) &&
        Objects.equals(this.rhNotFoundCount, batchStat.rhNotFoundCount) &&
        Objects.equals(this.rhNotFoundAmount, batchStat.rhNotFoundAmount) &&
        Objects.equals(this.rhNotFoundPercent, batchStat.rhNotFoundPercent) &&
        Objects.equals(this.rhFoundCount, batchStat.rhFoundCount) &&
        Objects.equals(this.rhFoundAmount, batchStat.rhFoundAmount) &&
        Objects.equals(this.rhFoundPercent, batchStat.rhFoundPercent) &&
        Objects.equals(this.eligibleCount, batchStat.eligibleCount) &&
        Objects.equals(this.eligibleAmount, batchStat.eligibleAmount) &&
        Objects.equals(this.eligiblePercent, batchStat.eligiblePercent) &&
        Objects.equals(this.sendForRaCount, batchStat.sendForRaCount) &&
        Objects.equals(this.sendForRaAmount, batchStat.sendForRaAmount) &&
        Objects.equals(this.sendForRaPercent, batchStat.sendForRaPercent) &&
        Objects.equals(this.paidCount, batchStat.paidCount) &&
        Objects.equals(this.paidAmount, batchStat.paidAmount) &&
        Objects.equals(this.paidPercent, batchStat.paidPercent);
  }

  @Override
  public int hashCode() {
    return Objects.hash(batchName, totalCount, totalAmount, matchedCount, matchedAmount, matchedPercent, worksNotFoundCount, worksNotFoundAmount, worksNotFoundPercent, multipleMatchingCount, multipleMatchingAmount, multipleMatchingPercent, ntsWithdrawnCount, ntsWithdrawnAmount, ntsWithdrawnPercent, rhNotFoundCount, rhNotFoundAmount, rhNotFoundPercent, rhFoundCount, rhFoundAmount, rhFoundPercent, eligibleCount, eligibleAmount, eligiblePercent, sendForRaCount, sendForRaAmount, sendForRaPercent, paidCount, paidAmount, paidPercent);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BatchStat {\n");
    
    sb.append("    batchName: ").append(toIndentedString(batchName)).append("\n");
    sb.append("    totalCount: ").append(toIndentedString(totalCount)).append("\n");
    sb.append("    totalAmount: ").append(toIndentedString(totalAmount)).append("\n");
    sb.append("    matchedCount: ").append(toIndentedString(matchedCount)).append("\n");
    sb.append("    matchedAmount: ").append(toIndentedString(matchedAmount)).append("\n");
    sb.append("    matchedPercent: ").append(toIndentedString(matchedPercent)).append("\n");
    sb.append("    worksNotFoundCount: ").append(toIndentedString(worksNotFoundCount)).append("\n");
    sb.append("    worksNotFoundAmount: ").append(toIndentedString(worksNotFoundAmount)).append("\n");
    sb.append("    worksNotFoundPercent: ").append(toIndentedString(worksNotFoundPercent)).append("\n");
    sb.append("    multipleMatchingCount: ").append(toIndentedString(multipleMatchingCount)).append("\n");
    sb.append("    multipleMatchingAmount: ").append(toIndentedString(multipleMatchingAmount)).append("\n");
    sb.append("    multipleMatchingPercent: ").append(toIndentedString(multipleMatchingPercent)).append("\n");
    sb.append("    ntsWithdrawnCount: ").append(toIndentedString(ntsWithdrawnCount)).append("\n");
    sb.append("    ntsWithdrawnAmount: ").append(toIndentedString(ntsWithdrawnAmount)).append("\n");
    sb.append("    ntsWithdrawnPercent: ").append(toIndentedString(ntsWithdrawnPercent)).append("\n");
    sb.append("    rhNotFoundCount: ").append(toIndentedString(rhNotFoundCount)).append("\n");
    sb.append("    rhNotFoundAmount: ").append(toIndentedString(rhNotFoundAmount)).append("\n");
    sb.append("    rhNotFoundPercent: ").append(toIndentedString(rhNotFoundPercent)).append("\n");
    sb.append("    rhFoundCount: ").append(toIndentedString(rhFoundCount)).append("\n");
    sb.append("    rhFoundAmount: ").append(toIndentedString(rhFoundAmount)).append("\n");
    sb.append("    rhFoundPercent: ").append(toIndentedString(rhFoundPercent)).append("\n");
    sb.append("    eligibleCount: ").append(toIndentedString(eligibleCount)).append("\n");
    sb.append("    eligibleAmount: ").append(toIndentedString(eligibleAmount)).append("\n");
    sb.append("    eligiblePercent: ").append(toIndentedString(eligiblePercent)).append("\n");
    sb.append("    sendForRaCount: ").append(toIndentedString(sendForRaCount)).append("\n");
    sb.append("    sendForRaAmount: ").append(toIndentedString(sendForRaAmount)).append("\n");
    sb.append("    sendForRaPercent: ").append(toIndentedString(sendForRaPercent)).append("\n");
    sb.append("    paidCount: ").append(toIndentedString(paidCount)).append("\n");
    sb.append("    paidAmount: ").append(toIndentedString(paidAmount)).append("\n");
    sb.append("    paidPercent: ").append(toIndentedString(paidPercent)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

