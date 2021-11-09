package com.copyright.rup.dist.foreign.ui.rest.gen.model;

import java.util.Objects;
import com.copyright.rup.dist.foreign.ui.rest.gen.model.BatchStat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Batches statistic
 */
@ApiModel(description = "Batches statistic")
@JsonInclude(JsonInclude.Include.NON_NULL)

public class BatchStats   {
  @JsonProperty("date")
  private String date;

  @JsonProperty("dateFrom")
  private String dateFrom;

  @JsonProperty("dateTo")
  private String dateTo;

  @JsonProperty("statistic")
  @Valid
  private List<BatchStat> statistic = null;

  public BatchStats date(String date) {
    this.date = date;
    return this;
  }

  /**
   * Get date
   * @return date
  */
  @ApiModelProperty(value = "")


  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public BatchStats dateFrom(String dateFrom) {
    this.dateFrom = dateFrom;
    return this;
  }

  /**
   * Get dateFrom
   * @return dateFrom
  */
  @ApiModelProperty(value = "")


  public String getDateFrom() {
    return dateFrom;
  }

  public void setDateFrom(String dateFrom) {
    this.dateFrom = dateFrom;
  }

  public BatchStats dateTo(String dateTo) {
    this.dateTo = dateTo;
    return this;
  }

  /**
   * Get dateTo
   * @return dateTo
  */
  @ApiModelProperty(value = "")


  public String getDateTo() {
    return dateTo;
  }

  public void setDateTo(String dateTo) {
    this.dateTo = dateTo;
  }

  public BatchStats statistic(List<BatchStat> statistic) {
    this.statistic = statistic;
    return this;
  }

  public BatchStats addStatisticItem(BatchStat statisticItem) {
    if (this.statistic == null) {
      this.statistic = new ArrayList<>();
    }
    this.statistic.add(statisticItem);
    return this;
  }

  /**
   * Get statistic
   * @return statistic
  */
  @ApiModelProperty(value = "")

  @Valid

  public List<BatchStat> getStatistic() {
    return statistic;
  }

  public void setStatistic(List<BatchStat> statistic) {
    this.statistic = statistic;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BatchStats batchStats = (BatchStats) o;
    return Objects.equals(this.date, batchStats.date) &&
        Objects.equals(this.dateFrom, batchStats.dateFrom) &&
        Objects.equals(this.dateTo, batchStats.dateTo) &&
        Objects.equals(this.statistic, batchStats.statistic);
  }

  @Override
  public int hashCode() {
    return Objects.hash(date, dateFrom, dateTo, statistic);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BatchStats {\n");
    
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("    dateFrom: ").append(toIndentedString(dateFrom)).append("\n");
    sb.append("    dateTo: ").append(toIndentedString(dateTo)).append("\n");
    sb.append("    statistic: ").append(toIndentedString(statistic)).append("\n");
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

