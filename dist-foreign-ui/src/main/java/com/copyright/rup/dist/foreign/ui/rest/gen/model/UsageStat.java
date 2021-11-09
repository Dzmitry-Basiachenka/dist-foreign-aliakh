package com.copyright.rup.dist.foreign.ui.rest.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Usage statistic
 */
@ApiModel(description = "Usage statistic")
@JsonInclude(JsonInclude.Include.NON_NULL)

public class UsageStat   {
  @JsonProperty("usageId")
  private String usageId;

  @JsonProperty("status")
  private String status;

  @JsonProperty("matchingMs")
  private Integer matchingMs;

  @JsonProperty("rightsMs")
  private Integer rightsMs;

  @JsonProperty("eligibilityMs")
  private Integer eligibilityMs;

  public UsageStat usageId(String usageId) {
    this.usageId = usageId;
    return this;
  }

  /**
   * Get usageId
   * @return usageId
  */
  @ApiModelProperty(value = "")


  public String getUsageId() {
    return usageId;
  }

  public void setUsageId(String usageId) {
    this.usageId = usageId;
  }

  public UsageStat status(String status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
  */
  @ApiModelProperty(value = "")


  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public UsageStat matchingMs(Integer matchingMs) {
    this.matchingMs = matchingMs;
    return this;
  }

  /**
   * Get matchingMs
   * @return matchingMs
  */
  @ApiModelProperty(value = "")


  public Integer getMatchingMs() {
    return matchingMs;
  }

  public void setMatchingMs(Integer matchingMs) {
    this.matchingMs = matchingMs;
  }

  public UsageStat rightsMs(Integer rightsMs) {
    this.rightsMs = rightsMs;
    return this;
  }

  /**
   * Get rightsMs
   * @return rightsMs
  */
  @ApiModelProperty(value = "")


  public Integer getRightsMs() {
    return rightsMs;
  }

  public void setRightsMs(Integer rightsMs) {
    this.rightsMs = rightsMs;
  }

  public UsageStat eligibilityMs(Integer eligibilityMs) {
    this.eligibilityMs = eligibilityMs;
    return this;
  }

  /**
   * Get eligibilityMs
   * @return eligibilityMs
  */
  @ApiModelProperty(value = "")


  public Integer getEligibilityMs() {
    return eligibilityMs;
  }

  public void setEligibilityMs(Integer eligibilityMs) {
    this.eligibilityMs = eligibilityMs;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UsageStat usageStat = (UsageStat) o;
    return Objects.equals(this.usageId, usageStat.usageId) &&
        Objects.equals(this.status, usageStat.status) &&
        Objects.equals(this.matchingMs, usageStat.matchingMs) &&
        Objects.equals(this.rightsMs, usageStat.rightsMs) &&
        Objects.equals(this.eligibilityMs, usageStat.eligibilityMs);
  }

  @Override
  public int hashCode() {
    return Objects.hash(usageId, status, matchingMs, rightsMs, eligibilityMs);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UsageStat {\n");
    
    sb.append("    usageId: ").append(toIndentedString(usageId)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    matchingMs: ").append(toIndentedString(matchingMs)).append("\n");
    sb.append("    rightsMs: ").append(toIndentedString(rightsMs)).append("\n");
    sb.append("    eligibilityMs: ").append(toIndentedString(eligibilityMs)).append("\n");
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

