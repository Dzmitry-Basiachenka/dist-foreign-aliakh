package com.copyright.rup.dist.foreign.ui.rest.gen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Batch statistic
 **/

/**
 * Batch statistic
 */
@ApiModel(description = "Batch statistic")
@JsonInclude(JsonInclude.Include.NON_NULL)

public class BatchStat   {
    @JsonProperty("batchName")
    




    private String batchName = null;

    @JsonProperty("totalCount")
    




    private Integer totalCount = null;

    @JsonProperty("loadedCount")
    




    private Integer loadedCount = null;

    @JsonProperty("loadedAmount")
    
@Valid



    private BigDecimal loadedAmount = null;

    @JsonProperty("loadedPercent")
    
@Valid



    private BigDecimal loadedPercent = null;

    @JsonProperty("createdCount")
    




    private Integer createdCount = null;

    @JsonProperty("createdAmount")
    
@Valid



    private BigDecimal createdAmount = null;

    @JsonProperty("createdPercent")
    
@Valid



    private BigDecimal createdPercent = null;

    @JsonProperty("matchedCount")
    




    private Integer matchedCount = null;

    @JsonProperty("matchedAmount")
    
@Valid



    private BigDecimal matchedAmount = null;

    @JsonProperty("matchedPercent")
    
@Valid



    private BigDecimal matchedPercent = null;

    @JsonProperty("worksNotFoundCount")
    




    private Integer worksNotFoundCount = null;

    @JsonProperty("worksNotFoundAmount")
    
@Valid



    private BigDecimal worksNotFoundAmount = null;

    @JsonProperty("worksNotFoundPercent")
    
@Valid



    private BigDecimal worksNotFoundPercent = null;

    @JsonProperty("multipleMatchingCount")
    




    private Integer multipleMatchingCount = null;

    @JsonProperty("multipleMatchingAmount")
    
@Valid



    private BigDecimal multipleMatchingAmount = null;

    @JsonProperty("multipleMatchingPercent")
    
@Valid



    private BigDecimal multipleMatchingPercent = null;

    @JsonProperty("ntsWithdrawnCount")
    




    private Integer ntsWithdrawnCount = null;

    @JsonProperty("ntsWithdrawnAmount")
    
@Valid



    private BigDecimal ntsWithdrawnAmount = null;

    @JsonProperty("ntsWithdrawnPercent")
    
@Valid



    private BigDecimal ntsWithdrawnPercent = null;

    @JsonProperty("rhNotFoundCount")
    




    private Integer rhNotFoundCount = null;

    @JsonProperty("rhNotFoundAmount")
    
@Valid



    private BigDecimal rhNotFoundAmount = null;

    @JsonProperty("rhNotFoundPercent")
    
@Valid



    private BigDecimal rhNotFoundPercent = null;

    @JsonProperty("rhFoundCount")
    




    private Integer rhFoundCount = null;

    @JsonProperty("rhFoundAmount")
    
@Valid



    private BigDecimal rhFoundAmount = null;

    @JsonProperty("rhFoundPercent")
    
@Valid



    private BigDecimal rhFoundPercent = null;

    @JsonProperty("eligibleCount")
    




    private Integer eligibleCount = null;

    @JsonProperty("eligibleAmount")
    
@Valid



    private BigDecimal eligibleAmount = null;

    @JsonProperty("eligiblePercent")
    
@Valid



    private BigDecimal eligiblePercent = null;

    @JsonProperty("sendForRaCount")
    




    private Integer sendForRaCount = null;

    @JsonProperty("sendForRaAmount")
    
@Valid



    private BigDecimal sendForRaAmount = null;

    @JsonProperty("sendForRaPercent")
    
@Valid



    private BigDecimal sendForRaPercent = null;

    @JsonProperty("paidCount")
    




    private Integer paidCount = null;

    @JsonProperty("paidAmount")
    
@Valid



    private BigDecimal paidAmount = null;

    @JsonProperty("paidPercent")
    
@Valid



    private BigDecimal paidPercent = null;

    public BatchStat batchName(String batchName) {
        this.batchName = batchName;
        return this;
    }

     /**
     * Get batchName
     * @return batchName
    **/
    @ApiModelProperty(value = "")
    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        if(batchName == null) {
            this.batchName = null;
        } else {
            this.batchName = batchName;
        }
    }

    public BatchStat totalCount(Integer totalCount) {
        this.totalCount = totalCount;
        return this;
    }

     /**
     * Get totalCount
     * @return totalCount
    **/
    @ApiModelProperty(value = "")
    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        if(totalCount == null) {
            this.totalCount = null;
        } else {
            this.totalCount = totalCount;
        }
    }

    public BatchStat loadedCount(Integer loadedCount) {
        this.loadedCount = loadedCount;
        return this;
    }

     /**
     * Get loadedCount
     * @return loadedCount
    **/
    @ApiModelProperty(value = "")
    public Integer getLoadedCount() {
        return loadedCount;
    }

    public void setLoadedCount(Integer loadedCount) {
        if(loadedCount == null) {
            this.loadedCount = null;
        } else {
            this.loadedCount = loadedCount;
        }
    }

    public BatchStat loadedAmount(BigDecimal loadedAmount) {
        this.loadedAmount = loadedAmount;
        return this;
    }

     /**
     * Get loadedAmount
     * @return loadedAmount
    **/
    @ApiModelProperty(value = "")
    public BigDecimal getLoadedAmount() {
        return loadedAmount;
    }

    public void setLoadedAmount(BigDecimal loadedAmount) {
        if(loadedAmount == null) {
            this.loadedAmount = null;
        } else {
            this.loadedAmount = loadedAmount;
        }
    }

    public BatchStat loadedPercent(BigDecimal loadedPercent) {
        this.loadedPercent = loadedPercent;
        return this;
    }

     /**
     * Get loadedPercent
     * @return loadedPercent
    **/
    @ApiModelProperty(value = "")
    public BigDecimal getLoadedPercent() {
        return loadedPercent;
    }

    public void setLoadedPercent(BigDecimal loadedPercent) {
        if(loadedPercent == null) {
            this.loadedPercent = null;
        } else {
            this.loadedPercent = loadedPercent;
        }
    }

    public BatchStat createdCount(Integer createdCount) {
        this.createdCount = createdCount;
        return this;
    }

     /**
     * Get createdCount
     * @return createdCount
    **/
    @ApiModelProperty(value = "")
    public Integer getCreatedCount() {
        return createdCount;
    }

    public void setCreatedCount(Integer createdCount) {
        if(createdCount == null) {
            this.createdCount = null;
        } else {
            this.createdCount = createdCount;
        }
    }

    public BatchStat createdAmount(BigDecimal createdAmount) {
        this.createdAmount = createdAmount;
        return this;
    }

     /**
     * Get createdAmount
     * @return createdAmount
    **/
    @ApiModelProperty(value = "")
    public BigDecimal getCreatedAmount() {
        return createdAmount;
    }

    public void setCreatedAmount(BigDecimal createdAmount) {
        if(createdAmount == null) {
            this.createdAmount = null;
        } else {
            this.createdAmount = createdAmount;
        }
    }

    public BatchStat createdPercent(BigDecimal createdPercent) {
        this.createdPercent = createdPercent;
        return this;
    }

     /**
     * Get createdPercent
     * @return createdPercent
    **/
    @ApiModelProperty(value = "")
    public BigDecimal getCreatedPercent() {
        return createdPercent;
    }

    public void setCreatedPercent(BigDecimal createdPercent) {
        if(createdPercent == null) {
            this.createdPercent = null;
        } else {
            this.createdPercent = createdPercent;
        }
    }

    public BatchStat matchedCount(Integer matchedCount) {
        this.matchedCount = matchedCount;
        return this;
    }

     /**
     * Get matchedCount
     * @return matchedCount
    **/
    @ApiModelProperty(value = "")
    public Integer getMatchedCount() {
        return matchedCount;
    }

    public void setMatchedCount(Integer matchedCount) {
        if(matchedCount == null) {
            this.matchedCount = null;
        } else {
            this.matchedCount = matchedCount;
        }
    }

    public BatchStat matchedAmount(BigDecimal matchedAmount) {
        this.matchedAmount = matchedAmount;
        return this;
    }

     /**
     * Get matchedAmount
     * @return matchedAmount
    **/
    @ApiModelProperty(value = "")
    public BigDecimal getMatchedAmount() {
        return matchedAmount;
    }

    public void setMatchedAmount(BigDecimal matchedAmount) {
        if(matchedAmount == null) {
            this.matchedAmount = null;
        } else {
            this.matchedAmount = matchedAmount;
        }
    }

    public BatchStat matchedPercent(BigDecimal matchedPercent) {
        this.matchedPercent = matchedPercent;
        return this;
    }

     /**
     * Get matchedPercent
     * @return matchedPercent
    **/
    @ApiModelProperty(value = "")
    public BigDecimal getMatchedPercent() {
        return matchedPercent;
    }

    public void setMatchedPercent(BigDecimal matchedPercent) {
        if(matchedPercent == null) {
            this.matchedPercent = null;
        } else {
            this.matchedPercent = matchedPercent;
        }
    }

    public BatchStat worksNotFoundCount(Integer worksNotFoundCount) {
        this.worksNotFoundCount = worksNotFoundCount;
        return this;
    }

     /**
     * Get worksNotFoundCount
     * @return worksNotFoundCount
    **/
    @ApiModelProperty(value = "")
    public Integer getWorksNotFoundCount() {
        return worksNotFoundCount;
    }

    public void setWorksNotFoundCount(Integer worksNotFoundCount) {
        if(worksNotFoundCount == null) {
            this.worksNotFoundCount = null;
        } else {
            this.worksNotFoundCount = worksNotFoundCount;
        }
    }

    public BatchStat worksNotFoundAmount(BigDecimal worksNotFoundAmount) {
        this.worksNotFoundAmount = worksNotFoundAmount;
        return this;
    }

     /**
     * Get worksNotFoundAmount
     * @return worksNotFoundAmount
    **/
    @ApiModelProperty(value = "")
    public BigDecimal getWorksNotFoundAmount() {
        return worksNotFoundAmount;
    }

    public void setWorksNotFoundAmount(BigDecimal worksNotFoundAmount) {
        if(worksNotFoundAmount == null) {
            this.worksNotFoundAmount = null;
        } else {
            this.worksNotFoundAmount = worksNotFoundAmount;
        }
    }

    public BatchStat worksNotFoundPercent(BigDecimal worksNotFoundPercent) {
        this.worksNotFoundPercent = worksNotFoundPercent;
        return this;
    }

     /**
     * Get worksNotFoundPercent
     * @return worksNotFoundPercent
    **/
    @ApiModelProperty(value = "")
    public BigDecimal getWorksNotFoundPercent() {
        return worksNotFoundPercent;
    }

    public void setWorksNotFoundPercent(BigDecimal worksNotFoundPercent) {
        if(worksNotFoundPercent == null) {
            this.worksNotFoundPercent = null;
        } else {
            this.worksNotFoundPercent = worksNotFoundPercent;
        }
    }

    public BatchStat multipleMatchingCount(Integer multipleMatchingCount) {
        this.multipleMatchingCount = multipleMatchingCount;
        return this;
    }

     /**
     * Get multipleMatchingCount
     * @return multipleMatchingCount
    **/
    @ApiModelProperty(value = "")
    public Integer getMultipleMatchingCount() {
        return multipleMatchingCount;
    }

    public void setMultipleMatchingCount(Integer multipleMatchingCount) {
        if(multipleMatchingCount == null) {
            this.multipleMatchingCount = null;
        } else {
            this.multipleMatchingCount = multipleMatchingCount;
        }
    }

    public BatchStat multipleMatchingAmount(BigDecimal multipleMatchingAmount) {
        this.multipleMatchingAmount = multipleMatchingAmount;
        return this;
    }

     /**
     * Get multipleMatchingAmount
     * @return multipleMatchingAmount
    **/
    @ApiModelProperty(value = "")
    public BigDecimal getMultipleMatchingAmount() {
        return multipleMatchingAmount;
    }

    public void setMultipleMatchingAmount(BigDecimal multipleMatchingAmount) {
        if(multipleMatchingAmount == null) {
            this.multipleMatchingAmount = null;
        } else {
            this.multipleMatchingAmount = multipleMatchingAmount;
        }
    }

    public BatchStat multipleMatchingPercent(BigDecimal multipleMatchingPercent) {
        this.multipleMatchingPercent = multipleMatchingPercent;
        return this;
    }

     /**
     * Get multipleMatchingPercent
     * @return multipleMatchingPercent
    **/
    @ApiModelProperty(value = "")
    public BigDecimal getMultipleMatchingPercent() {
        return multipleMatchingPercent;
    }

    public void setMultipleMatchingPercent(BigDecimal multipleMatchingPercent) {
        if(multipleMatchingPercent == null) {
            this.multipleMatchingPercent = null;
        } else {
            this.multipleMatchingPercent = multipleMatchingPercent;
        }
    }

    public BatchStat ntsWithdrawnCount(Integer ntsWithdrawnCount) {
        this.ntsWithdrawnCount = ntsWithdrawnCount;
        return this;
    }

     /**
     * Get ntsWithdrawnCount
     * @return ntsWithdrawnCount
    **/
    @ApiModelProperty(value = "")
    public Integer getNtsWithdrawnCount() {
        return ntsWithdrawnCount;
    }

    public void setNtsWithdrawnCount(Integer ntsWithdrawnCount) {
        if(ntsWithdrawnCount == null) {
            this.ntsWithdrawnCount = null;
        } else {
            this.ntsWithdrawnCount = ntsWithdrawnCount;
        }
    }

    public BatchStat ntsWithdrawnAmount(BigDecimal ntsWithdrawnAmount) {
        this.ntsWithdrawnAmount = ntsWithdrawnAmount;
        return this;
    }

     /**
     * Get ntsWithdrawnAmount
     * @return ntsWithdrawnAmount
    **/
    @ApiModelProperty(value = "")
    public BigDecimal getNtsWithdrawnAmount() {
        return ntsWithdrawnAmount;
    }

    public void setNtsWithdrawnAmount(BigDecimal ntsWithdrawnAmount) {
        if(ntsWithdrawnAmount == null) {
            this.ntsWithdrawnAmount = null;
        } else {
            this.ntsWithdrawnAmount = ntsWithdrawnAmount;
        }
    }

    public BatchStat ntsWithdrawnPercent(BigDecimal ntsWithdrawnPercent) {
        this.ntsWithdrawnPercent = ntsWithdrawnPercent;
        return this;
    }

     /**
     * Get ntsWithdrawnPercent
     * @return ntsWithdrawnPercent
    **/
    @ApiModelProperty(value = "")
    public BigDecimal getNtsWithdrawnPercent() {
        return ntsWithdrawnPercent;
    }

    public void setNtsWithdrawnPercent(BigDecimal ntsWithdrawnPercent) {
        if(ntsWithdrawnPercent == null) {
            this.ntsWithdrawnPercent = null;
        } else {
            this.ntsWithdrawnPercent = ntsWithdrawnPercent;
        }
    }

    public BatchStat rhNotFoundCount(Integer rhNotFoundCount) {
        this.rhNotFoundCount = rhNotFoundCount;
        return this;
    }

     /**
     * Get rhNotFoundCount
     * @return rhNotFoundCount
    **/
    @ApiModelProperty(value = "")
    public Integer getRhNotFoundCount() {
        return rhNotFoundCount;
    }

    public void setRhNotFoundCount(Integer rhNotFoundCount) {
        if(rhNotFoundCount == null) {
            this.rhNotFoundCount = null;
        } else {
            this.rhNotFoundCount = rhNotFoundCount;
        }
    }

    public BatchStat rhNotFoundAmount(BigDecimal rhNotFoundAmount) {
        this.rhNotFoundAmount = rhNotFoundAmount;
        return this;
    }

     /**
     * Get rhNotFoundAmount
     * @return rhNotFoundAmount
    **/
    @ApiModelProperty(value = "")
    public BigDecimal getRhNotFoundAmount() {
        return rhNotFoundAmount;
    }

    public void setRhNotFoundAmount(BigDecimal rhNotFoundAmount) {
        if(rhNotFoundAmount == null) {
            this.rhNotFoundAmount = null;
        } else {
            this.rhNotFoundAmount = rhNotFoundAmount;
        }
    }

    public BatchStat rhNotFoundPercent(BigDecimal rhNotFoundPercent) {
        this.rhNotFoundPercent = rhNotFoundPercent;
        return this;
    }

     /**
     * Get rhNotFoundPercent
     * @return rhNotFoundPercent
    **/
    @ApiModelProperty(value = "")
    public BigDecimal getRhNotFoundPercent() {
        return rhNotFoundPercent;
    }

    public void setRhNotFoundPercent(BigDecimal rhNotFoundPercent) {
        if(rhNotFoundPercent == null) {
            this.rhNotFoundPercent = null;
        } else {
            this.rhNotFoundPercent = rhNotFoundPercent;
        }
    }

    public BatchStat rhFoundCount(Integer rhFoundCount) {
        this.rhFoundCount = rhFoundCount;
        return this;
    }

     /**
     * Get rhFoundCount
     * @return rhFoundCount
    **/
    @ApiModelProperty(value = "")
    public Integer getRhFoundCount() {
        return rhFoundCount;
    }

    public void setRhFoundCount(Integer rhFoundCount) {
        if(rhFoundCount == null) {
            this.rhFoundCount = null;
        } else {
            this.rhFoundCount = rhFoundCount;
        }
    }

    public BatchStat rhFoundAmount(BigDecimal rhFoundAmount) {
        this.rhFoundAmount = rhFoundAmount;
        return this;
    }

     /**
     * Get rhFoundAmount
     * @return rhFoundAmount
    **/
    @ApiModelProperty(value = "")
    public BigDecimal getRhFoundAmount() {
        return rhFoundAmount;
    }

    public void setRhFoundAmount(BigDecimal rhFoundAmount) {
        if(rhFoundAmount == null) {
            this.rhFoundAmount = null;
        } else {
            this.rhFoundAmount = rhFoundAmount;
        }
    }

    public BatchStat rhFoundPercent(BigDecimal rhFoundPercent) {
        this.rhFoundPercent = rhFoundPercent;
        return this;
    }

     /**
     * Get rhFoundPercent
     * @return rhFoundPercent
    **/
    @ApiModelProperty(value = "")
    public BigDecimal getRhFoundPercent() {
        return rhFoundPercent;
    }

    public void setRhFoundPercent(BigDecimal rhFoundPercent) {
        if(rhFoundPercent == null) {
            this.rhFoundPercent = null;
        } else {
            this.rhFoundPercent = rhFoundPercent;
        }
    }

    public BatchStat eligibleCount(Integer eligibleCount) {
        this.eligibleCount = eligibleCount;
        return this;
    }

     /**
     * Get eligibleCount
     * @return eligibleCount
    **/
    @ApiModelProperty(value = "")
    public Integer getEligibleCount() {
        return eligibleCount;
    }

    public void setEligibleCount(Integer eligibleCount) {
        if(eligibleCount == null) {
            this.eligibleCount = null;
        } else {
            this.eligibleCount = eligibleCount;
        }
    }

    public BatchStat eligibleAmount(BigDecimal eligibleAmount) {
        this.eligibleAmount = eligibleAmount;
        return this;
    }

     /**
     * Get eligibleAmount
     * @return eligibleAmount
    **/
    @ApiModelProperty(value = "")
    public BigDecimal getEligibleAmount() {
        return eligibleAmount;
    }

    public void setEligibleAmount(BigDecimal eligibleAmount) {
        if(eligibleAmount == null) {
            this.eligibleAmount = null;
        } else {
            this.eligibleAmount = eligibleAmount;
        }
    }

    public BatchStat eligiblePercent(BigDecimal eligiblePercent) {
        this.eligiblePercent = eligiblePercent;
        return this;
    }

     /**
     * Get eligiblePercent
     * @return eligiblePercent
    **/
    @ApiModelProperty(value = "")
    public BigDecimal getEligiblePercent() {
        return eligiblePercent;
    }

    public void setEligiblePercent(BigDecimal eligiblePercent) {
        if(eligiblePercent == null) {
            this.eligiblePercent = null;
        } else {
            this.eligiblePercent = eligiblePercent;
        }
    }

    public BatchStat sendForRaCount(Integer sendForRaCount) {
        this.sendForRaCount = sendForRaCount;
        return this;
    }

     /**
     * Get sendForRaCount
     * @return sendForRaCount
    **/
    @ApiModelProperty(value = "")
    public Integer getSendForRaCount() {
        return sendForRaCount;
    }

    public void setSendForRaCount(Integer sendForRaCount) {
        if(sendForRaCount == null) {
            this.sendForRaCount = null;
        } else {
            this.sendForRaCount = sendForRaCount;
        }
    }

    public BatchStat sendForRaAmount(BigDecimal sendForRaAmount) {
        this.sendForRaAmount = sendForRaAmount;
        return this;
    }

     /**
     * Get sendForRaAmount
     * @return sendForRaAmount
    **/
    @ApiModelProperty(value = "")
    public BigDecimal getSendForRaAmount() {
        return sendForRaAmount;
    }

    public void setSendForRaAmount(BigDecimal sendForRaAmount) {
        if(sendForRaAmount == null) {
            this.sendForRaAmount = null;
        } else {
            this.sendForRaAmount = sendForRaAmount;
        }
    }

    public BatchStat sendForRaPercent(BigDecimal sendForRaPercent) {
        this.sendForRaPercent = sendForRaPercent;
        return this;
    }

     /**
     * Get sendForRaPercent
     * @return sendForRaPercent
    **/
    @ApiModelProperty(value = "")
    public BigDecimal getSendForRaPercent() {
        return sendForRaPercent;
    }

    public void setSendForRaPercent(BigDecimal sendForRaPercent) {
        if(sendForRaPercent == null) {
            this.sendForRaPercent = null;
        } else {
            this.sendForRaPercent = sendForRaPercent;
        }
    }

    public BatchStat paidCount(Integer paidCount) {
        this.paidCount = paidCount;
        return this;
    }

     /**
     * Get paidCount
     * @return paidCount
    **/
    @ApiModelProperty(value = "")
    public Integer getPaidCount() {
        return paidCount;
    }

    public void setPaidCount(Integer paidCount) {
        if(paidCount == null) {
            this.paidCount = null;
        } else {
            this.paidCount = paidCount;
        }
    }

    public BatchStat paidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
        return this;
    }

     /**
     * Get paidAmount
     * @return paidAmount
    **/
    @ApiModelProperty(value = "")
    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        if(paidAmount == null) {
            this.paidAmount = null;
        } else {
            this.paidAmount = paidAmount;
        }
    }

    public BatchStat paidPercent(BigDecimal paidPercent) {
        this.paidPercent = paidPercent;
        return this;
    }

     /**
     * Get paidPercent
     * @return paidPercent
    **/
    @ApiModelProperty(value = "")
    public BigDecimal getPaidPercent() {
        return paidPercent;
    }

    public void setPaidPercent(BigDecimal paidPercent) {
        if(paidPercent == null) {
            this.paidPercent = null;
        } else {
            this.paidPercent = paidPercent;
        }
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
                Objects.equals(this.loadedCount, batchStat.loadedCount) &&
                Objects.equals(this.loadedAmount, batchStat.loadedAmount) &&
                Objects.equals(this.loadedPercent, batchStat.loadedPercent) &&
                Objects.equals(this.createdCount, batchStat.createdCount) &&
                Objects.equals(this.createdAmount, batchStat.createdAmount) &&
                Objects.equals(this.createdPercent, batchStat.createdPercent) &&
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
        return Objects.hash(batchName, totalCount, loadedCount, loadedAmount, loadedPercent, createdCount, createdAmount, createdPercent, matchedCount, matchedAmount, matchedPercent, worksNotFoundCount, worksNotFoundAmount, worksNotFoundPercent, multipleMatchingCount, multipleMatchingAmount, multipleMatchingPercent, ntsWithdrawnCount, ntsWithdrawnAmount, ntsWithdrawnPercent, rhNotFoundCount, rhNotFoundAmount, rhNotFoundPercent, rhFoundCount, rhFoundAmount, rhFoundPercent, eligibleCount, eligibleAmount, eligiblePercent, sendForRaCount, sendForRaAmount, sendForRaPercent, paidCount, paidAmount, paidPercent);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class BatchStat {\n");
        
        sb.append("        batchName: ").append(toIndentedString(batchName)).append("\n");
        sb.append("        totalCount: ").append(toIndentedString(totalCount)).append("\n");
        sb.append("        loadedCount: ").append(toIndentedString(loadedCount)).append("\n");
        sb.append("        loadedAmount: ").append(toIndentedString(loadedAmount)).append("\n");
        sb.append("        loadedPercent: ").append(toIndentedString(loadedPercent)).append("\n");
        sb.append("        createdCount: ").append(toIndentedString(createdCount)).append("\n");
        sb.append("        createdAmount: ").append(toIndentedString(createdAmount)).append("\n");
        sb.append("        createdPercent: ").append(toIndentedString(createdPercent)).append("\n");
        sb.append("        matchedCount: ").append(toIndentedString(matchedCount)).append("\n");
        sb.append("        matchedAmount: ").append(toIndentedString(matchedAmount)).append("\n");
        sb.append("        matchedPercent: ").append(toIndentedString(matchedPercent)).append("\n");
        sb.append("        worksNotFoundCount: ").append(toIndentedString(worksNotFoundCount)).append("\n");
        sb.append("        worksNotFoundAmount: ").append(toIndentedString(worksNotFoundAmount)).append("\n");
        sb.append("        worksNotFoundPercent: ").append(toIndentedString(worksNotFoundPercent)).append("\n");
        sb.append("        multipleMatchingCount: ").append(toIndentedString(multipleMatchingCount)).append("\n");
        sb.append("        multipleMatchingAmount: ").append(toIndentedString(multipleMatchingAmount)).append("\n");
        sb.append("        multipleMatchingPercent: ").append(toIndentedString(multipleMatchingPercent)).append("\n");
        sb.append("        ntsWithdrawnCount: ").append(toIndentedString(ntsWithdrawnCount)).append("\n");
        sb.append("        ntsWithdrawnAmount: ").append(toIndentedString(ntsWithdrawnAmount)).append("\n");
        sb.append("        ntsWithdrawnPercent: ").append(toIndentedString(ntsWithdrawnPercent)).append("\n");
        sb.append("        rhNotFoundCount: ").append(toIndentedString(rhNotFoundCount)).append("\n");
        sb.append("        rhNotFoundAmount: ").append(toIndentedString(rhNotFoundAmount)).append("\n");
        sb.append("        rhNotFoundPercent: ").append(toIndentedString(rhNotFoundPercent)).append("\n");
        sb.append("        rhFoundCount: ").append(toIndentedString(rhFoundCount)).append("\n");
        sb.append("        rhFoundAmount: ").append(toIndentedString(rhFoundAmount)).append("\n");
        sb.append("        rhFoundPercent: ").append(toIndentedString(rhFoundPercent)).append("\n");
        sb.append("        eligibleCount: ").append(toIndentedString(eligibleCount)).append("\n");
        sb.append("        eligibleAmount: ").append(toIndentedString(eligibleAmount)).append("\n");
        sb.append("        eligiblePercent: ").append(toIndentedString(eligiblePercent)).append("\n");
        sb.append("        sendForRaCount: ").append(toIndentedString(sendForRaCount)).append("\n");
        sb.append("        sendForRaAmount: ").append(toIndentedString(sendForRaAmount)).append("\n");
        sb.append("        sendForRaPercent: ").append(toIndentedString(sendForRaPercent)).append("\n");
        sb.append("        paidCount: ").append(toIndentedString(paidCount)).append("\n");
        sb.append("        paidAmount: ").append(toIndentedString(paidAmount)).append("\n");
        sb.append("        paidPercent: ").append(toIndentedString(paidPercent)).append("\n");
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
        return o.toString().replace("\n", "\n        ");
    }
}

